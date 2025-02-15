<%-- 
    Document   : UploadImgSuccessJSP
    Created on : Feb 11, 2025, 6:54:55 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h5>${requestScope.notiAdd}</h5>
        <a href="AddProductServlet">Add tiếp</a>
        <a href="ProductDetailServlet?product_id=1">Xem sp</a>
    </body>
</html>
