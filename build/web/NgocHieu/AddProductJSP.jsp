
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>


<h1 style="text-align: center; font-size: 28px; color: #333;">Thêm Sản Phẩm</h1>
<form  action="${pageContext.request.contextPath}/AddProductServlet" method="POST" enctype="multipart/form-data" 
       style="width: 100%; padding: 20px; border-radius: 8px; background: #f8f9fa; box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);">

    <table style="width: 100%;">
        <tbody>
            <tr>
                <td><label style="font-weight: bold;">Tên sản phẩm:</label></td>
                <td><input type="text" name="product_name" required 
                           style="width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px;"></td>
            </tr>

            <tr>
                <td><label style="font-weight: bold;">Ảnh:</label></td>
                <td>
                    <input type="file" name="thumbnail" id="fileInput" required 
                           style="display: none;">
                    <div id="dropArea" 
                         style="border: 2px dashed #007bff; padding: 20px; text-align: center; cursor: pointer; width: 100%; height: 250px;
                         background: #e9f5ff; display: flex; align-items: center; justify-content: center; flex-direction: column;
                         border-radius: 8px; font-weight: bold; color: #007bff;">
                        <p>Kéo & Thả hoặc <span id="browse" style="color: red; text-decoration: underline; cursor: pointer;">Chọn file</span></p>
                    </div>
                    <div id="previewContainer" style="display: flex; gap: 10px; flex-wrap: wrap; margin-top: 10px;"></div>
                </td>
            </tr>

            <tr>
                <td><label style="font-weight: bold;">Mô tả:</label></td>
                <td><textarea name="description" rows="4" required 
                              style="width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px;"></textarea></td>
            </tr>

            <tr>
                <td><label style="font-weight: bold;">Danh mục:</label></td>
                <td>
                    <select id="category_id" name="category_id" required 
                            style="width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px;">
                        <c:forEach items="${listCategory}" var="category">
                            <option value="${category.category_id}">${category.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>     

            <tr>
                <td><label style="font-weight: bold;">Giảm giá:</label></td>
                <td><input type="number" name="discount" min="0" max="99" required 
                           style="width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px;"></td>
            </tr>

            <tr>
        <input type="hidden" name="status" value="1">
        </tr>

        <tr>
            <td colspan="2" style="text-align: center; padding-top: 15px;">
                <button onclick="showAddProductPrice(event)" type="submit" 
                        style="background-color: #28a745; color: white; padding: 10px 20px; border: none;
                        border-radius: 4px; cursor: pointer; font-size: 16px;">
                    Thêm Product
                </button>
            </td>
        </tr>
        </tbody>
    </table>
</form>


<script>
    document.addEventListener("DOMContentLoaded", function () {
        let dropArea = document.getElementById("dropArea");
        let fileInput = document.getElementById("fileInput");
        let browseButton = document.getElementById("browse");
        let previewContainer = document.getElementById("previewContainer");

        function updatePreview(files) {
            previewContainer.innerHTML = "";
            Array.from(files).forEach((file, index) => {
                let reader = new FileReader();
                reader.onload = function (e) {
                    let previewItem = document.createElement("div");
                    previewItem.classList.add("preview-item");

                    let img = document.createElement("img");
                    img.src = e.target.result;
                    previewItem.appendChild(img);

                    let deleteBtn = document.createElement("button");
                    deleteBtn.innerText = "×";
                    deleteBtn.classList.add("delete-btn");
                    deleteBtn.onclick = function () {
                        removeImage(index);
                    };
                    previewItem.appendChild(deleteBtn);

                    previewContainer.appendChild(previewItem);
                };
                reader.readAsDataURL(file);
            });
        }

        function removeImage(index) {
            let dataTransfer = new DataTransfer();
            let files = Array.from(fileInput.files);
            files.splice(index, 1);

            files.forEach(file => dataTransfer.items.add(file));
            fileInput.files = dataTransfer.files;
            updatePreview(fileInput.files);
        }

        dropArea.addEventListener("dragover", (event) => {
            event.preventDefault();
            dropArea.classList.add("dragover");
        });

        dropArea.addEventListener("dragleave", () => {
            dropArea.classList.remove("dragover");
        });

        dropArea.addEventListener("drop", (event) => {
            event.preventDefault();
            dropArea.classList.remove("dragover");

            let newFiles = event.dataTransfer.files;
            let dataTransfer = new DataTransfer();

            for (let i = 0; i < fileInput.files.length; i++) {
                dataTransfer.items.add(fileInput.files[i]);
            }
            for (let i = 0; i < newFiles.length; i++) {
                dataTransfer.items.add(newFiles[i]);
            }

            fileInput.files = dataTransfer.files;
            updatePreview(fileInput.files);
        });

        browseButton.addEventListener("click", () => {
            fileInput.click();
        });

        fileInput.addEventListener("change", () => {
            updatePreview(fileInput.files);
        });
    });
</script>


