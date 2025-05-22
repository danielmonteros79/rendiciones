let dtLink = 'parametrosAlertas.do'

$(document).ready(function() {
	showMessage('message', getLocalStorageItem('message'));
	$('.nav-parametros').addClass('active');
	setCombo('combos.do?action=getMotivos', '#motivo', { opcion: ['1', '2'].indexOf($('#glg').val()) == -1 ? 9 : 8, glg: ""});
	//setCombo('combos.do?action=getTiposGasto', '#filtroGasto', {codMotivo: $("#motivo").val() });
	console.log($("#motivo").val());
	dtParams = { accion: 'filtrar', cod_motivo: escapeHTML($("#motivo").val()) };
	filtrarAlertas();
});


jQuery(document).ready(function() {
	$('#checkParametros').show();
	selectMotivoLoad();
	//ayudaMotivo();
});

function filtrarAlertas() {
	loadAlertas();
}

function loadAlertas() {
	loadTable('#motivoDtContainer', dtLink, dtParams);
}

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
	 $("#edit_" + escapeHTML(codAlerta)).submit();
}

function filtrar() {
	$('#modalLoading').modal('show');
}

function eliminarAlerta(codAlerta) {
	$("#delete_" + escapeHTML(codAlerta)).submit();
}

function selectMotivoLoad() {
    $.ajax({
        url: "parametrosAlertas.do?accion=selectMotivo",
        type: "POST",
        data: { codMotivo: encodeURIComponent($("#motivo").val()) },
        dataType: "json",
        success: function (data) {
            try {
                if (Array.isArray(data)) {
                    $("#gasto").empty().append("<option value=''></option>");

                    $.each(data, function (index, item) {
                        const codigo = encodeHTML(item.codigo);
                        const descripcion = encodeHTML(item.descripcion);

                        $("#gasto").append(`<option value="${codigo}">${descripcion}</option>`);
                    });

                    $("#gasto").append("<option value='9999'>9999 - TODOS LOS GASTOS</option>");

                    if ($("#codGasto").val()) {
                        $("#gasto").val(escapeHTML($("#codGasto").val()));
                    }
                } else {
                    console.error('Respuesta no válida del servidor:', data);
                    showError({ message: 'Formato de respuesta inválido' });
                }
            } catch (err) {
                console.error('Error procesando la respuesta:', err);
                showError({ message: 'Error al procesar la respuesta del servidor', error: err });
            }
        },
        error: function (data) {
            console.error('Error en la petición AJAX:', data);
            showError({ message: 'Error en la comunicación con el servidor' });
        }
    });
}

function encodeHTML(str) {
    const div = document.createElement('div');
    div.innerText = str || '';
    return div.innerHTML;
}

function showError(errorData) {
    const errorMessage = typeof errorData === 'string' ? encodeHTML(errorData) : encodeHTML(errorData.message || 'Error desconocido');
    console.error('Error:', errorMessage);
    alert(`Error: ${errorMessage}`);
}

function selectMotivo() {
    const motivoVal = $("#motivo").val();

    if (motivoVal) {
        setCombo('combos.do?action=getTiposGasto', '#gasto', { codMotivo: encodeURIComponent(motivoVal) });
    }

    $.ajax({
        url: "parametrosAlertas.do?accion=selectMotivo",
        type: "POST",
        data: { codMotivo: encodeURIComponent(motivoVal) },
        dataType: "json",
        success: function (data) {
            try {
                if (Array.isArray(data)) {
                    $("#gasto").empty().append("<option value=''></option>");

                    $.each(data, function (index, item) {
                        const codigo = encodeHTML(item.codigo);
                        const descripcion = encodeHTML(item.descripcion);

                        $("#gasto").append(`<option value="${codigo}">${descripcion}</option>`);
                    });

                    $("#gasto").append("<option value='9999'>9999 - TODOS LOS GASTOS</option>");
                } else {
                    console.error('Respuesta no válida del servidor:', data);
                    showError({ message: 'Formato de respuesta inválido' });
                }
            } catch (err) {
                console.error('Error procesando la respuesta:', err);
                showError({ message: 'Error al procesar la respuesta del servidor', error: err });
            }
        },
        error: function (data) {
            console.error('Error en la petición AJAX:', data);
            showError({ message: 'Error en la comunicación con el servidor' });
        }
    });
}

function selectGasto() {
	if ($("#gasto").val() != '9999') {
		$.ajax( {
			url : "parametrosAlertas.do?accion=selectGasto",
			type : "POST",
			data : "codGasto=" + encodeURIComponent($("#gasto").val()),
			dataType: "json",
			success : function (data) {
				$("#motivo").val(escapeHTML(data.codGasto));
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

function escapeHTML(str) {
    return DOMPurify.sanitize(str);
}