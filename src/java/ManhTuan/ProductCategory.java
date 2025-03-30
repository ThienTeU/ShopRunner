/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ManhTuan;

import DAL.ProductDAOTuan;
import Model.CategoryTuan;
import Model.Color;
import Model.ProductTuan;
import Model.Size;
import Model.UserTuan;
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
        int id = Integer.parseInt(request.getParameter("category_id"));
        List<ProductTuan> product = dao.getAllProductsByCategoryId(id);
        int count = 0;
        for (ProductTuan p : product) {
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
        if (request.getParameter("index") != null && !request.getParameter("index").isEmpty()) {
            index = Integer.parseInt(request.getParameter("index"));
        }
        List<ProductTuan> products = dao.getAllProductsByCategoryIdPages(id, index, size);
        List<CategoryTuan> listCategory = dao.getAllCategories();
        List<Size> list = dao.getAllSize();
        List<Color> colorsAll = dao.getAllColor();
        List<CategoryTuan> categories = dao.getAllCategories();
        List<CategoryTuan> categoryTree = dao.buildCategoryTree(categories);
        request.setAttribute("categoryTree", categoryTree);
        request.setAttribute("colorsAll", colorsAll);
        request.setAttribute("size", list);
        request.setAttribute("end", end);
        request.setAttribute("check", "category");
        request.setAttribute("listCategory", listCategory);
        request.setAttribute("id", id);
        request.setAttribute("products", products);
        request.getRequestDispatcher("/ManhTuan/test.jsp").forward(request, response);
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
