/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAL.BannerDAO;
import DAL.CategoryDAO;
import DAL.ProductDAO;
import Model.Banner;
import Model.Category;
import Model.CategoryAnh;
import Model.Product;
import Model.ProductView;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anhco
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Home</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Home at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    CategoryDAO categoryDAO = new CategoryDAO();
    BannerDAO bannerDAO = new BannerDAO();
    ProductDAO productDAO = new ProductDAO();

    // Lấy các tham số
    String searchQuery = request.getParameter("query");
    String categoryFilter = request.getParameter("category");
    String sortPrice = request.getParameter("sortPrice");
    
    // Xử lý phân trang
    int pageSize = 12;
    int page = 1;
    try {
        page = Integer.parseInt(request.getParameter("page"));
    } catch (NumberFormatException e) {
        page = 1;
    }
    int offset = (page - 1) * pageSize;

    // Khởi tạo danh sách sản phẩm
    List<Product> listproduct;
    int totalProducts;

    // Xử lý tìm kiếm và lọc
    if (searchQuery != null && !searchQuery.trim().isEmpty()) {
        // Tìm kiếm sản phẩm
        listproduct = productDAO.searchProducts(searchQuery, sortPrice, offset, pageSize);
        totalProducts = productDAO.getTotalSearchResults(searchQuery);
    } else if (categoryFilter != null && !categoryFilter.trim().isEmpty()) {
        // Lọc theo danh mục
        switch (categoryFilter) {
            case "nam":
                listproduct = productDAO.getAllProductNam();
                break;
            case "nu":
                listproduct = productDAO.getAllProductNu();
                break;
            case "giay":
                listproduct = productDAO.getAllProductGiay();
                break;
            case "other":
                listproduct = productDAO.getAllProductOther();
                break;
            default:
                listproduct = productDAO.getAllProduct();
                break;
        }
        
        // Áp dụng sắp xếp nếu có
        if (sortPrice != null && !sortPrice.isEmpty()) {
            listproduct = productDAO.sortProducts(listproduct, sortPrice);
        }
        
        totalProducts = listproduct.size();
        
        // Áp dụng phân trang cho danh sách đã lọc
        int fromIndex = offset;
        int toIndex = Math.min(fromIndex + pageSize, totalProducts);
        listproduct = fromIndex < totalProducts ? 
            listproduct.subList(fromIndex, toIndex) : 
            new ArrayList<>();
    } else {
        // Lấy tất cả sản phẩm
        listproduct = productDAO.getAllProduct();
        if (sortPrice != null && !sortPrice.isEmpty()) {
            listproduct = productDAO.sortProducts(listproduct, sortPrice);
        }
        totalProducts = listproduct.size();
        
        // Áp dụng phân trang
        int fromIndex = offset;
        int toIndex = Math.min(fromIndex + pageSize, totalProducts);
        listproduct = fromIndex < totalProducts ? 
            listproduct.subList(fromIndex, toIndex) : 
            new ArrayList<>();
    }

    // Tính tổng số trang
    int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

    // Lấy dữ liệu khác
    List<Banner> bannerList = bannerDAO.getAllBanners();
    List<CategoryAnh> categories = categoryDAO.getAllCategories();
    List<Product> topViewedProducts = productDAO.getTopViewedProducts(9);
    List<Product> newestProducts = productDAO.getNewestProducts(9);

    // Set attributes
    request.setAttribute("cbanners", bannerList);
    request.setAttribute("categories", categories);
    request.setAttribute("topViewedProducts", topViewedProducts);
    request.setAttribute("newestProducts", newestProducts);
    request.setAttribute("listproduct", listproduct);
    request.setAttribute("currentPage", page);
    request.setAttribute("totalPages", totalPages);
    request.setAttribute("pageSize", pageSize);
    request.setAttribute("query", searchQuery);
    request.setAttribute("category", categoryFilter);
    request.setAttribute("sortPrice", sortPrice);

    // Forward to JSP
    request.getRequestDispatcher("HomePage.jsp").forward(request, response);
}


    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
