package HieuPTM.controller;

import java.util.Properties;
import java.util.Random;
import java.util.Date;
import javax.mail.*;
import javax.mail.internet.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "ForgotPassword", urlPatterns = {"/ForgotPassword"})
public class ForgotPassword extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/HieuPTM/ForgotPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        RequestDispatcher dispatcher;

        if (email != null && !email.trim().isEmpty()) {
            Random rand = new Random();
            String otpValue = String.format("%06d", rand.nextInt(999999));

            // Email config
            String fromEmail = "hieuphamtran04@gmail.com";
            String password = "uqrr rrhy mvxz hbzw";

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            Session sessionMail = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

            try {
                MimeMessage message = new MimeMessage(sessionMail);
                message.setFrom(new InternetAddress(fromEmail));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                message.setSubject("Runner Gear Shop - Đặt Lại Mật Khẩu Tài Khoản", "UTF-8");

                String htmlContent = "<div style='font-family: Arial, sans-serif; color: black;'>"
                        + "<p>Gần đây, bạn đã yêu cầu đặt lại mật khẩu cho tài khoản.</p>"
                        + "<ul><li>Nếu không phải bạn, bỏ qua email này.</li>"
                        + "<li>Sử dụng mã OTP sau để hoàn tất quá trình đặt lại mật khẩu.</li></ul>"
                        + "<h3 style='color: red;'>Mã OTP: <b>" + otpValue + "</b></h3>"
                        + "<p style='color: gray;'>Mã OTP có hiệu lực trong 60 phút.</p>"
                        + "</div>";

                message.setContent(htmlContent, "text/html; charset=UTF-8");
                Transport.send(message);

                HttpSession session = request.getSession();
                session.setAttribute("otp", otpValue);
                session.setAttribute("email", email);
                session.setAttribute("otpTime", new Date().getTime()); // Thời gian gửi OTP

                request.setAttribute("message", "Mã OTP đã được gửi tới email của bạn!");
                dispatcher = request.getRequestDispatcher("/HieuPTM/EnterOTP.jsp");

            } catch (MessagingException e) {
                e.printStackTrace();
                request.setAttribute("message", "Lỗi gửi email: " + e.getMessage());
                dispatcher = request.getRequestDispatcher("/HieuPTM/ForgotPassword.jsp");
            }

        } else {
            request.setAttribute("message", "Vui lòng nhập email hợp lệ!");
            dispatcher = request.getRequestDispatcher("/HieuPTM/ForgotPassword.jsp");
        }

        dispatcher.forward(request, response);
    }
}