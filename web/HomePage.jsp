
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


        <!-- Banner Slider -->
        <section class="slider ">
            <div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
                <!-- Carousel Indicators -->
                <div class="carousel-indicators">
                    <c:forEach items="${cbanners}" var="cbanner" varStatus="i">
                        <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="${i.index}"
                                <c:if test="${i.index == 0}"> class="active" aria-current="true"</c:if>
                                aria-label="Banner ${cbanner.banner_id}"></button>
                    </c:forEach>
                    <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="${fn:length(cbanners)}" aria-label="Banner mới"></button>
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
                <!--                <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                    <span class="visually-hidden">Previous</span>
                                </button>
                                <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                    <span class="visually-hidden">Next</span>
                                </button>-->
            </div>
        </section>

        <!-- SEARCHBAR -->
        <div class="container searchbar-container">
            <form class="d-flex searchbar" action="home" method="GET">
                <input class="form-control me-2" type="search" name="query" placeholder="Tìm kiếm sản phẩm..." aria-label="Search" value="${param.query}">
                <button class="btn btn-danger" type="submit">
                    <i class="fas fa-search"></i>
                </button>
            </form>
        </div>


        
        <div class="container my-5 text-center">
            <nav class="category-nav">
                <ul class="category-list d-flex justify-content-center">
                    <li><a href="home?category=" class="category-item ${empty param.category ? 'active' : ''}"><i></i> Tất Cả</a></li>
                    <li><a href="home?category=nam" class="category-item ${param.category == 'nam' ? 'active' : ''}"><i class="fas fa-male"></i> Đồ Nam</a></li>
                    <li><a href="home?category=nu" class="category-item ${param.category == 'nu' ? 'active' : ''}"><i class="fas fa-female"></i> Đồ Nữ</a></li>
                    <li><a href="home?category=giay" class="category-item ${param.category == 'giay' ? 'active' : ''}"><i class="fas fa-running"></i> Giày</a></li>
                    <li><a href="home?category=other" class="category-item ${param.category == 'other' ? 'active' : ''}"><i class="fas fa-dumbbell"></i> Phụ Kiện Khác</a></li>
                </ul>
            </nav>
            
            <!-- Bộ lọc theo giá -->
<!--            <div class="price-filter-container my-3 d-flex align-items-center justify-content-center  p-1 ">
                <label for="minPrice" class="me-2 fw-bold">Giá từ:</label>
                <input type="number" id="minPrice" min="0" max="5000000" step="50000" value="${param.minPrice}" class="form-control w-25 me-2 border-primary">
                <label for="maxPrice" class="me-2 fw-bold">đến</label>
                <input type="number" id="maxPrice" min="0" max="5000000" step="50000" value="${param.maxPrice}" class="form-control w-25 me-2 border-primary">
                <button class="btn btn-primary fw-bold" onclick="applyPriceFilter()">Lọc</button>
            </div>-->
            
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
                                        <a href="cart?action=add&productId=${product.product_id}" class="btn btn-primary mt-2">Mua Ngay</a>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>

            <!-- Pagination Controls -->
            <nav>
                <ul class="pagination justify-content-center">
                    <c:if test="${currentPage > 1}">
                        <li class="page-item">
                            <a class="page-link" href="home?page=${currentPage - 1}&category=${param.category}">Trước</a>
                        </li>
                    </c:if>

                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="home?page=${i}&category=${param.category}">${i}</a>
                        </li>
                    </c:forEach>

                    <c:if test="${currentPage < totalPages}">
                        <li class="page-item">
                            <a class="page-link" href="home?page=${currentPage + 1}&category=${param.category}">Sau</a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </section>


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