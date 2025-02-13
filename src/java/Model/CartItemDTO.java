/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author admin
 */
public class CartItemDTO {
    private Product product;
    private ProductPrice productPrice;
    private ProductQuantity productQuantity;
    private int quantity;

    // Constructors, getters, setters

    public CartItemDTO() {
    }

    public CartItemDTO(Product product, ProductPrice productPrice, ProductQuantity productQuantity, int quantity) {
        this.product = product;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.quantity = quantity;
    }
    
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product productId) {
        this.product = product;
    }

    public ProductPrice getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(ProductPrice productPriceId) {
        this.productPrice = productPriceId;
    }

    public ProductQuantity getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(ProductQuantity productQuantityId) {
        this.productQuantity = productQuantityId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
}

