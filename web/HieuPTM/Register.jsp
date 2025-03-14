<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng kí tài khoản</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #e9ecef;
                font-family: Arial, sans-serif;
            }
            .card {
                border: none;
                border-radius: 10px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }
            .form-label {
                font-weight: bold;
                color: #495057;
            }
            .form-control {
                border-radius: 8px;
                background-color: #f8f9fa;
                border: 1px solid #ced4da;
            }
            .btn-primary {
                background-color: #007bff;
                border: none;
                padding: 10px;
                font-size: 16px;
                border-radius: 8px;
                transition: background-color 0.3s ease;
            }
            .btn-primary:hover {
                background-color: #0056b3;
            }
            .text-muted a {
                color: #007bff;
                text-decoration: none;
            }
            .text-muted a:hover {
                text-decoration: underline;
            }
            .container {
                max-width: 500px;
            }
        </style>
    </head>
    <body>
        <div class="container mt-5">
            <div class="card p-4">
                <h3 class="text-center mb-4">Đăng kí tài khoản</h3>
                <form action="${pageContext.request.contextPath}/RegisterControl" method="post">
                    <!-- Display Error Message -->
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger" role="alert">
                            ${error}
                        </div>
                    </c:if>

                    <!-- UserName -->
                    <div class="mb-3">
                        <label for="username" class="form-label">Tên đăng nhập</label>
                        <input type="text" class="form-control" id="username" name="username" placeholder="Nhập tên đăng nhập" value="${param.username}" required>
                    </div>

                    <!-- FullName -->
                    <div class="mb-3">
                        <label for="fullname" class="form-label">Họ và tên</label>
                        <input type="text" class="form-control" id="fullname" name="fullname" placeholder="Nhập họ và tên" value="${param.fullname}" required>
                    </div>

                    <!-- Password -->
                    <div class="mb-3">
                        <label for="password" class="form-label">Mật khẩu</label>
                        <div class="input-group">
                            <input type="password" class="form-control" id="password" name="password" placeholder="Nhập mật khẩu" required>
                            <button type="button" class="btn btn-outline-secondary" onclick="togglePassword()">Hiện</button>
                        </div>
                    </div>

                    <!-- Email -->
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" placeholder="Nhập email" value="${param.email}" required>
                    </div>
                    <!-- Gender -->
                    <div class="mb-3">
                        <label class="form-label">Giới tính</label><br>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="gender" id="male" value="1"
                                   ${param.gender == '1' ? 'checked' : ''} required>
                            <label class="form-check-label" for="male">Nam</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="gender" id="female" value="2"
                                   ${param.gender == '2' ? 'checked' : ''}>
                            <label class="form-check-label" for="female">Nữ</label>
                        </div>
                    </div>

                    <!-- Phone -->
                    <div class="mb-3">
                        <label for="phone" class="form-label">Số điện thoại</label>
                        <input type="text" class="form-control" id="phone" name="phone" maxlength="11" placeholder="Nhập số điện thoại" value="${param.phone}" required>
                    </div>

                    <!-- Submit Button -->
                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-primary">Đăng kí</button>
                    </div>

                    <!-- Login Redirect -->
                    <p class="text-center text-muted mt-3">Bạn đã có tài khoản? <a href="${pageContext.request.contextPath}/LoginControl">Đăng nhập tại đây</a></p>
                </form>
            </div>
        </div>

        <script>
            function togglePassword() {
                var passwordInput = document.getElementById("password");
                if (passwordInput.type === "password") {
                    passwordInput.type = "text";
                } else {
                    passwordInput.type = "password";
                }
            }
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>