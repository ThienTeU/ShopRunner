/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author admin
 */
public class Revenue {
    private int revenue_id;
    private String date;
    private int total_orders;
    private int total_revenue;

    public Revenue() {
    }

    public Revenue(int revenue_id, String date, int total_orders, int total_revenue) {
        this.revenue_id = revenue_id;
        this.date = date;
        this.total_orders = total_orders;
        this.total_revenue = total_revenue;
    }

    public int getRevenue_id() {
        return revenue_id;
    }

    public void setRevenue_id(int revenue_id) {
        this.revenue_id = revenue_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal_orders() {
        return total_orders;
    }

    public void setTotal_orders(int total_orders) {
        this.total_orders = total_orders;
    }

    public int getTotal_revenue() {
        return total_revenue;
    }

    public void setTotal_revenue(int total_revenue) {
        this.total_revenue = total_revenue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Revenue{");
        sb.append("revenue_id=").append(revenue_id);
        sb.append(", date=").append(date);
        sb.append(", total_orders=").append(total_orders);
        sb.append(", total_revenue=").append(total_revenue);
        sb.append('}');
        return sb.toString();
    }
    
}
