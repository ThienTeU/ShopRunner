package HieuPTM.controller;

import HieuPTM.DAO.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import HieuPTM.model.UserHieu;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name="LoginControl", urlPatterns={"/LoginControl"})
public class LoginControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/HieuPTM/Login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String re = request.getParameter("re");
        UserDAO ad = new UserDAO();
        UserHieu u = ad.check(username, password);
        request.setAttribute("user", username);
        Cookie cname = new Cookie("name", username);
        Cookie cpass = new Cookie("pass", password);
        Cookie cre = new Cookie("re", re);
        if (re != null) {
            cname.setMaxAge(60 * 60 * 24 * 1);
            cpass.setMaxAge(60 * 60 * 24 * 1);
            cre.setMaxAge(60 * 60 * 24 * 1);
        } else {
            cname.setMaxAge(0);
            cpass.setMaxAge(0);
            cre.setMaxAge(0);
        }
        response.addCookie(cname);
        response.addCookie(cpass);
        response.addCookie(cre);
        if (u == null) {
            request.setAttribute("mess", "Error");
            request.setAttribute("user", username);
            request.setAttribute("pass", password);
            request.getRequestDispatcher("/HieuPTM/Login.jsp").forward(request, response);
        } else {
            UserHieu user = ad.getUser(username);
            response.sendRedirect(request.getContextPath() + "/home?uid=" + user.getUserName());
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}