package HieuPTM.Controller;

import HieuPTM.DBContext.DBContext;
import HieuPTM.model.UserHieu;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;

@WebServlet(name = "ChangePassword", urlPatterns = {"/ChangePassword"})
public class ChangePassword extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserHieu user = (UserHieu) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/LoginControl");
            return;
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/HieuPTM/ChangePassword.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserHieu user = (UserHieu) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/LoginControl");
            return;
        }
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confPassword = request.getParameter("confPassword");

        RequestDispatcher dispatcher;
        if (oldPassword == null || newPassword == null || confPassword == null
                || oldPassword.isEmpty() || newPassword.isEmpty() || confPassword.isEmpty()) {
            request.setAttribute("status", "emptyField");
            dispatcher = request.getRequestDispatcher("/HieuPTM/ChangePassword.jsp");
            dispatcher.forward(request, response);
            return;
        }

        if (!newPassword.equals(confPassword)) {
            request.setAttribute("status", "notMatch");
            dispatcher = request.getRequestDispatcher("/HieuPTM/ChangePassword.jsp");
            dispatcher.forward(request, response);
            return;
        }

        if (!isValidPassword(newPassword)) {
            request.setAttribute("status", "invalidNewPassword");
            dispatcher = request.getRequestDispatcher("/HieuPTM/ChangePassword.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try (Connection con = new DBContext().connection) {
            String uid = user.getUserName();
            String sqlCheck = "SELECT password FROM [User] WHERE user_name = ?";
            try (PreparedStatement checkPst = con.prepareStatement(sqlCheck)) {
                checkPst.setString(1, uid);
                try (ResultSet rs = checkPst.executeQuery()) {
                    if (rs.next()) {
                        String storedPassword = rs.getString("password");

                        if (!encoder.matches(oldPassword, storedPassword)) {
                            request.setAttribute("status", "wrongOldPassword");
                            dispatcher = request.getRequestDispatcher("/HieuPTM/ChangePassword.jsp");
                            dispatcher.forward(request, response);
                            return;
                        }
                    } else {
                        request.setAttribute("status", "userNotFound");
                        dispatcher = request.getRequestDispatcher("/HieuPTM/ChangePassword.jsp");
                        dispatcher.forward(request, response);
                        return;
                    }
                }
            }

            PasswordEncoder passEncoder = new BCryptPasswordEncoder(10);
            String hashedNewPassword = passEncoder.encode(newPassword);

            String sqlUpdate = "UPDATE [User] SET password = ? WHERE user_name = ?";
            try (PreparedStatement updatePst = con.prepareStatement(sqlUpdate)) {
                updatePst.setString(1, hashedNewPassword);
                updatePst.setString(2, uid);
                int rowUpdated = updatePst.executeUpdate();
                request.setAttribute("status", rowUpdated > 0 ? "changeSuccess" : "changeFailed");
            }
        } catch (Exception e) {
            request.setAttribute("status", "serverError");
        }

        dispatcher = request.getRequestDispatcher("/HieuPTM/ChangePassword.jsp");
        dispatcher.forward(request, response);
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*\\d).{6,}$";
        return Pattern.matches(regex, password);
    }
}
