package com.guida.unused;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/addProgram")
public class AddProgram extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AddProgram() {
		super();
	}
	
	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String connectionURL = "jdbc:mysql://127.0.0.1:3306/guidatv";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection(connectionURL, "artur", "Arturho22");
			
			String nome=request.getParameter("nome");
			String descrizione=request.getParameter("descrizione");
			String genere=request.getParameter("genere");
			String istvseries=request.getParameter("istvseries");
			if (istvseries != null) {
				istvseries = "1";
			}
			else {
				istvseries = "0";
			}
			String num_stagione=request.getParameter("num_stagione");
			if (num_stagione == "") {
				num_stagione = "-1";
			}
			String num_episodio=request.getParameter("num_episodio");
			if (num_episodio == "") {
				num_episodio = "-1";
			}
			String link_scheda=request.getParameter("link_scheda");
			String link_immagine=request.getParameter("link_immagine");
			String String_data_inizio=request.getParameter("data_inizio").replace('T', ' ')+":00";
			Timestamp data_inizio = Timestamp.valueOf(String_data_inizio);
			String String_data_fine=request.getParameter("data_fine").replace('T', ' ')+":00";
			Timestamp data_fine = Timestamp.valueOf(String_data_fine);
			String canale=request.getParameter("canale");
			
			PrintWriter out = response.getWriter();
			PreparedStatement checkstatement = connection.prepareStatement("SELECT * from guidatv.programma WHERE nome = ?");
			checkstatement.setString (1, nome);
			ResultSet rs = checkstatement.executeQuery();
			Long idProgram = (long) 0;
			String popUpString = "";
			
			if (rs.next()) {
				idProgram = rs.getLong("id");
//				out.println(idProgram);
				popUpString = "Era presente nel database quindi e stato aggiunto solo il nuovo orario.";
			}
			else {
				String query = "INSERT INTO guidatv.programma (nome, descrizione, genere, isTvShow, numero_stagione, numero_episodio, link_scheda, link_immagine) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
				PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				statement.setString (1, nome);
				statement.setString (2, descrizione);
				statement.setString (3, genere);
				statement.setString (4, istvseries);
				statement.setString (5, num_stagione);
				statement.setString (6, num_episodio);
				statement.setString (7, link_scheda);
				statement.setString (8, link_immagine);
				statement.executeUpdate();
				try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		                idProgram = generatedKeys.getLong(1);	                
		            }
				}
				popUpString = "Non era presente nel database.";
			}
			
			
			String query2 = "INSERT INTO guidatv.orari_programma (data_inizio, data_fine, id_canale, id_programma) VALUES (?, ?, ?, ?);";
			PreparedStatement statement2 = connection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
			statement2.setTimestamp (1, data_inizio);
			statement2.setTimestamp (2, data_fine);
			statement2.setString (3, canale);
			statement2.setLong (4, idProgram);
			int affected_rows2 = statement2.executeUpdate();
//			out.println(affected_rows2);
			
			connection.close();
			if (affected_rows2 != 0) {
				response.setContentType("text/html");  
	            out.println("<script type=\"text/javascript\">");  
	            out.println("alert('Programma aggiunto correttamente! " +popUpString + "');");  
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
