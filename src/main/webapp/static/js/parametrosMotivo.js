
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

function alphanumericOnly(event) {
    var key = event.which || event.keyCode;
    
    // Permitir teclas de control (backspace, delete, tab, escape, enter)
    if (key == 8 || key == 9 || key == 27 || key == 13 || key == 46) {
        return true;
    }
    
    // Permitir Ctrl+A, Ctrl+C, Ctrl+V, Ctrl+X
    if ((key == 65 || key == 67 || key == 86 || key == 88) && event.ctrlKey === true) {
        return true;
    }
    
    // Permitir números (0-9)
    if (key >= 48 && key <= 57) {
        return true;
    }
    
    // Permitir letras minúsculas (a-z)
    if (key >= 97 && key <= 122) {
        return true;
    }
    
    // Permitir letras mayúsculas (A-Z)
    if (key >= 65 && key <= 90) {
        return true;
    }
    
    // Permitir espacio
    if (key == 32) {
        return true;
    }
    
    // Bloquear cualquier otra tecla
    event.preventDefault();
    return false;
}

function limpiar() {
	$(".message").html("");
	$("#codigo").val("");
}
