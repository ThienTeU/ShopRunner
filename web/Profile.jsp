<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông tin cá nhân | RunnerShop</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <!-- Custom CSS -->
    <style>
        .profile-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 100px 0 30px;
            margin-bottom: -60px;
        }
        
        .profile-img {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            border: 5px solid white;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .profile-card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
        }
        
        .stats-card {
            border: none;
            border-radius: 10px;
            transition: transform 0.3s;
        }
        
        .stats-card:hover {
            transform: translateY(-5px);
        }
        
        .nav-pills .nav-link.active {
            background-color: #667eea;
        }
        
        .form-control:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102,126,234,0.25);
        }
        
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
        }
        
        .btn-primary:hover {
            background: linear-gradient(135deg, #764ba2 0%, #667eea 100%);
        }
        
        .activity-item {
            border-left: 2px solid #667eea;
            padding-left: 20px;
            margin-bottom: 20px;
            position: relative;
        }
        
        .activity-item::before {
            content: '';
            width: 12px;
            height: 12px;
            background: #667eea;
            border-radius: 50%;
            position: absolute;
            left: -7px;
            top: 0;
        }
    </style>
</head>
<body>

<!-- Header Section -->
<div class="profile-header text-center">
    <div class="container">
        <img src="${user.avatarUrl != null ? user.avatarUrl : 'https://via.placeholder.com/150'}" 
             alt="Profile Image" class="profile-img mb-4">
        <h2>${user.email}</h2>
        <p class="lead">Thành viên từ ${user.createdDate}</p>
    </div>
</div>

<!-- Main Content -->
<div class="container py-5">
    <div class="row">
        <!-- Stats Cards -->
        <div class="col-12 mb-4">
            <div class="row g-3">
                <div class="col-md-3">
                    <div class="stats-card card bg-primary text-white p-3 text-center">
                        <h3>12</h3>
                        <p class="mb-0">Đơn hàng</p>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="stats-card card bg-success text-white p-3 text-center">
                        <h3>5</h3>
                        <p class="mb-0">Đánh giá</p>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="stats-card card bg-info text-white p-3 text-center">
                        <h3>8</h3>
                        <p class="mb-0">Yêu thích</p>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="stats-card card bg-warning text-white p-3 text-center">
                        <h3>3</h3>
                        <p class="mb-0">Khuyến mãi</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- Profile Content -->
        <div class="col-md-8">
            <div class="profile-card card">
                <div class="card-body">
                    <ul class="nav nav-pills mb-4" id="profileTab" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" id="info-tab" data-bs-toggle="pill" href="#info" role="tab">
                                Thông tin cá nhân
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="orders-tab" data-bs-toggle="pill" href="#orders" role="tab">
                                Đơn hàng
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="security-tab" data-bs-toggle="pill" href="#security" role="tab">
                                Bảo mật
                            </a>
                        </li>
                    </ul>

                    <div class="tab-content" id="profileTabContent">
                        <!-- Thông tin cá nhân -->
       <!-- Thêm section địa chỉ vào tab Thông tin cá nhân -->
<div class="tab-pane fade show active" id="info" role="tabpanel">
    <form action="profile" method="POST">
        <input type="hidden" name="action" value="updateProfile">
        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" class="form-control" value="${user.email}" readonly>
        </div>
        <div class="mb-3">
            <label class="form-label">Số điện thoại</label>
            <input type="tel" class="form-control" name="phone" value="${user.phoneNumber}">
        </div>
        <button type="submit" class="btn btn-primary">Cập nhật thông tin</button>
    </form>

    <!-- Phần địa chỉ -->
    <hr>
    <h5>Danh sách địa chỉ</h5>
    <div class="addresses-list">
        <c:forEach items="${addresses}" var="address">
            <div class="card mb-3">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <h6 class="card-title">${address.name} 
                            <c:if test="${address.isDefault}">
                                <span class="badge bg-primary">Mặc định</span>
                            </c:if>
                        </h6>
                        <div class="btn-group">
                            <button type="button" class="btn btn-sm btn-outline-primary" 
                                    onclick="editAddress(${address.addressId})">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button type="button" class="btn btn-sm btn-outline-danger" 
                                    onclick="deleteAddress(${address.addressId})">
                                <i class="fas fa-trash"></i>
                            </button>
                            <c:if test="${!address.isDefault}">
                                <form action="profile" method="POST" style="display: inline;">
                                    <input type="hidden" name="action" value="setDefaultAddress">
                                    <input type="hidden" name="addressId" value="${address.addressId}">
                                    <button type="submit" class="btn btn-sm btn-outline-success">
                                        Đặt mặc định
                                    </button>
                                </form>
                            </c:if>
                        </div>
                    </div>
                    <p class="card-text mb-1">${address.phone}</p>
                    <p class="card-text">${address.street}, ${address.ward}, ${address.district}, ${address.city}</p>
                </div>
            </div>
        </c:forEach>
    </div>

    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addAddressModal">
        <i class="fas fa-plus"></i> Thêm địa chỉ mới
    </button>
</div>

<!-- Modal thêm địa chỉ -->
<div class="modal fade" id="addAddressModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Thêm địa chỉ mới</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form action="profile" method="POST" id="addAddressForm">
                    <input type="hidden" name="action" value="addAddress">
                    <div class="mb-3">
                        <label class="form-label">Tên người nhận</label>
                        <input type="text" class="form-control" name="name" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Số điện thoại</label>
                        <input type="tel" class="form-control" name="phone" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Tỉnh/Thành phố</label>
                        <input type="text" class="form-control" name="city" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Quận/Huyện</label>
                        <input type="text" class="form-control" name="district" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Phường/Xã</label>
                        <input type="text" class="form-control" name="ward" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Địa chỉ cụ thể</label>
                        <input type="text" class="form-control" name="street" required>
                    </div>
                    <div class="mb-3 form-check">
                        <input type="checkbox" class="form-check-input" name="isDefault" id="isDefault">
                        <label class="form-check-label" for="isDefault">Đặt làm địa chỉ mặc định</label>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                <button type="submit" form="addAddressForm" class="btn btn-primary">Thêm địa chỉ</button>
            </div>
        </div>
    </div>
</div>


                        <!-- Đơn hàng -->
                        <div class="tab-pane fade" id="orders" role="tabpanel">
                            <div class="activity-timeline">
                                <div class="activity-item">
                                    <h5>Đơn hàng #12345</h5>
                                    <p class="text-muted">Đã giao thành công - 12/03/2024</p>
                                    <p>Tổng giá trị: 1.200.000đ</p>
                                    <a href="#" class="btn btn-sm btn-outline-primary">Xem chi tiết</a>
                                </div>
                                <!-- Thêm các đơn hàng khác -->
                            </div>
                        </div>

                        <!-- Bảo mật -->
                        <div class="tab-pane fade" id="security" role="tabpanel">
                            <form action="/RunnerShop/profile/change-password" method="POST">
                                <div class="mb-3">
                                    <label class="form-label">Mật khẩu hiện tại</label>
                                    <input type="password" class="form-control" name="currentPassword">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Mật khẩu mới</label>
                                    <input type="password" class="form-control" name="newPassword">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Xác nhận mật khẩu mới</label>
                                    <input type="password" class="form-control" name="confirmPassword">
                                </div>
                                <button type="submit" class="btn btn-primary">Đổi mật khẩu</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Sidebar -->
        <div class="col-md-4">
            <!-- Quick Actions -->
            <div class="profile-card card mb-4">
                <div class="card-body">
                    <h5 class="card-title">Thao tác nhanh</h5>
                    <div class="d-grid gap-2">
                        <a href="/RunnerShop/wishlist" class="btn btn-outline-primary">
                            <i class="fas fa-heart me-2"></i>Danh sách yêu thích
                        </a>
                        <a href="/RunnerShop/cart" class="btn btn-outline-primary">
                            <i class="fas fa-shopping-cart me-2"></i>Giỏ hàng
                        </a>
                        <a href="/RunnerShop/reviews" class="btn btn-outline-primary">
                            <i class="fas fa-star me-2"></i>Đánh giá của tôi
                        </a>
                    </div>
                </div>
            </div>

            <!-- Recent Activities -->
            <div class="profile-card card">
                <div class="card-body">
                    <h5 class="card-title">Hoạt động gần đây</h5>
                    <div class="activity-timeline">
                        <div class="activity-item">
                            <small class="text-muted">2 giờ trước</small>
                            <p class="mb-0">Đã thêm sản phẩm vào giỏ hàng</p>
                        </div>
                        <div class="activity-item">
                            <small class="text-muted">1 ngày trước</small>
                            <p class="mb-0">Đã đánh giá sản phẩm</p>
                        </div>
                        <div class="activity-item">
                            <small class="text-muted">3 ngày trước</small>
                            <p class="mb-0">Đã hoàn thành đơn hàng #12345</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- Custom JS -->
<script>
// Thêm vào phần script ở cuối file
function editAddress(addressId) {
    // Lấy thông tin địa chỉ từ server
    fetch(`profile?action=getAddress&addressId=${addressId}`)
        .then(response => response.json())
        .then(address => {
            // Populate form
            document.querySelector('[name="name"]').value = address.name;
            document.querySelector('[name="phone"]').value = address.phone;
            document.querySelector('[name="city"]').value = address.city;
            document.querySelector('[name="district"]').value = address.district;
            document.querySelector('[name="ward"]').value = address.ward;
            document.querySelector('[name="street"]').value = address.street;
            document.querySelector('[name="isDefault"]').checked = address.isDefault;
            
            // Thay đổi action và thêm addressId
            const form = document.getElementById('addAddressForm');
            form.querySelector('[name="action"]').value = 'updateAddress';
            const addressIdInput = document.createElement('input');
            addressIdInput.type = 'hidden';
            addressIdInput.name = 'addressId';
            addressIdInput.value = addressId;
            form.appendChild(addressIdInput);
            
            // Hiển thị modal
            new bootstrap.Modal(document.getElementById('addAddressModal')).show();
        });
}

function deleteAddress(addressId) {
    if (confirm('Bạn có chắc chắn muốn xóa địa chỉ này?')) {
        const form = document.createElement('form');
        form.method = 'POST';
        form.action = 'profile';
        
        const actionInput = document.createElement('input');
        actionInput.type = 'hidden';
        actionInput.name = 'action';
        actionInput.value = 'deleteAddress';
        
        const addressIdInput = document.createElement('input');
        addressIdInput.type = 'hidden';
        addressIdInput.name = 'addressId';
        addressIdInput.value = addressId;
        
        form.appendChild(actionInput);
        form.appendChild(addressIdInput);
        document.body.appendChild(form);
        form.submit();
    }
}

// Hiển thị thông báo
function showNotification(message, type) {
    const toast = document.createElement('div');
    toast.className = `toast align-items-center text-white bg-${type} border-0`;
    toast.setAttribute('role', 'alert');
    toast.setAttribute('aria-live', 'assertive');
    toast.setAttribute('aria-atomic', 'true');
    
    toast.innerHTML = `
        <div class="d-flex">
            <div class="toast-body">${message}</div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
        </div>
    `;
    
    document.body.appendChild(toast);
    const bsToast = new bootstrap.Toast(toast);
    bsToast.show();
    
    toast.addEventListener('hidden.bs.toast', () => {
        toast.remove();
    });
}

// Hiển thị thông báo nếu có
<c:if test="${not empty sessionScope.message}">
    showNotification('${sessionScope.message}', '${sessionScope.messageType}');
    <% session.removeAttribute("message"); %>
    <% session.removeAttribute("messageType"); %>
</c:if>

</script>

</body>
</html>
