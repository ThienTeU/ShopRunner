/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.Role;
import Model.User;
import Model.UserTuan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author admin
 */
public class UserDAO extends DBContext {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    PreparedStatement ps = null;
    ResultSet rs = null;

    public static void main(String[] args) throws SQLException {
        UserDAO dao = new UserDAO();
//        User user = new User("admin@gmail.com", "123456", "0399488243");
//        dao.addUser(user);
//        System.out.println(dao.getRoleById(1));
        System.out.println(dao.getUserByUsername("admin"));
    }

    public void addUser(User user) throws SQLException {

        String query = "INSERT INTO dbo.[User] (role_id,email,password,phone_number,status,gender_id,created_at)\n"
                + "VALUES (2,?,?,?,DEFAULT,?,DEFAULT)";
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        ps = connection.prepareStatement(query);
        ps.setString(1, user.getEmail());
        ps.setString(2, hashedPassword);
        ps.setString(3, user.getPhone_number());
        ps.setInt(4,1);

        ps.executeUpdate();
    }
    
    public User getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM dbo.[USER] WHERE user_name = ?";
        User user = null;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username); // 
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            rs.getInt("user_id"),
                            rs.getInt("role_id"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("phone_number"),
                            rs.getBoolean("status"),
                            rs.getInt("gender_id"),
                            rs.getString("created_at")
                    );
                }
            }
        }
        return user;
    }

    public User getUserByEmail(String email) throws SQLException {
        String query = "SELECT * FROM dbo.[USER] WHERE email = ?";
        User user = null;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email); // ✅ Set giá trị cho tham số `?`
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            rs.getInt("user_id"),
                            rs.getInt("role_id"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("phone_number"),
                            rs.getBoolean("status"),
                            rs.getInt("gender_id"),
                            rs.getString("created_at")
                    );
                }
            }
        }
        return user;
    }
    
    public String getRoleById(int role_id) throws SQLException{
        String query = "SELECT role_name FROM dbo.Role WHERE role_id = ?";
        ps = connection.prepareStatement(query);
        ps.setInt(1, role_id);
        rs = ps.executeQuery();
        String role_name = null;
        while(rs.next()){
            role_name =  rs.getString(1);
        }
        return role_name;
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM User";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User user = new User(
                        rs.getInt("user_id"),
                        rs.getInt("role_id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone_number"),
                        rs.getBoolean("status"),
                        rs.getInt("gender_id"),
                        rs.getString("created_at")
                );
                list.add(user);
            }
        }
        return list;
    }
    
    
    
    public List<UserTuan> getInforUserById(int id) {
        List<UserTuan> list = new ArrayList<>();
        String sql = "SELECT * FROM [RunnerShop].[dbo].[User] WHERE user_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Sử dụng constructor đầy đủ với tất cả các tham số
                    UserTuan user = new UserTuan(
                        rs.getInt("user_id"),           // userId
                        rs.getInt("role_id"),           // roleId
                        rs.getString("user_name"),      // userName
                        rs.getString("full_name"),      // fullName
                        rs.getString("email"),          // email
                        rs.getString("password"),       // password
                        rs.getString("phone_number"),   // phoneNumber
                        rs.getBoolean("status"),        // status
                        rs.getInt("gender_id"),         // genderId
                        rs.getString("created_at"),     // createdAt
                        rs.getString("img_user"),       // imgUser
                        null                            // addresses (hiện tại không có thông tin địa chỉ)
                    );
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public boolean updateUserInfo(int userId, String email, String phoneNumber, String fullName) {
        boolean updated = false;
        String sql = "UPDATE [RunnerShop].[dbo].[User] SET email = ?, phone_number = ?, full_name = ? WHERE user_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, phoneNumber);
            ps.setString(3, fullName);
            ps.setInt(4, userId);

            int rowsAffected = ps.executeUpdate();  

            if (rowsAffected > 0) {
                updated = true;  
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updated;
    }
}
