<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết mã giảm giá</title>
    
    <!-- Bootstrap & FontAwesome -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

    <style>
        body {
            background-color: #f8f9fa;
        }

        .voucher-container {
            background: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
        }

        .voucher-form input {
            border-radius: 5px;
            padding: 8px;
        }

        .voucher-form label {
            font-weight: bold;
            margin-top: 10px;
        }

        .voucher-form button {
            margin-top: 20px;
            width: 100%;
        }

        .voucher-image {
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: #eef2f7;
            border-radius: 10px;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <div class="row">
            <!-- Form chi tiết voucher -->
            <div class="col-md-6">
                <div class="voucher-container">
                    <h3 class="text-center text-primary"><i class="fas fa-tag"></i> Chi tiết mã giảm giá</h3>
                    
                    <form action="voucherdetail" method="POST" class="voucher-form" onsubmit="return validateForm();">
                        <label for="name">Tên mã giảm giá</label>
                        <input type="text" class="form-control" name="name" value="${requestScope.voucher.getName()}" id="name">
                        
                        <label for="start">Ngày bắt đầu</label>
                        <input type="date" class="form-control" name="start" value="${requestScope.voucher.getStart()}">

                        <label for="end">Ngày kết thúc</label>
                        <input type="date" class="form-control" name="end" value="${requestScope.voucher.getEnd()}">

                        <label for="discount">Giảm giá (%)</label>
                        <input type="number" class="form-control" name="discount" value="${requestScope.voucher.getDiscount()}">

                        <label for="quantity">Số lượng</label>
                        <input type="number" class="form-control" name="quantity" value="${requestScope.voucher.getQuantity()}">

                        <label for="code">Mã giảm giá</label>
                        <input type="text" class="form-control" id="code-input" name="code" value="${requestScope.voucher.getVoucherCode()}" readonly>

                        <button type="submit" class="btn btn-success"><i class="fas fa-save"></i> Cập nhật mã giảm giá</button>
                    </form>
                </div>
            </div>

            <!-- Hình ảnh voucher -->
            <div class="col-md-6">
                <div class="voucher-container voucher-image">
                    <img src="admin/img/voucher-1.png" width="80%" class="img-fluid">
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap & JS -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        function validateForm() {
            var name = document.getElementById("name").value;
            if (name.trim() === "") {
                alert("Tên mã giảm giá không được để trống!");
                return false;
            }
            return true;
        }
    </script>
</body>
</html>
