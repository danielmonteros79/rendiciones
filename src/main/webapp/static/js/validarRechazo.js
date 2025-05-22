$(document).ready(function() {

	$.validator.addMethod("valueNotEquals", function(value, element, arg) {
		return arg != value;
	}, "");

	$("#RechazarRendicionForm").validate({
		rules : {
			
			motivoRechazo: {
				valueNotEquals : ""
			}

		},
		messages : {
			
			motivoRechazo: " *"
	
		}
	});
});
$.validator.setDefaults({

	submitHandler : function() {
		window.href.location = "motivoRechazo.do"

	}
});