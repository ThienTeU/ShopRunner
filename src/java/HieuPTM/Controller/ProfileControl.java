package HieuPTM.controller;

import HieuPTM.DAO.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import HieuPTM.model.UserHieu;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name="ProfileControl", urlPatterns={"/ProfileControl"})
public class ProfileControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // Your existing code...
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String uidString = request.getParameter("uid");
        UserDAO udao = new UserDAO();
        request.setAttribute("user", udao.getUser(uidString));
        request.getRequestDispatcher("HieuPTM/Profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Change password logic
        String username = request.getParameter("username");
        String newPassword = request.getParameter("newPassword");
        String message;

        if (isValidPassword(newPassword)) {
            UserDAO udao = new UserDAO();
            UserHieu user = udao.getUser(username);

            if (user != null) {
                user.setPassword(newPassword);
                udao.changePassword(user);
                message = "Password updated successfully!";
            } else {
                message = "User not found!";
            }
        } else {
            message = "Password does not meet the required criteria!";
        }

        request.setAttribute("message", message);
        request.setAttribute("user", new UserDAO().getUser(username)); // Ensure user details are reloaded
        request.getRequestDispatcher("HieuPTM/Profile.jsp").forward(request, response);
    }

    private boolean isValidPassword(String password) {
        // Add your password validation logic here
        // For example, ensure password is at least 8 characters long
        return password.length() >= 8;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}