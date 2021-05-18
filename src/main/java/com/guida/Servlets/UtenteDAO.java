package com.guida.Servlets;

import com.guida.Model.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO {
	private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;
     
    public UtenteDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }
     
    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(
                                        jdbcURL, jdbcUsername, jdbcPassword);
        }
    }
     
    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }
     
    public boolean registerUtente(Utente utente) throws SQLException {
    	connect();
    	PreparedStatement checkstatement = jdbcConnection.prepareStatement("SELECT * from guidatv.utente WHERE username = ?");
		checkstatement.setString (1, utente.getUsername());
		ResultSet rs = checkstatement.executeQuery();
		Long idUtente = (long) 0;
		boolean rowInserted = false; 
		
		if (rs.next()) {
			//Implementare errore "Utente gia esistente"
		}
		else {
			String query = "INSERT INTO guidatv.utente (username, password, email, hash) VALUES (?, ?, ?, ?);";
			PreparedStatement statement = jdbcConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setString (1, utente.getUsername());
			statement.setString (2, utente.getPassword());
			statement.setString (3, utente.getEmail());
			statement.setString (4, utente.getHash());
			rowInserted = statement.executeUpdate() > 0;
		}
		if (rowInserted) {
			SendEmail se = new SendEmail(utente.getEmail(), utente.getHash());
			se.sendEmail();
			
		}
		disconnect();
        return rowInserted;
    }
     
    public List<Utente> listAllUtente() throws SQLException {
        List<Utente> listProgramma = new ArrayList<>();
         
        String sql = "select * from guidatv.utente";
         
        connect();
        
        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
    	while (resultSet.next()) {
    		int id = resultSet.getInt("p.id");
            String username = resultSet.getString("nome");
            String password = resultSet.getString("descrizione");
            String email = resultSet.getString("genere");
            int mail_giornaliereInt = resultSet.getInt("mail_giornaliere");
            boolean mail_giornaliere;
            if (mail_giornaliereInt == 1) {
            	mail_giornaliere = true;
            }
            else {
            	mail_giornaliere = false;
            }
            int has_confirmedInt = resultSet.getInt("has_confirmed");
            boolean has_confirmed;
            if (has_confirmedInt == 1) {
            	has_confirmed = true;
            }
            else {
            	has_confirmed = false;
            }
            int isAdminInt = resultSet.getInt("isAdmin");
            boolean isAdmin;
            if (isAdminInt == 1) {
            	isAdmin = true;
            }
            else {
            	isAdmin = false;
            }

            Utente utente = new Utente(id, username, password, email, mail_giornaliere, isAdmin, has_confirmed);
            listProgramma.add(utente);
        }
         
        disconnect();
         
        return listProgramma;
    }
    
    
    public boolean deleteUtente(Utente utente) throws SQLException {
        String sql = "DELETE FROM guidatv.utente where id = ?";
         
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, utente.getId());
         
        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;     
    }
     
    public boolean updateUtente(Utente utente) throws SQLException {
    	String join_sql = "UPDATE guidatv.utente SET username = ?, password = ?, email = ? "
    			+ "WHERE id=? ";
        
        connect();
        
        PreparedStatement statement = jdbcConnection.prepareStatement(join_sql);
        statement.setString (1, utente.getUsername());
		statement.setString (2, utente.getPassword());
		statement.setString (3, utente.getEmail());
		statement.setInt (4, utente.getId());
        
        statement.toString();
        
        boolean rowUpdated = statement.executeUpdate() > 0;
        
        disconnect();

        return rowUpdated;     
    }
     
    public Utente getUtente(int id) throws SQLException {
        Utente utente = null;
        String sql  = "select * from guidatv.utente "
        		     + "WHERE id = ?";
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
         
        if (resultSet.next()) {
        	String username = resultSet.getString("nome");
            String password = resultSet.getString("descrizione");
            String email = resultSet.getString("genere");
            int mail_giornaliereInt = resultSet.getInt("mail_giornaliere");
            boolean mail_giornaliere;
            if (mail_giornaliereInt == 1) {
            	mail_giornaliere = true;
            }
            else {
            	mail_giornaliere = false;
            }
            int has_confirmedInt = resultSet.getInt("has_confirmed");
            boolean has_confirmed;
            if (has_confirmedInt == 1) {
            	has_confirmed = true;
            }
            else {
            	has_confirmed = false;
            }
            int isAdminInt = resultSet.getInt("isAdmin");
            boolean isAdmin;
            if (isAdminInt == 1) {
            	isAdmin = true;
            }
            else {
            	isAdmin = false;
            }

            utente = new Utente(id, username, password, email, mail_giornaliere, isAdmin, has_confirmed);

        }
        resultSet.close();
        statement.close();
         
        return utente;
    }
    
    public Utente login(Utente utente) throws SQLException {
    	
    	String sql = "select * from guidatv.utente where username=? and password=? ";
    	
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, utente.getUsername());
        statement.setString(2, utente.getPassword());
        ResultSet resultSet = statement.executeQuery();
         
        if (resultSet.next()) {
        	int id = resultSet.getInt("id");
        	String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String email = resultSet.getString("email");
            int mail_giornaliereInt = resultSet.getInt("mail_giornaliere");
            boolean mail_giornaliere;
            if (mail_giornaliereInt == 1) {
            	mail_giornaliere = true;
            }
            else {
            	mail_giornaliere = false;
            }
            int has_confirmedInt = resultSet.getInt("has_confirmed");
            boolean has_confirmed;
            if (has_confirmedInt == 1) {
            	has_confirmed = true;
            }
            else {
            	has_confirmed = false;
            }
            int isAdminInt = resultSet.getInt("isAdmin");
            boolean isAdmin;
            if (isAdminInt == 1) {
            	isAdmin = true;
            }
            else {
            	isAdmin = false;
            }

            utente = new Utente(id, username, password, email, mail_giornaliere, isAdmin, has_confirmed);

        }
        
        disconnect();
         
        return utente;
    }
    
    public boolean activateUser(Utente utente) throws SQLException {
    	boolean res = false;
    	connect();
    	String sql = "SELECT * from guidatv.utente WHERE email=? AND hash=? AND has_confirmed='0' ";
    	String update = "UPDATE guidatv.utente SET has_confirmed='1' WHERE email=? AND hash=? ";
    	try {
    		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
            statement.setString(1, utente.getEmail());
            statement.setString(2, utente.getHash());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
            	PreparedStatement statement2 = jdbcConnection.prepareStatement(update);
            	statement2.setString(1, utente.getEmail());
            	statement2.setString(2, utente.getHash());
            	res = statement2.executeUpdate() > 0;
            }
    		
    	} catch (Exception ex) {
    		
    	}
    	
    	return res;
    }
}

