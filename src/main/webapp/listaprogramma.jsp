<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="com.guida.Model.Utente"%>
<%@ page import="java.sql.*" %>

<c:set var="bodyContent">
	<%
		Utente utente = (Utente) session.getAttribute("utente");
		if(utente == null){
			response.sendRedirect("/guidatv/login.html");
		}
		else if ( utente.getIsAdmin() != true )
		{
			response.sendRedirect("/guidatv/homepage.jsp");
		}
	%>
	<style type="text/css">@import url("/guidatv/css/admin.css");</style>
	<style type="text/css">@import url("/guidatv/css/ricercaprogramma.css");</style>
	<div align="center">
        <h1>Lista Programmi</h1>
        <div class="menu">
	        <a href="/guidatv/admin/programma/new"><button type="button">Aggiungi programma</button></a>
	        &nbsp;&nbsp;&nbsp;
	        <a href="/guidatv/admin/programma/list"><button type="button">Lista Programma</button></a>
	        &nbsp;&nbsp;&nbsp;
	        <a href="/guidatv/admin/canale/new"><button type="button">Aggiungi Canale</button></a>
	        &nbsp;&nbsp;&nbsp;
	        <a href="/guidatv/admin/canale/list"><button type="button">Lista Canali</button></a>
        </div>
	</div>
	<form action="/guidatv/admin/programma/list" method="post">
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
		
		<input type="submit" value="Cerca" />
	</form>
    <div align="center">
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Descrizione</th>
                <th>Genere</th>
                <th>isTvShow</th>
                <th>Numero Stagione</th>
                <th>Numero Episodio</th>
                <th>Link Scheda</th>
                <th>Link Immagine</th>
                <th>Data Inizio</th>
                <th>Data Fine</th>
                <th>Canale</th>
                <th>Modifica</th>
            </tr>
            <c:forEach var="programma" items="${listProgramma}">
                <tr>
                    	<td><c:out value="${programma.id}" /></td>
                    <td><c:out value="${programma.nome}" /></td>
                    <td><c:out value="${programma.descrizione}" /></td>
                    <td><c:out value="${programma.genere}" /></td>
                    <td><c:out value="${programma.isTvShow}" /></td>
                    <td><c:out value="${programma.numero_stagione}" /></td>
                    <td><c:out value="${programma.numero_episodio}" /></td>
                    <td><c:out value="${programma.link_scheda}" /></td>
                    <td><c:out value="${programma.link_immagine}" /></td>
                    <td><c:out value="${programma.data_inizio}" /></td>
                    <td><c:out value="${programma.data_fine}" /></td>
                    <td><c:out value="${programma.id_canale}" /></td>
                    <td>
                        <a href="/guidatv/admin/programma/edit?id=<c:out value='${programma.id_orario}' />">Modifica</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="/guidatv/admin/programma/delete?id=<c:out value='${programma.id_orario}' />">Elimina</a>                     
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

