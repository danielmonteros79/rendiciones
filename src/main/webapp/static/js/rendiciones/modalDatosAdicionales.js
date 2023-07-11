var modalDatosAdicionalesLoadParams;
var modalDatosAdicionalesSubmitted;

function modalDatosAdicionalesShow(idRendicion, idGasto, codMotivo, codObserv) {
	modalDatosAdicionalesSubmitted = false;
	clearFormErrors('#modalDatosAdicionales');
	
	modalDatosAdicionalesLoadParams = {
		idRendicion: idRendicion,
		idGasto: idGasto,
		codMotivo: codMotivo,
		codObserv: codObserv
	};
	
	modalDatosAdicionalesLoad();
}

function modalDatosAdicionalesLoad() {
	callAjax('descripcionObligatoriaPopup.do', modalDatosAdicionalesLoadParams, 'modalDatosAdicionalesLoadSuccess');
}

function modalDatosAdicionalesLoadSuccess(data) {
	modalDatosAdicionalesSetCampos(data);
	modalDatosAdicionalesSetTabla(data);
	modalDatosAdicionalesSetOnChanges();
	
	$('#modalDatosAdicionales').modal('show');
}

function modalDatosAdicionalesSetCampos(data) {
	$('#modalDatosAdicionalesCampos').html('');
	
	$(data.listCampos).each(function(i, elem) {
		var input = '';
		var required = elem.campoObligatorio == 'S' ? ' required' : '';
		var label = elem.tituloCampo.substring(0,1) + elem.tituloCampo.substring(1).toLowerCase();
		if (elem.tipoCampo.indexOf('TXT250') != -1)
			input = '<textarea class="form-control bg-light" placeholder="(250 caracteres)" id="' + elem.tipoCampo + '" rows="4" maxlength="250"' +
				required + '></textarea>';
		else if (elem.tipoCampo.indexOf('TXT') != -1)
			input = '<input class="form-control bg-light" placeholder="' + label + '" id="' + elem.tipoCampo + '" maxlength="50"' + required +'/>';
		else if (elem.tipoCampo.indexOf('NUM') != -1)
			input = '<input class="form-control bg-light an-integer-pos" placeholder="' + label + '" id="' + elem.tipoCampo + '"' + required +'/>';
		else if (elem.tipoCampo.indexOf('FEC') != -1)
			input = '<input class="form-control bg-light datepicker" placeholder="' + label + '" id="' + elem.tipoCampo + '"' + required +'/>';
		else if (elem.tipoCampo.indexOf('COD') != -1) {
			var opciones = globalOpcionVacia;
			$(elem.opcionesCombo).each(function(i, opcion) {
				opciones += '<option value="' + opcion.id + '">' + opcion.descripcion + '</option>';
			});
			input = '<select class="form-control bg-light" id="' + elem.tipoCampo + '"' + required +'>' + opciones + '</select>';
		}

		if (elem.tipoCampo.indexOf('COD') != -1)
			$('#modalDatosAdicionalesCampos').append(
				'<div class="col-sm-12 pt-2">' +
					'<div class="has-float-label form-group">' +
						input +
						'<i class="bbva-icon icon-uniE003 text-primary"></i>' +
						'<label for="' + elem.tipoCampo + '">' + label + '</label>' +
						'<div class="invalid-feedback mb-3"></div>' +
					'</div>' +
				'</div>');
		else if (elem.tipoCampo.indexOf('FEC') != -1)
			$('#modalDatosAdicionalesCampos').append(
				'<div class="col-sm-12 pt-2 has-float-label">' +
					'<div class="input-group">' +
						input +
						'<label for="' + elem.tipoCampo + '">' + label + '</label>' +
						'<div class="input-group-append">' +
							'<button class="btn btn-outline-primary bg-light border-white hover-darkblue datepicker-btn" tabindex="-1" type="button">' +
								'<i class="bbva-icon icon-coronita_calendar fa-lg"></i>' +
							'</button>' +
						'</div>' +
						'<div class="invalid-feedback mb-3"></div>' +
					'</div>' +
				'</div>');
		else
			$('#modalDatosAdicionalesCampos').append(
				'<div class="col-sm-12 pt-2">' +
					'<div class="has-float-label">' +
						input +
						'<label for="' + elem.tipoCampo + '">' + label + '</label>' +
						'<div class="invalid-feedback mb-3"></div>' +
					'</div>' +
				'</div>');
	});
	
	setAutonumericElementInteger('#modalDatosAdicionalesCampos .an-integer-pos', '999999999');
	setDatepickerElements();
}

function modalDatosAdicionalesSetTabla(data) {
	$('#modalDatosAdicionalesTabla th, #modalDatosAdicionalesTabla tbody tr').remove();
	$("#modalDatosAdicionalesTabla thead tr").append("<th>NRO</th>");
	
	if (data.filas)
		$(data.headers).each(function(i, elem) {
			$('#modalDatosAdicionalesTabla thead tr').append('<th>' + elem + '</th>');
		});
	
	$(data.filas).each(function(i, fila) {
		var cols = '<td>' + (i + 1) + '</td>';
		$(fila).each(function(i, col) {
			cols += '<td>' + col + '</td>';
		});
		$('#modalDatosAdicionalesTabla tbody').append('<tr>' + cols + '</tr>');
	});
}

function modalDatosAdicionalesSetOnChanges() {
	$('#modalDatosAdicionales input, #modalDatosAdicionales textarea, #modalDatosAdicionales select').change(function() {
		if (modalDatosAdicionalesSubmitted)
			validateForm('modalDatosAdicionales', false);
	});
}

function modalDatosAdicionalesGuardar() {
	modalDatosAdicionalesSubmitted = true;
	
	if (!validateForm('modalDatosAdicionales', true))
		return;
	
	var params = {};
	$('#modalDatosAdicionales input, #modalDatosAdicionales textarea, #modalDatosAdicionales select').each(function(i, elem) {
		params[$(elem).attr('id')] = $(elem).val();
	});
	callAjax('descripcionObligatoriaPopupSend.do', params, 'modalDatosAdicionalesGuardarSuccess');
}

function modalDatosAdicionalesGuardarSuccess(data) {
	modalDatosAdicionalesLoad();
}