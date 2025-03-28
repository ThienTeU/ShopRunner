package HieuPTM.Controller;

import com.google.gson.Gson;
import HieuPTM.DAO.DashboardDAO;
import java.io.IOException;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;

@WebServlet(name = "AdminDashboard", urlPatterns = {"/admin/dashboard"})
public class AdminDashboard extends HttpServlet {

    private final DashboardDAO dashboardDAO = new DashboardDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            // Lấy dữ liệu từ DAO và kiểm tra null
            Map<String, Integer> orderStatus = dashboardDAO.getOrderStatusCounts();
            Map<String, Double> revenueByCategory = dashboardDAO.getRevenueByCategory();
            Map<String, Integer> newCustomers = dashboardDAO.getNewCustomersLast7Days();
            Map<String, Double> feedbackRatings = dashboardDAO.getFeedbackRatings();

            // Đảm bảo không bị null
            if (orderStatus == null) orderStatus = new HashMap<>();
            if (revenueByCategory == null) revenueByCategory = new HashMap<>();
            if (newCustomers == null) newCustomers = new HashMap<>();
            if (feedbackRatings == null) feedbackRatings = new HashMap<>();

            // Gán vào request
            request.setAttribute("orderStatus", orderStatus);
            request.setAttribute("revenueByCategory", revenueByCategory);
            request.setAttribute("newCustomers", newCustomers);
            request.setAttribute("feedbackRatings", feedbackRatings);

            // Chuyển hướng đến dashboard.jsp
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
            return;
        }
    }
}
