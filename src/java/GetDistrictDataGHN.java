import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GetDistrictDataGHN {

    public static void main(String[] args) {
        getDistrictData();
    }

    public static void getDistrictData() {
        try {
            String url = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("token", "88508158-d9a6-11ef-b2e4-6ec7c647cc27");

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
                    System.out.println("District Data Response: " + response.toString());
                }
            } else {
                System.out.println("Failed to get district data. HTTP response code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
