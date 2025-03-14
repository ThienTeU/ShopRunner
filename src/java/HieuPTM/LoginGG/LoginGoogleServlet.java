package HieuPTM.LoginGG;

import HieuPTM.LoginGG.GooglePojo;
import HieuPTM.LoginGG.GoogleUtils;
import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


@WebServlet(name="LoginGoogleServlet", urlPatterns={"/LoginGoogleServlet"})
public class LoginGoogleServlet extends HttpServlet {
private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginGoogleServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginGoogleServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 
    
    public LoginGoogleServlet() {
        super();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String code = request.getParameter("code");
            if (code == null || code.isEmpty()) {
                RequestDispatcher dis = request.getRequestDispatcher("HieuPTM/login.jsp");
                dis.forward(request, response);
            } else {
                    String accessToken = GoogleUtils.getToken(code);
                    GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);
                    request.setAttribute("id", googlePojo.getId());
                    request.setAttribute("name", googlePojo.getName());
                    request.setAttribute("email", googlePojo.getEmail());
                    RequestDispatcher dis = request.getRequestDispatcher("HieuPTM/index.jsp");
                    dis.forward(request, response);
            }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doGet(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}