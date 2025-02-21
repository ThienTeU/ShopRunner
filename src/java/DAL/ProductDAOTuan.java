/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

/**
 *
 * @author tuan
 */
import Model.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author admin
 */
public class ProductDAOTuan extends DBContext {
    
    public List<ProductTuan> getProductsByPriceRange(int min, int max) {
        List<ProductTuan> products = new ArrayList<>();
       String sql = "SELECT p.*, ISNULL(AVG(f.rating), 0) AS rating, MIN(pp.price) AS price FROM Product p "
           + "LEFT JOIN Feedback f ON p.product_id = f.product_id "
           + "LEFT JOIN ProductPrice pp ON p.product_id = pp.product_id "
           + "WHERE pp.price BETWEEN ? AND ? " // Thêm điều kiện lọc giá
           + "GROUP BY p.product_id, p.category_id, p.product_name, p.description, "
           + "p.discount, p.status, p.thumbnail, p.created_at";


         try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, min);
            ps.setInt(2, max);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductTuan product = extractProduct(rs);
                    product.setColors(getColorsByProductId(product.getProductId()));
                    product.setPrices(getPricesByProductId(product.getProductId()));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    public List<ProductTuan> getProducts(String key, String dateSort, String rateSort, String priceSort, int index, int pageSize) {
    List<ProductTuan> products = new ArrayList<>();
    StringBuilder sql = new StringBuilder(
            "WITH RecursiveCategory AS ( "
            + "    SELECT category_id FROM Category WHERE category_id IN (1, 2) "
            + "    UNION ALL "
            + "    SELECT c.category_id FROM Category c INNER JOIN RecursiveCategory rc ON c.parent_id = rc.category_id "
            + ") "
            + "SELECT p.*, ISNULL(avgFeedback.rating, 0) AS rating, pp.price "
            + "FROM Product p "
            + "LEFT JOIN ( "
            + "    SELECT product_id, AVG(rating) AS rating FROM Feedback GROUP BY product_id "
            + ") avgFeedback ON p.product_id = avgFeedback.product_id "
            + "LEFT JOIN ( "
            + "    SELECT product_id, MIN(price) AS price FROM ProductPrice GROUP BY product_id "
            + ") pp ON p.product_id = pp.product_id "
            + "WHERE p.category_id IN (SELECT category_id FROM RecursiveCategory) "
    );

    if (key != null && !key.isEmpty()) {
        sql.append(" AND p.product_name LIKE ? COLLATE Latin1_General_CI_AI ");
    }

    StringBuilder orderByClause = new StringBuilder();
    if (dateSort != null && !dateSort.equals("default")) {
        orderByClause.append(dateSort.equals("new") ? "p.created_at DESC, " : "p.created_at ASC, ");
    }
    if (rateSort != null && !rateSort.equals("default")) {
        orderByClause.append(rateSort.equals("high") ? "rating DESC, " : "rating ASC, ");
    }
    if (priceSort != null && !priceSort.equals("default")) {
        orderByClause.append(priceSort.equals("high") ? "pp.price DESC, " : "pp.price ASC, ");
    }
    if (!orderByClause.isEmpty()) {
        sql.append(" ORDER BY ").append(orderByClause.substring(0, orderByClause.length() - 2));
    } else {
        sql.append(" ORDER BY p.created_at DESC");
    }

    sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

    try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
        int paramIndex = 1;
        if (key != null && !key.isEmpty()) {
            ps.setString(paramIndex++, "%" + key + "%");
        }
        ps.setInt(paramIndex++, Math.max((index - 1) * pageSize, 0));
        ps.setInt(paramIndex, pageSize);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ProductTuan product = extractProduct(rs);
                product.setColors(getColorsByProductId(product.getProductId()));
                product.setPrices(getPricesByProductId(product.getProductId()));
                products.add(product);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return products;
}


    
    public List<ProductTuan> getProducts(String key, String dateSort, String rateSort, String priceSort) {
        List<ProductTuan> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "WITH RecursiveCategory AS ( "
                + "    SELECT category_id FROM Category WHERE category_id in (1,2) "
                + "    UNION ALL "
                + "    SELECT c.category_id FROM Category c INNER JOIN RecursiveCategory rc ON c.parent_id = rc.category_id "
                + ") "
                + "SELECT p.*, ISNULL(avgFeedback.rating, 0) AS rating, pp.price "
                + "FROM Product p "
                + "LEFT JOIN ( "
                + "    SELECT product_id, AVG(rating) AS rating FROM Feedback GROUP BY product_id "
                + ") avgFeedback ON p.product_id = avgFeedback.product_id "
                + "LEFT JOIN ( "
                + "    SELECT product_id, MIN(price) AS price FROM ProductPrice GROUP BY product_id "
                + ") pp ON p.product_id = pp.product_id "
                + "WHERE p.category_id IN (SELECT category_id FROM RecursiveCategory) "
        );

        // Search theo tên sản phẩm
        if (key != null && !key.isEmpty()) {
            sql.append(" AND p.product_name LIKE ? ");
        }

        // Sort điều kiện
        StringBuilder orderByClause = new StringBuilder();
        if (dateSort != null && !dateSort.equals("default")) {
            orderByClause.append(dateSort.equals("new") ? "p.created_at DESC, " : "p.created_at ASC, ");
        }
        if (rateSort != null && !rateSort.equals("default")) {
            orderByClause.append(rateSort.equals("high") ? "rating DESC, " : "rating ASC, ");
        }
        if (priceSort != null && !priceSort.equals("default")) {
            orderByClause.append(priceSort.equals("high") ? "pp.price DESC, " : "pp.price ASC, ");
        }
        if (!orderByClause.isEmpty()) {
            sql.append(" ORDER BY ").append(orderByClause.substring(0, orderByClause.length() - 2));
        }

        // Thực thi truy vấn
        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int index = 1;
            if (key != null && !key.isEmpty()) {
                ps.setString(index++, "%" + key + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductTuan product = extractProduct(rs);
                    product.setColors(getColorsByProductId(product.getProductId()));
                    product.setPrices(getPricesByProductId(product.getProductId()));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public List<ProductTuan> getAllProducts() {
        List<ProductTuan> products = new ArrayList<>();
        String sql = "SELECT p.*, ISNULL(AVG(f.rating), 0) AS rating FROM Product p "
                + "LEFT JOIN Feedback f ON p.product_id = f.product_id "
                + "GROUP BY p.product_id, p.category_id, p.product_name, p.description, "
                + "p.discount, p.status, p.thumbnail, p.created_at";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ProductTuan product = extractProduct(rs);
                product.setColors(getColorsByProductId(product.getProductId()));
                product.setPrices(getPricesByProductId(product.getProductId()));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<ProductTuan> getAllProducts(int id) {
        List<ProductTuan> products = new ArrayList<>();
        String sql = "WITH RecursiveCategory AS (\n"
                + "    SELECT category_id FROM Category WHERE category_id = ?\n"
                + "    UNION ALL\n"
                + "    SELECT c.category_id \n"
                + "    FROM Category c\n"
                + "    INNER JOIN RecursiveCategory rc ON c.parent_id = rc.category_id\n"
                + ")\n"
                + "SELECT p.*, ISNULL(AVG(f.rating), 0) AS rating \n"
                + "FROM Product p\n"
                + "LEFT JOIN Feedback f ON p.product_id = f.product_id \n"
                + "WHERE p.category_id IN (SELECT category_id FROM RecursiveCategory)\n"
                + "GROUP BY p.product_id, p.category_id, p.product_name, p.description, \n"
                + "         p.discount, p.status, p.thumbnail, p.created_at;";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductTuan product = extractProduct(rs);
                    product.setColors(getColorsByProductId(product.getProductId()));
                    product.setPrices(getPricesByProductId(product.getProductId()));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<ProductTuan> getAllProductsByKey(String key) {
        List<ProductTuan> products = new ArrayList<>();
        String sql = "SELECT p.*, ISNULL(AVG(f.rating), 0) AS rating FROM Product p \n"
                + "                LEFT JOIN Feedback f ON p.product_id = f.product_id \n"
                + "				where p.product_name like ?\n"
                + "                GROUP BY p.product_id, p.category_id, p.product_name, p.description, \n"
                + "                p.discount, p.status, p.thumbnail, p.created_at\n"
                + "				";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + key + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductTuan product = extractProduct(rs);
                    product.setColors(getColorsByProductId(product.getProductId()));
                    product.setPrices(getPricesByProductId(product.getProductId()));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<ProductTuan> getAllProductsByPages(int index, int size, String key) {
        List<ProductTuan> products = new ArrayList<>();
        int offset = (index - 1) * size;
        String sql = "SELECT p.*, ISNULL(AVG(f.rating), 0) AS rating \n"
                + "FROM Product p \n"
                + "LEFT JOIN Feedback f ON p.product_id = f.product_id \n"
                + "WHERE p.product_name LIKE ? \n"
                + "GROUP BY p.product_id, p.category_id, p.product_name, p.description, \n"
                + "         p.discount, p.status, p.thumbnail, p.created_at\n"
                + "ORDER BY (SELECT NULL)\n"
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + key + "%");
            ps.setInt(2, offset);
            ps.setInt(3, size);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductTuan product = extractProduct(rs);
                    product.setColors(getColorsByProductId(product.getProductId()));
                    product.setPrices(getPricesByProductId(product.getProductId()));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<ProductTuan> getAllProductsByPages(int index, int size) {
        List<ProductTuan> products = new ArrayList<>();
        int offset = (index - 1) * size;
        String sql = "SELECT p.*, ISNULL(AVG(f.rating), 0) AS rating FROM Product p "
                + "LEFT JOIN Feedback f ON p.product_id = f.product_id "
                + "GROUP BY p.product_id, p.category_id, p.product_name, p.description, "
                + "p.discount, p.status, p.thumbnail, p.created_at "
                + "ORDER BY (SELECT NULL) "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, size);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductTuan product = extractProduct(rs);
                    product.setColors(getColorsByProductId(product.getProductId()));
                    product.setPrices(getPricesByProductId(product.getProductId()));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public List<ProductTuan> getAllProductsByPages(int index, int size, String dateSort, String rateSort, String priceSort) {
        List<ProductTuan> products = new ArrayList<>();
        int offset = (index - 1) * size;
        String orderByClause = "";
        if (dateSort != null && !dateSort.isEmpty() && !dateSort.equals("default")) {
            if (dateSort.equals("new")) {
                orderByClause += "p.created_at DESC, ";
            } else if (dateSort.equals("old")) {
                orderByClause += "p.created_at ASC, ";
            }
        }
        if (rateSort != null && !rateSort.isEmpty() && !rateSort.equals("default")) {
            if (rateSort.equals("high")) {
                orderByClause += "rating DESC, ";
            } else if (rateSort.equals("low")) {
                orderByClause += "rating ASC, ";
            }
        }
        if (priceSort != null && !priceSort.isEmpty() && !priceSort.equals("default")) {
            if (priceSort.equals("high")) {
                orderByClause += "pp.price DESC, ";
            } else if (priceSort.equals("low")) {
                orderByClause += "pp.price ASC, ";
            }
        }
        String sql = "SELECT p.*, ISNULL(avgFeedback.rating, 0) AS rating, pp.price\n"
                + "FROM Product p\n"
                + "LEFT JOIN (\n"
                + "    SELECT product_id, AVG(rating) AS rating\n"
                + "    FROM Feedback\n"
                + "    GROUP BY product_id\n"
                + ") avgFeedback ON p.product_id = avgFeedback.product_id\n"
                + "LEFT JOIN (\n"
                + "    SELECT product_id, MIN(price) AS price\n"
                + "    FROM ProductPrice\n"
                + "    GROUP BY product_id\n"
                + ") pp ON p.product_id = pp.product_id\n";
        if (!orderByClause.isEmpty()) {
            orderByClause = "ORDER BY " + orderByClause.substring(0, orderByClause.length() - 2);
        }
        if (!orderByClause.isEmpty()) {
            sql += " " + orderByClause;
        }
        sql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, size);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductTuan product = extractProduct(rs);
                    product.setColors(getColorsByProductId(product.getProductId()));
                    product.setPrices(getPricesByProductId(product.getProductId()));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public int getTotalProducts() {
        String sql = "SELECT COUNT(*) FROM Product";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<ProductTuan> sortProducts(String dateSort, String rateSort, String priceSort) {
        List<ProductTuan> products = new ArrayList<>();

        String orderByClause = "";

        if (dateSort != null && !dateSort.isEmpty() && !dateSort.equals("default")) {
            if (dateSort.equals("new")) {
                orderByClause += "p.created_at DESC, ";
            } else if (dateSort.equals("old")) {
                orderByClause += "p.created_at ASC, ";
            }
        }
        if (rateSort != null && !rateSort.isEmpty() && !rateSort.equals("default")) {
            if (rateSort.equals("high")) {
                orderByClause += "rating DESC, ";
            } else if (rateSort.equals("low")) {
                orderByClause += "rating ASC, ";
            }
        }
        if (priceSort != null && !priceSort.isEmpty() && !priceSort.equals("default")) {
            if (priceSort.equals("high")) {
                orderByClause += "pp.price DESC, ";
            } else if (priceSort.equals("low")) {
                orderByClause += "pp.price ASC, ";
            }
        }
        String sql = "SELECT p.*, ISNULL(avgFeedback.rating, 0) AS rating, pp.price\n"
                + "FROM Product p\n"
                + "LEFT JOIN (\n"
                + "    SELECT product_id, AVG(rating) AS rating\n"
                + "    FROM Feedback\n"
                + "    GROUP BY product_id\n"
                + ") avgFeedback ON p.product_id = avgFeedback.product_id\n"
                + "LEFT JOIN (\n"
                + "    SELECT product_id, MIN(price) AS price\n"
                + "    FROM ProductPrice\n"
                + "    GROUP BY product_id\n"
                + ") pp ON p.product_id = pp.product_id\n";

        if (!orderByClause.isEmpty()) {
            orderByClause = "ORDER BY " + orderByClause.substring(0, orderByClause.length() - 2);
        }

        if (!orderByClause.isEmpty()) {
            sql += " " + orderByClause;
        }

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ProductTuan product = extractProduct(rs);
                product.setColors(getColorsByProductId(product.getProductId()));
                product.setPrices(getPricesByProductId(product.getProductId()));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Lấy sản phẩm theo ID
    public ProductTuan getProductById(int productId) {
        String sql = "SELECT p.*, ISNULL(AVG(f.rating), 0) AS rating FROM Product p "
                + "LEFT JOIN Feedback f ON p.product_id = f.product_id "
                + "WHERE p.product_id = ? "
                + "GROUP BY p.product_id, p.category_id, p.product_name, p.description, "
                + "p.discount, p.status, p.thumbnail, p.created_at";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ProductTuan product = extractProduct(rs);
                    product.setColors(getColorsByProductId(productId));
                    product.setPrices(getPricesByProductId(productId));
                    return product;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy danh sách màu của sản phẩm
    private List<ColorTuan> getColorsByProductId(int productId) {
        List<ColorTuan> colors = new ArrayList<>();
        String sql = "SELECT DISTINCT c.color_id, c.color FROM Color c "
                + "JOIN ProductPrice pp ON c.color_id = pp.color_id "
                + "WHERE pp.product_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    colors.add(new ColorTuan(rs.getString("color")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colors;
    }

    // Lấy danh sách giá của sản phẩm
    private List<ProductPriceTuan> getPricesByProductId(int productId) {
        List<ProductPriceTuan> prices = new ArrayList<>();
        String sql = "SELECT * FROM ProductPrice WHERE product_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    prices.add(new ProductPriceTuan(
                            rs.getInt("ProductPrice_id"),
                            rs.getInt("product_id"),
                            rs.getInt("color_id"),
                            rs.getInt("price")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prices;
    }

    public List<CategoryTuan> getAllCategories() {
        List<CategoryTuan> categories = new ArrayList<>();
        String sql = "SELECT * FROM Category";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("category_id");
                String name = rs.getString("name");
                int parentId = rs.getInt("parent_id");
                // Kiểm tra nếu parent_id là null
                Integer parent = rs.wasNull() ? null : parentId;
                CategoryTuan cat = new CategoryTuan(id, name, parent);
                categories.add(cat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    // Xây dựng cây danh mục
    public List<CategoryTuan> getCategoryTree() {
        List<CategoryTuan> allCategories = getAllCategories();
        Map<Integer, CategoryTuan> map = new HashMap<>();
        List<CategoryTuan> roots = new ArrayList<>();

        // Lưu tất cả danh mục theo id
        for (CategoryTuan cat : allCategories) {
            map.put(cat.getCategoryId(), cat);
        }

        // Phân loại danh mục cha con
        for (CategoryTuan cat : allCategories) {
            if (cat.getParentId() != null) {
                CategoryTuan parent = map.get(cat.getParentId());
                if (parent != null) {
                    parent.getChildren().add(cat);
                } else {
                    // Nếu không tìm thấy parent thì xem như danh mục gốc
                    roots.add(cat);
                }
            } else {
                roots.add(cat);
            }
        }
        return roots;
    }

    // Hàm hỗ trợ để tạo Product từ ResultSet
    private ProductTuan extractProduct(ResultSet rs) throws SQLException {
        return new ProductTuan(
                rs.getInt("product_id"),
                rs.getInt("category_id"),
                rs.getString("product_name"),
                rs.getString("description"),
                rs.getInt("discount"),
                rs.getInt("status"),
                rs.getString("thumbnail"),
                rs.getString("created_at"),
                rs.getDouble("rating"),
                new ArrayList<>(), // Danh sách màu sắc sẽ được set sau
                new ArrayList<>() // Danh sách giá sẽ được set sau
        );
    }

}
