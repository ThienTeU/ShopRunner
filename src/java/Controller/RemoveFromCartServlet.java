/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.CartItem;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author admin
 */
@WebServlet(name = "RemoveFromCartServlet", urlPatterns = {"/RemoveFromCartServlet"})
public class RemoveFromCartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String product_id = request.getParameter("product_id");
            String productprice_id = request.getParameter("productprice_id");
            String productquantity_id = request.getParameter("productquantity_id");

            if (product_id == null || productprice_id == null || productquantity_id == null) {
                response.sendRedirect("error.jsp");
                return;
            }

            int productId = Integer.parseInt(product_id);
            int productPriceId = Integer.parseInt(productprice_id);
            int productQuantityId = Integer.parseInt(productquantity_id);

            List<CartItem> cartItems = (List<CartItem>) request.getSession().getAttribute("cart");
            if (cartItems != null) {
                // Sử dụng Iterator để tránh lỗi ConcurrentModificationException
                Iterator<CartItem> iterator = cartItems.iterator();
                while (iterator.hasNext()) {
                    CartItem item = iterator.next();
                    if (item.getProduct_id() == productId
                            && item.getProductprice_id() == productPriceId
                            && item.getProductquantity_id() == productQuantityId) {
                        iterator.remove(); // Xóa phần tử an toàn
                        break;
                    }
                }
                request.getSession().setAttribute("cart", cartItems);
            }

            response.sendRedirect("CartDetailServlet");
        } catch (NumberFormatException e) {
            response.sendRedirect("error.jsp"); // Chuyển hướng khi lỗi chuyển đổi số
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
