/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ManhTuan;

import DAL.ProductDAOTuan;
import Model.OrderDetails;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author tuan
 */
@WebServlet(name = "OrderDetails", urlPatterns = {"/orderdetail"})
public class OrderDetail extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("id"));
        ProductDAOTuan dao = new ProductDAOTuan();
        List<OrderDetails> details = dao.getOrderDetailsByOrderId(orderId);
        
        request.setAttribute("orderDetails", details);
        request.getRequestDispatcher("/ManhTuan/orderdetail.jsp").forward(request, response);
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
        List<OrderDetails> details = dao.getOrderDetailsByOrderId(1);
        for(OrderDetails o : details){
            System.out.println(o.toString());
        }
    }
}
