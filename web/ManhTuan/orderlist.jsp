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

        <a href="ManhTuan/customeradd.jsp" class="btn btn-primary">Thêm khách hàng</a>
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
                        <td>${order.order_id}</td>
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
            <!-- Nút Trước -->
            <c:if test="${index > 1}">
                <c:if test="${check eq 'list'}">
                    <a class="page-link" href="customerlist?index=${index - 1}">Trước</a>
                </c:if>
                <c:if test="${check eq 'search'}">
                    <a class="page-link" href="customersearch?index=${index - 1}&userName=${param.userName}&email=${param.email}&phone=${param.phone}&status=${param.status}">
                        Trước
                    </a>
                </c:if>
            </c:if>

            <!-- Các số trang -->
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

            <!-- Nút Sau -->
            <c:if test="${index < end}">
                <c:if test="${check eq 'list'}">
                    <a class="page-link" href="customerlist?index=${index + 1}">Sau</a>
                </c:if>
                <c:if test="${check eq 'search'}">
                    <a class="page-link" href="customersearch?index=${index + 1}&userName=${param.userName}&email=${param.email}&phone=${param.phone}&status=${param.status}">
                        Sau
                    </a>
                </c:if>
            </c:if>
        </div>


    </body>
</html>
