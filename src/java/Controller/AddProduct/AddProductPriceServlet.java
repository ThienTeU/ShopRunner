package Controller.AddProduct;

import DAL.InsertProductDAO;
import DAL.ProductDAO;
import Model.Color;
import Model.Size;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
@WebServlet(name = "AddProductPriceServlet", urlPatterns = {"/AddProductPriceServlet"})
public class AddProductPriceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ProductDAO dao = new ProductDAO();
            List<Color> listColor = dao.getAllColors();
            request.setAttribute("listColor", listColor);
            request.getRequestDispatcher("AddProductPriceJSP.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AddProductPriceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            InsertProductDAO dao = new InsertProductDAO();
            ProductDAO dao2 = new ProductDAO();
            int product_id = Integer.parseInt(request.getParameter("product_id"));
            int color_id = Integer.parseInt(request.getParameter("color_id"));
            int price = Integer.parseInt(request.getParameter("price"));
            
            int productprice_id = dao.addProductPrice(product_id, color_id, price);
            List<Size> listSize = dao2.getAllSizes();
            
            request.setAttribute("listSize", listSize);
            request.setAttribute("productprice_id", productprice_id);
            request.getRequestDispatcher("AddProductQuantityJSP.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AddProductPriceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
