/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package NgocHieu.ProductManagement;

import DAL.ProductDAO;
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

/**
 *
 * @author admin
 */
@WebServlet(name="UpdatePriceServlet", urlPatterns={"/UpdatePriceServlet"})
public class UpdatePriceServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            String productPriceId = request.getParameter("productPriceId");
            String newPrice = request.getParameter("newPrice");
            
            if (productPriceId == null || newPrice == null || newPrice.trim().isEmpty()) {
                AjaxResponse.send(response, false, "Dữ liệu không hợp lệ!");
                return;
            }
            
            try {
                double price = Double.parseDouble(newPrice);
                if (price < 0) {
                    AjaxResponse.send(response, false, "Giá không thể là số âm!");
                    return;
                }
                
                ProductDAO dao = new ProductDAO();
                boolean result = dao.updatePrice(productPriceId, price);
                
                if (result) {
                    AjaxResponse.send(response, true, "Cập nhật giá thành công!");
                } else {
                    AjaxResponse.send(response, false, "Không thể cập nhật giá!");
                }
            } catch (NumberFormatException e) {
                AjaxResponse.send(response, false, "Giá không hợp lệ!");
            }
        } catch (Exception e) {
            AjaxResponse.send(response, false, "Lỗi: " + e.getMessage());
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
