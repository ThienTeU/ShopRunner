/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package NgocHieu;

import DAL.OrderDAO;
import Model.CartItem;
import Model.Orders;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
@WebServlet(name = "CheckOutVnpayServlet", urlPatterns = {"/CheckOutVnpayServlet"})
public class CheckOutVnpayServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<CartItem> cartItems = (List<CartItem>) request.getSession().getAttribute("cart");
            OrderDAO orderDAO = new OrderDAO();
            Orders order = (Orders) request.getSession().getAttribute("order");
            int order_id = orderDAO.insertOrder(order);
            order.setOrder_id(order_id);
            request.getSession().setAttribute("order", order);
            if (order_id > 0) {
                for (CartItem item : cartItems) {
                    orderDAO.insertOrderDetail(order_id, item);
                }
                deleteAllItemInCookie(request, response);
                response.sendRedirect("NgocHieu/OrderSuccessJSP.jsp");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CheckOutVnpayServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteAllItemInCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().startsWith("cartItem_")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
