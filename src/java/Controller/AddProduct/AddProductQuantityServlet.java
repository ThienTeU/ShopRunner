package Controller.AddProduct;

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
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        InsertProductDAO dao = new InsertProductDAO();

        int productprice_id = Integer.parseInt(request.getParameter("productprice_id"));
        String[] listSizeString = request.getParameterValues("size_id");
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
        request.getRequestDispatcher("UploadImgJSP.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
