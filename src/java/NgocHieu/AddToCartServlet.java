/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package NgocHieu;

import DAL.ProductDAO;
import Model.CartItem;
import Model.ProductQuantity;
import NgocHieu.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ProductDAO productDAO = new ProductDAO();
            String productIdStr = request.getParameter("product_id");
            String productPriceIdStr = request.getParameter("productprice_id");
            String productQuantityIdStr = request.getParameter("productquantity_id");

            if (productIdStr == null || productPriceIdStr == null || productQuantityIdStr == null) {
                response.sendRedirect("error.jsp");
                return;
            }

            int productId = Integer.parseInt(productIdStr);
            int productPriceId = Integer.parseInt(productPriceIdStr);
            int productQuantityId = Integer.parseInt(productQuantityIdStr);
            ProductQuantity pp = productDAO.getProductQuantityById(productQuantityId);

            // Lấy giỏ hàng từ cookie
            List<CartItem> cartItems = new ArrayList<>();
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("cart".equals(cookie.getName())) {
                        try {
                            cartItems = AuthenticationService.decodeCartToken(cookie.getValue()); // ✅ Giải mã JWT từ cookie
                        } catch (JOSEException | ParseException e) {
                        }
                        break;
                    }
                }
            }

            // Thêm hoặc cập nhật sản phẩm vào giỏ hàng
            boolean found = false;
            for (CartItem item : cartItems) {
                if (item.getProduct_id() == productId && item.getProductprice_id() == productPriceId && item.getProductquantity_id() == productQuantityId) {
                    int newQuantity = item.getQuantity() + 1;
                    if (newQuantity > pp.getQuantity()) {
                        break;
                    }
                    item.setQuantity(newQuantity);
                    found = true;
                    break;
                }
            }

            if (!found) {
                cartItems.add(new CartItem(productId, productPriceId, productQuantityId, 1));
            }

            // Mã hóa giỏ hàng mới thành JWT
            String jwtCart = AuthenticationService.generateCartToken(cartItems);

            // Lưu JWT vào cookie
            Cookie cartCookie = new Cookie("cart", jwtCart);
            cartCookie.setMaxAge(60 * 60 * 24 * 30); // 30 ngày
            cartCookie.setHttpOnly(true); // Ngăn JavaScript đọc cookie
            cartCookie.setSecure(true); // Chỉ gửi qua HTTPS
            response.addCookie(cartCookie);

            int cartQuantity = cartItems.size();
            
            request.getSession().setAttribute("cartQuantity", cartQuantity);

            response.sendRedirect("CartDetailServlet");

        } catch (NumberFormatException | SQLException | JOSEException e) {
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
