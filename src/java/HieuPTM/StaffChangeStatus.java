package HieuPTM;

import DAL.StaffDAOHieu;
import Model.StaffHieu;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "StaffChangeStatus", urlPatterns = {"/StaffChangeStatus"})
public class StaffChangeStatus extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StaffDAOHieu dao = new StaffDAOHieu();
        String id = request.getParameter("userId");
        int staffId = Integer.parseInt(id);
        StaffHieu staff = dao.getStaffById(staffId);
        boolean newStatus = !staff.isStatus();
        dao.updateStaffStatus(staffId, newStatus);
        response.sendRedirect("StaffManage");
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
        StaffDAOHieu dao = new StaffDAOHieu();
        int staffId = 5;
        StaffHieu customer = dao.getStaffById(staffId);
        System.out.println(customer);
        boolean newStatus = !customer.isStatus();
        System.out.println(newStatus);
        dao.updateStaffStatus(staffId, newStatus);
        System.out.println(customer);
    }
}
