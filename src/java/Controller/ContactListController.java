package Controller;

import DAL.ContactDAO;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
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

        // Nhận tham số tìm kiếm và lọc từ request
        String keyword = request.getParameter("keyword");
        String statusFilter = request.getParameter("statusFilter");

        // Lọc danh sách liên hệ dựa trên tham số
        List<Contact> filteredContactList = contactList;

        if (keyword != null && !keyword.isEmpty()) {
            filteredContactList = filteredContactList.stream()
                .filter(contact -> contact.getFullName().toLowerCase().contains(keyword.toLowerCase())
                        || contact.getEmail().toLowerCase().contains(keyword.toLowerCase())
                        || contact.getContent().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
        }

        if (statusFilter != null && !statusFilter.isEmpty()) {
            boolean status = Boolean.parseBoolean(statusFilter);
            filteredContactList = filteredContactList.stream()
                .filter(contact -> contact.getStatus()== status)
                .collect(Collectors.toList());
        }

        // Đưa danh sách đã lọc vào request để hiển thị trên giao diện
        request.setAttribute("contactList", filteredContactList);

        // Chuyển hướng tới trang danh sách contact (JSP)
        request.getRequestDispatcher("AdminManage/contactList.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet hiển thị danh sách tất cả các contact";
    }
}
