<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="Model.CategoryTuan" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Website Header</title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
        <style>
            body {
                margin: 0;
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
            }

            .navbar {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                background-color: #fff;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                z-index: 1000;
                padding: 0px 20px;
                transition: top 0.3s;
            }

            .navbar .container {
                display: flex;
                justify-content: space-between;
                align-items: center;
                max-width: 1200px;
                margin: 0 auto;
                width: 100%;
                height: auto;
            }

            .navbar .logo img {
                width: 30px;
                height: 30px;
            }

            .navbar .menu {
                margin-top: 13px;
                display: flex;
                list-style: none;
                padding: 0;
                line-height: 10px;
            }

            .navbar .menu li {
                position: relative;
            }

            .navbar .menu a {
                text-decoration: none;
                color: #333;
                font-weight: 600;
                padding: 8px 12px;
                display: block;
                border-radius: 5px;
                transition: background-color 0.3s;
            }

            .navbar .menu a:hover {
                background-color: #ddd;
            }

            .navbar .dropdown-menu {
                display: none;
                position: absolute;
                top: 100%;
                left: 0;
                background-color: #fff;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
                z-index: 1000;
                padding: 10px;
                width: 200px;
            }

            .navbar .dropdown:hover .dropdown-menu {
                display: block;
            }

            .navbar .dropdown-menu a {
                padding: 8px 12px;
                color: #333;
                text-decoration: none;
                transition: background-color 0.3s;
            }

            .navbar .dropdown-menu a:hover {
                background-color: #f0f0f0;
            }

            .navbar .nav-icons {
                display: flex;
                gap: 20px;
                align-items: center;
            }

            .navbar .nav-icon {
                position: relative;
                cursor: pointer;
                font-size: 20px;
                color: #333;
            }

            .navbar .nav-icon .badge-count {
                position: absolute;
                top: -8px;
                right: -25px;
                background-color: red;
                color: white;
                padding: 2px 8px;
                border-radius: 50%;
                font-size: 12px;
            }

            /* Responsive Styles */
            @media (max-width: 768px) {
                .navbar .container {
                    flex-direction: column;
                    text-align: center;
                }

                .navbar .menu {
                    flex-direction: column;
                    gap: 10px;
                    margin-top: 10px;
                }

                .navbar .nav-icons {
                    flex-direction: column;
                    margin-top: 10px;
                }

                .navbar .menu li {
                    width: 100%;
                }

                .navbar .menu a {
                    padding: 10px 20px;
                }
            }
        </style>
    </head>

    <body>
        <nav class="navbar">
            <div class="container">
                <!-- Logo -->
                <a href="/RunnerShop/home?uid=${param.uid}" class="logo">
                    <img src="${pageContext.request.contextPath}/images/LOGO.png" alt="Logo">
                </a>

                <!-- Menu -->
                <ul class="menu">
                    <li><a href="/RunnerShop/home">Trang chủ</a></li>
                    <li><a href="/RunnerShop/About.jsp">Giới thiệu</a></li>
                    <li class="dropdown">
                        <a href="productlist" class="nav-link">Sản phẩm</a>
                        <div class="dropdown-menu">
                            <c:forEach var="category" items="${categories}">
                                <c:if test="${category.parentId == null}">
                                    <a href="/RunnerShop/productlist?category=${category.id}">${category.name}</a>
                                    <ul>
                                        <c:forEach var="subCategory" items="${categories}">
                                            <c:if test="${subCategory.parentId == category.id}">
                                                <li><a href="/RunnerShop/productlist?category=${subCategory.id}&uid=${param.uid}">${subCategory.name}</a></li>
                                            </c:if>
                                        </c:forEach>
                                    </ul>
                                </c:if>
                            </c:forEach>
                        </div>
                    </li>
                    <li><a href="/RunnerShop/PostListController?type=news&uid=${param.uid}">Tin tức</a></li>
                    <li><a href="Contact.jsp">Liên hệ</a></li>
                </ul>

                <!-- User Account & Cart -->
                <div class="nav-icons">
                    <div class="nav-icon dropdown">
                        <a href="#" data-bs-toggle="dropdown">
                            <i class="fas fa-user"></i>
                        </a>
                        <div class="dropdown-menu">
                            <c:choose>
                                <c:when test="${sessionScope.user == null}">
                                    <a class="dropdown-item" href="/RunnerShop/LoginControl">Đăng nhập</a>
                                    <a class="dropdown-item" href="/RunnerShop/RegisterControl">Đăng ký</a>
                                </c:when>
                                <c:otherwise>
                                    <a class="dropdown-item" href="#">Xin chào, ${sessionScope.user.userName}</a>
                                    <a class="dropdown-item" href="/RunnerShop/profile">Thông tin cá nhân</a>
                                    <c:if test="${sessionScope.role == 'Admin' || sessionScope.role == 'Marketing' || sessionScope.role == 'Saler'}">
                                        <a class="dropdown-item" style="font-weight: 600; color: #029ef3" href="/RunnerShop/admin">Trang Quản Lý</a>
                                    </c:if>
                                    <a class="dropdown-item" href="/RunnerShop/ChangePassword">Đổi mật khẩu</a>
                                    <a class="dropdown-item" href="/RunnerShop/LogOut">Đăng xuất</a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <a href="${pageContext.request.contextPath}/CartDetailServlet" class="nav-icon">
                        <i class="fas fa-shopping-cart"></i>
                        <c:if test="${cartQuantity != null}">
                            <span class="badge-count">${cartQuantity}</span>
                        </c:if>
                    </a>
                </div>
            </div>
        </nav>
    </body>
</html>

</html>
