<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Home Page</title>

        <!-- Link to Bootstrap CSS -->
        <link rel="stylesheet" href="styles.css"> <!-- Custom styles if any -->
        <link rel="stylesheet" href="https://web.nvnstatic.net/js/jquery/fancybox-2.1.5/source/jquery.fancybox.css?v=5" />
        <link rel="stylesheet" href="https://web.nvnstatic.net/css/owlCarousel/owl.carousel.min.2.3.4.css?v=5" />
        <link rel="stylesheet" href="https://web.nvnstatic.net/css/font.css?v=5" />
        <link rel="stylesheet" href="https://web.nvnstatic.net/css/fontAwesome/font-awesome-4.7.0.min.css?v=5" />
        <link rel="stylesheet" href="https://web.nvnstatic.net/css/appLib.css" />
        <link rel="stylesheet" href="https://web.nvnstatic.net/tp/T0211/css/default_style.min.css?v=19" />

        <script src="https://pos.nvnstatic.net/cache/location.vn.js?v=20250213_2" defer></script>
        <script src="https://web.nvnstatic.net/js/lazyLoad/lazysizes.min.js" async></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </head>

    <body>

        <!-- Include Header -->
        <%@include file="/model/header.jsp" %>

        <!-- Banner Slider -->
        <section class="slider my-4">
            <div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
                <!-- Carousel Indicators -->
                <div class="carousel-indicators">
                    <c:forEach items="${cbanners}" var="cbanner" varStatus="i">
                        <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="${i.index}"
                                <c:if test="${i.index == 0}"> class="active" aria-current="true"</c:if>
                                aria-label="Banner ${cbanner.banner_id}"></button>
                    </c:forEach>
                </div>

                <!-- Carousel Inner -->
                <div class="carousel-inner">
                    <c:forEach items="${cbanners}" var="cbanner" varStatus="i">
                        <div class="carousel-item ${i.index == 0 ? 'active' : ''}">
                            <img src="${pageContext.request.contextPath}/resources/img/banner/${cbanner.image_url}"
                                 class="d-block w-100" alt="Banner ${cbanner.banner_id}" />
                        </div>
                    </c:forEach>
                </div>

                <!-- Carousel Controls -->
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions"
                        data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions"
                        data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </section>

        <!-- Top Viewed Products Section -->
        <section id="top-viewed-products" class="container my-5">
            <h2 class="text-center mb-4">Sản Phẩm Nổi Bật</h2>
            <div class="row">
                <c:forEach var="product" items="${topViewedProducts}">
                    <div class="col-md-3 mb-4">
                        <div class="card">
                            <img src="${product.thumbnail}" class="card-img-top" alt="${product.product_name}" />
                            <div class="card-body">
                                <h5 class="card-title">${product.product_name}</h5>
    <!--                            <p class="card-text">${product.description}</p>-->
                                <span class="badge bg-success">Discount: ${product.discount}%</span>
                                <!-- Buy Button -->
                                <a href="cart?action=add&productId=${product.product_id}" class="btn btn-primary mt-2">Mua Ngay</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>

        <!-- Newest Products Section -->
        <section id="newest-products" class="container my-5">
            <h2 class="text-center mb-4">Sản Phẩm Mới</h2>
            <div class="row">
                <c:forEach var="product" items="${newestProducts}">
                    <div class="col-md-3 mb-4">
                        <div class="card">
                            <img src="${product.thumbnail}" class="card-img-top" alt="${product.product_name}" />
                            <div class="card-body">
                                <h5 class="card-title">${product.product_name}</h5>
    <!--                            <p class="card-text">${product.description}</p>-->
                                <span class="badge bg-primary">Discount: ${product.discount}%</span>
                                <!-- Buy Button -->
                                <a href="cart?action=add&productId=${product.product_id}" class="btn btn-primary mt-2">Mua Ngay</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>

        <!-- Bottom Banners Section -->
        <section id="bottom-banners" class="container my-5">
            <div class="row">
                <div class="col-sm-6 mb-4">
                    <a href="/dinh-duong-pc255489.html">
                        <img class="img-fluid lazyload" 
                             src="https://web.nvnstatic.net/img/lazyLoading.gif?v=3" 
                             data-src="https://pos.nvncdn.com/29c4f1-30923/bn/20220601_mWlYPRGQw5XehQqwOa0XHzlc.jpg"
                             alt="Dinh Dưỡng Chạy Bộ">
                    </a>
                </div>
                <div class="col-sm-6 mb-4">
                    <a href="javascript:void(0);">
                        <img class="img-fluid lazyload"
                             src="https://web.nvnstatic.net/img/lazyLoading.gif?v=3"
                             data-src="https://pos.nvncdn.com/29c4f1-30923/bn/20220601_G5zTsHmb5Kjl9062eLk87mwD.jpg"
                             alt="Test">
                    </a>
                </div>
            </div>
        </section>

        <!-- Include Footer -->
        <%@include file="/model/footer.jsp" %>

        <!-- Link to Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    </body>

</html>
