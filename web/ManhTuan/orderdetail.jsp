<%-- 
    Document   : orderdetail
    Created on : Mar 28, 2025, 1:08:03 PM
    Author     : tuan
--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Chi Tiết Đơn Hàng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>

        <div class="container mt-5">
            <h2 class="text-center mb-4">Chi Tiết Đơn Hàng</h2>

            <div class="table-responsive">
                <table class="table table-bordered table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Tên Sản Phẩm</th>
                            <th>Giá</th>
                            <th>Số Lượng</th>
                        </tr>
                    </thead>


                    <tbody>
                        <c:forEach var="detail" items="${orderDetails}">
                            <tr>
                                <td>${detail.order_detail_id}</td>
                                <td>${detail.product_name}</td>
                                <td>${detail.price_item} ₫</td>
                                <td>${detail.quantity_item}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    <tfoot>
                        <tr class="table-warning">
                            <td colspan="2" class="text-end fw-bold">Tổng đơn hàng:</td>
                            <td colspan="2" class="fw-bold">${orderDetails[0].total} ₫</td>
                        </tr>
                        <tr class="table-info">
                            <td colspan="2" class="text-end fw-bold">Voucher:</td>
                            <td colspan="2">${orderDetails[0].voucher_id != null ? orderDetails[0].voucher_id : "Không có"}</td>
                        </tr>
                        <tr class="table-info">
                            <td colspan="2" class="text-end fw-bold">Địa Chỉ Giao Hàng:</td>
                            <td colspan="2">${orderDetails[0].shipping_address}</td>
                        </tr>
                    </tfoot>



                </table>
            </div>

            <div class="text-center mt-4">
                <a href="orderlist" class="btn btn-secondary">Quay lại danh sách đơn hàng</a>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>
