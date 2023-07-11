jQuery(document).ready(function() {
	jQuery('#fechaDesde').datepicker({
		dateFormat : 'dd/mm/yy'
	});
	
	jQuery('#fechaHasta').datepicker({
		dateFormat : 'dd/mm/yy'
	});
	
	jQuery('#fecha1').datepicker({
		dateFormat : 'dd/mm/yy'
	});
	
	jQuery('#fecha2').datepicker({
		dateFormat : 'dd/mm/yy'
	});
	
	jQuery('#feDesde').datepicker({
		dateFormat : 'dd/mm/yy'
	});
	
	jQuery('#feHasta').datepicker({
		dateFormat : 'dd/mm/yy'
	});
	
	jQuery.datepicker.setDefaults(jQuery.datepicker.regional['es']);
});