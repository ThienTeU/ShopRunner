/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ManhTuan;

import DAL.ProductDAOTuan;
import Model.ColorTuan;
import Model.*;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "ProductCheckbox", urlPatterns = {"/productcheckbox"})
public class ProductCheckbox extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAOTuan dao = new ProductDAOTuan();
        List<Size> list = new ArrayList<>();
        List<Color> colorsAll = new ArrayList<>();
        colorsAll = dao.getAllColor();
        list = dao.getAllSize();
        request.setAttribute("colorsAll", colorsAll);
        request.setAttribute("size", list);

        String[] selectedSizes = request.getParameterValues("size");
        String[] selectedColors = request.getParameterValues("color");

        List<String> selectedSizeList = (selectedSizes != null) ? Arrays.asList(selectedSizes) : null;
        List<String> selectedColorList = (selectedColors != null) ? Arrays.asList(selectedColors) : null;
        List<ProductTuan> products = dao.getProductsByColorAndSize(selectedColorList, selectedSizeList);
        request.setAttribute("selectedSizes", selectedSizeList);
        request.setAttribute("selectedColors", selectedColorList);
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

    public static void main(String[] args) {
        ProductDAOTuan dao = new ProductDAOTuan();

        List<String> selectedSizes = Arrays.asList("M");
        List<String> selectedColors = Arrays.asList("Red");

        List<ProductTuan> products = dao.getProductsByColorAndSize(selectedColors, selectedSizes);

        for(ProductTuan p : products){
            System.out.println(p);
        }
    }

}
