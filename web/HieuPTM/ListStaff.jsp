<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="Header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Quản Lý Nhân Viên</title>
</head>
<body>
    <div class="container" style="margin-top: 150px">
        <h1 class="text-center mt-3 mb-3 text-primary">Danh sách nhân viên</h1>
        <table border="1" class="table table-sm formTable text-center">
            <thead>
                <tr>
                    <th>Tên đăng nhập</th>
                    <th>Họ và tên</th>
                    <th>Địa chỉ</th>
                    <th>Ngày sinh</th>
                    <th>Số điện thoại</th>
                    <th>Email</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${userlist}" var="o">
                    <tr>
                        <td>${o.userName}</td>
                        <td>${o.fullName}</td>
                        <td>${o.address}</td>
                        <td>${o.formatBirthDate()}</td>
                        <td>${o.phone}</td>
                        <td>${o.email}</td>
                        <td>
                            <button class="btn btn-success fw-bold" onclick="window.location.href='StaffManage?action=promote&target=${o.userName}'">Thêm Staff</button>
                            <button class="btn btn-danger fw-bold" onclick="if(confirm('Bạn chắc chắn muốn cấm?')) window.location.href='StaffManage?action=delete&target=${o.userName}'">Cấm</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>