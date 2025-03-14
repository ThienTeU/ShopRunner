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
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 *
 * @author Acer
 */
@WebServlet(name="PostDashboardServlet", urlPatterns={"/postDashboard"})
public class PostDashboardServlet extends HttpServlet {
   
       protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PostDashboardServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PostDashboardServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
       

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        String pcateID_raw = request.getParameter("postCategoryID");
        String indexPage_raw = request.getParameter("indexPage");

        String message = (String) session.getAttribute("msg");
        session.removeAttribute("msg");

        PostListDAO pldao = new PostListDAO();
        ArrayList<PostDTO> postDTOs = new ArrayList<>();

        try {
            int indexPage = (indexPage_raw == null) ? 1 : Integer.parseInt(indexPage_raw);
            int endPage = 0;

            if (pcateID_raw != null) {
                int pcateID = Integer.parseInt(pcateID_raw);
                postDTOs = pldao.getAllPostWithCondition(pcateID);
                int numPost = postDTOs.size();
                endPage = (numPost / 3) + ((numPost % 3 != 0) ? 1 : 0);
                postDTOs = pldao.pagingPostWithCondition(pcateID, indexPage);
            } else {
                postDTOs = pldao.getAllPost();
                int numPost = postDTOs.size();
                endPage = (numPost / 3) + ((numPost % 3 != 0) ? 1 : 0);
                postDTOs = pldao.pagingPost(indexPage);
            }
            PostCategoryDAO postCategoryDAO = new PostCategoryDAO();
            ArrayList<PostCategoryDTO> postCategoryDTOs = postCategoryDAO.getAllPostCategory();

            request.setAttribute("endPage", endPage);
            request.setAttribute("postCategoryDTOs", postCategoryDTOs);
            request.setAttribute("postDTOs", postDTOs);
            request.setAttribute("msg", message);
            request.setAttribute("tag", indexPage);
            request.getRequestDispatcher("PostManage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchKey = request.getParameter("search");
        System.out.println(searchKey);
        
        ArrayList<PostDTO> postDTOs = new ArrayList<>();
        PostListDAO pldao = new PostListDAO();
        PostCategoryDAO postCategoryDAO = new PostCategoryDAO();

        postDTOs = pldao.getPostSearch(searchKey);
        ArrayList<PostCategoryDTO> postCategoryDTOs = postCategoryDAO.getAllPostCategory();

        request.setAttribute("postCategoryDTOs", postCategoryDTOs);
        request.setAttribute("size", postDTOs.size());
        request.setAttribute("postDTOs", postDTOs);
        request.getRequestDispatcher("PostSearchAdmin.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}