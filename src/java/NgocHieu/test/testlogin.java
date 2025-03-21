/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package NgocHieu.test;

import DAL.UserDAO;
import Model.User;
import NgocHieu.service.AuthenticationService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
@WebServlet(name = "testlogin", urlPatterns = {"/testlogin"})
public class testlogin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            User rawUser = new User("admin", "12345678");
            UserDAO dao = new UserDAO();
            AuthenticationService auth = new AuthenticationService();
            String token = auth.loginAuthentication(rawUser);
            response.getWriter().println("Token: " + token);
            if(token!=null){
                User user = dao.getUserByUsername(rawUser.getUsername());
                response.getWriter().println("Role: " + user.getRoleById());
                request.getSession().setAttribute("token", token);
            }
        } catch (SQLException ex) {
            Logger.getLogger(testlogin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
