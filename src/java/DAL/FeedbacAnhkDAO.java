package DAL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.FeedbackAnh;
public class FeedbacAnhkDAO extends DBContext {

    // Lấy danh sách phản hồi theo User ID
    public List<FeedbackAnh> getFeedbackByUserId(int userId) {
        List<FeedbackAnh> feedbackList = new ArrayList<>();
        String query = "SELECT f.feedback_id, f.product_id, p.product_name, f.feedback_content, f.rating, f.created_at, f.status " +
                       "FROM Feedback f " +
                       "JOIN Product p ON f.product_id = p.product_id " +
                       "WHERE f.user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId); // Gán giá trị userId vào câu lệnh SQL
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                FeedbackAnh feedback = new FeedbackAnh(
                    rs.getInt("feedback_id"),
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getString("feedback_content"),
                    rs.getInt("rating"),
                    rs.getString("created_at"),
                    rs.getBoolean("status")
                );
                feedbackList.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return feedbackList;
    }

    // Thêm phản hồi mới
    public boolean insertFeedback(FeedbackAnh feedback) {
        String query = "INSERT INTO Feedback (product_id, user_id, feedback_content, rating, created_at, status) " +
                       "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, feedback.getProductId());
            stmt.setInt(2, feedback.getFeedbackId()); // Thay bằng User ID nếu cần
            stmt.setString(3, feedback.getFeedbackContent());
            stmt.setInt(4, feedback.getRating());
            stmt.setString(5, feedback.getCreatedAt());
            stmt.setBoolean(6, feedback.isStatus());

            return stmt.executeUpdate() > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa phản hồi theo ID
    public boolean deleteFeedbackById(int feedbackId) {
        String query = "DELETE FROM Feedback WHERE feedback_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, feedbackId);
            return stmt.executeUpdate() > 0; // Trả về true nếu xóa thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    
    
public static void main(String[] args) {
    FeedbacAnhkDAO dao = new FeedbacAnhkDAO();

    // Test 1: Lấy danh sách phản hồi theo User ID
    System.out.println("----- Test getFeedbackByUserId -----");
    int testUserId = 8; // Thay đổi ID này theo dữ liệu trong database
    List<FeedbackAnh> feedbackList = dao.getFeedbackByUserId(testUserId);
    for (FeedbackAnh feedback : feedbackList) {
        System.out.println(feedback);
    }
}

}
