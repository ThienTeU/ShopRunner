<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Quản lý nhân viên</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
            .table th, .table td {
                text-align: center;
                vertical-align: middle;
            }
            .btn-primary {
                background-color: #007bff;
                border: none;
            }
            .btn-primary:hover {
                background-color: #dc3545;
            }
            .page-link.active {
                background-color: #007bff;
                border-color: #007bff;
            }
            .container {
                max-width: 900px;
                margin-top: 20px;
            }
            .btn-smaller {
                padding: 6px 12px;
                font-size: 14px;
            }
        </style>
    </head>

    <body>

        <div class="container">
            <h2 class="text-center my-4">Quản lý nhân viên</h2>

            <form action="StaffSearch" method="post" class="row g-3 align-items-end">
                <div class="col-md-2">
                    <label for="userName" class="form-label">Tên</label>
                    <input type="text" class="form-control" id="userName" name="userName">
                </div>
                <div class="col-md-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" name="email">
                </div>
                <div class="col-md-2">
                    <label for="phone" class="form-label">Điện thoại</label>
                    <input type="text" class="form-control" id="phone" name="phone">
                </div>
                <div class="col-md-2">
                    <label for="status" class="form-label">Trạng thái</label>
                    <select class="form-select" id="status" name="status">
                        <option value="">Tất cả</option>
                        <option value="1">Hoạt động</option>
                        <option value="0">Bị Khóa</option>
                    </select>
                </div>
                <div class="col-md-3 d-flex gap-2">
                    <button type="submit" class="btn btn-primary btn-smaller w-100">Tìm kiếm</button>
                    <button type="reset" class="btn btn-secondary btn-smaller w-100">Xóa bộ lọc</button>
                </div>
            </form>

            <div class="text-end my-3">
                <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addStaffModal">Thêm nhân viên</button>
            </div>

            <table class="table table-bordered table-striped">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Tên</th>
                        <th>Email</th>
                        <th>Điện thoại</th>
                        <th>Chức vụ</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="staff" items="${staffs}">
                        <tr>
                            <td>${staff.userId}</td>
                            <td><a href="staffdetail?id=${staff.userId}" class="text-decoration-none">${staff.userName}</a></td>
                            <td>${staff.email}</td>
                            <td>${staff.phoneNumber}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${staff.roleId == 4}">Nhân viên bán hàng</c:when>
                                    <c:when test="${staff.roleId == 3}">Nhân viên quảng cáo</c:when>
                                    <c:otherwise>Không xác định</c:otherwise>
                                </c:choose>
                            </td>
                            <td>${staff.status ? "Hoạt động" : "Bị khóa"}</td>
                            <td>
                                <a href="staffedit?id=${staff.userId}" class="btn btn-warning btn-sm">Sửa</a>
                                <form action="StaffChangeStatus" method="post" class="d-inline">
                                    <input type="hidden" name="userId" value="${staff.userId}">
                                    <button type="submit" class="btn btn-danger btn-sm">
                                        ${staff.status ? "Khóa" : "Mở khóa"}
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="modal fade" id="addStaffModal" tabindex="-1" aria-labelledby="addStaffModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addStaffModalLabel">Thêm nhân viên</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="StaffAdd" method="post">
                                <div class="mb-3">
                                    <label for="role" class="form-label">Chức vụ</label>
                                    <select class="form-select" id="role" name="role">
                                        <option value="4" selected>Nhân viên bán hàng</option>
                                        <option value="3">Nhân viên quảng cáo</option>
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label for="username" class="form-label">Tên người dùng</label>
                                    <input type="text" class="form-control" id="userName" name="userName" required>
                                </div>
                                <div class="mb-3">
                                    <label for="fullName" class="form-label">Họ và tên</label>
                                    <input type="text" class="form-control" id="fullName" name="fullName" required>
                                </div>
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email</label>
                                    <input type="email" class="form-control" id="email" name="email" required>
                                </div>
                                <div class="mb-3">
                                    <label for="phone" class="form-label">Số điện thoại</label>
                                    <input type="text" class="form-control" id="phone" name="phoneNumber" required>
                                </div>
                                <div class="mb-3">
                                    <label for="status" class="form-label">Trạng thái</label>
                                    <select class="form-select" id="status" name="status">
                                        <option value="1" selected>Hoạt động</option>
                                        <option value="0">Bị khóa</option>
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label for="gender" class="form-label">Giới tính</label>
                                    <select class="form-select" id="gender" name="gender">
                                        <option value="1" selected>Nam</option>
                                        <option value="2">Nữ</option>
                                    </select>
                                </div>
                                <div class="text-end">
                                    <button type="submit" class="btn btn-primary">Thêm nhân viên</button>
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Quay lại danh sách</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
