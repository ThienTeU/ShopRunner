/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.DucAnh;

import DAO.DucAnh.PostDetailDAO;
import DAO.DucAnh.PostListDAO;
import DTO.DucAnh.PostDTO;
import DTO.DucAnh.PostDetailDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author Acer
 */
@WebServlet(name="PostDetailController", urlPatterns={"/postDetail"})
public class PostDetailController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PostDetail</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PostDetail at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String postID_raw = request.getParameter("postID");
    PostDetailDAO pddao = new PostDetailDAO();
    PostListDAO pldao = new PostListDAO();
    
    try {
        int postID = Integer.parseInt(postID_raw);
        
        // Cập nhật lượt xem
        pddao.updatePostView(postID);
        
        // Lấy số lượt xem mới từ database
        int views = pddao.getPostViews(postID);
        
        // Lấy thông tin bài viết
        PostDTO pdto = pldao.getPostDTOByID(postID);
        ArrayList<PostDTO> pdtos = pldao.getPostLatest(postID);
        
        // Đặt dữ liệu vào request
        request.setAttribute("views", views);
        request.setAttribute("pdtos", pdtos);
        request.setAttribute("pdto", pdto);
        
        System.out.println("DEBUG: Views Count = " + views); // Kiểm tra log
        
        // Chuyển hướng đến trang JSP
        request.getRequestDispatcher("postDetail.jsp").forward(request, response);
    } catch (Exception e) {
        e.printStackTrace();
    }
}



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}