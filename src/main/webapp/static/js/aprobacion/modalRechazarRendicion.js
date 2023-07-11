var modalRechazarRendicionFirstLoad = true;
var modalRechazarRendicionLoadParams;

function modalRechazarRendicionShow(idRendicion, glg) {
	clearFormErrors('#modalRechazarRendicion');
	
	modalRechazarRendicionLoadParams = {
		idRendicion: idRendicion,
		glg: glg
	};
	
	if (modalRechazarRendicionFirstLoad) {
		modalRechazarRendicionFirstLoad = false;
		setCombo('combos.do?action=getMotivos', '#modalRechazarRendicionMotivo', { opcion: 5});
	}
	
	$('#modalRechazarRendicion').modal('show');
}

function modalRechazarRendicionRechazar() {
	if (!validateForm('modalRechazarRendicion', true))
		return;
	
	var params = {
		action: 'rechazar',
		idRendicion: modalRechazarRendicionLoadParams.idRendicion,
		glg: modalRechazarRendicionLoadParams.glg,
		motivo: $('#modalRechazarRendicionMotivo').val(),
		descripcion: $('#modalRechazarRendicionDescripcion').val()
	};
	
	callAjax('aprobacionDetalle.do', params, 'modalRechazarRendicionRechazarSuccess');
}

function modalRechazarRendicionRechazarSuccess(data) {
	setLocalStorageItem('message', data.message);
	$('#modalRechazarRendicion').modal('hide');
	window.location.href = 'listadoAprobaciones.do?glg=' + modalRechazarRendicionLoadParams.glg;
}