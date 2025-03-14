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

        String searchKey = request.getParameter("searchKey"); // Lấy từ khóa tìm kiếm nếu có
        String sortType = request.getParameter("sort"); // Lấy giá trị sắp xếp từ URL
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        try {
            ProductDAO productDAO = new ProductDAO();
            List<Product> paginatedList;
            int totalProducts;
            
            if (searchKey != null && !searchKey.trim().isEmpty()) {
                // Nếu có từ khóa tìm kiếm
                totalProducts = productDAO.getTotalProductsBySearch(searchKey);
                paginatedList = productDAO.searchProductsByNamePage(searchKey, page, PAGE_SIZE, sortType);
            } else {
                // Nếu không có từ khóa, lấy danh sách sản phẩm theo sắp xếp
                totalProducts = productDAO.getTotalProducts();
                paginatedList = productDAO.getProductsByPageSorted(page, PAGE_SIZE, sortType);
            }

            int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);

            request.setAttribute("paginatedList", paginatedList);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("sortType", sortType);
            request.setAttribute("searchKey", searchKey);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("AdminManage/productManageJSP.jsp").forward(request, response);
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
