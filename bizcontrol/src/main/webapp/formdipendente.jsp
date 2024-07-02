<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>BizControl</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/formdipendente.css">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="javascript/scripts.js"></script>
</head>
<body>

	<!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="home.jsp"><b>BizControl</b></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item"><a class="nav-link" href="home.jsp">Home</a></li>
            </ul>
            <ul class="navbar-nav ml-auto">
                <li class="nav-item"><a class="nav-link" href="Logout" onclick="return confirmLogout()">Logout</a></li>
            </ul>
        </div>
    </nav>

    <!-- ADMIN: Inserimento -->
    <c:if test="${permesso eq 'admin' && inserimento eq true}">
        <div class="container mx-auto contenitore-form-inserimento">
            <h1>Inserimento Dipendente</h1>
            <form method="POST" action="InserimentoDipendente">
                <div class="form-group">
                <label>Account</label>
                    <input type="text" name="username" class="form-control" placeholder="Inserisci username">
                </div>
                <div class="form-group">
                    <input type="email" name="email" class="form-control" placeholder="Inserisci l'email">
                </div>
                <div class="form-group">
                    <input type="password" name="password" class="form-control" placeholder="Inserisci la password">
                </div>
                <div class="form-group">
                    <select name="tipoPermesso" class="form-control">
                        <option value="0">Tipo di Permesso</option>
                        <option value="admin">admin</option>
                        <option value="guest">guest</option>
                    </select>
                </div>
                <div class="form-group">
                <label>Nome e Cognome</label>
                    <input type="text" name="nome" class="form-control" placeholder="Inserisci il nome">
                </div>
                <div class="form-group">
                    <input type="text" name="cognome" class="form-control" placeholder="Inserisci il cognome">
                </div>
                <div class="form-group">
                <label>Codice Fiscale</label>
                    <input type="text" name="codiceFiscale" class="form-control" placeholder="Inserisci il codice fiscale">
                </div>
                <div class="form-group">
                    <label>Sesso</label>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="sesso" value="m" id="sessoM">
                        <label class="form-check-label" for="sessoM">M</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="sesso" value="f" id="sessoF">
                        <label class="form-check-label" for="sessoF">F</label>
                    </div>
                </div>
                <div class="form-group">
                    <input type="date" name="dataNascita" class="form-control">
                </div>
                <div class="form-group">
                <label>Luogo di Nascita</label>
                    <input type="text" name="luogoNascita" class="form-control" placeholder="Inserisci il luogo di nascita">
                </div>
                <div class="form-group">
                <label>Stipendio</label>
                    <input type="text" name="stipendio" class="form-control" placeholder="Inserisci lo stipendio">
                </div>
                <button type="reset" class="btn btn-secondary">CANCELLA</button>
                <button type="submit" class="btn btn-primary">INVIA</button>
            </form>
        </div>
    </c:if>

    <!-- ADMIN: Modifica -->
    <c:if test="${permesso eq 'admin' && modifica eq true}">
        <div class="container mx-auto contenitore-form-modifica">
            <h1>Modifica Dipendente</h1>
            <form method="POST" action="ModifyDipendente" onsubmit="return confirmUpdate();">
                <div class="form-group">
                <label>Account</label>
                    <input type="text" name="username" class="form-control" value="${dipendente.username}">
                </div>
                <div class="form-group">
                    <input type="email" name="email" class="form-control" value="${dipendente.email}">
                </div>
                <div class="form-group">
                    <input type="password" name="password" class="form-control" value="${dipendente.password}">
                </div>
                <div class="form-group">
                    <select name="tipoPermesso" class="form-control">
                        <option>Tipo di Permesso</option>
                        <!-- Ternario per controllo su permesso ricevuto -->
                        <option value="admin" ${dipendente.idPermesso == 1 ? 'selected' : ''}>admin</option>
                        <option value="guest" ${dipendente.idPermesso == 2 ? 'selected' : ''}>guest</option>
                    </select>
                </div>
                <label>Nome e Cognome</label>
                <div class="form-group">
                    <input type="text" name="nome" class="form-control" value="${dipendente.nome}">
                </div>
                <div class="form-group">
                    <input type="text" name="cognome" class="form-control" value="${dipendente.cognome}">
                </div>
                <div class="form-group">
                <label>Codice Fiscale</label>
                    <input type="text" name="codiceFiscale" class="form-control" value="${dipendente.codiceFiscale}">
                </div>
                <div class="form-group">
                    <label>Sesso</label>
                    <!-- Ternario per controllo su sesso ricevuto -->
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="sesso" value="m" ${dipendente.sesso == true ? 'checked' : '' }>
                        <label class="form-check-label">M</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="sesso" value="f" ${dipendente.sesso == false ? 'checked' : '' }>
                        <label class="form-check-label">F</label>
                    </div>
                </div>
                <div class="form-group">
                    <input type="date" name="dataNascita" class="form-control" value="${dipendente.dataNascita}">
                </div>
                <div class="form-group">
                <label>Luogo di Nascita</label>
                    <input type="text" name="luogoNascita" class="form-control" value="${dipendente.luogoNascita}">
                </div>
                <label>Stipendio</label>
                <div class="form-group">
                    <input type="text" name="stipendio" class="form-control" value="${dipendente.stipendio}">
                </div>
                <!-- Mando in hidden il codice fiscale originale alla servlet ModifyDipendente -->
                <input type="hidden" name="codiceFiscaleOriginale" value="${codiceFiscaleOriginale}">
                <input type="hidden" name="usernameOriginale" value="${usernameOriginale}">
                <input type="hidden" name="emailOriginale" value="${emailOriginale}">
                <button type="reset" class="btn btn-secondary">CANCELLA</button>
                <button type="submit" class="btn btn-primary">MODIFICA</button>
            </form>
        </div>
    </c:if>

    <!-- GUEST: Modifica -->
    <c:if test="${permesso eq 'guest' && modifica eq true}">
        <div class="container mx-auto contenitore-form-modifica">
            <h1>Modifica Dipendente</h1>
            <form method="POST" action="ModifyDipendente" onsubmit="return confirmUpdate();">
            <label>Account</label>
                <div class="form-group">
                    <input type="text" name="username" class="form-control" value="${dipendente.username}">
                </div>
                <div class="form-group">
                    <input type="email" name="email" class="form-control" value="${dipendente.email}">
                </div>
                <div class="form-group">
                    <input type="password" name="password" class="form-control" value="${dipendente.password}">
                </div>
                <div class="form-group">
                <label>Nome e Cognome</label>
                    <input type="text" name="nome" class="form-control" value="${dipendente.nome}">
                </div>
                <div class="form-group">
                    <input type="text" name="cognome" class="form-control" value="${dipendente.cognome}">
                </div>
                <div class="form-group">
                    <label>Sesso</label>
                    <!-- Ternario per controllo su sesso ricevuto -->
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="sesso" value="m" ${dipendente.sesso == true ? 'checked' : '' }>
                        <label class="form-check-label">M</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="sesso" value="f" ${dipendente.sesso == false ? 'checked' : '' }>
                        <label class="form-check-label">F</label>
                    </div>
                </div>
                <div class="form-group">
                    <input type="date" name="dataNascita" class="form-control" value="${dipendente.dataNascita}">
                </div>
                <div class="form-group">
                <label>Luogo di Nascita</label>
                    <input type="text" name="luogoNascita" class="form-control" value="${dipendente.luogoNascita}">
                </div>
                <!-- Mando in hidden gli attributi non modificabili -->
                <input type="hidden" name="codiceFiscaleOriginale" value="${codiceFiscaleOriginale}">
                <input type="hidden" name="codiceFiscale" value="${codiceFiscaleOriginale}">
                <input type="hidden" name="stipendio" value="${dipendente.stipendio}">
                <input type="hidden" name="tipoPermesso" value="${dipendente.idPermesso == 1 ? 'admin' : 'guest'}">
                <button type="reset" class="btn btn-secondary">CANCELLA</button>
                <button type="submit" class="btn btn-primary">MODIFICA</button>
            </form>
        </div>
    </c:if>

</body>
</html>
