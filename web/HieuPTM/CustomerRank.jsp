<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Điểm Thưởng</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body>
        <!-- Header -->
        <%@ include file="/model/styles.jsp" %>
        <%@ include file="/model/header.jsp" %>
        <div class="row" style="margin-top: 50px;">
        <div class="container mt-4">
            <h2 class="text-center">Điểm Thưởng</h2>
            <div class="card p-3 mb-4">
                <h4>Tổng Điểm: <span id="totalPoints" class="text-primary">0</span></h4>
                <h4>Xếp Hạng: <span id="rank" class="text-success">Chưa xếp hạng</span></h4>
            </div>

            <h4>Lịch Sử Giao Dịch</h4>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Loại</th>
                        <th>Điểm</th>
                        <th>Mô Tả</th>
                        <th>Ngày</th>
                    </tr>
                </thead>
                <tbody id="transactionTable">
                    <!-- Dữ liệu sẽ được thêm vào động -->
                </tbody>
            </table>
        </div>

        <script>
        document.addEventListener("DOMContentLoaded", function() {
            fetch("rewards?userId=1") // Thay bằng ID người dùng động
                .then(response => response.json())
                .then(data => {
                    document.getElementById("totalPoints").innerText = data.rewardPoints.totalPoints;
                    
                    // Xác định xếp hạng dựa trên số lần mua hàng
                    let purchaseCount = data.rewardPoints.purchaseCount;
                    let rank = "Chưa xếp hạng";
                    if (purchaseCount < 2) {
                        rank = "Khách hàng loại 3";
                    } else if (purchaseCount >= 2 && purchaseCount < 5) {
                        rank = "Khách hàng loại 2";
                    } else {
                        rank = "Khách hàng loại 1";
                    }
                    document.getElementById("rank").innerText = rank;
                    
                    let transactionTable = document.getElementById("transactionTable");
                    data.transactions.forEach(trx => {
                        let row = `<tr>
                            <td>${trx.transactionType}</td>
                            <td>${trx.points}</td>
                            <td>${trx.description}</td>
                            <td>${trx.createdAt}</td>
                        </tr>`;
                        transactionTable.innerHTML += row;
                    });
                })
                .catch(error => console.error("Lỗi tải dữ liệu:", error));
        });
    </script>
        <!-- Footer -->
        <%@ include file="/model/footer.jsp" %>
    </body>
</html>