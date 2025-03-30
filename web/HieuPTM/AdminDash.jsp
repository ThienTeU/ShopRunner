<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Map" %>
<%@page import="com.google.gson.Gson" %>
<%
    Map<String, Integer> orderStatus = (Map<String, Integer>) request.getAttribute("orderStatus");
    Map<String, Double> revenue = (Map<String, Double>) request.getAttribute("revenue");
    Map<String, Integer> newCustomers = (Map<String, Integer>) request.getAttribute("newCustomers");
    Map<String, Double> feedbackRatings = (Map<String, Double>) request.getAttribute("feedbackRatings");

    String orderStatusJson = new Gson().toJson(orderStatus);
    String revenueJson = new Gson().toJson(revenue);
    String newCustomersJson = new Gson().toJson(newCustomers);
    String feedbackRatingsJson = new Gson().toJson(feedbackRatings);
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    </head>
    <style>
        canvas {
            max-width: 100%;
            max-height: 350px;
        }
    </style>
    <body>
        <!-- Header -->
        <%@ include file="/model/styles.jsp" %>
        <%@ include file="/model/header.jsp" %>
        <div class="row" style="margin-top: 50px;">
            <div class="container mt-4">
                <h2 class="text-center">Admin Dashboard</h2>

                <div class="row">
                    <div class="col-md-6">
                        <h4 class="text-center">Trạng thái đơn hàng</h4>
                        <canvas id="orderStatusChart" width="400" height="300"></canvas>
                    </div>
                    <div class="col-md-6">
                        <h4 class="text-center">Doanh thu theo danh mục</h4>
                        <canvas id="revenueChart" width="400" height="300"></canvas>
                    </div>
                </div>

                <div class="row mt-4">
                    <div class="col-md-6">
                        <h4 class="text-center">Khách hàng mới</h4>
                        <canvas id="customerChart" width="400" height="300"></canvas>
                        <p class="text-center mt-2" id="customerDate"></p>
                    </div>
                    <div class="col-md-6">
                        <h4 class="text-center">Phản hồi từ khách hàng</h4>
                        <canvas id="feedbackChart" width="400" height="300"></canvas>
                    </div>
                </div>

                <div class="row mt-4">
                    <div class="col-md-12">
                        <h4 class="text-center">Xu hướng số lượng đơn hàng</h4>
                        <div class="d-flex justify-content-center mb-3">
                            <label for="startDate" class="me-2">Từ ngày:</label>
                            <input type="date" id="startDate" class="me-3">
                            <label for="endDate" class="me-2">Đến ngày:</label>
                            <input type="date" id="endDate">
                        </div>
                        <canvas id="orderTrendChart" width="400" height="300"></canvas>
                    </div>
                </div>
            </div>

            <script>
                const orderStatusData = <%= orderStatusJson %>;
                const revenueData = <%= revenueJson %>;
                const newCustomersData = <%= newCustomersJson %>;
                const feedbackRatingsData = <%= feedbackRatingsJson %>;

                const orderStatusLabels = Object.keys(orderStatusData);
                const orderStatusValues = Object.values(orderStatusData);

                new Chart(document.getElementById('orderStatusChart'), {
                    type: 'doughnut',
                    data: {
                        labels: orderStatusLabels,
                        datasets: [{
                                data: orderStatusValues,
                                backgroundColor: ['#FFA500', '#007BFF', '#28A745', '#DC3545']
                            }]
                    }
                });

                const revenueLabels = Object.keys(revenueData);
                const revenueValues = Object.values(revenueData);

                new Chart(document.getElementById('revenueChart'), {
                    type: 'bar',
                    data: {
                        labels: revenueLabels,
                        datasets: [{
                                label: 'Doanh thu (VND)',
                                data: revenueValues,
                                backgroundColor: '#17A2B8'
                            }]
                    }
                });

                const customerLabels = Object.keys(newCustomersData);
                const customerValues = Object.values(newCustomersData);

                new Chart(document.getElementById('customerChart'), {
                    type: 'line',
                    data: {
                        labels: customerLabels,
                        datasets: [{
                                label: 'Khách hàng mới',
                                data: customerValues,
                                borderColor: '#007BFF',
                                fill: false
                            }]
                    }
                });

                const feedbackLabels = Object.keys(feedbackRatingsData);
                const feedbackValues = Object.values(feedbackRatingsData);

                new Chart(document.getElementById('feedbackChart'), {
                    type: 'bar',
                    data: {
                        labels: feedbackLabels,
                        datasets: [{
                                label: 'Điểm trung bình',
                                data: feedbackValues,
                                backgroundColor: '#FFC107'
                            }]
                    }
                });
            </script>
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    fetch("/AdminDashboard?startDate=2025-03-21&endDate=2025-03-28")
                            .then(response => response.json())
                            .then(data => {
                                var ctx = document.getElementById("orderTrendsChart").getContext("2d");
                                new Chart(ctx, {
                                    type: "line",
                                    data: {
                                        labels: Object.keys(data.orderTrends),
                                        datasets: [{
                                                label: "Số lượng đơn hàng",
                                                data: Object.values(data.orderTrends),
                                                borderColor: "blue",
                                                fill: false
                                            }]
                                    }
                                });
                            })
                            .catch(error => console.error("Lỗi khi tải dữ liệu:", error));
                });
            </script>
            <canvas id="orderTrendsChart" width="400" height="300"></canvas>
            <!-- Footer -->
            <%@ include file="/model/footer.jsp" %>
    </body>
</html>