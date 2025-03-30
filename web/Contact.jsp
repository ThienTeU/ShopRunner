<%-- 
    Document   : Contact
    Created on : Mar 20, 2025, 4:16:50 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng ký tư vấn</title>
        <style>
            /* Đặt nền và font chữ cơ bản */
body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f4f4f9;
    color: #333;
}

.container {
    max-width: 600px;
    margin: 50px auto;
    padding: 20px;
    background-color: #fff;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    border-radius: 8px;
}

.form-container img {
    display: block;
    margin: 0 auto 20px;
    max-width: 150px;
}

h1 {
    text-align: center;
    color: #333;
    font-size: 24px;
    margin-bottom: 20px;
}

.form-group {
    margin-bottom: 15px;
}

label {
    display: block;
    font-weight: bold;
    margin-bottom: 5px;
    color: #555;
}

input, select, textarea, button {
    width: 100%;
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}

input:focus, select:focus, textarea:focus {
    border-color: #007bff;
    outline: none;
    box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
}

.form-group textarea {
    resize: none;
    height: 100px;
}

.form-group small {
    display: block;
    margin-top: 5px;
    font-size: 14px;
    color: #888;
}

.submit-btn {
    background-color: #007bff;
    color: #fff;
    border: none;
    cursor: pointer;
    font-weight: bold;
    transition: background-color 0.3s ease;
}

.submit-btn:hover {
    background-color: #0056b3;
}

.footer {
    text-align: center;
    margin-top: 20px;
    font-size: 14px;
    color: #666;
}

.footer p {
    margin: 0;
}
        </style>
    </head>
   
    <body>
        <%-- CAS Authentication --%>
            <%-- Nếu người dùng chưa đăng nhập qua CAS, họ sẽ được chuyển hướng đến trang login của CAS --%>

        <%-- Include các thành phần chung như style hoặc header --%>


        <%-- Nội dung chính của trang --%>
        <section class="slider">
            <div class="container">
                <div class="form-container">
                    <img src="logo.png" alt="Logo">
                    <h1>Đăng ký tư vấn</h1>
<form action="submitContactForm" method="POST">
    <div class="form-group">
        <label for="name">Họ tên *</label>
        <input type="text" id="name" name="name" placeholder="Nhập họ tên" required>
    </div>
    <div class="form-group">
        <label for="phone">Điện thoại *</label>
        <input type="tel" id="phone" name="phone" placeholder="Nhập số điện thoại" required>
    </div>
    <div class="form-group">
        <label for="email">Email *</label>
        <input type="email" id="email" name="email" placeholder="Nhập email" required>
    </div>
    <div class="form-group">
        <label for="content">Nội dung</label>
        <textarea id="content" name="content" placeholder="Nhập nội dung"></textarea>
        <small>Tối đa 1000 ký tự</small>
    </div>
    <button type="submit" class="submit-btn">GỬI</button>
</form>

                    <div class="footer">
                        <p>&copy; 2025 Công ty ABC. Mọi quyền được bảo lưu.</p>
                    </div>
                </div>
            </div>
        </section>
    </body>
    <script>
    document.querySelector('form').addEventListener('submit', function(event) {
        const name = document.getElementById('name').value.trim();
        const phone = document.getElementById('phone').value.trim();
        const email = document.getElementById('email').value.trim();
        const nameRegex = /^[a-zA-ZÀ-ỹ\s]+$/; // Chỉ cho phép chữ cái và khoảng trắng
        const phoneRegex = /^0\d{9,10}$/; // Bắt đầu bằng 0 và dài 10-11 số
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Kiểm tra định dạng email

        // Kiểm tra họ tên
        if (!nameRegex.test(name)) {
            alert('Họ tên không được chứa ký tự đặc biệt hoặc số.');
            event.preventDefault();
            return;
        }

        // Kiểm tra số điện thoại
        if (!phoneRegex.test(phone)) {
            alert('Số điện thoại phải bắt đầu bằng số 0 và có 10-11 chữ số.');
            event.preventDefault();
            return;
        }

        // Kiểm tra email
        if (!emailRegex.test(email)) {
            alert('Vui lòng nhập đúng định dạng email.');
            event.preventDefault();
            return;
        }
    });
</script>


    
</html>