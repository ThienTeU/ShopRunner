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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Cookie[] cookies = request.getCookies();
            List<CartItem> cartItems = new ArrayList<>();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("cart")) {
                        try {
                            cartItems = AuthenticationService.decodeCartToken(cookie.getValue());//decode cart token tu cookie
                        } catch (JOSEException | ParseException e) {
                        }
                        break;
                    }
                }
            }

            if (cartItems == null) {
                response.sendRedirect("CartDetailServlet");
                return;
            }

            List<CartItemDTO> cartItemsDTO = new ArrayList<>();
            ProductDAO productDAO = new ProductDAO();
            List<Size> listSize = productDAO.getAllSizes();
            List<Color> listColor = productDAO.getAllColors();

            int total = 0;
            for (CartItem item : cartItems) {
                Product product = productDAO.getProductById(item.getProduct_id());
                ProductPrice productPrice = productDAO.getProductPriceById(item.getProductprice_id());
                ProductQuantity productQuantity = productDAO.getProductQuantityById(item.getProductquantity_id());
                total += productPrice.getPrice() * item.getQuantity();
                CartItemDTO dto = new CartItemDTO(product, productPrice, productQuantity, item.getQuantity());

                cartItemsDTO.add(dto);
            }
            request.setAttribute("total", total);
            request.setAttribute("listColor", listColor);
            request.setAttribute("listSize", listSize);
            request.setAttribute("cartItemsDTO", cartItemsDTO);

            request.getRequestDispatcher("NgocHieu/CheckOutJSP.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Checkout lai thong tin cua nguoi mua
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String contextPath = request.getContextPath();
        request.getSession().setAttribute("contextPath", contextPath);
        response.setContentType("text/html; charset=UTF-8");

        Cookie[] cookies = request.getCookies();
        List<CartItem> cartItems = new ArrayList<>();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cart")) {
                    try {
                        cartItems = AuthenticationService.decodeCartToken(cookie.getValue());//decode cart token tu cookie
                    } catch (JOSEException | ParseException e) {
                    }
                    break;
                }
            }
        }

        if (cartItems.isEmpty()) {
            response.sendRedirect("CartDetailServlet");
            return;
        }

        //String shippingFee = sanitizeInput(request.getParameter("shippingFee1"));
        String total = request.getParameter("total");
        //String totalPrice = request.getParameter("totalPrice1");
        //User user = (User) request.getSession().getAttribute("user");
        String email = sanitizeInput(request.getParameter("email").trim());
        String name = sanitizeInput(request.getParameter("name").trim());
        String phone = sanitizeInput(request.getParameter("phone"));
        String city = request.getParameter("city");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        String street = sanitizeInput(request.getParameter("street"));
        String paymentMethod = request.getParameter("paymentMethod");
        String voucher = sanitizeInput(request.getParameter("voucher"));
        //Demo get voucher_id by voucher
        int voucher_id = -1;

        String address = street + ", " + ward + ", " + district + ", " + city;
        Orders order = new Orders(email, Integer.parseInt(total), voucher_id, address, phone);
        if (paymentMethod.equals("cod")) {
            order.setPayment_method(paymentMethod);
            OrderDAO orderDAO = new OrderDAO();
            try {
                ProductDAO productDao = new ProductDAO();
                int order_id = orderDAO.insertOrder(order);
                List<ProductGhtk> listProductGhtk = new ArrayList<>();
                order.setOrder_id(order_id);
                request.getSession().setAttribute("order", order);
                request.getSession().setAttribute("order_id", order_id);
                if (order_id > 0) {
                    for (CartItem item : cartItems) {
                        orderDAO.insertOrderDetail(order_id, item);
                        listProductGhtk.add(new ProductGhtk(
                                productDao.getProductById(item.getProduct_id()).getProduct_name(),
                                0.2,
                                item.getQuantity(),
                                item.getProduct_id()
                        ));
                    }
                }
                OrderGhtk orderGhtk = new OrderGhtk(String.valueOf(order_id), phone, name, street, city, district,
                        ward, Integer.parseInt(total), Integer.parseInt(total));
                
                OrderRequest orderRequest = new OrderRequest(listProductGhtk, orderGhtk);
                GHTKApiService ghtkService = new GHTKApiService();
                ghtkService.sendOrderToGHTK(orderRequest.toJsonBody(orderRequest));

                response.sendRedirect("SendOrderToEmailServlet");
            } catch (SQLException ex) {
                Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (paymentMethod.equals("vnpay")) {
            order.setPayment_method(paymentMethod);
            request.getSession().setAttribute("order", order);
            OrderGhtk orderGhtk = new OrderGhtk("", phone, name, street, city, district,
                        ward, Integer.parseInt(total), Integer.parseInt(total));
            request.getSession().setAttribute("orderGhtk", orderGhtk);
            response.sendRedirect("NgocHieu/vnpay/vnpay_pay.jsp");
        }
    }
    
    public String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }

        // Xoa ki tu script
        input = input.replaceAll("(?i)<script.*?>.*?</script>", "");

        // Encode 
        input = input.replaceAll("<", "&lt;")
                     .replaceAll(">", "&gt;")
                     .replaceAll("\"", "&quot;")
                     .replaceAll("'", "&#x27;")
                     .replaceAll("&", "&amp;");

        return input;
    }
}
