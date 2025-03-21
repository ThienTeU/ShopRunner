/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAL.InsertProductDAO;
import DAL.ProductDAO;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author admin
 */
public class Product {

    private int product_id;
    private int category_id;
    private String product_name;
    private String description;
    private int discount;
    private boolean status;
    private String thumbnail;
    private String created_at;
    private double price;

    public Product() {
    }
    
    public int getView() throws SQLException{
        ProductDAO dao = new ProductDAO();
        return dao.getViewById(product_id);
    }
    public boolean isExistedId() throws SQLException{
        InsertProductDAO dao = new InsertProductDAO();
        return dao.isExistedProductId(product_id);
    }
    public List<ProductPrice> getProductPricesByProductId() throws SQLException{
        ProductDAO dao = new ProductDAO();
        List<ProductPrice> list = dao.getProductPricesByProductId(product_id);  
        return list;
    }
    public static void main(String[] args) {
        Product p = new Product();
        System.out.println(p.isWithin10Days("2025-02-24 14:12:12.123"));;
    }
    public boolean isWithin10Days(String inputTime) {
        try {
            // Định dạng thời gian đầu vào
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // Chuyển chuỗi đầu vào thành đối tượng Date
            java.util.Date inputDate = sdf.parse(inputTime);

            // Lấy thời gian hiện tại
            java.util.Date currentDate = new java.util.Date();

            // Tính toán sự khác biệt giữa thời gian hiện tại và thời gian đầu vào (tính bằng mili giây)
            long diffInMillies = currentDate.getTime() - inputDate.getTime();

            // Tính toán sự khác biệt trong ngày (1 ngày = 24 * 60 * 60 * 1000 mili giây)
            long diffInDays = diffInMillies / (24 * 60 * 60 * 1000);

            // Kiểm tra nếu sự khác biệt ít hơn 10 ngày
            return diffInDays < 10;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Nếu có lỗi trong quá trình phân tích chuỗi thời gian, trả về false
        }
    }

    public Product(int product_id, int category_id, String product_name, String description, int discount, boolean status, String thumbnail, String created_at) {
        this.product_id = product_id;
        this.category_id = category_id;
        this.product_name = product_name;
        this.description = description;
        this.discount = discount;
        this.status = status;
        this.thumbnail = thumbnail;
        this.created_at = created_at;
    }

     public Product(int product_id, int category_id, String product_name, String description, int discount, boolean status, String thumbnail, String created_at, double price) {
        this.product_id = product_id;
        this.category_id = category_id;
        this.product_name = product_name;
        this.description = description;
        this.discount = discount;
        this.status = status;
        this.thumbnail = thumbnail;
        this.created_at = created_at;
        this.price = price;
    }
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at.substring(0,10);
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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
        sb.append("Product{");
        sb.append("product_id=").append(product_id);
        sb.append(", category_id=").append(category_id);
        sb.append(", product_name=").append(product_name);
        sb.append(", description=").append(description);
        sb.append(", discount=").append(discount);
        sb.append(", status=").append(status);
        sb.append(", thumbnail=").append(thumbnail);
        sb.append(", created_at=").append(created_at);
        sb.append('}');
        return sb.toString();
    }

}
