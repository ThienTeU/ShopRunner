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
        String contactIdStr = request.getParameter("id");
        int contactId = Integer.parseInt(contactIdStr);

        ContactDAO contactDAO = new ContactDAO();
        boolean isDeleted = contactDAO.deleteContact(contactId);

        if (isDeleted) {
            response.sendRedirect("contactList");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không thể xóa liên hệ.");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet xử lý xóa liên hệ";
    }
}
