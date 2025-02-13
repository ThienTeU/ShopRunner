/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.CartItem;
import Model.CartItemDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
@WebServlet(name = "UpdateCart", urlPatterns = {"/UpdateCart"})
public class UpdateCart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cart");

        int product_id = Integer.parseInt(request.getParameter("product_id"));
        int productprice_id = Integer.parseInt(request.getParameter("productprice_id"));
        int productquantity_id = Integer.parseInt(request.getParameter("productquantity_id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        for(CartItem item : cartItems){
            if(item.getProduct_id()==product_id && item.getProductprice_id() == productprice_id && item.getProductquantity_id() == productquantity_id){
                item.setQuantity(quantity);
            }
        }
        session.setAttribute("cart", cartItems);
        response.sendRedirect("CartDetailServlet");
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
