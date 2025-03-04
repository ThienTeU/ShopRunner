<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="Model.CategoryTuan" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />

        <!-- Google Fonts -->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet" />

        <!-- Bootstrap 5 -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

        <!-- FontAwesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />

        <title>HEADER DEMO</title>
    </head>

    <body style="min-height: 2000px;">

        <!-- Navbar Chính -->
        <!-- Navbar Chính -->
        <!-- Navbar Chính -->
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container">
                <!-- Logo -->
                <a>
                    <img src="../images/LOGO.png"/>
                </a>

                <!-- Nút Toggle -->
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse justify-content-center" id="navbarNav">
                    <!-- Menu chính giữa -->
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item"><a class="nav-link active" href="/RunnerShop/home">Trang chủ</a></li>
                        <li class="nav-item"><a class="nav-link" href="/RunnerShop/About.jsp">Giới thiệu</a></li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="productDropdown" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">
                                Sản phẩm
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="productDropdown">
                                <c:forEach items="${categories}" var="category">
                                    <li><a class="dropdown-item" href="/RunnerShop/productlist?category=${category.id}">${category.name}</a></li>
                                    </c:forEach>
                            </ul>
                        </li>                       
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="newsDropdown" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">
                                Tin tức
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="newsDropdown">
                                <li><a class="dropdown-item" href="/RunnerShop/PostListController?type=news">Tin tức mới</a></li>
                                <li><a class="dropdown-item" href="/RunnerShop/PostListController?type=promotion">Khuyến mãi</a></li>
                                <li><a class="dropdown-item" href="/RunnerShop/PostListController?type=event">Sự kiện</a></li>
                            </ul>
                        </li>
                        <li class="nav-item"><a class="nav-link" href="#footer">Liên hệ</a></li>


                    </ul>

                    <!-- Giỏ hàng bên phải -->
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="accountDropdown" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">
                                Tài khoản
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="accountDropdown">
                                <li><a class="dropdown-item" href="#">Đăng nhập</a></li>
                                <li><a class="dropdown-item" href="#">Đăng ký</a></li>
                                <li><a class="dropdown-item" href="#">Yêu thích <span class="wishlist-count">0</span></a></li>
                            </ul>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">
                                <i class="fas fa-shopping-cart"></i>
                                <span class="cart-count">0</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>



        <!-- Banner -->
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
                <input class="form-control me-2" type="search" name="query" placeholder="Tìm kiếm sản phẩm..." value="${param.query}">
                <button class="btn btn-danger" type="submit"><i class="fas fa-search"></i></button>
            </form>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>

    <style>
        .category-list {
            list-style: none;
            padding: 0;
            display: flex;
            gap: 20px;
        }
        .category-item {
            text-align: center;
            text-decoration: none;
            color: #333;
            display: flex;
            flex-direction: column;
            align-items: center;
            transition: transform 0.3s ease;
        }
        .category-item img {
            width: 60px;
            height: 60px;
            border-radius: 10px;
            object-fit: cover;
        }
        .category-item span {
            margin-top: 5px;
            font-size: 14px;
            font-weight: bold;
        }
        .category-item:hover {
            transform: scale(1.1);
            color: #ff6600;
        }
        .active {
            color: #ff6600;
            font-weight: bold;
        }
    </style>

</html>
