<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.guida.Model.Programma"%>
<%@ page import="com.guida.Model.Utente"%>


<%
		Utente utente = (Utente) session.getAttribute("utente");
		if(utente == null){
			response.sendRedirect("/guidatv/login.html");
		}
		else if ( utente.gethas_confirmed() != true )
		{
			response.sendRedirect("/guidatv/verifica.jsp");
		}
%>
<c:set  var="bodyContent">
	<style type="text/css">@import url("/guidatv/css/scheda.css");</style>

    <center>
        <h1>Scheda <c:out value="${programma.nome}" /></h1>
    </center>
    <div class="scheda-container">
	    <div align="center" class="scheda-singola">
	    	<div class="scheda-bg">
				<img src="<c:out value="${programma.link_immagine}" />" alt="<c:out value="${programma.nome}" />">
				<h2>Nome programma:</h2>
		    	<c:out value="${programma.nome}" />
		    	<h2>Descrizione programma:</h2>
		    	<c:out value="${programma.descrizione}" />
		    	<h2>Genere programma:</h2>
		    	<c:out value="${programma.genere}" />
		    	<h2>Tipologia:</h2>
		    	<c:choose>
					<c:when test="${programma.isTvShow == true}">
						Serie TV
					</c:when>
					<c:otherwise>
						Film
					</c:otherwise>
				</c:choose>
		    	<h2>Link Scheda Wikipedia:</h2>
		    	<a href="<c:out value="${programma.link_scheda}" />">Link Scheda</a>
		    	<br><br><br>
		    	
		        <table border="1" cellpadding="5">
		            <caption>Programmazione<br></caption>
		            <tr>
		                <th>Canale</th>
		                <th>Genere</th>
		                <th>Inizio</th>
		                <th>Fine</th>
		               <c:choose>
							<c:when test="${programma.isTvShow == true}">
								<th>Stagione</th>
								<th>Episodio</th>
							</c:when>
						</c:choose>
		            </tr>
		            
		            <c:forEach var="orari" items="${listOrari}">
		                <tr>
		                    <td><a href="/guidatv/scheda/canale?id=<c:out value="${orari.id_canale}" />"><c:out value="${orari.nome_canale}" /></a></td>
		                    <td><c:out value="${orari.genere}" /></td>
		                    <td><c:out value="${orari.data_inizio}" /></td>
		                    <td><c:out value="${orari.data_fine}" /></td>
		                    <c:choose>
								<c:when test="${programma.isTvShow == true}">
									<td><c:out value="${orari.numero_stagione}" /></td>
									<td><c:out value="${orari.numero_episodio}" /></td>
								</c:when>
							</c:choose>
		                </tr>
		            </c:forEach>
		        </table>
		    </div>
		</div>
	</div>
</c:set>
    
<t:menu>
	<jsp:body>
	    ${bodyContent}
	</jsp:body>
</t:menu>
