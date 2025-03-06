
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Home Page</title>

        <!-- Link to Bootstrap CSS và các thư viện khác -->
        <link rel="stylesheet" href="styles.css"> <!-- Custom styles nếu cần -->
        <link rel="stylesheet" href="https://web.nvnstatic.net/js/jquery/fancybox-2.1.5/source/jquery.fancybox.css?v=5" />
        <link rel="stylesheet" href="https://web.nvnstatic.net/css/owlCarousel/owl.carousel.min.2.3.4.css?v=5" />
        <link rel="stylesheet" href="https://web.nvnstatic.net/css/font.css?v=5" />
        <link rel="stylesheet" href="https://web.nvnstatic.net/css/fontAwesome/font-awesome-4.7.0.min.css?v=5" />
        <link rel="stylesheet" href="https://web.nvnstatic.net/css/appLib.css" />
        <link rel="stylesheet" href="https://web.nvnstatic.net/tp/T0211/css/default_style.min.css?v=19" />

        <!-- Các script cần thiết -->
        <script src="https://pos.nvnstatic.net/cache/location.vn.js?v=20250213_2" defer></script>
        <script src="https://web.nvnstatic.net/js/lazyLoad/lazysizes.min.js" async></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        <!-- File CSS của trang home -->
        <link rel="stylesheet" href="css/home.css"/>


    </head>
    <body> 

        <%@ include file="/model/header.jsp" %>


        <nav class="category-nav">
            <ul class="category-list d-flex justify-content-center">
                <li>
                    <a href="home?category=nam" class="category-item ${param.category == 'nam' ? 'active' : ''}">
                        <img src="resources/img/danhmuc4.webp" alt="Đồ Nam">
                        <span>Đồ Nam</span>
                    </a>
                </li>
                <li>
                    <a href="home?category=nu" class="category-item ${param.category == 'nu' ? 'active' : ''}">
                        <img src="resources/img/danhmuc3.jpg" alt="Đồ Nữ">
                        <span>Đồ Nữ</span> 
                    </a>
                </li>
                <li>
                    <a href="home?category=giay" class="category-item ${param.category == 'giay' ? 'active' : ''}">
                        <img src="resources/img/danhmuc2.jpg" alt="Giày">
                        <span>Giày</span>
                    </a>
                </li>
                <li>
                    <a href="home?category=other" class="category-item ${param.category == 'other' ? 'active' : ''}">
                        <img src="resources/img/danhmuc21.jpg" alt="Phụ Kiện Khác">
                        <span>Phụ Kiện</span>
                    </a>
                </li>
            </ul>
        </nav>
        <!-- Nút sắp xếp theo giá -->
        <div class="sort-container my-3">
            <form method="GET" action="home" class="d-inline">
                <input type="hidden" name="category" value="${param.category}"/>
                <select name="sortPrice" class="form-select d-inline w-auto" onchange="this.form.submit()">
                    <option value="asc" ${param.sortPrice == 'asc' ? 'selected' : ''}>Sắp xếp: Giá tăng dần</option>
                    <option value="desc" ${param.sortPrice == 'desc' ? 'selected' : ''}>Sắp xếp: Giá giảm dần</option>
                </select>
            </form>
        </div>
    </div>


    <!-- View Product -->
    <section id="product-list" class="container my-5">
        <h2 class="section-title">Danh Sách Sản Phẩm</h2>
        <c:choose>
            <c:when test="${empty listproduct}">
                <p class="text-center text-muted">Không tìm thấy sản phẩm nào phù hợp.</p>
            </c:when>
            <c:otherwise>
                <div class="row">
                    <c:forEach var="product" items="${listproduct}">
                        <div class="col-3 mb-4">
                            <div class="card">
                                <img src="${product.thumbnail}" class="card-img-top" alt="${product.product_name}" />
                                <div class="card-body">
                                    <h5 class="card-title">${product.product_name}</h5>
                                    <p class="card-text">Mô tả ngắn về sản phẩm.</p>
                                    <p class="card-text">Giá: ${product.price} VND</p>
                                    <span class="badge bg-success">Giảm giá: ${product.discount}%</span>
                                    <a href="ProductDetailServlet?product_id=${product.product_id}" class="btn btn-primary mt-2">Mua Ngay</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>



        <!-- Top Viewed Products Section -->
        <section id="featured-products" class="container my-5 position-relative">
            <h2 class="section-title">Sản Phẩm Nổi Bật</h2>
            <div id="featuredProductsCarousel" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-inner">
                    <c:forEach var="product" items="${topViewedProducts}" varStatus="i">
                        <c:if test="${i.index % 3 == 0}">
                            <div class="carousel-item ${i.index == 0 ? 'active' : ''}">
                                <div class="row d-flex justify-content-center">
                                </c:if>
                                <div class="col-md-3 d-flex">
                                    <div class="card flex-fill">
                                        <img src="${product.thumbnail}" class="card-img-top" alt="${product.product_name}" />
                                        <div class="card-body d-flex flex-column">
                                            <h5 class="card-title">${product.product_name}</h5>
                                            <p class="card-text">Mô tả ngắn sản phẩm.</p>
                                            <p class="card-text">Giá:${product.price} VND</p>
                                            <span class="badge bg-success">Giảm giá: ${product.discount}%</span>
                                            <a href="cart?action=add&productId=${product.product_id}" class="btn btn-primary mt-auto">Mua Ngay</a>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${(i.index + 1) % 3 == 0 || i.last}">
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#featuredProductsCarousel" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#featuredProductsCarousel" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </section>

        <!-- Newest Products Section -->
        <section id="newest-products" class="container my-5 position-relative">
            <h2 class="section-title">Sản Phẩm Mới</h2>
            <div id="newestProductsCarousel" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-inner">
                    <c:forEach var="product" items="${newestProducts}" varStatus="i">
                        <c:if test="${i.index % 3 == 0}">
                            <div class="carousel-item ${i.index == 0 ? 'active' : ''}">
                                <div class="row d-flex justify-content-center">
                                </c:if>
                                <div class="col-md-3 d-flex">
                                    <div class="card flex-fill">
                                        <img src="${product.thumbnail}" class="card-img-top" alt="${product.product_name}" />
                                        <div class="card-body d-flex flex-column">
                                            <h5 class="card-title">${product.product_name}</h5>
                                            <p class="card-text">Mô tả ngắn sản phẩm.</p>
                                            <p class="card-text">Giá:${product.price} VND</p>
                                            <span class="badge bg-success">Giảm giá: ${product.discount}%</span>
                                            <a href="cart?action=add&productId=${product.product_id}" class="btn btn-primary mt-auto">Mua Ngay</a>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${(i.index + 1) % 3 == 0 || i.last}">
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#newestProductsCarousel" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#newestProductsCarousel" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
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
        <%@ include file="/model/footer.jsp" %>

        <!-- Link to Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 