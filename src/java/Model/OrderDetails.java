/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author admin
 */
public class OrderDetails {
    private int order_detail_id;
    private int order_id;
    private int ProductPrice_id;
    private int size_id;
    private int quantity;
    private double unit_price;

    public OrderDetails() {
    }

    public OrderDetails(int order_id, int ProductPrice_id, int size_id, int quantity, double unit_price) {
        this.order_id = order_id;
        this.ProductPrice_id = ProductPrice_id;
        this.size_id = size_id;
        this.quantity = quantity;
        this.unit_price = unit_price;
    }

    public int getOrder_detail_id() {
        return order_detail_id;
    }

    public void setOrder_detail_id(int order_detail_id) {
        this.order_detail_id = order_detail_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
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

    public double getPrice() {
        return unit_price;
    }

    public void setPrice(double unit_price) {
        this.unit_price = unit_price;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("OrderDetails{");
        sb.append("order_detail_id=").append(order_detail_id);
        sb.append(", order_id=").append(order_id);
        sb.append(", ProductPrice_id=").append(ProductPrice_id);
        sb.append(", size_id=").append(size_id);
        sb.append(", quantity=").append(quantity);
        sb.append(", unit_price=").append(unit_price);
        sb.append('}');
        return sb.toString();
    }
    
}
