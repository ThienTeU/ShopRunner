package HieuPTM.Controller;

import HieuPTM.DAO.CustomerRankDAO;
import Model.Orders;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="CustomerRank", urlPatterns={"/CustomerRank"})
public class CustomerRank extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        CustomerRankDAO dao = new CustomerRankDAO();
        List<Orders> orders = dao.getBonusPoint();

        request.setAttribute("orders", orders);

        request.getRequestDispatcher("/HieuPTM/CustomerRank.jsp").forward(request, response);
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
        return "Customer Rank Servlet - Hiển thị điểm thưởng khách hàng";
    }
}
