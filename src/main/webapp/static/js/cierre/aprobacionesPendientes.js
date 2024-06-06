let dtLink = 'aprobacionesPendientes.do';

$(document).ready(function() {
	let fechaActual = new Date();
	//Formatear fecha actual AAAA/MM/DD
	let fechaFormateada = fechaActual.toISOString().slice(0,10)
	setMoneda();
	dtParams = { action: 'filtrar', fechaCierre: fechaFormateada};
	$('#filtroMoneda').trigger("chosen:updated")
	loadAprobacionesPendientesTable();
	//loadConsumosNoRendidosTable()
});


function setMoneda() {
	setCombo('combos.do?action=getMonedas', '#filtroMoneda');
}

function filtrar() {
	if (!validarFiltro())
		return;
		
	let elementosFechas = $("#filtroCierre").val().replace('/','-').replace('/','-').split('-')
	let fecha_invertida = elementosFechas[2] + '-' +elementosFechas[1] + '-' + elementosFechas[0]
	dtParams.usuario = $('#filtroUsuario').val().trim();
	dtParams.montoMin = $('#filtroMontoMin').val().trim();
	dtParams.montoMax = $('#filtroMontoMax').val().trim();
	dtParams.moneda = $('#filtroMoneda').val().trim();
	dtParams.glg = '04';
	dtParams.fechaCierre = fecha_invertida
	
	loadAprobacionesPendientesTable();
}

function limpiar() {
	$('input[id^=filtro]').val('');
	$('input[id^=filtro]').removeClass('text-danger');
	$('#filtroNombre').val("")
	$('#filtroMoneda').val("").trigger("chosen:updated");

}


function validarFiltro() {
	$('#messageContainer').addClass('d-none');
	$('#tableMessageContainer').addClass('d-none');
	var msgValidacion = '';
	$('input[id^=filtro]').removeClass('text-danger');

	return msgValidacion == '';
}



function loadAprobacionesPendientesTable() {
	loadTable('#aprobacionesPendientesDtContainer', dtLink, dtParams);
}


function buscarUsuario(legajo) {
	if (legajo) {
		let params = {
			action: 'buscarUsuario',
			legajo: legajo,
		};
	 	callAjax('abmDelegado.do', params, 'buscarUsuarioLegajo', 'buscarUsuarioLegajoError', false)
	
}
}


  $('#filtroUsuario').blur(function() {
		buscarUsuario($('#filtroUsuario').val().trim().toUpperCase())
	 if ($('#filtroUsuario').val() == ""){
		$('#filtroNombre').val("")
		clearFormError($('#filtroUsuario').parent());
	}

 });

function buscarUsuarioLegajo(data) {
	clearFormError($('#filtroUsuario').parent());
	$('#filtroNombre').val(data.delegado.nombre)

	
}

function buscarUsuarioLegajoError(data) {
	showFormError('#filtroUsuario', data.error);
	$('#filtroNombre').val("")
	
}

function generarPendientesAprobacion(){}
