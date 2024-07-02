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
 * Servlet implementation class DeleteProgetto
 */
@WebServlet("/DeleteProgetto")
public class DeleteProgetto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProgettoRepository repo = new ProgettoRepository();
		String nomeProgetto = request.getParameter("nomeProgetto");
		
		if(repo.deleteProgettoByNomeProgetto(nomeProgetto) > 0) {
			RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
			request.setAttribute("msgSuccess", "Eliminazione avvenuta con successo!");
			rd.forward(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
			request.setAttribute("msgError", "Eliminazione non avvenuta.");
			rd.forward(request, response);
		}
	
	}
	
}
