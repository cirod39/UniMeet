package control;

import model.Ricevimento;
import model.RicevimentoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/EliminaRicevimentoServlet")
public class EliminaRicevimentoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EliminaRicevimentoServlet(){
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");

            String codiceProfessore = (String) request.getSession().getAttribute("codiceProfessore");
            int codiceRicevimento = Integer.parseInt(request.getParameter("codiceRicevimento"));
            String referer = request.getHeader("Referer");

            System.out.println("Referer: " + referer); // Log per debugging
            System.out.println("Codice ricevimento: " + codiceRicevimento);

            RicevimentoService ricevimentoService = new RicevimentoService();
            Ricevimento ricevimento = ricevimentoService.getRicevimentoPerCodice(codiceRicevimento);

            if (ricevimento != null) {
                boolean eliminato = ricevimentoService.eliminaRicevimento(ricevimento);
                request.getSession().setAttribute("esito", eliminato ? "Eliminazione effettuata." : "Eliminazione non effettuata.");
            } else {
                request.getSession().setAttribute("esito", "Errore: Ricevimento inesistente.");
            }

            // Gestione redirect in base al referer
            if (referer != null) {
                if (referer.contains("ModificaRicevimenti")) {
                    response.sendRedirect(request.getContextPath() + "/application/ModificaRicevimenti.jsp");
                    return;
                } else if (referer.contains("SelezionaOpzioniModifica")) {
                    response.sendRedirect(request.getContextPath() + "/application/SelezionaOpzioniModifica.jsp");
                    return;
                }
            }

            // Redirect di fallback
            response.sendRedirect(request.getContextPath() + "/application/Home.jsp");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.getSession().setAttribute("esito", "Errore nei dati ricevuti.");
            response.sendRedirect(request.getContextPath() + "/application/ModificaRicevimenti.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("esito", "Errore interno.");
            response.sendRedirect(request.getContextPath() + "/application/Home.jsp");
        }
    }
}