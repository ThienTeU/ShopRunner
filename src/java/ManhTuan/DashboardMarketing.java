/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ManhTuan;

import DAL.ProductDAOTuan;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tuan
 */
@WebServlet(name = "DashboardMarketing", urlPatterns = {"/dashboard"})
public class DashboardMarketing extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String filterType = request.getParameter("filter-type");
        if (filterType == null || filterType.isEmpty()) {
            filterType = "month"; // Mặc định lọc theo tháng
        }
        String startDate = request.getParameter("start-date");
        if (startDate == null || startDate.isEmpty()) {
            startDate = "2025-01-01"; // Mặc định từ đầu năm
        }
        String endDate = request.getParameter("end-date");
        if (endDate == null || endDate.isEmpty()) {
            endDate = LocalDate.now().toString(); // Mặc định đến hiện tại
        }

        request.setAttribute("start_date", startDate);
        request.setAttribute("end_date", endDate);

        ProductDAOTuan dao = new ProductDAOTuan();

        request.setAttribute("orders", dao.getOrderByDate(startDate, endDate));

        Map<String, Integer> viewStats = dao.getProductViews(startDate, endDate);
        List<String> viewLabels = new ArrayList<>(viewStats.keySet());
        List<Integer> viewCounts = new ArrayList<>(viewStats.values());

        Map<String, Integer> reviewStats = dao.getProductReviews(startDate, endDate);
        List<String> reviewLabels = new ArrayList<>(reviewStats.keySet());
        List<Integer> reviewCounts = new ArrayList<>(reviewStats.values());

        Map<String, Integer> customerStats = dao.getCustomerAnalysis(startDate, endDate);
        List<String> customerTypes = new ArrayList<>(customerStats.keySet());
        List<Integer> customerCounts = new ArrayList<>(customerStats.values());

        Map<String, Integer> topProducts = dao.getTopProducts(startDate, endDate);
        List<String> productNames = new ArrayList<>(topProducts.keySet());
        List<Integer> productCounts = new ArrayList<>(topProducts.values());

        Map<String, Double> revenueData = dao.getRevenueByDate(startDate, endDate);
        List<String> labels = new ArrayList<>(revenueData.keySet());
        List<Double> values = new ArrayList<>(revenueData.values());

        request.setAttribute("viewStats", Map.of("labels", viewLabels, "counts", viewCounts));
        request.setAttribute("reviewStats", Map.of("labels", reviewLabels, "counts", reviewCounts));
        request.setAttribute("customerStats", Map.of("types", customerTypes, "counts", customerCounts));
        request.setAttribute("topProducts", Map.of("names", productNames, "counts", productCounts));
        request.setAttribute("revenueData", Map.of("labels", labels, "values", values));

        request.getRequestDispatcher("/ManhTuan/marketingdashboard.jsp").forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    public static void main(String[] args) {
        ProductDAOTuan dao = new ProductDAOTuan();

        Map<String, Integer> viewStats = dao.getProductViews("2024-03-01", "2025-12-12");
        List<String> viewLabels = new ArrayList<>(viewStats.keySet());
        List<Integer> viewCounts = new ArrayList<>(viewStats.values());
        System.out.println("View Stats:");
        for (int i = 0; i < viewLabels.size(); i++) {
            System.out.println(viewLabels.get(i) + ": " + viewCounts.get(i));
        }

    }
}
