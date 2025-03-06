package Model;

import java.util.List;

public class UserTuan {
    private int userId;
    private int roleId;
    private String userName;
    private String email;
    private String password;
    private String phoneNumber;
    private boolean status;
    private int genderId;
    private List<AddressTuan> addresses; 

    public UserTuan() {
    }

    public UserTuan(int userId, int roleId, String userName, String email, String password, String phoneNumber, boolean status, int genderId, List<AddressTuan> addresses) {
        this.userId = userId;
        this.roleId = roleId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.genderId = genderId;
        this.addresses = addresses;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
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

   
    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

    public List<AddressTuan> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressTuan> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UserTuan{");
        sb.append("userId=").append(userId);
        sb.append(", roleId=").append(roleId);
        sb.append(", userName=").append(userName);
        sb.append(", email=").append(email);
        sb.append(", password=").append(password);
        sb.append(", phoneNumber=").append(phoneNumber);
        sb.append(", status=").append(status);
        sb.append(", genderId=").append(genderId);
        sb.append(", addresses=").append(addresses);
        sb.append('}');
        return sb.toString();
    }

}
