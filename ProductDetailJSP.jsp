<%-- 
    Document   : ProductDetailJSP
    Created on : Jan 25, 2025, 10:32:09 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

    <head>
        <title>
            Product Page
        </title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet" />
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&amp;display=swap" rel="stylesheet" />
        <link crossorigin="anonymous" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" rel="stylesheet" />
        <link rel="stylesheet" href="ProductDetail/ProductDetail.css">
    </head>

    <body>
        <div class="row">
            <div class="col-md-8">
                <div class="product-images">
                    <img alt="Main product image showing the product in detail" height="600" id="mainImage"
                         src="${requestScope.listProductImage[0].image_url}"
                         width="600" style="object-fit: contain"/>
                    <div class="thumbnail-slider">
                        <c:forEach items="${requestScope.listProductImage}" var="pi">
                            <img alt="Thumbnail image" height="80"
                                 onclick="changeImage(this)"
                                 src="${pi.image_url}"
                                 width="80" />
                        </c:forEach>
                    </div>
                </div>
                <br>
                <div class="left-section">
                    <div class="reviews">
                        <h5>
                            Reviews (1)
                            <span class="rating">
                                5.0
                                <i class="fas fa-star">
                                </i>
                            </span>
                        </h5>
                    </div>
                    <hr>
                    <div class="description">
                        <h5>
                            Description
                        </h5>
                        <p>${requestScope.product.description}</p>
                    </div>
                    <hr>
                    <div class="complete-look">
                        <h2>
                            Related Product
                        </h2>
                        <c:forEach items="${listRelatedProduct}" var="rp" begin="0" end="3">
                            <a href="ProductDetailServlet?product_id=${rp.product_id}">
                                <div class="item">
                                    <img alt="${rp.product_name}" height="200"
                                         src="${rp.thumbnail}"
                                         width="200" />
                                    <div class="price">
                                        <c:forEach items="${listUniqueProductPrice}" var="pp">
                                            <span class="productPrice">${rp.product_id == pp.product_id ? pp.price : ""}</span>
                                        </c:forEach>
                                    </div>
                                    <div class="name">
                                        ${rp.product_name}
                                    </div>
                                    <div class="category">
                                        <c:forEach items="${listCategory}" var="c">
                                            ${c.category_id == rp.category_id ? c.name : ""}
                                        </c:forEach>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>


                    </div>

                    <div class="complete-look">
                        <h2>
                            Recently View
                        </h2>
                        <c:forEach items="${listRecentlyView}" var="rv" begin="0" end="3">
                            <a href="ProductDetailServlet?product_id=${rv.product_id}">
                                <div class="item">
                                    <img alt="${rv.product_name}" height="200"
                                         src="${rv.thumbnail}"
                                         width="200" />
                                    <div class="price" >
                                        <c:forEach items="${listUniqueProductPrice}" var="pp">
                                            <span class="productPrice">${rv.product_id == pp.product_id ? pp.price : ""}</span>
                                        </c:forEach>
                                    </div>
                                    <div class="name">
                                        ${rv.product_name}
                                    </div>
                                    <div class="category">
                                        <c:forEach items="${listCategory}" var="c">
                                            ${c.category_id == rv.category_id ? c.name : ""}
                                        </c:forEach>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>                       
                    </div>

                    <div class="complete-look">
                        <h2>
                            Most View
                        </h2>
                        <c:forEach items="${listMostView}" var="mv" begin="0" end="3">
                            <a href="ProductDetailServlet?product_id=${mv.product_id}">
                                <div class="item">
                                    <img alt="${mv.product_name}" height="200"
                                         src="${mv.thumbnail}"
                                         width="200" />
                                    <div class="price" >
                                        <c:forEach items="${listUniqueProductPrice}" var="pp">
                                            <span class="productPrice">${mv.product_id == pp.product_id ? pp.price : ""}</span>
                                        </c:forEach>
                                    </div>
                                    <div class="name">
                                        ${mv.product_name}
                                    </div>
                                    <div class="category">
                                        <c:forEach items="${listCategory}" var="c">
                                            ${c.category_id == mv.category_id ? c.name : ""}
                                        </c:forEach>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>                       
                    </div>
                </div>

            </div>
            <div class="col-md-4" style="position: fixed; right: 0;">

                <div class="product-category">                    
                    <c:forEach items="${listCategory}" var="category">

                        <c:if test="${category.category_id == product.category_id}">
                            <c:forEach items="${listCategory}" var="cate">
                                <c:if test="${category.parent_id==cate.category_id}">
                                    ${cate.name} -
                                </c:if>
                            </c:forEach>
                            ${category.name}
                        </c:if>
                    </c:forEach>
                </div>
                <div class="product-title">
                    ${product.product_name}
                </div>
                <div class="product-price">                   
                    <c:if test="${product.discount != 0}">
                        <span class="productPrice">${selectedPrice}</span>
                        <span class="productPrice">${selectedPrice*(100-product.discount)/100}</span>
                    </c:if>

                </div>

                <div class="product-colours">
                    <div>Màu Sắc:</div>
                    <c:forEach items="${requestScope.listProductPrice}" var="pp">
                        <c:forEach items="${listColor}" var="c">
                            <c:if test="${pp.color_id == c.color_id}">
                                <a style="background-color: none" href="ProductDetailServlet?product_id=${requestScope.product.product_id}&color_id=${c.color_id}">
                                    <button style="background-color: ${c.color}; border-radius: 50px; width: 40px; margin-top: 10px"></button>
                                </a>
                            </c:if>
                        </c:forEach>                       
                    </c:forEach>
                </div>

                <div class="product-sizes">
                    <div>Kích Cỡ:</div>
                    <c:forEach items="${listProductQuantity}" var="pq">
                        <c:forEach items="${listSize}" var="s">
                            <c:if test="${pq.size_id == s.size_id}">
                                <button class="size-button"
                                        data-size-id="${s.size_id}"
                                        data-productprice-id="${selectedProductPriceId}"
                                        data-productquantity-id="${pq.productQuantity_id}"
                                        data-quantity="${pq.quantity}">
                                    ${s.size}
                                </button>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </div>
                <!-- Hiển thị số lượng -->
                <div class="product-quantity">
                    <strong>Quantity:</strong> <span id="selectedQuantity">Select a size</span>
                </div>

                <div class="size-guide">
                    <a href="#">
                        Size guide
                    </a>
                </div>
                <div class="add-to-bag1">
                    <form action="AddToCartServlet" method="post">
                        <input type="hidden" name="product_id" value="${product.product_id}">
                        <input type="hidden" name="productprice_id" id="selectedProductPriceId" value="${selectedProductPriceId}">
                        <input type="hidden" name="productquantity_id" id="selectedProductQuantityId" value="">

                        <button type="submit">
                            ADD TO BAG <i class="fas fa-arrow-right"></i>
                        </button>
                    </form>
                </div>

                <div class="shipping-info">
                    <div>
                        <a href="#">
                            FREE SHIPPING FOR ADICLUB MEMBERS!
                        </a>
                    </div>
                    <div>
                        <a href="#">
                            EASY RETURN
                        </a>
                    </div>
                </div>
            </div>
        </div>
        <script>
            function changeImage(element) {
                var mainImage = document.getElementById('mainImage');
                mainImage.src = element.src;
                var thumbnails = document.querySelectorAll('.thumbnail-slider img');
                thumbnails.forEach(function (thumbnail) {
                    thumbnail.classList.remove('active');
                });
                element.classList.add('active');
            }
        </script>
        <script>document.addEventListener("DOMContentLoaded", function () {
                const form = document.querySelector(".add-to-bag1 form");
                const productQuantityInput = document.getElementById("selectedProductQuantityId");

                form.addEventListener("submit", function (event) {
                    if (!productQuantityInput.value) {
                        event.preventDefault(); // Ngăn chặn gửi form
                        alert("Vui lòng chọn kích cỡ trước khi thêm vào giỏ hàng!");
                    }
                });
            });
            document.addEventListener("DOMContentLoaded", function () {
                const sizeButtons = document.querySelectorAll(".size-button");
                const quantityDisplay = document.getElementById("selectedQuantity");
                const productQuantityInput = document.getElementById("selectedProductQuantityId");

                sizeButtons.forEach(button => {
                    button.addEventListener("click", function (event) {
                        event.preventDefault(); // Ngăn chặn reload trang

                        // Bỏ highlight của tất cả nút size
                        sizeButtons.forEach(btn => btn.style.backgroundColor = "white");
                        sizeButtons.forEach(btn => btn.style.color = "black");

                        // Đánh dấu nút size được chọn
                        this.style.backgroundColor = "black";
                        this.style.color = "white";

                        // Lấy dữ liệu quantity và productquantity_id
                        const quantity = this.getAttribute("data-quantity");
                        const productQuantityId = this.getAttribute("data-productquantity-id");

                        // Cập nhật số lượng hiển thị
                        quantityDisplay.textContent = quantity ? quantity : "Out of stock";

                        // Cập nhật input hidden trong form
                        productQuantityInput.value = productQuantityId;
                    });
                });
            });
        </script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                let priceElements = document.querySelectorAll(".productPrice");
                priceElements.forEach(function (element) {
                    let price = parseFloat(element.textContent);
                    if (!isNaN(price)) {
                        element.textContent = price.toLocaleString("vi-VN") + "₫";
                    }
                });
            });
        </script>

    </body>

</html>
