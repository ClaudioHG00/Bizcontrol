package controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@WebFilter("/*") // Verra applicato per ogni indirizzo
public class AuthenticationFilter extends HttpFilter implements Filter {
       
    private static final long serialVersionUID = 1L;

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
    	// con false non creo una nuova sessione se non presente
        HttpSession session = req.getSession(false);
        
        // sessione gia presente
        boolean loggedIn = (session != null) && (session.getAttribute("utente") != null);
        String loginURL = req.getContextPath() + "/login.jsp"; // getContextPath per prendere url prima
        String loginServletURL = req.getContextPath() + "/Login";
        // Per prendere gli url della request corrente e confrontarli con quelli ottenuti.
        boolean loginRequest = req.getRequestURI().equals(loginURL); 
        boolean loginServlet = req.getRequestURI().equals(loginServletURL);
        
        /* Casi positivi
         * 1. l'utente gia loggato
         * 2. utente nella pagina login.jsp, in fase di compilazione form
         * 3. utente in fase login, nella servlet
         * 4. ritorna al punto 1
         */
        if (loggedIn || loginRequest || loginServlet) {
            chain.doFilter(req, res); // Lasciamo passare la richiesta con chain
        } else {
        	res.sendRedirect(loginURL); // Reindirizziamo alla pagina di login se non siamo autenticati
        }
    }
    
}
