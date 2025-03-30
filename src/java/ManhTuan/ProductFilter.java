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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author tuan
 */
@WebServlet(name = "ProductFilter", urlPatterns = {"/productfilter"})
public class ProductFilter extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAOTuan dao = new ProductDAOTuan();
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");
        String[] selectedSizes = request.getParameterValues("size");
        String[] selectedColors = request.getParameterValues("color");

        Integer minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Integer.parseInt(minPriceStr) : 1000000;
        Integer maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Integer.parseInt(maxPriceStr) : 10000000;

        List<String> sizes = (selectedSizes != null) ? Arrays.asList(selectedSizes) : null;
        List<String> colors = (selectedColors != null) ? Arrays.asList(selectedColors) : null;

        List<ProductTuan> counts = dao.filterProducts(colors, sizes, minPrice, maxPrice);
        int count = 0;
        for (ProductTuan cus : counts) {
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

        List<ProductTuan> products = dao.filterProductsPages(colors, sizes, minPrice, maxPrice, index, size);
        List<Size> list = dao.getAllSize();
        List<Color> colorsAll = dao.getAllColor();
        List<CategoryTuan> categories = dao.getAllCategories();
        List<CategoryTuan> categoryTree = dao.buildCategoryTree(categories);
        request.setAttribute("categoryTree", categoryTree);
        request.setAttribute("colorsAll", colorsAll);
        request.setAttribute("size", list);
        request.setAttribute("products", products);
        request.setAttribute("selectedSizes", sizes);
        request.setAttribute("selectedColors", colors);
        request.setAttribute("minPrice", minPrice);
        request.setAttribute("maxPrice", maxPrice);
        request.setAttribute("check", "filter");
        request.setAttribute("end", end);
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

        List<ProductTuan> productsByPrice = dao.filterProducts(null, null, 10000, 2000000);
        System.out.println("Sản phẩm theo khoảng giá:");
        for (ProductTuan p : productsByPrice) {
            System.out.println(p);
        }

        List<String> sizes = Arrays.asList("M", "L");
        List<ProductTuan> productsBySize = dao.filterProducts(null, sizes, 100000, 1000000000);
        System.out.println("Sản phẩm theo kích thước:");
        for (ProductTuan p : productsBySize) {
            System.out.println(p);
        }

        List<String> colors = Arrays.asList("Red", "Blue");
        List<ProductTuan> productsByColor = dao.filterProducts(colors, null, 100000, 1000000000);
        System.out.println("Sản phẩm theo màu sắc:");
        for (ProductTuan p : productsByColor) {
            System.out.println(p);
        }
    }

}
