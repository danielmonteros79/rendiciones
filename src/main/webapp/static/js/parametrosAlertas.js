$(document).ready(function() {
	showMessage('message', getLocalStorageItem('message'));
	$('.nav-parametros').addClass('active');
	setCombo('combos.do?action=getMotivos', '#motivo', { opcion: ['1', '2'].indexOf($('#glg').val()) == -1 ? 9 : 8, glg: ""});
	//setCombo('combos.do?action=getTiposGasto', '#filtroGasto', {codMotivo: $("#motivo").val() });
	console.log($("#motivo").val());
});



jQuery(document).ready(function() {
	$('#checkParametros').show();
	selectMotivoLoad();
	//ayudaMotivo();
});

function resetForm() {
	$(".message").html("");
	$("#motivo").val("");
	$("#gasto").val("");
	ayudaMotivo();
}

function agregarAlerta() {
	$("#addAlerta").submit();
}

function modificarAlerta(codAlerta) {
	$("#edit_" + codAlerta).submit();
}

function filtrar() {
	$('#modalLoading').modal('show');
}

function eliminarAlerta(codAlerta) {
	$("#delete_" + codAlerta).submit();
}

function selectMotivoLoad() {
	$.ajax( {
		url : "parametrosAlertas.do?accion=selectMotivo",
		type : "POST",
		data : "codMotivo=" + $("#motivo").val(),
		dataType: "json",
		success : function (data) {
			$("#gasto").empty().append("<option value=''></option>");
			$.each(data, function(index) {
				$("#gasto").append("<option value=" + data[index].codigo + ">" + data[index].descripcion + "</option>");
	       });
			$("#gasto").append("<option value='9999'>" + "9999 - TODOS LOS GASTOS" + "</option>");
		   if($("#codGasto").val())
			   $("#gasto").val($("#codGasto").val());
		}
	});
}

function selectMotivo() {
	if($("#motivo").val()){
		setCombo('combos.do?action=getTiposGasto', '#gasto', {codMotivo: $("#motivo").val() });
	}
	$.ajax( {
		url : "parametrosAlertas.do?accion=selectMotivo",
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
			url : "parametrosAlertas.do?accion=selectGasto",
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


function limpiar() {
	$('input[id^=filtro]').val('');
	$('input[id^=filtro]').removeClass('text-danger');
	AutoNumeric.getAutoNumericElement('input[id^=filtro].an-integer-pos').set('');
	validarFiltro();
	
	scrollToElem('#divFiltro', false);
}