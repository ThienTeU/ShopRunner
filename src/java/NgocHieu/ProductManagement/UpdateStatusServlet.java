package NgocHieu.ProductManagement;

import DAL.ManageProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "UpdateStatusServlet", urlPatterns = {"/UpdateStatusServlet"})
public class UpdateStatusServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String id = request.getParameter("product_id");
            String status = request.getParameter("status");
            if (id == null || status == null) {
                response.getWriter().print("Wrong input");
                response.sendRedirect("ProductDashboard");
                return;
            }
            int _status = status.equals("false") ? 1 : 0;
            int product_id = Integer.parseInt(id);
            response.getWriter().print(status + "/n" + _status);
            ManageProductDAO manageDAO = new ManageProductDAO();
            if (manageDAO.updateProductStatus(_status,product_id) > 0) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("<script>alert('Cập nhật trạng thái sản phẩm thành công'); window.location='ProductDashboard';</script>");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UpdateStatusServlet.class.getName()).log(Level.SEVERE, null, ex);
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
