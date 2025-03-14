package HieuPTM.controller;

import HieuPTM.DAO.UserDAO;
import HieuPTM.model.UserHieu;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "ValidateOTP", urlPatterns = {"/ValidateOTP"})
public class ValidateOTP extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userOtp = request.getParameter("otp");
        HttpSession session = request.getSession(false); // Không tạo mới nếu hết hạn

        RequestDispatcher dispatcher;

        if (session == null || session.getAttribute("otp") == null) {
            // Session hết hạn hoặc không có OTP
            request.setAttribute("message", "OTP đã hết hạn. Vui lòng yêu cầu lại!");
            dispatcher = request.getRequestDispatcher("/HieuPTM/EnterOTP.jsp");
        } else {
            String sessionOtp = (String) session.getAttribute("otp");
            Long otpTime = (Long) session.getAttribute("otpTime");
            long currentTime = System.currentTimeMillis();

            // Kiểm tra thời gian hợp lệ (60 phút)
            if (currentTime - otpTime > 60 * 60 * 1000) { // 60 phút
                session.invalidate(); // Hết hạn OTP, xóa session
                request.setAttribute("message", "Mã OTP đã hết hạn. Vui lòng thử lại!");
                dispatcher = request.getRequestDispatcher("/HieuPTM/EnterOTP.jsp");

            } else if (userOtp.equals(sessionOtp)) {
                // ✅ OTP đúng
                // Kiểm tra đây là OTP cho Đăng ký hay Quên mật khẩu
                if (session.getAttribute("userRegister") != null) {
                    // Trường hợp Đăng ký
                    UserHieu user = (UserHieu) session.getAttribute("userRegister");
                    UserDAO dao = new UserDAO();

                    // Thêm vào database
                    dao.insert(user);
                    response.getWriter().print(user.getFullName() + "|" + user.getPassword());
                    // Xoá session
                    session.removeAttribute("otp");
                    session.removeAttribute("otpTime");
                    session.removeAttribute("userRegister");

                    // Chuyển sang trang thành công
                    //dispatcher = request.getRequestDispatcher("/HieuPTM/RegisterSuccess.jsp");

                } else if (session.getAttribute("email") != null) {
                    // Trường hợp Quên mật khẩu
                    String email = (String) session.getAttribute("email");

                    // Cho phép đổi mật khẩu mới
                    request.setAttribute("email", email);

                    // Chuyển tới trang nhập mật khẩu mới
                    dispatcher = request.getRequestDispatcher("/HieuPTM/NewPassword.jsp");
                } else {
                    // Không xác định được mục đích OTP
                    request.setAttribute("message", "Lỗi hệ thống, vui lòng thử lại!");
                    dispatcher = request.getRequestDispatcher("/HieuPTM/EnterOTP.jsp");
                }

            } else {
                // ❌ Sai OTP
                request.setAttribute("message", "Mã OTP không chính xác, vui lòng thử lại!");
                dispatcher = request.getRequestDispatcher("/HieuPTM/EnterOTP.jsp");
            }
        }

        //dispatcher.forward(request, response);
    }
}