package com.myprotfolio.RegistrationLogin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con = null;
	private PreparedStatement pstmt;
	RequestDispatcher dispatcher = null;


	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uemail= req.getParameter("username");
		String pswd = req.getParameter("password");
		PrintWriter out = resp.getWriter();
		HttpSession session = req.getSession();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myportfolio?useSSL=false", "root", "@Rinku8923");
			
			
			String sql = "select * from users where uemail = ? and pswd = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, uemail);
			pstmt.setString(2, pswd);
			
			ResultSet res = pstmt.executeQuery();	
			if(res.next()) {
				session.setAttribute("name", res.getString("uname"));
				dispatcher = req.getRequestDispatcher("index.jsp");
			}else {
				req.setAttribute("status", "failed");
				dispatcher = req.getRequestDispatcher("login.jsp");
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
