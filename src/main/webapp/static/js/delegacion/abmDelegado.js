var dtLink = 'abmDelegado.do';
var paramsEliminarDelegado;

$(document).ready(function() {
	$('.nav-delegacion').addClass('active');
	dtParams = { action: 'getDelegados' };
	loadDelegadosTable();

});

function nuevoDelegado() {
	$('#tableMessageContainer').addClass('d-none');
	modalDelegadoShow();
}

function eliminarDelegado(delegado, fechaDesde, fechaHasta) {
	$('#tableMessageContainer').addClass('d-none');
	
	paramsEliminarDelegado = {
		action: 'abm',
		opcion: 'BAJA',
		delegadoUser: delegado,
		feHastaOld: fechaHasta,
		feHastaOld: fechaDesde,
		feDesde: fechaDesde,
		feHasta: fechaHasta,
	};
	
	
	showConfirm('eliminarDelegadoConfirm', '&iquest;Est&aacute;s seguro que quer&eacute;s dar de baja la delegaci&oacute;n?');
}

function eliminarDelegadoConfirm() {
	callAjax('abmDelegado.do', paramsEliminarDelegado, 'eliminarDelegadoConfirmSuccess');
}

function eliminarDelegadoConfirmSuccess(data) {
	showMessage('tableMessage', data.message);
	$('#modalConfirm').modal('hide');
	loadDelegadosTable();
}

function loadDelegadosTable() {
	loadTable('#delegadosDtContainer', dtLink, dtParams);
}