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
            //HieuDN
            "/home",
            "/ProductDetailServlet",
            "/CartDetailServlet",
            "/CheckOutServlet",
            "/NgocHieu/handler/access-denied.jsp",
            "/error.jsp",
            "/AddToCartServlet",
            "/RemoveFromCartServlet",
            "/SendOrderToEmailServlet",
            "/UpdateCart",
            "/GetShippingFeeServlet",
            "CheckVoucherValid",
            "/CheckOutVnpayServlet",
            "/vnpay_return.jsp",
            "/vnpayajax",
            "/vnpayquery/*",
            "/vnpayrefund/*",
            "/LoginControl",
            "/testlogin",
            //TuanDM
            "/productcategory",
            "/productcheckbox",
            "/productfilter",
            "/productlist",
            "/productsearch",
            "/productsort",
            "/home",
            "/Contact.jsp",
            "/success.jsp",
            "/error.jsp",
            //HieuPTM
            "/HieuPTM/Register.jsp",
            "/HieuPTM/RegisterSuccess.jsp",
            "/HieuPTM/EnterOTP.jsp",
            "/HieuPTM/Login.jsp",
            "/HieuPTM/ForgotPassword.jsp",
            "/HieuPTM/NewPassword",
            "/RegisterControl",
            "/ValidateOTP",
            "/LoginControl",
            "/ForgotPassword",
            "/NewPassword"
    );

    private static final List<String> customerAllowedUrls = Arrays.asList(
            "/AddFeedbackServlet",
            "/ChangePassword",
            "/HieuPTM/ChangePassword.jsp",
            "/update-profile",
            "/Profile.jsp",
            "/profile"
    );

    private static final List<String> marketingAllowedUrls = Arrays.asList(
            "/ManhTuan/customeradd.jsp",
            "/ManhTuan/customeraddressdetail.jsp",
            "/ManhTuan/customeraddressedit.jsp",
            "/ManhTuan/customeredit.jsp",
            "/ManhTuan/customerlist.jsp",
            "/ManhTuan/feedbackmanagement.jsp",
            "/ManhTuan/marketingdashboard.jsp",
            "/ManhTuan/orderdetail.jsp",
            "/ManhTuan/orderlist.jsp",
            "/customeradd",
            "/customeraddressadd",
            "/customeraddressdetail",
            "/customeraddressedit",
            "/changeStatus",
            "/customeredit",
            "/customerlist",
            "/customersearch",
            "/dashboard",
            "/feedbackstatus",
            "/export",
            "/feedbacklist",
            "/feedbackreply",
            "/feedbackreplydelete",
            "/feedbackreplyedit",
            "/feedbacksearch",
            "/orderdetail",
            "/orderlist",
            "/ordersearch",
            "/contactList",
            "/admin",
            "/AdminManage/adminHome.jsp"
    );

    private static final List<String> salerAllowedUrls = Arrays.asList(
            "/AddProductPriceServlet",
            "/AddProductQuantityServlet",
            "/AddProductServlet",
            "/CancelOrderServlet",
            "/EditProductServlet",
            "/ProductDashboard",
            "/TestUploadFileServlet",
            "/UpdatePriceServlet",
            "/UpdateQuantityServlet",
            "/UpdateStatusServlet",
            "/OrderManageServlet",
            //Validate Input For Adding Product
            "/CheckExistColorServlet",
            "/CheckExistProductIdServlet",
            "/CheckExistProductNameServlet",
            "/CheckExistProductPriceIdServlet",
            "/CheckExistProductQuantityIdServlet",
            //TuanDM
            "/ManhTuan/customeradd.jsp",
            "/ManhTuan/customeraddressdetail.jsp",
            "/ManhTuan/customeraddressedit.jsp",
            "/ManhTuan/customeredit.jsp",
            "/ManhTuan/customerlist.jsp",
            "/ManhTuan/feedbackmanagement.jsp",
            "/ManhTuan/orderdetail.jsp",
            "/ManhTuan/orderlist.jsp",
            "/customeradd",
            "/customeraddressadd",
            "/customeraddressdetail",
            "/customeraddressedit",
            "/changeStatus",
            "/customeredit",
            "/customerlist",
            "/customersearch",
            "/dashboard",
            "/feedbackstatus",
            "/export",
            "/feedbacklist",
            "/feedbackreply",
            "/feedbackreplydelete",
            "/feedbackreplyedit",
            "/feedbacksearch",
            "/orderdetail",
            "/orderlist",
            "/ordersearch",
            "/contactList",
            "/admin",
            "/AdminManage/adminHome.jsp"
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
        if (path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".png") ||
                path.endsWith(".jpg") || path.endsWith(".avif") || path.contains("sandbox") || path.contains("vnpay")) {
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

                if ("Customer".equals(role) && customerAllowedUrls.contains(path)) {
                    chain.doFilter(request, response);
                    return;
                }
                if ("Marketing".equals(role) && marketingAllowedUrls.contains(path)) {
                    chain.doFilter(request, response);
                    return;
                }
                if ("Saler".equals(role) && salerAllowedUrls.contains(path)) {
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