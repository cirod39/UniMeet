document.getElementById('inputPassword').addEventListener('input', function () {
    const password = this.value;
    
    // Verifica dei criteri
    const lengthCheck = password.length >= 8;
    const uppercaseCheck = /[A-Z]/.test(password);
    const specialCharCheck = /[!@#$%^&*(),.?":{}|<>]/.test(password);

    // Elementi di feedback
    const lengthElement = document.getElementById('length');
    const uppercaseElement = document.getElementById('uppercase');
    const specialCharElement = document.getElementById('specialChar');
    const submitButton = document.getElementById('submitBtn');
    
    // Funzione per aggiornare lo stato della validità
    function updateFeedback(element, isValid) {
        if (isValid) {
            element.classList.remove('invalid');
            element.classList.add('valid');
        } else {
            element.classList.remove('valid');
            element.classList.add('invalid');
        }
    }

    // Aggiorna lo stato per ciascun criterio
    updateFeedback(lengthElement, lengthCheck);
    updateFeedback(uppercaseElement, uppercaseCheck);
    updateFeedback(specialCharElement, specialCharCheck);

    // Abilita/disabilita il bottone di invio in base alla validità
    if (lengthCheck && uppercaseCheck && specialCharCheck) {
        submitButton.disabled = false;
    } else {
        submitButton.disabled = true;
    }
});

// Prevenire l'invio del form se i criteri non sono soddisfatti
document.getElementById('registrationForm').addEventListener('submit', function (event) {
    const password = document.getElementById('inputPassword').value;
    
    const lengthCheck = password.length >= 8;
    const uppercaseCheck = /[A-Z]/.test(password);
    const specialCharCheck = /[!@#$%^&*(),.?":{}|<>]/.test(password);
    
    if (!(lengthCheck && uppercaseCheck && specialCharCheck)) {
        event.preventDefault(); // Impedisce l'invio del form se i criteri non sono soddisfatti
        alert("La password non soddisfa i criteri richiesti.");
    }
});

