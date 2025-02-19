<%@ page import="java.util.List" %>
<%@ page import="model.RicevimentoService" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    String codiceProfessore = request.getParameter("codiceProfessore");
    model.Studente studente = (model.Studente) session.getAttribute("utente");
    if (studente == null) {
        response.sendRedirect("Login.jsp");
        return;
    }

    RicevimentoService service = new RicevimentoService();
    List<String[]> orari = service.cercaGiornoOraProf(codiceProfessore);
%>

<!doctype html>
<html lang="it">
<head>
    <title>Prenota ricevimento - UniMeet</title>
    <jsp:include page="/application/Header.jsp" />
    <title>Prenota ricevimento - UniMeet</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/prenotaRicevimento.css">

    <!-- Bootstrap Datepicker CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css">
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <!-- Bootstrap Datepicker JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
    <!-- jQuery Timepicker Addon -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.13.18/jquery.timepicker.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.13.18/jquery.timepicker.min.js"></script>
    <script>
        const config = {
            giorniDisponibili: <%= new Gson().toJson(orari.stream().map(giornoOra -> {
            return Map.of("giorno", giornoOra[0], "ora", giornoOra[1]);
        }).collect(Collectors.toList())) %>,
            contextPath: '<%= request.getContextPath() %>',
            codiceProfessore: '<%= codiceProfessore %>'
        };
    </script>
    <script src="${pageContext.request.contextPath}/scripts/PrenotaRicevimento.js"></script>
</head>

<body>
<div class="container custom-container">
    <div class="row justify-content-center text-start">
        <div class="col-md-10">
            <h1 class="h3 mb-3 font-weight-normal text-center">Prenota ricevimento</h1>
            <div class="custom-box">
                <h1 class="fw-bold text-center mb-4">
                    <%= request.getParameter("nome") != null ? request.getParameter("nome") : "Nome professore non disponibile" %>
                </h1>
                <div class="row">
                    <div class="col-md-6">
                        <p class="fw-bold">Ricevimenti:</p>
                        <ul class="list-unstyled">
                            <% if (orari != null && !orari.isEmpty()) {
                                for (String[] giornoOra : orari) {
                                    String giorno = giornoOra[0];
                                    String ora = giornoOra[1];
                                    String nota = giornoOra.length > 2 ? giornoOra[2] : "Nessuna nota";
                            %>
                            <li>
                                <strong><%= giorno %></strong> dalle <strong><%= ora %></strong>
                                <% if (!"Nessuna nota".equals(nota)) { %>
                                <br><strong>Nota:</strong> <em><%= nota %></em>
                                <% } %>
                            </li>
                            <%     }
                            } else { %>
                            <li>Orari non disponibili</li>
                            <% } %>
                        </ul>

                        <p class="small" style="text-align: center;">
                            Per fissare un appuntamento in un giorno o in un orario diverso da quelli indicati, inviare un E-Mail a:
                            <a href="mailto:<%= request.getParameter("email") != null ? request.getParameter("email") : "mail@example.com" %>">
                                <%= request.getParameter("email") != null ? request.getParameter("email") : "mail@example.com" %>
                            </a>
                        </p>
                    </div>
                    <div class="col-md-6">
                        <form action="<%= request.getContextPath() %>/PrenotaRicevimentoServlet" method="post">

                            <input type="hidden" name="codiceProfessore" value="<%= codiceProfessore %>"/>
                            <input type="hidden" name="matricolaStudente" value="<%= studente.getMatricola() %>"/>
                            <p class="fw-bold">Giorni disponibili:</p>
                            <div class="d-flex gap-2">

                                <div class="col-md-6">
                                    <label for="datePicker" class="form-label">Selezione Giorno:</label>
                                    <input type="text" class="form-control" id="datePicker" name="giorno"
                                           placeholder="Seleziona un Giorno">
                                </div>
                                <div class="col-md-6">
                                    <label for="timePicker" class="form-label">Selezione Ora:</label>
                                    <select class="form-control" id="timePicker" name="ora" placeholder="Seleziona Ora">
                                        <option value="">Seleziona un orario</option>
                                    </select>
                                </div>

                            </div>

                            <div id="noAvailabilityMessage" class="text-danger" style="display: none; font-size: 14px;">
                                Nessun orario disponibile per il giorno selezionato.
                            </div>

                            <div class="note-section">
                                <textarea class="custom-textarea" rows="3" name="nota" placeholder="Note"></textarea>
                            </div>

                            <div class="text-center mt-4">
                                <button id="prenotaBtn" class="custom-btn-prenota" type="submit" disabled>
                                    Prenota
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div class="text-center mt-3">
                <a href="MyHome.jsp" class="btn btn-danger text-white">
                    Torna alla landing page!
                </a>
            </div>
        </div>
    </div>
</div>
</body>

<footer>
    <jsp:include page="/application/Footer.jsp" />
</footer>
</html>
