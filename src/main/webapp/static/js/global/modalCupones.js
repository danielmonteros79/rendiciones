var modalCuponesLoadParams;
var modalCuponesSubmitted;
var modalCuponesCupones;
var modalCuponesCuponesSel = [];

function modalCuponesShow(idRendicion, estadoRend, codMotivo, fechaDesde, fechaHasta, idGasto, montoMin, moneda, verCupon) {
	modalCuponesSubmitted = false;
	modalCuponesCupones = {};
	
	$('#tableMessageContainer').addClass('d-none');
	
	$('#modalCuponesTitulo').html(verCupon ? 'Cup&oacute;n asignado al gasto' : idGasto == null ? 'Nuevo gasto con cup&oacute;n' : 'Asignar cup&oacute;n');
	
	modalCuponesLoadParams = {
		action: 'consulta',
		idRendicion: idRendicion,
		estadoRend: estadoRend,
		codMotivo: codMotivo,
		fechaDesde: fechaDesde,
		fechaHasta: fechaHasta,
		idGasto: idGasto,
		montoMin: montoMin,
		moneda: moneda,
		verCupon: verCupon
	};
	
	modalCuponesLoad();
}

function modalCuponesLoad() {
	callAjax('cuponesPopup.do', modalCuponesLoadParams, 'modalCuponesLoadSuccess');
}

function modalCuponesLoadSuccess(data) {
	showMessage('modalCuponesMessage', data.message);
	modalCuponesSetTabla(data);

	$('#modalCuponesBtnContinuar').toggleClass('d-none', modalCuponesLoadParams.verCupon || modalCuponesLoadParams.idGasto != null);
	$('#modalCuponesBtnAsignar').toggleClass('d-none', modalCuponesLoadParams.verCupon || modalCuponesLoadParams.idGasto == null);

	$('#modalCupones').modal('show');
}

function modalCuponesSetTabla(data) {
	$('#modalCuponesTabla tbody tr').remove();

	$('#modalCuponesTabla').toggleClass('d-none', data.filas.length == 0);
	$('#modalCuponesTablaVacia').toggleClass('d-none', data.filas.length > 0);

	$('#modalCuponesThTarjeta').toggleClass('d-none', modalCuponesLoadParams.verCupon);
	$('#modalCuponesThMontoUtiliz').toggleClass('d-none', modalCuponesLoadParams.verCupon);
	$('#modalCuponesThSel').toggleClass('d-none', modalCuponesLoadParams.verCupon);
	
	$(data.filas).each(function(i, fila) {
		modalCuponesCupones[i] = fila;
		var cols = '<td class="' + (modalCuponesLoadParams.verCupon ? ' d-none' : '') + '">' + fila.nroTarjetaCliente + '</td>';
		cols += '<td class="text-center">' + formatDate(fila.fechaPresentacionDate.time) + '</td>';
		cols += '<td>' + fila.nroCupon + '</td>';
		cols += '<td>' + fila.establecimiento + '</td>';
		cols += '<td class="text-right nowrap">' + formatCurrency(fila.liquidacionNeto) + '</td>';
		cols += '<td class="text-right nowrap' + (modalCuponesLoadParams.verCupon ? ' d-none' : '') + '">' + formatCurrency(fila.montoUtilizado) + '</td>';
		cols += '<td>' + fila.moneda + '</td>';
		cols += '<td class="' + (modalCuponesLoadParams.verCupon ? 'd-none' : '') + '">' +
			'<input type="' +  'radio' + '" name="modalCuponesCheckCupon" value="' + i + '"/></td>';
		
		$('#modalCuponesTabla tbody').append('<tr>' + cols + '</tr>');
	});

	modalCuponesSetOnChanges();
}

function modalCuponesSetOnChanges() {
	$('input[name=modalCuponesCheckCupon]').change(function() {
		if (modalCuponesSubmitted)
			modalCuponesValidarContinuar();
	});
}

function modalCuponesContinuar() {
	modalCuponesSubmitted = true;
	$('#modalGastoMessageContainer').addClass('d-none');
	
	if (!modalCuponesValidar())
		return;

	modalCuponesCuponesSel = [];
	$('input[name=modalCuponesCheckCupon]:checked').each(function(i, fila) {
		var cupon = modalCuponesCupones[$(fila).val()];
		cupon.esProxCupon = modalCuponesCuponesSel.length > 0;
		modalCuponesCuponesSel.push(cupon);
	});
	
	$('#modalCupones').modal('hide');
	modalGastoShow(modalCuponesLoadParams.idRendicion, modalCuponesLoadParams.estadoRend, null, modalCuponesLoadParams.codMotivo, modalCuponesCuponesSel[0]);
	modalCuponesCuponesSel.splice(0, 1);
}

function modalCuponesValidar() {
	var msgValidacion = '';
	
	if (!$('input[name=modalCuponesCheckCupon]:checked').val())
		msgValidacion = 'Ten\u00e9s que seleccionar al menos un cup\u00f3n';

	showMessage('modalCuponesMessage', msgValidacion, 'error');
	
	if (msgValidacion != '')
		scrollToElem('#modalCuponesMessage', false, '#modalCupones', '#modalCuponesMessageContainer');
	
	return msgValidacion == '';
}

function modalCuponesAsignar() {
	modalCuponesSubmitted = true;
	
	if (!modalCuponesValidar())
		return;
	
	var selCupon = modalCuponesCupones[$('input[name=modalCuponesCheckCupon]:checked').val()];
	
	var params = {
		action: 'asignar',
		idRendicion: modalCuponesLoadParams.idRendicion,
		idGasto: modalCuponesLoadParams.idGasto,
		codMotivo: modalCuponesLoadParams.codMotivo,
		nroTarjeta: selCupon.nroTarjeta,
		cupon: selCupon.nroCupon,
		cupDeb: selCupon.nroCuponDebito,
		cupCred: selCupon.nroCuponCredito,
		descCupon: selCupon.establecimiento,
		fechaPresentacion: selCupon.fechaPresentacion
	};
	callAjax('cuponesPopup.do', params, 'modalCuponesAsignarSuccess');
}

function modalCuponesAsignarSuccess(data) {
	$('#modalCupones').modal('hide');
	setLocalStorageItem('tableMessage', data.message);
	loadGastosTable();
}