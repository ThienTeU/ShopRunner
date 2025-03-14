package HieuPTM.controller;

import HieuPTM.DAO.UserDAO;
import HieuPTM.model.UserHieu;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@WebServlet(name = "RegisterControl", urlPatterns = {"/RegisterControl"})
public class RegisterControl extends HttpServlet {

    private boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    private boolean validatePhone(String phone) {
        String phoneRegex = "^0\\d{9}$";
        return Pattern.compile(phoneRegex).matcher(phone).matches();
    }

    private boolean validatePassword(String password) {
        String passwordRegex = "^(?=.*\\d).{6,}$";
        return Pattern.compile(passwordRegex).matcher(password).matches();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO udao = new UserDAO();

        String userName = request.getParameter("username");
        String fullName = request.getParameter("fullname");
        String password = request.getParameter("password");
        int roleID = 2;
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        int genderID = Integer.parseInt(request.getParameter("gender")); // Ép kiểu gender sang int

        // Lưu lại dữ liệu người dùng nhập để hiển thị lại khi lỗi
        request.setAttribute("username", userName);
        request.setAttribute("fullname", fullName);
        request.setAttribute("email", email);
        request.setAttribute("phone", phone);
        request.setAttribute("gender", genderID); // Lưu gender để hiện lại nếu lỗi
        PasswordEncoder passEncoder = new BCryptPasswordEncoder(10);
        String passHashed = passEncoder.encode(password);
        // Kiểm tra dữ liệu
        if (!validateEmail(email)) {
            request.setAttribute("error", "Định dạng email không hợp lệ!");
            request.getRequestDispatcher("HieuPTM/Register.jsp").forward(request, response);
            return;
        } else if (!validatePhone(phone)) {
            request.setAttribute("error", "Số điện thoại không hợp lệ!");
            request.getRequestDispatcher("HieuPTM/Register.jsp").forward(request, response);
            return;
        } else if (!validatePassword(password)) {
            request.setAttribute("error", "Mật khẩu phải có ít nhất 6 ký tự, gồm ít nhất 1 số.");
            request.getRequestDispatcher("HieuPTM/Register.jsp").forward(request, response);
            return;
        } else if (udao.checkUserNameDuplicate(userName)) {
            request.setAttribute("error", "Tên đăng nhập đã tồn tại!");
            request.getRequestDispatcher("HieuPTM/Register.jsp").forward(request, response);
            return;
        } else if (udao.checkEmailDuplicate(email)) {
            request.setAttribute("error", "Địa chỉ email đã tồn tại!");
            request.getRequestDispatcher("HieuPTM/Register.jsp").forward(request, response);
            return;
        }

        // Sinh OTP
        Random rand = new Random();
        String otpvalue = String.format("%06d", rand.nextInt(999999));
        System.out.println("OTP cho đăng ký: " + otpvalue);

        // Gửi email chứa OTP
        boolean sendMailSuccess = sendOTPEmail(email, otpvalue);

        if (!sendMailSuccess) {
            request.setAttribute("error", "Không thể gửi email xác nhận. Vui lòng thử lại.");
            request.getRequestDispatcher("HieuPTM/Register.jsp").forward(request, response);
            return;
        }

        // Lưu thông tin vào session để xác minh OTP
        HttpSession session = request.getSession();
        session.setAttribute("otp", otpvalue);
        session.setAttribute("otpTime", System.currentTimeMillis()); // Lưu thời điểm tạo OTP
        session.setAttribute("userRegister", new UserHieu(userName, fullName, passHashed, phone, email, genderID, roleID));

        // Điều hướng sang trang nhập OTP
        request.setAttribute("message", "Xác thực mã OTP đã được gửi qua email để hoàn tất đăng ký.");
        request.getRequestDispatcher("HieuPTM/EnterOTP.jsp").forward(request, response);
    }

    // Hàm gửi OTP qua email
    private boolean sendOTPEmail(String email, String otpvalue) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("hieuphamtran04@gmail.com", "uqrr rrhy mvxz hbzw"); // Thông tin tạm
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("hieuphamtran04@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Xác thực đăng ký tài khoản Runner Gear Shop", "UTF-8");

            String htmlContent = "<div style='font-family: Arial; color: black;'>"
                    + "<p>Xin chào,</p>"
                    + "<p>Bạn đã đăng ký tài khoản thành công trên Runner Gear Shop.</p>"
                    + "<p>Chỉ còn một bước nữa để có thể đăng nhập. Vui lòng nhập mã OTP sau để xác nhận đăng ký:</p>"
                    + "<h2 style='color: red;'>" + otpvalue + "</h2>"
                    + "<p>Mã OTP có hiệu lực trong 60 phút.</p>"
                    + "</div>";

            message.setContent(htmlContent, "text/html; charset=UTF-8");
            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/HieuPTM/Register.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Register with email verification (OTP)";
    }
}