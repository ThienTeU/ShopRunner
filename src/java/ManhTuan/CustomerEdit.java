/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ManhTuan;

import DAL.ProductDAOTuan;
import Model.User;
import Model.UserTuan;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author tuan
 */
@WebServlet(name = "CustomerEdit", urlPatterns = {"/customeredit"})
public class CustomerEdit extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAOTuan dao = new ProductDAOTuan();
        String id = request.getParameter("id");
        int customerId = Integer.parseInt(id);
        UserTuan customer = dao.getCustomerById(customerId);
        request.setAttribute("customer", customer);
        request.getRequestDispatcher("/ManhTuan/customeredit.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAOTuan dao = new ProductDAOTuan();
        String id = request.getParameter("userId");
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String status = request.getParameter("status");
        String gender = request.getParameter("gender");
        int customerId = Integer.parseInt(id);
        boolean customerStatus = "1".equals(status);
        int customerGender = Integer.parseInt(gender);
        UserTuan customer = new UserTuan(customerId, userName, email, phoneNumber, customerStatus, customerGender);
        dao.updateCustomer(customer);
        request.setAttribute("message", "Cập nhật thành công!");
        request.setAttribute("alertType", "success"); 
        request.getRequestDispatcher("/ManhTuan/customeredit.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
