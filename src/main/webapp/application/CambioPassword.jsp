<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<!-- Collegamento all'Header -->
<jsp:include page="/application/Header.jsp" />

<!DOCTYPE html>
<html lang="it">

<%
    String status = (String) session.getAttribute("status");
    if (status != null) {
        session.removeAttribute("status");
    }
%>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/controlloPass.css">
    <title>UniMeet - Cambio Password</title>
</head>

<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="registration-card">
                    <h2 class="h3 mb-3 font-weight-normal text-center">Ripristina password</h2>
                    <form action="<%= request.getContextPath() %>/ModificaPasswordServlet" method="post" accept-charset="UTF-8" id="form-reset">
                        <div class="form-group">
                            <label for="email">Indirizzo email</label>
                            <input type="email" id="email" name="email" class="form-control" placeholder="Indirizzo email" required autofocus>
                        </div>
                        
                        <div class="form-group">
                            <label for="domandaSicurezza">Domanda di sicurezza</label>
                            <select class="form-control" name="domanda" id="domandaSicurezza" required>
                                <option selected disabled>Scegli domanda di sicurezza</option>
                                <option>Qual è il nome del tuo primo animale domestico?</option>
                                <option>Qual è la tua città natale?</option>
                                <option>Qual è il cognome di tua madre da nubile?</option>
                                <option>Qual è il nome del tuo migliore amico?</option>
                                <option>Qual è il nome del tuo cantante preferito?</option>
                                <option>Qual era il tuo soprannome da piccolo?</option>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label for="risposta">Risposta</label>
                            <input type="text" class="form-control" name="risposta" id="risposta" placeholder="Risposta" required>
                        </div>
                        
                        
                        <div class="form-group">
                                <label for="inputPassword">Nuova Password</label>
                                <input type="password" name="newPassword" id="inputPassword" class="form-control" placeholder="Password" required>
                                <small id="passwordHelp" class="form-text text-muted">
                                    La password deve essere lunga almeno 8 caratteri, includere almeno una lettera maiuscola e un carattere speciale.
                                </small>
                                <ul id="passwordCriteria" style="list-style: none; padding: 0; margin-top: 10px;">
                                    <li id="length" class="invalid">Minimo 8 caratteri</li>
                                    <li id="uppercase" class="invalid">Almeno una lettera maiuscola</li>
                                    <li id="specialChar" class="invalid">Almeno un carattere speciale</li>
                                </ul>
                            </div>
                        
                        
                        <div class="custom-btn-container">
                                <button type="submit" id="submitBtn" class="btn btn-primary" disabled>Cambia password</button><br>
                                oppure<br>
                            <a href="Login.jsp" class="btn btn-danger text-white" type="submit">Torna al login!</a>
                            </div>
                        
                        <% if (status != null) { %>
                            <div class="alert alert-danger mt-3" role="alert">
                                <%= status %>
                            </div>
                        <% } %>
                    </form>
                </div>
            </div>
        </div>
    </div>
<script src="<%= request.getContextPath() %>/scripts/ControlloPass.js"></script>
    <% 
    String error = request.getParameter("error"); 
    if ("userNotFound".equals(error)) { %>
        <script>
            window.onload = function() {
                alert("Errore: Utente non trovato. Riprova.");
            };
        </script>
    <% } else if ("formErrore".equals(error)) { %>
        <script>
            window.onload = function() {
                alert("Errore: Errore nella compilazione del form.");
            };
        </script>
    <% } else if ("passwordCambiata".equals(error)) { %>
        <script>
            window.onload = function() {
                alert("Cambio password completato con successo!");
            };
        </script>
    <% } %>

    <jsp:include page="Footer.jsp" />
</body>

</html>
