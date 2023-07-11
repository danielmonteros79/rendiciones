var totalPesos = 0;
var totalDolar = 0;
var idRes = [];

jQuery(document).ready(function() {
	$('#checkCierre').show();
	separadorDecimalesInicial = ".";
	separadorDecimales = ",";
	separadorMiles = ".";
	
});

function arreglar(total, moneda) {
	if(idRes.length >= 10){
		alert("Solo puede seleccionar 10 resúmenes");
		return;
	}		
		
	var numeroo = "";
	total = "" + total;
	partes = total.split(separadorDecimalesInicial);
	entero = partes[0];
	if (partes.length > 1) {
		decimal = partes[1];
	}
	cifras = entero.length;
	cifras2 = cifras
	for (a = 0; a < cifras; a++) {
		cifras2 -= 1;
		numeroo += entero.charAt(a);
		if (cifras2 % 3 == 0 && cifras2 != 0) {
			numeroo += separadorMiles;
		}
	}
	if (partes.length > 1) {
		numeroo += separadorDecimales + decimal;
	}
	if (moneda === "ARS") {
		document.getElementById("totalPesos").innerHTML = numeroo;
	} else {
		document.getElementById("totalDolar").innerHTML = numeroo;
	}
}

// $(document).ready(function() {
//	
// });

function clickearCheckbox(idResumen, moneda, monto, checkbox) {
	var selectedMoneda = {};

	var numberOfChecked = $('input.cbox:checkbox:checked').length;
	var valor = +parseFloat(monto);
	if (moneda === "ARS") {
		if (checkbox.checked && numberOfChecked <= 10) {
			idRes.push(idResumen);
			console.log(idRes);
			selectedMoneda[moneda] = valor;
			totalPesos += valor;
			console.log(totalPesos);
			console.log(selectedMoneda);
			// document.getElementById("total").innerHTML = total;
		} else if (!checkbox.checked && numberOfChecked <= 10) {
			var index = idRes.indexOf(idResumen);
			if (index > -1) {
				idRes.splice(index, 1);
			}
			console.log(idRes);
			selectedMoneda[moneda] = valor;
			totalPesos -= valor;
			// document.getElementById("total").innerHTML = numeroo;
			console.log(totalPesos);
			console.log(selectedMoneda);
		}
		arreglar(totalPesos.toFixed(2), moneda);
	} else if (moneda === "USD") {
		if (checkbox.checked && numberOfChecked <= 10) {
			selectedMoneda[moneda] = valor;
			totalDolar += valor;
			console.log(totalDolar);
			console.log(selectedMoneda);
			// document.getElementById("total").innerHTML = total;
		} else if (!checkbox.checked && numberOfChecked <= 10) {
			selectedMoneda[moneda] = valor;
			totalDolar -= valor;
			// document.getElementById("total").innerHTML = numeroo;
			console.log(totalDolar);
			console.log(selectedMoneda);
		}
		arreglar(totalDolar.toFixed(2), moneda);
	}

	if (numberOfChecked > 10) {
		$(this).prop('checked', false);
		alert("No se pueden seleccionar m\u00E1s de 10 registros");
	}

}

function ShowCierreTarj() {
	if(idRes.length <= 0){
		alert("Debe serleccionar algun resumen");
		return;
	}
	var codUsr = $("#usuario").val();
	popUpObj = window.open("ShowCierreTarj.do?idSecResumen=" + idRes
			+ "&totalPes=" + totalPesos + "&totalDol=" + totalDolar + "&codUsr=" + codUsr,
			"ModalPopUp", "toolbar=no," + "scrollbars=no," + "location=no,"
					+ "statusbar=no," + "menubar=no," + "resizable=0,"
					+ "width=800," + "height=225," + "left = 300,"
					+ "right = 300," + "top=300");

	popUpObj.focus();
	LoadModalDiv();
}

function checkRendiciones(check) {
	var $checkboxes = $('#Rendiciontbl').find('input[id=checkCierre]');

	for (var i = 0; i < $checkboxes.size(); i++) {

		if ($checkboxes.get(i).checked) {
			seleccionado = false;
			break;
		}
	}

}

function selectMotivo() {
	$.ajax({
		url : "ShowCierreTarj.do?accion=selectMotivo",
		type : "POST",
		data : "codMotivo=" + $("#motivo").val(),
		dataType : "json",
		success : function(data) {
			$("#gasto").empty().append("<option value=''></option>");
			$.each(data, function(index) {
				$("#gasto").append(
						"<option value=" + data[index].id + ">"
								+ data[index].descripcion + "</option>");
			});
			// $("#gasto").append("<option value='9999'>" + "9999 - TODOS LOS
			// GASTOS" + "</option>");
		},
		error : function(data) {
			console.log(data);
		}
	});
}

function showThuban(Link, rend) {
	window.open(Link + rend);
}