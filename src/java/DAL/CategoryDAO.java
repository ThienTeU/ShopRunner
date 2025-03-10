package DAL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.CategoryAnh;
import Model.Product;

public class CategoryDAO {

    private static final Logger LOGGER = Logger.getLogger(CategoryDAO.class.getName());

    private static final String JDBC_URL = "jdbc:sqlserver://localhost:1433;databaseName=RunnerShop;encrypt=false";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "sa";

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }

    public List<CategoryAnh> getAllCategory() {
        List<CategoryAnh> categories = new ArrayList<>();
        String sql = "SELECT category_id, name, parent_id FROM Category";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("category_id");
                String name = rs.getString("name");

                Integer parentId = (rs.getObject("parent_id") != null) ? rs.getInt("parent_id") : null;

                // Sử dụng constructor không có categoryPath
                categories.add(new CategoryAnh(id, name, parentId));
            }
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi truy vấn danh mục", e);
        }

        return categories;
    }

    public List<CategoryAnh> getAllCategories() {
        List<CategoryAnh> categories = new ArrayList<>();

        String sql = """
            WITH CategoryTree AS (
                SELECT category_id, name, parent_id, CAST(name AS NVARCHAR(255)) AS category_path
                FROM Category
                WHERE parent_id IS NULL
                
                UNION ALL

                SELECT c.category_id, c.name, c.parent_id, 
                       CAST(ct.category_path + ' > ' + c.name AS NVARCHAR(255))
                FROM Category c
                JOIN CategoryTree ct ON c.parent_id = ct.category_id
            )
            SELECT * FROM CategoryTree ORDER BY category_path;
            """;

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("category_id");
                String name = rs.getString("name");

                Integer parentId = (rs.getObject("parent_id") != null) ? rs.getInt("parent_id") : null;
                String categoryPath = rs.getString("category_path");

                // Sử dụng constructor đầy đủ với categoryPath
                categories.add(new CategoryAnh(id, name, parentId, categoryPath));
            }
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi truy vấn danh mục theo cấu trúc cây", e);
        }

        return categories;
    }

// Method to search categories by name
    public List<CategoryAnh> searchCategoriesByName(String searchTerm) {
        List<CategoryAnh> categories = new ArrayList<>();
        String sql = "SELECT category_id, name, parent_id FROM Category WHERE name LIKE ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + searchTerm + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("category_id");
                String name = rs.getString("name");
                Integer parentId = (rs.getObject("parent_id") != null) ? rs.getInt("parent_id") : null;

                categories.add(new CategoryAnh(id, name, parentId));
            }
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi truy vấn danh mục theo tên", e);
        }

        return categories;
    }

    public static void main(String[] args) {
        CategoryDAO categoryDAO = new CategoryDAO();

        List<CategoryAnh> categories = categoryDAO.getAllCategory();
        if (categories.isEmpty()) {
            System.out.println("❌ Không có danh mục nào hoặc lỗi kết nối database.");
        } else {
            System.out.println("✅ Danh sách danh mục:");
            for (CategoryAnh category : categories) {
                System.out.println("ID: " + category.getId()
                        + " - Name: " + category.getName()
                        + " - Parent ID: " + (category.getParentId() != null ? category.getParentId() : "NULL"));
            }
        }

        List<CategoryAnh> categoryTree = categoryDAO.getAllCategories();
        if (categoryTree.isEmpty()) {
            System.out.println("❌ Không có danh mục nào theo cấu trúc cây.");
        } else {
            System.out.println("✅ Danh sách danh mục theo cấu trúc cây:");
            for (CategoryAnh category : categoryTree) {
                System.out.println("ID: " + category.getId() + " - Path: " + category.getCategoryPath());
            }
        }
    }
}
