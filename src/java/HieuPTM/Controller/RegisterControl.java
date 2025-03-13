package HieuPTM.controller;

import HieuPTM.DAO.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import HieuPTM.model.UserHieu;
import jakarta.servlet.annotation.WebServlet;






@WebServlet(name="RegisterControl", urlPatterns={"/RegisterControl"})
public class RegisterControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet signupControl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet signupControl at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validatePhone(String phone) {
        String phoneRegex = "^0\\d{9}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    private boolean validatePassword(String password) {
    String passwordRegex = "^(?=.*\\d).{6,}$"; // Ít nhất 6 ký tự và chứa ít nhất 1 chữ số
    Pattern pattern = Pattern.compile(passwordRegex);
    Matcher matcher = pattern.matcher(password);
    return matcher.matches();
}


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO udao = new UserDAO();

        String userName = request.getParameter("username");
        String fullName = request.getParameter("fullname");
        String password = request.getParameter("password");
        int roleID = 2;
        String email = request.getParameter("email");
        String birthDay = request.getParameter("birthday");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");

        request.setAttribute("username", userName);
        request.setAttribute("fullname", fullName);
        request.setAttribute("email", email);
        request.setAttribute("birthday", birthDay);
        request.setAttribute("address", address);
        request.setAttribute("phone", phone);

        if (!validateEmail(email)) {
            request.setAttribute("error", "Định dạng email không hợp lệ! Vui lòng nhập địa chỉ email hợp lệ.");
            request.getRequestDispatcher("HieuPTM/Register.jsp").forward(request, response);
        } else if (!validatePhone(phone)) {
            request.setAttribute("error", "Định dạng số điện thoại không hợp lệ! Vui lòng nhập số điện thoại bắt đầu bằng số 0 và có 10 chữ số.");
            request.getRequestDispatcher("HieuPTM/Register.jsp").forward(request, response);
        } else if (!validatePassword(password)) {
            request.setAttribute("error", "Mật khẩu phải có ít nhất 6 ký tự, bao gồm 1 chữ số.");
            request.getRequestDispatcher("HieuPTM/Register.jsp").forward(request, response);
        } else if (!udao.checkUserNameDuplicate(userName)) {  // Nếu username đã tồn tại, báo lỗi
            request.setAttribute("error", "Tài khoản đã tồn tại. Vui lòng chọn tên tài khoản khác!");
            request.getRequestDispatcher("HieuPTM/Register.jsp").forward(request, response);
        } else {
            //udao.insert(new UserHieu(userName, fullName, password, address, phone, email, "HieuPTM/Images/users/none.jpg", birthDay, roleID));
            udao.insert(new UserHieu(userName, fullName, password, phone, email, phone, roleID));
            response.sendRedirect("HieuPTM/RegisterSuccess.jsp");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/HieuPTM/Register.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}