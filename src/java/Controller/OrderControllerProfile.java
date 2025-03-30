package Controller;

import DAL.OrderDAO;
import Model.OrderDetailAnh;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "OrderControllerProfile", urlPatterns = {"/ordersprofile"})
public class OrderControllerProfile extends HttpServlet {

    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        // Khởi tạo OrderDAO
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderIdParam = request.getParameter("orderId");
        int orderId;

        if (orderIdParam == null || orderIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Order ID is required");
            return;
        }

        try {
            orderId = Integer.parseInt(orderIdParam);

            List<OrderDetailAnh> orderDetails = orderDAO.getProductsByOrderId(orderId);

            if (orderDetails.isEmpty()) {
                request.setAttribute("message", "No order details found for orderId = " + orderId);
            } else {
                request.setAttribute("orderDetails", orderDetails);
            }

            request.getRequestDispatcher("/orderDetailsProfile.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order ID format");
        } catch (SQLException e) {
            throw new ServletException("Error retrieving order details", e);
        }
    }
}
