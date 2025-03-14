/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.DucAnh;

import DAO.DucAnh.PostCategoryDAO;
import DAO.DucAnh.PostListDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Acer
 */
@WebServlet(name="DeletePostServlet", urlPatterns={"/DeletePost"})
public class DeletePostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String postID_raw = request.getParameter("id");
        PostListDAO postListDAO = new PostListDAO();

        try {
            int postID = Integer.parseInt(postID_raw);
            System.out.println("Deleting post ID: " + postID);

            int checkDelete = postListDAO.deletePostByID(postID);
            if (checkDelete != 0) {
                session.setAttribute("msg", "Delete Post Success");
            } else {
                session.setAttribute("msg", "Delete Post Not Success");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("msg", "Invalid Post ID");
            e.printStackTrace();
        } catch (Exception e) {
            session.setAttribute("msg", "Error while deleting post");
            e.printStackTrace();
        }

        response.sendRedirect("postDashboard");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for deleting posts";
    }
}