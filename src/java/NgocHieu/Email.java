/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NgocHieu;


import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.internet.MimeUtility;

/**
 *
 * @author admin
 */
public class Email {
    private final String from = "duonghieu294@gmail.com";
    private final String password = "yfmjurvslontrlyf";
    private final Properties props = new Properties();

    public Email() {
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
    }
    
    public boolean SendEmail(String to, String subject, String body) {
        // Create Authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };
        
        // Create session
        Session session = Session.getInstance(props, auth);
        
        // Send email to "duonghieu294@gmail.com"
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.setFrom(from);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            // Encode the subject in Base64 to ensure proper display of Vietnamese characters
            String encodedSubject = MimeUtility.encodeText(subject, "UTF-8", "B");
            msg.setSubject(encodedSubject);
            // Set the sent date
            msg.setSentDate(new Date());
            // Set the email content
            msg.setContent(body, "text/html; charset=UTF-8");
            // Send email
            Transport.send(msg);
            return true;
        } catch (MessagingException | UnsupportedEncodingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static void main(String[] args) {               
        String to = "phamtranminhhieu2004@gmail.com";        
        Email email = new Email();
        String subject = "Xác Nhận Đặt Vé Thành Công";
        String name = "Hiếu";

        String emailBody = "<div style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>"
                + "<h2 style='color: #034EA2;'>Xin chào ," + name +"</h2>"
                + "<p>Chúc mừng bạn đã đặt vé thành công! Đây là thông tin chi tiết vé của bạn:</p>"
                + "<table style='width: 100%; border-collapse: collapse;'>"
                + "<tr>"
                + "<td style='padding: 8px; border: 1px solid #ddd;'>Mã vé:</td>"
                + "<td style='padding: 8px; border: 1px solid #ddd;'></td>"
                + "</tr>"
                + "<tr>"
                + "<td style='padding: 8px; border: 1px solid #ddd;'>Phim:</td>"
                + "<td style='padding: 8px; border: 1px solid #ddd;'></td>"
                + "</tr>"
                + "<tr>"
                + "<td style='padding: 8px; border: 1px solid #ddd;'>Suất chiếu:</td>"
                + "<td style='padding: 8px; border: 1px solid #ddd;'></td>"
                + "</tr>"
                + "<tr>"
                + "<td style='padding: 8px; border: 1px solid #ddd;'>Ghế đã chọn:</td>"
                + "<td style='padding: 8px; border: 1px solid #ddd;'></td>"
                + "</tr>"
                + "<tr>"
                + "<td style='padding: 8px; border: 1px solid #ddd;'>Phương thức thanh toán:</td>"
                + "<td style='padding: 8px; border: 1px solid #ddd;'></td>"
                + "</tr>"
                + "<tr>"
                + "<td style='padding: 8px; border: 1px solid #ddd;'>Tổng tiền:</td>"
                + "<td style='padding: 8px; border: 1px solid #ddd;'> VND</td>"
                + "</tr>"
                + "</table>"
                + "<p style='margin-top: 16px;'>Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!</p>"
                + "<p>Trân trọng,<br>Đội ngũ hỗ trợ khách hàng</p>"
                + "</div>";

        // Gửi email
        email.SendEmail(to, subject, emailBody);
        
        
    }
}
