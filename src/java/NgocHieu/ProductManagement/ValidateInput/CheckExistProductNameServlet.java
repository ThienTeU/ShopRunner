package NgocHieu.ProductManagement.ValidateInput;

import DAL.InsertProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet(name="CheckExistProductNameServlet", urlPatterns={"/CheckExistProductNameServlet"})
public class CheckExistProductNameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        InsertProductDAO dao = new InsertProductDAO();
        String productName = request.getParameter("product_name");
        boolean exists = dao.isProductNameExists(productName);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject json = new JSONObject();
        json.put("exists", exists);

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
