package HieuPTM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@WebServlet(name="NewPassword", urlPatterns={"/NewPassword"})
public class NewPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String newPassword = request.getParameter("password");
		String confPassword = request.getParameter("confPassword");
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		RequestDispatcher dispatcher = null;
		if (newPassword != null && confPassword != null && newPassword.equals(confPassword)) {

			try {
                                String passHashed = passwordEncoder.encode(newPassword);
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=RunnerShop", "sa","123");
				PreparedStatement pst = con.prepareStatement("UPDATE [User] SET password = ? WHERE email = ?");
				pst.setString(1, passHashed);
				pst.setString(2, (String) session.getAttribute("email"));

				int rowUpdated = pst.executeUpdate();
				if (rowUpdated > 0) {
					request.setAttribute("status", "resetSuccess");
					dispatcher = request.getRequestDispatcher("HieuPTM/Login.jsp");
				} else {
					request.setAttribute("status", "resetFailed");
					dispatcher = request.getRequestDispatcher("HieuPTM/Login.jsp");
				}
				dispatcher.forward(request, response);
			} catch (Exception e) {
    e.printStackTrace();
    response.getWriter().println("Lỗi: " + e.getMessage()); // In lỗi ra màn hình
}

		}
	}
}