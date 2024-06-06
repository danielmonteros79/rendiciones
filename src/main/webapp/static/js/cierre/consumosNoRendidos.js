let dtLink = 'consumosSinRendir.do';
let listaMotivosYGastos = [];
let nuevaRend = {}
$(document).ready(function() {
	setMoneda();
	let fechaActual = new Date();
	//Formatear fecha actual AAAA/MM/DD
	let fechaFormateada = fechaActual.toISOString().slice(0,10)
	dtParams = { action: 'filtrar', fechaCierre : fechaFormateada};
	$('#filtroMoneda').trigger("chosen:updated");
	loadConsumosNoRendidosTable();

	//loadConsumosNoRendidosTable()

});

function setMoneda() {
	setCombo('combos.do?action=getMonedas', '#filtroMoneda');
}

function filtrar() {
	if (!validarFiltro())
		return;
	let elementosFechas = $("#filtroCierre").val().replace('/','-').replace('/','-').split('-')
	let fecha_invertida = elementosFechas[2] + '-' +elementosFechas[1] + '-' + elementosFechas[0]
	dtParams.usuario = $('#filtroUsuario').val().trim();
	dtParams.montoMin = $('#filtroMontoMin').val().trim();
	dtParams.montoMax = $('#filtroMontoMax').val().trim();
	dtParams.moneda = $('#filtroMoneda').val().trim();
	dtParams.fechaCierre = fecha_invertida
	loadConsumosNoRendidosTable();
}

function limpiar() {
	$('input[id^=filtro]').val('');
	$('input[id^=filtro]').removeClass('text-danger');
	$('#filtroNombre').val("")
	$('#filtroMoneda').val("").trigger("chosen:updated");

}

function crearRendicion(usuario, motivo, fecha, descripcion, valorGasto){
	callAjax('consumosSinRendir.do', { action: 'altaRend', usuario: usuario,motivo:motivo,
	fecha:fecha, descripcion:descripcion}, 'altaRendOk');
	
	
}

	function agregarGasto(idRend){
		let params = {
		opcion:'ALTA',
		idRendicion: idRend.substring(1,17),
		idGasto: "000000001",
		codMotivo: nuevaRend.motivo,
		moneda: nuevaRend.moneda,
		tipoComprobante: "0001",
		gasto: nuevaRend.gasto,
		monto: nuevaRend.monto,
		fechaGasto: "2024-01-03",
		costosDestino: "0118",
		observacionGasto: "Gasto creado mediante consumos pendientes, Cierre",
	};
	
	callAjax('gastos.do', params, 'gastoGuardadoOK', 'gastoGuardadoError');
	nuevaRend ={}

	}
	
	
	function gastoGuardadoOK(data){
		console.log("OK: " + JSON.stringify(data))
	}
	
	function gastoGuardadoError(data){
		console.log("ERROR gasto: " +  JSON.stringify(data))
	}


function altaRendOk(data){
	let idRend = JSON.stringify(data.codigo);
	agregarGasto(idRend)
}

function altaRendError(data){
	console.log("ERROR :" + data)
}


function validarFiltro() {
	$('#messageContainer').addClass('d-none');
	$('#tableMessageContainer').addClass('d-none');
	var msgValidacion = '';
	$('input[id^=filtro]').removeClass('text-danger');

	
	return msgValidacion == '';
}



function loadConsumosNoRendidosTable() {
	loadTable('#consumosNoRendidosDtContainer', dtLink, dtParams);
}


function buscarUsuario(legajo) {
	if (legajo) {
		let params = {
			action: 'buscarUsuario',
			legajo: legajo,
		};
	 	callAjax('abmDelegado.do', params, 'buscarUsuarioLegajo', 'buscarUsuarioLegajoError', false)
	
}
}


  $('#filtroUsuario').blur(function() {
		buscarUsuario($('#filtroUsuario').val().trim().toUpperCase())
	 if ($('#filtroUsuario').val() == ""){
		$('#filtroNombre').val("")
		clearFormError($('#filtroUsuario').parent());
	}

 });

function buscarUsuarioLegajo(data) {
	clearFormError($('#filtroUsuario').parent());
	$('#filtroNombre').val(data.delegado.nombre)

	
}


function buscarUsuarioLegajoError(data) {
	showFormError('#filtroUsuario', data.error);
	$('#filtroNombre').val("")
	
}


let consumoNoRendido = {};
function clickCheckbox(user, description, fecha, monto, moneda) {
	consumoNoRendido = {"user":user, "description": description, "fecha":fecha, "monto":monto, "moneda":moneda}
	return consumoNoRendido;

}


function asignarConsumosNoRendidos() {

	let checkboxes = document.querySelectorAll('.seleccionar-todo:checked');
	
	checkboxes.forEach(function (checkbox, index) {
	    let fila = checkbox.closest('tr');
	    let motivo = fila.querySelector('.selectMotivosCP');
	    let gasto = fila.querySelector('.selectGastosCP');
	   	let monto = fila.querySelector('.monto');
	   	let fecha = fila.querySelector('.fecha');
	   	let usuario = fila.querySelector('.usuario');
	   	let descripcion = fila.querySelector('.descripcion');
		let moneda = fila.querySelector('.moneda');
		
		nuevaRend = {
			"motivo": motivo.value.slice(0,4).slice(0,4),
			 "gasto":gasto.value, 
			 "monto": monto.textContent.replace(/[\.,\$]/g, '').trim(), 
			 "fecha":fecha.textContent.replace(/\//g,'-'), 
			 "usuario": usuario.textContent.trim(), 
			 "descripcion":descripcion.textContent,
			 "moneda":moneda.textContent
		  }
		  
	    
	   // listaMotivosYGastos.push(nuevaRend)
		crearRendicion(nuevaRend.usuario, nuevaRend.motivo, "2024-01-03", nuevaRend.descripcion, nuevaRend.gasto)
	
	});



}


