<%-- 
    Document   : productlist
    Created on : Feb 11, 2025, 5:33:39 AM
    Author     : tuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="Model.CategoryTuan" %>
<%@ page import="java.util.List" %>
<%!
    public void printTree(List<CategoryTuan> cats, JspWriter out) throws java.io.IOException {
        out.write("<ul>");
        for (CategoryTuan cat : cats) {
            out.write("<li>");

            // Thẻ <a> có href đến servlet
            out.write("<a href='productcategory?categoryId=" + cat.getCategoryId() + "' class='category-link' data-id='" + cat.getCategoryId() + "'>" + cat.getName() + "</a>");
            
            // Kiểm tra nếu danh mục có danh mục con
            if (cat.getChildren() != null && !cat.getChildren().isEmpty()) {
                out.write("<ul class='sub-category' id='sub-" + cat.getCategoryId() + "'>");
                printTree(cat.getChildren(), out); // Đệ quy để in danh mục con
                out.write("</ul>");
            }

            out.write("</li>");
        }
        out.write("</ul>");
    }
%>


<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Product List</title>
        <link rel="stylesheet" href="ProductList/list.css"/>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                document.querySelectorAll(".category-link").forEach(link => {
                    link.addEventListener("click", function (e) {
                        let subCategory = document.getElementById("sub-" + this.dataset.id);
                        if (subCategory) {
                            e.preventDefault(); // Ngăn load trang để xử lý mở danh mục con
                            subCategory.style.display = (subCategory.style.display === "none") ? "block" : "none";
                            window.location.href = this.href; // Vẫn gửi request để lọc sản phẩm
                        }
                    });
                });
            });
        </script>
    </head>
    <body>
        <form action="productsearch" method="get">
            <input type="text" name="key" placeholder="Nhập từ khóa...">
            <button type="submit">Tìm kiếm</button>
        </form>

        <form action="productsort" method="get" id="sortForm">
            <label>Theo ngày:</label>
            <select id="date" name="date" onchange="submitForm()">
                <option value="default">Mặc định</option>
                <option value="new" <c:if test="${param.date == 'new'}">selected</c:if>>Ngày mới nhất</option>
                <option value="old" <c:if test="${param.date == 'old'}">selected</c:if>>Ngày cũ nhất</option>
                </select>

                <label>Theo đánh giá:</label>
                <select id="rate" name="rate" onchange="submitForm()">
                    <option value="default">Mặc định</option>
                    <option value="high" <c:if test="${param.rate == 'high'}">selected</c:if>>Cao nhất</option>
                <option value="low" <c:if test="${param.rate == 'low'}">selected</c:if>>Thấp nhất</option>
                </select>

                <label>Theo giá:</label>
                <select id="price" name="price" onchange="submitForm()">
                    <option value="default">Mặc định</option>
                    <option value="high" <c:if test="${param.price == 'high'}">selected</c:if>>Từ cao đến thấp</option>
                <option value="low" <c:if test="${param.price == 'low'}">selected</c:if>>Từ thấp đến cao</option>
                </select>
            </form>

            <script>
                function submitForm() {
                    document.getElementById("sortForm").submit();
                }
            </script>

            <h1>Danh mục sản phẩm</h1>
        <%
        List<CategoryTuan> categories = (List<CategoryTuan>) request.getAttribute("categories");
        if (categories != null) {
            printTree(categories, out);
        } else {
            out.write("<p>No categories found.</p>");
        }
        %>

        
        
        <div class="container">
            <c:forEach var="product" items="${products}">
                <a href="ProductDetailServlet?product_id=${product.productId}">
                    <div class="product">
                    <a href="ProductDetailServlet?product_id=${product.productId}">
                        <img src="${product.thumbnail}" alt="${product.productName}">
                    </a>
                    <div class="product-name">${product.productName}</div>
                    <div class="product-prices">
                        <strong>Prices:</strong>
                        <c:forEach var="price" items="${product.sortedPrices}" varStatus="status">
                            ${price.price}đ
                            <c:if test="${not status.last}"> - </c:if>
                        </c:forEach>
                    </div>
                    <div class="product-colors">
                        <strong>Colors:</strong>
                        <c:forEach var="color" items="${product.colors}">
                            <div class="color-box ${color.colorName}"></div>
                        </c:forEach>
                    </div>
                    <div class="product-rating">★ ${product.rating}</div>
                </div>
                </a>
            </c:forEach>
        </div>

        <div class="pagination">
            <c:forEach begin="1" end="${end}" var="i">
                <c:choose>
                    <c:when test="${not empty key}">
                        <a href="productsearch?index=${i}&key=${key}">${i}</a>
                    </c:when>
                    <c:when test="${not empty date or not empty rate or not empty price}">
                        <a href="productsort?index=${i}
                           <c:if test='${not empty date}'>&date=${date}</c:if>
                           <c:if test='${not empty rate}'>&rate=${rate}</c:if>
                           <c:if test='${not empty price}'>&price=${price}</c:if>">${i}</a>
                    </c:when>
                    <c:otherwise>
                        <a href="productlist?index=${i}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>



        </div>

    </body>
</html>
