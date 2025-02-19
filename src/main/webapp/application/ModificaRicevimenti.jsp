<%@ page import="java.util.stream.Collectors" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="model.RicevimentoService" %>
<%@ page import="model.Ricevimento" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    //dovrebbe reindirizzare a login quando scade la sessione
    if (session == null || session.getAttribute("codiceProfessore") == null) {
        // Sessione scaduta o attributo mancante
        response.sendRedirect("Login.jsp");
        return;
    }

    // Recupera l'attributo
    String codiceProfessore = session.getAttribute("codiceProfessore").toString();
    String codiceR = request.getParameter("codiceRicevimento");
    int codiceRicevimento=0;
    if (codiceR != null) {
        // Usa il valore di codicePrenotazione per recuperare i dettagli necessari
        codiceRicevimento = Integer.parseInt(codiceR);
    } else {
    }
    RicevimentoService service = new RicevimentoService();
    List<Ricevimento> listaRicevimenti = service.getListaRicevimenti(codiceProfessore);

%>
<html>
<head>
    <jsp:include page="/application/Header.jsp" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/prenotaRicevimento.css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <title>Modifica Ricevimenti</title>
</head>
<body>
<div class="contenimento">
    <div class="container custom-container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <% String esito = (String) session.getAttribute("esito"); %>
                <% if (esito != null) { %>
                <div class="alert alert-info">
                    <%= esito %>
                </div>
                <% session.removeAttribute("esito"); %>
                <% } %>
                <h1 class="h3 mb-3 font-weight-normal text-center">Modifica Ricevimento</h1>
                <div class="custom-box">
                    <div class="row">
                        <!-- Colonna per la lista dei ricevimenti -->
                        <div class="col-md-6">
                            <p class="fw-bold">Ricevimenti:</p>
                            <ul class="list-unstyled">
                                <li>
                                    <% for(Ricevimento ricevimento: listaRicevimenti){%>
                                    <strong><%= ricevimento.getGiorno() %></strong> dalle <strong><%= ricevimento.getOra() %></strong>
                                    <% if (!"Nessuna nota".equals(ricevimento.getNote())) { %>
                                    <br><strong>Nota:</strong> <em><%= ricevimento.getNote()%></em>
                                    <br>
                                    <form action="<%= request.getContextPath() %>/EliminaRicevimentoServlet" method="post">
                                        <input type="hidden" value="<%= ricevimento.getCodice()%>" name = "codiceRicevimento">
                                        <button id="deleteBtn" class="btn btn-danger text-white" type="submit">
                                            elimina
                                        </button>
                                    </form>
                                    <% } %>
                                </li>
                                <%     }
                                %>
                            </ul>
                        </div>
                        <!-- Colonna per il form di modifica -->
                        <div class="col-md-6">
                            <form action="<%= request.getContextPath() %>/ModificaRicevimentoServlet" method="post" onsubmit="return validateForm()">
                                <input type="hidden" name="codiceProfessore" value="<%= codiceProfessore %>"/>
                                <p class="fw-bold">Selezione Giorno e ora:</p>
                                <div class="d-flex gap-2">
                                    <div class="col-md-6">
                                        <label for="giorno" class="form-label">Selezione Giorno:</label>
                                        <select class="form-control" id="giorno" name="giorno" onchange="enableAddButton()">
                                            <option value="">Seleziona un giorno</option>
                                            <option value="lunedì">Lunedì</option>
                                            <option value="martedì">Martedì</option>
                                            <option value="mercoledì">Mercoledì</option>
                                            <option value="giovedì">Giovedì</option>
                                            <option value="venerdì">Venerdì</option>
                                        </select>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="ora" class="form-label">Selezione ora:</label>
                                        <select class="form-control" id="ora" name="ora" onchange="enableAddButton()">
                                            <option value="">Seleziona un ora</option>
                                            <option value="9:00">9:00</option>
                                            <option value="9:30">9:30</option>
                                            <option value="10:00">10:00</option>
                                            <option value="10:30">10:30</option>
                                            <option value="11:00">11:00</option>
                                            <option value="11:30">11:30</option>
                                            <option value="12:00">12:00</option>
                                            <option value="12:30">12:30</option>
                                            <option value="13:00">13:00</option>
                                            <option value="13:30">13:30</option>
                                            <option value="14:00">14:00</option>
                                            <option value="14:30">14:30</option>
                                            <option value="15:00">15:00</option>
                                            <option value="15:30">15:30</option>
                                            <option value="16:00">16:00</option>
                                            <option value="16:30">16:30</option>
                                            <option value="17:00">17:00</option>
                                        </select>
                                    </div>
                                </div>

                                <div id="noAvailabilityMessage" class="text-danger" style="display: none; font-size: 14px;">
                                    Nessun orario disponibile per il giorno selezionato.
                                </div>

                                <div class="note-section mt-3">
                                    <textarea class="custom-textarea" rows="3" name="noteIn" placeholder="Note"></textarea>
                                </div>

                                <div class="text-center mt-4">
                                    <input type="hidden" name="codiceRicevimento" value = "<%= codiceRicevimento %>">
                                    <button id="modifyBtn" class="custom-btn-prenota" type="submit" disabled>
                                        Modifica
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
                    <a href="SelezionaOpzioniModifica.jsp" class="btn btn-danger text-white">
                        torna alla pagina di selezione
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<%  esito = (String) request.getAttribute("esito"); %>
<% if (esito != null) { %>
<div class="alert alert-info">
    <%= esito %>
</div>
<% } %>
<jsp:include page="Footer.jsp" />
</body>
<script src="${pageContext.request.contextPath}/scripts/controlloInserimentoRicevimento.js"></script>
<script>
    function enableAddButton() {
        const giorno = document.getElementById("giorno").value;
        const ora = document.getElementById("ora").value;

        const addBtn = document.getElementById("modifyBtn");
        if (giorno && ora) {
            addBtn.disabled = false;
        } else {
            addBtn.disabled = true;
        }
    }
</script>
</html>
