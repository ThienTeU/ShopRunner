package Model;


public class OrderDetailAnh {

    private int orderId;
    private int productId;
    private String productName;
    private String imageUrl;
    private int quantity;
    private double unitPrice;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public OrderDetailAnh() {
    }

    public OrderDetailAnh(int orderId, int productId, String productName, String imageUrl, int quantity, double unitPrice) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
    
    @Override
public String toString() {
    return "OrderDetailAnh{" +
            "orderId=" + orderId +
            ", productId=" + productId +
            ", productName='" + productName + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", quantity=" + quantity +
            ", unitPrice=" + unitPrice +
            '}';
}

}
