/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NgocHieu.GHTKService;

/**
 *
 * @author admin
 */
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class OrderRequest {
    private List<ProductGhtk> products;
    private OrderGhtk order;

    public OrderRequest(List<ProductGhtk> products, OrderGhtk order) {
        this.products = products;
        this.order = order;
    }
    
    public String toJsonBody(OrderRequest orderRequest) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(orderRequest);
    }

    public List<ProductGhtk> getProducts() { return products; }
    public OrderGhtk getOrder() { return order; }
}

