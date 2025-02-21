/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package NgocHieu;

import Model.CartItem;
import Model.CartItemDTO;
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
        Cookie[] cookies = request.getCookies();
        List<CartItem> cartItems = new ArrayList<>();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().startsWith("cartItem_")) {
                    String[] itemData = URLDecoder.decode(cookie.getValue(), "UTF-8").split(",");
                    CartItem cartItem = new CartItem();
                    cartItem.setProduct_id(Integer.parseInt(itemData[0]));
                    cartItem.setProductprice_id(Integer.parseInt(itemData[1]));
                    cartItem.setProductquantity_id(Integer.parseInt(itemData[2]));
                    cartItem.setQuantity(Integer.parseInt(itemData[3]));
                    cartItems.add(cartItem);
                }
            }
        }

        int product_id = Integer.parseInt(request.getParameter("product_id"));
        int productprice_id = Integer.parseInt(request.getParameter("productprice_id"));
        int productquantity_id = Integer.parseInt(request.getParameter("productquantity_id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        for (CartItem item : cartItems) {
            if (item.getProduct_id() == product_id && item.getProductprice_id() == productprice_id
                    && item.getProductquantity_id() == productquantity_id) {
                item.setQuantity(quantity);
            }
        }

        // Update cookies
        for (CartItem item : cartItems) {
            String cookieName = "cartItem_" + item.getProduct_id() + "_" + item.getProductprice_id() + "_"
                    + item.getProductquantity_id();
            String cookieValue = URLEncoder.encode(item.getProduct_id() + "," + item.getProductprice_id() + ","
                    + item.getProductquantity_id() + "," + item.getQuantity(), "UTF-8");
            Cookie cookie = new Cookie(cookieName, cookieValue);
            cookie.setMaxAge(60 * 60 * 24 * 30); // 30 days
            response.addCookie(cookie);
        }

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

