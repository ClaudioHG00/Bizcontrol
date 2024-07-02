<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>BizControl</title>
<!-- Link a Bootstrap CSS -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/styles.css">
<script src="javascript/progetti.js"></script>
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

	<!-- Paginazione -->
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-12 col-md-6 col-lg-6">
				<form method="post" action="FindAllProgetti"
					class="form-inline mb-2 justify-content-center">
					<label for="limit" class="mr-2">Numero di elementi per
						pagina:</label> <select name="limitCards" class="form-control mr-2">
						<option value="12" ${limitCards == 12 ? 'selected' : ''}>12</option>
						<option value="16" ${limitCards == 16 ? 'selected' : ''}>16</option>
						<option value="20" ${limitCards == 20 ? 'selected' : ''}>20</option>
					</select> <input type="hidden" name="azioneCard" value="${azioneCard}">
					<button type="submit" class="btn btn-primary">Imposta</button>
				</form>
			</div>

			<div class="col-sm-12 col-md-6 col-lg-6">
				<div class="row justify-content-center">
					<form method="post" action="FindAllProgetti">
						<div class="btn-group" role="group">
						<!-- 2. Prendo 'limitCards' dal doGet, mando al doPost -->
							<input type="hidden" name="limitCards" value="${limitCards}">
							<button type="submit" name="azioneCard" value="first"
								class="btn btn-primary">Prima Pagina</button>
							<button type="submit" name="azioneCard" value="previous"
								class="btn btn-primary">Pagina Precedente</button>
							<button type="submit" name="azioneCard" value="next"
								class="btn btn-primary">Pagina Successiva</button>
							<button type="submit" name="azioneCard" value="last"
								class="btn btn-primary">Ultima Pagina</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- Cards dei Progetti -->
	<c:if test="${permesso eq 'admin'}">
		<div class="container">
			<div class="row justify-content-center">
				<c:forEach var="progetto" items="${arrayprog}">

					<!-- Singola Card -->
					<div class="col-lg-3 col-md-4 col-sm-6 col-12 d-flex mb-3">
						<div class="card mx-auto d-flex flex-column" style="width: 100%;">
							<div class="card-img-container">
								<img class="card-img-top" src="${progetto.linkImg}"
									alt="Card image cap">
							</div>
							<div class="card-body d-flex flex-column flex-grow-1">
								<div class="card-text-container mb-3">
									<h5 class="card-title">${progetto.nomeProgetto}</h5>
									<p class="card-text">${progetto.descrizione}</p>
									<p class="card-text">
										<b>Costo:</b> ${progetto.costo}$
									</p>
									<p class="card-text">
										<b>Dipendenti:</b> ${progetto.numDipendenti}
									</p>
								</div>
								<div class="card-buttons-container mt-auto row">
									<!-- Modifica -->
									<div class="col-4 p-1">
										<a href="ModifyProgetto?nomeProgetto=${progetto.nomeProgetto}"
											class="btn btn-info w-70">Modifica</a>
									</div>
									<!-- Gestione -->
									<div class="col-4 p-1">
										<a
											href="GestioneProgetto?nomeProgetto=${progetto.nomeProgetto}"
											class="btn btn-secondary w-70">Gestisci</a>
									</div>
									<!-- Elimina -->
									<div class="col-4 p-1">
										<form action="DeleteProgetto" method="post"
											style="display: inline;"
											onsubmit="return confirmDelete('${progetto.nomeProgetto}');">
											<input type="hidden" name="nomeProgetto"
												value="${progetto.nomeProgetto}">
											<button type="submit" class="btn btn-danger w-70">Elimina</button>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>

				</c:forEach>
			</div>
		</div>
	</c:if>


	<c:if test="${permesso eq 'guest'}">

		<div class="container">
			<div class="row justify-content-center">
				<c:forEach var="progetto" items="${arrayprogDip}">

					<!-- Singola Card -->
					<div class="col-lg-3 col-md-4 col-sm-6 col-12 d-flex mb-3">
						<div class="card mx-auto d-flex flex-column" style="width: 100%;">
							<div class="card-img-container">
								<img class="card-img-top" src="${progetto.linkImg}"
									alt="Card image cap">
							</div>
							<div class="card-body d-flex flex-column flex-grow-1">
								<div class="card-text-container mb-3">
									<h5 class="card-title">${progetto.nomeProgetto}</h5>
									<p class="card-text">${progetto.descrizione}</p>
									<p class="card-text">
										<b>Costo:</b> ${progetto.costo}$
									</p>
									<p class="card-text">
										<b>Dipendenti:</b> ${progetto.numDipendenti}
									</p>
								</div>
							</div>
						</div>
					</div>

				</c:forEach>
			</div>
		</div>
	</c:if>



	<!-- Importa il JavaScript di Bootstrap -->
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	<script src="javascript/scripts.js"></script>

</body>
</html>
