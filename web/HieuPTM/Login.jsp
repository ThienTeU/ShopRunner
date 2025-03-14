<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Đăng Nhập</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <style>
        .vh-100 {
            height: 100vh;
        }
        .divider::before,
        .divider::after {
            content: "";
            flex: 1;
            height: 1px;
            background: #eee;
        }
    </style>
</head>
<body>
    <section class="vh-100 d-flex align-items-center">
        <div class="container">
            <div class="row justify-content-center align-items-center">
                <!-- Hình ảnh minh họa -->
                <div class="col-md-6 d-none d-md-block">
                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.svg" class="img-fluid" alt="Phone image">
                </div>
                <!-- Form đăng nhập -->
                <div class="col-md-6">
                    <form action="${pageContext.request.contextPath}/LoginControl" method="post">
                        <h3 class="mb-4 text-center text-primary">Đăng nhập</h3>
                        <p class="text-danger">${exist}</p>
                        <!-- Email input -->
                        <div class="mb-3">
                            <label for="username" class="form-label">Tên đăng nhập ${mess != null ? '<i class="bi bi-exclamation-circle" style="color: red; font-size: 12px"></i>' : ''}</label>
                            <input type="text" class="form-control" id="username" name="username" value="${cookie.name.value}" />
                        </div>
                        <!-- Password input -->
                        <div class="mb-3">
                            <label for="password" class="form-label">Mật khẩu ${mess != null ? '<i class="bi bi-exclamation-circle" style="color: red; font-size: 12px"></i>' : ''}</label>
                            <input type="password" class="form-control" id="password" name="password" value="${cookie.pass.value}" />
                        </div>
                        <!-- Checkbox và Forgot password -->
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" id="rememberMe" name="re" ${cookie.re != null ? "checked" : ""} />
                                <label class="form-check-label" for="rememberMe">Ghi nhớ mật khẩu</label>
                            </div>
                            <a href="${pageContext.request.contextPath}/ForgotPassword" id="forgot" class="text-muted">Quên mật khẩu?</a>
                        </div>
                        <!-- Submit button -->
                        <button type="submit" class="btn btn-primary w-100">Đăng nhập</button>
                        <!-- Google login button -->
                        <button type="button" class="btn btn-danger w-100 my-2" onclick="window.location.href='https://accounts.google.com/o/oauth2/auth?scope=email profile openid&redirect_uri=http://localhost:9999/shop/login&response_type=code&client_id=370235026777-etuk7qtgsnp0ql3fuu9ccs63i9fdofjd.apps.googleusercontent.com&approval_prompt=force'">
                            <i class="fab fa-google me-2"></i> Đăng nhập với Google
                        </button>
                        <!-- Divider -->
                        <div class="divider d-flex align-items-center my-4">
                            <p class="text-center fw-bold mx-3 mb-0 text-muted">HOẶC</p>
                        </div>
                        <!-- Link to signup -->
                        <div class="text-center">
                            <a href="${pageContext.request.contextPath}/RegisterControl" class="btn btn-primary w-100">Đăng kí</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript">
        var status = document.getElementByID("status").value;
        if (status === "resetSuccess"){
            swal("Chúc mừng", "Đã thay đổi mật khẩu thành công", "success");
        }
        else if (status === "resetFailed"){
            swal("Xin lỗi", "Thay đổi mật khẩu thất bại", "error");
        }
    </script>    
</body>
</html>