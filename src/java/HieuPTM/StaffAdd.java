package HieuPTM;

import DAL.StaffDAOHieu;
import Model.StaffHieu;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@WebServlet(name = "StaffAdd", urlPatterns = {"/StaffAdd"})
public class StaffAdd extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy dữ liệu từ form
            String userName = request.getParameter("userName").trim();
            String fullName = request.getParameter("fullName").trim();
            String email = request.getParameter("email").trim();
            String phoneNumber = request.getParameter("phoneNumber").trim();
            boolean status = "1".equals(request.getParameter("status"));
            int gender = Integer.parseInt(request.getParameter("gender"));
            int roleId = Integer.parseInt(request.getParameter("role"));

            // Mật khẩu mặc định
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            String password = "12345678";
            String encodedPassword = passwordEncoder.encode(password);

            // Kiểm tra dữ liệu đầu vào
            List<String> errors = new ArrayList<>();
            if (userName.isEmpty() || fullName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
                errors.add("Vui lòng nhập đầy đủ thông tin.");
            }

            // Tạo DAO để kiểm tra dữ liệu
            StaffDAOHieu dao = new StaffDAOHieu();

            if (dao.checkUserNameDuplicate(userName)) {
                errors.add("Tên tài khoản đã tồn tại.");
            }
            if (dao.checkEmailDuplicate(email)) {
                errors.add("Email đã tồn tại.");
            }
            if (dao.checkPhoneDuplicate(phoneNumber)) {
                errors.add("Số điện thoại đã tồn tại.");
            }
            
            // Tạo đối tượng nhân viên
            StaffHieu staff = new StaffHieu(0, roleId, userName, fullName, email, encodedPassword, phoneNumber, status, gender, null);
            if (!errors.isEmpty()) {
    request.setAttribute("errors", errors);
    request.setAttribute("staff", staff); // Giữ dữ liệu nhập
    request.getRequestDispatcher("HieuPTM/StaffManage.jsp").forward(request, response);
    return;
}


            // Nếu có lỗi, quay lại trang và hiển thị lỗi
            if (!errors.isEmpty()) {
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("HieuPTM/StaffManage.jsp").forward(request, response);
                return;
            }

            
            
            // Thêm nhân viên
            String result = dao.addStaff(staff);
            if (result.equals("Thêm nhân viên thành công!")) {
                request.setAttribute("success", "Thêm nhân viên thành công!");
            } else {
                request.setAttribute("error", result);
            }

            // Chuyển hướng về trang quản lý nhân viên
            request.getRequestDispatcher("HieuPTM/StaffManage.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi xử lý dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("HieuPTM/StaffManage.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Thêm nhân viên mới";
    }
}
