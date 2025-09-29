var dtLink = 'consumosNoRendidos.do';

$(document).ready(function() {
	$('.nav-tarjeta-corporativa').addClass('active');
	dtParams = { action: 'filtrar' };
	loadTable('#consumosDtContainer', dtLink, dtParams);
	
});