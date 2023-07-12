$(document).ready(function() {

	$.validator.addMethod("valueNotEquals", function(value, element, arg) {
		return arg != value;
	}, "");

	$("#formCupon").validate({
		rules : {
			
			cupon : {
				valueNotEquals : ""
			}

		},
		messages : {
			
			cupon : " *"
	
		}
	});
});
$.validator.setDefaults({

	submitHandler : function() {
		alert("Cupon guardado.");
		window.href.location = "rendicionDetalleGastos.do"

	}
});