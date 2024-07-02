package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ProgettoRepository;

/**
 * Servlet implementation class AssociaDipendente
 */
@WebServlet("/AssociaDipendente")
public class AssociaDipendente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProgettoRepository repo = new ProgettoRepository();
		
		String codiceFiscale = request.getParameter("codiceFiscale");
		String nomeProgetto = request.getParameter("nomeProgetto");
		
		// Se associazione non esiste, va avanti, fixxa f5
		if (!repo.verifyWorkingEsistente(codiceFiscale, nomeProgetto)) {
			if ((repo.addDipendenteToNomeProgettoByCodiceFiscale(codiceFiscale, nomeProgetto)) > 0) {
				RequestDispatcher rd = request.getRequestDispatcher("GestioneProgetto");
				rd.forward(request, response);
			}
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
			request.setAttribute("msgError", "Qualcosa e' andato storto nell'associazione");
			rd.forward(request, response);
		}
	
	}

}
