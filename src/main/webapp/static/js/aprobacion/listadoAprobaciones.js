let dtLink = 'listadoAprobaciones.do';
let paramsAprobarRendiciones;
let selRendiciones = {};
let check=false;

$(document).ready(function() {
	
	$('.nav-aprobacion').addClass('active');
	dtParams = { action: 'filtrar' };
	//setCombo('combos.do?action=getMotivos', '#filtroMotivo', { opcion: ['1', '2'].indexOf($('#glg').val()) == -1 ? 9 : 8});
	setCombo('combos.do?action=getMotivos', '#filtroMotivo', { opcion: 4});
	filtrar();
		
	$('.basic-single2').chosen();
	$('#filtroMotivo').trigger("chosen:updated");
	setTipoAprobacionOculta()
	
	 
	
});


function filtrar() {
	dtParams.glg = $('#glg').val();
	dtParams.idRendicion = $('#filtroId').val();
	dtParams.usuario = $('#filtroUsuario').val();
	dtParams.motivo = $('#filtroMotivo').val();
	dtParams.nroAlerta = $("#filtroAlerta").val()
	loadAprobacionesTable(true);
}

function loadAprobacionesTable(showMessage) {
	dtParams.showMessage = showMessage;
	selRendiciones = {};
	loadTable('#aprobacionesDtContainer', dtLink, dtParams);
}

function seleccionarTodo(){
	if(!check){
		document.querySelectorAll('.seleccionar-todo').forEach(function(checkElement) {
        $(checkElement).prop( "checked", true );  
        selRendiciones[$(checkElement).val()] = $(checkElement).val();
        check=true;

    	});
	}else{
		deseleccionarTodo()
	}
	
}

function deseleccionarTodo(){
		check= false;
		document.querySelectorAll('.seleccionar-todo').forEach(function(checkElement){
        $(checkElement).prop( "checked", false );
    	});
    	selRendiciones = [];
}



function limpiar() {
	$('input[id^=filtro]').val('');
	$('#filtroMotivo').val('');
	$('#filtroAlerta').val('')
	AutoNumeric.getAutoNumericElement('input[id^=filtro].an-integer-pos').set('');
	
	scrollToElem('#divFiltro', false);
}

function tableAfterLoad() {
	$.each(selRendiciones, function(i, v) {
		$('input[type=checkbox][value=' + i + ']').prop('checked', true);
	});
}

function clickCheckbox(idRendicion, checkbox) {
	if ($(checkbox).prop('checked')){
		selRendiciones[idRendicion] = idRendicion;
		}
	else
		delete selRendiciones[idRendicion];
}


function aprobarRendiciones() {
	$('#messageContainer').addClass('d-none');
	$('#aprobacionesTableMessage').addClass('d-none');
	
	if (Object.keys(selRendiciones).length == 0) {
		showMessage2('#aprobacionesTableMessage', 'Ten&eacute;s que seleccionar al menos una rendici&oacute;n.', 'error');
		scrollToElem('#aprobacionesTableMessage', true);
		return;
	}
	
	paramsAprobarRendiciones = {
		action: 'aprobar',
		idRendiciones: JSON.stringify(Object.keys(selRendiciones)),
		glg : $('#glg').val()
	};
	showConfirm('aprobarRendicionesConfirm', '&iquest;Est&aacute;s seguro que quer&eacute;s aprobar las rendiciones seleccionadas?');
}

function aprobarRendicionesConfirm() {
	callAjax('listadoAprobaciones.do', paramsAprobarRendiciones, 'aprobarRendicionesConfirmSuccess', 'aprobarRendicionesConfirmError');
}

function aprobarRendicionesConfirmSuccess(data) {
	showMessage('tableMessage', data.message);
	$('#modalConfirm').modal('hide');
	loadAprobacionesTable(false);
}

function aprobarRendicionesConfirmError(data) {
	showMessage('tableMessage', data.error);
	$('#modalConfirm').modal('hide');
}

function showThuban(link, rend) {
	window.open(link);	
}


// Modal de detalle alerta

function obtenerDetalleAlerta(id, adea){
	modalAlertaShow(id, adea.toString())
	
}


function setTipoAprobacionOculta(){
	let glgOculta = $('#glg').val();
	let tipoAprobacion = document.getElementById('aprobacionOculta')
	switch(glgOculta){
		case "1":
		
		tipoAprobacion.innerHTML = '<span class="text-dark"> APROBACI&Oacute;N - SUPERVISOR </span>'
		break;
		case "2":
		tipoAprobacion.innerHTML = '<span class="text-dark"> APROBACI&Oacute;N - FIRMA </span>'
		break;
		case "3":
		tipoAprobacion.innerHTML = '<span class="text-dark"> APROBACI&Oacute;N - GLG </span>'
		break;
		case "4":
		tipoAprobacion.innerHTML = '<span class="text-dark"> APROBACI&Oacute;N - GLG (ENTRADA) </span>'
		break;
		default:
		tipoAprobacion.textContent = 'APROBACI&Oacute;N';
	}
}

