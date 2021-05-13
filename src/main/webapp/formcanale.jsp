<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="java.sql.*" %>

<t:menu>
<style type="text/css">@import url("/guidatv/css/admin.css");</style>
<div align="center">
        <h1>Modifica Canali</h1>
        <div class="menu">
	        <a href="/guidatv/admin/new"><button type="button">Aggiungi programma</button></a>
	        &nbsp;&nbsp;&nbsp;
	        <a href="/guidatv/admin/list"><button type="button">Lista Programma</button></a>
	        &nbsp;&nbsp;&nbsp;
	        <a href="/guidatv/canale/new"><button type="button">Aggiungi Canale</button></a>
	        &nbsp;&nbsp;&nbsp;
	        <a href="/guidatv/canale/list"><button type="button">Lista Canali</button></a>
        </div>
</div>
    <div align="center">
        <c:if test="${canale != null}">
            <form action="update" method="post">
        </c:if>
        <c:if test="${canale == null}">
            <form action="insert" method="post">
        </c:if>
        <table border="1" cellpadding="5">
            <caption>
                <h2>
                    <c:if test="${canale != null}">
                        Modifica Canale
                    </c:if>
                    <c:if test="${canale == null}">
                        Aggiungi Canale
                    </c:if>
                </h2>
            </caption>
                <c:if test="${canale != null}">
                    <input type="hidden" name="id" value="<c:out value='${canale.id}' />" />
                </c:if>	    
            <tr>
                <th>Nome canale: </th>
                <td>
                    <input type="text" name="nome" size="45"
                            value="<c:out value='${canale.nome}' />"
                        />
                </td>
            </tr>
            <tr>
                <th>Immagine: </th>
                <td>
                    <input type="text" name="descrizione" size="45"
                            value="<c:out value='${canale.immagine}' />"
                        />
                </td>
            </tr>
            
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Salva" />
                </td>
            </tr>
        </table>
        <input name="id" type="hidden"
                            value="<c:out value='${canale.id}' />"
                        />
        </form>
    </div>   
</t:menu>