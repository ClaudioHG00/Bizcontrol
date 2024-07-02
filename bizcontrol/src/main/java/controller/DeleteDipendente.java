package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DipendenteRepository;

/**
 * Servlet implementation class DeleteDipendente
 */
@WebServlet("/DeleteDipendente")
public class DeleteDipendente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteDipendente() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String codiceFiscale = request.getParameter("codiceFiscale");
		DipendenteRepository repo = new DipendenteRepository();
//		System.out.println(codiceFiscale);
		
		boolean isDeleted = repo.deleteDipendenteByCodiceFiscale(codiceFiscale);
		
		if(isDeleted) {
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
