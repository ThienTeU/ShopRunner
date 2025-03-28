/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ManhTuan;

import DAL.ProductDAOTuan;
import Model.Orders;
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
@WebServlet(name = "OrderSearch", urlPatterns = {"/ordersearch"})
public class OrderSearch extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String orderDate = request.getParameter("orderDate");
        String status = request.getParameter("status");
        String paymentMethod = request.getParameter("paymentMethod");
        int page = 1;
        int pageSize = 10; 
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        ProductDAOTuan dao = new ProductDAOTuan();
        List<Orders> list = dao.searchOrders(email, orderDate, status, paymentMethod);
        int count = 0;
        for (Orders cus : list) {
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
        
        List<Orders> orders = dao.searchOrdersByPage(email, orderDate, status, paymentMethod, page, pageSize);
        
        request.setAttribute("check", "search");
        request.setAttribute("end", end);
        request.setAttribute("orders", orders);
        request.setAttribute("email", email);
        request.setAttribute("orderDate", orderDate);
        request.setAttribute("status", status);
        request.setAttribute("paymentMethod", paymentMethod);
        request.getRequestDispatcher("/ManhTuan/orderlist.jsp").forward(request, response);

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
