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

public class CanaleDAO {
	private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;
     
    public CanaleDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
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
     
    public boolean insertCanale(Canale canale) throws SQLException {
    	connect();
        
		String query = "INSERT INTO guidatv.canale (nome, immagine) VALUES (?, ?, ?, ?);";
		PreparedStatement statement2 = jdbcConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		statement2.setString (1, canale.getNome());
		statement2.setString (2, canale.getImmagine());
		boolean rowInserted = statement2.executeUpdate() > 0;
		
        return rowInserted;
    }
     
    public List<Canale> listAllCanale() throws SQLException {
        List<Canale> listCanale = new ArrayList<>();
         
        String sql = "SELECT * FROM guidatv.canale";
         
        connect();
        
        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
        		int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String immagine = resultSet.getString("immagine");
                Canale canale = new Canale(id, nome, immagine);
                listCanale.add(canale);
        }
        statement.close();
        
         
        return listCanale;
    }
     
    public boolean deleteCanale(Canale canale) throws SQLException {
        String sql = "DELETE FROM guidatv.canale where id = ?";
         
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, canale.getId());
         
        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();

        return rowDeleted;     
    }
     
    public boolean updateCanale(Canale canale) throws SQLException {
        String sql = "UPDATE guidatv.canale SET nome = ?, immagine = ?";
        sql += " WHERE id = ?";
        
        connect();
        
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString (1, canale.getNome());
		statement.setString (2, canale.getImmagine());
		statement.setInt (3, canale.getId());
        
        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();

        return rowUpdated;
    }
     
    public Canale getCanale(int id) throws SQLException {
        Canale canale = null;
        String sql = "SELECT * FROM guidatv.canale WHERE id = ?";
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);
        
        ResultSet resultSet = statement.executeQuery();
         
        if (resultSet.next()) {
            String nome = resultSet.getString("nome");
            String immagine = resultSet.getString("immagine");
            
            canale = new Canale(id, nome, immagine);
        }
        resultSet.close();
        statement.close();
         
        return canale;
    }
    
    public List<Programma> listOrariGiornalieri(int id) throws SQLException {
        List<Programma> listOrariGiornalieri = new ArrayList<>();

        String sql = "select op.*, p.*, c.* from orari_programma as op "
	        		+ "join programma p on p.id = op.id_programma "
	        		+ "join canale c on c.id = op.id_canale "
	        		+ "WHERE op.id_canale = ? "
	        		+ "AND date(op.data_inizio) = date(now()) "
	        		+ " ORDER by op.data_inizio ";
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt (1, id);
        ResultSet resultSet = statement.executeQuery();
        
        while (resultSet.next()) {
            String nome = resultSet.getString("nome");
            String descrizione = resultSet.getString("descrizione");
            String genere = resultSet.getString("genere");
            String link_scheda = resultSet.getString("link_scheda");
            String link_immagine = resultSet.getString("link_immagine");
            String nome_canale = resultSet.getString("c.nome");
            int isTvShowInt = resultSet.getInt("isTvShow");
            boolean isTvShow;
            if (isTvShowInt == 1) {
            	isTvShow = true;
            }
            else {
            	isTvShow = false;
            }
            int numero_stagione = resultSet.getInt("numero_stagione");
            int numero_episodio = resultSet.getInt("numero_episodio");
            int id_canale = id;
            int id_programma = resultSet.getInt("p.id");
            int id_orario = resultSet.getInt("op.id");
            String data_inizio_string = resultSet.getString("data_inizio");
            Timestamp data_inizio = resultSet.getTimestamp("data_inizio");
            String data_fine_string = resultSet.getString("data_fine");
            Timestamp data_fine = resultSet.getTimestamp("data_fine");
            Programma programma = new Programma(id_programma, nome, descrizione, genere, link_scheda, link_immagine, isTvShow, numero_stagione, numero_episodio, id_canale, nome_canale, id_orario, data_inizio, data_fine);
            listOrariGiornalieri.add(programma);
    	}
        
         
        resultSet.close();
        statement.close();
         

         
        return listOrariGiornalieri;
    }
}

