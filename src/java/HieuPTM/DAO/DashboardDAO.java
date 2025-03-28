package HieuPTM.DAO;

import HieuPTM.DBContext.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DashboardDAO extends DBContext {

    // Lấy số lượng đơn hàng theo trạng thái
    public Map<String, Integer> getOrderStatusCounts() {
        Map<String, Integer> orderStatusCounts = new HashMap<>();
        String sql = "SELECT status, COUNT(*) AS count FROM Orders GROUP BY status";

        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                orderStatusCounts.put(rs.getString("status"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderStatusCounts;
    }

    // Lấy doanh thu theo danh mục sản phẩm
    public Map<String, Double> getRevenueByCategory() {
        Map<String, Double> revenueByCategory = new HashMap<>();
        String sql = "SELECT c.name AS category_name, "
                + "SUM(od.quantity * od.unit_price) AS revenue "
                + "FROM OrderDetails od "
                + "JOIN Product p ON od.Product_id = p.product_id "
                + "JOIN Category c ON p.category_id = c.category_id "
                + "GROUP BY c.name "
                + "ORDER BY revenue DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                revenueByCategory.put(rs.getString("category_name"), rs.getDouble("revenue"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenueByCategory;
    }

    // Lấy số lượng khách hàng đăng ký mới trong 7 ngày qua
    public Map<String, Integer> getNewCustomersLast7Days() {
    Map<String, Integer> newCustomers = new HashMap<>();
    String sql = "SELECT CONVERT(varchar, created_at, 23) AS date, COUNT(*) AS count "
               + "FROM [RunnerShop].[dbo].[User] "
               + "WHERE role_id = 2 AND created_at >= DATEADD(DAY, -6, GETDATE()) "
               + "GROUP BY CONVERT(varchar, created_at, 23) "
               + "ORDER BY date";

    try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            newCustomers.put(rs.getString("date"), rs.getInt("count"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return newCustomers;
}


    // Lấy điểm đánh giá trung bình của từng danh mục
    public Map<String, Double> getFeedbackRatings() {
        Map<String, Double> feedbackRatings = new HashMap<>();
        String sql = "SELECT c.name AS category_name, AVG(f.rating) AS avg_rating "
           + "FROM Feedback f "
           + "JOIN Product p ON f.product_id = p.product_id "
           + "JOIN Category c ON p.category_id = c.category_id "
           + "GROUP BY c.name";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                feedbackRatings.put(rs.getString("category_name"), rs.getDouble("avg_rating"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackRatings;
    }

    // Lấy số lượng đơn hàng trong khoảng thời gian được chọn
    public Map<String, Integer> getOrderTrends(String startDate, String endDate) {
        Map<String, Integer> orderTrends = new HashMap<>();
        String sql = "SELECT CONVERT(varchar, order_date, 23) AS date, COUNT(*) AS count "
                + "FROM Orders "
                + "WHERE order_date BETWEEN ? AND ? "
                + "GROUP BY CONVERT(varchar, order_date, 23) "
                + "ORDER BY date";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, startDate);
            stmt.setString(2, endDate);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orderTrends.put(rs.getString("date"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderTrends;
    }

    public Map<String, Integer> getOrderStatus() {
        Map<String, Integer> orderStatus = new HashMap<>();
        String sql = "SELECT status, COUNT(*) AS count FROM Orders GROUP BY status";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                orderStatus.put(rs.getString("status"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderStatus;
    }

    public static void main(String[] args) {
        DashboardDAO dao = new DashboardDAO();

        // 1️⃣ Trạng thái đơn hàng
        Map<String, Integer> orderStatus = dao.getOrderStatus();
        System.out.println("🛒 Trạng thái đơn hàng:");
        orderStatus.forEach((status, count) -> System.out.println(status + ": " + count));

        // 2️⃣ Doanh thu theo danh mục
        Map<String, Double> revenue = dao.getRevenueByCategory();
        System.out.println("\n💰 Doanh thu theo danh mục:");
        revenue.forEach((category, amount) -> System.out.println(category + ": " + amount + " VND"));

        // 3️⃣ Số lượng khách hàng đăng ký mới trong 7 ngày qua
        Map<String, Integer> newCustomers = dao.getNewCustomersLast7Days();
        System.out.println("\n👥 Khách hàng đăng ký mới (7 ngày qua):");
        newCustomers.forEach((date, count) -> System.out.println(date + ": " + count + " khách"));

        // 4️⃣ Điểm đánh giá trung bình của từng danh mục
        Map<String, Double> feedbackRatings = dao.getFeedbackRatings();
        System.out.println("\n⭐ Điểm đánh giá trung bình theo danh mục:");
        feedbackRatings.forEach((category, rating) -> System.out.println(category + ": " + String.format("%.2f", rating) + " ⭐"));

        // 5️⃣ Xu hướng đơn hàng theo khoảng thời gian (Ví dụ: 2024-03-01 đến 2024-03-10)
        String startDate = "2024-03-01";
        String endDate = "2024-03-10";
        Map<String, Integer> orderTrends = dao.getOrderTrends(startDate, endDate);
        System.out.println("\n📊 Xu hướng đơn hàng từ " + startDate + " đến " + endDate + ":");
        orderTrends.forEach((date, count) -> System.out.println(date + ": " + count + " đơn"));
    }

}
