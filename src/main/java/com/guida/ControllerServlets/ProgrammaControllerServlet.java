package com.guida.ControllerServlets;

import com.guida.Model.*;
import com.guida.Servlets.*;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
 
/**
 * ControllerServlet.java
 * This servlet acts as a page controller for the application, handling all
 * requests from the user.
 * @author www.codejava.net
 */
public class ProgrammaControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProgrammaDAO programmaDAO;
 
    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
 
        programmaDAO = new ProgrammaDAO(jdbcURL, jdbcUsername, jdbcPassword);
 
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
 
        try {
            switch (action) {
            case "/new":
                showNewForm(request, response);
                break;
            case "/insert":
            	insertProgramma(request, response);
                break;
            case "/delete":
                deleteProgramma(request, response);
                break;
            case "/deleteSearch":
            	deleteSearch(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/update":
                updateProgramma(request, response);
                break;
            case "/find":
                findProgramma(request, response);
                break;
            case "/findMail":
                findProgrammaMail(request, response);
                break;
            case "/saveRicerca":
            	saveRicerca(request, response);
                break;
            default:
                listProgramma(request, response);
                break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
 
    private void listProgramma(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	List<Programma> listProgramma = programmaDAO.ricercaProgramma(request);
        request.setAttribute("listProgramma", listProgramma);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/listaprogramma.jsp");
        dispatcher.forward(request, response);
    }
 
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/formprogramma.jsp");
        dispatcher.forward(request, response);
    }
 
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id_orario = Integer.parseInt(request.getParameter("id"));
        Programma existingProgramma = programmaDAO.getProgramma(id_orario);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/formprogramma.jsp");
        request.setAttribute("programma", existingProgramma);
        dispatcher.forward(request, response);
 
    }
 
    private void insertProgramma(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
    	String nome=request.getParameter("nome");
		String descrizione=request.getParameter("descrizione");
		String genere=request.getParameter("genere");
		boolean isTvShow;
		String isTvShow_string=request.getParameter("isTvShow");
		if (isTvShow_string != null) {
			isTvShow = true;
		}
		else {
			isTvShow = false;
		}
		int numero_stagione;
		String numero_stagione_string=request.getParameter("numero_stagione");
		if (numero_stagione_string == "") {
			numero_stagione = -1;
		}
		else {
			numero_stagione = Integer.parseInt(numero_stagione_string);
		}
		int numero_episodio;
		String numero_episodio_string=request.getParameter("numero_episodio");
		if (numero_episodio_string == "") {
			numero_episodio = -1;
		}
		else {
			numero_episodio = Integer.parseInt(numero_episodio_string);
		}
		String link_scheda=request.getParameter("link_scheda");
		String link_immagine=request.getParameter("link_immagine");
		String String_data_inizio=request.getParameter("data_inizio").replace('T', ' ')+":00";
		Timestamp data_inizio = Timestamp.valueOf(String_data_inizio);
		String String_data_fine=request.getParameter("data_fine").replace('T', ' ')+":00";
		Timestamp data_fine = Timestamp.valueOf(String_data_fine);
		int id_canale=Integer.parseInt(request.getParameter("id_canale"));
 
        Programma newProgramma = new Programma(nome, descrizione, genere, link_scheda, link_immagine, isTvShow, numero_stagione, numero_episodio, id_canale,  data_inizio, data_fine);
        programmaDAO.insertProgramma(newProgramma);
        response.sendRedirect("list");
    }
 
    private void updateProgramma(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
    	String nome=request.getParameter("nome");
		String descrizione=request.getParameter("descrizione");
		String genere=request.getParameter("genere");
		boolean isTvShow;
		String isTvShow_string=request.getParameter("isTvShow");
		if (isTvShow_string != null) {
			isTvShow = true;
		}
		else {
			isTvShow = false;
		}
		int numero_stagione;
		String numero_stagione_string=request.getParameter("numero_stagione");
		if (numero_stagione_string == "") {
			numero_stagione = -1;
		}
		else {
			numero_stagione = Integer.parseInt(numero_stagione_string);
		}
		int numero_episodio;
		String numero_episodio_string=request.getParameter("numero_episodio");
		if (numero_episodio_string == "") {
			numero_episodio = -1;
		}
		else {
			numero_episodio = Integer.parseInt(numero_episodio_string);
		}
		String link_scheda=request.getParameter("link_scheda");
		String link_immagine=request.getParameter("link_immagine");
		String String_data_inizio=request.getParameter("data_inizio").replace('T', ' ');
		if (String_data_inizio.length() == 21) {
			
		} else {
			String_data_inizio += ":00";
		}
		Timestamp data_inizio = Timestamp.valueOf(String_data_inizio);
		String String_data_fine=request.getParameter("data_fine").replace('T', ' ');
		if (String_data_fine.length() == 21) {
			
		} else {
			String_data_fine += ":00";
		}
		Timestamp data_fine = Timestamp.valueOf(String_data_fine);
		int id_canale=Integer.parseInt(request.getParameter("id_canale"));
		int id_orario=Integer.parseInt(request.getParameter("id_orario"));
		int id=Integer.parseInt(request.getParameter("id"));
		Programma newProgramma = new Programma(id, nome, descrizione, genere, link_scheda, link_immagine, isTvShow, numero_stagione, numero_episodio, id_canale, id_orario, data_inizio, data_fine);
        programmaDAO.updateProgramma(newProgramma);
        response.sendRedirect("list");
    }
 
    private void deleteProgramma(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id_orario = Integer.parseInt(request.getParameter("id"));
 
        Programma programma = new Programma(id_orario);
        programmaDAO.deleteProgramma(programma);
        response.sendRedirect("list");
    }
    
    private void deleteSearch(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id_ricerca = Integer.parseInt(request.getParameter("id"));
        programmaDAO.deleteRicerca(id_ricerca);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/programma/findMail");
        dispatcher.forward(request, response);
    }
    
    private void findProgramma(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Programma> listProgramma = programmaDAO.ricercaProgramma(request);
        request.setAttribute("listProgramma", listProgramma);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ricercaprogramma.jsp");
        dispatcher.forward(request, response);
    }
    
    private void findProgrammaMail(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Programma> listProgramma = programmaDAO.ricercaProgramma(request);
        request.setAttribute("listProgramma", listProgramma);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/mailgiornaliere.jsp");
        dispatcher.forward(request, response);
    }
    
    private void saveRicerca(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        boolean res = programmaDAO.saveRicerca(request);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/programma/findMail");
        dispatcher.forward(request, response);
    }
}
 
  