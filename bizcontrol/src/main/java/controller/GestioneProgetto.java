package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DipendenteDTO;
import model.DipendenteRepository;

/**
 * Servlet implementation class GestioneProgetto
 */
@WebServlet("/GestioneProgetto")
public class GestioneProgetto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DipendenteRepository dipRepo = new DipendenteRepository();
		String nomeProgetto = request.getParameter("nomeProgetto");
		ArrayList<DipendenteDTO> arrayDipAssociati = dipRepo.findAllDipendentiAssociatiByNomeProgetto(nomeProgetto);
		ArrayList<DipendenteDTO> arrayDipNonAssociati = dipRepo.findAllDipendentiNonAssociatiByNomeProgetto(nomeProgetto);
		
		RequestDispatcher rd = request.getRequestDispatcher("gestioneprogetto.jsp");
		request.setAttribute("arrayDipAssociati", arrayDipAssociati);
		request.setAttribute("arrayDipNonAssociati", arrayDipNonAssociati);
		// Porto anche il nome, per mostrarlo nel titolo
		request.setAttribute("nomeProgetto", nomeProgetto);
		rd.forward(request, response);
	
	}

}
