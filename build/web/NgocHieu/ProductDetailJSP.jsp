<%-- 
    Document   : ProductDetailJSP
    Created on : Jan 25, 2025, 10:32:09 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.LocalDateTime" %>


<!DOCTYPE html>
<html>

    <head>
        <title>
            Product Page
        </title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet" />
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&amp;display=swap" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper/swiper-bundle.min.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/swiper/swiper-bundle.min.js"></script>

        <link crossorigin="anonymous" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" rel="stylesheet" />
        <link rel="stylesheet" href="NgocHieu/ProductDetail.css">
    </head>

    <body>     
        <%@ include file="/model/header.jsp" %>
        <div class="row">
            <div class="col-md-8">
                <div class="product-images">
                    <img alt="Main product image showing the product in detail" height="600" id="mainImage"
                         src="${requestScope.listProductImage[0].image_url}"
                         width="600" style="object-fit: contain"/>

                    <!-- Slider chứa ảnh thumbnail -->
                    <div style="display: flex" class="swiper thumbnail-slider">
                        <div style="margin-left: 10px" class="swiper-wrapper">
                            <c:forEach items="${requestScope.listProductImage}" var="pi">
                                <div class="swiper-slide">
                                    <img alt="Thumbnail image" height="80"
                                         onclick="changeImage(this)"
                                         src="${pi.image_url}"
                                         width="80" />
                                </div>
                            </c:forEach>
                        </div>
                    </div>

                    <!-- Thanh hiển thị vị trí phân trang-->
                    <div style="text-align: center" class="swiper-pagination"></div>
                </div>

                <br>
                <div class="left-section">
                    <div class="reviews">
                        <%
                            LocalDateTime endFeedbackDate = (LocalDateTime) request.getAttribute("endFeedbackDate");
                            boolean isExpired = (endFeedbackDate != null) && endFeedbackDate.isBefore(java.time.LocalDateTime.now());
                        %>
                        <h5 class="reviews">
                            <span>Đánh Giá (${listFeedback.size()})</span>
                            <span class="rating">
                                ${averageRate} <i class="fas fa-star"></i>
                            </span>

                            <c:if test="${isExpired}">
                                <h3 id="countdown" class="expired">Hết hạn đánh giá!</h3>
                            </c:if>

                            <c:if test="${!isExpired && !checkFeedback && orderDate != null}">
                                <h3 style="font-size: 15px">Thời gian còn lại: <span id="countdown"></span></h3>
                                </c:if>

                            <button id="feedbackButton" 
                                    onclick="toggleFeedback()" 
                                    ${isExpired ? "disabled" :""}
                                    ${checkFeedback ? "disabled" : ""}
                                    ${orderDate==null ?"hidden":""}>
                                ${checkFeedback ? "Bạn đã đánh giá" : "Đánh giá"}
                            </button>
                        </h5>

                        <script>
                            function startCountdown(targetTime) {
                                function updateCountdown() {
                                    let now = new Date().getTime();
                                    let distance = targetTime - now;

                                    if (distance < 0) {
                                        document.getElementById("countdown").textContent = "Hết hạn đánh giá!";
                                        document.getElementById("countdown").classList.add("expired");
                                        document.getElementById("feedbackButton").disabled = true;
                                        clearInterval(interval);
                                        return;
                                    }

                                    let days = Math.floor(distance / (1000 * 60 * 60 * 24));
                                    let hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                                    let minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
                                    let seconds = Math.floor((distance % (1000 * 60)) / 1000);

                                    document.getElementById("countdown").textContent =
                                            days + " ngày " + hours + " giờ " + minutes + " phút " + seconds + " giây";
                                }

                                updateCountdown();
                                let interval = setInterval(updateCountdown, 1000);
                            }

                            window.onload = function () {
                                let endFeedbackDate = "<%= (endFeedbackDate != null) ? endFeedbackDate : "" %>";
                                if (endFeedbackDate) {
                                    let targetTime = new Date(endFeedbackDate.replace("T", " ")).getTime();
                                    startCountdown(targetTime);
                                }
                            };
                        </script>

                        <div class="feedback-list">
                            <c:forEach items="${listFeedback}" var="fb" varStatus="status">
                                <div class="review-container" style="${status.index > 0 ? 'display: none;' : ''}">
                                    <div class="review-header">
                                        <div class="star-rating">
                                            <c:forEach begin="1" end="${fb.rating}">
                                                <i id="star-review" class="bi bi-star-fill"></i>
                                            </c:forEach>
                                        </div>
                                        <span class="email">${fb.email}</span>
                                    </div>
                                    <p class="review-content">${fb.feedback_content}</p>
                                    <span class="review-date">${fb.create_at}</span>
                                </div>
                            </c:forEach>
                        </div>

                        <c:if test="${listFeedback.size() != 0}">
                            <button id="loadMoreBtn" class="btn btn-outline-dark" onclick="showMoreReviews()">Xem thêm</button>
                        </c:if>
                        <script>
                            let reviews = document.querySelectorAll('.review-container'); // Danh sách tất cả đánh giá
                            let btn = document.getElementById('loadMoreBtn'); // Button "Xem thêm"
                            let visibleCount = 1; // Ban đầu hiển thị 1 đánh giá
                            let loadAmount = 3; // Số lượng đánh giá sẽ hiển thị thêm mỗi lần nhấn

                            function showMoreReviews() {
                                let totalReviews = reviews.length;

                                for (let i = visibleCount; i < visibleCount + loadAmount && i < totalReviews; i++) {
                                    reviews[i].style.display = 'block';
                                }

                                visibleCount += loadAmount;

                                // Ẩn nút nếu đã hiển thị hết
                                if (visibleCount >= totalReviews) {
                                    btn.style.display = 'none';
                                }
                            }
                        </script>

                    </div>

                    <!-- Form Đánh Giá -->
                    <div id="feedback-form" style="display: none;">
                        <button type="button" class="btn-close" onclick="toggleFeedback()">×</button>

                        <h3>Chia sẻ trải nghiệm của bạn</h3>
                        <p>Bạn đánh giá sản phẩm này như thế nào?</p>

                        <form action="AddFeedbackServlet" method="POST">
                            <input type="hidden" name="email" value="${user.email}">
                            <input type="hidden" name="product_id" value="${product.product_id}">
                            <div id="star-feedback" class="star-rating">
                                <input type="hidden" name="rating" id="rating-value"> <!-- Input ẩn lưu số sao -->
                                <i class="bi bi-star star" data-value="1"></i>
                                <i class="bi bi-star star" data-value="2"></i>
                                <i class="bi bi-star star" data-value="3"></i>
                                <i class="bi bi-star star" data-value="4"></i>
                                <i class="bi bi-star star" data-value="5"></i>
                            </div>

                            <p>Cho chúng tôi biết trải nghiệm của bạn:</p>
                            <textarea name="feedback_content" id="feedback" cols="30" rows="5" placeholder="Viết đánh giá của bạn..." ></textarea>

                            <button type="submit" class="btn-submit">Gửi Đánh Giá</button>
                        </form>
                    </div>
                </div>
                <script>
                    function toggleFeedback() {
                        let form = document.getElementById("feedback-form");
                        form.style.display = (form.style.display === "none" || form.style.display === "") ? "block" : "none";
                    }

                    // Kích hoạt đánh giá sao
                    document.addEventListener("DOMContentLoaded", function () {
                        const stars = document.querySelectorAll(".star");
                        const ratingInput = document.getElementById("rating-value");

                        stars.forEach(star => {
                            star.addEventListener("click", function () {
                                let value = this.getAttribute("data-value");
                                ratingInput.value = value; // Gán giá trị vào input hidden

                                stars.forEach(s => s.classList.remove("active"));
                                for (let i = 0; i < value; i++) {
                                    stars[i].classList.add("active");
                                }
                            });
                        });
                    });
                </script>

                <hr>
                <div class="description">
                    <h5 style="font-family: Calibri; font-weight: 700">
                        Mô Tả
                    </h5>
                    <p>${requestScope.product.description}</p>
                </div>
                <hr>

                <div class="complete-look">
                    <a style="text-decoration: none" href="productcategory?categoryId=${product.category_id}">
                        <h2>
                            SẢN PHẨM CÙNG THỂ LOẠI (${countRelatedProduct-1})
                        </h2>
                    </a>
                    <div class="item-container">
                        <c:forEach items="${listRelatedProduct}" var="rp" begin="0" end="3">
                            <a style="text-decoration: none" href="ProductDetailServlet?product_id=${rp.product_id}">
                                <div class="item">
                                    <c:if test="${rp.isWithin10Days(rp.created_at)}">
                                        <div class="type">
                                            <span class="content">SẢN PHẨM MỚI</span>
                                        </div>
                                    </c:if>
                                    <img alt="${rp.product_name}" height="200"
                                         src="${rp.thumbnail}"
                                         width="200" />
                                    <div class="price">
                                        <c:forEach items="${listUniqueProductPrice}" var="pp">
                                            <c:if test="${rp.product_id == pp.product_id}">
                                                <c:choose>
                                                    <c:when test="${rp.discount != 0}">
                                                        <span class="productPrice original-price">${pp.price}</span>
                                                        <span class="productPrice discounted-price">${pp.price * (100 - rp.discount) / 100}</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="productPrice">${pp.price}</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                    <div class="name" >
                                        <span style="color: #101010;font-family: Roboto, sans-serif; font-size: 17px">${rp.product_name}</span>
                                    </div>
                                    <div class="category">
                                        <c:forEach items="${listCategory}" var="c">
                                            ${c.category_id == rp.category_id ? c.name : ""}
                                        </c:forEach>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </div>
                <div class="complete-look">
                    <h2>
                        VỪA XEM GẦN ĐÂY
                    </h2>
                    <div class="item-container">
                        <c:forEach items="${listRecentlyView}" var="rv" begin="0" end="3">
                            <a style="text-decoration: none" href="ProductDetailServlet?product_id=${rv.product_id}">
                                <div class="item">
                                    <c:if test="${rv.isWithin10Days(rv.created_at)}">
                                        <div class="type">
                                            <span class="content">SẢN PHẨM MỚI</span>
                                        </div>
                                    </c:if>
                                    <img alt="${rv.product_name}" height="200"
                                         src="${rv.thumbnail}"
                                         width="200" />
                                    <div class="price" >
                                        <c:forEach items="${listUniqueProductPrice}" var="pp">
                                            <c:if test="${rv.product_id == pp.product_id}">
                                                <c:choose>
                                                    <c:when test="${rv.discount != 0}">
                                                        <span class="productPrice original-price">${pp.price}</span>
                                                        <span class="productPrice discounted-price">${pp.price * (100 - rv.discount) / 100}</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="productPrice">${pp.price}</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                    <div class="name">
                                        <span style="color: #101010;font-family: Roboto, sans-serif; font-size: 17px">${rv.product_name}</span>
                                    </div>
                                    <div class="category">
                                        <c:forEach items="${listCategory}" var="c">
                                            ${c.category_id == rv.category_id ? c.name : ""}
                                        </c:forEach>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>        
                    </div>               
                </div>

                <div class="complete-look">
                    <h2>
                        XEM NHIỀU NHẤT
                    </h2>
                    <div class="item-container">
                        <c:forEach items="${listMostView}" var="mv" begin="0" end="3">
                            <a style="text-decoration: none; " href="ProductDetailServlet?product_id=${mv.product_id}">
                                <div style="position: relative" class="item">
                                    <c:if test="${mv.isWithin10Days(mv.created_at)}">
                                        <div class="type">
                                            <span class="content">SẢN PHẨM MỚI</span>
                                        </div>
                                        <div style="color: black; position: absolute;right: 0; font-size: 13px; margin: 0 5px 0 0; font-weight: 600" class="view">
                                            <i  class="fa-regular fa-eye"></i>
                                            <c:forEach items="${listProductView}" var="pv">
                                                <c:if test="${pv.product_id == mv.product_id}">
                                                    <span>${pv.view}</span>
                                                </c:if>
                                            </c:forEach>
                                        </div>
                                    </c:if>
                                    <c:if test="${!mv.isWithin10Days(mv.created_at)}">
                                        <div style="color: black; position: absolute;right: 0; font-size: 13px; margin: 0 5px 0 0; font-weight: 600" class="view">
                                            <i  class="fa-regular fa-eye"></i>
                                            <c:forEach items="${listProductView}" var="pv">
                                                <c:if test="${pv.product_id == mv.product_id}">
                                                    <span>${pv.view}</span>
                                                </c:if>
                                            </c:forEach>
                                        </div>
                                    </c:if>
                                    <img alt="${mv.product_name}" height="200"
                                         src="${mv.thumbnail}"
                                         width="200" />
                                    <div class="price" >
                                        <c:forEach items="${listUniqueProductPrice}" var="pp">
                                            <c:if test="${mv.product_id == pp.product_id}">
                                                <c:choose>
                                                    <c:when test="${mv.discount != 0}">
                                                        <span class="productPrice original-price">${pp.price}</span>
                                                        <span class="productPrice discounted-price">${pp.price * (100 - mv.discount) / 100}</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="productPrice">${pp.price}</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                    <div class="name">
                                        <span style="color: #101010;font-family: Roboto, sans-serif; font-size: 17px">${mv.product_name}</span>
                                    </div>
                                    <div class="category">
                                        <c:forEach items="${listCategory}" var="c">
                                            ${c.category_id == mv.category_id ? c.name : ""}
                                        </c:forEach>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>  
                    </div>                     
                </div>
            </div>

            <div class="col-md-4" style="position: fixed; right: 0; top: 70px">
                <c:if test="${checkNew}">
                    <div class="main-type">
                        <span class="content">SẢN PHẨM MỚI</span>
                    </div>
                </c:if>
                <div class="product-category">                    
                    <c:forEach items="${listCategory}" var="category">

                        <c:if test="${category.category_id == product.category_id}">
                            <c:forEach items="${listCategory}" var="cate">
                                <c:if test="${category.parent_id==cate.category_id}">
                                    <a href="productcategory?categoryId=${cate.category_id}" style="text-decoration: none; color: black" >${cate.name}</a> -
                                </c:if>
                            </c:forEach>
                            <a  href="productcategory?categoryId=${category.category_id}" style="text-decoration: none; color: black">${category.name}</a>                           
                        </c:if>
                    </c:forEach>
                </div>
                <div class="product-title">
                    ${product.product_name}
                </div>
                <div class="product-price">  
                    <c:choose>
                        <c:when test="${product.discount != 0}">
                            <span class="productPrice original-price">${selectedPrice}</span>
                            <span class="productPrice discounted-price">${selectedPrice * (100 - product.discount) / 100}</span>
                        </c:when>
                        <c:otherwise>
                            <span class="productPrice">${selectedPrice}</span>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="product-colours">
                    <div>Màu Sắc:</div>
                    <c:forEach items="${requestScope.listProductPrice}" var="pp">
                        <c:forEach items="${listColor}" var="c">
                            <c:if test="${pp.color_id == c.color_id}">
                                <a style="text-decoration: none;" href="ProductDetailServlet?product_id=${requestScope.product.product_id}&color_id=${c.color_id}">
                                    <button style="background-color: ${c.color}; border-radius: 50px; width: 40px; margin-top: 10px;  ${selectedColor == c.color_id ? "border: 3px black solid" : ""}"></button>
                                </a>
                            </c:if>
                        </c:forEach>                       
                    </c:forEach>
                </div>

                <div class="product-sizes">
                    <div>Kích Cỡ:</div>
                    <c:forEach items="${listProductQuantity}" var="pq">
                        <c:forEach items="${listSize}" var="s">
                            <c:if test="${pq.size_id == s.size_id}">
                                <button ${pq.quantity == 0 ? "disabled" : ""}
                                    class="size-button"
                                    data-size-id="${s.size_id}"
                                    data-productprice-id="${selectedProductPriceId}"
                                    data-productquantity-id="${pq.productQuantity_id}"
                                    data-quantity="${pq.quantity}">
                                    ${s.size}
                                </button>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </div>
                <!-- Hiển thị số lượng -->
                <div class="product-quantity">
                    <strong>Số lượng: </strong> <span id="selectedQuantity">Hãy chọn kích cỡ</span>
                </div>

                <div class="size-guide">
                    <a href="#" onclick="showSizeGuide()">
                        Size guide
                    </a>
                </div>
                <div id="sizeGuideModal" class="modal" style="display: none">
                    <span class="close" onclick="closeSizeGuide()">&times;</span>
                    <div class="modal-content-wrapper">
                        <img class="modal-content" src="https://hanaichi.vn/blog/wp-content/uploads/2020/09/bang-size-adidas.jpg" alt="Size Guide">
                        <img class="modal-content" src="https://hanaichi.vn/blog/wp-content/uploads/2020/09/bang-size-quan-ao-adidas.jpg" alt="alt"/>
                    </div>
                </div>
                <script>
                    function showSizeGuide() {
                        document.getElementById("sizeGuideModal").style.display = "flex"; // Hiện modal
                    }

                    function closeSizeGuide() {
                        document.getElementById("sizeGuideModal").style.display = "none"; // Ẩn modal
                    }

                    // Đóng modal khi nhấn ra ngoài ảnh
                    window.onclick = function (event) {
                        let modal = document.getElementById("sizeGuideModal");
                        if (event.target === modal) {
                            modal.style.display = "none";
                        }
                    };
                </script>

                <div class="add-to-bag1">
                    <form action="AddToCartServlet" method="post">
                        <input type="hidden" name="product_id" value="${product.product_id}">
                        <input type="hidden" name="productprice_id" id="selectedProductPriceId" value="${selectedProductPriceId}">
                        <input type="hidden" name="productquantity_id" id="selectedProductQuantityId" value="">

                        <button type="submit">
                            THÊM VÀO GIỎ HÀNG <i class="fas fa-arrow-right"></i>
                        </button>
                    </form>
                </div>

                <div class="shipping-info">
                    <div>
                        <a href="#">
                            MIỄN PHÍ SHIP CHO THÀNH VIÊN CỦA RUNNER SHOP!
                        </a>
                    </div>
                    <div>
                        <a href="#">
                            DỄ HOÀN TRẢ
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <!-- CHAT ICON -->
        <div id="chat-icon" onclick="toggleChat()">
            💬 Chat với Admin
        </div>

        <!-- CỬA SỔ CHAT -->
        <div id="chat-window">
            <div id="chat-header" onclick="toggleChat()">
                Chat Support <span onclick="closeChat(event)">✖</span>
            </div>


            <div id="chat-input">
                <input type="email" id="email" placeholder="Nhập email">
                <button onclick="connectWebSocket()">Bắt đầu chat</button>

                <div id="chat-messages"></div>

                <input type="text" id="message" placeholder="Nhập tin nhắn...">
                <button onclick="sendMessage()">Gửi</button>
                <button onclick="clearChatHistory()">Xóa lịch sử</button>
            </div>
        </div>
        <script>
            function toggleChat() {
                let chatWindow = document.getElementById("chat-window");
                chatWindow.style.display = (chatWindow.style.display === "none" || chatWindow.style.display === "") ? "block" : "none";
            }
            let ws;
            let currentEmail;

            function connectWebSocket() {
                let emailInput = document.getElementById("email");
                let email = emailInput.value.trim();

                if (!email) {
                    alert("Vui lòng nhập email trước khi bắt đầu chat!");
                    return;
                }

                currentEmail = email;

                if (ws) {
                    ws.close();
                }

                ws = new WebSocket("ws://" + window.location.host + "/RunnerShop/chat/" + email);

                ws.onopen = function () {
                    console.log("WebSocket connected for " + email);
                    loadChatHistory(email);
                };

                ws.onmessage = function (event) {
                    displayMessage(event.data);
                    saveMessage(email, event.data);
                };

                ws.onclose = function () {
                    console.log("WebSocket closed");
                };
            }

            function displayMessage(message) {
                let chatMessages = document.getElementById("chat-messages");
                let messageElement = document.createElement("p");
                messageElement.textContent = message;
                chatMessages.appendChild(messageElement);
                chatMessages.scrollTop = chatMessages.scrollHeight;
            }

            function sendMessage() {
                let messageInput = document.getElementById("message");
                let message = messageInput.value.trim();
                if (message !== "" && ws) {
                    let formattedMessage = currentEmail + ": " + message;
                    ws.send(message);
                    displayMessage(formattedMessage);
                    saveMessage(currentEmail, formattedMessage);
                    messageInput.value = "";
                }
            }

            function saveMessage(email, message) {
                let chatHistoryKey = "chat_history_" + email;
                let chatHistory = JSON.parse(localStorage.getItem(chatHistoryKey)) || [];
                chatHistory.push(message);
                localStorage.setItem(chatHistoryKey, JSON.stringify(chatHistory));
            }

            function loadChatHistory(email) {
                let chatHistoryKey = "chat_history_" + email;
                let chatMessages = document.getElementById("chat-messages");
                chatMessages.innerHTML = "";
                let chatHistory = JSON.parse(localStorage.getItem(chatHistoryKey)) || [];
                chatHistory.forEach(message => {
                    displayMessage(message);
                });
            }

            function clearChatHistory() {
                if (!currentEmail)
                    return;
                let chatHistoryKey = "chat_history_" + currentEmail;
                localStorage.removeItem(chatHistoryKey);
                document.getElementById("chat-messages").innerHTML = "";
            }
        </script>
        <script>
            function changeImage(element) {
                var mainImage = document.getElementById('mainImage');
                mainImage.src = element.src;
                var thumbnails = document.querySelectorAll('.thumbnail-slider img');
                thumbnails.forEach(function (thumbnail) {
                    thumbnail.classList.remove('active');
                });
                element.classList.add('active');
            }

            var swiper = new Swiper(".thumbnail-slider", {
                slidesPerView: 4, // Hiển thị 4 ảnh mỗi lần
                spaceBetween: 10, // Khoảng cách giữa các ảnh
                pagination: {
                    el: ".swiper-pagination",
                    clickable: true, // Cho phép bấm vào pagination để chọn slide
                },
                breakpoints: {
                    768: {slidesPerView: 5},
                    1024: {slidesPerView: 6}
                }
            });

            // Khi bấm vào ảnh thumbnail, slide sẽ tự trượt next/prev nếu cần
            function changeImage(img) {
                document.getElementById("mainImage").src = img.src;

                let thumbnails = Array.from(document.querySelectorAll(".swiper-slide img"));
                let index = thumbnails.indexOf(img);
                let totalSlides = thumbnails.length;
                let slidesPerView = swiper.params.slidesPerView;

                // Nếu bấm vào ảnh cuối cùng hiển thị, trượt next
                if (index >= swiper.activeIndex + slidesPerView - 1 && index < totalSlides - 1) {
                    swiper.slideNext();
                }
                // Nếu bấm vào ảnh đầu tiên hiển thị, trượt prev
                else if (index <= swiper.activeIndex && index > 0) {
                    swiper.slidePrev();
                }
            }


        </script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const form = document.querySelector(".add-to-bag1 form");
                const productQuantityInput = document.getElementById("selectedProductQuantityId");

                form.addEventListener("submit", function (event) {
                    if (!productQuantityInput.value) {
                        event.preventDefault(); // Ngăn chặn gửi form
                        alert("Vui lòng chọn kích cỡ trước khi thêm vào giỏ hàng!");
                    }
                });
            });

            document.addEventListener("DOMContentLoaded", function () {
                const sizeButtons = document.querySelectorAll(".size-button");
                const quantityDisplay = document.getElementById("selectedQuantity");
                const productQuantityInput = document.getElementById("selectedProductQuantityId");

                sizeButtons.forEach(button => {
                    button.addEventListener("click", function (event) {
                        if (button.disabled) {
                            return; // Không làm gì nếu nút bị vô hiệu hóa
                        }

                        event.preventDefault(); // Ngăn chặn reload trang

                        // Bỏ highlight của tất cả nút size không bị vô hiệu hóa
                        sizeButtons.forEach(btn => {
                            if (!btn.disabled) {

                                btn.style.backgroundColor = "white";
                                btn.style.color = "black";
                            }
                        });

                        // Đánh dấu nút size được chọn
                        this.style.backgroundColor = "black";
                        this.style.color = "white";

                        // Lấy dữ liệu quantity và productquantity_id
                        const quantity = this.getAttribute("data-quantity");
                        const productQuantityId = this.getAttribute("data-productquantity-id");

                        // Cập nhật số lượng hiển thị
                        quantityDisplay.textContent = quantity ? quantity : "Out of stock";

                        // Cập nhật input hidden trong form
                        productQuantityInput.value = productQuantityId;
                    });
                });
            });

        </script>
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
    </body>


</html>
