/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package ManhTuan;

import DAL.ProductDAOTuan;
import Model.Feedback;
import Model.UserTuan;
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
@WebServlet(name="FeedbackList", urlPatterns={"/feedbacklist"})
public class FeedbackList extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ProductDAOTuan dao = new ProductDAOTuan();
        List<Feedback> list = dao.getAllFeedback();
        int count = 0;
        for (Feedback f : list) {
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
        List<Feedback> feedbacks = dao.getAllFeedbackByPage(index, size);
        request.setAttribute("end", end);
        request.setAttribute("check", "list");
        request.setAttribute("feedbacks", feedbacks);
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
    
    public static void main(String[] args) {
        ProductDAOTuan dao = new ProductDAOTuan();
        List<Feedback> list = dao.getAllFeedback();
        int count = 0;
        for (Feedback f : list) {
            count++;
        }
        int size = 1;
        int end = 0;
        if (count % size == 0) {
            end = count / size;
        } else {
            end = (count / size) + 1;
        }
        int index = 1;
        
        List<Feedback> feedbacks = dao.getAllFeedbackByPage(index, size);
        for(Feedback l : feedbacks){
            System.out.println(l.toString());
        }
    }

}
