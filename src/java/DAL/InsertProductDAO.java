/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.ProductPrice;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class InsertProductDAO extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;

    public static void main(String[] args) throws SQLException {
        InsertProductDAO dao = new InsertProductDAO();
        //int product_id = dao.addProduct(1, "Test Name", "Test description", 0, 1, "Hi");//success

        //int productprice_id = dao.addProductPrice(product_id, 1, 0);
        //int productquantity_id = dao.addProductQuantity(productprice_id, 1, 1);
        dao.addProductImage(1, "link", 1);
    }

    //Insert Data
    public ProductPrice getProductPrice(int productPrice_id) throws SQLException {
        String sql = "SELECT product_id, color_id, price FROM dbo.ProductPrice WHERE productPrice_id = ?";
        ProductPrice productPrice = null;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productPrice_id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int product_id = rs.getInt("product_id");
                    int color_id = rs.getInt("color_id");
                    int price = rs.getInt("price");

                    productPrice = new ProductPrice(productPrice_id, product_id, color_id, price);
                }
            }
        }

        return productPrice;
    }

    public int getMaxProductId() throws SQLException {
        String sql = "SELECT MAX(product_id) FROM dbo.Product";
        int maxProductId = 0; // Mặc định nếu bảng rỗng

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                maxProductId = rs.getInt(1);
            }
        }
        return maxProductId;
    }

    public void addProductImage(int product_id, String image_url, int color_id) throws SQLException {
        String sql = "INSERT INTO dbo.ProductImage (product_id, image_url, color_id) VALUES (?, ?, ?)";
        int productImage_id = -1;

        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, product_id);
            ps.setString(2, image_url);
            ps.setInt(3, color_id);

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        productImage_id = generatedKeys.getInt(1);
                        System.out.println("✅ Thêm hình ảnh sản phẩm thành công với productImage_id = " + productImage_id);
                        saveProductImageQueryToFile(product_id, image_url, color_id);  // Ghi câu lệnh vào file
                    }
                }
            } else {
                System.out.println("⚠️ Lỗi thêm hình ảnh sản phẩm.");
            }
        }
    }

    private void saveProductImageQueryToFile(int product_id, String image_url, int color_id) {
        String query = String.format(
                "INSERT INTO dbo.ProductImage (product_id, image_url, color_id) VALUES (%d, N'%s', %d);",
                product_id, image_url.replace("'", "''"), color_id
        );

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("D:/insertQueries.txt", true), StandardCharsets.UTF_8))) {
            writer.write(query + "\n");
            writer.flush();
            System.out.println("📁 Query đã được lưu vào insert_queries.txt với UTF-8");
        } catch (IOException e) {
            System.err.println("❌ Lỗi ghi file: " + e.getMessage());
        }
    }

    public int addProductQuantity(int productPrice_id, int size_id, int quantity) throws SQLException {
        String sql = "INSERT INTO dbo.ProductQuantity (productPrice_id, size_id, quantity) VALUES (?, ?, ?)";
        int productQuantity_id = -1;

        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, productPrice_id);
            ps.setInt(2, size_id);
            ps.setInt(3, quantity);

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        productQuantity_id = generatedKeys.getInt(1);
                        System.out.println("✅ Thêm số lượng sản phẩm thành công với productQuantity_id = " + productQuantity_id);
                        saveProductQuantityQueryToFile(productPrice_id, size_id, quantity);  // Ghi câu lệnh vào file
                    }
                }
            } else {
                System.out.println("⚠️ Lỗi thêm số lượng sản phẩm.");
            }
        }

        return productQuantity_id;
    }

    private void saveProductQuantityQueryToFile(int productPrice_id, int size_id, int quantity) {
        String query = String.format(
                "INSERT INTO dbo.ProductQuantity (productPrice_id, size_id, quantity) VALUES (%d, %d, %d);",
                productPrice_id, size_id, quantity
        );

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("D:/insertQueries.txt", true), StandardCharsets.UTF_8))) {
            writer.write(query + "\n");
            writer.flush();
            System.out.println("📁 Query đã được lưu vào insert_queries.txt với UTF-8");
        } catch (IOException e) {
            System.err.println("❌ Lỗi ghi file: " + e.getMessage());
        }
    }

    public int addProductPrice(int product_id, int color_id, int price) throws SQLException {
        String sql = "INSERT INTO dbo.ProductPrice (product_id, color_id, price) VALUES (?, ?, ?)";
        int productPrice_id = -1;

        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, product_id);
            ps.setInt(2, color_id);
            ps.setInt(3, price);

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        productPrice_id = generatedKeys.getInt(1);
                        System.out.println("✅ Thêm giá sản phẩm thành công với productPrice_id = " + productPrice_id);
                        saveProductPriceQueryToFile(product_id, color_id, price);  // Ghi câu lệnh vào file
                    }
                }
            } else {
                System.out.println("⚠️ Lỗi thêm giá sản phẩm.");
            }
        }

        return productPrice_id;
    }

    private void saveProductPriceQueryToFile(int product_id, int color_id, int price) {
        String query = String.format(
                "INSERT INTO dbo.ProductPrice (product_id, color_id, price) VALUES (%d, %d, %d);",
                product_id, color_id, price
        );

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("D:/insertQueries.txt", true), StandardCharsets.UTF_8))) {
            writer.write(query + "\n");
            writer.flush();
            System.out.println("📁 Query đã được lưu vào insert_queries.txt với UTF-8");
        } catch (IOException e) {
            System.err.println("❌ Lỗi ghi file: " + e.getMessage());
        }
    }

    public int addProduct(int category_id, String product_name, String description, int discount, int status, String thumbnail) throws SQLException {
        String sql = "INSERT INTO dbo.Product (category_id, product_name, description, discount, status, thumbnail, created_at) VALUES (?, ?, ?, ?, ?, ?, DEFAULT)";
        int product_id = -1;

        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, category_id);
            ps.setString(2, product_name);
            ps.setString(3, description);
            ps.setInt(4, discount);
            ps.setInt(5, status);
            ps.setString(6, thumbnail);

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        product_id = generatedKeys.getInt(1);
                        System.out.println("✅ Thêm sản phẩm thành công với product_id = " + product_id);
                        saveProductQueryToFile(category_id, product_name, description, discount, status, thumbnail);
                    }
                }
            } else {
                System.out.println("⚠️ Lỗi thêm sản phẩm.");
            }
        }

        return product_id;
    }

    private void saveProductQueryToFile(int category_id, String product_name, String description, int discount, int status, String thumbnail) {
        String query = String.format(
                "INSERT INTO dbo.Product (category_id, product_name, description, discount, status, thumbnail, created_at) VALUES (%d, N'%s', N'%s', %d, %d, '%s', DEFAULT);",
                category_id, product_name.replace("'", "''"), description.replace("'", "''"), discount, status, thumbnail.replace("'", "''")
        );

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("D:/insertQueries.txt", true), StandardCharsets.UTF_8))) {
            writer.write(query + "\n");
            writer.flush();
            System.out.println("📁 Query đã được lưu vào insert_queries.txt với UTF-8");
        } catch (IOException e) {
            System.err.println("❌ Lỗi ghi file: " + e.getMessage());
        }
    }

}
