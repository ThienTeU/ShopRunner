
package HieuPTM.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "LogOut", urlPatterns = {"/LogOut"})
public class LogOut extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            response.sendRedirect("/home");
            return;
        }

        for (Cookie c : cookies) {
            if (c.getName().equals("tokenC")) {
                  // Xóa cookie bằng cách đặt thời gian sống về 0
                
                Cookie cookie = new Cookie("tokenC","");
                Cookie cookieR = new Cookie("re","");
                cookie.setMaxAge(0);
                cookieR.setMaxAge(0);
                c.setPath("/"); 
                response.addCookie(cookie);
                response.addCookie(cookieR);
                response.sendRedirect("/RunnerShop/LoginControl");
                request.getSession().invalidate();
                return; // Ngăn chặn gửi redirect nhiều lần
            }
        }

        response.sendRedirect("/RunnerShop/home");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
