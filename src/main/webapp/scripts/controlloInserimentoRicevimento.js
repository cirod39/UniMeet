// Funzione per validare il form prima dell'invio
function validateForm() {
    var giorno = document.getElementById('giorno').value;
    var ora = document.getElementById('ora').value;
    if (giorno == "" || ora == "") {
        alert("Si prega di selezionare un giorno e un orario.");
        return false;
    }
    return true;
}

// Funzione per abilitare/disabilitare il bottone "Aggiungi" in base alla selezione
function enableAddButton() {
    var giorno = document.getElementById('giorno').value;
    var ora = document.getElementById('ora').value;
    var addBtn = document.getElementById('addBtn');

    // Se entrambi sono selezionati, abilita il bottone
    if (giorno != "" && ora != "") {
        addBtn.disabled = false;
    } else {
        addBtn.disabled = true;
    }
}

// Funzione per far scomparire il messaggio di esito dopo 5 secondi
function hideMessage() {
    setTimeout(function() {
        $('.alert').fadeOut();
    }, 5000);
}

// Esegui la funzione di scomparsa solo se il messaggio è presente
$(document).ready(function() {
    var esito = "<%= esito %>";
    if (esito != "") {
        hideMessage();
    }
});