package Controller;

import DAL.ProductDAO;
import Model.Category;
import Model.Color;
import Model.Size;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "AdminHomeServlet", urlPatterns = {"/admin"})
public class AdminHomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ProductDAO dao = new ProductDAO();
            
            List<Category> listCategory = dao.getAllCategories();
            List<Color> listColor = dao.getAllColors();
            List<Size> listSize = dao.getAllSizes();
            request.getSession().setAttribute("listColor", listColor);
            request.getSession().setAttribute("listSize", listSize);
            request.setAttribute("listCategory", listCategory);
            response.sendRedirect("AdminManage/adminHome.jsp");
        } catch (SQLException ex) {
            Logger.getLogger(AdminHomeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Admin Home Servlet";
    }
}
