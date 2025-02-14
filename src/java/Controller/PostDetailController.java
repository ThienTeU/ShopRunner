/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;



import DAO.PostDetailDAO;
import DAO.PostListDAO;
import DTO.PostDTO;
import DTO.PostDetailDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import jakarta.servlet.annotation.WebServlet;

/**
 *
 * @author 1234
 */

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
                    ArrayList<PostDTO> pdtos = new ArrayList<>();
                    int postID = Integer.parseInt(postID_raw);
                    pdtos = pldao.getPostLatest(postID);
                    PostDTO pdto = pldao.getPostDTOByID(postID);
                    request.setAttribute("pdtos", pdtos);
                    request.setAttribute("pdto", pdto);
                    request.getRequestDispatcher("postDetail.jsp").forward(request, response);

                } catch (Exception e) {
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
