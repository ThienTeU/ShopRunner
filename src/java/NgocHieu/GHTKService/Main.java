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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            OrderDAO dao = new OrderDAO();
            
            ProductGhtk product1 = new ProductGhtk("Giày", 0.1, 1, 1241);
            ProductGhtk product2 = new ProductGhtk("Áo", 0.2, 1, 1254);
            List<ProductGhtk> list = new ArrayList<>();
            list.add(product1);
            list.add(product2);
            OrderGhtk order = new OrderGhtk(
                "102", "0911222333", "Duong Ngoc Hieu",
             "123 nguyễn chí thanh", "TP. Hồ Chí Minh", "Quận 1", "Phường Bến Nghé", 100000,1000000
            );

            OrderRequest orderRequest = new OrderRequest(list, order);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBody = objectMapper.writeValueAsString(orderRequest);
//            System.out.println(jsonBody);
            GHTKApiService service = new GHTKApiService();
            System.out.println(service.sendOrderToGHTK(jsonBody));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

