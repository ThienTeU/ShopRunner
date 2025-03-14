<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng kí tài khoản</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="alert alert-success">
            <h4>Đăng kí tài khoản thành công!</h4>
            <p>Chào mừng bạn đến với Runner Gear Shop.</p>
            <a href="${pageContext.request.contextPath}/LoginControl" class="btn btn-primary">Đăng nhập</a>
        </div>
    </div>
</body>
</html>