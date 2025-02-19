<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<!-- Collegamento all'Header -->
<jsp:include page="/application/Header.jsp" />

<!doctype html>
<html lang="it">
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/controlloPass.css">
</head>
    <title>Registrazione Professore - UniMeet</title>
    <body>
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="registration-card">
                        <h2 class="text-center mb-4">Registrazione Professore</h2>
                        <form action="<%= request.getContextPath() %>/RegistrazioneServlet" method="post" id="registrationForm" onsubmit="return validateForm()">

                            <div class="form-group">
                                <label for="email">E-Mail</label>
                                <input type="text" class="form-control" name="email" id="email" placeholder="Inserisci l'E-MAIL" required>
                            </div>

                            <div class="form-group">
                                <label for="inputPassword">Password</label>
                                <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
                                <small id="passwordHelp" class="form-text text-muted">
                                    La password deve essere lunga almeno 8 caratteri, includere almeno una lettera maiuscola e un carattere speciale.
                                </small>
                                <ul id="passwordCriteria" style="list-style: none; padding: 0; margin-top: 10px;">
                                    <li id="length" class="invalid">Minimo 8 caratteri</li>
                                    <li id="uppercase" class="invalid">Almeno una lettera maiuscola</li>
                                    <li id="specialChar" class="invalid">Almeno un carattere speciale</li>
                                </ul>
                            </div>

                            <div class="form-group">
                                <label for="inputConfirmPassword">Conferma Password</label>
                                <input type="password" name="confirmPassword" id="inputConfirmPassword" class="form-control" placeholder="Conferma Password" required>
                                <small id="passwordError" class="text-danger" style="display:none;">Le password non corrispondono!</small>
                            </div>

                            <div class="form-group form-check">
                                <input type="checkbox" class="form-check-input" id="showPassword" onclick="togglePasswordVisibility()">
                                <label class="form-check-label" for="showPassword">Mostra Password</label>
                            </div>

                            <div class="form-group">
                                <label for="nome">Nome</label>
                                <input type="text" class="form-control" name="nome" id="nome" placeholder="Inserisci il tuo nome" required>
                            </div>

                            <div class="form-group">
                                <label for="cognome">Cognome</label>
                                <input type="text" class="form-control" name="cognome" id="cognome" placeholder="Inserisci il tuo cognome" required>
                            </div>

                            <div class="form-group">
                                <label for="codice">Codice Professore</label>
                                <input type="text" class="form-control" name="codiceProfessore" id="codice" placeholder="Inserisci il codice professore" required>
                            </div>

                            <div class="form-group">
                                <label for="insegnamento">Insegnamenti</label>
                                <div id="insegnamenti-container">
                                    <div class="insegnamento-item">
                                        <input type="text" class="form-control mb-2" name="insegnamenti" placeholder="Inserisci un insegnamento" required>
                                        <div class="btn-container mt-2 mb-3">
                                            <button type="button" class="btn btn-success btn-sm" onclick="aggiungiInsegnamento()" style="border-radius: 12px;">Aggiungi</button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="ufficio">Ufficio</label>
                                <input type="text" class="form-control" name="ufficio" id="ufficio" placeholder="Inserisci l'ufficio" required>
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

                            <div class="custom-btn-container">
                                <button type="submit" id="submitBtn" class="btn btn-primary" disabled>Registrati!</button>
                                <a href="Registrazione.jsp" class="btn btn-danger" role="button">Torna alle opzioni</a>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </div>

<script src="<%= request.getContextPath() %>/scripts/ControlloPass.js"></script>
<script src="<%= request.getContextPath() %>/scripts/AggiuntaInsegnamenti.js"></script>
<script src="<%= request.getContextPath() %>/scripts/ValidazionePass.js"></script>
<script src="<%= request.getContextPath() %>/scripts/RegistrazioneProfessore.js"></script>

</body>
<jsp:include page="Footer.jsp" />
</html>
