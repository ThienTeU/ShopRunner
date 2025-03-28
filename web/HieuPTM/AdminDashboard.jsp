<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map.Entry" %>

<%
    Map<String, Integer> orderStatus = (Map<String, Integer>) request.getAttribute("orderStatus");
    Map<String, Double> revenueByCategory = (Map<String, Double>) request.getAttribute("revenueByCategory");
    Map<String, Integer> newCustomers = (Map<String, Integer>) request.getAttribute("newCustomers");
    Map<String, Double> feedbackRatings = (Map<String, Double>) request.getAttribute("feedbackRatings");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; }
        .dashboard-section { margin-bottom: 20px; }
        h2 { color: #2c3e50; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f4f4f4; }
    </style>
</head>
<body>
    <h1>Admin Dashboard</h1>

    <!-- Trạng thái đơn hàng -->
    <div class="dashboard-section">
        <h2>🛒 Trạng thái đơn hàng</h2>
        <table>
            <tr><th>Trạng thái</th><th>Số lượng</th></tr>
            <% for (Map.Entry<String, Integer> entry : orderStatus.entrySet()) { %>
                <tr><td><%= entry.getKey() %></td><td><%= entry.getValue() %></td></tr>
            <% } %>
        </table>
    </div>

    <!-- Doanh thu theo danh mục -->
    <div class="dashboard-section">
        <h2>💰 Doanh thu theo danh mục</h2>
        <table>
            <tr><th>Danh mục</th><th>Doanh thu (VND)</th></tr>
            <% for (Map.Entry<String, Double> entry : revenueByCategory.entrySet()) { %>
                <tr><td><%= entry.getKey() %></td><td><%= String.format("%,.0f", entry.getValue()) %></td></tr>
            <% } %>
        </table>
    </div>

    <!-- Khách hàng đăng ký mới -->
    <div class="dashboard-section">
        <h2>👥 Khách hàng đăng ký mới (7 ngày qua)</h2>
        <table>
            <tr><th>Ngày</th><th>Số lượng</th></tr>
            <% for (Map.Entry<String, Integer> entry : newCustomers.entrySet()) { %>
                <tr><td><%= entry.getKey() %></td><td><%= entry.getValue() %></td></tr>
            <% } %>
        </table>
    </div>

    <!-- Điểm đánh giá trung bình -->
    <div class="dashboard-section">
        <h2>⭐ Điểm đánh giá trung bình theo danh mục</h2>
        <table>
            <tr><th>Danh mục</th><th>Điểm trung bình</th></tr>
            <% for (Map.Entry<String, Double> entry : feedbackRatings.entrySet()) { %>
                <tr><td><%= entry.getKey() %></td><td><%= String.format("%.2f", entry.getValue()) %> ⭐</td></tr>
            <% } %>
        </table>
    </div>
</body>
</html>
