package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Ricevimento;
import model.RicevimentoService;

@WebServlet("/AggiungiRicevimentoServlet") // Mapping del Servlet
public class AggiungiRicevimentoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AggiungiRicevimentoServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        // Recupero del codice professore con controllo
        String codiceProfessore = (String) request.getSession().getAttribute("codiceProfessore");
        // Recupero i parametri dalla richiesta

        String giorno = request.getParameter("giorno");
        String ora = request.getParameter("ora");
        String noteIn = request.getParameter("noteIn");

        if (codiceProfessore == null || codiceProfessore.isEmpty()) {
            // Se il codice professore non è valido, ritorno alla pagina con un messaggio d'errore
            request.getSession().setAttribute("esito", "Errore: Codice professore non valido.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/application/AggiungiRicevimento.jsp");
            dispatcher.forward(request, response);
            return;
        }



        RicevimentoService ricevimentoService = new RicevimentoService();
        try {
            if (ricevimentoService.ricevimentoPresente(giorno, codiceProfessore)) {
                request.getSession().setAttribute("esito", "Errore: Ricevimento aggiunto presente.");
                response.sendRedirect(request.getContextPath() + "/application/AggiungiRicevimento.jsp");
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            // Validazione degli input
            if (giorno == null || giorno.isEmpty() || ora == null || ora.isEmpty()) {
                throw new IllegalArgumentException("Giorno e ora sono obbligatori.");
            }
            int codiceRicevimento= ricevimentoService.getCodiceRicevimento(ora,giorno,codiceProfessore);
            // Creazione e salvataggio del Ricevimento
            Ricevimento ricevimento = new Ricevimento(codiceRicevimento,giorno, ora, noteIn, codiceProfessore);
           if( ricevimentoService.aggiungiRicevimento(ricevimento)) {// Imposta messaggio di successo
               // Imposta l'attributo "esito" nella sessione
               request.getSession().setAttribute("esito", "Ricevimento aggiunto con successo.");
           }
           else
               request.getSession().setAttribute("esito", "impossibile aggiungere ricevimento.");
        } catch (IllegalArgumentException e) {
            request.setAttribute("esito", "Errore: " + e.getMessage());
        } catch (Exception e) {
            request.setAttribute("esito", "Errore imprevisto: " + e.getMessage());
            e.printStackTrace(); // Log dell'errore
        }

        // Inoltro della richiesta alla JSP per mostrare l'esito
        response.sendRedirect(request.getContextPath()+"/application/AggiungiRicevimento.jsp");
    }
}
