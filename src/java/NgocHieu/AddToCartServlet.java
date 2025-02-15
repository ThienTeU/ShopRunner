/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package NgocHieu;

import Model.CartItem;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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
            // Lấy tham số từ request và kiểm tra null
            String productIdStr = request.getParameter("product_id");
            String productPriceIdStr = request.getParameter("productprice_id");
            String productQuantityIdStr = request.getParameter("productquantity_id");

            if (productIdStr == null || productPriceIdStr == null || productQuantityIdStr == null) {
                response.sendRedirect("error.jsp");
                return;
            }

            // Chuyển đổi sang int
            int productId = Integer.parseInt(productIdStr);
            int productPriceId = Integer.parseInt(productPriceIdStr);
            int productQuantityId = Integer.parseInt(productQuantityIdStr);

            // Kiểm tra giá trị hợp lệ
            if (productId <= 0 || productPriceId <= 0 || productQuantityId <= 0) {
                response.sendRedirect("error.jsp");
                return;
            }

            // Tạo đối tượng CartItem
            CartItem cartItem = new CartItem();
            cartItem.setProduct_id(productId);
            cartItem.setProductprice_id(productPriceId);
            cartItem.setProductquantity_id(productQuantityId);
            cartItem.setQuantity(1); // Mặc định số lượng = 1 khi thêm mới

            // Lấy giỏ hàng từ session (hoặc tạo mới)
            HttpSession session = request.getSession();
            List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cart");

            if (cartItems == null) {
                cartItems = new ArrayList<>();
            }

            // Kiểm tra sản phẩm đã tồn tại hay chưa
            boolean found = false;
            for (CartItem item : cartItems) {
                if (item.getProduct_id() == cartItem.getProduct_id()
                        && item.getProductprice_id() == cartItem.getProductprice_id()
                        && item.getProductquantity_id() == cartItem.getProductquantity_id()) {
                    item.setQuantity(item.getQuantity() + 1);
                    found = true;
                    break;
                }
            }

            // Nếu chưa tồn tại, thêm mới vào giỏ hàng
            if (!found) {
                cartItems.add(cartItem);
            }

            // Lưu lại vào session
            session.setAttribute("cart", cartItems);

            // Chuyển hướng về trang giỏ hàng
            response.sendRedirect("CartDetailServlet");

        } catch (NumberFormatException e) {
            response.sendRedirect("error.jsp"); // Xử lý lỗi nếu tham số không phải số
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
