
var modalAlertaLoadParams

jQuery(document).ready(function() {
	$('#checkParametros').show();
	$('.nav-parametros').addClass('active');
	setFormValidate();
	
	setCombo('combos.do?action=getMotivos', '#filtroMotivo', { opcion: ['1', '2'].indexOf($('#glg').val()) == -1 ? 9 : 8, glg:""});

	setCombo('combos.do?action=getTiposGasto', '#filtroGasto', {codMotivo: $('#filtroMotivo').val()});
	
	
    $('#impCant').bind("cut copy paste",function(e) {
        e.preventDefault();
    });
    $('#impCant').attr("autocomplete", "off");
    $('#impCant').val($('#impCant').val().replace(/^0+/, ''));
});

function confirmarEliminarAlerta() {
	if (confirm("\u00bfEst\u00e1 seguro que quiere eliminar la alerta?"))
		$("#parametrosAlertasForm").submit();
}

function setFormValidate() {
	jQuery.validator.addMethod("importe", function(value, element) {
		try {
			$("#errorImpCant").html("");
			
			if ($('#montCant').val() == 'M') {
				var impCant = $('#impCant').val();
				var indexDecimal = $("#impCant").val().indexOf('.');
				
				if (impCant.substring(0, indexDecimal == -1 ? impCant.length : indexDecimal).length > 14) {
					$("#errorImpCant").html("El importe no puede tener m\u00e1s de 14 enteros.");
					return false;
				} else if (indexDecimal != -1 && impCant.substring(indexDecimal).length > 3) {
					$("#errorImpCant").html("El importe no puede tener m\u00e1s de 2 decimales.");
					return false;
				}
			}
			
	        return true;
		} catch(e) {
			return false;
		}
    }, "");
	
	jQuery.validator.addMethod("nivelMax", function(value, element) {
		try {
        	$("#errorNiveles").html("");
            
            if ($("#nivMax").val() < $("#nivMin").val()) {
            	$("#errorNiveles").html("El nivel m\u00e1ximo debe ser mayor o igual al m\u00ednimo.");
    			return false;
            }
            
            return true;
		} catch(e) {
			return false;
		}
    }, "");
	
	$("#parametrosAlertasForm").validate({
		rules: {
			codMotivo:  { required : true },
			codGasto:   { required : true },
			estado: 	{ required : true },
			rend:		{ required : true },
			montCant: 	{ required : true },
			impCant:	{ required : true, importe: true },
			periodo: 	{ required : true },
			criticidad: { required : true },
			nivMax: 	{ required : true, nivelMax: true },
			nivMin: 	{ required : true }
		},
		messages: {
			codMotivo: { required : globalMsgRequired },
			codGasto: { required : globalMsgRequired },
			estado: globalMsgRequired ,
			rend: globalMsgRequired ,
			montCant: { required : globalMsgRequired },
			impCant: { required : globalMsgRequired },
			periodo: { required : globalMsgRequired },
			criticidad: { required : globalMsgRequired },
			nivMax: { required : globalMsgRequired },
			nivMin: { required : globalMsgRequired }
		},
		errorElement: "p",
		errorPlacement: function(error, element) {      
        	//error.insertAfter(element);
        }
	});
}

function selectMotivo() {
	$.ajax( {
		url : "parametrosAlertasDetalle.do?accion=selectMotivo",
		type : "POST",
		data : "codMotivo=" + $("#motivo").val(),
		dataType: "json",
		success : function (data) {
			$("#gasto").empty().append("<option value=''></option>");
			$.each(data, function(index) {
				$("#gasto").append("<option value=" + data[index].codigo + ">" + data[index].descripcion + "</option>");
	       });
			$("#gasto").append("<option value='9999'>" + "9999 - TODOS LOS GASTOS" + "</option>");
		},
		error: function (data) {
			console.log(data);
		}
	});
}

function selectGasto() {
	if ($("#gasto").val() != '9999') {
		$.ajax( {
			url : "parametrosAlertasDetalle.do?accion=selectGasto",
			type : "POST",
			data : "codGasto=" + $("#gasto").val(),
			dataType: "json",
			success : function (data) {
				$("#motivo").val(data.codGasto);
			},
			error: function (data) {
				console.log(data);
			}
		});
	}
}

function numericOnly(e){
	var code = e.charCode || e.keyCode;
	var impCant = $("#impCant").val();
	var indexNewChar = $("#impCant")[0].selectionStart;
	var indexDecimal = $("#impCant").val().indexOf('.');
	
	if ($('#montCant').val() == 'C') {
	    if (e.keyCode == 46 && e.charCode == 0)
	    	return;
	    else if ($.inArray(code, [8, 9, 13]) !== -1 || (code >= 35 && code <= 40))
	    	return;
	    if (!(code >= 48 && code <= 57))
	        return false;
	} else if ($('#montCant').val() == 'M') {
		if (indexDecimal == -1 && e.keyCode == 0 && e.charCode == 46) {
			return;
		}
	    else if ($.inArray(code, [8, 9, 13]) !== -1 || (code >= 35 && code <= 40))
	        return;
	    if (!(code >= 48 && code <= 57))
	        return false;
	    
	    // Hasta 2 decimales
	    if (indexDecimal != -1 && indexNewChar > indexDecimal && impCant.substring(indexDecimal).length > 2)
	        return false;
	    
	    // Hasta 14 enteros
	    if (impCant.substring(0, indexDecimal == -1 ? impCant.length : indexDecimal).length > 13 &&
	       (indexDecimal == -1 || indexNewChar < indexDecimal))
	        return false;
	}
}

function impCantChange() {
	$('#impCant').val(null);
	if ($('#montCant').val() == 'M')
		$('#impCant').attr('maxlength', '17');
	else
		$('#impCant').attr('maxlength', '16');
}