package HieuPTM.LoginGG;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="Constants", urlPatterns={"/Constants"})
public class Constants extends HttpServlet {
    public static String GOOGLE_CLIENT_ID = "370235026777-etuk7qtgsnp0ql3fuu9ccs63i9fdofjd.apps.googleusercontent.com";
    public static String GOOGLE_CLIENT_SECRET = "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";
    public static String GOOGLE_REDIRECT_URI = "http://localhost:9999/shop/login";
    public static String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
    public static String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    public static String GOOGLE_GRANT_TYPE = "authorization_code";
}