/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author admin
 */
public class CartItem {
    private int product_id;
    private int productprice_id;
    private int productquantity_id;
    private int quantity;
    public CartItem() {
    }

    public CartItem(int product_id, int productprice_id, int productquantity_id, int quantity) {
        this.product_id = product_id;
        this.productprice_id = productprice_id;
        this.productquantity_id = productquantity_id;
        this.quantity = quantity;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getProductprice_id() {
        return productprice_id;
    }

    public void setProductprice_id(int productprice_id) {
        this.productprice_id = productprice_id;
    }

    public int getProductquantity_id() {
        return productquantity_id;
    }

    public void setProductquantity_id(int productquantity_id) {
        this.productquantity_id = productquantity_id;
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
        sb.append("CartItem{");
        sb.append("product_id=").append(product_id);
        sb.append(", productprice_id=").append(productprice_id);
        sb.append(", productquantity_id=").append(productquantity_id);
        sb.append(", quantity=").append(quantity);
        sb.append('}');
        return sb.toString();
    }
    
}
