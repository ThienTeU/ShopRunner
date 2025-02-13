/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.Revenue;
import Model.RevenueDetails;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class RevenueDAO extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Revenue> getAllRevenue() throws SQLException {
        List<Revenue> list = new ArrayList<>();
        String sql = "SELECT * FROM Revenue";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Revenue revenue = new Revenue(
                        rs.getInt("revenue_id"),
                        rs.getString("date"),
                        rs.getInt("total_orders"),
                        rs.getInt("total_revenue")
                );
                list.add(revenue);
            }
        }
        return list;
    }

    public List<RevenueDetails> getAllRevenueDetails() throws SQLException {
        List<RevenueDetails> list = new ArrayList<>();
        String sql = "SELECT * FROM RevenueDetails";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RevenueDetails revenueDetail = new RevenueDetails(
                        rs.getInt("revenue_detail_id"),
                        rs.getInt("revenue_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity_sold"),
                        rs.getInt("revenue")
                );
                list.add(revenueDetail);
            }
        }
        return list;
    }

}
