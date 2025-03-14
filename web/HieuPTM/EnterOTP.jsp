<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Nhập mã OTP</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <style>
        body {
            background: linear-gradient(135deg, #f5f7fa, #c3cfe2);
            font-family: 'Roboto', sans-serif;
        }
        .otp-container {
            max-width: 400px;
            background: #fff;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            margin-top: 50px;
        }
        .btn-custom {
            background-color: #007bff;
            border-color: #007bff;
            color: #fff;
            transition: all 0.3s;
        }
        .btn-custom:hover {
            background-color: #0056b3;
            border-color: #004085;
        }
    </style>
</head>
<body>
    <div class="container d-flex justify-content-center align-items-center vh-100">
        <div class="otp-container text-center">
            <h3><i class="fa-solid fa-lock fa-3x text-primary"></i></h3>
            <h2 class="mb-3">Nhập mã OTP</h2>
            <% if(request.getAttribute("message") != null) { %>
                <p class="text-danger"><%= request.getAttribute("message") %></p>
            <% } %>
            <form action="ValidateOTP" method="post">
                <div class="mb-3 text-start">
                    <label for="otp" class="form-label">Mã OTP:</label>
                    <input type="text" class="form-control" id="otp" name="otp" placeholder="Nhập mã OTP" required>
                </div>
                <button type="submit" class="btn btn-custom w-100">Xác nhận</button>
                <input type="hidden" name="token" id="token" value="">
            </form>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>