/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ManhTuan;

import DAL.ProductDAOTuan;
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
@WebServlet(name = "CustomerAdd", urlPatterns = {"/customeradd"})
public class CustomerAdd extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        boolean status = "1".equals(request.getParameter("status"));
        int gender = Integer.parseInt(request.getParameter("gender"));
        UserTuan customer = new UserTuan(userName, email, phoneNumber, status, gender);
        ProductDAOTuan dao = new ProductDAOTuan();
        int id = dao.addCustomer(customer);
        if (id == -1) {
            request.setAttribute("error", "Email đã tồn tại");
            request.getRequestDispatcher("ManhTuan/customeradd.jsp").forward(request, response);
        } else {
            request.setAttribute("id", id);
            request.getRequestDispatcher("ManhTuan/customeraddressadd.jsp").forward(request, response);
        }

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
