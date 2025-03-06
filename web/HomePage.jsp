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

        <style>
            :root {
                --primary-color: #ff6600;
                --secondary-color: #333333;
                --light-gray: #f8f9fa;
                --border-radius: 8px;
                --box-shadow: 0 2px 10px rgba(0,0,0,0.08);
                --transition: all 0.3s ease;
            }

            body {
                font-family: 'Segoe UI', Arial, sans-serif;
                background-color: #fff;
            }

            /* Header Banner */
            .header-banner {
                background-color: var(--primary-color);
                color: white;
                text-align: center;
                padding: 8px 0;
                font-size: 0.9rem;
            }

            /* Hero Banner Styles */
            .slider {
                margin-bottom: 40px;
            }

            .carousel-item {
                height: 500px;
                position: relative;
            }

            .carousel-item img {
                object-fit: cover;
                height: 100%;
                width: 100%;
            }

            .carousel-caption {
                background: rgba(0, 0, 0, 0.5);
                padding: 20px;
                border-radius: 10px;
                max-width: 600px;
                margin: 0 auto;
            }

            .carousel-indicators [data-bs-target] {
                width: 12px;
                height: 12px;
                border-radius: 50%;
                margin: 0 6px;
            }

            /* Search Bar Styles */
            .searchbar-container {
                margin: -25px auto 40px;
                position: relative;
                z-index: 100;
                max-width: 700px;
            }

            .searchbar {
                background: white;
                padding: 15px;
                border-radius: 50px;
                box-shadow: 0 5px 20px rgba(0,0,0,0.1);
            }

            .searchbar .form-control {
                border: none;
                padding: 10px 20px;
                font-size: 1.1rem;
                border-radius: 25px;
            }

            .searchbar .btn {
                padding: 10px 30px;
                border-radius: 25px;
                font-weight: 600;
                margin-left: 10px;
            }

            /* Category Navigation */
            .category-nav {
                padding: 40px 0;
                background-color: var(--light-gray);
                margin-bottom: 30px;
            }

            .category-list {
                list-style: none;
                padding: 0;
                margin: 0;
                display: flex;
                justify-content: center;
                gap: 40px;
            }

            .category-item {
                text-decoration: none;
                text-align: center;
                display: flex;
                flex-direction: column;
                align-items: center;
                transition: var(--transition);
                position: relative;
            }

            .category-item:hover {
                transform: translateY(-5px);
            }

            .category-item img {
                width: 140px;
                height: 140px;
                border-radius: 50%;
                object-fit: cover;
                border: 4px solid #fff;
                box-shadow: var(--box-shadow);
                margin-bottom: 15px;
                transition: var(--transition);
            }

            .category-item:hover img {
                border-color: var(--primary-color);
            }

            .category-item span {
                font-size: 1.2rem;
                font-weight: 600;
                color: var(--secondary-color);
            }

            .category-item.active {
                pointer-events: none;
            }

            .category-item.active img {
                border-color: var(--primary-color);
            }

            .category-item.active span {
                color: var(--primary-color);
            }

            .category-item.active::after {
                content: '';
                position: absolute;
                bottom: -10px;
                left: 50%;
                transform: translateX(-50%);
                width: 40px;
                height: 3px;
                background-color: var(--primary-color);
                border-radius: 3px;
            }

            /* Product Cards */
            .product-card {
                border: none;
                border-radius: var(--border-radius);
                box-shadow: var(--box-shadow);
                transition: var(--transition);
                height: 100%;
                position: relative;
                overflow: hidden;
                background: #fff;
            }

            .product-card:hover {
                transform: translateY(-10px);
                box-shadow: 0 15px 30px rgba(0,0,0,0.1);
            }

            .product-image {
                height: 300px;
                object-fit: cover;
                transition: var(--transition);
                width: 100%;
            }

            .product-card:hover .product-image {
                transform: scale(1.05);
            }

            .discount-badge {
                position: absolute;
                top: 10px;
                right: 10px;
                background-color: var(--primary-color);
                color: white;
                padding: 8px 12px;
                border-radius: 20px;
                font-weight: 600;
                font-size: 0.9rem;
                z-index: 2;
            }

            .product-info {
                padding: 20px;
            }

            .product-title {
                font-size: 1.1rem;
                font-weight: 600;
                margin-bottom: 10px;
                height: 2.4em;
                overflow: hidden;
                display: -webkit-box;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
            }

            .price-section {
                margin: 15px 0;
            }

            .original-price {
                text-decoration: line-through;
                color: #999;
                font-size: 0.9rem;
                margin-right: 10px;
            }

            .discounted-price {
                color: var(--primary-color);
                font-weight: 700;
                font-size: 1.2rem;
            }

            .btn-primary {
                background-color: var(--primary-color);
                border: none;
                padding: 10px 20px;
                border-radius: var(--border-radius);
                font-weight: 600;
                transition: var(--transition);
                width: 100%;
            }

            .btn-primary:hover {
                background-color: #ff8533;
                transform: translateY(-2px);
            }

            /* Newsletter Section */
            .newsletter-section {
                background: linear-gradient(rgba(255,102,0,0.9), rgba(255,102,0,0.9)),
                    url('${pageContext.request.contextPath}/resources/img/newsletter-bg.jpg') center/cover;
                padding: 60px 0;
                margin: 60px 0;
                color: white;
                text-align: center;
            }

            .newsletter-content {
                max-width: 600px;
                margin: 0 auto;
            }

            .newsletter-content h3 {
                font-size: 2rem;
                margin-bottom: 15px;
            }

            .newsletter-form {
                margin-top: 30px;
            }

            .newsletter-form .input-group {
                max-width: 500px;
                margin: 0 auto;
            }

            .newsletter-form .form-control {
                padding: 15px;
                border-radius: 30px 0 0 30px;
                border: none;
            }

            .newsletter-form .btn {
                padding: 15px 30px;
                border-radius: 0 30px 30px 0;
                background: var(--secondary-color);
                border: none;
            }

            /* Back to Top Button */
            .back-to-top {
                position: fixed;
                bottom: 20px;
                right: 20px;
                background: var(--primary-color);
                width: 40px;
                height: 40px;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                color: white;
                text-decoration: none;
                opacity: 0;
                transition: var(--transition);
                z-index: 1000;
            }

            .back-to-top.visible {
                opacity: 1;
            }

            /* Cart Animation */
            .cart-animation {
                position: fixed;
                z-index: 9999;
                width: 30px;
                height: 30px;
                background: var(--primary-color);
                border-radius: 50%;
                color: white;
                display: flex;
                align-items: center;
                justify-content: center;
                animation: flyToCart 1s ease-in-out forwards;
            }

            @keyframes flyToCart {
                0% {
                    transform: scale(1);
                    opacity: 1;
                }
                100% {
                    transform: scale(0) translate(100px, -100px);
                    opacity: 0;
                }
            }

            /* Loading Skeleton */
            @keyframes loading {
                0% {
                    background-position: -200px 0;
                }
                100% {
                    background-position: calc(200px + 100%) 0;
                }
            }

            .skeleton {
                background: linear-gradient(90deg, #f0f0f0 25%, #e0e0e0 50%, #f0f0f0 75%);
                background-size: 200px 100%;
                animation: loading 1.5s infinite;
            }

            /* Responsive Styles */
            @media (max-width: 991.98px) {
                .category-list {
                    gap: 20px;
                    flex-wrap: wrap;
                }

                .category-item img {
                    width: 100px;
                    height: 100px;
                }

                .carousel-item {
                    height: 400px;
                }

                .product-image {
                    height: 250px;
                }
            }

            @media (max-width: 767.98px) {
                .searchbar-container {
                    margin-top: -15px;
                }

                .category-item img {
                    width: 80px;
                    height: 80px;
                }

                .category-item span {
                    font-size: 1rem;
                }

                .newsletter-content h3 {
                    font-size: 1.5rem;
                }

                .newsletter-form .input-group {
                    flex-direction: column;
                }

                .newsletter-form .form-control,
                .newsletter-form .btn {
                    border-radius: 30px;
                    margin: 10px 0;
                }
            }
            /* Pagination Styles */
            .pagination {
                margin-top: 40px;
            }

            .page-link {
                color: var(--primary-color);
                border: 1px solid #dee2e6;
                padding: 8px 16px;
                margin: 0 4px;
                border-radius: var(--border-radius) !important;
                transition: var(--transition);
            }

            .page-link:hover {
                color: white;
                background-color: var(--primary-color);
                border-color: var(--primary-color);
            }

            .page-item.active .page-link {
                background-color: var(--primary-color);
                border-color: var(--primary-color);
            }

            .page-item.disabled .page-link {
                color: #6c757d;
                pointer-events: none;
                background-color: #fff;
                border-color: #dee2e6;
            }

            /* Responsive Pagination */
            @media (max-width: 576px) {
                .pagination {
                    flex-wrap: wrap;
                    justify-content: center;
                }

                .page-link {
                    margin: 2px;
                    padding: 6px 12px;
                }
            }
            /* Search Results Styles */
            .alert-info {
                background-color: #f8f9fa;
                border-color: #e9ecef;
                color: var(--secondary-color);
            }

            mark {
                background-color: #fff3cd;
                padding: 0.2em;
                border-radius: 2px;
            }

            .empty-search-state {
                text-align: center;
                padding: 60px 0;
            }

            .empty-search-state i {
                font-size: 4rem;
                color: var(--primary-color);
                margin-bottom: 20px;
            }

            .empty-search-state h3 {
                color: var(--secondary-color);
                margin-bottom: 15px;
            }

            .empty-search-state p {
                color: #6c757d;
                margin-bottom: 25px;
            }
            /* Product Section Styles */
            .section-title {
                font-size: 1.75rem;
                margin-bottom: 1.5rem;
            }

            .section-title i {
                font-size: 1.5rem;
            }
            /* Section Header Styles */
            .section-header {
                position: relative;
                margin-bottom: 2rem;
            }

            .section-title {
                font-size: 2rem;
                font-weight: 700;
                margin-bottom: 0;
            }

            .section-title-text {
                position: relative;
                display: inline-block;
                padding: 0 15px;
            }

            .section-title-text::before,
            .section-title-text::after {
                content: '';
                position: absolute;
                top: 50%;
                width: 60px;
                height: 2px;
                background-color: var(--primary-color);
                transform: translateY(-50%);
            }

            .section-title-text::before {
                right: 100%;
            }

            .section-title-text::after {
                left: 100%;
            }

            .section-title i {
                font-size: 1.75rem;
                vertical-align: middle;
            }

            /* Responsive styles */
            @media (max-width: 768px) {
                .section-title {
                    font-size: 1.5rem;
                }

                .section-title-text::before,
                .section-title-text::after {
                    width: 30px;
                }

                .section-title i {
                    font-size: 1.25rem;
                }
            }

            @media (max-width: 576px) {
                .section-title-text::before,
                .section-title-text::after {
                    display: none;
                }
            }

            .product-image-wrapper {
                position: relative;
                overflow: hidden;
            }

            .product-overlay {
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background: rgba(0,0,0,0.2);
                display: flex;
                align-items: center;
                justify-content: center;
                opacity: 0;
                transition: var(--transition);
            }

            .product-card:hover .product-overlay {
                opacity: 1;
            }

            .product-badges .badge {
                padding: 0.5rem 1rem;
                font-size: 0.8rem;
                font-weight: 600;
                letter-spacing: 0.5px;
            }

            .btn-outline-primary {
                padding: 0.5rem 1.5rem;
                font-weight: 600;
                border-width: 2px;
            }

            /* Responsive adjustments */
            @media (max-width: 768px) {
                .section-title {
                    font-size: 1.5rem;
                }

                .section-title i {
                    font-size: 1.25rem;
                }

                .btn-outline-primary {
                    padding: 0.375rem 1rem;
                    font-size: 0.9rem;
                }
            }

        </style>
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














            <!-- Homepage Content -->

        </div>



        <!-- Footer -->
        <%@ include file="/model/footer.jsp" %>

        <!-- Scripts -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://web.nvnstatic.net/js/lazyLoad/lazysizes.min.js" async></script>

        <script>
                                        // Lazy loading initialization
                                        document.addEventListener('DOMContentLoaded', function () {
                                            var lazyLoadImages = document.querySelectorAll('img.lazyload');
                                            lazyLoadImages.forEach(function (img) {
                                                img.setAttribute('data-src', img.getAttribute('src'));
                                                img.setAttribute('src', 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==');
                                            });

                                            // Newsletter Form Handler
                                            document.getElementById('newsletterForm')?.addEventListener('submit', function (e) {
                                                e.preventDefault();
                                                const email = this.querySelector('input[type="email"]').value;
                                                if (email) {
                                                    alert('Cảm ơn bạn đã đăng ký! Chúng tôi sẽ gửi voucher qua email của bạn.');
                                                    this.reset();
                                                }
                                            });

                                            // Back to Top Button
                                            const backToTop = document.createElement('a');
                                            backToTop.href = '#';
                                            backToTop.className = 'back-to-top';
                                            backToTop.innerHTML = '<i class="fas fa-arrow-up"></i>';
                                            document.body.appendChild(backToTop);

                                            window.addEventListener('scroll', () => {
                                                if (window.pageYOffset > 300) {
                                                    backToTop.classList.add('visible');
                                                } else {
                                                    backToTop.classList.remove('visible');
                                                }
                                            });

                                            backToTop.addEventListener('click', (e) => {
                                                e.preventDefault();
                                                window.scrollTo({top: 0, behavior: 'smooth'});
                                            });

                                            // Product Image Loading Animation
                                            document.querySelectorAll('.product-image').forEach(img => {
                                                img.addEventListener('load', function () {
                                                    this.style.animation = 'fadeIn 0.5s ease-in';
                                                });
                                            });

                                            // Add to Cart Animation
                                            document.querySelectorAll('.btn-primary').forEach(btn => {
                                                btn.addEventListener('click', function () {
                                                    const icon = document.createElement('div');
                                                    icon.className = 'cart-animation';
                                                    icon.innerHTML = '<i class="fas fa-shopping-cart"></i>';
                                                    document.body.appendChild(icon);

                                                    setTimeout(() => {
                                                        icon.remove();
                                                    }, 1000);
                                                });
                                            });
                                        });

                                        // Price Format Helper
                                        function formatPrice(price) {
                                            return new Intl.NumberFormat('vi-VN', {
                                                style: 'currency',
                                                currency: 'VND'
                                            }).format(price);
                                        }
                                        function validateSearch() {
                                            const searchInput = document.querySelector('input[name="query"]');
                                            if (searchInput.value.trim().length < 2) {
                                                alert('Vui lòng nhập ít nhất 2 ký tự để tìm kiếm');
                                                return false;
                                            }
                                            return true;
                                        }
        </script>
    </body>
</html>
