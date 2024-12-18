var cambiarFondoId = "";
var identifNuevoGasto = 1;
var gastoIdSeleccionado = 0;
var maximoPermitido = 15;

$(document).ready(function() {
    $('#datosGastoSelected').hide();
    $('#distribucionTable').hide();
    $('#gastosDistribuidosTable').hide();
    $('#msgEspera').hide();

    $(".distribucionTableClass").find('tbody tr').remove();
    $('.buttonSave').attr('disabled', false);
    $('.distribucionTableClass').attr('disabled', false);
    $('#checkAprobacion').show();

    $.ajax({
        url: "checkGastosMotivoDistribucion.do?codMotivo=" + escapeHtml($('#motivoRendicion').val()),
        type: "POST",
        dataType: "json",
        success: function(data) { 
            $.each(data, function(index) {
                for (var i = 0; i < data[index].gastos.length; i++) {
                    var gastoId = escapeHtml(data[index].gastos[i].id).substring(0, 4);
                    var gastoDescripcion = escapeHtml(data[index].gastos[i].descripcion.trim());
                    $('#comboGasto').append(
                        $("<option>").val(gastoId).text(gastoDescripcion)
                    );
                }
            });
        },
        error: function() {
            alert("Ocurrió un error general al intentar obtener los codigos de gastos");
        }
    });
});

$(".CambioFondo tbody tr")
		.click(
				function() {

					var continuar = true;
					if (gastoIdSeleccionado != 0) {
						if (!confirm("Al seleccionar otro Gasto se perder\u00E1n los cambios realizados para el anterior seleccionado.\n \u00bfDesea continuar?.")) {
							//							
							continuar = false;
						}
					}
					if (continuar) {
						$("#saldoTable" + gastoIdSeleccionado).val("");
						gastoIdSeleccionado = 0;
						$(this).addClass('selected').siblings().removeClass(
								'selected');

						var value = $(this).find('td:first').html();
						cambiarFondoId = $(this).closest("tr").attr('id');

						$('#descripcionSelected').val(escapeHtml($(this).find('td:eq(2)').text().trim()));
				        $('#montoModificado').val(escapeHtml($(this).find('td:eq(4)').text().trim()));
				        $('#montoOriginal').val(escapeHtml($(this).find('td:eq(4)').text().trim()));
				        $('#idGastoOriginal').val(escapeHtml($(this).find('td:eq(0)').text().trim()));
				        $('#centroCostoOriginal').val(escapeHtml($(this).find('td:eq(3)').text().trim()));
				        $('#centroCostoModificado').val(escapeHtml($(this).find('td:eq(3)').text().trim()));

						$("#montoModificado").val(
								$("#montoModificado").val().replace(",", "."));
						$("#montoOriginal").val(
								$("#montoOriginal").val().replace(",", "."));
						$('#saldoPendiente').val(
								parseFloat($('#montoOriginal').val()));
						$('#datosGastoSelected').show();
						$('#distribucionTable').hide();
						identifNuevoGasto = 1;

						$(".distribucionTableClass").find('tbody tr').remove();
						$(".gastosDistribuidosClass").find('tbody tr').remove();
						$('#comboGasto').val(escapeHtml($(this).find('td:eq(1)').text().trim()));
						$('#gastosDistribuidosTable').hide();
						obtenerGastosDistribuidos(escapeHtml($(this).find('td:eq(0)').text().trim()));
					}
				});


function guardarModifGasto() {
    if ($('#montoModificado').val() == "") {
        alert("Debe ingresar un Monto para poder guardar");
        return;
    }

    var montoModif = parseFloat($('#montoModificado').val());
    var montoOrig = parseFloat($('#montoOriginal').val());

    if (montoModif == 0) {
        alert("El Monto ingresado debe ser MAYOR a 0 (cero)");
    } else if (montoModif > montoOrig) {
        alert("El monto ingresado no debe superar el importe: " + montoOrig);
    } else {
        var gastoCod = $('#comboGasto option:selected').val();
        var markup = $("<tr>", { id: "nuevoGasto" + identifNuevoGasto })
            .append($("<td>", { style: 'display:none;', text: gastoCod }))
            .append($("<td>", { align: 'center', text: $('#comboGasto option:selected').text() }))
            .append($("<td>", { align: 'center', text: montoModif }))
            .append($("<td>", { align: 'center', text: $('#centroCostoModificado').val() }))
            .append($("<td>", { align: 'center' }).append(
                $("<img>", { src: './images/clearButton.png', click: function() { deleteNuevoGasto('nuevoGasto' + identifNuevoGasto); } })
            ));

        $(".distribucionTableClass").append(markup);
        identifNuevoGasto++;
        $('#distribucionTable').show();
    }
}

function deleteNuevoGasto(dat) {
	var conf = confirm("\u00bfDesea elimnar el registro?")
	if (conf) {
		// alert(dat);
		var montoEliminar = parseFloat($('#' + dat).find('td:eq(2)').text()
				.trim());
		// alert(montoEliminar);
		var montoModif = parseFloat($('#montoModificado').val());
		var montoOrig = parseFloat($('#montoOriginal').val());
		var saldoPendiente = parseFloat($('#saldoPendiente').val());

		var value = $(".CambioFondo tbody tr .selected").find('td:first')
				.html();
		var gastoId = $(".selected").find('td:eq(0)').text().trim();
		// alert(gastoId);
		var idTD = "#saldoTable" + gastoId;

		var saldo = saldoPendiente + montoEliminar;
		$(idTD).val(saldo);
		$('#saldoPendiente').val(saldo);
		$('#' + dat).remove();
	}

}

function confirmarNuevosGastos() {
	if (confirm("\u00bfConfirma la redistribuci\u00F3n del Gasto: "
			+ gastoIdSeleccionado + "?")) {
		$('#msgEspera').show();
		$('.buttonSave').attr('disabled', true);
		$('.distribucionTableClass').attr('disabled', true);
		var montoAgrupado = "";
		var ccostoAgrupado = "";
		var codGastoAgrupado = "";
		var index = 1;
		$(".distribucionTableClass").find('tbody tr').each(function() {
			$this = $(this);
			codGastoAgrupado += $this.find("td:eq(0)").html() + ";";
			montoAgrupado += $this.find("td:eq(2)").html() + ";";
			ccostoAgrupado += $this.find("td:eq(3)").html() + ";";
			// alert("cod: "+$this.find("td:eq(0)").html())
		});
		var query = "accion=IMP&idRendicion=" + $('#idRendicion').val();
		query += "&codGastoRedistribucion=" + codGastoAgrupado;
		query += "&gastoRedistribucion=" + gastoIdSeleccionado;
		query += "&montoGastoItems=" + montoAgrupado;
		query += "&centroCostoItems=" + ccostoAgrupado;
		// alert(query);
		executePeticion(query)

	}
}

function anularGastos() {
	if (confirm("Se anular\u00e1n todos los Gastos agrupados al Gasto Original:"
			+ $('#idGastoOriginal').val() + "\n \u00bfDesea continuar?")) {
		$('#msgEspera').show();
		var query = "accion=ANU&idRendicion=" + $('#idRendicion').val();
		query += "&gastoRedistribucion=" + gastoIdSeleccionado;
		// query += "&montoGastoItems=" + montoAgrupado;
		// query += "&centroCostoItems=" + ccostoAgrupado;
		executePeticion(query);

	}
}

function executePeticion(query) {
	$
			.ajax({
				url : "confirmDistribucionGasto.do?" + query,
				type : "POST",
				dataType : "json",
				success : function(data) {
					if (data.msg != "") {
						$("#saldoTable" + gastoIdSeleccionado).val("");
						alert(data.msg);
						window.location.reload();
					} else {
						alert("ERROR: " + data.error);
						$('#msgEspera').hide();
						$('.buttonSave').attr('disabled', false);
						$('.distribucionTableClass').attr('disabled', false);
					}
				},
				error : function() {
					alert("Ocurrió un error general al intentar Confirmar la distribución");
				}
			});
}

function obtenerGastosDistribuidos(gastoSeleccionado) {
	$
			.ajax({
				url : "obtenerDistribucionGasto.do?gastoSeleccionado="
						+ gastoSeleccionado,
				type : "POST",
				dataType : "json",
				success : function(data) {
					$
							.each(
									data,
									function(index) {
										var totalDist = 0;
										for (var i = 0; i < data[index].gastos.length; i++) {
											var montoRedistribucion = data[index].gastos[i].monto
													.trim().replace(",", ".");
											// alert(monto);
											var totalDist = totalDist
													+ parseFloat(montoRedistribucion);
											var markup = "<tr>"
													+ "<td align='center'>"
													+ data[index].gastos[i].idGasto
													+ "</td>"
													+ "<td align='center'>"
													+ data[index].gastos[i].descGasto
													+ "</td>"
													+ "<td align='center'>"
													+ data[index].gastos[i].centroCostoGasto
													+ "</td> "
													+ "<td align='center'>"
													+ data[index].gastos[i].monto
													+ "</td> " + "</tr>";
											$(".gastosDistribuidosClass")
													.append(markup);
											identifNuevoGasto++;
											$('#gastosDistribuidosTable')
													.show();

										}
										// Se identifica la fila seleccionada
										// para colocarle el Saldo.
										var montoOrig = parseFloat($(
												'#montoOriginal').val());
										var value = $(
												".CambioFondo tbody tr .selected")
												.find('td:first').html();
										gastoIdSeleccionado = $(".selected")
												.find('td:eq(0)').text().trim();
										var idTD = "#saldoTable"
												+ gastoIdSeleccionado;

										var saldo = montoOrig - totalDist;
										$(idTD).val(saldo);
										// $('#montoOriginal').val(saldo);
										$('#saldoPendiente').val(saldo);
										// alert(totalD ist);
									})
				},
				error : function() {
					alert("Ocurrió un error general al intentar Confirmar la distribución");
				}
			});
}

function keyPressMonto(e) {

	var code = e.charCode || e.keyCode;
	// alert(code);
	var monto = $("#montoModificado").val();
	var indexNewChar = $("#montoModificado")[0].selectionStart;
	var indexDecimal = $("#montoModificado").val().indexOf('.');

	if (indexDecimal == -1 && e.keyCode == 0 && e.charCode == 46) {
		return;
	} else if ($.inArray(code, [ 8, 9, 13 ]) !== -1
			|| (code >= 35 && code <= 40))
		return;
	if (!(code >= 48 && code <= 57))
		return false;

	// Hasta 2 decimales
	if (indexDecimal != -1 && indexNewChar > indexDecimal
			&& monto.substring(indexDecimal).length > 2)
		return false;

	// Hasta 13 enteros
	if (monto.substring(0, indexDecimal == -1 ? monto.length : indexDecimal).length > 12
			&& (indexDecimal == -1 || indexNewChar < indexDecimal))
		return false;
}
function keyPressNumber(e) {

	var code = e.charCode || e.keyCode;

	if (e.charCode == 46) {
		return false;
	} else if ($.inArray(code, [ 8, 9, 13 ]) !== -1
			|| (code >= 35 && code <= 40))
		return;
	if (!(code >= 48 && code <= 57))
		return false;

}
function volver() {

	window.location.href = "aprobacionDetalle.do?action=aprobacionDetalle&codigo="
			+ $('#idRendicion').val() + "&usuarioRendicion="+
	$('#usuarioRendicion').val().trim()+"&glg=3&estadoRend=PGLG";
}

function escapeHtml(text) {
    return String(text)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}