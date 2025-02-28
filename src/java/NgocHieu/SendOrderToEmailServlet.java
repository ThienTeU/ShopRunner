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
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.NumberFormat;
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
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            ProductDAO productDAO = new ProductDAO();
            List<Size> listSize = productDAO.getAllSizes();
            List<Color> listColor = productDAO.getAllColors();
            Orders order = (Orders) request.getSession().getAttribute("order");
            Email email = new Email();
            List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cart");
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
                        break; // Thoát vòng lặp khi tìm thấy kích cỡ phù hợp
                    }
                }

                String color = "";
                for (Color c : listColor) {
                    if (item.getProductPrice().getColor_id() == c.getColor_id()) {
                        color = c.getColor();
                        break; // Thoát vòng lặp khi tìm thấy màu sắc phù hợp
                    }
                }
                String inputPath = "C:/Users/admin/ShopRunner/web/Image2/productID_" + item.getProduct().getProduct_id() + "/colorID_" + item.getProductPrice().getColor_id() + "/image_1.avif";
                String outputPath = "C:/Users/admin/ShopRunner/web/Image2/productID_" + item.getProduct().getProduct_id() + "/colorID_" + item.getProductPrice().getColor_id() + "/image_1.jpg";
                ConvertAvifToJpg convert = new ConvertAvifToJpg();
                convert.convert(inputPath, outputPath);
                emailBody += "<tr>"
                        + "<td><img src='" + outputPath +"' width='50' height='50' /></td>"
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
                    + "</div>"
                    + "</div>"
                    + "</body>"
                    + "</html>";

            email.SendEmail(order.getEmail(), subject, emailBody);
            System.out.println(emailBody);
            if (session != null) {
                session.invalidate();
            }

            response.sendRedirect(request.getContextPath() + "/productlist");
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
