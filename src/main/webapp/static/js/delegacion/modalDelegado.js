var modalDelegadoFirstLoad = true;
var modalDelegadoSubmitted = false;
var modalDelegadoLoadParams;
var modalDelegadoTipoComprobanteSel;


function modalDelegadoShow(id) {
	modalDelegadoSubmitted = false;
	clearFormErrors('#modalDelegado');
	
	modalDelegadoLoadParams = {
		id: id
	};
	
	if (modalDelegadoFirstLoad) {
		modalDelegadoFirstLoad = false;
		modalDelegadoSetOnChanges();
	}
	
	modalDelegadoLoad();
}

function modalDelegadoLoad() {
	if (modalDelegadoLoadParams.id != null) {
		var params = {
			action: 'getDelegado',
			id: modalDelegadoLoadParams.id
		}
		
		callAjax('abmDelegado.do', params, 'modalDelegadoLoadSuccess');
	} else
		modalDelegadoLoadSuccess({});
}

function modalDelegadoLoadSuccess(data) {
	modalDelegadoSetVisibility();
	modalDelegadoSetValues(data);
	
	$('#modalDelegado').modal('show');
}

function modalDelegadoSetCombos() {
	setCombo('combos.do?action=getTiposGasto', '#modalDelegadoTipoGasto', { codMotivo: modalDelegadoLoadParams.codMotivo });
	setCombo('combos.do?action=getMonedas', '#modalDelegadoMoneda', null, modalDelegadoLoadParams.cupon ? modalDelegadoLoadParams.cupon.moneda + " " : null);
}

function modalDelegadoSetOnChanges() {
	$('input[id^=modalDelegado],textarea[id^=modalDelegado],select[id^=modalDelegado]').change(function() {
		if (modalDelegadoSubmitted)
			validateForm('modalDelegado', false);
	});
	
	$('#modalDelegadoUsuario').change(function() {
		modalDelegadoBuscarUsuario($(this).val());
	});

	$('#modalDelegadoFechaDesde, #modalDelegadoFechaHasta').change(function() {
		modalDelegadoValidarFechas();
	});
	
	$('#modalDelegadoFechaHasta').change(function() {
		$('#modalDelegadoEstado').val($('#modalDelegadoFechaHasta').data('datepicker').getDate() < new Date() ? 'I' : 'A');
	});
}

function modalDelegadoBuscarUsuario(legajo) {
	if (legajo) {
		var params = {
			action: 'buscarUsuario',
			legajo: legajo
		};
		
		callAjax('abmDelegado.do', params, 'modalDelegadoBuscarUsuarioSuccess', 'modalDelegadoBuscarUsuarioError', false);
	}
}

function modalDelegadoBuscarUsuarioSuccess(data) {
	clearFormError($('#modalDelegadoUsuario').parent());
	$('#modalDelegadoCCostos').val(data.delegado.ccostos);
	$('#modalDelegadoSector').val(data.delegado.sector);
	$('#modalDelegadoNombre').val(data.delegado.nombre);
}

function modalDelegadoBuscarUsuarioError(data) {
	showFormError('#modalDelegadoUsuario', data.error);
	$('input[id^=modalDelegado]:disabled').val('');
}

function modalDelegadoValidarFechas() {
	if (compareDates($('#modalDelegadoFechaDesde').val(), $('#modalDelegadoFechaHasta').val()) == 1) {
		$('#modalDelegadoFechaDesde').parent().find('label, input').addClass('text-danger');
		$('#modalDelegadoFechaHasta').parent().find('label, input').addClass('text-danger');
		showFormError('#modalDelegadoValidacionFechas', 'La fecha hasta no puede ser menor a la fecha desde');
	} else {
		clearFormError($('#modalDelegadoFechaDesde').parent());
		clearFormError($('#modalDelegadoFechaHasta').parent());
		clearFormError($('#modalDelegadoValidacionFechas').parent());
	}
}

function modalDelegadoSetVisibility() {
	$('#modalDelegadoNuevoModif').html(modalDelegadoLoadParams.id ? 'Modificaci&oacute;n de' : 'Nuevo');
}

function modalDelegadoSetValues(data) {
	$('[id^=modalDelegado]').val('');
	$('#modalDelegadoInforme').val('S');
	$('#modalDelegadoAccion').val('I');

	
	if (modalDelegadoLoadParams.id != null) {

		$('#modalDelegadoUsuario').val(data.delegado.delegadoUser);
		$('#modalDelegadoCCostos').val(data.delegado.delegadoCentroCostos);
		$('#modalDelegadoSector').val(data.delegado.delegadoSector);
		$('#modalDelegadoEstado').val(data.delegado.delegadoEstado);
		$('#modalDelegadoNombre').val(data.delegado.delegadoNombre);
		$('#modalDelegadoInforme').val(data.delegado.delegadoInforme);
		$('#modalDelegadoAccion').val(data.delegado.delegadoAccion);
		$('#modalDelegadoFechaDesde').datepicker('setDate', new Date(data.delegado.feDesde.time));
		$('#modalDelegadoFechaHasta').datepicker('setDate', new Date(data.delegado.feHasta.time));
		$('#modalDelegadoFechaDesdeOld').val(formatDate(data.delegado.feDesde.time, 'yyyy-mm-dd'));
		$('#modalDelegadoFechaHastaOld').val(formatDate(data.delegado.feHasta.time, 'yyyy-mm-dd'));
		$('#modalDelegadoUserAlta').val(data.delegado.usuarioAlta);
		$('#modalDelegadoFechaAlta').val(data.delegado.fechaAlta.substring(0, 10));
	}
}

function modalDelegadoValidateForm() {
	modalDelegadoBuscarUsuario($('#modalDelegadoUsuario').val());
	modalDelegadoValidarFechas();
}

function modalDelegadoGuardar() {
	modalDelegadoSubmitted = true;
	
	if (!validateForm('modalDelegado', true, 'modalDelegadoValidateForm'))
		return;
	
	var params = {
		action: 'abm',
		opcion: modalDelegadoLoadParams.id ? 'MODI' : 'ALTA',
		//delegadoUser: modalDelegadoLoadParams.id,
		estado: $('#modalDelegadoEstado').val(),
		informe: $('#modalDelegadoInforme').val(),
		accion: $('#modalDelegadoAccion').val(),
		feDesde: $('#modalDelegadoFechaDesde').data('datepicker').getFormattedDate('yyyy-mm-dd'),
		feHasta: $('#modalDelegadoFechaHasta').data('datepicker').getFormattedDate('yyyy-mm-dd'),
		feDesdeOld: $('#modalDelegadoFechaDesdeOld').val(),
		feHastaOld: $('#modalDelegadoFechaHastaOld').val(),
		userAlta: $('#modalDelegadoUserAlta').val(),
		fechaAlta: $('#modalDelegadoFechaAlta').val(),
		delegadoUser: $('#modalDelegadoUsuario').val()
	};
	
	callAjax('abmDelegado.do', params, 'modalDelegadoGuardarSuccess');
}

function modalDelegadoGuardarSuccess(data) {
	showMessage('tableMessage', data.message);
	$('#modalDelegado').modal('hide');
	loadDelegadosTable();
}