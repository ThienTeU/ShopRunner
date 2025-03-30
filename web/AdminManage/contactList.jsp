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
<div class="container-fluid pt-4 px-4">
    <div class="bg-secondary text-center rounded p-4">
        <div class="d-flex align-items-center justify-content-between mb-4">
            <h6 class="mb-0">Quản Lí Contact</h6>
        </div>              
        <!-- Form tìm kiếm và lọc -->
<form action="contactList" method="get" class="mb-4">
            <div class="row g-3">
                <div class="col-md-4">
                    <input type="text" name="keyword" class="form-control" placeholder="Nhập từ khóa tìm kiếm..." value="${param.keyword}">
                </div>
                <div class="col-md-4">
                    <select name="statusFilter" class="form-select">
                        <option value="">Tất cả trạng thái</option>
                        <option value="true" ${param.statusFilter == 'true' ? 'selected' : ''}>Đã hoàn thành</option>
                        <option value="false" ${param.statusFilter == 'false' ? 'selected' : ''}>Yêu cầu mới</option>
                    </select>
                </div>
                <div class="col-md-4">
                    <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                </div>
            </div>
        </form>
        <div class="table-responsive">
            <table class="table table-striped table-hover align-middle">
                <thead class="thead-dark">
                    <tr class="text-white bg-primary">
                        <th class="text-center">ID</th>
                        <th>Họ và tên</th>
                        <th>Số điện thoại</th>
                        <th>Email</th>
                        <th>Nội dung</th>
                        <th>Ngày tạo</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="contact" items="${contactList}">
                        <tr>
                            <td class="text-center">${contact.contactId}</td>
                            <td>${contact.fullName}</td>
                            <td>${contact.phone}</td>
                            <td>${contact.email}</td>
                            <td>${contact.content}</td>
                            <td>${contact.createdAt}</td>
                            <td>
                                <!-- Kiểm tra trạng thái -->
                                <c:choose>
                                    <c:when test="${contact.status}">
                                        <span class="badge bg-success" style="color: green">Đã hoàn thành</span>
                                    </c:when>
                                    <c:otherwise>
                                        <form action="updateContactStatus" method="post" style="display:inline;">
                                            <input type="hidden" name="id" value="${contact.contactId}">
                                            <button type="submit" name="status" value="true" class="btn btn-sm btn-primary">
                                                ✔
                                            </button>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="contactDetail?id=${contact.contactId}" class="btn btn-sm btn-info">Xem chi tiết</a>
                                <form action="deleteContact" method="post" style="display:inline;">
                                    <input type="hidden" name="id" value="${contact.contactId}">
                                    <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Bạn có chắc chắn muốn xóa liên hệ này?');">Xóa</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
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

        <!-- Template Javascript -->
        <script src="${pageContext.request.contextPath}/AdminManage/admin/js/main.js"></script>
    </body>


</html>