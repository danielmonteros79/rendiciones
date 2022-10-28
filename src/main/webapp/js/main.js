jQuery(document).ready(function() {
	$('#checkRendiciones').hide();
	$('#checkAprobacion').hide();
	$('#checkParametros').hide();
	$('#checkReemplazar').hide();
	$('#checkCierre').hide();
	$("#checkResumen").hide();
	$("#checkCuadro").hide();

	$.balloon.defaults.tipSize = 12;
	$.balloon.defaults.maxLifetime = 5000;
	
	$.datepicker.setDefaults({ beforeShow: function (i) { if ($(i).attr('readonly')) { return false; } } });
});

function mostrarOpciones(menu) {
	if ($("#opciones" + menu).is(":visible")) 
		$("#opciones" + menu).hide();
	else {
		$("[id^=opciones]").hide();
		$("#opciones" + menu).show();
	}
};

function numericOnly(e){
	var code = e.charCode || e.keyCode;
	
	if (e.keyCode == 46 && e.charCode == 0)
    	return;
    else if ($.inArray(code, [8, 9, 13]) !== -1 || (code >= 35 && code <= 40))
    	return;
    if (!(code >= 48 && code <= 57))
        return false;
}

function ayudaMotivo(){
	var motivo = $("#motivo").val();
	
	if (motivo === "")
		$("#imagen").hide();
	else
		$("#imagen").show();
	
	$("#ayudaMotivo").attr("href", "ayuda/ayuda_" + motivo + ".pdf");
}

function LoadModalDiv() {
	var bcgDiv = document.getElementById("divBackground");
	bcgDiv.style.display = "block";
}

function HideModalDiv() {
	var bcgDiv = document.getElementById("divBackground");
	bcgDiv.style.display = "none";
}

var cssDefaultBalloon = {
    border: 'solid 4px #000000',
    borderRadius: '25px',
    padding: '8px 10px',
    fontSize: '70%',
    fontWeight: 'bold',
    backgroundColor: '#FFFFFF',
    color: '#000000'
}