<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.guida.Model.Programma"%>
<%@page import="java.util.List"%>
<%@page import="com.guida.Model.Canale"%>
<%@page import="java.util.Map"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/guida.css">
</head>
<body>

	<%
	Map<Canale, List<Programma>> programs = new HashMap<>();

	Class.forName("com.mysql.cj.jdbc.Driver");

	Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/guidatv", "root", "");

	Statement stmt = conn.createStatement();

	ResultSet rs = stmt.executeQuery("select \r\n" + "op.id_canale, op.id_programma, op.data_inizio, op.data_fine,\r\n"
			+ "p.nome as nome_programma, p.descrizione, p.genere,\r\n" + "c.nome as nome_canale,\r\n"
			+ "TIMESTAMPDIFF (MINUTE, op.data_inizio, op.data_fine) as durata\r\n" + "from orari_programma as op\r\n"
			+ "join programma p on p.id = op.id_programma\r\n" + "join canale c on c.id = op.id_canale\r\n"
			+ "where date(op.data_inizio) < date(now())"
			+ "ORDER by id_canale");

	while (rs.next()) {

		Canale channel = new Canale();
		channel.setId(rs.getInt("id_canale"));
		channel.setNome(rs.getString("nome_canale"));

		List<Programma> list = programs.getOrDefault(channel, new ArrayList<Programma>());

		Programma program = new Programma();
		program.setId(rs.getInt("id_programma"));
		program.setNome(rs.getString("nome_programma"));
		program.setdata_inizio(rs.getString("data_inizio"));
		program.setdata_fine(rs.getString("data_fine"));
		program.setDurata(rs.getInt("durata"));
		program.setDescrizione(rs.getString("descrizione"));

		list.add(program);

		programs.put(channel, list);

	}
	%>

	<div class="container">
		<div class="channels">
			<div class="title">
				<p>Channels</p>
			</div>
			<%
			Iterator<Entry<Canale, List<Programma>>> iter = programs.entrySet().iterator();

			while (iter.hasNext()) {
				Entry<Canale, List<Programma>> entry = iter.next();
				Canale channel = entry.getKey();
				List<Programma> list = entry.getValue();
			%>
			<div class="channel">
				<p>
					<img
						src="${channel.getImmagine()}" />
				</p>
			</div>
			<%
			}
			%>
		</div>
		<!-- end channels -->

		<div class="program">
			<div class="hours">
				<div class="h">
					<p>01.00</p>
				</div>
				<div class="h">
					<p>02.00</p>
				</div>
				<div class="h">
					<p>03.00</p>
				</div>
				<div class="h">
					<p>04.00</p>
				</div>
				<div class="h">
					<p>05.00</p>
				</div>
				<div class="h">
					<p>06.00</p>
				</div>
				<div class="h">
					<p>07.00</p>
				</div>
				<div class="h">
					<p>08.00</p>
				</div>
				<div class="h">
					<p>09.00</p>
				</div>
				<div class="h">
					<p>10.00</p>
				</div>
				<div class="h">
					<p>11.00</p>
				</div>
				<div class="h">
					<p>12.00</p>
				</div>
				<div class="h">
					<p>13.00</p>
				</div>
				<div class="h">
					<p>14.00</p>
				</div>
				<div class="h">
					<p>15.00</p>
				</div>
				<div class="h">
					<p>16.00</p>
				</div>
				<div class="h">
					<p>17.00</p>
				</div>
				<div class="h">
					<p>18.00</p>
				</div>
				<div class="h">
					<p>19.00</p>
				</div>
				<div class="h">
					<p>20.00</p>
				</div>
				<div class="h">
					<p>21.00</p>
				</div>
				<div class="h">
					<p>22.00</p>
				</div>
				<div class="h">
					<p>23.00</p>
				</div>
				<div class="h">
					<p>00.00</p>
				</div>
			</div>

			<%
			iter = programs.entrySet().iterator();

			while (iter.hasNext()) {
				Entry<Canale, List<Programma>> entry = iter.next();
				Canale channel = entry.getKey();
				List<Programma> list = entry.getValue();
			%>

			<div class="channelrow">
			
			<%
			Iterator<Programma> pIter = list.iterator();
			while (pIter.hasNext()) {
				Programma program = pIter.next();
			%>
				<div class="p h<%= program.getDurata() %>" onclick="showDetails('<%= channel.getId() %>', '<%= program.getName() %>', '<%= program.getDescrizione()%>')">
					<p>
						<%= program.getNome() %>
					</p>
				</div>
			<%
			}
			%>
			
			</div> <!-- channelrow -->

			<%
			}
			%>

		</div>
		
		<div class="overlay">
		test
		</div>
		<!-- end program -->
	</div>
	<!--  end container -->




	<%-- 	<tr class="channel-row">
		<td class="channel-logo"><%=channel.getName()%></td>

		<td class="program-list">
			<%
			Iterator<Program> pIter = list.iterator();
			while (pIter.hasNext()) {
				Program p = pIter.next();
			%>
			<div class="program w-<%=p.getDurata()%>"><%=p.getName()%></div> <%
 }
 %>

		</td>
	</tr> --%>
	
	<script>
	var dialog;
	function showDetails(id, name, desc) {
		document.querySelector('.overlay').classList.add('show');
		const container = document.querySelector('.container');
		dialog = document.createElement('div');
		dialog.className = 'dialog';
		const content = `
		<div class="closeBtn" >&#10006</div>
		<div class="logo-wrapper">
			<div><img
			src="canali/logo_`+id+`_hd.png" /></div>
			<div>`+name+`</div>
		</div>
		<div>
			`+desc+`
		</div>
		`;
		dialog.innerHTML = content;
		container.appendChild(dialog);
		document.querySelector('.closeBtn').addEventListener('click', e => {
			dialog.remove();
			document.querySelector('.overlay').classList.remove('show');
		});
	}
	

	</script>


</body>
</html>