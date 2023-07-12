var dtLink = 'aprobacionDetalle.do';

$(document).ready(function() {
	callAjax('aprobacionDetalle.do', 'action=getMessage', 'init');
	$('.nav-aprobacion').addClass('active');
});

function init(data) {
	setVisibility();
	loadTables();
}

function setVisibility() {
	$('.glg-' + $('#glg').val()).removeClass('d-none');
}

function loadTables() {
	dtParams = {
		action: 'getRendicionGastos',
		idRendicion: $('#idRendicion').html(),
		usuarioRend: $('#user').val(),
		codMotivo: $('#codMotivo').val(),
		estadoRend: $('#estadoRend').val()
	};
	loadTable('#gastosDtContainer', dtLink, dtParams);
}

function aprobar() {
	modalAprobarRendicionShow($('#idRendicion').html(), $('#glg').val());
}

function rechazar() {
	modalRechazarRendicionShow($('#idRendicion').html(), $('#glg').val());
}

function observar() {
	modalObservarRendicionShow($('#idRendicion').html(), $('#glg').val());
}

function redistribuir() {
	window.location.href = "distribucionGastosLoad.do?idRendicion=" + $('#idRendicion').html() + "&usuarioRendicion="+$('#user').val();
}

function openImagenes() {
	//modalImagenesShow($('#idRendicion').html(), true, $('#urlThuban').val());
	window.open($('#urlThuban').val(), '_blank');
}