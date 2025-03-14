<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Access Denied</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .error-container {
            text-align: center;
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
        }
        .error-code {
            font-size: 100px;
            font-weight: bold;
            color: #dc3545;
        }
        .error-message {
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 20px;
        }
        .btn-home {
            background: #000;
            color: white;
            padding: 10px 20px;
            border-radius: 5px;
            text-transform: uppercase;
            font-weight: bold;
            transition: 0.3s;
        }
        .btn-home:hover {
            background: #444;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-code">403</div>
        <div class="error-message">Access Denied</div>
        <p>You do not have permission to access this page.</p>
        <p>Please contact the administrator if you believe this is a mistake.</p>
        <a href="${pageContext.request.contextPath}/home" class="btn btn-home">Go to Homepage</a>
    </div>
</body>
</html>
