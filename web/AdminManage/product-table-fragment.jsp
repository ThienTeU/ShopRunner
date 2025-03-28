<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<div class="table-responsive">
    <table class="table text-start align-middle table-bordered table-hover mb-0">
        <thead>
            <tr class="text-white">
                <th scope="col">Tên</th>
                <th scope="col">Ảnh</th>
                <th scope="col">Ngày Tạo</th>
                <th scope="col">Màu sắc/Kích cỡ/Số lượng</th>
                <th scope="col">Trạng Thái</th>
                <th scope="col">Hành Động</th>
            </tr>
        </thead>
        <tbody style="font-size: 14px">
            <c:forEach items="${paginatedList}" var="product">
                <tr>
                    <td style="max-width: 150px">
                        <a href="ProductDetailServlet?product_id=${product.product_id}">${product.product_name}</a>
                    </td>
                    <td>
                        <div style="position: relative">
                            <div style="padding: 0 5px;background-color: #cff4fc;color: black;
                                 position: absolute; right: 0px; font-size: 13px; margin: 0 5px 0 0;
                                 font-weight: 600; border-bottom-left-radius:5px;border-bottom-right-radius:5px" class="view">
                                <i class="fa-regular fa-eye"></i>
                                <span>${product.getView()}</span>
                            </div>
                            <img src="${product.thumbnail}" width="100px" height="100px" alt="alt"/>
                        </div>
                    </td>
                    <td style="max-width: 150px">${product.created_at}</td>
                    <td style="line-height: 0;align-items: center; font-size: 13px;">
                        <c:forEach items="${product.getProductPricesByProductId()}" var="pp">
                            <form style="display:flex; align-items: center; margin-bottom: 10px" action="UpdateQuantityServlet">
                                <c:forEach items="${pp.getAllColor()}" var="c">
                                    <c:if test="${c.color_id == pp.color_id}">
                                        <div style="border: 1px solid gray; padding: 3px; border-radius: 8px; display: flex; align-items: center; width: fit-content;">
                                            <a style="text-decoration: none; display: flex; align-items: center; gap: 5px;" 
                                               title="Sửa giá sản phẩm" href="#" onclick="editPrice(event, this)">
                                                <input type="hidden" name="productPriceId" value="${pp.productPrice_id}">
                                                <button style="display: flex; background-color: ${c.color}; border-radius: 50%;
                                                        height: 30px; width: 30px; border: solid 1px gray; cursor: pointer;">
                                                </button>
                                                <div class="productPrice" style="color: red; font-weight: bold; font-size: 14px;">
                                                    ${pp.price - pp.price*product.discount/100} VND
                                                </div>
                                                
                                            </a>
                                        </div>
                                    </c:if>
                                </c:forEach>

                                <select name="productQuantityId" style="height: 30px; margin: 0 10px 0 10px">
                                    <c:forEach items="${pp.getAllProductQuantitesById()}" var="pq">
                                        <option value="${pq.productQuantity_id}">
                                            <c:forEach items="${pq.getAllSize()}" var="s">
                                                <c:if test="${s.size_id == pq.size_id}">
                                                    ${s.size}
                                                </c:if>
                                            </c:forEach>
                                            / ${pq.quantity}
                                        </option>
                                    </c:forEach>
                                </select>

                                <button style="font-size: 13px; height: 30px;" type="button" class="btn btn-outline-secondary" 
                                        onclick="editQuantity(event, this)">
                                    Sửa Số Lượng <i class="bi bi-pencil-square"></i> 
                                </button>
                            </form>
                            <br>
                        </c:forEach>
                    </td>
                    <td>
                        <c:if test="${product.status == false}">
                            Còn bán <i style="color: green" class="bi bi-check-circle-fill"></i>
                        </c:if>
                        <c:if test="${product.status == true}">
                            Ngừng bán <i style="color:red" class="bi bi-x-circle-fill"></i>
                        </c:if>
                    </td>
                    <td>
                        <a style="font-size: 13px; height: 30px;" class="btn btn-light" href="#" 
                           onclick="updateStatus(event, '${product.product_id}', '${product.status}')"> 
                            Chuyển Trạng Thái <i class="bi bi-circle"></i>
                        </a><br>
                        <a style="font-size: 13px; height: 30px" href="AddProductPriceServlet?product_id=${product.product_id}"
                           class="btn mt-1 btn-primary">
                            Thêm Màu Mới <i class="bi bi-plus-circle"></i>
                        </a>
                        <a style="font-size: 13px; height: 30px" href="EditProductServlet?product_id=${product.product_id}" 
                           class="btn mt-1 btn-outline-danger">Sửa Sản Phẩm</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div class="text-center mt-3">
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="#" data-page="${currentPage - 1}">
                            Previous
                        </a>
                    </li>
                </c:if>

                <c:forEach begin="1" end="${totalPages}" var="i">
                    <li class="page-item">
                        <a class="page-link" href="#" data-page="${i}"
                           style="${i == currentPage ? 'color: black;' : ''}">
                            ${i}
                        </a>
                    </li>
                </c:forEach>

                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="#" data-page="${currentPage + 1}">
                            Next
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div> 

<script>
    document.addEventListener("DOMContentLoaded", function () {
        let priceElements = document.querySelectorAll(".productPrice");
        priceElements.forEach(function (element) {
            let price = parseFloat(element.textContent);
            if (!isNaN(price)) {
                element.textContent = price.toLocaleString("vi-VN") + "₫";
            }
        });
    });
</script>