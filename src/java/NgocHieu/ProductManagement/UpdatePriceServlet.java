/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package NgocHieu.ProductManagement;

import DAL.ManageProductDAO;
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
        String productPriceId = request.getParameter("productPriceId");
        String newPrice = request.getParameter("newPrice");
        if (productPriceId == null || newPrice == null || !newPrice.matches("\\d+")) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<script>alert('Giá tiền không hợp lệ!'); window.location='ProductDashboard';</script>");
            return;
        }
        int _productPriceId = Integer.parseInt(productPriceId);
        int _newPrice = Integer.parseInt(newPrice);
        response.getWriter().print(productPriceId + " " + newPrice);
        ManageProductDAO manageDao = new ManageProductDAO();

        try {
            if (manageDao.updateProductPrice(_newPrice, _productPriceId) > 0) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("<script>alert('Cập nhật giá sản phẩm thành công'); window.location='ProductDashboard';</script>");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UpdateQuantityServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
