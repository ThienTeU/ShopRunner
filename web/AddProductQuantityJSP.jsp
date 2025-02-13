<%-- 
    Document   : AddProductQuantityJSP
    Created on : Feb 11, 2025, 10:14:15 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Product Quantity</title>
    </head>
    <body>
        <h1>Add Product Quantity</h1>
        <form action="AddProductQuantityServlet" method="POST">
            <table>
                <tbody>
                    <tr>
                        <td><label>Product Price Id:</label></td>
                        <td><input type="text" name="productprice_id" value="${requestScope.productprice_id}" readonly></td>
                    </tr>                   
                    <tr>
                        <td><label>Size:</label></td>
                        <td>
                                <c:forEach items="${requestScope.listSize}" var="s">
                                    <input type="checkbox" name="size_id" value="${s.size_id}">${s.size}
                                </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td><label>Quantity:</label></td>
                        <td><input type="text" name="quantity" required></td>
                    </tr> 
                    <tr><td><button type="submit" >Thêm Product Quantity</button></td></tr>
                </tbody>
            </table>
            <!-- comment -->


        </form>
    </body>
</html>
