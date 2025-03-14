package NgocHieu.ProductManagement;

import DAL.InsertProductDAO;
import Model.ProductPrice;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "TestUploadFileServlet", urlPatterns = {"/TestUploadFileServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class TestUploadFileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        InsertProductDAO dao = new InsertProductDAO();
        int productId = 0;
        try {
            productId = dao.getMaxProductId();
        } catch (SQLException ex) {
            Logger.getLogger(TestUploadFileServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        int productprice_id = Integer.parseInt(request.getParameter("productprice_id"));
        ProductPrice pp = new ProductPrice();
        try {
            pp = dao.getProductPrice(productprice_id);
        } catch (SQLException ex) {
            Logger.getLogger(TestUploadFileServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Đường dẫn lưu file
        String uploadPath = "C:\\Users\\admin\\ShopRunner\\web\\Image2\\productID_"
                + +productId + "\\colorID_" + pp.getColor_id();
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Tạo thư mục nếu chưa có
        }

        // Đếm số file hiện có để đặt tên tiếp theo
        File[] existingFiles = uploadDir.listFiles((dir, name) -> name.startsWith("image_") && name.endsWith(".avif"));
        int index = (existingFiles != null) ? existingFiles.length + 1 : 1;

        // **Lặp qua tất cả các file**
        Collection<Part> parts = request.getParts(); // Lấy danh sách các Part
        List<Part> partList = new ArrayList<>(parts); // Chuyển sang List

        for (Part part : partList) {
            System.out.println("File proceses--- " + part.getSubmittedFileName());
            if (part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
                // Đặt tên file theo thứ tự
                String newFileName = "image_" + index + ".avif";
                index++;
                String filePath = uploadPath + File.separator + newFileName;

                // Lưu file vào thư mục
                File file = new File(filePath);
                try (InputStream fileContent = part.getInputStream(); FileOutputStream outputStream = new FileOutputStream(file)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fileContent.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                System.out.println("Luu file r " + filePath);

                // Lưu vào database nếu cần
                String relativePath = "Image2/productID_" + productId + "/colorID_" + pp.getColor_id() + "/" + newFileName;
                try {
                    dao.addProductImage(productId, relativePath, pp.getColor_id());
                } catch (SQLException ex) {
                    Logger.getLogger(TestUploadFileServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        request.setAttribute("notiAdd", "Da them san pham thanh cong");
        request.getRequestDispatcher("NgocHieu/UploadImgSuccessJSP.jsp").forward(request, response);
    }

// Lấy tên file từ Part
    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return "unknown";
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
