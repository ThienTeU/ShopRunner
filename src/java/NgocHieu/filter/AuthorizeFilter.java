/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package NgocHieu.filter;

import NgocHieu.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class AuthorizeFilter implements Filter {

    private final AuthenticationService authService = new AuthenticationService();

    private static final List<String> publicUrls = Arrays.asList(
            "/",

            "/home",    
            //HieuPTM_Guest
            "/RegisterControl",
            "/ordersearch",
            "/orderdetail",
            "/ForgotPassword",
            "/ValidateOTP",
            "/NewPassword",
            //HieuPTM_User
            "/LoginControl",
            "/Charts",
            "/home",
            //HieuPTM_STAFF
            "/LogOut",
            "/ChangePassword",
            //HieuPTM_Admin
            "/AdminDashboard",
            "/HieuPTM/AdminDashboard.jsp",
            "/HieuPTM/StaffManage.jsp",
            "/HieuPTM.Controller.StaffManage",
            "/StaffManage",
            "/StaffSearch",
            "/StaffAdd",
            "/StaffChangeStatus",
            "/HieuPTM/StaffManage",
            "/profile",
            "/Profile.jsp",
            "/About.jsp",
            "/feedbacklist",
            "/feedbacksearch",
            "/feedbackreply",
            "/feedbackreplydelete",
            "/tableVoucher",
            "/Charts",
            "/ManhTuan/orderlist.jsp",
            "/ManhTuan/test.jsp",
            "/tableVoucher",
            "/orderlist",
            "/ordersprofile",
            "/dashboard",
            "/ManhTuan/marketingdashboard.jsp",
            "/home",
            "/ManagerSeller.jsp",
            "/AddFeedbackServlet",
            "/RunnerShop/ManhTuan/customeradd",
            "/ManhTuan/customeradd",


            "/feedbackstatus",
            "/export",
            "/HieuPTM/StaffManage.jsp",
            "/HieuPTM.Controller.StaffManage",
            "/StaffManage",
            "/StaffSearch",
            "/StaffAdd",
            "/StaffChangeStatus",
            //HieuPTM
            "/LoginControl",
            "/RegisterControl",
            "/ForgotPassword",
            "/ValidateOTP",
            "/ChangePassword",
            "/NewPassword",
            "/RunnerShop/ManhTuan/customeradd",
            "/ManhTuan/customeradd",
            "/ManhTuan/customeraddressdetail.jsp",
            "/ManhTuan/feedbackmanagement.jsp",
            "/customeradd.jsp",
            "/customeraddressedit",
            "/customeraddressadd",
            "/ManhTuan/marketingdashboard.jsp",
            "/ManhTuan/customeraddressadd.jsp",
            "/ManhTuan/customeraddressedit.jsp",
            "/RunnerShop",
            "/testlogin",
            "/productlist",
            "/ProductDetailServlet",
            "/CheckOutServlet",
            "/CheckOutVnpayServlet",
            "/customeraddressdetail",
            "/ManhTuan/customeraddressadd",
            "/ManhTuan/CheckOutJSP.jsp",
            "/ManhTuan/customeradd.jsp",
            "/customeradd",
            "/RunnerShop/customeradd",
            "/customeraddressadd",
            "/changeStatus",
            "/customeredit",
            "/customersearch",
            "/customerlist",
            "/CartDetailServlet",
            "/AddToCartServlet",
            "/UpdateCart",
            "/GetShippingFeeServlet",
            "/RemoveFromCartServlet",
            "/SendOrderToEmailServlet",
            "/vnpayquery/*",
            "/vnpayajax",
            "/NgocHieu/CheckOutJSP.jsp",
            "/NgocHieu/OrderSuccessJSP.jsp",
            "/vnpayrefund/*",
            "/AddFeedbackServlet",
            "/NgocHieu/vnpay/vnpay_pay.jsp",
            "https://sandbox.vnpayment.vn/paymentv2/Transaction/PaymentMethod.html?token=161cbbf8d2ed4c8baf99ae68ea32fd7d",
            "/NgocHieu/handler/access-denied.jsp",
            "/model/header.jsp"
    );

    private static final List<String> userAllowedUrls = Arrays.asList(
            "/user/dashboard.jsp",
            "/user/Profile.jsp",
            "/user/settings.jsp",
            "/api/user/data"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getServletPath();

        if (publicUrls.contains(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Bỏ qua file tĩnh
        if (path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".png") || path.endsWith(".jpg") || path.endsWith(".avif") || path.contains("sandbox") || path.contains("vnpay")) {
            chain.doFilter(request, response);
            return;
        }

        String token = (String) req.getSession().getAttribute("token");
        System.out.println("Token: " + token);

        try {
            if (token != null && authService.introspect(token)) {
                String role = authService.getUserRoleFromToken(token);
                System.out.println("User Role: " + role); // Debug

                if ("Admin".equals(role)) {
                    chain.doFilter(request, response);
                    return;
                }

                if ("User".equals(role) && userAllowedUrls.contains(path)) {
                    chain.doFilter(request, response);
                    return;
                }
            }
        } catch (ParseException | JOSEException ex) {
            Logger.getLogger(AuthorizeFilter.class.getName()).log(Level.SEVERE, null, ex);
        }

        res.sendRedirect(req.getContextPath() + "/NgocHieu/handler/access-denied.jsp");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
