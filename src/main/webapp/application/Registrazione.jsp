<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<!-- Collegamento all'Header -->
<jsp:include page="/application/Header.jsp" />

<!doctype html>
<html lang="it">

<title>Registrazione - UniMeet</title>

<body>


<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="registration-card">
                <h2 class="text-center mb-4">Registrati</h2>
                <div class="custom-btn-container">
                    <a href="RegistrazioneStudente.jsp" class="btn btn-primary">Studente</a>
                    <a href="RegistrazioneProfessore.jsp" class="btn btn-primary">Professore</a>
                </div>
                <div class="custom-btn-container">
                        oppure<br>
                        <a href="MyHome.jsp" class="btn btn-danger" role="button">Torna alla landing page!</a>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="Footer.jsp" />


</body>
</html>
