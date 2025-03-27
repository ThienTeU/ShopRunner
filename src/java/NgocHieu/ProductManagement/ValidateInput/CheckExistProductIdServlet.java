/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package NgocHieu.ProductManagement.ValidateInput;

import DAL.InsertProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author admin
 */
@WebServlet(name="CheckExistProductIdServlet", urlPatterns={"/CheckExistProductIdServlet"})
public class CheckExistProductIdServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        InsertProductDAO dao = new InsertProductDAO();
        int product_id = Integer.parseInt(request.getParameter("product_id"));
        boolean exists=false;
        try {
            exists = dao.isExistedProductId(product_id);
        } catch (SQLException ex) {
            Logger.getLogger(CheckExistProductNameServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

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
