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
import java.util.AbstractList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductDAOTuan extends DBContext {

    public static void main(String[] args) {
        ProductDAOTuan dao = new ProductDAOTuan();
//        UserTuan customer = new UserTuan(2, "tuan", "tuan@gma", "123", "0821441", true, 1);
//        System.out.println(dao.addCustomer(customer));
//        List<Orders> test = dao.getOrderByDate("2024-03-01", "2025-03-26");
//        for (Orders o : test) {
//            System.out.println(o.toString());
//        }

        Map<String, Integer> revenueMap = dao.getProductReviews("2024-03-01", "2025-03-26");
        revenueMap.forEach((date, revenue) -> {
            System.out.println("Date: " + date + ", Revenue: " + revenue);
        });

    }

    public List<OrderDetails> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetails> orderDetails = new ArrayList<>();
        String sql = "SELECT \n"
                + "    od.order_detail_id,\n"
                + "    p.product_name,\n"
                + "    pp.price,\n"
                + "    od.quantity,\n"
                + "    (od.quantity * pp.price) AS total_price_per_item,\n"
                + "    o.total_price AS total_order_price,\n"
                + "    o.VoucherID,\n"
                + "    o.shipping_address\n"
                + "FROM Orders o\n"
                + "JOIN OrderDetails od ON o.order_id = od.order_id\n"
                + "JOIN Product p ON p.product_id = od.Product_id\n"
                + "JOIN ProductPrice pp ON pp.ProductPrice_id = od.ProductPrice_id\n"
                + "WHERE o.order_id = ?;";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String voucherId = rs.getString("VoucherID");
                    if (rs.wasNull()) {
                        voucherId = "Không có";
                    }

                    OrderDetails detail = new OrderDetails(
                            rs.getInt("order_detail_id"),
                            rs.getString("product_name"),
                            rs.getInt("price"),
                            rs.getInt("quantity"),
                            voucherId,
                            rs.getInt("total_order_price"),
                            rs.getString("shipping_address")
                    );
                    orderDetails.add(detail);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderDetails;
    }

    public void updateFeedbackStatus(int feedbackId, boolean status) {
        String sql = "UPDATE Feedback SET status = ? WHERE feedback_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, status);
            ps.setInt(2, feedbackId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteReply(int feedbackId) {
        String sql = "delete from FeedbackReply where feedback_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, feedbackId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editReply(int feedbackId, String replyContent) {
        String sql = "UPDATE FeedbackReply SET reply_content = ? WHERE feedback_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, replyContent);
            ps.setInt(2, feedbackId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addReply(int feedbackId, String replyContent) {
        String sql = "INSERT INTO FeedbackReply (feedback_id, reply_content)\n"
                + "VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(2, replyContent);
            ps.setInt(1, feedbackId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int countFilteredFeedbacks(String searchName, String searchProduct, String ratingFilter) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM Feedback fb "
                + "JOIN [User] u ON u.email = fb.email "
                + "JOIN [Product] p ON p.product_id = fb.product_id "
                + "WHERE 1=1 ";

        if (searchName != null && !searchName.isEmpty()) {
            sql += "AND u.user_name LIKE ? ";
        }
        if (searchProduct != null && !searchProduct.isEmpty()) {
            sql += "AND p.product_name LIKE ? ";
        }
        if (ratingFilter != null && !ratingFilter.isEmpty()) {
            sql += "AND fb.rating = ? ";
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int paramIndex = 1;

            if (searchName != null && !searchName.isEmpty()) {
                ps.setString(paramIndex++, "%" + searchName + "%");
            }
            if (searchProduct != null && !searchProduct.isEmpty()) {
                ps.setString(paramIndex++, "%" + searchProduct + "%");
            }
            if (ratingFilter != null && !ratingFilter.isEmpty()) {
                ps.setInt(paramIndex++, Integer.parseInt(ratingFilter));
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<Feedback> searchFeedback(String searchName, String searchProduct, String ratingFilter, String sortOrder, int page, int pageSize) {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT fb.*, fr.reply_content, u.user_name, p.product_name "
                + "FROM Feedback fb "
                + "LEFT JOIN FeedbackReply fr ON fb.feedback_id = fr.feedback_id "
                + "JOIN [User] u ON u.email = fb.email "
                + "JOIN [Product] p ON p.product_id = fb.product_id "
                + "WHERE (u.user_name LIKE ? OR ? IS NULL) "
                + "AND (p.product_name LIKE ? OR ? IS NULL) "
                + "AND (fb.rating = ? OR ? IS NULL) ";

        // Thêm điều kiện sắp xếp
        if ("desc".equals(sortOrder)) {
            sql += "ORDER BY fb.rating DESC ";
        } else if ("asc".equals(sortOrder)) {
            sql += "ORDER BY fb.rating ASC ";
        } else if ("newest".equals(sortOrder)) {
            sql += "ORDER BY fb.created_at DESC ";
        } else if ("oldest".equals(sortOrder)) {
            sql += "ORDER BY fb.created_at ASC ";
        } else {
            sql += "ORDER BY fb.feedback_id "; // Mặc định
        }

        sql += "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY"; // Phân trang

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, searchName != null && !searchName.isEmpty() ? "%" + searchName + "%" : null);
            ps.setString(2, searchName != null && !searchName.isEmpty() ? searchName : null);
            ps.setString(3, searchProduct != null && !searchProduct.isEmpty() ? "%" + searchProduct + "%" : null);
            ps.setString(4, searchProduct != null && !searchProduct.isEmpty() ? searchProduct : null);
            ps.setString(5, ratingFilter != null && !ratingFilter.isEmpty() ? ratingFilter : null);
            ps.setString(6, ratingFilter != null && !ratingFilter.isEmpty() ? ratingFilter : null);
            ps.setInt(7, (page - 1) * pageSize);
            ps.setInt(8, pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Feedback feedback = new Feedback();
                    feedback.setFeedback_id(rs.getInt("feedback_id"));
                    feedback.setProduct_id(rs.getInt("product_id"));
                    feedback.setEmail(rs.getString("email"));
                    feedback.setFeedback_content(rs.getString("feedback_content"));
                    feedback.setRating(rs.getInt("rating"));
                    feedback.setCreate_at(rs.getString("created_at"));
                    feedback.setStatus(rs.getBoolean("status"));
                    feedback.setReply_content(rs.getString("reply_content"));
                    feedback.setUser_name(rs.getString("user_name"));
                    feedback.setProduct_name(rs.getString("product_name"));
                    list.add(feedback);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Feedback> getAllFeedbackByPage(int page, int pageSize) {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT fb.*, fr.reply_content, u.user_name, p.product_name "
                + "FROM Feedback fb "
                + "LEFT JOIN FeedbackReply fr ON fb.feedback_id = fr.feedback_id "
                + "JOIN [User] u ON u.email = fb.email "
                + "JOIN [Product] p ON p.product_id = fb.product_id "
                + "ORDER BY fb.feedback_id "
                + // Sắp xếp để OFFSET hoạt động đúng
                "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY"; // Phân trang

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, (page - 1) * pageSize); // Bỏ qua số lượng record phù hợp
            ps.setInt(2, pageSize); // Số lượng record cần lấy

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Feedback feedback = new Feedback();
                    feedback.setFeedback_id(rs.getInt("feedback_id"));
                    feedback.setProduct_id(rs.getInt("product_id"));
                    feedback.setEmail(rs.getString("email"));
                    feedback.setFeedback_content(rs.getString("feedback_content"));
                    feedback.setRating(rs.getInt("rating"));
                    feedback.setCreate_at(rs.getString("created_at"));
                    feedback.setStatus(rs.getBoolean("status"));
                    feedback.setReply_content(rs.getString("reply_content"));
                    feedback.setUser_name(rs.getString("user_name"));
                    feedback.setProduct_name(rs.getString("product_name"));
                    list.add(feedback);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Feedback> getAllFeedback() {
        List<Feedback> list = new ArrayList<>();
        String sql = "select fb.*, fr.reply_content, u.user_name, p.product_name\n"
                + "from Feedback fb\n"
                + "left join FeedbackReply fr on fb.feedback_id=fr.feedback_id\n"
                + "join [User] u on u.email = fb.email\n"
                + "join [Product] p on p.product_id = fb.product_id";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Feedback feedback = new Feedback();
                    feedback.setFeedback_id(rs.getInt("feedback_id"));
                    feedback.setProduct_id(rs.getInt("product_id"));
                    feedback.setEmail(rs.getString("email"));
                    feedback.setFeedback_content(rs.getString("feedback_content"));
                    feedback.setRating(rs.getInt("rating"));
                    feedback.setCreate_at(rs.getString("created_at"));
                    feedback.setStatus(rs.getBoolean("status"));
                    feedback.setReply_content(rs.getString("reply_content"));
                    feedback.setUser_name(rs.getString("user_name"));
                    feedback.setProduct_name(rs.getString("product_name"));
                    list.add(feedback);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Map<String, Integer> getProductReviews(String startDate, String endDate) {
        Map<String, Integer> reviewStats = new LinkedHashMap<>();
        String sql = "SELECT rating, COUNT(*) AS Total FROM Feedback "
                + "WHERE created_at BETWEEN ? AND ? "
                + "GROUP BY rating";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reviewStats.put(rs.getString("Rating") + " sao", rs.getInt("Total"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviewStats;
    }

    public Map<String, Integer> getProductViews(String startDate, String endDate) {
        Map<String, Integer> customerStats = new LinkedHashMap<>();
        String sql = "SELECT p.product_name, pv.[view]\n"
                + "FROM Product p\n"
                + "JOIN ProductView pv ON p.product_id = pv.product_id\n"
                + "where pv.viewed_at BETWEEN ? AND ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    customerStats.put(rs.getString("product_name"), rs.getInt("view"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerStats;
    }

    public Map<String, Integer> getCustomerAnalysis(String startDate, String endDate) {
        Map<String, Integer> customerStats = new LinkedHashMap<>();
        String sql = "SELECT r.role_name, COUNT(*) AS Total\n"
                + "FROM [User] u\n"
                + "JOIN Role r ON u.role_id = r.role_id\n"
                + "WHERE u.created_at BETWEEN ? AND ?\n"
                + "GROUP BY r.role_name, u.role_id\n"
                + "ORDER BY Total DESC;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    customerStats.put(rs.getString("role_name"), rs.getInt("Total"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerStats;
    }

    public Map<String, Integer> getTopProducts(String startDate, String endDate) {
        Map<String, Integer> topProducts = new LinkedHashMap<>();
        String sql = "SELECT TOP 5 p.product_name, SUM(od.quantity) AS TotalSold\n"
                + "FROM OrderDetails od\n"
                + "JOIN Product p ON od.Product_id = p.product_id\n"
                + "JOIN Orders o ON od.order_id = o.order_id\n"
                + "WHERE order_date BETWEEN ? AND ?\n"
                + "GROUP BY p.product_name\n"
                + "ORDER BY TotalSold DESC;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    topProducts.put(rs.getString("product_name"), rs.getInt("TotalSold"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return topProducts;
    }

    public Map<String, Double> getRevenueByDate(String startDate, String endDate) {
        Map<String, Double> revenueData = new LinkedHashMap<>();
        String sql = "SELECT order_date, SUM(total_price) AS Revenue "
                + "FROM Orders WHERE order_date BETWEEN ? AND ? "
                + "GROUP BY order_date";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    revenueData.put(rs.getString("order_date"), rs.getDouble("Revenue"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return revenueData;
    }

    public List<Orders> getOrderByDate(String startDate, String endDate) {
        List<Orders> orders = new ArrayList<Orders>();
        String sql = "SELECT TOP 5 * \n"
                + "FROM Orders \n"
                + "WHERE order_date BETWEEN ? AND ? \n"
                + "ORDER BY order_date DESC;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Orders order = new Orders();
                    order.setOrder_id(rs.getInt("order_id"));
                    order.setEmail(rs.getString("email"));
                    order.setOrder_date(rs.getString("order_date"));
                    order.setTotal_price(rs.getInt("total_price"));
                    order.setStatus(rs.getString("status"));
                    order.setPhone(rs.getString("phone"));
                    order.setPayment_method(rs.getString("payment_method"));
                    order.setShipping_address(rs.getString("shipping_address"));
                    orders.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Orders> searchOrders(String email, String orderDate, String status, String paymentMethod) {
        List<Orders> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM [Orders] WHERE 1=1");

        if (email != null && !email.isEmpty()) {
            sql.append(" AND email LIKE ?");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND status = ?");
        }
        if (paymentMethod != null && !paymentMethod.isEmpty()) {
            sql.append(" AND payment_method = ?");
        }

        // Xử lý sắp xếp theo order_date
        if ("asc".equalsIgnoreCase(orderDate)) {
            sql.append(" ORDER BY order_date ASC");
        } else {
            sql.append(" ORDER BY order_date DESC"); // Mặc định mới nhất
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int index = 1;
            if (email != null && !email.isEmpty()) {
                ps.setString(index++, "%" + email + "%");
            }
            if (status != null && !status.isEmpty()) {
                ps.setString(index++, status);
            }
            if (paymentMethod != null && !paymentMethod.isEmpty()) {
                ps.setString(index++, paymentMethod);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Orders order = new Orders();
                    order.setOrder_id(rs.getInt("order_id"));
                    order.setEmail(rs.getString("email"));
                    order.setOrder_date(rs.getString("order_date"));
                    order.setTotal_price(rs.getInt("total_price"));
                    order.setStatus(rs.getString("status"));
                    order.setPhone(rs.getString("phone"));
                    order.setPayment_method(rs.getString("payment_method"));
                    order.setShipping_address(rs.getString("shipping_address"));
                    list.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Orders> searchOrdersByPage(String email, String orderDate, String status, String paymentMethod, int page, int pageSize) {
        List<Orders> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM [Orders] WHERE 1=1");

        if (email != null && !email.isEmpty()) {
            sql.append(" AND email LIKE ?");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND status = ?");
        }
        if (paymentMethod != null && !paymentMethod.isEmpty()) {
            sql.append(" AND payment_method = ?");
        }

        // Xử lý sắp xếp theo order_date
        if ("asc".equalsIgnoreCase(orderDate)) {
            sql.append(" ORDER BY order_date ASC");
        } else {
            sql.append(" ORDER BY order_date DESC"); // Mặc định là mới nhất
        }

        sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int index = 1;
            if (email != null && !email.isEmpty()) {
                ps.setString(index++, "%" + email + "%");
            }
            if (status != null && !status.isEmpty()) {
                ps.setString(index++, status);
            }
            if (paymentMethod != null && !paymentMethod.isEmpty()) {
                ps.setString(index++, paymentMethod);
            }

            ps.setInt(index++, (page - 1) * pageSize);
            ps.setInt(index++, pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Orders order = new Orders();
                    order.setOrder_id(rs.getInt("order_id"));
                    order.setEmail(rs.getString("email"));
                    order.setOrder_date(rs.getString("order_date"));
                    order.setTotal_price(rs.getInt("total_price"));
                    order.setStatus(rs.getString("status"));
                    order.setPhone(rs.getString("phone"));
                    order.setPayment_method(rs.getString("payment_method"));
                    order.setShipping_address(rs.getString("shipping_address"));
                    list.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Orders> getAllOrderByPage(int page, int pageSize) {
        List<Orders> list = new ArrayList<>();
        String sql = "SELECT * FROM [Orders] ORDER BY order_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, (page - 1) * pageSize);
            ps.setInt(2, pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Orders order = new Orders();
                    order.setOrder_id(rs.getInt("order_id"));
                    order.setEmail(rs.getString("email"));
                    order.setOrder_date(rs.getString("order_date"));
                    order.setTotal_price(rs.getInt("total_price"));
                    order.setStatus(rs.getString("status"));
                    order.setPhone(rs.getString("phone"));
                    order.setPayment_method(rs.getString("payment_method"));
                    order.setShipping_address(rs.getString("shipping_address"));
                    list.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Orders> getAllOrder() {
        List<Orders> list = new ArrayList<Orders>();
        String sql = "select*from [Orders]";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Orders order = new Orders();
                    order.setOrder_id(rs.getInt("order_id"));
                    order.setEmail(rs.getString("email"));
                    order.setOrder_date(rs.getString("order_date"));
                    order.setTotal_price(rs.getInt("total_price"));
                    order.setStatus(rs.getString("status"));
                    order.setPhone(rs.getString("phone"));
                    order.setPayment_method(rs.getString("payment_method"));
                    order.setShipping_address(rs.getString("shipping_address"));
                    list.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public AddressTuan getCustomerAddressById(int id) {
        AddressTuan address = null;
        String sql = "SELECT * FROM Address WHERE address_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    address = new AddressTuan();
                    address.setAddressId(rs.getInt("address_id"));
                    address.setUserId(rs.getInt("user_id"));
                    address.setName(rs.getString("name"));
                    address.setPhone(rs.getString("phone"));
                    address.setCity(rs.getString("city"));
                    address.setDistrict(rs.getString("district"));
                    address.setWard(rs.getString("ward"));
                    address.setStreet(rs.getString("street"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }

    public List<UserTuan> getTotal() {
        List<UserTuan> orders = new ArrayList<>();
        String sql = "SELECT \n"
                + "    u.user_id, \n"
                + "    COUNT( o.order_id) AS TotalOrder, \n"
                + "    SUM(o.total_price) AS TotalPrice\n"
                + "FROM [User] u\n"
                + "left JOIN [Orders] o ON u.email = o.email\n"
                + "where role_id=2\n"
                + "GROUP BY u.user_id;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    UserTuan order = new UserTuan(
                            rs.getInt("user_id"),
                            rs.getInt("TotalOrder"),
                            rs.getInt("TotalPrice"));
                    orders.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public void updateCustomerAddress(AddressTuan address) {
        String sql = "UPDATE [dbo].[Address] "
                + "SET [name] = ?, [phone] = ?, [city] = ?, "
                + "[district] = ?, [ward] = ?, [street] = ? "
                + "WHERE [address_id] = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, address.getName());
            ps.setString(2, address.getPhone());
            ps.setString(3, address.getCity());
            ps.setString(4, address.getDistrict());
            ps.setString(5, address.getWard());
            ps.setString(6, address.getStreet());
            ps.setInt(7, address.getAddressId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<AddressTuan> getCustomerAddressesById(int id) {
        List<AddressTuan> addressList = new ArrayList<>();
        String sql = "SELECT * FROM Address WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AddressTuan address = new AddressTuan();
                    address.setAddressId(rs.getInt("address_id"));
                    address.setUserId(rs.getInt("user_id"));
                    address.setName(rs.getString("name"));
                    address.setPhone(rs.getString("phone"));
                    address.setCity(rs.getString("city"));
                    address.setDistrict(rs.getString("district"));
                    address.setWard(rs.getString("ward"));
                    address.setStreet(rs.getString("street"));
                    addressList.add(address);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addressList;
    }

    public void updateCustomerStatus(int id, boolean status) {
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

    public int addCustomerAddress(AddressTuan address) {
        int id = -1;
        String sql = "INSERT INTO [dbo].[Address] "
                + "([user_id], name, phone, city, district, ward, street) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, address.getUserId());
            ps.setString(2, address.getName());
            ps.setString(3, address.getPhone());
            ps.setString(4, address.getCity());
            ps.setString(5, address.getDistrict());
            ps.setString(6, address.getWard());
            ps.setString(7, address.getStreet());
            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int addCustomer(UserTuan customer) {
        int id = -1;
        String sql = "INSERT INTO [RunnerShop].[dbo].[User] "
                + "([role_id], [user_name], [full_name], [email], [password], [phone_number], [status], [gender_id], [created_at]) "
                + "VALUES (2, ?, ?, ?, ?, ?, ?, ?, GETDATE())";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, customer.getUserName());
            ps.setString(2, customer.getFullName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPassword());
            ps.setString(5, customer.getPhoneNumber());
            ps.setBoolean(6, customer.isStatus());
            ps.setInt(7, customer.getGenderId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void updateCustomer(UserTuan customer) {
        String sql = "UPDATE [User]\n"
                + "SET user_name = ?,\n"
                + "    full_name = ?,\n"
                + "    email = ?,\n"
                + "    phone_number = ?,\n"
                + "    status = ?,\n"
                + "    gender_id = ?\n"
                + "WHERE user_id = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, customer.getUserName());
            ps.setString(2, customer.getFullName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPhoneNumber());
            ps.setBoolean(5, customer.isStatus());
            ps.setInt(6, customer.getGenderId());
            ps.setInt(7, customer.getUserId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UserTuan getCustomerById(int id) {
        UserTuan customer = null;
        String sql = "SELECT "
                + "    u.user_id, "
                + "    u.role_id, "
                + "    u.user_name, "
                + "    u.full_name, "
                + "    u.email, "
                + "    u.phone_number, "
                + "    u.status, "
                + "    u.gender_id, "
                + "    a.address_id, "
                + "    a.name AS address_name, "
                + "    a.phone AS address_phone, "
                + "    a.city, "
                + "    a.district, "
                + "    a.ward, "
                + "    a.street "
                + "FROM [User] u "
                + "LEFT JOIN [Address] a ON u.user_id = a.user_id "
                + "WHERE u.role_id = 2 AND u.user_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    customer = new UserTuan();
                    customer.setUserId(rs.getInt("user_id"));
                    customer.setUserName(rs.getString("user_name"));
                    customer.setFullName(rs.getString("full_name"));
                    customer.setRoleId(rs.getInt("role_id"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPhoneNumber(rs.getString("phone_number"));
                    customer.setStatus(rs.getBoolean("status"));
                    customer.setGenderId(rs.getInt("gender_id"));
                    customer.setAddresses(getAddressById(customer.getUserId()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    public List<UserTuan> searchUsers(String userName, String email, String phone, Boolean status, int offset, int size) {
        List<UserTuan> userList = new ArrayList<>();
        String sql = "WITH UserWithAddress AS (\n"
                + "    SELECT \n"
                + "        u.user_id, u.role_id, u.user_name, u.email, u.phone_number, u.status, u.gender_id,\n"
                + "        a.address_id, a.name AS address_name, a.phone AS address_phone, \n"
                + "        a.city, a.district, a.ward, a.street,\n"
                + "        ROW_NUMBER() OVER (PARTITION BY u.user_id ORDER BY a.address_id) AS rn\n"
                + "    FROM [User] u\n"
                + "    LEFT JOIN [Address] a ON u.user_id = a.user_id\n"
                + "    WHERE u.role_id = 2\n"
                + ")\n"
                + "SELECT user_id, role_id, user_name, email, phone_number, status, gender_id,\n"
                + "       address_id, address_name, address_phone, city, district, ward, street\n"
                + "FROM UserWithAddress\n"
                + "WHERE rn = 1\n"
                + "AND (? IS NULL OR user_name LIKE ?)\n"
                + "AND (? IS NULL OR email LIKE ?)\n"
                + "AND (? IS NULL OR phone_number LIKE ?)\n"
                + "AND (? IS NULL OR status = ?)\n"
                + "ORDER BY user_id\n"
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, userName != null ? "%" + userName + "%" : null);
            ps.setObject(2, userName != null ? "%" + userName + "%" : null);
            ps.setObject(3, email != null ? "%" + email + "%" : null);
            ps.setObject(4, email != null ? "%" + email + "%" : null);
            ps.setObject(5, phone != null ? "%" + phone + "%" : null);
            ps.setObject(6, phone != null ? "%" + phone + "%" : null);
            ps.setObject(7, status);
            ps.setObject(8, status);
            ps.setInt(9, offset);
            ps.setInt(10, size);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    UserTuan customer = new UserTuan();
                    customer.setUserId(rs.getInt("user_id"));
                    customer.setUserName(rs.getString("user_name"));
                    customer.setRoleId(rs.getInt("role_id"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPhoneNumber(rs.getString("phone_number"));
                    customer.setStatus(rs.getBoolean("status"));
                    customer.setGenderId(rs.getInt("gender_id"));
                    customer.setAddresses(getAddressById(customer.getUserId()));
                    userList.add(customer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public List<UserTuan> searchUsers(String userName, String email, String phone, Boolean status) {
        List<UserTuan> userList = new ArrayList<>();
        String sql = "WITH UserWithAddress AS (\n"
                + "    SELECT \n"
                + "        u.user_id, u.role_id, u.user_name, u.email, u.phone_number, u.status, u.gender_id,\n"
                + "        a.address_id, a.name AS address_name, a.phone AS address_phone, \n"
                + "        a.city, a.district, a.ward, a.street,\n"
                + "        ROW_NUMBER() OVER (PARTITION BY u.user_id ORDER BY a.address_id) AS rn\n"
                + "    FROM [User] u\n"
                + "    LEFT JOIN [Address] a ON u.user_id = a.user_id\n"
                + "    WHERE u.role_id = 2\n"
                + ")\n"
                + "SELECT user_id, role_id, user_name, email, phone_number, status, gender_id,\n"
                + "       address_id, address_name, address_phone, city, district, ward, street\n"
                + "FROM UserWithAddress\n"
                + "WHERE rn = 1\n"
                + "AND (? IS NULL OR user_name LIKE ?)\n"
                + "AND (? IS NULL OR email LIKE ?)\n"
                + "AND (? IS NULL OR phone_number LIKE ?)\n"
                + "AND (? IS NULL OR status = ?)\n"
                + "ORDER BY user_id\n";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, userName != null ? "%" + userName + "%" : null);
            ps.setObject(2, userName != null ? "%" + userName + "%" : null);
            ps.setObject(3, email != null ? "%" + email + "%" : null);
            ps.setObject(4, email != null ? "%" + email + "%" : null);
            ps.setObject(5, phone != null ? "%" + phone + "%" : null);
            ps.setObject(6, phone != null ? "%" + phone + "%" : null);
            ps.setObject(7, status);
            ps.setObject(8, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    UserTuan customer = new UserTuan();
                    customer.setUserId(rs.getInt("user_id"));
                    customer.setUserName(rs.getString("user_name"));
                    customer.setRoleId(rs.getInt("role_id"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPhoneNumber(rs.getString("phone_number"));
                    customer.setStatus(rs.getBoolean("status"));
                    customer.setGenderId(rs.getInt("gender_id"));
                    customer.setAddresses(getAddressById(customer.getUserId()));
                    userList.add(customer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public List<AddressTuan> getAddressById(int id) {
        List<AddressTuan> address = new ArrayList<>();
        String sql = "select*from Address\n"
                + "where user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    address.add(new AddressTuan(rs.getInt("address_id"),
                            rs.getInt("user_id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("city"),
                            rs.getString("district"),
                            rs.getString("ward"),
                            rs.getString("street"))
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return address;
    }

    public List<UserTuan> getAllCustomer() {
        List<UserTuan> customers = new ArrayList<>();
        String sql = "WITH UserDistinct AS (\n"
                + "    SELECT \n"
                + "        u.user_id,\n"
                + "        u.role_id,\n"
                + "        u.user_name,\n"
                + "        u.email,\n"
                + "        u.phone_number,\n"
                + "        u.status,\n"
                + "        u.gender_id,\n"
                + "        a.address_id,\n"
                + "        a.name AS address_name,\n"
                + "        a.phone AS address_phone,\n"
                + "        a.city,\n"
                + "        a.district,\n"
                + "        a.ward,\n"
                + "        a.street,\n"
                + "        ROW_NUMBER() OVER (PARTITION BY u.user_id ORDER BY a.address_id) AS rn\n"
                + "    FROM [User] u\n"
                + "    LEFT JOIN [Address] a ON u.user_id = a.user_id\n"
                + "    WHERE u.role_id = 2\n"
                + ")\n"
                + "SELECT *\n"
                + "FROM UserDistinct\n"
                + "WHERE rn = 1\n"
                + "ORDER BY user_id\n";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                UserTuan customer = new UserTuan();
                customer.setUserId(rs.getInt("user_id"));
                customer.setUserName(rs.getString("user_name"));
                customer.setRoleId(rs.getInt("role_id"));
                customer.setEmail(rs.getString("email"));
                customer.setPhoneNumber(rs.getString("phone_number"));
                customer.setStatus(rs.getBoolean("status"));
                customer.setGenderId(rs.getInt("gender_id"));
                customer.setAddresses(getAddressById(customer.getUserId()));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public List<UserTuan> getAllCustomer(int index, int size) {
        List<UserTuan> customers = new ArrayList<>();
        int offset = (index - 1) * size;
        String sql = "WITH UserDistinct AS (\n"
                + "    SELECT \n"
                + "        u.user_id,\n"
                + "        u.role_id,\n"
                + "        u.user_name,\n"
                + "        u.email,\n"
                + "        u.phone_number,\n"
                + "        u.status,\n"
                + "        u.gender_id,\n"
                + "        a.address_id,\n"
                + "        a.name AS address_name,\n"
                + "        a.phone AS address_phone,\n"
                + "        a.city,\n"
                + "        a.district,\n"
                + "        a.ward,\n"
                + "        a.street,\n"
                + "        ROW_NUMBER() OVER (PARTITION BY u.user_id ORDER BY a.address_id) AS rn\n"
                + "    FROM [User] u\n"
                + "    LEFT JOIN [Address] a ON u.user_id = a.user_id\n"
                + "    WHERE u.role_id = 2\n"
                + ")\n"
                + "SELECT *\n"
                + "FROM UserDistinct\n"
                + "WHERE rn = 1\n"
                + "ORDER BY user_id\n"
                + "OFFSET ? ROWS\n"
                + "FETCH NEXT ? ROWS ONLY;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, size);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    UserTuan customer = new UserTuan();
                    customer.setUserId(rs.getInt("user_id"));
                    customer.setUserName(rs.getString("user_name"));
                    customer.setRoleId(rs.getInt("role_id"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPhoneNumber(rs.getString("phone_number"));
                    customer.setStatus(rs.getBoolean("status"));
                    customer.setGenderId(rs.getInt("gender_id"));
                    customer.setAddresses(getAddressById(customer.getUserId()));
                    customers.add(customer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public List<ProductTuan> filterProducts(List<String> colors, List<String> sizes, int minPrice, int maxPrice) {
        List<ProductTuan> products = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT p.*, ISNULL(AVG(f.rating), 0) AS rating, MIN(pp.price) AS price FROM Product p "
                + "LEFT JOIN Feedback f ON p.product_id = f.product_id "
                + "LEFT JOIN ProductPrice pp ON p.product_id = pp.product_id "
                + "LEFT JOIN ProductQuantity pq ON pp.ProductPrice_id = pq.ProductPrice_id "
                + "LEFT JOIN Color c ON pp.color_id = c.color_id "
                + "LEFT JOIN Size s ON pq.size_id = s.size_id "
                + "WHERE pp.price BETWEEN ? AND ?");

        if (colors != null && !colors.isEmpty()) {
            sql.append(" AND c.color IN (");
            sql.append(colors.stream().map(c -> "'" + c + "'").collect(Collectors.joining(",")));
            sql.append(")");
        }

        if (sizes != null && !sizes.isEmpty()) {
            sql.append(" AND s.size IN (");
            sql.append(sizes.stream().map(s -> "'" + s + "'").collect(Collectors.joining(",")));
            sql.append(")");
        }

        sql.append(" GROUP BY p.product_id, p.category_id, p.product_name, p.description, p.discount, p.status, p.thumbnail, p.created_at");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            ps.setInt(1, minPrice);
            ps.setInt(2, maxPrice);
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

    public List<ProductTuan> filterProductsPages(List<String> colors, List<String> sizes, int minPrice, int maxPrice, int page, int pageSize) {
        List<ProductTuan> products = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT p.*, ISNULL(AVG(f.rating), 0) AS rating, MIN(pp.price) AS price FROM Product p "
                + "LEFT JOIN Feedback f ON p.product_id = f.product_id "
                + "LEFT JOIN ProductPrice pp ON p.product_id = pp.product_id "
                + "LEFT JOIN ProductQuantity pq ON pp.ProductPrice_id = pq.ProductPrice_id "
                + "LEFT JOIN Color c ON pp.color_id = c.color_id "
                + "LEFT JOIN Size s ON pq.size_id = s.size_id "
                + "WHERE pp.price BETWEEN ? AND ?");
        if (colors != null && !colors.isEmpty()) {
            sql.append(" AND c.color IN (");
            sql.append(colors.stream().map(c -> "'" + c + "'").collect(Collectors.joining(",")));
            sql.append(")");
        }
        if (sizes != null && !sizes.isEmpty()) {
            sql.append(" AND s.size IN (");
            sql.append(sizes.stream().map(s -> "'" + s + "'").collect(Collectors.joining(",")));
            sql.append(")");
        }
        sql.append(" GROUP BY p.product_id, p.category_id, p.product_name, p.description, p.discount, p.status, p.thumbnail, p.created_at");

        sql.append(" ORDER BY p.created_at DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            ps.setInt(1, minPrice);
            ps.setInt(2, maxPrice);
            ps.setInt(3, (page - 1) * pageSize); 
            ps.setInt(4, pageSize); 

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

    public List<ProductTuan> getProductsByColorAndSize(List<String> colors, List<String> sizes) {
        List<ProductTuan> products = new ArrayList<>();
        if (colors == null || colors.isEmpty() || sizes == null || sizes.isEmpty()) {
            return products;
        }
        String sql = "SELECT p.*, ISNULL(AVG(f.rating), 0) AS rating "
                + "FROM Product p "
                + "LEFT JOIN Feedback f ON p.product_id = f.product_id "
                + "LEFT JOIN ProductPrice pp ON p.product_id = pp.product_id "
                + "LEFT JOIN ProductQuantity pq ON pp.ProductPrice_id = pq.ProductPrice_id "
                + "LEFT JOIN Color c ON pp.color_id = c.color_id "
                + "LEFT JOIN Size s ON pq.size_id = s.size_id "
                + "WHERE c.color IN (";

        for (int i = 0; i < colors.size(); i++) {
            sql += "'" + colors.get(i) + "'";
            if (i < colors.size() - 1) {
                sql += ",";
            }
        }
        sql += ") AND s.size IN (";
        for (int i = 0; i < sizes.size(); i++) {
            sql += "'" + sizes.get(i) + "'";
            if (i < sizes.size() - 1) {
                sql += ",";
            }
        }
        sql += ") GROUP BY p.product_id, p.category_id, p.product_name, p.description, "
                + "p.discount, p.status, p.thumbnail, p.created_at "
                + "ORDER BY p.created_at DESC";
        System.out.println(sql);

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                products.add(extractProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Size> getAllSize() {
        List<Size> list = new ArrayList<>();
        String sql = "select*from Size";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Size size = new Size(rs.getInt("size_id"), rs.getString("size"));
                    list.add(size);
                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Color> getAllColor() {
        List<Color> list = new ArrayList<>();
        String sql = "select*from Color";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Color color = new Color(rs.getInt("color_id"), rs.getString("color"));
                    list.add(color);
                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<ProductTuan> getProductsByPriceRange(int min, int max) {
        List<ProductTuan> products = new ArrayList<>();
        String sql = "SELECT p.*, ISNULL(AVG(f.rating), 0) AS rating, MIN(pp.price) AS price FROM Product p "
                + "LEFT JOIN Feedback f ON p.product_id = f.product_id "
                + "LEFT JOIN ProductPrice pp ON p.product_id = pp.product_id "
                + "WHERE pp.price BETWEEN ? AND ? "
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

    public List<ProductTuan> searchProducts(String key, String date, String rate, String price, String views) {
        List<ProductTuan> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT p.product_id, p.category_id, p.product_name, p.description, p.discount, \n"
                + "       p.status, p.thumbnail, p.created_at, \n"
                + "       ISNULL(AVG(f.rating), 0) AS rating, \n"
                + "       ISNULL(AVG(pp.price), 0) AS price, \n"
                + "       ISNULL(pv.[view], 0) AS view_count\n"
                + "FROM Product p\n"
                + "LEFT JOIN Feedback f ON f.product_id = p.product_id\n"
                + "LEFT JOIN ProductPrice pp ON pp.product_id = p.product_id\n"
                + "LEFT JOIN ProductView pv ON pv.product_id = p.product_id ");

        List<String> conditions = new ArrayList<>();
        if (key != null && !key.trim().isEmpty()) {
            conditions.add("p.product_name LIKE ?");
        }

        if (!conditions.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", conditions));
        }
        sql.append(" GROUP BY p.product_id, p.category_id, p.product_name, p.description, \n"
                + "         p.discount, p.status, p.thumbnail, p.created_at, pv.[view] ");

        List<String> orderByClauses = new ArrayList<>();
        if (date.equals("new")) {
            orderByClauses.add("p.created_at DESC");
        } else if (date.equals("old")) {
            orderByClauses.add("p.created_at ASC");
        }
        if (rate.equals("high")) {
            orderByClauses.add("rating DESC");
        } else if (rate.equals("low")) {
            orderByClauses.add("rating ASC");
        }
        if (price.equals("high")) {
            orderByClauses.add("price DESC");
        } else if (price.equals("low")) {
            orderByClauses.add("price ASC");
        }
        if (views.equals("high")) {
            orderByClauses.add("view_count DESC");
        } else if (views.equals("low")) {
            orderByClauses.add("view_count ASC");
        }

        if (!orderByClauses.isEmpty()) {
            sql.append(" ORDER BY ").append(String.join(", ", orderByClauses));
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (key != null && !key.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + key + "%");
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

    public List<ProductTuan> searchProductsPages(String key, String date, String rate, String price, String views, int page, int pageSize) {
        List<ProductTuan> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT p.product_id, p.category_id, p.product_name, p.description, p.discount, \n"
                + "       p.status, p.thumbnail, p.created_at, \n"
                + "       ISNULL(AVG(f.rating), 0) AS rating, \n"
                + "       ISNULL(AVG(pp.price), 0) AS price, \n"
                + "       ISNULL(pv.[view], 0) AS view_count\n"
                + "FROM Product p\n"
                + "LEFT JOIN Feedback f ON f.product_id = p.product_id\n"
                + "LEFT JOIN ProductPrice pp ON pp.product_id = p.product_id\n"
                + "LEFT JOIN ProductView pv ON pv.product_id = p.product_id ");

        List<String> conditions = new ArrayList<>();
        if (key != null && !key.trim().isEmpty()) {
            conditions.add("p.product_name LIKE ?");
        }

        if (!conditions.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", conditions));
        }
        sql.append(" GROUP BY p.product_id, p.category_id, p.product_name, p.description, \n"
                + "         p.discount, p.status, p.thumbnail, p.created_at, pv.[view] ");

        List<String> orderByClauses = new ArrayList<>();
        if ("new".equals(date)) {
            orderByClauses.add("p.created_at DESC");
        } else if ("old".equals(date)) {
            orderByClauses.add("p.created_at ASC");
        }

        if ("high".equals(rate)) {
            orderByClauses.add("rating DESC");
        } else if ("low".equals(rate)) {
            orderByClauses.add("rating ASC");
        }

        if ("high".equals(price)) {
            orderByClauses.add("price DESC");
        } else if ("low".equals(price)) {
            orderByClauses.add("price ASC");
        }

        if ("high".equals(views)) {
            orderByClauses.add("view_count DESC");
        } else if ("low".equals(views)) {
            orderByClauses.add("view_count ASC");
        }

        if (!orderByClauses.isEmpty()) {
            sql.append(" ORDER BY ").append(String.join(", ", orderByClauses));
        } else {
            sql.append(" ORDER BY p.created_at ASC");
        }

        sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (key != null && !key.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + key + "%");
            }
            ps.setInt(paramIndex++, (page - 1) * pageSize);
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

    public List<ProductTuan> getAllProductsByCategoryIdPages(int id, int index, int size) {
        List<ProductTuan> products = new ArrayList<>();
        int offset = (index - 1) * size;
        String sql = "WITH RecursiveCategory AS (\n"
                + "                    SELECT category_id FROM Category WHERE category_id = ?\n"
                + "                   UNION ALL\n"
                + "                   SELECT c.category_id \n"
                + "                   FROM Category c\n"
                + "                    INNER JOIN RecursiveCategory rc ON c.parent_id = rc.category_id\n"
                + "                )\n"
                + "                SELECT p.*, ISNULL(AVG(f.rating), 0) AS rating \n"
                + "                FROM Product p\n"
                + "               LEFT JOIN Feedback f ON p.product_id = f.product_id \n"
                + "                WHERE p.category_id IN (SELECT category_id FROM RecursiveCategory)\n"
                + "               GROUP BY p.product_id, p.category_id, p.product_name, p.description, \n"
                + "                  p.discount, p.status, p.thumbnail, p.created_at\n"
                + "				  order by p.created_at\n"
                + "				  OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
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

    public List<ProductTuan> getAllProductsByCategoryId(int id) {
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
        String sql = "SELECT category_id, name, parent_id FROM Category";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                categories.add(new CategoryTuan(
                        rs.getInt("category_id"),
                        rs.getString("name"),
                        rs.getObject("parent_id") != null ? rs.getInt("parent_id") : null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public List<CategoryTuan> buildCategoryTree(List<CategoryTuan> categories) {
        Map<Integer, CategoryTuan> categoryMap = new HashMap<>();
        List<CategoryTuan> rootCategories = new ArrayList<>();

        // Đưa tất cả danh mục vào HashMap
        for (CategoryTuan category : categories) {
            categoryMap.put(category.getId(), category);
        }

        // Xây dựng cây danh mục
        for (CategoryTuan category : categories) {
            if (category.getParentId() == null) {
                rootCategories.add(category);
            } else {
                CategoryTuan parent = categoryMap.get(category.getParentId());
                if (parent != null) {
                    parent.getChildren().add(category);
                }
            }
        }
        return rootCategories;
    }

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
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

}
