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
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cart");
            if (cartItems == null) {
                cartItems = new ArrayList<>();
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
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String contextPath = request.getContextPath();
        request.getSession().setAttribute("contextPath", contextPath);
        response.setContentType("text/html; charset=UTF-8");
        List<CartItem> cartItems = (List<CartItem>) request.getSession().getAttribute("cart");
        if (cartItems == null) {
            response.sendRedirect("CartDetailServlet");
            return;
        }
        
        PrintWriter out = response.getWriter();
        ProductDAO productDAO = new ProductDAO();
        try {
            List<CartItemDTO> cartItemsDTO = new ArrayList<>();
            for (CartItem item : cartItems) {
                Product product = productDAO.getProductById(item.getProduct_id());
                ProductPrice productPrice = productDAO.getProductPriceById(item.getProductprice_id());
                ProductQuantity productQuantity = productDAO.getProductQuantityById(item.getProductquantity_id());
                CartItemDTO dto = new CartItemDTO(product, productPrice, productQuantity, item.getQuantity());
                
                cartItemsDTO.add(dto);
            }
            List<Size> listSize = productDAO.getAllSizes();
            List<Color> listColor = productDAO.getAllColors();
            request.setAttribute("listColor", listColor);
            request.setAttribute("listSize", listSize);
            request.setAttribute("cartItemsDTO", cartItemsDTO);
            
        } catch (SQLException ex) {
            Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String shippingFee = request.getParameter("shippingFee1");
        String total = request.getParameter("total");
        String totalPrice = request.getParameter("totalPrice1");
        
        User user = (User) request.getSession().getAttribute("user");
        String email = request.getParameter("email").trim();
        String name = request.getParameter("name").trim();
        String phone = request.getParameter("phone");
        String city = request.getParameter("city");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        String street = request.getParameter("street");
        String paymentMethod = request.getParameter("paymentMethod");
        
        String voucher = request.getParameter("voucher");
        //Demo get voucher_id by voucher
        int voucher_id = -1;
        
        String address = street + ", " + ward + ", " + district + ", " + city;
        Orders order = new Orders(email, Integer.parseInt(total), voucher_id, address, phone);
        
        if (paymentMethod.equals("cod")) {
            order.setPayment_method(paymentMethod);
            OrderDAO orderDAO = new OrderDAO();
            try {
                int order_id = orderDAO.insertOrder(order);
                order.setOrder_id(order_id);
                request.getSession().setAttribute("order", order);
                if (order_id > 0) {
                    for (CartItem item : cartItems) {
                        orderDAO.insertOrderDetail(order_id, item);
                    }
                    deleteAllItemInCookie(request, response);
                    response.sendRedirect("NgocHieu/OrderSuccessJSP.jsp");                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (paymentMethod.equals("vnpay")) {
            order.setPayment_method(paymentMethod);
            request.getSession().setAttribute("order", order);
            response.sendRedirect("NgocHieu/vnpay/vnpay_pay.jsp");
        }
    }
    
    public void deleteAllItemInCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().startsWith("cartItem_")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
