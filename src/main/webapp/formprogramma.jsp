<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ page import="java.sql.*" %>


<c:set var="bodyContent">
	<style type="text/css">@import url("/guidatv/css/admin.css");</style>
	<div align="center">
		<h1>Modifica Programmi</h1>
		<div class="menu">
			<a href="/guidatv/admin/new"><button type="button">Aggiungi
					programma</button></a> &nbsp;&nbsp;&nbsp; <a href="/guidatv/admin/list"><button
					type="button">Lista Programma</button></a> &nbsp;&nbsp;&nbsp; <a
				href="/guidatv/canale/new"><button type="button">Aggiungi
					Canale</button></a> &nbsp;&nbsp;&nbsp; <a href="/guidatv/canale/list"><button
					type="button">Lista Canali</button></a>
		</div>
	</div>
	<div align="center">
		<c:if test="${programma != null}">
			<form action="update" method="post">
		</c:if>
		<c:if test="${programma == null}">
			<form action="insert" method="post">
		</c:if>
		<table border="1" cellpadding="5">
			<caption>
				<h2>
					<c:if test="${programma != null}">
	                        Modifica Programma
	                    </c:if>
					<c:if test="${programma == null}">
	                        Aggiungi Programma
	                    </c:if>
				</h2>
			</caption>
			<c:if test="${programma != null}">
				<input type="hidden" name="id"
					value="<c:out value='${programma.id}' />" />
			</c:if>
			<tr>
				<th>Nome programma:</th>
				<td><input type="text" name="nome" size="45"
					value="<c:out value='${programma.nome}' />" /></td>
			</tr>
			<tr>
				<th>Descrizione:</th>
				<td><input type="text" name="descrizione" size="45"
					value="<c:out value='${programma.descrizione}' />" /></td>
			</tr>
			<tr>
				<th>Genere:</th>
				<td><input type="text" name="genere" size="45"
					value="<c:out value='${programma.genere}' />" /></td>
			</tr>
			<tr>
				<th>Link scheda Wikipedia:</th>
				<td><input type="text" name=link_scheda size="45"
					value="<c:out value='${programma.link_scheda}' />" /></td>
			</tr>
			<tr>
				<th>Link immagine:</th>
				<td><input type="text" name="link_immagine" size="45"
					value="<c:out value='${programma.link_immagine}' />" /></td>
			</tr>
			<tr>
				<th>E' una serie TV?</th>
				<td><input type="checkbox" name="isTvShow" size="45"
					value="<c:out value='${programma.link_immagine}' />" /></td>
			</tr>
			<tr>
				<th>Numero stagione:</th>
				<td><input type="number" name="numero_stagione" size="45"
					value="<c:out value='${programma.numero_stagione}' />" /></td>
			</tr>
			<tr>
				<th>Numero episodio:</th>
				<td><input type="number" name="numero_episodio" size="45"
					value="<c:out value='${programma.numero_episodio}' />" /></td>
			</tr>
			<tr>
				<th>Data e ora inizio:</th>
				<td><input type="datetime-local" name="data_inizio" size="45"
					value="<c:out value='${fn:replace(programma.data_inizio, " ", "T")}' />" />
				</td>
			</tr>
			<tr>
				<th>Data e ora fine:</th>
				<td><input type="datetime-local" name="data_fine" size="45"
					value="<c:out value='${fn:replace(programma.data_fine, " ", "T")}' />" />
				</td>
			</tr>
			<tr>
				<th>Canale:</th>
				<td><select name="id_canale">
						<option>---Scegli il canale---</option>
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
						
					}
				%>
				</select></td>
			</tr>

			<tr>
				<td colspan="2" align="center"><input type="submit"
					value="Salva" /></td>
			</tr>
		</table>
		<input name="id_orario" type="hidden"
			value="<c:out value='${programma.id_orario}' />" /> <input name="id"
			type="hidden" value="<c:out value='${programma.id}' />" />
		</form>
	</div>
</c:set>
<t:menu>
	<jsp:body>
	    ${bodyContent}
	</jsp:body>
</t:menu>
