<%--
  Created by IntelliJ IDEA.
  User: giovannitufano
  Date: 26/01/25
  Time: 11:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<%@ page import="model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Modifica o elimina ricevimenti</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/riepilogo.css">
</head>
<body>
<jsp:include page="/application/Header.jsp" />
<h1 class="text-center">Ricevimenti in programma</h1>
<%
  //dovrebbe reindirizzare a login quando scade la sessione
  if (session == null || session.getAttribute("codiceProfessore") == null) {
    // Sessione scaduta o attributo mancante
    response.sendRedirect("Login.jsp");
    return;
  }



  String codiceProfessore = (String) session.getAttribute("codiceProfessore");
  if (codiceProfessore == null || codiceProfessore.isEmpty()) {
%>
<div class="alert alert-danger text-center" role="alert">
  Errore: codice professore non trovato nella sessione.
</div>
<%
} else {
  RicevimentoService ricevimentoService = new RicevimentoService();
  StudenteService studenteService = new StudenteService();


    List<Ricevimento> listaRicevimenti= ricevimentoService.getListaRicevimenti(codiceProfessore);

    if (listaRicevimenti != null && !listaRicevimenti.isEmpty()) {
%><div class="container">
  <div class="row">
    <% for (Ricevimento ricevimento : listaRicevimenti) {
    %>
    <div class="col-md-4 d-flex align-items-stretch">
      <div class="card mb-4">
        <div class="card-body">
          <h2 class="card-title">Prenotazione <%= ricevimento.getCodice() %></h2>
          <h6 class="card-subtitle mb-2 text-muted">Giorno: <%= ricevimento.getGiorno() %></h6>
          <h6 class="card-subtitle mb-2 text-muted">Ora: <%= ricevimento.getOra() %></h6>
          <h6 class="card-subtitle mb-2 text-muted">Note: <%= ricevimento.getNote() %></h6>
          <div class="row">
            <div class="col">
              <form action="${pageContext.request.contextPath}/EliminaRicevimentoServlet" method="POST">
                <input type="hidden" name="codiceProfessore" value="<%= ricevimento.getCodiceProfessore() %>">
                <input type="hidden" name="codiceRicevimento" value="<%=ricevimento.getCodice()%>">
                <button class="btn btn-danger btn-block" type="submit" name="actionElimina" value="elimina">Elimina</button>
              </form>
            </div>
            <div class="col">
              <a class="btn btn-success btn-block" href="ModificaRicevimenti.jsp?codiceRicevimento=<%= ricevimento.getCodice() %>">
                Modifica
              </a>
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
  non ci sono ricevimenti disponibili
</div>
<%
  }
  }
%>
<%  String esito = (String) request.getAttribute("esito"); %>
<% if (esito != null) { %>
<div class="alert alert-info">
  <%= esito %>
</div>
<% } %>
<jsp:include page="Footer.jsp" />
</body>
</html>

