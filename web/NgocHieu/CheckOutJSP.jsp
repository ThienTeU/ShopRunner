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
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <title>Checkout</title>
        <link rel="stylesheet" href="NgocHieu/CheckOutStyle.css">
    </head>
    
    <body class="bg-light text-dark">
            <%@ include file="/model/header.jsp" %>
        <c:if test="${cartItemsDTO.size() == 0 || cartItemsDTO == null }">
            <c:redirect url="CartDetailServlet"></c:redirect>
        </c:if> 
        
        <div class="container custom-container" style="margin-top: 0; padding-top: 70px">
            <div class="text-center mb-4">
                <Strong class="h2 font-weight-bold">THANH TOÁN</Strong>
                <p class="text-muted"><a style="color: #6C757D; text-decoration: none" href="CartDetailServlet">(${sessionScope.cart.size()} các sản phẩm)</a> <span class="productPrice">${total}</span> </p>
            </div>
            <div class="row">
                <!-- Left Column -->
                <div class="col-lg-7">
                    <!-- Contact Section -->
                    <form id="checkoutForm" action="CheckOutServlet" method="POST">
                        <div class="mb-4">
                            <strong class="h4 section-title">LIÊN HỆ</strong>
                            <div class="form-group">
                                <label class="form-label" for="contactEmail">Email<span style="color: red">*</span></label>
                                <div class="input-group" style="align-items: center">
                                    <input name="email" style="position: relative" class="form-control" id="contactEmail" placeholder="Nhập email" type="email" value="" required/>
                                    <i id="emailValid" style="position: absolute; right: 10px; display: none" class="fas fa-check text-success"></i>
                                </div>
                                <span id="invalidEmail" style="color: red; font-size: 15px;display: none; margin-top: 5px" >Địa chỉ email không hợp lệ. Vui lòng nhập một địa chỉ email hợp lệ.</span>
                            </div>
                        </div>
                        <hr>
                        <!-- Address Section -->
                        <div class="mb-4">
                            <h2 class="h4 section-title">ĐỊA CHỈ</h2>
                            <div class="form-row">
                                <div style="align-items: center" class="form-group col-md-6">
                                    <label class="form-label" for="fullName">Họ Và Tên<span style="color: red">*</span></label>
                                    <div class="input-group" style="align-items: center">
                                        <input name="name" style="position: relative" class="form-control" placeholder="Nhập họ và tên" id="fullName" type="text" value="" required/>
                                        <i id="nameValid" style="position: absolute; right: 10px; display: none" class="fas fa-check text-success"></i>
                                    </div>
                                    <span id="invalidName" style="color: red; font-size: 15px;display: none; margin-top: 5px" >Họ và tên chỉ được chứa chữ cái và dấu tiếng Việt.</span>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="form-label" for="phoneNumber">Số Điện Thoại<span style="color: red">*</span></label>
                                    <div class="input-group" style="align-items: center">
                                        <input name="phone" style="position: relative" class="form-control" placeholder="Nhập số điện thoại" id="phoneNumber" type="text" value="" required/>
                                        <i id="phoneValid" style="position: absolute; right: 10px; display: none" class="fas fa-check text-success"></i>
                                    </div>
                                    <span id="invalidPhone" style="color: red; font-size: 15px;display: none; margin-top: 5px" >Số điện thoại bắt đầu bằng 03-09 và có 10 số</span>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label class="form-label" for="city">Tỉnh<span style="color: red">*</span></label>
                                    <select name="city" class="form-control" id="city" required>
                                        <option>Chọn Tỉnh</option>
                                    </select>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="form-label" for="district">Huyện/Quận<span style="color: red">*</span></label>
                                    <select name="district" class="form-control" id="district" required>
                                        <option>Chọn Huyện/Quận</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label class="form-label" for="ward">Xã/Phường<span style="color: red">*</span></label>
                                    <select name="ward" class="form-control" id="ward" required>
                                        <option>Chọn Xã/Phường</option>
                                    </select>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="form-label" for="streetAddress">Số Đường/Tên Đường<span style="color: red">*</span></label>
                                    <div class="input-group" style="align-items: center">
                                        <input name="street" class="form-control" id="streetAddress" placeholder="Nhập số đường/tên đường" 
                                               type="text" required/>
                                        <i id="streetValid" style="position: absolute; right: 10px; display: none" class="fas fa-check text-success"></i>
                                    </div>
                                    <span id="invalidStreet" style="color: red; font-size: 15px;display: none; margin-top: 5px" >Địa chỉ không hợp lệ. Vui lòng nhập một địa chỉ hợp lệ.</span>
                                </div>
                            </div>
                        </div>
                        <hr><!-- comment -->
                        <div class="mb-4">
                            <strong class="h4 section-title">TÙY CHỌN GIAO HÀNG</strong>
                            <div class="shipMethod">
                                <span>Thanh toán trực tuyến để giao hàng nhanh hơn và hoàn trả/hoàn tiền nhanh hơn.</span>
                            </div>
                            <div class="shipOption">
                                <span id="shippingFee2">Đang tính...</span>
                                <p>GHTK - Cơ bản</p>
                                <p>08:00 - 20:00</p>
                            </div>
                        </div>
                        <hr><!-- comment -->
                        <div class="mb-4">
                            <strong class="h4 section-title">PHƯƠNG THỨC THANH TOÁN</strong>
                            <div class="paymentMethod">
                                <div id="cod" class="method" onclick="selectPaymentMethod('cod')">
                                    Thanh Toán Khi Nhận Hàng
                                </div>
                                <div id="vnpay" class="method" onclick="selectPaymentMethod('vnpay')">
                                    VN Pay
                                </div>
                                <!-- lay gia tri ship method tu js -->
                                <input type="hidden" id="paymentMethod" name="paymentMethod" value="">
                            </div>
                            <div class="form-group form-check">
                                <input class="form-check-input" id="billingSame" type="checkbox" required/>
                                <label class="form-check-label" for="billingSame">Thông tin hóa đơn / thuế và thông tin giao hàng của tôi là giống nhau.</label>
                            </div>                   
                            <div class="form-group form-check">
                                <input class="form-check-input" id="ageConfirmation" type="checkbox" required/>
                                <label class="form-check-label" for="ageConfirmation">Vâng, tôi trên 16 tuổi.</label>
                            </div>
                            <div class="form-group form-check">
                                <input class="form-check-input" id="privacyConsent" type="checkbox" required/>
                                <label class="form-check-label" for="privacyConsent">Tôi xin đồng ý cho việc chuyển nhượng, chia sẻ, sử dụng, thu thập và tiết lộ dữ liệu cá nhân của tôi cho các bên thứ ba như được nêu trong <a class="privacy-link" href="#">Chính sách Bảo mật của RGS</a>.</label>
                            </div>
                            <div class="form-group form-check">
                                <input class="form-check-input" id="termsConditions" type="checkbox" required/>
                                <label class="form-check-label" for="termsConditions">Tôi đã đọc, hiểu và chấp nhận <a class="privacy-link" href="#">Thông báo Bảo mật</a> và <a class="privacy-link" href="#">Điều khoản và Điều kiện</a>.</label>
                            </div>
                            <div class="row mt-3">
                                <div class="col text-left">
                                    <a href="CartDetailServlet" class="btn border border-dark bg-light text-dark"><i class="fas fa-arrow-left ml-2"></i> CHI TIẾT GIỎ HÀNG</a>
                                </div>
                                <div class="col text-right">
                                    <button id="paymentButton" class="btn btn-dark" disabled>TIẾP TỤC <i class="fas fa-arrow-right ml-2"></i></button>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" name="total" id="total" value="${total}">
                        <input type="hidden" id="shippingFee1" name="shippingFee1" value="">
                        <input type="hidden" id="totalPrice1" name="totalPrice1" value="">
                    </form>
                </div>
                <!-- Right Column -->

                <div class="col-lg-5">
                    <h2 class="h4 section-title">GIỎ HÀNG CỦA BẠN</h2>
                    <div class="d-flex justify-content-between mb-2">
                        <span>${sessionScope.cart.size()} các sản phẩm</span>
                        <span class="productPrice">${total}</span>

                    </div>
                    <div class="d-flex justify-content-between mb-2">
                        <span>Giao hàng</span>
                        <span id="shippingFee" class="shippingFee">Đang tính...</span>

                    </div>
                    <div class="d-flex justify-content-between mb-2 total-price">
                        <span style="font-weight: 700">Tổng</span>
                        <span style="font-weight: 700" id="totalPrice" class="totalPrice">Đang tính...</span>

                    </div>

                    <p class="text-muted small mb-4">[Bao gồm thuế 79,259₫]</p>
                    <div style="color: black; text-decoration: underline !important; cursor: pointer" onclick="showVoucher()">
                        Sử dụng mã giảm giá
                        <div id="voucherInput" style="margin-top: 10px; display: none"><input class="form-control" type="text" name="voucher"></div>
                    </div>
                    <hr>
                    <c:forEach items="${cartItemsDTO}" var="item">
                        <div class="item-container d-flex mb-4">
                            <a style="text-decoration: none; color: black; transform: scale(1.01)" href="ProductDetailServlet?product_id=${item.product.product_id}&color_id=${item.productPrice.color_id}">
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
                                / Số lượng: ${item.quantity}
                            </p> 
                            <p class="small text-muted">
                                Màu sắc: 
                                <c:forEach items="${listColor}" var="c">
                                    <c:if test="${item.productPrice.color_id == c.color_id}">
                                        <span style="vertical-align: middle;width: 13px;height: 13px;border-radius: 50px;
                                              display: inline-block !important;background-color: ${c.color};
                                              box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);">
                                        </span>
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
        function showVoucher() {
            var voucher = document.getElementById("voucherInput");
            voucher.style.display = "flex";
        }
        function selectPaymentMethod(method) {
            document.getElementById("paymentMethod").value = method;

            document.querySelectorAll('.method').forEach(function (element) {
                element.style.backgroundColor = ""; // Reset background color
            });
            if (method === 'cod') {
                document.getElementById('cod').style.backgroundColor = '#e0e0e0'; // Highlight COD
            } else if (method === 'vnpay') {
                document.getElementById('vnpay').style.backgroundColor = '#e0e0e0'; // Highlight VN Pay
            }
            togglePayButton();
        }
        function togglePayButton() {
            const paymentMethod = document.getElementById('paymentMethod').value;
            const payButton = document.getElementById('paymentButton');

            // Nếu đã chọn phương thức thanh toán, bật nút thanh toán, ngược lại thì vô hiệu hóa
            if (paymentMethod) {
                payButton.disabled = false;
            } else {
                payButton.disabled = true;
            }
        }
    </script>
    <script>
        //api lay tinh thanh pho
        document.addEventListener("DOMContentLoaded", function () {
            function updateShippingFee() {
                let city = document.getElementById("city").value;
                let district = document.getElementById("district").value;
                let ward = document.getElementById("ward").value;
                let total = document.getElementById("total").value;

                if (city !== "Chọn Tỉnh" && district !== "Chọn Huyện/Quận" && ward !== "Chọn Xã/Phường") {
                    $.ajax({
                        url: "GetShippingFeeServlet",
                        type: "GET",
                        data: {city: city, district: district, ward: ward, total: total},
                        success: function (response) {
                            let shippingFee = response.shippingFee;
                            document.getElementById("shippingFee").innerText = shippingFee.toLocaleString("vi-VN") + "₫";
                            let totalPrice = parseFloat(total) + shippingFee;
                            console.log(shippingFee);
                            document.getElementById("shippingFee2").innerText = shippingFee.toLocaleString("vi-VN") + "₫";
                            document.getElementById("shippingFee1").value = shippingFee;
                            document.getElementById("totalPrice").innerText = totalPrice.toLocaleString("vi-VN") + "₫";
                            document.getElementById("totalPrice1").value = totalPrice;
                        },
                        error: function () {
                            document.getElementById("shippingFee").innerText = "Không thể tính phí";
                        }
                    });
                }
            }

            document.getElementById("city").addEventListener("change", updateShippingFee);
            document.getElementById("district").addEventListener("change", updateShippingFee);
            document.getElementById("ward").addEventListener("change", updateShippingFee);
        });
    </script>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            let form = document.getElementById("checkoutForm");
            let contactEmail = document.getElementById("contactEmail");
            let emailValid = document.getElementById("emailValid");
            let invalidEmail = document.getElementById("invalidEmail");
            
            let fullNameInput = document.getElementById("fullName");
            let nameValid = document.getElementById("nameValid");
            let invalidName = document.getElementById("invalidName");

            let phoneInput = document.getElementById("phoneNumber");
            let phoneValid = document.getElementById("phoneValid");
            let invalidPhone = document.getElementById("invalidPhone");

            let streetInput = document.getElementById("streetAddress");
            let streetValid = document.getElementById("streetValid");
            let invalidStreet = document.getElementById("invalidStreet");
            
            function validateEmail() {
                let regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
                if (regex.test(contactEmail.value.trim())) {
                    contactEmail.style.borderColor = "green";
                    emailValid.style.display = "flex";
                    invalidEmail.style.display = "none";
                    return true;
                } else {
                    contactEmail.style.borderColor = "red";
                    invalidEmail.style.display = "flex";
                    emailValid.style.display = "none";
                    return false;
                }
            }

            function validateFullName() {
                let regex = /^[A-Za-zÀ-Ỹà-ỹ\s]+$/;
                if (regex.test(fullNameInput.value.trim())) {
                    fullNameInput.style.borderColor = "green";
                    nameValid.style.display = "flex";
                    invalidName.style.display = "none";
                    return true;
                } else {
                    fullNameInput.style.borderColor = "red";
                    nameValid.style.display = "none";
                    invalidName.style.display = "flex";
                    return false;
                }
            }

            function validatePhoneNumber() {
                let regex = /^(0[3-9])([0-9]{8})$/;
                if (regex.test(phoneInput.value.trim())) {
                    phoneInput.style.borderColor = "green";
                    phoneValid.style.display = "flex";
                    invalidPhone.style.display = "none";
                    return true;
                } else {
                    phoneInput.style.borderColor = "red";
                    phoneValid.style.display = "none";
                    invalidPhone.style.display = "flex";
                    return false;
                }
            }
            
            function validateStreet() {
                let regex = /^[A-Za-zÀ-Ỹà-ỹ\s0-9,]+$/;
                if (regex.test(streetInput.value.trim())) {
                    streetInput.style.borderColor = "green";
                    streetValid.style.display = "flex";
                    invalidStreet.style.display = "none";
                    return true;
                } else {
                    streetInput.style.borderColor = "red";
                    streetValid.style.display = "none";
                    invalidStreet.style.display = "flex";
                    return false;
                }
            }

            // Gọi các hàm validate khi nhập
            contactEmail.addEventListener("input", validateEmail);
            fullNameInput.addEventListener("input", validateFullName);
            phoneInput.addEventListener("input", validatePhoneNumber);
            streetInput.addEventListener("input",validateStreet);

            // Ngăn submit form nếu có lỗi
            form.addEventListener("submit", function (event) {
                if (!validateEmail() || !validateFullName() || !validatePhoneNumber()) {
                    event.preventDefault(); // Ngăn không cho submit
                    alert("Vui lòng nhập đúng định dạng trước khi gửi!");
                }
            });
        });
    </script>

    <script>
        //js format gia tien
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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>
    <script src="NgocHieu/apiprovince.js"></script>

</body>
</html>
