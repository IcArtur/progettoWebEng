package com.guida.unused;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public RegistrationServlet() {
		
		super();
		
		
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		
			
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		try {
			java.lang.String  username = request.getParameter("user");
			java.lang.String  password = request.getParameter("password");
			java.lang.String  email = request.getParameter("email");
			java.lang.String  sql = "insert into utente(username, password,email, mail_giornaliere, has_confirmed, isAdmin) Values(?, ?, ?, 0, 1, 0)";
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/guidatv","artur","Arturho22");
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, username);
			pst.setString(2, password);
			pst.setString(3, email);
			pst.executeUpdate();
			
			if(username != null && password!= null) {
			
				response.sendRedirect("login.html");
			
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
