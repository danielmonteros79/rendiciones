var modalGastoFirstLoad = true;
var modalGastoSubmitted = false;
var modalGastoLoadParams;
var modalGastoTipoComprobanteSel;

function modalGastoShow(accion, idRendicion, idGasto, codMotivo, cupon) {
	modalGastoSubmitted = false;
	clearFormErrors('#modalGasto');
	
	modalGastoLoadParams = {
		accion: accion,
		idRendicion: idRendicion,
		idGasto: idGasto,
		codMotivo: codMotivo,
		cupon: cupon == "1" ? {} : cupon,
		fechaDesde: $('#fechaDesde').html(),
		fechaHasta: $('#fechaHasta').html()
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
	if (modalGastoLoadParams.accion == 'update')
		callAjax('editarGasto.do', modalGastoLoadParams, 'modalGastoLoadSuccess');
	else
		modalGastoLoadSuccess({});
}

function modalGastoLoadSuccess(data) {
	modalGastoSetVisibility();
	modalGastoSetValues(data);
	modalGastoSetValidaciones(data);
	
	$('#modalGasto').modal('show');
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
	setCombo('combos.do?action=getTiposGasto', '#modalGastoTipoGasto', { codMotivo: modalGastoLoadParams.codMotivo });
	setCombo('combos.do?action=getMonedas', '#modalGastoMoneda', null, modalGastoLoadParams.cupon ? modalGastoLoadParams.cupon.moneda + " " : null);
}

function modalGastoSetOnChanges() {
	$('input[id^=modalGasto],textarea[id^=modalGasto],select[id^=modalGasto]').change(function() {
		if (modalGastoSubmitted)
			validateForm('modalGasto', false);
	});
	
	$('#modalGastoTipoGasto').change(function() {
		var selTipoGasto = $(this).val();
		
		if (selTipoGasto) {
			var params = {
				tipoGasto: selTipoGasto.substring(0, 4)
			};
			setCombo('combos.do?action=getTiposComprobante', '#modalGastoTipoComprobante', params, modalGastoTipoComprobanteSel);
			modalGastoTipoComprobanteSel = null;
			modalGastoCheckBimon();
			
			$('#modalGastoTipoComprobanteDiv').fadeIn('slow');
		} else
			$('#modalGastoTipoComprobanteDiv').fadeOut();
	});

	$('#modalGastoTipoComprobante').change(function() {
		var selTipoComprobanteText = $(this).find('option:selected').text();
		if (selTipoComprobanteText == 'FACTURA OBLIGATORIA')
			$('.modalGastoFacturaCuitDiv').fadeIn('slow');
		else
			$('.modalGastoFacturaCuitDiv').fadeOut();
	});
}

function modalGastoSetVisibility() {
	$('#modalGastoConCupon').toggle(modalGastoLoadParams.cupon);
	$('#modalGastoTipoComprobanteDiv').hide();
	$('.modalGastoFacturaCuitDiv').hide();
}

function modalGastoSetValues(data) {
	modalGastoTipoComprobanteSel = null;
	$('[id^=modalGasto]').val('');
	$('#modalGastoTipoFactura').val('A');
	$('[id^=modalGasto]').attr('disabled', false);
	AutoNumeric.getAutoNumericElement('#modalGastoMonto').clear();
	
	$('#modalGastoIdRendicion').html(modalGastoLoadParams.idRendicion);
	$('#modalGastoUsuario').html($('#usuario').html());
	$('#modalGastoCCostos').html($('#cCostos').html());
	AutoNumeric.getAutoNumericElement('#modalGastoCCostosDestino').set($('#cCostos').html());
	
	$('#modalGastoFechaGasto').datepicker('setStartDate', modalGastoLoadParams.fechaDesde);
	$('#modalGastoFechaGasto').datepicker('setEndDate', modalGastoLoadParams.fechaHasta);
	
	if (data.gasto) {
		modalGastoTipoComprobanteSel = data.gasto.tipoComprobante;
		AutoNumeric.getAutoNumericElement('#modalGastoCCostosDestino').set(data.gasto.costosDestino);
		$('#modalGastoTipoGasto').val($('#modalGastoTipoGasto option').filter(function () { return $(this).html() == data.gasto.descGasto; }).val());
		$('#modalGastoMoneda').val(data.gasto.moneda + " ");
		$('#modalGastoFechaGasto').datepicker('setDate', data.gasto.fechagastos);
		AutoNumeric.getAutoNumericElement('#modalGastoMonto').set(data.gasto.monto);
		$('#modalGastoObservaciones').val(data.gasto.observacionGasto);
		$('#modalGastoTipoFactura').val(data.gasto.tipoFactura);
		$('#modalGastoFactura').val(data.gasto.factura);
		(data.gasto.cuit).replace("-", "")
		let cuitFormateado = Integer.parseInt(cuitFormateado)
		$('#modalGastoCuit').val(cuitFormateado);
		$('[id^=modalGasto]').change();
	} else if (modalGastoLoadParams.cupon) {
		$('#modalGastoMoneda').val(modalGastoLoadParams.cupon.moneda + " ");
		$('#modalGastoMoneda').attr('disabled', true);
		$('#modalGastoFechaGasto').datepicker('setDate', formatDate(modalGastoLoadParams.cupon.fechaPresentacion));
		$('#modalGastoFechaGasto').attr('disabled', !modalGastoLoadParams.cupon.adelanto);
		$($('#modalGastoFechaGasto').parent().find('button')[0]).attr('disabled', !modalGastoLoadParams.cupon.adelanto);
		AutoNumeric.getAutoNumericElement('#modalGastoMonto').set(modalGastoLoadParams.cupon.disponible);
	}
}

function modalGastoSetValidaciones() {
	$('#modalGastoMonto').removeAttr('data-maxvalue');

	$('#modalGastoFechaGasto').attr('data-mindate', modalGastoLoadParams.fechaDesde);
	$('#modalGastoFechaGasto').attr('data-maxdate', modalGastoLoadParams.fechaHasta);
	
	if (modalGastoLoadParams.cupon)
		$('#modalGastoMonto').attr('data-maxvalue', modalGastoLoadParams.cupon.disponible.replace(',', '.'));
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
	
	if (!validateForm('modalGasto', true))
		return;
	
	var params = {
		opcion: modalGastoLoadParams.idGasto ? 'MODIF' : 'ALTA',
		idRendicion: modalGastoLoadParams.idRendicion,
		idGasto: modalGastoLoadParams.idGasto,
		codMotivo: modalGastoLoadParams.codMotivo,
		moneda: $('#modalGastoMoneda').val(),
		tipoComprobante: $('#modalGastoTipoComprobante').val(),
		tipoFactura: $('#modalGastoTipoFactura').val(),
		factura: $('#modalGastoTipoFactura').inputmask('unmaskedvalue'),
		cuit: $('#modalGastoCuit').inputmask('unmaskedvalue'),
		gasto: $('#modalGastoTipoGasto').val(),
		monto: AutoNumeric.getAutoNumericElement('#modalGastoMonto').getNumericString(),
		fechaGasto: $('#modalGastoFechaGasto').val(),
		costosDestino: $('#modalGastoCCostosDestino').val(),
		observacionGasto: $('#modalGastoObservaciones').val(),
		cupCred: modalGastoLoadParams.cupon ? modalGastoLoadParams.cupon.nroCuponCredito : null,
		cupDeb: modalGastoLoadParams.cupon ? modalGastoLoadParams.cupon.nroCuponDebito : null,
		cupon: modalGastoLoadParams.cupon ? modalGastoLoadParams.cupon.nroCupon : null,
		descCupon: modalGastoLoadParams.cupon ? modalGastoLoadParams.cupon.establecimiento : null,
		importeCupon: modalGastoLoadParams.cupon ? modalGastoLoadParams.cupon.disponible.replace(',', '.') : null,
		nroTarjeta: modalGastoLoadParams.cupon ? modalGastoLoadParams.cupon.nroTarjeta : null
	};
	
	callAjax('gastos.do', params, 'modalGastoGuardarSuccess');
}

function modalGastoGuardarSuccess(data) {
	$('#modalGasto').modal('hide');
	
	if (data.showModalDatosAdicionales)
		modalDatosAdicionalesShow(modalGastoLoadParams.idRendicion, data.idGasto, modalGastoLoadParams.codMotivo,
				$('#modalGastoTipoGasto').val().substring(55, 59));
}