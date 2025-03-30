package Model;

import java.sql.Timestamp;

public class Contact {
    private int contactId; 
    private String fullName; 
    private String phone; 
    private String email; 
    private String city; 
    private String content; 
    private Timestamp createdAt;
    private Boolean status;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Contact(int contactId, String fullName, String phone, String email, String city, String content, Timestamp createdAt, Boolean status) {
        this.contactId = contactId;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.city = city;
        this.content = content;
        this.createdAt = createdAt;
        this.status = status;
    }

    public Contact() {
    }

    public Contact(int contactId, String fullName, String phone, String email, String city, String content, Timestamp createdAt) {
        this.contactId = contactId;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.city = city;
        this.content = content;
        this.createdAt = createdAt;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactId=" + contactId +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}