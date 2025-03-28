/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
/**
 *
 * @author Admin
 */
public class FeedbackAnh {

    private int feedbackId;
    private int productId;
    private String productName; // Tên sản phẩm
    private String feedbackContent;
    private int rating;
    private String createdAt;
    private boolean status;

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public FeedbackAnh(int feedbackId, int productId, String productName, String feedbackContent, int rating, String createdAt, boolean status) {
        this.feedbackId = feedbackId;
        this.productId = productId;
        this.productName = productName;
        this.feedbackContent = feedbackContent;
        this.rating = rating;
        this.createdAt = createdAt;
        this.status = status;
    }

    public FeedbackAnh() {
    }
    
    @Override
public String toString() {
    return "FeedbackAnh{" +
           "feedbackId=" + feedbackId +
           ", productId=" + productId +
           ", productName='" + productName + '\'' +
           ", feedbackContent='" + feedbackContent + '\'' +
           ", rating=" + rating +
           ", createdAt='" + createdAt + '\'' +
           ", status=" + status +
           '}';
}

}
