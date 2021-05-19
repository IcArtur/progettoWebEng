package com.guida.ControllerServlets;

import com.guida.Model.*;
import com.guida.Servlets.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class UtenteControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UtenteDAO utenteDAO;
 
    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
 
        utenteDAO = new UtenteDAO(jdbcURL, jdbcUsername, jdbcPassword);
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
            case "/login":
                login(request, response);
                break;
            case "/logout":
                logout(request, response);
                break;
            case "/registrazione":
                registrazione(request, response);
                break;
            case "/profilo":
            	profilo(request, response);
                break;
            case "/update":
            	update(request, response);
                break;
            case "/activate":
            	activate(request, response);
                break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    
    private void login(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	String username = request.getParameter("user");
		String password = request.getParameter("password");
		Utente loginUtente = new Utente(username, password);
        Utente realUtente = utenteDAO.login(loginUtente);
        
        if (realUtente.getEmail() == null) {
        	PrintWriter out = response.getWriter();
        	out.println("login fallito");
//      	Account o password errati, da implementare messaggio di errore qui
        	response.sendRedirect("/guidatv/login.html");
        }
        else {
        	HttpSession session = request.getSession();
			session.setAttribute("utente", realUtente);
			if (realUtente.getIsAdmin() == true) {
				session.setAttribute("isAdmin", true);
				response.sendRedirect("/guidatv/admin/programma/list");
			}
			else {
				session.setAttribute("isAdmin", false);
				response.sendRedirect("/guidatv/homepage.jsp");
			}
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect("/guidatv/login.html");
    }

    private void registrazione(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	String  username = request.getParameter("user");
		String  password = request.getParameter("password");
		String  email = request.getParameter("email");
		Utente utente = new Utente(username, password, email);
		try {
			utenteDAO.registerUtente(utente);
			response.sendRedirect("/guidatv/verifica.jsp");
		}
		catch (Exception e) {
			response.sendRedirect("/guidatv/registrazione.html");
		}
    }
    
    private void profilo(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
			response.sendRedirect("/guidatv/utente.jsp");
    }

    private void update(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			String email=request.getParameter("email");
			boolean mail_giornaliere;
			String mail_giornaliere_string=request.getParameter("mail_giornaliere");
			if (mail_giornaliere_string != null) {
				mail_giornaliere = true;
			}
			else {
				mail_giornaliere = false;
			}
			int id=Integer.parseInt(request.getParameter("id"));
			Utente newUtente = new Utente(id, username, password, email, mail_giornaliere);
			utenteDAO.updateUtente(newUtente);
			Utente refreshUtente = utenteDAO.login(newUtente);
			HttpSession session = request.getSession();
			session.setAttribute("utente", refreshUtente);
	        response.sendRedirect("/guidatv/utente/profilo");
    }
    
    private void activate(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
			String email=request.getParameter("email");
			String hash=request.getParameter("hash");
			Utente utente = Utente.UtenteActivate(email, hash);
			Boolean result = utenteDAO.activateUser(utente);
			if (result) {
				response.sendRedirect("/guidatv/homepage.jsp");
			}
			else {
				response.sendRedirect("/guidatv/utente/registrazione");
			}
    }
}
 
  