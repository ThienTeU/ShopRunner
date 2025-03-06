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
@WebServlet(name = "UpdateQuantityServlet", urlPatterns = {"/UpdateQuantityServlet"})
public class UpdateQuantityServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productQuantityId = request.getParameter("productQuantityId");
        String newQuantity = request.getParameter("newQuantity");
        if (productQuantityId == null || newQuantity == null) {
            response.sendRedirect("ProductDashBoard");
            return;
        }
        int _productQuantityId = Integer.parseInt(productQuantityId);
        int _newQuantity = Integer.parseInt(newQuantity);
        response.getWriter().print(productQuantityId + " " + newQuantity);
        ManageProductDAO manageDao = new ManageProductDAO();

        try {
            if (manageDao.updateProductQuantity(_newQuantity, _productQuantityId) > 0) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("<script>alert('Cập nhật số lượng sản phẩm thành công'); window.location='ProductDashboard';</script>");
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
