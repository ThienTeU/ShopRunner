/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author admin
 */
public class Voucher {
    private int voucher_id;
    private String code;
    private int discount_value;
    private int min_order_value;
    private String start_date;
    private String end_date;
    private boolean status;

    public Voucher() {
    }

    public Voucher(int voucher_id, String code, int discount_value, int min_order_value, String start_date, String end_date, boolean status) {
        this.voucher_id = voucher_id;
        this.code = code;
        this.discount_value = discount_value;
        this.min_order_value = min_order_value;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
    }

    public int getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(int voucher_id) {
        this.voucher_id = voucher_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getDiscount_value() {
        return discount_value;
    }

    public void setDiscount_value(int discount_value) {
        this.discount_value = discount_value;
    }

    public int getMin_order_value() {
        return min_order_value;
    }

    public void setMin_order_value(int min_order_value) {
        this.min_order_value = min_order_value;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Voucher{");
        sb.append("voucher_id=").append(voucher_id);
        sb.append(", code=").append(code);
        sb.append(", discount_value=").append(discount_value);
        sb.append(", min_order_value=").append(min_order_value);
        sb.append(", start_date=").append(start_date);
        sb.append(", end_date=").append(end_date);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
    
}
