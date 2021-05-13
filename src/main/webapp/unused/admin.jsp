<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.sql.*" %>
	


<!DOCTYPE html><html>
<head>
<meta charset="UTF-8">
<title>Admin panel</title>
<link href="./css/admin.css" rel="stylesheet" type="text/css">
</head>
<body>


<%
/* while (rs.next())
{
	out.println(rs.getString("username"));
	out.println("<br>");
}


rs.close(); */

	if(session.getAttribute("user")==null){
		
		response.sendRedirect("login.html");
	}

%>
<div class="container">
<h1> PANNELLO ADMIN</h1><br>
<select name="type" id="query-select" onchange="displayHandler()">
    <option value="programma">Programma</option>
    <option value="canale">Canale</option>
    <option value="utente">Utente</option>
</select>
<form class="addProgram" id="addProgram"  method="post" action="addProgram">

	<span class="query_field">Nome programma:</span>
		<input type="text" name="nome">
	<span class="query_field">Descrizione programma:</span>
		<input type="text" name="descrizione">
	<span class="query_field">Genere:</span>
		<input type="text" name="genere">
	<span class="query_field">E' una serie TV?</span>
		<input id="checkbox" type="checkbox" checked="checked" name="istvseries">
	<div id="serie-tv">
		<span class="query_field">Numero stagione:</span>
			<input type="text" name="num_stagione">
		<span class="query_field">Numero episodio:</span>
			<input type="text" name="num_episodio">
	</div>
	<span class="query_field">Link scheda Wikipedia:</span>
		<input type="text" name="link_scheda">
	<span class="query_field">Link immagine descrittiva:</span>
		<input type="text" name="link_immagine">
	<span class="query_field">Data inizio:</span>
		<input type="datetime-local" name="data_inizio" value="2021-06-06T08:30">
	<span class="query_field">Data fine:</span>
		<input type="datetime-local" name="data_fine" value="2021-06-06T10:30">
	<span class="query_field">Canale:</span>
		<select name="canale">
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
		</select>
	<br><br>
	<input type="submit" value="Inserisci">
	
</form>

<form class="addChannel" id="addChannel"  method="post" action="addChannel">

	<span class="query_field">Nome canale:</span>
		<input type="text" name="nome">
	<input type="submit" value="Inserisci">
	
</form>

<form class="addUser" id="addUser"  method="post" action="addUser">

	<span class="query_field">Nome utente:</span>
		<input type="text" name="username">
	<span class="query_field">Password:</span>
		<input type="password" name="password">
	<span class="query_field">Email:</span>
		<input type="email" name="email">
	<span class="query_field">Mail giornaliere?</span>
		<input id="checkboxmail" type="checkbox" checked="checked" name="mailgiornaliere">
	<span class="query_field">Admin?</span>
		<input id="checkboxadmin" type="checkbox" name="checkboxadmin">
	<br>
	<input type="submit" value="Inserisci">
	
</form>
	<div class="footermenu" style="display:flex;">
		<input id="homepage" type="button" value="HomePage" name="Homepage" onclick="window.location = 'homepage.jsp'" />
		<input id="login" type="button" value="Login" name="Login" onclick="window.location = 'login.html'" />
	</div>
	<br>
</div>
</body>
<script>
var checkbox = document.getElementById('checkbox');
var box = document.getElementById('serie-tv');
checkbox.onclick = function() {
    console.log(this);
    if (this.checked) {
        box.style['display'] = 'flex';
    } else {
        box.style['display'] = 'none';
    }
};
function displayHandler() {
	var queryselect = document.getElementById('query-select');
	var divprogramma = document.getElementById('addProgram');
	var divcanale = document.getElementById('addChannel');
	var divutente = document.getElementById('addUser');
    if (queryselect.value == 'programma') {
    	divprogramma.style['display'] = 'flex';
    	divcanale.style['display'] = 'none';
    	divutente.style['display'] = 'none';
    } else if (queryselect.value == 'canale')  {
    	divprogramma.style['display'] = 'none';
    	divcanale.style['display'] = 'flex';
    	divutente.style['display'] = 'none';
    } else if (queryselect.value == 'utente')  {
    	divprogramma.style['display'] = 'none';
    	divcanale.style['display'] = 'none';
    	divutente.style['display'] = 'flex';
    }
};
</script>
</html>