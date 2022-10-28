$(document).ready(function() {
	$('#checkCuadro').show();
	
	$("#imageCal1").click(function() {
		$('#fechaDesde').datepicker("show");        
	});
	$("#imageCal2").click(function() {
		$("#fechaHasta").datepicker("show");
	});
	
	setFormValidate();
	selectOpcion();
	selectGlg();
});

function filtrar() {
	$("#cuadroGeneralFiltro").attr('action', 'cuadroGeneralFiltro.do');
	$("#cuadroGeneralFiltro").submit();
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
		$("#imageCal1").hide();
		$("#imageCal2").hide();
	} else {
		$("#fechaDesde").attr('readonly', false);
		$("#fechaHasta").attr('readonly', false);
		$("#imageCal1").css('display', '');
		$("#imageCal2").css('display', '');
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
        	$("#errorFechas").html("Las fechas deben tener formato DD/MM/AAAA");
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
	
	$("#cuadroGeneralFiltro").validate({
		rules: {
			codGlg:  	{ required : true},
			montoDesde: { importe : true},
			montoHasta: { importe : true},
			fechaHasta: { required : true, dateDMY : true, dateAfter : 'fechaDesde' },
			fechaDesde: { required : true }	
		},
		messages: {
			codGlg:  	{ required: " *" },
			fechaDesde: { required: " *" },
			fechaHasta: { required: " *" }
		},
		submitHandler: function (form) {
			$('input[type=checkbox]').each(function () {
				$(this).val($(this).is(':checked') ? $(this).parent().find('span')[0].innerHTML : ' ');
				
				if (!$(this).is(':checked'))
					$(this).parent().find('span').addClass("notChecked");
				
				$(this).prop('checked', true);
			});
			
			form.submit();
		}
	});
}

function selectGlg() {	
	var codGlg = "";
	$('input[type=checkbox]').each(function () {
		$(this).val($(this).is(':checked') ? $(this).parent().find('span')[0].innerHTML : ' ');
		if ($(this).is(':checked')){
			codGlg = codGlg + $(this).val();
		}			
    });
	
	$.ajax( {
		url : "cuadroGeneral.do?accion=selectGlg",
		type : "POST",
		data : "codGlg=" + codGlg,
		dataType: "json",
		success : function (data) {
			$("#motivo").append("<option value=''></option>");
			$.each(data, function(index) {
				$("#motivo").append("<option value=" + data[index].codigo + ">" + data[index].descripcion + "</option>");
			});
		},
		error: function (data) {
			console.log(data);
		}
	});
}

function keyPressMonto(e, id){
	var code = e.charCode || e.keyCode;
	var monto = $("#" + id).val();
	var indexNewChar = $("#" + id)[0].selectionStart;
	var indexDecimal = $("#" + id).val().indexOf(',');
	
	if (indexDecimal == -1 && (code == 44 || code == 46)) {
		if (code == 46) {
			$("#" + id).val((monto.substring(0, indexNewChar) + "," + monto.substring(indexNewChar)));
			return false;
		}
		
		return;
	}
    else if ($.inArray(code, [8, 9, 13]) !== -1 || (code >= 35 && code <= 40))
        return;
    if (!(code >= 48 && code <= 57))
        return false;
    
    // Hasta 2 decimales
    if (indexDecimal != -1 && indexNewChar > indexDecimal && monto.substring(indexDecimal).length > 2)
        return false;
    
    // Hasta 13 enteros
    if (monto.substring(0, indexDecimal == -1 ? monto.length : indexDecimal).length > 12 &&
       (indexDecimal == -1 || indexNewChar < indexDecimal))
        return false;
}