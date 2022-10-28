function cantCaracteres(limit, idCampo) {
	var texto = document.getElementById(idCampo).value;

	if (texto.length > limit) {
		alert("Se supero el m\u00e1ximo de caracteres permitidos");
		document.getElementById(idCampo).value = texto.substring(0, limit - 1);
	}
}

function checkDateRange(start, end) {
	   // Parse the entries
	   var startDate = Date.parse(start);
	   var endDate = Date.parse(end);
	   // Make sure they are valid
	    if (isNaN(startDate)) {
	      alert("The start date provided is not valid, please enter a valid date.");
	      return false;
	   }
	   if (isNaN(endDate)) {
	       alert("The end date provided is not valid, please enter a valid date.");
	       return false;
	   }
	   // Check the date range, 86400000 is the number of milliseconds in one day
	   var difference = (endDate - startDate) / (86400000 * 7);
	   if (difference < 0) {
	       alert("The start date must come before the end date.");
	       return false;
	   }
	   if (difference <= 1) {
	       alert("The range must be at least seven days apart.");
	       return false;
	    }
	   return true;
	}

$(document).ready(function() {
	$.validator.addMethod("valueNotEquals", function(value, element, arg) {
	    return arg != value;
	}, "");
	
	jQuery.validator.addMethod("dateDMY", function(value, element) {
		try {
			jQuery.datepicker.parseDate( 'dd/mm/yy', value);
        	$("#fechaFormat").hide();
			return true;
		} catch(e) {
			$("#fechaFormat").show();
        	return false;
        }
    }, "");
	
	$("#RendicionForm").validate({
			rules: {
				motivo: {
					valueNotEquals: ""
		            },
				fechaDesde: {
					valueNotEquals: "",
					dateDMY : true
	            },
				fechaHasta: {
					valueNotEquals: "",
					dateDMY : true
	            },
				descripcion: {
					valueNotEquals: ""
	            }
			},
			messages: {
				motivo: " *",
				fechaDesde: {
					valueNotEquals: " *"
				},
				fechaHasta: {
					valueNotEquals: " *"
				},
				descripcion: "* Ingrese descripcion"
			}
	});			
	document.getElementById("BotonManual").style.display = "";
	document.getElementById("BotonPreguntas").style.display = "";
});

$.validator.setDefaults({
	submitHandler: function() {
		$('#sending').show();
		window.href.location = "listadoRendiciones.do"
	}
});

function numericOnly(e){
	var code = e.charCode || e.keyCode;
    if (e.keyCode == 46 && e.charCode == 0)
    	return;
    else if ($.inArray(code, [8, 9, 13]) !== -1 || (code >= 35 && code <= 40))
    	return;
    if (!(code >= 48 && code <= 57))
        return false;
}