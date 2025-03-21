package NgocHieu;

import DAL.FeedbackDAO;
import DAL.ProductDAO;
import DAL.UserDAO;
import Model.Category;
import Model.Color;
import Model.Feedback;
import Model.Product;
import Model.ProductImage;
import Model.ProductPrice;
import Model.ProductQuantity;
import Model.ProductView;
import Model.Size;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ProductDetailServlet", urlPatterns = {"/ProductDetailServlet"})
public class ProductDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //UserDAO dao = new UserDAO();
            //User user = dao.getUserByEmail("admin@gmail.com");
            User user = new User("duonghieu294@gmail.com","0923232332");
            request.getSession().setAttribute("user", user);
            // Lấy product_id và color_id từ request (nếu có)
            int product_id = Integer.parseInt(request.getParameter("product_id"));
            String colorIdParam = request.getParameter("color_id");

            int color_id = -1; // Giá trị mặc định nếu không có color_id

            if (colorIdParam != null && !colorIdParam.trim().isEmpty()) {
                try {
                    color_id = Integer.parseInt(colorIdParam);
                } catch (NumberFormatException e) {
                }
            } else {
                System.out.println("color_id is missing or empty!");
            }
            //Thông tin feedback
            ProductDAO productDao = new ProductDAO();
            Product product = productDao.getProductById(product_id);
            if(product.isStatus()){
                request.getRequestDispatcher("NgocHieu/handler/error.jsp").forward(request, response);
                return;
            }
            FeedbackDAO feedbackDao = new FeedbackDAO();
            boolean checkFeedback = feedbackDao.checkFeedbackOrNot(user.getEmail(), product_id);
            String orderDate = feedbackDao.checkOrderOrNot(user.getEmail(), product_id);
            response.getWriter().print(orderDate);
            if (orderDate != null && !checkFeedback) {
                if (orderDate.matches(".+\\.\\d{1}")) {
                    orderDate += "00";  // Nếu chỉ có 1 số mili-giây, thêm "00"
                } else if (orderDate.matches(".+\\.\\d{2}")) {
                    orderDate += "0";   // Nếu có 2 số mili-giây, thêm "0"
                } else if (!orderDate.contains(".")) {
                    orderDate += ".000"; // Nếu không có mili-giây, thêm ".000"
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                LocalDateTime originDate = LocalDateTime.parse(orderDate, formatter);
                LocalDateTime endFeedbackDate = originDate.plusDays(10);
                boolean isExpired = (endFeedbackDate != null) && endFeedbackDate.isBefore(LocalDateTime.now());
                request.setAttribute("isExpired", isExpired);
                request.setAttribute("endFeedbackDate", endFeedbackDate);
            }

            List<Feedback> listFeedback = feedbackDao.getAllFeedbackById(product_id);
            double averageRate = 0;
            double total = 0;
            if (!listFeedback.isEmpty()) {
                for (Feedback fb : listFeedback) {
                    total += fb.getRating();
                }
                averageRate = total / listFeedback.size();
            }

            //Update view cho product
            productDao.updateProductView(product_id);
            // Lấy danh sách dữ liệu cơ bản
            List<ProductPrice> listUniqueProductPrice = productDao.getAllUniqueProductPrices();
            List<Color> listColor = productDao.getAllColors();
            List<Size> listSize = productDao.getAllSizes();
            List<Category> listCategory = productDao.getAllCategories();
            
            List<ProductPrice> listProductPrice = productDao.getProductPricesByProductId(product_id);
            List<ProductView> listProductView = productDao.getAllProductView();
            //Danh sách most view item
            List<Product> listMostView = productDao.getMostViewItems();
            //Danh sách related product
            List<Product> listRelatedProduct = productDao.getProductsByCategory(product.getCategory_id(), product_id);
            //Danh sách recently product
            List<Product> listRecentlyView = productDao.getRecentlyItem(product_id);
            // Nếu color_id chưa được chọn, chọn color đầu tiên trong danh sách
            if (color_id == -1 && !listProductPrice.isEmpty()) {
                color_id = listProductPrice.get(0).getColor_id();
            }

            // Lấy ProductPrice tương ứng với color_id đã chọn
            ProductPrice selectedProductPrice = null;
            for (ProductPrice pp : listProductPrice) {
                if (pp.getColor_id() == color_id) {
                    selectedProductPrice = pp;
                    break;
                }
            }

            int selectedPrice = (int) (selectedProductPrice != null ? selectedProductPrice.getPrice() : 0);
            int productPriceId = selectedProductPrice != null ? selectedProductPrice.getProductPrice_id() : -1;

            // Lấy danh sách ProductQuantity theo ProductPrice_id đã chọn
            List<ProductQuantity> listProductQuantity = productDao.getProductQuantitiesByProductPriceId(productPriceId);

            // Lấy danh sách ProductImage theo product_id và color_id
            List<ProductImage> listProductImage = productDao.getProductImagesByProductIdAndColorId(product_id, color_id);

            int countRelatedProduct = productDao.getProductCountByCategory(product.getCategory_id());

            String contextPath = request.getContextPath();
            
            
            request.setAttribute("user", user);
            request.setAttribute("orderDate", orderDate);
            request.setAttribute("checkFeedback", checkFeedback);
            request.setAttribute("listFeedback", listFeedback);
            request.setAttribute("averageRate", averageRate);
            request.getSession().setAttribute("contextPath", contextPath);
            request.setAttribute("listProductView", listProductView);

            request.setAttribute("checkNew", isWithin10Days(product.getCreated_at()));

            request.setAttribute("listCategory", listCategory);
            request.setAttribute("listProductPrice", listProductPrice);
            request.setAttribute("listUniqueProductPrice", listUniqueProductPrice);
            request.setAttribute("listProductQuantity", listProductQuantity);
            request.setAttribute("listProductImage", listProductImage);
            request.setAttribute("product", product);
            request.setAttribute("listColor", listColor);
            request.setAttribute("listSize", listSize);

            request.setAttribute("listRelatedProduct", listRelatedProduct);
            request.setAttribute("countRelatedProduct", countRelatedProduct);
            request.setAttribute("listRecentlyView", listRecentlyView);
            request.setAttribute("listMostView", listMostView);

            request.setAttribute("selectedColor", color_id);
            request.setAttribute("selectedPrice", selectedPrice);
            request.setAttribute("selectedProductPriceId", productPriceId);

            request.getRequestDispatcher("NgocHieu/ProductDetailJSP.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ProductDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean isWithin10Days(String inputTime) {
        try {
            // Định dạng thời gian đầu vào
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // Chuyển chuỗi đầu vào thành đối tượng Date
            Date inputDate = sdf.parse(inputTime);

            // Lấy thời gian hiện tại
            Date currentDate = new Date();

            // Tính toán sự khác biệt giữa thời gian hiện tại và thời gian đầu vào (tính bằng mili giây)
            long diffInMillies = currentDate.getTime() - inputDate.getTime();

            // Tính toán sự khác biệt trong ngày (1 ngày = 24 * 60 * 60 * 1000 mili giây)
            long diffInDays = diffInMillies / (24 * 60 * 60 * 1000);

            // Kiểm tra nếu sự khác biệt ít hơn 10 ngày
            return diffInDays < 10;
        } catch (ParseException e) {
            return false; // Nếu có lỗi trong quá trình phân tích chuỗi thời gian, trả về false
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
