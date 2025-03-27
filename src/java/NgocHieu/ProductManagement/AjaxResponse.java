package NgocHieu.ProductManagement;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AjaxResponse {
    private boolean success;
    private String message;
    private Object data;

    public AjaxResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public AjaxResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static void send(HttpServletResponse response, boolean success, String message) throws IOException {
        send(response, success, message, null);
    }

    public static void send(HttpServletResponse response, boolean success, String message, Object data) throws IOException {
        AjaxResponse ajaxResponse = new AjaxResponse(success, message, data);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(new Gson().toJson(ajaxResponse));
        out.flush();
    }
} 