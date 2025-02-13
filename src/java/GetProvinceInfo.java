
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetProvinceInfo {

    //GHN Token
    private static final String TOKEN = "88508158-d9a6-11ef-b2e4-6ec7c647cc27";
    // API Endpoint (Use production or test URL as needed)
    private static final String API_URL = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province";

    public static void main(String[] args) {
        try {
            // Fetch all provinces
            Map<String, Integer> provinceMap = getProvinces();

            // Example: Get Province ID by Name
            String cityName = "Hà Nội";
            Integer provinceId = provinceMap.get(cityName);

            if (provinceId != null) {
                System.out.println("Province ID for " + cityName + ": " + provinceId);
            } else {
                System.out.println("City not found: " + cityName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Integer> getProvinces() {
        Map<String, Integer> provinceMap = new HashMap<>();

        try {
            // Create URL object
            URL url = new URL(API_URL);

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method and headers
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Token", TOKEN);

            // Get response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) { // Success
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse the response JSON
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray provinces = jsonResponse.getJSONArray("data"); // Directly fetch as JSONArray

                for (int i = 0; i < provinces.length(); i++) {
                    JSONObject province = provinces.getJSONObject(i);
                    String provinceName = province.getString("ProvinceName");
                    int provinceId = province.getInt("ProvinceID");
                    provinceMap.put(provinceName, provinceId);
                }

            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
            }

            // Disconnect the connection
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return provinceMap;
    }

}
