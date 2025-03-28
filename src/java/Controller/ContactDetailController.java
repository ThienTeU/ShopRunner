package Controller;

import DAL.ContactDAO;
import Model.Contact;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ContactDetailController", urlPatterns = {"/contactDetail"})
public class ContactDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy ID từ tham số request
        String contactIdStr = request.getParameter("id");
        int contactId = Integer.parseInt(contactIdStr);

        // Lấy thông tin liên hệ từ DAO
        ContactDAO contactDAO = new ContactDAO();
        Contact contact = contactDAO.getContactById(contactId);

        // Kiểm tra nếu contact không tồn tại
        if (contact == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Liên hệ không tồn tại.");
            return;
        }

        // Đưa thông tin liên hệ vào request
        request.setAttribute("contact", contact);

        // Chuyển hướng tới trang chi tiết liên hệ (JSP)
        request.getRequestDispatcher("AdminManage/contactDetail.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet hiển thị chi tiết liên hệ";
    }
}
