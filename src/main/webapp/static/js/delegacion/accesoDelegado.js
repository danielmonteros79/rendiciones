$(document).ready(function() {
	$('.nav-delegacion').addClass('active');
	setCombo('combos.do?action=getDelegados', '#delegado', null, null, false);
	 $('#delegado').trigger("chosen:updated");
});

function reemplazar() {
	var params = {
		action: 'reemplazar',
		delegado: $('#delegado').val()
	};
	
	callAjax('accesoDelegado.do', params, 'reemplazarSuccess');
}

function reemplazarSuccess(data) {
	window.location.href = 'bienvenida.do';
}