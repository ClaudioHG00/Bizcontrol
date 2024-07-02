package controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;
import model.Dipendente;
import model.DipendenteRepository;


/**
 * Servlet implementation class InserimentoDipendente
 */
@WebServlet("/InserimentoDipendente")
public class InserimentoDipendente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("formdipendente.jsp");
		request.setAttribute("inserimento", true);
		rd.forward(request, response);
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DipendenteRepository repo = new DipendenteRepository();
		Dipendente d = new Dipendente();
		Account a = new Account();
		
		// nel db sesso = boolean
		String sesso = request.getParameter("sesso");
		if(sesso.equalsIgnoreCase("m")) {
			d.setSesso(true);
		} else if (sesso.equalsIgnoreCase("f")) {
			d.setSesso(false);
		}
		// nel db admin=1,guest=2
		String permesso = request.getParameter("tipoPermesso");
		if(permesso.equals("admin")) {
			a.setIdPermesso(1);
		} else if (permesso.equals("guest")) {
			a.setIdPermesso(2);
		}
		
		d.setNome(request.getParameter("nome"));
		d.setCognome(request.getParameter("cognome"));
		d.setDataNascita(Date.valueOf(request.getParameter("dataNascita")));
		d.setCodiceFiscale(request.getParameter("codiceFiscale"));
		d.setLuogoNascita(request.getParameter("luogoNascita"));
		// Si potrebbe usare anche parseDouble()
		d.setStipendio(Double.valueOf(request.getParameter("stipendio")));
		
		a.setUsername(request.getParameter("username"));
		a.setEmail(request.getParameter("email"));
		a.setPassword(request.getParameter("password"));
		
		// Se esiste gia, per fixxare f5 di pagina con reinserimento
		if(!repo.verifyDipendenteEsistente(d.getCodiceFiscale())) {
			
			if(repo.insertDipendente(d,a) > 0) {
				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
				request.setAttribute("msgSuccess", "Inserimento avvenuto con successo!");
				rd.forward(request, response);
			}
		
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
			request.setAttribute("msgError", "Inserimento non avvenuto.");
			rd.forward(request, response);
		}
	
	}

}
