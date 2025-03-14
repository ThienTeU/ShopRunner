package HieuPTM.LoginGG;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet(name="GetTokenServlet", urlPatterns={"/GetTokenServlet"})
public class GetTokenServlet extends HttpServlet {

    private static final String CLIENT_ID = "370235026777-etuk7qtgsnp0ql3fuu9ccs63i9fdofjd.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String REDIRECT_URI = "http://localhost:9999/shop/login";
    private static final String TOKEN_URL = "https://oauth2.googleapis.com/token";
    private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v2/userinfo";
    private static final String USER_DETAILS_URL = "https://people.googleapis.com/v1/people/me?personFields=birthdays,phoneNumbers";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String code = request.getParameter("code");
        if (code == null) {
            response.getWriter().println("Authorization code is missing!");
            return;
        }

        try {
            // Bước 1: Lấy Access Token từ Authorization Code
            String accessToken = getAccessToken(code);
            if (accessToken == null) {
                response.getWriter().println("Failed to retrieve access token!");
                return;
            }

            // Bước 2: Lấy thông tin người dùng từ Access Token
            JSONObject userInfo = getUserInfo(accessToken);
            JSONObject userDetails = getUserDetails(accessToken);

            // Gộp thông tin người dùng
            JSONObject userResponse = new JSONObject();
            userResponse.put("userInfo", userInfo);
            userResponse.put("userDetails", userDetails);

            // Trả về JSON response
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(userResponse.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }

    public static String getAccessToken(String authorizationCode) throws Exception {
        String params = "code=" + authorizationCode
                + "&client_id=" + CLIENT_ID
                + "&client_secret=" + CLIENT_SECRET
                + "&redirect_uri=" + REDIRECT_URI
                + "&grant_type=authorization_code";

        HttpURLConnection conn = (HttpURLConnection) new URL(TOKEN_URL).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(params.getBytes());
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        JSONObject jsonResponse = new JSONObject(response.toString());
        return jsonResponse.optString("access_token", null);
    }

    public static JSONObject getUserInfo(String accessToken) throws Exception {
        return sendGetRequest(USER_INFO_URL, accessToken);
    }

    public static JSONObject getUserDetails(String accessToken) throws Exception {
        return sendGetRequest(USER_DETAILS_URL, accessToken);
    }

    private static JSONObject sendGetRequest(String urlString, String accessToken) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return new JSONObject(response.toString());
    }
}