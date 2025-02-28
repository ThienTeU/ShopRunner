package NgocHieu;

import DAL.ProductDAO;
import Model.Category;
import Model.Color;
import Model.Product;
import Model.ProductImage;
import Model.ProductPrice;
import Model.ProductQuantity;
import Model.ProductView;
import Model.Size;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
@WebServlet(name = "ProductDetailServlet", urlPatterns = {"/ProductDetailServlet"})
public class ProductDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            User user = new User(1,1,"duonghieu294@gmail.com","hieuhieu","0397761602","Phuc Tho, Nghi Loc, Nghe An",true);
            request.getSession().setAttribute("user", user);
            // Lấy product_id và color_id từ request (nếu có)
            int product_id = Integer.parseInt(request.getParameter("product_id"));
            String colorIdParam = request.getParameter("color_id");

            int color_id = -1; // Giá trị mặc định nếu không có color_id

            if (colorIdParam != null && !colorIdParam.trim().isEmpty()) {
                try {
                    color_id = Integer.parseInt(colorIdParam);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("color_id is missing or empty!");
            }

            ProductDAO productDao = new ProductDAO();

            //Update view cho product
            productDao.updateProductView(product_id);
            // Lấy danh sách dữ liệu cơ bản
            List<ProductPrice> listUniqueProductPrice = productDao.getAllUniqueProductPrices();
            List<Color> listColor = productDao.getAllColors();
            List<Size> listSize = productDao.getAllSizes();
            List<Category> listCategory = productDao.getAllCategories();
            Product product = productDao.getProductById(product_id);
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
                    break; // Thoát vòng lặp khi tìm thấy kết quả
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
            // Gửi dữ liệu sang JSP
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
        } catch (Exception e) {
            e.printStackTrace();
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
