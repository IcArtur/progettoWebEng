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
            case "/edit":
                showEditForm(request, response);
                break;
            case "/update":
                updateProgramma(request, response);
                break;
            case "/find":
                findProgramma(request, response);
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
        List<Programma> listProgramma = programmaDAO.listAllProgramma();
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
		Timestamp data_inizio = Timestamp.valueOf(String_data_inizio);
		String String_data_fine=request.getParameter("data_fine").replace('T', ' ');
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
    
    private void findProgramma(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	String nome=request.getParameter("nome");
		String descrizione="";
		String genere=request.getParameter("genere");
//		String String_data_max=request.getParameter("datamax");
//		Timestamp data_max = Timestamp.valueOf(String_data_max);
//		String String_data_min=request.getParameter("datamin");
//		Timestamp data_min = Timestamp.valueOf(String_data_min);
		String String_ora_max=request.getParameter("oramax");
		LocalTime oramax = LocalTime.parse(String_ora_max);
		String String_ora_min=request.getParameter("oramin");
		Timestamp oramin = Timestamp.valueOf(String_ora_min);
		int id_canale=Integer.parseInt(request.getParameter("id_canale"));
        List<Programma> listProgramma = programmaDAO.listAllProgramma();
        request.setAttribute("listProgramma", listProgramma);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ricercaprogramma.jsp");
        dispatcher.forward(request, response);
    }
}
 
  