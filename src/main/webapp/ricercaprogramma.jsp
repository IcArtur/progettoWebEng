<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="com.guida.Model.Utente"%>
<%@ page import="java.sql.*" %>
<%@ page import="com.guida.Model.Programma"%>




<c:set var="bodyContent">
	<style type="text/css">@import url("/guidatv/css/ricercaprogramma.css");</style>
	<%
		Utente utente = (Utente) session.getAttribute("utente");
		if(utente == null){
			response.sendRedirect("/guidatv/login.html");
		}
	%>
	<style type="text/css">@import url("/guidatv/css/admin.css");</style>
	
	<form action="/guidatv/programma/find" method="post">
		<div class="filter">
			<span>Titolo:</span>
			<input type=text name="nome">
		</div>
		<div class="filter">
			<span>Genere:</span>
			<input type=text name="genere">
		</div>
		<div class="filter">
			<span>Ora inizio minimo:</span>
			<input type="time" name="oramin">
		</div>
		<div class="filter">
			<span>Ora inizio massimo:</span>
			<input type="time" name="oramax">
		</div>
		<div class="filter">
			<span>Dal:</span>
			<input type="date" name="datamin">
		</div>
		<div class="filter">
			<span>Al:</span>
			<input type="date" name="datamax">
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
						<option value="<%=rs.getInt("id")%>"><%=rs.getString("nome") %></option>
						<%
						}
					} catch (Exception e) {
						
					} %>
			</select>
		</div>
		
		<input type="submit" value="Cerca" />
	</form>
	
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
                    <td><c:out value="${programma.numero_stagione}" /></td>
                    <td><c:out value="${programma.numero_episodio}" /></td>
                    <td><c:out value="${programma.data_inizio}" /></td>
                    <td><c:out value="${programma.data_fine}" /></td>
                    <td><c:out value="${programma.id_canale}" /></td>
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

