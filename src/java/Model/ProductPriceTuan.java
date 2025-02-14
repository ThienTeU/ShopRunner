package Model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author tuan
 */
public class ProductPriceTuan {
    private int productPriceId;
    private int productId;
    private int colorId;
    private int price;

    public ProductPriceTuan(int productPriceId, int productId, int colorId, int price) {
        this.productPriceId = productPriceId;
        this.productId = productId;
        this.colorId = colorId;
        this.price = price;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ProductPrice{");
        sb.append("productPriceId=").append(productPriceId);
        sb.append(", productId=").append(productId);
        sb.append(", colorId=").append(colorId);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }

    public int getProductPriceId() {
        return productPriceId;
    }

    public void setProductPriceId(int productPriceId) {
        this.productPriceId = productPriceId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
}
