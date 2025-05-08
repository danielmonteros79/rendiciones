var modalDatosAdicionalesLoadParams;
var modalDatosAdicionalesSubmitted;
var modalDatosAdicionalesAction;
var modalDatosAdicionalesCampos;
var modalDatosAdicionalesParamsEliminar;
let hayExcel = false;
let legajosData=[];
filteredData=[];
var params = jQuery.extend({}, modalDatosAdicionalesLoadParams);
let nombreUsuarioRend = $('#nombreUsuarioRend').val();

$('#modalDatosAdicionales').on('hidden.bs.modal', function (event) {
	if (modalCuponesCuponesSel.length > 0)
		modalGastoShowProximoGastoCupon({});
});

function setUsuarioDatoAdicional(nombreUser){
	nombreUsuarioRend=nombreUser;
}

function modalDatosAdicionalesShow(idRendicion, codMotivo, idGasto, codGasto, codObserv, gastoMonto ,readOnly, message) {
	$('#tableMessageContainer').addClass('d-none');
	$('#modalDatosAdicionalesActionMessageContainer').addClass('d-none');
	clearFormErrors('#modalDatosAdicionales');
	
	modalDatosAdicionalesLoadParams = {
		action: 'consulta',
		idRendicion: idRendicion,
		codMotivo: codMotivo,
		idGasto: idGasto,
		codGasto: codGasto,
		codObserv: codObserv,
		gastoMonto: gastoMonto,
		readOnly: readOnly,
		message: message
	};
	
	modalDatosAdicionalesLoad();
}

function modalDatosAdicionalesLoad() {
	callAjax('datosAdicionales.do', modalDatosAdicionalesLoadParams, 'modalDatosAdicionalesLoadSuccess');
}

function modalDatosAdicionalesLoadSuccess(data) {
	if(data.message.includes('CLAVE PARCIAL NO EXISTE')) data.message=""
	showMessage('modalDatosAdicionalesMessage', (modalDatosAdicionalesLoadParams.message != null ? modalDatosAdicionalesLoadParams.message + '<br>' : '') + 
			(data.message != null ? data.message : ''));
	modalDatosAdicionalesLoadParams.message = null;

	modalDatosAdicionalesAction = 'new';
	modalDatosAdicionalesSetVisibility();
	modalDatosAdicionalesSetTabla(data);
	
	if (!modalDatosAdicionalesLoadParams.readOnly) {
		modalDatosAdicionalesSetCampos(data);
		modalDatosAdicionalesSetOnChanges();
	}
	
	$('#modalDatosAdicionales').modal('show');
}

function modalDatosAdicionalesSetVisibility() {
	modalDatosAdicionalesSubmitted = false;
	
	$('#modalDatosAdicionales .d-' + (modalDatosAdicionalesLoadParams.readOnly ? '' : 'not-') + 'readonly').removeClass('d-none');
	$('#modalDatosAdicionales .d-new').toggleClass('d-none', modalDatosAdicionalesAction != 'new');
	$('#modalDatosAdicionales .d-edit').toggleClass('d-none', modalDatosAdicionalesAction != 'edit');
}

function modalDatosAdicionalesSetCampos(data) {
	modalDatosAdicionalesCampos = data;
	console.log(modalDatosAdicionalesCampos)
	$('#modalDatosAdicionalesCampos').html('<input type="hidden" id="modalDatosAdicionales_IDOBS"/>');
	
	$(data.listCampos).each(function(i, elem) {
		var input = '';
		var required = elem.campoObligatorio == 'S' ? ' required' : '';
		var label = elem.tituloCampo.substring(0,1) + elem.tituloCampo.substring(1).toLowerCase();
		if (elem.tipoCampo.indexOf('TXT250') != -1)
			input = '<textarea class="form-control bg-light" placeholder="(250 caracteres)" id="modalDatosAdicionales_' + elem.tipoCampo + 
				'" rows="4" maxlength="250"' + required + '></textarea>';
		else if (elem.tipoCampo.indexOf('TXT') != -1)
			input = '<input class="form-control bg-light" placeholder="' + label + '" id="modalDatosAdicionales_' + elem.tipoCampo + 
				'" maxlength="50"' + required +'/>';
		else if (elem.tipoCampo.indexOf('NUM') != -1)
			input = '<input class="form-control bg-light an-integer-pos" placeholder="' + label + '" id="modalDatosAdicionales_' + elem.tipoCampo + '"' +
				required +'/>';
		else if (elem.tipoCampo.indexOf('FEC') != -1)
			input = '<input class="form-control bg-light datepicker" placeholder="' + label + '" id="modalDatosAdicionales_' + elem.tipoCampo + '"' +
				required +'/>';
		else if (elem.tipoCampo.indexOf('COD') != -1) {
			var opciones = globalOpcionVacia;
			$(elem.opcionesCombo).each(function(i, opcion) {
				opciones += '<option value="' + opcion.id + '">' + opcion.descripcion.slice(7) + '</option>';

			});
			input = '<select class="form-control bg-light" id="modalDatosAdicionales_' + elem.tipoCampo + '"' + required +'>' + opciones + '</select>';
		}

		if (elem.tipoCampo.indexOf('COD') != -1)
			$('#modalDatosAdicionalesCampos').append(
				'<div class="col-sm-12 pt-2">' +
					'<div class="has-float-label form-group">' +
						input +
						'<i class="bbva-icon icon-uniE003 text-primary"></i>' +
						'<label>' + label + '</label>' +
						'<div class="invalid-feedback mb-3"></div>' +
					'</div>' +
				'</div>');
		else if (elem.tipoCampo.indexOf('FEC') != -1)
			$('#modalDatosAdicionalesCampos').append(
				'<div class="col-sm-12 pt-2 has-float-label">' +
					'<div class="input-group">' +
						input +
						'<label>' + label + '</label>' +
						'<div class="input-group-append">' +
							'<button class="btn btn-outline-primary bg-light border-white hover-darkblue datepicker-btn" tabindex="-1" type="button">' +
								'<i class="bbva-icon icon-coronita_calendar fa-lg"></i>' +
							'</button>' +
						'</div>' +
						'<div class="invalid-feedback mb-3"></div>' +
					'</div>' +
				'</div>');
		else
			$('#modalDatosAdicionalesCampos').append(
				'<div class="col-sm-12 pt-2">' +
					'<div class="has-float-label">' +
						input +
						'<label>' + label + '</label>' +
						'<div class="invalid-feedback mb-3"></div>' +
					'</div>' +
				'</div>');
	});
	
	
	if (modalDatosAdicionalesLoadParams.codObserv.includes("0235") || modalDatosAdicionalesLoadParams.codObserv.includes("9999")){
        $('#modalDatosAdicionalesCampos').append(
                '<div class="col-sm-12 pt-2">' +
                '<label class="pt-2"> Agregue un excel con la lista de invitados ( Debe indicar en una sola columna el legajo de cada invitado ) </label>' + 
                '<img src="./images/carga_automatica.png" class="img-fluid w-100" style="height:320px" >' + '<br>' +
                    '<div class="has-float-label">' +
                        '<input type="file" id="archivoExcel" accept=".xlsx, .xls" onchange="handleFile(this)"/>'
                    + '</div>' +
                '</div>');
    }
	
	setAutonumericElementInteger('#modalDatosAdicionalesCampos .an-integer-pos', '999999999');
	setDatepickerElements();
}



function readExcel(inputElement) {
    return new Promise((resolve, reject) => {
        let file = inputElement.files[0];
        if (file) {
            if (file.type === 'application/vnd.ms-excel' || file.name.endsWith('.xls') || file.name.endsWith('.xlsx')) {
                let reader = new FileReader();
                reader.onload = function(e) {
                    let data = e.target.result;
                    let workbook = XLSX.read(data, { type: 'binary' });

                    // toma la primera hoja
                    let sheetName = workbook.SheetNames[0];
                    let sheet = workbook.Sheets[sheetName];

                    // Convierte la hoja en un objeto JSON sin encabezados
                    let jsonData = XLSX.utils.sheet_to_json(sheet, { header: 1 });
                    // Filtra y modifica los datos para tomar solo los campos con valores y asignar códigos numéricos
                    let legajos = jsonData
                        .filter(function(row) {
                            return row[0] // Filtra solo filas con valores en ambas columnas
                        })
                        .map(function(row) {
                            return { 
                                legajo: row!= undefined ? row[0] : "",
                            };
                        });     
                    resolve(legajos);
                };  
                reader.onerror = reject;
                reader.readAsBinaryString(file);
                hayExcel=true;
            } else {
                reject('Por favor, seleccione un archivo Excel válido con extensión .xls');
            }
        } else {
            reject('No file selected');
        }
    });
}

async function handleFile(inputElement) {
    try {
        legajosData = await readExcel(inputElement);
        		setTimeout(() => {
				        hayExcel=true;
			}, 500);

        // Do something with data
    } catch (error) {
        alert(error);
    }
}


function modalDatosAdicionalesSetTabla(data) {
	console.log(data);
	$('#modalDatosAdicionalesTabla th, #modalDatosAdicionalesTabla tbody tr').remove();
	$("#modalDatosAdicionalesTabla thead tr").append("<th>NRO</th>");
	if (data.filas) {
		$(data.headers).each(function(i, elem) {
			$('#modalDatosAdicionalesTabla thead tr').append('<th>' + elem + '</th>');
		});
		
		if (!modalDatosAdicionalesLoadParams.readOnly)
			$('#modalDatosAdicionalesTabla thead tr').append('<th>OPCIONES</th>');
		
		$(data.filas).each(function(i, fila) {
			var cols = '';
			$(fila).each(function(j, col) {
				var key = col.substring(0, col.indexOf('='));
				var value = col.substring(col.indexOf('=') + 1);
				cols += '<td id="modalDatosAdicionalesFila_' + i + '_' + key + '">' + value + '</td>';
			});
			
			if (!modalDatosAdicionalesLoadParams.readOnly) {
				cols += '<td>' + 
						'<a href="#a" class="text-gray" onclick="modalDatosAdicionalesEditar(' + i + ')">' +
							'<i class="bbva-icon icon-coronita_contract fa-lg" data-toggle="tooltip" title="Editar"></i>' +
						'</a>&nbsp;' +
						'<a href="#a" class="text-gray" onclick="modalDatosAdicionalesEliminar(' + i + ')">' +
							'<i class="bbva-icon icon-coronita_trash fa-lg" data-toggle="tooltip" title="Eliminar"></i>' +
						'</a>' +
					'</td>';
			}
			$('#modalDatosAdicionalesTabla tbody').append('<tr>' + cols + '</tr>');
		});
	}
}





function buscarUsuarioOrigenSuccess(data) {
	clearFormError($('#legajoDelegadoOrigen').parent());
	$('#nombreDelegadoOrigen').val(data.delegado.nombre)

	
}

function buscarUsuarioOrigenError(data) {
	showFormError('#legajoDelegadoOrigen', data.error);
	$('#nombreDelegadoOrigen').val("")
	
}

function buscarUsuarioDestinoSuccess(data) {
	clearFormError($('#legajoDelegadoDestino').parent());
	 $('#nombreDelegadoDestino').val(data.delegado.nombre)
	
}

function buscarUsuarioDestinoError(data) {
	showFormError('#legajoDelegadoDestino', data.error);
	$('#nombreDelegadoDestino').val("")
	
}

//método para obtener los datos de los invitados.
function obtenerDatosInvitados(){
	let params = {
			action: 'buscarInvitado',
	};
	if(legajosData.length>0){
	legajosData.forEach(invitado => {
		params.legajo = invitado.legajo;
		callAjax('datosAdicionales.do', params, 'handleDataInvitados', true)
	});
	}
}

function obtenerValoresPorCampo(campo, datos) {
    return datos.filas.map(fila => {
        const dato = fila.find(d => d.startsWith(`${campo}=`));
        return dato ? dato.split("=")[1].trim() : null;
    }).filter(valor => valor !== null);
}

$("#modalDatosAdicionalesBtnSalir").click(function() {
    let cantKm = obtenerValoresPorCampo("NUM1", modalDatosAdicionalesCampos)[0];
    let precioNafta = obtenerValoresPorCampo("NUM2", modalDatosAdicionalesCampos)[0];
    let vehiculoPropio = obtenerValoresPorCampo("COD1", modalDatosAdicionalesCampos)[0];
    let monto = parseFloat(sessionStorage.getItem('monto'));

    cantKm = parseFloat(cantKm);
    precioNafta = parseFloat(precioNafta);

    let coef = vehiculoPropio === "00002 - SI" ? 0.22 : 1;
    let montoPolitica = cantKm * precioNafta * coef;

    let diferencia = Math.abs(monto - montoPolitica);

    if (diferencia > 0.01) {
        showConfirm(
            'confirmarSalir',
            "El monto del ticket ingresado no coincide con cuenta según política: km x coef x litro de nafta. ¿Querés ajustarlo a la política vigente?"
        );
    }
});


function confirmarSalir(){
	if (modalDatosAdicionalesLoadParams.codObserv == "00210" || modalDatosAdicionalesLoadParams.codObserv == "0210"  ) { 
				calcularCombustible();
			}else{
				console.log('entra')
				console.log(modalDatosAdicionalesLoadParams)
			}
}

function calcularCombustible(){
	console.log("INTENTO OBTENER DATOS COMBUSTIBLES.")
	console.log(modalDatosAdicionalesCampos)
	let datosAdicionesCombustible = "";
	
	var monedaGuardada = sessionStorage.getItem('moneda');
	var tipoComprobanteGuardado = sessionStorage.getItem('tipoComprobante');

	
	modalDatosAdicionalesCampos.filas.forEach(function (item, index) {
	  console.log(item, index);
	  datosAdicionesCombustible += item.toString().trim() + " _ " 
	  item.forEach(function (item, index) {
		  console.log(item, index);
		  miString = item[index]
	  });	  
	});
	console.log(datosAdicionesCombustible);
	
	
	let params = {
			action: 'calcularCombustible',
			idRendicion: modalDatosAdicionalesLoadParams.idRendicion,
			codMotivo: modalDatosAdicionalesLoadParams.codMotivo,
			idGasto: modalDatosAdicionalesLoadParams.idGasto,
			codGasto: modalDatosAdicionalesLoadParams.codGasto,
			serv: modalDatosAdicionalesLoadParams.codObserv,
			gastoMonto: modalDatosAdicionalesLoadParams.gastoMonto,
			readOnly: modalDatosAdicionalesLoadParams.readOnly,
			message: modalDatosAdicionalesLoadParams.message,
			nombreUsuarioRend: nombreUsuarioRend,
			moneda: monedaGuardada,
			tipoComprobante: tipoComprobanteGuardado,
			datosAdicionesCombustible: datosAdicionesCombustible			
	};
	
	sessionStorage.removeItem('moneda');
	sessionStorage.removeItem('tipoComprobante');
	sessionStorage.removeItem('monto');
	
	callAjax('datosAdicionales.do', params, 'respuestaCalcularCoeficiente');
	location.reload();	
}

//function obtenerDatosCumbustible(){
	//console.log("obtenerDatosCombustible");
	//console.log(modalDatosAdicionalesCampos)
	//let datosAdicionesCombustible = "";
	
	//modalDatosAdicionalesCampos.filas.forEach(function (item, index) {
	  //console.log(item, index);
	  //datosAdicionesCombustible += item.toString().trim() + " _ " 
	  //item.forEach(function (item, index) {
		  //console.log(item, index);
		  //miString = item[index]
	  //});	  
	//});
	//console.log(datosAdicionesCombustible);
	
	
	//let params = {
		//	action: 'calcularCoeficiente',
			//idRendicion: modalDatosAdicionalesLoadParams.idRendicion,
			//codMotivo: modalDatosAdicionalesLoadParams.codMotivo,
			//idGasto: modalDatosAdicionalesLoadParams.idGasto,
			//codGasto: modalDatosAdicionalesLoadParams.codGasto,
			//serv: modalDatosAdicionalesLoadParams.codObserv,
			//gastoMonto: modalDatosAdicionalesLoadParams.gastoMonto,
			//readOnly: modalDatosAdicionalesLoadParams.readOnly,
			//message: modalDatosAdicionalesLoadParams.message,
			//nombreUsuarioRend: nombreUsuarioRend,
			//datosAdicionesCombustible: datosAdicionesCombustible			
	//};
	
	//callAjax('datosAdicionales.do', params, 'respuestaCalcularCoeficiente');
	
//}

function respuestaCalcularCoeficiente(data){
	//if(data.massage == 'igual'){
		//$('#modalDatosAdicionales').modal('hide');
	//}else{		
		//showAcept(close, data.message) 
	//}
	console.log("RESPUESTA CALCULAR COMBUSTIBLE");
}

//function close (){
	 	
	//	$('#modalDatosAdicionales').modal('hide');
		//$('#modalAcept').modal('hide');
		//loadTables();
	//}
		


function buscarInvitadoSuccess(data) {
    return new Promise((resolve, reject) => {
        let sector = data.invitado.sector
        let nombre = data.invitado.nombre
        let nombreExterno = data.invitado.idUser
        let ccostos = data.invitado.ccostos
        let userDate = {};
        let info = [];

        if(sector != "INTE"){
            userDate = {
                "elemento": nombreExterno,
                "codigo" : "0003",
            }
            info.push(userDate)
        }else if(ccostos == $('#cCostos').html()){
            userDate = {
                "elemento":nombre,
                "codigo" : "0001",
            }
            info.push(userDate)
        }else{
            userDate = {
                "elemento": nombre,
                "codigo" : "0002",
            }
            info.push(userDate)
        }
        resolve(info);
    });
}

async function handleDataInvitados(data) {
    try {
        filteredData = await buscarInvitadoSuccess(data);
        filteredData.forEach(e =>{
			params.COD1 = e.codigo;
			params.TXT1 = e.elemento
  			callAjax('datosAdicionales.do', params, 'modalDatosAdicionalesGuardarSuccess', 'modalDatosAdicionalesGuardarError'); 
		})
			setTimeout(() => {
				hayExcel=false;
				filteredData=[]    	
			}, 300);
		
	
    } catch (error) {
        console.error(error);
    }
}



function buscarInvitadoError(data) {
	console.log("ERROR", data)
	
}


function modalDatosAdicionalesSetOnChanges() {
	$('#modalDatosAdicionales input, #modalDatosAdicionales textarea, #modalDatosAdicionales select').change(function() {
		if (modalDatosAdicionalesSubmitted)
			validateForm('modalDatosAdicionales', false);
	});
}

function modalDatosAdicionalesGuardar() {
	modalDatosAdicionalesSubmitted = true;
		
	$('#modalDatosAdicionales_TXT1').removeClass('is-invalid');
	$('#modalDatosAdicionales_TXT1').next('.invalid-feedback').remove();
	
	if (!validateForm('modalDatosAdicionales', true))
		return;
		
	if (modalDatosAdicionalesLoadParams.codMotivo == 8221) {
		var campoObligatorio = $('#modalDatosAdicionales_TXT1');
		if ($.trim(campoObligatorio.val()) === '') {
			campoObligatorio.addClass('is-invalid');
			campoObligatorio.after('<div class="invalid-feedback" style="color: #dc3545; font-size: 0.875em; display: block;">Este campo es obligatorio.</div>');
			campoObligatorio.focus();
			return;
		}
	}
	
	params = jQuery.extend({}, modalDatosAdicionalesLoadParams);
	params.action = 'altaModif';
	$('#modalDatosAdicionales input, #modalDatosAdicionales textarea, #modalDatosAdicionales select').each(function(i, elem) {
		var id = $(elem).attr('id');
		params[id.substring(id.lastIndexOf('_') + 1)] = $(elem).val();
	});
	
	if(hayExcel){	
	obtenerDatosInvitados(); 

	}else{
		callAjax('datosAdicionales.do', params, 'modalDatosAdicionalesGuardarSuccess', 'modalDatosAdicionalesGuardarError', true); 
	}

}

function modalDatosAdicionalesGuardarSuccess(data) {
	showMessage('modalDatosAdicionalesActionMessage', data.message);
	modalDatosAdicionalesLoad();
}

function modalDatosAdicionalesGuardarError(data) {
	$('#modalDatosAdicionalesMessageContainer').addClass('d-none');
	showMessage('modalDatosAdicionalesActionMessage', data.error, 'error');
	scrollToElem('#modalDatosAdicionalesActionMessage', false, '#modalDatosAdicionales', '#modalDatosAdicionalesMessageContainer');
}

function modalDatosAdicionalesEditar(filaId) {
	$('[id^=modalDatosAdicionalesFila_' + filaId + ']').each(function(i, elem) {
		var id = $(elem).attr('id').replace('Fila_' + filaId, '');
		var campo = id.substring(22);
		var value = $(elem).html().trim();
		
		if (campo.indexOf('FEC') != -1)
			$('#' + id).datepicker('setDate', value);
		else if (campo.indexOf('NUM') != -1)
			AutoNumeric.getAutoNumericElement('#' + id).set(value);
		else
			$('#' + id).val(value);
	});
	
	modalDatosAdicionalesAction = 'edit';
	modalDatosAdicionalesSetVisibility();
}

function modalDatosAdicionalesCancelar() {
	modalDatosAdicionalesAction = 'new';
	modalDatosAdicionalesSetCampos(modalDatosAdicionalesCampos);
	modalDatosAdicionalesSetVisibility();
}

function modalDatosAdicionalesEliminar(filaId) {
	$('[id^=modalDatosAdicionalesFila_' + filaId + ']').each(function(i, elem) {
		var id = $(elem).attr('id').replace('Fila_' + filaId, '');
		var campo = id.substring(22);
		var value = $(elem).html().trim();
		
		if (campo.indexOf('FEC') != -1)
			$('#' + id).datepicker('setDate', value);
		else if (campo.indexOf('NUM') != -1)
			AutoNumeric.getAutoNumericElement('#' + id).set(value);
		else
			$('#' + id).val(value);
	});
	
	modalDatosAdicionalesAction = 'edit';
	modalDatosAdicionalesSetVisibility();
}

function modalDatosAdicionalesEliminar(filaId) {
	$('#modalDatosAdicionalesActionMessageContainer').addClass('d-none');
	
	modalDatosAdicionalesParamsEliminar = jQuery.extend({}, modalDatosAdicionalesLoadParams);
	modalDatosAdicionalesParamsEliminar.action = 'baja';
	modalDatosAdicionalesParamsEliminar.idObservacion = $('#modalDatosAdicionalesFila_' + filaId + '_IDOBS').html();
	
	showConfirm('modalDatosAdicionalesEliminarConfirm', '&iquest;Est&aacute;s seguro que quer&eacute;s eliminar el dato adicional?');
}

function modalDatosAdicionalesEliminarConfirm() {
	callAjax('datosAdicionales.do', modalDatosAdicionalesParamsEliminar, 'modalDatosAdicionalesEliminarConfirmSuccess');
}

function modalDatosAdicionalesEliminarConfirmSuccess(data) {
	showMessage('modalDatosAdicionalesActionMessage', data.message);
	$('#modalConfirm').modal('hide');
	modalDatosAdicionalesLoad();
}

function showAcept(confirmCallback, message) {
	$('#modalAceptMsg').html(message);
	$('#modalAceptAceptar').off('click');
	$('#modalAceptAceptar').click(eval(confirmCallback));
	$('#modalAcept').modal('show');
}