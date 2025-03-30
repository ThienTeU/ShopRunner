package HieuPTM.DAO;

import HieuPTM.DBContext.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import HieuPTM.model.UserHieu;
import Model.StaffHieu;
import NgocHieu.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.annotation.WebServlet;
import java.text.ParseException;
import java.util.List;

@WebServlet(name = "UserDAO", urlPatterns = {"/UserDAO"})
public class UserDAO extends DBContext {

    public boolean checkPhoneDuplicate(String phone) {
        String sql = "SELECT 1 FROM [User] WHERE phone_number = ? AND [status] = 1";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, phone);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kiểm tra số điện thoại: " + e);
        }
        return false;
    }
public List<StaffHieu> getAllStaffPage(int index, int size) {
        List<StaffHieu> staffs = new ArrayList<>();
        
        // Kiểm tra tham số đầu vào
        if (index < 1) index = 1;
        if (size <= 0) size = 10;
        
        int offset = (index - 1) * size;
        String sql = "SELECT \n"
                   + "    user_id, role_id, user_name, full_name, email, password, \n"
                   + "    phone_number, status, created_at, gender_id\n"
                   + "FROM [User] \n"
                   + "WHERE role_id IN (3, 4)\n"
                   + "ORDER BY user_id\n"
                   + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, size);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StaffHieu staff = new StaffHieu(
                            rs.getInt("user_id"),
                            rs.getInt("role_id"),
                            rs.getString("user_name"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("phone_number"),
                            rs.getBoolean("status"),
                            rs.getInt("gender_id"),
                            rs.getString("created_at")
                    );
                    staffs.add(staff);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffs;
    }
public List<StaffHieu> getAllStaff() {
        List<StaffHieu> staffs = new ArrayList<>();
        String sql = "SELECT user_id, role_id, user_name, full_name, email, password, phone_number, status, created_at, gender_id "
                   + "FROM [User] WHERE role_id IN (3,4)";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                StaffHieu staff = new StaffHieu(
                        rs.getInt("user_id"),
                        rs.getInt("role_id"),
                        rs.getString("user_name"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone_number"),
                        rs.getBoolean("status"),
                        rs.getInt("gender_id"),
                        rs.getString("created_at")
                );
                staffs.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffs;
    }
    public UserHieu getUserByUsername(String username) {
        UserHieu user = null;
        String query = "SELECT * FROM Users WHERE userName = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new UserHieu(
                        rs.getString("userName"),
                        rs.getString("fullName"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getInt("genderID"),
                        rs.getInt("roleID")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    

    public ArrayList<UserHieu> getAllUsers() {
        ArrayList<UserHieu> list = new ArrayList<>();
        String sql = "SELECT * FROM [User] WHERE [status] = 1 ORDER BY role_id ASC";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new UserHieu(
                        rs.getString("user_name"),
                        rs.getString("full_name"),
                        rs.getString("password"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getInt("gender_id"),
                        rs.getInt("role_id")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public ArrayList<UserHieu> getAllUsersNoAdmin() {
        ArrayList<UserHieu> list = new ArrayList<>();
        String sql = "SELECT * FROM [User] WHERE [status] = 1 AND role_id != 1 AND role_id != 2";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new UserHieu(
                        rs.getString("user_name"),
                        rs.getString("full_name"),
                        rs.getString("password"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getInt("gender_id"),
                        rs.getInt("role_id"),
                        rs.getString("address"),
                        rs.getDate("birth_date")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public UserHieu check(String username, String password) {
        String sql = "SELECT * FROM [User] WHERE user_name = ? AND password = ? AND [status] = 1";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new UserHieu(
                        rs.getString("user_name"),
                        rs.getString("full_name"),
                        rs.getString("password"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getInt("gender_id"),
                        rs.getInt("role_id")
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public UserHieu getUserByEmail(String email) {
        String sql = "SELECT * FROM [User] WHERE email = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new UserHieu(
                        rs.getString("password"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getString("created_at"),
                        rs.getInt("gender_id"),
                        rs.getInt("role_id"),
                        rs.getInt("user_id"),
                        rs.getBoolean("status"),
                        rs.getString("user_name")
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public UserHieu getUser(String username) {
        String sql = "SELECT * FROM [User] WHERE user_name = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new UserHieu(
                        rs.getString("user_name"),
                        rs.getString("full_name"),
                        rs.getString("password"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getInt("gender_id"),
                        rs.getInt("role_id")
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public int checkAccountAdmin(String userName) {
        String sql = "SELECT role_id FROM [User] WHERE user_name = ? AND [status] = 1";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, userName);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("role_id");
            }
        } catch (SQLException e) {
        }
        return 0;
    }

    public boolean checkUserNameDuplicate(String username) {
        String sql = "SELECT 1 FROM [User] WHERE user_name = ? AND [status] = 1";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, username);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kiểm tra username: " + e);
        }
        return false;
    }

    public boolean checkEmailDuplicate(String email) {
        String sql = "SELECT 1 FROM [User] WHERE email = ? AND [status] = 1";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, email);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kiểm tra email: " + e);
        }
        return false;
    }

    public String registerUser(UserHieu user) {
        if (checkUserNameDuplicate(user.getUserName())) {
            return "Tên đăng nhập đã tồn tại!";
        }
        if (checkEmailDuplicate(user.getEmail())) {
            return "Email đã tồn tại!";
        }
        if (checkPhoneDuplicate(user.getPhone())) {
            return "Số điện thoại đã tồn tại!";
        }

        String sql = "INSERT INTO [User] (role_id, user_name, full_name, email, password, phone_number, gender_id, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, 1)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, user.getRoleID());
            st.setString(2, user.getUserName());
            st.setString(3, user.getFullName());
            st.setString(4, user.getEmail());
            st.setString(5, user.getPassword());
            st.setString(6, user.getPhone());
            st.setInt(7, user.getGenderID());
            int rows = st.executeUpdate();
            return rows > 0 ? "Đăng ký thành công!" : "Đăng ký thất bại!";
        } catch (SQLException e) {
            return "Lỗi: " + e.getMessage();
        }
    }

    public void updateImage(String image, String userName) {
        String sql = "UPDATE [User] SET [image] = ? WHERE user_name = ? AND [status] = 1";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, image);
            st.setString(2, userName);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void changePassword(UserHieu user) {
        String sql = "UPDATE [User] SET password = ? WHERE user_name = ? AND [status] = 1";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, user.getPassword());
            st.setString(2, user.getUserName());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public ArrayList<UserHieu> searchUserByName(String search) {
        ArrayList<UserHieu> list = new ArrayList<>();
        String sql = "SELECT * FROM [User] WHERE user_name LIKE ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new UserHieu(
                        rs.getString("user_name"),
                        rs.getString("full_name"),
                        rs.getString("password"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getInt("gender_id"),
                        rs.getInt("role_id")
                ));
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public void deleteUser(String userName) {
        String sql = "DELETE FROM [User] WHERE user_name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void becomeStaff(String userName) {
        String sql = "UPDATE [User] SET role_id = 2 WHERE user_name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void insert(UserHieu user) {
        String sql = "INSERT INTO [User] (user_name, full_name, password, phone_number, email, gender_id, role_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getEmail());
            ps.setInt(6, user.getGenderID());
            ps.setInt(7, user.getRoleID());
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public static void main(String[] args) throws ParseException, JOSEException {
        UserDAO dao = new UserDAO();
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJOZ29jSGlldS5jb20iLCJzdWIiOiJkdW9uZ2hpZXUyOTRAZ21haWwuY29tIiwicm9sZSI6IkFkbWluIiwiZXhwIjoxNzQzMDUzMTUzLCJpYXQiOjE3NDMwNDIzNTN9.-FVxmDcEIdppdrUZU5ziCIuPHRx5lkhCn6VR1UnmIq6zqC0OtF8KmcaNdw8nkolpdDldTTPCc1gV89zr24npbQ";
        String email = AuthenticationService.getEmailFromToken(token);
        System.out.println(email);
        System.out.println(dao.getUserByEmail(email));
    }
}
