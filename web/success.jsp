<%-- 
    Document   : success.jsp
    Created on : Mar 27, 2025
    Author     : Admin
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Liên hệ thành công</title>
        <link rel="stylesheet" href="/assets/css/style.css"> <!-- Đường dẫn CSS -->
        <style>
            /* CSS bổ sung cho giao diện chuyên nghiệp */
            .success-page {
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                min-height: 70vh;
                text-align: center;
                background-color: #f9f9f9;
                padding: 20px;
            }
            .success-message h1 {
                font-size: 2.5rem;
                color: #4CAF50; /* Màu xanh lá biểu thị thành công */
                margin-bottom: 15px;
            }
            .success-message p {
                font-size: 1.2rem;
                color: #555;
                margin-bottom: 30px;
            }
            .btn-primary {
                display: inline-block;
                padding: 10px 20px;
                font-size: 1rem;
                color: #fff;
                background-color: #007BFF; /* Màu xanh dương chủ đạo */
                border: none;
                border-radius: 5px;
                text-decoration: none;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }
            .btn-primary:hover {
                background-color: #0056b3; /* Màu xanh dương đậm hơn khi hover */
            }
        </style>
    </head>
    <body>
        <!-- Header -->
        <%@ include file="/model/styles.jsp" %>
        <%@ include file="/model/header.jsp" %>
        
        <!-- Main Content -->
        <main class="success-page">
            <section class="success-message">
                <h1>Cảm ơn bạn!</h1>
                <p>Thông tin của bạn đã được gửi thành công. Chúng tôi sẽ liên hệ với bạn trong thời gian sớm nhất.</p>
                <a href="home" class="btn-primary">Quay lại trang chủ</a>
            </section>
        </main>

        <!-- Footer -->
        <%@ include file="/model/footer.jsp" %>
    </body>
</html>
