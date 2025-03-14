/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ManhTuan;

import DAL.ProductDAOTuan;
import Model.AddressTuan;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author tuan
 */
@WebServlet(name = "CustomerAddressAdd", urlPatterns = {"/customeraddressadd"})
public class CustomerAddressAdd extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String city = request.getParameter("city");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        String street = request.getParameter("street");
        int customerId = Integer.parseInt(id);
        ProductDAOTuan dao = new ProductDAOTuan();
        AddressTuan address = new AddressTuan(customerId, name, phone, city, district, ward, street);
        int check = dao.addCustomerAddress(address);
        if (check == -1) {
            request.setAttribute("msg", "Thêm thất bại");
            request.getRequestDispatcher("ManhTuan/customeraddressadd.jsp").forward(request, response);
        } else {
            request.setAttribute("msg", "Thêm thành công");
            request.getRequestDispatcher("ManhTuan/customeraddressadd.jsp").forward(request, response);
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

}
