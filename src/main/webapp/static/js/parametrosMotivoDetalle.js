
jQuery(document).ready(function() {
	$("#txAviso").attr("maxlength", "250");
	$('#checkParametros').show();
	
	$("#imageCal1").click(function() {
		$('#fechaDesde').datepicker("show");        
	});
	$("#imageCal2").click(function() {
		$("#fechaHasta").datepicker("show");
	});

	$('input[type=checkbox]').change(function() {
        $('#oscar' + $(this)[0].value).val($(this).is(':checked') ? $(this)[0].value : ' ');
    });
	
	if($("input[name^='centrosCostoI']").size() == 15)
		$("#addCC").hide();
	
	$('.nav-parametros').addClass('active');
	
	setFormValidate();
});

function setFormValidate() {
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
            

			var start = parseDate(startDate);
			var end = parseDate(value);
			
			if (isNaN(start) || isNaN(end)) {
	            $("#errorFechas").html("Formato de fecha no válido");
	            return false;
        	}
            
			console.log("START: " + start);
			console.log("END: " + end);
			
			var validacion = start <= end;
			console.log("VALIDACION: " + validacion);

            if (validacion) {
	            $("#errorFechas").html(""); // Limpiar mensaje de error si es válido
	        } else {
	            $("#errorFechas").html("La fecha Hasta debe ser mayor o igual a la fecha Desde");
	        }
            
            return validacion;
		} catch(e) {
			$("#errorFechas").html("La fecha Hasta debe ser mayor o igual a la fecha Desde");
			return false;
		}
    }, "");
	
	jQuery.validator.addMethod("centrosCosto", function(value, element) {
		try {
			$("#errorCentrosCosto").html("");
			var algunoConValor = false;
			
			var repetidos = [];
			$("input[name^='centrosCostoI']").each(function( index ) {
				var val = this.value.replace(/^0+/, '');
				var existe = false;
				
				if (val) {
					$("input[name^='centrosCostoI']").each(function( index ) {
						if (val == this.value.replace(/^0+/, '')) {
							if (existe && $.inArray(val, repetidos) == -1)
								repetidos.push(val);
							else
								existe = true;
						}
					});
					algunoConValor = true;
				}
			});
			
			if (repetidos.length > 0) {
				$("#errorCentrosCosto").html("Centros de Costo repetidos: " + repetidos.toString().replace(",", ", "));
	            return false;
			}
			
	        return true;
		} catch(e) {
			return false;
		}
    }, "");
	
	$("#parametrosMotivoForm").validate({
	    ignore: "",
		rules: {
			codigo: 			{ required : false, number : true },
			descripcion: 		{ required : true },
			idGlg: 				{ required : true, number : true },
			idNivCarga: 		{ required : true },
			maInclExcl: 		{ required : true },
			idCentroCostos: 	{ required : true },
			fechaDesde: 		{ required : true},
			fechaHasta: 		{ required : true, dateAfter : 'fechaDesde' },
			meDiasInterv: 		{ number : true },
			centrosCosto: 		{ centrosCosto : true }
		},
		messages: {
			codigo: { required : globalMsgRequired, number : "Campo solo numerico" },
			descripcion:{ required : globalMsgRequired },
			idGlg: { required : globalMsgRequired, number : "Campo solo numerico" },
			idNivCarga: globalMsgRequired,
			maInclExcl: globalMsgRequired,
			idCentroCostos: globalMsgRequired,
			fechaDesde: { required : globalMsgRequired },
			fechaHasta: { required : globalMsgRequired },
			meDiasInterv: { number : globalMsgRequired }
		},
		errorElement: "p",
		rrorPlacement: function(error, element) {      
        error.insertAfter(element);
    }

		
		
	});
}



function borrarCentroCosto(index) {
	$.ajax( {
		url : "parametrosMotivoDetalle.do?accionJson=borrarCentroCosto",
		type : "POST",
		data : "index=" + index,
		success : function () {
			$("[name*='centrosCostoI[" + index + "]']").remove();
			$("#addCC").show();
			
			var i = 0;
			$("input[name^='centrosCostoI']").each(function( index ) {
				this.name = this.name.replace(/\d+/, i);
				i++;
			});
			
			i = 0;
			$("a[name^='d_centrosCostoI']").each(function( index ) {
				this.setAttribute("onclick", "borrarCentroCosto(" + i + ");");
				this.name = this.name.replace(/\d+/, i);
				i++;
			});
		}
	});
}

function agregarCentroCosto() {
	$("#errorCentrosCosto").html("");
	$.ajax( {
		url : "parametrosMotivoDetalle.do?accionJson=agregarCentroCosto",
		type : "POST",
		success : function (data) {
			if (data == "14")
				$("#addCC").hide();
			if (data != "-1")
				$("#addCC").before(
					"<input type='text' name='centrosCostoI[" + data + "]' maxlength='4' style='width:50px;' " + 
						"onkeypress='return numericOnly(event);'>" +
					"<a href='#' onclick='borrarCentroCosto(" + data + ");' name='d_centrosCostoI[" + data + "]' style='margin:0 3px;'>" +
						"<img src='./images/iconos/borrar.png' alt='Borrar' title='Borrar' style='width:10px;'/>" +
					"</a>"
				);
		}
	});
}

function confirmEliminarMotivo() {
	if (confirm("\u00bfEst\u00e1 seguro que quiere eliminar el motivo " + $("#codigo").val() + "?"))
		$("#parametrosMotivoForm").submit();
}

function parseDate(dateStr) {
            var parts = dateStr.split("/");
            if (parts.length === 3) {
                return new Date(parts[2], parts[1] - 1, parts[0]);
            } else {
                return new Date(dateStr);
            }
        }

