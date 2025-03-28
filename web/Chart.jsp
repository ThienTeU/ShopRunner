<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>ADMIN SITE RUNNERSHOP</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    
    <!-- Favicon -->
    <link href="img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Roboto:wght@500;700&display=swap" rel="stylesheet">

    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="admin/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="admin/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

    <!-- Customized Bootstrap Stylesheet -->
    <link href="admin/css/bootstrap.min.css" rel="stylesheet">
    <link href="admin/css/style.css" rel="stylesheet">
    
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body>
    <div class="container-fluid position-relative d-flex p-0">
        <!-- Sidebar Start -->
        <%@include file="component/SideBarAdmin.jsp" %>
        <!-- Sidebar End -->

        <!-- Content Start -->
        <div class="content">
            <!-- Navbar Start -->
            <%@include file="component/navbarAdmin.jsp" %>
            <!-- Navbar End -->

            <!-- Bộ lọc -->
            <div class="container pt-4 px-4">
                <h3>Bộ lọc dữ liệu</h3>
                <form action="Charts" method="post" class="filter-section row mb-4">
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
            </div>

            <!-- Biểu đồ doanh thu -->
            <div class="container pt-4 px-4">
                <h3>Doanh thu</h3>
                <canvas id="revenueChart"></canvas>
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
            </script>
        </div>
        <!-- Content End -->
    </div>
</body>
</html>
