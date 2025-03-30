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
        String contactIdStr = request.getParameter("id");
        int contactId = Integer.parseInt(contactIdStr);

        ContactDAO contactDAO = new ContactDAO();
        Contact contact = contactDAO.getContactById(contactId);

        if (contact == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Liên hệ không tồn tại.");
            return;
        }

        request.setAttribute("contact", contact);

        request.getRequestDispatcher("AdminManage/contactDetail.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet hiển thị chi tiết liên hệ";
    }
}
