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

@WebServlet(name = "ProductCategory", urlPatterns = {"/productcategory"})
public class ProductCategory extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAOTuan dao = new ProductDAOTuan();
        int id = Integer.parseInt(request.getParameter("categoryId"));
        List<ProductTuan> products = dao.getAllProducts(id);
        List<CategoryTuan> categories = dao.getCategoryTree();
        List<CategoryTuan> listCategory = dao.getAllCategories();
        request.setAttribute("listCategory", listCategory);
        request.setAttribute("id", id);
        request.setAttribute("categories", categories);
        request.setAttribute("products", products);
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
