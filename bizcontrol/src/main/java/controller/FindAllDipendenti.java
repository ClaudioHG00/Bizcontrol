package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DipendenteDTO;
import model.DipendenteRepository;
import model.ProgettoRepository;

/**
 * Servlet implementation class FindAllDipendenti
 */
@WebServlet("/FindAllDipendenti")
public class FindAllDipendenti extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DipendenteRepository repo = new DipendenteRepository();
		ProgettoRepository progRepo = new ProgettoRepository();
		HttpSession session = request.getSession();
		// Eseguo il downcast esplicito, sono sicuro che permesso abbia un tipo String
		String permesso = (String) session.getAttribute("permesso");

		// Stampa di tutto con DTO per admin
		if (permesso.equals("admin")) {
			ArrayList<DipendenteDTO> arrayDTO = new ArrayList<DipendenteDTO>();
			// 1. Ricevo i parametri dalla session, impostati dal doPost
			Integer limit = (Integer) session.getAttribute("limit");
			Integer offset = (Integer) session.getAttribute("offset");
			String azione = (String) session.getAttribute("azione");
			String filtro = (String) session.getAttribute("filtro");
			ResultSet rs = null;

			// Filtro Codice Fiscale con Paginazione
			if (filtro.equals("filtroCodiceFiscale")) {
				String cfFilterStringa = (String) session.getAttribute("cfFilterStringa");
				int nDip = repo.countDipendentiFilterByCodiceFiscale(cfFilterStringa);
				offset = calculateOffset(nDip, limit, offset, azione);
				rs = repo.filterByCodiceFiscaleWithPaginazione(cfFilterStringa, limit, offset);

				// Filtro Nome Cognome con Paginazione
			} else if (filtro.equals("nomeCognomeFilter")) {
				String nomeCognomeFilterNome = (String) session.getAttribute("nomeCognomeFilterNome");
				String nomeCognomeFilterCognome = (String) session.getAttribute("nomeCognomeFilterCognome");
				int nDip = repo.countDipendentiFilterByNomeCognome(nomeCognomeFilterNome, nomeCognomeFilterCognome);
				offset = calculateOffset(nDip, limit, offset, azione);
				rs = repo.filterByNomeCognomeWithPaginazione(nomeCognomeFilterNome, nomeCognomeFilterCognome, limit,
						offset);

				// Filtro Luogo di nascita
			} else if (filtro.equals("luogoNascitaFilter")) {
				String luogoNascitaFilterString = (String) session.getAttribute("luogoNascitaFilterString");
				int nDip = repo.countDipendentiFilterByLuogoNascita(luogoNascitaFilterString);
				offset = calculateOffset(nDip, limit, offset, azione);
				rs = repo.filterByLuogoNascitaWithPaginazione(luogoNascitaFilterString, limit, offset);

				// Filtro Stipendio
			} else if (filtro.equals("stipendioFilter")) {
				// prendo stringa da sessione, downcast da object a stringa, valueof per prendere valore int
				int maxStipendio = Integer.valueOf((String) session.getAttribute("stipendioFilterStringa"));
				int nDip = repo.countDipendentiFilterByMaxStipendio(maxStipendio);
				offset = calculateOffset(nDip, limit, offset, azione);
				rs = repo.filterByMaxStipendioWithPaginazione(maxStipendio, limit, offset);

				// Filtro Tipo Permesso
			} else if (filtro.equals("permessoFilter")) {
				String permessoFilterStringa = (String) session.getAttribute("permessoFilterStringa");
				int nDip = repo.countDipendentiFilterTipoPermesso(permessoFilterStringa);
				offset = calculateOffset(nDip, limit, offset, azione);
				rs = repo.filterByTipoPermessoWithPaginazione(permessoFilterStringa, limit, offset);

				// Default: Solo paginazione
			} else if (filtro.equals("paginazione")) {
				int nDip = repo.countAllDipendenti();
				offset = calculateOffset(nDip, limit, offset, azione);
				rs = repo.findDipendentiFilteredWithPaginazione(limit, offset);
			}

			// Setto l'offset aggiornato, cosi da poterlo riprendere a rinizio ciclo vita servlet
			session.setAttribute("offset", offset);

			try {
				while (rs.next()) {
					DipendenteDTO dip = new DipendenteDTO();
					dip.setNome(rs.getString("nome"));
					dip.setCognome(rs.getString("cognome"));
					dip.setSesso(rs.getBoolean("sesso"));
					dip.setDataNascita(rs.getDate("dataNascita"));
					dip.setLuogoNascita(rs.getString("luogoNascita"));
					dip.setCodiceFiscale(rs.getString("codiceFiscale"));
					dip.setStipendio(rs.getDouble("stipendio"));
					dip.setUsername(rs.getString("username"));
					dip.setEmail(rs.getString("email"));
					dip.setTipoPermesso(rs.getString("tipo"));
					// Conteggio a parte del num progetti associati
					dip.setNumProgetti(progRepo.countProgettiDipendente(dip.getCodiceFiscale()));
					arrayDTO.add(dip);
				}

				rs.close();
				RequestDispatcher rd = request.getRequestDispatcher("gestionedipendente.jsp");
				request.setAttribute("arraydip", arrayDTO);
				rd.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// Visibilita di un singolo utente per guest
		if (permesso.equals("guest")) {
			String usernameEmail = (String) session.getAttribute("utente");
			ResultSet rs = repo.findDipendenteByUsernameEmail(usernameEmail);
			DipendenteDTO dip = null;

			try {
				if (rs.next()) {
					dip = new DipendenteDTO();
					dip.setNome(rs.getString("nome"));
					dip.setCognome(rs.getString("cognome"));
					dip.setSesso(rs.getBoolean("sesso"));
					dip.setDataNascita(rs.getDate("dataNascita"));
					dip.setCodiceFiscale(rs.getString("codiceFiscale"));
					dip.setLuogoNascita(rs.getString("luogoNascita"));
					dip.setStipendio(rs.getDouble("stipendio"));
					dip.setUsername(rs.getString("username"));
					dip.setEmail(rs.getString("email"));
//					dip.setPassword(rs.getString("password"));
					dip.setTipoPermesso(rs.getString("tipo"));
				}
				RequestDispatcher rd = request.getRequestDispatcher("gestionedipendente.jsp");
				request.setAttribute("dip", dip);
				rd.forward(request, response);

			} catch (SQLException e) {
				e.printStackTrace();
				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
				request.setAttribute("msgError", "Qualcosa � andato storto!");
				rd.forward(request, response);
			}
		}

//		OBSOLETO: Stampa iniziale di tutti i dipendenti senza DTO
//		if (permesso.equals("admin")) {
//			ArrayList<Dipendente> array = new ArrayList<Dipendente>();
//			ArrayList<Account> arrAccount = new ArrayList<Account>();
//
//			ResultSet rs = repo.findAllDipendenti();
//
//			try {
//				while (rs.next()) {
//					Dipendente d = new Dipendente();
//					d.setNome(rs.getString("nome"));
//					d.setCognome(rs.getString("cognome"));
//					d.setSesso(rs.getBoolean("sesso"));
//					d.setDataNascita(rs.getDate("dataNascita"));
//					d.setCodiceFiscale(rs.getString("codiceFiscale"));
//					d.setLuogoNascita(rs.getString("luogoNascita"));
//					d.setStipendio(rs.getDouble("stipendio"));
//					array.add(d);
//					Account a = new Account();
//					a.setUsername(rs.getString("username"));
//					a.setEmail(rs.getString("email"));
//					a.setTipo(rs.getString("tipo"));
//					// Non voglio vedere la pw
//					arrAccount.add(a);
//				}
//				// Wrapper che permette il forward alla pagina gestionedipendente.jsp
//				RequestDispatcher rd = request.getRequestDispatcher("gestionedipendente.jsp");
//				// In questa request, mando il parametro arraydip assegnandogli l'array ottenuto
//				request.setAttribute("arraydip", array);
//				request.setAttribute("arrayacc", arrAccount);
//				// Manda la request
//				rd.forward(request, response);
//
//				// SQLException tipo di errore lanciato da rs.next()
//			} catch (SQLException e) {
//				e.printStackTrace();
//
//				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
//				request.setAttribute("msg", "Qualcosa � andato storto!");
//				rd.forward(request, response);
//			}
//		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		// 3. Ricevo dal JSP, setto nella session gli attributi per la paginazione
		int limit = Integer.parseInt(request.getParameter("limit"));
		session.setAttribute("limit", limit);
		String azione = request.getParameter("azione");
		session.setAttribute("azione", azione);

		// 4. Ritorno a doGet per aggiornare la visualizzazione
		response.sendRedirect("FindAllDipendenti");
	}

	// Metodo ausiliare per paginazione in doGet()
	private int calculateOffset(int nDip, int limit, int offset, String azione) {
													// 20-10=10 22-(22%10)=20
		int lastPageOffset = (nDip % limit == 0) ? nDip - limit : nDip - (nDip % limit);
		// Parametro che passero al click nell jsp
		switch (azione) {
		// Pagina successiva
		case "next":
			offset += limit;
			if (offset > nDip - limit) // Se supera, equivale a trovarsi nell'ultima pagina
				offset = lastPageOffset;
			break;
		// Pagina precedente
		case "previous":
			offset -= limit;
			if (offset < 0)// Azzero se prova ad andare a offset negativo
				offset = 0;
			break;
		// Prima pagina
		case "first":
			offset = 0;
			break;
		// Ultima pagina
		case "last":
			offset = lastPageOffset;
			break;
		default:
			break;
		}
		return offset;
	}

}
