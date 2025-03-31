package HieuPTM;

import HieuPTM.DAO.UserDAO;
import HieuPTM.model.UserHieu;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.regex.Pattern;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@WebServlet(name = "ValidateOTP", urlPatterns = {"/ValidateOTP"})
public class ValidateOTP extends HttpServlet {

    private boolean validatePassword(String password) {
        String passwordRegex = "^(?=.*\\d).{6,}$";
        return Pattern.compile(passwordRegex).matcher(password).matches();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userOtp = request.getParameter("otp");
        HttpSession session = request.getSession(false);

        RequestDispatcher dispatcher;

        if (session == null || session.getAttribute("otp") == null) {
            request.setAttribute("message", "OTP đã hết hạn. Vui lòng yêu cầu lại!");
            dispatcher = request.getRequestDispatcher("/HieuPTM/EnterOTP.jsp");
        } else {
            String sessionOtp = (String) session.getAttribute("otp");
            Long otpTime = (Long) session.getAttribute("otpTime");
            long currentTime = System.currentTimeMillis();

            if (currentTime - otpTime > 60 * 60 * 1000) { // 60 phút
                //if (currentTime - otpTime > 1000) { // 1 giây

                session.invalidate();
                request.setAttribute("message", "Mã OTP đã hết hạn. Vui lòng thử lại!");
                dispatcher = request.getRequestDispatcher("/HieuPTM/EnterOTP.jsp");

            } else if (userOtp.equals(sessionOtp)) {
                if (session.getAttribute("userRegister") != null) {
                    UserHieu user = (UserHieu) session.getAttribute("userRegister");
                    UserDAO dao = new UserDAO();
                    String password = user.getPassword();
                    

                    if (!validatePassword(password)) {
                        request.setAttribute("error", "Mật khẩu phải có ít nhất 6 ký tự, gồm ít nhất 1 số.");
                        request.getRequestDispatcher("HieuPTM/Register.jsp").forward(request, response);
                        return;
                    }
                    PasswordEncoder passEncoder = new BCryptPasswordEncoder(10);
                    String passHashed = passEncoder.encode(password);
                    user.setPassword(passHashed);
                    dao.insert(user);
                    response.getWriter().print(user.getFullName() + "|" + user.getPassword());

                    session.removeAttribute("otp");
                    session.removeAttribute("otpTime");
                    session.removeAttribute("userRegister");

                    dispatcher = request.getRequestDispatcher("/HieuPTM/RegisterSuccess.jsp");

                } else if (session.getAttribute("email") != null) {

                    String email = (String) session.getAttribute("email");

                    request.setAttribute("email", email);

                    dispatcher = request.getRequestDispatcher("/HieuPTM/NewPassword.jsp");
                } else {
                    request.setAttribute("message", "Lỗi hệ thống, vui lòng thử lại!");
                    dispatcher = request.getRequestDispatcher("/HieuPTM/EnterOTP.jsp");
                }

            } else {

                request.setAttribute("message", "Mã OTP không chính xác, vui lòng thử lại!");
                dispatcher = request.getRequestDispatcher("/HieuPTM/EnterOTP.jsp");
            }
        }

        dispatcher.forward(request, response);
    }
}
