/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ManhTuan;

import DAL.FeedbackDAO;
import DAL.ProductDAOTuan;
import Model.Feedback;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author tuan
 */
@WebServlet(name = "FeedbackExportExcel", urlPatterns = {"/export"})
public class FeedbackExportExcel extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Thiết lập response để tải file
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=FeedbackList.xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Feedback");

            // Tạo hàng tiêu đề
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Tên khách hàng", "Sản phẩm", "Đánh giá", "Nhận xét", "Trạng thái", "Phản hồi"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                // Định dạng tiêu đề (in đậm)
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }

            // Lấy danh sách feedback từ DAO
            ProductDAOTuan dao = new ProductDAOTuan();
            List<Feedback> feedbackList = dao.getAllFeedback();

            // Ghi dữ liệu vào file Excel
            int rowNum = 1;
            for (Feedback feedback : feedbackList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(feedback.getFeedback_id());
                row.createCell(1).setCellValue(feedback.getUser_name());
                row.createCell(2).setCellValue(feedback.getProduct_name());
                row.createCell(3).setCellValue(feedback.getRating());
                row.createCell(4).setCellValue(feedback.getFeedback_content());
                row.createCell(5).setCellValue(feedback.isStatus() ? "Hoạt động" : "Đang khóa");
                row.createCell(6).setCellValue(feedback.getReply_content());
            }

            // Ghi file và gửi về client
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
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
