/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package NgocHieu.ProductManagement;

import NgocHieu.GHTKService.GHTKApiService;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
@WebServlet(name="CancelOrderServlet", urlPatterns={"/CancelOrderServlet"})
public class CancelOrderServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        GHTKApiService service = new GHTKApiService();
        String label = request.getParameter("label");
        String orderId = request.getParameter("orderId");
        service.cancelOrder(label, orderId);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<script>alert('Hủy đơn hàng thành công!'); window.location='OrderManageServlet';</script>");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
