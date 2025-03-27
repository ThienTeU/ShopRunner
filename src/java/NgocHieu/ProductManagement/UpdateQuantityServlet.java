/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package NgocHieu.ProductManagement;

import DAL.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
@WebServlet(name = "UpdateQuantityServlet", urlPatterns = {"/UpdateQuantityServlet"})
public class UpdateQuantityServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String productQuantityId = request.getParameter("productQuantityId");
            String newQuantity = request.getParameter("newQuantity");
            
            if (productQuantityId == null || newQuantity == null || newQuantity.trim().isEmpty()) {
                AjaxResponse.send(response, false, "Dữ liệu không hợp lệ!");
                return;
            }
            
            try {
                int quantity = Integer.parseInt(newQuantity);
                if (quantity < 0) {
                    AjaxResponse.send(response, false, "Số lượng không thể là số âm!");
                    return;
                }
                
                ProductDAO dao = new ProductDAO();
                boolean result = dao.updateQuantity(productQuantityId, quantity);
                
                if (result) {
                    AjaxResponse.send(response, true, "Cập nhật số lượng thành công!");
                } else {
                    AjaxResponse.send(response, false, "Không thể cập nhật số lượng!");
                }
            } catch (NumberFormatException e) {
                AjaxResponse.send(response, false, "Số lượng không hợp lệ!");
            }
        } catch (Exception e) {
            AjaxResponse.send(response, false, "Lỗi: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
