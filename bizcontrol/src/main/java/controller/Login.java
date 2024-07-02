package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AccountRepository;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AccountRepository repo = new AccountRepository();
		// email e username Unique in DB, possiamo usare qualsiasi dei due per idenfiticare.
		String utente = request.getParameter("username-email");
		// Ricevo il permesso in string
		String permesso = repo.findAccount(utente,
				request.getParameter("password"));
				
		
		if(permesso != null) {
			HttpSession session = request.getSession(true);
			session.setAttribute("permesso", permesso);
			session.setAttribute("utente", utente);
			session.setAttribute("offset", 0);
			session.setAttribute("limit", 10);
			session.setAttribute("azione", "first");
			session.setAttribute("filtro", "paginazione");
			session.setAttribute("azioneCard", "first");
			session.setAttribute("limitCards", 12);
			session.setAttribute("offsetCards", 0);
			RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
			rd.forward(request, response);
			
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			request.setAttribute("msgError", "Login non effettuato.");
			rd.forward(request, response);
		}
	
	}

}
