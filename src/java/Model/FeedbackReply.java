/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author admin
 */
public class FeedbackReply {
    private int reply_id;
    private int feedback_id;
    private String reply_content;

    public FeedbackReply() {
    }

    public FeedbackReply(int reply_id, int feedback_id, String reply_content) {
        this.reply_id = reply_id;
        this.feedback_id = feedback_id;
        this.reply_content = reply_content;
    }

    public int getReply_id() {
        return reply_id;
    }

    public void setReply_id(int reply_id) {
        this.reply_id = reply_id;
    }

    public int getFeedback_id() {
        return feedback_id;
    }

    public void setFeedback_id(int feedback_id) {
        this.feedback_id = feedback_id;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FeedbackReply{");
        sb.append("reply_id=").append(reply_id);
        sb.append(", feedback_id=").append(feedback_id);
        sb.append(", reply_content=").append(reply_content);
        sb.append('}');
        return sb.toString();
    }
    
}
