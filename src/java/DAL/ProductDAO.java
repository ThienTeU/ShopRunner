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
import com.sun.jdi.connect.spi.Connection;
import java.security.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.util.Types;

/**
 *
 * @author admin
 */
public class ProductDAO extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;

    public int getTotalProducts() throws SQLException {
        String query = "SELECT COUNT(*) FROM dbo.Product";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    // Lấy danh sách sản phẩm có phân trang và sắp xếp theo yêu cầu
    public List<Product> getProductsByPageSorted(int page, int pageSize, String sortType) throws SQLException {
        List<Product> productList = new ArrayList<>();

        String orderBy = "created_at DESC";
        if ("name".equals(sortType)) {
            orderBy = "product_name ASC";
        } else if ("status".equals(sortType)) {
            orderBy = "status DESC";
        } else if("view".equals(sortType)){
            orderBy = "pv.[view] DESC";
        }

        String query = "SELECT p.product_id, p.category_id, p.product_name, p.description, "
                + "p.discount, p.status, p.thumbnail, p.created_at, "
                + "COALESCE(pv.[view], 0) AS total_views "
                + "FROM Product p "
                + "LEFT JOIN ProductView pv ON p.product_id = pv.product_id "
                + "ORDER BY "+orderBy+ " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        PreparedStatement ps = connection.prepareStatement(query);
        int offset = (page - 1) * pageSize;
        ps.setInt(1, offset);
        ps.setInt(2, pageSize);

        ResultSet rs = ps.executeQuery();
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
        return productList;
    }

    // Đếm tổng số sản phẩm theo từ khóa tìm kiếm
    public int getTotalProductsBySearch(String keyword) throws SQLException {
        String query = "SELECT COUNT(*) FROM Product WHERE product_name LIKE ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public List<Product> searchProductsByNamePage(String keyword, int page, int pageSize, String sortType) throws SQLException {
        List<Product> productList = new ArrayList<>();
        // Xác định kiểu sắp xếp an toàn
        String orderBy;
        switch (sortType) {
            case "name":
                orderBy = "product_name ASC";
                break;
            case "status":
                orderBy = "status DESC";
                break;
            case "view":
                orderBy = "total_views DESC"; // Nếu sắp xếp theo view, đổi ORDER BY
                break;
            default:
                orderBy = "created_at ASC"; // Mặc định sắp xếp theo ngày tạo
        }

        // Tạo query chung
        String query = "SELECT p.product_id, p.category_id, p.product_name, p.description, "
                + "p.discount, p.status, p.thumbnail, p.created_at, "
                + "COALESCE(pv.[view], 0) AS total_views "
                + "FROM Product p "
                + "LEFT JOIN ProductView pv ON p.product_id = pv.product_id "
                + "WHERE p.product_name LIKE ? "
                + "ORDER BY " + orderBy + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int offset = (page - 1) * pageSize;
            ps.setString(1, "%" + keyword + "%");
            ps.setInt(2, offset);
            ps.setInt(3, pageSize);

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

    // Lấy danh sách sản phẩm theo trang
    public List<Product> getProductsByPage(int page, int pageSize) throws SQLException {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM Product ORDER BY created_at DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        PreparedStatement ps = connection.prepareStatement(query);

        int offset = (page - 1) * pageSize;
        ps.setInt(1, offset);
        ps.setInt(2, pageSize);

        ResultSet rs = ps.executeQuery();
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
        return productList;
    }

    public List<ProductView> getAllProductView() {
        List<ProductView> productViews = new ArrayList<>();
        String sql = "SELECT * FROM ProductView";

        try (
                PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int viewId = rs.getInt("view_id");
                int productId = rs.getInt("product_id");
                int view = rs.getInt("view");
                String viewedAt = rs.getString("viewed_at");

                productViews.add(new ProductView(viewId, productId, view, viewedAt));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productViews;
    }

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
                + "LEFT JOIN ProductView pv ON p.product_id = pv.product_id where p.status = 0"
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
                + "WHERE p.product_id != ? and p.status=0\n"
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
        String sql = "SELECT * FROM Product WHERE category_id = ? and product_id != ? and status = 0";

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

    public List<Product> getAllProductBySort(String orderBy) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT "
                + "    p.product_id, "
                + "    p.product_name, "
                + "    p.description, "
                + "    p.discount, "
                + "    p.status, "
                + "    p.thumbnail, "
                + "    p.created_at, "
                + "    p.category_id, "
                + "    pp.price, "
                + "    pp.color_id "
                + "FROM Product p "
                + "LEFT JOIN ProductPrice pp ON p.product_id = pp.product_id "
                + "ORDER BY pp.price " + (orderBy.equalsIgnoreCase("asc") ? "ASC" : "DESC") + ";"; // Sắp xếp theo giá

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
                        rs.getString("created_at"),
                        rs.getDouble("price")
                );
                list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> getAllProduct() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT \n"
                + "    p.product_id,\n"
                + "    p.product_name,\n"
                + "    p.description,\n"
                + "    p.discount,\n"
                + "    p.status,\n"
                + "    p.thumbnail,\n"
                + "    p.created_at,\n"
                + "	p.category_id,\n"
                + "    pp.price,\n"
                + "    pp.color_id\n"
                + "FROM Product p\n"
                + "LEFT JOIN ProductPrice pp ON p.product_id = pp.product_id;";

        try (
                PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getInt("category_id"),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getInt("discount"),
                        rs.getBoolean("status"),
                        rs.getString("thumbnail"),
                        rs.getString("created_at"),
                        rs.getDouble("price")
                );
                list.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> getAllProductNam() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT \n"
                + "    p.product_id,\n"
                + "    p.product_name,\n"
                + "    p.description,\n"
                + "    p.discount,\n"
                + "    p.status,\n"
                + "    p.thumbnail,\n"
                + "    p.created_at,\n"
                + "    p.category_id,\n"
                + "    pp.price,\n"
                + "    pp.color_id\n"
                + "FROM Product p\n"
                + "LEFT JOIN ProductPrice pp ON p.product_id = pp.product_id\n"
                + "WHERE p.product_name LIKE N'%nam%';";

        try (
                PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getInt("category_id"),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getInt("discount"),
                        rs.getBoolean("status"),
                        rs.getString("thumbnail"),
                        rs.getString("created_at"),
                        rs.getDouble("price")
                );
                list.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> getAllProductNu() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT \n"
                + "    p.product_id,\n"
                + "    p.product_name,\n"
                + "    p.description,\n"
                + "    p.discount,\n"
                + "    p.status,\n"
                + "    p.thumbnail,\n"
                + "    p.created_at,\n"
                + "    p.category_id,\n"
                + "    pp.price,\n"
                + "    pp.color_id\n"
                + "FROM Product p\n"
                + "LEFT JOIN ProductPrice pp ON p.product_id = pp.product_id\n"
                + "WHERE p.product_name LIKE N'%nữ%';";

        try (
                PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getInt("category_id"),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getInt("discount"),
                        rs.getBoolean("status"),
                        rs.getString("thumbnail"),
                        rs.getString("created_at"),
                        rs.getDouble("price")
                );
                list.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> getAllProductGiay() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT \n"
                + "    p.product_id,\n"
                + "    p.product_name,\n"
                + "    p.description,\n"
                + "    p.discount,\n"
                + "    p.status,\n"
                + "    p.thumbnail,\n"
                + "    p.created_at,\n"
                + "    p.category_id,\n"
                + "    pp.price,\n"
                + "    pp.color_id\n"
                + "FROM Product p\n"
                + "LEFT JOIN ProductPrice pp ON p.product_id = pp.product_id\n"
                + "WHERE p.product_name LIKE N'%giày%';";

        try (
                PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getInt("category_id"),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getInt("discount"),
                        rs.getBoolean("status"),
                        rs.getString("thumbnail"),
                        rs.getString("created_at"),
                        rs.getDouble("price")
                );
                list.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> getAllProductOther() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT \n"
                + "    p.product_id,\n"
                + "    p.product_name,\n"
                + "    p.description,\n"
                + "    p.discount,\n"
                + "    p.status,\n"
                + "    p.thumbnail,\n"
                + "    p.created_at,\n"
                + "    p.category_id,\n"
                + "    pp.price,\n"
                + "    pp.color_id\n"
                + "FROM Product p\n"
                + "LEFT JOIN ProductPrice pp ON p.product_id = pp.product_id\n"
                + "WHERE product_name NOT LIKE N'%nam%'  \n"
                + "AND product_name NOT LIKE N'%nữ%'\n"
                + "AND product_name NOT LIKE N'%giày%';";

        try (
                PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getInt("category_id"),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getInt("discount"),
                        rs.getBoolean("status"),
                        rs.getString("thumbnail"),
                        rs.getString("created_at"),
                        rs.getDouble("price")
                );
                list.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public int getViewById(int product_id) throws SQLException {
        String query = "SELECT * FROM dbo.ProductView WHERE product_id = ?";
        ps = connection.prepareStatement(query);
        ps.setInt(1, product_id);
        rs = ps.executeQuery();
        while (rs.next()) {
            return rs.getInt("view");
        }
        return 0;
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
    // Lấy danh sách sản phẩm nổi bật (dựa vào lượt xem)

    public ArrayList<Product> getTopViewedProducts(int limit) {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT p.product_id, p.category_id, p.product_name, p.description, p.discount, p.status, p.thumbnail, p.created_at, "
                + "SUM(ISNULL(pv.[view], 0)) AS total_views, "
                + "COALESCE(MIN(pp.price), 0) AS price "
                + "FROM Product p "
                + "LEFT JOIN ProductView pv ON p.product_id = pv.product_id "
                + "LEFT JOIN ProductPrice pp ON p.product_id = pp.product_id " // Join với bảng giá
                + "GROUP BY p.product_id, p.category_id, p.product_name, p.description, p.discount, p.status, p.thumbnail, p.created_at "
                + "ORDER BY total_views DESC "
                + "OFFSET 0 ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("product_id"),
                        rs.getInt("category_id"),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getInt("discount"),
                        rs.getBoolean("status"),
                        rs.getString("thumbnail"),
                        rs.getString("created_at"),
                        rs.getInt("price")
                );
                products.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public ArrayList<Product> getNewestProducts(int limit) {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT p.product_id, p.category_id, p.product_name, p.description, p.discount, p.status, p.thumbnail, p.created_at, "
                + "COALESCE(MIN(pp.price), 0) AS price "
                + "FROM Product p "
                + "LEFT JOIN ProductPrice pp ON p.product_id = pp.product_id "
                + "GROUP BY p.product_id, p.category_id, p.product_name, p.description, p.discount, p.status, p.thumbnail, p.created_at "
                + "ORDER BY p.created_at DESC "
                + "OFFSET 0 ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("product_id"),
                        rs.getInt("category_id"),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getInt("discount"),
                        rs.getBoolean("status"),
                        rs.getString("thumbnail"),
                        rs.getString("created_at"),
                        rs.getInt("price")
                );
                products.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> searchProductsByName(String search) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE product_name LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + search + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setProduct_id(rs.getInt("product_id"));
                product.setProduct_name(rs.getString("product_name"));
                product.setThumbnail(rs.getString("thumbnail"));
                product.setCategory_id(rs.getInt("category_id"));
                product.setDiscount(rs.getInt("discount"));
                product.setDiscount(rs.getInt("discount"));
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> searchProducts(String query, String sortPrice, int offset, int limit) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT DISTINCT p.*, MIN(pp.price) as min_price "
                + "FROM Product p "
                + "LEFT JOIN ProductPrice pp ON p.product_id = pp.product_id "
                + "WHERE p.status = 0 "
                + "AND (LOWER(p.product_name) LIKE LOWER(?) OR LOWER(p.description) LIKE LOWER(?)) "
                + "GROUP BY p.product_id, p.category_id, p.product_name, p.description, "
                + "p.discount, p.status, p.thumbnail, p.created_at ";

        if (sortPrice != null) {
            sql += sortPrice.equals("asc") ? "ORDER BY min_price ASC "
                    : sortPrice.equals("desc") ? "ORDER BY min_price DESC "
                    : "ORDER BY p.created_at DESC ";
        } else {
            sql += "ORDER BY p.created_at DESC ";
        }

        sql += "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String searchPattern = "%" + query.trim() + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setInt(3, offset);
            ps.setInt(4, limit);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProduct_id(rs.getInt("product_id"));
                    product.setCategory_id(rs.getInt("category_id"));
                    product.setProduct_name(rs.getString("product_name"));
                    product.setDescription(rs.getString("description"));
                    product.setDiscount(rs.getInt("discount"));
                    product.setStatus(rs.getBoolean("status"));
                    product.setThumbnail(rs.getString("thumbnail"));
                    product.setCreated_at(rs.getString("created_at"));
                    product.setPrice(rs.getDouble("min_price"));
                    list.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalSearchResults(String query) {
        int total = 0;
        String sql = "SELECT COUNT(DISTINCT p.product_id) "
                + "FROM Product p "
                + "WHERE p.status = 0 "
                + // 0 là active trong database của bạn
                "AND (p.product_name LIKE ? OR p.description LIKE ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            String searchPattern = "%" + query + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public List<Product> sortProducts(List<Product> products, String sortOrder) {
        if (sortOrder == null || products == null) {
            return products;
        }

        Collections.sort(products, (p1, p2) -> {
            if (sortOrder.equals("asc")) {
                return Double.compare(p1.getPrice(), p2.getPrice());
            } else if (sortOrder.equals("desc")) {
                return Double.compare(p2.getPrice(), p1.getPrice());
            }
            return 0;
        });

        return products;
    }

    public List<Product> getProductsByCategory(int categoryId) {
        List<Product> products = new ArrayList<>();
        String sql = """
        WITH CategoryTree AS (
            SELECT category_id FROM Category WHERE category_id = ?
            UNION ALL
            SELECT c.category_id FROM Category c
            JOIN CategoryTree ct ON c.parent_id = ct.category_id
        )
        SELECT p.product_id, p.category_id, p.product_name, p.description, 
               p.discount, p.status, p.thumbnail, p.created_at, pp.price
        FROM Product p
        JOIN CategoryTree ct ON p.category_id = ct.category_id
        LEFT JOIN ProductPrice pp ON p.product_id = pp.product_id
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("product_id"),
                        rs.getInt("category_id"),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getInt("discount"),
                        rs.getBoolean("status"),
                        rs.getString("thumbnail"),
                        rs.getString("created_at"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
