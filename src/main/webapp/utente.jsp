<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="java.sql.*" %>
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
<style type="text/css">@import url("/guidatv/css/admin.css");</style>
<div align="center">
        <h1>Utente ${utente.username}</h1>
</div>
    <div align="center">
        <form action="/guidatv/utente/update" method="post">
        <table border="1" cellpadding="5">
            <caption>
                <h2>
                    Modifica Dati Personali
                </h2>
            </caption>
            <tr>
                <th>Username: </th>
                <td>
                    <input type="text" name="username" size="45" value="<c:out value='${utente.username}' />"/>
                </td>
            </tr>
            <tr>
                <th>Password: </th>
                <td>
                    <input type="password" name="password" size="45" value="<c:out value='${utente.password}' />"/>
                </td>
            </tr>
            <tr>
                <th>Email: </th>
                <td>
                    <input type="email" name="email" size="45" value="<c:out value='${utente.email}' />"/>
                </td>
            </tr>
            <tr>
                <th>Mail giornaliere: </th>
                <td>
                	<div style="display:flex">
	                    <input type="checkbox" id="mail_giornaliere"  name="mail_giornaliere" onchange="valueChanged()"
	                    <c:choose>
							<c:when test="${utente.mail_giornaliere == true}">
								checked="true"
							</c:when>
						</c:choose> >
						<a href="/guidatv/programma/findMail">
							<input type="button"  id="toMailGiornaliere" 
							style="margin-left:20%" value="Imposta mail giornaliera">
						</a>
					</div>
                </td>
            </tr>
            
            
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Salva" />
                </td>
            </tr>
        </table>
        <input name="id" type="hidden"
                            value="<c:out value='${utente.id}' />"
                        />
        </form>
    </div>
    <script type="text/javascript">
	    $(document).ready(function(){
            $("#toMailGiornaliere").hide();
	    	if($('#mail_giornaliere').is(":checked"))   
	            $("#toMailGiornaliere").show();
	        else
	            $("#toMailGiornaliere").hide();
	    });
	    function valueChanged()
	    {
	        if($('#mail_giornaliere').is(":checked"))   
	            $("#toMailGiornaliere").show();
	        else
	            $("#toMailGiornaliere").hide();
	    }
</script>
</c:set>
<t:menu>
	<jsp:body>
	    ${bodyContent}
	</jsp:body>
</t:menu>