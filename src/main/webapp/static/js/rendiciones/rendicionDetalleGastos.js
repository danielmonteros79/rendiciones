var dtLink = 'rendicionDetalleGastos.do';
var dtParamsGastos = {};
var dtParamsConsumosPendientes = {};
var paramsEliminarGasto;
var isEditing = false;
var tableFirstLoad = true;

$(document).ready(function() {
	callAjax('rendicionDetalleGastos.do', 'action=getMessage', 'init');
	$('.nav-rendiciones').addClass('active');
	
	// TODO: Hacer si viene desde crear rendicion
	window.history.pushState("", "", "rendicionDetalleGastos.do" +
    	"?codigo=" + $('#idRendicion').html() +
    	"&codMotivo=" + $('#codMotivo').val() +
    	"&estadoRend=" + $('#estadoRend').val());

});

function init(data) {
	var lsMessage = getLocalStorageItem('message');
	showMessage('message', data.message);
	if (lsMessage) {
		showMessage('message', $('#message').html() ? $('#message').html() + '<br>' + lsMessage : lsMessage);
		scrollToElem('body');
	}

	dtParamsGastos = {
		action: 'getRendicionGastos',
		idRendicion: $('#idRendicion').html(),
		usuarioRend: $('#user').val(),
		codMotivo: $('#codMotivo').val(),
		estadoRend: $('#estadoRend').val()
	};
	
	dtParamsConsumosPendientes = {
		action: 'getConsumosPendientes',
		idRendicion: $('#idRendicion').html(),
		codMotivo: $('#codMotivo').val(),
		fechaDesde: $('#fechaDesde').html(),
		fechaHasta: $('#fechaHasta').html()
	};
	
	if ($('#message').hasClass('text-danger')) {
		$('#content .container').addClass('d-none');
		$('#messageContainer').parent().children('div').addClass('d-none');
		$('#messageContainer').parent().removeClass('d-none');
		$('#messageContainer').removeClass('d-none');
	} else {
		setVisibility();
		loadTables();
	}
}

function setVisibility() {
	$('.d-no-edit').toggleClass('d-none', isEditing);
	$('.d-edit').toggleClass('d-none', !isEditing);
	$('.motivo-div.d-edit').toggleClass('d-none', !(isEditing && !$('#gastoFechaMax').val()));
	$('.motivo-div.d-no-edit').toggleClass('d-none', isEditing && !$('#gastoFechaMax').val());
	$('.motivo-div.d-no-edit').toggleClass('border', !isEditing);
	
	if (!isEditing) {
		$('#aviso').parent().parent().toggleClass('d-none', !($('#estadoRend').val() == 'PENDI' && $('#aviso').html() != ""));
		$('[class*=d-est-]').addClass('d-none');
		$('.d-est-' + $('#estadoRend').val()).removeClass('d-none');
		
		$('#divAcciones').toggleClass('d-none', $('#divAcciones a:visible').length == 0);
		$('#divNuevoGasto').toggleClass('d-none', $('#divNuevoGasto a:visible').length == 0);
	}
}

function loadTables() {
	loadTable('#gastosDtContainer', dtLink, dtParamsGastos);
	loadTable('#consumosPendientesDtContainer', dtLink, dtParamsConsumosPendientes);
}

function tableLoadAfterFinished() {
	showMessage('tableMessage', tableFirstLoad ? $('#tableMessage').html() : getLocalStorageItem('tableMessage'));
	$('#imagenesBtn').toggleClass('d-none', $('.dt-empty:visible').length == 2);
	tableFirstLoad = false;
}

function nuevoGasto(cupon) {
	$('#tableMessageContainer').addClass('d-none');
	if (cupon)
		modalCuponesShow($('#idRendicion').html(), $('#estadoRend').val(), $('#codMotivo').val(), $('#fechaDesde').html(), $('#fechaHasta').html(),
				null, null, null, false);
	else
		modalGastoShow($('#idRendicion').html(), $('#estadoRend').val(), null, $('#codMotivo').val(), cupon);
}

function eliminarGasto(idGasto) {
	$('#tableMessageContainer').addClass('d-none');
	paramsEliminarGasto = {
		opcion: 'BAJA',
		idRendicion: $('#idRendicion').html(),
		idGasto: idGasto
	};
	showConfirm('eliminarGastoConfirm', '&iquest;Est&aacute;s seguro que quer&eacute;s eliminar el gasto?');
}

function eliminarGastoConfirm() {
	callAjax('gastos.do', paramsEliminarGasto, 'eliminarGastoConfirmSuccess');
}

function eliminarGastoConfirmSuccess(data) {
	$('#modalConfirm').modal('hide');
	//setLocalStorageItem('message', data.message);
	loadTables();
	setTimeout(function(){
		showMessage('tableMessage', data.message);
	},500)
	//loadTables();
	//location.reload();
	}

function activarRechazarRendicion(activarRechazar) {
	$('#tableMessageContainer').addClass('d-none');
	showConfirm('activarRechazarRendicionConfirm', '&iquest;Est&aacute;s seguro que quer&eacute;s ' + activarRechazar + ' la rendici&oacute;n?');
}

function activarRechazarRendicionConfirm() {
	var params = {
		action: 'activarRechazar',
		idRendicion: $('#idRendicion').html(),
		estado: $('#estadoRend').val()
	};
	callAjax('rendicionDetalleGastos.do', params, 'activarRechazarRendicionConfirmSuccess');
}

function activarRechazarRendicionConfirmSuccess(data) {
	setLocalStorageItem('message', data.message);
	$('#modalConfirm').modal('hide');
	window.location.href = 'listadoRendiciones.do';
}

function modificarRendicion() {
	$('#message').html('');
	isEditing = true;
	clearFormErrors('#editRendicion');

	if (!$('#gastoFechaMax').val() && $('#editRendicionMotivo > option').length == 0)
		setCombo('combos.do?action=getMotivos', '#editRendicionMotivo', { opcion: 4}, $('#codMotivo').val());

	$('#editRendicionFechaDesde').datepicker('setDate', $('#fechaDesde').html());
	$('#editRendicionFechaHasta').datepicker('setDate', $('#fechaHasta').html());
	$('#editRendicionDescripcion').val($('#descripcion').html());

	$('[id^=editRendicionFecha]').attr('data-maxdate', formatDate(new Date())).datepicker('setEndDate', new Date());
	
	setVisibility();
	scrollToElem('#subtitle', true);
}

function cancelarModificacion() {
	isEditing = false;
	setVisibility();
}

function finalizarModificacion() {
	var valid = true;
	
	if (!validateForm('editRendicion', true))
		valid = false;
	
	if (compareDates($('#editRendicionFechaDesde').val(), $('#editRendicionFechaHasta').val()) == 1) {
		$('input[id^=editRendicionFecha]').addClass('text-danger');
		$('input[id^=editRendicionFecha]').parent().find('label').addClass('text-danger');
		showFormError('#editRendicionFechaHasta', 'La fecha hasta no puede ser menor a la fecha desde');
		valid = false;
	} else {
		if (compareDates($('#editRendicionFechaDesde').val(), $('#gastoFechaMin').val()) == 1) {
			$('#editRendicionFechaDesde').addClass('text-danger');
			$('#editRendicionFechaDesde').parent().find('label').addClass('text-danger');
			showFormError('#editRendicionFechaDesde', 'La fecha desde no puede ser mayor a ' + $('#gastoFechaMin').val() + ' por haber un gasto con esa fecha');
			valid = false;
		}
		
		if (compareDates($('#editRendicionFechaHasta').val(), $('#gastoFechaMax').val()) == -1) {
			$('#editRendicionFechaHasta').addClass('text-danger');
			$('#editRendicionFechaHasta').parent().find('label').addClass('text-danger');
			showFormError('#editRendicionFechaHasta', 'La fecha desde no puede ser menor a ' + $('#gastoFechaMax').val() + ' por haber un gasto con esa fecha');
			valid = false;
		}
	}
	
	if (valid) {
		var params = {
			action: 'modificarRendicion',
			idRendicion: $('#idRendicion').html(),
			codMotivo: $('#editRendicionMotivo').val() ? $('#editRendicionMotivo').val() : $('#codMotivo').val(),
			fechaDesde: $('#editRendicionFechaDesde').val(),
			fechaHasta: $('#editRendicionFechaHasta').val(),
			descripcion: $('#editRendicionDescripcion').val(),
			estadoRend: $('#estadoRend').val()
		};
		callAjax('rendicionDetalleGastos.do', params, 'finalizarModificacionSuccess');
	} else
		scrollToElem('#editRendicion', true);
}

function finalizarModificacionSuccess(data) {
	location.reload();
	setLocalStorageItem('message', data.message);
}

function finalizarObservacion() {
	$('#tableMessageContainer').addClass('d-none');
	showConfirm('finalizarObservacionConfirm', '&iquest;Est&aacute;s seguro que quer&eacute;s finalizar la observaci&oacute;n de la rendici&oacute;n?');
}

function finalizarObservacionConfirm() {
	var params = {
		action: 'finalizarObservacion',
		idRendicion: $('#idRendicion').html(),
		idu: $('#idu').val()
	};
	callAjax('rendicionDetalleGastos.do', params, 'finalizarObservacionConfirmSuccess');
}

function finalizarObservacionConfirmSuccess(data) {
	setLocalStorageItem('message', data.message);
	$('#modalConfirm').modal('hide');
	window.location.href = 'listadoRendiciones.do';
}

function openImagenes() {
	
	modalImagenesShow($('#idRendicion').html(), false, $('#urlThuban').val() );
}