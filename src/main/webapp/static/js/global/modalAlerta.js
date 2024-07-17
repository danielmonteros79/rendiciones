
let alerta = ""
let mensaje = []

function modalAlertaShow(id, adea) {
	mensaje.length = 0;
	obtenerAlerta(adea)	
	obtenerMensaje(adea)
	modalAlertaLoad(alerta, mensaje, id);
}



function obtenerMensaje(adea) {
  const adeaNueva = adea.substr(1, 9).split('');
  let cont = 0;
  let detalle = { tipo: '', frase: '', tiempo: '' };

  while (cont < 8 && adeaNueva[cont] !== '0') {
    obtenerMensajeAlerta(adeaNueva[cont], adeaNueva[cont + 1], detalle);
    obtenerMensajeAlertaEspecial(adeaNueva[cont], adeaNueva[cont + 1], detalle);

    if (adeaNueva[cont + 1] === '5' && adeaNueva[cont] === '3') {
      mensaje.push('No se informan datos.');
    } else if (adeaNueva[cont + 1] === '6' && adeaNueva[cont] === '3') {
      mensaje.push('Supera la dieta diaria, definida por pol&iacutetica.');
    } else if (adeaNueva[cont + 1] === '7' && adeaNueva[cont] === '1') {
      mensaje.push('Este motivo requiere validaci&oacuten de la glg.');
    } else if(adeaNueva[cont + 1] === '7' && adeaNueva[cont] === '3') {
		mensaje.push('Supera el monto asignado al equipo de trabajo.');
	}
    else {
      mensaje.push(`Supera ${detalle.tipo}${detalle.tiempo}${detalle.frase}`);
    }
    cont += 2;
  }
}

function obtenerMensajeAlertaEspecial(adeaA, adeaS, detalle) {
  if (adeaA === '3') {
    if (adeaS === '1') detalle.tiempo = 'por usuario,';
    else if (adeaS === '2') detalle.tiempo = 'por invitado,';
    else if (adeaS === '3') detalle.tiempo = 'por consumo de combustible,';
    else if (adeaS === '4') detalle.tiempo = 'por refrigerio, ';
  }

  return detalle;
}

function obtenerMensajeAlerta(adeaA, adeaS, detalle) {
  if (adeaA === '1' || adeaA === '3') {
    detalle.tipo = 'el monto ';
    detalle.frase = ' definido por pol&iacutetica';
  } else if (adeaA === '2') {
    detalle.tipo = 'la cantidad ';
    detalle.frase = ' definida por pol&iacutetica.';
  }

  if (adeaS === '1') detalle.tiempo = 'anual,';
  else if (adeaS === '2') detalle.tiempo = 'trimestral,';
  else if (adeaS === '3') detalle.tiempo = 'mensual,';
  else if (adeaS === '4') detalle.tiempo = 'semanal,';
  else if (adeaS === '5' && adeaA === '2') detalle.tiempo = 'diaria,';
  else if (adeaS === '5') detalle.tiempo = 'diario,';
  else if (adeaS === '6') detalle.tiempo = 'de la rendici&oacuten,';

  return detalle;
}



function obtenerAlerta(adea){
	let tipoAlerta = adea.substr(0,1)
	switch(tipoAlerta){
			case "1":
				alerta = "Riesgo";
				break;
			case "2":
				alerta = "Riesgo Grave"
				break;
			case "3":
				alerta = "Incidencia grave"
				break;
			case "4":
				alerta = "Incidente"
				break;
			case "5":
				alerta ="Anomal&iacutea"
				break;
			}		
}

function modalAlertaLoad(alerta, mensaje, id) {
		modalAlertaLoadSuccess(alerta, mensaje, id);
}

function modalAlertaLoadSuccess(alerta, mensaje, id) {
	modalAlertaSetVisibility(alerta, mensaje, id);
	$('#modalAlerta').modal('show');
}


function modalAlertaSetVisibility(alerta, mensaje, id) {
	let agregado = document.getElementById('itemsAlerta')
	  agregado.innerHTML = "";
	
	if(alerta.length != 0){
		$('#modalAlertaTitulo').html( alerta + " - ID: " + id );
	}
	else{
		$('#alertaRiskTitle').css('display','none');
		$('#itemsAlerta').css('list-style','none');
	}
	
	if(mensaje.length == 0) $('#itemsAlerta').html('<p >'+ '</p>')
	mensaje.forEach(m => {
		let nuevoLi = document.createElement("li");
		if(m.includes('por pol') || m.includes('con la pol')){
			nuevoLi.innerHTML = '<p class="text-dark ">' + m + '  <h6 class="text-dark"> - Ver  <a target="_blank" href="https://drive.google.com/file/d/1jbb0NfnVkOYn8z3IQpsqDVFnw2K1gn18/view">  Politica  </a> y  <a target="_blank"  href="https://drive.google.com/file/d/1qWLOC96Pwzk6BGv9qVVurnDmiz8EphNu/view">  Montos</a>. Por consultas contactarse con  gastoseinversionestyc-arg@bbva.com</h6> </p> '
		}else{
			nuevoLi.innerHTML = '<p class="text-dark">'+ m + '</p>'
		}
		agregado.append(nuevoLi)
	})
	
	
	
	
}

