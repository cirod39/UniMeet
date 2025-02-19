package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PrenotazioneRicevimento;
import model.PrenotazioneRicevimentoService;

/**
 * Servlet implementation class EliminaRicevimentoRiepilogoServlet
 */
@WebServlet("/EliminaRicevimentoRiepilogoServlet")
public class EliminaRicevimentoRiepilogoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminaRicevimentoRiepilogoServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Puoi lasciare il metodo vuoto se non lo usi
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Recupero del parametro codicePrenotazione dalla richiesta
        String codicePrenotazioneStr = request.getParameter("codicePrenotazione");
     
        // Controllo se il parametro è presente e non è vuoto
        if (codicePrenotazioneStr == null || codicePrenotazioneStr.isEmpty()) {
            request.setAttribute("esito", "Errore: codice prenotazione mancante.");
            RequestDispatcher rd = request.getRequestDispatcher("/RiepilogoRicevimenti.jsp");
            rd.forward(request, response);
            return;
        }
        
        int codicePrenotazione = Integer.parseInt(codicePrenotazioneStr);

        // Esegui la logica di rimozione
        PrenotazioneRicevimentoService prenotazioneRicevimento = new PrenotazioneRicevimentoService();
        boolean esitoRimozione = prenotazioneRicevimento.rimuoviPrenotazionePerCodice(codicePrenotazione);
       
        // Imposta il messaggio di esito della rimozione
        if (esitoRimozione) {
            request.setAttribute("esito", "Rimozione avvenuta con successo!");
            
           
        } else {
            request.setAttribute("esito", "Errore durante la rimozione.");
        }
        
        
        response.sendRedirect(request.getContextPath()+"/application/RiepilogoRicevimenti.jsp");
       
       
       
        
    }
}
