/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package NgocHieu;

import Model.CartItem;
import Model.CartItemDTO;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                if (cookie.getName().equals("cart")) {
                    try {
                        cartItems = AuthenticationService.decodeCartToken(cookie.getValue());
                    } catch (JOSEException | ParseException ex) {
                        Logger.getLogger(UpdateCart.class.getName()).log(Level.SEVERE, null, ex);
                    }
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

        try {
            // Generate jwt moi
            String newCartToken = AuthenticationService.generateCartToken(cartItems);
            Cookie cartCookie = new Cookie("cart", newCartToken);
                cartCookie.setMaxAge(60 * 60 * 24 * 30);
                cartCookie.setHttpOnly(true);
                cartCookie.setSecure(true);
                response.addCookie(cartCookie);
        } catch (JOSEException ex) {
            Logger.getLogger(UpdateCart.class.getName()).log(Level.SEVERE, null, ex);
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

