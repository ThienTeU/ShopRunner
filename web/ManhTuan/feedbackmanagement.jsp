<%-- 
    Document   : feedbackmanagement
    Created on : Mar 25, 2025, 8:38:09 AM
    Author     : tuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">

    <head>
        <title>Quản lý Feedback Khách Hàng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>

    <body class="bg-light">
        <div class="container my-5">
            <h1 class="text-center mb-4">Quản lý Feedback Khách Hàng</h1>

            <!-- Form tìm kiếm -->
            <div class="card p-4 mb-4">
                <h2 class="h4 mb-3">Tìm kiếm Feedback</h2>
                <div class="row g-3">
                    <div class="col-md-4">
                        <input type="text" id="searchName" class="form-control" placeholder="Nhập tên khách hàng" onkeyup="searchFeedback()">
                    </div>
                    <div class="col-md-4">
                        <input type="text" id="searchProduct" class="form-control" placeholder="Nhập tên sản phẩm" onkeyup="searchFeedback()">
                    </div>
                    <div class="col-md-3">
                        <select id="ratingFilter" class="form-select" onchange="searchFeedback()">
                            <option value="">Tất cả đánh giá</option>
                            <option value="5">5 sao</option>
                            <option value="4">4 sao</option>
                            <option value="3">3 sao</option>
                            <option value="2">2 sao</option>
                            <option value="1">1 sao</option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <select id="ratingFilter" class="form-select" onchange="searchFeedback()">
                            <option value="">Mặc định</option>
                            <option value="5">Đánh giá từ cao đến thấp</option>
                            <option value="4">Đánh giá từ thấp đến cao</option>
                            <option value="4">Đánh giá mới nhất</option>
                            <option value="4">Đánh giá cũ nhất</option>
                        </select>
                    </div>
                    <div class="col-md-1">
                        <button class="btn btn-success" onclick="exportToCSV()">Xuất CSV</button>
                    </div>
                </div>
            </div>

            <!-- Form thêm feedback -->
            <div class="card p-4 mb-4">
                <h2 class="h4 mb-3">Thêm Feedback</h2>
                <form id="feedbackForm">
                    <div class="row g-3">
                        <div class="col-md-4">
                            <input type="text" id="customerName" class="form-control" placeholder="Tên khách hàng" required>
                        </div>
                        <div class="col-md-4">
                            <input type="text" id="productName" class="form-control" placeholder="Sản phẩm" required>
                        </div>
                        <div class="col-md-2">
                            <input type="number" id="rating" class="form-control" placeholder="Đánh giá (1-5)" min="1" max="5" required>
                        </div>
                        <div class="col-md-12">
                            <textarea id="comment" class="form-control" placeholder="Nhận xét" required></textarea>
                        </div>
                        <div class="col-md-12">
                            <button type="button" class="btn btn-primary" onclick="addFeedback()">Thêm Feedback</button>
                        </div>
                    </div>
                </form>
            </div>

            <!-- Danh sách feedback -->
            <div class="card p-4">
                <h2 class="h4 mb-3">Danh sách Feedback</h2>
                <div class="table-responsive">
                    <table class="table table-bordered table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Tên khách hàng</th>
                                <th>Sản phẩm</th>
                                <th>Đánh giá</th>
                                <th>Nhận xét</th>
                                <th>Phản hồi</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody id="feedbackTableBody">
                            <tr>
                                <td>1</td>
                                <td>Nguyễn Văn A</td>
                                <td>Giày Chạy Bộ</td>
                                <td>5</td>
                                <td>Rất hài lòng, chất lượng tốt!</td>
                                <td class="reply">Chưa có phản hồi</td>
                                <td class="actions">
                                    <button class="btn btn-warning btn-sm" onclick="replyFeedback(this)">Phản hồi</button>
                                    <button class="btn btn-info btn-sm" onclick="editFeedback(this)">Sửa</button>
                                    <button class="btn btn-danger btn-sm" onclick="deleteFeedback(this)">Xóa</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        
    </body>

</html>