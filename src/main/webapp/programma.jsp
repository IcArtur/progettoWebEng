<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.guida.Model.Programma"%>



<%
	if(session.getAttribute("utente")==null){
		response.sendRedirect("/guidatv/login.html");
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
		    	<%
						try {
							Programma newProgramma = (Programma) request.getAttribute("programma");
							if (newProgramma.getIsTvShow() == true) {
								%>
								Serie TV
								<h2>Numero Stagione:</h2>
						    	<c:out value="${programma.numero_stagione}" />
						    	<h2>Numero Episodio:</h2>
						    	<c:out value="${programma.numero_episodio}" />
								<%
							}
							else {
								%>
								Film
								<%
							}
						} catch (Exception e) {
							
						}
					%>
		    	<h2>Link Scheda Wikipedia:</h2>
		    	<c:out value="${programma.descrizione}" />
		    	<br><br><br>
		    	
		    	
		        <table border="1" cellpadding="5">
		            <caption>Programmazione<br></caption>
		            <tr>
		                <th>Canale</th>
		                <th>Inizio</th>
		                <th>Fine</th>
		            </tr>
		            
		            <c:forEach var="orari" items="${listOrari}">
		                <tr>
		                    <td><c:out value="${orari.nome_canale}" /></td>
		                    <td><c:out value="${orari.data_inizio}" /></td>
		                    <td><c:out value="${orari.data_fine}" /></td>
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
