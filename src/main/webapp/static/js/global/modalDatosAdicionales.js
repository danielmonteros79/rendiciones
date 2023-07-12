var modalDatosAdicionalesLoadParams;
var modalDatosAdicionalesSubmitted;
var modalDatosAdicionalesAction;
var modalDatosAdicionalesCampos;
var modalDatosAdicionalesParamsEliminar;

$('#modalDatosAdicionales').on('hidden.bs.modal', function (event) {
	if (modalCuponesCuponesSel.length > 0)
		modalGastoShowProximoGastoCupon({});
});

function modalDatosAdicionalesShow(idRendicion, codMotivo, idGasto, codGasto, codObserv, readOnly, message) {
	$('#tableMessageContainer').addClass('d-none');
	$('#modalDatosAdicionalesActionMessageContainer').addClass('d-none');
	clearFormErrors('#modalDatosAdicionales');
	
	modalDatosAdicionalesLoadParams = {
		action: 'consulta',
		idRendicion: idRendicion,
		codMotivo: codMotivo,
		idGasto: idGasto,
		codGasto: codGasto,
		codObserv: codObserv,
		readOnly: readOnly,
		message: message
	};
	
	modalDatosAdicionalesLoad();
}

function modalDatosAdicionalesLoad() {
	callAjax('datosAdicionales.do', modalDatosAdicionalesLoadParams, 'modalDatosAdicionalesLoadSuccess');
}

function modalDatosAdicionalesLoadSuccess(data) {
	if(data.message.includes('CLAVE PARCIAL NO EXISTE')) data.message=""
	showMessage('modalDatosAdicionalesMessage', (modalDatosAdicionalesLoadParams.message != null ? modalDatosAdicionalesLoadParams.message + '<br>' : '') + 
			(data.message != null ? data.message : ''));
	modalDatosAdicionalesLoadParams.message = null;

	modalDatosAdicionalesAction = 'new';
	modalDatosAdicionalesSetVisibility();
	modalDatosAdicionalesSetTabla(data);
	
	if (!modalDatosAdicionalesLoadParams.readOnly) {
		modalDatosAdicionalesSetCampos(data);
		modalDatosAdicionalesSetOnChanges();
	}
	
	$('#modalDatosAdicionales').modal('show');
}

function modalDatosAdicionalesSetVisibility() {
	modalDatosAdicionalesSubmitted = false;
	
	$('#modalDatosAdicionales .d-' + (modalDatosAdicionalesLoadParams.readOnly ? '' : 'not-') + 'readonly').removeClass('d-none');
	$('#modalDatosAdicionales .d-new').toggleClass('d-none', modalDatosAdicionalesAction != 'new');
	$('#modalDatosAdicionales .d-edit').toggleClass('d-none', modalDatosAdicionalesAction != 'edit');
}

function modalDatosAdicionalesSetCampos(data) {
	modalDatosAdicionalesCampos = data;
	$('#modalDatosAdicionalesCampos').html('<input type="hidden" id="modalDatosAdicionales_IDOBS"/>');
	
	$(data.listCampos).each(function(i, elem) {
		var input = '';
		var required = elem.campoObligatorio == 'S' ? ' required' : '';
		var label = elem.tituloCampo.substring(0,1) + elem.tituloCampo.substring(1).toLowerCase();
		if (elem.tipoCampo.indexOf('TXT250') != -1)
			input = '<textarea class="form-control bg-light" placeholder="(250 caracteres)" id="modalDatosAdicionales_' + elem.tipoCampo + 
				'" rows="4" maxlength="250"' + required + '></textarea>';
		else if (elem.tipoCampo.indexOf('TXT') != -1)
			input = '<input class="form-control bg-light" placeholder="' + label + '" id="modalDatosAdicionales_' + elem.tipoCampo + 
				'" maxlength="50"' + required +'/>';
		else if (elem.tipoCampo.indexOf('NUM') != -1)
			input = '<input class="form-control bg-light an-integer-pos" placeholder="' + label + '" id="modalDatosAdicionales_' + elem.tipoCampo + '"' +
				required +'/>';
		else if (elem.tipoCampo.indexOf('FEC') != -1)
			input = '<input class="form-control bg-light datepicker" placeholder="' + label + '" id="modalDatosAdicionales_' + elem.tipoCampo + '"' +
				required +'/>';
		else if (elem.tipoCampo.indexOf('COD') != -1) {
			var opciones = globalOpcionVacia;
			$(elem.opcionesCombo).each(function(i, opcion) {
				opciones += '<option value="' + opcion.id + '">' + opcion.descripcion.slice(7) + '</option>';

			});
			input = '<select class="form-control bg-light" id="modalDatosAdicionales_' + elem.tipoCampo + '"' + required +'>' + opciones + '</select>';
		}

		if (elem.tipoCampo.indexOf('COD') != -1)
			$('#modalDatosAdicionalesCampos').append(
				'<div class="col-sm-12 pt-2">' +
					'<div class="has-float-label form-group">' +
						input +
						'<i class="bbva-icon icon-uniE003 text-primary"></i>' +
						'<label>' + label + '</label>' +
						'<div class="invalid-feedback mb-3"></div>' +
					'</div>' +
				'</div>');
		else if (elem.tipoCampo.indexOf('FEC') != -1)
			$('#modalDatosAdicionalesCampos').append(
				'<div class="col-sm-12 pt-2 has-float-label">' +
					'<div class="input-group">' +
						input +
						'<label>' + label + '</label>' +
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
						'<label>' + label + '</label>' +
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
	
	if (data.filas) {
		$(data.headers).each(function(i, elem) {
			$('#modalDatosAdicionalesTabla thead tr').append('<th>' + elem + '</th>');
		});
		
		if (!modalDatosAdicionalesLoadParams.readOnly)
			$('#modalDatosAdicionalesTabla thead tr').append('<th>OPCIONES</th>');
		
		$(data.filas).each(function(i, fila) {
			var cols = '';
			$(fila).each(function(j, col) {
				var key = col.substring(0, col.indexOf('='));
				var value = col.substring(col.indexOf('=') + 1);
				cols += '<td id="modalDatosAdicionalesFila_' + i + '_' + key + '">' + value + '</td>';
			});
			
			if (!modalDatosAdicionalesLoadParams.readOnly) {
				cols += '<td>' + 
						'<a href="#a" class="text-gray" onclick="modalDatosAdicionalesEditar(' + i + ')">' +
							'<i class="bbva-icon icon-coronita_contract fa-lg" data-toggle="tooltip" title="Editar"></i>' +
						'</a>&nbsp;' +
						'<a href="#a" class="text-gray" onclick="modalDatosAdicionalesEliminar(' + i + ')">' +
							'<i class="bbva-icon icon-coronita_trash fa-lg" data-toggle="tooltip" title="Eliminar"></i>' +
						'</a>' +
					'</td>';
			}
			
			$('#modalDatosAdicionalesTabla tbody').append('<tr>' + cols + '</tr>');
		});
	}
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
	
	var params = jQuery.extend({}, modalDatosAdicionalesLoadParams);
	params.action = 'altaModif';
	$('#modalDatosAdicionales input, #modalDatosAdicionales textarea, #modalDatosAdicionales select').each(function(i, elem) {
		var id = $(elem).attr('id');
		params[id.substring(id.lastIndexOf('_') + 1)] = $(elem).val();
	});
	callAjax('datosAdicionales.do', params, 'modalDatosAdicionalesGuardarSuccess', 'modalDatosAdicionalesGuardarError');
}

function modalDatosAdicionalesGuardarSuccess(data) {
	showMessage('modalDatosAdicionalesActionMessage', data.message);
	modalDatosAdicionalesLoad();
}

function modalDatosAdicionalesGuardarError(data) {
	$('#modalDatosAdicionalesMessageContainer').addClass('d-none');
	showMessage('modalDatosAdicionalesActionMessage', data.error, 'error');
	scrollToElem('#modalDatosAdicionalesActionMessage', false, '#modalDatosAdicionales', '#modalDatosAdicionalesMessageContainer');
}

function modalDatosAdicionalesEditar(filaId) {
	$('[id^=modalDatosAdicionalesFila_' + filaId + ']').each(function(i, elem) {
		var id = $(elem).attr('id').replace('Fila_' + filaId, '');
		var campo = id.substring(22);
		var value = $(elem).html().trim();
		
		if (campo.indexOf('FEC') != -1)
			$('#' + id).datepicker('setDate', value);
		else if (campo.indexOf('NUM') != -1)
			AutoNumeric.getAutoNumericElement('#' + id).set(value);
		else
			$('#' + id).val(value);
	});
	
	modalDatosAdicionalesAction = 'edit';
	modalDatosAdicionalesSetVisibility();
}

function modalDatosAdicionalesCancelar() {
	modalDatosAdicionalesAction = 'new';
	modalDatosAdicionalesSetCampos(modalDatosAdicionalesCampos);
	modalDatosAdicionalesSetVisibility();
}

function modalDatosAdicionalesEliminar(filaId) {
	$('[id^=modalDatosAdicionalesFila_' + filaId + ']').each(function(i, elem) {
		var id = $(elem).attr('id').replace('Fila_' + filaId, '');
		var campo = id.substring(22);
		var value = $(elem).html().trim();
		
		if (campo.indexOf('FEC') != -1)
			$('#' + id).datepicker('setDate', value);
		else if (campo.indexOf('NUM') != -1)
			AutoNumeric.getAutoNumericElement('#' + id).set(value);
		else
			$('#' + id).val(value);
	});
	
	modalDatosAdicionalesAction = 'edit';
	modalDatosAdicionalesSetVisibility();
}

function modalDatosAdicionalesEliminar(filaId) {
	$('#modalDatosAdicionalesActionMessageContainer').addClass('d-none');
	
	modalDatosAdicionalesParamsEliminar = jQuery.extend({}, modalDatosAdicionalesLoadParams);
	modalDatosAdicionalesParamsEliminar.action = 'baja';
	modalDatosAdicionalesParamsEliminar.idObservacion = $('#modalDatosAdicionalesFila_' + filaId + '_IDOBS').html();
	
	showConfirm('modalDatosAdicionalesEliminarConfirm', '&iquest;Est&aacute;s seguro que quer&eacute;s eliminar el dato adicional?');
}

function modalDatosAdicionalesEliminarConfirm() {
	callAjax('datosAdicionales.do', modalDatosAdicionalesParamsEliminar, 'modalDatosAdicionalesEliminarConfirmSuccess');
}

function modalDatosAdicionalesEliminarConfirmSuccess(data) {
	showMessage('modalDatosAdicionalesActionMessage', data.message);
	$('#modalConfirm').modal('hide');
	modalDatosAdicionalesLoad();
}