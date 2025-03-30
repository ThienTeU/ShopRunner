/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAL.ContactDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
@WebServlet(name = "UpdateContactStatusController", urlPatterns = {"/updateContactStatus"})
public class UpdateContactStatusController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int contactId = Integer.parseInt(request.getParameter("id"));
        boolean status = Boolean.parseBoolean(request.getParameter("status"));

        ContactDAO contactDAO = new ContactDAO();
        contactDAO.updateContactStatus(contactId, status);

        response.sendRedirect("contactList");
    }

    @Override
    public String getServletInfo() {
        return "Servlet cập nhật trạng thái của liên hệ";
    }
}

