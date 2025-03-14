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
@WebServlet(name="CustomerChangeStatus", urlPatterns={"/changeStatus"})
public class CustomerChangeStatus extends HttpServlet {
   
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ProductDAOTuan dao = new ProductDAOTuan();
       String id = request.getParameter("userId");
       int customerId = Integer.parseInt(id);
       UserTuan customer = dao.getCustomerById(customerId);
       boolean newStatus = !customer.isStatus();
       dao.updateCustomerStatus(customerId, newStatus);
       response.sendRedirect("customerlist");
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
        ProductDAOTuan dao = new ProductDAOTuan();
       int customerId = 5;
       UserTuan customer = dao.getCustomerById(customerId);
        System.out.println(customer);
       boolean newStatus = !customer.isStatus();
        System.out.println(newStatus);
       dao.updateCustomerStatus(customerId, newStatus);
        System.out.println(customer);
    }

}
