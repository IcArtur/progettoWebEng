<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
	if(session.getAttribute("user")==null){
		response.sendRedirect("login.html");
	}
%>
<t:menu>
    welcome ${user}
	<form action="Logout">
		<input type="submit" value="Logout">
	</form>
</t:menu>
</body>
</html>