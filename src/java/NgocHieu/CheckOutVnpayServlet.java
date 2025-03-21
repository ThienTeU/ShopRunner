/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package NgocHieu;

import DAL.OrderDAO;
import DAL.ProductDAO;
import Model.CartItem;
import Model.Orders;
import Model.Product;
import NgocHieu.GHTKService.GHTKApiService;
import NgocHieu.GHTKService.OrderGhtk;
import NgocHieu.GHTKService.OrderRequest;
import NgocHieu.GHTKService.ProductGhtk;
import NgocHieu.service.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private static final Logger LOGGER = Logger.getLogger(CheckOutVnpayServlet.class.getName());
    private final ProductDAO productDAO = new ProductDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private final GHTKApiService ghtkService = new GHTKApiService();
    private final HttpServletRequest request = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Not implemented
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<CartItem> cartItems = getCartItemsFromCookies(request);
            if (cartItems.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cart is empty");
                return;
            }

            Orders order = processOrder(request);
            if (order == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order");
                return;
            }

            int orderId = saveOrder(order);
            if (orderId <= 0) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to save order");
                return;
            }

            List<ProductGhtk> productGhtkList = createProductGhtkList(cartItems);
            saveOrderDetails(orderId, cartItems);
            sendOrderToGHTK(orderId, productGhtkList, order);

            response.sendRedirect("SendOrderToEmailServlet");
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error processing VNPay checkout", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

    private List<CartItem> getCartItemsFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("cart".equals(cookie.getName())) {
                    try {
                        return AuthenticationService.decodeCartToken(cookie.getValue());
                    } catch (JOSEException | ParseException e) {
                        LOGGER.log(Level.WARNING, "Error decoding cart token", e);
                    }
                    break;
                }
            }
        }
        return new ArrayList<>();
    }

    private Orders processOrder(HttpServletRequest request) {
        Orders order = (Orders) request.getSession().getAttribute("order");
        if (order == null) {
            return null;
        }
        order.setStatus("paid");
        return order;
    }

    private int saveOrder(Orders order) throws SQLException {
        int orderId = orderDAO.insertOrder(order);
        if (orderId > 0) {
            order.setOrder_id(orderId);
            request.getSession().setAttribute("order_id", orderId);
        }
        return orderId;
    }

    private List<ProductGhtk> createProductGhtkList(List<CartItem> cartItems) throws SQLException {
        List<ProductGhtk> productGhtkList = new ArrayList<>();
        for (CartItem item : cartItems) {
            Product product = productDAO.getProductById(item.getProduct_id());
            if (product != null) {
                productGhtkList.add(new ProductGhtk(
                        product.getProduct_name(),
                        0.2,
                        item.getQuantity(),
                        item.getProduct_id()
                ));
            }
        }
        return productGhtkList;
    }

    private void saveOrderDetails(int orderId, List<CartItem> cartItems) throws SQLException {
        for (CartItem item : cartItems) {
            orderDAO.insertOrderDetail(orderId, item);
        }
    }

    private void sendOrderToGHTK(int orderId, List<ProductGhtk> productGhtkList, Orders order) throws JsonProcessingException, IOException {
        OrderGhtk orderGhtk = (OrderGhtk) request.getSession().getAttribute("orderGhtk");
        if (orderGhtk != null) {
            orderGhtk.setId(String.valueOf(orderId));
            orderGhtk.setPick_money(0);
            OrderRequest orderRequest = new OrderRequest(productGhtkList, orderGhtk);
            ghtkService.sendOrderToGHTK(orderRequest.toJsonBody(orderRequest));
        }
    }

    @Override
    public String getServletInfo() {
        return "VNPay Checkout Servlet";
    }
}
