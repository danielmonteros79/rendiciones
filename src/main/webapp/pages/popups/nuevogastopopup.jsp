<html>
<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>


<head>

<link rel="stylesheet" type="text/css" href="./css/validation.css">
<link rel="stylesheet" type="text/css" href="./css/main.css">
<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css">
<link rel='stylesheet' type='text/css' href='./css/displaytag.css' />
<link rel="stylesheet" type='text/css' href="./css/jquery-ui.structure.css" />
<link rel="stylesheet" type='text/css' href="./css/jquery-ui.theme.css" />
<link rel="stylesheet" type="text/css" href="./css/validation.css">
<link rel="stylesheet" type="text/css" href="./css/buttons.css">
<link rel="stylesheet" type="text/css" href="./css/rendicionDetalle.css">
<link rel="stylesheet" type="text/css" href="./css/detalleGastos.css">
<link rel="stylesheet" type="text/css" href="./css/nuevoGastoPopUp.css">
<script type="text/javascript" src="./static/js/jquery.js"></script>
<script type="text/javascript" src="./static/js/jquery-ui.js"></script>
<script type="text/javascript" src="./static/js/jquery.ui.datepicker-es.js"></script>
<script type="text/javascript" src="./static/js/datepicker-settings.js"></script>
<script type="text/javascript" src="./static/js/date-calculator.js"></script>
<script type="text/javascript" src="./static/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="./static/js/listadoRendiciones.js"></script>
<script type="text/javascript" src="./static/js/validacionDetalleGastos.js"></script>
<script type="text/javascript" src="./static/js/nuevoGastoPopup.js"></script>
<script type="text/javascript">

$(document).ready(function(){
    // Función a lanzar cada vez que se presiona una tecla en un textarea
    // en el que se encuentra el atributo maxlength
    $("#obsGasto").keyup(function() {
        var limit   = 120; // Límite del textarea
        var value   = $(this).val();             // Valor actual del textarea
        var current = value.length;              // Número de caracteres actual
        if (limit < current) {                   // Más del límite de caracteres?
            // Establece el valor del textarea al límite
            $(this).val(value.substring(0, limit));
		}
    });
    check();
});
</script>
<style>
label {
	margin-left: 0px;
	padding-left: 0px;
	display: none;
}

#switchDiv {
	width: 100%;
	float: right;
	text-align: right;
	margin-right: 15%;
	font-size: small;
	font-weight: bold;
	margin-bottom: 1%;
}

body {
	background-color: #c2d5f1;
}
</style>
<logic:notEqual value="1" name="opcionTitulo">
<title>Alta Gastos</title>
</logic:notEqual>
<logic:equal value="1" name="opcionTitulo">
<title>Modificacion de gastos</title>
</logic:equal>
</head>
<body>

<div>
<logic:notEqual value="1" name="opcionTitulo">
<h2 style="text-align: center;">Alta Gastos</h2>
</logic:notEqual>
<logic:equal value="1" name="opcionTitulo">
<h2 style="text-align: center;">Modificacion de gastos</h2>
</logic:equal>

</div>
<logic:present name="message">
	<% String message = (String) request.getAttribute("message");
	
	if(message.contains("ERROR")) { %>
		<div id="messageErr" class="message">
			<%= message.substring(7) %>
		</div>
	<%} else if(message.contains("OK")) { %>
		<div id="messageOk" class="message">
			<%= message.substring(4) %>
		</div>
	<%} else {%>
		<div id="messageAviso" class="message">
			AVISO: <%= message %>
		</div>
	<%}%>
</logic:present>
<logic:present name="messageModifTCJP">
	<% String message = (String) request.getAttribute("messageModifTCJP"); 
	if(message.contains("ERROR")){
	%>
	<div id="messageErr" style="color: red; font-weight: bold; text-align: center; width: 100%; font-size: 14">
		<%= message %>
	</div>
	<%} else { %>
		<script>window.close(); window.opener.location.reload();</script>
	<%} %>
	<input type="button" value="Salir" onClick=window.close();window.opener.location.reload();;;
		class="buttonCancel" style="float: right; margin-right: 1%;" />
</logic:present>
<logic:notPresent name="messageModifTCJP">
<div id="NuevoGastoPopUp"><html:form action="NuevoGastoPopUpSave"
	styleId="RendicionDetalleForm">
	<html:hidden property="idRendicion" styleId="idRendicion"></html:hidden>
	<html:hidden property="fechaD" styleId="fechaD" />
	<html:hidden property="fechaH" styleId="fechaH" />
	<html:hidden property="opcion" styleId="opcion" />
	<html:hidden property="idG" styleId="idG" />
	<html:hidden property="importeCupon" styleId="importeMax"></html:hidden>
	<html:hidden property="estadoRendicion" styleId="estadoRendicion" />
	<html:hidden property="codMotivo" styleId="codMotivo" />
	<html:hidden property="glg" styleId="glg" />
	<html:hidden property="usuarioRend" styleId="usuarioRend" />
	<html:hidden property="montoMaximo" styleId="montoMaximo"/>
	<html:hidden property="esAdelanto" styleId="esAdelanto"/>
	
	<table>
		<tr>
			<td colspan="2">
				ID-Rendición:
				<html:text styleClass="green" property="idRendicion" styleId="idRendicion" readonly="true" />
			</td>
        	<td colspan="2">
        		C.Costos:
        		<html:text styleClass="green" property="costos" styleId="costos" readonly="true" style="width:100px;" />
        	</td>
		</tr>
		<tr>
			<td colspan="2">
			<html:hidden property="user"/>
				Usuario:
				<html:text styleClass="green" property="nombreUsuario" styleId="nombreUsuario" readonly="true"  style="width:250px;"/>
			</td>
        	<td id="TDCostos" colspan="2">
	       		C.Costo Destino:
				<html:text property="costosDestino" onkeypress='return numericOnly(event);' styleId="costosDestino" maxlength="4" style="width:17%;"/> 	
	       	</td>
		</tr>
		<tr>
			<td colspan="2">
			</td>
			<td colspan="2">
				<div class="error" style="display: none; font-size: small; color: red; font-weight: bold;" id="valorInvalido">El valor ingresado no puede ser '0000' ni '9999'</div>
			</td>
		</tr>
		<tr>
			<td>
				Tipo Gasto: 
			</td>
			<td colspan="3">
				<html:select styleId="idGasto" property="gastos" style="min-width:150px;margin-left:0.5px;" styleId="comboTipoGasto" onchange="checkBimon();selectTipoGasto();">
					<html:options collection="ComboGastos" property="id"
						labelProperty="descripcion" />
				</html:select>
			</td>
		</tr>
		<tr>
			<td>
				Moneda: 
			</td>
			<td style="width:190px;">
				 <html:select property="moneda" style="width:150px;margin-left:0.5px;" styleId="comboMoneda">
					<html:options collection="ComboMoneda" property="id" labelProperty="descripcion" />
				 </html:select>
			</td>
			<td>
				F.Gasto:
			</td>
			<td>
				<html:text property="fechagastos" style="width:143px;" styleId="fechagastos" styleClass="fechaDDMMYY" onchange="validarFecha(this);" />
				<a id="calendarFechagastos" style="float:left;position:absolute" href='#' onClick="showCalendar('fechagastos');">
					<img id="imageCal5" src='./images/Calendar.png' border='0'>
				</a>
			</td>
		</tr>
		<tr>
			<td>
				Tipo Comprobante: 
			</td>
			<td>
				<html:select property="comprobante" style="width:150px;" styleId="comboComprobante" onchange="check();">
					<html:options collection="ComboComprobante" property="id" labelProperty="descripcion" />
				</html:select>
			</td>
			<td>
				Monto:
			</td>
			<td>
				<html:text property="monto" style="width:143px;" styleId="monto" onkeypress="return keyPressMonto(event);" maxlength="14" />
			</td>
		</tr>
		<tr>
			<td colspan="2"></td>
			<td colspan="2">
				<div id="errorMonto" style="color:red;"></div>
			</td>
		</tr>
		<tr id="detalleComprobanteCuit" style="display:none;">
			<td>
				Comprobante
			</td>
			<td>
				<html:select property="cmbComprobante" onchange="clearComprobante();" styleId="cmbComprobante">
					<html:option value="A">A</html:option>
					<html:option value="B">B</html:option>
					<html:option value="C">C</html:option>
				</html:select>
				<html:text property="comprobante1" onkeypress="return numericOnly(event);" style="width:38px;" styleId="comprobante1" maxlength="4"/>
				<html:text property="comprobante2" onkeypress="return numericOnly(event);" style="width:65px;" styleId="comprobante2" maxlength="8"/>
			</td>
			<td>
				CUIT:
			</td>
			<td>
				<html:text property="cuit1" onkeypress="return numericOnly(event);" style="width:22px;" styleId="cuit1" maxlength="2" />
				<html:text property="cuit2" onkeypress="return numericOnly(event);" style="width:65px;" size="7" styleId="cuit2" maxlength="8" />
				<html:text property="cuit3" onkeypress="return numericOnly(event);" style="width:16px;" styleId="cuit3" maxlength="1" />
			</td>
		</tr>
		<tr>
			<td>
				Observaciones:<br>
				(120 caracteres)
			</td>
			<td colspan="3">
				<html:textarea property="observacionGasto" styleId="obsGasto" cols="50" rows="8" title="Obs" style="height:70px"/>
			</td>
		</tr>
	</table>
	<div id="switchDiv" style=""><input
		style="float: right; text-align: right;" id="cuponesCheck"
		type="checkBox" readonly="readonly" disabled>Cupon</input></div>
	<div class="error"
		style="display: none; font-size: small; color: red; font-weight: bold;"
		id="fechaInvalida">La fecha indicada debe estar entre  
	DESDE y FECHA HASTA de la rendición</div>
	<div class="error" style="display: none; font-size: small; color: red; font-weight: bold;" id="montoInvalido">El monto ingresado debe ser mayor a 0</div>
	<div class="error" style="display: none; font-size: small; color: red; font-weight: bold;" id="noNumeric">El monto ingresado no es de caracter numerico</div>
	<div class="error" style="display: none; font-size: small; color: red; font-weight: bold;" id="montoIncorrecto">El monto ingresado es mayor al del gasto</div>
	<div class="error" style="display: none; font-size: small; color: red; font-weight: bold;" id="cuitIncorrecto">El formato del CUIT no es correcto.</div>
	
	<div id="BotonSavePopUp" style="width: 100%;">
		<input type="button" value="Guardar" onClick="submitForm();" id="saveButton" style="margin-lef:40px" class="buttonSave" />
		<input type="button" value="Salir" onClick="window.close();window.opener.location.reload();" class="buttonCancel"/>
	</div>
</html:form></div>
    
 <script>
 	function submitForm() {
 		validarCuit();
 		$('#montoInvalido').hide();
		$('#montoIncorrecto').hide();
 		if(!$('#cuitIncorrecto').is(":visible")){
			if (esDineroAlert()){
	 			$('#comboMoneda').attr('disabled', false);
	 			$("#RendicionDetalleForm").submit();
	 		}
 		}
 		
 	};
 	
 	function keyPressMonto(e){
		var code = e.charCode || e.keyCode;
		var monto = $("#monto").val();
		var indexNewChar = $("#monto")[0].selectionStart;
		var indexDecimal = $("#monto").val().indexOf(',');

 		console.log(e.charCode);
		if (indexDecimal == -1 && (code == 44 || code == 46)) {
			if (code == 46) {
				$("#monto").val((monto.substring(0, indexNewChar) + "," + monto.substring(indexNewChar)));
				return false;
			}
			
			return;
		}
	    else if ($.inArray(code, [8, 9, 13]) !== -1 || (code >= 35 && code <= 40))
	        return;
	    if (!(code >= 48 && code <= 57))
	        return false;
	    
	    // Hasta 2 decimales
	    if (indexDecimal != -1 && indexNewChar > indexDecimal && monto.substring(indexDecimal).length > 2)
	        return false;
	    
	    // Hasta 11 enteros
	    if (monto.substring(0, indexDecimal == -1 ? monto.length : indexDecimal).length > 10 &&
	       (indexDecimal == -1 || indexNewChar < indexDecimal))
	        return false;
	}
 </script>
 <script>
 function validarCuit() {
	 if($('#cuit1').is(":visible")){		 
		 if($('#cuit1').val().length == 2 && $('#cuit2').val().length == 8 && $('#cuit3').val().length == 1) {
			var cuit = $('#cuit1').val() + $('#cuit2').val() + $('#cuit3').val();
	
			var acumulado 	= 0;
			var digitos 	= cuit.split("");
			var digito	= digitos.pop();
	
			for(var i = 0; i < digitos.length; i++) {
				acumulado += digitos[9 - i] * (2 + (i % 6));
			}
	
			var verif = 11 - (acumulado % 11);
			if(verif == 11) {
				verif = 0;
			}
			
			if (digito == verif){
				$('#cuitIncorrecto').hide();
				return true;	
			}else{
				$('#cuitIncorrecto').show();
				return false;
			}
			
		}else{
			$('#cuitIncorrecto').show();
			 return false;
	 	}
	 }else{		 
		 return true;}
}
 </script>
 <script>
	function numericOnly(e){
 		var code = e.charCode || e.keyCode;
	    if (e.keyCode == 46 && e.charCode == 0)
	    	return;
	    else if ($.inArray(code, [8, 9, 13]) !== -1 || (code >= 35 && code <= 40))
	    	return;
	    if (!(code >= 48 && code <= 57))
	        return false;
	}
 </script>
<logic:equal value="1" name="conCupon">
	<script>
	$(document).ready(function(){
		$("#comboMoneda").attr("disabled",true);
		$("#fechagastosDiv").hide();
		$("#fechagastos2Div").show();
	});
	function validarFecha(event) {
		var fDesdeRend = new Date();
		var fecha = $('#fechaD').val().split("/");
		fDesdeRend.setFullYear(fecha[2], fecha[1] - 1, fecha[0]);

		var fHastaRend = new Date();
		fecha = $('#fechaH').val().split("/");
		fHastaRend.setFullYear(fecha[2], fecha[1] - 1, fecha[0]);

		var fGasto = new Date();
		fecha = $('#fechagastos').val().split("/");
		fGasto.setFullYear(fecha[2], fecha[1] - 1, fecha[0]);

		if (!(fGasto >= fDesdeRend && fGasto <= fHastaRend)) {
			$('#fechaInvalida').show();
			return false;

		} else {
			$('#fechaInvalida').hide();
			return true;
		}
	}
	

	function esDineroAlert() {
		var elem = $('#monto');
	    // var txt='123.123,50';
	    // var regex = "^\\d{1,3}(.\\d\\d\\d)*(\\,\\d\\d)*$";
	    
	    var regex = "\\d{0,9999999999}(\\,)?\\d{1,99}$";
	    var p = new RegExp(regex,["i"]);
	    var m = p.exec(elem.value);
	    var max = $('#importeMax').val();
	    var monto = $('#monto').val();
	    
	    if ( $("#costosDestino").val() == "9999" || $("#costosDestino").val() == "0000")
			$("#valorInvalido").show();
		else
			$("#valorInvalido").hide();
	    
	    if (monto.match(/[a-z]/i)) {
	    	$('#noNumeric').show();
	    	return flase;
	    }
	    $('#noNumeric').hide();
	    if (monto<=0){
	    	$('#montoInvalido').show();
	    	return false;
	    }
	    $('#montoInvalido').hide();
	    	    
	    if(
			(parseFloat(monto.replace(",","."))>parseFloat(max.replace(",",".")))
				&& !isNaN(parseFloat($('#monto').val()))
				&& !isNaN(parseFloat(max.replace(",",".")))
				){
	        //alert("El valor " + elem.value + " es mayor al valor del cupon ("+max+")");
	        $('#montoIncorrecto').show(); 
	        elem.value = "";

	          focusElement(elem);
	        
	        return false;
	        }
	    $('#montoInvalido').hide();
	    $('#montoIncorrecto').hide();
	    
	    return true;
	}

	</script>
</logic:equal>
<logic:notEqual value="1" name="conCupon">
<logic:equal value="PENDI" name="estadoRend">
	<script>
    
function esDineroAlert() {
	var elem = $('#monto');
   
    var regex = "\\d{0,9999999999}(\\,)?\\d{1,99}$";
    var monto = $('#monto').val();
    
    if ( $("#costosDestino").val() == "9999" || $("#costosDestino").val() == "0000")
		$("#valorInvalido").show();
	else
		$("#valorInvalido").hide();
    
    if (monto.match(/[a-z]/i)) {
    	$('#noNumeric').show();
    	return flase;
    }
    $('#noNumeric').hide();
    if (monto<=0){
    	$('#montoInvalido').show();
    	return false;
    }
    $('#montoInvalido').hide();	
    return true;
}
	
</script>
</logic:equal>
<logic:notEqual value="PENDI" name="estadoRend">
<script>
function esDineroAlert() {
	var elem = $('#monto');
    // var txt='123.123,50';
    // var regex = "^\\d{1,3}(.\\d\\d\\d)*(\\,\\d\\d)*$";
    
    var regex = "\\d{0,9999999999}(\\,)?\\d{1,99}$";
    var p = new RegExp(regex,["i"]);
    var m = p.exec(elem.value);
    var max = $('#montoMaximo').val();
    var monto = $('#monto').val();
    if (monto.match(/[a-z]/i)) {
    	$('#noNumeric').show();
    	return flase;
    }
    $('#noNumeric').hide();
    if (monto<=0){
    	$('#montoInvalido').show();
    	return false;
    }
    $('#montoInvalido').hide();
    
    if ( $("#costosDestino").val() == "9999" || $("#costosDestino").val() == "0000")
		$("#valorInvalido").show();
	else
		$("#valorInvalido").hide();
       
    if(
			(parseFloat(monto.replace(",","."))>parseFloat(max.replace(",",".")))
				&& !isNaN(parseFloat($('#monto').val()))
				&& !isNaN(parseFloat(max.replace(",",".")))
				){
	        //alert("El monto cargado " + elem.value.trim() + " no puede ser mayor al monto original  ("+max.trim()+")");
	        $('#montoIncorrecto').show();
	        elem.value = "";

	          focusElement(elem);
	        
	        return false;
	        }

		$('#montoInvalido').hide();
		$('#montoIncorrecto').hide();
		
    return true;
}
</script>
</logic:notEqual>
</logic:notEqual>

    
<script>
	function validarFecha(event) {
		var fDesdeRend = new Date();
		var fecha = $('#fechaD').val().split("/");
		fDesdeRend.setFullYear(fecha[2], fecha[1] - 1, fecha[0]);

		var fHastaRend = new Date();
		fecha = $('#fechaH').val().split("/");
		fHastaRend.setFullYear(fecha[2], fecha[1] - 1, fecha[0]);

		var fGasto = new Date();
		fecha = $('#fechagastos').val().split("/");
		fGasto.setFullYear(fecha[2], fecha[1] - 1, fecha[0]);

		if (!(fGasto >= fDesdeRend && fGasto <= fHastaRend)) {
			//alert("fecha invalida, la fecha debe estar entre: "+fechaD+" y "+fechaH);
			$('#fechaInvalida').show();
			return false;

		} else {
			$('#fechaInvalida').hide();
			return true;
		}
	}
</script>
<logic:equal value="1" name="conCupon">
	<script>
		if ($('#esAdelanto').val() != "1") {
			$('#fechagastos').attr('readonly', true);
			$("#imageCal5").hide();
		} else {
			jQuery('#fechagastos').datepicker({
				dateFormat : 'dd/mm/yy'
			});
		}
	</script>
</logic:equal>
<logic:notEqual value="1" name="conCupon">
	<script type="text/javascript">
		jQuery('#fechagastos').datepicker({
			dateFormat : 'dd/mm/yy'
		});
		$("#switchDiv").hide();
		$("#fechagastosDiv").show();
	</script>
</logic:notEqual>
<logic:equal value="1" name="conCupon">
	<script type="text/javascript">
	$('#cuponesCheck').prop('checked', true);
	$("#switchDiv").show();
	$("#fechagastos2Div").show();
</script>
</logic:equal>
<script>
	var query = "";
	var $checkboxes = find('input[id=checkAsignado]');

	var checkeado = false;

	for ( var i = 0; i < $checkboxes.size(); i++) {
		if ($checkboxes.get(i).checked) {
			query += "idClient"
					+ i
					+ "="
					+ jQuery(jQuery($checkboxes[i]).parent().prevAll().get(7))
							.text().trim() + "&";

			query += "naClient" + i;
		}
	}
</script>
<logic:equal value="PENDI" name="estadoRend">
	<script>
	function OnClose() {
		var str = $("#idGasto").val();
		if(str.substring(59,str.length) == "N"){
			
        if (window.opener != null && !window.opener.closed) {
            window.opener.HideModalDiv();
            
//	        window.close();

       
    window.opener.location.href="mostrarDetalleGastos.do?codigo="+$('#idRendicion').val()+"&codMotivo="+$('#codMotivo').val()+"&estadoRend="+$('#estadoRendicion').val();
	}}
		else{
            window.opener.HideModalDiv();
            
			}
    }
    window.onunload = OnClose;
</script>
</logic:equal>
<logic:present name="listadoAprob">
	<script>
	$(document).ready(function(){
		$("#comboMoneda").attr("disabled",true);
		$("#comboComprobante").attr("disabled",true);
		$("#comboTipoGasto").attr("disabled",true);
		$("#fechagastos").attr("disabled",true);
		$("#obsGasto").attr("disabled",true);
		$("#fechagastosDiv").hide();
		$("#fechagastos2Div").show();
		$("#calendarFechagastos").hide();
	});

    </script>
</logic:present>
<logic:present name="estadoRend">
<logic:notEqual value="PENDI" name="estadoRend">
<script>
$(document).ready(function(){
		$("#comboMoneda").attr("disabled",true);
		$("#comboComprobante").attr("disabled",true);
		$("#comboTipoGasto").attr("disabled",true);
		$("#obsGasto").attr("disabled",true);
		
		$("#fechagastosDiv").hide();
		$("#fechagastos2Div").show();
		
	});</script>
</logic:notEqual>
</logic:present>
</logic:notPresent>
<script>
function OnClose() {
   	if (window.opener != null && !window.opener.closed) {
  	  window.opener.HideModalDiv(); 
	}
}
window.onunload = OnClose;
</script>
 
<script>
function check() {
	    var el = document.getElementById("comboComprobante");
	    var str = el.options[el.selectedIndex].text;
	    if(str == "FACTURA OBLIGATORIA") {
	        show();
	    }else {
	        hide();
	    }
	}
	
function hide(){
    document.getElementById('detalleComprobanteCuit').style.display='none';
	 $('#cuitIncorrecto').hide();
}

function show(){
    document.getElementById('detalleComprobanteCuit').style.display='';
}
</script>
<script>
function clearComprobante() {
	document.getElementById('comprobante1').val='';
	document.getElementById('comprobante2').val='';
}
</script>
</body>

</html>