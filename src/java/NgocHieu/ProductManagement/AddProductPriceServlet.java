package NgocHieu.ProductManagement;

import DAL.InsertProductDAO;
import DAL.ProductDAO;
import Model.Color;
import Model.Size;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
@WebServlet(name = "AddProductPriceServlet", urlPatterns = {"/AddProductPriceServlet"})
public class AddProductPriceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ProductDAO dao = new ProductDAO();
            List<Color> listColor = dao.getAllColors();
            request.getSession().setAttribute("listColor", listColor);
            String id = request.getParameter("product_id");
            if (id != null) {
                request.setAttribute("product_id", id);
            }
            request.getRequestDispatcher("NgocHieu/AddProductPriceJSP.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AddProductPriceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int product_id = Integer.parseInt(request.getParameter("product_id"));
        int color_id = Integer.parseInt(request.getParameter("color_id"));
        int price = Integer.parseInt(request.getParameter("price"));
        try {
            InsertProductDAO dao = new InsertProductDAO();
            ProductDAO dao2 = new ProductDAO();
            if(dao.isExistedProductId(price)){
                 response.setContentType("text/html;charset=UTF-8");
                 response.getWriter().println("<script>alert('Mã sản phẩm không tồn tại!'); window.location='ProductDashboard';</script>");
                 return;
            }
            if(dao.isColorExistsForProduct(product_id, color_id)){
                 response.setContentType("text/html;charset=UTF-8");
                 response.getWriter().println("<script>alert('Màu này đã tồn tại!'); window.location='AddProductPriceServlet';</script>");
                 return;
            }
            
            int productprice_id = dao.addProductPrice(product_id, color_id, price);
            request.setAttribute("color_id", color_id);
            request.setAttribute("productprice_id", productprice_id);

        } catch (Exception e) {
            e.printStackTrace(); // In stack trace đầy đủ
            // Hoặc log lỗi chi tiết
            Logger.getLogger(AddProductPriceServlet.class.getName()).log(Level.SEVERE, "Chi tiết lỗi: ", e);
            // Trả về response lỗi
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Lỗi: " + e.getMessage());
        }
        request.getRequestDispatcher("NgocHieu/AddProductQuantityJSP.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
