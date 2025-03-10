<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Runner Shop - ${not empty param.category ? categoryName : 'Trang chủ'}</title>

        <!-- SEO Meta Tags -->
        <meta name="description" content="Runner Shop - Cửa hàng thời trang thể thao chính hãng với nhiều ưu đãi hấp dẫn">
        <meta name="keywords" content="thời trang thể thao, giày thể thao, quần áo nam, quần áo nữ">
        <meta name="author" content="Runner Shop">
        <meta property="og:title" content="Runner Shop - Thời trang thể thao chính hãng">
        <meta property="og:description" content="Cửa hàng thời trang thể thao chính hãng với nhiều ưu đãi hấp dẫn">
        <meta property="og:image" content="${pageContext.request.contextPath}/resources/img/logo.png">
        <meta property="og:url" content="${pageContext.request.contextPath}">
        <link rel="canonical" href="${pageContext.request.contextPath}">

        <!-- CSS Libraries -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">

        <link href="model/css/home.css" rel="stylesheet" type="text/css"/>
    </head>

    <body>
        <!-- Header -->
        <%@ include file="/model/styles.jsp" %>
        <%@ include file="/model/header.jsp" %>

        <!-- Banner Carousel -->
        <section class="slider">
            <div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-indicators">
                    <c:forEach items="${cbanners}" var="cbanner" varStatus="i">
                        <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="${i.index}"
                                class="${i.index == 0 ? 'active' : ''}" aria-current="true"
                                aria-label="Banner ${cbanner.banner_id}"></button>
                    </c:forEach>
                </div>

                <div class="carousel-inner">
                    <c:forEach items="${cbanners}" var="cbanner" varStatus="i">
                        <div class="carousel-item ${i.index == 0 ? 'active' : ''}">
                            <img src="${pageContext.request.contextPath}/resources/img/banner/${cbanner.image_url}" 
                                 class="d-block w-100" alt="Banner ${cbanner.banner_id}" />
                        </div>
                    </c:forEach>
                </div>
            </div>
        </section>

        <!-- Search Bar -->
        <div class="container searchbar-container">
            <form class="d-flex searchbar" action="home" method="GET">
                <div class="input-group">
                    <input class="form-control" 
                           type="search" 
                           name="query" 
                           placeholder="Tìm Kiếm">
                    <button class="btn btn-danger" type="submit">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </form>
        </div>


        <!-- Search Results Message -->
        <c:if test="${not empty param.query}">
            <div class="container mt-4">
                <div class="alert alert-info d-flex justify-content-between align-items-center">
                    <div>
                        Kết quả tìm kiếm cho "<strong>${fn:escapeXml(param.query)}</strong>": 
                        <strong>${totalProducts}</strong> sản phẩm
                    </div>
                    <a href="home" class="btn btn-outline-primary btn-sm">
                        <i class="fas fa-times me-1"></i>Xóa tìm kiếm
                    </a>
                </div>
            </div>
        </c:if>

        <!-- Category Navigation -->
        <c:if test="${empty param.query}">
            <!-- Category Navigation -->
            <nav class="category-nav">
                <div class="container">
                    <ul class="category-list">
                        <li>
                            <a href="home?category=nam" class="category-item ${param.category == 'nam' ? 'active' : ''}">
                                <img src="resources/img/danhmuc4.webp" alt="Đồ Nam" class="lazyload">
                                <span>Đồ Nam</span>
                            </a>
                        </li>
                        <li>
                            <a href="home?category=nu" class="category-item ${param.category == 'nu' ? 'active' : ''}">
                                <img src="resources/img/danhmuc3.jpg" alt="Đồ Nữ" class="lazyload">
                                <span>Đồ Nữ</span>
                            </a>
                        </li>
                        <li>
                            <a href="home?category=giay" class="category-item ${param.category == 'giay' ? 'active' : ''}">
                                <img src="resources/img/danhmuc2.jpg" alt="Giày" class="lazyload">
                                <span>Giày</span>
                            </a>
                        </li>
                        <li>
                            <a href="home?category=other" class="category-item ${param.category == 'other' ? 'active' : ''}">
                                <img src="resources/img/danhmuc21.jpg" alt="Phụ Kiện" class="lazyload">
                                <span>Phụ Kiện</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>            
        </c:if>



        <!-- Main Content -->
        <div class="container">

            <c:choose>
                <%-- Hiển thị kết quả tìm kiếm --%>
                <c:when test="${not empty param.query}">
                    <section id="search-results" class="product-grid">
                        <c:choose>
                            <c:when test="${empty listproduct}">
                                <div class="empty-search-state text-center py-5">
                                    <i class="fas fa-search fa-3x mb-3"></i>
                                    <h3>Không tìm thấy kết quả</h3>
                                    <p>Không tìm thấy sản phẩm nào phù hợp với từ khóa "${fn:escapeXml(param.query)}"</p>
                                    <a href="home" class="btn btn-primary">
                                        <i class="fas fa-arrow-left me-2"></i>Quay lại trang chủ
                                    </a>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="row g-4">
                                    <c:forEach var="product" items="${listproduct}">
                                        <div class="col-lg-3 col-md-4 col-sm-6">
                                            <div class="product-card">
                                                <c:if test="${product.discount > 0}">
                                                    <span class="discount-badge">-${product.discount}%</span>
                                                </c:if>
                                                <img src="${product.thumbnail}" 
                                                     class="product-image lazyload" 
                                                     alt="${product.product_name}"
                                                     data-src="${product.thumbnail}">
                                                <div class="product-info">
                                                    <h3 class="product-title">
                                                        ${fn:replace(product.product_name, param.query, '<mark>'.concat(param.query).concat('</mark>'))}
                                                    </h3>
                                                    <div class="price-section">
                                                        <c:if test="${product.discount > 0}">
                                                            <span class="original-price">
                                                                <fmt:formatNumber value="${product.price}" 
                                                                                  type="currency" 
                                                                                  currencySymbol="₫"/>
                                                            </span>
                                                        </c:if>
                                                        <span class="discounted-price">
                                                            <fmt:formatNumber value="${product.price * (100 - product.discount) / 100}" 
                                                                              type="currency" 
                                                                              currencySymbol="₫"/>
                                                        </span>
                                                    </div>
                                                    <a href="ProductDetailServlet?product_id=${product.product_id}" 
                                                       class="btn btn-primary">
                                                        <i class="fas fa-eye me-2"></i>Xem chi tiết
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>

                                <!-- Phân trang cho kết quả tìm kiếm -->
                                <c:if test="${totalPages > 1}">
                                    <nav aria-label="Search results pages" class="my-5">
                                        <ul class="pagination justify-content-center">
                                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                                <a class="page-link" href="home?query=${fn:escapeXml(param.query)}&page=${currentPage - 1}" aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </li>

                                            <c:forEach begin="1" end="${totalPages}" var="i">
                                                <li class="page-item ${currentPage == i ? 'active' : ''}">
                                                    <a class="page-link" href="home?query=${fn:escapeXml(param.query)}&page=${i}">${i}</a>
                                                </li>
                                            </c:forEach>

                                            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                                <a class="page-link" href="home?query=${fn:escapeXml(param.query)}&page=${currentPage + 1}" aria-label="Next">
                                                    <span aria-hidden="true">&raquo;</span>
                                                </a>
                                            </li>
                                        </ul>
                                    </nav>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </section>
                </c:when>

                <%-- Giữ nguyên phần hiển thị danh mục và trang chủ hiện tại --%>
                <c:when test="${not empty param.category}">
                    <c:if test="${not empty param.category}">
                        <!-- Breadcrumb -->
                        <nav aria-label="breadcrumb" class="category-breadcrumb">
                            <ol class="breadcrumb mb-0">
                                <li class="breadcrumb-item"><a href="home">Trang chủ</a></li>
                                <li class="breadcrumb-item active" aria-current="page">
                                    <c:choose>
                                        <c:when test="${param.category == 'nam'}">Thời Trang Nam</c:when>
                                        <c:when test="${param.category == 'nu'}">Thời Trang Nữ</c:when>
                                        <c:when test="${param.category == 'giay'}">Giày Dép</c:when>
                                        <c:when test="${param.category == 'other'}">Phụ Kiện</c:when>
                                    </c:choose>
                                </li>
                            </ol>
                        </nav>

                        <!-- Sort Section -->
                        <div class="sort-container">
                            <form method="GET" action="home" class="d-flex justify-content-between align-items-center">
                                <input type="hidden" name="category" value="${param.category}"/>
                                <div class="d-flex align-items-center">
                                    <label class="me-3 mb-0">Sắp xếp theo:</label>
                                    <select name="sortPrice" class="form-select" onchange="this.form.submit()">
                                        <option value="">Mặc định</option>
                                        <option value="asc" ${param.sortPrice == 'asc' ? 'selected' : ''}>Giá tăng dần</option>
                                        <option value="desc" ${param.sortPrice == 'desc' ? 'selected' : ''}>Giá giảm dần</option>
                                    </select>
                                </div>
                                <div class="results-count">
                                    Hiển thị ${fn:length(listproduct)} sản phẩm
                                </div>
                            </form>
                        </div>

                        <!-- Product List -->
                        <section id="category-products" class="product-grid">
                            <c:choose>
                                <c:when test="${empty listproduct}">
                                    <div class="empty-state">
                                        <i class="fas fa-box-open"></i>
                                        <p>Không tìm thấy sản phẩm nào trong danh mục này</p>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="row g-4">
                                        <c:forEach var="product" items="${listproduct}">
                                            <div class="col-lg-3 col-md-4 col-sm-6">
                                                <div class="product-card">
                                                    <c:if test="${product.discount > 0}">
                                                        <span class="discount-badge">-${product.discount}%</span>
                                                    </c:if>
                                                    <img src="${product.thumbnail}" 
                                                         class="product-image lazyload" 
                                                         alt="${product.product_name}"
                                                         data-src="${product.thumbnail}">
                                                    <div class="product-info">
                                                        <h3 class="product-title">${product.product_name}</h3>
                                                        <div class="price-section">
                                                            <c:if test="${product.discount > 0}">
                                                                <span class="original-price">
                                                                    <fmt:formatNumber value="${product.price}" 
                                                                                      type="currency" 
                                                                                      currencySymbol="₫"/>
                                                                </span>
                                                            </c:if>
                                                            <span class="discounted-price">
                                                                <fmt:formatNumber value="${product.price * (100 - product.discount) / 100}" 
                                                                                  type="currency" 
                                                                                  currencySymbol="₫"/>
                                                            </span>
                                                        </div>
                                                        <a href="ProductDetailServlet?product_id=${product.product_id}" 
                                                           class="btn btn-primary">
                                                            <i class="fas fa-eye me-2"></i>Xem chi tiết
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </section>
                    </c:if>
                    <!-- Pagination -->
                    <c:if test="${not empty param.category && not empty listproduct && totalPages > 1}">
                        <nav aria-label="Page navigation" class="my-5">
                            <ul class="pagination justify-content-center">
                                <!-- Previous Button -->
                                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                    <a class="page-link" href="home?page=${currentPage - 1}${not empty category ? '&category='.concat(category) : ''}${not empty sortPrice ? '&sortPrice='.concat(sortPrice) : ''}${not empty query ? '&query='.concat(query) : ''}" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>

                                <!-- Page Numbers -->
                                <c:forEach begin="1" end="${totalPages}" var="i">
                                    <li class="page-item ${currentPage == i ? 'active' : ''}">
                                        <a class="page-link" href="home?page=${i}${not empty category ? '&category='.concat(category) : ''}${not empty sortPrice ? '&sortPrice='.concat(sortPrice) : ''}${not empty query ? '&query='.concat(query) : ''}">${i}</a>
                                    </li>
                                </c:forEach>

                                <!-- Next Button -->
                                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                    <a class="page-link" href="home?page=${currentPage + 1}${not empty category ? '&category='.concat(category) : ''}${not empty sortPrice ? '&sortPrice='.concat(sortPrice) : ''}${not empty query ? '&query='.concat(query) : ''}" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </c:if>
                </c:when>

                <c:otherwise>
                    <!-- [Giữ nguyên phần homepage content hiện tại] -->
                    <c:if test="${empty param.category}">
                        <!-- Sản Phẩm Nổi Bật -->
                        <section id="featured-products" class="my-5">
                            <div class="section-header mb-4">
                                <h2 class="section-title text-center position-relative">
                                    <span class="section-title-text">
                                        <i class="fas fa-star text-warning me-2"></i>Sản Phẩm Nổi Bật
                                    </span>
                                </h2>

                            </div>
                            <div class="row g-4">
                                <c:forEach var="product" items="${topViewedProducts}" varStatus="loop">
                                    <c:if test="${loop.index < 8}">
                                        <div class="col-lg-3 col-md-4 col-sm-6">
                                            <div class="product-card">
                                                <div class="product-badges">
                                                    <span class="badge bg-danger position-absolute" 
                                                          style="top: 10px; left: 10px; z-index: 2;">HOT</span>
                                                    <c:if test="${product.discount > 0}">
                                                        <span class="discount-badge">-${product.discount}%</span>
                                                    </c:if>
                                                </div>
                                                <div class="product-image-wrapper">
                                                    <img src="${product.thumbnail}" 
                                                         class="product-image lazyload" 
                                                         alt="${product.product_name}"
                                                         data-src="${product.thumbnail}">
                                                    <div class="product-overlay">
                                                        <a href="ProductDetailServlet?product_id=${product.product_id}" 
                                                           class="btn btn-light btn-sm">
                                                            <i class="fas fa-eye"></i>
                                                        </a>
                                                    </div>
                                                </div>
                                                <div class="product-info">
                                                    <h3 class="product-title">${product.product_name}</h3>
                                                    <div class="price-section">
                                                        <c:if test="${product.discount > 0}">
                                                            <span class="original-price">
                                                                <fmt:formatNumber value="${product.price}" 
                                                                                  type="currency" 
                                                                                  currencySymbol="₫"/>
                                                            </span>
                                                        </c:if>
                                                        <span class="discounted-price">
                                                            <fmt:formatNumber value="${product.price * (100 - product.discount) / 100}" 
                                                                              type="currency" 
                                                                              currencySymbol="₫"/>
                                                        </span>
                                                    </div>
                                                    <a href="ProductDetailServlet?product_id=${product.product_id}" 
                                                       class="btn btn-primary w-100">
                                                        <i class="fas fa-shopping-cart me-2"></i>Xem chi tiết
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </c:forEach>
                                <div class="text-center mt-3">
                                    <a href="/RunnerShop2/productlist" class="btn btn-outline-primary">
                                        Xem tất cả <i class="fas fa-arrow-right ms-2"></i>
                                    </a>
                                </div>
                            </div>
                        </section>

                        <!-- Sản Phẩm Mới -->
                        <section id="newest-products" class="my-5">
                            <div class="section-header mb-4">
                                <h2 class="section-title text-center position-relative">
                                    <span class="section-title-text">
                                        <i class="fas fa-gift text-primary me-2"></i>Sản Phẩm Mới
                                    </span>
                                </h2>

                            </div>
                            <div class="row g-4">
                                <c:forEach var="product" items="${newestProducts}" varStatus="loop">
                                    <c:if test="${loop.index < 8}">
                                        <div class="col-lg-3 col-md-4 col-sm-6">
                                            <div class="product-card">
                                                <div class="product-badges">
                                                    <span class="badge bg-success position-absolute" 
                                                          style="top: 10px; left: 10px; z-index: 2;">NEW</span>
                                                    <c:if test="${product.discount > 0}">
                                                        <span class="discount-badge">-${product.discount}%</span>
                                                    </c:if>
                                                </div>
                                                <div class="product-image-wrapper">
                                                    <img src="${product.thumbnail}" 
                                                         class="product-image lazyload" 
                                                         alt="${product.product_name}"
                                                         data-src="${product.thumbnail}">
                                                    <div class="product-overlay">
                                                        <a href="ProductDetailServlet?product_id=${product.product_id}" 
                                                           class="btn btn-light btn-sm">
                                                            <i class="fas fa-eye"></i>
                                                        </a>
                                                    </div>
                                                </div>
                                                <div class="product-info">
                                                    <h3 class="product-title">${product.product_name}</h3>
                                                    <div class="price-section">
                                                        <c:if test="${product.discount > 0}">
                                                            <span class="original-price">
                                                                <fmt:formatNumber value="${product.price}" 
                                                                                  type="currency" 
                                                                                  currencySymbol="₫"/>
                                                            </span>
                                                        </c:if>
                                                        <span class="discounted-price">
                                                            <fmt:formatNumber value="${product.price * (100 - product.discount) / 100}" 
                                                                              type="currency" 
                                                                              currencySymbol="₫"/>
                                                        </span>
                                                    </div>
                                                    <a href="ProductDetailServlet?product_id=${product.product_id}" 
                                                       class="btn btn-primary w-100">
                                                        <i class="fas fa-shopping-cart me-2"></i>Xem chi tiết
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </c:forEach>
                                <div class="text-center mt-3">
                                    <a href="/RunnerShop2/productlist" class="btn btn-outline-primary">
                                        Xem tất cả <i class="fas fa-arrow-right ms-2"></i>
                                    </a>
                                </div>
                        </section>

                    </c:if>
                </c:otherwise>
            </c:choose>

<!-- Phần hiển thị bài viết -->
<%-- Chỉ hiển thị blog section khi KHÔNG có tìm kiếm --%>
<c:if test="${empty param.query}">
    <section class="blog-section">
        <!-- Bài viết mới nhất -->
<!--        <div class="latest-posts">
            <div class="section-header">
                <h2 class="section-title text-center position-relative">
                    <span class="section-title-text">
                        <i class="fa-sharp-duotone fa-solid fa-newspaper"></i> Bài Viết  Mới
                    </span>
                </h2>
            </div>
            <div class="row g-4">
                <c:forEach items="${latestPosts}" var="post">
                    <div class="col-md-4">
                        <article class="post-card">
                            <div class="post-image">
                                <img src="images/Post/${post.postbanner}" alt="${post.title}" class="img-fluid lazyload">
                            </div>
                            <div class="post-content">
                                <h3 class="post-title">
                                    <a href="=postDetail?postID=${post.getPostID()}">${post.title}</a>
                                </h3>
                                <p class="post-excerpt">${post.description}</p>
                                <div class="post-meta">
                                    <span class="date">
                                        <i class="fas fa-calendar-alt"></i>
                                        ${post.dateCreated}
                                    </span>
                                    <span class="views">
                                        <i class="fas fa-eye"></i>
                                        ${post.views}
                                    </span>
                                </div>
                            </div>
                        </article>
                    </div>
                </c:forEach>
            </div>
        </div>-->

        <!-- Bài viết phổ biến -->
        <div class="popular-posts mt-5">
            <div class="section-header">
                <h2 class="section-title text-center position-relative">
                    <span class="section-title-text">
                        <i class="fa-regular fa-newspaper"></i> Bài viết nổi bật
                    </span>
                </h2>
            </div>
            <div class="row g-4">
                <c:forEach items="${popularPosts}" var="post">
                    <div class="col-md-4">
                        <article class="post-card">
                            <div class="post-image">
                                <img src="images/Post/${post.postbanner}" alt="${post.title}" class="img-fluid lazyload">
                            </div>
                            <div class="post-content">
                                <h3 class="post-title">
                                    <a href="postDetail?postID==${post.postID}">${post.title}</a>
                                </h3>
                                <p class="post-excerpt">${post.description}</p>
                                <div class="post-meta">
                                    <span class="date">
                                        <i class="fas fa-calendar-alt"></i>
                                        ${post.dateCreated}
                                    </span>
                                    <span class="views">
                                        <i class="fas fa-eye"></i>
                                        ${post.views}
                                    </span>
                                </div>
                            </div>
                        </article>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>
</c:if>




        <!-- Footer -->
        <%@ include file="/model/footer.jsp" %>

        <!-- Scripts -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://web.nvnstatic.net/js/lazyLoad/lazysizes.min.js" async></script>
        <link href="model/js/home.js" rel="stylesheet" type="text/css"/>

    </body>
</html>
