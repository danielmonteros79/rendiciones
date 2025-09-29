var dtLink = 'cierreDetalle.do';

$(document).ready(function() {
	callAjax('cierreDetalle.do', 'action=getMessage', 'init');
	$('.nav-cierre').addClass('active');
});

function init(data) {
	showMessage('message', data.message);
	loadGastosTable();
}

function loadGastosTable() {
	dtParams = {
		action: 'getRendicionGastos',
		idRendicion: $('#idRendicion').html(),
		usuarioRend: $('#user').val(),
		codMotivo: $('#codMotivo').val(),
		estadoRend: $('#estadoRend').val()
	};
	loadTable('#gastosDtContainer', dtLink, dtParams);
}

function openImagenes() {
	//modalImagenesShow($('#idRendicion').html(), true, $('#urlThuban').val());
	window.open($('#urlThuban').val(), '_blank');
}

function generarCierre() {
   $('#messageContainer').addClass('d-none');
   $('#gastosTableMessage').addClass('d-none');

   paramsOrdenDePago = {
       action: 'generar',
       idRendiciones: JSON.stringify([$('#idRendicion').html()])
   };

   showConfirm('generarCierreConfirm', '&iquest;Est&aacute;s seguro que quer&eacute;s generar el cierre de esta rendici&oacute;n?');
}

function generarCierreConfirm() {
   callAjax('cierreOrdenDePago.do', paramsOrdenDePago, 'generarCierreConfirmSuccess', 'generarCierreConfirmError');
}

function generarCierreConfirmSuccess(data) {
   showMessage2('#gastosTableMessage', data.message);
   $('#modalConfirm').modal('hide');

}

function generarCierreConfirmError(data) {
   showMessage2('#gastosTableMessage', data.error);
   $('#modalConfirm').modal('hide');
}

function suspenderCierre() {
    $('#messageContainer').addClass('d-none');
    $('#gastosTableMessage').addClass('d-none');

    var idRendicion = $('#idRendicion').html();

    if (idRendicion) {
        modalSuspenderCierreShow(JSON.stringify([idRendicion]));
    } else {
        showMessage2('#gastosTableMessage', 'No se pudo identificar la rendición.', 'error');
        scrollToElem('#gastosTableMessage', true);
    }
}

function suspenderCierreConfirm() {
    var paramsSuspender = {
        action: 'suspender',
        idRendiciones: JSON.stringify([$('#idRendicion').html()])
    };

    callAjax('cierreDetalle.do', paramsSuspender, 'suspenderCierreConfirmSuccess', 'suspenderCierreConfirmError');
}

function suspenderCierreConfirmSuccess(data) {
    showMessage2('#cierreOrdenDePagoTableMessage', data.message);
    $('#modalConfirm').modal('hide');

    console.log("Cierre suspendido exitosamente.");
}


function suspenderCierreConfirmError(data) {
    showMessage2('#gastosTableMessage', data.error);
    $('#modalConfirm').modal('hide');
}

function loadCierreTable() {
    const dtParams = {
        action: 'getRendicionGastos',
        idRendicion: $('#idRendicion').html(),
        usuarioRend: $('#usuarioRend').val(),
        codMotivo: $('#codMotivo').val(),
        estadoRend: $('#estadoRend').val()
    };

    loadTable('#gastosDtContainer', dtLink, dtParams);
}
