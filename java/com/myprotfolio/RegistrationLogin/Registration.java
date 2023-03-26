package com.myprotfolio.RegistrationLogin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/register")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con = null;
	private PreparedStatement pstmt; 
	RequestDispatcher dispatcher;
       
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uname = req.getParameter("name");
		String uemail = req.getParameter("email");
		String pswd = req.getParameter("pass");
		String ucontact = req.getParameter("contact");
		PrintWriter out = resp.getWriter();
//		out.print(uname+" "+uemail+" "+pswd+" "+ucontact);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myportfolio?useSSL=false", "root", "@Rinku8923");
			
			
			String sql = "insert into users(uname, uemail, pswd, ucontact) values(?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, uname);
			pstmt.setString(2, uemail);
			pstmt.setString(3, pswd);
			pstmt.setString(4, ucontact);
			
			int result = pstmt.executeUpdate();	
			dispatcher = req.getRequestDispatcher("registration.jsp");
			if(result>0) {
				req.setAttribute("status", "success");
			}else {
				req.setAttribute("status", "failed");

			}
			
			dispatcher.forward(req, resp);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
