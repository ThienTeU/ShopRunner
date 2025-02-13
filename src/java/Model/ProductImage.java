/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author admin
 */
public class ProductImage {
    private int image_id;
    private int product_id;
    private String image_url;
    private int color_id;

    public ProductImage() {
    }

    public ProductImage(int image_id, int product_id, String image_url, int color_id) {
        this.image_id = image_id;
        this.product_id = product_id;
        this.image_url = image_url;
        this.color_id = color_id;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getColor_id() {
        return color_id;
    }

    public void setColor_id(int color_id) {
        this.color_id = color_id;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ProductImage{");
        sb.append("image_id=").append(image_id);
        sb.append(", product_id=").append(product_id);
        sb.append(", image_url=").append(image_url);
        sb.append(", color_id=").append(color_id);
        sb.append('}');
        return sb.toString();
    }
     
}
