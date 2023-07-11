var dtLink = 'cierreDetalle.do';

$(document).ready(function() {
	callAjax('cierreDetalle.do', 'action=getMessage', 'init');
	$('.nav-cierre').addClass('active');
});

function init(data) {
	showMessage('message', data.message);
	loadGastosTable();
}

function loadGastosTable() {
	dtParams = {
		action: 'getRendicionGastos',
		idRendicion: $('#idRendicion').html(),
		usuarioRend: $('#user').val(),
		codMotivo: $('#codMotivo').val(),
		estadoRend: $('#estadoRend').val()
	};
	loadTable('#gastosDtContainer', dtLink, dtParams);
}

function openImagenes() {
	//modalImagenesShow($('#idRendicion').html(), true, $('#urlThuban').val());
	window.open($('#urlThuban').val(), '_blank');
}