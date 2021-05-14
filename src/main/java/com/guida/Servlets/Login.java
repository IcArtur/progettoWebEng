package com.guida.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



@SuppressWarnings("unused")
@WebServlet("/Login")
public class Login extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Login() {
		
		super();
		
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		

		try {
			String name = request.getParameter("user");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String dbName = null;
			String dbPassword = null;
			String dbEmail = null;
			Boolean isAdmin = false;
			String sql = "select * from utente where username=? and password=? ";
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/guidatv","artur","Arturho22");
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, name);
			pst.setString(2, password);
			
			ResultSet rs = pst.executeQuery();
			PrintWriter out = response.getWriter();
			
			while(rs.next()) {
				
				dbName = rs.getString(4);
				dbPassword = rs.getString(3);
				if (rs.getInt(7) == 1) {
					isAdmin = true;
				}
				else {
					isAdmin = false;
				}
				out.println(dbName);
				out.println(dbPassword);
				
				out.println(name);
				out.println(password);
				
				
			}
			if(name.equals(dbName) && password.equals(dbPassword)) {
				//Impostare qui tutte le variabili necessarie per la SESSIONE
				
				HttpSession session = request.getSession();
				
				session.setAttribute("user", name);
				session.setAttribute("pass", dbPassword);
				session.setAttribute("isAdmin", isAdmin);
				if (isAdmin) {
					response.sendRedirect("admin/programma/list");
				}
				else {
				 response.sendRedirect("homepage.jsp");
				}
			}
//			
			else {
				
				out.println("login fallito");
			}
//		
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	

}
