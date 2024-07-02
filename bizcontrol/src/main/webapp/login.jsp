<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
		</div>
	</nav>

	<!-- Login Form -->

	<div class="container">
		<div class="row justify-content-center mt-5">
			<div class="col-md-6">
				<div class="card">
					<div class="card-body">
						<h2 class="card-title text-center">Login</h2>
						<form action="Login" method="post">
							<div class="form-group">
								<input type="text" class="form-control" name="username-email"
									placeholder="Username o Email">
							</div>
							<div class="form-group">
								<input type="password" class="form-control" name="password"
									placeholder="Password">
							</div>
							<div class="form-group">
								<button type="reset" class="btn btn-secondary">Reset</button>
								<button type="submit" class="btn btn-primary">Login</button>
							</div>
						</form>
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

					</div>
				</div>
			</div>
		</div>

	</div>


</body>
</html>
