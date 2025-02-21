<%-- 
    Document   : header
    Created on : Feb 14, 2025, 11:14:44 AM
    Author     : anhco
--%>
<%@page import="Model.Category" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="DAL.CategoryDAO"%>
<%@ page import="java.util.ArrayList" %>



<!DOCTYPE html>
<%
    ArrayList<Category> categoryList = (ArrayList<Category>) request.getAttribute("categoryList");
    ArrayList<Category> categoriesNam = (ArrayList<Category>) request.getAttribute("categoriesNam");
    ArrayList<Category> categoriesNu = (ArrayList<Category>) request.getAttribute("categoriesNu");
%>
<html lang="en">

    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />

        <!-- link Fonts -->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet"/>
        <!--BOOTSTRAP5-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!--FONTAWESOME-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" integrity="sha512-iBBXm8fW90+nuLcSKlbmrPcLa0OT92xO1BIsZ+ywDWZCvqsWgccV3gFoRBv0z+8dLJgyAHIhR35VZc2oM/gI1w==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <!-- CSS -->

        <title>HEADER DEMO</title>
    </head>
    <body style="min-height: 2000px;">
        <div class="row m-0 p-0" style="background-color:lightgrey;">
            <span id="promo">
                <marquee behavior="scroll" direction="left">BLACK FRIDAY! SALE 0%</marquee>
            </span>
        </div>
        <nav class="navbar main-navbar navbar-expand-lg navbar-light bg-light ">

            <div class="container-fluid">
                <!-- LOGO -->
                <a class="navbar-brand col-lg-2 offset-lg-2 logo" href="#"><img src="resources/img/.png"> </a>

                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <!-- SEARCHBAR -->
                    <form class="d-flex searchbar">
                        <input class="form-control me-2" type="search" placeholder="Search entire store here..." aria-label="Search">
                        <button class="btn btn-danger" type="submit"><i class="fas fa-search" style="font-size: 100%;"></i></button>
                    </form>
                    <ul class="navbar-nav me-auto ms-auto">                
                        <!-- EXPANDED -->
                        <li class="nav-item d-none d-lg-block">
                            <a class="nav-link" href="#"><i class="fas fa-bell"></i>
                                <span class="position-relative translate-middle badge rounded-pill bg-danger">
                                    0
                                    <span class="visually-hidden">unread notifications</span>
                                </span>
                            </a> 
                        </li>
                        <li class="nav-item d-none d-lg-block">
                            <a class="nav-link" href="#">
                                <i class="fas fa-shopping-cart"></i>
                                <span class="position-relative translate-middle badge rounded-pill bg-danger">
                                    0
                                    <span class="visually-hidden">cart items</span>
                                </span>
                            </a>
                        </li>
                        <li class="nav-item dropdown d-none d-lg-block">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="fas fa-user-circle"></i>
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="#">Action</a></li>
                                <li><a class="dropdown-item" href="#">Another action</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="#">Something else here</a></li>
                            </ul>
                        </li>
                        <!-- COLLAPSED -->
                        <li class="nav-item d-block d-lg-none">
                            <a class="nav-link active" aria-current="page" href="#">Trang chủ</a>
                        </li>
                        <li class="nav-item dropdown d-block d-lg-none">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Sản Phẩm Thể Thao
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="#">Action</a></li>
                                <li><a class="dropdown-item" href="#">Another action</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="#">Something else here</a></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown d-block d-lg-none">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Nữ
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="#">Action</a></li>
                                <li><a class="dropdown-item" href="#">Another action</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="#">Something else here</a></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown d-block d-lg-none">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Nam
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="#">Action</a></li>
                                <li><a class="dropdown-item" href="#">Another action</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="#">Something else here</a></li>
                            </ul>
                        </li>
                        <li class="nav-item d-block d-lg-none">
                            <a class="nav-link" href="#">Cộng Đồng</a>
                        </li>
                        <li class="nav-item d-block d-lg-none">
                            <a class="nav-link" href="#footer">Liện Hệ</a>
                        </li>
                        <li class="nav-item d-block d-lg-none">
                            <a class="nav-link d-inline-block" href="#"><i class="fas fa-bell"></i>
                                <span class="position-relative translate-middle badge rounded-pill bg-dark">
                                    0
                                    <span class="visually-hidden">unread notifications</span>
                                </span>
                            </a> 
                            <a class="nav-link d-inline-block" href="#">
                                <i class="fas fa-shopping-cart"></i>
                                <span class="position-relative translate-middle badge rounded-pill bg-dark">
                                    0
                                    <span class="visually-hidden">cart items</span>
                                </span>
                            </a>
                            <i class="fas fa-user-circle"></i>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- SECONDARY NAVBAR -->
        <nav id="navbar2" class="navbar navbar-expand-lg navbar-dark bg-dark d-none d-lg-block">
            <div class="container-fluid">
                <ul class="navbar-nav offset-2 me-auto mb-2 mb-lg-0">
                    <li class="nav-item me-4">
                        <a class="nav-link active" aria-current="page" href="#">Trang chủ</a>
                    </li>
                    <li class="nav-item dropdown me-4">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Sản Phẩm Thể Thao
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <% if (categoryList != null) { %>
                            <% for (Category category : categoryList) { %>
                            <li><a class="dropdown-item" href="#"> <%= category.getName() %> </a></li>
                                <% } %>
                                <% } else { %>
                            <li><a class="dropdown-item" href="#">Không có danh mục nào</a></li>
                                <% } %>
                        </ul>
                    </li>
                    <!-- Danh mục Nữ -->
                    <li class="nav-item dropdown me-4">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownNu" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Nữ
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdownNu">
                            <% if (categoriesNu != null) { %>
                            <% for (Category category : categoriesNu) { %>
                            <li><a class="dropdown-item" href="#"><%= category.getName() %></a></li>
                                <% } %>
                                <% } else { %>
                            <li><a class="dropdown-item" href="#">Không có danh mục</a></li>
                                <% } %>
                        </ul>
                    </li>
                    <li class="nav-item dropdown me-4">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownNam" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Nam
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdownNam">
                            <% if (categoriesNam != null) { %>
                            <% for (Category category : categoriesNam) { %>
                            <li><a class="dropdown-item" href="#"><%= category.getName() %></a></li>
                                <% } %>
                                <% } else { %>
                            <li><a class="dropdown-item" href="#">Không có danh mục</a></li>
                                <% } %>
                        </ul>
                    </li>
                    <li class="nav-item me-4">
                        <a class="nav-link" href="#">Cộng Đồng</a>
                    </li>
                    <li class="nav-item me-4">
                        <a class="nav-link" href="#footer">Liên Hệ</a>
                    </li>
                </ul>
            </div>
        </nav>
        <!-- BOOTSTRAP5-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <!-- SCRIPT -->
        <script src="js/script.js"></script>
    </body>
</html>
