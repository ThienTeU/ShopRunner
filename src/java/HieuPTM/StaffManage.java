package HieuPTM;

import HieuPTM.DAO.UserDAO;
import Model.StaffHieu;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import DAL.StaffDAOHieu;

@WebServlet(name = "StaffManage", urlPatterns = {"/StaffManage"})
public class StaffManage extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StaffDAOHieu dao = new StaffDAOHieu();
        List<StaffHieu> c = dao.getAllStaff();
        int count = 0;
        for (StaffHieu staff : c) {
            count++;
        }
        int size = 10;
        int end = 0;
        if (count % size == 0) {
            end = count / size;
        } else {
            end = (count / size) + 1;
        }
        int index = 1;
        if (request.getParameter("index") != null && !request.getParameter("index").isEmpty()) {
            index = Integer.parseInt(request.getParameter("index"));
        }
        List<StaffHieu> staffs = dao.getAllStaffPage(index, size);
        request.setAttribute("end", end);
        request.setAttribute("staffs", staffs);

        request.getRequestDispatcher("/HieuPTM/StaffManage.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
        List<StaffHieu> c = dao.getAllStaff();
        int count = 0;
        for (StaffHieu staff : c) {
            count++;
        }
        int size = 10;
        int end = 0;
        if (count % size == 0) {
            end = count / size;
        } else {
            end = (count / size) + 1;
        }
        int index = 1;

        List<StaffHieu> staffs = dao.getAllStaffPage(index, size);
        for (StaffHieu staff : staffs) {
            System.out.println(staff.toString());
        }
    }
}
