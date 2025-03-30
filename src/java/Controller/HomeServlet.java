package Controller;

import DAL.BannerDAO;
import DAL.CategoryDAO;
import DAL.ProductDAO;
import DAL.PostDAO; // Thêm PostDAO
import Model.Banner;
import Model.Category;
import Model.CategoryAnh;
import Model.Product;
import Model.Post; // Thêm Model Post
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

@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CategoryDAO categoryDAO = new CategoryDAO();
        BannerDAO bannerDAO = new BannerDAO();
        ProductDAO productDAO = new ProductDAO();
        PostDAO postDAO = new PostDAO(); 

        String searchQuery = request.getParameter("query");
        String categoryFilter = request.getParameter("category");
        String sortPrice = request.getParameter("sortPrice");
        
        int pageSize = 12;
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
            page = 1;
        }
        int offset = (page - 1) * pageSize;

        List<Product> listproduct;
        int totalProducts;

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            listproduct = productDAO.searchProducts(searchQuery, sortPrice, offset, pageSize);
            totalProducts = productDAO.getTotalSearchResults(searchQuery);
        } else if (categoryFilter != null && !categoryFilter.trim().isEmpty()) {
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
            
            if (sortPrice != null && !sortPrice.isEmpty()) {
                listproduct = productDAO.sortProducts(listproduct, sortPrice);
            }
            
            totalProducts = listproduct.size();
            
            int fromIndex = offset;
            int toIndex = Math.min(fromIndex + pageSize, totalProducts);
            listproduct = fromIndex < totalProducts ? 
                listproduct.subList(fromIndex, toIndex) : 
                new ArrayList<>();
        } else {
            listproduct = productDAO.getAllProduct();
            if (sortPrice != null && !sortPrice.isEmpty()) {
                listproduct = productDAO.sortProducts(listproduct, sortPrice);
            }
            totalProducts = listproduct.size();
            
            int fromIndex = offset;
            int toIndex = Math.min(fromIndex + pageSize, totalProducts);
            listproduct = fromIndex < totalProducts ? 
                listproduct.subList(fromIndex, toIndex) : 
                new ArrayList<>();
        }

        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

        List<Banner> bannerList = bannerDAO.getAllBanners();
        List<CategoryAnh> categories = categoryDAO.getAllCategories();
        List<Product> topViewedProducts = productDAO.getTopViewedProducts(9);
        List<Product> newestProducts = productDAO.getNewestProducts(9);

        List<Post> latestPosts = postDAO.getLatestPosts(3); // Lấy 3 bài viết mới nhất
        List<Post> popularPosts = postDAO.getPopularPosts(3); // Lấy 3 bài viết phổ biến nhất

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

        request.setAttribute("latestPosts", latestPosts);
        request.setAttribute("popularPosts", popularPosts);

        request.getRequestDispatcher("HomePage.jsp").forward(request, response);
    }

}
