var dtLink = 'detalleAlerta.do';
let param = new URLSearchParams(location.search).get('codigo');
let paramMot = new URLSearchParams(location.search).get('codMotivo');
let paramUser = new URLSearchParams(location.search).get('codMotivo');


$(document).ready(function() {
	showMessage('message', getLocalStorageItem('message'));
	$('.nav-aprobacion').addClass('active');
	dtParams = { action: 'filtrar' };
	loadDetalleAlertaTable();
});

function loadDetalleAlertaTable() {
	dtParams.codigo = param;
	dtParams.codMotivo = paramMot;
	dtParams.codUsuario = paramUser;
	loadTable('#detalleAlertaDtContainer', dtLink, dtParams);
}

function filtrar() {

	dtParams.codigo = param;
	dtParams.codMotivo = paramMot;
	dtParams.codUsuario = paramUser;
	loadDetalleAlertaTable();
}