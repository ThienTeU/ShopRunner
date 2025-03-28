package Controller;

import DAL.ContactDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Model.Contact;

/**
 * Servlet hiển thị danh sách tất cả các contact
 */
@WebServlet(name = "ContactListController", urlPatterns = {"/contactList"})
public class ContactListController extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy danh sách contact từ DAO
        ContactDAO contactDAO = new ContactDAO();
        List<Contact> contactList = contactDAO.getAllContacts();

        // Đưa danh sách contact vào request để hiển thị trên giao diện
        request.setAttribute("contactList", contactList);

        // Chuyển hướng tới trang danh sách contact (JSP)
        request.getRequestDispatcher("AdminManage/contactList.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet hiển thị danh sách tất cả các contact";
    }
}
