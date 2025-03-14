package HieuPTM.DAO;

import HieuPTM.DBContext.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import HieuPTM.model.UserHieu;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name = "UserDAO", urlPatterns = {"/UserDAO"})
public class UserDAO extends DBContext {

    // Lấy tất cả user có status = 1
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

    // Lấy tất cả user không phải admin
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

    // Kiểm tra đăng nhập
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

    // Lấy user theo username
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

    // Kiểm tra account admin
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

    // Kiểm tra username trùng
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

    // Kiểm tra email trùng
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

    // Đăng ký user mới
    public String registerUser(UserHieu user) {
        if (checkUserNameDuplicate(user.getUserName())) {
            return "Tên đăng nhập đã tồn tại!";
        }
        if (checkEmailDuplicate(user.getEmail())) {
            return "Email đã tồn tại!";
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

    // Cập nhật ảnh user
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

    // Đổi mật khẩu
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

    // Tìm kiếm user theo tên
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

// Hàm xóa user
    public void deleteUser(String userName) {
        String sql = "DELETE FROM [User] WHERE user_name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hàm update role thành Staff
    public void becomeStaff(String userName) {
        String sql = "UPDATE [User] SET role_id = 2 WHERE user_name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        UserHieu user = new UserHieu("admin","admin","$2a$10$D1KlmGpruxif2dJyBHRz9ed.Y9UByop8SR.YZ9xLStV6iihzKcl1S","0988738872","duonghieu294@gmail.com",1,1);
        UserDAO dao = new UserDAO();
        dao.insert(user);
    }

}
