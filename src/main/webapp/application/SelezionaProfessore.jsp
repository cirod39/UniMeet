<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Professore" %>
<%@ page import="model.ProfessoreService" %>
<%@ page import="model.Insegnamento" %>
<%@ page import="model.InsegnamentoService" %>

<!-- Collegamento all'Header -->
<jsp:include page="/application/Header.jsp" />

<!doctype html>
<html lang="it">
<head>
  <title>Docenti disponibili - UniMeet</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ricerca.css">
</head>

<body>

<div class="container mt-5">
  <h1 class="text-center">Docenti disponibili:</h1>
  <h6 class="text-center">(In alternativa è possibile ricercare tramite la barra di ricerca per un risultato più rapido)</h6><br>

  <%
    ProfessoreService professoreService = new ProfessoreService();
    List<Professore> professori = ProfessoreService.stampaListaProfessori(); // Recupera tutti i professori
  %>

  <!-- Contenitore per la griglia dei professori -->
  <div class="row">
    <% for (Professore p : professori) { %>
    <div class="col-md-4">
      <div class="article-card card">
        <div class="card-body">
          <h2 class="card-title"><%= p.getNome() + " " + p.getCognome() %></h2>
          <h6 class="card-subtitle mb-2 text-muted"><%= "Ufficio: " + p.getUfficio() %></h6>
          <h6 class="card-subtitle mb-2 text-muted"><%= "Codice Professore: " + p.getCodiceProfessore() %></h6>
          <h6 class="card-subtitle mb-2 text-muted"><%= "E-mail: " + p.getEmail() %></h6>

          <div class="text-center">
            <h5 class="mt-3">Insegnamenti:</h5>
            <ul class="list-unstyled d-inline-block text-start">
              <%
                List<Insegnamento> insegnamenti = null;
                try {
                  insegnamenti = new InsegnamentoService().cercaInsegnamentiPerProfessore(p.getCodiceProfessore());
                } catch (SQLException e) {
                  e.printStackTrace();
                }
                if (insegnamenti != null && !insegnamenti.isEmpty()) {
                  for (Insegnamento i : insegnamenti) { %>
              <li>- <%= i.getNomeInsegnamento() %></li>
              <%   }
              } else { %>
              <li>Nessun insegnamento disponibile.</li>
              <% } %>
            </ul>
          </div>

          <a class="btn btn-success" href="PrenotaRicevimento.jsp?nome=<%= p.getNome() + " " + p.getCognome() %>&email=<%= p.getEmail() %>&codiceProfessore=<%= p.getCodiceProfessore() %>">
            Prenota ricevimento
          </a>

        </div>
      </div>
    </div>
    <% } %>
  </div>

  <div class="custom-btn-container">
    <a href="MyHome.jsp" class="btn btn-danger text-white" type="submit" style="width: 300px;">Torna alla landing page!</a>
  </div>
</div>

</body>

<jsp:include page="Footer.jsp" />

</html>
