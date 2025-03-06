/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package ManhTuan;

import DAL.ProductDAOTuan;
import Model.CategoryTuan;
import Model.ProductTuan;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author tuan
 */
@WebServlet(name="ProductSearch", urlPatterns={"/productsearch"})
public class ProductSearch extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ProductDAOTuan dao = new ProductDAOTuan();
        String key = request.getParameter("key");
        List<ProductTuan> products = dao.getAllProductsByKey(key);
        int count = 0;
        for(ProductTuan p :products){
            count++;
        }
        int size = 9;
        int end = 0;
        if (count % size == 0) {
            end = count / size;
        } else {
            end = (count / size) + 1;
        }
        int index = 1;
        if(request.getParameter("index")!=null && !request.getParameter("index").isEmpty()){
             index = Integer.parseInt(request.getParameter("index"));
        }
        List<ProductTuan> productPage = dao.getAllProductsByPages(index, size, key);
        List<CategoryTuan> categories = dao.getCategoryTree();
        request.setAttribute("categories", categories);
        request.setAttribute("key", key);
        request.setAttribute("end", end);
        request.setAttribute("products", productPage);
        request.getRequestDispatcher("/ManhTuan/productlist.jsp").forward(request, response);
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

}
