<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thêm Sản Phẩm Thành Công</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }

            .container {
                background-color: #fff;
                padding: 40px 30px;
                border-radius: 12px;
                box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.1);
                text-align: center;
                max-width: 500px;
                width: 100%;
            }

            h1 {
                color: #28a745;
                font-size: 32px;
                margin-bottom: 20px;
            }

            p {
                color: #333;
                font-size: 18px;
                margin-bottom: 30px;
            }

            /* CSS cho thẻ a */
            a {
                display: inline-block;
                font-size: 16px;
                color: #007bff;
                text-decoration: none;
                font-weight: bold;
                padding: 10px 20px;
                border: 1px solid #007bff;
                border-radius: 5px;
                transition: background-color 0.3s, color 0.3s;
            }

            a:hover {
                background-color: #007bff;
                color: #fff;
            }
        </style>

    </head>
    <body>
        <div class="container">
            <h1>Thêm Sản Phẩm Thành Công!</h1>
            <p>Sản phẩm của bạn đã được thêm vào hệ thống thành công.</p>
            <a href="/RunnerShop/ProductDashboard">Quay lại danh sách sản phẩm</a>
        </div>
    </body>
</html>
