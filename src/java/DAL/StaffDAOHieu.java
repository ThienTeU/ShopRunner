/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import HieuPTM.model.UserHieu;
import Model.StaffHieu;
import jakarta.servlet.annotation.WebServlet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UserDAO", urlPatterns = {"/UserDAO"})
public class StaffDAOHieu extends HieuPTM.DBContext.DBContext {
    
    public List<StaffHieu> searchStaffPage(String userName, String email, String phone, Boolean status, int offset, int size) {
        List<StaffHieu> staffList = new ArrayList<>();
        String sql = "SELECT u.user_id, u.role_id, u.user_name, u.email, u.phone_number, u.status, u.gender_id "
                   + "FROM [User] u "
                   + "WHERE u.role_id IN (3,4) "
                   + "AND (? IS NULL OR u.user_name LIKE ?) "
                   + "AND (? IS NULL OR u.email LIKE ?) "
                   + "AND (? IS NULL OR u.phone_number LIKE ?) "
                   + (status != null ? "AND u.status = ? " : "")
                   + "ORDER BY u.user_id "
                   + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int paramIndex = 1;
            ps.setObject(paramIndex++, userName != null ? "%" + userName + "%" : null);
            ps.setObject(paramIndex++, userName != null ? "%" + userName + "%" : null);
            ps.setObject(paramIndex++, email != null ? "%" + email + "%" : null);
            ps.setObject(paramIndex++, email != null ? "%" + email + "%" : null);
            ps.setObject(paramIndex++, phone != null ? "%" + phone + "%" : null);
            ps.setObject(paramIndex++, phone != null ? "%" + phone + "%" : null);

            if (status != null) {
                ps.setBoolean(paramIndex++, status);
            }

            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex++, size);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StaffHieu staff = new StaffHieu(
                        rs.getInt("user_id"),
                        rs.getInt("role_id"),
                        rs.getString("user_name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getBoolean("status")
                    );

                    staffList.add(staff);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }
    
    public List<StaffHieu> searchStaff(String userName, String email, String phone, Boolean status) {
        List<StaffHieu> staffList = new ArrayList<>();
        String sql = "SELECT u.user_id, u.role_id, u.user_name, u.email, u.phone_number, u.status, u.gender_id "
                   + "FROM [User] u "
                   + "WHERE u.role_id IN (3,4) "
                   + "AND (? IS NULL OR u.user_name LIKE ?) "
                   + "AND (? IS NULL OR u.email LIKE ?) "
                   + "AND (? IS NULL OR u.phone_number LIKE ?) "
                   + (status != null ? "AND u.status = ? " : "")
                   + "ORDER BY u.user_id ";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int paramIndex = 1;
            ps.setObject(paramIndex++, userName != null ? "%" + userName + "%" : null);
            ps.setObject(paramIndex++, userName != null ? "%" + userName + "%" : null);
            ps.setObject(paramIndex++, email != null ? "%" + email + "%" : null);
            ps.setObject(paramIndex++, email != null ? "%" + email + "%" : null);
            ps.setObject(paramIndex++, phone != null ? "%" + phone + "%" : null);
            ps.setObject(paramIndex++, phone != null ? "%" + phone + "%" : null);
            
            if (status != null) {
                ps.setBoolean(paramIndex++, status);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StaffHieu staff = new StaffHieu(
                        rs.getInt("user_id"),
                        rs.getInt("role_id"),
                        rs.getString("user_name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getBoolean("status")
                    );

                    staffList.add(staff);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public void updateStaffStatus(int id, boolean status) {
        String sql = "  update [User]\n"
                + "  set status = ?\n"
                + "  where user_id =?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public StaffHieu getStaffById(int id) {
        StaffHieu staff = null;
        String sql = "SELECT "
                + "    u.user_id, "
                + "    u.role_id, "
                + "    u.user_name, "
                + "    u.full_name, "
                + "    u.email, "
                + "    u.phone_number, "
                + "    u.status, "
                + "    u.gender_id "
                + "FROM [User] u "
                + "LEFT JOIN [Address] a ON u.user_id = a.user_id "
                + "WHERE u.role_id IN (3,4) AND u.user_id = ?"; // Sửa lại điều kiện role_id

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    staff = new StaffHieu(
                            rs.getInt("user_id"),
                            rs.getInt("role_id"),
                            rs.getString("user_name"),
                            rs.getString("email"),
                            rs.getString("phone_number"),
                            rs.getBoolean("status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staff;
    }

    public List<StaffHieu> getAllStaff() {
        List<StaffHieu> staffs = new ArrayList<>();
        String sql = "  select * from [User] where role_id in (3,4)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StaffHieu staff = new StaffHieu(
                            rs.getInt("user_id"),
                            rs.getInt("role_id"),
                            rs.getString("user_name"),
                            rs.getString("email"),
                            rs.getString("phone_number"),
                            rs.getBoolean("status")
                    );

                    staffs.add(staff);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffs;
    }

    public List<StaffHieu> getAllStaffPage(int index, int size) {
        List<StaffHieu> staffs = new ArrayList<>();
        int offset = (index - 1) * size;
        String sql = "SELECT \n"
                + "    u.user_id,\n"
                + "    u.role_id,\n"
                + "    u.user_name,\n"
                + "    u.email,\n"
                + "    u.phone_number,\n"
                + "    u.status,\n"
                + "    u.gender_id\n"
                + "FROM [User] u\n"
                + "WHERE u.role_id IN (3, 4)\n"
                + "ORDER BY u.user_id\n"
                + "OFFSET ? ROWS\n"
                + "FETCH NEXT ? ROWS ONLY;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, size);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StaffHieu staff = new StaffHieu(
                            rs.getInt("user_id"),
                            rs.getInt("role_id"),
                            rs.getString("user_name"),
                            rs.getString("email"),
                            rs.getString("phone_number"),
                            rs.getBoolean("status")
                    );

                    staffs.add(staff);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffs;
    }

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
}