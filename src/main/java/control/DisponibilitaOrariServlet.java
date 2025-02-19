package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import model.DriverManagerConnectionPool;

@WebServlet("/DisponibilitaOrariServlet")
public class DisponibilitaOrariServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        String giorno = request.getParameter("giorno");
        String codiceProfessore = request.getParameter("codiceProfessore");

        Map<String, String> mappaGiorni = Map.of(
                "monday", "lunedì",
                "tuesday", "martedì",
                "wednesday", "mercoledì",
                "thursday", "giovedì",
                "friday", "venerdì"
        );

        try (PrintWriter out = response.getWriter()) {
            if (giorno == null || codiceProfessore == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"error\":\"Parametri 'giorno' e 'codiceProfessore' richiesti.\"}");
                return;
            }

            try (Connection con = DriverManagerConnectionPool.getConnessione()) {
                String query = "SELECT giorno, ora FROM prenotazioneRicevimento WHERE giorno = ? AND codiceProfessore = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, giorno.toLowerCase());
                ps.setString(2, codiceProfessore);

                ResultSet rs = ps.executeQuery();
                List<Map<String, String>> giorniOre = new ArrayList<>();
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("H:mm");
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm");

                while (rs.next()) {
                    String giornoDb = rs.getString("giorno").toLowerCase();
                    String giornoItaliano = mappaGiorni.getOrDefault(giornoDb, giornoDb);

                    LocalTime ora = LocalTime.parse(rs.getString("ora"), inputFormatter);
                    String oraFormattata = ora.format(outputFormatter);

                    Map<String, String> giornoOra = new HashMap<>();
                    giornoOra.put("giorno", giornoItaliano);
                    giornoOra.put("ora", oraFormattata);

                    giorniOre.add(giornoOra);
                }

                String jsonResponse = new Gson().toJson(giorniOre);
                out.println(jsonResponse);

            } catch (SQLException | DateTimeParseException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println("{\"error\":\"Errore nella connessione al database: " + e.getMessage() + "\"}");
            }
        }
    }
}

