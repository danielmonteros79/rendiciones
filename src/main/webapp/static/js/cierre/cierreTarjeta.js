var dtLink = 'cierreTarjeta.do';
var paramsGenerarCierre;
var selConsumos = {};
var totalPesos = 0;
var totalDolares = 0;

$(document).ready(function() {
	showMessage('message', getLocalStorageItem('message'));
	$('.nav-cierre').addClass('active');
	dtParams = { action: 'filtrar' };
});

function filtrar() {
	if (!validateForm('filtro', true)) {
		scrollToElem('#filtro', false);
		$('.div-resultado').addClass('d-none');
		return;
	}
	
	dtParams.usuario = $('#filtroUsuario').val();
	
	loadCierreTable();
}

function loadCierreTable() {
	selConsumos = {};
	totalPesos = 0;
	totalDolares = 0;
	$('#totalPesos').html(formatCurrency(totalPesos));
	$('#totalDolares').html(formatCurrency(totalDolares));
	
	loadTable('#cierreTarjetaDtContainer', dtLink, dtParams);
}

function tableFirstLoad() {
	$('.div-resultado').removeClass('d-none');
}

function tableAfterLoad() {
	$.each(selConsumos, function(i, v) {
		$('input[type=checkbox][value=' + i + ']').prop('checked', true);
	});
}

function limpiar() {
	$('[id^=filtro]').val('');
	
	scrollToElem('#filtro', false);
}

function clickCheckbox(idConsumo, moneda, monto, checkbox) {
	if ($(checkbox).prop('checked') && Object.keys(selConsumos).length == 10) {
		$(checkbox).prop('checked', false);
		showError({error: 'No se pueden seleccionar m\u00E1s de 10 registros.'});
		return;
	}
	
	if ($(checkbox).prop('checked'))
		selConsumos[idConsumo] = {
			moneda: moneda,
			monto: monto
		};
	else
		delete selConsumos[idConsumo];
	
	calcularTotales();
}

function calcularTotales() {
	totalPesos = 0;
	totalDolares = 0;
	
	$.each(selConsumos, function(i, v) {
		if (v.moneda == 'ARS')
			totalPesos += v.monto;
		else if (v.moneda == 'USD')
			totalDolares += v.monto;
	});
	
	$('#totalPesos').html(formatCurrency(totalPesos));
	$('#totalDolares').html(formatCurrency(totalDolares));
}

function generarCierre() {
	$('#messageContainer').addClass('d-none');
	$('#cierreTarjetaTableMessage').addClass('d-none');
	
	if (Object.keys(selConsumos).length > 0)
		modalGenerarCierreTarjetaShow(JSON.stringify(Object.keys(selConsumos)), dtParams.usuario, totalPesos, totalDolares);
	else {
		showMessage2('#cierreTarjetaTableMessage', 'Ten&eacute;s que seleccionar al menos un consumo.', 'error');
		scrollToElem('#cierreTarjetaTableMessage', true);
	}
}