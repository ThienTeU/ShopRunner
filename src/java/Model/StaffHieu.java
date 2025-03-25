/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author tuan
 */
public class StaffHieu {
    private int userId;
    private int roleId;
    private String userName;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private boolean status;
    private int genderId;
    private String createdAt;

    public StaffHieu(int userId, int roleId, String userName, String fullName, String email, String password, String phoneNumber, boolean status, int genderId, String createdAt) {
        this.userId = userId;
        this.roleId = roleId;
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.genderId = genderId;
        this.createdAt = createdAt;
    }

    public StaffHieu(int userId, int roleId, String userName, String email, String phoneNumber, boolean status) {
        this.userId = userId;
        this.roleId = roleId;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("StaffHieu{");
        sb.append("userId=").append(userId);
        sb.append(", roleId=").append(roleId);
        sb.append(", userName=").append(userName);
        sb.append(", fullName=").append(fullName);
        sb.append(", email=").append(email);
        sb.append(", password=").append(password);
        sb.append(", phoneNumber=").append(phoneNumber);
        sb.append(", status=").append(status);
        sb.append(", genderId=").append(genderId);
        sb.append(", createdAt=").append(createdAt);
        sb.append('}');
        return sb.toString();
    }
    
}
