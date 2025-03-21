<%-- 
    Document   : addressdetail
    Created on : Mar 3, 2025, 9:19:56 AM
    Author     : tuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <title>JSP Page</title>
    </head>
    <body>
        <a href="customerlist" class="btn btn-secondary">Quay lại</a>
        <a href="customeraddressadd?id=${id}" class="btn btn-warning btn-sm">Thêm</a>
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Tên</th>
                    <th>Số điện thoại</th>
                    <th>Tỉnh/Thành phố</th>
                    <th>Quận/Huyện</th>
                    <th>Phường/Xã</th>
                    <th>Đường</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="address" items="${addresses}">
                <tr>
                    <td>${address.addressId}</td>
                    <td>${address.name}</td>
                    <td>${address.phone}</td>
                    <td>${address.city}</td>
                    <td>${address.district}</td>
                    <td>${address.ward}</td>
                    <td>${address.street}</td>
                    <td>
                        <a href="customeraddressedit?id=${address.addressId}" class="btn btn-warning btn-sm">Sửa</a>
                        <a href="customeraddresschangestatus?id=${address.addressId}" class="btn btn-warning btn-sm">Ẩn</a>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
    </table>

</body>
</html>
