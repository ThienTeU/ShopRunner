package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBcontext {
    public Connection connection;

    // Constructor: Mở kết nối đến cơ sở dữ liệu
    public DBcontext() {
        try {
            String username = "sa";
            String password = "sa";
            String url = "jdbc:sqlserver://localhost:1433;databaseName=RunnerShop";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Kết nối đến cơ sở dữ liệu thành công.");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("❌ Lỗi kết nối: " + ex.getMessage());
        }
    }

    // Trả về kết nối hiện tại
    public Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) {
        DBcontext db = new DBcontext();  // Tạo đối tượng DBcontext và thử kết nối
        if (db.getConnection() != null) {
            System.out.println("✅ Kết nối thành công!");
        } else {
            System.err.println("❌ Không thể kết nối đến cơ sở dữ liệu.");
        }
    }
}
