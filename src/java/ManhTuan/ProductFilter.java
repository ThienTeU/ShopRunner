/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package ManhTuan;

import DAL.ProductDAOTuan;
import Model.*;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author tuan
 */
@WebServlet(name="ProductFilter", urlPatterns={"/productfilter"})
public class ProductFilter extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ProductDAOTuan dao = new ProductDAOTuan();
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        if (minPrice != null && maxPrice != null && !minPrice.isEmpty() && !maxPrice.isEmpty()) {
            int min = Integer.parseInt(minPrice);
            int max = Integer.parseInt(maxPrice);
            List<ProductTuan> products = dao.getProductsByPriceRange(min, max);
            request.setAttribute("products", products);
            request.getRequestDispatcher("/ManhTuan/productlist.jsp").forward(request, response);
        }
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    public static void main(String[] args) {
        ProductDAOTuan dao = new ProductDAOTuan();
        List<ProductTuan> products = dao.getProductsByPriceRange(10000, 1000000);
        for(ProductTuan p : products){
            System.out.println(p);
        }
    }
    
}
