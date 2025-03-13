package HieuPTM.DAO;

import HieuPTM.DBContext.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import HieuPTM.model.UserHieu;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name="UserDAO", urlPatterns={"/UserDAO"})
public class UserDAO extends DBContext {

    public ArrayList<UserHieu> getAllUsers() {
        ArrayList<UserHieu> list = new ArrayList<>();
        String sql = "SELECT * FROM [User] WHERE [status] = 1 ORDER BY roleId ASC";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new UserHieu(rs.getString(
                        "userName"),
                        rs.getString("fullName"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("email"), 
                        rs.getString("gender"), 
                        rs.getInt("roleID")));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public ArrayList<UserHieu> getAllUsersNoAdmin() {
        ArrayList<UserHieu> list = new ArrayList<>();
        String sql = "select * from [User] WHERE [status] = 1 and role_id = 2 order by role_id asc";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new UserHieu(rs.getString(
                        "userName"),
                        rs.getString("fullName"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("email"), 
                        rs.getString("gender"), 
                        rs.getInt("roleID")));
                   }
        } catch (SQLException e) {
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
            UserHieu u = new UserHieu(
                rs.getString("user_name"),
                rs.getString("full_name"),
                rs.getString("password"),
                rs.getString("phone_number"),
                rs.getString("email"),
                rs.getString("gender_id"),
                rs.getInt("role_id")
            );
            return u;
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
                rs.getString("gender_id"),
                rs.getInt("role_id")
            );
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return null;
}


    public int checkAccountAdmin(String userName) {
        String sql = "select * from [User] where [user_name]=? and [status] = 1";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, userName);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
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
                return !rs.next(); // Nếu có bản ghi tồn tại -> return false, ngược lại return true
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi kiểm tra tên đăng nhập: " + e.getMessage());
        }
        return false;
    }

    //////////////////////////////////////////
   public String registerUser(UserHieu user) {
    // Kiểm tra trùng username
    if (checkUserNameDuplicate(user.getUserName())) {
        return "Tên đăng nhập đã tồn tại!";
    }

    String sql = "INSERT INTO [User] (role_id, user_name, full_name, email, password, phone_number, status) "
               + "VALUES (?, ?, ?, ?, ?, ?, 1)"; // status mặc định 1

    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, user.getRoleID());          // role_id
        st.setString(2, user.getUserName());     // user_name
        st.setString(3, user.getFullName());     // full_name
        st.setString(4, user.getEmail());        // email
        st.setString(5, user.getPassword());    // password
        st.setString(6, user.getPhone());        // phone_number

        int rowsInserted = st.executeUpdate();
        return rowsInserted > 0 ? "Đăng ký thành công!" : "Đăng ký thất bại!";
    } catch (SQLException e) {
        return "Lỗi hệ thống: " + e.getMessage();
    }
}


    public void updateImage(String image, String userName) {
        String sql = "UPDATE [dbo].[User]\n"
                + "   SET \n"
                + "      [Image] = ?\n"
                + " WHERE userName = ? and [status] = 1";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, image);
            st.setString(2, userName);

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
///////////////////////////////////////
   public void insert(UserHieu c) {
    String sql = "INSERT INTO [User] (role_id, user_name, full_name, email, password, phone_number, status) "
               + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, c.getRoleID());                   // role_id
        st.setString(2, c.getUserName());              // user_name
        st.setString(3, c.getFullName());              // full_name
        st.setString(4, c.getEmail());                 // email
        st.setString(5, c.getPassword());              // password
        st.setString(6, c.getPhone());                 // phone_number
        st.setInt(7, 1);                               // status (mặc định là 1)
        st.executeUpdate();
    } catch (SQLException e) {
        System.out.println(e);
    }
}


    public void changePassword(UserHieu s) {
        String sql = "Update [User] set password = ? where user_name = ? and [status] = 1";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, s.getPassword());
            st.setString(2, s.getUserName());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public ArrayList<UserHieu> searchUserByName(String search) {
        ArrayList<UserHieu> list = new ArrayList<>();
        String sql = "select * from [User]\n"
                + "where user_name like ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
               list.add(new UserHieu(rs.getString(
                        "userName"),
                        rs.getString("fullName"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("email"), 
                        rs.getString("gender"), 
                        rs.getInt("roleID")));
            }
        } catch (SQLException e) {
        }
        return list;
    }
    
    public void becomeAdmin(String uname){
        String sql = "update [User] set RoleID = 1 where user_name = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, uname);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void deleteUser(String userName){
        String sql = "update [User] set [status] = 0 where user_name = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
    
    public static void main(String[] args) {
        UserDAO udao = new UserDAO();
        ArrayList<UserHieu> list = udao.searchUserByName("pt");
        for (UserHieu user : list) {
            System.out.println(user.toString());
        }
        udao.becomeAdmin("TuanAnh");
    }
}