var modalGastosFirstLoad = true;
var modalGastosSubmitted = false;
var modalGastosLoadParams;
var modalGastosTipoComprobanteSel;

function modalGastosShow(id) {
	modalGastosSubmitted = false;
	clearFormErrors('#modalGastos');
	
	modalGastosLoadParams = {
		id: id
	};
	
	if (modalGastosFirstLoad) {
		modalGastosFirstLoad = false;
		modalGastosSetOnChanges();
	}
	
	modalGastosLoad();
}

function modalGastosLoad() {
	if (modalGastosLoadParams.id != null) {
		var params = {
			action: 'getDelegado',
			id: modalGastosLoadParams.id
		}
		
		callAjax('abmDelegado.do', params, 'modalGastosLoadSuccess');
	} else
		modalGastosLoadSuccess({});
}

function modalGastosLoadSuccess(data) {
	modalGastosSetVisibility();
	modalGastosSetValues(data);
	
	$('#modalGastos').modal('show');
}

function modalGastosSetCombos() {
	setCombo('combos.do?action=getTiposGasto', '#modalGastosTipoGasto', { codMotivo: modalGastosLoadParams.codMotivo });
	setCombo('combos.do?action=getMonedas', '#modalGastosMoneda', null, modalGastosLoadParams.cupon ? modalGastosLoadParams.cupon.moneda + " " : null);
}

function modalGastosSetOnChanges() {
	$('input[id^=modalGastos],textarea[id^=modalGastos],select[id^=modalGastos]').change(function() {
		if (modalGastosSubmitted)
			validateForm('modalGastos', false);
	});
	
	$('#modalGastosUsuario').change(function() {
		modalGastosBuscarUsuario($(this).val());
	});

	$('#modalGastosFechaDesde, #modalGastosFechaHasta').change(function() {
		modalGastosValidarFechas();
	});
	
	$('#modalGastosFechaHasta').change(function() {
		$('#modalGastosEstado').val($('#modalGastosFechaHasta').data('datepicker').getDate() < new Date() ? 'I' : 'A');
	});
}

function modalGastosBuscarUsuario(legajo) {
	if (legajo) {
		var params = {
			action: 'buscarUsuario',
			legajo: legajo
		};
		
		callAjax('abmDelegado.do', params, 'modalGastosBuscarUsuarioSuccess', 'modalGastosBuscarUsuarioError', false);
	}
}




function modalGastosSetVisibility() {
	$('#modalGastosNuevoModif').html(modalGastosLoadParams.id ? 'Modificaci&oacute;n de' : 'Nuevo');
}

function modalGastosSetValues(data) {
	$('[id^=modalGastos]').val('');
	$('#modalGastosInforme').val('S');
	$('#modalGastosAccion').val('I');
	
	if (modalGastosLoadParams.id != null) {
		$('#modalGastosUsuario').val(data.delegado.delegadoUser);
		$('#modalGastosCCostos').val(data.delegado.delegadoCentroCostos);
		$('#modalGastosSector').val(data.delegado.delegadoSector);
		$('#modalGastosEstado').val(data.delegado.delegadoEstado);
		$('#modalGastosNombre').val(data.delegado.delegadoNombre);
		$('#modalGastosInforme').val(data.delegado.delegadoInforme);
		$('#modalGastosAccion').val(data.delegado.delegadoAccion);
		$('#modalGastosFechaDesde').datepicker('setDate', new Date(data.delegado.feDesde.time));
		$('#modalGastosFechaHasta').datepicker('setDate', new Date(data.delegado.feHasta.time));
		$('#modalGastosFechaDesdeOld').val(formatDate(data.delegado.feDesde.time, 'yyyy-mm-dd'));
		$('#modalGastosFechaHastaOld').val(formatDate(data.delegado.feHasta.time, 'yyyy-mm-dd'));
		$('#modalGastosUserAlta').val(data.delegado.usuarioAlta);
		$('#modalGastosFechaAlta').val(data.delegado.fechaAlta.substring(0, 10));
	}
}



function modalGastosGuardar() {
	modalGastosSubmitted = true;
	
	if (!validateForm('modalGastos', true, 'modalGastosValidateForm'))
		return;
	
	var params = {
		action: 'abm',
		opcion: modalGastosLoadParams.id ? 'MODI' : 'ALTA',
		delegadoUser: modalGastosLoadParams.id,
		estado: $('#modalGastosEstado').val(),
		informe: $('#modalGastosInforme').val(),
		accion: $('#modalGastosAccion').val(),
		feDesde: $('#modalGastosFechaDesde').data('datepicker').getFormattedDate('yyyy-mm-dd'),
		feHasta: $('#modalGastosFechaHasta').data('datepicker').getFormattedDate('yyyy-mm-dd'),
		feDesdeOld: $('#modalGastosFechaDesdeOld').val(),
		feHastaOld: $('#modalGastosFechaHastaOld').val(),
		userAlta: $('#modalGastosUserAlta').val(),
		fechaAlta: $('#modalGastosFechaAlta').val()
	};
	
	callAjax('abmDelegado.do', params, 'modalGastosGuardarSuccess');
}

function modalGastosGuardarSuccess(data) {
	$('#modalGastos').modal('hide');
	loadDelegadosTable();
}