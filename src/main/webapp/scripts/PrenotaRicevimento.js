$(document).ready(function () {
    window.addEventListener("pageshow", function (event) {
        if (event.persisted) {
            window.location.reload();
        }
    });

    const giorniDisponibili = config.giorniDisponibili;
    const contextPath = config.contextPath;
    const codiceProfessore = config.codiceProfessore;

    console.log("Giorni disponibili:", giorniDisponibili);

    const mappaGiorni = {
        "lunedì": 1,
        "martedì": 2,
        "mercoledì": 3,
        "giovedì": 4,
        "venerdì": 5,
        "sabato": 6,
        "domenica": 0
    };

    let intervalloOrari = [];

    $('#timePicker').prop('disabled', true);
    $('#prenotaBtn').prop('disabled', true);

    $('#datePicker').datepicker({
        format: 'dd/mm/yyyy',
        autoclose: true,
        todayHighlight: true,
        language: 'it',
        beforeShowDay: function (date) {
            const giornoSettimana = date.getDay();
            const oggi = new Date();
            const isOggi = date.getDate() === oggi.getDate() && date.getMonth() === oggi.getMonth() && date.getFullYear() === oggi.getFullYear();

            if (giorniDisponibili.some(item => mappaGiorni[item.giorno.toLowerCase()] === giornoSettimana) && (isOggi || date > oggi)) {
                return {
                    enabled: true,
                    classes: 'giorno-disponibile',
                    tooltip: 'Giorno disponibile'
                };
            } else {
                return {
                    enabled: false,
                    classes: '',
                    tooltip: 'Giorno non disponibile'
                };
            }
        },
        startDate: new Date()
    }).on('change', function () {
        const giornoSelezionato = $('#datePicker').val();
        const giornoSettimana = (new Date(giornoSelezionato.split('/').reverse().join('-'))).getDay();

        if (!giornoSelezionato) {
            console.error("Nessun giorno selezionato.");
            $('#timePicker').prop('disabled', true);
            $('#prenotaBtn').prop('disabled', true);
            $('#noAvailabilityMessage').hide();
            return;
        }

        const orariGiorno = giorniDisponibili
            .filter(item => mappaGiorni[item.giorno.toLowerCase()] === giornoSettimana)
            .map(item => item.ora);

        if (orariGiorno.length) {
            const minTime = orariGiorno[0];
            const [minHour, minMinute] = minTime.split(":").map(Number);
            const minDate = new Date(1970, 0, 1, minHour, minMinute);
            const maxDate = new Date(minDate.getTime() + 90 * 60 * 1000);

            intervalloOrari = [];
            for (let i = minDate; i <= maxDate; i = new Date(i.getTime() + 30 * 60 * 1000)) {
                const oraCorrente = i.toTimeString().slice(0, 5);
                if (oraCorrente <= "17:30") {
                    intervalloOrari.push(oraCorrente);
                }
            }


            console.log(`Intervallo orari per ${giornoSelezionato}:`, intervalloOrari);

            $.ajax({
                url: contextPath + "/DisponibilitaOrariServlet",
                method: 'GET',
                data: {
                    giorno: giornoSelezionato,
                    codiceProfessore: codiceProfessore
                },
                success: function (giorniOreOccupati) {
                    console.log(`Orari occupati ricevuti per ${giornoSelezionato}:`, giorniOreOccupati);

                    if (!Array.isArray(giorniOreOccupati)) {
                        console.error("Errore: La servlet non ha restituito un array di orari occupati.", giorniOreOccupati);
                        return;
                    }

                    const orariOccupati = giorniOreOccupati.map(item => item.ora.trim());

                    orariOccupati.forEach(orario => {
                        const index = intervalloOrari.indexOf(orario);
                        if (index !== -1) {
                            intervalloOrari.splice(index, 1);
                        }
                    });

                    console.log(`Intervallo orari aggiornato:`, intervalloOrari);

                    if (intervalloOrari.length > 0) {
                        $('#timePicker').empty();

                        intervalloOrari.forEach(function (ora) {
                            $('#timePicker').append($('<option>', {
                                value: ora,
                                text: ora
                            }));
                        });

                        $('#timePicker').prop('disabled', false);
                        $('#noAvailabilityMessage').hide();
                        $('#prenotaBtn').prop('disabled', false);
                    } else {
                        $('#timePicker').prop('disabled', true);
                        $('#noAvailabilityMessage').show();
                        $('#prenotaBtn').prop('disabled', true);
                        console.log("Nessun orario disponibile per il giorno selezionato.");
                    }
                },
                error: function (xhr, status, error) {
                    console.error("Errore nel recupero degli orari occupati:", status, error);
                }
            });
        } else {
            $('#timePicker').prop('disabled', true);
            $('#noAvailabilityMessage').show();
            $('#prenotaBtn').prop('disabled', true);
            console.log(`Nessun orario disponibile per il giorno selezionato (${giornoSelezionato})`);
        }
    });

    const style = document.createElement('style');
    style.innerHTML = `
        .giorno-disponibile {
            background-color: #b2ebf2 !important;
            color: black !important;
        }
    `;
    document.head.appendChild(style);
});
