package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Progetto;
import model.ProgettoDTO;
import model.ProgettoRepository;

/**
 * Servlet implementation class ModifyProgetto
 */
@WebServlet("/ModifyProgetto")
public class ModifyProgetto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProgettoRepository repo = new ProgettoRepository();
		String nomeProgettoOld = request.getParameter("nomeProgetto");
		ProgettoDTO prog = repo.findProgettoByNomeProgetto(nomeProgettoOld);
		
		RequestDispatcher rd = request.getRequestDispatcher("formprogetto.jsp");
		request.setAttribute("prog", prog);
		// Mi serve nel caso in cui modifichi il nomeProgetto, mi serve il "vecchio" per cercarlo nel db
		request.setAttribute("nomeProgettoOld", nomeProgettoOld);
		request.setAttribute("modifica", true);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProgettoRepository repo = new ProgettoRepository();
		Progetto prog = new Progetto();
		String nomeProgettoOld = request.getParameter("nomeProgettoOld");
		
		// Posso usare direttamente Progetto piuttosto che ProgettoDTO, in quanto non modifico gli altri campi
		prog.setNomeProgetto(request.getParameter("nomeProgetto"));
		prog.setDescrizione(request.getParameter("descrizione"));
		prog.setLinkImg(request.getParameter("linkImg"));
		prog.setCosto(Double.valueOf(request.getParameter("costo")));
		
		if (repo.modifyProgettoByNomeProgetto(prog, nomeProgettoOld) > 0) {
			RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
			request.setAttribute("msgSuccess", "Modifica avvenuta con successo!");
			rd.forward(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
			request.setAttribute("msgError", "Modifica non avvenuta.");
			rd.forward(request, response);
		}
	
	}

}
