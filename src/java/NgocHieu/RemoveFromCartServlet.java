package NgocHieu;

import Model.CartItem;
import NgocHieu.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

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

            // Lay gio hang tu cookie
            Cookie[] cookies = request.getCookies();
            List<CartItem> cartItems = new ArrayList<>();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("cart".equals(cookie.getName())) {
                        try {
                            cartItems = AuthenticationService.decodeCartToken(cookie.getValue());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            // Xoa san pham khoi gio hang iterator
            Iterator<CartItem> iterator = cartItems.iterator();
            while (iterator.hasNext()) {
                CartItem item = iterator.next();
                if (item.getProduct_id() == productId
                        && item.getProductprice_id() == productPriceId
                        && item.getProductquantity_id() == productQuantityId) {
                    iterator.remove();
                    break;
                }
            }

            // Xoa cookie neu gio hang trong
            if (cartItems.isEmpty()) {
                Cookie emptyCartCookie = new Cookie("cart", "");
                emptyCartCookie.setMaxAge(0);
                response.addCookie(emptyCartCookie);
            } else {
                // Ma hoa gio hang moi thanh jwt
                String newCartToken = AuthenticationService.generateCartToken(cartItems);
                Cookie cartCookie = new Cookie("cart", newCartToken);
                cartCookie.setMaxAge(60 * 60 * 24 * 30);//Set thoi gian 30 ngay cho cookie
                cartCookie.setHttpOnly(true);
                cartCookie.setSecure(true);
                response.addCookie(cartCookie);
            }

            response.sendRedirect("CartDetailServlet");

        } catch (NumberFormatException e) {
            response.sendRedirect("error.jsp");
        } catch (JOSEException ex) {
            Logger.getLogger(RemoveFromCartServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
