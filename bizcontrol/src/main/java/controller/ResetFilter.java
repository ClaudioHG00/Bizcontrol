package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ResetFilter
 */
@WebServlet("/ResetFilter")
public class ResetFilter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		// Azzero come a inizio login
		session.setAttribute("filtro", "paginazione");
		session.setAttribute("offset", 0);
		session.setAttribute("limit", 10);
		session.setAttribute("azione", "first");
		// Rimuovo gli attributi di input dal form di gestionedipendente.jsp
		session.removeAttribute("cfFilterStringa");
		session.removeAttribute("nomeCognomeFilterNome");
		session.removeAttribute("nomeCognomeFilterCognome");
		session.removeAttribute("luogoNascitaFilterString");
		session.removeAttribute("stipendioFilterStringa");
		session.removeAttribute("permessoFilterStringa");
		
		response.sendRedirect("FindAllDipendenti");
	}

}
