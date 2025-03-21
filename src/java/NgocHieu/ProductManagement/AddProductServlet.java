package NgocHieu.ProductManagement;

import DAL.InsertProductDAO;
import DAL.ProductDAO;
import Model.Category;
import Model.Color;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "AddProductServlet", urlPatterns = {"/AddProductServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class AddProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ProductDAO dao = new ProductDAO();

            List<Category> listCategory = dao.getAllCategories();

            request.setAttribute("listCategory", listCategory);

            request.getRequestDispatcher("NgocHieu/AddProductJSP.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AddProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            InsertProductDAO dao = new InsertProductDAO();
            ProductDAO dao2 = new ProductDAO();
            
            Part filePart = request.getPart("thumbnail");
            //
            String thumbnail = getThumbnailUrl(filePart);
            
            String product_name = request.getParameter("product_name");
            if(!product_name.matches("^.{1,255}")){
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("<script>alert('Tên sản phẩm không vượt quá 255 kí tự!'); window.location='AddProductServlet';</script>");
                return;
            }
            if(thumbnail == null || filePart == null || product_name == null){
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("<script>alert('Thiếu thông tin!'); window.location='AddProductServlet';</script>");
                return;
            }
            if(dao.isProductNameExists(product_name.trim())){
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("<script>alert('Tên sản phẩm này đã tồn tại!'); window.location='AddProductServlet';</script>");
                return;
            }
            String description = request.getParameter("description");
            int discount = Integer.parseInt(request.getParameter("discount"));
            if(discount < 0 ){
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("<script>alert('Discount không hợp lệ!'); window.location='AddProductServlet';</script>");
            }
            int category_id = Integer.parseInt(request.getParameter("category_id"));
            
            List<Color> listColor = dao2.getAllColors();
            int product_id = dao.addProduct(category_id, product_name.trim(), description.trim(), discount, 0, thumbnail);
            
            request.getSession().setAttribute("listColor", listColor);
            request.setAttribute("product_id", product_id);
            request.getRequestDispatcher("NgocHieu/AddProductPriceJSP.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AddProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getThumbnailUrl(Part filePart) throws IOException, SQLException{
        InsertProductDAO dao = new InsertProductDAO();
            int maxId = dao.getMaxProductId();
            
            // Xử lý file upload
            String fileExtension = ".avif"; // Chỉ chấp nhận .avif

            String fileName = "thumbnail" + fileExtension; // Đổi tên file 

            // Đường dẫn lưu file
            int productId = maxId+1;
            String uploadPath = "C:\\Users\\admin\\ShopRunner\\web\\Image2\\productID_"+ productId;
            File uploadDir = new File(uploadPath);
            
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // Tạo thư mục nếu chưa có
            }

            // Ghi file vào thư mục
            File file = new File(uploadPath, fileName);
            try (InputStream fileContent = filePart.getInputStream(); FileOutputStream outputStream = new FileOutputStream(file)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fileContent.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            // Lưu thông tin sản phẩm vào DB với đường dẫn ảnh
            String thumbnailPath = "Image2/productID_" + productId + "/"+fileName; // Đường dẫn tương đối
            
            return thumbnailPath;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
