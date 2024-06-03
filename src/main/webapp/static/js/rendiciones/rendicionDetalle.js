var dtLink = 'saveRendicion.do';

$(document).ready(function() {
	$('.nav-rendiciones').addClass('active');
	setValidaciones();
	$('.basic-single').chosen();
	ayudaMotivo();
	getPreformato()
	
});

function setValidaciones() {
	$('#rendicionDetalleMotivo').attr('required', true);
	$('#rendicionDetalleDescripcion').attr('required', true);
	$('input[id^=rendicionDetalleFecha]').attr('data-maxdate', formatDate(new Date())).datepicker('setEndDate', new Date());
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

function getPreformato(){
	let valorSeleccionado = $("#rendicionDetalleMotivo").val();
	dtParams.action = "formatear";
	dtParams.codigoMotivo = valorSeleccionado;
	callAjax(dtLink, dtParams, 'setPreFormato', 'errorFormto');
}

function setPreFormato(data){
    let motivo = data.motivoActual
    let textFecha = "Periodo";
    let textDesde = ""
    let textHasta =""
    switch(motivo){
        case "EVENT":
        textDesde = "Realizacion del Evento"
        $('#rendicionDetalleFechaHastaContainer').hide()
        setFechaHastaPreFormato()
        break;

        case "VIAJE":
        textDesde = "Inicio del Viaje"
        textHasta = "Fin del Viaje"
        $('#rendicionDetalleFechaHastaContainer').show()
        break;

        case "AEREO":
        textDesde = "Fecha de Compra Aereo"
        $('#rendicionDetalleFechaHastaContainer').hide()
        setFechaHastaPreFormato()
        break;

        case "MENSU":
        textDesde = "Mes del Gasto"
        $('#rendicionDetalleFechaHastaContainer').hide()
        setFechaHastaPreFormato()
        break;

        default:
            textDesde = "Desde"
            textHasta = "Hasta"
            $('#rendicionDetalleFechaHastaContainer').show()
            $('#rendicionDetalleFechaHasta').val("")
            // elimino eventListener
            $("#rendicionDetalleFechaDesde").off("change");
        break;

    }

    $('#preFormato').html(textFecha)
    $('#leyendaDesde').html(textDesde)
    $('#leyendaHasta').html(textHasta)
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
	if (!validarContinuar())
		return;	
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