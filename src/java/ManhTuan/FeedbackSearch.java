/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ManhTuan;

import DAL.ProductDAOTuan;
import Model.Feedback;
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
@WebServlet(name = "FeedbackSearch", urlPatterns = {"/feedbacksearch"})
public class FeedbackSearch extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String searchName = request.getParameter("searchName");
        String searchProduct = request.getParameter("searchProduct");
        String ratingFilter = request.getParameter("ratingFilter");
        String sortOrder = request.getParameter("sortOrder");

        int page = 1;
        int pageSize = 10;
        if (request.getParameter("index") != null) {
            page = Integer.parseInt(request.getParameter("index"));
        }

        ProductDAOTuan dao = new ProductDAOTuan();
        
        List<Feedback> feedbacks = dao.searchFeedback(searchName, searchProduct, ratingFilter, sortOrder, page, pageSize);
        int totalRecords = dao.countFilteredFeedbacks(searchName, searchProduct, ratingFilter);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        request.setAttribute("feedbacks", feedbacks);
        request.setAttribute("end", totalPages);
        request.setAttribute("index", page);
        request.setAttribute("check", "search");
        request.setAttribute("searchName", searchName);
        request.setAttribute("searchProduct", searchProduct);
        request.setAttribute("ratingFilter", ratingFilter);
        request.setAttribute("sortOrder", sortOrder);

        request.getRequestDispatcher("/ManhTuan/feedbackmanagement.jsp").forward(request, response);
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
