$(document).ready(function () {
    $('#gestisciRicevimenti').on('click', function (e) {
        e.preventDefault();

        $('#submenuRicevimenti').toggle();
        $('.dropdown-menu').addClass('show');
        $(this).toggleClass('active');
    });

    $('.dropdown-menu, #submenuRicevimenti').on('click', function (e) {
        e.stopPropagation();
    });

    $(document).click(function (event) {
        if (!$(event.target).closest('.dropdown-menu, #submenuRicevimenti').length) {
            $('#submenuRicevimenti').hide();
            $('#gestisciRicevimenti').removeClass('active');
        }
    });
});