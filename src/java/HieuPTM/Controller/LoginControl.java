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
                if ("re".equals(c.getName()) && "on".equals(c.getValue())) {
                    rememberMe = true;
                }
                if ("tokenC".equals(c.getName())) {
                    token = c.getValue();
                }
            }
        }
        if (token != null && rememberMe) {
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
                Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        request.getRequestDispatcher("/HieuPTM/Login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String re = request.getParameter("re");

        UserDAO ad = new UserDAO();
        UserHieu userDB = ad.getUser(username);

        if (userDB == null || userDB.getPassword() == null) {
            request.setAttribute("mess", "Sai tài khoản !");
            request.getRequestDispatcher("/HieuPTM/Login.jsp").forward(request, response);
            return;
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if (!passwordEncoder.matches(password, userDB.getPassword())) {
            request.setAttribute("mess", "Sai mật khẩu!");
            request.getRequestDispatcher("/HieuPTM/Login.jsp").forward(request, response);
            return;
        }

        AuthenticationService auth = new AuthenticationService();
        String token = null;
        try {
            token = auth.loginAuthentication(new User(userDB.getUserName(), password));
        } catch (SQLException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        }

        Cookie tokenC = new Cookie("tokenC", token);
        Cookie cre = new Cookie("re", re != null ? "on" : "off");

        if (re != null) {
            tokenC.setMaxAge(60 * 60 * 24);
            cre.setMaxAge(60 * 60 * 24);
        } else {
            cre.setMaxAge(0);
        }

        response.addCookie(tokenC);
        response.addCookie(cre);

        try {
            String email = AuthenticationService.getEmailFromToken(token);
            UserHieu user = ad.getUserByEmail(email);
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("user_id", user.getUserID());
            response.sendRedirect(request.getContextPath() + "/home");
        } catch (ParseException | JOSEException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Login Controller";
    }
}