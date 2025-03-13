package HieuPTM.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="ValidateOTP", urlPatterns={"/ValidateOTP"})
public class ValidateOTP extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		int value=Integer.parseInt(request.getParameter("otp"));
		HttpSession session=request.getSession();
		int otp=(int)session.getAttribute("otp");

		RequestDispatcher dispatcher=null;
		if (value==otp) 
		{
                request.setAttribute("email", request.getParameter("email"));
		request.setAttribute("status", "success");
		dispatcher=request.getRequestDispatcher("HieuPTM/NewPassword.jsp");
		dispatcher.forward(request, response);
		}
		else
		{
		request.setAttribute("message","Mã OTP sai");
		dispatcher=request.getRequestDispatcher("HieuPTM/EnterOTP.jsp");
		dispatcher.forward(request, response);
		}
	}
}