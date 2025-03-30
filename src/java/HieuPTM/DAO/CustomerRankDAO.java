package HieuPTM.DAO;

import static DAL.DBContext.connection;
import HieuPTM.DBContext.DBContext;
import Model.Orders;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="CustomerRankDAO", urlPatterns={"/CustomerRankDAO"})
public class CustomerRankDAO extends HttpServlet {

    private final Connection connection;
    
    public CustomerRankDAO() {
        DBContext db = new DBContext();
        this.connection = db.connection;
    }
    
    public List<Orders> getBonusPoint() {
        List<Orders> list = new ArrayList<>();
        String sql = "SELECT order_id, email, order_date, total_price FROM [Orders]";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Orders order = new Orders();
                order.setOrder_id(rs.getInt("order_id"));
                order.setEmail(rs.getString("email"));
                order.setOrder_date(rs.getString("order_date"));
                order.setTotal_price(rs.getInt("total_price"));
                list.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console để kiểm tra
        }
        return list;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
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
        CustomerRankDAO dao = new CustomerRankDAO();
        List<Orders> orders = dao.getBonusPoint();

        if (orders.isEmpty()) {
            System.out.println("Không có dữ liệu nào trong bảng Orders.");
        } else {
            System.out.println("===== Danh sách giao dịch điểm thưởng =====");
            for (Orders order : orders) {
                int bonusPoints = order.getTotal_price() / 100000; // Làm tròn xuống số nguyên
                System.out.println("Email: " + order.getEmail());
                System.out.println("Ngày: " + order.getOrder_date());
                System.out.println("Điểm cộng: " + bonusPoints);
                System.out.println("Mô tả: Điểm cộng qua mua hàng");
                System.out.println("-----------------------------------------");
        }
    }
}
}
