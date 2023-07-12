var dtLink = 'listadoAlertas.do';
let param = new URLSearchParams(location.search).get('codigo');
let paramGlg = new URLSearchParams(location.search).get('glg');

$(document).ready(function() {
	showMessage('message', getLocalStorageItem('message'));
	$('.nav-aprobacion').addClass('active');
	dtParams = { action: 'filtrar' };
	loadListadoAlertasTable();
});

function loadListadoAlertasTable() {
	dtParams.codigo = param;
	dtParams.glg = paramGlg
	loadTable('#listadoAlertasDtContainer', dtLink, dtParams);
}

function filtrar() {
	dtParams.codigo = param;
	dtParams.glg = paramGlg
	loadListadoAlertasTable();
}

function determinarRuta(){
	switch(paramGlg){
		case "1" : 
		$("#btnAprobar").attr("href","listadoAprobaciones.do?glg=1")
		break;
		case "2" :
		$("#btnAprobar").attr("href","listadoAprobaciones.do?glg=2")
		break;
		case "3" : 
		$("#btnAprobar").attr("href","listadoAprobaciones.do?glg=3")
		break;
		case "4" : 
		$("#btnAprobar").attr("href","listadoAprobaciones.do?glg=4")
		break;
	}
}