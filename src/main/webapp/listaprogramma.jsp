<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="bodyContent">
	<%
		if(session.getAttribute("user")==null){
			response.sendRedirect("/guidatv/login.html");
		}
		else if ( ( (Boolean) session.getAttribute("isAdmin") ).booleanValue() != true )
		{
			response.sendRedirect("/guidatv/login.html");
		}
	%>
	<style type="text/css">@import url("/guidatv/css/admin.css");</style>
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
    <div align="center">
        <table border="1" cellpadding="5">
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

