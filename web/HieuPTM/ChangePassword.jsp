<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html> 
    <head>
        <meta charset="UTF-8">
        <title>Đổi Mật Khẩu</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f1f1f1;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }
            .container {
                background-color: white;
                padding: 30px;
                border-radius: 12px;
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                width: 400px;
            }
            h2 {
                text-align: center;
                color: #333;
            }
            label {
                display: block;
                margin-top: 15px;
                font-weight: bold;
            }
            input[type="password"] {
                width: 100%;
                padding: 10px;
                margin-top: 5px;
                border: 1px solid #ccc;
                border-radius: 8px;
            }
            button {
                width: 100%;
                padding: 12px;
                margin-top: 20px;
                background-color: #007bff;
                color: white;
                border: none;
                border-radius: 8px;
                font-size: 16px;
                cursor: pointer;
            }
            button:hover {
                background-color: #ff3333;
            }
            .message {
                margin-top: 20px;
                text-align: center;
                font-weight: bold;
            }
            .error {
                color: red;
            }
            .success {
                color: green;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Đổi Mật Khẩu</h2>
            <form action="ChangePassword" method="post">
                <input type="hidden" name="uid" value="${uid}" />
                <label for="oldPassword">Mật khẩu hiện tại:</label>
                <input type="password" name="oldPassword" required>

                <label for="newPassword">Mật khẩu mới:</label>
                <input type="password" name="newPassword" required>

                <label for="confPassword">Xác nhận mật khẩu mới:</label>
                <input type="password" name="confPassword" required>

                <button type="submit">Đổi mật khẩu</button>
            </form>

            <!-- Hiển thị các thông báo -->
            <c:if test="${status == 'emptyField'}">
                <p class="message error">Vui lòng nhập đầy đủ tất cả các trường!</p>
            </c:if>
            <c:if test="${status == 'notMatch'}">
                <p class="message error">Mật khẩu mới và xác nhận mật khẩu không khớp!</p>
            </c:if>
            <c:if test="${status == 'invalidNewPassword'}">
                <p class="message error">Mật khẩu mới phải ít nhất 6 ký tự và chứa ít nhất 1 chữ số!</p>
            </c:if>
            <c:if test="${status == 'wrongOldPassword'}">
                <p class="message error">Mật khẩu hiện tại không đúng!</p>
            </c:if>
            <c:if test="${status == 'changeFailed'}">
                <p class="message error">Đổi mật khẩu thất bại. Vui lòng thử lại!</p>
            </c:if>

            <!-- Hiển thị thông báo thành công + redirect -->
            <c:if test="${status == 'changeSuccess'}">
                <script>
                    alert('Đổi mật khẩu thành công!');
                    window.location.href = '${pageContext.request.contextPath}/home?uid=${uid}';
                </script>
            </c:if>


        </div>
    </body>
</html>