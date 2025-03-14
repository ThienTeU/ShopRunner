/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.CartItem;
import Model.OrderDetails;
import Model.Orders;
import Model.ProductPrice;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class OrderDAO extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;

    public static void main(String[] args) throws SQLException {
        OrderDAO dao = new OrderDAO();
        Orders order = new Orders("hieu@gmail.com", 150000, -1, "NA", "0397761602");
        order.setPayment_method("vnpay");
        order.setStatus("paid");
        System.out.println(dao.insertOrder(order));

    }

    public boolean updateProductQuantity(int productQuantityId, int quantity) throws SQLException {
        String sql = "UPDATE ProductQuantity SET quantity = quantity - ? WHERE ProductQuantity_id = ? AND quantity >= ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, productQuantityId);
            ps.setInt(3, quantity); // Ensure quantity won't go below zero

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0; // Return true if update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if an error occurred
        }
    }

    public void insertOrderDetail(int orderId, CartItem cartItem) throws SQLException {
        String sql = "INSERT INTO OrderDetails (order_id, Product_id, ProductPrice_id, ProductQuantity_id, quantity, unit_price) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setInt(2, cartItem.getProduct_id());
            ps.setInt(3, cartItem.getProductprice_id());
            ps.setInt(4, cartItem.getProductquantity_id());
            ps.setInt(5, cartItem.getQuantity());
            ProductDAO dao = new ProductDAO();
            ProductPrice pp = dao.getProductPriceById(cartItem.getProductprice_id());
            ps.setDouble(6, pp.getPrice());
            updateProductQuantity(cartItem.getProductquantity_id(), cartItem.getQuantity());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer insertOrder(Orders order) throws SQLException {
        String sql = "INSERT INTO Orders (email, total_price, order_date, status, voucher_id, phone, payment_method, shipping_address) "
                + "VALUES (?, ?, DEFAULT, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, order.getEmail());
            ps.setInt(2, order.getTotal_price());
            ps.setString(3, order.getStatus());
            if (order.getVoucher_id() != -1) {
                ps.setInt(4, order.getVoucher_id());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            ps.setString(5, order.getPhone());
            ps.setString(6, order.getPayment_method());
            ps.setString(7, order.getShipping_address());
            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu INSERT thất bại
    }

    public List<Orders> getAllOrders() throws SQLException {
        List<Orders> list = new ArrayList<>();
        String sql = "SELECT * FROM Orders";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Orders order = new Orders(
                        rs.getString("email"),
                        rs.getInt("total_price"),
                        rs.getInt("voucher_id"),
                        rs.getString("shipping_address"),
                        rs.getString("phone")
                );
                order.setOrder_date(rs.getString("order_date"));
                order.setStatus(rs.getString("status"));
                order.setOrder_id(rs.getInt("order_id"));
                list.add(order);
            }
        }
        return list;
    }

    public List<OrderDetails> getAllOrderDetails() throws SQLException {
        List<OrderDetails> list = new ArrayList<>();
        String sql = "SELECT * FROM OrderDetails";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                OrderDetails orderDetail = new OrderDetails(
                        rs.getInt("order_id"),
                        rs.getInt("ProductPrice_id"),
                        rs.getInt("size_id"),
                        rs.getInt("quantity"),
                        rs.getInt("unit_price")
                );
                orderDetail.setOrder_id(rs.getInt("order_detail_id"));
                list.add(orderDetail);
            }
        }
        return list;
    }

}
