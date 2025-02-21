package NgocHieu;

import DAL.ProductDAO;
import Model.CartItem;
import Model.CartItemDTO;
import Model.Color;
import Model.Product;
import Model.ProductPrice;
import Model.ProductQuantity;
import Model.Size;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cart");
            if (cartItems == null) {
                cartItems = new ArrayList<>();
            }

            List<CartItemDTO> cartItemsDTO = new ArrayList<>();
            ProductDAO productDAO = new ProductDAO();
            List<Size> listSize = productDAO.getAllSizes();
            List<Color> listColor = productDAO.getAllColors();

            double total = 0;
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
            request.setAttribute("cartItemsDTO", cartItemsDTO);

            request.getRequestDispatcher("NgocHieu/CheckOutJSP.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        List<CartItem> cartItems = (List<CartItem>) request.getSession().getAttribute("cart");
        if (cartItems == null) {
            response.sendRedirect("CartDetailServlet");
            return;
        }
        
        PrintWriter out = response.getWriter();
        ProductDAO productDAO = new ProductDAO();
        try {
            List<CartItemDTO> cartItemsDTO = new ArrayList<>();
            for (CartItem item : cartItems) {
                Product product = productDAO.getProductById(item.getProduct_id());
                ProductPrice productPrice = productDAO.getProductPriceById(item.getProductprice_id());
                ProductQuantity productQuantity = productDAO.getProductQuantityById(item.getProductquantity_id());
                CartItemDTO dto = new CartItemDTO(product, productPrice, productQuantity, item.getQuantity());

                cartItemsDTO.add(dto);
            }
            List<Size> listSize = productDAO.getAllSizes();
            List<Color> listColor = productDAO.getAllColors();
            request.setAttribute("listColor", listColor);
            request.setAttribute("listSize", listSize);
            request.setAttribute("cartItemsDTO", cartItemsDTO);

        } catch (SQLException ex) {
            Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        String shippingFee = request.getParameter("shippingFee1");
        String total = request.getParameter("total");
        String totalPrice = request.getParameter("totalPrice1");

        User user = (User) request.getSession().getAttribute("user");
        String email = request.getParameter("email").trim();
        String name = request.getParameter("name").trim();
        String phone = request.getParameter("phone");
        String city = request.getParameter("city");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        String street = request.getParameter("street");
        String note = request.getParameter("note");
        String paymentMethod = request.getParameter("paymentMethod");
        request.setAttribute("shippingFee", shippingFee);
        request.setAttribute("total", total);
        request.setAttribute("totalPrice", totalPrice);
        request.setAttribute("user", user);
        request.setAttribute("email", email);
        request.setAttribute("name", name);
        request.setAttribute("phone", phone);
        request.setAttribute("city", city);
        request.setAttribute("district", district);
        request.setAttribute("ward", ward);
        request.setAttribute("street", street);
        request.setAttribute("note", note);

        out.print(email + name + " | " + paymentMethod);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
