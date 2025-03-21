/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author admin
 */
public class Orders {
    private int order_id;
    private String email;
    private String order_date;
    private int total_price;
    private String status;
    private int voucher_id;
    private String payment_method;
    private String shipping_address;
    private String phone;
    public Orders() {
    }

    public Orders(String email, int total_price, int voucher_id, String shipping_address, String phone) {
        this.email = email;
        this.total_price = total_price;
        this.voucher_id = voucher_id;
        this.shipping_address = shipping_address;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }
    

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getEmail() {
        return email;
    }

    public void setUser_id(String email) {
        this.email = email;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public int getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(int voucher_id) {
        this.voucher_id = voucher_id;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Orders{");
        sb.append("order_id=").append(order_id);
        sb.append(", email=").append(email);
        sb.append(", order_date=").append(order_date);
        sb.append(", total_price=").append(total_price);
        sb.append(", status=").append(status);
        sb.append(", voucher_id=").append(voucher_id);
        sb.append(", shipping_address=").append(shipping_address);
        sb.append('}');
        return sb.toString();
    }
    
}
