/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NgocHieu.GHTKService;

/**
 *
 * @author admin
 */
public class ProductGhtk {
    private String name;
    private double weight;
    private int quantity;
    private int product_code;

    public ProductGhtk(String name, double weight, int quantity, int product_code) {
        this.name = name;
        this.weight = weight;
        this.quantity = quantity;
        this.product_code = product_code;
    }

    public String getName() { return name; }
    public double getWeight() { return weight; }
    public int getQuantity() { return quantity; }
    public int getProduct_code() { return product_code; }
}

