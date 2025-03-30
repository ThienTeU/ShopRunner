<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Lỗi - Không có Voucher</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container text-center mt-5">
        <div class="alert alert-danger">
            <h2>⚠️ Không có Voucher hợp lệ</h2>
            <p><%= request.getAttribute("errorMessage") %></p>
            <a href="sendVoucher.jsp" class="btn btn-primary">Thử lại</a>
        </div>
    </div>
</body>
</html>
