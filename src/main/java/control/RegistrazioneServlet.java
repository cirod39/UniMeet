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
import model.Insegnamento;
import model.InsegnamentoService;
import model.Professore;
import model.ProfessoreService;

//ATTENZIONE FORSE E' IL CASO DI UTILIZZARE DUE SERVLET DIVERSE PER STUDENTE E PROFESSORE!

@WebServlet("/RegistrazioneServlet")
public class RegistrazioneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(RegistrazioneServlet.class.getName());

    public RegistrazioneServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        String codiceProfessore = request.getParameter("codiceProfessore");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        String ufficio = request.getParameter("ufficio");
        String domanda = request.getParameter("domanda");
        String risposta = request.getParameter("risposta");
        
        // QUI INVEEC DI PRENDERE GLI INSEGNAMENTI SINGOLARMENTE LI METTO IN UN ARRAY DI INSEGNAMENTI
        String[] insegnamenti = request.getParameterValues("insegnamenti");

        HttpSession session = request.getSession();

        if (codiceProfessore == null || nome == null || cognome == null || email == null || pass == null || ufficio == null || domanda == null || risposta == null ||
                codiceProfessore.isEmpty() || nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || pass.isEmpty() || ufficio.isEmpty() || domanda.isEmpty() || risposta.isEmpty()) {
            session.setAttribute("status", "Tutti i campi devono essere compilati.");
            response.sendRedirect(request.getContextPath() + "/application/Registrazione.jsp");
            return;
        }

        try {
            Professore professore = new Professore();
            professore.setPassword(PasswordHasher.hashPassword(pass));
            professore.setCognome(cognome);
            professore.setEmail(email);
            professore.setNome(nome);
            professore.setCodiceProfessore(codiceProfessore);
            professore.setUfficio(ufficio);
            professore.setDomanda(domanda);
            professore.setRisposta(PasswordHasher.hashPassword(risposta));
            
            int row = ProfessoreService.aggiungiProfessore(professore);

            // QUI MI SCORRO L'ARRAY E LI INSERISCO TUTTI
            if (insegnamenti != null) {
                for (String nomeInsegnamento : insegnamenti) {
                    if (!nomeInsegnamento.isEmpty()) {
                        Insegnamento i = new Insegnamento();
                        i.setCodiceProfessore(codiceProfessore);
                        i.setNomeInsegnamento(nomeInsegnamento);
                        InsegnamentoService.aggiungiInsegnamento(i);
                    }
                }
            }

            if (row > 0) {
                session.setAttribute("status", "Complimenti, ti sei registrato con successo! Effettua il login.");
                response.sendRedirect(request.getContextPath() + "/application/Login.jsp");
            } else {
                session.setAttribute("status", "Qualcosa è andato storto, riprova!");
                response.sendRedirect(request.getContextPath() + "/application/RegistrazioneProfessore.jsp");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Errore durante la registrazione: ", e);
            session.setAttribute("status", "Si è verificato un errore durante la registrazione.");
            response.sendRedirect(request.getContextPath() + "/application/RegistrazioneProfessore.jsp");
        }
    }

}
