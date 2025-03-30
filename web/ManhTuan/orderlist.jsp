<%-- 
    Document   : orderlist
    Created on : Mar 25, 2025, 6:07:48 PM
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
            <form action="ordersearch" method="post" class="row g-3" id="orderSearchForm">
                <div class="col-md-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" name="email">
                </div>
                <div class="col-md-3">
                    <label for="orderDate" class="form-label">Sắp xếp ngày đặt hàng</label>
                    <select class="form-select" id="orderDate" name="orderDate">
                        <option value="">Mặc định</option>
                        <option value="desc">Mới nhất</option>
                        <option value="asc">Cũ nhất</option>
                    </select>
                </div>

                <div class="col-md-2">
                    <label for="status" class="form-label">Trạng thái</label>
                    <select class="form-select" id="status" name="status">
                        <option value="">Tất cả</option>
                        <option value="Pending">Chờ xử lý</option>
                        <option value="paid">Đã thanh toán</option>
                        <option value="Canceled">Đã hủy</option>
                    </select>
                </div>

                <div class="col-md-2">
                    <label for="paymentMethod" class="form-label">Thanh toán</label>
                    <select class="form-select" id="paymentMethod" name="paymentMethod">
                        <option value="">Tất cả</option>
                        <option value="cod">COD</option>
                        <option value="vnpay">VNPAY</option>
                    </select>
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                    <button type="reset" class="btn btn-secondary">Xóa bộ lọc</button>
                </div>
            </form>


            <table border="1">
                <thead>
                    <tr>
                        <th>Mã đơn hàng</th>
                        <th>Email</th>
                        <th>Ngày đặt</th>
                        <th>Tổng tiền</th>
                        <th>Trạng thái</th>
                        <th>Số điện thoại</th>
                        <th>Phương thức thanh toán</th>
                        <th>Địa chỉ giao hàng</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td>
                                <a href="orderdetail?id=${order.order_id}">${order.order_id}</a>
                            </td>

                            <td>${order.email}</td>
                            <td>${order.order_date}</td>
                            <td>${order.total_price}</td>
                            <td>${order.status}</td>
                            <td>${order.phone}</td>
                            <td>${order.payment_method}</td>
                            <td>${order.shipping_address}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="pagination justify-content-center">
                <c:forEach begin="1" end="${end}" var="i">
                    <c:if test="${check eq 'list'}">
                        <a class="page-link ${i eq index ? 'active' : ''}" href="orderlist?index=${i}">${i}</a>
                    </c:if>
                    <c:if test="${check eq 'search'}">
                        <a class="page-link ${i eq index ? 'active' : ''}" href="ordersearch?index=${i}&email=${param.email}&orderDate=${param.orderDate}&status=${param.status}&paymentMethod=${param.paymentMethod}">
                            ${i}
                        </a>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <%@ include file="/model/footer.jsp" %>
    </body>
</html>
