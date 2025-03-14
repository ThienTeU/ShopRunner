/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAL.InsertProductDAO;
import DAL.ProductDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author admin
 */
public class ProductQuantity {
    private int ProductQuantity_id;
    private int ProductPrice_id;
    private int size_id;
    private int quantity;

    public ProductQuantity() {
    }
     public boolean isExistedProductQuantityId() throws SQLException{
        InsertProductDAO dao = new InsertProductDAO();
        return dao.isExistedProductQuantityId(ProductQuantity_id);
    }
    public List<Size> getAllSize() throws SQLException{
        ProductDAO dao = new ProductDAO();
        List<Size> list = dao.getAllSizes();
        return list;
    }

    public ProductQuantity(int ProductQuantity_id, int ProductPrice_id, int size_id, int quantity) {
        this.ProductQuantity_id = ProductQuantity_id;
        this.ProductPrice_id = ProductPrice_id;
        this.size_id = size_id;
        this.quantity = quantity;
    }

    public int getProductQuantity_id() {
        return ProductQuantity_id;
    }

    public void setProductQuantity_id(int ProductQuantity_id) {
        this.ProductQuantity_id = ProductQuantity_id;
    }

    public int getProductPrice_id() {
        return ProductPrice_id;
    }

    public void setProductPrice_id(int ProductPrice_id) {
        this.ProductPrice_id = ProductPrice_id;
    }

    public int getSize_id() {
        return size_id;
    }

    public void setSize_id(int size_id) {
        this.size_id = size_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ProductQuantity{");
        sb.append("ProductQuantity_id=").append(ProductQuantity_id);
        sb.append(", ProductPrice_id=").append(ProductPrice_id);
        sb.append(", size_id=").append(size_id);
        sb.append(", quantity=").append(quantity);
        sb.append('}');
        return sb.toString();
    }
    
}
