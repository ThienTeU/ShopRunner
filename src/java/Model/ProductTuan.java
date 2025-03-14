package Model;


import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author tuan
 */
public class ProductTuan {
    private int productId;
    private int categoryId;
    private String productName;
    private String description;
    private int discount;
    private int status;
    private String thumbnail;
    private String created_at;
    private double rating;
    private List<ColorTuan> colors;
    private List<ProductPriceTuan> prices;

    public ProductTuan() {
    }
    public boolean isWithin10Days(String inputTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            java.util.Date inputDate = sdf.parse(inputTime);
            java.util.Date currentDate = new java.util.Date();
            long diffInMillies = currentDate.getTime() - inputDate.getTime();
            long diffInDays = diffInMillies / (24 * 60 * 60 * 1000);
            return diffInDays < 10;
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ProductTuan(int productId, int categoryId, String productName, String description, int discount, int status, String thumbnail, String created_at, double rating, List<ColorTuan> colors, List<ProductPriceTuan> prices) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.productName = productName;
        this.description = description;
        this.discount = discount;
        this.status = status;
        this.thumbnail = thumbnail;
        this.created_at = created_at;
        this.rating = rating;
        this.colors = colors;
        this.prices = prices;
    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public List<ColorTuan> getColors() {
        return colors;
    }

    public void setColors(List<ColorTuan> colors) {
        this.colors = colors;
    }

    public List<ProductPriceTuan> getPrices() {
        return prices;
    }

    public void setPrices(List<ProductPriceTuan> prices) {
        this.prices = prices;
    }
    
    public List<ProductPriceTuan> getSortedPrices() {
        Collections.sort(prices, (a, b) -> Double.compare(a.getPrice(), b.getPrice()));
        return prices;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Product{");
        sb.append("productId=").append(productId);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", productName=").append(productName);
        sb.append(", description=").append(description);
        sb.append(", discount=").append(discount);
        sb.append(", status=").append(status);
        sb.append(", thumbnail=").append(thumbnail);
        sb.append(", created_at=").append(created_at);
        sb.append(", rating=").append(rating);
        sb.append(", colors=").append(colors);
        sb.append(", prices=").append(prices);
        sb.append('}');
        return sb.toString();
    }
    
}
