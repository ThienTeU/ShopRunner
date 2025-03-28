<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.OrderDetailAnh" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết đơn hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            color: #333;
        }
        h1 {
            text-align: center;
            color: #4CAF50;
            margin-top: 20px;
        }
        .table {
            margin: 20px auto;
            width: 90%;
            background-color: #fff;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        .table th {
            background-color: #4CAF50;
            color: white;
            text-align: center;
        }
        .table td {
            text-align: center;
            vertical-align: middle;
        }
        img {
            border-radius: 5px;
            max-width: 100px;
        }
        .message {
            text-align: center;
            margin: 20px auto;
            padding: 10px;
            width: 90%;
            background-color: #ffdddd;
            border: 1px solid #f5c6cb;
            color: #721c24;
            border-radius: 5px;
        }
        .back-to-profile {
            display: block;
            margin: 20px auto;
            text-align: center;
        }
        .back-to-profile a {
            text-decoration: none;
            color: #fff;
            background-color: #4CAF50;
            padding: 10px 20px;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }
        .back-to-profile a:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <h1>Lịch sử chi tiết đơn hàng</h1>

    <% 
        String message = (String) request.getAttribute("message");
        if (message != null) { 
    %>
        <div class="message"><%= message %></div>
    <% 
        } else { 
            List<OrderDetailAnh> orderDetails = (List<OrderDetailAnh>) request.getAttribute("orderDetails");
            if (orderDetails != null && !orderDetails.isEmpty()) { 
    %>
        <table class="table table-bordered table-hover">
            <thead>
                <tr>
                    <th>Mã đơn hàng</th>
                    <th>Mã sản phẩm</th>
                    <th>Tên sản phẩm</th>
                    <th>Hình ảnh</th>
                    <th>Số lượng</th>
                    <th>Đơn giá</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    for (OrderDetailAnh detail : orderDetails) { 
                %>
                    <tr>
                        <td><%= detail.getOrderId() %></td>
                        <td><%= detail.getProductId() %></td>
                        <td><%= detail.getProductName() %></td>
                        <td>
                            <img src="<%= detail.getImageUrl() %>" alt="Hình ảnh sản phẩm">
                        </td>
                        <td><%= detail.getQuantity() %></td>
                        <td><%= detail.getUnitPrice() %> đ</td>
                    </tr>
                <% 
                    } 
                %>
            </tbody>
        </table>
    <% 
            } else { 
    %>
        <div class="message">Không tìm thấy chi tiết đơn hàng nào.</div>
    <% 
            } 
        } 
    %>

    <div class="back-to-profile">
        <a href="profile">Quay lại hồ sơ cá nhân</a>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>