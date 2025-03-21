/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ManhTuan;

import DAL.ProductDAOTuan;
import Model.UserTuan;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author tuan
 */
@WebServlet(name = "CustomerAdd", urlPatterns = {"/customeradd"})
public class CustomerAdd extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        boolean status = "1".equals(request.getParameter("status"));
        int gender = Integer.parseInt(request.getParameter("gender"));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String password = "1";
        String encodedPassword = passwordEncoder.encode(password);
        UserTuan customer = new UserTuan(userName, fullName, email, encodedPassword, phoneNumber, status, gender);
        ProductDAOTuan dao = new ProductDAOTuan();
        List<UserTuan> listCustomer = dao.getAllCustomer();
        request.setAttribute("customer", customer);
        for (UserTuan cus : listCustomer) {
            if (cus.getEmail().equals(customer.getEmail())) {
                request.setAttribute("error", "Email đã tồn tại");
                request.getRequestDispatcher("ManhTuan/customeradd.jsp").forward(request, response);
                return;
            } else if (cus.getPhoneNumber().equals(customer.getPhoneNumber())) {
                request.setAttribute("error", "Số điện thoại đã tồn tại");
                request.getRequestDispatcher("ManhTuan/customeradd.jsp").forward(request, response);
                return;
            } else if (cus.getUserName().equals(customer.getUserName())) {
                request.setAttribute("error", "Tên tài khoản đã tồn tại");
                request.getRequestDispatcher("ManhTuan/customeradd.jsp").forward(request, response);
                return;
            }
        }
        int id = dao.addCustomer(customer);
        request.setAttribute("error", "Thêm thành công");
        request.getRequestDispatcher("ManhTuan/customeradd.jsp").forward(request, response);
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
        return "Short description";
    }

}
