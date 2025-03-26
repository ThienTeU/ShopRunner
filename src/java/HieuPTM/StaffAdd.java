package HieuPTM;

import DAL.StaffDAOHieu;
import Model.StaffHieu;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@WebServlet(name = "StaffAdd", urlPatterns = {"/StaffAdd"})
public class StaffAdd extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userName = request.getParameter("userName");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        boolean status = "1".equals(request.getParameter("status"));
        int gender = Integer.parseInt(request.getParameter("gender"));
        int roleId = Integer.parseInt(request.getParameter("role"));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String password = "12345678";
        String encodedPassword = passwordEncoder.encode(password);

        StaffHieu staff = new StaffHieu(userName, fullName, email, encodedPassword, phoneNumber, status, gender);
        StaffDAOHieu dao = new StaffDAOHieu();
        List<StaffHieu> listStaff = dao.getAllStaff();
        List<String> errors = new ArrayList<>();
        request.setAttribute("staff", staff);

        for (StaffHieu sta : listStaff) {
            if (sta.getEmail().equals(email)) {
                errors.add("Email đã tồn tại");
            }
            if (sta.getPhoneNumber().equals(phoneNumber)) {
                errors.add("Số điện thoại đã tồn tại");
            }
            if (sta.getUserName().equals(userName)) {
                errors.add("Tên tài khoản đã tồn tại");
            }
        }
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("staff", staff);
            //request.getRequestDispatcher("HieuPTM/StaffManage.jsp").forward(request, response);
            return;
        }

        String result = dao.addStaff(staff);
        if ("Thêm nhân viên thành công!".equals(result)) {
            request.setAttribute("success", "Thêm thành công!");
        } else {
            request.setAttribute("error", result);
        }

        request.getRequestDispatcher("HieuPTM/StaffManage.jsp").forward(request, response);
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
    
    public static void main(String[] args) {
        StaffDAOHieu dao = new StaffDAOHieu();
        StaffHieu staff = new StaffHieu("sv", "sv", "sv@36.com", "1", "0931338288", true, 1);
                //userName, fullName, email, encodedPassword, phoneNumber, status, gender
        dao.addStaff(staff);
        List<StaffHieu> listStaff = dao.getAllStaff();
        for(StaffHieu a : listStaff){
            System.out.println(a.toString());
        }
    }
}
