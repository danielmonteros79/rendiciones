var modalImagenesLoadParams;
var modalImagenesFirstLoad = true;

function modalImagenesShow(idRendicion, esAprobacion, urlThuban) {
	$('#modalImagenesMsgValidacion').addClass('d-none');
	
	modalImagenesLoadParams = {
		idRendicion: idRendicion,
		esAprobacion: esAprobacion,
		urlThuban: urlThuban
	};
	
	if (modalImagenesFirstLoad) {
		modalImagenesFirstLoad = false;
		modalImagenesOnChanges();
	}

	var params = {
		action: 'inicializar',
		idRendicion: idRendicion
	};
	callAjax('imagenes.do', params, 'modalImagenesInit');
}

function modalImagenesInit(data) {
	$('#modalImagenesTabla tbody tr').remove();
	$('#modalImagenesTablaVacia').toggleClass('d-none', false);
	$('#modalImagenesNavLinkAdjuntarImagenes').tab('show');

	modalImagenesSetTablaArchivosSubidos(data);

	$('#modalImagenes').modal('show');
}

function modalImagenesOnChanges() {
	$('a[id^=modalImagenes][data-toggle="tab"]').on('shown.bs.tab', function (e) {
		$('#modalImagenesBtnGenerar').toggleClass('d-none', e.target.id == 'modalImagenesNavLinkVerImagenes');
	});
}

function modalImagenesSetTablaArchivosSubidos(data) {
	$('#modalImagenesTablaArchivosSubidos tbody tr').remove();
	
	$('#modalImagenesTablaArchivosSubidos').toggleClass('d-none', data.archivos.length == 0);
	$('#modalImagenesTablaArchivosSubidosVacia').toggleClass('d-none', data.archivos.length > 0);
	
	$(data.archivos).each(function(i, fila) {
		var cols = '<td>' + fila.nomArchivo + '</td>';
		cols += '<td class="text-center">' +
				'<a href="#a" onclick=\"modalImagenesVer(' + fila.idu + ')">' +
					'<i class="bbva-icon icon-coronita_search" data-toggle="tooltip" title="Ver"></i>'+
				'</a>' +
			'</td>';
		
		$('#modalImagenesTablaArchivosSubidos tbody').append('<tr>' + cols + '</tr>');
	});
}

function modalImagenesSeleccionarArchivo() {
	$('#modalImagenesArchivo').click();
}

function modalImagenesAgregarArchivo() {
	clearFormErrors('#modalImagenes');

	$($('#modalImagenesArchivo').prop('files')).each(function(i, file) {
		if (!modalImagenesValidarArchivo(file))
			return;
		const reader = new FileReader();	
		let base64Content = "";
		reader.onload = function (e){
			base64Content = e.target.result.split(',')[1];
		
		}
		reader.readAsDataURL(file)
		let params = new FormData();
		

		setTimeout(function() {
			params.append('nombreArchivo', file.name)
			params.append('tipoArchivo', file.type)
			params.append('archivo', file)
			params.append('base64', base64Content)
			params.append('action', 'cargarArchivo');
		
	
			callAjax('imagenes.do', params, 'modalImagenesAgregarArchivoSuccess', 'modalImagenesAgregarArchivoError');
		}, 600);
			
		
	});
	
	$('#modalImagenesArchivo').val('');
}

function modalImagenesValidarArchivo(file) {
	if (['application/pdf', 'image/tiff'].indexOf(file.type) == -1)
		showFormError('#modalImagenesArchivo', file.name + ': Solo se pueden cargar archivos de tipo .PDF o .TIF.', true);
	else if (file.size > (1.2 * 1024 * 1024) && file.type == 'image/tiff')
		showFormError('#modalImagenesArchivo', file.name + ': El tama\u00f1o del archivo .TIF no debe superar los 1.2 MB.', true);
	else if ($('#modalImagenesTabla tbody tr td:contains(' + file.name + ')').length > 0)
		showFormError('#modalImagenesArchivo', file.name + ': Ya existe un archivo con ese nombre.', true);
	
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

function modalImagenesAgregarArchivoError(data) {
	showFormError('#modalImagenesArchivo', data.error, true);
}

function modalImagenesBorrarArchivo(nombreArchivo) {
	var params = {
		action: 'borrarArchivo',
		nombreArchivo: nombreArchivo
	};
	
	callAjax('imagenes.do', params, 'modalImagenesBorrarArchivoSuccess');
}

function modalImagenesBorrarArchivoSuccess(data) {
	$('#modalImagenesTabla tbody tr td:contains(' + data.nombreArchivo + ')').parent().remove();
	$('#modalImagenesTablaVacia').toggleClass('d-none', $('#modalImagenesTabla tbody tr').length > 0);
	
	removeTooltip();
}

function modalImagenesGenerar() {
	if (!modalImagenesValidarGenerar())
		return;
	
	var params = {
		action: 'generar',
		esAprobacion: modalImagenesLoadParams.esAprobacion,
		idRendicion: modalImagenesLoadParams.idRendicion,
		motivo: modalImagenesLoadParams.motivo,
	};

	callAjax('imagenes.do', params, 'modalImagenesGenerarSuccess');
}

function modalImagenesGenerarSuccess(data) {
	window.location.href = 'listadoRendiciones.do';
	setLocalStorageItem('message', data.message);
	//location.reload();
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

function modalImagenesOpenThuban() {
	window.open(modalImagenesLoadParams.urlThuban, '_blank');
}