package Controller;

import DAL.ContactDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteContactController", urlPatterns = {"/deleteContact"})
public class DeleteContactController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy ID từ tham số request
        String contactIdStr = request.getParameter("id");
        int contactId = Integer.parseInt(contactIdStr);

        // Xóa liên hệ thông qua DAO
        ContactDAO contactDAO = new ContactDAO();
        boolean isDeleted = contactDAO.deleteContact(contactId);

        // Kiểm tra kết quả xóa
        if (isDeleted) {
            // Xóa thành công, chuyển hướng về danh sách liên hệ
            response.sendRedirect("contactList");
        } else {
            // Xóa thất bại, hiển thị thông báo lỗi
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không thể xóa liên hệ.");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet xử lý xóa liên hệ";
    }
}
