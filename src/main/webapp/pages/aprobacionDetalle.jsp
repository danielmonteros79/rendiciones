<html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>

<script type="text/javascript" src="./js/rendicionDetalleGastos.js"></script>
<script type="text/javascript" src="./js/rendicionDetalle.js"></script>
<script type="text/javascript" src="./js/popUpsOpen.js"></script>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="./css/rendicionDetalle.css">
<link rel="stylesheet" type="text/css" href="./css/validation.css">
<link rel="stylesheet" type="text/css" href="./css/detalleGastos.css">
<script>
function screenLoadImages() {
	window.location.href = "rendicionAviso.do" +
		"?action=mostrarPantalla" +
		"&codigo=" + $('#idRendicion').val() +
		"&codMotivo=" + $('#codMotivo').val() +
		"&estadoRend=" + $('#estadoRendicion').val() +
		"&usuarioRend=" + $('#usuarioRend').val() +
		"&glg=" + $('#glg').val();
}

function generateCaratula() {
	window.location.href = "rendicionAviso.do?generate=anymode&rnd="+$('#idRendicion').val();
}
</script>
</head>
<body>
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
	<logic:notPresent name="error">
		<div id="divBackground"></div>
		<input type="hidden" name="tipoSubmit" id="tipoSubmit">
		
		<html:form action="aprobacionDetalle" styleId="RendicionForm">
		<html:hidden property="usuarioRend" styleId="usuarioRend"/>
		<html:hidden property="glg" styleId="glg"/>
		<html:hidden property="codMotivo" styleId="codMotivo"/>
		<html:text property="linkThuban" style="display:none;" styleId="linkThuban"/>
		<fieldset>
			<table style="height: 60%;">
				<thead>
					<tr>
						<th colspan="8">Detalle Rendici&oacute;n</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td align="left" colspan="7">
							ID-Rendici&oacute;n:
							<html:text styleClass="green" property="idRendicion" styleId="idRendicion" readonly="true" />
						</td>
						<td colspan="1">
							<logic:present name="showCaratula">
								<html:button styleClass="buttonSave" property="" value="Generar Caratula" onclick="generateCaratula()" />
							</logic:present>
							<logic:notEqual value="PENDI" name="estadoRend">
								<a href="#" onclick="thubanOpen()" style="margin-left:5px;float:right;">
									<img src="./images/iconos/infoIcon.png" alt="Thuban" height="24" width="24">
								</a>
							</logic:notEqual>
						</td>
					</tr>
					<tr>
						<td align="left" colspan="4">
							Usuario:
							<html:hidden property="user"/>
							<html:text styleClass="green" property="nombreUsuario" readonly="true"  style="width:250px;"/>
						</td>
		
						<td align="left" colspan="1">
							C.Costos:
							<html:text styleClass="green" property="costos" styleId="Costos" readonly="true" style="width:30%;"/>
						</td>
						<td align="left" colspan="2" id="TDSector" style="width: 25%">
							Sector:
							<html:text styleClass="green" property="sector" readonly="true" />
						</td>
					</tr>
					<tr>
						<td align="left" colspan="4">RENDICION</td>
						<td align=right colspan="4" style="text-align:center;padding-right:80px;">PERIODO</td>
					</tr>
					<tr>
						<td align="left" colspan="4">
							<label for="motivo">Motivo:</label>
							<span class="green" style="font-weight:initial;">
								<bean:write name="RendicionForm" property="motivo"/>
							</span>
			        		<a id="ayudaMotivo" target="_blank">
								<img width="15px" src='./images/iconos/question-mark-2-48.png' alt='Ayuda' title="Ayuda"
									border='0' style="margin-left: 5px;" align="top" />
							</a>
						</td>
						<td align="right">
							<label for="fechaDesde">Desde</label>
							<html:text property="fechaDesde" size="8" styleClass="fechaDDMMYY green" styleId="fechaDesdeDisable" readonly="true" />
						</td>
	
						<td align="left" colspan="2">
							<label for="fechaHasta">Hasta</label>
							<html:text property="fechaHasta" size="8" styleClass="fechaDDMMYY green" readonly="true" styleId="fechaHastaDisable"/>
						</td>
					</tr>
					<tr>
						<td align="left" colspan="4">Comentario/Observaciones:</td>
					</tr>
					
					<tr>
						<td align="left" colspan="8"><label for="descripcion"></label>
							<html:textarea styleClass="green" property="descripcion" onkeyup="cantCaracteres(120)" 
								styleId="descripcion" readonly="true"/>
						</td>
					</tr>
					<logic:equal value="Si" name="motivoRechazo">
						<tr>
							<td align="left" colspan="8">Motivo del rechazo:</td>
						</tr>
						<tr>
							<td align="left" colspan="8">
			        			<html:textarea styleClass="green" property="motivoRechazo" styleId="descripcionRechazo" readonly="true"/>
			        		</td>
						</tr>
					</logic:equal>
				</tbody>
			</table>
		</fieldset>
		</html:form>
		<html:form action="aprobacionesSend" styleId="AprobarRendicionForm">
			<html:hidden property="id" styleId="formAprobacion" />
			<html:hidden property="estado" styleId="formAprobacion" value="1" />
			<logic:equal value="2" name="displayButtons">
				<html:link href="#" styleClass="buttonAprobar" style="float:right; margin-right:1.5%;" onclick="aprobar()"
					styleId="buttonAprobar">Aprobar</html:link>
			</logic:equal>
		</html:form>
	
		<html:form action="aprobacionesSend" styleId="RechazarRendicionForm"
			method="post">
			<html:hidden property="id" styleId="formAprobacion" />
			<html:hidden property="estado" styleId="formAprobacion" value="3" />
			<logic:equal value="2" name="displayButtons">
				<html:link href="#" styleClass="buttonCancel" style="float:right;margin-right:1.5%;" onclick="rechazar()">
					Rechazar
				</html:link>
			</logic:equal>
		</html:form>
		
		<a href="#" onclick="screenLoadImages()" style="margin-left:10px;">
			<img width="32px" height="32px" alt="Ingreso" title="Adjuntar im&aacute;genes" src="./images/iconos/scanner.png" />
		</a>
		
		<logic:equal name="RendicionForm" property="glg" value="3">
			<a onclick="observarPopUp();this.disabled=true;" class="buttonMessage"
				style="float:right;margin-right:1.5%;margin-bottom:10px;margin-top:0px;">Observar</a> 
		<!--<a onclick="distribuirGastos()" class="buttonContinue"
			style="float:right;margin-right:1.5%;margin-bottom:10px;margin-top:0px;">Redistribuir</a>-->
		</logic:equal>
		<logic:equal name="RendicionForm" property="glg" value="4">
			<a onclick="observarPopUp();this.disabled=true;" class="buttonMessage"
				style="float:right;margin-right:1.5%;margin-bottom:10px;margin-top:0px;">Observar</a> 
		<!--<a onclick="distribuirGastos()" class="buttonContinue"
			style="float:right;margin-right:1.5%;margin-bottom:10px;margin-top:0px;">Redistribuir</a>-->
		</logic:equal>
			
			<display:table uid="row" name="Gastos"
				requestURI="aprobacionDetalle.do" id="GastosTable" excludedParams="false" 
				decorator="com.sa.decorator.AprobacionDetalleTableDecorator" pagesize="10"
				style="width:98%;">
				<display:column property="descGasto" title="Rendicion Gastos"
					style="width:10%" />
				<display:column property="monto" title="Monto Gastos"
					style="width:10%" />
				<display:column property="moneda" title="Moneda" style="width:10%" />
				<display:column property="fechagastos" format="{0,date,dd/MM/yyyy}"
					title="Fe. Gastos" style="width:10%" />
				<display:column property="comprobante" title="Tipo Comprobante"
					style="width:10%;" />
				<logic:equal value="2" name="displayButtons">
					<display:column property="opciones" title="Ver"
						style="width:2.5%; text-align:center;">
					</display:column>
				</logic:equal>
				<display:column property="comentarios" title="Comentarios"
					style="width:2.5%; text-align:center;">
				</display:column>
				<display:column property="cupones" title="Cupones"
					style="width:2.5%; text-align:center;">
				</display:column>
			</display:table>
		
	
		<script>
			function aprobar() {
				document.forms[2].action = document.forms[2].action + "?codigo="
				+ $('#idRendicion').val();
				popUpObj = window.open("MotivoAprobacion.do" + "?codigo="
						+ $('#idRendicion').val()+"&glg="+$('#glg').val()+"&"+"usuarioRendicion="+$('#usuarioRend').val(), "ModalPopUp", "toolbar=no,"
						+ "scrollbars=no," + "location=no," + "statusbar=no,"
						+ "menubar=no," + "resizable=0," + "width=450,"
						+ "height=160," + "left = 500," + "right = 500,"
						+ "top=250," + "bottom = 250");
				popUpObj.focus();
				LoadModalDiv();
			}
	
			jQuery(document).ready(function() {
				$("#ayudaMotivo").attr("href", "ayuda/ayuda_" + $("#codMotivo").val() + ".pdf");
				$('#checkAprobacion').show();
			});
	
			function rechazar() {
				document.forms[2].action = document.forms[2].action + "?codigo="
						+ $('#idRendicion').val();
				popUpObj = window.open("MotivoRechazo.do" + "?codigo="
						+ $('#idRendicion').val()+"&glg="+$('#glg').val()+"&"+"usuarioRendicion="+$('#usuarioRend').val(), "ModalPopUp", "toolbar=no,"
						+ "scrollbars=no," + "location=no," + "statusbar=no,"
						+ "menubar=no," + "resizable=0," + "width=450,"
						+ "height=160," + "left = 500," + "right = 500,"
						+ "top=250," + "bottom = 250");
				popUpObj.focus();
				LoadModalDiv();
	
			};
			
	 		function observarPopUp() {
	 			iz = (screen.height / 2) - (300 / 2);
	 			de = (screen.width / 2) - (500 / 2);
					
	 			popUpObj = window.open(
					"MotivoObservar.do?id=" + $('#idRendicion').val() + "&glg=" + $('#glg').val(),
					"ModalPopUp",
					"toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, width=480, height=280, left="
						+ de + ", top=" + iz);
	 			popUpObj.focus();
	 			LoadModalDiv();
	 		}
	 		
			function distribuirGastos() {
	 			window.location.href = "distribucionGastosLoad.do?idRendicion=" + $('#idRendicion').val() + "&usuarioRendicion="+$('#usuarioRend').val();
	 		}
	 		
	 		function thubanOpen (){
				var link =($('#linkThuban').val());
				window.open(link);
			};
		</script> 
		<logic:equal value="1" name="displayButtons">
			<script>jQuery(document).ready(function() {
				$('#checkAprobacion').hide();
				$('#checkCierre').show();
			});
			</script>
		</logic:equal>
	</logic:notPresent>
</body>
</html>