jQuery(document).ready(function() {
	$('#checkCierre').show();
	$("#opcionesCierre").show();
	ayudaMotivo();
});

$(document).ready(function() {
	$("#imageCal6").click(function() {
		$('#fechaDesde').datepicker("show");
	});
	$("#imageCal7").click(function() {
		$('#fechaHasta').datepicker("show");
	});
});

function checkRendiciones(check) {
	var $checkboxes = $('#Rendiciontbl').find('input[id=checkCierre]');

	for (var i = 0; i < $checkboxes.size(); i++) {

		if ($checkboxes.get(i).checked) {
			seleccionado = false;
			break;
		}
	}

}
function gestionarCierre(tipo) {
	if (tipo == 2) {
		$("#estado").val("SUSPE");
	} else {
		$("#estado").val("ORDPG");

	}
	var query = "";
	var estado = "";
	estado = document.getElementById("estado").value;
	var $checkboxes = $('#Rendiciontbl').find('input[id=checkCierre]');

	var checkeado = false;

	for (var i = 0; i < $checkboxes.size(); i++) {
		if ($checkboxes.get(i).checked) {

			query +="idRendicion"
					+ i
					+ "="
					+ jQuery(jQuery($checkboxes[i]).parent().prevAll().get(5))
							.text().trim() + "&";

			query += "seleccionado" + i + "=true&";
			checkeado = true;
		}

	}
	if (checkeado) {
		iz = (screen.height / 2) - (300 / 2);
		de = (screen.width / 2) - (500 / 2);
		
		if (tipo == 2) {
			
			popUpObj = window.open(
				"MotivoSuspension.do?estado="+estado+"&" + query,
				"",
				"toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, width=500, height=300, left="
						+ de + ", top=" + iz);
			popUpObj.focus();
			LoadModalDiv();
		} else {
			$("#Aguarde").show();
			var ventana = window.location.replace(
				"gestionarCierre.do?estado="+estado+"&" + query,
				"",
				"toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, width=500, height=300, left="
						+ de + ", top=" + iz);
		
			document.getElementById("CierreForm").reset();
		}
	} else {
		alert("Debe seleccionar al menos una rendicion");
	}

}

function suspender() {
	popUpObj = window.open("MotivoSuspension.do" + "?codigo="
			+ $('#idRendicion').val()+"&glg="+$('#glg').val()+"&"+"usuarioRendicion="+$('#usuarioRendicion').val(), "ModalPopUp", "toolbar=no,"
			+ "scrollbars=no," + "location=no," + "statusbar=no,"
			+ "menubar=no," + "resizable=0," + "width=450,"
			+ "height=160," + "left = 500," + "right = 500,"
			+ "top=250," + "bottom = 250");
	popUpObj.focus();
	LoadModalDiv();
	
};

function showThuban(Link, rend) {
	window.open(Link+rend);	
}