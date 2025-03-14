/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package NgocHieu;

import DAL.ProductDAO;
import Model.CartItem;
import Model.CartItemDTO;
import Model.Color;
import Model.Product;
import Model.ProductPrice;
import Model.ProductQuantity;
import Model.Size;
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
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "CartDetailServlet", urlPatterns = {"/CartDetailServlet"})
public class CartDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PrintWriter out = response.getWriter();
            // them du lieu cho cart bang cookie
            Cookie[] cookies = request.getCookies();
            List<CartItem> cartItems = new ArrayList<>();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("cart")) {
                        try {
                            cartItems = AuthenticationService.decodeCartToken(cookie.getValue()); 
                        } catch (JOSEException | ParseException e) {
                        }
                        break;
                    }
                    out.println(cookie.getName() + "|" + cookie.getValue());
                }
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
            request.setAttribute("cart", cartItems);
            request.setAttribute("cartItemsDTO", cartItemsDTO);
            
            
            request.getRequestDispatcher("NgocHieu/CartDetailJSP.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CartDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
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

