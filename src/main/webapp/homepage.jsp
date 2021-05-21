<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.guida.Model.Programma"%>
<%@ page import="com.guida.Model.Utente"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.*"%>
<%@page import="com.guida.Model.Canale"%>
<%@page import="java.time.*"%>
<%@page import="java.sql.*"%>



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
	<style type="text/css">@import url("/guidatv/css/guida.css");</style>
	<form action="/guidatv/scheda/homepage" method="POST">
		<div align="center">
			<fmt:formatDate var="fmtDate" value="<%=new java.util.Date()%>" pattern="YYYY-MM-dd"/>  
			<input type="date" name="data_calendario"  value="${fmtDate}" />
			<input type="submit" value="Cerca" />
		</div>
	</form>

<%
Map<Canale, List<Programma>> programs = new HashMap<>();

String day =  (String) request.getAttribute("data_calendario");
String today = java.time.LocalDate.now().toString();
String now = java.time.LocalDateTime.now().toString();
LocalTime midnight = LocalTime.MIDNIGHT;
LocalDate tdy = LocalDate.now(ZoneId.of("Europe/Berlin")).plusDays(1);
String todayMidnight = LocalDateTime.of(tdy, midnight).toString();
Integer hour = 0;
String sql = "";
Class.forName("com.mysql.jdbc.Driver");
Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/guidatv", "artur", "Arturho22");
PreparedStatement statement;
Calendar calendar = Calendar.getInstance();
java.util.Date oggi = new java.util.Date();
calendar.setTime(oggi);
if (day == null) {
	day = today;
}
if (day.equals(today)) {
	hour = calendar.get(Calendar.HOUR_OF_DAY);
	sql = 	"select op.*,p.*, c.*, "
			+ "TIMESTAMPDIFF (MINUTE, op.data_inizio, op.data_fine) as durata "
			+ "from orari_programma as op "
			+ "join programma p on p.id = op.id_programma " 
			+ "join canale c on c.id = op.id_canale "
			+ "where date(op.data_inizio) = DATE(?) "
			+ "and timestamp(op.data_fine) > timestamp(?) "
			+ "and timestamp(op.data_inizio) < timestamp(?) "
			+ "ORDER by op.data_inizio";
	statement = con.prepareStatement(sql);
	statement.setString(1, day);
	statement.setString(2, now);
	statement.setString(3, todayMidnight);
}
else {
	hour = 0;
	sql = "select op.*,p.*, c.*, "
			+ "TIMESTAMPDIFF (MINUTE, op.data_inizio, op.data_fine) as durata "
			+ "from orari_programma as op "
			+ "join programma p on p.id = op.id_programma " 
			+ "join canale c on c.id = op.id_canale "
			+ "where date(op.data_inizio) = DATE(?) "
			+ "ORDER by op.data_inizio";
	statement = con.prepareStatement(sql);
	statement.setString(1, day);
}
double hours_remaning = 26.3 - hour;



ResultSet rs = statement.executeQuery();


while (rs.next()) {

	Canale canale = new Canale(rs.getInt("id_canale"), rs.getString("c.nome"),rs.getString("c.immagine"));

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

		<div class="program" style="width: calc(var(--hw)* <%=hours_remaning %>);">
			<div class="hours">
				<%
				while (hour <= 24) {
					%>
					<div class="h">
					<p><%=hour%>.00</p>
					</div>
					<%
					hour = hour + 1;
				}
				%>
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
				Timestamp data_inizio1 = program.getdata_inizio();
				Timestamp built_timestamp = Timestamp.valueOf(String.format("%d-%d-%d %d:00:00", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY)));  
				if(data_inizio1.before(built_timestamp) && day.equals(today)) {
					long diffInMS =  program.getdata_fine().getTime() - built_timestamp.getTime();
					int diffInMin = (int) diffInMS / 60000;
					program.setDurata(diffInMin);
					
				}
			%>
				<div class="p" style="width: calc(var(--hw)* <%= ((float)program.getDurata())/60 %>);" onclick="showDetails('<%= channel.getId() %>', '<%= program.getNome() %>', '<%= program.getDescrizione()%>')">
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
		
		<div class="overlay-bis">
		test
		</div>
		<!-- end program -->
	</div>
	
	<script>
	var dialog;
	function showDetails(id, name, desc) {
		document.querySelector('.overlay-bis').classList.add('show');
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
			document.querySelector('.overlay-bis').classList.remove('show');
		});
	}
	

	</script>

</c:set>
<t:menu>
	<jsp:body>
	    ${bodyContent}
	</jsp:body>
</t:menu>
