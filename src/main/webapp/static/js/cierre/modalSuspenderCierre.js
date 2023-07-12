var modalSuspenderCierreFirstLoad = true;
var modalSuspenderCierreSubmitted = false;
var modalSuspenderCierreLoadParams;

function modalSuspenderCierreShow(idRendiciones) {
	modalSuspenderCierreSubmitted = false;
	clearFormErrors('#modalSuspenderCierre');
	
	modalSuspenderCierreLoadParams = {
		idRendiciones: idRendiciones
	};
	
	if (modalSuspenderCierreFirstLoad) {
		modalSuspenderCierreFirstLoad = false;
		modalSuspenderCierreSetCombos();
		modalSuspenderCierreSetOnChanges();
	}
	
	$('[id^=modalSuspenderCierre]').val('');
	
	$('#modalSuspenderCierre').modal('show');
}

function modalSuspenderCierreSetCombos() {
	setCombo('combos.do?action=getMotivos', '#modalSuspenderCierreMotivo', { opcion: 6 });
}

function modalSuspenderCierreSetOnChanges() {
	$('input[id^=modalSuspenderCierre],textarea[id^=modalSuspenderCierre],select[id^=modalSuspenderCierre]').change(function() {
		if (modalSuspenderCierreSubmitted)
			validateForm('modalSuspenderCierre', false);
	});
}

function modalSuspenderCierreSuspender() {
	modalSuspenderCierreSubmitted = true;
	$('#modalSuspenderCierreMessageContainer').addClass('d-none');
	
	if (!validateForm('modalSuspenderCierre', true))
		return;
	
	var params = {
		action: 'suspender',
		idRendiciones: modalSuspenderCierreLoadParams.idRendiciones,
		codMotivo: $('#modalSuspenderCierreMotivo').val(),
		descripcion: $('#modalSuspenderCierreDescripcion').val()
	};
	
	callAjax('cierreOrdenDePago.do', params, 'modalSuspenderCierreSuspenderSuccess');
}

function modalSuspenderCierreSuspenderSuccess(data) {
	showMessage2('#cierreOrdenDePagoTableMessage', data.message);
	$('#modalSuspenderCierre').modal('hide');
	loadCierreTable();
}