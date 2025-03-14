<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>


<html lang="en">

    <head>
        <meta charset="utf-8">
        <title>ADMIN SITE RUNNERSHOP</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="" name="keywords">
        <meta content="" name="description">

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

        <!-- Template Stylesheet -->
        <link href="admin/css/style.css" rel="stylesheet">
                <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
               
    </head>

    <body>
        
            <!-- Sidebar End -->


            <!-- Content Start -->
            <div class="content">
                
                <!-- Form Start -->
                <div class="container pt-4 px-4">
                    <!--chart doanh thu-->
                    <div>Doanh thu</div><br>
                    <div class="row g-4">
                        <div>
                            <form id="rangeOptions" method="GET" action="">
                                <input id="rangeWeek" type="radio" name="range" value="1W" checked onclick="this.form.submit()"/>
                                <label for="rangeWeek">1 Tuần </label>
                                
                                <input id="rangeMonth" type="radio" name="range" value="1M" onclick="this.form.submit()"/>
                                <label for="rangeMonth">1 Tháng </label>
                                
                                <input id="rangeYear" type="radio" name="range" value="1Y" onclick="this.form.submit()"/>
                                <label for="rangeYear">1 Năm </label>
                            </form>
                            <canvas id="revenueChart"></canvas>
                        </div>
                    </div>
                    <!--chart top bán ch?y-->
                    <br>
                    <br><!-- <br> -->
                    <br><!-- comment -->
                    <div>Top 10 bán chạy</div>
                    <div class="row g-4">
                        <div>
                            <canvas id="bestSellersChart"></canvas>
                        </div>
                    </div>
                    <br><!-- comment -->
                    <br><!-- comment -->
                    <br><!-- comment -->
                    <div>Top 10 Favorites</div>
                    <!--chart top rating (thang)-->
                    <div class="row g-4">
                        <div>
                            <canvas id="topRatingChart"></canvas>
                        </div>
                    </div>
                </div>
                <!-- Form End -->

               
            </div>
            <!-- Content End -->


            <!-- Back to Top -->
            <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>
        </div>

        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="admin/lib/chart/chart.min.js"></script>
        <script src="admin/lib/easing/easing.min.js"></script>
        <script src="admin/lib/waypoints/waypoints.min.js"></script>
        <script src="admin/lib/owlcarousel/owl.carousel.min.js"></script>
        <script src="admin/lib/tempusdominus/js/moment.min.js"></script>
        <script src="admin/lib/tempusdominus/js/moment-timezone.min.js"></script>
        <script src="admin/lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

        <!-- Template Javascript -->
        <script src="admin/js/main.js"></script>
        
        <script>
            
            const rangeOptions = document.getElementById("rangeOptions").getElementsByTagName("input");
            const urlParams = new URLSearchParams(window.location.search);
            let matched = false;

            const selectedRangeValue = urlParams.get('range');
            for (const option of rangeOptions) {
                if (option.value === selectedRangeValue && !matched) {
                    option.checked = true;
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                rangeOptions[0].checked = true;
            }
            
            const revenueChart = document.getElementById('revenueChart');

            new Chart(revenueChart, {
                type: 'line',
                data: {
                    labels: [${revenueDateSet}].reverse(), // period / day / month here
                    datasets: [{
                            label: 'Doanh thu',
                            data: [${revenueSumSet}].reverse(), // data here
                            borderColor: 'rgb(75, 192, 192)',
                    }]
                }
            });
            
            const bestSellersChart = document.getElementById('bestSellersChart');
            
            new Chart(bestSellersChart, {
                type: 'bar',
                data: {
                    labels: [${bestSellersNameSet}].reverse(), // period / day / month here
                    datasets: [{
                            label: 'Số Lượng',
                            data: [${bestSellersQuantitySet}].reverse(), // sorted data here
                            backgroundColor: 'rgb(75, 192, 192)',
                        }]
                }
            });
            
            const topRatingChart = document.getElementById('topRatingChart');
            
            new Chart(topRatingChart, {
                type: 'bar',
                data: {
                    labels: [${bestRatingNameSet}].reverse(), // product name here
                    datasets: [{
                            label: 'Đánh giá trung bình',
                            data: [${bestRatingAvgSet}].reverse(), // sorted data here
                            backgroundColor: 'rgb(75, 192, 192)',
                        }]
                }
            });
        </script>
    </body>

</html>