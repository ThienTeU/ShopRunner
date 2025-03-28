package Model;

import java.sql.Timestamp;

public class Contact {
    private int contactId; // ID của liên hệ
    private String fullName; // Tên đầy đủ
    private String phone; // Số điện thoại
    private String email; // Email
    private String city; // Thành phố
    private String content; // Nội dung liên hệ
    private Timestamp createdAt; // Thời gian tạo (nếu có)

    // Constructor không tham số
    public Contact() {
    }

    // Constructor đầy đủ tham số
    public Contact(int contactId, String fullName, String phone, String email, String city, String content, Timestamp createdAt) {
        this.contactId = contactId;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.city = city;
        this.content = content;
        this.createdAt = createdAt;
    }

    // Getter và Setter cho thuộc tính contactId
    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    // Getter và Setter cho thuộc tính fullName
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    // Getter và Setter cho thuộc tính phone
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Getter và Setter cho thuộc tính email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter và Setter cho thuộc tính city
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    // Getter và Setter cho thuộc tính content
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // Getter và Setter cho thuộc tính createdAt
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // Phương thức toString để hiển thị thông tin đối tượng
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
