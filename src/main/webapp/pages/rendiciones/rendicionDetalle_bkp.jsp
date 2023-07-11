<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<script type="text/javascript" src="./static/js/rendicionDetalle.js"></script>
<script type="text/javascript" src="./static/js/listadoRendiciones.js"></script>

<head>
<link rel="stylesheet" type="text/css" href="./css/rendicionDetalle.css">
<link rel="stylesheet" type="text/css" href="./css/validation.css">
</head>

<input type="hidden" name="tipoSubmit" id="tipoSubmit">
<html:form action="SaveRendicion" styleId="RendicionForm">
<html:hidden property="fechaHoy" styleId="fechaHoy"/>
	<fieldset><logic:equal value="1" name="validarTrx">
		<h3 style="color: red; font-size: small; text-align: center;">La
		TRX no retorno ningun ID de rendicion, por favor intente dar de alta
		nuevamente</h3>
	</logic:equal>
	<logic:present name="messageModifTCJP">
		<% String message = (String) request.getAttribute("messageModifTCJP"); 
		if(message.contains("ERROR")){
		%>
		<div id="messageErr" style="color: red; font-weight: bold; text-align: center; width: 100%; font-size: 14">
			<%= message %>
		</div>
		<%} %>
	</logic:present>
	<div id="fechaHoyValidate" style="color:red; text-size:small;text-align:center;display:none;"><strong>La fecha desde / hasta no puede ser mayor a la fecha de hoy</strong></div>
	<div id="fechaDesdeValidate" style="color:red; text-size:small;text-align:center;display:none;"><strong>La fecha desde no puede ser mayor a la fecha Hasta</strong></div>
	<div id="fechaFormat" style="color:red; text-size:small;text-align:center;display:none;"><strong>Las fechas deben tener formato DD/MM/AAAA</strong></div>
	
	<table>
		<thead>
			<tr>
				<th colspan="8">Detalle Rendición</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td align="left" colspan="4" id="TDUsuario">Usuario: <html:hidden
					property="user" styleId="TDUser" /> <html:text styleClass="green"
					property="nombreUsuario" styleId="TDNombreUsuario" readonly="true" /></td>

				<td align="left" colspan="1" id="TDCostos" style="width:49%;">C.Costo Usuario: <html:text styleClass="green"
					property="costos" styleId="TDCcostos" readonly="true" /></td>
				<td align="left" colspan="2" id="TDSector" style="width: 35%">Sector:
				<html:text property="sector" styleId="TDSectorStyle" readonly="true" styleClass="green" />
				</td>
			</tr>

			<tr>
				<td align="left" colspan="4" class="Titulos">RENDICION</td>
			</tr>

			<tr>
				<td align="left" colspan="4" Id="TDMotivo" style="min-width:390px;"><label for="motivo">
				Motivo:</label> <html:select property="motivo" styleId="motivo" onchange="ayudaMotivo();">
					<html:options collection="ComboMotivo" property="id"
						labelProperty="descripcion" />
				</html:select><a id="ayudaMotivo"  target="_blank">
							<img width="15px" src='./images/iconos/question-mark-2-48.png' alt='Ayuda' title="Ayuda"
							border='0' style="margin-left: 5px;margin-top: 3px;" align="top" /></a>
<!-- 				<a href=" http://normas.arg.igrupobbva/ciclohtm/010/puntos/p010-0008.pdf" target="_blank"> -->
<!-- 					<img width="20px" src='./images/iconos/question-mark-2-48.png' alt='Normas'  border='0' style="float:left; position:absolute;" />	 -->
<!-- 				 </a> -->
				</td>
			</tr>
			<tr>
				<td align="left" colspan="4" class="Titulos">PERIODO</td>
			</tr>
			<tr>
				<td align="left" colspan="3" Id="TDFechaDesde"><label
					for="fechaDesde"> Desde</label> <html:text value=""
					property="fechaDesde" styleId="fechaDesde" size="8"
					styleClass="fechaDDMMYY" onchange="validarFecha(this);" /><a href='#'
					onClick="showCalendar('fechaDesde')"><img id="imageCal1"
					src='./images/Calendar.png' border='0' style="float:left;position:absolute;"></a></td>
				<td align="left" colspan="3" Id="TDFechaHasta" style="padding-left: 30px;"><label
					for="fechaHasta"> Hasta</label> <html:text value=""
					property="fechaHasta" styleId="fechaHasta" styleClass="fechaDDMMYY"
					size="8" onchange="validarFecha(this);"/><a href='#' onClick="showCalendar('fechaHasta');">
					<img id="imageCal2" src='./images/Calendar.png' border='0' style="float:left;position:absolute;"></a>
				</td>
			</tr>
			<tr><td></td></tr>
			<tr>
				<td align="left" colspan="4" Id="TDDescripcion">Descripci&oacute;n/Observaciones:</td>
			</tr>
			<tr>
				<td></td>
			</tr>
			<tr>
				<td align="left" colspan="8"><label for="descripcion"></label>
				<html:textarea styleClass="green" property="descripcion" styleId="des"
					onkeyup="cantCaracteres(120)" style="width:99.5%;height:50px;"
					value=""></html:textarea></td>
			</tr>
			<tr>
				<td colspan="8" style="text-align: right;">
<%-- 				<html:submit styleClass="buttonSave" onclick="checkSubmit(1)" value="Guardar" styleId="send" /> --%>
				<html:submit styleClass="buttonContinue" onclick="checkSubmit(2)" value="Ingreso de gastos" /></td>
			</tr>

		</tbody>
	</table>
	<html:hidden property="accion" styleId="accion"/>
	<div id="sending"
		style="display: none; text-align: center; font-weight: bold; font-size: small;color:green;">Aguarde
	mientras se env&iacute;a el formulario</div>
	</fieldset>
</html:form>

<script>
	jQuery(document).ready(function() {
		ayudaMotivo();
	});

	function resetForm() {
		document.getElementById("RendicionForm").reset();
	}
	
	function submit() {
		document.getElementById("RendicionForm").submit();
	}
	
	function checkSubmit(tipoClick) {
		$('#tipoSubmit').val(tipoClick);
		document.forms[0].action = "SaveRendicion.do";
		document.forms[0].action = document.forms[0].action + "?tipoSubmit=" + tipoClick+"&estadoRend="+"PENDI";
	}
</script>
<script type="text/javascript">
	function cantCaracteres(limit) {
		var texto = document.getElementById("des").value;

		if (texto.length > limit) {
			alert("Se supero el máximo de caracteres permitidos");
			document.getElementById("des").value = texto
					.substring(0, limit - 1);
		}
	}
</script>
<script> function validarFecha(event) {
	var fDesdeRend = new Date();
	var fecha = $('#fechaDesde').val().split("/");
	fDesdeRend.setFullYear(fecha[2], fecha[1] - 1, fecha[0]);

	var fHastaRend = new Date();
	fecha = $('#fechaHasta').val().split("/");
	fHastaRend.setFullYear(fecha[2], fecha[1] - 1, fecha[0]);

	var fHoy = new Date();
	fecha = $('#fechaHoy').val().split("/");
	fHoy.setFullYear(fecha[2], fecha[1] - 1, fecha[0]);

	if (fHastaRend < fDesdeRend) {
		//alert("fecha invalida, la fecha debe estar entre: "+fechaD+" y "+fechaH);
		$('#saveButton').disable;
		$('#fechaDesdeValidate').show();
		$('input[type="submit"]').prop('disabled', true);
		return false;
	
	} else {
	
		if (fHastaRend > fHoy || fDesdeRend > fHoy ){
			$('#saveButton').disable;
			$('#fechaHoyValidate').show();
			$('input[type="submit"]').prop('disabled', true);
			return false;
			
			}
		else{
		$('#saveButton').enable;
		$('#fechaDesdeValidate').hide();
		$('#fechaHoyValidate').hide();
		$('input[type="submit"]').prop('disabled', false);
		return true;
	}}
}
</script>