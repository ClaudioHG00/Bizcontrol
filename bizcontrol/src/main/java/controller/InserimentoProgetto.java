package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ProgettoDTO;
import model.ProgettoRepository;

/**
 * Servlet implementation class InserimentoProgetto
 */
@WebServlet("/InserimentoProgetto")
public class InserimentoProgetto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("formprogetto.jsp");
		request.setAttribute("inserimento", true);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProgettoRepository repo = new ProgettoRepository();
		ProgettoDTO prog = new ProgettoDTO();
		
		prog.setNomeProgetto(request.getParameter("nomeProgetto"));
		prog.setDescrizione(request.getParameter("descrizione"));
		prog.setLinkImg(request.getParameter("linkImg"));
		prog.setCosto(Double.valueOf(request.getParameter("costo")));
		
		// Fix di f5 reinserimento di progetto impossibile, controllo su esistente
		if(!repo.verifyProgettoEsistente(prog.getNomeProgetto())) {
			if(repo.insertProgetto(prog) > 0) {
				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
				request.setAttribute("msgSuccess", "Inserimento progetto avvenuto con successo!");
				rd.forward(request, response);
			}
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
			request.setAttribute("msgError", "Inserimento progetto non avvenuto.");
			rd.forward(request, response);
		} 
	}

}
