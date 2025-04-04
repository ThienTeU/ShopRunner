/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAL.FeedbackDAO;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class Feedback {

    private int feedback_id;
    private int product_id;
    private String email;
    private String feedback_content;
    private int rating;
    private String create_at;
    private boolean status;
    private String reply_content;
    private String user_name;
    private String product_name;
    private int reply_id;

    public Feedback() {
    }

    public int getReply_id() {
        return reply_id;
    }

    public void setReply_id(int reply_id) {
        this.reply_id = reply_id;
    }

    public FeedbackReply getFeedbackReply() throws SQLException {
        FeedbackDAO dao = new FeedbackDAO();
        try {
            return dao.getFeedbackReplyByFeedbackId(feedback_id);
        } catch (SQLException e) {
            e.printStackTrace();  // In ra lỗi nếu có
            return null;  // Trả về null nếu xảy ra lỗi
        }
    }

    public Feedback(int feedback_id, int product_id, String email, String feedback_content, int rating, String create_at, boolean status, String reply_content, String user_name, String product_name, int reply_id) {
        this.feedback_id = feedback_id;
        this.product_id = product_id;
        this.email = email;
        this.feedback_content = feedback_content;
        this.rating = rating;
        this.create_at = create_at;
        this.status = status;
        this.reply_content = reply_content;
        this.user_name = user_name;
        this.product_name = product_name;
        this.reply_id = reply_id;
    }

    public Feedback(int feedback_id, int product_id, String email, String feedback_content, int rating, String create_at, boolean status, String reply_content, String user_name, String product_name) {
        this.feedback_id = feedback_id;
        this.product_id = product_id;
        this.email = email;
        this.feedback_content = feedback_content;
        this.rating = rating;
        this.create_at = create_at;
        this.status = status;
        this.reply_content = reply_content;
        this.user_name = user_name;
        this.product_name = product_name;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getName() {
        String name = "Bổ sung tên người dùng vào bảng user";
        return name;
    }

    public Feedback(int feedback_id, int product_id, String email, String feedback_content, int rating, String create_at, boolean status) {
        this.feedback_id = feedback_id;
        this.product_id = product_id;
        this.email = email;
        this.feedback_content = feedback_content;
        this.rating = rating;
        this.create_at = create_at;
        this.status = status;
    }

    public Feedback(int feedback_id, int product_id, String email, String feedback_content, int rating, String create_at, boolean status, int reply_id) {
        this.feedback_id = feedback_id;
        this.product_id = product_id;
        this.email = email;
        this.feedback_content = feedback_content;
        this.rating = rating;
        this.create_at = create_at;
        this.status = status;
        this.reply_id = reply_id;
    }

    public int getFeedback_id() {
        return feedback_id;
    }

    public void setFeedback_id(int feedback_id) {
        this.feedback_id = feedback_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFeedback_content() {
        return feedback_content;
    }

    public void setFeedback_content(String feedback_content) {
        this.feedback_content = feedback_content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Feedback{");
        sb.append("feedback_id=").append(feedback_id);
        sb.append(", product_id=").append(product_id);
        sb.append(", email=").append(email);
        sb.append(", feedback_content=").append(feedback_content);
        sb.append(", rating=").append(rating);
        sb.append(", create_at=").append(create_at);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }

}
