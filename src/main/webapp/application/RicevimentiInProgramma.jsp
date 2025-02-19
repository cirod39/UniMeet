<%@ page import="model.PrenotazioneRicevimentoService" %>
<%@ page import="model.PrenotazioneRicevimento" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<%@ page import="model.StudenteService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Ricevimenti in programma</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/riepilogo.css">
</head>
<body>
<jsp:include page="/application/Header.jsp" />
<h1 class="text-center">Ricevimenti in programma</h1>
<%
  String codiceProfessore = (String) session.getAttribute("codiceProfessore");
  if (codiceProfessore == null || codiceProfessore.isEmpty()) {
%>
<div class="alert alert-danger text-center" role="alert">
  Errore: codice professore non trovato nella sessione.
</div>
<%
} else {
  PrenotazioneRicevimentoService prenotazioneService = new PrenotazioneRicevimentoService();
  StudenteService studenteService = new StudenteService();

  try {
    List<PrenotazioneRicevimento> listaPrenotazioni = prenotazioneService.stampaPrenotazioneProfessore(codiceProfessore);

    if (listaPrenotazioni != null && !listaPrenotazioni.isEmpty()) {
%><div class="container">
  <div class="row">
    <% for (PrenotazioneRicevimento prenotazione : listaPrenotazioni) {
      //questo codice serve per prendere il nome e il cognome dello studente di una determinata prenotazione dalla
      //matricola nella prenotazione e metterli nell'oggetto prenotazione per poi stamparli a video
      String matricolaStudente = prenotazioneService.getmatricolaStudenteDaPrenotazione(prenotazione.getCodice());
      String nomeStudente = studenteService.getNomeStudenteByMatricola(matricolaStudente);
      String cognomeStudente = studenteService.getCognomeStudenteByMatricola(matricolaStudente);
    %>
    <div class="col-md-4 d-flex align-items-stretch">
      <div class="card mb-4">
        <div class="card-body">
          <h2 class="card-title">Prenotazione <%= prenotazione.getCodice() %></h2>
          <h6 class="card-subtitle mb-2 text-muted">Giorno: <%= prenotazione.getGiorno() %></h6>
          <h6 class="card-subtitle mb-2 text-muted">Ora: <%= prenotazione.getOra() %></h6>
          <h6 class="card-subtitle mb-2 text-muted">Nome Studente: <%= nomeStudente%></h6>
          <h6 class="card-subtitle mb-2 text-muted">Cognome Studente: <%= cognomeStudente %></h6>
          <h6 class="card-subtitle mb-2 text-muted">Note: <%= prenotazione.getNota() %></h6>
          <h6 class="card-subtitle mb-2 text-muted">Stato: <%= prenotazione.getStato() %></h6>
          <div class="row">
            <div class="col">
              <form action="${pageContext.request.contextPath}/RifiutaRicevimentoServlet" method="POST">
                <input type="hidden" name="codiceProfessore" value="<%= prenotazione.getCodiceProfessore() %>">
                <input type="hidden" name="codicePrenotazione" value="<%=prenotazione.getCodice()%>">
                <button class="btn btn-danger btn-block" type="submit" name="actionRifiuta" value="rifiuta">Rifiuta</button>
              </form>
            </div>
            <div class="col">
              <form action="${pageContext.request.contextPath}/AccettaRicevimentoServlet" method="POST">
                <input type="hidden" name="codiceProfessore" value="<%= prenotazione.getCodiceProfessore() %>">
                <input type="hidden" name="codicePrenotazione" value="<%= prenotazione.getCodice() %>">
                <button class="btn btn-success btn-block" type="submit" name="actionAccetta" value="accetta">Accetta</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
    <% } %>
  </div>
</div>

<%
} else {
%>
<div class="alert alert-info text-center" role="alert">
  Nessun ricevimento in programma.
</div>
<%
  }
} catch (SQLException e) {
  e.printStackTrace();
%>
<div class="alert alert-danger text-center" role="alert">
  Errore nella ricerca delle prenotazioni: <%= e.getMessage() %>
</div>
<%
    }
  }
%>
<% String esito = (String) request.getAttribute("esito"); %>
<% if (esito != null) { %>
<div class="alert alert-info">
  <%= esito %>
</div>
<% } %>
<jsp:include page="Footer.jsp" />
</body>
</html>
