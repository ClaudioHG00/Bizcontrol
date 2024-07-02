package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Ottieni la sessione esistente con false, non crearne una nuova
		HttpSession session = request.getSession(); 
        session.invalidate(); // Invalida la sessione
	    RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
	    request.setAttribute("msgSuccess","Logout avvenuto con successo.");
	    rd.forward(request, response);
	}


}
