package HieuPTM.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserHieu {

    private String userName, fullName, password, phone, email, address, created_at;
    private int genderID, roleID, userID;
    private Date birthDate;
    private boolean status;

    public UserHieu(String password, String phone, String email, String created_at, int genderID, int roleID, int userID, boolean status, String userName) {
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.created_at = created_at;
        this.genderID = genderID;
        this.roleID = roleID;
        this.userID = userID;
        this.status = status;
        this.userName = userName;
    }

    public UserHieu() {
    }

    public UserHieu(String userName, String fullName, String password, String phone, String email, int genderID, int roleID) {
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.genderID = genderID;
        this.roleID = roleID;
    }

    public String getCreated_at() {
        return created_at;
    }

    public int getUserID() {
        return userID;
    }

    public boolean isStatus() {
        return status;
    }

    public boolean isValidUser() {
        return userName != null && !userName.isEmpty()
                && password != null && !password.isEmpty();
    }

    public UserHieu(String userName, String fullName, String password, String phone, String email, int genderID, int roleID, String address, Date birthDate) {
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.genderID = genderID;
        this.roleID = roleID;
        this.address = address;
        this.birthDate = birthDate;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getGenderID() {
        return genderID;
    }

    public void setGenderID(int genderID) {
        this.genderID = genderID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String formatBirthDate() {
        if (birthDate != null) {
            return new SimpleDateFormat("dd/MM/yyyy").format(birthDate);
        }
        return "";
    }
}
