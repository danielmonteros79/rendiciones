var dtLink = 'parametrosGastos.do';


$(document).ready(function() {
	$('#checkParametros').show();
	$('.nav-parametros').addClass('active');
	setCombo('combos.do?action=getMotivos', '#motivo', { opcion: ['1', '2'].indexOf($('#glg').val()) == -1 ? 9 : 8});

});


function filtrar() {
	
	dtParams.gasto = $('#gasto').val();
	loadGastos();
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
	loadTable('#parametrosdGastosDtContainer', dtLink, dtParams);
}
