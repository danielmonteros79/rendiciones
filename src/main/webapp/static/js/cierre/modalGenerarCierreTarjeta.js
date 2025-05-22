var modalGenerarCierreTarjetaFirstLoad = true;
var modalGenerarCierreTarjetaSubmitted = false;
var modalGenerarCierreTarjetaLoadParams;

function modalGenerarCierreTarjetaShow(idConsumos, usuario, totalPesos, totalDolares) {
	modalGenerarCierreTarjetaSubmitted = false;
	clearFormErrors('#modalGenerarCierreTarjeta');
	
	modalGenerarCierreTarjetaLoadParams = {
		idConsumos: idConsumos,
		usuario: usuario,
		totalPesos: totalPesos,
		totalDolares: totalDolares
	};
	
	if (modalGenerarCierreTarjetaFirstLoad) {
		modalGenerarCierreTarjetaFirstLoad = false;
		modalGenerarCierreTarjetaSetCombos();
		modalGenerarCierreTarjetaSetOnChanges();
	}

	$('#modalGenerarCierreTarjetaTipoGastoDiv').hide();
	$('[id^=modalGenerarCierreTarjeta]').val('');
	
	$('#modalGenerarCierreTarjeta').modal('show');
}

function modalGenerarCierreTarjetaSetCombos() {
	setCombo('combos.do?action=getMotivos', '#modalGenerarCierreTarjetaMotivo', { opcion: 4, glg: ""});
}

function modalGenerarCierreTarjetaSetOnChanges() {
	$('input[id^=modalGenerarCierreTarjeta],textarea[id^=modalGenerarCierreTarjeta],select[id^=modalGenerarCierreTarjeta]').change(function() {
		if (modalGenerarCierreTarjetaSubmitted)
			validateForm('modalGenerarCierreTarjeta', false);
	});
	
	$('#modalGenerarCierreTarjetaMotivo').change(function() {
		var selCodMotivo = $(this).val();
		
		if (selCodMotivo) {
			var params = {
				codMotivo: selCodMotivo
			};
			setCombo('combos.do?action=getTiposGasto', '#modalGenerarCierreTarjetaTipoGasto', params);
			
			$('#modalGenerarCierreTarjetaTipoGastoDiv').fadeIn('slow');
		} else
			$('#modalGenerarCierreTarjetaTipoGastoDiv').fadeOut();
	});
}

function modalGenerarCierreTarjetaGenerar() {
	modalGenerarCierreTarjetaSubmitted = true;
	$('#modalGenerarCierreTarjetaMessageContainer').addClass('d-none');
	
	if (!validateForm('modalGenerarCierreTarjeta', true))
		return;
	
	var params = {
		action: 'generar',
		usuario: modalGenerarCierreTarjetaLoadParams.usuario,
		idConsumos: modalGenerarCierreTarjetaLoadParams.idConsumos,
		totalPesos: modalGenerarCierreTarjetaLoadParams.totalPesos,
		totalDolares: modalGenerarCierreTarjetaLoadParams.totalDolares,
		motivo: $('#modalGenerarCierreTarjetaMotivo').val(),
		tipoGasto: $('#modalGenerarCierreTarjetaTipoGasto').val()
	};
	
	callAjax('cierreTarjeta.do', params, 'modalGenerarCierreTarjetaGenerarSuccess');
}

function modalGenerarCierreTarjetaGenerarSuccess(data) {
	showMessage2('#cierreTarjetaTableMessage', data.message);
	$('#modalGenerarCierreTarjeta').modal('hide');
	loadCierreTable();
}