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
	        <h1>Lista Canali</h1>
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
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Immagine</th>
                <th>Modifica</th>
            </tr>
            <c:forEach var="canale" items="${listCanale}">
                <tr>
                    <td><c:out value="${canale.id}" /></td>
                    <td><c:out value="${canale.nome}" /></td>
                    <td><c:out value="${canale.immagine}" /></td>
                    <td>
                        <a href="/guidatv/admin/canale/edit?id=<c:out value='${canale.id}' />">Modifica</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="/guidatv/admin/canale/delete?id=<c:out value='${canale.id}' />">Elimina</a>                     
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
