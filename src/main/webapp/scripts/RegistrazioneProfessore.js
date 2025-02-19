let emailField = document.getElementById('email');
    let defaultDomain = '@professori.unisa.it';

    // Funzione per aggiungere il dominio se necessario
    function ensureDomain(value) {
        return value.includes('@') ? value.split('@')[0] + defaultDomain : value + defaultDomain;
    }

    // Aggiungi il dominio quando l'utente scrive
    emailField.addEventListener('input', function() {
        emailField.value = ensureDomain(emailField.value);
        emailField.setSelectionRange(emailField.value.indexOf('@'), emailField.value.indexOf('@'));
    });

    // Gestisci i tasti speciali (invio, spazio, @)
    emailField.addEventListener('keydown', function(event) {
        if (['Enter', ' ', '@'].includes(event.key)) {
            emailField.value = ensureDomain(emailField.value);
            emailField.setSelectionRange(emailField.value.indexOf('@'), emailField.value.indexOf('@'));
            event.preventDefault();  // Previeni l'azione predefinita
        }
    });