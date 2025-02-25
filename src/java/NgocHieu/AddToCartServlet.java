/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package NgocHieu;

import DAL.ProductDAO;
import Model.CartItem;
import Model.ProductQuantity;
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
            if (productId <= 0 || productPriceId <= 0 || productQuantityId <= 0) {
                response.sendRedirect("error.jsp");
                return;
            }

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

            CartItem cartItem = new CartItem();
            cartItem.setProduct_id(productId);
            cartItem.setProductprice_id(productPriceId);
            cartItem.setProductquantity_id(productQuantityId);
            cartItem.setQuantity(1);

            boolean found = false;
            for (CartItem item : cartItems) {
                if (item.getProduct_id() == cartItem.getProduct_id() && item.getProductprice_id() == cartItem.getProductprice_id()
                        && item.getProductquantity_id() == cartItem.getProductquantity_id()) {
                    int newQuantity = item.getQuantity()+1;
                    if(newQuantity > pp.getQuantity()) break;
                    item.setQuantity(item.getQuantity() + 1);
                    found = true;
                    break;
                }
            }

            if (!found) {
                cartItems.add(cartItem);
            }

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

        } catch (NumberFormatException e) {
            response.sendRedirect("error.jsp");
        } catch (SQLException ex) {
            Logger.getLogger(AddToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

