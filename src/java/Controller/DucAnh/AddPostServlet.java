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
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Acer
 */
@WebServlet(name="AddPostServlet", urlPatterns={"/AddPost"})
public class AddPostServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddPostServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddPostServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // List Post Categories
        PostCategoryDAO postCategoryDAO = new PostCategoryDAO();
        ArrayList<PostCategoryDTO> postCategoryDTOs = postCategoryDAO.getAllPostCategory();
        request.setAttribute("postCategoryDTOs", postCategoryDTOs);
        request.getRequestDispatcher("AddNewPost.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String content = request.getParameter("content");
        String title = request.getParameter("title");
        String category = request.getParameter("category");
        System.out.println(category);
        Part banner = request.getPart("banner");
        String fileBanner = banner.getSubmittedFileName();

        if (content.isEmpty() || title.isEmpty() || fileBanner.isEmpty()) {
            request.getSession().setAttribute("msg", "Add New Post Not Success!");
            response.sendRedirect("postDashboard");
        } else {
            PostDTO pdto = new PostDTO(0, title, "", fileBanner, "", content, category);
            PostListDAO postListDAO = new PostListDAO();
            int checkAddPost = postListDAO.addNewPost(pdto);
            if (checkAddPost != 0) {
                String path = getServletContext().getRealPath("") + "images/Post";
                File file = new File(path);
                banner.write(path + File.separator + fileBanner);
                request.getSession().setAttribute("msg", "Add New Post Success!");
                response.sendRedirect("postDashboard");
            } else {
                System.out.println("Error server");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
