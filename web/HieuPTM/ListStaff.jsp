<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="Header.jsp" %>s
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản Lý Nhân Viên</title>
    </head>
    <body>
        <div class="container" style="margin-top: 150px">
            <h1 class="text-center mt-3 mb-3 text-primary">Danh sách nhân viên</h1>
            <table border ="1" class="table table-sm formTable text-center">
                <thead>
                    <tr>
                        <th>Tên đăng nhập</th>
                        <th>Họ và tên</th>
                        <th>Địa chỉ</th>
                        <th>Ngày sinh</th>
                        <th>Số điện thoại</th>
                        <th>Email</th>
                        <th>Trạng thái</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${userlist}" var="o">
                        <tr>
                            <td>${o.userName}</td>
                            <td>${o.fullName}</td>  
                            <td>${o.address}</td>  
                            <td>${o.formatBirthDate()}</td>  
                            <td>${o.phone}</td>  
                            <td>${o.email}</td>  
                            <td>
                                <button class="btn btn-success fw-bold pd-start-3" onclick="becomeAdmin('${user.userName}', '${o.userName}')">Thêm Staff</button>
                                <button class="btn btn-danger fw-bold pd-start-3" onclick="deleteUser('${user.userName}', '${o.userName}')">Cấm</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

    </body>
<!--    <script>
        function becomeAdmin(uid, did) {
            if (confirm("Thêm thành Staff?")) {
                window.location.href = "badmin?uid=" + uid + "&did=" + did;
            }
        }
        
        function deleteUser(uid, did) {
            if (confirm("Cấm người dùng?")) {
                window.location.href = "udelete?uid=" + uid + "&did=" + did;
            }
        }
    </script>-->
</html>