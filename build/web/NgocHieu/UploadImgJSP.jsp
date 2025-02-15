<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Upload Image</title>
        <style>
            #dropArea {
                border: 2px dashed #ccc;
                padding: 20px;
                text-align: center;
                cursor: pointer;
                margin-top: 10px;
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
        <h1>Upload Image</h1>
        <form action="TestUploadFileServlet" method="POST" enctype="multipart/form-data">
            <table>
                <tbody>
                    <tr>
                        <td><label>Product Price ID:</label></td>
                        <td>
                            <input type="text" name="productprice_id" value="${requestScope.productprice_id}" readonly>
                        </td>
                    </tr>
                    <tr>
                        <td><label>Upload Image:</label></td>
                        <td>
                            <input type="file" name="images" id="fileInput" multiple hidden>
                            <div id="dropArea">Drop images here or <span id="browse">browse</span></div>
                            <div class="preview-container" id="previewContainer"></div>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Upload"></td>
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