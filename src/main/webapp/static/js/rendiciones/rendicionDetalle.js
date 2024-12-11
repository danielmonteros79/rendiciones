var dtLink = 'saveRendicion.do';
$(document).ready(function() {
	$('.nav-rendiciones').addClass('active');
	setValidaciones();
	$('#rendicionDetalleMotivo').on('change', function() {
        updateFormForMotivo();
    });
    updateFormForMotivo(); // Ejecutar en carga para preseleccionar
	$('.basic-single').chosen();
	ayudaMotivo();
	getPreformato()
	
	// Escuchar el evento de input en el textarea
    $('#rendicionDetalleDescripcion').on('input', function() {
        let originalText = $(this).val();
        let normalizedText = normalizeText(originalText);

        // Actualizar el valor del textarea si es necesario
        if (originalText !== normalizedText) {
            $(this).val(normalizedText);
        }
    });
});
function setValidaciones() {
	$('#rendicionDetalleMotivo').attr('required', true);
	$('#rendicionDetalleDescripcion').attr('required', true);
	$('input[id^=rendicionDetalleFecha]').attr('data-maxdate', formatDate(new Date())).datepicker('setEndDate', new Date());
	$("#rendicionDetalleMotivo").on("change", function() {
    	getPreformato();
	});
}
function updateFormForMotivo() {
    let motivo = $('#rendicionDetalleMotivo').val();
    
    if (motivo === '0230' || motivo === '8713') {	//0230 - GYMPASS; 8713 - REFRIGERIO; xxxx - VEHÌCULOS DE DIRECTORES
        $('#fechaContainer').hide();
        $('#mesContainer').show();
    } else {
        $('#fechaContainer').show();
        $('#mesContainer').hide();
        
        if (motivo === 'VIAJE') {
            $('#rendicionDetalleFechaHastaContainer').show();
        } else {
            $('#rendicionDetalleFechaHastaContainer').hide();
        }
    }
}
//alternar visibilidad del desplegable
function toggleSelect(){
	$('#btnSelect').click(function(){
	$('#rendicionDetalleMotivo').toggle();
})
}
$("#rendicionDetalleMotivo").on("change", function(){
	getPreformato()
})
$("#mesSelect").on("change", function(){
	getPreformato()
})
function getPreformato(){
    let valorSeleccionado = $("#rendicionDetalleMotivo").val();
    
    if (valorSeleccionado === '0230') {
        let mesSeleccionado = $("#mesSelect").val();
        let anioActual = new Date().getFullYear();

        let fecha = `01/${mesSeleccionado}/${anioActual}`;
		
        $('#rendicionDetalleFechaDesde').val(fecha);
        $('#rendicionDetalleFechaHasta').val(fecha);
        dtParams.fechaDesde = fecha;
        dtParams.fechaHasta = fecha;
    }

    dtParams.action = "formatear";
    dtParams.codigoMotivo = valorSeleccionado;
    callAjax(dtLink, dtParams, 'setPreFormato', 'errorFormto');
}

function formatDate(date) {
    let day = ("0" + date.getDate()).slice(-2);
    let month = ("0" + (date.getMonth() + 1)).slice(-2);
    let year = date.getFullYear();
    return `${day}-${month}-${year}`;
}


function setPreFormato(data) {
    let motivo = data.motivoActual;
    let textFecha = "Periodo";
    let textDesde = "";
    let textHasta = "";

    switch(motivo) {
        case "GYMPASS":
            textDesde = "Mes del Gasto";
            $('#fechaContainer').hide();
            $('#mesContainer').show();
			setMesPreFormato();
            break;
        case "EVENT":
            textDesde = "Realizacion del Evento";
            $('#rendicionDetalleFechaHastaContainer').hide();
            setFechaHastaPreFormato();
            break;
        case "VIAJE":
            textDesde = "Inicio del Viaje";
            textHasta = "Fin del Viaje";
            $('#rendicionDetalleFechaHastaContainer').show();
            break;
        case "AEREO":
            textDesde = "Fecha de Compra Aereo";
            $('#rendicionDetalleFechaHastaContainer').hide();
            setFechaHastaPreFormato();
            break;
        case "MENSU":
            textDesde = "Mes del Gasto";
            $('#rendicionDetalleFechaHastaContainer').hide();
            setFechaHastaPreFormato();
            break;
        default:
            textDesde = "Desde";
            textHasta = "Hasta";
            $('#rendicionDetalleFechaHastaContainer').show();
            //$('#rendicionDetalleFechaHasta').val("");
            $("#rendicionDetalleFechaDesde").off("change");
            break;
    }
    $('#preFormato').html(textFecha);
    $('#leyendaDesde').html(textDesde);
    $('#leyendaHasta').html(textHasta);
}
function errorFormto(data){
	console.log(data, "error")
}
function setFechaHastaPreFormato(){
	$("#rendicionDetalleFechaDesde").on("change", function(){
		let fechaDesde = $('#rendicionDetalleFechaDesde').val()
		$('#rendicionDetalleFechaHasta').val(fechaDesde)
	})
}
function continuar() {
	if (!validarContinuar()){
		return;
	}
	$('form').submit();
}
function validarContinuar() {
	var valid = true;
	
	if (!validateForm('rendicionDetalle', true))
		valid = false;
	
	if (compareDates($('#rendicionDetalleFechaDesde').val(), $('#rendicionDetalleFechaHasta').val()) == 1) {
		$('input[id^=rendicionDetalleFecha]').addClass('text-danger');
		$('input[id^=rendicionDetalleFecha]').parent().find('label').addClass('text-danger');
		showFormError('#rendicionDetalleFechaHasta', 'La fecha hasta no puede ser menor a la fecha desde');
		valid = false;
	}
	return valid;
}

function normalizeText(text) {
    return text.replace(/[^a-zA-ZáéíóúÁÉÍÓÚ0-9\s\/$%*#-]/g, "");
}