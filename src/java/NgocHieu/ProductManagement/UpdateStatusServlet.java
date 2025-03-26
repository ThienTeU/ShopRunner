package NgocHieu.ProductManagement;

import DAL.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateStatusServlet", urlPatterns = {"/UpdateStatusServlet"})
public class UpdateStatusServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String product_id = request.getParameter("product_id");
            String status = request.getParameter("status");
            
            ProductDAO dao = new ProductDAO();
            boolean result = dao.updateStatus(product_id, Boolean.parseBoolean(status));
            
            if (result) {
                AjaxResponse.send(response, true, "Cập nhật trạng thái thành công!");
            } else {
                AjaxResponse.send(response, false, "Không thể cập nhật trạng thái!");
            }
        } catch (Exception e) {
            AjaxResponse.send(response, false, "Lỗi: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
