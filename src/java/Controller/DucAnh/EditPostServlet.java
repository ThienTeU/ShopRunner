/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.DucAnh;

import DAO.DucAnh.PostCategoryDAO;
import DAO.DucAnh.PostDetailDAO;
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
@WebServlet(name="EditPostServlet", urlPatterns={"/EditPost"})
public class EditPostServlet extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EditPostServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditPostServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String poid_raw = request.getParameter("pcid");

        PostDetailDAO pddao = new PostDetailDAO();
        PostCategoryDAO postCategoryDAO = new PostCategoryDAO();
        ArrayList<PostCategoryDTO> postCategoryDTOs = postCategoryDAO.getAllPostCategory();

        try {
            int poid = Integer.parseInt(poid_raw);
            PostDTO pdto = pddao.getPostDTOByID(poid);
            request.setAttribute("postCategoryDTOs", postCategoryDTOs);
            request.setAttribute("pdto", pdto);
            request.getRequestDispatcher("EditPost.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String pid_raw = request.getParameter("pid");
        String banner_raw = request.getParameter("pbanner");
        String content = request.getParameter("content");
        String title = request.getParameter("title");
        try {
            int pid = Integer.parseInt(pid_raw);
            Part banner = request.getPart("banner");
            String fileBanner = banner.getSubmittedFileName();
            PostDTO pdto;
            if (fileBanner.isEmpty()) {
                pdto = new PostDTO(pid, title, "", banner_raw, "", content, "category");
            } else {
                pdto = new PostDTO(pid, title, "", fileBanner, "", content, "category");
            }
            PostDetailDAO postDetailDAO = new PostDetailDAO();
            int checkUpdatePost = postDetailDAO.updatePost(pdto);
            if (checkUpdatePost != 0) {
                if (!fileBanner.isEmpty()) {
                    String path = getServletContext().getRealPath("") + "images/Post";
                    File file = new File(path);
                    banner.write(path + File.separator + fileBanner);
                }
                session.setAttribute("msg", "Update Post Success!");
                response.sendRedirect("postDashboard");
            } else {
                response.sendRedirect("error.jsp");
            }
        } catch (Exception e) {
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

