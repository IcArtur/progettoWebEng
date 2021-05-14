package com.guida;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProgrammaDAO {
	private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;
     
    public ProgrammaDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
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
     
    public boolean insertProgramma(Programma programma) throws SQLException {
    	connect();
    	PreparedStatement checkstatement = jdbcConnection.prepareStatement("SELECT * from guidatv.programma WHERE nome = ?");
		checkstatement.setString (1, programma.nome);
		ResultSet rs = checkstatement.executeQuery();
		Long idProgram = (long) 0;
		
		if (rs.next()) {
			idProgram = rs.getLong("id");
		}
		else {
			String query = "INSERT INTO guidatv.programma (nome, descrizione, genere, isTvShow, numero_stagione, numero_episodio, link_scheda, link_immagine) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement statement = jdbcConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setString (1, programma.nome);
			statement.setString (2, programma.descrizione);
			statement.setString (3, programma.genere);
			statement.setBoolean (4, programma.isTvShow);
			statement.setInt (5, programma.numero_stagione);
			statement.setInt (6, programma.numero_episodio);
			statement.setString (7, programma.link_scheda);
			statement.setString (8, programma.link_immagine);
			statement.executeUpdate();
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                idProgram = generatedKeys.getLong(1);	                
	            }
			}
		}
        
		String query2 = "INSERT INTO guidatv.orari_programma (data_inizio, data_fine, id_canale, id_programma) VALUES (?, ?, ?, ?);";
		PreparedStatement statement2 = jdbcConnection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
		statement2.setTimestamp (1, programma.data_inizio);
		statement2.setTimestamp (2, programma.data_fine);
		statement2.setInt (3, programma.id_canale);
		statement2.setLong (4, idProgram);
		statement2.toString();
		boolean rowInserted = statement2.executeUpdate() > 0;
		
        return rowInserted;
    }
     
    public List<Programma> listAllProgramma() throws SQLException {
        List<Programma> listProgramma = new ArrayList<>();
         
        String sql = "select op.*, p.* from orari_programma as op "
        		+ "join programma p on p.id = op.id_programma";
         
        connect();
        
        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
    	while (resultSet.next()) {
    		int id = resultSet.getInt("p.id");
            String nome = resultSet.getString("nome");
            String descrizione = resultSet.getString("descrizione");
            String genere = resultSet.getString("genere");
            String link_scheda = resultSet.getString("link_scheda");
            String link_immagine = resultSet.getString("link_immagine");
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
            int id_canale = resultSet.getInt("id_canale");
            int id_orario = resultSet.getInt("op.id");
            Timestamp data_inizio = resultSet.getTimestamp("data_inizio");
            Timestamp data_fine = resultSet.getTimestamp("data_fine");
            Programma programma = new Programma(id, nome, descrizione, genere, link_scheda, link_immagine, isTvShow, numero_stagione, numero_episodio, id_canale, id_orario, data_inizio, data_fine);
            listProgramma.add(programma);
        }
         
        resultSet.close();
        statement.close();
         
         
        return listProgramma;
    }
    
    public List<Programma> listOrari(int id) throws SQLException {
        List<Programma> listOrari = new ArrayList<>();
         
        String sql = "select op.*, p.*, c.* from orari_programma as op "
	        		+ "join programma p on p.id = op.id_programma "
	        		+ "join canale c on c.id = op.id_canale "
	        		+ "WHERE op.id_programma = ?";
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt (1, id);
        statement.toString();
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
            int id_canale = resultSet.getInt("id_canale");
            int id_orario = resultSet.getInt("id");
            Timestamp data_inizio = resultSet.getTimestamp("data_inizio");
            Timestamp data_fine = resultSet.getTimestamp("data_fine");
            Programma programma = new Programma(id, nome, descrizione, genere, link_scheda, link_immagine, isTvShow, numero_stagione, numero_episodio, id_canale, nome_canale, id_orario, data_inizio, data_fine);
            listOrari.add(programma);
    	}
        
         
        resultSet.close();
        statement.close();
        
        return listOrari;
    }
     
    public boolean deleteProgramma(Programma programma) throws SQLException {
        String sql = "DELETE FROM guidatv.orari_programma where id = ?";
         
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, programma.getId_orario());
         
        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;     
    }
     
    public boolean updateProgramma(Programma programma) throws SQLException {
    	String join_sql = "UPDATE programma as p "
    			+ "join orari_programma as op  ON p.id = op.id_programma "
    			+ "SET nome = ?, descrizione = ?, genere = ?, link_scheda = ?, link_immagine = ?, isTvShow = ?, numero_stagione = ?, numero_episodio = ?, data_inizio = ?, data_fine= ?, id_canale = ? "
    			+ "WHERE op.id=? ";
        
        connect();
        
        PreparedStatement statement = jdbcConnection.prepareStatement(join_sql);
        statement.setString (1, programma.getNome());
		statement.setString (2, programma.getDescrizione());
		statement.setString (3, programma.getGenere());
		statement.setString (4, programma.getlink_scheda());
		statement.setString (5, programma.getlink_immagine() );
		statement.setBoolean (6, programma.getIsTvShow());
		statement.setInt (7, programma.getnumero_stagione());
		statement.setInt (8, programma.getnumero_episodio());
        statement.setTimestamp(9, programma.getdata_inizio());
        statement.setTimestamp(10, programma.getdata_fine());
        statement.setInt(11, programma.getId_canale());
        statement.setInt(12, programma.getId_orario());
        
        statement.toString();
        
        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();

        return rowUpdated;     
    }
     
    public Programma getProgramma(int id_orario) throws SQLException {
        Programma programma = null;
        String join  = "select op.*, p.* from orari_programma as op "
        		     + "join programma p on p.id = op.id_programma "
        		     + "WHERE op.id_programma = ?";
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(join);
        statement.setInt(1, id_orario);
        ResultSet resultSet = statement.executeQuery();
         
        if (resultSet.next()) {
    		int id = resultSet.getInt("p.id");
            String nome = resultSet.getString("nome");
            String descrizione = resultSet.getString("descrizione");
            String genere = resultSet.getString("genere");
            String link_scheda = resultSet.getString("link_scheda");
            String link_immagine = resultSet.getString("link_immagine");
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
            int id_canale = resultSet.getInt("id_canale");
            Timestamp data_inizio = resultSet.getTimestamp("data_inizio");
            Timestamp data_fine = resultSet.getTimestamp("data_fine");
            programma = new Programma(id, nome, descrizione, genere, link_scheda, link_immagine, isTvShow, numero_stagione, numero_episodio, id_canale, id_orario, data_inizio, data_fine);
        }
        resultSet.close();
        statement.close();
         
        return programma;
    }
    public Programma getSoloProgramma(int id) throws SQLException {
        Programma programma = null;
        String sql = "SELECT * FROM guidatv.programma WHERE id=?";
        
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        
        statement.setInt(1, id);
        statement.toString();
        ResultSet resultSet = statement.executeQuery();
         
        if (resultSet.next()) {
            String nome = resultSet.getString("nome");
            String descrizione = resultSet.getString("descrizione");
            String genere = resultSet.getString("genere");
            String link_scheda = resultSet.getString("link_scheda");
            String link_immagine = resultSet.getString("link_immagine");
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
            programma = new Programma(id, nome, descrizione, genere, link_scheda, link_immagine, isTvShow, numero_stagione, numero_episodio);
        	
        }
        resultSet.close();
        statement.close();
         
        return programma;
    }
}

