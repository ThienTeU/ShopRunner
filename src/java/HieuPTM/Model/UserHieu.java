package HieuPTM.model;

import jakarta.servlet.annotation.WebServlet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@WebServlet(name="User", urlPatterns={"/User"})
public class UserHieu {
    
private String userName, fullName, password, phone, email, gender;
    private int roleID;
    
    public UserHieu() {
    }

    public UserHieu(String userName, String fullName, String password, String phone, String email, String gender, int roleID) {
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.roleID = roleID;
    }
    
   

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User{");
        sb.append("userName=").append(userName);
        sb.append(", fullName=").append(fullName);
        sb.append(", password=").append(password);
        sb.append(", phone=").append(phone);
        sb.append(", email=").append(email);
        sb.append(", gender=").append(gender);
        sb.append(", roleID=").append(roleID);
        sb.append('}');
        return sb.toString();
    }

   
}
