
package HieuPTM.controller;

import HieuPTM.DAO.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import HieuPTM.model.UserHieu;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name="StaffDelete", urlPatterns={"/StaffDelete"})
public class StaffDelete extends HttpServlet {
    
protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserDelete</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserDelete at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String did = request.getParameter("did");
        String username = request.getParameter("uid");
        UserDAO udao = new UserDAO();
        udao.deleteUser(did);
        response.sendRedirect("umanage?uid="+ username);
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