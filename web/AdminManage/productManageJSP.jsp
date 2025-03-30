<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <title>ADMIN SITE</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="" name="keywords">
        <meta content="" name="description">

        <!-- Favicon -->
        <link href="${pageContext.request.contextPath}/AdminManage/img/favicon.ico" rel="icon">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Roboto:wght@500;700&display=swap" rel="stylesheet"> 

        <!-- Icon Font Stylesheet -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <!--Link bootstrap phan trang-->
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <!-- Libraries Stylesheet -->
        <link href="${pageContext.request.contextPath}/AdminManage/admin/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/AdminManage/admin/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

        <!-- Customized Bootstrap Stylesheet -->
        <link href="${pageContext.request.contextPath}/AdminManage/admin/css/bootstrap.min.css" rel="stylesheet">

        <!-- Template Stylesheet -->
        <link href="${pageContext.request.contextPath}/AdminManage/admin/css/style.css" rel="stylesheet">
    </head>

    <body>
        <div class="container-fluid position-relative d-flex p-0">
            <!-- Spinner Start -->
            <div id="spinner" class="show bg-dark position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
                <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status">
                    <span class="sr-only">Loading...</span>
                </div>
            </div>
            <!-- Spinner End -->


            <!-- Sidebar Start -->
            <%@include file="component/SideBarAdmin.jsp" %>
            <!-- Sidebar End -->


            <!-- Content Start -->
            <div class="content">
                <!-- Navbar Start -->
                <%@include file="component/navbarAdmin.jsp" %>
                <!-- Navbar End -->
                <!-- Sale & Revenue End -->
                <!-- Recent Sales Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-secondary text-center rounded p-4">
                        <div class="d-flex align-items-center justify-content-between mb-4">
                            <h6 class="mb-0">Danh sách sản phẩm </h6>

                            <a onclick="showAddProduct(event)" href="#" class="btn btn-success">Thêm Sản Phẩm Mới <i class="bi bi-bag-plus-fill"></i></a>
                        </div>
                        <div class="d-flex justify-content-between mb-3">
                            <!-- Ô tìm kiếm -->
                            <div class="w-25">
                                <div class="search-container d-flex">
                                    <input type="text" id="searchInput" class="form-control form-control-sm" 
                                           style="height: 35px;" placeholder="Tìm kiếm sản phẩm...">
                                    <button id="searchButton" class="btn btn-outline-secondary btn-sm ms-2" 
                                            style="height: 35px; display: flex; align-items: center;" type="button">
                                        <i class="fas fa-search"></i> Tìm
                                    </button>
                                </div>
                            </div>
                            <a href="ProductDashboard">Xem tất cả sản phẩm</a>
                            <div class="dropdown">
                                <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="dropdownMenuButton"
                                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    Sắp xếp theo: 
                                    <c:choose>
                                        <c:when test="${sortType == 'name'}">Tên</c:when>
                                        <c:when test="${sortType == 'date'}">Ngày tạo</c:when>
                                        <c:when test="${sortType == 'status'}">Trạng thái</c:when>
                                        <c:when test="${sortType == 'view'}">Lượt xem</c:when>
                                        <c:otherwise>Mặc định</c:otherwise>
                                    </c:choose>
                                </button>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                    <a class="dropdown-item" href="ProductDashboard?searchKey=${searchKey}&sort=name">Sắp xếp theo: Tên</a>
                                    <a class="dropdown-item" href="ProductDashboard?searchKey=${searchKey}&sort=date">Sắp xếp theo: Ngày tạo</a>
                                    <a class="dropdown-item" href="ProductDashboard?searchKey=${searchKey}&sort=status">Sắp xếp theo: Trạng thái</a>
                                    <a class="dropdown-item" href="ProductDashboard?searchKey=${searchKey}&sort=view">Sắp xếp theo: Lượt xem</a>
                                </div>
                            </div>

                        </div>

                        <!-- Container cho bảng sản phẩm -->
                        <div id="productTableContainer">
                            <%@ include file="product-table-fragment.jsp" %>
                        </div>
                        <!-- Nền mờ khi mở form -->
                        <div id="overlay" style="display: none;"></div>

                        <!-- Container hiển thị form thêm sản phẩm -->
                        <div id="addProductModal" class="modal" style="display: none;">
                            <div id="addProductContainer" class="addContainer"></div>
                            <button id="closeModal" onclick="closeAddProduct()" style="position: absolute; top: 10px; right: 10px; background: red; color: white; border: none; padding: 5px 10px; border-radius: 5px; cursor: pointer;">✖</button>
                        </div>

                        <div id="addProductPriceModal" class="modal" style="display: none;">
                            <div id="addProductPriceContainer" class="addContainer"></div>
                            <button id="closeModal" onclick="closeAddProductPrice()" style="position: absolute; top: 10px; right: 10px; background: red; color: white; border: none; padding: 5px 10px; border-radius: 5px; cursor: pointer;">✖</button>
                        </div>
                        <div id="addProductQuantityModal" class="modal" style="display: none;">
                            <div id="addProductQuantityContainer" class="addContainer"></div>
                            <button id="closeModal" onclick="closeAddProductQuantity()" style="position: absolute; top: 10px; right: 10px; background: red; color: white; border: none; padding: 5px 10px; border-radius: 5px; cursor: pointer;">✖</button>
                        </div>
                        <div id="addImageModal" class="modal" style="display: none;">
                            <div id="addImageContainer" class="addContainer"></div>
                            <button id="closeModal" onclick="closeAddImage()" style="position: absolute; top: 10px; right: 10px; background: red; color: white; border: none; padding: 5px 10px; border-radius: 5px; cursor: pointer;">✖</button>
                        </div>
                        <div id="addProductSuccessModal" class="modal" style="display: none;">
                            <div id="addProductSuccessContainer" class="addContainer"></div>
                            <button id="closeModal" onclick="closeAddProductSuccess()" style="position: absolute; top: 10px; right: 10px; background: red; color: white; border: none; padding: 5px 10px; border-radius: 5px; cursor: pointer;">✖</button>
                        </div>

                    </div>
                </div>
                <!-- Recent Sales End -->



                <!-- Footer End -->
            </div>
            <!-- Content End -->


            <!-- Back to Top -->
            <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>
        </div>

        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/AdminManage/admin/lib/chart/chart.min.js"></script>
        <script src="${pageContext.request.contextPath}/AdminManage/admin/lib/easing/easing.min.js"></script>
        <script src="${pageContext.request.contextPath}/AdminManage/admin/lib/waypoints/waypoints.min.js"></script>
        <script src="${pageContext.request.contextPath}/AdminManage/admin/lib/owlcarousel/owl.carousel.min.js"></script>
        <script src="${pageContext.request.contextPath}/AdminManage/admin/lib/tempusdominus/js/moment.min.js"></script>
        <script src="${pageContext.request.contextPath}/AdminManage/admin/lib/tempusdominus/js/moment-timezone.min.js"></script>
        <script src="${pageContext.request.contextPath}/AdminManage/admin/lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

        <!-- Template Javascript -->
        <script src="${pageContext.request.contextPath}/AdminManage/admin/js/main.js"></script>
    </body>

    <script>
                                $(document).on('click', 'a', function (e) {
                                    // Đảm bảo không ngăn chặn hành vi mặc định của liên kết
                                    window.location.href = $(this).attr('href');
                                });
                                $(document).ready(function () {
                                    let currentPage = 1; // Mặc định là trang 1
                                    // Lấy trang hiện tại từ URL khi load trang
                                    let urlParams = new URLSearchParams(window.location.search);
                                    currentPage = parseInt(urlParams.get('page')) || 1;

                                    // Cập nhật currentPage khi click vào các nút phân trang
                                    $(document).on('click', '.pagination .page-link', function (e) {
                                        e.preventDefault();
                                        currentPage = $(this).data('page');
                                        let searchKey = $('#searchInput').val();
                                        let sortType = '${sortType}';
                                        loadProducts(currentPage, searchKey, sortType);
                                    });

                                    // Xử lý tìm kiếm khi nhấn Enter
                                    $('#searchInput').on('keypress', function (e) {
                                        if (e.which === 13) {
                                            e.preventDefault();
                                            performSearch();
                                        }
                                    });

                                    // Xử lý tìm kiếm khi click button
                                    $('#searchButton').on('click', function (e) {
                                        e.preventDefault();
                                        performSearch();
                                    });

                                    // Hàm thực hiện tìm kiếm
                                    function performSearch() {
                                        let searchKey = $('#searchInput').val();
                                        let sortType = '${sortType}';

                                        // Hiển thị loading spinner
                                        $('#spinner').show();

                                        loadProducts(1, searchKey, sortType)
                                                .always(function () {
                                                    // Luôn ẩn spinner sau khi hoàn thành
                                                    $('#spinner').hide();
                                                });
                                    }

                                    // Xử lý phân trang với Ajax
                                    $(document).on('click', '.pagination .page-link', function (e) {
                                        e.preventDefault();
                                        let page = $(this).data('page');
                                        let searchKey = $('#searchInput').val();
                                        let sortType = '${sortType}';
                                        loadProducts(page, searchKey, sortType);
                                    });

                                    // Xử lý sắp xếp với Ajax
                                    $('.dropdown-item').on('click', function (e) {
                                        e.preventDefault();
                                        let sortType = $(this).attr('href').split('sort=')[1];
                                        let searchKey = $('#searchInput').val();
                                        loadProducts(1, searchKey, sortType);
                                    });

                                    // Hàm load sản phẩm--------------------------------------
                                    function loadProducts(page, searchKey, sortType) {
                                        return $.ajax({
                                            url: 'ProductDashboard',
                                            type: 'GET',
                                            data: {
                                                page: page,
                                                searchKey: searchKey,
                                                sort: sortType,
                                                ajax: true
                                            },
                                            beforeSend: function () {
                                                $('#spinner').show();
                                            },
                                            success: function (response) {
                                                $('#productTableContainer').html(response);
                                                formatCurrency();
                                                let newUrl = 'ProductDashboard?page=' + page;
                                                if (searchKey)
                                                    newUrl += '&searchKey=' + searchKey;
                                                if (sortType)
                                                    newUrl += '&sort=' + sortType;
                                                window.history.pushState({}, '', newUrl);
                                            },
                                            error: function (xhr, status, error) {
                                                console.error('Lỗi khi tải dữ liệu:', error);
                                                alert('Có lỗi xảy ra khi tải dữ liệu!');
                                            },
                                            complete: function () {
                                                $('#spinner').hide();
                                            }
                                        });
                                    }

                                    // Hàm format giá tiền
                                    function formatCurrency() {
                                        let priceElements = document.querySelectorAll(".productPrice");
                                        priceElements.forEach(function (element) {
                                            let price = parseFloat(element.textContent);
                                            if (!isNaN(price)) {
                                                element.textContent = price.toLocaleString("vi-VN") + "₫";
                                            }
                                        });
                                    }

                                    // Xử lý cập nhật số lượng với Ajax------------------------
                                    window.editQuantity = function (event, button) {
                                        event.preventDefault();
                                        let form = $(button).closest("form");
                                        let productQuantityId = form.find("select[name='productQuantityId']").val();

                                        let newQuantity = prompt("Nhập số lượng mới:");
                                        if (newQuantity !== null && newQuantity !== "") {
                                            $.ajax({
                                                url: 'UpdateQuantityServlet',
                                                type: 'POST',
                                                data: {
                                                    productQuantityId: productQuantityId,
                                                    newQuantity: newQuantity
                                                },
                                                success: function (response) {
                                                    if (response.success) {
                                                        alert('Cập nhật số lượng thành công!');
                                                        // Refresh bảng sản phẩm
                                                        loadProducts(currentPage, $('#searchInput').val(), '${sortType}');
                                                    } else {
                                                        alert('Có lỗi xảy ra khi cập nhật số lượng!');
                                                    }
                                                }
                                            });
                                        }
                                    }

                                    // Xử lý cập nhật giá với Ajax-----------------------
                                    window.editPrice = function (event, button) {
                                        event.preventDefault();
                                        let form = $(button).closest("form");
                                        let productPriceId = form.find("input[name='productPriceId']").val();

                                        let newPrice = prompt("Nhập giá tiền mới:");
                                        if (newPrice !== null && newPrice !== "") {
                                            $.ajax({
                                                url: 'UpdatePriceServlet',
                                                type: 'POST',
                                                data: {
                                                    productPriceId: productPriceId,
                                                    newPrice: newPrice
                                                },
                                                success: function (response) {
                                                    if (response.success) {
                                                        alert('Cập nhật giá thành công!');
                                                        loadProducts(currentPage, $('#searchInput').val(), '${sortType}');
                                                    } else {
                                                        alert('Có lỗi xảy ra khi cập nhật giá!');
                                                    }
                                                }
                                            });
                                        }
                                    }

                                    // Xử lý cập nhật trạng thái với Ajax------------------
                                    window.updateStatus = function (event, productId, status) {
                                        event.preventDefault();
                                        if (confirm("Bạn có muốn cập nhật lại trạng thái sản phẩm không?")) {
                                            $.ajax({
                                                url: 'UpdateStatusServlet',
                                                type: 'POST',
                                                data: {
                                                    product_id: productId,
                                                    status: status
                                                },
                                                success: function (response) {
                                                    if (response.success) {
                                                        alert('Cập nhật trạng thái thành công!');
                                                        loadProducts(currentPage, $('#searchInput').val(), '${sortType}');
                                                    } else {
                                                        alert('Có lỗi xảy ra khi cập nhật trạng thái!');
                                                    }
                                                }
                                            });
                                        }
                                    }
                                    // Mở cửa sổ thêm sản phẩm ------------------------------------------------------------------
                                    window.showAddProduct = function (event) {
                                        event.preventDefault(); // Ngăn chặn load lại trang khi bấm nút

                                        // Ẩn bảng sản phẩm

                                        // Hiển thị nền mờ và modal
                                        $('#overlay').fadeIn();
                                        $('#addProductModal').fadeIn();

                                        $.ajax({
                                            url: 'AddProductServlet', // Servlet trả về addProduct.jsp
                                            type: 'GET',
                                            success: function (response) {
                                                // Hiển thị form thêm sản phẩm vào #addProductContainer
                                                $('#addProductContainer').html(response).show();

                                                // Gán lại sự kiện kéo thả sau khi nội dung đã tải
                                                initDragAndDrop();
                                            },
                                            error: function () {
                                                alert("Lỗi khi tải trang thêm sản phẩm!");
                                            }
                                        });
                                    };

                                    function initDragAndDrop() {
                                        let dropArea = document.getElementById("dropArea");
                                        let fileInput = document.getElementById("fileInput");
                                        let browseButton = document.getElementById("browse");
                                        let previewContainer = document.getElementById("previewContainer");

                                        if (!dropArea || !fileInput || !browseButton || !previewContainer)
                                            return;

                                        function updatePreview(files) {
                                            previewContainer.innerHTML = "";
                                            Array.from(files).forEach((file, index) => {
                                                let reader = new FileReader();
                                                reader.onload = function (e) {
                                                    let previewItem = document.createElement("div");
                                                    previewItem.style.position = "relative";
                                                    previewItem.style.display = "inline-block";

                                                    let img = document.createElement("img");
                                                    img.src = e.target.result;
                                                    img.style.width = "100px";
                                                    img.style.height = "100px";
                                                    img.style.objectFit = "cover";
                                                    img.style.borderRadius = "5px";
                                                    previewItem.appendChild(img);

                                                    let deleteBtn = document.createElement("button");
                                                    deleteBtn.innerText = "×";
                                                    deleteBtn.style.position = "absolute";
                                                    deleteBtn.style.top = "5px";
                                                    deleteBtn.style.right = "5px";
                                                    deleteBtn.style.background = "red";
                                                    deleteBtn.style.color = "white";
                                                    deleteBtn.style.border = "none";
                                                    deleteBtn.style.cursor = "pointer";
                                                    deleteBtn.style.borderRadius = "50%";
                                                    deleteBtn.style.width = "20px";
                                                    deleteBtn.style.height = "20px";
                                                    deleteBtn.style.textAlign = "center";
                                                    deleteBtn.style.fontSize = "12px";
                                                    deleteBtn.style.lineHeight = "18px";
                                                    deleteBtn.onclick = function () {
                                                        removeImage(index);
                                                    };
                                                    previewItem.appendChild(deleteBtn);

                                                    previewContainer.appendChild(previewItem);
                                                };
                                                reader.readAsDataURL(file);
                                            });
                                        }

                                        function removeImage(index) {
                                            let dataTransfer = new DataTransfer();
                                            let files = Array.from(fileInput.files);
                                            files.splice(index, 1);

                                            files.forEach(file => dataTransfer.items.add(file));
                                            fileInput.files = dataTransfer.files;
                                            updatePreview(fileInput.files);
                                        }

                                        dropArea.addEventListener("dragover", (event) => {
                                            event.preventDefault();
                                            dropArea.style.borderColor = "green";
                                        });

                                        dropArea.addEventListener("dragleave", () => {
                                            dropArea.style.borderColor = "#007bff";
                                        });

                                        dropArea.addEventListener("drop", (event) => {
                                            event.preventDefault();
                                            dropArea.style.borderColor = "#007bff";

                                            let newFiles = event.dataTransfer.files;
                                            let dataTransfer = new DataTransfer();

                                            for (let i = 0; i < fileInput.files.length; i++) {
                                                dataTransfer.items.add(fileInput.files[i]);
                                            }
                                            for (let i = 0; i < newFiles.length; i++) {
                                                dataTransfer.items.add(newFiles[i]);
                                            }

                                            fileInput.files = dataTransfer.files;
                                            updatePreview(fileInput.files);
                                        });

                                        browseButton.addEventListener("click", () => {
                                            fileInput.click();
                                        });

                                        fileInput.addEventListener("change", () => {
                                            updatePreview(fileInput.files);
                                        });
                                    }

                                    // All hàm đóng modal------------------------------------------------------------------
                                    window.closeAddProduct = function () {
                                        $('#overlay').fadeOut();
                                        $('#addProductModal').fadeOut();
                                    };
                                    window.closeAddProductPrice = function () {
                                        $('#overlay').fadeOut();
                                        $('#addProductPriceModal').fadeOut();
                                    };
                                    window.closeAddProductQuantity = function () {
                                        $('#overlay').fadeOut();
                                        $('#addProductQuantityModal').fadeOut();
                                    };
                                    window.closeAddImage = function () {
                                        $('#overlay').fadeOut();
                                        $('#addImageModal').fadeOut();
                                    };
                                    window.closeAddProductSuccess = function () {
                                        $('#overlay').fadeOut();
                                        $('#addProductSuccessModal').fadeOut();
                                    };


                                    //Mở cửa sổ thêm ProductPRice----------------------------------------------------------
                                    window.showAddProductPrice = function (event) {
                                        event.preventDefault();

                                        let form = $("form");
                                        let productName = form.find("input[name='product_name']").val();
                                        let fileInput = form.find("input[name='thumbnail']")[0];
                                        let file = fileInput.files[0];
                                        let description = form.find("textarea[name='description']").val();
                                        let discount = form.find("input[name='discount']").val();
                                        let category_id = form.find("select[id='category_id']").val();

                                        // Kiểm tra dữ liệu nhập vào
                                        if (!validateInputs(productName, file, description, discount, category_id)) {
                                            $('#addProductModal').fadeIn();
                                            $('#addProductPriceModal').fadeOut();
                                            return;
                                        }

                                        // Kiểm tra tên sản phẩm trong database
                                        checkProductName(productName).then(existName => {
                                            if (existName) {
                                                alert("Tên sản phẩm đã tồn tại!");
                                                $('#addProductModal').fadeIn();
                                                $('#addProductPriceModal').fadeOut();
                                                return;
                                            }

                                            // Hiển thị modal
                                            $('#overlay').fadeIn();
                                            $('#addProductModal').fadeOut();
                                            $('#addProductPriceModal').fadeIn();

                                            // Nếu mọi thứ hợp lệ, thực hiện AJAX
                                            let formData = new FormData();
                                            formData.append("product_name", productName);
                                            formData.append("thumbnail", file);
                                            formData.append("description", description);
                                            formData.append("discount", discount);
                                            formData.append("category_id", category_id);

                                            $.ajax({
                                                url: 'AddProductServlet',
                                                type: 'POST',
                                                data: formData,
                                                processData: false,
                                                contentType: false,
                                                success: function (response) {
                                                    $('#addProductPriceContainer').html(response).show();
                                                },
                                                error: function () {
                                                    alert("Lỗi khi tải trang thêm sản phẩm!");
                                                }
                                            });
                                        });
                                    };


                                    function checkProductName(productName) {
                                        return fetch("CheckExistProductNameServlet?product_name=" + encodeURIComponent(productName))
                                                .then(response => response.json()) // Chuyển response thành JSON
                                                .then(data => {
                                                    console.log("Tên tồn tại:", data.exists); // Log để kiểm tra
                                                    return data.exists; // Trả về true nếu tên tồn tại, false nếu không
                                                })
                                                .catch(error => {
                                                    console.error("Lỗi kiểm tra tên sản phẩm:", error);
                                                    return false; // Mặc định là false nếu có lỗi
                                                });
                                    }
                                    function checkProductId(product_id) {
                                        return fetch("CheckExistProductIdServlet?product_id=" + encodeURIComponent(product_id))
                                                .then(response => response.json()) // Chuyển response thành JSON
                                                .then(data => {
                                                    console.log("Product id tồn tại:", data.exists); // Log để kiểm tra
                                                    return data.exists; // Trả về true nếu id tồn tại, false nếu không
                                                })
                                                .catch(error => {
                                                    console.error("Lỗi kiểm tra tên sản phẩm:", error);
                                                    return false; // Mặc định là false nếu có lỗi
                                                });
                                    }
                                    function checkColorExist(product_id, color_id) {
                                        return fetch("CheckExistColorServlet?product_id=" + encodeURIComponent(product_id) + "&color_id=" + encodeURIComponent(color_id))
                                                .then(response => response.json()) // Chuyển response thành JSON
                                                .then(data => {
                                                    console.log("Màu đã tồn tại:", data.exists); // Log để kiểm tra
                                                    return data.exists; // Trả về true nếu màu tồn tại, false nếu không
                                                })
                                                .catch(error => {
                                                    console.error("Lỗi kiểm tra tên sản phẩm:", error);
                                                    return false; // Mặc định là false nếu có lỗi
                                                });
                                    }
                                    function validateInputs(productName, file, description, discount, category_id) {
                                        if (!productName || !file || !description || !discount || !category_id) {
                                            alert("Vui lòng điền đủ thông tin!");
                                            return false;
                                        }

                                        if (productName.length > 255) {
                                            alert("Tên sản phẩm không được quá 255 ký tự!");
                                            return false;
                                        }

                                        // Kiểm tra định dạng file (chỉ chấp nhận ảnh)
                                        const allowedTypes = ["image/jpeg", "image/png", "image/gif", "image/webp", "image/avif"];
                                        if (!allowedTypes.includes(file.type)) {
                                            alert("Chỉ được phép tải lên file ảnh (JPG, PNG, GIF, WEBP, AVIF)!");
                                            return false;
                                        }

                                        if (discount < 0 || discount > 99) {
                                            alert("Giảm giá phải nằm trong khoảng 0 - 99%!");
                                            return false;
                                        }

                                        return true;
                                    }

                                    function validateInputsPrice(product_id, color_id, price) {
                                        if (!product_id || !color_id || !price) {
                                            alert("Vui lòng điền đủ thông tin!");
                                            return false;
                                        }

                                        if (price < 0 || price > 100000000) {
                                            alert("Giá sản phẩm không hợp lệ!");
                                            return false;
                                        }

                                        return true;
                                    }


                                    //Mở cửa sổ thêm quantity-------------------------------------------------------------------------
                                    window.showAddProductQuantity = function (event) {
                                        event.preventDefault();

                                        let form = $("form");
                                        let product_id = form.find("input[name='product_id']").val();
                                        let color_id = form.find("select[id='color_id']").val();
                                        let price = form.find("input[name='price']").val();

                                        // Kiểm tra dữ liệu nhập vào
                                        if (!validateInputsPrice(product_id, color_id, price)) {
                                            $('#addProductPriceModal').fadeIn();
                                            $('#addProductQuantityModal').fadeOut();
                                            return;
                                        }

                                        // Kiểm tra tên sản phẩm trong database
                                        checkProductId(product_id).then(existId => {
                                            if (!existId) {
                                                alert("Id sản phẩm không hợp lệ hoặc không tồn tại trong hệ thống!");
                                                $('#addProductPriceModal').fadeIn();
                                                $('#addProductQuantityModal').fadeOut();
                                                return;
                                            }
                                            checkColorExist(product_id, color_id).then(existColor => {
                                                if (existColor) {
                                                    alert("Màu của sản phầm này đã tồn tại!");
                                                    $('#addProductPriceModal').fadeIn();
                                                    $('#addProductQuantityModal').fadeOut();
                                                    return;
                                                }

                                                // Hiển thị modal
                                                $('#overlay').fadeIn();
                                                $('#addProductQuantityModal').fadeIn();
                                                $('#addProductPriceModal').fadeOut();

                                                // Nếu mọi thứ hợp lệ, thực hiện AJAX

                                                console.log(product_id + "|" + color_id + "|" + price);

                                                $.ajax({
                                                    url: 'AddProductPriceServlet',
                                                    type: 'POST',
                                                    data: {
                                                        product_id: product_id,
                                                        color_id: color_id,
                                                        price: price
                                                    },
                                                    success: function (response) {
                                                        $('#addProductQuantityContainer').html(response).show();
                                                        // Thêm timeout để đảm bảo DOM đã được cập nhật hoàn toàn
                                                        setTimeout(function () {
                                                            updateSizeOptions();
                                                            console.log("✅ Đã chạy updateSizeOptions sau khi load nội dung");
                                                        }, 200);
                                                    },
                                                    error: function () {
                                                        alert("Lỗi khi tải trang thêm sản phẩm!");
                                                    }
                                                });
                                            });
                                        });
                                    };

                                    function updateSizeOptions() {
                                        console.log("🔄 Đang chạy updateSizeOptions...");

                                        var categoryElement = document.getElementById("category");
                                        if (!categoryElement) {
                                            // Hiển thị tất cả các phần tử có ID trong form để debug
                                            var allElements = document.querySelectorAll("#addProductQuantityContainer [id]");
                                            return;
                                        }

                                        var category = categoryElement.value;
                                        // Ẩn tất cả các nhóm size
                                        var allSizeGroups = document.querySelectorAll("[id^=size_group_]");

                                        if (allSizeGroups.length === 0) {
                                            return;
                                        }

                                        allSizeGroups.forEach(function (span) {
                                            span.style.display = "none";
                                        });

                                        var rangeStart, rangeEnd;
                                        if (category === "1") { // Giày
                                            rangeStart = 1;
                                            rangeEnd = 9;
                                        } else if (category === "2") { // Quần áo
                                            rangeStart = 10;
                                            rangeEnd = 15;
                                        } else if (category === "3") { // Phụ kiện
                                            rangeStart = 10;
                                            rangeEnd = 21;
                                        } else {
                                            console.log("⚠️ Danh mục không hợp lệ:", category);
                                            return;
                                        }

                                        console.log(`🎯 Đang hiển thị size từ ${rangeStart} đến ${rangeEnd}`);

                                        // Hiển thị các nhóm size trong phạm vi
                                        for (var i = rangeStart; i <= rangeEnd; i++) {
                                            var sizeGroup = document.getElementById("size_group_" + i);
                                            if (sizeGroup) {
                                                sizeGroup.style.display = "inline";
                                            } else {
                                            }
                                        }
                                    }

                                    // Khi trang được tải bằng AJAX, đảm bảo gọi lại updateSizeOptions
                                    $(document).ready(function () {
                                        // Đăng ký event handler cho #category khi được thêm vào DOM bằng Ajax
                                        $(document).on("change", "#category", function () {
                                            updateSizeOptions();
                                        });
                                    });


                                    // Hàm mở cửa sổ thêm ảnh cho sản phẩm
                                    window.showAddImage = function (event) {
                                        event.preventDefault();

                                        let form = $("form");
                                        let productprice_id = form.find("input[name='productprice_id']").val();
                                        let quantity = form.find("input[name='quantity']").val();
                                        let productId = form.find("input[name='product_id']").val();

                                        // Kiểm tra dữ liệu đầu vào
                                        if (!productprice_id || !quantity) {
                                            alert("Vui lòng điền đầy đủ thông tin!");
                                            return;
                                        }

                                        // Lấy danh sách size đã chọn
                                        let selectedSizes = [];
                                        form.find("input[name='size_id']:checked").each(function () {
                                            selectedSizes.push($(this).val());
                                        });

                                        // Kiểm tra xem đã chọn size chưa
                                        if (selectedSizes.length === 0) {
                                            alert("Vui lòng chọn ít nhất một kích thước!");
                                            return;
                                        }

                                        console.log("Selected sizes:", selectedSizes);
                                        console.log("Product price ID:", productprice_id, "Quantity:", quantity);

                                        // Hiển thị modal
                                        $('#overlay').fadeIn();
                                        $('#addProductQuantityModal').fadeOut();
                                        $('#addImageModal').fadeIn();

                                        // Gửi AJAX với dữ liệu size đã chọn
                                        $.ajax({
                                            url: 'AddProductQuantityServlet',
                                            type: 'POST',
                                            data: {
                                                product_id: productId,
                                                productprice_id: productprice_id,
                                                quantity: quantity,
                                                size_id: selectedSizes.join(',')
                                            },
                                            success: function (response) {
                                                console.log("Response từ server:", response);
                                                $('#addImageContainer').html(response);
                                                console.log("Đã load nội dung form upload ảnh");

                                                // Đợi DOM được cập nhật hoàn toàn
                                                setTimeout(function () {
                                                    console.log("Bắt đầu khởi tạo drag & drop...");
                                                    initDragAndDropForUpload();
                                                }, 500);
                                            },
                                            error: function (xhr, status, error) {
                                                console.error("Lỗi khi gửi dữ liệu:", error);
                                                console.error("Status:", status);
                                                console.error("Response:", xhr.responseText);
                                                alert("Lỗi khi tải form upload ảnh: " + (xhr.responseText || error));
                                            }
                                        });
                                    };

                                    function initDragAndDropForUpload() {
                                        console.log("🔄 Bắt đầu khởi tạo Drag & Drop...");

                                        // Tìm các phần tử DOM trong container
                                        let container = document.querySelector("#addImageContainer");
                                        if (!container) {
                                            console.error("❌ Không tìm thấy container #addImageContainer");
                                            return;
                                        }

                                        let dropArea = container.querySelector("#dropArea");
                                        let fileInput = container.querySelector("#fileInput");
                                        let browseButton = container.querySelector("#browse");
                                        let previewContainer = container.querySelector("#previewContainer");

                                        // Log để debug
                                        console.log("Container:", container);
                                        console.log("DropArea:", dropArea);
                                        console.log("FileInput:", fileInput);
                                        console.log("BrowseButton:", browseButton);
                                        console.log("PreviewContainer:", previewContainer);

                                        // Kiểm tra xem các phần tử có tồn tại không
                                        if (!dropArea || !fileInput || !browseButton || !previewContainer) {
                                            console.error("❌ Thiếu các phần tử cần thiết cho form upload");
                                            return;
                                        }

                                        // Xóa sự kiện cũ nếu có
                                        dropArea.removeEventListener("dragover", handleDragOver);
                                        dropArea.removeEventListener("dragleave", handleDragLeave);
                                        dropArea.removeEventListener("drop", handleDrop);
                                        browseButton.removeEventListener("click", handleBrowseClick);
                                        fileInput.removeEventListener("change", handleFileChange);

                                        // Thêm sự kiện mới
                                        dropArea.addEventListener("dragover", handleDragOver);
                                        dropArea.addEventListener("dragleave", handleDragLeave);
                                        dropArea.addEventListener("drop", handleDrop);
                                        browseButton.addEventListener("click", handleBrowseClick);
                                        fileInput.addEventListener("change", handleFileChange);

                                        // Click vào dropArea cũng mở hộp thoại chọn file
                                        dropArea.addEventListener("click", function (event) {
                                            if (event.target !== browseButton) {
                                                fileInput.click();
                                            }
                                        });

                                        console.log("✅ Đã khởi tạo xong các event cho Drag & Drop");
                                    }

                                    // Các hàm xử lý sự kiện
                                    function handleDragOver(event) {
                                        event.preventDefault();
                                        event.stopPropagation();
                                        this.style.borderColor = "green";
                                        console.log("🔄 Đang kéo file qua vùng thả");
                                    }

                                    function handleDragLeave(event) {
                                        event.preventDefault();
                                        event.stopPropagation();
                                        this.style.borderColor = "#007bff";
                                        console.log("👋 Đã rời khỏi vùng thả");
                                    }

                                    function handleDrop(event) {
                                        event.preventDefault();
                                        event.stopPropagation();
                                        this.style.borderColor = "#007bff";
                                        console.log("📥 Đã thả file!");

                                        try {
                                            let fileInput = document.querySelector("#addImageContainer #fileInput");
                                            if (!fileInput) {
                                                console.error("❌ Không tìm thấy fileInput");
                                                return;
                                            }

                                            let newFiles = event.dataTransfer.files;
                                            console.log(`📦 Số file được thả: ${newFiles.length}`);

                                            if (typeof DataTransfer !== 'undefined') {
                                                let dataTransfer = new DataTransfer();

                                                // Thêm các file đã có
                                                for (let i = 0; i < fileInput.files.length; i++) {
                                                    dataTransfer.items.add(fileInput.files[i]);
                                                }

                                                // Thêm các file mới
                                                for (let i = 0; i < newFiles.length; i++) {
                                                    dataTransfer.items.add(newFiles[i]);
                                                }

                                                fileInput.files = dataTransfer.files;
                                                updatePreview(fileInput.files);
                                                console.log(`✅ Đã thêm ${newFiles.length} file mới`);
                                                return;
                                            }

                                            alert("Trình duyệt của bạn không hỗ trợ đầy đủ chức năng kéo thả file. Chỉ file cuối cùng sẽ được sử dụng.");
                                            fileInput.files = newFiles;
                                            updatePreview(fileInput.files);
                                        } catch (error) {
                                            console.error("❌ Lỗi xử lý kéo thả:", error);
                                            alert("Có lỗi xảy ra khi xử lý file. Vui lòng sử dụng nút Browse để chọn file.");
                                        }
                                    }

                                    function handleBrowseClick(event) {
                                        event.preventDefault();
                                        event.stopPropagation();
                                        console.log("👆 Đã click nút browse");
                                        let fileInput = document.querySelector("#addImageContainer #fileInput");
                                        if (fileInput) {
                                            fileInput.click();
                                        } else {
                                            console.error("❌ Không tìm thấy fileInput");
                                        }
                                    }

                                    function handleFileChange() {
                                        let fileInput = document.querySelector("#addImageContainer #fileInput");
                                        if (!fileInput) {
                                            console.error("❌ Không tìm thấy fileInput");
                                            return;
                                        }
                                        console.log(`📂 Đã chọn ${fileInput.files.length} file từ hộp thoại`);
                                        updatePreview(fileInput.files);
                                    }

                                    function updatePreview(files) {
                                        let previewContainer = document.querySelector("#addImageContainer #previewContainer");
                                        if (!previewContainer) {
                                            console.error("❌ Không tìm thấy previewContainer");
                                            return;
                                        }

                                        console.log(`🖼️ Cập nhật preview cho ${files.length} file`);
                                        previewContainer.innerHTML = "";

                                        Array.from(files).forEach((file, index) => {
                                            let reader = new FileReader();
                                            reader.onload = function (e) {
                                                let previewItem = document.createElement("div");
                                                previewItem.style.position = "relative";
                                                previewItem.style.display = "inline-block";
                                                previewItem.style.margin = "5px";

                                                let img = document.createElement("img");
                                                img.src = e.target.result;
                                                img.style.width = "100px";
                                                img.style.height = "100px";
                                                img.style.objectFit = "cover";
                                                img.style.borderRadius = "5px";
                                                previewItem.appendChild(img);

                                                let deleteBtn = document.createElement("button");
                                                deleteBtn.innerText = "×";
                                                deleteBtn.style.position = "absolute";
                                                deleteBtn.style.top = "5px";
                                                deleteBtn.style.right = "5px";
                                                deleteBtn.style.background = "red";
                                                deleteBtn.style.color = "white";
                                                deleteBtn.style.border = "none";
                                                deleteBtn.style.cursor = "pointer";
                                                deleteBtn.style.borderRadius = "50%";
                                                deleteBtn.style.width = "20px";
                                                deleteBtn.style.height = "20px";
                                                deleteBtn.style.textAlign = "center";
                                                deleteBtn.style.fontSize = "12px";
                                                deleteBtn.style.lineHeight = "18px";
                                                deleteBtn.onclick = function () {
                                                    removeImage(index);
                                                };
                                                previewItem.appendChild(deleteBtn);

                                                previewContainer.appendChild(previewItem);
                                            };
                                            reader.readAsDataURL(file);
                                        });
                                    }

                                    function removeImage(index) {
                                        console.log(`🗑️ Xóa ảnh tại vị trí ${index}`);
                                        try {
                                            let fileInput = document.querySelector("#addImageContainer #fileInput");
                                            if (!fileInput) {
                                                console.error("❌ Không tìm thấy fileInput");
                                                return;
                                            }

                                            let dataTransfer = new DataTransfer();
                                            let files = Array.from(fileInput.files);
                                            files.splice(index, 1);

                                            files.forEach(file => dataTransfer.items.add(file));
                                            fileInput.files = dataTransfer.files;
                                            updatePreview(fileInput.files);
                                        } catch (error) {
                                            console.error("❌ Lỗi khi xóa ảnh:", error);
                                            alert("Không thể xóa ảnh do trình duyệt không hỗ trợ. Vui lòng tải lại trang và thử lại.");
                                        }
                                    }

                                    //Mở cửa sổ khi thêm sản phẩm thành công
                                    window.showAddProductSuccess = function (event) {
                                        event.preventDefault(); // Ngăn chặn load lại trang khi bấm nút

                                        // Lấy form hiện tại
                                        let form = $("form");
                                        let productprice_id = form.find("input[name='productprice_id']").val();

                                        // Lấy danh sách file từ input type=file
                                        let fileInput = document.querySelector("#addImageContainer #fileInput");
                                        if (!fileInput || fileInput.files.length === 0) {
                                            alert("Vui lòng chọn ít nhất một ảnh!");
                                            return;
                                        }

                                        // Hiển thị nền mờ và modal
                                        $('#overlay').fadeIn();
                                        $('#addImageModal').fadeOut();
                                        $('#addProductSuccessModal').fadeIn();

                                        // Tạo FormData và thêm productprice_id
                                        let formData = new FormData();
                                        formData.append("productprice_id", productprice_id);

                                        // Thêm từng file vào formData
                                        for (let i = 0; i < fileInput.files.length; i++) {
                                            formData.append("images", fileInput.files[i]);
                                        }

                                        // Log số lượng file được gửi để debug
                                        console.log("Gửi " + fileInput.files.length + " file ảnh");

                                        // Hiển thị loading
                                        $('#addProductSuccessContainer').html('<div class="text-center"><div class="spinner-border" role="status"><span class="visually-hidden">Đang xử lý...</span></div><p>Đang tải ảnh lên, vui lòng đợi...</p></div>');

                                        $.ajax({
                                            url: 'TestUploadFileServlet', // Servlet xử lý upload ảnh
                                            type: 'POST',
                                            data: formData,
                                            processData: false, // Không xử lý dữ liệu
                                            contentType: false, // Không đặt content-type
                                            success: function (response) {
                                                // Hiển thị thông báo thành công
                                                $('#addProductSuccessContainer').html(response).show();
                                                // Reload danh sách sản phẩm
                                                setTimeout(function () {
                                                    loadProducts(1, $('#searchInput').val(), '${sortType}');
                                                }, 2000);
                                            },
                                            error: function (xhr, status, error) {
                                                console.error("Lỗi khi upload ảnh:", error);
                                                alert("Lỗi khi tải ảnh lên: " + error);
                                                $('#addProductSuccessContainer').html('<div class="alert alert-danger">Có lỗi xảy ra khi tải ảnh lên. Vui lòng thử lại.</div>');
                                            }
                                        });
                                    };
                                });
    </script>
    <style>
        /* Nền mờ */
        #overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5); /* Màu đen trong suốt */
            z-index: 9998;
        }

        /* Modal hiển thị form */
        .modal {
            position: fixed;
            height: auto;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background: white;
            padding: 20px;
            width: 80%;
            max-width: 1000px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
            z-index: 9999;
        }

    </style>
</html>