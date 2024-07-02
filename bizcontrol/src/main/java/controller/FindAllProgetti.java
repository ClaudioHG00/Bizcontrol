package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DipendenteRepository;
import model.ProgettoDTO;
import model.ProgettoRepository;

/**
 * Servlet implementation class FindAllProgetti
 */
@WebServlet("/FindAllProgetti")
public class FindAllProgetti extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProgettoRepository repo = new ProgettoRepository();
		DipendenteRepository dipRepo = new DipendenteRepository();
		HttpSession session = request.getSession();
		String permesso = (String) session.getAttribute("permesso");
		String utente = (String) session.getAttribute("utente");
		
		// Tutti i Progetti per Admin
		if(permesso.equals("admin")) {
			// 1. Ricevo i parametri dalla session, impostati dal doPost
			Integer limitCards = (Integer) session.getAttribute("limitCards");
			Integer offsetCards = (Integer) session.getAttribute("offsetCards");
			String azioneCard = (String) session.getAttribute("azioneCard");
			int nProg = repo.countAllProgetti();
			int offset = calculateOffset(nProg, limitCards, offsetCards, azioneCard);
			ArrayList<ProgettoDTO> arrayprog = repo.findAllProgettiWithPaginazione(limitCards, offset);
			// setto il numdipendenti per ogni elemento dell'array
			for (ProgettoDTO progetto : arrayprog) {
				progetto.setNumDipendenti(dipRepo.countDipendentiWorkingByNomeProgetto(progetto.getNomeProgetto()));
			}
			
			session.setAttribute("offsetCards", offsetCards);
			
			RequestDispatcher rd = request.getRequestDispatcher("progetti.jsp");
			request.setAttribute("arrayprog", arrayprog);
			rd.forward(request, response);
		}
		// Progetti associati per Guest
		if(permesso.equals("guest")) {
			ArrayList<ProgettoDTO> arrayprogDip = repo.findProgettiAssociatiByUsernameEmail(utente,utente);
			for (ProgettoDTO progetto : arrayprogDip) {
				progetto.setNumDipendenti(dipRepo.countDipendentiWorkingByNomeProgetto(progetto.getNomeProgetto()));
			}
			if (!arrayprogDip.isEmpty()) {
				RequestDispatcher rd = request.getRequestDispatcher("progetti.jsp");
				request.setAttribute("arrayprogDip", arrayprogDip);
				rd.forward(request, response);
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
				request.setAttribute("msgError", "Attualmente, non stai lavorando ad alcun progetto.");
				rd.forward(request, response);
			}
		}
	
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		// 3. Ricevo dal JSP, setto nella session gli attributi per la paginazione
		int limitCards = Integer.parseInt(request.getParameter("limitCards"));
		session.setAttribute("limitCards", limitCards);
		String azioneCard = request.getParameter("azioneCard");
		session.setAttribute("azioneCard", azioneCard);

		// 4. Ritorno a doGet per aggiornare la visualizzazione
		response.sendRedirect("FindAllProgetti");
		
	}
	
	// Metodo ausiliare per paginazione in doGet()
	private int calculateOffset(int nProg, int limit, int offset, String azioneCard) {
													// 20-10=10 22-(22%10)=20
		int lastPageOffset = (nProg % limit == 0) ? nProg - limit : nProg - (nProg % limit);
		// Parametro che passero al click nell jsp
		switch (azioneCard) {
		// Pagina successiva
		case "next":
			offset += limit;
			if (offset > nProg - limit) // Se supera, equivale a trovarsi nell'ultima pagina
				offset = lastPageOffset;
			break;
		// Pagina precedente
		case "previous":
			offset -= limit;
			if (offset < 0)// Azzero se prova ad andare a offset negativo
				offset = 0;
			break;
		// Prima pagina
		case "first":
			offset = 0;
			break;
		// Ultima pagina
		case "last":
			offset = lastPageOffset;
			break;
		default:
			break;
		}
		return offset;
	}
	
	
}
