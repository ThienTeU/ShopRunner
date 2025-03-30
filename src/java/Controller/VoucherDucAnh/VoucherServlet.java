package Controller.VoucherDucAnh;

import DAO.DucAnh.VoucherDAO;
import DTO.DucAnh.Voucher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="VoucherServlet", urlPatterns={"/tableVoucher"})
public class VoucherServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        VoucherDAO vdao = new VoucherDAO();
        
        if (action == null) {
            request.setAttribute("voucher", vdao.listVoucher());
            request.getRequestDispatcher("TableVoucher.jsp").forward(request, response);
        } else if ("detail".equals(action)) {
            String name = request.getParameter("voucherName").trim();
            Voucher v = vdao.getDataByName(name);
            request.setAttribute("v", v);
            request.getRequestDispatcher("TableVoucher.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name").toUpperCase();
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        String code = request.getParameter("code").toUpperCase();

        try {
            int discount = Integer.parseInt(request.getParameter("discount"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            if (discount < 0 || quantity <= 0) {
                request.setAttribute("error", "Mã giảm giá phải lớn hơn 0%");
                request.getRequestDispatcher("TableVoucher.jsp").forward(request, response);
                return;
            }
            
            VoucherDAO vdao = new VoucherDAO();
            vdao.addVoucher(name, code, start, end, discount, quantity);
            response.sendRedirect("tableVoucher");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid input: Số lượng phải là số dương");
            request.getRequestDispatcher("TableVoucher.jsp").forward(request, response);
        }
    }
}
