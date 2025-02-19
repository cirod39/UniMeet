package control;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Utils.PasswordHasher;
import model.Studente;
import model.StudenteService;

@WebServlet("/RegistrazioneStudenteServlet")
public class RegistrazioneStudenteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(RegistrazioneStudenteServlet.class.getName());

    public RegistrazioneStudenteServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	//IMPORTANTE, SENZA QUESTE DUE RIGHE NON VENGONO STORATI CORRETTAMENTE I CARATTERI SPECIALI
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        String matricola = request.getParameter("matricola");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        String domanda = request.getParameter("domanda");
        String risposta = request.getParameter("risposta");

        HttpSession session = request.getSession();

        // Controllo dei parametri di input da RegistrazioneStudente.jsp
        if (matricola == null || nome == null || cognome == null || email == null || pass == null || domanda == null || risposta == null ||
                matricola.isEmpty() || nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || pass.isEmpty() || domanda.isEmpty() || risposta.isEmpty()) {
            session.setAttribute("status", "Tutti i campi devono essere compilati.");
            response.sendRedirect(request.getContextPath() + "/application/Registrazione.jsp");
            return;
        }

        try {
            // Creazione dell'oggetto studente
            Studente studente = new Studente();
            studente.setPassword(PasswordHasher.hashPassword(pass));
            studente.setCognome(cognome);
            studente.setEmail(email);
            studente.setNome(nome);
            studente.setMatricola(matricola);
            studente.setDomanda(domanda);
            studente.setRisposta(PasswordHasher.hashPassword(risposta));

            // Salvataggio del professore nel database
            int row = StudenteService.aggiungiStudente(studente);

            if (row > 0) {
                // Registrazione avvenuta con successo
                session.setAttribute("status", "Complimenti, ti sei registrato con successo! Effettua il login.");
                response.sendRedirect(request.getContextPath() + "/application/Login.jsp");
            } else {
                // Fallimento nella registrazione
                session.setAttribute("status", "Qualcosa è andato storto, riprova!");
                response.sendRedirect(request.getContextPath() + "/application/RegistrazioneStudente.jsp");
            }
        } catch (Exception e) {
            // Errore durante la registrazione
            logger.log(Level.SEVERE, "Errore durante la registrazione: ", e);
            session.setAttribute("status", "Si è verificato un errore durante la registrazione.");
            response.sendRedirect(request.getContextPath() + "/application/RegistrazioneStudente.jsp");
        }
    }
}