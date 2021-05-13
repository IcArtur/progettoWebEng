package com.guida;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		statement2.setString (1, canale.nome);
		statement2.setString (2, canale.immagine);
		boolean rowInserted = statement2.executeUpdate() > 0;
		
        disconnect();
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
         
        disconnect();
         
        return listCanale;
    }
     
    public boolean deleteCanale(Canale canale) throws SQLException {
        String sql = "DELETE FROM guidatv.canale where id = ?";
         
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, canale.getId());
         
        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;     
    }
     
    public boolean updateCanale(Canale canale) throws SQLException {
        String sql = "UPDATE guidatv.canale SET nome = ?, immagine = ?";
        sql += " WHERE id = ?";
        
        connect();
        
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString (1, canale.getNome());
		statement.setString (2, canale.getImmagine());
        
        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
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
}

