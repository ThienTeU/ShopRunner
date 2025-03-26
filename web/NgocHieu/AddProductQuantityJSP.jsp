<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thêm Số Lượng Sản Phẩm</title>
        <script>
            function updateSizeOptions() {
                var category = document.getElementById("category").value;

                var allSizeGroups = document.querySelectorAll("[id^=size_group_]");
                allSizeGroups.forEach(function (span) {
                    span.style.display = "none";
                });

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

                for (var i = rangeStart; i <= rangeEnd; i++) {
                    var sizeGroup = document.getElementById("size_group_" + i);
                    if (sizeGroup) {
                        sizeGroup.style.display = "inline";
                    }
                }
            }
        </script>
    </head>
    <body style="font-family: Arial, sans-serif; background: #f8f9fa; margin: 0; padding: 20px;">

        <h1 style="text-align: center; font-size: 28px; color: #333;">Thêm Số Lượng Sản Phẩm</h1>

        <form action="${pageContext.request.contextPath}/AddProductQuantityServlet" method="POST" 
              style="width: 100%; max-width: 600px; margin: 20px auto; padding: 20px; background: #fff;
              border-radius: 8px; box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);">
            <table style="width: 100%;">
                <tbody>
                    <tr>
                        <td><label style="font-weight: bold;">Mã Giá Sản Phẩm:</label></td>
                        <td><input type="number" name="productprice_id" value="${productprice_id}" required 
                                   style="width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px;"></td>
                    </tr>                   
                    <tr>
                        <td><label style="font-weight: bold;">Danh Mục:</label></td>
                        <td>
                            <select id="category" name="category" onchange="updateSizeOptions()" 
                                    style="width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px;">
                                <option value="1" selected>Giày</option>
                                <option value="2">Quần áo</option>
                                <option value="3">Phụ kiện</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><label style="font-weight: bold;">Size:</label></td>
                        <td>
                            <c:forEach items="${sessionScope.listSize}" var="s" begin="1" end="20">
                                <span id="size_group_${s.size_id}" style="display: ${s.size_id <= 9 ? "inline" : "none"}; margin-right: 10px;">
                                    <input type="checkbox" name="size_id" value="${s.size_id}" id="size_${s.size_id}" 
                                           class="size-checkbox"> ${s.size}
                                </span>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td><label style="font-weight: bold;">Số Lượng:</label></td>
                        <td><input type="text" name="quantity" required 
                                   style="width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px;"></td>
                    </tr> 
                    <tr>
                        <td colspan="2" style="text-align: center; padding-top: 15px;">
                            <button type="submit" 
                                    onclick="showAddImage(event)"
                                    style="background-color: #28a745; color: white; padding: 10px 20px; border: none;
                                    border-radius: 4px; cursor: pointer; font-size: 16px;">
                                Thêm Số Lượng
                            </button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>
    </body>
</html>
