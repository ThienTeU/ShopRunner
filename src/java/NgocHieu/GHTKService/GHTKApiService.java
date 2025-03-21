/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NgocHieu.GHTKService;

import DAL.OrderDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class GHTKApiService {
    
    public OrderResponse sendOrderToGHTK(String jsonBody) throws IOException {
        String apiUrl = "https://services.giaohangtietkiem.vn/services/shipment/order";
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Token", "13C9GJOiLh8zRzSXSIKkmYfTNPSvhO3a8sowSla");
        connection.setRequestProperty("X-Client-Source", "S19268530");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Gửi dữ liệu JSON
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Đọc phản hồi từ server
        int responseCode = connection.getResponseCode();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                responseCode < 300 ? connection.getInputStream() : connection.getErrorStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String responseLine;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }

        //Map sang orderResponse
        ObjectMapper objectMapper = new ObjectMapper();
        OrderResponse orderResponse = objectMapper.readValue(response.toString(), OrderResponse.class);
        if(orderResponse!=null){
            OrderDAO dao = new OrderDAO();
            dao.saveOrder(orderResponse);
            return orderResponse;
        }
        return null;
    }

    public OrderResponse getOrderByTrackingId(long id) {
        OrderDAO orderDao = new OrderDAO();
        return orderDao.getOrderByTrackingId(id);
    }
    
    //Hủy giao hàng
    public CancelResponse cancelOrder(String label, String order_id) throws MalformedURLException, IOException {
        String apiUrl = "https://services.giaohangtietkiem.vn/services/shipment/cancel/" + label;
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Token", "13C9GJOiLh8zRzSXSIKkmYfTNPSvhO3a8sowSla");
        connection.setRequestProperty("X-Client-Source", "S19268530");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(false);// Không cần gửi body thì set false

        // Nhận phản hồi từ API
        int responseCode = connection.getResponseCode();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                responseCode < 300 ? connection.getInputStream() : connection.getErrorStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String responseLine;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }

        // Chuyển phản hồi JSON thành đối tượng CancelResponse
        ObjectMapper objectMapper = new ObjectMapper();
        CancelResponse cancelResponse = objectMapper.readValue(response.toString(), CancelResponse.class);
        
        OrderDAO dao = new OrderDAO();
        
        //Sửa lại status trong bảng OrderResponse
        dao.updateStatusInOrderResponse(label,-1);
        
        //Sửa status trong bảng Orders chính
        dao.updateStatusToCancelledInOrder(order_id);
        
        //Phục hồi quantity cho sản phẩm
        dao.restoreProductQuantity(Integer.parseInt(order_id));
        return cancelResponse;
    }
    
    //Kiểm tra trnajg thái đơn hàng
    public OrderStatusResponse checkStatusOrder(long tracking_id) throws ProtocolException, MalformedURLException, IOException{
        String apiUrl = "https://services.giaohangtietkiem.vn/services/shipment/v2/" + tracking_id;
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Token", "13C9GJOiLh8zRzSXSIKkmYfTNPSvhO3a8sowSla");
        connection.setRequestProperty("X-Client-Source", "S19268530");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(false);// Không cần gửi body thì set false
        
        // Nhận phản hồi từ API
        int responseCode = connection.getResponseCode();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                responseCode < 300 ? connection.getInputStream() : connection.getErrorStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String responseLine;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }
        
        ObjectMapper objectMapper = new ObjectMapper();
        OrderStatusResponse orderStatusResponse = objectMapper.readValue(response.toString(), OrderStatusResponse.class);
        OrderDAO dao = new OrderDAO();
        dao.updateStatusInOrderResponse(orderStatusResponse.order.getLabelId(), orderStatusResponse.order.getStatus());
        return orderStatusResponse;
    }
    
    //Cập nhật trạng thái giao hàng lại trong database
    public void updateAllStatusInDB() throws MalformedURLException, IOException{
        OrderDAO dao = new OrderDAO();
        List<OrderResponse> listOrderResponse = dao.getAllOrderResponse();
        for(OrderResponse order : listOrderResponse){
            checkStatusOrder(order.getOrder().getTrackingId());
        }
    }
    
    public static void main(String[] args) throws MalformedURLException, IOException {
        GHTKApiService service = new GHTKApiService();
        service.cancelOrder("S19268530.MB21-04-D5.1921080642", "2120");
    }
}
