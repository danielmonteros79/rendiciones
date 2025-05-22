var dtLink = 'resumenesAnteriores.do';

$(document).ready(function() {
	$('.nav-tarjeta-corporativa').addClass('active');
	dtParams = { action: 'filtrar' };
	setCombo('combos.do?action=getFechasResumenes', '#filtroFecha');
	$('#filtroFecha').trigger("chosen:updated");
});

function filtrar() {
	$('#resumenDivResultado').addClass('d-none');
	
	if (!validateForm('filtro', true))
		return;
	
	dtParams.fecha = $('#filtroFecha').val();
	loadResumen();
}

function loadResumen() {
	$('#resumenDivResultado').removeClass('d-none');
	$('#fechaResumen').html($('#filtroFecha option:selected').text());
	loadTable('#resumenDtContainer', dtLink, dtParams);
}