//var dtLink = 'abmDelegado.do';
var paramsEliminarMotivo;


var dtLink = 'parametrosExceptuadosFiltro.do';
var paramsExceptuados;


$(document).ready(function() {
	showMessage('message', getLocalStorageItem('message'));
	$('.nav-parametros').addClass('active');
	dtParams = { action: 'filtrar' };
	$('.nav-parametros').addClass('active');
	var mu = $("input[name=motivoUsuario]:checked").val();
	if (mu == null)
		$("#exceptuadoFiltro").attr('readonly', true);
	//filtrar();
});

function filtrar() {
	
	$('#exceptuadoDivResultado').addClass('d-none');

	console.log($('#exceptuadoFiltro').val().toUpperCase());
	console.log( $("input[name=motivoUsuario]:checked").val().charAt(0).toUpperCase())
	dtParams.motivoUsuario = $('#exceptuadoFiltro').val().toUpperCase();
	dtParams.marca = $("input[name=motivoUsuario]:checked").val().charAt(0).toUpperCase();
	loadExceptuadosTable(true);
	
}

function loadExceptuadosTable(showMessage) {
	$('#exceptuadoDivResultado').removeClass('d-none');
	dtParams.showMessage = showMessage;
	//selRendiciones = {};
	loadTable('#exceptuadosDtContainer', dtLink, dtParams);
}




function resetForm() {
	$(".message").html("");
	$("#motivoUsuario").val("");
	$("input[name=motivoUsuario]").prop('checked', false);
	$("#exceptuadoFiltro").val("");
	$("#exceptuadoFiltro").attr('readonly', true);
}

function habilitar(){
	$("#exceptuadoFiltro").attr('readonly', false);
}



function agregarExceptuado() {
	$("#addExceptuado").submit();
}

function modificarExcepcion(motivoUsuario) {
	$("#edit_" + motivoUsuario).submit();
}

function confirmEliminarExcepcion(motivoUsuario) {
	$("#delete_" + motivoUsuario).submit();

}


function limpiar() {
	$('input[id^=filtro]').val('');
	$('input[id^=filtro]').removeClass('text-danger');
	AutoNumeric.getAutoNumericElement('input[id^=filtro].an-integer-pos').set('');
	validarFiltro();
	
	scrollToElem('#divFiltro', false);
}