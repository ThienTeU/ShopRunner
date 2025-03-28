package DAL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.FeedbackAnh;
public class FeedbacAnhkDAO extends DBContext {


    
    
        // Lấy danh sách phản hồi theo Email
    public List<FeedbackAnh> getFeedbackByEmail(String email) {
        List<FeedbackAnh> feedbackList = new ArrayList<>();
        String query = "SELECT f.feedback_id, f.product_id, p.product_name, f.feedback_content, f.rating, f.created_at, f.status " +
                       "FROM Feedback f " +
                       "JOIN Product p ON f.product_id = p.product_id " +
                       "WHERE f.email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email); // Gán giá trị email vào câu lệnh SQL
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
    
    
        public int getTotalFeedbackByEmail(String email) {
        String query = "SELECT COUNT(*) AS total_feedback " +
                       "FROM Feedback " +
                       "WHERE email = ?";
        int totalFeedback = 0;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email); // Gán giá trị email vào câu lệnh SQL
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                totalFeedback = rs.getInt("total_feedback");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalFeedback;
    }
    
    

    public static void main(String[] args) {
        FeedbacAnhkDAO dao = new FeedbacAnhkDAO();

        // Test: Đếm tổng số phản hồi theo Email
        System.out.println("----- Test getTotalFeedbackByEmail -----");
        String testEmail = "123@gmail.com"; // Thay đổi email này theo dữ liệu trong database
        int totalFeedback = dao.getTotalFeedbackByEmail(testEmail);
        System.out.println("Total feedback for email " + testEmail + ": " + totalFeedback);
    }

}
