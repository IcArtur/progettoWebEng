package com.guida.unused;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/addUser")
public class AddUser extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AddUser() {
		super();
	}
	
	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String connectionURL = "jdbc:mysql://127.0.0.1:3306/guidatv";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection(connectionURL, "artur", "Arturho22");
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			String email=request.getParameter("email");
			String mail_giornaliere=request.getParameter("mail_giornaliere");
			if (mail_giornaliere != null) {
				mail_giornaliere = "1";
			}
			else {
				mail_giornaliere = "0";
			}
			String checkboxadmin=request.getParameter("checkboxadmin");
			if (checkboxadmin != null) {
				checkboxadmin = "1";
			}
			else {
				checkboxadmin = "0";
			}
			
			PrintWriter out = response.getWriter();
			
			PreparedStatement checkstatement = connection.prepareStatement("SELECT username from guidatv.utente WHERE username = ?");
			checkstatement.setString (1, username);
			ResultSet rs = checkstatement.executeQuery();
			String popUpString = "";
			if (rs.next()) {
				popUpString = "L username "+ rs.getString("username") +" e presente nel database. Riprovare con un username non utilizzato.";
			}
			else {
				String query = "INSERT INTO guidatv.utente (username, password, email, mail_giornaliere, has_confirmed, isAdmin) VALUES (?, ?, ?, ?, 1, ?);";
				PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				statement.setString (1, username);
				statement.setString (2, password);
				statement.setString (3, email);
				statement.setString (4, mail_giornaliere);
				statement.setString (5, checkboxadmin);
				int affected_rows = statement.executeUpdate();
				if (affected_rows != 0) {
					popUpString = "L utente "+ username +" e stato creato correttamente!.";
				}
				
			}
			response.setContentType("text/html");  
            out.println("<script type=\"text/javascript\">");  
            out.println("alert('"+ popUpString +"');");  
            out.println("window.location.href = 'admin.jsp';");
            out.println("</script>");
            connection.close();
			
			
			
		
	
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
