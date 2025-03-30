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
    private String product_name;
    private int price_item;
    private int quantity_item;
    private String voucher_id;
    private int total;
    private String shipping_address;

    public OrderDetails() {
    }

    public OrderDetails(int order_detail_id, String product_name, int price_item, int quantity_item, String voucher_id, int total, String shipping_address) {
        this.order_detail_id = order_detail_id;
        this.product_name = product_name;
        this.price_item = price_item;
        this.quantity_item = quantity_item;
        this.voucher_id = voucher_id;
        this.total = total;
        this.shipping_address = shipping_address;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getPrice_item() {
        return price_item;
    }

    public void setPrice_item(int price_item) {
        this.price_item = price_item;
    }

    public int getQuantity_item() {
        return quantity_item;
    }

    public void setQuantity_item(int quantity_item) {
        this.quantity_item = quantity_item;
    }

    public String getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(String voucher_id) {
        this.voucher_id = voucher_id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
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
        return "OrderDetails{" + "order_detail_id=" + order_detail_id + ", order_id=" + order_id + ", ProductPrice_id=" + ProductPrice_id + ", size_id=" + size_id + ", quantity=" + quantity + ", unit_price=" + unit_price + ", product_name=" + product_name + ", price_item=" + price_item + ", quantity_item=" + quantity_item + ", voucher_id=" + voucher_id + ", total=" + total + ", shipping_address=" + shipping_address + '}';
    }

    
}
