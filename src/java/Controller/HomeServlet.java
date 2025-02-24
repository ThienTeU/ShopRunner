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

        // **Lấy tham số tìm kiếm từ request**
        String searchQuery = request.getParameter("query");
        
        String categoryFilter = request.getParameter("category");
        
           String sortPrice = request.getParameter("sortPrice");

        // **Fetch categories**
        ArrayList<Category> categoryList = categoryDAO.getAllCategory();
        ArrayList<Category> categoriesNam = categoryDAO.getAllCategoryNam();
        ArrayList<Category> categoriesNu = categoryDAO.getAllCategoryNu();

    // **Fetch banners**
        List<Banner> bannerList = bannerDAO.getAllBanners();

      // **Fetch top-viewed products**
        int topLimit = 9;
        ArrayList<Product> topViewedProducts = productDAO.getTopViewedProducts(topLimit);

    // **Fetch newest products**
        int newestLimit = 9;
        ArrayList<Product> newestProducts = productDAO.getNewestProducts(newestLimit);

    // **Cập nhật phân trang danh sách sản phẩm**
        int pageSize = 6; // Số sản phẩm trên mỗi trang
        int page = 1; // Trang mặc định

    // **Lấy tham số page từ request**
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1; // Nếu lỗi, mặc định về trang 1
            }
        }
        

    // **Kiểm tra nếu có từ khóa tìm kiếm**
        List<Product> allProducts;
if (categoryFilter != null && !categoryFilter.trim().isEmpty()) {
    switch (categoryFilter) {
        case "nam":
            allProducts = productDAO.getAllProductNam();
            break;
        case "nu":
            allProducts = productDAO.getAllProductNu();
            break;
        case "giay":
            allProducts = productDAO.getAllProductGiay();
            break;
        case "other":
            allProducts = productDAO.getAllProductOther();
            break;
        default:
            allProducts = productDAO.getAllProduct();
            break;
    }
} else {
    // Nếu không có lọc danh mục, tìm kiếm sản phẩm theo tên
    if (searchQuery != null && !searchQuery.trim().isEmpty()) {
        allProducts = productDAO.searchProductsByName(searchQuery);
    } else {
        allProducts = productDAO.getAllProduct();
    }
}

 if (sortPrice != null) {
            allProducts = productDAO.getAllProductBySort(sortPrice);
        }

        int totalProducts = allProducts.size();
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

        // **Xác định khoảng sản phẩm hiển thị**
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalProducts);
        List<Product> listproduct = allProducts.subList(fromIndex, toIndex);
        System.out.println("Số sản phẩm tìm thấy: " + listproduct.size());

        // **Kiểm tra nếu có sản phẩm để hiển thị**
        if (fromIndex < totalProducts) {
            listproduct = allProducts.subList(fromIndex, toIndex);
        } else {
            listproduct = new ArrayList<>(); // Tránh lỗi nếu truy cập trang vượt quá số lượng sản phẩm
        }

        // **Gán dữ liệu vào request**
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("categoriesNam", categoriesNam);
        request.setAttribute("categoriesNu", categoriesNu);
        request.setAttribute("cbanners", bannerList);
        request.setAttribute("topViewedProducts", topViewedProducts);
        request.setAttribute("newestProducts", newestProducts);
        request.setAttribute("listproduct", listproduct);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("query", searchQuery); 
        request.setAttribute("category", categoryFilter); 

// **Chuyển hướng đến trang JSP**
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
