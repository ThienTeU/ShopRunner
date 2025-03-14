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
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label class="form-label" for="city">Tỉnh<span style="color: red">*</span></label>
                    <select name="city" class="form-control" id="city" required>
                        <option>Chọn Tỉnh</option>
                    </select>
                </div>
                <div class="form-group col-md-6">
                    <label class="form-label" for="district">Huyện/Quận<span style="color: red">*</span></label>
                    <select name="district" class="form-control" id="district" required>
                        <option>Chọn Huyện/Quận</option>
                    </select>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label class="form-label" for="ward">Xã/Phường<span style="color: red">*</span></label>
                    <select name="ward" class="form-control" id="ward" required>
                        <option>Chọn Xã/Phường</option>
                    </select>
                </div>
                <div class="form-group col-md-6">
                    <label class="form-label" for="streetAddress">Số Đường/Tên Đường<span style="color: red">*</span></label>
                    <div class="input-group" style="align-items: center">
                        <input name="street" class="form-control" id="streetAddress" placeholder="Nhập số đường/tên đường" 
                               type="text" required/>
                        <i id="streetValid" style="position: absolute; right: 10px; display: none" class="fas fa-check text-success"></i>
                    </div>
                    <span id="invalidStreet" style="color: red; font-size: 15px;display: none; margin-top: 5px" >Địa chỉ không hợp lệ. Vui lòng nhập một địa chỉ hợp lệ.</span>
                </div>
            </div>
            <div class="col-12">
                <button type="submit" class="btn btn-primary">Thêm địa chỉ</button>
                <a href="customerlist" class="btn btn-secondary">Hủy</a>
            </div>
        </form>
        <p>${msg != null ? msg :""}</p>
    </body>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>
    <script src="apiprovince.js"></script>
</html>
