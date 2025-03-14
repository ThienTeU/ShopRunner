/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package NgocHieu;

import DAL.OrderDAO;
import DAL.ProductDAO;
import Model.CartItem;
import Model.Orders;
import NgocHieu.GHTKService.GHTKApiService;
import NgocHieu.GHTKService.OrderGhtk;
import NgocHieu.GHTKService.OrderRequest;
import NgocHieu.GHTKService.ProductGhtk;
import NgocHieu.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
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
            List<CartItem> cartItems = new ArrayList<>();
            List<ProductGhtk> listProductGhtk = new ArrayList<>();
            ProductDAO productDao = new ProductDAO();
            
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("cart")) {
                        cartItems = AuthenticationService.decodeCartToken(cookie.getValue());
                    }
                }
            }

            OrderDAO orderDAO = new OrderDAO();
            Orders order = (Orders) request.getSession().getAttribute("order");
            order.setStatus("paid");
            int order_id = orderDAO.insertOrder(order);
            order.setOrder_id(order_id);
            request.getSession().setAttribute("order_id", order_id);
            response.getWriter().println(order_id);
            response.getWriter().println(order.getStatus());
            if (order_id > 0) {
                for (CartItem item : cartItems) {
                    orderDAO.insertOrderDetail(order_id, item);
                    listProductGhtk.add(new ProductGhtk(
                            productDao.getProductById(item.getProduct_id()).getProduct_name(),
                            0.2,
                            item.getQuantity(),
                            item.getProduct_id()
                    ));
                }
            }
            //Lay thong tin order o ben check out va set lai cac gia tri khi nguoi dung da thanh toan
            OrderGhtk orderGhtk = (OrderGhtk) request.getSession().getAttribute("orderGhtk");
            orderGhtk.setId(String.valueOf(order_id));
            orderGhtk.setPick_money(0);
            OrderRequest orderRequest = new OrderRequest(listProductGhtk, orderGhtk);
            GHTKApiService ghtkService = new GHTKApiService();
            ghtkService.sendOrderToGHTK(orderRequest.toJsonBody(orderRequest));
            response.sendRedirect("SendOrderToEmailServlet");
        } catch (SQLException | JOSEException | ParseException ex) {
            Logger.getLogger(CheckOutVnpayServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
