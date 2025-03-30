package HieuPTM.Controller;

import HieuPTM.DAO.DashboardDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@WebServlet(name = "AdminDashboard", urlPatterns = {"/AdminDashboard"})
public class AdminDashboard extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        DashboardDAO dao = new DashboardDAO();

        // Lấy dữ liệu
        Map<String, Integer> orderStatus = dao.getOrderStatusCounts();
        Map<String, Double> revenue = dao.getRevenueByCategory();
        Map<String, Integer> newCustomers = dao.getNewCustomersLast7Days();
        Map<String, Double> feedbackRatings = dao.getFeedbackRatings();

        // Nếu có ngày, lấy xu hướng đơn hàng
        if (startDate != null && endDate != null) {
            Map<String, Integer> orderTrends = dao.getOrderTrends(startDate, endDate);
            request.setAttribute("orderTrends", orderTrends);
        }

        request.setAttribute("orderStatus", orderStatus);
        request.setAttribute("revenue", revenue);
        request.setAttribute("newCustomers", newCustomers);
        request.setAttribute("feedbackRatings", feedbackRatings);

        // Chuyển hướng đến trang AdminDash.jsp
        request.getRequestDispatcher("/HieuPTM/AdminDash.jsp").forward(request, response);
    }
}
