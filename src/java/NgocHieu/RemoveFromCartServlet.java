package NgocHieu;

import Model.CartItem;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

            // them du lieu cho cart tu cookie
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

            // dung iterator tranh loi 
            Iterator<CartItem> iterator = cartItems.iterator();
            while (iterator.hasNext()) {
                CartItem item = iterator.next();
                if (item.getProduct_id() == productId
                        && item.getProductprice_id() == productPriceId
                        && item.getProductquantity_id() == productQuantityId) {
                    iterator.remove();
                    // xoa cookie co ten tuong ung bang cach set value ve rong
                    String cookieName = "cartItem_" + item.getProduct_id() + "_" + item.getProductprice_id() + "_" + item.getProductquantity_id();
                    Cookie cookie = new Cookie(cookieName, "");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }

            response.sendRedirect("CartDetailServlet");
        } catch (NumberFormatException e) {
            response.sendRedirect("error.jsp"); // Redirect when number format error
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
