<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="model.Studente, model.Professore" %>

<!doctype html>
<html lang="it">
<head>
    <title>Header - UniMeet</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/ajax.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/stile.css">
    <link rel="icon" href="<%= request.getContextPath() %>/images/favicon.ico"
          type="image/x-icon">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/subMenuProfessore.css">
</head>

<body style="margin: 0; padding: 0; position: relative;">
<div style="position: fixed; top: 0; left: 0; width: 100%; height: 100%;
                z-index: -2; background-image: url('../images/sfondo.webp');
                background-size: cover; background-position: center;
                background-attachment: fixed;">
</div>
<div style="position: fixed; top: 0; left: 0; width: 100%; height: 100%;
                z-index: -1; background-color: rgba(255, 255, 255, 0.8);">
</div>

<div style="position: relative; z-index: 1;">
    <header class="py-4">
        <nav class="navbar navbar-custom">
            <a href="MyHome.jsp">
                <img src="../images/logo.png" class="logo" alt="UniMeet Logo">
            </a>

            <%
                HttpSession sessione = (HttpSession) request.getSession(false);
                String role = (sessione != null)
                        ? (String) sessione.getAttribute("role") : null;
                String email = null;

                if (sessione != null) {
                    if ("studente".equals(role)) {
                        Studente studente = (Studente) sessione.getAttribute("utente");
                        if (studente != null) {
                            email = studente.getEmail();
                        }
                    } else if ("professore".equals(role)) {
                        Professore professore = (Professore) sessione.getAttribute("utente");
                        if (professore != null) {
                            email = professore.getEmail();
                        }
                    }
                }
            %>

            <% if (role == null || email == null) { %>
            <a class="btn btn-primary" href="Login.jsp">Accedi</a>
            <a class="btn btn-primary ml-2" href="Registrazione.jsp">Registrati</a>
            <% } else if ("studente".equals(role)) { %>
            <div class="dropdown">
                <a class="btn btn-primary dropdown-toggle" href="#" id="studentMenu"
                   role="button" data-toggle="dropdown" aria-haspopup="true"
                   aria-expanded="false">
                    Menu studente
                </a>
                <div class="dropdown-menu" aria-labelledby="studentMenu">
                    <a class="dropdown-item" href="SelezionaProfessore.jsp">Prenota un ricevimento</a>
                    <a class="dropdown-item" href="RiepilogoRicevimenti.jsp">
                        Riepilogo ricevimenti
                    </a>
                    <form action="../LogoutServlet" method="POST" style="display:inline;">
                        <button type="submit" class="dropdown-item">Logout</button>
                    </form>
                </div>
            </div>
            <a class="btn btn-primary ml-2" href="#"><%= email %></a>
            <% } else if ("professore".equals(role)) { %>
            <div class="right-aligned">
                <a class="btn btn-success ml-2" href="#"><%= email %></a>
                <div class="dropdown">
                    <a class="btn btn-success dropdown-toggle" href="#" id="professorMenu"
                       role="button" data-toggle="dropdown" aria-haspopup="true"
                       aria-expanded="false">
                        Menu professore
                    </a>
                    <div class="dropdown-menu" aria-labelledby="professorMenu">

                        <!-- NUOVO HEADER GESTISCI RICEV-->
                        <a class="dropdown-item" href="#" id="gestisciRicevimenti">Gestisci ricevimenti</a>
                        <div id="submenuRicevimenti" class="custom-submenu" style="display: none;">
                            <a class="dropdown-item" href="">Aggiungi ricevimento</a>
                            <a class="dropdown-item" href="">Modifica ricevimenti</a>
                        </div>


                        <a class="dropdown-item" href="">Riepilogo ricevimenti</a>
                        <a class="dropdown-item" href="">Ricevimenti in programma</a>
                        <form action="../LogoutServlet" method="POST" style="display:inline;">
                            <button type="submit" class="dropdown-item">Logout</button>
                        </form>
                    </div>
                </div>
            </div>
            <% } %>


            <% if("studente".equals(role) || role==null) {%>
            <form id="searchForm" action="Risultati.jsp" method="post"
                  class="form-inline ml-auto" style="position: relative;">
                <input class="form-control mr-sm-2"
                       type="search"
                       name="ajax-search"
                       placeholder="Cerca"
                       aria-label="Search"
                       autocomplete="off"
                       style="border: 2px solid #000; border-radius: 8px;">
                <button class="btn btn-outline-dark" type="submit">Cerca</button>
                <div id="searchSuggestions" class="list-group"></div>
            </form>
            <% } %>
        </nav>
        <div class="headerLine"></div>
    </header>
    <%
        String status = (String) session.getAttribute("status");
        if (status != null) {
    %>
    <div
            id="messaggioAlert"
            class="alert alert-info text-center mt-3"
            role="alert"
            style="display: inline-block; border-radius: 15px; padding: 10px 20px; margin: 0 auto; text-align: center;">
        <%= status %>
    </div>
    <%
            session.removeAttribute("status");
        }
    %>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.bundle.min.js"></script>

<script src="${pageContext.request.contextPath}/scripts/SubMenuProfessore.js"></script>

<script>
    var contextPath = '<%= request.getContextPath() %>';
</script>
<script src="${pageContext.request.contextPath}/scripts/Header.js"></script>
<script src="${pageContext.request.contextPath}/scripts/Ajax.js"></script>
</body>
</html>
