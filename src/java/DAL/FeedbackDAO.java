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

    public List<Feedback> getAllFeedback() throws SQLException {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT * FROM Feedback";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Feedback feedback = new Feedback(
                        rs.getInt("feedback_id"),
                        rs.getInt("product_id"),
                        rs.getInt("user_id"),
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

}
