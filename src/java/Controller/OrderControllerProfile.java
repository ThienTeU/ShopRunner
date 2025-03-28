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
        // Lấy tham số từ request (ví dụ: orderId)
        String orderIdParam = request.getParameter("orderId");
        int orderId;

        if (orderIdParam == null || orderIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Order ID is required");
            return;
        }

        try {
            // Chuyển orderId từ String sang int
            orderId = Integer.parseInt(orderIdParam);

            // Gọi phương thức getProductsByOrderId từ DAO
            List<OrderDetailAnh> orderDetails = orderDAO.getProductsByOrderId(orderId);

            // Kiểm tra nếu không có dữ liệu
            if (orderDetails.isEmpty()) {
                request.setAttribute("message", "No order details found for orderId = " + orderId);
            } else {
                // Gửi danh sách sản phẩm đến JSP
                request.setAttribute("orderDetails", orderDetails);
            }

            // Chuyển tiếp (forward) đến trang JSP để hiển thị
            request.getRequestDispatcher("/orderDetailsProfile.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            // Xử lý lỗi chuyển đổi orderId
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order ID format");
        } catch (SQLException e) {
            // Xử lý lỗi truy vấn SQL
            throw new ServletException("Error retrieving order details", e);
        }
    }
}
