<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<%@include file="Header.jsp" %>
<html>
    <head>
        <title>Trang chủ</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="format-detection" content="telephone=no">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="author" content="">
        <meta name="keywords" content="">
        <meta name="description" content="">
        <link rel="stylesheet" type="text/css" href="HieuPTM/Home/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="HieuPTM/Home/style.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@9/swiper-bundle.min.css" />
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Jost:wght@300;400;500&family=Lato:wght@300;400;700&display=swap" rel="stylesheet">
        <!-- script
        ================================================== -->
        <script src="js/modernizr.js"></script>
    </head>
    <body data-bs-spy="scroll" data-bs-target="#navbar" data-bs-root-margin="0px 0px -40%" data-bs-smooth-scroll="true" tabindex="0">


        <section id="billboard" class="position-relative overflow-hidden bg-light-blue">
            <div class="swiper main-swiper">
                <div class="swiper-wrapper">
                    <div class="swiper-slide">
                        <div class="" style="width: 100%; height: 50%;">
                            <!-- <img src="images/banner/banner.png" alt="banner" style="width: 100%; height: 100%;"> -->
                        </div>
                    </div>

                </div>
            </div>
        </section>
        <section id="company-services" class="padding-large">
            <div class="container">
                <div class="row">
                    <div class="col-lg-3 col-md-6 pb-3">
                        <div class="icon-box d-flex">
                            <div class="icon-box-icon pe-3 pb-3">
                                <svg class="cart-outline">
                                <use xlink:href="#cart-outline" />
                                </svg>
                            </div>
                            <div class="icon-box-content">
                                <h3 class="card-title text-uppercase text-dark">Free delivery</h3>
                                <!--<p>Consectetur adipi elit lorem ipsum dolor sit amet.</p>-->
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6 pb-3">
                        <div class="icon-box d-flex">
                            <div class="icon-box-icon pe-3 pb-3">
                                <svg class="quality">
                                <use xlink:href="#quality" />
                                </svg>
                            </div>
                            <div class="icon-box-content">
                                <h3 class="card-title text-uppercase text-dark">Quality guarantee</h3>
                                <!--<p>Dolor sit amet orem ipsu mcons ectetur adipi elit.</p>-->
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6 pb-3">
                        <div class="icon-box d-flex">
                            <div class="icon-box-icon pe-3 pb-3">
                                <svg class="price-tag">
                                <use xlink:href="#price-tag" />
                                </svg>
                            </div>
                            <div class="icon-box-content">
                                <h3 class="card-title text-uppercase text-dark">Daily offers</h3>
                                <!--<p>Amet consectetur adipi elit loreme ipsum dolor sit.</p>-->
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6 pb-3">
                        <div class="icon-box d-flex">
                            <div class="icon-box-icon pe-3 pb-3">
                                <svg class="shield-plus">
                                <use xlink:href="#shield-plus" />
                                </svg>
                            </div>
                            <div class="icon-box-content">
                                <h3 class="card-title text-uppercase text-dark">100% secure payment</h3>
                                <!--<p>Rem Lopsum dolor sit amet, consectetur adipi elit.</p>-->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <section id="best-seller" class="product-store padding-large position-relative">
            <div class="container">
                <div class="row">
                    <div class="display-header d-flex justify-content-between pb-3">
                        <h2 class="display-7 text-dark text-uppercase">Sản phẩm bán chạy</h2>
                        <div class="btn-right">
                            <!--<a href="pshow?uid=${user.userName}&index=1" class="btn btn-medium btn-normal text-uppercase">Go to Shop</a>-->
                        </div>
                    </div>
                    <div class="swiper product-swiper">
                        <div class="swiper-wrapper">
                            <c:forEach items="${bslist}" var="o">
                                <div class="swiper-slide">
                                    <div class="product-card position-relative">
                                        <div class="image-holder">
                                            <img src="${o.img}" alt="product-item" class="img-fluid">
                                        </div>
                                        <div class="cart-concern position-absolute">
                                            <div class="cart-button d-flex">
                                                <a href="addcart?uid=${user.userName}&pid=${o.id}&quantity=1" class="btn btn-medium btn-black">
                                                    Add to Cart
                                                    <svg class="cart-outline">
                                                    <use xlink:href="#cart-outline"></use>
                                                    </svg>
                                                </a>
                                            </div>
                                        </div>
                                        <div class="card-detail d-flex justify-content-between align-items-baseline pt-3">
                                            <h3 class="card-title text-uppercase">
                                                <a href="pdetail?uid=${user.userName}&pid=${o.id}">${o.name}</a>
                                            </h3>
                                            <span class="item-price text-primary">${o.price}đ</span>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="swiper-pagination position-absolute text-center"></div>
                    </div>
                </div>
            </div>

        </section>
        <section id="last-items" class="product-store padding-large position-relative">
            <div class="container">
                <div class="row">
                    <div class="display-header d-flex justify-content-between pb-3">
                        <h2 class="display-7 text-dark text-uppercase">Sản phẩm mới</h2>
                        <div class="btn-right">
                            <!--<a href="pshow?uid=${user.userName}&index=1" class="btn btn-medium btn-normal text-uppercase">Go to Shop</a>-->
                        </div>
                    </div>
                    <div class="swiper product-watch-swiper">
                        <div class="swiper-wrapper">
                            <c:forEach items="${lastlist}" var="o">
                                <div class="swiper-slide">
                                    <div class="product-card position-relative">
                                        <div class="image-holder">
                                            <img src="${o.img}" alt="product-item" class="img-fluid">
                                        </div>
                                        <div class="cart-concern position-absolute">
                                            <div class="cart-button d-flex">
                                                <a href="addcart?uid=${user.userName}&pid=${o.id}&quantity=1" class="btn btn-medium btn-black">
                                                    Add to Cart
                                                    <svg class="cart-outline">
                                                    <use xlink:href="#cart-outline"></use>
                                                    </svg>
                                                </a>
                                            </div>
                                        </div>
                                        <div class="card-detail d-flex justify-content-between align-items-baseline pt-3">
                                            <h3 class="card-title text-uppercase">
                                                <a href="pdetail?uid=${user.userName}&pid=${o.id}">${o.name}</a>
                                            </h3>
                                            <span class="item-price text-primary">${o.price}đ</span>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>

                </div>

            </div>
            <div class="swiper-pagination position-absolute text-center"></div>

        </section>
        <section id="good-rate" class="product-store padding-large position-relative">
            <div class="container">
                <div class="row">
                    <div class="display-header d-flex justify-content-between pb-3">
                        <h2 class="display-7 text-dark text-uppercase">Sản phẩm nổi gần đây</h2>
                        <div class="btn-right">
                            <!--<a href="pshow?uid=${user.userName}&index=1" class="btn btn-medium btn-normal text-uppercase">Go to Shop</a>-->
                        </div>
                    </div>
                    <div class="swiper product-watch-swiper">
                        <div class="swiper-wrapper">
                            <c:forEach items="${ratelist}" var="o">
                                <div class="swiper-slide">
                                    <div class="product-card position-relative">
                                        <div class="image-holder">
                                            <img src="${o.img}" alt="product-item" class="img-fluid">
                                        </div>
                                        <div class="cart-concern position-absolute">
                                            <div class="cart-button d-flex">
                                                <a href="addcart?uid=${user.userName}&pid=${o.id}&quantity=1" class="btn btn-medium btn-black">
                                                    Add to Cart
                                                    <svg class="cart-outline">
                                                    <use xlink:href="#cart-outline"></use>
                                                    </svg>
                                                </a>
                                            </div>
                                        </div>
                                        <div class="card-detail d-flex justify-content-between align-items-baseline pt-3">
                                            <h3 class="card-title text-uppercase">
                                                <a href="pdetail?uid=${user.userName}&pid=${o.id}">${o.name}</a>
                                            </h3>
                                            <span class="item-price text-primary">${o.price}đ</span>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
            <div class="swiper-pagination position-absolute text-center"></div>
        </section>
                            
    </body>
</html>