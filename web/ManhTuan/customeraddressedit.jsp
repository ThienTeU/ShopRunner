<%-- 
    Document   : customeredit
    Created on : Mar 7, 2025, 1:51:15 AM
    Author     : tuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="DAL.ProductDAOTuan" %>
<%@ page import="Model.AddressTuan" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <title>JSP Page</title>
    </head>
    <body>
        <% 
            String id = request.getParameter("id");
            int customerId = Integer.parseInt(id);
            ProductDAOTuan dao = new ProductDAOTuan();
            AddressTuan address = dao.getCustomerAddressById(customerId);
            request.setAttribute("address", address);
        %>

        <form action="/RunnerShop/customeraddressedit" method="post" class="row g-3">
              <input type="hidden" name="customerId" value="${customerId}">
            <div class="col-md-6">
                <label for="id" class="form-label">ID</label>
                <input value="${address.addressId}" type="text" class="form-control" id="id" name="id" readonly>
            </div>
            <div class="col-md-6">
                <label for="name" class="form-label">Họ và Tên</label>
                <input value="${address.name}" type="text" class="form-control" id="name" name="name" required>
            </div>

            <div class="col-md-6">
                <label class="form-label" for="phone">Số điện thoại<span style="color: red">*</span></label>
                <input value="${address.phone}" type="text" class="form-control" id="phone" name="phone" required>
            </div>

            <div class="col-md-6">
                <label class="form-label" for="city">Tỉnh<span style="color: red">*</span></label>
                <select name="city" class="form-control" id="city" required>
                    <option>${address.city}</option>
                </select>
            </div>

            <div class="col-md-6">
                <label class="form-label" for="district">Huyện/Quận<span style="color: red">*</span></label>
                <select name="district" class="form-control" id="district" required>
                    <option>${address.district}</option>
                </select>
            </div>

            <div class="col-md-6">
                <label class="form-label" for="ward">Xã/Phường<span style="color: red">*</span></label>
                <select name="ward" class="form-control" id="ward" required>
                    <option>${address.ward}</option>
                </select>
            </div>

            <div class="col-md-6">
                <label class="form-label" for="streetAddress">Số Đường/Tên Đường<span style="color: red">*</span></label>
                <div class="input-group" style="align-items: center">
                    <input value="${address.street}" name="street" class="form-control" id="streetAddress"
                           placeholder="Nhập số đường/tên đường" type="text" required />
                    <i id="streetValid" style="position: absolute; right: 10px; display: none" class="fas fa-check text-success"></i>
                </div>
                <span id="invalidStreet" style="color: red; font-size: 15px; display: none; margin-top: 5px">
                    Địa chỉ không hợp lệ. Vui lòng nhập một địa chỉ hợp lệ.
                </span>
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
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>
    <script src="apiprovince.js"></script>
</body>

</html>
