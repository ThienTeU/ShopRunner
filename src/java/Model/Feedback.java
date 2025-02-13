/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author admin
 */
public class Feedback {
    private int feedback_id;
    private int product_id;
    private int user_id;
    private String feedback_content;
    private int rating;
    private String create_at;
    private boolean status;

    public Feedback() {
    }

    public Feedback(int feedback_id, int product_id, int user_id, String feedback_content, int rating, String create_at, boolean status) {
        this.feedback_id = feedback_id;
        this.product_id = product_id;
        this.user_id = user_id;
        this.feedback_content = feedback_content;
        this.rating = rating;
        this.create_at = create_at;
        this.status = status;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
        sb.append(", user_id=").append(user_id);
        sb.append(", feedback_content=").append(feedback_content);
        sb.append(", rating=").append(rating);
        sb.append(", create_at=").append(create_at);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
    
}
