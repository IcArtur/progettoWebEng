package com.guida.ControllerServlets;

import com.guida.Model.*;
import com.guida.Servlets.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class SchedaControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProgrammaDAO programmaDAO;
    private CanaleDAO canaleDAO;
 
    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
 
        programmaDAO = new ProgrammaDAO(jdbcURL, jdbcUsername, jdbcPassword);
        canaleDAO = new CanaleDAO(jdbcURL, jdbcUsername, jdbcPassword);
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String action = request.getPathInfo();
    	
    	try {
            switch (action) {
            case "/homepage":
                homepage(request, response);
                break;
            default:
                doGet(request, response);
                break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
 
        try {
            switch (action) {
            case "/programma":
                listOrari(request, response);
                break;
            case "/canale":
                listOrariGiornalieri(request, response);
                break;
            case "/homepage":
                homepage(request, response);
                break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    
    private void listOrari(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	int id = Integer.parseInt(request.getParameter("id"));
        List<Programma> listProgramma = programmaDAO.listOrari(id);
        Programma existingProgramma = programmaDAO.getSoloProgramma(id);
        request.setAttribute("listOrari", listProgramma);
        request.setAttribute("programma", existingProgramma);
        request.setAttribute("isTvShow", existingProgramma.getIsTvShow());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/programma.jsp");
        dispatcher.forward(request, response);
    }

    private void listOrariGiornalieri(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	int id = Integer.parseInt(request.getParameter("id"));
        List<Programma> listOrariGiornalieri = canaleDAO.listOrariGiornalieri(id);
        Canale existingCanale = canaleDAO.getCanale(id);
        request.setAttribute("listOrariGiornalieri", listOrariGiornalieri);
        request.setAttribute("canale", existingCanale);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/canale.jsp");
        dispatcher.forward(request, response);
    }
    
    private void homepage(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	String data_calendario = request.getParameter("data_calendario");
        request.setAttribute("data_calendario", data_calendario);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/homepage.jsp");
        dispatcher.forward(request, response);
    }

}
 
  