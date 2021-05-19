	<%@tag description="Template Tag" pageEncoding="UTF-8"%>

<html lang="en">
<head>	
<title>GuidaTV</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link href="/guidatv/layout/styles/layout.css" rel="stylesheet" type="text/css" media="all">
<link href="/guidatv/css/general.css" rel="stylesheet" type="text/css" media="all">
<link rel="shortcut icon" href="/guidatv/images/favicon.ico" />

<!-- JAVASCRIPTS -->
<script src="layout/scripts/jquery.min.js"></script>
<script src="layout/scripts/jquery.backtotop.js"></script>
<script src="layout/scripts/jquery.mobilemenu.js"></script>

</head>
<body id="top">

<div class="bgded overlay"> 
  <!-- ################################################################################################ -->
  <div class="wrapper row0">
    <div id="topbar" class="hoc clear">
      <div class="fl_left"> 
        <!-- ################################################################################################ -->
        <ul class="nospace">
          <li><i class="fas fa-phone rgtspace-5"></i> +39 3202771491</li>
          <li><i class="far fa-envelope rgtspace-5"></i> info@guidatv.com</li>
        </ul>
        <!-- ################################################################################################ -->
      </div>
      <div class="fl_right"> 
        <!-- ################################################################################################ -->
        <ul class="nospace">
          <li><a href="/guidatv/homepage.jsp" title="Home"><i class="fas fa-home"></i></a></li>
          <li><a href="/guidatv/login.html" title="Login"><i class="fas fa-sign-in-alt"></i></a></li>
          <li><a href="/guidatv/registrazione.html" title="Registrati"><i class="fas fa-edit"></i></a></li>
          <li><a href="/guidatv/utente/profilo" title="Profilo"><i class="fas fa-user"></i></a></li>
          <li><a href="/guidatv/utente/logout" title="Logout"><i class="fas fa-sign-out-alt"></i></a></li>
        </ul>
        <!-- ################################################################################################ -->
      </div>
    </div>
  </div>
  
  <div class="wrapper row1">
    <header id="header" class="hoc clear">
      <div id="logo" class="fl_left"> 
        <!-- ################################################################################################ -->
        <h1><a style="color:white" href="/guidatv/homepage.jsp">Guida TV</a></h1>
        <!-- ################################################################################################ -->
      </div>
      <nav id="mainav" class="fl_right"> 
        <!-- ################################################################################################ -->
        <ul class="clear">
        <%
		if (session.getAttribute("isAdmin") == null)
		{
			
		}
		else if ( ( (Boolean) session.getAttribute("isAdmin")) == true )
		{
			%>
			<li class="active"><a href="/guidatv/admin/programma/list">Pannello admin</a></li>
			<%
		}
		%>
						
           
          
          <li><a class="drop" href="/guidatv/homepage.jsp">Homepage</a>
            <ul>
              <li><a href="pages/gallery.html">Non</a></li>
              <li><a href="pages/full-width.html">Saprei</a></li>
              <li><a href="pages/sidebar-left.html">Cosa</a></li>
              <li><a href="pages/sidebar-right.html">Metterci</a></li>
            </ul>
          </li>
          <li><a class="drop" href="/guidatv/programma/find">Ricerca Programma</a>
            <ul>
              <li><a href="#">Level 2</a></li>
              <li><a class="drop" href="#">Level 2 + Drop</a>
                <ul>
                  <li><a href="#">Level 3</a></li>
                  <li><a href="#">Level 3</a></li>
                  <li><a href="#">Level 3</a></li>
                </ul>
              </li>
              <li><a href="#">Level 2</a></li>
            </ul>
          </li>
          <li><a href="/guidatv/scheda/programma?id=22">Programma singolo</a></li>
          <li><a href="/guidatv/utente/profilo">Profilo</a></li>
        </ul>
        <!-- ################################################################################################ -->
      </nav>
    </header>
  </div>
</div>

<jsp:doBody/>

<div class="footer-wrapper">
	<div class="bgded overlay row4">
	  <footer id="footer" class="hoc clear">
	      <ul class="nospace">
	        <li class="one_quarter first">
	          <div class="block clear"><a href="#"><i class="fas fa-phone"></i></a> <span><strong>Chiamaci:</strong> +39 320 277 1491</span></div>
	        </li>
	        <li class="one_quarter">
	          <div class="block clear"><a href="#"><i class="fas fa-envelope"></i></a> <span><strong>Mandaci una mail:</strong> support@guidatv.com</span></div>
	        </li>
	        <li class="one_quarter">
	          <div class="block clear"><a href="#"><i class="fas fa-clock"></i></a> <span><strong> Lunedi - Venerdi:</strong> 08.00 - 18.00</span></div>
	        </li>
	        <li class="one_quarter">
	          <div class="block clear"><a href="#"><i class="fas fa-map-marker-alt"></i></a> <span><strong>Vieni a visitarci:</strong> Indicazioni per la <a href="#">nostra sede.</a></span></div>
	        </li>
	      </ul>
	    <!-- ################################################################################################ -->
	  </footer>
	</div>
</div>
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<a id="backtotop" href="#top"><i class="fas fa-chevron-up"></i></a>

</body>
</html>