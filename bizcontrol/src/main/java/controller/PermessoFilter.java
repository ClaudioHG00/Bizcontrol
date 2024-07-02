package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PermessoFilter
 */
@WebServlet("/PermessoFilter")
public class PermessoFilter extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		session.setAttribute("filtro", "permessoFilter");
		String permessoFilterStringa = request.getParameter("permessoFilterStringa");
		session.setAttribute("permessoFilterStringa", permessoFilterStringa);
		response.sendRedirect("FindAllDipendenti");
	}

}
