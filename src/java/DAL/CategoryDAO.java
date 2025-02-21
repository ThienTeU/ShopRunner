package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.Category;
import DAO.DBcontext;

public class CategoryDAO extends DBcontext {

    private static final Logger LOGGER = Logger.getLogger(CategoryDAO.class.getName());

    public ArrayList<Category> getAllCategory() {
        ArrayList<Category> list = new ArrayList<>();
        String query = "SELECT * FROM dbo.Category"; // SQL Query

        // Ensure connection is valid
        if (connection == null) {
            LOGGER.log(Level.SEVERE, "Database connection is null.");
            return list;
        }

        // Try-with-resources to handle resource closing
        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Category(rs.getInt("category_id"), rs.getString("name"),rs.getInt("parent_id")));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving categories", e);
        }

        return list;
    }

    public ArrayList<Category> getAllCategoryNam() {
        ArrayList<Category> list = new ArrayList<>();
        String query = "SELECT * FROM category WHERE name LIKE '%nam%';"; // SQL Query

        // Ensure connection is valid
        if (connection == null) {
            LOGGER.log(Level.SEVERE, "Database connection is null.");
            return list;
        }

        // Try-with-resources to handle resource closing
        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Category(rs.getInt("category_id"), rs.getString("name"),rs.getInt("parent_id")));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving categories", e);
        }

        return list;
    }

    public ArrayList<Category> getAllCategoryNu() {
        ArrayList<Category> list = new ArrayList<>();
        String query = "SELECT * FROM category WHERE name LIKE N'%Nữ%';"; // SQL Query

        // Ensure connection is valid
        if (connection == null) {
            LOGGER.log(Level.SEVERE, "Database connection is null.");
            return list;
        }

        // Try-with-resources to handle resource closing
        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Category(rs.getInt("category_id"), rs.getString("name"),rs.getInt("parent_id")));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving categories", e);
        }

        return list;
    }

    public static void main(String[] args) {
        CategoryDAO categoryDAO = new CategoryDAO();
        ArrayList<Category> categories = categoryDAO.getAllCategory();

        if (categories.isEmpty()) {
            System.out.println("Không có danh mục nào hoặc lỗi kết nối database.");
        } else {
            System.out.println("Danh sách danh mục:");
            for (Category category : categories) {
                System.out.println("ID: " + category.getCategory_id() + " - Name: " + category.getName());
            }
        }
    }
}
