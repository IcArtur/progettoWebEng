package com.guida.unused;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/addChannel")
public class AddChannel extends HttpServlet{
	public AddChannel() {
		super();
	}
	
	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String connectionURL = "jdbc:mysql://127.0.0.1:3306/guidatv";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection(connectionURL, "artur", "Arturho22");
			String nome=request.getParameter("nome");
			PrintWriter out = response.getWriter();
			String query = "INSERT INTO guidatv.canale (nome) VALUES (?);";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString (1, nome);
			int affected_rows = statement.executeUpdate();
			
			connection.close();
			if (affected_rows != 0) {
				response.setContentType("text/html");  
	            out.println("<script type=\"text/javascript\">");  
	            out.println("alert('Canale aggiunto correttamente!');");  
	            out.println("window.location.href = 'admin.jsp';");
	            out.println("</script>");
			}
			
		
	
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
