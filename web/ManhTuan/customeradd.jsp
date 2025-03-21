<%-- 
    Document   : customeradd
    Created on : Mar 7, 2025, 6:38:06 AM
    Author     : tuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="/RunnerShop/customeradd" method="post" class="row g-3">
            <div class="col-md-6">
                <label for="userName" class="form-label">Tên người dùng</label>
                <input type="text" class="form-control" id="userName" name="userName" value="${customer.userName}" required>
            </div>
            <div class="col-md-6">
                <label for="fullName" class="form-label">Họ và tên</label>
                <input type="text" class="form-control" id="fullName" name="fullName" value="${customer.fullName}"  required>
            </div>
            <div class="col-md-6">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email" value="${customer.email}"  required>
            </div>
            <div class="col-md-6">
                <label for="phoneNumber" class="form-label">Số điện thoại</label>
                <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="${customer.phoneNumber}"  required>
            </div>
            <div class="col-md-6">
                <label for="status" class="form-label">Trạng thái</label>
                <select class="form-select" id="status" name="status">
                    <option value="1" ${customer.status ? "selected" : ""}>Hoạt động</option>
                    <option value="0" ${!customer.status ? "selected" : ""}>Khóa</option>
                </select>
            </div>

            <div class="col-md-6">
                <label for="gender" class="form-label">Giới tính</label>
                <select class="form-select" id="gender" name="gender">
                    <option value="1" ${customer.genderId == 1 ? "selected" : ""}>Nam</option>
                    <option value="2" ${customer.genderId == 2 ? "selected" : ""}>Nữ</option>
                </select>
            </div>

            <div class="col-12">
                <button type="submit" class="btn btn-primary">Thêm khách hàng</button>
                <a href="customerlist" class="btn btn-secondary">Hủy</a>
            </div>
        </form>

        <p>${error != null ? error :""}</p>
    </body>
</html>
