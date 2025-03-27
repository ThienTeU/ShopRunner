/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ManhTuan;

import DAL.ProductDAOTuan;
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
@WebServlet(name = "FeedbackChangeStatus", urlPatterns = {"/feedbackstatus"})
public class FeedbackChangeStatus extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int feedbackId = Integer.parseInt(request.getParameter("feedbackId"));
        boolean currentStatus = Boolean.parseBoolean(request.getParameter("status"));
        boolean newStatus = !currentStatus; 

        ProductDAOTuan dao = new ProductDAOTuan();
        dao.updateFeedbackStatus(feedbackId, newStatus);
        response.sendRedirect("/RunnerShop/feedbacklist");
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
