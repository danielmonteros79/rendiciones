var idArchivo = 0;

$(document).ready(function() {
	jQuery.validator.addMethod("checkArchivos", function(value, element) {
		if ($("input[name=accion]:checked").val() == "archivo" && $("#archivosASubirTable tbody tr").length == 0)
			return false;
		
		return true;
	}, "Debe ingresar por lo menos un archivo.");
	
	$("form").validate({
		rules : {
			accion: {
				required : true,
				checkArchivos : true
			}
		},
	    messages: {
			accion: {
				required: "Debe ingresar una opci\u00F3n."
			}
	    },
		errorPlacement: function(error, element) {
			error.appendTo("#errores");
		},
		invalidHandler: function(event, validator) {
			$('#errores').show();
		}
	});
});

function showAdjuntarImagenPopup() {
	if ($("#archivosASubirTable tbody tr").length == 0) {
		$("#errores").html("<label class='error'>Debe ingresar por lo menos un archivo.</label>");
		$('#errores').show();
	} else {
		$("#errores").html("");
		$('#errores').hide();
		var popUpObj = window.open("adjuntarImagenPopUp.do?accion=generar", "ModalPopUp",
				"toolbar=no," + "scrollbars=no," + "location=no," + "statusbar=no,"
				+ "menubar=no," + "resizable=0," + "width=650,"
				+ "height=225," + "left = 300," + "right = 300,"
				+ "top=200");
		popUpObj.focus();
		LoadModalDiv();
	}
}

function recuperarForm() {
	$.ajax( {
		url : "rendicionAviso.do?action=getAccion",
		type : "POST",
	    dataType: "json",
		success : function (data) {
			$('input:radio[name="accion"][value="' + escapeHtml(data.accion) + '"]').prop('checked', true);

			getArchivosASubir();
			
			if (data.accion == "archivo") {
				$('.imagenesTr').show();
			    $('#submitDiv').show();
			}
		}, error: function(xhr, textStatus, error){
		      console.log(xhr.statusText);
		      console.log(textStatus);
		      console.log(error);
		}
	});
}

function getArchivosASubir() {
	$.ajax( {
		url : "rendicionAviso.do?action=getArchivosASubir",
		type : "POST",
	    dataType: "json",
		success : function (data) {
			$.each(data, function(index) {
				agregarASubir(escapeHtml(data[index].nombre));
	        });
			
			checkUnsaved();
		}
	});
}

function checkUnsaved() {
	if ($("#archivosASubirTable tbody tr").length > 0) {
		$("#unsavedSpan").show();
		$("#imagenesDiv").show();
	} else {
		$("#unsavedSpan").hide();
		$("#imagenesDiv").hide();
	}
}

function cargar() {
	var fileName = $("#archivo").val();
	if (fileName == null || fileName == "")
		$("#validacionArchivo").html("");
	else {
		if (validarExtension(fileName, ['.pdf', '.tif'])) {
			if ($('#archivo')[0].files[0].size > (1.2 * 1024 * 1024) && ($('#archivo')[0].files[0].name.toLowerCase().indexOf('.tif') != -1))
				$("#validacionArchivo").html("El tama\u00D1o del archivo '.tif' no debe superar los 1.2 MB.");
			else if (existe($('#archivo')[0].files[0].name))
				$("#validacionArchivo").html("Ya existe un archivo con ese nombre.");
			else {
				$("#validacionArchivo").html("");
				var myFormData = new FormData();
				myFormData.append("archivo", $("#archivo").prop('files')[0]);
				$.ajax({
				  url: "rendicionAviso.do?action=cargarArchivo",
				  type: "POST",
				  processData: false,
				  contentType: false,
				  dataType : "json",
				  data: myFormData,
					success : function(data) {
						var idArchivo = escapeHtml(data.nombreArchivo.split(".").slice(0, -1).join(''));
						agregarASubir(escapeHtml(data.nombreArchivo));
						$("#archivo").val(null);
					    $('#errores').hide();
						checkUnsaved();
					},
					error : function() {
						alert("Error al agregar archivo a subir.");
					}
				});
			}
		} else {
			$("#validacionArchivo").html("Solo puede cargar archivos de tipo PDF o TIF.");
		}
	}
}

function existe(fileName) {
	var existe = false;
	$("#archivosASubirTable tr").each(function() {
		console.log($(this).children(":first").text() + " - " + fileName);
	    if ($(this).children(":first").text() == fileName)
	    	existe = true;
	});
	
	return existe;
}

function validarExtension(fileName, fileTypes) {
	dots = fileName.split(".");
	fileType = "." + dots[dots.length - 1];
	return (fileTypes.join(".").indexOf(fileType.toLowerCase()) != -1) ? true : false;
}

function agregarASubir(nombreArchivo) {
	$("#archivosASubirTable").append(
		"<tr id='archivo_" + idArchivo +"'>" +
			"<td>" + escapeHtml(nombreArchivo) + "</td>" +
			"<td>" +
				"<a class='borrar' href='#' onclick='borrarArchivo(\"archivo_" + idArchivo + "\",\"" + escapeHtml(nombreArchivo) + "\")'>" +
					"<img src='./images/iconos/borrar.png' alt='Borrar' title='Borrar'>" +
				"</a>" +
			"</td>" +
		"</tr>"
	);
	
	idArchivo ++;
}

function borrarArchivo(id, nombreArchivo) {
	var json = JSON.stringify(nombreArchivo);
	var jsonEnc = encodeURIComponent(json);
	
	$.ajax( {
		url : "rendicionAviso.do?action=borrarArchivo",
		type : "POST",
		data : "nombreArchivo=" + jsonEnc,  
		success : function () {
			$("#" + id).remove();
			checkUnsaved();
		}
	});
}

$("form input:radio").change(function () {
    if ($(this).val() == "caratula") {
        $('.imagenesTr').hide();
    } else {
        $('.imagenesTr').show();
    }
    
    $('#errores').hide();
    $('#submitDiv').show();
});

function escapeHtml(text) {
    return String(text)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}