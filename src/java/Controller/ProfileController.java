package Controller;

import DAL.AddressDAO;
import DAL.FeedbacAnhkDAO;
import DAL.OrderDAO;
import DAL.UserDAO;
import Model.AddressAnh;
import Model.FeedbackAnh;
import Model.UserAnh;
import Model.Orders;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProfileController", urlPatterns = {"/profile"})
public class ProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    HttpSession session = request.getSession();
    Integer userId = (Integer) session.getAttribute("user_id"); // Lấy userId từ session

    if (userId < 0) {
        response.sendRedirect("/LoginControl"); // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
        return;
    }

        // Gọi DAO để lấy thông tin người dùng
        UserDAO userDAO = new UserDAO();
        List<UserAnh> userInfo = userDAO.getInforUserById(userId);

        if (userInfo.isEmpty()) {
            // Nếu không tìm thấy người dùng, chuyển hướng đến trang lỗi
            response.sendRedirect(request.getContextPath() + "/errorPage");
            return;
        }

        // Lấy email của người dùng từ thông tin đã lấy
        String email = userInfo.get(0).getEmail();

        // Gọi OrderDAO để lấy tổng số đơn hàng của người dùng
        OrderDAO orderDAO = new OrderDAO();
        int totalOrders = orderDAO.getTotalOrdersByCustomer(email);
        List<Orders> orders = orderDAO.getOrdersByUserId(userId);

        // lấy tổng số đơn hàng feedback
        FeedbacAnhkDAO feedbackDAO = new FeedbacAnhkDAO();
        int totalFeedbacks = feedbackDAO.getTotalFeedbackByEmail(email);

        FeedbacAnhkDAO feedbackanhDAO = new FeedbacAnhkDAO();

        List<FeedbackAnh> feedbackHistory = feedbackanhDAO.getFeedbackByEmail(email); // Lấy danh sách phản hồi

        AddressDAO addressDAO = new AddressDAO();
        List<AddressAnh> addresses = addressDAO.getAddressesByUserId(userId);

// Gửi danh sách địa chỉ sang JSP
        request.setAttribute("addresses", addresses);
        request.setAttribute("orders", orders); // Truyền danh sách đơn hàng sang JSP

        // Gửi thông tin người dùng và tổng số đơn hàng sang JSP
        request.setAttribute("user", userInfo.get(0)); // Lấy thông tin người dùng đầu tiên
        request.setAttribute("totalOrders", totalOrders); // Gửi tổng số đơn hàng
        request.setAttribute("totalFeedbacks", totalFeedbacks); // Gửi tổng số lượt đánh giá
        request.setAttribute("feedbackHistory", feedbackHistory);

        request.getRequestDispatcher("Profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    HttpSession session = request.getSession();
    Integer userId = (Integer) session.getAttribute("user_id"); // Lấy userId từ session

    if (userId < 0) {
        response.sendRedirect("/LoginControl"); // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
        return;
    }
        String action = request.getParameter("action");

        AddressDAO addressDAO = new AddressDAO();

        if ("editAddress".equals(action)) {
            // Lấy thông tin địa chỉ từ request
            int addressId = Integer.parseInt(request.getParameter("addressId"));
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String city = request.getParameter("city");
            String district = request.getParameter("district");
            String ward = request.getParameter("ward");
            String street = request.getParameter("street");

            // Tạo đối tượng AddressAnh với thông tin mới
            AddressAnh updatedAddress = new AddressAnh();
            updatedAddress.setAddressId(addressId);
            updatedAddress.setName(name);
            updatedAddress.setPhone(phone);
            updatedAddress.setCity(city);
            updatedAddress.setDistrict(district);
            updatedAddress.setWard(ward);
            updatedAddress.setStreet(street);

            // Gọi DAO để cập nhật địa chỉ
            boolean success = addressDAO.updateAddress(updatedAddress);

            if (success) {
                // Cập nhật thành công, chuyển hướng lại trang profile
                response.sendRedirect(request.getContextPath() + "/profile");
            } else {
                // Cập nhật thất bại, hiển thị thông báo lỗi
                request.setAttribute("errorMessage", "Cập nhật địa chỉ thất bại.");
                doGet(request, response);
            }
        } else if ("addAddress".equals(action)) {
            // Lấy thông tin địa chỉ từ request
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String city = request.getParameter("city");
            String district = request.getParameter("district");
            String ward = request.getParameter("ward");
            String street = request.getParameter("street");

            // Tạo đối tượng AddressAnh mới
            AddressAnh newAddress = new AddressAnh();
            newAddress.setName(name);
            newAddress.setPhone(phone);
            newAddress.setCity(city);
            newAddress.setDistrict(district);
            newAddress.setWard(ward);
            newAddress.setStreet(street);

            // Gọi DAO để thêm địa chỉ
            boolean success = addressDAO.addAddress(newAddress, userId);

            if (success) {
                // Thêm thành công, chuyển hướng lại trang profile
                response.sendRedirect(request.getContextPath() + "/profile");
            } else {
                // Thêm thất bại, hiển thị thông báo lỗi
                request.setAttribute("errorMessage", "Thêm địa chỉ thất bại.");
                doGet(request, response);
            }
        } else if ("deleteAddress".equals(action)) {
            // Lấy ID của địa chỉ cần xóa
            int addressId = Integer.parseInt(request.getParameter("addressId"));

            // Gọi DAO để xóa địa chỉ
            boolean success = addressDAO.deleteAddress(addressId);

            if (success) {
                // Xóa thành công, chuyển hướng lại trang profile
                response.sendRedirect(request.getContextPath() + "/profile");
            } else {
                // Xóa thất bại, hiển thị thông báo lỗi
                request.setAttribute("errorMessage", "Xóa địa chỉ thất bại.");
                doGet(request, response);
            }
        }
    }

}
