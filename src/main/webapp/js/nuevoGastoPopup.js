$(document).ready(function () {
    window.resizeTo(750, 450);
    $.validator.addMethod("costosDestino", function (value, element) {
        $("#valorInvalido").hide();
        try {
            if ($("#costosDestino").val() == "9999" || $("#costosDestino").val() == "0000") {
                $("#valorInvalido").show();
                return false;
            }
            return true;
        } catch (e) {
            return false;
        }
    }, "");

    $.validator.addMethod("valueNotEquals", function (value, element, arg) {
        return arg != value;
    }, "");

    $("#NuevoGastoPopUpSave").validate({
        rules: {
            costosDestino: {
                valueNotEquals: "",
                costosDestino: true
            }
        },
        messages: {
            costosDestino: {valueNotEquals: " *"},
        }
    });

    checkBimon();
    checkCDestino();
});

function checkCDestino() {
    if ($("#costosDestino").val() != "0000" && $("#costosDestino").val() != "9999") {
        $("#costosDestino").attr('readonly', true);
    } else if ($("#costosDestino").val() == "0000") {
        $("#costosDestino").attr('readonly', true);
    } else if ($("#costosDestino").val() == "9999") {
        $("#costosDestino").attr('readonly', false);
        $("#costosDestino").val("");
    }
}

function checkBimon() {
    var bimon = $("#comboTipoGasto").val().substring(59, 60);

    if (!$('#cuponesCheck').is(':checked')) {
        if (bimon == "N") {
            $("#comboMoneda").attr('disabled', true);
            $("#comboMoneda").val("ARS ");
        } else {
            $("#comboMoneda").attr('disabled', false);
        }
    }
}

function selectTipoGasto() {
    $.ajax({
        url: "NuevoGastoPopUp.do?accion=selectTipoGasto",
        type: "POST",
        data: "codTipoGasto=" + $("#comboTipoGasto").val().substring(0, 4),
        dataType: "json",
        success: function (data) {
            $("#comboComprobante").empty();
            $.each(data, function (index) {
                $("#comboComprobante").append("<option value=" + data[index].codigo + ">" + data[index].descripcion + "</option>");
            });
            check();
        },
        error: function (data) {
            console.log(data);
        }
    });
}

function numericOnly(e) {
    var code = e.charCode || e.keyCode;
    if (e.keyCode == 46 && e.charCode == 0)
        return;
    else if ($.inArray(code, [8, 9, 13]) !== -1 || (code >= 35 && code <= 40))
        return;
    if (!(code >= 48 && code <= 57))
        return false;
}