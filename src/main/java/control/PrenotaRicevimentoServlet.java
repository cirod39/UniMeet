package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.PrenotazioneRicevimento;
import model.PrenotazioneRicevimentoService;

@WebServlet("/PrenotaRicevimentoServlet")
public class PrenotaRicevimentoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String giorno = request.getParameter("giorno");
        String ora = request.getParameter("ora");
        String nota = request.getParameter("nota");
        String codiceProfessore = request.getParameter("codiceProfessore");
        String matricolaStudente = request.getParameter("matricolaStudente");

        if (giorno == null || ora == null || codiceProfessore == null || matricolaStudente == null ||
                giorno.isEmpty() || ora.isEmpty() || codiceProfessore.isEmpty() || matricolaStudente.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri mancanti o non validi");
            return;
        }

        try {

            PrenotazioneRicevimento prenotazione = new PrenotazioneRicevimento(0, "In sospeso", giorno, ora, nota, codiceProfessore, matricolaStudente);

            PrenotazioneRicevimentoService.inserisciPrenotazione(prenotazione);

            response.sendRedirect(request.getContextPath() + "/application/RiepilogoRicevimenti.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante l'inserimento della prenotazione");
        }
    }
}
