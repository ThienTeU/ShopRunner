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
@WebServlet(name = "ProductSearch", urlPatterns = {"/productsearch"})
public class ProductSearch extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAOTuan dao = new ProductDAOTuan();
        String key = request.getParameter("key");
        String date = request.getParameter("date");
        String rate = request.getParameter("rate");
        String price = request.getParameter("price");
        String views = request.getParameter("views");
        if (date == null || date.isEmpty()) {
            date = "default";
        }
        if (rate == null || rate.isEmpty()) {
            rate = "default";
        }
        if (price == null || price.isEmpty()) {
            price = "default";
        }
        if (views == null || views.isEmpty()) {
            views = "default";
        }

        request.setAttribute("key", key);
        request.setAttribute("date", date);
        request.setAttribute("rate", rate);
        request.setAttribute("price", price);
        request.setAttribute("views", views);
        List<ProductTuan> product = dao.searchProducts(key, date, rate, price, views);
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
        List<ProductTuan> products = dao.searchProductsPages(key, date, rate, price, views, index, size);
        List<Size> list = dao.getAllSize();
        List<Color> colorsAll = dao.getAllColor();
        List<CategoryTuan> categories = dao.getAllCategories();
        List<CategoryTuan> categoryTree = dao.buildCategoryTree(categories);
        request.setAttribute("categoryTree", categoryTree);
        request.setAttribute("colorsAll", colorsAll);
        request.setAttribute("size", list);
        request.setAttribute("end", end);
        request.setAttribute("check", "search");
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

    public static void main(String[] args) {
        ProductDAOTuan dao = new ProductDAOTuan();

        String key = "a";
        String date = "";
        String rate = "";
        String price = "";
        String views = "";
        int page = 1;
        int pageSize = 5;
        List<ProductTuan> list = dao.searchProductsPages(key, date, rate, price, views, page, pageSize);
        for (ProductTuan t : list) {
            System.out.println(t.toString());
        }
        System.out.println("---------------------------------------------");
        System.out.println("");
        List<ProductTuan> product = dao.searchProducts(key, date, rate, price, views);
        for (ProductTuan t : product) {
            System.out.println(t.toString());
        }
    }

}
