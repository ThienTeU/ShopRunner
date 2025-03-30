<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <title>ADMIN SITE</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="" name="keywords">
        <meta content="" name="description">

        <!-- Favicon -->
        <link href="${pageContext.request.contextPath}/AdminManage/img/favicon.ico" rel="icon">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Roboto:wght@500;700&display=swap" rel="stylesheet"> 

        <!-- Icon Font Stylesheet -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <!--Link bootstrap phan trang-->
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <!-- Libraries Stylesheet -->
        <link href="${pageContext.request.contextPath}/AdminManage/admin/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/AdminManage/admin/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

        <!-- Customized Bootstrap Stylesheet -->
        <link href="${pageContext.request.contextPath}/AdminManage/admin/css/bootstrap.min.css" rel="stylesheet">

        <!-- Template Stylesheet -->
        <link href="${pageContext.request.contextPath}/AdminManage/admin/css/style.css" rel="stylesheet">

<style>
    /* Nền tổng thể */
    body {
        background-color: #f4f7f9;
        font-family: 'Roboto', sans-serif;
        color: #333;
    }

    /* Tiêu đề chính */
    h1, h2, h3 {
        font-family: 'Roboto', sans-serif;
        font-weight: 700;
        color: #2c3e50;
    }

    /* Nâng cấp giao diện nút */
    .btn-back, .btn-primary {
        background: linear-gradient(135deg, #007bff, #0056b3);
        color: #fff;
        font-weight: bold;
        padding: 12px 20px;
        border-radius: 5px;
        text-transform: uppercase;
        transition: all 0.3s ease;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }

    .btn-back:hover, .btn-primary:hover {
        background: linear-gradient(135deg, #0056b3, #003f7d);
        box-shadow: 0 6px 8px rgba(0, 0, 0, 0.2);
    }

    /* Card nâng cấp */
    .contact-details {
        background: #fff;
        border: 1px solid #e0e0e0;
        border-radius: 10px;
        padding: 30px;
        margin-top: 20px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        transition: transform 0.3s ease, box-shadow 0.3s ease;
    }

    .contact-details:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 12px rgba(0, 0, 0, 0.2);
    }

    /* Dòng trạng thái */
    .status {
        font-weight: bold;
        padding: 5px 10px;
        border-radius: 5px;
    }

    .status-resolved {
        color: #fff;
        background-color: #28a745;
    }

    .status-pending {
        color: #fff;
        background-color: #dc3545;
    }

    /* Hiệu ứng hover cho liên kết */
    a {
        color: #007bff;
        text-decoration: none;
        transition: color 0.3s ease;
    }

    a:hover {
        color: #0056b3;
    }

    /* Footer */
    footer {
        background: #2c3e50;
        color: #fff;
        padding: 20px 0;
        text-align: center;
    }

    footer a {
        color: #fff;
        text-decoration: underline;
    }

    footer a:hover {
        color: #f4d03f;
    }
</style>

    </head>

    <body>
        <div class="container-fluid position-relative d-flex p-0">
            <!-- Spinner Start -->
            <div id="spinner" class="show bg-dark position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
                <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status">
                    <span class="sr-only">Loading...</span>
                </div>
            </div>
            <!-- Spinner End -->


            <!-- Sidebar Start -->
            <%@include file="component/SideBarAdmin.jsp" %>
            <!-- Sidebar End -->


            <!-- Content Start -->
            <div class="content">
                <!-- Navbar Start -->
                <%@include file="component/navbarAdmin.jsp" %>
                <!-- Navbar End -->
                <!-- Sale & Revenue End -->
                <!-- Recent Sales Start -->
                <div class="container my-4">
                    <div class="contact-details">
                        <h1>Chi tiết liên hệ</h1>
                        <p><strong>ID:</strong> ${contact.contactId}</p>
                        <p><strong>Tên:</strong> ${contact.fullName}</p>
                        <p><strong>Email:</strong> ${contact.email}</p>
                        <p><strong>Số điện thoại:</strong> ${contact.phone}</p>
                        <p><strong>Nội dung:</strong> ${contact.content}</p>
                        <p><strong>Ngày tạo:</strong> ${contact.createdAt}</p>
<p>
    <strong>Trạng thái đơn:</strong>
    <span class="status ${contact.status ? 'status-resolved' : 'status-pending'}">
        ${contact.status ? 'Đã giải quyết' : 'Chưa giải quyết'}
    </span>
</p>

                        <a href="contactList" class="btn-back">Quay lại danh sách liên hệ</a>
                    </div>
                </div>


                <!-- Recent Sales End -->



                <!-- Footer End -->
            </div>
            <!-- Content End -->


            <!-- Back to Top -->
            <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>
        </div>

        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/AdminManage/admin/lib/chart/chart.min.js"></script>
        <script src="${pageContext.request.contextPath}/AdminManage/admin/lib/easing/easing.min.js"></script>
        <script src="${pageContext.request.contextPath}/AdminManage/admin/lib/waypoints/waypoints.min.js"></script>
        <script src="${pageContext.request.contextPath}/AdminManage/admin/lib/owlcarousel/owl.carousel.min.js"></script>
        <script src="${pageContext.request.contextPath}/AdminManage/admin/lib/tempusdominus/js/moment.min.js"></script>
        <script src="${pageContext.request.contextPath}/AdminManage/admin/lib/tempusdominus/js/moment-timezone.min.js"></script>
        <script src="${pageContext.request.contextPath}/AdminManage/admin/lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>
<script>
    // Hiệu ứng cuộn mượt
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            document.querySelector(this.getAttribute('href')).scrollIntoView({
                behavior: 'smooth'
            });
        });
    });

    // Hiển thị nút quay lại đầu trang khi cuộn
    const backToTop = document.querySelector('.back-to-top');
    window.addEventListener('scroll', () => {
        if (window.scrollY > 300) {
            backToTop.classList.add('show');
        } else {
            backToTop.classList.remove('show');
        }
    });
</script>

        <!-- Template Javascript -->
        <script src="${pageContext.request.contextPath}/AdminManage/admin/js/main.js"></script>
    </body>


</html>