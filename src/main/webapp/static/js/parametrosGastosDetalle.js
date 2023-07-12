var dtLink = 'ParametrosGastosDetalle.do';


$(document).ready(function() {
	$('#checkParametros').show();
	
	setFormValidate();
	showMessage('message', getLocalStorageItem('message'));
	$('.nav-parametros').addClass('active');
	setCombo('combos.do?action=getMotivos', '#filtroMotivo', { opcion: ['1', '2'].indexOf($('#glg').val()) == -1 ? 9 : 8});
	setCombo('combos.do?action=getTiposComprobante', '#filtroComprobante', {tipoGasto: "HOSPEDAJE"});
});


function nuevaRistra() {
	//$('#tableMessageContainer').addClass('d-none');
	modalGastosShow();
}



function showRistraPopup() {
	$('form').attr('action', 'parametrosGastosDetalle.do');
	$('form').submit();
	popUpObj = window.open("RistraPopUp.do", "ModalPopUp", "toolbar=no, scrollbars=no, location=no, statusbar=no,"
					+ "menubar=no, resizable=0, width=400, height=600, left = 550, right = 550, top=100");
	popUpObj.focus();
	
	if($("input[name^='centrosCostoI']").size() == 15)
		$("#addCC").hide();
	
	setFormValidate();
}


function confirmEliminarGasto() {
	if (confirm("\u00bfEst\u00e1 seguro que quiere eliminar el gasto " + $("#codigo").val() + "?"))
		$("#parametrosGastosForm").submit();
}



function borrarCentroCosto(index) {
	$.ajax( {
		url : "parametrosGastosDetalle.do?accionJson=borrarCentroCosto",
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
		url : "parametrosGastosDetalle.do?accionJson=agregarCentroCosto",
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

function setFormValidate() {
	jQuery.validator.addMethod("centrosCosto", function(value, element) {
		try {
			$("#errorCentrosCosto").html("");
				
			var repetidos = [];
			$("input[name^='centrosCostoI']").each(function( index ) {
				var val = this.value.replace(/^0+/, '');
				var existe = false;
				
				if(val) {
					$("input[name^='centrosCostoI']").each(function( index ) {
						if (val == this.value.replace(/^0+/, '')) {
							if (existe && $.inArray(val, repetidos) == -1)
								repetidos.push(val);
							else
								existe = true;
						}
					});
				}
			});
			
			if (repetidos.length > 0) {
				$("#errorCentrosCosto").html("Centros de Costo repetidos: " + repetidos.toString().replace(",", ", "));
	            return false;
			} else
	            return true;
		} catch(e) {
			return false;
		}
    }, "");

	$("#parametrosGastosForm").validate({
	    ignore: "",
		rules: {
			codigo: 			{ required : true, number : true },
			descripcionGasto: 	{ required : true },
			motivo: 			{ required : true },
			estado: 			{ required : true },
			bimon: 				{ required : true },
			plazoAprob: 		{ required : true },
			idCentroCostos: 	{ required : true, number : true },
			centrosCosto: 		{ centrosCosto : true }
		},
		messages: {
			codigo: { required : globalMsgRequired, number : "Campo solo numerico" },
			descripcionGasto: globalMsgRequired,
			motivo: globalMsgRequired,
			estado: globalMsgRequired,
			bimon: globalMsgRequired,
			plazoAprob: globalMsgRequired,
			idCentroCostos: { required : globalMsgRequired, number : "Campo solo numerico" }
		},
		errorElement: "p",
		errorPlacement: function(error, element) {      
        error.insertAfter(element);
        }
	});
}