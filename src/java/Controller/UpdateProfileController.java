package Controller;

import DAL.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "UpdateProfileController", urlPatterns = {"/update-profile"})
public class UpdateProfileController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy thông tin từ session
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");

        if (userId == null) {
            // Nếu không có user_id trong session, chuyển hướng đến trang đăng nhập
            response.sendRedirect(request.getContextPath() + "/LoginControl");
            return;
        }

        // Lấy dữ liệu từ form
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");

        // Kiểm tra dữ liệu hợp lệ
        if (fullName == null || fullName.isEmpty() ||
            email == null || email.isEmpty() ||
            phoneNumber == null || phoneNumber.isEmpty()) {
            // Nếu dữ liệu không hợp lệ, gửi thông báo lỗi
            request.setAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin.");
            request.getRequestDispatcher("Profile.jsp").forward(request, response);
            return;
        }

        // Gọi DAO để cập nhật thông tin người dùng
        UserDAO userDAO = new UserDAO();
        boolean isUpdated = userDAO.updateUserInfo(userId, email, phoneNumber, fullName);

        if (isUpdated) {
            // Nếu cập nhật thành công, chuyển hướng về trang profile với thông báo thành công
            session.setAttribute("successMessage", "Cập nhật thông tin thành công!");
            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            // Nếu cập nhật thất bại, gửi thông báo lỗi
            request.setAttribute("errorMessage", "Cập nhật thông tin thất bại. Vui lòng thử lại.");
            request.getRequestDispatcher("Profile.jsp").forward(request, response);
        }
    }
}
