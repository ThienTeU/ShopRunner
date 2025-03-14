package HieuPTM.controller;

import HieuPTM.DAO.UserDAO;
import HieuPTM.model.UserHieu;
import HieuPTM.LoginGG.GetTokenServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.json.JSONObject;

@WebServlet(name = "GoogleLoginServlet", urlPatterns = {"/shop/login"})
public class GoogleLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            response.sendRedirect("HieuPTM/error.jsp?message=Missing authorization code");
            return;
        }

        try {
            //Lấy access token từ Google thông qua GetTokenServlet
            String accessToken = GetTokenServlet.getAccessToken(code);
            if (accessToken == null) {
                response.sendRedirect("HieuPTM/error.jsp?message=Failed to retrieve access token");
                return;
            }

            //Lấy thông tin user từ Google API
            JSONObject userInfo = GetTokenServlet.getUserInfo(accessToken);
            JSONObject userDetails = GetTokenServlet.getUserDetails(accessToken);

            String email = userInfo.optString("email", "unknown@example.com");
            String name = userInfo.optString("name", "Unknown");
            String picture = userInfo.optString("picture", "default.png");

            //Lấy số điện thoại & ngày sinh (nếu có)
            String birthday = "N/A";
            if (userDetails.has("birthdays")) {
                JSONObject birthObj = userDetails.getJSONArray("birthdays").getJSONObject(0);
                if (birthObj.has("date")) {
                    JSONObject date = birthObj.getJSONObject("date");
                    int year = date.optInt("year", 0);
                    int month = date.optInt("month", 0);
                    int day = date.optInt("day", 0);
                    if (year > 0 && month > 0 && day > 0) {
                        birthday = year + "-" + month + "-" + day;
                    }
                }
            }

            String phone = "N/A";
            if (userDetails.has("phoneNumbers")) {
                phone = userDetails.getJSONArray("phoneNumbers").getJSONObject(0).optString("value", "N/A");
            }

//            //Kiểm tra user có tồn tại trong DB không
//            UserDAO userDAO = new UserDAO();
//            if (!userDAO.checkUserNameDuplicate(email)) {
//                //UserHieu newUser = new UserHieu(email, name, "", "", phone, email, picture, birthday, 2);
//                UserHieu newUser = new UserHieu(email, name, "", phone, email, code, 2);
//                userDAO.insert(newUser);
//            }

            //Lưu session và chuyển hướng
            request.getSession().setAttribute("userEmail", email);
            response.sendRedirect("HieuPTM/home.jsp");

        } catch (Exception e) {
            response.sendRedirect(e.getMessage());
        }
    }
}