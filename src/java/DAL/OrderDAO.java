/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.OrderDetails;
import Model.Orders;
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
    
    

    public List<Orders> getAllOrders() throws SQLException {
        List<Orders> list = new ArrayList<>();
        String sql = "SELECT * FROM Orders";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Orders order = new Orders(
                        rs.getString("email"),
                        rs.getInt("user_id"),
                        rs.getString("order_date"),
                        rs.getInt("total_price"),
                        rs.getString("status"),
                        rs.getInt("voucher_id"),
                        rs.getString("shipping_address")
                );
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
                        rs.getInt("order_detail_id"),
                        rs.getInt("order_id"),
                        rs.getInt("ProductPrice_id"),
                        rs.getInt("size_id"),
                        rs.getInt("quantity"),
                        rs.getInt("unit_price")
                );
                list.add(orderDetail);
            }
        }
        return list;
    }

}

