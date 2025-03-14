<%-- 
    Document   : AddProductPriceJSP
    Created on : Feb 11, 2025, 9:58:57 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Add Product Price</h1>
        <form action="${pageContext.request.contextPath}/AddProductPriceServlet" method="POST">
            <table>
                <tbody>
                    <tr>
                        <td><label>Product Id:</label></td>
                        <td><input type="number" name="product_id" value="${requestScope.product_id}" min="0" required></td>
                    </tr>                   
                <tr>
                    <td><label>Color:</label></td>
                    <td>
                        <select name="color_id" required>
                            <c:forEach items="${sessionScope.listColor}" var="c">
                                <option value="${c.color_id}">${c.color}</option>
                            </c:forEach>
                    </td>
                </tr>
                <tr>
                        <td><label>Price:</label></td>
                        <td><input type="text" name="price" required min="0" max="10000000"></td>
                    </tr> 
                <tr><td><button type="submit" >Thêm Product Price</button></td></tr>
                </tbody>
            </table>
            <!-- comment -->


        </form>
    </body>
</html>
