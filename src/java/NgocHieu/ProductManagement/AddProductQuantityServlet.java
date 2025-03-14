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
        InsertProductDAO dao = new InsertProductDAO();

        int productprice_id = Integer.parseInt(request.getParameter("productprice_id"));
        try {
            if(!dao.isExistedProductPriceId(productprice_id)){
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("<script>alert('Product Price id không hợp lệ hoặc không tồn tại!'); window.location='AddProductQuantityServlet';</script>");
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddProductQuantityServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] listSizeString = request.getParameterValues("size_id");
        if (listSizeString == null) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<script>alert('Vui lòng chọn size!'); window.location='AddProductQuantityServlet';</script>");
            return;
        }
        List<Integer> listSizeId = new ArrayList<>();

        if (listSizeString != null) {
            for (String id : listSizeString) {
                listSizeId.add(Integer.parseInt(id));
            }
        }
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        for (Integer size_id : listSizeId) {
            try {
                dao.addProductQuantity(productprice_id, size_id, quantity);
            } catch (SQLException ex) {
                Logger.getLogger(AddProductQuantityServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        request.setAttribute("productprice_id", productprice_id);
        request.getRequestDispatcher("NgocHieu/UploadImgJSP.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
