$(document).ready(function() {

	$.validator.addMethod("valueNotEquals", function(value, element, arg) {
		return arg != value;
	}, "");
	
	jQuery.validator.addMethod("importe", function(value, element) {
		try {
			$("#errorMonto").html("");
			
			var monto = $('#monto').val();
			var indexDecimal = $("#monto").val().indexOf(',');
			
			if (monto.substring(0, indexDecimal == -1 ? monto.length : indexDecimal).length > 11) {
				$("#errorMonto").html("El monto no puede tener m\u00e1s de 11 enteros.");
				return false;
			} else if (indexDecimal != -1 && monto.substring(indexDecimal).length > 3) {
				$("#errorMonto").html("El monto no puede tener m\u00e1s de 2 decimales.");
				return false;
			}
			
	        return true;
		} catch(e) {
			return false;
		}
    }, "");

	$("#RendicionDetalleForm").validate({
		rules : {
			gastos : {
				valueNotEquals : ""
			},
			monto : {
				valueNotEquals : "",
				importe: true
			},
			moneda : {
				valueNotEquals : ""
			},
			fechagastos : {
				valueNotEquals : ""
			},
			comprobante1 : {
				valueNotEquals : ""
			},
			comprobante2 : {
				valueNotEquals : ""
			},
			cuit1 : {
				valueNotEquals : ""
			},
			cuit2 : {
				valueNotEquals : ""
			},
			cuit3 : {
				valueNotEquals : ""
			},
			costosDestino: {
            	valueNotEquals: "",
            	costosDestino: true
            }
		},
		messages : {
			gastos : " *",
			monto : { valueNotEquals : " *" },
			moneda : " *",
			fechagastos : " *",
			comprobante1 : " *",
			comprobante2 : " *",
			cuit1 :" *",
			cuit2 :" *",
			cuit3 :" *",
			costosDestino: {valueNotEquals:" *"}
		}
	});
});