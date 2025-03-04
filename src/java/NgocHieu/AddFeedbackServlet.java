/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package NgocHieu;

import DAL.FeedbackDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
@WebServlet(name="AddFeedbackServlet", urlPatterns={"/AddFeedbackServlet"})
public class AddFeedbackServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String id = request.getParameter("product_id");
        String email = request.getParameter("email");
        String ratingS = request.getParameter("rating");
        String feedback_content = request.getParameter("feedback_content");
        if(id == null || email == null || ratingS == null){
            response.getWriter().println("<script>alert('Thông tin feedback bị thiếu'); window.location='ProductDetailServlet?product_id=" +id+"';</script>");
        }
        int product_id = Integer.parseInt(id);
        int rating = Integer.parseInt(ratingS);
        FeedbackDAO feedbackDao = new FeedbackDAO();
        if(feedbackDao.insertFeedback(email, product_id, feedback_content, rating)){
            response.getWriter().println("<script>alert('Đã gửi đánh giá'); window.location='ProductDetailServlet?product_id=" +id+"';</script>");
        }
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
