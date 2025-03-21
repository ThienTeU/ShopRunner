package NgocHieu;

import DAL.OrderDAO;
import DAL.ProductDAO;
import Model.CartItem;
import Model.CartItemDTO;
import Model.Color;
import Model.Orders;
import Model.Product;
import Model.ProductPrice;
import Model.ProductQuantity;
import Model.Size;
import NgocHieu.GHTKService.GHTKApiService;
import NgocHieu.GHTKService.OrderGhtk;
import NgocHieu.GHTKService.OrderRequest;
import NgocHieu.GHTKService.ProductGhtk;
import NgocHieu.service.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.jose.JOSEException;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CheckOutServlet.class.getName());
    private final ProductDAO productDAO = new ProductDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private final GHTKApiService ghtkService = new GHTKApiService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<CartItem> cartItems = getCartItemsFromCookies(request);
            if (cartItems == null || cartItems.isEmpty()) {
                response.sendRedirect("CartDetailServlet");
                return;
            }

            List<CartItemDTO> cartItemsDTO = processCartItems(cartItems);
            int total = calculateTotal(cartItemsDTO);
            
            setupCheckoutPage(request, cartItemsDTO, total);
            request.getRequestDispatcher("NgocHieu/CheckOutJSP.jsp").forward(request, response);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error in doGet", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String contextPath = request.getContextPath();
            request.getSession().setAttribute("contextPath", contextPath);
            response.setContentType("text/html; charset=UTF-8");

            List<CartItem> cartItems = getCartItemsFromCookies(request);
            if (cartItems.isEmpty()) {
                handleError(response, "Giỏ hàng trống", HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Orders order = createOrderFromRequest(request);
            try {
                validateOrder(order);
            } catch (IllegalArgumentException e) {
                handleError(response, e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String paymentMethod = request.getParameter("paymentMethod");
            if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
                handleError(response, "Vui lòng chọn phương thức thanh toán", HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            if ("cod".equals(paymentMethod)) {
                handleCODOrder(request, response, order, cartItems);
            } else if ("vnpay".equals(paymentMethod)) {
                handleVNPayOrder(request, response, order);
            } else {
                handleError(response, "Phương thức thanh toán không hợp lệ", HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException ex) {
            handleError(response, "Lỗi xử lý đơn hàng", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private List<CartItem> getCartItemsFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return new ArrayList<>();
        }
        
        return Arrays.stream(cookies)
                .filter(cookie -> "cart".equals(cookie.getName()))
                .findFirst()
                .map(cookie -> {
                    try {
                        return AuthenticationService.decodeCartToken(cookie.getValue());
                    } catch (JOSEException | ParseException e) {
                        LOGGER.log(Level.WARNING, "Error decoding cart token", e);
                        return new ArrayList<CartItem>();
                    }
                })
                .orElse(new ArrayList<>());
    }

    private List<CartItemDTO> processCartItems(List<CartItem> cartItems) throws SQLException {
        return cartItems.stream()
                .map(item -> {
                    try {
                        Product product = productDAO.getProductById(item.getProduct_id());
                        ProductPrice productPrice = productDAO.getProductPriceById(item.getProductprice_id());
                        ProductQuantity productQuantity = productDAO.getProductQuantityById(item.getProductquantity_id());
                        return new CartItemDTO(product, productPrice, productQuantity, item.getQuantity());
                    } catch (SQLException e) {
                        LOGGER.log(Level.SEVERE, "Error processing cart item", e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private int calculateTotal(List<CartItemDTO> cartItemsDTO) {
        return (int) cartItemsDTO.stream()
                .mapToDouble(item -> item.getProductPrice().getPrice() * item.getQuantity())
                .sum();
    }

    private void setupCheckoutPage(HttpServletRequest request, List<CartItemDTO> cartItemsDTO, int total) 
            throws SQLException {
        List<Size> listSize = productDAO.getAllSizes();
        List<Color> listColor = productDAO.getAllColors();
        
        request.setAttribute("total", total);
        request.setAttribute("listColor", listColor);
        request.setAttribute("listSize", listSize);
        request.setAttribute("cartItemsDTO", cartItemsDTO);
    }

    private String formatAddress(String street, String ward, String district, String city) {
        return String.format("%s, %s, %s, %s", street, ward, district, city);
    }

    private String[] parseAddress(String address) {
        return address.split(", ");
    }

    private Orders createOrderFromRequest(HttpServletRequest request) {
        String email = sanitizeInput(request.getParameter("email").trim());
        String name = sanitizeInput(request.getParameter("name").trim());
        String phone = sanitizeInput(request.getParameter("phone"));
        String city = request.getParameter("city");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        String street = sanitizeInput(request.getParameter("street"));
        String total = request.getParameter("total");
        String voucher = sanitizeInput(request.getParameter("voucher"));
        int voucher_id = -1; // TODO: Implement voucher validation

        String address = formatAddress(street, ward, district, city);
        return new Orders(email, Integer.parseInt(total), voucher_id, address, phone);
    }

    private void handleCODOrder(HttpServletRequest request, HttpServletResponse response, 
            Orders order, List<CartItem> cartItems) throws SQLException, IOException {
        order.setStatus("Pending");
        order.setPayment_method("cod");
        
        int order_id = orderDAO.insertOrder(order);
        if (order_id > 0) {
            List<ProductGhtk> listProductGhtk = createProductGhtkList(cartItems);
            order.setOrder_id(order_id);
            
            request.getSession().setAttribute("order", order);
            request.getSession().setAttribute("order_id", order_id);
            
            saveOrderDetails(order_id, cartItems);
            sendOrderToGHTK(order_id, listProductGhtk, order);
            
            response.sendRedirect("SendOrderToEmailServlet");
        }
    }

    private void handleVNPayOrder(HttpServletRequest request, HttpServletResponse response, Orders order) throws IOException {
        order.setPayment_method("vnpay");
        request.getSession().setAttribute("order", order);
        
        OrderGhtk orderGhtk = createOrderGhtk(order);
        request.getSession().setAttribute("orderGhtk", orderGhtk);
        
        response.sendRedirect("NgocHieu/vnpay/vnpay_pay.jsp");
    }

    private List<ProductGhtk> createProductGhtkList(List<CartItem> cartItems) throws SQLException {
        List<ProductGhtk> listProductGhtk = new ArrayList<>();
        for (CartItem item : cartItems) {
            Product product = productDAO.getProductById(item.getProduct_id());
            listProductGhtk.add(new ProductGhtk(
                    product.getProduct_name(),
                    0.2,
                    item.getQuantity(),
                    item.getProduct_id()
            ));
        }
        return listProductGhtk;
    }

    private void saveOrderDetails(int order_id, List<CartItem> cartItems) throws SQLException {
        for (CartItem item : cartItems) {
            orderDAO.insertOrderDetail(order_id, item);
        }
    }

    private void sendOrderToGHTK(int order_id, List<ProductGhtk> listProductGhtk, Orders order) throws JsonProcessingException, IOException {
        String[] addressParts = parseAddress(order.getShipping_address());
        OrderGhtk orderGhtk = new OrderGhtk(
                String.valueOf(order_id),
                order.getPhone(),
                order.getEmail(),
                addressParts[0], // street
                addressParts[3], // city
                addressParts[2], // district
                addressParts[1], // ward
                order.getTotal_price(),
                order.getTotal_price()
        );
        
        OrderRequest orderRequest = new OrderRequest(listProductGhtk, orderGhtk);
        ghtkService.sendOrderToGHTK(orderRequest.toJsonBody(orderRequest));
    }

    private OrderGhtk createOrderGhtk(Orders order) {
        String[] addressParts = parseAddress(order.getShipping_address());
        return new OrderGhtk(
                "",
                order.getPhone(),
                order.getEmail(),
                addressParts[0], // street
                addressParts[3], // city
                addressParts[2], // district
                addressParts[1], // ward
                order.getTotal_price(),
                order.getTotal_price()
        );
    }
    
    public String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }

        // Xóa ký tự script
        input = input.replaceAll("(?i)<script.*?>.*?</script>", "");

        // Encode các ký tự đặc biệt
        input = input.replaceAll("<", "&lt;")
                     .replaceAll(">", "&gt;")
                     .replaceAll("\"", "&quot;")
                     .replaceAll("'", "&#x27;")
                     .replaceAll("&", "&amp;");

        return input;
    }

    private void validateOrder(Orders order) {
        if (order.getEmail() == null || order.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống");
        }
        if (order.getPhone() == null || order.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("Số điện thoại không được để trống");
        }
        if (order.getShipping_address() == null || order.getShipping_address().trim().isEmpty()) {
            throw new IllegalArgumentException("Địa chỉ không được để trống");
        }
        if (order.getTotal_price() <= 0) {
            throw new IllegalArgumentException("Tổng tiền không hợp lệ");
        }
    }

    private void handleError(HttpServletResponse response, String message, int statusCode) throws IOException {
        LOGGER.log(Level.SEVERE, message);
        response.setStatus(statusCode);
        response.getWriter().write(message);
    }
}
