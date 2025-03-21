<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Marketing Dashboard</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    </head>
    <body class="bg-light">
        <div class="container mt-5">
            <h2 class="mb-4">Marketing Dashboard</h2>

            <!-- Date Range & Filter -->
            <div class="row mb-4">
                <div class="col-md-3">
                    <label for="startDate" class="form-label">Start Date:</label>
                    <input type="date" id="startDate" class="form-control">
                </div>
                <div class="col-md-3">
                    <label for="endDate" class="form-label">End Date:</label>
                    <input type="date" id="endDate" class="form-control">
                </div>
                <div class="col-md-3">
                    <label for="filterType" class="form-label">Filter:</label>
                    <select id="filterType" class="form-select">
                        <option value="day">Day</option>
                        <option value="month">Month</option>
                        <option value="year">Year</option>
                    </select>
                </div>
                <div class="col-md-3 d-flex align-items-end">
                    <button class="btn btn-primary" onclick="updateData()">Apply</button>
                </div>
            </div>

            <!-- Summary Cards -->
            <div class="row text-center">
                <div class="col-md-3">
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Products</h5>
                            <p id="productCount" class="fs-3">120</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Customers</h5>
                            <p id="customerCount" class="fs-3">230</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Feedbacks</h5>
                            <p id="feedbackCount" class="fs-3">32</p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Additional Marketing Metrics -->
            <div class="row text-center mt-4">
                <div class="col-md-4">
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Revenue</h5>
                            <p id="revenue" class="fs-3">$7520.50</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Orders</h5>
                            <p id="orderCount" class="fs-3">128</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Conversion Rate</h5>
                            <p id="conversionRate" class="fs-3">5.83%</p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Charts -->
            <div class="mt-5">
                <h4>New Customers Trend</h4>
                <canvas id="customerTrendChart"></canvas>
            </div>

            <div class="mt-5">
                <h4>Product Sales Trend</h4>
                <canvas id="productSalesChart"></canvas>
            </div>

            <div class="mt-5">
                <h4>Order Volume Trend</h4>
                <canvas id="orderVolumeChart"></canvas>
            </div>
        </div>

        <script>
            const customerTrendChart = new Chart(document.getElementById('customerTrendChart'), {
                type: 'line',
                data: {
                    labels: ['Day 1', 'Day 2', 'Day 3', 'Day 4', 'Day 5', 'Day 6', 'Day 7'],
                    datasets: [{
                            label: 'New Customers',
                            data: [12, 18, 9, 14, 20, 15, 22],
                            borderColor: 'rgb(75, 192, 192)',
                            tension: 0.1
                        }]
                }
            });

            const productSalesChart = new Chart(document.getElementById('productSalesChart'), {
                type: 'bar',
                data: {
                    labels: ['Day 1', 'Day 2', 'Day 3', 'Day 4', 'Day 5', 'Day 6', 'Day 7'],
                    datasets: [{
                            label: 'Product Sales',
                            data: [30, 25, 40, 50, 35, 45, 60],
                            backgroundColor: 'rgba(54, 162, 235, 0.5)'
                        }]
                }
            });

            const orderVolumeChart = new Chart(document.getElementById('orderVolumeChart'), {
                type: 'line',
                data: {
                    labels: ['Day 1', 'Day 2', 'Day 3', 'Day 4', 'Day 5', 'Day 6', 'Day 7'],
                    datasets: [{
                            label: 'Order Volume',
                            data: [50, 60, 45, 70, 65, 75, 80],
                            borderColor: 'rgb(255, 99, 132)',
                            tension: 0.1
                        }]
                }
            });
        </script>
    </body>
</html>
