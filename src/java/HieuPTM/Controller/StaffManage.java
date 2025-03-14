package HieuPTM.controller;

import HieuPTM.DAO.UserDAO;
import HieuPTM.model.UserHieu;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "StaffManage", urlPatterns = {"/StaffManage"})
public class StaffManage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO dao = new UserDAO();

        String action = request.getParameter("action");
        String targetUser = request.getParameter("target");

        if ("promote".equals(action) && targetUser != null) {
            dao.becomeStaff(targetUser);
        } else if ("delete".equals(action) && targetUser != null) {
            dao.deleteUser(targetUser);
        }

ArrayList<UserHieu> list = dao.getAllUsersNoAdmin();
System.out.println("Số lượng user lấy được: " + list.size());
for (UserHieu u : list) {
    System.out.println("User: " + u.getUserName()); // hoặc u.getUsername()
}
request.setAttribute("userlist", list);

        request.getRequestDispatcher("HieuPTM/ListStaff.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}