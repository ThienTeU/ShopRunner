/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author admin
 */
public class User {
    private int user_id;
    private int role_id;
    private String email;
    private String password;
    private String phone_number;
    private String address;
    private boolean status;

    public User() {
    }

    public User(int user_id, int role_id, String email, String password, String phone_number, String address, boolean status) {
        this.user_id = user_id;
        this.role_id = role_id;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.address = address;
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        sb.append("User{");
        sb.append("user_id=").append(user_id);
        sb.append(", role_id=").append(role_id);
        sb.append(", email=").append(email);
        sb.append(", password=").append(password);
        sb.append(", phone_number=").append(phone_number);
        sb.append(", address=").append(address);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
    
    
    
}
