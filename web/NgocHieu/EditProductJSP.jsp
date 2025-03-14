<!DOCTYPE html>
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit PRODUCT</title>
    <style>
        #dropArea {
            border: 2px dashed #ccc;
            padding: 20px;
            text-align: center;
            align-items: center;
            cursor: pointer;
            width: 500px;
            height: 300px;
        }
        .preview-container {
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
            margin-top: 10px;
        }
        .preview-item {
            position: relative;
            display: inline-block;
        }
        .preview-item img {
            width: 100px;
            height: 100px;
            object-fit: cover;
            border-radius: 5px;
        }
        .delete-btn {
            position: absolute;
            top: 5px;
            right: 5px;
            background: red;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 50%;
            width: 20px;
            height: 20px;
            text-align: center;
            font-size: 12px;
            line-height: 18px;
        }
    </style>
</head>
<body>
    <h1>Add Product</h1>
    <form action="EditProductServlet" method="POST" enctype="multipart/form-data">
        <table>
            <tbody>
                <tr>
                    <td><input type="text" name="product_id" value="${product.product_id}" hidden></td>
                </tr>
                <tr>
                    <td><label>Product Name:</label></td>
                    <td><input type="text" name="product_name" value="${product.product_name}" required></td>
                </tr>
                <tr>
                    <td><label>Thumbnail:</label></td>
                    <td>
                        <input type="file" name="thumbnail" id="fileInput">
                        <div id="dropArea">
                            <p>Tha anh vao & <span id="browse">Chon File</span></p>
                        </div>
                        <div class="preview-container" id="previewContainer">
                            <div class="preview-item"><img src="${product.thumbnail}" alt="alt"/></div>
                            <button class="delete-btn">×</button>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td><label>Description:</label></td>
                    <td><textarea name="description" rows="10" cols="70" required>${product.description}</textarea></td>
                </tr>
                <tr>
                    <td><label>Category:</label></td>
                    <td>
                        <select name="category_id" required>
                            <c:forEach items="${listCategory}" var="category">
                                <c:set var="found" value="false"></c:set>
                                <c:forEach items="${listCategory}" var="car2">
                                    <c:if test="${category.category_id == car2.parent_id}">
                                        <c:set var="found" value="true"></c:set>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${found == false}">
                                    <option value="${category.category_id}" ${product.category_id == category.category_id ? "selected" : ""}>${category.name}</option>
                                </c:if>     
                            </c:forEach>
                        </select>
                    </td>
                </tr>     
                <tr>
                    <td><label>Discount:</label></td>
                    <td><input type="number" name="discount" value="${product.discount}" required></td>
                </tr>
                <tr>
                    <td><label>Create at:</label></td>
                    <td><input type="date" name="created_at" value="${product.created_at}" required></td>
                </tr>
                <tr>
                    <input type="hidden" name="status" value="1">
                </tr>
                <tr>
                    <td><button type="submit">Edit Product</button></td>
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
</body>
</html>
