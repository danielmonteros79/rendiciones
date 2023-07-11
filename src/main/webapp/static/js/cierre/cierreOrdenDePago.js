var dtLink = 'cierreOrdenDePago.do';
var paramsOrdenDePago;
var selRendiciones = {};

$(document).ready(function() {
	showMessage('message', getLocalStorageItem('message'));
	$('.nav-cierre').addClass('active');
	dtParams = { action: 'filtrar' };
	setCombo('combos.do?action=getMotivos', '#filtroMotivo', { opcion: 4});
	filtrar();
	
 	$('#filtroMotivo').trigger("chosen:updated");
	
});

function filtrar() {
	
	dtParams.idRendicion = $('#filtroId').val();
	dtParams.motivo = $('#filtroMotivo').val();
	dtParams.usuario = $('#filtroUsuario').val();

	
	loadCierreTable();
}

function loadCierreTable() {
	selRendiciones = {};
	loadTable('#cierreOrdenDePagoDtContainer', dtLink, dtParams);
}

function limpiar() {
	$('[id^=filtro]').val('');
	AutoNumeric.getAutoNumericElement('input[id^=filtro].an-integer-pos').set('');
	
	scrollToElem('#divFiltro', false);
}

function tableAfterLoad() {
	$.each(selRendiciones, function(i, v) {
		$('input[type=checkbox][value=' + i + ']').prop('checked', true);
	});
}

function clickCheckbox(idRendicion, checkbox) {
	if ($(checkbox).prop('checked'))
		selRendiciones[idRendicion] = idRendicion;
	else
		delete selRendiciones[idRendicion];
}

function generarCierre() {
	$('#messageContainer').addClass('d-none');
	$('#cierreOrdenDePagoTableMessage').addClass('d-none');
	
	if (Object.keys(selRendiciones).length > 0) {
		paramsOrdenDePago = {
			action: 'generar',
			idRendiciones: JSON.stringify(Object.keys(selRendiciones))
		};
		showConfirm('generarCierreConfirm', '&iquest;Est&aacute;s seguro que quer&eacute;s generar el cierre de las rendiciones seleccionadas?');
	} else {
		showMessage2('#cierreOrdenDePagoTableMessage', 'Ten&eacute;s que seleccionar al menos una rendici&oacute;n.', 'error');
		scrollToElem('#cierreOrdenDePagoTableMessage', true);
	}
}

function generarCierreConfirm() {
	callAjax('cierreOrdenDePago.do', paramsOrdenDePago, 'generarCierreConfirmSuccess', 'generarCierreConfirmError');
}

function generarCierreConfirmSuccess(data) {
	showMessage2('#cierreOrdenDePagoTableMessage', data.message);
	$('#modalConfirm').modal('hide');
	loadCierreTable();
}

function generarCierreConfirmError(data) {
	showMessage2('cierreOrdenDePagoTableMessage', data.error);
	$('#modalConfirm').modal('hide');
}

function suspenderCierre() {
	$('#messageContainer').addClass('d-none');
	$('#cierreOrdenDePagoTableMessage').addClass('d-none');
	
	if (Object.keys(selRendiciones).length > 0)
		modalSuspenderCierreShow(JSON.stringify(Object.keys(selRendiciones)));
	else {
		showMessage2('#cierreOrdenDePagoTableMessage', 'Ten&eacute;s que seleccionar al menos una rendici&oacute;n.', 'error');
		scrollToElem('#cierreOrdenDePagoTableMessage', true);
	}
}

function showThuban(link, rend) {
	window.open(link);		
}