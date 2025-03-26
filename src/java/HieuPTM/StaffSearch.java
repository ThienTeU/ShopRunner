/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package HieuPTM;

import DAL.StaffDAOHieu;
import Model.StaffHieu;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(name="StaffSearch", urlPatterns={"/StaffSearch"})
public class StaffSearch extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String strStatus = request.getParameter("status");
        Boolean status = null;
                if (strStatus.isEmpty()) {
            status = null;
        } else {
            status = "1".equals(request.getParameter("status"));
        }
        if (userName.isEmpty()) {
            userName = null;
        }
        if (email.isEmpty()) {
            email = null;
        }
        if (phone.isEmpty()) {
            phone = null;
        }
        
        StaffDAOHieu dao = new StaffDAOHieu();
        List<StaffHieu> staff = dao.searchStaff(userName, email, phone, status);
        int count = 0;
        for (StaffHieu sta : staff){
            count++;
        }
        int size = 10;
        int end = 0;
        if (count % size == 0){
            end = count / size;
        } else {
            end = (count / size) + 1;
        }
        int index = 1;
        if (request.getParameter("index") != null && !request.getParameter("index").isEmpty()) {
            index = Integer.parseInt(request.getParameter("index"));
        }
        int offset = (index - 1) * size;
        List<StaffHieu> staffs = dao.searchStaffPage(userName, email, phone, status, offset, size);
        request.setAttribute("check", "search");
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
}
