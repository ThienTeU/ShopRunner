<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thêm Banner Mới</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 20px;
            }

            .form-container {
                max-width: 800px;
                margin: 0 auto;
                background-color: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            .form-header {
                text-align: center;
                margin-bottom: 30px;
            }

            .form-header h2 {
                color: #333;
                margin: 0;
            }

            .form-group {
                margin-bottom: 20px;
            }

            label {
                display: block;
                margin-bottom: 5px;
                color: #333;
                font-weight: bold;
            }

            input[type="text"],
            textarea,
            select {
                width: 100%;
                padding: 8px;
                border: 1px solid #ddd;
                border-radius: 4px;
                box-sizing: border-box;
                font-size: 14px;
            }

            textarea {
                height: 100px;
                resize: vertical;
            }

            .required {
                color: red;
                margin-left: 3px;
            }

            .error-message {
                color: #dc3545;
                background-color: #f8d7da;
                border: 1px solid #f5c6cb;
                padding: 10px;
                border-radius: 4px;
                margin-bottom: 20px;
            }

            .button-group {
                text-align: center;
                margin-top: 30px;
            }

            button {
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
                margin: 0 10px;
            }

            .submit-btn {
                background-color: #28a745;
                color: white;
            }

            .cancel-btn {
                background-color: #dc3545;
                color: white;
            }

            button:hover {
                opacity: 0.9;
            }

            .drop-zone {
                width: 100%;
                height: 150px;
                border: 2px dashed #ccc;
                border-radius: 4px;
                position: relative;
                text-align: center;
                transition: border .3s ease-in-out;
                background-color: #f8f9fa;
                margin-bottom: 10px;
            }

            .drop-zone.dragover {
                border-color: #4CAF50;
                background-color: rgba(76, 175, 80, 0.1);
            }

            .drop-zone-text {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                color: #666;
                font-size: 16px;
            }

            .drop-zone input[type="file"] {
                position: absolute;
                width: 100%;
                height: 100%;
                opacity: 0;
                cursor: pointer;
            }

            .preview-container {
                margin-top: 10px;
                text-align: center;
            }

            .preview-image {
                max-width: 200px;
                max-height: 120px;
                display: none;
                border: 1px solid #ddd;
                padding: 5px;
                margin: 0 auto;
            }

            /* Style cho phần status */
            .status-group {
                display: flex;
                gap: 20px;
                margin-top: 10px;
            }

            .status-option {
                display: flex;
                align-items: center;
            }

            .status-option input[type="radio"] {
                margin-right: 5px;
            }
        </style>
    </head>
    <body>
        <div class="form-container">
            <div class="form-header">
                <h2>Thêm Banner Mới</h2>
            </div>

            <c:if test="${not empty error}">
                <div class="error-message">
                    ${error}
                </div>
            </c:if>

            <form action="managerbanner?action=create" method="post" enctype="multipart/form-data" id="bannerForm">
                <div class="form-group">
                    <label>Hình ảnh banner<span class="required">*</span>:</label>
                    <div class="drop-zone" id="dropZone">
                        <div class="drop-zone-text">
                            Kéo và thả ảnh vào đây hoặc click để chọn file
                        </div>
                        <input type="file" id="imageFile" name="imageFile" accept="image/*" required>
                    </div>
                    <div class="preview-container">
                        <img id="preview" class="preview-image" alt="Preview">
                    </div>
                    <small class="file-info" style="color: #666; display: block; margin-top: 5px;">
                        Định dạng cho phép: .jpg, .jpeg, .png, .gif (Tối đa 5MB)
                    </small>
                </div>

                <div class="form-group">
                    <label>Tiêu đề<span class="required">*</span>:</label>
                    <input type="text" name="title" required maxlength="255">
                </div>

                <div class="form-group">
                    <label>Mô tả:</label>
                    <textarea name="description" maxlength="1000"></textarea>
                </div>

                <div class="form-group">
                    <label>Thứ tự hiển thị<span class="required">*</span>:</label>
                    <input type="number" name="displayOrder" required min="1" value="1">
                </div>

                <div class="form-group">
                    <label>Trạng thái:</label>
                    <div class="status-group">
                        <div class="status-option">
                            <input type="radio" id="active" name="status" value="1" checked>
                            <label for="active">Hoạt động</label>
                        </div>
                        <div class="status-option">
                            <input type="radio" id="inactive" name="status" value="0">
                            <label for="inactive">Không hoạt động</label>
                        </div>
                    </div>
                </div>

                <div class="button-group">
                    <button type="submit" class="submit-btn">Thêm Banner</button>
                    <button type="button" class="cancel-btn" onclick="window.location.href = 'managerbanner?action=list'">Hủy</button>
                </div>
            </form>
        </div>

        <script>
            const dropZone = document.getElementById('dropZone');
            const fileInput = document.getElementById('imageFile');
            const preview = document.getElementById('preview');
            const bannerForm = document.getElementById('bannerForm');
            const maxFileSize = 5 * 1024 * 1024; // 5MB
            const allowedTypes = ['image/jpeg', 'image/png', 'image/gif'];

            // Ngăn chặn hành vi mặc định của trình duyệt
            ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
                dropZone.addEventListener(eventName, preventDefaults, false);
                document.body.addEventListener(eventName, preventDefaults, false);
            });

            function preventDefaults(e) {
                e.preventDefault();
                e.stopPropagation();
            }

            // Hiệu ứng khi kéo file vào
            ['dragenter', 'dragover'].forEach(eventName => {
                dropZone.addEventListener(eventName, highlight, false);
            });

            ['dragleave', 'drop'].forEach(eventName => {
                dropZone.addEventListener(eventName, unhighlight, false);
            });

            function highlight(e) {
                dropZone.classList.add('dragover');
            }

            function unhighlight(e) {
                dropZone.classList.remove('dragover');
            }

            // Xử lý khi thả file
            dropZone.addEventListener('drop', handleDrop, false);

            function handleDrop(e) {
                const dt = e.dataTransfer;
                const files = dt.files;
                handleFiles(files);
            }

            // Xử lý khi chọn file qua input
            fileInput.addEventListener('change', function () {
                handleFiles(this.files);
            });

            function handleFiles(files) {
                if (files.length > 0) {
                    const file = files[0];

                    // Kiểm tra kích thước file
                    if (file.size > maxFileSize) {
                        alert('Kích thước file không được vượt quá 5MB!');
                        fileInput.value = '';
                        preview.style.display = 'none';
                        return;
                    }

                    // Kiểm tra định dạng file
                    if (!allowedTypes.includes(file.type)) {
                        alert('Chỉ chấp nhận file ảnh có định dạng: JPG, JPEG, PNG, GIF');
                        fileInput.value = '';
                        preview.style.display = 'none';
                        return;
                    }

                    const reader = new FileReader();
                    reader.onload = function (e) {
                        preview.src = e.target.result;
                        preview.style.display = 'block';
                    }
                    reader.readAsDataURL(file);

                    // Cập nhật file input
                    const dataTransfer = new DataTransfer();
                    dataTransfer.items.add(file);
                    fileInput.files = dataTransfer.files;
                }
            }

            // Validate form trước khi submit
            bannerForm.addEventListener('submit', function (e) {
                const title = document.querySelector('input[name="title"]').value.trim();
                const displayOrder = document.querySelector('input[name="displayOrder"]').value;

                if (!title) {
                    e.preventDefault();
                    alert('Vui lòng nhập tiêu đề banner!');
                    return;
                }

                if (!displayOrder || displayOrder < 1) {
                    e.preventDefault();
                    alert('Thứ tự hiển thị phải lớn hơn 0!');
                    return;
                }

                if (!fileInput.files || fileInput.files.length === 0) {
                    e.preventDefault();
                    alert('Vui lòng chọn ảnh banner!');
                    return;
                }
            });

            // Validate displayOrder
            document.querySelector('input[name="displayOrder"]').addEventListener('input', function (e) {
                let value = parseInt(this.value);
                if (isNaN(value) || value < 1) {
                    this.value = 1;
                }
            });
        </script>
    </body>
</html>