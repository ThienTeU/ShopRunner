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
        <script>
            function updateSizeOptions() {
                var category = document.getElementById("category").value;

                // Ẩn tất cả checkbox + số size
                var allSizeGroups = document.querySelectorAll("[id^=size_group_]");
                allSizeGroups.forEach(function (span) {
                    span.style.display = "none";
                });

                // Xác định phạm vi size theo danh mục sản phẩm
                var rangeStart, rangeEnd;
                if (category === "1") { // Giày
                    rangeStart = 1;
                    rangeEnd = 9;
                } else if (category === "2") { // Quần áo
                    rangeStart = 10;
                    rangeEnd = 15;
                } else if (category === "3") { // Phụ kiện
                    rangeStart = 10;
                    rangeEnd = 21;
                }

                // Hiển thị checkbox + số size phù hợp
                for (var i = rangeStart; i <= rangeEnd; i++) {
                    var sizeGroup = document.getElementById("size_group_" + i);
                    if (sizeGroup) {
                        sizeGroup.style.display = "inline"; // Hiển thị cả checkbox và số
                    }
                }
            }
        </script>
    </head>
    <body>
        <h1>Add Product Quantity</h1>
        <form action="${pageContext.request.contextPath}/AddProductQuantityServlet" method="POST">
            <table>
                <tbody>
                    <tr>
                        <td><label>Product Price Id:</label></td>
                        <td><input type="number" name="productprice_id" value="${productprice_id}" required></td>
                    </tr>                   
                    <tr>
                        <td>
                            <select id="category" name="category" onchange="updateSizeOptions()">
                                <option value="1" selected>Giày</option>
                                <option value="2">Quần áo</option>
                                <option value="3">Phụ kiện</option>
                            </select>
                        </td>
                        <td><label>Size:</label></td>
                        <td><c:forEach items="${sessionScope.listSize}" var="s" begin="1" end="20">
                                    <span id="size_group_${s.size_id}" style="display: ${s.size_id <= 9 ? "inline" : "none"};">
                                        <input type="checkbox" name="size_id" value="${s.size_id}" 
                                               id="size_${s.size_id}" class="size-checkbox"> ${s.size}
                                    </span>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td><label>Quantity:</label></td>
                        <td><input type="text" name="quantity" required></td>
                    </tr> 
                    <tr><td><button type="submit">Thêm Product Quantity</button></td></tr>
                </tbody>
            </table>
        </form>
    </body>
</html>


