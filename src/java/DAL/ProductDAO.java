/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.Category;
import Model.Color;
import Model.Product;
import Model.ProductImage;
import Model.ProductPrice;
import Model.ProductQuantity;
import Model.ProductView;
import Model.Size;
import java.security.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.util.Types;

/**
 *
 * @author admin
 */
public class ProductDAO extends DBContext {

    public static void main(String[] args) throws SQLException {
        ProductDAO dao = new ProductDAO();
        ProductQuantity pp = dao.getProductQuantityById(10);
        System.out.println(pp.toString());
    }

    PreparedStatement ps = null;
    ResultSet rs = null;

    public int getProductCountByCategory(int categoryId) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM Product WHERE category_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        return 0; // Trả về 0 nếu không có sản phẩm nào
    }

    public List<Product> getMostViewItems() throws SQLException {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT p.product_id, p.category_id, p.product_name, p.description, "
                + "p.discount, p.status, p.thumbnail, p.created_at, "
                + "COALESCE(pv.[view], 0) AS total_views "
                + "FROM Product p "
                + "LEFT JOIN ProductView pv ON p.product_id = pv.product_id "
                + "ORDER BY pv.[view] DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getInt("category_id"),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getInt("discount"),
                        rs.getBoolean("status"),
                        rs.getString("thumbnail"),
                        rs.getString("created_at")
                );
                productList.add(product);
            }
        }
        return productList;
    }

    public List<Product> getRecentlyItem(int product_id) throws SQLException {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT p.product_id, p.category_id, p.product_name, p.description, \n"
                + "       p.discount, p.status, p.thumbnail, p.created_at, \n"
                + "       COALESCE(pv.[view], 0) AS total_views \n"
                + "FROM Product p \n"
                + "LEFT JOIN ProductView pv ON p.product_id = pv.product_id \n"
                + "WHERE p.product_id != ?\n"
                + "ORDER BY pv.viewed_at DESC;";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, product_id); // Gán giá trị product_id cần loại bỏ

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product(
                            rs.getInt("product_id"),
                            rs.getInt("category_id"),
                            rs.getString("product_name"),
                            rs.getString("description"),
                            rs.getInt("discount"),
                            rs.getBoolean("status"),
                            rs.getString("thumbnail"),
                            rs.getString("created_at")
                    );
                    productList.add(product);
                }
            }
        }
        return productList;
    }

    public List<Product> getProductsByCategory(int categoryId, int productId) throws SQLException {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE category_id = ? and product_id != ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ps.setInt(2, productId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product(
                            rs.getInt("product_id"),
                            rs.getInt("category_id"),
                            rs.getString("product_name"),
                            rs.getString("description"),
                            rs.getInt("discount"),
                            rs.getBoolean("status"),
                            rs.getString("thumbnail"),
                            rs.getString("created_at")
                    );
                    productList.add(product);
                }
            }
        }
        return productList;
    }

    public void updateProductView(int product_id) throws SQLException {
        String sql = "UPDATE ProductView SET [view] = [view] + 1, viewed_at = GETDATE() WHERE product_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, product_id); // Gán giá trị view_id vào câu lệnh SQL
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("✅ Cập nhật thành công prduct_id = " + product_id);
            } else {
                System.out.println("⚠️ Không tìm thấy product_id = " + product_id);
            }
        }
    }

    public ProductQuantity getProductQuantityById(int productquantityId) throws SQLException {
        ProductQuantity productQuantity = null;
        String query = "SELECT * FROM ProductQuantity WHERE productquantity_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, productquantityId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                productQuantity = new ProductQuantity();
                productQuantity.setProductQuantity_id(rs.getInt("productquantity_id"));
                productQuantity.setProductPrice_id(rs.getInt("productprice_id"));
                productQuantity.setSize_id(rs.getInt("size_id"));
                productQuantity.setQuantity(rs.getInt("quantity"));
            }
        }
        return productQuantity;
    }

    public ProductPrice getProductPriceById(int productpriceId) throws SQLException {
        ProductPrice productPrice = null;
        String query = "SELECT * FROM ProductPrice WHERE productprice_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, productpriceId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                productPrice = new ProductPrice();
                productPrice.setProductPrice_id(rs.getInt("productprice_id"));
                productPrice.setProduct_id(rs.getInt("product_id"));
                productPrice.setColor_id(rs.getInt("color_id"));
                productPrice.setPrice(rs.getDouble("price"));
            }
        }
        return productPrice;
    }

    public Product getProductById(int productId) throws SQLException {
        String sql = "SELECT * FROM Product WHERE product_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt("product_id"),
                            rs.getInt("category_id"),
                            rs.getString("product_name"),
                            rs.getString("description"),
                            rs.getInt("discount"),
                            rs.getBoolean("status"),
                            rs.getString("thumbnail"),
                            rs.getString("created_at")
                    );
                }
            }
        }
        return null; // Không tìm thấy sản phẩm
    }

    public List<ProductImage> getProductImagesByProductIdAndColorId(int productId, int colorId) throws SQLException {
        List<ProductImage> list = new ArrayList<>();
        String sql = "SELECT * FROM ProductImage WHERE product_id = ? AND color_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.setInt(2, colorId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductImage productImage = new ProductImage(
                            rs.getInt("image_id"),
                            rs.getInt("product_id"),
                            rs.getString("image_url"),
                            rs.getInt("color_id")
                    );
                    list.add(productImage);
                }
            }
        }
        return list;
    }

    public List<ProductQuantity> getProductQuantitiesByProductPriceId(int productPriceId) throws SQLException {
        List<ProductQuantity> list = new ArrayList<>();
        String sql = "SELECT * FROM ProductQuantity WHERE ProductPrice_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productPriceId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductQuantity productQuantity = new ProductQuantity(
                            rs.getInt("ProductQuantity_id"),
                            rs.getInt("ProductPrice_id"),
                            rs.getInt("size_id"),
                            rs.getInt("quantity")
                    );
                    list.add(productQuantity);
                }
            }
        }
        return list;
    }

    public List<ProductPrice> getProductPricesByProductId(int productId) throws SQLException {
        List<ProductPrice> list = new ArrayList<>();
        String sql = "SELECT * FROM ProductPrice WHERE product_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductPrice productPrice = new ProductPrice(
                            rs.getInt("ProductPrice_id"),
                            rs.getInt("product_id"),
                            rs.getInt("color_id"),
                            rs.getInt("price")
                    );
                    list.add(productPrice);
                }
            }
        }
        return list;
    }

    public List<Product> getAllProduct() throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getInt("category_id"),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getInt("discount"),
                        rs.getBoolean("status"),
                        rs.getString("thumbnail"),
                        rs.getString("created_at")
                );
                list.add(product);
            }
        }
        return list;
    }

    public List<Category> getAllCategories() throws SQLException {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM Category";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Category category = new Category(
                        rs.getInt("category_id"),
                        rs.getString("name"),
                        rs.getInt("parent_id")
                );
                list.add(category);
            }
        }
        return list;
    }

    public List<Size> getAllSizes() throws SQLException {
        List<Size> list = new ArrayList<>();
        String sql = "SELECT * FROM Size";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Size size = new Size(
                        rs.getInt("size_id"),
                        rs.getString("size")
                );
                list.add(size);
            }
        }
        return list;
    }

    public List<Color> getAllColors() throws SQLException {
        List<Color> list = new ArrayList<>();
        String sql = "SELECT * FROM Color";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Color color = new Color(
                        rs.getInt("color_id"),
                        rs.getString("color")
                );
                list.add(color);
            }
        }
        return list;
    }

    public List<ProductImage> getAllProductImages() throws SQLException {
        List<ProductImage> list = new ArrayList<>();
        String sql = "SELECT * FROM ProductImage";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ProductImage productImage = new ProductImage(
                        rs.getInt("image_id"),
                        rs.getInt("product_id"),
                        rs.getString("image_url"),
                        rs.getInt("color_id")
                );
                list.add(productImage);
            }
        }
        return list;
    }

    public List<ProductPrice> getAllUniqueProductPrices() throws SQLException {
        List<ProductPrice> list = new ArrayList<>();
        Set<Integer> seenProductIds = new HashSet<>(); // Dùng để lưu product_id đã thấy

        String sql = "SELECT * FROM ProductPrice";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                if (!seenProductIds.contains(productId)) { // Nếu chưa có product_id trong danh sách
                    ProductPrice productPrice = new ProductPrice(
                            rs.getInt("ProductPrice_id"),
                            productId,
                            rs.getInt("color_id"),
                            rs.getInt("price")
                    );
                    list.add(productPrice);
                    seenProductIds.add(productId); // Đánh dấu đã thấy product_id này
                }
            }
        }
        return list;
    }

    public List<ProductPrice> getAllProductPrices() throws SQLException {
        List<ProductPrice> list = new ArrayList<>();
        String sql = "SELECT * FROM ProductPrice";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ProductPrice productPrice = new ProductPrice(
                        rs.getInt("ProductPrice_id"),
                        rs.getInt("product_id"),
                        rs.getInt("color_id"),
                        rs.getInt("price")
                );
                list.add(productPrice);
            }
        }
        return list;
    }

    public List<ProductQuantity> getAllProductQuantities() throws SQLException {
        List<ProductQuantity> list = new ArrayList<>();
        String sql = "SELECT * FROM ProductQuantity";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ProductQuantity productQuantity = new ProductQuantity(
                        rs.getInt("ProductQuantity_id"),
                        rs.getInt("ProductPrice_id"),
                        rs.getInt("size_id"),
                        rs.getInt("quantity")
                );
                list.add(productQuantity);
            }
        }
        return list;
    }

}
