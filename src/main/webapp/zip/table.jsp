<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.*"%>
<%@page import="com.guida.Model.Programma"%>
<%@page import="com.guida.Model.Canale"%>
<%@ page import="java.sql.Timestamp" %>
<%@page import="java.time.*"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	

	<style type="text/css">@import url("/guidatv/css/guida.css");</style>
	<form action="/guidatv/canale/homepage">
		<div align="center">
			<fmt:formatDate var="fmtDate" value="<%=new java.util.Date()%>" pattern="YYYY-MM-dd"/>  
			<input type="date" name="data_calendario"  value="${fmtDate}" />
		</div>
	</form>
				

<%
Map<Canale, List<Programma>> programs = new HashMap<>();

String sql = "select op.*,p.*, c.*, "
		+ "TIMESTAMPDIFF (MINUTE, op.data_inizio, op.data_fine) as durata "
		+ "from orari_programma as op "
		+ "join programma p on p.id = op.id_programma " 
		+ "join canale c on c.id = op.id_canale "
		+ "where date(op.data_inizio) = DATE(?) "
		+ "ORDER by op.id_canale";
Class.forName("com.mysql.jdbc.Driver");
Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/guidatv", "artur", "Arturho22");
PreparedStatement statement = con.prepareStatement(sql);
String data =  (String) pageContext.getAttribute("data_calendario");
if (data == null)
{
	data = java.time.LocalDate.now().toString();
}
statement.setString(1, data);
ResultSet rs = statement.executeQuery();


while (rs.next()) {

	Canale canale = new Canale();
	canale.setId(rs.getInt("id_canale"));
	canale.setNome(rs.getString("c.nome"));
	canale.setImmagine(rs.getString("c.immagine"));

	List<Programma> list = programs.getOrDefault(canale, new ArrayList<Programma>());

	Programma program = new Programma();
	program.setId(rs.getInt("id_programma"));
	program.setNome(rs.getString("p.nome"));
	program.setdata_inizio(Timestamp.valueOf(rs.getString("data_inizio")));
	program.setdata_fine(Timestamp.valueOf(rs.getString("data_fine")));
	program.setDurata(rs.getInt("durata"));
	program.setDescrizione(rs.getString("descrizione"));

	list.add(program);

	programs.put(canale, list);

}
%>

	<div class="container">
		<div class="channels">
			<div class="title">
				<p>Canali</p>
			</div>
			<%
			Iterator<Entry<Canale, List<Programma>>> iter = programs.entrySet().iterator();

			while (iter.hasNext()) {
				Entry<Canale, List<Programma>> entry = iter.next();
				Canale canale = entry.getKey();
				List<Programma> list = entry.getValue();
			%>
			<div class="channel">
				<p>
					<img
						src="<%=canale.getImmagine()%>" />
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
				<div class="p h<%= program.getDurata() %>" onclick="showDetails('<%= channel.getId() %>', '<%= program.getNome() %>', '<%= program.getDescrizione()%>')">
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
