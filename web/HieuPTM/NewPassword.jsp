<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
<meta charset='utf-8'>
<meta name='viewport' content='width=device-width, initial-scale=1'>
<title>Đặt lại mật khẩu</title>
<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>
<style>
    body {
        background: linear-gradient(to bottom, #e0e7ff, #ffffff);
        font-family: Arial, sans-serif;
    }
    .container-box {
        background: #fff;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
    }
    .form-control {
        border: 1px solid #007bff;
    }
    .btn-custom {
        background-color: #007bff;
        color: white;
    }
    .text-center h2 {
        color: #0056b3;
    }
</style>
</head>
<body>
    <div class="container d-flex justify-content-center align-items-center min-vh-100">
        <div class="col-12 col-md-6">
            <div class="container-box text-center">
                <h2 class="mb-4">Đặt lại mật khẩu</h2>
                <form action="NewPassword" method="POST" id="passwordForm" onsubmit="return validatePassword()">
                    <div class="mb-3 text-start">
                        <label for="password" class="form-label">Mật khẩu mới</label>
                        <input type="password" id="password" name="password" class="form-control" required>
                        <small id="passwordError" class="text-danger"></small>
                    </div>
                    <div class="mb-3 text-start">
                        <label for="confPassword" class="form-label">Xác nhận mật khẩu</label>
                        <input type="password" id="confPassword" name="confPassword" class="form-control" required>
                        <small id="confPasswordError" class="text-danger"></small>
                    </div>
                    <div class="text-center">
                        <button type="submit" class="btn btn-custom">Đặt lại</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script>
        function validatePassword() {
            let password = document.getElementById("password").value;
            let confPassword = document.getElementById("confPassword").value;
            let passwordError = document.getElementById("passwordError");
            let confPasswordError = document.getElementById("confPasswordError");

            passwordError.innerHTML = "";
            confPasswordError.innerHTML = "";

            let passwordRegex = /^(?=.*\d).{6,}$/;
            if (!passwordRegex.test(password)) {
                passwordError.innerHTML = "Mật khẩu phải có ít nhất 6 ký tự và chứa ít nhất 1 chữ số.";
                return false;
            }

            if (password !== confPassword) {
                confPasswordError.innerHTML = "Mật khẩu xác nhận không khớp.";
                return false;
            }
            return true;
        }
    </script>
</body>
</html>