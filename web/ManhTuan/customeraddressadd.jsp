<%-- 
    Document   : customeraddressadd
    Created on : Mar 7, 2025, 7:19:56 AM
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
        <form action="customeraddressadd" method="post" class="row g-3">
            <div class="col-md-6">
                <label for="id" class="form-label">ID</label>
                <input type="text" class="form-control" id="id" name="id" value="${id}" readonly>
            </div>
            <div class="col-md-6">
                <label for="name" class="form-label">Họ và Tên</label>
                <input type="text" class="form-control" id="name" name="name" required>
            </div>
            <div class="col-md-6">
                <label for="phone" class="form-label">Số điện thoại</label>
                <input type="text" class="form-control" id="phone" name="phone" required>
            </div>
            <div class="col-md-6">
                <label for="city" class="form-label">Tỉnh/Thành phố</label>
                <input type="text" class="form-control" id="city" name="city" required>
            </div>
            <div class="col-md-6">
                <label for="district" class="form-label">Quận/Huyện</label>
                <input type="text" class="form-control" id="district" name="district" required>
            </div>
            <div class="col-md-6">
                <label for="ward" class="form-label">Phường/Xã</label>
                <input type="text" class="form-control" id="ward" name="ward" required>
            </div>
            <div class="col-md-6">
                <label for="street" class="form-label">Đường</label>
                <input type="text" class="form-control" id="street" name="street" required>
            </div>
            <div class="col-12">
                <button type="submit" class="btn btn-primary">Thêm địa chỉ</button>
                <a href="customerlist" class="btn btn-secondary">Hủy</a>
            </div>
        </form>
            <p>${msg != null ? msg :""}</p>
    </body>
</html>
