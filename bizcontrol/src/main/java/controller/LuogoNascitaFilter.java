package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LuogoNascitaFilter
 */
@WebServlet("/LuogoNascitaFilter")
public class LuogoNascitaFilter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		session.setAttribute("filtro", "luogoNascitaFilter");
		String luogoNascitaFilterString = request.getParameter("luogoNascitaFilterString");
		session.setAttribute("luogoNascitaFilterString", luogoNascitaFilterString);
		response.sendRedirect("FindAllDipendenti");
	}

}
