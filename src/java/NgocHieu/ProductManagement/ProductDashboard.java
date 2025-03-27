/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package NgocHieu.ProductManagement;

import DAL.ProductDAO;
import Model.Product;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author admin
 */
@WebServlet(name = "ProductDashboard", urlPatterns = {"/ProductDashboard"})
public class ProductDashboard extends HttpServlet {

    private static final int PAGE_SIZE = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String searchKey = request.getParameter("searchKey");
        String sortType = request.getParameter("sort");
        boolean isAjax = "true".equals(request.getParameter("ajax"));
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        try {
            ProductDAO productDAO = new ProductDAO();
            List<Product> paginatedList;
            int totalProducts;
            
            if (searchKey != null && !searchKey.trim().isEmpty()) {
                totalProducts = productDAO.getTotalProductsBySearch(searchKey);
                paginatedList = productDAO.searchProductsByNamePage(searchKey, page, PAGE_SIZE, sortType);
            } else {
                totalProducts = productDAO.getTotalProducts();
                paginatedList = productDAO.getProductsByPageSorted(page, PAGE_SIZE, sortType);
            }

            int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);

            request.setAttribute("paginatedList", paginatedList);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("sortType", sortType);
            request.setAttribute("searchKey", searchKey);

            if (isAjax) {
                // Nếu là request Ajax, chỉ trả về phần bảng sản phẩm
                request.getRequestDispatcher("AdminManage/product-table-fragment.jsp").forward(request, response);
            } else {
                // Nếu không phải Ajax, trả về toàn bộ trang
                request.getRequestDispatcher("AdminManage/productManageJSP.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            if (isAjax) {
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            } else {
                throw new ServletException(e);
            }
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
