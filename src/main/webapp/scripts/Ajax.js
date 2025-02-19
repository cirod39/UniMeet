$(document).ready(function() {
    const $searchInput = $('input[name="ajax-search"]');
    const $suggestions = $('#searchSuggestions');
    const $searchForm = $('#searchForm');

    // Ascolta sia il 'keyup' sia l'evento 'input' (per gestire la X del browser)
    $searchInput.on('input keyup', function() {
        const query = $(this).val().trim();

        // Nascondi e svuota la tendina se il campo è vuoto
        if (!query) {
            $suggestions.empty().css('display', 'none');
            return;
        }

        // Esegui la ricerca AJAX solo se c'è testo
        $.ajax({
            url: contextPath + '/AjaxSearch',
            method: 'GET',
            data: { 'ajax-search': query },
            dataType: 'json',
            success: function(response) {
                $suggestions.empty();

                if (response.length === 0) {
                    $suggestions.css('display', 'none'); // Nasconde se non ci sono risultati
                    return;
                }

                // Popola la tendina con i risultati
                $.each(response, function(index, prof) {
                    const suggestion = $('<a>')
                        .addClass('list-group-item')
                        .css('cursor', 'pointer')
                        .append(
                            $('<strong>').text(prof.nome + ' ' + prof.cognome + ' '),
                            $('<span>').text(prof.email)
                        )
                        .on('click', function() {
                            $searchInput.val(prof.nome + ' ' + prof.cognome);
                            $suggestions.empty().css('display', 'none'); // Nasconde la tendina
                            $searchForm.submit();
                        });
                    $suggestions.append(suggestion);
                });

                $suggestions.css('display', 'block'); // Mostra la tendina
            },
            error: function() {
                $suggestions.empty().css('display', 'none'); // Nasconde la tendina in caso di errore
            }
        });
    });
});
