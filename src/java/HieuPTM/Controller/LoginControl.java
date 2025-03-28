package HieuPTM.Controller;

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
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.annotation.WebServlet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@WebServlet(name = "LoginControl", urlPatterns = {"/LoginControl"})
public class LoginControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        boolean rememberMe = false;
        String token = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("re") && c.getValue().equals("on")) {
                    rememberMe = true;
                }
                if (c.getName().equals("tokenC")) {
                    token = c.getValue();
                }
            }
        } else {
            response.sendRedirect("/RunnerShop/home");
            return;
        }
        response.getWriter().println(token);
        response.getWriter().print(rememberMe);
        if (rememberMe) {
            try {
                String email = AuthenticationService.getEmailFromToken(token);
                if (email != null) {
                    UserDAO dao = new UserDAO();
                    UserHieu user = dao.getUserByEmail(email);
                    request.getSession().setAttribute("user", user);
                    response.sendRedirect("/RunnerShop/home");
                    return;
                }
            } catch (ParseException | JOSEException ex) {
            }
        }
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
        UserHieu u = new UserHieu(); // Kiểm tra tài khoản

        UserHieu userDB = ad.getUser(username);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String token = null;
        if (passwordEncoder.matches(password, userDB.getPassword())) {
            u = userDB;
            AuthenticationService auth = new AuthenticationService();
            User userRaw = new User(userDB.getUserName(), password);
            try {
                token = auth.loginAuthentication(userRaw);
            } catch (SQLException ex) {
                Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.getSession().setAttribute("token", token);
        }
        // Xử lý Cookie Remember Me
        Cookie tokenC = new Cookie("tokenC", token);
        Cookie cre = new Cookie("re", re != null ? "on" : "off");

        if (re != null) { // Nếu chọn ghi nhớ
            tokenC.setMaxAge(60 * 60 * 24 * 1); // 1 ngày
            cre.setMaxAge(60 * 60 * 24 * 1);
        } else { // Nếu không chọn
            cre.setMaxAge(0);
        }

        // Thêm cookie vào response
        response.addCookie(tokenC);
        response.addCookie(cre);

        // Xử lý đăng nhập
        if (u.getUserName() == null) { // Nếu tài khoản không đúng
            request.setAttribute("mess", "Sai tài khoản hoặc mật khẩu!");
            request.setAttribute("user", username); // Giữ lại tên tài khoản khi load lại form
            request.setAttribute("pass", password);
            request.getRequestDispatcher("/HieuPTM/Login.jsp").forward(request, response);
        } else {
            // Nếu đăng nhập thành công, redirect về Home và truyền uid qua URL
            String email = "";
            try {
                email = AuthenticationService.getEmailFromToken(token);
            } catch (ParseException | JOSEException ex) {
                Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            UserDAO dao = new UserDAO();
            UserHieu user = dao.getUserByEmail(email);
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("user_id",user.getUserID());
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }

    @Override
    public String getServletInfo() {
        return "Login Controller";
    }
}
