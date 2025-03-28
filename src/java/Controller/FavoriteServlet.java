package Controller;

import DAL.FavoriteDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "FavoriteServlet", urlPatterns = {"/favorite"})
public class FavoriteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy session của người dùng
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");

        // Kiểm tra nếu người dùng chưa đăng nhập
        if (userId == null) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"Bạn cần đăng nhập.\"}");
            return;
        }

        try {
            // Lấy thông tin từ request
            int productId = Integer.parseInt(request.getParameter("productId"));
            String action = request.getParameter("action");

            // Khởi tạo DAO
            FavoriteDAO favoriteDAO = new FavoriteDAO();
            boolean success = false;

            // Xử lý thêm hoặc xóa yêu thích
            if ("add".equals(action)) {
                success = favoriteDAO.addFavorite(userId, productId);
            } else if ("remove".equals(action)) {
                success = favoriteDAO.removeFavorite(userId, productId);
            }

            // Trả về kết quả dưới dạng JSON
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": " + success + "}");
        } catch (NumberFormatException e) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"ID sản phẩm không hợp lệ.\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"Có lỗi xảy ra. Vui lòng thử lại.\"}");
        }
    }
}
