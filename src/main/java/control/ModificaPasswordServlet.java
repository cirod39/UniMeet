package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Utils.PasswordHasher;
import model.Professore;
import model.ProfessoreService;
import model.Studente;
import model.StudenteService;


@WebServlet("/ModificaPasswordServlet")
public class ModificaPasswordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        String userEmail = request.getParameter("email");
        String rispostaUtente = request.getParameter("risposta");
        String domandaUtente = request.getParameter("domanda");
        String newPassword = request.getParameter("newPassword");
        
        HttpSession session = request.getSession();

        	Professore p=null;
        	Studente s = null;
        
        	try {
        		
        		
                s = StudenteService.cercaStudenteEmail(userEmail);
                
                if (s != null) {
                    String domandaSicurezza = s.getDomanda();
                    String rispostaRegistrata = s.getRisposta();
                    
                    if (PasswordHasher.verifyPassword(rispostaUtente, rispostaRegistrata) && 
                        domandaUtente.trim().equalsIgnoreCase(domandaSicurezza.trim())) {
                        
                        String hashedPassword = PasswordHasher.hashPassword(newPassword);
                        s.setPassword(hashedPassword);

                        boolean isUpdated = StudenteService.modificaStudente(s);
                        
                        
                        if (isUpdated) {
                        	session.setAttribute("status", "Password aggiornata con successo! Effettua l'accesso!");
                            response.sendRedirect(request.getContextPath() + "/application/Login.jsp");
                            }
                         else {
                             session.setAttribute("status", "Errore durante l'aggiornamento della password.");
                             response.sendRedirect(request.getContextPath() + "/application/CambioPassword.jsp");
                          }
                        
                        
                    } else {
                        session.setAttribute("status", "Risposta di sicurezza errata.");
                        response.sendRedirect(request.getContextPath() + "/application/CambioPassword.jsp");
                    }
                    
                }
            
			} catch (Exception e) {
				session.setAttribute("status", "Studente non trovato! Riprova!");
                response.sendRedirect(request.getContextPath() + "/application/CambioPassword.jsp");
			}
        	
        	//verifica per il professore
        	try {

        		
                 p = ProfessoreService.cercaProfessoreEmail(userEmail);
                if (p != null) {
                    String domandaSicurezza = p.getDomanda();
                    String rispostaRegistrata = p.getRisposta();

                    if (PasswordHasher.verifyPassword(rispostaUtente, rispostaRegistrata) && 
                        domandaUtente.trim().equalsIgnoreCase(domandaSicurezza.trim())) {
                        
                        String hashedPassword = PasswordHasher.hashPassword(newPassword);
                        p.setPassword(hashedPassword);

                        boolean isUpdated = ProfessoreService.modificaProfessore(p);
                        
                        
                        if (isUpdated) {
                            
                            session.setAttribute("status", "Password aggiornata con successo! Effettua l'accesso");
                            response.sendRedirect(request.getContextPath() + "/application/Login.jsp");
                            }
                         else {
                        	 session.setAttribute("status", "Errore durante l'aggiornamento della password.");
                             response.sendRedirect(request.getContextPath() + "/application/CambioPassword.jsp");
                         }
                        
                    } else {
                    	session.setAttribute("status", "Risposta di sicurezza errata.");
                        response.sendRedirect(request.getContextPath() + "/application/CambioPassword.jsp");
                    }
                }
            
			} catch (Exception e) {
				session.setAttribute("status", "Professore non trovato! Riprova!");
                response.sendRedirect(request.getContextPath() + "/application/CambioPassword.jsp");
			}
        	
        
        	if(s==null && p==null) {
				System.out.println("Utente non trovato");
				response.sendRedirect(request.getContextPath()+"/application/CambioPassword.jsp?error=userNotFound");
        	}
        
    }
}