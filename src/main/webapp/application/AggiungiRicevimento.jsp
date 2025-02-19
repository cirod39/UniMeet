<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Aggiungi Ricevimento</title>
    <jsp:include page="/application/Header.jsp" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/prenotaRicevimento.css">
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/aggiungiRicevimento.css">
</head>
<body>
<% String codiceProfessore = session.getAttribute("codiceProfessore").toString(); %>
<div class="contenimento">
    <div class="container custom-container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <% String esito = (String) session.getAttribute("esito"); %>
                <% if (esito != null) { %>
                <div class="alert alert-info">
                    <%= esito %>
                </div>
                <% session.removeAttribute("esito"); %> <!-- Rimuovi l'attributo per evitare che venga visualizzato più volte -->
                <% } %>
                <h1 class="h3 mb-3 font-weight-normal text-center">Aggiungi ricevimento</h1>
                <div class="custom-box">
                    <form action="<%= request.getContextPath() %>/AggiungiRicevimentoServlet" method="post" onsubmit="return validateForm()">
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
                            <button id="addBtn" class="custom-btn-prenota" type="submit" disabled>
                                Aggiungi
                            </button>
                        </div>
                    </form>
                </div>
                <div class="text-center mt-3">
                    <a href="MyHome.jsp" class="btn btn-danger text-white">
                        Torna alla landing page!
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/scripts/controlloInserimentoRicevimento.js"></script>
<footer>
    <jsp:include page="/application/Footer.jsp" />
</footer>
</html>
