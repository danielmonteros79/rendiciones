
let paramsEliminarMotivo;
let dtLink = 'parametrosMotivo.do';
let paramsMotivos;

$(document).ready(function() {
	showMessage('message', getLocalStorageItem('message'));
	$('.nav-parametros').addClass('active');
	dtParams = { action: 'filtrar' };
	filtrar();
});

function filtrar() {
	dtParams.codigo = $('#codigo').val();
	loadMotivosTable(true);
}

function loadMotivosTable(showMessage) {
	dtParams.showMessage = showMessage;
	//selRendiciones = {};
	loadTable('#motivosDtContainer', dtLink, dtParams);
}



function resetForm() {
	$(".message").html("");
	$("#codigo").val("");
}

function agregarMotivo() {
	$("#addMotivo").submit();
}

function modificarMotivo(codMotivo) {
	$("#edit_" + codMotivo).submit();
}

function eliminarMotivo(codMotivo) {
	$("#delete_" + codMotivo).submit();
}



function limpiar() {
	$(".message").html("");
	$("#codigo").val("");
}
