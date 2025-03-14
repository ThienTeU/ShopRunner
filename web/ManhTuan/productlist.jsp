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
        <link rel="stylesheet" href="ManhTuan/list.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/noUiSlider/15.7.1/nouislider.min.css">
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                document.querySelectorAll(".category-link").forEach(link => {
                    link.addEventListener("click", function (e) {
                        let subCategory = document.getElementById("sub-" + this.dataset.id);
                        if (subCategory) {
                            e.preventDefault(); 
                            subCategory.style.display = (subCategory.style.display === "none") ? "block" : "none";
                            window.location.href = this.href;
                        }
                    });
                });
            });
        </script>
    </head>
    <body>

        <div class="product-category">                    
            <a href="home" style="text-decoration: none; color: black">Trang chủ</a> - 
            <a href="productlist" style="text-decoration: none; color: black">Danh sách sản phẩm</a>  
            <c:forEach items="${listCategory}" var="category">
                <c:if test="${category.categoryId == id}">
                    - 
                    <c:forEach items="${listCategory}" var="cate">
                        <c:if test="${category.parentId == cate.categoryId}">
                            <a href="productcategory?categoryId=${cate.categoryId}" style="text-decoration: none; color: black">${cate.name}</a> -
                        </c:if>
                    </c:forEach>
                    <a href="productcategory?categoryId=${category.categoryId}" style="text-decoration: none; color: black">${category.name}</a>                           
                </c:if>
            </c:forEach>
        </div>
        <hr>

        <div class="slider-container">
            <h2>Chọn khoảng giá</h2>
            <form action="productfilter" method="get">
                <div id="slider"></div>
                <div class="price-values">
                    <span id="minPriceValue">${param.minPrice != null ? param.minPrice : "1000000"} đ</span>
                    <span id="maxPriceValue">${param.maxPrice != null ? param.maxPrice : "10000000"} đ</span>
                </div>
                <input type="hidden" name="minPrice" id="minPrice">
                <input type="hidden" name="maxPrice" id="maxPrice">
                <button type="submit">Lọc</button>
            </form>
        </div>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/noUiSlider/15.7.1/nouislider.min.js"></script>
        <script>
            const slider = document.getElementById('slider');
            noUiSlider.create(slider, {
                start: [${param.minPrice != null ? param.minPrice : 1000000}, ${param.maxPrice != null ? param.maxPrice : 10000000}],
                connect: true,
                range: {
                    'min': 0,
                    'max': 20000000
                },
                step: 100000,
                format: {
                    to: value => Math.round(value).toLocaleString('vi-VN'),
                    from: value => Number(value.replace(/\./g, ''))
                }
            });

            slider.noUiSlider.on('update', (values, handle) => {
                const minPriceValue = document.getElementById('minPriceValue');
                const maxPriceValue = document.getElementById('maxPriceValue');
                const minPrice = document.getElementById('minPrice');
                const maxPrice = document.getElementById('maxPrice');

                minPriceValue.innerText = values[0] + " đ";
                maxPriceValue.innerText = values[1] + " đ";
                minPrice.value = slider.noUiSlider.get()[0].replace(/\./g, '');
                maxPrice.value = slider.noUiSlider.get()[1].replace(/\./g, '');
            });
        </script>

        <form action="productcheckbox" method="get">
            <strong>Kích thước</strong><br>
            <c:forEach var="s" items="${size}">
                <input type="checkbox" name="size" value="${s.size}"
                       <c:forEach var="selected" items="${selectedSizes}">
                           <c:if test="${selected eq s.size}">checked</c:if>
                       </c:forEach>
                       > ${s.size} <br>
            </c:forEach>

            <strong>Màu sắc</strong><br>
            <c:forEach var="c" items="${colorsAll}">
                <label>
                    <input type="checkbox" name="color" value="${c.color}"
                           <c:forEach var="selected" items="${selectedColors}">
                               <c:if test="${selected eq c.color}">checked</c:if>
                           </c:forEach>
                           > 
                    <span class="color-box ${c.color}"></span>
                </label>
            </c:forEach>

            <br>
            <button type="submit">Lọc</button>
        </form>

        <form action="ProductListTest" method="get" id="filterForm">
            <input type="text" value="${key}" name="key" placeholder="Nhập từ khóa...">
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
                <button type="submit">Tìm kiếm</button>
            </form>
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
                        <c:if test="${product.isWithin10Days(product.created_at)}">
                            <div class="type">
                                <span class="content">SẢN PHẨM MỚI</span>
                            </div>
                        </c:if>
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
                    <c:when test="${not empty date or not empty rate or not empty price or not empty key}">
                        <a href="ProductListTest?index=${i}&key=${key}&date=${date}&rate=${rate}&price=${price}">${i}</a>
                    </c:when>
                    <c:otherwise>
                        <a href="productlist?index=${i}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

    </body>
</html>
