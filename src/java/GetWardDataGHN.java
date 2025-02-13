import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GetWardDataGHN {

    public static void main(String[] args) {
        // Mã quận/huyện cần lấy dữ liệu phường/xã
        String districtId = "3259"; // Thay bằng mã quận/huyện thực tế

        // Gọi hàm lấy dữ liệu phường/xã và in ra kết quả
        getWardData(districtId);
    }

    public static void getWardData(String districtId) {
        try {
            String url = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id=" + districtId;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("token", "88508158-d9a6-11ef-b2e4-6ec7c647cc27"); // Thay bằng token của bạn

            int responseCode = con.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    // In ra kết quả
                    System.out.println("Ward Data Response: " + response.toString());
                }
            } else {
                System.out.println("Failed to get ward data. HTTP response code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
