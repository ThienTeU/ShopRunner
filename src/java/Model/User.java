/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAL.UserDAO;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class User {
    private int user_id;
    private int role_id;
    private String email;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
    private String full_name;
    private String password;
    private String phone_number;
    private boolean status;
    private int gender_id;
    private String created_at;

    public User() {
    }
    
    public User(int user_id, int role_id, String email, String password, String phone_number, boolean status,int gender_id, String created_at) {
        this.user_id = user_id;
        this.role_id = role_id;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.status = status;
        this.gender_id = gender_id;
        this.created_at = created_at;
    }

    public User(String email, String password, String phone_number) {
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
    }
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
    public String getRoleById() throws SQLException{
        UserDAO dao = new UserDAO();
        return dao.getRoleById(role_id);
    }
    public int getGender_id() {
        return gender_id;
    }

    public void setGender_id(int gender_id) {
        this.gender_id = gender_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
    
    
    
}
