/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.DucAnh;

import DAO.DucAnh.VoucherDAO;
import DTO.DucAnh.Voucher;
import NgocHieu.Email;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Acer
 */
@WebServlet(name = "SendVoucherEmail", urlPatterns = {"/SendVoucherEmail"})
public class SendVoucherEmail extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("sendVoucher.jsp");
    }

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    // Thiết lập UTF-8 để tránh lỗi tiếng Việt
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    String email = request.getParameter("email");
    VoucherDAO dao = new VoucherDAO();
    Voucher voucher = dao.getRandomVoucherWith10PercentDiscount();

    if (voucher == null) {
        // Nếu không có voucher, chuyển hướng đến trang lỗi
        request.setAttribute("errorMessage", "Hiện tại không có voucher hợp lệ. Vui lòng thử lại sau!");
        request.getRequestDispatcher("voucher_error.jsp").forward(request, response);
        return;
    }

    // Nếu có voucher, gửi email
    String subject = "🎁 Mã giảm giá 10% của bạn!";
    String body = "<html><body>"
            + "<h2>🎉 Chúc mừng! Bạn nhận được mã giảm giá 10%</h2>"
            + "<p><strong>Mã Voucher:</strong> <span style='color: blue;'>" + voucher.getVoucherCode() + "</span></p>"
            + "<p><strong>Tên:</strong> " + voucher.getName() + "</p>"
            + "<p><strong>Thời gian hiệu lực:</strong> " + voucher.getStart() + " - " + voucher.getEnd() + "</p>"
            + "<p>Hãy sử dụng ngay để nhận ưu đãi!</p>"
            + "</body></html>";

    Email emailService = new Email();
    emailService.SendEmail(email, subject, body);

    // Chuyển hướng đến trang thông báo thành công
    request.setAttribute("successMessage", "Mã voucher đã được gửi đến email của bạn!");
    request.getRequestDispatcher("voucher_success.jsp").forward(request, response);
}

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
