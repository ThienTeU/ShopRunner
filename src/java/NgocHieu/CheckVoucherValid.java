/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package NgocHieu;

import DAO.DucAnh.VoucherDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
@WebServlet(name="CheckVoucherValid", urlPatterns={"/CheckVoucherValid"})
public class CheckVoucherValid extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        VoucherDAO dao = new VoucherDAO();
        String voucher = request.getParameter("voucherInput");
        
        boolean isValid = dao.isVoucherValid(voucher);
        int discount = 0;
        if(isValid){
            discount = dao.getDiscountByVoucherCode(voucher);
        }
        // Cấu hình response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        // Gửi kết quả trả về dưới dạng JSON
        out.print("{\"isValid\": " + isValid + ",\"discount\":"+discount+"}");
        out.flush();
        
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
