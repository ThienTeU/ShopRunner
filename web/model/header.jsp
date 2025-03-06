<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="Model.CategoryTuan" %>


<nav class="navbar navbar-expand-lg navbar-light">
    <div class="container">
        <!-- Logo -->
        <a class="navbar-brand" href="/RunnerShop/home">
            <img src="../images/LOGO.png" alt="Runner Shop Logo"/>
        </a>

        <!-- Toggle Button -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Navbar Items -->
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav mx-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="/RunnerShop/home">Trang chủ</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="/RunnerShop/About.jsp">Giới thiệu</a>
                </li>

<!-- Products Dropdown -->
<li class="nav-item dropdown position-static">
  <a class="nav-link dropdown-toggle" href="#" id="productDropdown" role="button"
     data-bs-toggle="dropdown" aria-expanded="false">
    <i class="fas fa-store me-1"></i> Sản phẩm
  </a>
  <div class="dropdown-menu mega-menu" aria-labelledby="productDropdown">
    <div class="mega-menu-header mb-4">
      <h5 class="mega-menu-title">Danh mục sản phẩm</h5>
      <p class="mega-menu-subtitle">Khám phá các sản phẩm chất lượng của chúng tôi</p>
    </div>
    <ul class="category-menu">
      <c:forEach var="category" items="${categories}">
        <c:if test="${category.parentId == null}">
          <li class="category-item">
            <a href="/RunnerShop/productlist?category=${category.id}" class="category-link main-category">
              <i class="fas fa-angle-right category-icon"></i>
              ${category.name}
            </a>
            <ul class="sub-menu">
              <c:forEach var="subCategory" items="${categories}">
                <c:if test="${subCategory.parentId == category.id}">
                  <li class="category-item">
                    <a href="/RunnerShop/productlist?category=${subCategory.id}" class="category-link sub-category">
                      ${subCategory.name}
                      <c:if test="${hasChildren}">
                        <i class="fas fa-chevron-right submenu-icon"></i>
                      </c:if>
                    </a>
                    <ul class="sub-menu">
                      <c:forEach var="subSubCategory" items="${categories}">
                        <c:if test="${subSubCategory.parentId == subCategory.id}">
                          <li class="category-item">
                            <a href="/RunnerShop/productlist?category=${subSubCategory.id}" class="category-link sub-sub-category">
                              ${subSubCategory.name}
                            </a>
                          </li>
                        </c:if>
                      </c:forEach>
                    </ul>
                  </li>
                </c:if>
              </c:forEach>
            </ul>
          </li>
        </c:if>
      </c:forEach>
    </ul>
    <div class="mega-menu-footer mt-4">
      <a href="/RunnerShop/productlist" class="view-all-link">
        Xem tất cả sản phẩm <i class="fas fa-arrow-right ms-1"></i>
      </a>
    </div>
  </div>
</li>


                <!-- News Dropdown -->
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

                <li class="nav-item">
                    <a class="nav-link" href="#footer">Liên hệ</a>
                </li>
            </ul>

            <!-- Right Icons -->
            <div class="d-flex align-items-center">
                <!-- User Account -->
                <div class="dropdown">
                    <a class="nav-link nav-icon" href="#" id="userDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="fas fa-user"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
                        <li><a class="dropdown-item" href="/RunnerShop/login">Đăng nhập</a></li>
                        <li><a class="dropdown-item" href="/RunnerShop/register">Đăng ký</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li>
                            <a class="dropdown-item" href="/RunnerShop/wishlist">
                                Yêu thích <span class="badge bg-danger ms-2">0</span>
                            </a>
                        </li>
                    </ul>
                </div>

                <!-- Shopping Cart -->
                <a class="nav-link nav-icon" href="/RunnerShop/cart">
                    <i class="fas fa-shopping-cart"></i>
                    <span class="badge-count">0</span>
                </a>
            </div>
        </div>
    </div>
</nav>

