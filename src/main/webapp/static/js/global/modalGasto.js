var modalGastoFirstLoad = true;
var modalGastoSubmitted = false;
var modalGastoLoadParams;
var modalGastoTipoComprobanteSel;
let centroDeCostos="";
let cantCupones = 0;
let codigos = []; 

$(document).ready(function() {
	params.action = 'obtenerCodigosPatagonia'
	callAjax('datosAdicionales.do', params, 'codsSuccess', 'codsError', true);
});

function codsSuccess(data) {	
	codigos = [...data.codigos] 
}

function modalGastoShow(idRendicion, estadoRend, idGasto, codMotivo, cupon, costosDestino) {
	$('#tableMessageContainer').addClass('d-none');
	modalGastoSubmitted = false;
	clearFormErrors('#modalGasto');
	centroDeCostos=costosDestino;
	if (cupon == '0')
		$('#modalGastoMessageContainer').addClass('d-none');
	
	modalGastoLoadParams = {
		idRendicion: idRendicion,
		estadoRend: estadoRend,
		idGasto: idGasto,
		codMotivo: codMotivo,
		cupon: cupon == 1 ? {} : cupon,
		fechaDesde: $('#fechaDesde').html(),
		fechaHasta: $('#fechaHasta').html(),
		opcion: 'CONS'
	};
	
	if (modalGastoFirstLoad) {
		modalGastoFirstLoad = false;
		modalGastoSetMasks();
		modalGastoSetCombos();
		modalGastoSetOnChanges();
	}
	
	modalGastoLoad();
}

function modalGastoLoad() {
	if (modalGastoLoadParams.idGasto)
		callAjax('gastos.do', modalGastoLoadParams, 'modalGastoLoadSuccess');
	else
		modalGastoLoadSuccess({});
}

function modalGastoLoadSuccess(data) {
	setTimeout(function(){		
	modalGastoSetVisibility();	
	setTimeout(function(){
		modalGastoSetValues(data);
		modalGastoSetValidaciones(data);
	},300)
		$('#modalGasto').modal('show');	
	},200)

		
}

function modalGastoSetMasks() {
	AutoNumeric.getAutoNumericElement('#modalGastoCCostosDestino').update({maximumValue: '9998'});
	AutoNumeric.getAutoNumericElement('#modalGastoMonto').update({maximumValue: '99999999999.99'});
	
	$('#modalGastoFactura').inputmask({
		mask: '9999-99999999',
		showMaskOnHover: false
	});
	$('#modalGastoCuit').inputmask({
		mask: '99-99999999-9',
		showMaskOnHover: false
	});

	$('#modalGastoCCostosDestino').change(function() {
		AutoNumeric.getAutoNumericElement('#modalGastoCCostosDestino').set(String('0000' + $(this).val()).slice(-4));
		
		if ($(this).val() < 1)
			AutoNumeric.getAutoNumericElement('#modalGastoCCostosDestino').set('');
	});
}

function modalGastoSetCombos() {
	setCombo('combos.do?action=getTiposGasto', '#modalGastoTipoGasto', { codMotivo: modalGastoLoadParams.codMotivo});
	setCombo('combos.do?action=getMonedas', '#modalGastoMoneda', null, modalGastoLoadParams.cupon ? modalGastoLoadParams.cupon.moneda + " " : null);
}

function modalGastoSetOnChanges() {
	$('input[id^=modalGasto],textarea[id^=modalGasto],select[id^=modalGasto]').change(function() {
		if (modalGastoSubmitted)
			validateForm('modalGasto', false);
	});
			$('.modalGastoPatagoniaDiv').hide();
	
	$('#modalGastoTipoGasto').change(function() {	
		console.log("modalGastoSetOnChanges: " + codigos)
		let selTipoGasto = $(this).val();
		let hayDatoAdicional = false;
		if(selTipoGasto != null){
			let isPatagonia = codigos.some(str => str.includes(selTipoGasto.substring(54,59)))
			hayDatoAdicional = selTipoGasto.substring(60,61) == "S" && isPatagonia
		}
		hayDatoAdicional  ? $('.modalGastoPatagoniaDiv').fadeIn('slow') : $('.modalGastoPatagoniaDiv').fadeOut()
		if (selTipoGasto) {
			let params = {
				tipoGasto: selTipoGasto.substring(0, 4)
			};
			setCombo('combos.do?action=getTiposComprobante', '#modalGastoTipoComprobante', params, modalGastoTipoComprobanteSel);
			modalGastoCheckBimon();
			
			$('#modalGastoTipoComprobanteDiv').fadeIn('slow');
		} else{
			$('.modalGastoPatagoniaDiv').hide();
			$('#modalGastoTipoComprobanteDiv').fadeOut();}
	});

	$('#modalGastoTipoComprobante').change(function() {
		modalGastoTipoComprobanteSel = $(this).val();
		let selTipoComprobanteText = $(this).find('option:selected').text();
		if (selTipoComprobanteText == 'FACTURA OBLIGATORIA')
			$('.modalGastoFacturaCuitDiv').fadeIn('slow');
		else
			$('.modalGastoFacturaCuitDiv').fadeOut();
	});
}

function modalGastoSetVisibility() {
	$('#modalGastoConCupon').toggle(modalGastoLoadParams.cupon != "0");
	if (!modalGastoLoadParams.cupon.esProxCupon) {
		$('#modalGastoTipoComprobanteDiv').hide();
		$('.modalGastoFacturaCuitDiv').hide();
	}
}

function modalGastoSetValues(data) {
	let cantCupones = modalCuponesCuponesSel.length +1;
	let estadoRend = modalGastoLoadParams.estadoRend;
	
	let esEditable = estadoRend === "PENDI" || estadoRend === "ESCAN";
	
	if (!modalGastoLoadParams.cupon.esProxCupon) {
		modalGastoTipoComprobanteSel = null;
		$('[id^=modalGasto]').not($('#modalGastoTipoGasto')).val('');
		$('#modalGastoCuponesCant').addClass("d-none")
		$('#modalGastoDetalleCuponDesc').addClass("d-none")
		$('#modalGastoTipoFactura').val('A');
		//$('[id^=modalGasto]').attr('disabled', false);
		$('#modalGastoTipoGasto').attr('disabled', !esEditable);
		//$('#modalGastoMoneda').attr('disabled', !esEditable);
		$('#modalGastoTipoComprobante').attr('disabled', !esEditable);
		$('#modalGastoObservaciones').attr('disabled', !esEditable);
		$($('#modalGastoFechaGasto').parent().find('button')[0]).attr('disabled', !esEditable);
		AutoNumeric.getAutoNumericElement('#modalGastoMonto').clear();
		
		$('#modalGastoMsgCupon').html('');
		$('#modalGastoIdRendicion').html(Number(modalGastoLoadParams.idRendicion));
		$('#modalGastoUsuario').html($('#usuario').html());
		$('#modalGastoCCostos').html($('#cCostos').html());
		AutoNumeric.getAutoNumericElement('#modalGastoCCostosDestino').set($('#cCostos').html());
		$('#modalGastoFechaGasto').datepicker('setStartDate', modalGastoLoadParams.fechaDesde);
		$('#modalGastoFechaGasto').datepicker('setEndDate', modalGastoLoadParams.fechaHasta);
		
		$('#modalGastoFechaGasto').attr('disabled', !esEditable);
	}
	
	if (data.gasto) {
		modalGastoTipoComprobanteSel = data.gasto.tipoComprobante;

		$('#modalGastoMsgCupon').html(data.gasto.cuponGasto != '' ? ' con el cup&oacute;n ' + data.gasto.cuponGasto : '');
		AutoNumeric.getAutoNumericElement('#modalGastoCCostosDestino').set(data.gasto.costosDestino);
		$('#modalGastoTipoGasto').val($('#modalGastoTipoGasto option').filter(function () { return $(this).html() == data.gasto.descGasto; }).val());
		$('#modalGastoMoneda').val(data.gasto.moneda + " ");
		$('#modalGastoFechaGasto').datepicker('setDate', data.gasto.fechagastos);
		$('#modalGastoFechaGasto').attr('disabled', data.gasto.cuponGasto != '');
		$($('#modalGastoFechaGasto').parent().find('button')[0]).attr('disabled', data.gasto.cuponGasto != '');
		AutoNumeric.getAutoNumericElement('#modalGastoMonto').set(data.gasto.monto);
		$('#modalGastoObservaciones').val(data.gasto.observacionGasto);
		$('#modalGastoTipoFactura').val(data.gasto.tipoFactura);
		$('#modalGastoFactura').val(data.gasto.factura);
		$('#modalGastoCuit').val(data.gasto.cuit);
		$('[id^=modalGasto]').change();
	} else if (modalGastoLoadParams.cupon) {
		$('#modalGastoDetalleCuponDesc').removeClass("d-none")
		$('#modalGastoCuponesCant').removeClass("d-none")
		$('#modalGastoCuponesCant').text("Cupones pendientes por completar : ")
		$('#modalGastoCuponesCant').append("<span id='modalGastocantCupones'</span>")
		$('#modalGastocantCupones').text(cantCupones)
		$('#modalGastocantCupones').addClass('font-weight-bold')
		$('#modalGastoDetalleCuponDesc').html(" " + modalGastoLoadParams.cupon.establecimiento )
		$('#modalGastoMsgCupon').html(' con el cup&oacute;n ' + modalGastoLoadParams.cupon.nroCupon);
		$('#modalGastoMoneda').val(modalGastoLoadParams.cupon.moneda + " ");
		$('#modalGastoMoneda').attr('disabled', true);
		$('#modalGastoFechaGasto').datepicker('setDate',  formatDateConsumos(modalGastoLoadParams.cupon.fechaPresentacion));
		$('#modalGastoFechaGasto').attr('disabled', !modalGastoLoadParams.cupon.adelanto);
		$($('#modalGastoFechaGasto').parent().find('button')[0]).attr('disabled', !modalGastoLoadParams.cupon.adelanto);
		AutoNumeric.getAutoNumericElement('#modalGastoMonto').set(modalGastoLoadParams.cupon.disponible);

	}

	//Habilitar la edición del centro de costos
	if(centroDeCostos.trim().includes("9999")){
		$('#modalGastoCCostosDestino').removeAttr("readonly")
		.removeClass("bg-white")	
		.addClass("bg-light")	
	}else if(!centroDeCostos.trim().includes("9999")){
		$('#modalGastoCCostosDestino').addClass("bg-white")	
		.removeClass("bg-light")
		.attr('readonly', 'readonly')
	}

	$('#modalGastoFechaGasto').attr('disabled', !esEditable);
	
}

function modalGastoSetValidaciones(data) {
	$('#modalGastoMonto').removeAttr('data-maxvalue');
	$('#modalGastoFechaGasto').attr('data-mindate', modalGastoLoadParams.fechaDesde);
	$('#modalGastoFechaGasto').attr('data-maxdate', modalGastoLoadParams.fechaHasta);
	
	if (data.gasto) {
		if (modalGastoLoadParams.estadoRend != 'PENDI') {
			var idTipoGasto = $('#modalGastoTipoGasto').val().substring(0, 4);
	    	var puedeSubirMontoMotivo = modalGastoLoadParams.codMotivo == '0723' && ['0761', '0762'].indexOf(idTipoGasto) != -1;
	    	var maxMontos = {'0761': 8000, '0762': 5600};
	    	
			$('#modalGastoMonto').attr('data-maxvalue', puedeSubirMontoMotivo ? maxMontos[idTipoGasto] : data.gasto.montoNum);
		} else if (data.gasto.cuponGasto)
			$('#modalGastoMonto').attr('data-maxvalue', data.gasto.montoNum);
	} 
	
	
}

function modalGastoCheckBimon() {
	if (!modalGastoLoadParams.cupon) {
		var bimon = $('#modalGastoTipoGasto').val().substring(59, 60);
		if (bimon == "N") {
			$('#modalGastoMoneda').attr('disabled', true);
			$('#modalGastoMoneda').val("ARS ");
		} else
			$('#modalGastoMoneda').attr('disabled', false);
	}
}

function modalGastoGuardar() {
	modalGastoSubmitted = true;
	$('#modalGastoMessageContainer').addClass('d-none');
	
	if (!validateForm('modalGasto', true))
		return;
	var params = {
		opcion: modalGastoLoadParams.idGasto ? 'MODIF' : 'ALTA',
		idRendicion: modalGastoLoadParams.idRendicion,
		idGasto: modalGastoLoadParams.idGasto,
		codMotivo: modalGastoLoadParams.codMotivo,
		moneda: $('#modalGastoMoneda').val(),
		tipoComprobante: '0004',
		tipoFactura: $('#modalGastoTipoFactura').val(),
		factura: $('#modalGastoFactura').inputmask('unmaskedvalue'),
		cuit: $('#modalGastoCuit').inputmask('unmaskedvalue'),
		gasto: $('#modalGastoTipoGasto').val(),
		monto: AutoNumeric.getAutoNumericElement('#modalGastoMonto').getNumericString(),
		fechaGasto: $('#modalGastoFechaGasto').val(),
		costosDestino: $('#modalGastoCCostosDestino').val(),
		observacionGasto: $('#modalGastoObservaciones').val(),
		cupCred: modalGastoLoadParams.cupon && !modalGastoLoadParams.idGasto ? modalGastoLoadParams.cupon.nroCuponCredito : null,
		cupDeb: modalGastoLoadParams.cupon && !modalGastoLoadParams.idGasto ? modalGastoLoadParams.cupon.nroCuponDebito : null,
		cupon: modalGastoLoadParams.cupon && !modalGastoLoadParams.idGasto ? modalGastoLoadParams.cupon.nroCupon : null,
		descCupon: modalGastoLoadParams.cupon && !modalGastoLoadParams.idGasto ? modalGastoLoadParams.cupon.establecimiento : null,
		importeCupon: modalGastoLoadParams.cupon && !modalGastoLoadParams.idGasto ? modalGastoLoadParams.cupon.disponible.replace(',', '.') : null,
		nroTarjeta: modalGastoLoadParams.cupon && !modalGastoLoadParams.idGasto ? modalGastoLoadParams.cupon.nroTarjeta : null
	};
	
	callAjax('gastos.do', params, 'modalGastoGuardarSuccess');
	setPatagonia()
	$('#modalGastoFueraDePolitica').modal('hide');



}

function validarGasto(idGasto){
	//modalGastoSubmitted = true;
	//$('#modalGastoMessageContainer').addClass('d-none');

	if (!validateForm('modalGasto', true))
		return;
	var params = {
		opcion: 'VALIDAR',
		idRendicion: modalGastoLoadParams.idRendicion,
		//idGasto: modalGastoLoadParams.idGasto,
		idGasto: idGasto
	};

	callAjax('gastos.do', params, 'evaluarValidacionGasto');
}

function modalGastoCancelar(){
	$('#modalGastoFueraDePolitica').modal('hide');
}

function evaluarValidacionGasto(data) {
    
    if (!data || typeof data.textoValidacion !== 'string') {
        console.error("Error: data.textoValidacion es indefinido o no es una cadena de texto.", data);
        return;
    }

    if (!data.textoValidacion.includes('OK')) {
        setTimeout(function() {
            setTimeout(function() {
                if (data.textoValidacion.includes('ALERTA')) { 
                    $('#modalGastoTitle').html("El gasto cargado no cumple con la política de gastos acordada");
                    $('#aceptarGastoBtn').html("Continuar");
                } else {
                    $('#modalGastoTitle').html("El gasto requiere ser confirmado");
                    $('#aceptarGastoBtn').html("Confirmar");
                }
                $('#modalGastoValidarMessage').html(data.textoValidacion);
            }, 300);
            $('#modalGastoFueraDePolitica').modal('show');
        }, 200);
    }
}

function modalGastoGuardarSuccess(data) {
    loadTables();
    //validarGasto(data.idGasto);
	if(!data.showModalDatosAdicionales){
		validarGasto(data.idGasto);
	}
    if (data.showModalDatosAdicionales && $('#modalGastoTipoGasto').val().substring(54,59) != '00212') {
        $('#modalGasto').modal('hide');
        modalDatosAdicionalesShow(modalGastoLoadParams.idRendicion, modalGastoLoadParams.codMotivo, data.idGasto,
        $('#modalGastoTipoGasto').val().substring(0, 4), $('#modalGastoTipoGasto').val().substring(55, 59), $('#modalGastoMonto').val().trim() ,false, data.message);
    } else if (modalCuponesCuponesSel.length == 0) {
            $('#modalGasto').modal('hide');
    } else
        modalGastoShowProximoGastoCupon(data);
       
    setTimeout(function(){
        showMessage('tableMessage', data.message);
    },500)
        
}

function setPatagonia(){
	params.action = 'altaModif';
	params.IDOBS = '000000001'
	params.idRendicion = modalGastoLoadParams.idRendicion //'0000000000001813'
	params.idGasto = '0000000' + modalGastoLoadParams.idGasto
	params.codGasto = '8212'
	params.codObserv = '00212'
	params.NUM1 = "000000000"
	params.NUM2 = "000000000"

	params.COD1 = $('#modalGastoPatagonia').val()
	
	callAjax('datosAdicionales.do', params, 'modalDatosSuccess', 'modalDatosError', true);

}

function modalDatosSuccess(data){
	console.log("ok ", data)
}

function modalDatosError(data){
	console.log("error", data)
}

function modalGastoShowProximoGastoCupon(data) {
	modalGastoTipoComprobanteSel = null;
	$('#modalGastoTipoComprobanteDiv').fadeOut();
	$('#modalGastoObservaciones').val("")
	$('#modalGastoTipoComprobante').val("")
	$('#modalGastoTipoFactura').val("")
	$('#modalGastoTipoGasto').val("")
	modalGastoShow(modalCuponesLoadParams.idRendicion, modalCuponesLoadParams.estadoRend, null, modalCuponesLoadParams.codMotivo, modalCuponesCuponesSel[0],centroDeCostos );
	modalCuponesCuponesSel.splice(0, 1);
	//showMessage('modalGastoMessage',data.message);
	scrollToElem('#modalGastoMessage', false, '#modalGasto', '#modalGastoMessageContainer');
	cantCupones--
}