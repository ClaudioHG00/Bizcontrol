<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>BizControl</title>
<!-- Link to Bootstrap CSS -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/styles.css">
<script src="javascript/gestionedipendente.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="javascript/scripts.js"></script>
<script src="javascript/gestioneprogetto.js"></script>
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

	<!-- Container delle due colonnne -->
	<div class="container mt-4">
		<div class="row">

			<!-- Tabella Dipendenti Assegnati -->
			<div class="col-md-12 mb-4">
				<br>
				<div class="table-responsive">
					<table class="table table-bordered bg-light table-striped">
						<thead class="thead-dark">
							<tr>
								<th colspan="4" class="text-center">Dipendenti Associati</th>
							</tr>
							<tr>
								<th>Nome</th>
								<th>Cognome</th>
								<th>Codice Fiscale</th>
								<th>Modifica</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="dipAssociato" items="${arrayDipAssociati}">
								<tr>
									<td>${dipAssociato.nome}</td>
									<td>${dipAssociato.cognome}</td>
									<td>${dipAssociato.codiceFiscale}</td>
									<td>
										<form action="DissociaDipendente" method="GET"
											onsubmit="return confirmDissocia('${dipAssociato.nome}','${dipAssociato.cognome}');">
											<input type="hidden" name="codiceFiscale"
												value="${dipAssociato.codiceFiscale}" /> <input
												type="hidden" name="nomeProgetto" value="${nomeProgetto}" />
											<button type="submit" class="btn btn-danger">Dissocia</button>
										</form>
									</td>

								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>

			<!-- Tabella Dipendenti non Assegnati -->
			<div class="col-md-12 mb-4">
				<br>
				<div class="table-responsive">
					<table class="table table-bordered bg-light table-striped">
						<thead class="thead-dark">
							<tr>
								<th colspan="4" class="text-center">Dipendenti non
									Associati</th>
							</tr>
							<tr>
								<th>Nome</th>
								<th>Cognome</th>
								<th>Codice Fiscale</th>
								<th>Modifica</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="dipNonAssociato" items="${arrayDipNonAssociati}">
								<!-- FIX 1: mostro tutto se non presente nell'array di dipAssociati (override di equals) -->
								<c:choose>
									<c:when
										test="${not arrayDipAssociati.contains(dipNonAssociato)}">
										<tr>
											<td>${dipNonAssociato.nome}</td>
											<td>${dipNonAssociato.cognome}</td>
											<td>${dipNonAssociato.codiceFiscale}</td>
											<td>
												<form action="AssociaDipendente" method="GET">
													<input type="hidden" name="codiceFiscale" value="${dipNonAssociato.codiceFiscale}" /> 
													<input type="hidden" name="nomeProgetto" value="${nomeProgetto}" />
													<button type="submit" class="btn btn-primary">Associa</button>
												</form>
											</td>
										</tr>
									</c:when>
								</c:choose>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>

		</div>
	</div>

</body>
</html>
