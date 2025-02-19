<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<!doctype html>
<html lang="it">
<head>
  <title>Seleziona professore - UniMeet</title>
  <jsp:include page="/application/Header.jsp" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/selezionaProfessore.css">
</head>
<body>
<h1 class="h3 mb-3 font-weight-normal text-center">Prenota un ricevimento</h1>
<div class="centro-ricerca-pagina">
  <form class="d-flex" role="search" action="Risultati.jsp" method="get">
    <input class="form-ricerca me-2" type="search" placeholder="Cerca un professore" aria-label="Cerca" name="ajax-search">
    <button class="btn-ricerca" type="submit">Cerca</button>
  </form>
</div>
<div class="text-center mt-3">
  <a href="MyHome.jsp" class="btn btn-danger text-white">
    Torna alla landing page!
  </a>
</div>
</body>
<footer>
  <jsp:include page="Footer.jsp" />
</footer>
</html>
