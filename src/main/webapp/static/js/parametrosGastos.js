
let dtLink = 'parametrosGastos.do'


$(document).ready(function() {
	$('#checkParametros').show();
	$('.nav-parametros').addClass('active');
	//dtParams = { action: 'filtrar' };
	console.log(codigo);
	dtParams = { action: 'filtrar', motivo: codigo };

	filtrar()
});

//$(document).ready(function() {
	//showMessage('message', getLocalStorageItem('message'));
	//$('.nav-parametros').addClass('active');
	//dtParams = { action: 'filtrar' };
	//filtrar();
//});

function filtrar() {
	dtParams.gasto = $('#gasto').val();
	loadGastos();
	console.log("filtro")
}

function resetForm() {
	$(".message").html("");
	$("#gasto").val("");
}

function agregarGasto() {
	$("#addGasto").submit();
}

function modificarGasto(codigo) {
	$("#edit_" + codigo).submit();
}

function eliminarGasto(codigo) {
	$("#delete_" + codigo).submit();
}


function limpiar() {
	$(".message").html("");
	$("#gasto").val("");
	$('input[id^=filtro]').val('');
	$('input[id^=filtro]').removeClass('text-danger');
	AutoNumeric.getAutoNumericElement('input[id^=filtro].an-integer-pos').set('');
	validarFiltro();
	
	scrollToElem('#divFiltro', false);
}

function loadGastos() {
	loadTable('#parametrosGastosDtContainer', dtLink, dtParams);
}
