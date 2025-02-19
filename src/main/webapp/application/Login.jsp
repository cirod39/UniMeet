<%--
  Created by IntelliJ IDEA.
  User: cirodanzilli
  Date: 03/01/25
  Time: 10:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<%
    String status = (String) session.getAttribute("status");
    if (status != null) {
        session.removeAttribute("status");
    }
%>
<!-- QUI STO IMPOSTANDO IL RIFERIMENTO ALL'HEADER -->
<jsp:include page="/application/Header.jsp" />

<!doctype html>
<html lang="it" class="h-100">

<% if(session.getAttribute("status") != null) { %>
<div class="alert alert-warning">
    <%= session.getAttribute("status") %>
</div>
<% session.removeAttribute("status"); %>
<% } %>

<body>

<!-- IN QUESTO PUNTO STO INIZIANDO A FORMARE IL CONTENUTO DELLA PAGINA (SOLO BODY, HEADER E FOOTER SONO NEI LORO FILES -->
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="registration-card">
                <h1 class="h3 mb-3 font-weight-normal text-center">Accedi</h1>
                <form action="<%= request.getContextPath() %>/LoginServlet" method="post">
                    <div class="form-group">
                        <label for="email">Indirizzo email</label>
                        <input type="email" id="email" name="email" class="form-control" placeholder="Indirizzo email" required autofocus>
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
                    </div>
                    <div class="form-group form-check">
                        <input type="checkbox" class="form-check-input" id="showPassword" onclick="togglePasswordVisibility()">
                        <label class="form-check-label" for="showPassword">Mostra Password</label>
                    </div>
                    <div class="custom-btn-container">
                        <button class="btn btn-primary" type="submit">Entra!</button>
                        <a href="Registrazione.jsp" class="btn btn-danger text-white" type="submit">Sei nuovo? Registrati!</a><br>
                        oppure<br>
                        <a href="MyHome.jsp" class="btn btn-danger text-white" type="submit">Torna alla landing page!</a>
                    </div>
                    <% if (status != null) { %>
                    <div class="alert alert-danger mt-3" role="alert">
                        <%= status %>
                    </div>
                    <% } %>
                </form>
            </div>
            <div class="text-center mt-3 fw-bold">
                Password dimenticata? Clicca <a href="CambioPassword.jsp" class="text-danger text-decoration-none">qui</a>
            </div>
        </div>
    </div>
</div>

<!-- QUESTO E' UN PICCOLO SCRIPT PER MOSTRARE LA PASSWORD -->
<script src="<%= request.getContextPath() %>/scripts/Login.js"></script>

<!-- QUI FINISCE IL CONTENUTO DELLA PAGINA (BODY) -->
</body>

<!-- QUI HO INSERITO IL RIFERIMENTO AL FOOTER -->
<jsp:include page="Footer.jsp" />
</html>
