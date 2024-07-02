<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BizControl</title>
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/styles.css">
<script src="javascript/scripts.js"></script>
</head>
<body>

	<!-- Navbar -->
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="home.jsp"><b>BizControl</b></a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item"><a class="nav-link" href="home.jsp">Home</a></li>
			</ul>
			<ul class="navbar-nav ml-auto">
				<li class="nav-item"><a class="nav-link" href="Logout"
					onclick="return confirmLogout()">Logout</a></li>
			</ul>
		</div>
	</nav>

	<!-- Dipendenti e Progetto -->
	<div class="container my-5">
		<div class="row">
			<div class="col-md-6 mb-4">
				<div class="card">
					<div class="card-body">
						<h3 class="card-title text-center">Dipendente</h3>
						<c:if test="${permesso eq 'admin'}">
							<a href="InserimentoDipendente"
								class="btn btn-primary btn-lg btn-block">Inserimento</a>
							<a href="FindAllDipendenti"
								class="btn btn-secondary btn-lg btn-block">Gestione</a>
							<a href="FindCodiceFiscale?modificaDip=false"
								class="btn btn-info btn-lg btn-block">Modifica Profilo</a>
						</c:if>
						<c:if test="${permesso eq 'guest'}">
							<a href="FindCodiceFiscale?modificaDip=false"
								class="btn btn-info btn-lg btn-block">Modifica Profilo</a>
						</c:if>
					</div>
				</div>
			</div>
			<div class="col-md-6 mb-4">
				<div class="card">
					<div class="card-body">
						<h3 class="card-title text-center">Progetto</h3>
						<c:if test="${permesso eq 'admin'}">
							<a href="InserimentoProgetto"
								class="btn btn-primary btn-lg btn-block">Inserimento</a>
							<a href="FindAllProgetti"
								class="btn btn-secondary btn-lg btn-block">Gestione</a>
						</c:if>
						<c:if test="${permesso eq 'guest'}">
							<a href="FindAllProgetti"
								class="btn btn-secondary btn-lg btn-block">Gestione</a>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Messaggio di Successo-->
	<%
	String msgSuccess = (String) request.getAttribute("msgSuccess");
	if (msgSuccess != null) {
	%>
	<div class="row justify-content-center">
		<div class="col-8">
			<div class="alert alert-info text-center">
				<%=msgSuccess%>
			</div>
		</div>
	</div>
	<%
	}
	%>
	
		<!-- Messaggio di Errore-->
	<%
	String msgError = (String) request.getAttribute("msgError");
	if (msgError != null) {
	%>
	<div class="row justify-content-center">
		<div class="col-8">
			<div class="alert alert-danger text-center">
				<%=msgError%>
			</div>
		</div>
	</div>
	<%
	}
	%>

</body>
</html>
