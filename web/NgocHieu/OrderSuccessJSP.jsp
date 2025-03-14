<%-- 
    Document   : OrderSuccessJSP
    Created on : Feb 21, 2025, 10:00:50 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Order Confirmation</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
            body {
                background-color: #f8f9fa;
            }
            .confirmation-container {
                max-width: 500px;
                margin: 100px auto;
                background: white;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
                text-align: center;
            }
            .status-box {
                background: #f4f1eb;
                padding: 12px;
                border-radius: 5px;
                font-weight: bold;
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
    <%
        Integer orderId = (Integer) session.getAttribute("order_id");
//        if (orderId == null) {
//            response.sendRedirect("handler/error.jsp");
//            return;
//        }
        session.removeAttribute("order_id");
        session.removeAttribute("order");
        session.removeAttribute("orderGhtk");
    %>
    <body>
        <%@ include file="/model/header.jsp" %>
        <div class="confirmation-container">
            <h2 class="fw-bold">THANK YOU</h2>
            <div class="status-box">
                Your order is being processed
            </div>
            <p class="mt-3"><strong>Order Number: #<%=orderId%></strong></p>
            <p>You’ll receive a confirmation email shortly. The order will appear in your account as soon as you’ve received the email.</p>
            <p class="fst-italic">While you wait on your delivery, you can always explore our website to find more gear.</p>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-dark">GO TO HOMEPAGE</a>
        </div>
    </body>
</html>

