<%-- 
    Document   : test
    Created on : Mar 27, 2025, 10:47:35 PM
    Author     : tuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh sách sản phẩm</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/noUiSlider/15.7.1/nouislider.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .product-card {
                border: 1px solid #ddd;
                padding: 15px;
                border-radius: 8px;
                text-align: center;
                margin-bottom: 20px;
                background: #fff;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                transition: transform 0.3s;
            }
            .product-card:hover {
                transform: scale(1.05);
            }
            .product-card img {
                max-width: 100%;
                height: auto;
                border-radius: 5px;
            }
            .product-name {
                font-weight: bold;
                margin-top: 10px;
            }
            .product-prices {
                color: #d9534f;
                font-size: 18px;
            }
            .product-rating {
                color: #f0ad4e;
                font-size: 16px;
            }
            .color-box {
                display: inline-block;
                width: 20px;
                height: 20px;
                border-radius: 50%;
                margin: 5px;
                border: 1px solid #ccc;
            }
            .White {
                background-color: white;
                border: 1px solid rgba(0, 0, 0, 0.3);
            }
            .Black {
                background-color: black;
                border: 1px solid rgba(0, 0, 0, 0.3);
            }
            .BLue {
                background-color: blue;
                border: 1px solid rgba(0, 0, 0, 0.3);
            }
            .Green {
                background-color: green;
                border: 1px solid rgba(0, 0, 0, 0.3);
            }
            .Red {
                background-color: red;
                border: 1px solid rgba(0, 0, 0, 0.3);
            }
            .Purple {
                background-color: purple;
                border: 1px solid rgba(0, 0, 0, 0.3);
            }
            .Yellow {
                background-color: yellow;
                border: 1px solid rgba(0, 0, 0, 0.3);
            }
            .Gray {
                background-color: gray;
                border: 1px solid rgba(0, 0, 0, 0.3);
            }
            .Pink {
                background-color: pink;
                border: 1px solid rgba(0, 0, 0, 0.3);
            }
            .Blush {
                background-color: #de5d83;
                border: 1px solid rgba(0, 0, 0, 0.3);
            }

            .Orange{
                background-color: orange;
                border: 1px solid rgba(0, 0, 0, 0.3);
            }
            .menu {
                list-style-type: none;
                padding: 0;
            }
            .menu li {
                position: relative;
                padding: 10px;
                background: #f4f4f4;
                margin: 2px;
            }
            .submenu {
                display: none;
                position: absolute;
                left: 100%;
                top: 0;
                list-style-type: none;
                padding: 0;
                background: #e0e0e0;
            }
            .menu li:hover > .submenu {
                display: block;
            }
            .submenu li {
                padding: 8px;
                white-space: nowrap;
            }
            a {
                text-decoration: none;
                color: black;
                display: block;
            }
        </style>
    </head>
    <body>
        <div class="container mt-4">
            <h2 class="text-center mb-4">Danh sách sản phẩm</h2>
            <div class="row">
                <!-- Cột bộ lọc bên trái -->
                <div class="col-md-3">
                    <div>
                        <ul class="menu">
                            <c:forEach var="category" items="${categoryTree}">
                                <li>
                                    <a href="productcategory?category_id=${category.id}">${category.name}</a>
                                    <c:if test="${not empty category.children}">
                                        <ul class="submenu">
                                            <c:forEach var="subCategory" items="${category.children}">
                                                <li>
                                                    <a href="productcategory?category_id=${subCategory.id}">${subCategory.name}</a>
                                                    <c:if test="${not empty subCategory.children}">
                                                        <ul class="submenu">
                                                            <c:forEach var="subSubCategory" items="${subCategory.children}">
                                                                <li>
                                                                    <a href="productcategory?category_id=${subSubCategory.id}">${subSubCategory.name}</a>
                                                                </li>
                                                            </c:forEach>
                                                        </ul>
                                                    </c:if>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </c:if>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="container mt-4">
                        <h2 class="mb-3">Lọc sản phẩm</h2>
                        <form action="productfilter" method="get" class="p-4 border rounded shadow-sm bg-light">

                            <!-- Chọn khoảng giá -->
                            <div class="mb-3">
                                <label class="form-label fw-bold">Chọn khoảng giá</label>
                                <div id="slider" class="mb-2"></div>
                                <div class="d-flex justify-content-between">
                                    <span id="minPriceValue">${param.minPrice != null ? param.minPrice : "1000000"} đ</span>
                                    <span id="maxPriceValue">${param.maxPrice != null ? param.maxPrice : "10000000"} đ</span>
                                </div>
                                <input type="hidden" name="minPrice" id="minPrice">
                                <input type="hidden" name="maxPrice" id="maxPrice">
                            </div>

                            <!-- Chọn kích thước -->
                            <div class="mb-3">
                                <label class="form-label fw-bold">Kích thước</label><br>
                                <c:forEach var="s" items="${size}">
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" name="size" value="${s.size}"
                                               <c:forEach var="selected" items="${selectedSizes}">
                                                   <c:if test="${selected eq s.size}">checked</c:if>
                                               </c:forEach>>
                                        <label class="form-check-label">${s.size}</label>
                                    </div>
                                </c:forEach>
                            </div>

                            <!-- Chọn màu sắc -->
                            <div class="mb-3">
                                <label class="form-label fw-bold">Màu sắc</label><br>
                                <c:forEach var="c" items="${colorsAll}">
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" name="color" value="${c.color}"
                                               <c:forEach var="selected" items="${selectedColors}">
                                                   <c:if test="${selected eq c.color}">checked</c:if>
                                               </c:forEach>>
                                        <span class="color-box ${c.color} ms-1"></span>
                                    </div>
                                </c:forEach>
                            </div>

                            <button type="submit" class="btn btn-primary">Lọc</button>
                        </form>
                    </div>
                </div>

                <!-- Cột danh sách sản phẩm bên phải -->
                <div class="col-md-9">
                    <form action="productsearch" method="get" id="filterForm" class="container p-4 border rounded shadow-sm">
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <input type="text" class="form-control" value="${key}" name="key" placeholder="Nhập từ khóa...">
                            </div>
                            <div class="col-md-6 d-flex justify-content-md-end">
                                <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                            </div>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-3">
                                <label class="form-label">Theo ngày:</label>
                                <select id="date" name="date" class="form-select" onchange="submitForm()">
                                    <option value="default">Mặc định</option>
                                    <option value="new" <c:if test="${param.date == 'new'}">selected</c:if>>Ngày mới nhất</option>
                                    <option value="old" <c:if test="${param.date == 'old'}">selected</c:if>>Ngày cũ nhất</option>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label">Theo đánh giá:</label>
                                    <select id="rate" name="rate" class="form-select" onchange="submitForm()">
                                        <option value="default">Mặc định</option>
                                        <option value="high" <c:if test="${param.rate == 'high'}">selected</c:if>>Cao nhất</option>
                                    <option value="low" <c:if test="${param.rate == 'low'}">selected</c:if>>Thấp nhất</option>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label">Theo giá:</label>
                                    <select id="price" name="price" class="form-select" onchange="submitForm()">
                                        <option value="default">Mặc định</option>
                                        <option value="high" <c:if test="${param.price == 'high'}">selected</c:if>>Từ cao đến thấp</option>
                                    <option value="low" <c:if test="${param.price == 'low'}">selected</c:if>>Từ thấp đến cao</option>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label">Theo lượt xem:</label>
                                    <select id="views" name="views" class="form-select" onchange="submitForm()">
                                        <option value="default">Mặc định</option>
                                        <option value="high" <c:if test="${param.views == 'high'}">selected</c:if>>Nhiều nhất</option>
                                    <option value="low" <c:if test="${param.views == 'low'}">selected</c:if>>Ít nhất</option>
                                    </select>
                                </div>
                            </div>
                        </form>



                        <div class="container mt-4">
                            <div class="row" id="productList">
                            <c:forEach var="product" items="${products}">
                                <div class="col-md-4 mb-4">
                                    <div class="card h-100 shadow-sm">
                                        <a href="ProductDetailServlet?product_id=${product.productId}" class="text-decoration-none">
                                            <img src="${product.thumbnail}" class="card-img-top" alt="${product.productName}">
                                        </a>
                                        <div class="card-body">
                                            <h5 class="card-title text-truncate">${product.productName}</h5>
                                            <p class="card-text text-muted">
                                                <c:forEach var="price" items="${product.sortedPrices}" varStatus="status">
                                                    <fmt:formatNumber value="${price.price}" type="number" pattern="#,###" />đ
                                                    <c:if test="${not status.last}"> - </c:if>
                                                </c:forEach>
                                            </p>
                                            <p class="mb-1"><strong>Màu sắc:</strong></p>
                                            <div class="d-flex gap-2">
                                                <c:forEach var="color" items="${product.colors}">
                                                    <div class="color-box border rounded-circle" style="width: 20px; height: 20px; background-color: ${color.colorName};"></div>
                                                </c:forEach>
                                            </div>
                                            <div class="mt-2 text-warning">★ ${product.rating}</div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>

                    <div class="pagination justify-content-center">
                        <c:forEach begin="1" end="${end}" var="i">
                            <c:if test="${check eq 'list'}">
                                <a class="page-link ${i eq index ? 'active' : ''}" href="productlist?index=${i}">${i}</a>
                            </c:if>
                            <c:if test="${check eq 'category'}">
                                <a class="page-link ${i eq index ? 'active' : ''}" href="productcategory?index=${i}&category_id=${id}">${i}</a>
                            </c:if>
                            <c:if test="${check eq 'filter'}">
                                <a class="page-link ${i eq index ? 'active' : ''}" href="productfilter?index=${i}&minPrice=${minPrice}&maxPrice=${maxPrice}<c:forEach var='c' items='${selectedColors}'>&color=${c}</c:forEach><c:forEach var='s' items='${selectedSizes}'>&size=${s}</c:forEach>">${i}</a>
                            </c:if>
                            <c:if test="${check eq 'search'}"> 
                                <a class="page-link ${i eq index ? 'active' : ''}" href="productsearch?index=${i}&key=${key}&date=${date}&rate=${rate}&price=${price}&views=${views}">
                                    ${i}
                                </a>
                            </c:if>
                        </c:forEach>
                    </div>

                </div>
            </div>
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
    </body>
</html>
