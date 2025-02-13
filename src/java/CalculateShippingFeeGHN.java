
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CalculateShippingFeeGHN {

    public static void main(String[] args) {

        String fromDistrictId = "1442"; 
        String fromWardCode = "21211"; 
        String toDistrictId = "3444"; 
        String toWardCode = "431110"; 

        System.out.println("Output:");
        calculateAndPrintShippingFee(fromDistrictId, fromWardCode, toDistrictId, toWardCode, 30, 40, 20, 300);

    }

    public static void calculateAndPrintShippingFee(String fromDistrictId, String fromWardCode, String toDistrictId, String toWardCode, int length, int width, int height, int weight) {
        try {
            String url = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Token", "88508158-d9a6-11ef-b2e4-6ec7c647cc27"); // token
            con.setRequestProperty("ShopId", "195891"); // shop id

            String jsonInputString = "{"
                    + "\"service_type_id\": 5,"
                    + "\"from_district_id\": " + fromDistrictId + ","
                    + "\"from_ward_code\": \"" + fromWardCode + "\","
                    + "\"to_district_id\": " + toDistrictId + ","
                    + "\"to_ward_code\": \"" + toWardCode + "\","
                    + "\"length\": " + length + ","
                    + "\"width\": " + width + ","
                    + "\"height\": " + height + ","
                    + "\"weight\": " + weight + ","
                    + "\"insurance_value\": 0,"
                    + "\"coupon\": null,"
                    + "\"items\": ["
                    + "    {"
                    + "        \"name\": \"TEST1\","
                    + "        \"quantity\": 1,"
                    + "        \"length\": 200,"
                    + "        \"width\": 200,"
                    + "        \"height\": 200,"
                    + "        \"weight\": 1000"
                    + "    }"
                    + "]"
                    + "}";

            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

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
                    System.out.println("Shipping Fee Response: " + response.toString());
                }
            } else {
                System.out.println("Failed to calculate shipping fee. HTTP response code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
