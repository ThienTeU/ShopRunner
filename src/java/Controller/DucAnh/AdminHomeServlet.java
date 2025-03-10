package Controller.DucAnh;

import DAO.DucAnh.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AdminHomeServlet", urlPatterns = {"/admin"})
public class AdminHomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ProductDAO pdao = new ProductDAO();

        // Get statistics
        int newProduct = pdao.getTotalNewProduct();
        int totalProductA = pdao.getTotalProduct();
        
        // Set attributes for the JSP page
        request.setAttribute("newProduct", newProduct);
        request.setAttribute("totalProduct", totalProductA);
        
        request.getRequestDispatcher("adminHome.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}