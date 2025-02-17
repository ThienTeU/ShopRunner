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
@WebServlet(name = "ProductSort", urlPatterns = {"/productsort"})
public class ProductSort extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String date = request.getParameter("date");
        String rate = request.getParameter("rate");
        String price = request.getParameter("price");
        ProductDAOTuan dao = new ProductDAOTuan();
        List<ProductTuan> product = dao.sortProducts(date, rate, price);
        int count = 0;
        int end = 0;
        int size = 9;
        for (ProductTuan p : product) {
            count++;
        }
        if (count % size == 0) {
            end = (count / size);
        } else {
            end = (count / size) + 1;
        }
        int index = 1;
        String indexParam = request.getParameter("index");
        if (indexParam != null && indexParam.matches("\\d+")) { // Kiểm tra chỉ chứa số
            index = Integer.parseInt(indexParam);
        } else {
            index = 1; // Mặc định trang đầu tiên
        }

        List<ProductTuan> products = dao.getAllProductsByPages(index, size, date, rate, price);
        List<CategoryTuan> categories = dao.getCategoryTree();
        
        request.setAttribute("categories", categories);
        request.setAttribute("date", date);
        request.setAttribute("rate", rate);
        request.setAttribute("price", price);
        request.setAttribute("end", end);
        request.setAttribute("products", product);
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
