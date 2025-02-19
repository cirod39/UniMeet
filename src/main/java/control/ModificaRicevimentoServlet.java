package control;

import model.Ricevimento;
import model.RicevimentoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/ModificaRicevimentoServlet")
public class ModificaRicevimentoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ModificaRicevimentoServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String codiceProfessore = (String) request.getSession().getAttribute("codiceProfessore");
        String giorno = request.getParameter("giorno");
        String ora = request.getParameter("ora");
        String noteIn = request.getParameter("noteIn");
        int codiceRicevimento = Integer.parseInt(request.getParameter("codiceRicevimento"));

      

        RicevimentoService ricevimentoService = new RicevimentoService();

        try {
            // Verifica se il ricevimento esiste già per il giorno e ora specificati
            if (ricevimentoService.ricevimentoPresenteOra(giorno, codiceProfessore,ora)) {
                request.getSession().setAttribute("esito", "Errore: Ricevimento già presente.");
                response.sendRedirect(request.getContextPath() + "/application/ModificaRicevimenti.jsp");
                return;
            }
        } catch (SQLException e) {
            // Gestisci gli errori di connessione al database
            e.printStackTrace();
            request.setAttribute("esito", "Errore di connessione al database.");
            response.sendRedirect(request.getContextPath() + "/application/ModificaRicevimenti.jsp");
            return;
        }

        try {
            // Validazione degli input
            if (giorno == null || giorno.isEmpty() || ora == null || ora.isEmpty()) {
                throw new IllegalArgumentException("Giorno e ora sono obbligatori.");
            }


            // Recupera il ricevimento da modificare
            Ricevimento ricevimento = ricevimentoService.getRicevimentoPerCodice(codiceRicevimento);
            ricevimento.setData(giorno);
            ricevimento.setOra(ora);
            ricevimento.setNote(noteIn);
            if (ricevimento != null) {
                // Modifica il ricevimento

                if (ricevimentoService.modificaRicevimento(ricevimento)) {
                    // Successo nella modifica

                    request.getSession().setAttribute("esito", "Ricevimento modificato con successo.");
                } else {
                    // Errore nella modifica
                    request.getSession().setAttribute("esito", "Impossibile modificare il ricevimento.");
                }
            } else {
                // Errore: ricevimento non trovato
                request.getSession().setAttribute("esito", "Ricevimento non trovato.");
            }
        } catch (IllegalArgumentException e) {
            // Gestisci l'errore di validazione degli input
            request.setAttribute("esito", "Errore: " + e.getMessage());
        } catch (Exception e) {
            // Gestisci gli altri errori imprevisti
            request.setAttribute("esito", "Errore imprevisto: " + e.getMessage());
            e.printStackTrace(); // Log dell'errore
        }

        // Redirect alla stessa pagina con il messaggio di esito
        response.sendRedirect(request.getContextPath() + "/application/ModificaRicevimenti.jsp");
    }
}
