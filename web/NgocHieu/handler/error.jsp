<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>404 - Page Not Found</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
            .bodyS{
                display: flex;
                align-items: center;
                justify-content: center;
                height: 600px;
            }
            .error-container {
                max-width: 600px;
                text-align: center;
                background: white;
                padding: 40px;
                border-radius: 10px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            }
            .error-icon {
                font-size: 80px;
                color: #dc3545;
            }
            .btn-home {
                background: #007bff;
                color: white;
                padding: 12px 20px;
                border-radius: 5px;
                font-weight: bold;
                transition: 0.3s;
                text-decoration: none;
            }
            .btn-home:hover {
                background: #0056b3;
            }
        </style>
    </head>
    <body>
        <%@ include file="/model/header.jsp" %>
        <div class="bodyS">
            <div class="error-container">
            <div class="error-icon">🚫</div>
            <h1 class="fw-bold text-danger">404 - Page Not Found</h1>
            <p class="text-muted">
                Xin lỗi! Trang bạn đang tìm kiếm không tồn tại hoặc đã bị xóa.  
                Vui lòng kiểm tra lại đường dẫn hoặc quay về trang chủ.
            </p>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-home">🏠 Quay về Trang Chủ</a>
        </div>
        </div>
    </body>
</html>
