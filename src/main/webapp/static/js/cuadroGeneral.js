

jQuery(document).ready(function() {
	$('#checkCuadro').show();
	$('.nav-reporteria').addClass('active');
	selectOpcion();
	selectGlg();
	setFormValidate();
	
	$('#codMotivo').chosen();
	$('#codGlg').chosen();
	$('#opcion').chosen();

	
});

function filtrar() {
	$("#cuadroGeneralFiltro").attr('action', 'cuadroGeneralFiltro.do');
	$('#modalLoading').modal('show');
	$("#cuadroGeneralFiltro").submit()

}


function resetForm() {
	$("#opcion").val("01");
	selectOpcion();
	selectGlg();

	$(".error").html("");
	$(".message").html("");
	$("#montoDesde").val("");
	$("#montoHasta").val("");
	$("#glg").val("");
	$("#motivo").val("");
	$("#codGlg").val("");
	$("#codMotivo").val("");
	$("#codEstado").val("");
	$("#usuario").val("");
	$('input[type=checkbox]').prop('checked', false);
	$("#cuadroGeneralFiltro").attr('action', 'cuadroGeneralFiltro.do');

}

function selectOpcion() {
	var opcion = $("#opcion").val();
	
	if (opcion == "01") {
		$("#fechaDesde").datepicker().datepicker("setDate", new Date()).attr('readonly', true);
		$("#fechaHasta").datepicker().datepicker("setDate", new Date()).attr('readonly', true);
		
	} else {
		$("#fechaDesde").attr('readonly', false);
		$("#fechaHasta").attr('readonly', false);
	}
}

function detalle(codEstado) {
	$("#codEstado").val(codEstado);
	$("#cuadroGeneralFiltro").attr('action', 'FiltroCuadroDetallado.do');
	$("#cuadroGeneralFiltro").submit();
}

function setFormValidate() {
	jQuery.validator.addMethod("importe", function(value, element) {
		try {
			$("#errorMonto").html("");
			
			var montoDesde = $('#montoDesde').val();
			var montoHasta = $('#montoHasta').val();
			var indexDecimalDesde = $("#montoDesde").val().indexOf(',');
			var indexDecimalHasta = $("#montoHasta").val().indexOf(',');
			
			if (montoDesde.substring(0, indexDecimalDesde == -1 ? montoDesde.length : indexDecimalDesde).length > 13) {
				$("#errorMonto").html("El monto no puede tener m\u00e1s de 13 enteros.");
				return false;
			} else if (montoHasta.substring(0, indexDecimalHasta == -1 ? montoHasta.length : indexDecimalHasta).length > 13) {
				$("#errorMonto").html("El monto no puede tener m\u00e1s de 13 enteros.");
				return false;
			} else if (indexDecimalDesde != -1 && montoDesde.substring(indexDecimalDesde).length > 3) {
				$("#errorMonto").html("El monto no puede tener m\u00e1s de 2 decimales.");
				return false;
			} else if (indexDecimalHasta != -1 && montoHasta.substring(indexDecimalHasta).length > 3) {
				$("#errorMonto").html("El monto no puede tener m\u00e1s de 2 decimales.");
				return false;
			}	else if (parseFloat(montoDesde.replace(",",".")) > parseFloat(montoHasta.replace(",","."))) {
				$("#errorMonto").html("Monto Desde no puede ser mayor al Monto Hasta");
				return false;
			}
			
	        return true;
		} catch(e) {
			return false;
		}
    }, "");
	
	jQuery.validator.addMethod("dateDMY", function(value, element) {
		try {
			jQuery.datepicker.parseDate( 'dd/mm/yy', value);
        	$("#errorFechas").html("");
			return true;
		} catch(e) {
        	$("#errorFechas").html("La fechas deben tener formato DD/MM/AAAA");
        	return false;
        }
    }, "");
	
	jQuery.validator.addMethod("dateAfterToday", function(value, element) {
		try {
            var validacion = jQuery.datepicker.parseDate( 'dd/mm/yy', value) > new Date();
            
            if (validacion)
            	$("#errorFechas").html("");
            else
            	$("#errorFechas").html("La fecha Desde debe ser mayor o igual a la de hoy");
            
            return validacion;
		} catch(e) {
			return false;
		}
    }, "");
	
	jQuery.validator.addMethod("dateAfter", function(value, element, fromDate) {
		try {
			var startDate = $("#" + fromDate).val();
            var validacion = jQuery.datepicker.parseDate( 'dd/mm/yy', startDate) <= jQuery.datepicker.parseDate( 'dd/mm/yy', value);
            
            if (validacion)
            	$("#errorFechas").html("");
            else
            	$("#errorFechas").html("La fecha Hasta debe ser mayor o igual a la fecha Desde");
            
            return validacion;
		} catch(e) {
			return false;
		}
    }, "");
	
	
}
function selectGlg() {  
    var codGlg = "";

    $('input[type=checkbox]').each(function () {
        var value = $(this).is(':checked') ? $(this).parent().find('span')[0].innerHTML : ' ';
        
        value = encodeHTML(value);
        $(this).val(value);
        
        if ($(this).is(':checked')) {
            codGlg += value;
        }  
    });

    $.ajax({
        url: "cuadroGeneral.do?accion=selectGlg",
        type: "POST",
        data: "codGlg=" + encodeURIComponent(codGlg),
        dataType: "json",
        success: function (data) {
            $("#motivo").empty().append("<option value=''></option>");

            $.each(data, function(index) {
                let codigo = encodeHTML(data[index].codigo);
                let descripcion = encodeHTML(data[index].descripcion);
                $("#motivo").append(`<option value="${codigo}">${descripcion}</option>`);
            });
        },
        error: function (data) {
            console.log(data);
        }
    });
}

function keyPressMonto(e, id) {
    let code = e.charCode || e.keyCode;
    let monto = $("#" + id).val();
    let indexNewChar = $("#" + id)[0].selectionStart;
    let indexDecimal = $("#" + id).val().indexOf(',');

    // Eliminar puntos y espacios del valor del campo de entrada
    monto = monto.replace(/[.,\s]/g, '');

    if (indexDecimal == -1 && (code == 44 || code == 46)) {
        // Si ya hay una coma, no permitir otra coma
        if (monto.indexOf(',') !== -1) {
            return false;
        }

        $("#" + id).val((monto.substring(0, indexNewChar) + "," + monto.substring(indexNewChar)));
        return false;
    } else if ($.inArray(code, [8, 9, 13]) !== -1 || (code >= 35 && code <= 40)) {
        return;
    }

    // Solo permitir números y una coma (,)
    if (!(code >= 48 && code <= 57) && code !== 44) {
        return false;
    }

    // Hasta 2 decimales
    if (indexDecimal != -1 && indexNewChar > indexDecimal && monto.substring(indexDecimal).length > 2) {
        return false;
    }

    // Hasta 13 enteros
    if (monto.length > 15) {
        return false;
    }
}

function encodeHTML(str) {
    return String(str)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}