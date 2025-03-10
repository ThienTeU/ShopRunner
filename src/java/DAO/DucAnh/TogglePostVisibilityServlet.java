package DAO.DucAnh;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAO.DucAnh.DBcontext;

@WebServlet("/TogglePostVisibilityServlet")
public class TogglePostVisibilityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int postID = Integer.parseInt(request.getParameter("postID"));
        int Status = Integer.parseInt(request.getParameter("Status")); // Lấy trạng thái từ request

        // Đảo trạng thái: Nếu đang là 1 (hiện) thì chuyển thành 2 (ẩn), ngược lại
        int newStatus = (Status == 1) ? 2 : 1;

        try (Connection conn = new DBcontext().getConnection()) { // Sử dụng DBContext
            String sql = "UPDATE Post SET status = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, newStatus);
                ps.setInt(2, postID);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("PostListController");  // Load lại danh sách bài viết
    }
}
