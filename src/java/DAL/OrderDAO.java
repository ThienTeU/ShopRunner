/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.CartItem;
import Model.OrderDetails;
import Model.Orders;
import Model.ProductPrice;
import NgocHieu.GHTKService.OrderResponseInfo;
import NgocHieu.GHTKService.OrderResponse;
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
//        Orders order = new Orders("hieu@gmail.com", 150000, -1, "NA", "0397761602");
//        order.setPayment_method("vnpay");
//        order.setStatus("paid");
//        int order_id = dao.insertOrder(order);
//        List<OrderResponse> list = dao.getAllOrderResponse();
//        for (OrderResponse o : list) {
//            System.out.println(o.getOrder().getLabel());
//        }
    dao.restoreProductQuantity(2116);

    }
    
    public void restoreProductQuantity(int orderId) {
        String query = "UPDATE pq "
                + "SET pq.quantity = pq.quantity + od.quantity "
                + "FROM ProductQuantity pq "
                + "INNER JOIN OrderDetails od ON pq.ProductQuantity_id = od.ProductQuantity_id "
                + "WHERE od.order_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, orderId);
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Đã cập nhật lại số lượng sản phẩm cho đơn hàng #" + orderId);
            } else {
                System.out.println(" Không có sản phẩm nào cần cập nhật số lượng.");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật số lượng sản phẩm khi hủy đơn: " + e.getMessage());
        }
    }

    public List<OrderResponse> getAllOrderResponse() {
        List<OrderResponse> orderList = new ArrayList<>();
        String query = "SELECT * FROM OrderResponse ORDER BY id DESC";

        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Tạo đối tượng OrderInfo từ dữ liệu database
                OrderResponseInfo orderInfo = new OrderResponseInfo();
                orderInfo.setOrderId(rs.getString("order_id"));
                orderInfo.setLabel(rs.getString("label"));
                orderInfo.setArea(rs.getInt("area"));
                orderInfo.setFee(rs.getInt("fee"));
                orderInfo.setInsuranceFee(rs.getInt("insurance_fee"));
                orderInfo.setEstimatedPickTime(rs.getString("estimated_pick_time"));
                orderInfo.setEstimatedDeliverTime(rs.getString("estimated_deliver_time"));
                orderInfo.setTrackingId(rs.getLong("tracking_id"));
                orderInfo.setSortingCode(rs.getString("sorting_code"));
                orderInfo.setStatusId(rs.getInt("status_id"));
                orderInfo.setPackageId(rs.getString("package_id"));

                // Tạo đối tượng OrderResponse
                OrderResponse orderResponse = new OrderResponse();
                orderResponse.setSuccess(rs.getBoolean("success"));
                orderResponse.setMessage(rs.getString("message"));
                orderResponse.setOrder(orderInfo);

                orderList.add(orderResponse);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    public void updateStatusToCancelledInOrder(String order_id) {
        String query = "UPDATE dbo.Orders SET status = 'Canceled' WHERE order_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, Integer.parseInt(order_id));
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Đã hủy đơn hàng có label: " + order_id);
            } else {
                System.out.println("Không tìm thấy đơn hàng với label: " + order_id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStatusInOrderResponse(String label, int status_id) {
        String query = "UPDATE dbo.OrderResponse SET status_id = ? WHERE label = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, status_id);
            ps.setString(2, label);
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Đã capaj nhaajt status đơn hàng có label: " + label);
            } else {
                System.out.println("Không tìm thấy đơn hàng với label: " + label);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveOrder(OrderResponse orderResponse) {
        String query = "INSERT INTO dbo.OrderResponse (order_id, label, area, fee, insurance_fee, estimated_pick_time, "
                + "estimated_deliver_time, tracking_id, sorting_code, status_id, package_id, success, message) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            OrderResponseInfo order = orderResponse.getOrder();

            ps.setString(1, order.getOrderId());
            ps.setString(2, order.getLabel());
            ps.setInt(3, order.getArea());
            ps.setInt(4, order.getFee());
            ps.setInt(5, order.getInsuranceFee());
            ps.setString(6, order.getEstimatedPickTime());
            ps.setString(7, order.getEstimatedDeliverTime());
            ps.setLong(8, order.getTrackingId());
            ps.setString(9, order.getSortingCode());
            ps.setInt(10, order.getStatusId());
            ps.setString(11, order.getPackageId());
            ps.setBoolean(12, orderResponse.isSuccess());
            ps.setString(13, orderResponse.getMessage());

            ps.executeUpdate();
            System.out.println("Đơn hàng đã được lưu vào SQL Server.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy đơn hàng từ database bằng tracking_id
    public OrderResponse getOrderByTrackingId(long trackingId) {
        String query = "SELECT * FROM dbo.OrderResponse WHERE tracking_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, trackingId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                OrderResponseInfo orderInfo = new OrderResponseInfo();
                orderInfo.setOrderId(rs.getString("order_id"));
                orderInfo.setLabel(rs.getString("label"));
                orderInfo.setArea(rs.getInt("area"));
                orderInfo.setFee(rs.getInt("fee"));
                orderInfo.setInsuranceFee(rs.getInt("insurance_fee"));
                orderInfo.setEstimatedPickTime(rs.getString("estimated_pick_time"));
                orderInfo.setEstimatedDeliverTime(rs.getString("estimated_deliver_time"));
                orderInfo.setTrackingId(rs.getLong("tracking_id"));
                orderInfo.setSortingCode(rs.getString("sorting_code"));
                orderInfo.setStatusId(rs.getInt("status_id"));
                orderInfo.setPackageId(rs.getString("package_id"));

                OrderResponse orderResponse = new OrderResponse();
                orderResponse.setSuccess(rs.getBoolean("success"));
                orderResponse.setMessage(rs.getString("message"));
                orderResponse.setOrder(orderInfo);

                return orderResponse;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public int getTotalOrdersByCustomer(String email) {
        String sql = "SELECT COUNT(*) AS total_orders FROM Orders WHERE email = ?";
        int totalOrders = 0;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email); // Gán giá trị email vào câu lệnh SQL
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalOrders = rs.getInt("total_orders"); // Lấy tổng số đơn hàng
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalOrders; // Trả về tổng số đơn hàng
    }

}
