<%@ page import="Model.StaffHieu" %> <!-- Import class StaffHieu -->
<%@ page import="java.util.List" %> <!-- Import List để tránh lỗi -->
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
        .container {
            max-width: 900px;
            margin-top: 20px;
        }
    </style>
</head>

<body>
    <%@ include file="/model/styles.jsp" %>
    <%@ include file="/model/header.jsp" %>

    <div class="container">
        <h2 class="text-center my-4">Quản lý nhân viên</h2>

        <!-- Form tìm kiếm -->
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

        <!-- Bảng danh sách nhân viên -->
        <table class="table table-bordered table-striped mt-3">
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

        <!-- Modal thêm nhân viên -->
        <div class="modal fade" id="addStaffModal" tabindex="-1" aria-labelledby="addStaffLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Thêm Nhân Viên</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <form action="StaffAdd" method="post">
                    <!-- Hiển thị lỗi nếu có -->
                    <% if (request.getAttribute("errors") != null) { %>
                        <div class="alert alert-danger">
                            <ul>
                                <% for (String error : (List<String>) request.getAttribute("errors")) { %>
                                    <li><%= error %></li>
                                <% } %>
                            </ul>
                        </div>
                    <% } %>

                    <% if (request.getAttribute("success") != null) { %>
                        <div class="alert alert-success"><%= request.getAttribute("success") %></div>
                    <% } %>

                    <% if (request.getAttribute("error") != null) { %>
                        <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
                    <% } %>

                    <!-- Các input nhập thông tin nhân viên -->
                    <div class="mb-3">
                        <label for="role" class="form-label">Chức vụ</label>
                        <select class="form-select" id="role" name="role">
                            <option value="4" selected>Nhân viên bán hàng</option>
                            <option value="3">Nhân viên quảng cáo</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="userName" class="form-label">Tên tài khoản</label>
                        <input type="text" class="form-control" id="userName" name="userName"
                               value="<%= request.getAttribute("staff") != null ? ((StaffHieu) request.getAttribute("staff")).getUserName() : "" %>" required>
                    </div>
                    <div class="mb-3">
                        <label for="fullName" class="form-label">Họ và tên</label>
                        <input type="text" class="form-control" id="fullName" name="fullName"
                               value="<%= request.getAttribute("staff") != null ? ((StaffHieu) request.getAttribute("staff")).getFullName() : "" %>" required>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email"
                               value="<%= request.getAttribute("staff") != null ? ((StaffHieu) request.getAttribute("staff")).getEmail() : "" %>" required>
                    </div>
                    <div class="mb-3">
                        <label for="phone" class="form-label">Số điện thoại</label>
                        <input type="text" class="form-control" id="phone" name="phoneNumber"
                               value="<%= request.getAttribute("staff") != null ? ((StaffHieu) request.getAttribute("staff")).getPhoneNumber() : "" %>" required>
                    </div>
                    <div class="mb-3">
                        <label for="status" class="form-label">Trạng thái</label>
                        <select class="form-select" id="status" name="status">
                            <option value="1" <%= (request.getAttribute("staff") != null && ((StaffHieu) request.getAttribute("staff")).isStatus()) ? "selected" : "" %>>Hoạt động</option>
                            <option value="0" <%= (request.getAttribute("staff") != null && !((StaffHieu) request.getAttribute("staff")).isStatus()) ? "selected" : "" %>>Bị khóa</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="gender" class="form-label">Giới tính</label>
                        <select class="form-select" id="gender" name="gender">
                            <option value="1" <%= (request.getAttribute("staff") != null && ((StaffHieu) request.getAttribute("staff")).getGenderId() == 1) ? "selected" : "" %>>Nam</option>
                            <option value="2" <%= (request.getAttribute("staff") != null && ((StaffHieu) request.getAttribute("staff")).getGenderId() == 2) ? "selected" : "" %>>Nữ</option>
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
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            <% if (request.getAttribute("errors") != null || request.getAttribute("success") != null || request.getAttribute("error") != null) { %>
                var myModal = new bootstrap.Modal(document.getElementById('addStaffModal'));
                myModal.show();
            <% } %>
        });
    </script>

    <%@ include file="/model/footer.jsp" %>
</body>
</html>
