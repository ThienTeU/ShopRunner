<%-- 
    Document   : customerlist
    Created on : Feb 28, 2025, 9:29:18 AM
    Author     : tuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Quản lý khách hàng</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="ManhTuan/customerlist.css"/>
    </head>
    <body>
        <%@ include file="/model/header.jsp" %>

        <div style="margin-top: 100px">
            <form action= "customersearch" method="post" class="row g-3" id="userSearchForm">
                <div class="col-md-3">
                    <label for="userName" class="form-label">Tên</label>
                    <input type="text" class="form-control" id="userName" name="userName">
                </div>
                <div class="col-md-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" name="email">
                </div>
                <div class="col-md-2">
                    <label for="phone" class="form-label">Điện thoại</label>
                    <input type="text" class="form-control" id="phone" name="phone">
                </div>
                <div class="col-md-2">
                    <label for="status" class="form-label">Trạng thái</label>
                    <select class="form-select" id="status" name="status">
                        <option value="">Tất cả</option>
                        <option value="1">Hoạt động</option>
                        <option value="0">Khóa</option>
                    </select>
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                    <button type="reset" class="btn btn-secondary">Xóa bộ lọc</button>
                </div>
            </form>
            <a href="ManhTuan/customeradd.jsp" class="btn btn-primary">Thêm khách hàng</a>
            <table>
                <tr>
                    <th>ID</th>
                    <th>Tên</th>
                    <th>Email</th>
                    <th>Điện thoại</th>
                    <th>Giới tính</th>
                    <th>Trạng thái</th>
                    <th>Số đơn đã đặt</th>
                    <th>Tổng tiền</th>
                    <th>Lựa chọn</th>
                </tr>

                <c:forEach var="customer" items="${customers}">
                    <tr>
                        <td>${customer.userId}</td>
                        <td>
                            <a href="customeraddressdetail?id=${customer.userId}" class="text-decoration-none">
                                ${customer.userName}
                            </a>
                        </td>
                        <td>${customer.email}</td>
                        <td>${customer.phoneNumber}</td>
                        <td>
                            <c:choose>
                                <c:when test="${customer.genderId == 1}">Nam</c:when>
                                <c:when test="${customer.genderId == 2}">Nữ</c:when>
                                <c:otherwise>Khác</c:otherwise>
                            </c:choose>
                        </td>
                        <td>${customer.status ? "Hoạt động" : "Bị khóa"}</td>

                        <!-- Hiển thị số đơn hàng đã đặt -->
                        <td>
                            <c:set var="found" value="false" />
                            <c:forEach var="order" items="${orders}">
                                <c:if test="${order.userId == customer.userId}">
                                    ${order.totalOrder}
                                    <c:set var="found" value="true" />
                                </c:if>
                            </c:forEach>
                            <c:if test="${!found}">0</c:if>
                            </td>

                            <!-- Hiển thị tổng tiền -->
                            <td>
                            <c:set var="foundPrice" value="false" />
                            <c:forEach var="order" items="${orders}">
                                <c:if test="${order.userId == customer.userId}">
                                    ${order.totalPrice}
                                    <c:set var="foundPrice" value="true" />
                                </c:if>
                            </c:forEach>
                            <c:if test="${!foundPrice}">0</c:if>
                            </td>

                            <!-- Các lựa chọn: Sửa, Khóa/Mở khóa -->
                            <td>
                                <a href="customeredit?id=${customer.userId}">
                                <button class="edit-btn">Sửa</button>
                            </a>
                            <form action="changeStatus" method="post" style="display:inline;">
                                <input type="hidden" name="userId" value="${customer.userId}">
                                <button type="submit" class="status-btn">
                                    ${customer.status ? "Khóa" : "Mở khóa"}
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <div class="pagination justify-content-center">

                <c:forEach begin="1" end="${end}" var="i">
                    <c:if test="${check eq 'list'}">
                        <a class="page-link ${i eq index ? 'active' : ''}" href="customerlist?index=${i}">${i}</a>
                    </c:if>
                    <c:if test="${check eq 'search'}">
                        <a class="page-link ${i eq index ? 'active' : ''}" href="customersearch?index=${i}&userName=${param.userName}&email=${param.email}&phone=${param.phone}&status=${param.status}">
                            ${i}
                        </a>
                    </c:if>
                </c:forEach>

            </div>
        </div>

        <%@ include file="/model/footer.jsp" %>

    </body>
</html>
