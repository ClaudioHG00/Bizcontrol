<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>BizControl</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/formprogetto.css">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
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
	
	    <!-- ADMIN: Inserimento Progetto -->
    <c:if test="${permesso eq 'admin' && inserimento eq true}">
        <div class="container mx-auto contenitore-form-inserimento">
            <h1>Inserimento Progetto</h1>
            <form method="POST" action="InserimentoProgetto">
                <div class="form-group">
                <label>Nome Progetto</label>
                    <input type="text" name="nomeProgetto" class="form-control" placeholder="Inserisci nome">
                </div>
                <div class="form-group">
                <label>Descrizione</label>
                    <input type="text" name="descrizione" class="form-control" placeholder="Inserisci una descrizione">
                </div>
                <div class="form-group">
                <label>Link Immagine</label>
                    <input type="text" name="linkImg" class="form-control" placeholder="Inserisci link immagine">
                </div>
                <div class="form-group">
                <label>Costo</label>
                    <input type="text" name="costo" class="form-control" placeholder="Inserisci il costo">
                </div>
                <button type="reset" class="btn btn-secondary">CANCELLA</button>
                <button type="submit" class="btn btn-primary">INVIA</button>
            </form>
        </div>
    </c:if>
    
    
    	    <!-- ADMIN: Modifica Progetto -->
    <c:if test="${permesso eq 'admin' && modifica eq true}">
        <div class="container mx-auto contenitore-form-inserimento">
            <h1>Modifica Progetto</h1>
            <form method="POST" action="ModifyProgetto" onsubmit="return confirmUpdate();">
                <div class="form-group">
                <label>Nome Progetto</label>
                    <input type="text" name="nomeProgetto" class="form-control" value="${prog.nomeProgetto}">
                </div>
                <div class="form-group">
                <label>Descrizione</label>
                    <input type="text" name="descrizione" class="form-control" value="${prog.descrizione}">
                </div>
                <div class="form-group">
                <label>Link Immagine</label>
                    <input type="text" name="linkImg" class="form-control" value="${prog.linkImg}">
                </div>
                <div class="form-group">
                <label>Costo</label>
                    <input type="text" name="costo" class="form-control" value="${prog.costo}">
                </div>
                <input type="hidden" name="nomeProgettoOld" value="${nomeProgettoOld}">
                <button type="reset" class="btn btn-secondary">CANCELLA</button>
                <button type="submit" class="btn btn-primary">MODIFICA</button>
            </form>
        </div>
    </c:if>

</body>
</html>