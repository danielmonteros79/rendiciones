var dtParams = {};
var globalOpcionVacia = '<option value="">Seleccion\u00e1 una opci\u00f3n</option>';
var globalMsgRequired = 'Campo obligatorio.';
var globalMsgCuit = 'CUIT incorrecto.';
function globalMsgLength(length) { return 'El campo debe tener ' + length + ' caracteres.' };
function globalMsgMaxValue(maxValue) { return 'El valor no puede ser mayor a ' + maxValue + '.' };
function globalMsgMinDate(minDate) { return 'La fecha no puede ser anterior a ' + minDate + '.' };
function globalMsgMaxDate(maxDate) { return 'La fecha no puede ser posterior a ' + maxDate + '.' };

function escapeHtml(text) {
    return DOMPurify.sanitize(text);
}

$(document).ready(function() {
	$('.has-float-label label').click(function() {
		$(this).parent().find('input, select, textarea')[0].focus();
	});

	$('.modal').on('show.bs.modal', function() {
		var zIndex = 1040 + (10 * $('.modal:visible').length);
		$(this).css('z-index', zIndex);
		setTimeout(function() {
			$('.modal-backdrop').not('.modal-stack').css('z-index', zIndex - 1).addClass('modal-stack');
		}, 0);
	});

	$('.modal').on('shown.bs.modal', function(event) {
		$('body').addClass('modal-open');
	});

	$('.modal').on('hidden.bs.modal', function(event) {
		if ($('.modal:visible').length > 0)
			$('body').addClass('modal-open');
	});

	$('.modal').scroll(function() {
		$('.datepicker').datepicker('place');
	});

	setTooltips();
	setDatepickerElements();
	setAutonumericElements();

	toggleScrollButton();
	$('#scroll-btn').click(function() {
		scrollToTop();
	});



	$(window).scroll(function() {
		toggleScrollButton();


	});

	$(window).scroll(function() {
		$('[data-toggle="tooltip"]').tooltip({
			placement: 'top'
		})
	});
	
	$('#manualRendiciones').click(function(){
		let urlPdf = 'ayuda/manual_rendiciones.pdf';
		window.open(urlPdf, '_blank')
	})

	setTimeout(function() {
		if($('#containerImagenesCargadas').find("div").length > 0){
			$('#abrirTodas').show()
			$('#containerImagenesCargadas').addClass('flex-wrap')
			 
		}else{
			$('#abrirTodas').hide()
		}
	}, 500);

	

});


//Comportamiento de icono de scroll

function toggleScrollButton() {
	let scrollButton = $('#scroll-btn');
	let arrowIcon = scrollButton.find('i');
	let windowHeight = $(window).height();
	let documentHeight = document.documentElement.scrollHeight
	let scrollTop = window.scrollY || document.documentElement.scrollTop || document.body.scrollTop;

	scrollButton.toggleClass('show', documentHeight > windowHeight);
	arrowIcon.toggleClass('fa-arrow-up', scrollTop + windowHeight >= documentHeight);
	arrowIcon.toggleClass('fa-arrow-down', scrollTop + windowHeight < documentHeight);
}

function scrollToTop() {
	$('html, body').animate({ scrollTop: 0 }, 'slow');

}



$(document).ajaxComplete(function() {
	$('#modalLoading').modal('hide');


});


function ayudaMotivo() {
	let motivo = $("#rendicionDetalleMotivo").val()
	if (motivo === "")
		$("#ayudaMotivo").hide();
	else
		$("#ayudaMotivo").show();
	let ayuda = "ayuda_" + motivo + ".pdf"
	if (buscarPdf(ayuda)) {
		$("#ayudaMotivo").attr("href", "ayuda/" + ayuda);
	} else {
		$("#ayudaMotivo").attr("href", "ayuda/ayuda_por_defecto.pdf");
	}
}


function buscarPdf(pdf) {
	let url = 'ayuda/' + pdf;
	let http = new XMLHttpRequest();
	http.open('HEAD', url, false);
	http.send();
	return http.status != 404
}

function setTooltips() {
	$('[data-toggle="tooltip"]').tooltip();
}

function removeTooltip() {
	$('.tooltip').remove();
}

function setDatepickerElements() {
	$('.datepicker').datepicker({
		language: 'es',
		format: 'dd/mm/yyyy',
		todayBtn: "linked",
		todayHighlight: true,
		autoclose: true
	});

	$('.datepicker-btn').click(function() {
		$($(this).parent().parent().children().first()).datepicker('show');
	});
}

function setAutonumericElements() {
	setAutonumericElementInteger('.an-integer-pos');

	if ($('.an-float-pos').length > 0)
		var anElement = new AutoNumeric('.an-float-pos', autoNumericOptionsFloatPos);
}

function setAutonumericElementInteger(selector, maximumValue) {
	if ($(selector).length > 0) {
		var anElement = new AutoNumeric(selector, autoNumericOptionsIntegerPos);

		if (maximumValue)
			anElement.update({ maximumValue: maximumValue });

		// Para que no deje poner separador decimal
		$(selector).on("keydown", function(event) {
			if (event.key == '.')
				return false;
		});
	}
}

function scrollToElem(elem, allways, modalSelector, closestSelector) {
	try {
		var $elem = $(elem);
		if (allways || !isElemVisible(elem))
			$(modalSelector || 'html').animate({ scrollTop: (modalSelector ? $elem.closest(closestSelector).position().top + 70 : $elem.offset().top) }, 500);
	} catch (e) { }
}

function isElemVisible(elem) {
	var $elem = $(elem);
	var $window = $(window);

	var docViewTop = $window.scrollTop();
	var docViewBottom = docViewTop + $window.height();

	var elemTop = $elem.offset().top;
	var elemBottom = elemTop + $elem.height();

	return ((elemBottom <= docViewBottom) && (elemTop >= docViewTop));
}

function compareDates(date1, date2) {
	if (!date1 || !date2)
		return 0;

	date1 = date1.substring(6) + date1.substring(3, 5) + date1.substring(0, 2);
	date2 = date2.substring(6) + date2.substring(3, 5) + date2.substring(0, 2);

	if (date1 > date2)
		return 1;
	else if (date1 == date2)
		return 0;
	else
		return -1;
}



function setCombo(url, comboSelector, params, selectedOption, showEmpty) {
	$.ajax({
		url: url,
		type: "GET",
		data: params,
		success: function(response) {

			let data = transformResponse(response)

			if (data.status == 'ERROR')
				return showError(data);

			$(comboSelector).empty();
			
			// Combo Excepcional
			if (
			    comboSelector == "#modalGastoTipoGasto" &&
			    data.combo &&
			    Array.isArray(data.combo) &&
			    data.combo.length == 1
			) {
					$(comboSelector).append('<option value="' + escapeHtml(data.combo[0].id) + '"' + 
                        (data.combo[0].id == selectedOption ? ' selected' : '') + '>' +
                        escapeHtml(data.combo[0].descripcion.trim()) + '</option>');
			    $(comboSelector).attr("disabled", true);
			} else {
			    if (showEmpty !== false) {
			        $(comboSelector).append(globalOpcionVacia);
			    }
			
			    selectedOption = selectedOption === undefined ? data.selected : selectedOption;
			
			    if (data.combo && Array.isArray(data.combo)) {
			        $(data.combo).each(function(i, elem) {
			            console.log("a" + elem.id);
			            console.log("b" + elem.descripcion);
			            $(comboSelector).append('<option value="' + escapeHtml(elem.id) + '"' +
                            (elem.id == selectedOption ? ' selected' : '') + '>' +
                            escapeHtml(elem.descripcion.trim()) + '</option>');
			        });
			    } else {
			        console.error("data.combo no está definido o no es un array.");
			    }
			}

			
			if ($('#delegado') && $('#delegado').length) { 
    			$('#delegado').chosen(); 
			}
			if ($('#filtroFecha') && $('#filtroFecha').length) { 
				$('#filtroFecha').chosen();
			}
			if ($('#filtroMoneda') && $('#filtroMoneda').length) { 
				$('#filtroMoneda').chosen();
			}
			if ($('#selectGastosCP') && $('#selectGastosCP').length) { 
				$('.selectGastosCP').chosen();
			}
					
		setTimeout(() => {
			if ($('#selectMotivosCP') && $('#selectMotivosCP').length) { 
  				$('.selectMotivosCP').chosen();
			}
		}, 1000);
			
		setTimeout(() => {
			if ($('#filtroMotivo') && $('#filtroMotivo').length) { 
  				$('#filtroMotivo').chosen();
			}
			if ($('#filtroSupervisado') && $('#filtroSupervisado').length) { 
  				$('#filtroSupervisado').chosen();
			}
		}, 1800);

		},
		error: function(request, status, error) {
			console.log("error: " + status + " - " + error);
		}
	});
}


function setOnlyDataCombo(url, params, callback) {
  $.ajax({
    url: url,
    type: "GET",
    data: params,
    success: function (response) {
      let data = transformResponse(response);

      if (data.status === 'ERROR') {
        showError(data);
      } else {
        callback(data.combo);
      }
    },
    error: function (request, status, error) {
      console.log("error: " + status + " - " + error);
    }
  });
}







function callAjax(url, params, successCallBack, errorCallBack, async, showLoading = true, successCallBackParams) {
	if (showLoading)
		$('#modalLoading').modal('show');

	$.ajax({
		url: url,
		type: 'POST',
		data: params,
		async: async == null ? true : async,
		processData: !(params instanceof FormData),
		contentType: params instanceof FormData ? false : 'application/x-www-form-urlencoded; charset=UTF-8',
		success: function(response) {
			let data = transformResponse(response)

			if (data.status == 'ERROR')
				if (errorCallBack != null)
					return eval(errorCallBack + '(data);');
				else
					return showError(data);

			if (successCallBack != null) {
				data = $.extend({}, data, successCallBackParams);
				
				eval(successCallBack + '(data);');
			}
		},
		error: function(request, status, error) {
    			const sanitizedError = escapeHtml(error || 'Se ha producido un error desconocido');
			if (errorCallBack != null)
				eval(errorCallBack + '(request);');
			else
				showError(escapeHtml(request))
		}
	});
}

function transformResponse(res) {
    console.log("MAIN TRANSFORM RESPONSE:");
    console.log(res);
	console.log(JSON.stringify(res));
    
    if (typeof res === "string") {
        try {
            return JSON.parse(res);
        } catch (error) {
            console.warn("Error al parsear JSON:", error);
            
            let startIndex = res.indexOf('{');
            let endIndex = res.lastIndexOf('}');
            
            if (startIndex !== -1 && endIndex !== -1) {
                let jsonString = res.substring(startIndex, endIndex + 1);
                try {
                    return JSON.parse(jsonString);
                } catch (nestedError) {
                    console.warn("Error al parsear JSON extraído:", nestedError);
                    return res;
                }
            }
        }
    }
    
    return res;
}


function loadTable(containerSelector, link, params, firstLoad = true) {
	$('#modalLoading').modal('show');
	$(containerSelector).load(link, params, function(response) {
		setTable(containerSelector, dtLink, dtParams, firstLoad, response);
	});
}

function setTable(containerSelector, link, params, firstLoad, responseText) {
	try {
		var resp = JSON.parse(responseText);
		if (resp.status == 'ERROR')
			return showError(resp);
	} catch (e) {
		$(containerSelector).html(responseText);

		setTooltips();

		$(containerSelector + ' th.sortable, ' + containerSelector + ' .dt-page-links a').each(function() {
			$(this).click(function() {
				loadTable(containerSelector, $(this).attr('href'), params, false);
				return false;
			});
		});

		if (firstLoad) {
			tableLoadFinished(link, containerSelector);

			if (typeof tableFirstLoad == 'function')
				tableFirstLoad();
		}

		if (typeof tableAfterLoad == 'function')
			tableAfterLoad();
	}
}

function tableLoadFinished(link, containerSelector) {
	callAjax(link, 'action=getMessage', 'tableLoadFinishedSuccess', null, false, false, { tableMessageSelector: containerSelector + ' .table-message' });
}

function tableLoadFinishedSuccess(data) {
	if (dtParams.showMessage != false)
		showMessage(data.tableMessageSelector.substring(1), data.message);

	if (typeof tableLoadAfterFinished == 'function')
		tableLoadAfterFinished();
}

function showMessage(id, message, type) {
	$('#' + id).html(message);
	$('#' + id).removeClass('text-success text-warning text-danger');
	$('#' + id + 'Container').toggleClass('d-none', !message);

	if (!message)
		return;

	if (type)
		$('#' + id).addClass(type == 'error' ? 'text-danger' : type == 'warning' ? 'text-warning' : 'text-success');
	else
		$('#' + id).addClass(message.indexOf('ERROR: ') > -1 ? 'text-danger' : message.indexOf('OK: ') > -1 ? 'text-success' : 'text-warning');
	if (message.indexOf('OK: ') > -1) {
		let nuevoMensaje = message.replace('OK: ', '')
		console.log(nuevoMensaje)
		return nuevoMensaje;
	}
}

function showMessage2(selector, message, type) {
	$(selector).html(message);
	$(selector).removeClass('text-success text-warning text-danger');
	$(selector).toggleClass('d-none', !message);

	if (!message)
		return;

	if (type)
		$(selector).addClass(type == 'error' ? 'text-danger' : type == 'warning' ? 'text-warning' : 'text-success');
	else
		$(selector).addClass(message.indexOf('ERROR: ') > -1 ? 'text-danger' : message.indexOf('OK: ') > -1 ? 'text-success' : 'text-warning');
}

function showConfirm(confirmCallback, message) {
	$('#modalConfirmMsg').html(message);
	$('#modalConfirmConfirmar').off('click');
	$('#modalConfirmConfirmar').click(eval(confirmCallback));
	$('#modalConfirm').modal('show');
}

function showError(data) {
    const sanitizedData = escapeHtml(data && data.error ? data.error : 'Se ha producido un error');
	console.log(sanitizedData);
	$('#modalErrorMsg').html(sanitizedData && sanitizedData.error ? sanitizedData.error : 'Se ha producido un error.');
	$('#modalError').modal('show');
}

function validateForm(formId, scrollToError, customFormValidationFunc) {
	clearFormErrors('#' + formId);

	$('input[id^=' + formId + ']:visible,textarea[id^=' + formId + ']:visible,select[id^=' + formId + ']:visible').each(function(i, elem) {
		validateElem($(elem));
	});

	if (customFormValidationFunc != null)
		eval(customFormValidationFunc + '();');

	return validateFormHasError(formId, scrollToError);
}

function validateElem(elem) {
	if (elem.is('[required]') && !$(elem).val())
		showFormError(elem, globalMsgRequired);

	if (elem.is('[data-length]') && $(elem).inputmask('unmaskedvalue').length < $(elem).data('length'))
		showFormError(elem, globalMsgLength($(elem).data('length')));

	if (elem.is('[data-maxvalue]')) {
		var value;
		try {
			value = AutoNumeric.getAutoNumericElement('#' + $(elem).attr('id')).getNumericString();
		} catch (e) {
			value = $(elem).inputmask('unmaskedvalue');
		}

		if (parseFloat(value) > parseFloat($(elem).data('maxvalue')))
			showFormError(elem, globalMsgMaxValue(formatCurrency($(elem).data('maxvalue'))));
	}

	if (elem.is('[data-mindate]') && compareDates($(elem).val(), $(elem).data('mindate')) < 0)
		showFormError(elem, globalMsgMinDate($(elem).data('mindate')));

	if (elem.is('[data-maxdate]') && compareDates($(elem).val(), $(elem).data('maxdate')) > 0)
		showFormError(elem, globalMsgMaxDate($(elem).data('maxdate')));

	if (elem.is('[data-cuit]') && !validateCuit($(elem).inputmask('unmaskedvalue')))
		showFormError(elem, globalMsgCuit);
}

function validateFormHasError(formId, scrollToError) {
	var firstError = $('#' + formId + ' .invalid-feedback:visible:first');
	if (scrollToError && firstError.length > 0)
		scrollToElem(firstError, true, '#' + formId, '.scroll-err');

	return firstError.length == 0;
}

function validateCuit(cuit) {
	if (cuit.length != 11)
		return false;

	var acumulado = 0;
	var digitos = cuit.split('');
	var digito = digitos.pop();

	for (var i = 0; i < digitos.length; i++) {
		acumulado += digitos[9 - i] * (2 + (i % 6));
	}

	var verif = 11 - (acumulado % 11);

	if (verif == 11)
		verif = 0;

	return digito == verif;
}

function clearFormErrors(selector) {
	$(selector).find('.text-danger').removeClass('text-danger');
	$(selector).find('.invalid-feedback').html('').hide();

	$(selector + 'MsgValidacion').html('');
	$(selector + 'MsgValidacion').addClass('text-danger');
	$(selector + 'MsgValidacion').toggleClass('d-none', true);
}

function clearFormError(elem) {
	$(elem).find('.text-danger').removeClass('text-danger');
	$(elem).find('.invalid-feedback').html('').hide();
}

function showFormError(elem, msg, append) {
	if (append) {
		msg = $(elem).parent().find('.invalid-feedback').html() + '<br>' + msg;
		msg = msg.startsWith('<br>') ? msg.substring(4) : msg;
	}

	if ($(elem).parent().find('.invalid-feedback').html() == '' || append) {
		$(elem).addClass('text-danger');
		$(elem).parent().find('label').addClass('text-danger');
		$(elem).parent().find('input').addClass('text-danger');
		$(elem).parent().find('.invalid-feedback').html(msg).show();
	}
}

function setLocalStorageItem(key, value) {
	if (value != null)
		localStorage.setItem(key, value);
}

function getLocalStorageItem(key) {
	var value = localStorage.getItem(key);
	localStorage.removeItem(key);
	return value ? value : '';
}

function formatDate(date, format) {
	return $('<input/>').datepicker({ format: (format == null ? 'dd/mm/yyyy' : format) }).datepicker('update', new Date(date)).val();
}

function formatDateConsumos(date){
	let [year, month, day] = date.split('-');
	let new_date = `${day}/${month}/${year}`;
	return new_date;
}

function formatCurrency(amount) {
	var input = $('<input/>');
	input.inputmask('currency', { 'radixPoint': ',', 'groupSeparator': '.' });
	input.inputmask('setvalue', typeof amount == 'string' ? amount.replace(',', '.') : amount);
	return input.val();
}



let popupWin;
let popupDocument;
let combinedPDFUrl;
let generatedPDFs=[];
let pdfsCombinados = 0;
let cantidadPdfs = 0;


 function setImagenes(data) {
  let divImagenesCargadas = $('#containerImagenesCargadas');
  let i = 0;
	pdfsCombinados = data.archivos.length;
  for (let imagen of data.archivos) {
    divImagenesCargadas.append(`<div class="mr-3">
    <img onclick="obtenerImagen(${escapeHtml(imagen.id)})" id="Img-${i}" style="cursor:pointer" alt="Img ${i}" src="./images/iconos/pdf-48.png" class="mt-4 abrir-imagen" data-id="${escapeHtml(imagen.id)}" data-extension=".pdf"/>
    <p>${escapeHtml(imagen.nomArchivo)}</p>
	</div>`);
  }

  $('#abrirTodas').on('click', function() {
	 data.archivos.map(imagen => obtenerImagenes(imagen.id));

  });
}



function loadAndCombinePDFs(pdfDataArray) {
  return PDFLib.PDFDocument.create().then(function(combinedPDF) {
    var promises = pdfDataArray.map(function(pdfBytes) {
      return PDFLib.PDFDocument.load(pdfBytes).then(function(externalPDF) {
        return combinedPDF.copyPages(externalPDF, externalPDF.getPageIndices()).then(function(copiedPages) {
          copiedPages.forEach(function(page) {
            combinedPDF.addPage(page);
          });
        });
      });
    });
    
    return Promise.all(promises).then(function() {
      return combinedPDF.save();
    });
  }).then(function(combinedPDFBytes) {
    var combinedPDFBlob = new Blob([combinedPDFBytes], { type: 'application/pdf' });
    combinedPDFUrl = URL.createObjectURL(combinedPDFBlob);
    abrirVentanaEmergente();
  }).catch(function(error) {
    console.error("Error al combinar PDFs:", error);
  });
}

function abrirVentanaEmergente() {
  popupWin = window.open("", "_blank");
  popupDocument = popupWin.document;

  popupDocument.open();
  popupDocument.write(`
    <!DOCTYPE html>
    <html>
    <head>
        <title>PDFs Combinados</title>
        <style>
            body, html {
                margin: 0;
                padding: 0;
                width: 100%;
                height: 100%;
                overflow: hidden;
            }
            object {
                width: 100%;
                height: 100%;
            }
        </style>
    </head>
    <body>
        <object type="application/pdf" data="${combinedPDFUrl}"></object>
    </body>
    </html>
  `);
  popupDocument.close();
  
  cantidadPdfs=0;
  generatedPDFs=[];
}



function obtenerImagen(idImg) {
  callAjax('imagenes.do', { action: 'descargarImg', idImagen: idImg }, "descargarImagen");

}

function obtenerImagenes(idImg) {
  callAjax('imagenes.do', { action: 'descargarImg', idImagen: idImg }, "descargarImagenes", true);

}

function descargarImagenes(data) {
	$('#modalLoading').addClass('show');
     generatedPDFs.push(data.archivo[0].base64File);
     cantidadPdfs++;

   if(pdfsCombinados === cantidadPdfs){
	    loadAndCombinePDFs(generatedPDFs)
	}

}

function escapeHtml(str) {
    var element = document.createElement('div');
    if (str) {
        element.innerText = str;
        element.textContent = str;
    }
    return element.innerHTML;
}


function descargarImagen(data){
	// Decodifica el contenido base64 del PDF
	const decodedPDF = atob(data.archivo[0].base64File);
	let tipoArchivo = 'image/tiff'
	let tipoExtension = '.tiff'
	
	if(data.archivo[0].tipo.includes("PDF")){
		tipoArchivo =  'application/pdf'
		tipoExtension = '.pdf' 
	} 
	// Convierte el contenido decodificado en un Uint8Array (arreglo de bytes)
	const uint8Array = new Uint8Array(decodedPDF.length);
	for (let i = 0; i < decodedPDF.length; i++) {
  		uint8Array[i] = decodedPDF.charCodeAt(i);
	}
	
	// Crea un Blob a partir del Uint8Array con el tipo 'application/pdf'
	
	const pdfBlob = new Blob([uint8Array], { type: tipoArchivo });
	// Crea una URL para el Blob
	
	const pdfURL = URL.createObjectURL(pdfBlob);
	// Crea un enlace de descarga
	const linkDescarga = document.createElement('a');
	linkDescarga.href = pdfURL;
	linkDescarga.download = "Archivo" + tipoExtension;


  	// Abre una nueva ventana o pestaña con la imagen
  	const nuevaVentana = window.open(linkDescarga, '_blank');

  	// Limpia la URL creada cuando la ventana se cierre
 	 nuevaVentana.addEventListener('DOMContentLoaded', () => {
   	 URL.revokeObjectURL(imageURL);
  	});


}

