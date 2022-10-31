$(document).ready(function () {
    $("#imageCal1").click(function () {
        $('#fechaDesde').datepicker("show");
    });
    $("#imageCal2").click(function () {
        $("#fechaHasta").datepicker("show");
    });
    var msgAviso = $("#msgAviso").val();
    if (msgAviso != "") {
        alert(msgAviso);
        window.location.href = "relacionUsuarioDelegado.do";
    }
//	if($('#estado').val() == "I"){
//		$('#bntSubmit').hide();
//		$('#delegadoUser').attr('disabled','true');
//		$('#informe').attr('disabled','true');
//		$('#accion').attr('disabled','true');
//		$('#fechaDesde').attr('disabled','true');
//		$('#fechaHasta').attr('disabled','true');
//	}
    //	
    $.validator.addMethod("valueNotEquals", function (value, element, arg) {
        return arg != value;
    }, "");
    $("#ambDelegacionForm").validate({
        rules: {
            delegadoUser: {
                valueNotEquals: ""
            },
            delegadoCentroCostos: {
                valueNotEquals: ""
            },
            delegadoSector: {
                valueNotEquals: ""
            },
            feDesde: {
                valueNotEquals: ""
            },
            feHasta: {
                valueNotEquals: ""
            }
        },
        messages: {
            delegadoUser: " *",
            feDesde: "",
            feHasta: ""
        },
        submitHandler: function (form) {
            $('#msgModOk').hide();
            $('#msgError').hide();
            if (validarFecha()) {
                form.submit();
            }
        }
    });
});

function checkUser() {

    var u = $('#delegadoUser').val();
    $('#delegadoUser').val(u.toUpperCase());
    u = $('#delegadoUser').val();
    $.ajax({
        url: "chequearUsuario.do?cod_user=" + u,
        type: "POST",
        dataType: "json",
        success: function (data) {

            if (data.error == "") {

                $('#delegadoNombre').val(data.delegadoNombre);
                $('#delegadoNombre').css("color", "black");
                $('#delegadoCentroCosto').val(data.delegadoCentroCosto);
                $('#delegadoSector').val(data.delegadoSector);
            } else {

                $('#delegadoNombre').val(data.error);
                $('#delegadoNombre').css("color", "red");
                // $('#delegadoNombre').css("font-weight", "bold");
                $('#delegadoCentroCosto').val("");
                $('#delegadoSector').val("");
            }

        },
        error: function (xhr, textStatus, error) {
            alert("Error al ejecutar la sentencia chequearUsuario: " + error);
        }
    });

}

function validarFecha() {

    var fDesdeRend = new Date();
    var fecha = $('#fechaDesde').val().split("/");
    fDesdeRend.setFullYear(fecha[2], fecha[1] - 1, fecha[0]);

    var fHastaRend = new Date();
    fecha = $('#fechaHasta').val().split("/");
    fHastaRend.setFullYear(fecha[2], fecha[1] - 1, fecha[0]);

    var fHoy = new Date();
    fecha = $('#fechaHoy').val().split("/");
    fHoy.setFullYear(fecha[2], fecha[1] - 1, fecha[0]);

    if (fHastaRend < fDesdeRend) {
        // alert("fecha invalida, la fecha debe estar entre: "+fechaD+" y
        // "+fechaH);
        // $('#saveButton').disable;
        $('#fechaDesdeValidate').show();
        // $('input[type="submit"]').prop('disabled', true);
        return false;

    } else {

        if (fHastaRend > fHoy || fDesdeRend > fHoy) {
            $('#saveButton').disable;
            $('#fechaHoyValidate').show();
            $('input[type="submit"]').prop('disabled', true);
            return false;

        } else {
            // $('#saveButton').enable;
            $('#fechaDesdeValidate').hide();
            $('#fechaHoyValidate').hide();
            // $('input[type="submit"]').prop('disabled', false);
            return true;
        }
    }
}

function checkEstado() {

    var fHastaRend = new Date();
    var fecha = $('#fechaHasta').val().split("/");
    fHastaRend.setFullYear(fecha[2], fecha[1] - 1, fecha[0]);

    var fHoy = new Date();

    if (fHastaRend < fHoy) {
        $('#estado').val("I");
    } else {
        $('#estado').val("A");
    }

}


