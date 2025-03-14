package HieuPTM.controller;

import HieuPTM.DAO.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import HieuPTM.model.UserHieu;
import Model.User;
import NgocHieu.service.AuthenticationService;
import jakarta.servlet.annotation.WebServlet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@WebServlet(name = "LoginControl", urlPatterns = {"/LoginControl"})
public class LoginControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chuyển hướng đến trang Login.jsp khi vào bằng GET
        request.getRequestDispatcher("/HieuPTM/Login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy thông tin từ form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String re = request.getParameter("re"); // "Remember me"

        UserDAO ad = new UserDAO();
        UserHieu u=null; // Kiểm tra tài khoản
        
        UserHieu userDB = ad.getUser(username);
        
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if(passwordEncoder.matches(password, userDB.getPassword())){
            u = userDB;
        }
        // Xử lý Cookie Remember Me
        Cookie cname = new Cookie("name", username);
        Cookie cpass = new Cookie("pass", password);
        Cookie cre = new Cookie("re", re != null ? "on" : "off");

        if (re != null) { // Nếu chọn ghi nhớ
            cname.setMaxAge(60 * 60 * 24 * 1); // 1 ngày
            cpass.setMaxAge(60 * 60 * 24 * 1);
            cre.setMaxAge(60 * 60 * 24 * 1);
        } else { // Nếu không chọn
            cname.setMaxAge(0); // Xóa cookie
            cpass.setMaxAge(0);
            cre.setMaxAge(0);
        }

        // Thêm cookie vào response
        response.addCookie(cname);
        response.addCookie(cpass);
        response.addCookie(cre);

        // Xử lý đăng nhập
        if (u==null) { // Nếu tài khoản không đúng
            request.setAttribute("mess", "Sai tài khoản hoặc mật khẩu!");
            request.setAttribute("user", username); // Giữ lại tên tài khoản khi load lại form
            request.setAttribute("pass", password);
            request.getRequestDispatcher("/HieuPTM/Login.jsp").forward(request, response);
        } else {
            AuthenticationService auth = new AuthenticationService();
            User user = new User(userDB.getUserName(),password);
            try {
                String token = auth.loginAuthentication(user);
                request.getSession().setAttribute("token", token);
                response.getWriter().print(token);
            } catch (SQLException ex) {
                Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            // Nếu đăng nhập thành công, redirect về Home và truyền uid qua URL
            response.sendRedirect(request.getContextPath() + "/home?uid=" + u.getUserName());
        }
    }

    @Override
    public String getServletInfo() {
        return "Login Controller";
    }
}