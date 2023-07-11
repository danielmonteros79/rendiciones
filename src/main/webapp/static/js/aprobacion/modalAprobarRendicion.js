var modalAprobarRendicionFirstLoad = true;
var modalAprobarRendicionLoadParams;

function modalAprobarRendicionShow(idRendicion, glg) {
	clearFormErrors('#modalAprobarRendicion');
	
	modalAprobarRendicionLoadParams = {
		idRendicion: idRendicion,
		glg: glg
	};
	
	if (modalAprobarRendicionFirstLoad) {
		modalAprobarRendicionFirstLoad = false;
		setCombo('combos.do?action=getMotivos', '#modalObservarRendicionMotivo', { opcion: 4});
	}
	
	$('#modalAprobarRendicion').modal('show');
}

function modalAprobarRendicionAprobar() {
	var params = {
		action: 'aprobar',
		idRendicion: modalAprobarRendicionLoadParams.idRendicion,
		glg: modalAprobarRendicionLoadParams.glg,
		comentario: $('#modalAprobarRendicionComentario').val()
	};
	
	callAjax('aprobacionDetalle.do', params, 'modalAprobarRendicionAprobarSuccess');
}

function modalAprobarRendicionAprobarSuccess(data) {
	setLocalStorageItem('message', data.message);
	$('#modalAprobarRendicion').modal('hide');
	window.location.href = 'listadoAprobaciones.do?glg=' + modalAprobarRendicionLoadParams.glg;
}