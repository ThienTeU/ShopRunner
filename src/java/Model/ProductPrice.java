/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAL.InsertProductDAO;
import DAL.ProductDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class ProductPrice {
    private int ProductPrice_id;
    private int product_id;
    private int color_id;
    private double price;

    public ProductPrice() {
    }
    
     public boolean isExistedProductPriceId() throws SQLException{
        InsertProductDAO dao = new InsertProductDAO();
        return dao.isExistedProductPriceId(ProductPrice_id);
    }
    
    public List<ProductQuantity> getAllProductQuantitesById() throws SQLException{
        ProductDAO dao = new ProductDAO();
        List<ProductQuantity> list = dao.getProductQuantitiesByProductPriceId(ProductPrice_id);
        return list;
    }
    
    public List<Color> getAllColor() throws SQLException{
        ProductDAO dao = new ProductDAO();
        List<Color> list = dao.getAllColors();
        return list;
    }

    public ProductPrice(int ProductPrice_id, int product_id, int color_id, double price) {
        this.ProductPrice_id = ProductPrice_id;
        this.product_id = product_id;
        this.color_id = color_id;
        this.price = price;
    }

    public int getProductPrice_id() {
        return ProductPrice_id;
    }

    public void setProductPrice_id(int ProductPrice_id) {
        this.ProductPrice_id = ProductPrice_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getColor_id() {
        return color_id;
    }

    public void setColor_id(int color_id) {
        this.color_id = color_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ProductPrice{");
        sb.append("ProductPrice_id=").append(ProductPrice_id);
        sb.append(", product_id=").append(product_id);
        sb.append(", color_id=").append(color_id);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
    
    
}
