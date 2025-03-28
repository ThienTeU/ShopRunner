<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sửa Banner</title>
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
            padding: 30px;
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
            margin-bottom: 8px;
            color: #333;
            font-weight: bold;
        }

        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 14px;
        }

        .current-image-container {
            margin: 15px 0;
            text-align: center;
        }

        .current-image {
            max-width: 300px;
            max-height: 200px;
            border: 1px solid #ddd;
            border-radius: 4px;
            padding: 5px;
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

        .status-toggle {
            display: inline-block;
            position: relative;
            width: 60px;
            height: 34px;
        }

        .status-toggle input {
            opacity: 0;
            width: 0;
            height: 0;
        }

        .slider {
            position: absolute;
            cursor: pointer;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background-color: #ccc;
            transition: .4s;
            border-radius: 34px;
        }

        .slider:before {
            position: absolute;
            content: "";
            height: 26px;
            width: 26px;
            left: 4px;
            bottom: 4px;
            background-color: white;
            transition: .4s;
            border-radius: 50%;
        }

        input:checked + .slider {
            background-color: #4CAF50;
        }

        input:checked + .slider:before {
            transform: translateX(26px);
        }

        .button-group {
            text-align: center;
            margin-top: 30px;
        }

        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            transition: all 0.3s ease;
        }

        .btn-primary {
            background-color: #4CAF50;
            color: white;
        }

        .btn-secondary {
            background-color: #6c757d;
            color: white;
            text-decoration: none;
            margin-left: 15px;
        }

        .btn:hover {
            opacity: 0.9;
        }

        .error-message {
            color: #dc3545;
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 20px;
        }

        .file-info {
            color: #666;
            font-size: 13px;
            margin-top: 5px;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <div class="form-header">
            <h2>Sửa Banner</h2>
        </div>

        <c:if test="${not empty error}">
            <div class="error-message">
                ${error}
            </div>
        </c:if>

        <form action="managerbanner?action=update" method="post" enctype="multipart/form-data" id="bannerForm">
            <input type="hidden" name="id" value="${banner.banner_id}">

            <div class="form-group">
                <label>Hình ảnh hiện tại:</label>
                <div class="current-image-container">
                    <img src="${banner.image_url}" alt="Current Banner" class="current-image">
                </div>
            </div>

            <div class="form-group">
                <label>Thay đổi hình ảnh:</label>
                <div class="drop-zone" id="dropZone">
                    <div class="drop-zone-text">
                        Kéo và thả ảnh vào đây hoặc click để chọn file
                    </div>
                    <input type="file" id="imageFile" name="imageFile" accept="${banner.image_url}">
                </div>
                <div class="preview-container">
                    <img id="preview" class="preview-image" alt="Preview">
                </div>
                <small class="file-info">
                    Định dạng cho phép: .jpg, .jpeg, .png, .gif (Tối đa 5MB)
                </small>
            </div>

            <div class="form-group">
                <label for="linkUrl">Link URL:</label>
                <input type="text" id="linkUrl" name="linkUrl" value="${banner.link_url}" 
                       maxlength="255" placeholder="Nhập URL liên kết">
            </div>

            <div class="form-group">
                <label for="displayOrder">Thứ tự hiển thị:</label>
                <input type="number" id="displayOrder" name="displayOrder" 
                       value="${banner.display_order}" required min="1">
            </div>

            <div class="form-group">
                <label>Trạng thái:</label>
                <label class="status-toggle">
                    <input type="checkbox" name="status" ${banner.status ? 'checked' : ''}>
                    <span class="slider"></span>
                </label>
            </div>

            <div class="button-group">
                <button type="submit" class="btn btn-primary">Cập Nhật</button>
                <a href="managerbanner?action=list" class="btn btn-secondary">Hủy</a>
            </div>
        </form>
    </div>

    <script>
        // Định nghĩa các biến và hằng số
        const dropZone = document.getElementById('dropZone');
        const fileInput = document.getElementById('imageFile');
        const preview = document.getElementById('preview');
        const bannerForm = document.getElementById('bannerForm');
        const maxFileSize = 5 * 1024 * 1024; // 5MB
        const allowedTypes = ['image/jpeg', 'image/png', 'image/gif'];

        // Xử lý kéo thả
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
            dropZone.addEventListener(eventName, () => dropZone.classList.add('dragover'));
        });

        ['dragleave', 'drop'].forEach(eventName => {
            dropZone.addEventListener(eventName, () => dropZone.classList.remove('dragover'));
        });

        // Xử lý khi thả file
        dropZone.addEventListener('drop', (e) => {
            const dt = e.dataTransfer;
            handleFiles(dt.files);
        });

        // Xử lý khi chọn file
        fileInput.addEventListener('change', function() {
            handleFiles(this.files);
        });

        function handleFiles(files) {
            if (files.length > 0) {
                const file = files[0];
                
                // Kiểm tra kích thước
                if (file.size > maxFileSize) {
                    alert('Kích thước file không được vượt quá 5MB!');
                    fileInput.value = '';
                    preview.style.display = 'none';
                    return;
                }
                
                // Kiểm tra định dạng
                if (!allowedTypes.includes(file.type)) {
                    alert('Chỉ chấp nhận file ảnh có định dạng: JPG, JPEG, PNG, GIF');
                    fileInput.value = '';
                    preview.style.display = 'none';
                    return;
                }
                
                // Hiển thị preview
                const reader = new FileReader();
                reader.onload = function(e) {
                    preview.src = e.target.result;
                    preview.style.display = 'block';
                }
                reader.readAsDataURL(file);
            }
        }

        // Validate form
        bannerForm.addEventListener('submit', function(e) {
            const displayOrder = document.getElementById('displayOrder').value;
            
            if (displayOrder < 1) {
                e.preventDefault();
                alert('Thứ tự hiển thị phải lớn hơn 0!');
                return;
            }
        });

        // Validate displayOrder
        document.getElementById('displayOrder').addEventListener('input', function() {
            if (this.value < 1) {
                this.value = 1;
            }
        });
    </script>
</body>
</html>
