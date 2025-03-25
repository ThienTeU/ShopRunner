<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Quản lý nhân viên</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
            .table th, .table td { text-align: center; vertical-align: middle; }
            .btn-primary { background-color: #007bff; border: none; }
            .btn-primary:hover { background-color: #dc3545; }
            .page-link.active { background-color: #007bff; border-color: #007bff; }
            .container { max-width: 900px; margin-top: 20px; }
            .btn-smaller { padding: 6px 12px; font-size: 14px; }
        </style>
    </head>
    <body>
        <div class="container">
            <h2 class="text-center my-4">Quản lý nhân viên</h2>
            
            <form action="customersearch" method="post" class="row g-3 align-items-end">
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
                <a href="#" class="btn btn-primary">Thêm nhân viên</a>
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
                                    <c:when test="${staff.roleId == 3}">Nhân viên bán hàng</c:when>
                                    <c:when test="${staff.roleId == 4}">Nhân viên quảng cáo</c:when>
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
            
            <nav>
                <ul class="pagination justify-content-center">
                    <c:if test="${index > 1}">
                        <li class="page-item">
                            <a class="page-link" href="customerlist?index=${index - 1}">Trước</a>
                        </li>
                    </c:if>
                    <c:forEach begin="1" end="${end}" var="i">
                        <li class="page-item ${i eq index ? 'active' : ''}">
                            <a class="page-link" href="customerlist?index=${i}">${i}</a>
                        </li>
                    </c:forEach>
                    <c:if test="${index < end}">
                        <li class="page-item">
                            <a class="page-link" href="customerlist?index=${index + 1}">Sau</a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </body>
</html>