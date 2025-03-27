<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 style="text-align: center; font-size: 28px; color: #333;">Thêm Giá Sản Phẩm</h1>

<form action="${pageContext.request.contextPath}/AddProductPriceServlet" method="POST" 
      style="width: 100%; max-width: 600px; margin: 20px auto; padding: 20px; background: #f8f9fa;
      border-radius: 8px; box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);">
    <table style="width: 100%;">
        <tbody>
            <tr>
                <td><label style="font-weight: bold;">Mã Sản Phẩm:</label></td>
                <td><input type="number" name="product_id" value="${requestScope.product_id}" min="0" required 
                           style="width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px;"></td>
            </tr>                   
            <tr>
                <td><label style="font-weight: bold;">Màu Sắc:</label></td>
                <td>
                    <select id="color_id" name="color_id" required 
                            style="width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px;">
                        <c:forEach items="${sessionScope.listColor}" var="c">
                            <option value="${c.color_id}">${c.color}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label style="font-weight: bold;">Giá:</label></td>
                <td><input type="number" name="price" required min="0" max="10000000" 
                           style="width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px;"></td>
            </tr> 
            <tr>
                <td colspan="2" style="text-align: center; padding-top: 15px;">
                    <button type="submit" onclick="showAddProductQuantity(event)"
                            style="background-color: #28a745; color: white; padding: 10px 20px; border: none;
                            border-radius: 4px; cursor: pointer; font-size: 16px;">
                        Thêm Giá Sản Phẩm
                    </button>
                </td>
            </tr>
        </tbody>
    </table>
</form>
