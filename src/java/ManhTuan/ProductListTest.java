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

/**
 *
 * @author tuan
 */
@WebServlet(name="ProductListTest", urlPatterns={"/ProductListTest"})
public class ProductListTest extends HttpServlet {
   
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ProductDAOTuan dao = new ProductDAOTuan();
        String key = request.getParameter("key") != null ? request.getParameter("key").trim() : null;
        String date = request.getParameter("date") != null ? request.getParameter("date").trim() : null;
        String rate = request.getParameter("rate") != null ? request.getParameter("rate").trim() : null;
        String price = request.getParameter("price") != null ? request.getParameter("price").trim() : null;
        int index = 1;
        if (request.getParameter("index") != null && !request.getParameter("index").isEmpty()) {
            index = Integer.parseInt(request.getParameter("index").trim());
        }
        //category
        String categoryIdStr = request.getParameter("categoryId");
        int categoryId = (categoryIdStr != null && !categoryIdStr.isEmpty()) ? Integer.parseInt(categoryIdStr) : 0;

        List<CategoryTuan> categories = dao.getCategoryTree();
        //getList
        List<ProductTuan> product = dao.getProducts(key, date, rate, price);
        //Phan trang
        int count = 0;
        for (ProductTuan p : product) {
            count++;
        }
        int size = 9;
        int end = 0;
        if (count % size == 0) {
            end = count / size;
        } else {
            end = count / size + 1;
        }
        List<ProductTuan> products = dao.getProducts(key, date, rate, price, index, size);
        request.setAttribute("end", end);
        request.setAttribute("key", key);
        request.setAttribute("date", date);
        request.setAttribute("rate", rate);
        request.setAttribute("price", price);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
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
    public static void main(String[] args) {
        ProductDAOTuan dao = new ProductDAOTuan();

        List<ProductTuan> products = dao.getProducts("", "default", "default", "default", 6, 3);
        for (ProductTuan p : products) {
            System.out.println(p);
        }
    }

}
