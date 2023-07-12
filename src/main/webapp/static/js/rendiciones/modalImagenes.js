var modalImagenesLoadParams;
var modalImagenesSubmitted;
var modalImagenesCupones;

function modalImagenesShow(idRendicion) {
	modalImagenesSubmitted = false;
	modalImagenesCupones = {};
	$('#modalImagenesMsgValidacion').addClass('d-none');
	
	modalImagenesLoadParams = {
		idRendicion: idRendicion
	};

	callAjax('rendicionAviso.do', { action: 'inicializar' }, null);
	
	$('#modalImagenesTabla tbody tr').remove();
	$('#modalImagenesTablaVacia').toggleClass('d-none', false);

	$('#modalImagenes').modal('show');
}

function modalImagenesSetTabla(data) {
	$('#modalImagenesTabla tbody tr').remove();

	$('#modalImagenesTabla').toggleClass('d-none', !data.filas);
	$('#modalImagenesTablaVacia').toggleClass('d-none', data.filas);
	
	$(data.filas).each(function(i, fila) {
		modalImagenesCupones[i] = fila;
		var cols = '<td>' + fila.nroTarjetaCliente + '</td>';
		cols += '<td class="text-center">' + formatDate(fila.fechaPresentacionDate.time) + '</td>';
		cols += '<td class="text-right">' + fila.nroCupon + '</td>';
		cols += '<td>' + fila.establecimiento + '</td>';
		cols += '<td class="text-right nowrap">' + formatCurrency(fila.liquidacionNeto) + '</td>';
		cols += '<td class="text-right nowrap">' + formatCurrency(fila.montoUtilizado) + '</td>';
		cols += '<td>' + fila.moneda + '</td>';
		cols += '<td><input type="radio" name="modalImagenesRadioCupon" value="' + i + '"/></td>';
		
		$('#modalImagenesTabla tbody').append('<tr>' + cols + '</tr>');
	});
}

function modalImagenesSeleccionarArchivo() {
	$('#modalImagenesArchivo').click();
}

function modalImagenesAgregarArchivo() {
	clearFormErrors('#modalImagenes');
	
	var file = $('#modalImagenesArchivo').prop('files')[0];
	
	if (!modalImagenesValidarArchivo(file))
		return;
	
	var params = new FormData();
	params.append('action', 'cargarArchivo');
	params.append('archivo', file);
	
	callAjax('rendicionAviso.do', params, 'modalImagenesAgregarArchivoSuccess');
	
	$('#modalImagenesArchivo').val('');
}

function modalImagenesValidarArchivo(file) {
	if (['application/pdf', 'image/tiff'].indexOf(file.type) == -1)
		showFormError('#modalImagenesArchivo', 'Solo se pueden cargar archivos de tipo .PDF o .TIF.');
	else if (file.size > (1.2 * 1024 * 1024) && file.type == 'image/tiff')
		showFormError('#modalImagenesArchivo', 'El tama\u00f1o del archivo .TIF no debe superar los 1.2 MB.');
	else if ($('#modalImagenesTabla tbody tr td:contains(' + file.name + ')').length > 0)
		showFormError('#modalImagenesArchivo', 'Ya existe un archivo con ese nombre.');
	
	return validateFormHasError('modalImagenes', true);
}

function modalImagenesAgregarArchivoSuccess(data) {
	var cols = '<td>' + data.nombreArchivo + '</td>';
	cols += '<td><a href="#a" class="text-gray" onclick="modalImagenesBorrarArchivo(\'' + data.nombreArchivo + '\')">' +
			'<i class="bbva-icon icon-coronita_trash fa-lg" data-toggle="tooltip" title="Eliminar"></i>' +
		'</a></td>';
	
	$('#modalImagenesTabla tbody').append('<tr>' + cols + '</tr>');
	$('#modalImagenesTablaVacia').toggleClass('d-none', true);
	
	setTooltips();
}

function modalImagenesBorrarArchivo(nombreArchivo) {
	var params = {
		action: 'borrarArchivo',
		nombreArchivo: nombreArchivo
	};
	
	callAjax('rendicionAviso.do', params, 'modalImagenesBorrarArchivoSuccess');
}

function modalImagenesBorrarArchivoSuccess(data) {
	$('#modalImagenesTabla tbody tr td:contains(' + data.nombreArchivo + ')').parent().remove();
	$('#modalImagenesTablaVacia').toggleClass('d-none', $('#modalImagenesTabla tbody tr').length > 0);
	
	removeTooltip();
}

function modalImagenesGenerar() {
	modalImagenesSubmitted = true;
	
	if (!modalImagenesValidarGenerar())
		return;
	
	var params = {
		accion: 'generar'
	};

	callAjax('adjuntarImagenPopUp.do', params, 'modalImagenesGenerarSuccess');
}

function modalImagenesGenerarSuccess(data) {


	window.location.href = 'listadoRendiciones.do' ;
}

function modalImagenesValidarGenerar() {
	var msgValidacion = '';
	
	if ($('#modalImagenesTabla tbody tr td').length == 0)
		msgValidacion = 'Ten\u00e9s que seleccionar al menos un archivo';

	$('#modalImagenesMsgValidacion').html(msgValidacion);
	$('#modalImagenesMsgValidacion').toggleClass('d-none', msgValidacion == '');
	
	if (msgValidacion != '')
		scrollToElem('#modalImagenesMsgValidacion', false, '#modalImagenes', '.row');
	
	return msgValidacion == '';
}