$(document).ready(function() {
	$("#ui-id-1").text('AVISO');
	
	$('#imgAdjuntadas').balloon().showBalloon({
		position: "left",
		css: cssDefaultBalloon
	});

	$('#adjuntarImg').balloon().showBalloon({
		position: "right",
		css: cssDefaultBalloon
	});

	$('#addEfectivo').balloon().showBalloon({
		position: "right",
		css: cssDefaultBalloon
	});

	$('#addCupon').balloon().showBalloon({
		position: "top",
		css: cssDefaultBalloon
	});
});

function nofunciona() {
	alert("Temporalmente no disponible");
}

$("#show").click(function(e) {
	$("#RendicionForm").show();

	e.preventDefault();
});
function AddForm(a) {
	if (a == 1)
		document.getElementById("AddForm").style.display = "none";
	else
		document.getElementById("AddForm").style.display = "block";
}

var popUpObj;
var rendicion = "";
var codMotivo = "";
var cCosto = "";
var fechaD = "";
var fechaH = "";
var estadoRendicion = "";

function showNuevoGastoPopup() {

	rendicion = document.getElementById("idRendicion").value;
	cCosto = document.getElementById("Costos").value;
	fechaD = document.getElementById("fechaDesdeDisable").value;
	fechaH = document.getElementById("fechaHastaDisable").value;
	estadoRendicion = document.getElementById("estadoRendicion").value;
	codMotivo = document.getElementById("codMotivo").value;
	costosDestino = document.getElementById("costosDestino").value;
	
	popUpObj = window.open("NuevoGastoPopUp.do?codigo=" + rendicion + "&"
			+ "cCosto=" + cCosto + "&" + "fechaD=" + fechaD + "&" + "fechaH="
			+ fechaH + "&" + "opcion=" + "ALTA" + "&estadoRendicion="
			+ estadoRendicion + "&codMotivo=" + codMotivo + "&costosDestino=" + costosDestino, "ModalPopUp",
			"toolbar=no," + "scrollbars=no," + "location=no," + "statusbar=no,"
					+ "menubar=no," + "resizable=0," + "width=650,"
					+ "height=425," + "left = 300," + "right = 300,"
					+ "top=200");
	popUpObj.focus();
	LoadModalDiv();

}
function showNuevoGastoPopupCupon() {
	$("#confirmBox").show();

	rendicion = document.getElementById("idRendicion").value;
	cCosto = document.getElementById("Costos").value;
	fechaD = document.getElementById("fechaDesdeDisable").value;
	fechaH = document.getElementById("fechaHastaDisable").value;
	estadoRendicion = document.getElementById("estadoRendicion").value;
	codMotivo = document.getElementById("codMotivo").value;
	costosDestino = document.getElementById("costosDestino").value;

	popUpObj = window.open("cuponesPopup.do?codigo=" + rendicion + "&feD="
			+ fechaD + "&feH=" + fechaH + "&view=" + "f" + "&tipoClick=" + "2"
			+ "&cCosto=" + cCosto + "&estadoRendicion=" + estadoRendicion +  "&costosDestino=" + costosDestino
			+ "&codMotivo=" + codMotivo, "ModalPopUp", "toolbar=no,"
			+ "location=no," + "statusbar=no,"
			+ "menubar=no," + "resizable=0," + "width=1150," + "height=475,"
			+ "left = 150," + "right = 500," + "top=150," + "bottom = 500");
	popUpObj.focus();
	LoadModalDiv();

}

function showEditarGastoPopup(idGasto, estadoRend, tieneCupon, listadoAprob,
		user,glg) {

	rendicion = document.getElementById("idRendicion").value;
	cCosto = document.getElementById("Costos").value;
	fechaD = document.getElementById("fechaDesdeDisable").value;
	fechaH = document.getElementById("fechaHastaDisable").value;
	codMotivo = document.getElementById("codMotivo").value;

	popUpObj = window.open("editarGasto.do?codigo=" + rendicion + "&"
			+ "cCosto=" + cCosto + "&" + "fechaD=" + fechaD + "&" + "fechaH="
			+ fechaH + "&" + "codMotivo=" + codMotivo + "&" + "idGasto="
			+ idGasto + "&" + "opcion=" + "MODI" + "&feD=" + fechaD + "&feH="
			+ fechaH + "&" + "estadoRendicion=" + estadoRend + "&"
			+ "tieneCupon=" + tieneCupon + "&listadoAprob=" + listadoAprob
			+ "&user=" + user+"&glg="+glg, "ModalPopUp", "toolbar=no," + "scrollbars=no,"
			+ "location=no," + "statusbar=no," + "menubar=no," + "resizable=0,"
			+ "width=650," + "height=425," + "left = 300," + "right = 300,"
			+ "top=300");
	popUpObj.focus();
	LoadModalDiv();
}
function showDescripcionObligatoriaPopup(gasto, codGasto, codObs, tipoEntrada,codMotivo,estadoRend) {
	rendicion = document.getElementById("idRendicion").value;
	popUpObj = window.open("descripcionObligatoriaPopup.do?rnd=" + rendicion
			+ "&rndg=" + gasto + "&codGasto=" + codGasto + "&codObserv="
			+ codObs + "&tipoEntrada=" + tipoEntrada+"&codMotivo="+codMotivo+"&estadoRend="+estadoRend, "ModalPopUp",
			"toolbar=no," + "scrollbars=no," + "location=no," + "statusbar=no,"
					+ "menubar=no," + "resizable=0," + "width=720,"
					+ "height=550," + "left = 500," + "right = 500,"
					+ "top=150," + "bottom = 500");
	popUpObj.focus();
	LoadModalDiv();
}

function showCuponesTarjetasPopup(gasto, cupon, fechaGasto, tipoView,codMotivo) {
	fechaD = document.getElementById("fechaDesdeDisable").value;
	fechaH = document.getElementById("fechaHastaDisable").value;
	rendicion = document.getElementById("idRendicion").value;
	popUpObj = window.open("cuponesPopup.do?codigo=" + rendicion + "&idGasto="
			+ gasto + "&cg=" + cupon + "&feD=" + fechaD + "&feH=" + fechaH
			+ "&view=" + tipoView + "&tipoClick=" + "1"+"&codMotivo="+codMotivo, "ModalPopUp",
			"toolbar=no," + "location=no," + "statusbar=no,"
					+ "menubar=no," + "resizable=0," + "width=700,"
					+ "height=475," + "left = 500," + "right = 500,"
					+ "top=150," + "bottom = 500");
	popUpObj.focus();
	LoadModalDiv();
}

function LoadModalDiv() {
	var bcgDiv = document.getElementById("divBackground");
	bcgDiv.style.display = "block";
}

function HideModalDiv() {
	var bcgDiv = document.getElementById("divBackground");
	bcgDiv.style.display = "none";
}
function OnUnload() {
	alert($('#idGasto').val());
	if (false == popUpObj.closed) {
		popUpObj.close();
	}
}
window.onunload = OnUnload;