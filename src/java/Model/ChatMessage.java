package Model;

import java.sql.Timestamp;

public class ChatMessage {
    private int messageId;
    private String senderEmail;
    private String receiverEmail;
    private String messageContent;
    private Timestamp timestamp;

    public ChatMessage(int messageId, String senderEmail, String receiverEmail, String messageContent, Timestamp timestamp) {
        this.messageId = messageId;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
    }

    public int getMessageId() { return messageId; }
    public String getSenderEmail() { return senderEmail; }
    public String getReceiverEmail() { return receiverEmail; }
    public String getMessageContent() { return messageContent; }
    public Timestamp getTimestamp() { return timestamp; }

    public void setMessageId(int messageId) { this.messageId = messageId; }
    public void setSenderEmail(String senderEmail) { this.senderEmail = senderEmail; }
    public void setReceiverEmail(String receiverEmail) { this.receiverEmail = receiverEmail; }
    public void setMessageContent(String messageContent) { this.messageContent = messageContent; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
}
