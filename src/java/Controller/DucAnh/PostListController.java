/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.DucAnh;

import DAO.DucAnh.PostCategoryDAO;
import DAO.DucAnh.PostListDAO;
import DTO.DucAnh.PostCategoryDTO;
import DTO.DucAnh.PostDTO;
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
@WebServlet(name="PostListController", urlPatterns={"/PostListController"})
public class PostListController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PostListController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PostListController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String num_raw = request.getParameter("num");
        ArrayList<PostDTO> postDTOs = new ArrayList<>();
        PostCategoryDAO postCategoryDAO = new PostCategoryDAO();
        ArrayList<PostCategoryDTO> postCategoryDTOs = postCategoryDAO.getAllPostCategory();
        PostListDAO pldao = new PostListDAO();
        
        try {
            if (num_raw != null) {
                int num = Integer.parseInt(num_raw);
                postDTOs = pldao.getAllPostWithCondition(num);
            } else {
                postDTOs = pldao.getAllPost();
            }
            request.setAttribute("postCategoryDTOs", postCategoryDTOs);
            request.setAttribute("postDTOs", postDTOs);
            request.setAttribute("size", postDTOs.size());
            request.getRequestDispatcher("PostList.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String searchKey = request.getParameter("search");
        System.out.println(searchKey);
        ArrayList<PostDTO> postDTOs = new ArrayList<>();
        PostListDAO pldao = new PostListDAO();
        PostCategoryDAO postCategoryDAO = new PostCategoryDAO();

        postDTOs = pldao.getPostSearch(searchKey);
        ArrayList<PostCategoryDTO> postCategoryDTOs = postCategoryDAO.getAllPostCategory();

        request.setAttribute("postCategoryDTOs", postCategoryDTOs);
        System.out.println("size post" + postDTOs.size());
        request.setAttribute("size", postDTOs.size());
        request.setAttribute("postDTOs", postDTOs);
        request.getRequestDispatcher("PostList.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
