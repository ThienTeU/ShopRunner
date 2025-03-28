<%-- 
    Document   : feedbackmanagement
    Created on : Mar 25, 2025, 8:38:09 AM
    Author     : tuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">

    <head>
        <title>Quản lý Feedback Khách Hàng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    </head>

    <body class="bg-light">
        <!-- Modal thêm phản hồi -->


        <div class="container-fluid my-5">

            <h1 class="text-center mb-4">Quản lý Feedback Khách Hàng</h1>

            <!-- Form tìm kiếm -->
            <div class="card p-4 mb-4">
                <h2 class="h4 mb-3">Tìm kiếm Feedback</h2>
                <form action="feedbacksearch" method="post">
                    <div class="row g-3">
                        <div class="col-md-4">
                            <input type="text" name="searchName" class="form-control" placeholder="Nhập tên khách hàng">
                        </div>
                        <div class="col-md-4">
                            <input type="text" name="searchProduct" class="form-control" placeholder="Nhập tên sản phẩm">
                        </div>
                        <div class="col-md-3">
                            <select name="ratingFilter" class="form-select">
                                <option value="">Tất cả đánh giá</option>
                                <option value="5">5 sao</option>
                                <option value="4">4 sao</option>
                                <option value="3">3 sao</option>
                                <option value="2">2 sao</option>
                                <option value="1">1 sao</option>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <select name="sortOrder" class="form-select">
                                <option value="">Mặc định</option>
                                <option value="desc">Đánh giá từ cao đến thấp</option>
                                <option value="asc">Đánh giá từ thấp đến cao</option>
                                <option value="newest">Đánh giá mới nhất</option>
                                <option value="oldest">Đánh giá cũ nhất</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-12 mt-3">
                        <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                        <button type="reset" class="btn btn-secondary">Xóa bộ lọc</button>
                    </div>
                </form>
            </div>

            <!-- Danh sách feedback -->
            <div class="card p-4 w-100">
                <h2 class="h4 mb-3">Danh sách Feedback</h2>
                <form action="export" method="get">
                    <button type="submit" class="btn btn-success">Xuất Excel</button>
                </form>
                `
                <div class="table-responsive">
                    <table class="table table-bordered table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Tên khách hàng</th>
                                <th>Sản phẩm</th>
                                <th>Đánh giá</th>
                                <th>Nhận xét</th>
                                <th>Trạng thái</th>
                                <th>Phản hồi</th>

                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="feedback" items="${feedbacks}">
                                <tr>
                                    <td>${feedback.feedback_id}</td>
                                    <td>${feedback.user_name}</td>
                                    <td>
                                        <a href="ProductDetailServlet?product_id=${feedback.product_id}">
                                            ${feedback.product_name}
                                        </a>
                                        </td>
                                    <td>${feedback.rating}</td>
                                    <td>${feedback.feedback_content}</td>
                                    <td>
                                        <form action="feedbackstatus" method="post" style="display:inline;">
                                            <input type="hidden" name="feedbackId" value="${feedback.feedback_id}">
                                            <input type="hidden" name="status" value="${feedback.status}">
                                            <button type="submit" class="btn ${feedback.status ? 'btn-success' :'btn-danger' }">
                                                ${feedback.status ? "Hiển thị" : "Ẩn"}
                                            </button>
                                        </form>

                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${empty feedback.reply_content}">
                                                Chưa có phản hồi
                                            </c:when>
                                            <c:otherwise>
                                                ${feedback.reply_content}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:if test="${empty feedback.reply_content}">
                                            <button class="btn btn-warning btn-sm" onclick="openReplyModal(${feedback.feedback_id})">Phản hồi</button>
                                        </c:if>

                                        <!-- Nút sửa phản hồi -->
                                        <button class="btn btn-info btn-sm" 
                                                onclick="openEditModal(${feedback.feedback_id}, `${feedback.reply_content}`)">
                                            Sửa
                                        </button>

                                        <c:if test="${not empty feedback.reply_content}">
                                            <button class="btn btn-danger btn-sm" 
                                                    onclick="confirmDelete(${feedback.feedback_id})">
                                                Xóa phản hồi
                                            </button>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                </div>
                <div class="pagination justify-content-center">

                    <c:forEach begin="1" end="${end}" var="i">
                        <c:if test="${check eq 'list'}">
                            <a class="page-link ${i eq index ? 'active' : ''}" href="feedbacklist?index=${i}">${i}</a>
                        </c:if>
                        <c:if test="${check eq 'search'}">
                            <a class="page-link ${i eq index ? 'active' : ''}" href="feedbacksearch?index=${i}">
                                ${i}
                            </a>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </div>
        <!-- Modal thêm phản hồi -->
        <div class="modal fade" id="replyModal" tabindex="-1" aria-labelledby="replyModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="replyModalLabel">Thêm Phản Hồi</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="replyForm" action="feedbackreply" method="post">
                            <input type="hidden" id="replyFeedbackId" name="feedback_id">
                            <div class="mb-3">
                                <label for="replyContent" class="form-label">Nội dung phản hồi</label>
                                <textarea class="form-control" id="replyContent" name="reply_content" rows="3" required></textarea>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary">Gửi phản hồi</button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal sửa phản hồi -->
        <div class="modal fade" id="editReplyModal" tabindex="-1" aria-labelledby="editReplyModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editReplyModalLabel">Sửa Phản Hồi</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="editReplyForm" action="feedbackreplyedit" method="post">
                            <input type="hidden" id="editFeedbackId" name="feedback_id">
                            <div class="mb-3">
                                <label for="editReplyContent" class="form-label">Nội dung phản hồi</label>
                                <textarea class="form-control" id="editReplyContent" name="reply_content" rows="3" required></textarea>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary">Lưu chỉnh sửa</button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script>
            function openReplyModal(feedbackId) {
                document.getElementById("replyFeedbackId").value = feedbackId;
                var myModal = new bootstrap.Modal(document.getElementById('replyModal'));
                myModal.show();
            }

            function openEditModal(feedbackId, replyContent) {
                document.getElementById("editFeedbackId").value = feedbackId;
                document.getElementById("editReplyContent").value = replyContent || ''; // Đảm bảo không bị undefined
                var myModal = new bootstrap.Modal(document.getElementById('editReplyModal'));
                myModal.show();
            }

            function confirmDelete(feedbackId) {
                if (confirm("Bạn có chắc chắn muốn xóa phản hồi này không?")) {
                    window.location.href = "feedbackreplydelete?feedback_id=" + feedbackId;
                }
            }
        </script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </body>

</html>
