<%-- 
    Document   : CartDetailJSP
    Created on : Jan 25, 2025, 10:28:58 AM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLDecoder" %>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/js/select2.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/css/select2.min.css" rel="stylesheet" />
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cart Details</title>
        <link crossorigin="anonymous" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" 
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" rel="stylesheet" />
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="./CartDetail/CartDetail.css">
    </head>

    <body>
        <div class="row">
            <div class="col-md-8">
                <c:choose>
                    <c:when test="${empty sessionScope.cart}">
                        <div class="alert alert-warning text-center">Giỏ hàng của bạn đang trống <a class="fw-bold" href="productlist">MUA NGAY</a></div>
                    </c:when>
                    <c:otherwise>
                        <h2 class="fw-bold">GIỎ HÀNG CỦA BẠN</h2>
                        <p>TỔNG CỘNG (${sessionScope.cart.size()} các sản phẩm) <span class="fw-bold"><span class="productPrice">${total}</span></span></p>
                        <p class="text-muted">Các mặt hàng trong giỏ hàng của bạn không được bảo lưu — hãy kiểm tra ngay để đặt hàng.</p>

                        <div class="alert alert-light border">
                            Để đảm bảo các bưu phẩm có thể được giao trước Tết Nguyên Đán, vui lòng đặt hàng trước ngày 20/01 cho địa 
                            chỉ trong TP.HCM và trước ngày 17/01 cho các tỉnh/thành phố khác. <a class="fw-bold" href="productlist">MUA NGAY</a>
                        </div>

                        <div class="row">
                            <c:forEach var="item" items="${cartItemsDTO}">
                                <div class="col-md-8 mb-3" style="width: 100%">
                                    <div class="card p-3 position-relative">
                                        <!-- Nút X để xóa -->
                                        <a href="RemoveFromCartServlet?product_id=${item.product.product_id}&productprice_id=${item.productPrice.productPrice_id}&productquantity_id=${item.productQuantity.productQuantity_id}"
                                           class="position-absolute top-0 end-0 m-2 text-danger fs-4">
                                            <i class="fas fa-times"></i>
                                        </a>

                                        <div class="row g-3 align-items-center">
                                            <div class="col-md-3">
                                                <a style="text-decoration: none; color: black" href="ProductDetailServlet?product_id=${item.product.product_id}&color_id=${item.productPrice.color_id}">
                                                    <img src="Image2/productID_${item.product.product_id}/colorID_${item.productPrice.color_id}/image_1.avif" 
                                                         class="img-fluid rounded" alt="">
                                                </a>
                                            </div>
                                            <div class="col-md-6">
                                                <a style="text-decoration: none; color: black" href="ProductDetailServlet?product_id=${item.product.product_id}&color_id=${item.productPrice.color_id}">
                                                    <h6 class="fw-bold">${item.product.product_name}</h6>
                                                </a>
                                                <p class="text-muted">
                                                    <c:forEach items="${listColor}" var="c">
                                                        <c:if test="${item.productPrice.color_id == c.color_id}">
                                                            <span style="vertical-align: middle;width: 20px;height: 20px;border-radius: 50px; display: inline-block !important;background-color: ${c.color};"></span>
                                                        </c:if>

                                                    </c:forEach>

                                                    | KÍCH CỠ:
                                                    <c:forEach items="${listSize}" var="s">
                                                        <c:if test="${item.productQuantity.size_id == s.size_id}">
                                                            ${s.size}
                                                        </c:if>
                                                    </c:forEach> </p>
                                                <p class="fw-bold"><span class="productPrice">${item.productPrice.price}</span></p>
                                                <!-- Moved select here -->
                                                <select class="form-select selectWithScroll" name="quantity" style="width: 50px;"
                                                        onchange="window.location.href = 'UpdateCart?product_id=${item.product.product_id}&productprice_id=${item.productPrice.productPrice_id}&productquantity_id=${item.productQuantity.productQuantity_id}&quantity=' + this.value;">
                                                    <c:forEach var="i" begin="1" end="${item.productQuantity.quantity}">
                                                        <option value="${i}" ${item.quantity == i ? 'selected' : ''}>${i}</option>
                                                    </c:forEach>
                                                </select>


                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                    </c:otherwise>
                </c:choose>
            </div>

            <div class="col-md-4" style="position: fixed; right: 0;">
                <div class="order-summary p-3 border rounded">
                    <h5>TÓM TẮT ĐƠN HÀNG</h5>
                    <div class="d-flex justify-content-between">
                        <p>${sessionScope.cart.size()} các sản phẩm</p>
                        <span class="productPrice">${total}</span>
                    </div>
                    <div class="d-flex justify-content-between">
                        <p>Giao hàng</p>
                        <span id="ship" class="productPrice">${sessionScope.cart.size() == 0 ? "0" : "70000"}</span>
                    </div>
                    <hr>
                    <div class="d-flex justify-content-between fw-bold">
                        <p>Tổng</p>
                        <span class="productPrice">${sessionScope.cart.size() == 0 ? "0" : total + 70000}</span>
                    </div>
                    <c:if test="${sessionScope.cart.size() != 0}">
                        <p class="text-muted">(Đã bao gồm thuế <span class="productPrice">79259</span>)</p>
                    </c:if>
                    <a href="#" class="text-primary">SỬ DỤNG MÃ KHUYẾN MÃI</a>
                    <a class="btn btn-dark w-100 mt-3" href="CheckOutServlet" id="checkout-button">THANH TOÁN</a>
                </div>
            </div>
        </div>
    </body>
</html>

<script>
    // Hàm kiểm tra số lượng sản phẩm trong giỏ hàng
    function checkCartItems() {
        // Lấy số lượng sản phẩm trong giỏ hàng
        var cartSize = ${sessionScope.cart.size()};  // Hoặc thay thế bằng cách lấy giá trị từ một phần tử DOM

        // Lấy nút thanh toán
        var checkoutButton = document.getElementById("checkout-button");

        // Kiểm tra số lượng sản phẩm
        if (cartSize === 0) {
            // Nếu giỏ hàng trống, vô hiệu hóa nút thanh toán
            checkoutButton.disabled = true;
            checkoutButton.style.opacity = 0.5; // Thêm hiệu ứng mờ cho nút
            checkoutButton.style.cursor = "not-allowed"; // Thay đổi con trỏ chuột

            // Ngăn chặn hành động nhấn nút (nếu nút vẫn có thể nhấn được)
            checkoutButton.addEventListener("click", function (event) {
                event.preventDefault();  // Ngừng hành động mặc định (chuyển hướng)
            });
        } else {
            // Nếu có sản phẩm trong giỏ, kích hoạt nút thanh toán
            checkoutButton.disabled = false;
            checkoutButton.style.opacity = 1; // Khôi phục độ sáng cho nút
            checkoutButton.style.cursor = "pointer"; // Thay đổi con trỏ chuột về bình thường

            // Đảm bảo sự kiện click sẽ không bị chặn nếu giỏ hàng có sản phẩm
            checkoutButton.removeEventListener("click", function (event) {
                event.preventDefault(); // Hủy bỏ sự kiện chặn click cũ
            });
        }
    }

// Gọi hàm khi trang được tải hoặc khi giỏ hàng thay đổi
    window.onload = checkCartItems;


    $(document).ready(function () {
        $(".selectWithScroll").select2({
            dropdownAutoWidth: true,
            width: '50px'
        });
    });
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