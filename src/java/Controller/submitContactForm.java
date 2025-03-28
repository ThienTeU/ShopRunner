package Controller;

import DAL.ContactDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Model.Contact;

/**
 * Servlet xử lý form liên hệ
 * @author Admin
 */
@WebServlet(name="submitContactForm", urlPatterns={"/submitContactForm"})
public class submitContactForm extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Lấy dữ liệu từ form
        String fullName = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String content = request.getParameter("content");

        // Kiểm tra dữ liệu đầu vào
        if (fullName == null || phone == null || email == null || 
            fullName.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            // Chuyển hướng đến trang lỗi nếu dữ liệu không hợp lệ
            response.sendRedirect("error.jsp");
            return;
        }

        // Tạo đối tượng Contact
        Contact contact = new Contact();
        contact.setFullName(fullName);
        contact.setPhone(phone);
        contact.setEmail(email);
        contact.setCity("Không xác định"); // Giá trị mặc định cho city
        contact.setContent(content);

        // Gọi DAO để lưu thông tin liên hệ
        ContactDAO contactDAO = new ContactDAO();
        boolean isSaved = contactDAO.saveContact(contact);

        // Xử lý kết quả
        if (isSaved) {
            // Chuyển hướng đến trang thành công
            response.sendRedirect("success.jsp");
        } else {
            // Chuyển hướng đến trang lỗi
            response.sendRedirect("error.jsp");
        }
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet xử lý form liên hệ";
    }
}
