/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;
import Model.Voucher;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author admin
 */
public class VoucherDAO extends DBContext {
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    public List<Voucher> getAllVouchers() throws SQLException {
    List<Voucher> list = new ArrayList<>();
    String sql = "SELECT * FROM Voucher";
    
    try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            Voucher voucher = new Voucher(
                rs.getInt("voucher_id"),
                rs.getString("code"),
                rs.getInt("discount_value"),
                rs.getInt("min_order_value"),
                rs.getString("start_date"),
                rs.getString("end_date"),
                rs.getBoolean("status")
            );
            list.add(voucher);
        }
    }
    return list;
}

}
