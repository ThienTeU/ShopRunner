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
                            <h6 class="mb-0">Products </h6>

                            <a href="AddProductServlet" class="btn btn-success">Add New Product <i class="bi bi-bag-plus-fill"></i></a>
                        </div>
                        <div class="d-flex justify-content-between mb-3">
                            <!-- Ô tìm kiếm -->
                            <div class="w-25">
                                <form action="ProductDashboard">
                                    <input type="text" id="searchInput" placeholder=" Tìm kiếm sản phẩm..." name="searchKey">
                                    <input type="text" hidden value="${sort == null ? "date" : sort}" name="sort">
                                    <button style="border: 1px solid gray; border-radius: 5px; color: gray" type="submit">Tìm</button>

                                </form>
                            </div>
                            <a href="ProductDashboard">Xem tất cả sản phẩm</a>
                            <div class="dropdown">
                                <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="dropdownMenuButton"
                                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    Sắp xếp theo: 
                                    <c:choose>
                                        <c:when test="${sortType == 'name'}">Tên</c:when>
                                        <c:when test="${sortType == 'date'}">Ngày tạo</c:when>
                                        <c:when test="${sortType == 'status'}">Trạng thái</c:when>
                                        <c:when test="${sortType == 'view'}">Lượt xem</c:when>
                                        <c:otherwise>Mặc định</c:otherwise>
                                    </c:choose>
                                </button>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                    <a class="dropdown-item" href="ProductDashboard?searchKey=${searchKey}&sort=name">Sắp xếp theo: Tên</a>
                                    <a class="dropdown-item" href="ProductDashboard?searchKey=${searchKey}&sort=date">Sắp xếp theo: Ngày tạo</a>
                                    <a class="dropdown-item" href="ProductDashboard?searchKey=${searchKey}&sort=status">Sắp xếp theo: Trạng thái</a>
                                    <a class="dropdown-item" href="ProductDashboard?searchKey=${searchKey}&sort=view">Sắp xếp theo: Lượt xem</a>
                                </div>
                            </div>

                        </div>

                        <div class="table-responsive">
                            <table class="table text-start align-middle table-bordered table-hover mb-0">
                                <thead>
                                    <tr class="text-white">
                                        <th scope="col">Name</th>
                                        <th scope="col">Thumbnail</th>
                                        <th scope="col">Date</th>
                                        <th scope="col">Color/Size/Quantity</th>
                                        <th scope="col">Status</th>
                                        <th scope="col">Action</th>
                                    </tr>
                                </thead>
                                <tbody style="font-size: 14px">
                                    <c:forEach items="${paginatedList}" var="product">
                                        <tr>
                                            <td style="max-width: 200px"><a href="ProductDetailServlet?product_id=${product.product_id}">${product.product_name}</a></td>
                                            <td>
                                                <div style="position: relative">
                                                    <div style="padding: 0 5px;background-color: #cff4fc;color: black;
                                                         position: absolute; right: 0px; font-size: 13px; margin: 0 5px 0 0;
                                                         font-weight: 600; border-bottom-left-radius:5px;border-bottom-right-radius:5px" class="view">
                                                        <i class="fa-regular fa-eye"></i>
                                                        <span>${product.getView()}</span>
                                                    </div>
                                                    <img src="${product.thumbnail}" width="100px" height="100px" alt="alt"/>
                                                </div>

                                            </td>
                                            <td>${product.created_at}</td>
                                            <td style="line-height: 0;align-items: center; font-size: 13px;">
                                                <c:forEach items="${product.getProductPricesByProductId()}" var="pp">
                                                    <form style="display:flex; align-items: center; margin-bottom: 10px" action="UpdateQuantityServlet">
                                                        <c:forEach items="${pp.getAllColor()}" var="c">
                                                            <c:if test="${c.color_id == pp.color_id}">
                                                                <div style="border: 1px solid gray; padding: 3px; border-radius: 8px; display: flex; align-items: center; width: fit-content;">
                                                                    <a style="text-decoration: none; display: flex; align-items: center; gap: 5px;" 
                                                                       title="Sửa giá sản phẩm" href="#" onclick="editPrice(event, this)">
                                                                        <input type="hidden" name="productPriceId" value="${pp.productPrice_id}">
                                                                        <button style="display: flex; background-color: ${c.color}; border-radius: 50%;
                                                                                height: 30px; width: 30px; border: solid 1px gray; cursor: pointer;">
                                                                        </button>
                                                                        <div class="productPrice" style="color: red; font-weight: bold; font-size: 14px;">
                                                                            ${pp.price} VND
                                                                        </div>
                                                                    </a>
                                                                </div>

                                                            </c:if>
                                                        </c:forEach>

                                                        <select name="productQuantityId" style="height: 30px; margin: 0 10px 0 10px">
                                                            <c:forEach items="${pp.getAllProductQuantitesById()}" var="pq">
                                                                <option value="${pq.productQuantity_id}">
                                                                    <c:forEach items="${pq.getAllSize()}" var="s">
                                                                        <c:if test="${s.size_id == pq.size_id}">
                                                                            ${s.size}
                                                                        </c:if>
                                                                    </c:forEach>
                                                                    / ${pq.quantity}
                                                                </option>
                                                            </c:forEach>
                                                        </select>

                                                        <button style="font-size: 13px; height: 30px;" type="button" class="btn btn-outline-secondary" 
                                                                onclick="editQuantity(event, this)">
                                                            Edit Quantity <i class="bi bi-pencil-square"></i> 
                                                        </button>

                                                    </form>
                                                    <br>
                                                </c:forEach>
                                            </td>
                                            <td>
                                                <c:if test="${product.status == false}">
                                                    Active <i style="color: green" class="bi bi-check-circle-fill"></i>
                                                </c:if>
                                                <c:if test="${product.status == true}">
                                                    Inactive <i style="color:red" class="bi bi-x-circle-fill"></i>
                                                </c:if>
                                            </td>
                                            <td >
                                                <a style="font-size: 13px; height: 30px;" class="btn btn-light" href="UpdateStatusServlet?product_id=${product.product_id}&status=${product.status}" 
                                                   onclick="updateStatus(event, '${product.product_id}', '${product.status}')"> 
                                                    Change Status <i class="bi bi-circle"></i>
                                                </a><br>
                                                <a style="font-size: 13px; height: 30px" href="AddProductPriceServlet?product_id=${product.product_id}"
                                                   class="btn mt-1 btn-primary">
                                                    Add new color <i class="bi bi-plus-circle"></i>
                                                </a>
                                                <a style="font-size: 13px; height: 30px" href="EditProductServlet?product_id=${product.product_id}" class="btn mt-1 btn-outline-danger">Edit product</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <div class="text-center mt-3">
                                <nav aria-label="Page navigation">
                                    <ul class="pagination justify-content-center">
                                        <c:if test="${currentPage > 1}">
                                            <li class="page-item">
                                                <a class="page-link"
                                                   href="ProductDashboard?searchKey=${searchKey}&sort=${sortType}&page=${currentPage - 1}">
                                                    Previous
                                                </a>
                                            </li>
                                        </c:if>

                                        <c:forEach begin="1" end="${totalPages}" var="i">
                                            <li class="page-item">
                                                <a class="page-link"
                                                   href="ProductDashboard?searchKey=${searchKey}&sort=${sortType}&page=${i}"
                                                   style="${i == currentPage ? 'color: black;' : ''}">
                                                    ${i}
                                                </a>
                                            </li>
                                        </c:forEach>

                                        <c:if test="${currentPage < totalPages}">
                                            <li class="page-item">
                                                <a class="page-link"
                                                   href="ProductDashboard?searchKey=${searchKey}&sort=${sortType}&page=${currentPage + 1}">
                                                    Next
                                                </a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </nav>
                            </div>
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

    <script>

                                                       function editQuantity(event, button) {
                                                           event.preventDefault();

                                                           let form = button.closest("form"); // Lấy form cha của button
                                                           let selectedOption = form.querySelector("select[name='productQuantityId']").value; // Lấy productQuantityId từ select

                                                           if (!selectedOption) {
                                                               alert("Please select size.");
                                                               return;
                                                           }

                                                           let newQuantity = prompt("Nhập số lượng mới:");
                                                           if (newQuantity !== null && newQuantity !== "") {
                                                               window.location.href = "UpdateQuantityServlet?productQuantityId=" + selectedOption + "&newQuantity=" + newQuantity;
                                                           }
                                                       }

                                                       function editPrice(event, button) {
                                                           event.preventDefault();

                                                           let form = button.closest("form"); // Lấy form cha của button
                                                           let productPriceId = form.querySelector("input[name='productPriceId']").value;


                                                           let newPrice = prompt("Nhập giá tiền mới:");
                                                           if (newPrice !== null && newPrice !== "") {
                                                               window.location.href = "UpdatePriceServlet?productPriceId=" + productPriceId + "&newPrice=" + newPrice;
                                                           }
                                                       }


                                                       function updateStatus(event, productId, status) {
                                                           event.preventDefault();//Ngan chan hanh dong mac dinh cua the a

                                                           let confirmation = window.confirm("Bạn có muốn cập nhật lại trạng thái sản phẩm không?");
                                                           if (confirmation) {
                                                               window.location.href = "UpdateStatusServlet?product_id=" + productId + "&status=" + status;
                                                           }
                                                       }
                                                       document.addEventListener("DOMContentLoaded", function () {
                                                           let priceElements = document.querySelectorAll(".productPrice");
                                                           priceElements.forEach(function (element) {
                                                               let price = parseFloat(element.textContent);
                                                               if (!isNaN(price)) {
                                                                   element.textContent = price.toLocaleString("vi-VN") + "₫";
                                                               }
                                                           });
                                                       });
    </script>
</html>