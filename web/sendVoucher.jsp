<%-- 
    Document   : sendVoucher
    Created on : Mar 28, 2025, 12:21:06 PM
    Author     : Acer
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*, javax.servlet.*, javax.servlet.http.*" %>


<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nhận Voucher 10%</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .voucher-container {
            max-width: 400px;
            margin: 100px auto;
            padding: 30px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        .voucher-container h2 {
            color: #007bff;
            margin-bottom: 20px;
        }
        .voucher-container input {
            border-radius: 5px;
        }
        .voucher-container button {
            width: 100%;
            border-radius: 5px;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="voucher-container">
        <h2>Nhận Voucher Giảm 10%</h2>
        <p>Nhập email của bạn để nhận mã giảm giá ngay!</p>
        <form action="SendVoucherEmail" method="post">
            <div class="mb-3">
                <input type="email" class="form-control" name="email" required placeholder="Nhập email của bạn">
            </div>
            <button type="submit" class="btn btn-primary">Nhận Voucher</button>
        </form>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>


