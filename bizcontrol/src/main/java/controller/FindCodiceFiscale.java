package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DipendenteRepository;

/**
 * Servlet che ricerca codice fiscale partendo da username o email della session
 * Servlet ausiliare della servlet ModifyDipendente, in home.jsp
 */
@WebServlet("/FindCodiceFiscale")
public class FindCodiceFiscale extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DipendenteRepository repo = new DipendenteRepository();
		HttpSession session = request.getSession();
		
		String usernameEmail = (String) session.getAttribute("utente");
		Boolean modificaDip = Boolean.valueOf(request.getParameter("modificaDip"));
		String codiceFiscaleUtente = repo.findCodiceFiscaleByUsernameEmail(usernameEmail);
		// Ricevo da gestionedipendente.jsp
		String codiceFiscaleDip = null;
		// Solo se da gestionedipendente.jsp, dove cliccando su Modifica setto modificaDip su true
		if (modificaDip) 
			codiceFiscaleDip = request.getParameter("codiceFiscale");
		
		// Modifica utente corrente (admin) 
		if (codiceFiscaleDip == null) {
			RequestDispatcher rd = request.getRequestDispatcher("ModifyDipendente");
			request.setAttribute("codiceFiscale", codiceFiscaleUtente);
			rd.forward(request, response);
		// Modifica dipendente
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("ModifyDipendente");
			request.setAttribute("codiceFiscale", codiceFiscaleDip);
			rd.forward(request, response);
		}
		
	
	}


}
