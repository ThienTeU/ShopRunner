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
    Integer userId = (Integer) session.getAttribute("user_id"); 

    if (userId < 0) {
        response.sendRedirect("/LoginControl"); 
        return;
    }

        UserDAO userDAO = new UserDAO();
        List<UserAnh> userInfo = userDAO.getInforUserById(userId);

        if (userInfo.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/errorPage");
            return;
        }

        String email = userInfo.get(0).getEmail();

        OrderDAO orderDAO = new OrderDAO();
        int totalOrders = orderDAO.getTotalOrdersByCustomer(email);
        List<Orders> orders = orderDAO.getOrdersByUserId(userId);

        FeedbacAnhkDAO feedbackDAO = new FeedbacAnhkDAO();
        int totalFeedbacks = feedbackDAO.getTotalFeedbackByEmail(email);

        FeedbacAnhkDAO feedbackanhDAO = new FeedbacAnhkDAO();

        List<FeedbackAnh> feedbackHistory = feedbackanhDAO.getFeedbackByEmail(email); 

        AddressDAO addressDAO = new AddressDAO();
        List<AddressAnh> addresses = addressDAO.getAddressesByUserId(userId);

        request.setAttribute("addresses", addresses);
        request.setAttribute("orders", orders); 

        request.setAttribute("user", userInfo.get(0)); 
        request.setAttribute("totalOrders", totalOrders); 
        request.setAttribute("totalFeedbacks", totalFeedbacks); 
        request.setAttribute("feedbackHistory", feedbackHistory);

        request.getRequestDispatcher("Profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    HttpSession session = request.getSession();
    Integer userId = (Integer) session.getAttribute("user_id");

    if (userId < 0) {
        response.sendRedirect("/LoginControl"); 
        return;
    }
        String action = request.getParameter("action");

        AddressDAO addressDAO = new AddressDAO();

        if ("editAddress".equals(action)) {
            int addressId = Integer.parseInt(request.getParameter("addressId"));
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String city = request.getParameter("city");
            String district = request.getParameter("district");
            String ward = request.getParameter("ward");
            String street = request.getParameter("street");

            AddressAnh updatedAddress = new AddressAnh();
            updatedAddress.setAddressId(addressId);
            updatedAddress.setName(name);
            updatedAddress.setPhone(phone);
            updatedAddress.setCity(city);
            updatedAddress.setDistrict(district);
            updatedAddress.setWard(ward);
            updatedAddress.setStreet(street);

            boolean success = addressDAO.updateAddress(updatedAddress);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/profile");
            } else {
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

            AddressAnh newAddress = new AddressAnh();
            newAddress.setName(name);
            newAddress.setPhone(phone);
            newAddress.setCity(city);
            newAddress.setDistrict(district);
            newAddress.setWard(ward);
            newAddress.setStreet(street);

            boolean success = addressDAO.addAddress(newAddress, userId);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/profile");
            } else {
                request.setAttribute("errorMessage", "Thêm địa chỉ thất bại.");
                doGet(request, response);
            }
        } else if ("deleteAddress".equals(action)) {
            int addressId = Integer.parseInt(request.getParameter("addressId"));

            boolean success = addressDAO.deleteAddress(addressId);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/profile");
            } else {
                request.setAttribute("errorMessage", "Xóa địa chỉ thất bại.");
                doGet(request, response);
            }
        }
        
        
    }

}
