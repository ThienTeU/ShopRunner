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
            body {
                font-family: 'Roboto', sans-serif;
                background-color: #f8f9fa;
            }

            .profile-header {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                padding: 80px 0 30px;
                margin-bottom: -60px;
                position: relative;
                text-align: center;
            }

            .profile-img {
                width: 150px;
                height: 150px;
                border-radius: 50%;
                border: 5px solid white;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }

            .profile-card {
                border: none;
                border-radius: 15px;
                box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
                background: white;
            }

            .stats-card {
                border: none;
                border-radius: 10px;
                text-align: center;
                padding: 20px;
                color: white;
                transition: transform 0.3s;
            }

            .stats-card:hover {
                transform: translateY(-5px);
            }

            .nav-pills .nav-link {
                border-radius: 10px;
                color: #6a11cb;
                font-weight: 500;
                transition: all 0.3s ease-in-out;
            }

            .nav-pills .nav-link.active {
                background-color: #6a11cb;
                color: white;
                box-shadow: 0 4px 10px rgba(106, 17, 203, 0.3);
            }

            .btn-primary {
                background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
                border: none;
                font-weight: 600;
                padding: 10px 15px;
                transition: all 0.3s ease-in-out;
            }

            .btn-primary:hover {
                background: linear-gradient(135deg, #2575fc 0%, #6a11cb 100%);
                box-shadow: 0 4px 15px rgba(106, 17, 203, 0.5);
            }

            .table-hover tbody tr:hover {
                background-color: rgba(106, 17, 203, 0.1);
            }

            .badge {
                font-size: 0.9rem;
                padding: 0.5em 0.75em;
                text-transform: capitalize;
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


            body {
                font-family: 'Roboto', sans-serif;
                background-color: #f8f9fa;
                overflow-x: hidden;
            }

            .profile-header {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                padding: 80px 0 30px;
                margin-bottom: -60px;
                position: relative;
                text-align: center;
            }

            .profile-img {
                width: 150px;
                height: 150px;
                border-radius: 50%;
                border: 5px solid white;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                transition: transform 0.3s ease-in-out;
            }

            .profile-img:hover {
                transform: scale(1.1);
            }

            .profile-card {
                border: none;
                border-radius: 15px;
                box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
                background: white;
                transition: all 0.3s ease-in-out;
            }

            .profile-card:hover {
                box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
            }

            .stats-card {
                border: none;
                border-radius: 10px;
                text-align: center;
                padding: 20px;
                color: white;
                transition: transform 0.3s, box-shadow 0.3s;
            }

            .stats-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
            }

            .stats-card.bg-primary {
                background-color: #6c63ff;
            }
            .stats-card.bg-success {
                background-color: #28a745;
            }
            .stats-card.bg-info {
                background-color: #17a2b8;
            }

            .nav-pills .nav-link {
                border-radius: 10px;
                color: #6a11cb;
                font-weight: 500;
                transition: all 0.3s ease-in-out;
            }

            .nav-pills .nav-link.active {
                background-color: #6a11cb;
                color: white;
                box-shadow: 0 4px 10px rgba(106, 17, 203, 0.3);
            }

            .btn-primary {
                background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
                border: none;
                font-weight: 600;
                padding: 10px 15px;
                transition: all 0.3s ease-in-out;
            }

            .btn-primary:hover {
                background: linear-gradient(135deg, #2575fc 0%, #6a11cb 100%);
                box-shadow: 0 4px 15px rgba(106, 17, 203, 0.5);
            }

            .table-hover tbody tr:hover {
                background-color: rgba(106, 17, 203, 0.1);
            }

            .badge {
                font-size: 0.9rem;
                padding: 0.5em 0.75em;
                text-transform: capitalize;
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

            .modal-footer .btn-secondary:hover {
                background-color: #ddd;
            }

            .badge {
                font-size: 0.9rem;
                padding: 0.5em 0.75em;
                text-transform: capitalize;
            }

            .table-hover tbody tr:hover {
                background-color: rgba(106, 17, 203, 0.1);
            }

            .badge.bg-success {
                background-color: #28a745 !important;
            }

            .badge.bg-warning {
                background-color: #ffc107 !important;
                color: #212529;
            }

            .badge.bg-danger {
                background-color: #dc3545 !important;
            }

            .badge.bg-secondary {
                background-color: #6c757d !important;
            }
        </style>
    </head>
    <body>
        <!-- Header -->
        <div class="profile-header">
            <div class="container">
                <img src="${user.imgUser != null ? user.imgUser : 'default-profile.png'}" 
                     alt="Profile Image" class="profile-img mb-4">
                <h2>${user.fullName}</h2>
                <p class="lead">Thành viên từ ${user.createdAt}</p>
            </div>
            <button type="button" class="btn btn-outline-light btn-sm position-absolute top-0 end-0 me-3 mt-3" 
                    data-bs-toggle="modal" data-bs-target="#editImageModal">
                <i class="fas fa-edit"></i>
            </button>
        </div>

        <!-- Main Content -->
        <div class="container py-5">
            <div class="row">
                <!-- Thống kê -->
                <div class="col-12 mb-4">
                    <div class="row g-3">
                        <div class="col-md-4">
                            <div class="stats-card bg-primary">
                                <h3>${stats.orders}</h3>
                                <p><strong>Tổng số đơn hàng:</strong> ${totalOrders}</p>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="stats-card bg-success">
                                <h3>${stats.reviews}</h3>
                                <p><strong>Tổng số lượt đánh giá:</strong> ${totalFeedbacks}</p>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="stats-card bg-info">
                                <h3>${stats.favorites}</h3>
                                <p><strong>Yêu thích</strong></p>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Thông tin cá nhân -->
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
                                    <a class="nav-link" id="feedback-tab" data-bs-toggle="pill" href="#address" role="tab">
                                        Địa chỉ
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" id="security-tab" data-bs-toggle="pill" href="#security" role="tab">
                                        Bảo mật
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" id="feedback-tab" data-bs-toggle="pill" href="#feedback" role="tab">
                                        Lịch sử phản hồi
                                    </a>
                                </li>
                            </ul>

                            <div class="tab-content" id="profileTabContent">
                                <!-- Tab Thông tin cá nhân -->
                                <div class="tab-pane fade show active" id="info" role="tabpanel">
                                    <c:if test="${not empty user}">
                                        <p><strong>Tên đăng nhập:</strong> ${user.userName}</p>
                                        <p><strong>Họ và tên:</strong> ${user.fullName}</p>
                                        <p><strong>Email:</strong> ${user.email}</p>
                                        <p><strong>Số điện thoại:</strong> ${user.phoneNumber}</p>
                                    </c:if>
                                    <c:if test="${empty user}">
                                        <p>Không tìm thấy thông tin người dùng.</p>
                                    </c:if>
                                    <ul class="nav nav-pills mb-4" id="profileTab" role="tablist">
                                        <li class="nav-item">
                                            <a class="nav-link" id="update-profile-tab" data-bs-toggle="pill" href="#update-profile" role="tab">
                                                Cập nhật thông tin
                                            </a>
                                        </li>
                                    </ul>
                                    <div class="tab-pane fade" id="update-profile" role="tabpanel">
                                        <h5>Cập nhật thông tin cá nhân</h5>

                                        <form action="update-profile" method="POST">
                                            <!-- Hidden field để gửi userId -->
                                            <input type="hidden" name="userId" value="${user.userId}">

                                            <div class="mb-3">
                                                <label for="fullName" class="form-label">Họ và tên</label>
                                                <input type="text" class="form-control" id="fullName" name="fullName" value="${user.fullName}" required>
                                            </div>

                                            <div class="mb-3">
                                                <label for="email" class="form-label">Email</label>
                                                <input type="email" class="form-control" id="email" name="email" value="${user.email}" required>
                                            </div>

                                            <div class="mb-3">
                                                <label for="phoneNumber" class="form-label">Số điện thoại</label>
                                                <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}" required>
                                            </div>

                                            <button type="submit" class="btn btn-primary">Cập nhật</button>
                                        </form>

                                        <!-- Hiển thị thông báo lỗi nếu có -->
                                        <c:if test="${not empty errorMessage}">
                                            <div class="alert alert-danger mt-3">
                                                ${errorMessage}
                                            </div>
                                        </c:if>
                                    </div>


                                </div>


                                <div class="tab-pane fade" id="address" role="tabpanel">
                                    <div class="card-header">
                                        <h5>Danh sách địa chỉ</h5>
                                    </div>
                                    <div class="card-body">
                                        <!-- Kiểm tra nếu danh sách địa chỉ không rỗng -->
                                        <c:if test="${not empty addresses}">
                                            <ul class="list-group">
                                                <!-- Lặp qua danh sách địa chỉ -->
                                                <c:forEach items="${addresses}" var="address">
                                                    <li class="list-group-item d-flex justify-content-between align-items-center">
                                                        <div>
                                                            <p><strong>Người nhận:</strong> ${address.name}</p>
                                                            <p><strong>Địa chỉ:</strong> ${address.street}, ${address.ward}, ${address.district}, ${address.city}</p>
                                                            <p><strong>Số điện thoại:</strong> ${address.phone}</p>
                                                        </div>
                                                        <div>
                                                            <!-- Nút chỉnh sửa -->
                                                            <button class="btn btn-sm btn-outline-primary me-2" data-bs-toggle="modal" data-bs-target="#editAddressModal-${address.addressId}">
                                                                <i class="fas fa-edit"></i> Chỉnh sửa
                                                            </button>

                                                            <!-- Modal chỉnh sửa địa chỉ -->
                                                            <div class="modal fade" id="editAddressModal-${address.addressId}" tabindex="-1" aria-labelledby="editAddressModalLabel-${address.addressId}" aria-hidden="true">
                                                                <div class="modal-dialog">
                                                                    <div class="modal-content">
                                                                        <form action="profile" method="POST">
                                                                            <input type="hidden" name="action" value="editAddress">
                                                                            <input type="hidden" name="addressId" value="${address.addressId}">
                                                                            <div class="modal-header">
                                                                                <h5 class="modal-title" id="editAddressModalLabel-${address.addressId}">Chỉnh sửa địa chỉ</h5>
                                                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                                            </div>
                                                                            <div class="modal-body">
                                                                                <div class="mb-3">
                                                                                    <label for="name-${address.addressId}" class="form-label">Họ và tên người nhận</label>
                                                                                    <input type="text" class="form-control" id="name-${address.addressId}" name="name" value="${address.name}" required>
                                                                                </div>
                                                                                <div class="mb-3">
                                                                                    <label for="phone-${address.addressId}" class="form-label">Số điện thoại</label>
                                                                                    <input type="text" class="form-control" id="phone-${address.addressId}" name="phone" value="${address.phone}" required pattern="[0-9]{10,11}" title="Số điện thoại phải chứa 10-11 chữ số">
                                                                                </div>
                                                                                <div class="mb-3">
                                                                                    <label for="city-${address.addressId}" class="form-label">Thành phố</label>
                                                                                    <input type="text" class="form-control" id="city-${address.addressId}" name="city" value="${address.city}" required>
                                                                                </div>
                                                                                <div class="mb-3">
                                                                                    <label for="district-${address.addressId}" class="form-label">Quận</label>
                                                                                    <input type="text" class="form-control" id="district-${address.addressId}" name="district" value="${address.district}" required>
                                                                                </div>
                                                                                <div class="mb-3">
                                                                                    <label for="ward-${address.addressId}" class="form-label">Phường</label>
                                                                                    <input type="text" class="form-control" id="ward-${address.addressId}" name="ward" value="${address.ward}" required>
                                                                                </div>
                                                                                <div class="mb-3">
                                                                                    <label for="street-${address.addressId}" class="form-label">Đường</label>
                                                                                    <input type="text" class="form-control" id="street-${address.addressId}" name="street" value="${address.street}" required>
                                                                                </div>
                                                                            </div>
                                                                            <div class="modal-footer">
                                                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                                                                <button type="submit" class="btn btn-primary">Cập nhật</button>
                                                                            </div>
                                                                        </form>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <!-- Nút xóa -->
                                                            <button class="btn btn-sm btn-outline-danger" data-bs-toggle="modal" data-bs-target="#deleteAddressModal-${address.addressId}">
                                                                <i class="fas fa-trash"></i> Xóa
                                                            </button>

                                                            <!-- Modal xóa địa chỉ -->
                                                            <div class="modal fade" id="deleteAddressModal-${address.addressId}" tabindex="-1" aria-labelledby="deleteAddressModalLabel-${address.addressId}" aria-hidden="true">
                                                                <div class="modal-dialog">
                                                                    <div class="modal-content">
                                                                        <form action="profile" method="POST">
                                                                            <input type="hidden" name="action" value="deleteAddress">
                                                                            <input type="hidden" name="addressId" value="${address.addressId}">
                                                                            <div class="modal-header">
                                                                                <h5 class="modal-title" id="deleteAddressModalLabel-${address.addressId}">Xóa địa chỉ</h5>
                                                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                                            </div>
                                                                            <div class="modal-body">
                                                                                <p>Bạn có chắc chắn muốn xóa địa chỉ này không?</p>
                                                                                <p><strong>Người nhận:</strong> ${address.name}</p>
                                                                                <p><strong>Địa chỉ:</strong> ${address.street}, ${address.ward}, ${address.district}, ${address.city}</p>
                                                                                <p><strong>Số điện thoại:</strong> ${address.phone}</p>
                                                                            </div>
                                                                            <div class="modal-footer">
                                                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                                                                <button type="submit" class="btn btn-danger">Xóa</button>
                                                                            </div>
                                                                        </form>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </c:if>
                                        <!-- Hiển thị thông báo nếu danh sách địa chỉ rỗng -->
                                        <c:if test="${empty addresses}">
                                            <p>Không có địa chỉ nào được lưu. Nhấn vào nút "Thêm địa chỉ mới" để thêm địa chỉ.</p>
                                        </c:if>
                                    </div>

                                    <!-- Nút thêm địa chỉ -->
                                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addAddressModal">
                                        <i class="fas fa-plus"></i> Thêm địa chỉ mới
                                    </button>

                                    <!-- Modal thêm địa chỉ -->
                                    <div class="modal fade" id="addAddressModal" tabindex="-1" aria-labelledby="addAddressModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <form action="profile" method="POST">
                                                    <input type="hidden" name="action" value="addAddress">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="addAddressModalLabel">Thêm địa chỉ mới</h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label for="name" class="form-label">Họ và tên người nhận</label>
                                                            <input type="text" class="form-control" id="name" name="name" required>
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="phone" class="form-label">Số điện thoại</label>
                                                            <input type="text" class="form-control" id="phone" name="phone" required pattern="[0-9]{10,11}" title="Số điện thoại phải chứa 10-11 chữ số">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="city" class="form-label">Thành phố</label>
                                                            <input type="text" class="form-control" id="city" name="city" required>
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="district" class="form-label">Quận</label>
                                                            <input type="text" class="form-control" id="district" name="district" required>
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="ward" class="form-label">Phường</label>
                                                            <input type="text" class="form-control" id="ward" name="ward" required>
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="street" class="form-label">Đường</label>
                                                            <input type="text" class="form-control" id="street" name="street" required>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                                        <button type="submit" class="btn btn-primary">Thêm địa chỉ</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>


                                <!-- Tab Đơn hàng -->
                                <div class="tab-pane fade" id="orders" role="tabpanel">
                                    <h3>Danh sách đơn hàng</h3>
                                    <c:if test="${not empty orders}">
                                        <table class="table table-hover align-middle">
                                            <thead class="table-light">
                                                <tr>
                                                    <th>#</th>
                                                    <th>Ngày đặt</th>
                                                    <th>Tổng giá trị</th>
                                                    <th>Trạng thái</th>
                                                    <th>Chi tiết</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${orders}" var="order" varStatus="status">
                                                    <tr>
                                                        <th scope="row">${status.index + 1}</th>
                                                        <td>${order.order_date}</td>
                                                        <td>${order.total_price}đ</td>
                                                        <td>
                                                            <span class="badge ${order.status == 'Đã giao' ? 'bg-success' : order.status == 'Đang xử lý' ? 'bg-warning text-dark' : order.status == 'Đã hủy' ? 'bg-danger' : 'bg-secondary'}">
                                                                ${order.status}
                                                            </span>
                                                        </td>
                                                        <td>
                                                            <a href="ordersprofile?action=details&orderId=${order.order_id}" class="btn btn-sm btn-primary">
                                                                <i class="fas fa-eye"></i> Xem chi tiết
                                                            </a>

                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </c:if>
                                    <c:if test="${empty orders}">
                                        <div class="alert alert-warning" role="alert">
                                            <i class="fas fa-exclamation-circle"></i> Không có đơn hàng nào được tìm thấy.
                                        </div>
                                    </c:if>
                                </div>

                                <!-- Tab Bảo mật -->
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

                                <!-- Tab Lịch sử phản hồi -->
                                <div class="tab-pane fade" id="feedback" role="tabpanel">
                                    <h3>Lịch sử phản hồi</h3>
                                    <c:if test="${not empty feedbackHistory}">
                                        <table class="table table-hover align-middle">
                                            <thead class="table-light">
                                                <tr>
                                                    <th>#</th>
                                                    <th>Sản phẩm</th>
                                                    <th>Nội dung phản hồi</th>
                                                    <th>Đánh giá</th>
                                                    <th>Ngày tạo</th>
                                                    <th>Trạng thái</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${feedbackHistory}" var="feedback" varStatus="status">
                                                    <tr>
                                                        <th scope="row">${status.index + 1}</th>
                                                        <td>${feedback.productName}</td>
                                                        <td>${feedback.feedbackContent}</td>
                                                        <td>
                                                            <span class="badge ${feedback.rating >= 4 ? 'bg-success' : feedback.rating >= 2 ? 'bg-warning text-dark' : 'bg-danger'}">
                                                                ${feedback.rating} <i class="fas fa-star"></i>
                                                            </span>
                                                        </td>
                                                        <td>${feedback.createdAt}</td>
                                                        <td>
                                                            <span class="badge ${feedback.status ? 'bg-success' : 'bg-secondary'}">
                                                                ${feedback.status ? 'Hiển thị' : 'Ẩn'}
                                                            </span>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </c:if>
                                    <c:if test="${empty feedbackHistory}">
                                        <div class="alert alert-warning" role="alert">
                                            <i class="fas fa-exclamation-circle"></i> Bạn chưa có phản hồi nào.
                                        </div>
                                    </c:if>
                                </div>


                                <!-- Sidebar -->
                                <!--                <div class="col-md-4">
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
                                
                                                    <div class="profile-card card">
                                                        <div class="card-body">
                                                            <h5 class="card-title">Hoạt động gần đây</h5>
                                                            <div class="activity-timeline">
                                <c:forEach items="${recentActivities}" var="activity">
                                    <div class="activity-item">
                                        <small class="text-muted">${activity.time}</small>
                                        <p class="mb-0">${activity.description}</p>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>-->
                            </div>
                        </div>

                        <!-- Bootstrap JS -->
                        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
                        <script>
                            // Hiệu ứng cho các nút
                            document.querySelectorAll('.btn').forEach(btn => {
                                btn.addEventListener('mouseenter', () => btn.classList.add('shadow-lg'));
                                btn.addEventListener('mouseleave', () => btn.classList.remove('shadow-lg'));
                            });
                        </script>
                        </body>
                        </html>