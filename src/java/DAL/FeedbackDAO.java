/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.Feedback;
import Model.FeedbackReply;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class FeedbackDAO extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;

    public static void main(String[] args) throws SQLException {
        FeedbackDAO dao = new FeedbackDAO();
        List<Feedback> list = dao.getAllFeedbackById(1);
        for (Feedback f : list) {
            System.out.println(f.toString());
        }
        //System.out.println(dao.checkOrderOrNot("duonghieu294@gmail.com", 1));
        //System.out.println(dao.checkFeedbackOrNot("duonghieu294@gmail.com", 1));
    }

    public boolean checkFeedbackOrNot(String email, int product_id) throws SQLException {
        String query = "SELECT * FROM dbo.Feedback WHERE email = ? AND product_id = ?";
        ps = connection.prepareStatement(query);
        ps.setString(1, email);
        ps.setInt(2, product_id);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return true;
            } else {
                System.out.println("No data found for email: " + email + " and product_id: " + product_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertFeedback(String email, int productId, String feedbackContent, int rating) {
        String query = "INSERT INTO Feedback (email, product_id, feedback_content, rating) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setInt(2, productId);
            ps.setString(3, feedbackContent);
            ps.setInt(4, rating);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String checkOrderOrNot(String email, int product_id) throws SQLException {
        String query = "SELECT * FROM dbo.Orders LEFT JOIN dbo.OrderDetails ON OrderDetails.order_id = Orders.order_id\n"
                + "WHERE Product_id = ? AND email = ? ORDER BY order_date desc";
        ps = connection.prepareStatement(query);
        ps.setInt(1, product_id);
        ps.setString(2, email);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("order_date");
            } else {
                System.out.println("No data found for email: " + email + " and product_id: " + product_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Feedback> getAllFeedbackById(int product_id) throws SQLException {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT * FROM Feedback WHERE product_id = ?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, product_id);
        try (ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Feedback feedback = new Feedback(
                        rs.getInt("feedback_id"),
                        rs.getInt("product_id"),
                        rs.getString("email"),
                        rs.getString("feedback_content"),
                        rs.getInt("rating"),
                        rs.getString("created_at"),
                        rs.getBoolean("status")
                );
                list.add(feedback);
            }
        }
        return list;
    }

    public List<Feedback> getAllFeedback() throws SQLException {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT * FROM Feedback";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Feedback feedback = new Feedback(
                        rs.getInt("feedback_id"),
                        rs.getInt("product_id"),
                        rs.getString("email"),
                        rs.getString("feedback_content"),
                        rs.getInt("rating"),
                        rs.getString("created_at"),
                        rs.getBoolean("status")
                );
                list.add(feedback);
            }
        }
        return list;
    }

    public List<FeedbackReply> getAllFeedbackReplies() throws SQLException {
        List<FeedbackReply> list = new ArrayList<>();
        String sql = "SELECT * FROM FeedbackReply";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                FeedbackReply feedbackReply = new FeedbackReply(
                        rs.getInt("reply_id"),
                        rs.getInt("feedback_id"),
                        rs.getString("reply_content")
                );
                list.add(feedbackReply);
            }
        }
        return list;
    }

    public int getTotalFeedbacksByUser(int userId) {
        String sql = "SELECT COUNT(*) AS total_feedbacks FROM Feedback WHERE user_id = ?";
        int totalFeedbacks = 0;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Gán giá trị userId vào câu truy vấn
            ps.setInt(1, userId);

            // Thực thi câu truy vấn và xử lý kết quả
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalFeedbacks = rs.getInt("total_feedbacks"); // Lấy giá trị từ cột "total_feedbacks"
                }
            }
        } catch (SQLException e) {
            // Ghi log lỗi nếu có ngoại lệ xảy ra
            System.err.println("Error while fetching total feedbacks: " + e.getMessage());
        }

        return totalFeedbacks; // Trả về tổng số lượt đánh giá
    }

}
