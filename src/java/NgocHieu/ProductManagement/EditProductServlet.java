package NgocHieu.ProductManagement;

import DAL.InsertProductDAO;
import DAL.ProductDAO;
import Model.Category;
import Model.Product;
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

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
@WebServlet(name="EditProductServlet", urlPatterns={"/EditProductServlet"})
public class EditProductServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String id = request.getParameter("product_id");
        if(id == null ){
            response.getWriter().println("<script>alert('Product Id bị rỗng'); window.location='ProductDashboard';</script>");
            return;
        }
        int product_id = Integer.parseInt(id);
        ProductDAO productDao = new ProductDAO();
        
        try {
            Product product = productDao.getProductById(product_id);
            List<Category> listCategory = productDao.getAllCategories();

            request.setAttribute("listCategory", listCategory);
            request.setAttribute("product", product);
            request.getRequestDispatcher("NgocHieu/EditProductJSP.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(EditProductServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            
            String id = request.getParameter("product_id");
            String product_name = request.getParameter("product_name");
            String description = request.getParameter("description");
            String created_at = request.getParameter("created_at");
            int discount = Integer.parseInt(request.getParameter("discount"));
            int category_id = Integer.parseInt(request.getParameter("category_id"));
            int product_id = Integer.parseInt(id);
            String thumbnail = getThumbnailUrl(filePart,product_id);
            Product product = new Product(product_id,category_id,product_name,description,discount,false,thumbnail,created_at);
            response.getWriter().print(id);
            dao.updateProduct(product);
            response.sendRedirect("ProductDashboard");
        } catch (SQLException ex) {
            Logger.getLogger(AddProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getThumbnailUrl(Part filePart,int product_id) throws IOException, SQLException{
        InsertProductDAO dao = new InsertProductDAO();
            
            // Xử lý file upload
            String fileExtension = ".avif"; // Chỉ chấp nhận .avif
            String fileName = "thumbnail" + fileExtension; // Đổi tên file 

            // Đường dẫn lưu file
            String uploadPath = "C:\\Users\\admin\\ShopRunner\\web\\Image2\\productID_"+ product_id;
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
            String thumbnailPath = "Image2/productID_" + product_id + "/"+fileName; // Đường dẫn tương đối
            
            return thumbnailPath;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
