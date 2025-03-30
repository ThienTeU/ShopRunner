package NgocHieu.ProductManagement;

import DAL.InsertProductDAO;
import DAL.ProductDAO;
import Model.Size;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "AddProductQuantityServlet", urlPatterns = {"/AddProductQuantityServlet"})
public class AddProductQuantityServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO dao2 = new ProductDAO();
        try {
            List<Size> listSize = dao2.getAllSizes();
            request.getSession().setAttribute("listSize", listSize);
            response.sendRedirect("NgocHieu/AddProductQuantityJSP.jsp");
        } catch (SQLException ex) {
            Logger.getLogger(AddProductQuantityServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            
            InsertProductDAO dao = new InsertProductDAO();

            // Lấy và kiểm tra các tham số
            String productprice_idStr = request.getParameter("productprice_id");
            String quantityStr = request.getParameter("quantity");
            String sizeIdsString = request.getParameter("size_id");
            String product_id = request.getParameter("product_id");

            if (productprice_idStr == null || quantityStr == null || sizeIdsString == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("Thiếu thông tin cần thiết");
                return;
            }

            int productprice_id = Integer.parseInt(productprice_idStr);
            int quantity = Integer.parseInt(quantityStr);

            // Xử lý danh sách size
            List<Integer> listSizeId = new ArrayList<>();
            if (!sizeIdsString.isEmpty()) {
                String[] listSizeString = sizeIdsString.split(",");
                for (String id : listSizeString) {
                    listSizeId.add(Integer.parseInt(id.trim()));
                }
            }

            // Kiểm tra danh sách size
            if (listSizeId.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("Vui lòng chọn ít nhất một kích thước");
                return;
            }

            // Thêm số lượng cho từng size
            boolean success = true;
            for (Integer size_id : listSizeId) {
                try {
                    dao.addProductQuantity(productprice_id, size_id, quantity);
                } catch (SQLException ex) {
                    Logger.getLogger(AddProductQuantityServlet.class.getName()).log(Level.SEVERE, null, ex);
                    success = false;
                    break;
                }
            }

            if (!success) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println("Lỗi khi thêm số lượng sản phẩm");
                return;
            }

            // Nếu thành công, forward đến trang upload ảnh
            request.setAttribute("productprice_id", productprice_id);
            request.setAttribute("product_id", product_id);
            request.getRequestDispatcher("NgocHieu/UploadImgJSP.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Dữ liệu không hợp lệ");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Có lỗi xảy ra: " + e.getMessage());
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
