var dtLink = 'listadoRendiciones.do';
var paramsEliminarRendicion;

$(document).ready(function() {
	showMessage('message', getLocalStorageItem('message'));
	$('.nav-rendiciones').addClass('active');
	dtParams = { action: 'filtrar' };
	loadRendicionesTable();
});

function filtrar() {
	if (!validarFiltro())
		return;
	
	dtParams.id = $('#filtroId').val();
	dtParams.fechaDesde = $('#filtroFechaDesde').val();
	dtParams.fechaHasta = $('#filtroFechaHasta').val();
	
	loadRendicionesTable();
}

function loadRendicionesTable() {
	loadTable('#rendicionesDtContainer', dtLink, dtParams);
}

function validarFiltro() {
	$('#messageContainer').addClass('d-none');
	$('#tableMessageContainer').addClass('d-none');
	var msgValidacion = '';
	$('input[id^=filtro]').removeClass('text-danger');
	clearFormError($('#filtroFechaDesde').parent());
	clearFormError($('#filtroFechaHasta').parent());
	
	if (compareDates($('#filtroFechaDesde').val(), $('#filtroFechaHasta').val()) == 1) {
		$('[id^=filtroFecha]').parent().find('label, input').addClass('text-danger');
		msgValidacion = 'La fecha hasta no puede ser menor a la fecha desde';
	}

	$('#filtroMsgValidacion').html(msgValidacion);
	$('#filtroMsgValidacion').toggle(msgValidacion != '');
	
	if (msgValidacion != '')
		scrollToElem('#divFiltro', false);
	
	return msgValidacion == '';
}

function limpiar() {
	$('input[id^=filtro]').val('');
	$('input[id^=filtro]').removeClass('text-danger');
	AutoNumeric.getAutoNumericElement('input[id^=filtro].an-integer-pos').set('');
	validarFiltro();
	
	scrollToElem('#divFiltro', false);
}

function eliminarRendicion(idRendicion) {
	$('#messageContainer').addClass('d-none');
	$('#tableMessageContainer').addClass('d-none');
	paramsEliminarRendicion = {
		action: 'eliminar',
		idRendicion: idRendicion
	};
	showConfirm('eliminarRendicionConfirm', '&iquest;Est&aacute;s seguro que quer&eacute;s eliminar la rendici&oacute;n?');
}

// Modal de detalle alerta
function obtenerDetalleAlerta(id, adea){
	modalAlertaShow(id, adea.toString())
}


function eliminarRendicionConfirm() {
	callAjax('listadoRendiciones.do', paramsEliminarRendicion, 'eliminarRendicionConfirmSuccess', 'eliminarRendicionConfirmError');
}

function eliminarRendicionConfirmSuccess(data) {
	showMessage('tableMessage', data.message);
	$('#modalConfirm').modal('hide');
	loadRendicionesTable();
}

function eliminarRendicionConfirmError(data) {
	showMessage('tableMessage', data.error);
	$('#modalConfirm').modal('hide');
}