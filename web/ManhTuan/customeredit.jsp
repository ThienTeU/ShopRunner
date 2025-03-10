<%-- 
    Document   : customeredit
    Created on : Mar 7, 2025, 1:51:15 AM
    Author     : tuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="customeredit" method="post" class="row g-3">
            <div class="col-md-6">
                <label for="userId" class="form-label">User ID</label>
                <input type="text" class="form-control" id="userId" name="userId" value="${customer.userId}" readonly>
            </div>
            <div class="col-md-6">
                <label for="userName" class="form-label">Tên người dùng</label>
                <input type="text" class="form-control" id="userName" name="userName" value="${customer.userName}" required>
            </div>
            <div class="col-md-6">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email" value="${customer.email}" required>
            </div>
            <div class="col-md-6">
                <label for="phoneNumber" class="form-label">Số điện thoại</label>
                <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="${customer.phoneNumber}" required>
            </div>
            <div class="col-md-6">
                <label for="status" class="form-label">Trạng thái</label>
                <select class="form-select" id="status" name="status">
                    <option value="1" ${customer.status ? 'selected' : ''}>Hoạt động</option>
                    <option value="0" ${!customer.status ? 'selected' : ''}>Khóa</option>
                </select>
            </div>
            <div class="col-md-6">
                <label for="gender" class="form-label">Giới tính</label>
                <select class="form-select" id="gender" name="gender">
                    <option value="1">Nam</option>
                    <option value="2">Nữ</option>
                </select>
            </div>
            <div class="col-12">
                <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                <a href="customerlist" class="btn btn-secondary">Hủy</a>
            </div>
        </form>

    <c:if test="${not empty message}">
        <div class="alert alert-${alertType}" role="alert">
            ${message}
        </div>
    </c:if>

</body>
</html>
