jQuery(document).ready(function () {
    $('#checkParametros').show();

    $("#imageCal2").click(function () {
        $('#fechaDesde').datepicker("show");
    });
    $("#imageCal1").click(function () {
        $("#fechaHasta").datepicker("show");
    });

    setFormValidate();
});

function checkCodigo() {
    var u = $('#desMotivo').val();
    var v = $("input[name=motivoUsuario]:checked").val();
    $('#desMotivo').val(u.toUpperCase());
    u = $('#desMotivo').val();

    $.ajax({
        url: "chequearCodigo.do?desMotivo=" + u + "&motivoUsuario=" + v,
        type: "POST",
        dataType: "json",
        success: function (data) {
            if (data.error == "") {
                $('#descripcionCodigo').val(data.descripcion);
                $('#descripcionCodigo').css("color", "black");
            } else {
                $('#descripcionCodigo').val(data.error);
                $('#descripcionCodigo').css("color", "red");
            }
        },
        error: function (xhr, textStatus, error) {
            console.log("Error al ejecutar la sentencia chequearUsuario: " + error);
        }
    });
}

function setFormValidate() {
    jQuery.validator.addMethod("dateDMY", function (value, element) {
        try {
            jQuery.datepicker.parseDate('dd/mm/yy', value);
            $("#errorFechas").html("");
            return true;
        } catch (e) {
            $("#errorFechas").html("La fechas deben tener formato DD/MM/AAAA");
            return false;
        }
    }, "");

    jQuery.validator.addMethod("dateAfterToday", function (value, element) {
        try {
            var validacion = jQuery.datepicker.parseDate('dd/mm/yy', value) > new Date();

            if (validacion)
                $("#errorFechas").html("");
            else
                $("#errorFechas").html("La fecha Desde debe ser mayor o igual a la de hoy");

            return validacion;
        } catch (e) {
            return false;
        }
    }, "");

    jQuery.validator.addMethod("dateAfter", function (value, element, fromDate) {
        try {
            var startDate = $("#" + fromDate).val();
            var validacion = jQuery.datepicker.parseDate('dd/mm/yy', startDate) <= jQuery.datepicker.parseDate('dd/mm/yy', value);

            if (validacion)
                $("#errorFechas").html("");
            else
                $("#errorFechas").html("La fecha Hasta debe ser mayor o igual a la fecha Desde");

            return validacion;
        } catch (e) {
            return false;
        }
    }, "");

    $("#parametrosExceptuadosForm").validate({
        rules: {
            motivoUsuario: {required: true},
            hasta: {required: true, dateDMY: true, dateAfter: 'fechaDesde'},
            desde: {required: true},
            estado: {required: true},
            desMotivo: {required: true}
        },
        messages: {
            motivoUsuario: "* ",
            hasta: " *",
            desde: " *",
            estado: "*",
            desMotivo: "*"
        }
    });
}
function confirmEliminarExceptuado() {
    var motivoUsuario = $("input[name=motivoUsuario]:checked").val();
    if (confirm("\u00bfEst\u00e1 seguro que quiere eliminar el " + motivoUsuario + " " + $("#desMotivo").val() + "?"))
        $("#parametrosExceptuadosForm").submit();
}