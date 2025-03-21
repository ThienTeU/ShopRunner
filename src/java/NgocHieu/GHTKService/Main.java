/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NgocHieu.GHTKService;

/**
 *
 * @author admin
 */
import DAL.OrderDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            OrderDAO dao = new OrderDAO();
            
            ProductGhtk product1 = new ProductGhtk("Giày", 0.1, 1, 1241);
            ProductGhtk product2 = new ProductGhtk("Áo", 0.2, 1, 1254);
            List<ProductGhtk> list = new ArrayList<>();
            list.add(product1);
            list.add(product2);
            OrderGhtk order = new OrderGhtk(
                "128", "0911222333", "Duong Ngoc Hieu",
             "123 nguyễn chí thanh", "TP. Hồ Chí Minh", "Quận 1", "Phường Bến Nghé", 100000,1000000
            );

            OrderRequest orderRequest = new OrderRequest(list, order);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBody = objectMapper.writeValueAsString(orderRequest);
//            System.out.println(jsonBody);
            GHTKApiService service = new GHTKApiService();
            
            // Chuyển JSON response thành object OrderResponse
            OrderResponse orderResponse = service.sendOrderToGHTK(jsonBody);
            
            // In ra thông tin đơn hàng
            System.out.println("Success: " + orderResponse.isSuccess());
            System.out.println("Message: " + orderResponse.getMessage());
            System.out.println(orderResponse.getOrder().toString());
            
            System.out.println("-----------");
            System.out.println("Hủy đơn----------");
            
            CancelResponse cancelResponse = service.cancelOrder(orderResponse.getOrder().getLabel(), orderResponse.getOrder().getOrderId());
            System.out.println(cancelResponse);
            
            System.out.println(service.getOrderByTrackingId(orderResponse.getOrder().getTrackingId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
//            GHTKApiService service = new GHTKApiService();
//            service.cancelOrder("S19268530.MB21-02-B3.1212836697");
    }
}

