package controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DipendenteDTO;
import model.DipendenteRepository;

/**
 * Servlet implementation class ModifyDipendente
 */
@WebServlet("/ModifyDipendente")
public class ModifyDipendente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifyDipendente() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		Per fare modifica sia in home che gestione, ricevo da FindCodiceFiscale attribute
		String codiceFiscale = (String) request.getAttribute("codiceFiscale");
//		String codiceFiscale = request.getParameter("codiceFiscale");
		DipendenteRepository repo = new DipendenteRepository();
		DipendenteDTO dto = repo.findDipendenteByCodiceFiscale(codiceFiscale);
		String username = dto.getUsername();
		String email = dto.getEmail();

		RequestDispatcher rd = request.getRequestDispatcher("formdipendente.jsp");
		request.setAttribute("dipendente", dto);
		// Mi mando anche il codice fiscale originale, che usero nel doPost
		request.setAttribute("codiceFiscaleOriginale", codiceFiscale);
		request.setAttribute("modifica", true);
		request.setAttribute("usernameOriginale", username);
		request.setAttribute("emailOriginale", email);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DipendenteRepository repo = new DipendenteRepository();
		// Prendo tale codice dal jsp in hidden
		String codiceFiscaleOriginale = request.getParameter("codiceFiscaleOriginale");
		// mi servono per controllare se utente corrente e' loggato
		String usernameOriginale = request.getParameter("usernameOriginale");
		String emailOriginale = request.getParameter("emailOriginale");
		
		HttpSession session = request.getSession();
		String utente = (String) session.getAttribute("utente");
		String permesso = (String) session.getAttribute("permesso");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		
		DipendenteDTO dto = new DipendenteDTO();
		dto.setUsername(username);
		dto.setEmail(email);
		dto.setPassword(request.getParameter("password"));
		String tipoPermesso = request.getParameter("tipoPermesso");
		if (tipoPermesso.equals("admin")) {
			dto.setIdPermesso(1);
		} else if (tipoPermesso.equals("guest")) {
			dto.setIdPermesso(2);
		}
		dto.setNome(request.getParameter("nome"));
		dto.setCognome(request.getParameter("cognome"));
		String sesso = request.getParameter("sesso");
		if (sesso.equals("m")) {
			dto.setSesso(true);
		} else if (sesso.equals("f")) {
			dto.setSesso(false);
		}
		dto.setDataNascita(Date.valueOf(request.getParameter("dataNascita")));
		dto.setLuogoNascita(request.getParameter("luogoNascita"));
		dto.setCodiceFiscale(request.getParameter("codiceFiscale"));
		dto.setStipendio(Double.valueOf(request.getParameter("stipendio")));
		
		// Confronto utente corrente con dati inseriti
		
		if (repo.modifyDipendenteByCodiceFiscale(dto, codiceFiscaleOriginale) > 0) {
			request.setAttribute("msgSuccess", "Modifica avvenuta con successo!");
			
			// Se modifica e' fatta da utente loggato, aggiorno le credenziali
			if(utente.equalsIgnoreCase(usernameOriginale) || utente.equalsIgnoreCase(emailOriginale)) {
				// Blocco if se username o email sono stati modificati, per aggiornare la session
				if (!utente.equalsIgnoreCase(username)) {
					session.setAttribute("utente", username);
					
				} else if(!utente.equalsIgnoreCase(email)){
					session.setAttribute("utente", email);
				}
				// Aggiorno il permesso della sessione se diverso, per demozione da admin a guest.
				if(!permesso.equals(tipoPermesso)) {
					session.setAttribute("permesso", tipoPermesso);
				}
			}
			
		} else {
			request.setAttribute("msgError", "Modifica non avvenuta.");
		}
		
		
		RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
		rd.forward(request, response);
	}

}
