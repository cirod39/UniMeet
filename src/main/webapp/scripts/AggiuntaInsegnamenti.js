function aggiungiInsegnamento() {
    const container = document.getElementById('insegnamenti-container');
    const insegnamentoDiv = document.createElement('div');
    insegnamentoDiv.className = 'insegnamento-item mb-3';

    const input = document.createElement('input');
    input.type = 'text';
    input.name = 'insegnamenti';
    input.className = 'form-control mb-2';
    input.placeholder = 'Inserisci un insegnamento';

    const buttonContainer = document.createElement('div');
    buttonContainer.className = 'btn-container mt-2';

    const addButton = document.createElement('button');
    addButton.type = 'button';
    addButton.className = 'btn btn-success btn-sm mr-2';
    addButton.textContent = 'Aggiungi';
    addButton.onclick = function() {
        aggiungiInsegnamento();
    };
    addButton.style.borderRadius = "12px";

    const removeButton = document.createElement('button');
    removeButton.type = 'button';
    removeButton.className = 'btn btn-danger btn-sm';
    removeButton.textContent = 'Rimuovi';
    removeButton.onclick = function() {
        rimuoviInsegnamento(removeButton);
    };
    removeButton.style.borderRadius = "12px";

    buttonContainer.appendChild(addButton);
    buttonContainer.appendChild(removeButton);

    insegnamentoDiv.appendChild(input);
    insegnamentoDiv.appendChild(buttonContainer);

    container.appendChild(insegnamentoDiv);
}

function rimuoviInsegnamento(button) {
    const insegnamentoDiv = button.parentElement.parentElement;
    insegnamentoDiv.remove();
}
