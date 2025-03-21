/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class ManageProductDAO extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;
    public int updateProductPrice(int newPrice,int productPriceId) throws SQLException{
        String query = "UPDATE dbo.ProductPrice SET price = ? WHERE ProductPrice_id =	?";
        try{
            ps = connection.prepareStatement(query);
            ps.setInt(1, newPrice);
            ps.setInt(2, productPriceId);
            int rowUpdated = ps.executeUpdate();
            return rowUpdated;
        } finally{
            if(ps !=null){
                ps.close();
            }
        }
    }
    public int updateProductQuantity(int newQuantity,int productQuantityId) throws SQLException{
        String query = "UPDATE dbo.ProductQuantity SET quantity = ? WHERE ProductQuantity_id =	?";
        try{
            ps = connection.prepareStatement(query);
            ps.setInt(1, newQuantity);
            ps.setInt(2, productQuantityId);
            int rowUpdated = ps.executeUpdate();
            return rowUpdated;
        } finally{
            if(ps !=null){
                ps.close();
            }
        }
    }
    public int updateProductStatus(int status,int product_id) throws SQLException {
        String query = "UPDATE dbo.Product SET status = ? WHERE product_id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, status);
            ps.setInt(2, product_id);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated;
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }
    public static void main(String[] args) throws SQLException {
        ManageProductDAO dao = new ManageProductDAO();
        dao.updateProductStatus(0, 1);
    }
}
