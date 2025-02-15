<%-- 
    Document   : CheckOutJSP
    Created on : Feb 14, 2025, 11:37:48 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
        <title>Checkout</title>
        <link rel="stylesheet" href="CheckOutStyle.css">
    </head>
    <body class="bg-light text-dark">
        <c:if test="${sessionScope.cart.size() == 0 || sessionScope.cart == null }">
            <c:redirect url="${sessionScope.contextPath}/CartDetailServlet"></c:redirect>
        </c:if>
        <div class="container custom-container">
            <div class="text-center mb-4">
                <h1 class="h4 font-weight-bold">THANH TOÁN</h1>
                <p class="text-muted"><a style="color: #6C757D; text-decoration: none" href="CartDetailServlet">(${sessionScope.cart.size()} các sản phẩm)</a> <span class="productPrice">${total}</span> </p>
            </div>
            <div class="row">
                <!-- Left Column -->
                <div class="col-lg-8">
                    <!-- Contact Section -->
                    <div class="mb-4">
                        <h2 class="section-title">LIÊN HỆ</h2>
                        <div class="form-group">
                            <label class="form-label" for="contactEmail">Email *</label>
                            <div class="input-group">
                                <input class="form-control" id="contactEmail" type="email" value="duonghieu294@gmail.com"/>
                                <div class="input-group-append">
                                    <span class="input-group-text"><i class="fas fa-check text-success"></i></span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Address Section -->
                    <div class="mb-4">
                        <h2 class="section-title">ĐỊA CHỈ</h2>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label class="form-label" for="firstName">Họ</label>
                                <input class="form-control" id="firstName" placeholder="" type="text"/>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="form-label" for="lastName">Tên</label>
                                <input class="form-control" id="lastName" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="streetAddress">Số Đường/Tên Đường *</label>
                            <input class="form-control" id="streetAddress" placeholder="" type="text"/>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="buildingName">Tên Tòa Nhà/Số Nhà</label>
                            <input class="form-control" id="buildingName" placeholder="" type="text"/>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label class="form-label" for="provinceSelect">Tỉnh</label>
                                <select class="form-control" id="provinceSelect">
                                    <option>Chọn Tỉnh</option>
                                </select>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="form-label" for="districtSelect">District</label>
                                <select class="form-control" id="districtSelect">
                                    <option>Chọn Huyện/Quận</option>
                                    <option>Select here</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label class="form-label" for="wardInput">Xã/Phường</label>
                                <input class="form-control" id="wardInput" type="text"/>
                            </div>

                        </div>
                        <div class="form-group">
                            <label class="form-label" for="phoneNumber">Số Điện Thoại</label>
                            <input class="form-control" id="phoneNumber" placeholder="" type="text"/>
                        </div>
                        <div class="form-group form-check">
                            <input class="form-check-input" id="billingSame" type="checkbox"/>
                            <label class="form-check-label" for="billingSame">Thông tin hóa đơn / thuế và thông tin giao hàng của tôi là giống nhau.</label>
                        </div>                   
                        <div class="form-group form-check">
                            <input class="form-check-input" id="ageConfirmation" type="checkbox"/>
                            <label class="form-check-label" for="ageConfirmation">Vâng, tôi trên 16 tuổi.</label>
                        </div>
                        <div class="form-group form-check">
                            <input class="form-check-input" id="privacyConsent" type="checkbox"/>
                            <label class="form-check-label" for="privacyConsent">Tôi xin đồng ý cho việc chuyển nhượng, chia sẻ, sử dụng, thu thập và tiết lộ dữ liệu cá nhân của tôi cho các bên thứ ba như được nêu trong <a class="privacy-link" href="#">Chính sách Bảo mật của RGS</a>.</label>
                        </div>
                        <div class="form-group form-check">
                            <input class="form-check-input" id="termsConditions" type="checkbox"/>
                            <label class="form-check-label" for="termsConditions">Tôi đã đọc, hiểu và chấp nhận <a class="privacy-link" href="#">Thông báo Bảo mật</a> và <a class="privacy-link" href="#">Điều khoản và Điều kiện</a>.</label>
                        </div>
                        <button class="btn btn-dark mt-3">TIẾP TỤC <i class="fas fa-arrow-right ml-2"></i></button>
                    </div>
                </div>
                <!-- Right Column -->
                <div class="col-lg-4">
                    <h2 class="section-title">GIỎ HÀNG CỦA BẠN</h2>
                    <div class="d-flex justify-content-between mb-2">
                        <span>${sessionScope.cart.size()} các sản phẩm</span>
                        <span class="productPrice">${total}</span>
                    </div>
                    <div class="d-flex justify-content-between mb-2">
                        <span>Delivery</span>
                        <span>70,000₫</span>
                    </div>
                    <div class="d-flex justify-content-between mb-2 total-price">
                        <span style="font-weight: 700">Total</span>
                        <span style="font-weight: 700" class="productPrice">${total + 70000}</span>
                    </div>
                    <p class="text-muted small mb-4">[Bao gồm thuế 79,259₫]</p>
                    <a class="privacy-link mb-4 d-block" href="#">Sử dụng mã giảm giá</a>
                    <hr>
                    <c:forEach items="${cartItemsDTO}" var="item">
                        <div class="d-flex mb-4">
                            <a style="text-decoration: none; color: black" href="ProductDetailServlet?product_id=${item.product.product_id}&color_id=${item.productPrice.color_id}">
                                <img alt="${item.product.product_name}" class="cart-item-image" 
                                     src="Image2/productID_${item.product.product_id}/colorID_${item.productPrice.color_id}/image_1.avif"
                                     style="width: 110px;height: 110px;object-fit: cover"/>
                                <div class="ml-3">
                            </a>
                            <a style="text-decoration: none; color: black" href="ProductDetailServlet?product_id=${item.product.product_id}&color_id=${item.productPrice.color_id}">
                                <p style="margin: 0; font-weight: 500">${item.product.product_name}</p>
                            </a>
                            <span class="productPrice">${item.productPrice.price}</span>
                            <p style="margin: 0" class="small text-muted">

                                Kích cỡ: 
                                <c:forEach items="${listSize}" var="s">
                                    <c:if test="${item.productQuantity.size_id == s.size_id}">
                                        ${s.size}
                                    </c:if>
                                </c:forEach> 
                                / Quantity: ${item.quantity}
                            </p> 
                            <p class="small text-muted">
                                Màu sắc: 
                                <c:forEach items="${listColor}" var="c">
                                    <c:if test="${item.productPrice.color_id == c.color_id}">
                                        <span style="vertical-align: middle;width: 13px;height: 13px;border-radius: 50px; display: inline-block !important;background-color: ${c.color};"></span>
                                    </c:if>
                                </c:forEach>
                            </p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            let priceElements = document.querySelectorAll(".productPrice");
            priceElements.forEach(function (element) {
                let price = parseFloat(element.textContent);
                if (!isNaN(price)) {
                    element.textContent = price.toLocaleString("vi-VN") + "₫";
                }
            });
        });
    </script>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
