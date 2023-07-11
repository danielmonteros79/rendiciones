var modalCuponesLoadParams;
var modalCuponesSubmitted;
var modalCuponesCupones;

function modalCuponesShow(idRendicion, codMotivo, fechaDesde, fechaHasta, idGasto) {
	modalCuponesSubmitted = false;
	modalCuponesCupones = {};
	$('#modalCuponesMessage').addClass('d-none');
	
	$('#modalCuponesTitulo').html(idGasto != null ? 'Cup\u00f3n asignado al gasto' : 'Asignar cup\u00f3n');
	
	modalCuponesLoadParams = {
		idRendicion: idRendicion,
		codMotivo: codMotivo,
		fechaDesde: fechaDesde,
		fechaHasta: fechaHasta,
		idGasto: idGasto
	};
	
	modalCuponesLoad();
}

function modalCuponesLoad() {
	callAjax('cuponesPopup.do', modalCuponesLoadParams, 'modalCuponesLoadSuccess');
}

function modalCuponesLoadSuccess(data) {
	showMessage('#modalCuponesMessage', data.message);
	modalCuponesSetTabla(data);

	$('#modalCuponesBtnContinuar').toggleClass('d-none', modalCuponesLoadParams.idGasto != null);

	$('#modalCupones').modal('show');
}

function modalCuponesSetTabla(data) {
	$('#modalCuponesTabla tbody tr').remove();

	$('#modalCuponesTabla').toggleClass('d-none', !data.filas);
	$('#modalCuponesTablaVacia').toggleClass('d-none', data.filas.length > 0);

	$('#modalCuponesThTarjeta').toggleClass('d-none', modalCuponesLoadParams.idGasto != null);
	$('#modalCuponesThMontoUtiliz').toggleClass('d-none', modalCuponesLoadParams.idGasto != null);
	$('#modalCuponesThSel').toggleClass('d-none', modalCuponesLoadParams.idGasto != null);
	
	$(data.filas).each(function(i, fila) {
		modalCuponesCupones[i] = fila;
		var cols = '<td class="' + (modalCuponesLoadParams.idGasto != null ? ' d-none' : '') + '">' + fila.nroTarjetaCliente + '</td>';
		cols += '<td class="text-center">' + formatDate(fila.fechaPresentacionDate.time) + '</td>';
		cols += '<td>' + fila.nroCupon + '</td>';
		cols += '<td>' + fila.establecimiento + '</td>';
		cols += '<td class="text-right nowrap">' + formatCurrency(fila.liquidacionNeto) + '</td>';
		cols += '<td class="text-right nowrap' + (modalCuponesLoadParams.idGasto != null ? ' d-none' : '') + '">' + formatCurrency(fila.montoUtilizado) + '</td>';
		cols += '<td>' + fila.moneda + '</td>';
		cols += '<td class="' + (modalCuponesLoadParams.idGasto != null ? 'd-none' : '') + '">' +
			'<input type="radio" name="modalCuponesRadioCupon" value="' + i + '"/></td>';
		
		$('#modalCuponesTabla tbody').append('<tr>' + cols + '</tr>');
	});

	modalCuponesSetOnChanges();
}

function modalCuponesSetOnChanges() {
	$('input[name=modalCuponesRadioCupon]').change(function() {
		if (modalCuponesSubmitted)
			modalCuponesValidarContinuar();
	});
}

function modalCuponesContinuar() {
	modalCuponesSubmitted = true;
	
	if (!modalCuponesValidarContinuar())
		return;
	
	var selCupon = modalCuponesCupones[$('input[name=modalCuponesRadioCupon]:checked').val()];

	$('#modalCupones').modal('hide');
	modalGastoShow('create', modalCuponesLoadParams.idRendicion, null, modalCuponesLoadParams.codMotivo, selCupon);
}

function modalCuponesValidarContinuar() {
	var msgValidacion = '';
	
	if (!$('input[name=modalCuponesRadioCupon]:checked').val())
		msgValidacion = 'Ten\u00e9s que seleccionar un cup\u00f3n';

	showMessage('#modalCuponesMessage', msgValidacion, 'error');
	
	if (msgValidacion != '')
		scrollToElem('#modalCuponesMessage', false, '#modalCupones', '.row');
	
	return msgValidacion == '';
}