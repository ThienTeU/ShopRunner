<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Marketing Dashboard</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    </head>
    <body>
        <div class="container-fluid">
            <h1 class="my-4">Marketing Dashboard</h1>

            <!-- Bộ lọc và tìm kiếm -->
            <form action="dashboard" method="post" class="filter-section row mb-4">
                <div class="col-md-3">
                    <label for="filter-type">Loại lọc:</label>
                    <select id="filter-type" name="filter-type" class="form-select">
                        <option value="day">Theo ngày</option>
                        <option value="month">Theo tháng</option>
                        <option value="year">Theo năm</option>
                    </select>
                </div>
                <div class="col-md-3">
                    <label for="start-date">Ngày bắt đầu:</label>
                    <input type="date" id="start-date" name="start-date" value="${start_date}" class="form-control">
                </div>
                <div class="col-md-3">
                    <label for="end-date">Ngày kết thúc:</label>
                    <input type="date" id="end-date" name="end-date" value="${end_date}" class="form-control">
                </div>
                <div class="col-md-3 d-flex align-items-end">
                    <button type="submit" class="btn btn-primary w-100">Lọc</button>
                </div>
            </form>



            <!-- Biểu đồ doanh thu, sản phẩm bán chạy, khách hàng, lượt xem, đánh giá -->
            <div class="row mb-4">
                <div class="col-md-6">
                    <h5>Doanh thu theo ngày</h5>
                    <canvas id="revenueChart"></canvas>
                </div>

                <div class="col-md-6">
                    <h5>Sản phẩm bán chạy</h5>
                    <canvas id="topProductsChart"></canvas>
                </div>
            </div>

            <div class="row mb-4">
                <div class="col-md-4">
                    <h5>Phân tích khách hàng</h5>
                    <canvas id="customerChart"></canvas>
                </div>

                <div class="col-md-4">
                    <h5>Lượt xem sản phẩm</h5>
                    <canvas id="viewChart"></canvas>
                </div>

                <div class="col-md-4">
                    <h5>Đánh giá sản phẩm</h5>
                    <canvas id="reviewChart"></canvas>
                </div>
            </div>

            <!-- Danh sách đơn hàng gần đây -->
            <h3>Đơn hàng gần đây</h3>
            <table class="table table-bordered table-striped">
                <thead>
                    <tr>
                        <th>Mã đơn hàng</th>
                        <th>Email</th>
                        <th>Tổng tiền</th>
                        <th>Trạng thái</th>
                        <th>Ngày đặt hàng</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody id="orderTableBody">
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td>${order.order_id}</td>
                            <td>${order.email}</td>
                            <td>${order.total_price}</td>
                            <td>${order.status}</td>
                            <td>${order.order_date}</td>
                            <td><a href="orderDetail?id=${order.order_id}" class="btn btn-info">Chi tiết</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <script>
            const revenueLabels = [<c:forEach var="label" items="${revenueData.labels}">
            '${label}'<c:if test="${not empty label}">,</c:if>
            </c:forEach>
];

            const revenueData = [<c:forEach var="value" items="${revenueData.values}">
                ${value}<c:if test="${not empty value}">,</c:if>
            </c:forEach>
];

            const productNames = [<c:forEach var="name" items="${topProducts.names}">
            '${name}'<c:if test="${not empty name}">,</c:if>
            </c:forEach>
];

            const productCounts = [<c:forEach var="count" items="${topProducts.counts}">
                ${count}<c:if test="${not empty count}">,</c:if>
            </c:forEach>
];

            const customerTypes = [<c:forEach var="type" items="${customerStats.types}">
            '${type}'<c:if test="${not empty type}">,</c:if>
            </c:forEach>
];

            const customerCounts = [<c:forEach var="count" items="${customerStats.counts}">
                ${count}<c:if test="${not empty count}">,</c:if>
            </c:forEach>
];

            const viewLabels = [<c:forEach var="label" items="${viewStats.labels}">
            '${label}'<c:if test="${not empty label}">,</c:if>
            </c:forEach>
];

            const viewCounts = [<c:forEach var="count" items="${viewStats.counts}">
                ${count}<c:if test="${not empty count}">,</c:if>
            </c:forEach>
];

            const reviewLabels = [<c:forEach var="label" items="${reviewStats.labels}">
            '${label}'<c:if test="${not empty label}">,</c:if>
            </c:forEach>
];

            const reviewCounts = [<c:forEach var="count" items="${reviewStats.counts}">
                ${count}<c:if test="${not empty count}">,</c:if>
            </c:forEach>
];

            new Chart(document.getElementById('revenueChart'), {
                type: 'line',
                data: {
                    labels: revenueLabels,
                    datasets: [{
                            label: 'Doanh thu (VND)',
                            data: revenueData,
                            borderColor: 'rgb(75, 192, 192)',
                            tension: 0.3
                        }]
                }
            });

            new Chart(document.getElementById('topProductsChart'), {
                type: 'bar',
                data: {
                    labels: productNames,
                    datasets: [{
                            label: 'Số lượng bán',
                            data: productCounts,
                            backgroundColor: 'rgb(255, 99, 132)'
                        }]
                }
            });

            new Chart(document.getElementById('customerChart'), {
                type: 'pie',
                data: {
                    labels: customerTypes,
                    datasets: [{
                            label: 'Phân tích khách hàng',
                            data: customerCounts,
                            backgroundColor: ['#4caf50', '#2196f3', '#ff9800']
                        }]
                }
            });

            new Chart(document.getElementById('viewChart'), {
                type: 'bar',
                data: {
                    labels: viewLabels,
                    datasets: [{
                            label: 'Lượt xem',
                            data: viewCounts,
                            backgroundColor: 'rgb(153, 102, 255)'
                        }]
                }
            });

            new Chart(document.getElementById('reviewChart'), {
                type: 'doughnut',
                data: {
                    labels: reviewLabels,
                    datasets: [{
                            label: 'Đánh giá sản phẩm',
                            data: reviewCounts,
                            backgroundColor: ['#4caf50', '#8bc34a', '#ffeb3b', '#ffc107', '#f44336']
                        }]
                }
            });
        </script>

    </body>
</html>
