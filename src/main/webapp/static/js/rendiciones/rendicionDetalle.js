$(document).ready(function() {
	$('.nav-rendiciones').addClass('active');
	setValidaciones();
	$('.basic-single').chosen();
	ayudaMotivo();
	
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