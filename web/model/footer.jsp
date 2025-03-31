<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Footer -->
<footer class="footer">
    <!-- Main Footer -->
    <div class="footer-main" style="background-color: #333; color: white; padding: 40px 0;">
        <div class="container">
            <div class="row">
                <!-- Company Info -->
                <div class="col-lg-3 col-md-6 footer-column">
                    <div class="footer-widget">
                        <h4 class="widget-title">Runner Shop</h4>
                        <div class="about-company">
                            <img src="${pageContext.request.contextPath}/images/LOGO.png" 
                                 height="40px"
                                 width="40px"
                                 alt="Runner Shop Logo" 
                                 class="footer-logo mb-3">
                            <p class="company-description">
                                Chuyên cung cấp các sản phẩm thể thao chính hãng với chất lượng tốt nhất cho người tiêu dùng Việt Nam.
                            </p>
                            <div class="social-links">
                                <a href="#" class="social-link facebook" target="_blank">
                                    <i class="fab fa-facebook-f"></i>
                                </a>
                                <a href="#" class="social-link instagram" target="_blank">
                                    <i class="fab fa-instagram"></i>
                                </a>
                                <a href="#" class="social-link youtube" target="_blank">
                                    <i class="fab fa-youtube"></i>
                                </a>
                                <a href="#" class="social-link tiktok" target="_blank">
                                    <i class="fab fa-tiktok"></i>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Quick Links -->
                <div class="col-lg-3 col-md-6 footer-column">
                    <div class="footer-widget">
                        <h4 class="widget-title">Liên kết nhanh</h4>
                        <ul class="footer-links">
                            <li><a href="home">Trang chủ</a></li>
                            <li><a href="/RunnerShop/About.jsp">Giới thiệu</a></li>
                            <li><a href="/RunnerShop/productlist">Sản phẩm</a></li>
                            <li><a href="footer.jsp">Liên hệ</a></li>
                            <li><a href="/RunnerShop/PostListController?type=news">Tin tức</a></li>
                        </ul>
                    </div>
                </div>

                <!-- Categories -->
                <div class="col-lg-2 col-md-6 footer-column">
                    <div class="footer-widget">
                        <h4 class="widget-title">Danh mục</h4>
                        <ul class="footer-links">
                            <li><a href="home?category=nam">Đồ nam</a></li>
                            <li><a href="home?category=nu">Đồ nữ</a></li>
                            <li><a href="home?category=giay">Giày</a></li>
                            <li><a href="home?category=other">Phụ kiện</a></li>
                            <li><a href="category?type=sale">Khuyến mãi</a></li>
                        </ul>
                    </div>
                </div>

                <!-- Contact Info -->
                <div class="col-lg-4 col-md-6 footer-column">
                    <div class="footer-widget">
                        <h4 class="widget-title">Thông tin liên hệ</h4>
                        <div class="contact-info">
                            <div class="contact-item">
                                <i class="fas fa-map-marker-alt"></i>
                                <p>123 Đường ABC, Quận XYZ, TP.HCM</p>
                            </div>
                            <div class="contact-item">
                                <i class="fas fa-phone-alt"></i>
                                <p>
                                    Hotline: <a href="tel:1900xxxx">1900 xxxx</a><br>
                                    CSKH: <a href="tel:0123456789">0123 456 789</a>
                                </p>
                            </div>
                            <div class="contact-item">
                                <i class="fas fa-envelope"></i>
                                <p>
                                    Email: <a href="mailto:info@runnershop.com">info@runnershop.com</a>
                                </p>
                            </div>
                            <div class="contact-item">
                                <i class="fas fa-clock"></i>
                                <p>
                                    Giờ làm việc: 8:00 - 22:00<br>
                                    Tất cả các ngày trong tuần
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Bottom Footer -->
    <div class="footer-bottom" style="background-color: #222; color: white; padding: 20px 0;">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-md-6">
                    <p class="copyright">
                        © 2024 Runner Shop. Tất cả quyền được bảo lưu.
                    </p>
                </div>
                <div class="col-md-6 text-md-right">
                    <div class="payment-methods">
                        <span>Chấp nhận thanh toán:</span>
                        <img src="${pageContext.request.contextPath}/resources/img/payment-visa.png" alt="Visa" class="payment-icon">
                        <img src="${pageContext.request.contextPath}/resources/img/payment-mastercard.png" alt="Mastercard" class="payment-icon">
                        <img src="${pageContext.request.contextPath}/resources/img/payment-momo.png" alt="Momo" class="payment-icon">
                        <img src="${pageContext.request.contextPath}/resources/img/payment-vnpay.png" alt="VNPay" class="payment-icon">
                    </div>
                </div>
            </div>
        </div>
    </div>
</footer>

<!-- CSS -->
<style>
    .footer {
        font-family: Arial, sans-serif;
        margin-top: 20px;
    }

    .footer-widget {
        margin-bottom: 30px;
    }

    .footer-widget h4 {
        font-size: 18px;
        margin-bottom: 20px;
        color: #f1f1f1;
        font-weight: bold;
    }

    .footer-links li {
        list-style-type: none;
        margin-bottom: 10px;
    }

    .footer-links li a {
        color: #bbb;
        text-decoration: none;
        transition: color 0.3s ease;
    }

    .footer-links li a:hover {
        color: #f5a623;
    }

    .social-links a {
        color: #bbb;
        margin-right: 10px;
        font-size: 18px;
        transition: color 0.3s ease;
    }

    .social-links a:hover {
        color: #f5a623;
    }

    .contact-info .contact-item {
        margin-bottom: 15px;
        color: #bbb;
    }

    .contact-info .contact-item i {
        margin-right: 10px;
        color: #f5a623;
    }

    .footer-bottom {
        text-align: center;
        font-size: 14px;
    }

    .payment-methods img {
        margin-right: 10px;
        height: 30px;
    }

    .payment-icon {
        max-width: 100px;
    }

    .copyright {
        margin: 0;
    }
</style>
