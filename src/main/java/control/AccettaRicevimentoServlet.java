package control;

import java.io.IOException;
import java.sql.SQLException;

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
@WebServlet("/AccettaRicevimentoServlet")
public class AccettaRicevimentoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccettaRicevimentoServlet() {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codiceProfessore = request.getParameter("codiceProfessore").toString();
        int codicePrenotazione = Integer.parseInt(request.getParameter("codicePrenotazione").toString());

        if(codiceProfessore ==null || codiceProfessore.isEmpty()){
            request.setAttribute("esito", "Errore: codice prenotazione mancante.");
            response.sendRedirect(request.getContextPath()+"/application/RicevimentiInProgramma.jsp");
        }





        PrenotazioneRicevimentoService prenotazioneRicevimento = new PrenotazioneRicevimentoService();
        boolean esitoOperazione = false;
        try {
            esitoOperazione = prenotazioneRicevimento.confermaPrenotazionePerProfessore(codiceProfessore, codicePrenotazione);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (esitoOperazione) {
            request.setAttribute("esito", "Rimozione avvenuta con successo!");


        } else {
            request.setAttribute("esito", "Errore durante la rimozione.");
        }
        response.sendRedirect(request.getContextPath()+"/application/RicevimentiInProgramma.jsp");



    }
}
