/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package NgocHieu;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONObject;
import Model.CartItem;
import NgocHieu.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.Cookie;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

/**
 *
 * @author admin
 */
@WebServlet(name = "GetShippingFeeServlet", urlPatterns = {"/GetShippingFeeServlet"})
public class GetShippingFeeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        double total = Double.parseDouble(request.getParameter("total"));
        String city = request.getParameter("city");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        List<CartItem> cartItems = new ArrayList<>();
        
        double totalQuantity = 0;
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                try {
                    cartItems = AuthenticationService.decodeCartToken(cookie.getValue());
                } catch (JOSEException | ParseException ex) {
                    Logger.getLogger(GetShippingFeeServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if(cartItems.isEmpty()){
            response.sendRedirect("CartDetailServlet");
            return;
        }
        
        for(CartItem c : cartItems){
            totalQuantity += c.getQuantity();
        }
        double weight = totalQuantity * 500;
        
        double shippingFee = getShippingFee(city, district, ward, weight, total);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print("{\"shippingFee\":" + shippingFee + "}");
        out.flush();
    }

    public double getShippingFee(String city, String district, String ward, double weight, double value) {
        try {
            String API_URL = "https://services.giaohangtietkiem.vn/services/shipment/fee";
            String TOKEN = "13C9GJOiLh8zRzSXSIKkmYfTNPSvhO3a8sowSla";
            String encodedPickCity = URLEncoder.encode("Hà Nội", "UTF-8");
            String encodedPickDistrict = URLEncoder.encode("Thạch Thất", "UTF-8");
            
            // ma hoa tham so de tranh loi ki tu dac biet
            String encodedCity = URLEncoder.encode(city, "UTF-8");
            String encodedDistrict = URLEncoder.encode(district, "UTF-8");
            String encodedWard = URLEncoder.encode(ward, "UTF-8");

            // Xay dung url voi tham so
            String requestUrl = API_URL + "?pick_province=" + encodedPickCity
                    + "&pick_district=" + encodedPickDistrict
                    + "&province=" + encodedCity
                    + "&district=" + encodedDistrict
                    + "&address=" + encodedWard
                    + "&weight=" + weight
                    + "&value=" + value;

            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Token", TOKEN);
            conn.setRequestProperty("Content-Type", "application/json");

            // kiem tra phan hoi tu server
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) { // Nếu thành công
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // chuyen doi phan hoi thanh json
                JSONObject jsonResponse = new JSONObject(response.toString());
                if (jsonResponse.has("fee")) {
                    return jsonResponse.getJSONObject("fee").getDouble("fee");
                }
            } else {
                System.out.println("Lỗi HTTP: " + responseCode);
            }
        } catch (IOException | JSONException e) {
        }
        return -1;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
