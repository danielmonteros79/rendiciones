
var paramsEliminarMotivo;


var dtLink = 'parametrosMotivoFiltro.do';
var paramsMotivos;



$(document).ready(function() {
	showMessage('message', getLocalStorageItem('message'));
	$('.nav-parametros').addClass('active');
	dtParams = { action: 'filtrar' };
	//setCombo('combos.do?action=getMotivos', '#filtroMotivo', { opcion: ['1', '2'].indexOf($('#glg').val()) == -1 ? 9 : 8});
	setCombo('combos.do?action=getMotivos', '#filtroMotivo', { opcion: 4});
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

var paramsEliminarMotivo;




//function loadMotivoTable() {
	//loadTable('#motivoDtContainer', dtLink, dtParams);
//}


function limpiar() {
	$(".message").html("");
	$("#codigo").val("");
}
