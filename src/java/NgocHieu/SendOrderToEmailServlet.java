/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package NgocHieu;

import DAL.ProductDAO;
import Model.CartItem;
import Model.CartItemDTO;
import Model.Color;
import Model.Orders;
import Model.Product;
import Model.ProductPrice;
import Model.ProductQuantity;
import Model.Size;
import NgocHieu.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
@WebServlet(name = "SendOrderToEmailServlet", urlPatterns = {"/SendOrderToEmailServlet"})
public class SendOrderToEmailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            ProductDAO productDAO = new ProductDAO();
            List<Size> listSize = new ArrayList<>();
      
            listSize = productDAO.getAllSizes();
           
            List<Color> listColor = new ArrayList<>();
            listColor = productDAO.getAllColors();
        
            
            Orders order = (Orders) request.getSession().getAttribute("order");
            Email email = new Email();

            Cookie[] cookies = request.getCookies();
            List<CartItem> cartItems = new ArrayList<>();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("cart")) {
                        try {
                            cartItems = AuthenticationService.decodeCartToken(cookie.getValue());
                        } catch (JOSEException ex) {
                            Logger.getLogger(SendOrderToEmailServlet.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ParseException ex) {
                            Logger.getLogger(SendOrderToEmailServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }

            if (cartItems.isEmpty()) {
                response.sendRedirect("CartDetailServlet");
                return;
            }

            List<CartItemDTO> cartItemsDTO = new ArrayList<>();
            int total = 0;
            for (CartItem item : cartItems) {
                try {
                    Product product = productDAO.getProductById(item.getProduct_id());
                    ProductPrice productPrice = productDAO.getProductPriceById(item.getProductprice_id());
                    ProductQuantity productQuantity = productDAO.getProductQuantityById(item.getProductquantity_id());
                    total += productPrice.getPrice() * item.getQuantity();
                    CartItemDTO dto = new CartItemDTO(product, productPrice, productQuantity, item.getQuantity());

                    cartItemsDTO.add(dto);
                } catch (SQLException ex) {
                    Logger.getLogger(SendOrderToEmailServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            String subject = "Xác nhận đơn hàng: #" + order.getOrder_id() + " - RunnerShop";
            String emailBody = "<html>"
                    + "<head>"
                    + "<style>"
                    + "body { font-family: Arial, sans-serif; }"
                    + ".container { width: 100%; margin: 0 auto; padding: 20px; }"
                    + ".header { background-color: #f8f9fa; padding: 10px 0; text-align: center; }"
                    + ".header h1 { margin: 0; }"
                    + ".order-details { margin-top: 20px; }"
                    + ".order-details table { width: 100%; border-collapse: collapse; }"
                    + ".order-details th, .order-details td { padding: 10px; border: 1px solid #ddd; }"
                    + ".order-details th { background-color: #f1f1f1; }"
                    + ".footer { margin-top: 20px; padding: 10px 0; text-align: center; }"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "<div class='container'>"
                    + "<div class='header'>"
                    + "<h1>RunnerShop</h1>"
                    + "<p>Xác Nhận Đơn Hàng</p>"
                    + "</div>"
                    + "<div class='order-details'>"
                    + "<h2>Chi Tiết Đơn Hàng #" + order.getOrder_id() + "</h2>"
                    + "<table>"
                    + "<tr>"
                    + "<th>Ảnh</th>"
                    + "<th>Tên Sản Phẩm</th>"
                    + "<th>Kích Cỡ</th>"
                    + "<th>Màu Sắc</th>"
                    + "<th>Số Lượng</th>"
                    + "<th>Giá</th>"
                    + "</tr>";

            for (CartItemDTO item : cartItemsDTO) {
                String size = "";
                for (Size s : listSize) {
                    if (item.getProductQuantity().getSize_id() == s.getSize_id()) {
                        size = s.getSize();
                        break;
                    }
                }

                String color = "";
                for (Color c : listColor) {
                    if (item.getProductPrice().getColor_id() == c.getColor_id()) {
                        color = c.getColor();
                        break;
                    }
                }
                emailBody += "<tr>"
                        + "<td><img src='' width='50' height='50' /></td>"
                        + "<td>" + item.getProduct().getProduct_name() + "</td>"
                        + "<td>" + size + "</td>"
                        + "<td>" + color + "</td>"
                        + "<td>" + item.getQuantity() + "</td>"
                        + "<td>" + formatToVND((int) item.getProductPrice().getPrice()) + "</td>"
                        + "</tr>";
            }
            emailBody += "<tr>"
                    + "<td colspan='5' style='text-align: right; font-weight: bold;'>Tổng Tiền:</td>"
                    + "<td style='font-weight: bold;'>" + formatToVND(total) + "</td>"
                    + "</tr>";
            emailBody += "</table>"
                    + "</div>"
                    + "<div class='footer'>"
                    + "<p>Thông Tin Người Nhận:</p>"
                    + "<p>Tên: " + order.getEmail() + "</p>"
                    + "<p>Số Điện Thoại: " + order.getPhone() + "</p>"
                    + "<p>Địa Chỉ: " + order.getShipping_address() + "</p>"
                    + "<p>Phương Thức Thanh Toán: " + order.getPayment_method() + "</p>"
                    + "</div>"
                    + "</div>"
                    + "</body>"
                    + "</html>";

            email.SendEmail(order.getEmail(), subject, emailBody);
            System.out.println(emailBody);
            //Xoa thong tin cart token tren cookie khi da thanh toan xong
            Cookie cartCookie = new Cookie("cart", "");
            cartCookie.setMaxAge(0);
            response.addCookie(cartCookie);
            
            response.sendRedirect("NgocHieu/OrderSuccessJSP.jsp");
        } catch (SQLException ex) {
            Logger.getLogger(SendOrderToEmailServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String formatToVND(int amount) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        return currencyFormatter.format(amount);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
