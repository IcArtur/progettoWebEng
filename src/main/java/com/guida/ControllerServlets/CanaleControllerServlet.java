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
 
/**
 * ControllerServlet.java
 * This servlet acts as a page controller for the application, handling all
 * requests from the user.
 * @author www.codejava.net
 */
public class CanaleControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CanaleDAO canaleDAO;
 
    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
 
        canaleDAO = new CanaleDAO(jdbcURL, jdbcUsername, jdbcPassword);
 
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
            	insertCanale(request, response);
                break;
            case "/delete":
                deleteCanale(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/update":
                updateCanale(request, response);
                break;
            default:
                listCanale(request, response);
                break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
 
    private void listCanale(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Canale> listCanale = canaleDAO.listAllCanale();
        request.setAttribute("listCanale", listCanale);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/listacanale.jsp");
        dispatcher.forward(request, response);
    }
 
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/formcanale.jsp");
        dispatcher.forward(request, response);
    }
 
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Canale existingCanale = canaleDAO.getCanale(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/formcanale.jsp");
        request.setAttribute("canale", existingCanale);
        dispatcher.forward(request, response);
    }
 
    private void insertCanale(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
    	String nome=request.getParameter("nome");
		String immagine=request.getParameter("immagine");
        Canale newCanale = new Canale(nome, immagine);
        canaleDAO.insertCanale(newCanale);
        response.sendRedirect("list");
    }
 
    private void updateCanale(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
    	String nome=request.getParameter("nome");
		String immagine=request.getParameter("immagine");
		int id=Integer.parseInt(request.getParameter("id"));
		Canale newCanale = new Canale(id, nome, immagine);
        canaleDAO.updateCanale(newCanale);
        response.sendRedirect("list");
    }
 
    private void deleteCanale(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
 
        Canale canale = new Canale(id);
        canaleDAO.deleteCanale(canale);
        response.sendRedirect("list");
    }
}
 
  