/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NgocHieu.GHTKService;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class GHTKApiService {
    public String sendOrderToGHTK(String jsonBody) {
        String apiUrl = "https://services.giaohangtietkiem.vn/services/shipment/order";
        try {
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

            // Trả về kết quả phản hồi
            return response.toString();

        } catch (IOException e) {
            return "Lỗi gửi đơn hàng: " + e.getMessage();
        }
    }
}


