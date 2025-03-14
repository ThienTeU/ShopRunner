package HieuPTM.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ChangePassword", urlPatterns = {"/ChangePassword"})
public class ChangePassword extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uid = request.getParameter("uid"); // Lấy uid từ URL
        if (uid == null || uid.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/LoginControl"); // Nếu chưa login thì quay về login
            return;
        }
        request.setAttribute("uid", uid); // Gửi uid sang JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/HieuPTM/ChangePassword.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uid = request.getParameter("uid"); // Lấy uid từ form

        // Nếu chưa login (không có uid), redirect về login
        if (uid == null || uid.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/LoginControl");
            return;
        }

        String oldPassword = request.getParameter("oldPassword");  // Mật khẩu cũ
        String newPassword = request.getParameter("newPassword");  // Mật khẩu mới
        String confPassword = request.getParameter("confPassword"); // Xác nhận mật khẩu mới

        RequestDispatcher dispatcher;

        // Kiểm tra các trường có trống không
        if (oldPassword == null || newPassword == null || confPassword == null
                || oldPassword.isEmpty() || newPassword.isEmpty() || confPassword.isEmpty()) {
            request.setAttribute("status", "emptyField"); // Báo lỗi nhập thiếu
            request.setAttribute("uid", uid); // Gửi lại uid
            dispatcher = request.getRequestDispatcher("/HieuPTM/ChangePassword.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu mới và xác nhận có khớp không
        if (!newPassword.equals(confPassword)) {
            request.setAttribute("status", "notMatch"); // Báo lỗi không khớp
            request.setAttribute("uid", uid);
            dispatcher = request.getRequestDispatcher("/HieuPTM/ChangePassword.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Kiểm tra độ mạnh của mật khẩu (ít nhất 6 ký tự và có ít nhất 1 chữ số)
        if (!isValidPassword(newPassword)) {
            request.setAttribute("status", "invalidNewPassword"); // Mật khẩu không đủ mạnh
            request.setAttribute("uid", uid);
            dispatcher = request.getRequestDispatcher("/HieuPTM/ChangePassword.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            // Kết nối CSDL
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:sqlserver://localhost:1433;databaseName=RunnerShop", "sa", "123");

            // Kiểm tra mật khẩu cũ
            PreparedStatement checkPst = con.prepareStatement(
                    "SELECT * FROM [User] WHERE user_name = ? AND password = ?");
            checkPst.setString(1, uid);
            checkPst.setString(2, oldPassword);
            ResultSet rs = checkPst.executeQuery();

            if (rs.next()) { // Nếu đúng mật khẩu cũ
                // Cập nhật mật khẩu mới
                PreparedStatement updatePst = con.prepareStatement(
                        "UPDATE [User] SET password = ? WHERE user_name = ?");
                updatePst.setString(1, newPassword);
                updatePst.setString(2, uid);

                int rowUpdated = updatePst.executeUpdate();
                if (rowUpdated > 0) {
                    request.setAttribute("status", "changeSuccess"); // Thành công
                } else {
                    request.setAttribute("status", "changeFailed"); // Không thành công
                }
            } else {
                request.setAttribute("status", "wrongOldPassword"); // Mật khẩu cũ sai
            }

            request.setAttribute("uid", uid); // Giữ lại uid khi reload form
            dispatcher = request.getRequestDispatcher("/HieuPTM/ChangePassword.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi: " + e.getMessage());
        }
    }

    // Hàm kiểm tra độ mạnh mật khẩu
    private boolean isValidPassword(String password) {
        String regex = "^(?=.*\\d).{6,}$"; // Ít nhất 6 ký tự, có ít nhất 1 chữ số
        return Pattern.matches(regex, password);
    }
}
