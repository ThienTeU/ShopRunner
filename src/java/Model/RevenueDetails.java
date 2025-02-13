/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author admin
 */
public class RevenueDetails {
    private int revenue_detail_id;
    private int revenue_id;
    private int product_id;
    private int quantity_sold;
    private int revenue;

    public RevenueDetails() {
    }

    public RevenueDetails(int revenue_detail_id, int revenue_id, int product_id, int quantity_sold, int revenue) {
        this.revenue_detail_id = revenue_detail_id;
        this.revenue_id = revenue_id;
        this.product_id = product_id;
        this.quantity_sold = quantity_sold;
        this.revenue = revenue;
    }

    public int getRevenue_detail_id() {
        return revenue_detail_id;
    }

    public void setRevenue_detail_id(int revenue_detail_id) {
        this.revenue_detail_id = revenue_detail_id;
    }

    public int getRevenue_id() {
        return revenue_id;
    }

    public void setRevenue_id(int revenue_id) {
        this.revenue_id = revenue_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity_sold() {
        return quantity_sold;
    }

    public void setQuantity_sold(int quantity_sold) {
        this.quantity_sold = quantity_sold;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RevenueDetails{");
        sb.append("revenue_detail_id=").append(revenue_detail_id);
        sb.append(", revenue_id=").append(revenue_id);
        sb.append(", product_id=").append(product_id);
        sb.append(", quantity_sold=").append(quantity_sold);
        sb.append(", revenue=").append(revenue);
        sb.append('}');
        return sb.toString();
    }
    
}
