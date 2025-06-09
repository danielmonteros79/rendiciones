
let dtLink = 'reasignarBandeja.do';
let paramsDelegarBandeja;

$(document).ready(function() {
	$('#comboTipoAprobacion').chosen();
	$('#comboTipoAprobacion').val()
	$('#legajoDelegadoOrigen').val()
	$('#legajoDelegadoDestino').val()
});


function reasignar(){
	paramsDelegarBandeja = {
		action: 'reasignarBandeja',
		userOrigen : $('#legajoDelegadoOrigen').val(),
		userDestino : $('#legajoDelegadoDestino').val(),
	 	tipoBandeja : $('#comboTipoAprobacion').val()

	};
	if (validateForm('contianerResignarBandeja', true, 'reasingarBandejaValidateForm') ){
		showConfirm('delegarBandejaConfirm', '&iquest;Est&aacute;s seguro que quer&eacute;s reasingar la bandeja?');
	
	}
}

function reasingarBandejaValidateForm(){
	validateElem($('#legajoDelegadoOrigen'));
	validateElem($('#legajoDelegadoDestino'));
	validateElem($('#comboTipoAprobacion'));
}


function delegarBandejaConfirm() {
	callAjax(dtLink, paramsDelegarBandeja, 'reasignarBandejaConfirmSuccess', 'reasignarBandejaConfirmError');
}


function reasignarBandejaConfirmSuccess(data) {
    if (data.error) {
        reasignarBandejaConfirmError(data);
        return;
    }
    showMessage('message', 'OK: BANDEJA REASIGNADA EXITOSAMENTE');
    $('#modalConfirm').modal('hide');
}

function reasignarBandejaConfirmError(data) {
    let errorMessage = data.error ? data.error : "Se produjo un error desconocido.";
    showMessage('message', errorMessage);
    $('#modalConfirm').modal('hide');
}

 $('#legajoDelegadoOrigen').blur(function() {
      buscarUsuario($('#legajoDelegadoOrigen').val(), "O")
 });
 
  $('#legajoDelegadoDestino').blur(function() {
     buscarUsuario($('#legajoDelegadoDestino').val(), "D")
 });

function modalDelegadoValidateForm() {
	modalDelegadoBuscarUsuario($('#modalDelegadoUsuario').val());
	modalDelegadoValidarFechas();
}

function buscarUsuario(legajo, tipoUsuario) {
	if (legajo) {
		let params = {
			action: 'buscarUsuario',
			legajo: legajo,
		};
		tipoUsuario == "O" 
			? callAjax('abmDelegado.do', params, 'buscarUsuarioOrigenSuccess', 'buscarUsuarioOrigenError', false)
			: callAjax('abmDelegado.do', params, 'buscarUsuarioDestinoSuccess', 'buscarUsuarioDestinoError', false)
	}else if(legajo.trim() == ""){
		tipoUsuario == "O" ?
		$('#nombreDelegadoOrigen').val("")
		: $('#nombreDelegadoDestino').val("")
	}
}

function buscarUsuarioOrigenSuccess(data) {
    if (data.success) {
        clearFormError($('#legajoDelegadoOrigen').parent());
        $('#nombreDelegadoOrigen').val(data.delegado.nombre);
    } else {
        buscarUsuarioOrigenError(data);
    }
}

function buscarUsuarioOrigenError(data) {
    showFormError('#legajoDelegadoOrigen', data.error || 'Error al buscar usuario');
    $('#nombreDelegadoOrigen').val("");
}

function buscarUsuarioDestinoSuccess(data) {
    if (data.success) {
        clearFormError($('#legajoDelegadoDestino').parent());
        $('#nombreDelegadoDestino').val(data.delegado.nombre);
    } else {
        buscarUsuarioDestinoError(data);
    }
}

function buscarUsuarioDestinoError(data) {
    showFormError('#legajoDelegadoDestino', data.error || 'Error al buscar usuario');
    $('#nombreDelegadoDestino').val("");
}
