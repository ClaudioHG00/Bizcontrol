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
<script src="javascript/gestionedipendente.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
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
				<li class="nav-item"><a class="nav-link" href="home.jsp">Home</a>
				</li>
			</ul>
			<ul class="navbar-nav ml-auto">
				<li class="nav-item"><a class="nav-link" href="Logout"
					onclick="return confirmLogout()">Logout</a></li>
			</ul>
		</div>
	</nav>



	<!-- Expression language per indicare se permesso eq(uals) admin -->
	<c:if test="${permesso eq 'admin'}">


		<div class="container-fluid">
			<div class="row">

				<!-- Paginazione -->
				<div class="col-sm-12 col-md-6 col-lg-6">
					<form method="post" action="FindAllDipendenti"
						class="form-inline mb-2 justify-content-center">
						<label for="limit" class="mr-2">Numero di elementi per
							pagina:</label> <select name="limit" class="form-control mr-2">
							<option value="10" ${limit == 10 ? 'selected' : ''}>10</option>
							<option value="20" ${limit == 20 ? 'selected' : ''}>20</option>
							<option value="30" ${limit == 30 ? 'selected' : ''}>30</option>
							<option value="40" ${limit == 40 ? 'selected' : ''}>40</option>
							<option value="50" ${limit == 50 ? 'selected' : ''}>50</option>
						</select> <input type="hidden" name="azione" value="${azione}">
						<button type="submit" class="btn btn-primary">Imposta</button>
					</form>
				</div>
				<!-- select con tipo di filtro -->
				<div class="col-sm-12 col-md-6 col-lg-6">
					<select id="filterType" class="form-control mb-2">
						<option value="">Ricerca Filtro</option>
						<option value="cf">Ricerca Codice Fiscale</option>
						<option value="nomeCognome">Ricerca Nome e Cognome</option>
						<option value="luogoNascita">Ricerca Luogo di Nascita</option>
						<option value="stipendio">Ricerca Stipendio</option>
						<option value="permesso">Ricerca per Ruolo</option>
					</select>
				</div>
			</div>
		</div>

		<!-- Modali per i filtri -->

		<!-- Ricerca Codice Fiscale -->
		<div id="cfModal" class="modal fade" tabindex="-1" role="dialog">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Ricerca Codice Fiscale</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<form method="post" action="CFFilter" class="filter-form">
							<div class="form-group">
								<label for="cfFilterStringa">Codice Fiscale</label> <input
									type="text" name="cfFilterStringa" class="form-control"
									placeholder="Inserire codice fiscale">
							</div>
							<button type="submit" class="btn btn-primary">Cerca</button>
						</form>
					</div>
				</div>
			</div>
		</div>

		<!-- Ricerca Nome Cognome -->
		<div id="nomeCognomeModal" class="modal fade" tabindex="-1"
			role="dialog">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Ricerca Nome e Cognome</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<form method="post" action="NomeCognomeFilter" class="filter-form">
							<div class="form-group">
								<label for="nomeCognomeFilterNome">Nome</label> <input
									type="text" name="nomeCognomeFilterNome" class="form-control"
									placeholder="Inserire nome">
							</div>
							<div class="form-group">
								<label for="nomeCognomeFilterCognome">Cognome</label> <input
									type="text" name="nomeCognomeFilterCognome"
									class="form-control" placeholder="Inserire cognome">
							</div>
							<button type="submit" class="btn btn-primary">Cerca</button>
						</form>
					</div>
				</div>
			</div>
		</div>

		<!-- Ricerca Luogo di Nascita -->
		<div id="luogoNascitaModal" class="modal fade" tabindex="-1"
			role="dialog">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Ricerca Luogo di Nascita</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<form method="post" action="LuogoNascitaFilter"
							class="filter-form">
							<div class="form-group">
								<label for="luogoNascitaFilterString">Luogo di Nascita</label> <input
									type="text" name="luogoNascitaFilterString"
									class="form-control" placeholder="Inserire luogo di nascita">
							</div>
							<button type="submit" class="btn btn-primary">Cerca</button>
						</form>
					</div>
				</div>
			</div>
		</div>

		<!-- Ricerca Filtro Stipendio -->
		<div id="stipendioModal" class="modal fade" tabindex="-1"
			role="dialog">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Ricerca Stipendio</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<form method="post" action="StipendioFilter" class="filter-form">
							<div class="form-group">
								<label for="stipendioFilterStringa">Stipendio</label> <input
									type="number" name="stipendioFilterStringa"
									class="form-control" placeholder="Inserire stipendio">
							</div>
							<button type="submit" class="btn btn-primary">Cerca</button>
						</form>
					</div>
				</div>
			</div>
		</div>

		<!-- Ricerca Tipo Permesso -->
		<div id="permessoModal" class="modal fade" tabindex="-1" role="dialog">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Ricerca per Ruolo</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<form method="post" action="PermessoFilter" class="filter-form">
							<div class="form-group">
								<label for="permessoFilterStringa">Ruolo</label> <select
									name="permessoFilterStringa" class="form-control">
									<option value="admin">admin</option>
									<option value="guest">guest</option>
								</select>
							</div>
							<button type="submit" class="btn btn-primary">Cerca</button>
						</form>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<!-- Paginazione -->
			<div class="col-sm-12 col-md-6 col-lg-6">
				<div class="row justify-content-center">
					<form method="post" action="FindAllDipendenti">
						<div class="btn-group" role="group">
							<!-- 2. Prendo 'limit' dal doGet, mando al doPost -->
							<input type="hidden" name="limit" value="${limit}">
							<button type="submit" name="azione" value="first"
								class="btn btn-primary">Prima Pagina</button>
							<button type="submit" name="azione" value="previous"
								class="btn btn-primary">Pagina Precedente</button>
							<button type="submit" name="azione" value="next"
								class="btn btn-primary">Pagina Successiva</button>
							<button type="submit" name="azione" value="last"
								class="btn btn-primary">Ultima Pagina</button>
						</div>
					</form>
				</div>
			</div>
			<!-- Reset Filtri -->
			<div class="col-sm-12 col-md-6 col-lg-6 justify-content-center">
				<form method="post" action="ResetFilter" class="form-inline mb-2">
					<button type="submit" class="btn btn-primary">Reset Filtri</button>
				</form>
			</div>
		</div>



		<!-- Tabella -->
		<div class="container-fluid">
			<div class="table-responsive mt-4 bg-light">
				<table class="table table-striped table-bordered">
					<thead class="thead-dark">
						<tr>
							<th>NOME</th>
							<th>COGNOME</th>
							<th>SESSO</th>
							<th>DATA DI NASCITA</th>
							<th>LUOGO DI NASCITA</th>
							<th>CODICE FISCALE</th>
							<th>STIPENDIO</th>
							<th>USERNAME</th>
							<th>EMAIL</th>
							<th>PERMESSO</th>
							<th>PROGETTI</th>
							<th>OPERAZIONI</th>
						</tr>
					</thead>
					<tbody>
						<!-- OBSOLETO: Sostituito con DTO.
					Se faccio utilizzo del varStatus, posso accedere agli elementi di arrayacc nello stesso foreach
					utilizzando status.index per accedere all'indice corrente -->
						<%-- <c:forEach var="dipendente" items="${arraydip}" varStatus="status"> --%>
						<c:forEach var="dipendente" items="${arraydip}">
							<tr>
								<td>${dipendente.nome}</td>
								<td>${dipendente.cognome}</td>
								<td>${dipendente.sesso ? 'Maschio' : 'Femmina'}</td>
								<td>${dipendente.dataNascita}</td>
								<td>${dipendente.luogoNascita}</td>
								<td>${dipendente.codiceFiscale}</td>
								<td>${dipendente.stipendio}</td>
								<%-- <td>${arrayacc[status.index].username}</td>
							<td>${arrayacc[status.index].email}</td>
							<td>${arrayacc[status.index].tipo}</td> --%>
								<td>${dipendente.username}</td>
								<td>${dipendente.email}</td>
								<td>${dipendente.tipoPermesso}</td>
								<td>${dipendente.numProgetti}</td>
								<td class="button-container"><a
									href="FindCodiceFiscale?codiceFiscale=${dipendente.codiceFiscale}&modificaDip=true"
									class="btn btn-info">MODIFICA</a>
									<form action="DeleteDipendente" method="post"
										style="display: inline;"
										onsubmit="return confirmDelete('${dipendente.nome}', '${dipendente.cognome}');">
										<input type="hidden" name="codiceFiscale"
											value="${dipendente.codiceFiscale}">
										<button type="submit" class="btn btn-danger">ELIMINA</button>
									</form></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>

	</c:if>

	<c:if test="${permesso eq 'guest'}">
		<div class="container mt-4">
			<table class="table">
				<thead class="thead-dark">
					<tr>
						<th>NOME</th>
						<th>COGNOME</th>
						<th>SESSO</th>
						<th>DATA DI NASCITA</th>
						<th>LUOGO DI NASCITA</th>
						<th>CODICE FISCALE</th>
						<th>STIPENDIO</th>
						<th>USERNAME</th>
						<th>EMAIL</th>
						<th>PERMESSO</th>
						<th>PROGETTI</th>
						<th>OPERAZIONI</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>${dip.nome}</td>
						<td>${dip.cognome}</td>
						<td>${dip.sesso ? 'Maschio' : 'Femmina'}</td>
						<td>${dip.dataNascita}</td>
						<td>${dip.luogoNascita}</td>
						<td>${dip.codiceFiscale}</td>
						<td>${dip.stipendio}</td>
						<td>${dip.username}</td>
						<td>${dip.email}</td>
						<td>${dip.tipoPermesso}</td>
						<td>${dip.numProgetti}</td>
						<td class="button-container">
							<!-- & per molteplici parametri --> <a
							href="FindCodiceFiscale?codiceFiscale=${dip.codiceFiscale}&modificaDip=true"
							class="btn btn-primary">MODIFICA</a>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</c:if>


	<!-- Mostra e chiudi modal -->
	<script type="text/javascript">
		/* avvia l'esecuzione dello script quando il documento HTML è completamente caricato */
		$(document).ready(function() {
			/*  seleziona l'elemento con l'id "filterType"  */
			$('#filterType').change(function() {
				/* cattura il valore dell'opzione selezionata nel menu a discesa */
				var selectedFilter = $(this).val();
				/* viene mostrato il modal corrispondente all'opzione selezionata */
				$('#' + selectedFilter + 'Modal').modal('show');
			});

			// Aggiungi un evento di submit per i form dei filtri
			$('.filter-form').submit(function() {
				// Chiudi il modal dopo l'invio del form in automatico
				$(this).closest('.modal').modal('hide');
			});
		});
	</script>
	<!-- Bootstrap JS, Popper.js, and jQuery -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
