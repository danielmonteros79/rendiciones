
function validarFecha(event) {
    var fDesdeRend = new Date();
    var fecha = $('#feDesde').val().split("/");
    fDesdeRend.setFullYear(fecha[2], fecha[1] - 1, fecha[0]);

    var fHastaRend = new Date();
    fecha = $('#feHasta').val().split("/");
    fHastaRend.setFullYear(fecha[2], fecha[1] - 1, fecha[0]);

    if (fHastaRend < fDesdeRend) {
        //alert("fecha invalida, la fecha debe estar entre: "+fechaD+" y "+fechaH);
        $('#saveButton').disable;
        $('#fechaDesdeValidate').show();
        $('input[type="submit"]').prop('disabled', true);
        return false;

    } else {
        $('#saveButton').enable;
        $('#fechaDesdeValidate').hide();
        $('input[type="submit"]').prop('disabled', false);
        return true;
    }
}