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
                <tr>
                    <td>${address.addressId}</td>
                    <td>${address.name}</td>
                    <td>${address.phone}</td>
                    <td>${address.city}</td>
                    <td>${address.district}</td>
                    <td>${address.ward}</td>
                    <td>${address.street}</td>
                    <td>
                        <a href="editAddress?id=${address.addressId}" class="btn btn-warning btn-sm">Sửa</a>
                        <form action="deleteAddress" method="post" style="display:inline;">
                            <input type="hidden" name="id" value="${address.addressId}">
                        </form>
                    </td>
                </tr>
        </tbody>
    </table>

</body>
</html>
