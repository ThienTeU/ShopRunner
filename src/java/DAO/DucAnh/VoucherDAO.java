/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.DucAnh;

import DTO.DucAnh.Voucher;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author msi
 */
public class VoucherDAO extends DBcontext {

    public List<Voucher> listVoucher() {
        String sql = "SELECT [VoucherID]\n"
                + "      ,[Name]\n"
                + "      ,[VoucherCode]\n"
                + "      ,[DateStart]\n"
                + "      ,[DateEnd]\n"
                + "      ,[Discount]\n"
                + "      ,[Quantity]\n"
                + "  FROM [dbo].[Voucher] ORDER BY VoucherID DESC";
        List<Voucher> lv = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Voucher v = new Voucher();
                v.setVourcherID(rs.getInt(1));
                v.setName(rs.getString(2));
                v.setVoucherCode(rs.getString(3));
                v.setStart(rs.getString(4));
                v.setEnd(rs.getString(5));
                v.setDiscount(rs.getInt(6));
                v.setQuantity(rs.getInt(7));
                lv.add(v);
            }
        } catch (Exception e) {
        }
        return lv;
    }

    public String getVoucherCodeById(int voucherId) {
        // SQL query to get VoucherCode by VoucherID
        String sql = "SELECT VoucherCode "
                + "FROM [dbo].[Voucher] "
                + "WHERE VoucherID = ?";

        try {
            // Prepare the SQL statement
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, voucherId);

            // Execute the query and get the result
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("VoucherCode"); // Return the VoucherCode
            }
        } catch (Exception e) {
            // Handle exceptions properly
            e.printStackTrace();
        }

        return null; // Return null if no VoucherCode found or any exception occurs
    }

    public List<Voucher> getidVoucher(String id) {
        String sql = "SELECT [VoucherID],[Name],[VoucherCode],[DateStart],[DateEnd],[Quantity],[Discount]\n"
                + "  FROM [dbo].[Voucher] where VoucherCode = ?";
        List<Voucher> lv = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Voucher v = new Voucher();
                v.setVourcherID(rs.getInt(1));
                v.setName(rs.getString(2));
                v.setVoucherCode(rs.getString(3));
                v.setStart(rs.getString(4));
                v.setEnd(rs.getString(5));
                v.setQuantity(rs.getInt(6));
                v.setDiscount(rs.getInt(7));
                lv.add(v);
            }
        } catch (Exception e) {
        }
        return lv;
    }

    public List<Voucher> getQuantityVoucher(String id) {
        String sql = "SELECT [VoucherID],[Name],[VoucherCode],[DateStart],[DateEnd],[Quantity],[Discount]\n"
                + "  FROM [dbo].[Voucher] where VoucherCode = ? and Quantity = 0 ";
        List<Voucher> lv = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Voucher v = new Voucher();
                v.setVourcherID(rs.getInt(1));
                v.setName(rs.getString(2));
                v.setVoucherCode(rs.getString(3));
                v.setStart(rs.getString(4));
                v.setEnd(rs.getString(5));
                v.setQuantity(rs.getInt(6));
                v.setDiscount(rs.getInt(7));
                lv.add(v);
            }
        } catch (Exception e) {
        }
        return lv;
    }

    public int getVoucherIdByCode(String voucherCode) {
        // SQL query to get VoucherID by VoucherCode
        String sql = "SELECT VoucherID "
                + "FROM [dbo].[Voucher] "
                + "WHERE VoucherCode = ?";

        try {
            // Prepare the SQL statement
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, voucherCode);

            // Execute the query and get the result
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("VoucherID"); // Return the VoucherID
            }
        } catch (Exception e) {
            // Handle exceptions properly
            e.printStackTrace();
        }

        return -1; // Return -1 if no VoucherID found or any exception occurs
    }

    public int getDiscountByVoucherCode(String voucherCode) {
        // SQL query to get Discount by VoucherCode
        String sql = "SELECT Discount "
                + "FROM [dbo].[Voucher] "
                + "WHERE VoucherCode = ?";

        try {
            // Prepare the SQL statement
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, voucherCode);

            // Execute the query and get the result
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("Discount"); // Return the Discount value
            }
        } catch (Exception e) {
            // Handle exceptions properly
            e.printStackTrace();
        }

        return 1; // Return -1 if no Discount is found or any exception occurs
    }

    public boolean isVoucherValid(String voucherCode) {
        // SQL query to check voucher validity
        String sql = "SELECT COUNT(*) "
                + "FROM [dbo].[Voucher] "
                + "WHERE VoucherCode = ? "
                + "AND Quantity > 0 "
                + "AND GETDATE() BETWEEN DateStart AND DateEnd";

        try {
            // Prepare the SQL statement
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, voucherCode);

            // Execute the query and check if any valid rows exist
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Return true if the voucher is valid, otherwise false
            }
        } catch (Exception e) {
            // Handle exceptions properly
            e.printStackTrace();
        }

        return false; // Return false if any exception occurs or voucher is not valid
    }

    public List<Voucher> getDateVoucher(String name, String id) {
        String sql = "SELECT [VoucherID],[Name],[VoucherCode],[DateStart],[DateEnd],[Quantity],[Discount]\n"
                + "                 FROM [dbo].[Voucher] where VoucherCode = ? and [DateEnd] >= ? ";
        List<Voucher> lv = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Voucher v = new Voucher();
                v.setVourcherID(rs.getInt(1));
                v.setName(rs.getString(2));
                v.setVoucherCode(rs.getString(3));
                v.setStart(rs.getString(4));
                v.setEnd(rs.getString(5));
                v.setQuantity(rs.getInt(6));
                v.setDiscount(rs.getInt(7));
                lv.add(v);
            }
        } catch (Exception e) {
        }
        return lv;
    }

    public List<Voucher> getDateStartVoucher(String name, String id) {
        String sql = "SELECT [VoucherID],[Name],[VoucherCode],[DateStart],[DateEnd],[Quantity],[Discount]\n"
                + "                 FROM [dbo].[Voucher] where VoucherCode = ? and [DateStart] <= ?  ";
        List<Voucher> lv = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Voucher v = new Voucher();
                v.setVourcherID(rs.getInt(1));
                v.setName(rs.getString(2));
                v.setVoucherCode(rs.getString(3));
                v.setStart(rs.getString(4));
                v.setEnd(rs.getString(5));
                v.setQuantity(rs.getInt(6));
                v.setDiscount(rs.getInt(7));
                lv.add(v);
            }
        } catch (Exception e) {
        }
        return lv;
    }

    public void addVoucher(String name, String code, String start, String end, int discount, int quantity) {
        String sql = "INSERT INTO [dbo].[Voucher]\n"
                + "           ([Name]\n"
                + "           ,[VoucherCode]\n"
                + "           ,[DateStart]\n"
                + "           ,[DateEnd]\n"
                + "           ,[Discount]\n"
                + "           ,[Quantity])\n"
                + "     VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, code);
            ps.setString(3, start);
            ps.setString(4, end);
            ps.setInt(5, discount);
            ps.setInt(6, quantity);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void UpdateVoucher(String name, String code, String start, String end, int discount, int quantity) {
        String sql = "UPDATE [dbo].[Voucher]\n"
                + "SET [Name] = ?,\n"
                + "    [DateStart] = ?,\n"
                + "    [DateEnd] = ?,\n"
                + "    [Discount] = ?,\n"
                + "    [Quantity] = ?\n"
                + "WHERE [VoucherCode] = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);

            ps.setString(2, start);
            ps.setString(3, end);
            ps.setInt(4, discount);
            ps.setInt(5, quantity);
            ps.setString(6, code);

            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Voucher getDataByName(String name) {
        Voucher voucher = null;
        String sql = "SELECT * FROM [dbo].[Voucher] WHERE [Name] = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                Voucher v = new Voucher();
                v.setDiscount(rs.getInt("Discount"));
                v.setEnd(rs.getString("DateEnd"));
                v.setName(rs.getString("Name"));
                v.setQuantity(rs.getInt("Quantity"));
                v.setVoucherCode(rs.getString("VoucherCode"));
                v.setStart(rs.getString("DateStart"));
                return v;
//      [VoucherID]
//      ,[Name]
//      ,[VoucherCode]
//      ,[DateStart]
//      ,[DateEnd]
//      ,[Quantity]
//      ,[Discount]
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return voucher;
    }

    public void UpdateQuantityVoucher(String name, String quantity) {
        String sql = "UPDATE [dbo].[Voucher] SET [Quantity] = ? WHERE [VoucherCode] = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, quantity);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void decrementVoucherQuantity(String voucherCode) {
        String sql = "UPDATE [dbo].[Voucher] SET [Quantity] = [Quantity] - 1 WHERE [VoucherCode] = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, voucherCode);
            int rowsUpdated = ps.executeUpdate(); 

            if (rowsUpdated > 0) {
            } else {
            }
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        VoucherDAO voucherDAO = new VoucherDAO();
//            voucherDAO.UpdateQuantityVoucher("BOGOCODE", "15");
        System.out.println(voucherDAO.getDiscountByVoucherCode("1"));
        System.out.println(voucherDAO.isVoucherValid("1"));
    }
}
