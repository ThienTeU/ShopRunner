/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Model.*;
import java.util.List;
import DAL.*;
import java.util.Map;

/**
 *
 * @author tuan
 */
@WebServlet(name = "ProductList", urlPatterns = {"/productlist"})
public class ProductList extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAOTuan dao = new ProductDAOTuan();
        int count = dao.getTotalProducts();
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
        List<ProductTuan> products = dao.getAllProductsByPages(index, size);
        List<CategoryTuan> categories = dao.getCategoryTree();
        
        request.setAttribute("categories", categories);
        request.setAttribute("end", end);
        request.setAttribute("products", products);
        request.getRequestDispatcher("/ProductList/productlist.jsp").forward(request, response);
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
        List<ProductTuan> products = dao.getAllProductsByPages(1, 9);
        for (ProductTuan p : products) {
            System.out.println(p);
        }
    }
}
