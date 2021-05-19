<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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




<c:set var="bodyContent">
	<style type="text/css">@import url("/guidatv/css/ricercaprogramma.css");</style>
	<style type="text/css">@import url("/guidatv/css/admin.css");</style>
	
	<form method="post">
		<div class="filter">
			<span>Titolo:</span>
			<input type=text name="nome" value="<c:out value='${nome}' />" >
		</div>
		<div class="filter">
			<span>Genere:</span>
			<input type=text name="genere" value="<c:out value='${genere}' />">
		</div>
		<div class="filter">
			<span>Ora inizio minimo:</span>
			<input type="time" name="oramin" value="<c:out value='${oramin}' />">
		</div>
		<div class="filter">
			<span>Ora inizio massimo:</span>
			<input type="time" name="oramax" value="<c:out value='${oramax}' />">
		</div>
		<div class="filter">
			<span>Dal:</span>
			<input type="date" name="datamin" value="<c:out value='${datamin}' />">
		</div>
		<div class="filter">
			<span>Al:</span>
			<input type="date" name="datamax" value="<c:out value='${datamax}' />">
		</div>
		<div class="filter">
			<span>Canale:</span>
			<select name="id_canale">
				<option value="">------</option>
			<%
					try {
						Class.forName("com.mysql.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/guidatv", "artur", "Arturho22");
						Statement st = con.createStatement();
						String query = "SELECT * FROM guidatv.canale";
						ResultSet rs = st.executeQuery(query);
						while (rs.next()) {
							%>
						<option value="<%=rs.getInt("id")%>" ><%=rs.getString("nome") %></option>
						<%
						}
					} catch (Exception e) {
						
					} %>
			</select>
		</div>
		
		<div class="filter">
			<input type="submit" formaction="/guidatv/programma/findMail" style="display:block" value="Cerca" />
			<input type="submit" formaction="/guidatv/programma/saveRicerca" style="display:block;width:auto;margin-top:10px" value="Salva Ricerca" />
		</div>
		<input name="id" type="hidden" value="<c:out value='${utente.id}' />" />
	</form>
	<div align="center">
	<%
					try {
						Class.forName("com.mysql.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/guidatv", "artur", "Arturho22");
						Statement st = con.createStatement();
						String query = "SELECT * FROM guidatv.mailgiornaliere";
						ResultSet rs = st.executeQuery(query);
						while (rs.next()) {
						%>
						<div class="saved-search">
							<span class="writes">Ricerca numero <%=rs.getRow() %></span>
							<a href="/guidatv/programma/deleteSearch?id=<%=rs.getInt("id")%>"><i class="fas fa-window-close"></i></a>
						</div>
						<%
						}
						
						
					} catch (Exception e) {
						
					}
				%>
	</div>
    <div align="center">
        <table border="1">
            <tr>
                <th>Nome</th>
                <th>Descrizione</th>
                <th>Genere</th>
                <th>Tipologia</th>
                <th>Numero Stagione</th>
                <th>Numero Episodio</th>
                <th>Data Inizio</th>
                <th>Data Fine</th>
                <th>Canale</th>
                <th>Scheda</th>
            </tr>
            <c:forEach var="programma" items="${listProgramma}">
                <tr>
                    <td><c:out value="${programma.nome}" /></td>
                    <td><c:out value="${programma.descrizione}" /></td>
                    <td><c:out value="${programma.genere}" /></td>
                    <td>
                    <%
						try {
							Programma newProgramma = (Programma) pageContext.getAttribute("programma");
							if (newProgramma.getIsTvShow() == true) {
								%>
								Serie TV
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
                    </td>
                    <td>
                    	<c:if test="${programma.numero_stagione > 0}">
							<c:out value="${programma.numero_stagione}" />
						</c:if>
                    </td>
                    <td>
                    	<c:if test="${programma.numero_episodio > 0}">
							<c:out value="${programma.numero_episodio}" />
						</c:if>
                    </td>
                    <td><c:out value="${fn:substring(programma.data_inizio, 0, 19)}" /></td>
                    <td><c:out value="${fn:substring(programma.data_fine, 0, 19)}" /></td>
                    <td><c:out value="${programma.nome_canale}" /></td>
                    <td>
                        <a href="/guidatv/scheda/programma?id=<c:out value='${programma.id}' />">Scheda</a>                  
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>   
</c:set>
<t:menu>
	<jsp:body>
	    ${bodyContent}
	</jsp:body>
</t:menu>


