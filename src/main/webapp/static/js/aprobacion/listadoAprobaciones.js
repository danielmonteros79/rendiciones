let dtLink = 'listadoAprobaciones.do';
let paramsAprobarRendiciones;
let selRendiciones = {};
let check=false;
	let supActual="";
$(document).ready(function() {
	
	$('.nav-aprobacion').addClass('active');
	dtParams = { action: 'filtrar' };
	setFiltros();
	//setCombo('combos.do?action=getMotivos', '#filtroMotivo', { opcion: ['1', '2'].indexOf($('#glg').val()) == -1 ? 9 : 8});
	$('.basic-single2').chosen();
	listadoAprobacionesSetCombo()
	$('#filtroSupervisado').on("change", function(){
	setCombo('combos.do?action=getMotivos', '#filtroMotivo', { opcion: 4, glg: $('#glg').val(), userActual: $('#filtroSupervisado').val() });
	setTimeout(() => {
		$('#filtroMotivo').trigger("chosen:updated");
	},1200);
	})
	filtrar();	
	setTipoAprobacionOculta()

});

function setFiltros() {
    const selectedValue = $('#glg').val();
    const isValueThreeOrFour = selectedValue === "3" || selectedValue === "4";
    
    const supervisadoContainer = $("#containerFiltroSupervisado");
    const usuarioContainer = $("#containerFiltroUsuario");
    const motivoContainer = $("#containerFiltroMotivo");
    const idContainer = $("#containerFiltroId");
    const alertaContainer = $("#containerFiltroAlerta");
    
    if (isValueThreeOrFour) {
        supervisadoContainer.addClass('d-none');
        supervisadoContainer.removeClass('col-lg-4').addClass('col-lg-3');
        usuarioContainer.removeClass('col-lg-6').addClass('col-lg-3');
        motivoContainer.removeClass('col-lg-4').addClass('col-lg-3');
        idContainer.removeClass('col-lg-6').addClass('col-lg-3');
        alertaContainer.removeClass('col-lg-4').addClass('col-lg-3');
        motivoContainer.removeClass('mt-lg-3');
        alertaContainer.removeClass('mt-lg-3');
    } else {
        supervisadoContainer.removeClass('d-none').removeClass('col-lg-3').addClass('col-lg-4');
        usuarioContainer.removeClass('col-lg-3').addClass('col-lg-6');
        motivoContainer.removeClass('col-lg-3 mt-sm-3 mt-lg-3').addClass('col-lg-4 mt-sm-3 mt-lg-3');
        idContainer.removeClass('col-lg-3').addClass('col-lg-6');
        alertaContainer.removeClass('col-lg-3 mt-sm-3 mt-lg-3').addClass('col-lg-4 mt-sm-3 mt-lg-3');
    }
}




function listadoAprobacionesSetCombo() {
	if($('#filtroSupervisado').val() === null){
		supActual = "";
	}else{
		supActual = $('#filtroSupervisado').val()
	}
	setCombo('combos.do?action=getMotivos', '#filtroMotivo', { opcion: 4, glg: $('#glg').val(), userActual: supActual });
	setCombo('combos.do?action=getSupervisados','#filtroSupervisado', { sector:"SUPE"} );
	
	setTimeout(() => {
		$('#filtroSupervisado').trigger("chosen:updated");
			$('#filtroMotivo').trigger("chosen:updated");
	},1200);
	

}


function filtrar() {

	dtParams.glg = $('#glg').val();
	dtParams.idRendicion = $('#filtroId').val();
	dtParams.usuario = $('#filtroUsuario').val();
	dtParams.motivo = $('#filtroMotivo').val();
	dtParams.nroAlerta = $("#filtroAlerta").val();
	dtParams.supervisado = $('#filtroSupervisado').val();
	
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

