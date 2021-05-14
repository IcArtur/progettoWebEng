<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="java.sql.*" %>



<c:set  var="bodyContent">
	<style type="text/css">@import url("/guidatv/css/scheda.css");</style>

    <center>
        <h1>Scheda <c:out value="${canale.nome}" /></h1>
    </center>
    <div class="scheda-container">
	    <div align="center" class="scheda-singola">
	    	<div class="scheda-bg">
				<img src="<c:out value="${canale.immagine}" />" alt="<c:out value="${canale.nome}" />">
				<h2>Nome canale:</h2>
		    	<c:out value="${canale.nome}" />
		    	<br><br><br>
		    	
		    	
		        <table border="1" cellpadding="5">
		            <caption>Programmazione della Giornata<br></caption>
		            <tr>
		                <th>Programma</th>
		                <th>Inizio</th>
		                <th>Fine</th>
		            </tr>
		            
		            <c:forEach var="orari" items="${listOrariGiornalieri}">
		                <tr>
		                    <td><c:out value="${orari.nome}" /></td>
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
