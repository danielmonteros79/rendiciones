var modalObservarRendicionFirstLoad = true;
var modalObservarRendicionLoadParams;

function modalObservarRendicionShow(idRendicion, glg) {
	clearFormErrors('#modalObservarRendicion');
	
	modalObservarRendicionLoadParams = {
		idRendicion: idRendicion,
		glg: glg
	};
	
	if (modalObservarRendicionFirstLoad) {
		modalObservarRendicionFirstLoad = false;
		setCombo('combos.do?action=getMotivos', '#modalObservarRendicionMotivo', { opcion: 7});
	}
	
	$('#modalObservarRendicion').modal('show');
}

function modalObservarRendicionGuardar() {
	if (!validateForm('modalObservarRendicion', true))
		return;
	
	var params = {
		action: 'observar',
		idRendicion: modalObservarRendicionLoadParams.idRendicion,
		glg: modalObservarRendicionLoadParams.glg,
		motivo: $('#modalObservarRendicionMotivo').val(),
		descripcion: $('#modalObservarRendicionDescripcion').val()
	};
	
	callAjax('aprobacionDetalle.do', params, 'modalObservarRendicionGuardarSuccess');
}

function modalObservarRendicionGuardarSuccess(data) {
	setLocalStorageItem('message', data.message);
	$('#modalObservarRendicion').modal('hide');
	window.location.href = 'listadoAprobaciones.do?glg=' + modalObservarRendicionLoadParams.glg;
}