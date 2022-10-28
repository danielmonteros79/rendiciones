<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<link rel="stylesheet" type="text/css" href="./css/validation.css">
	<link rel="stylesheet" type="text/css" href="./css/Parametros.css">
	<style>td {white-space:nowrap;text-align:left;}</style>
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
	
	<html:form action="saveAlerta" styleId="parametrosAlertasForm">
		<table>
			<tbody>
				<tr>
					<td class="fieldTable">Motivo</td>
					<td>
						<html:select property="codMotivo" style="width:250px; color:black;" onchange="selectMotivo();" styleId="motivo">
							<html:option value=""></html:option>
							<html:options collection="cmbMotivo" property="id" labelProperty="descripcion"/>
						</html:select>
					</td>
					<td class="fieldTable">Rendici&oacute;n</td>
					<td>
						<html:select property="rend" style="width:100px; color:black;">
							<html:option value=""></html:option>
							<html:option value="REND">Rendici&oacute;n</html:option>
							<html:option value="PROM">Promedio</html:option>
						</html:select>
					</td>
					<td class="fieldTable">Estado</td>
					<td>
						<html:select property="estado" style="width:100px color:black;" styleId="estado">
						<html:option value="I">Inactivo</html:option>
						<html:option value="A">Activo</html:option>
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="fieldTable">Gasto</td>
					<td>
						<html:select property="codGasto" style="width:250px; color:black;" onchange="selectGasto();" styleId="gasto">
							<html:option value=""></html:option>
							<html:options collection="cmbGasto" property="id" labelProperty="descripcion"/>
							<html:option value="9999">9999 - TODOS LOS GASTOS</html:option>
						</html:select>
					</td>
					<td class="fieldTable">Per&iacute;odo</td>
					<td>
						<html:select property="periodo" style="width:100px; color:black;">
							<html:option value=""></html:option>
							<html:option value="DI">Diario</html:option>
							<html:option value="SE">Semanal</html:option>
							<html:option value="ME">Mensual</html:option>
							<html:option value="BI">Bimestral</html:option>
							<html:option value="TR">Trimestral</html:option>
							<html:option value="CU">Cuatrimestral</html:option>
							<html:option value="SM">Semestral</html:option>
							<html:option value="AN">Anual</html:option>
						</html:select>
					</td>
					<td class="fieldTable">Criticidad</td>
					<td>
						<html:select property="criticidad" style="width:100px; color:black;">
							<html:option value=""></html:option>
							<html:option value="1">Riesgo Grave</html:option>
							<html:option value="2">Riesgo</html:option>
							<html:option value="3">Inc. Grave</html:option>
							<html:option value="4">Incidencia</html:option>
							<html:option value="5">Anomal&iacute;a</html:option>
						</html:select> 
					</td>
				</tr>
				<tr>
					<td class="fieldTable">Mont/cant</td>
					<td>
						<html:select property="montCant" styleId="montCant" style="color:black;" onchange="impCantChange();">
							<html:option value=""></html:option>
							<html:option value="M">Monto</html:option>
							<html:option value="C">Cantidad</html:option>
						</html:select>
						<html:text property="impCant" styleId="impCant" onkeypress="return numericOnly(event);" style="width:162px"/> 
					</td>
					<td class="fieldTable">Nivel M&aacute;x</td>
					<td>
						<html:select property="nivMax" styleId="nivMax" style="width:100px; color:black;">
							<html:option value=""></html:option>
							<html:option value="0">0</html:option>
							<html:option value="1">1</html:option>
							<html:option value="2">2</html:option>
							<html:option value="3">3</html:option>
							<html:option value="4">4</html:option>
							<html:option value="5">5</html:option>
							<html:option value="6">6</html:option>
							<html:option value="7">7</html:option>
							<html:option value="8">8</html:option>
							<html:option value="9">9</html:option>
						</html:select>
					</td>
					<td class="fieldTable">Nivel M&iacute;n</td>
					<td>				
						<html:select property="nivMin" styleId="nivMin" style="width:100px; color:black;">
							<html:option value=""></html:option>
							<html:option value="0">0</html:option>
							<html:option value="1">1</html:option>
							<html:option value="2">2</html:option>
							<html:option value="3">3</html:option>
							<html:option value="4">4</html:option>
							<html:option value="5">5</html:option>
							<html:option value="6">6</html:option>
							<html:option value="7">7</html:option>
							<html:option value="8">8</html:option>
							<html:option value="9">9</html:option>
						</html:select>
					</td>
				</tr>
				<tr>
					<td></td>
					<td colspan="2">
						<div id="errorImpCant" style="color:red;"></div>
					</td>
					<td colspan="3">
						<div id="errorNiveles" style="color:red;"></div>
					</td>
				</tr>
				<tr>
					<td class="fieldTable">Aviso</td>
					<td colspan="5">
						<html:textarea styleId="txAviso" property="txAviso" style="width:98%;"/>
					</td>
				</tr>
				<tr>
					<td colspan="8" style="text-align:right;padding-right:10px;">
						<logic:notEqual value="baja" name="ParametrosAlertasForm" property="accion">
					 		<html:submit styleClass="buttonSave" value="Guardar" />	
						</logic:notEqual>
						<logic:equal value="baja" name="ParametrosAlertasForm" property="accion">
							<html:button property="" styleClass="buttonCancel" onclick="confirmarEliminarAlerta()" value="Eliminar"/>
						</logic:equal>
						
						<a href="parametrosAlertasFiltro.do"><input type="button" class="buttonCancel" value="Volver"/></a>
					</td>
				</tr>
			</tbody>
		</table>
		<html:hidden property="accion" styleId="accion"/>
	</html:form>
	<logic:equal value="alta" name="ParametrosAlertasForm" property="accion">
		<script>
			$( document ).ready(function() {
				$('#estado').attr('disabled','disabled');
			});
		</script>
	</logic:equal>
	<logic:equal value="baja" name="ParametrosAlertasForm" property="accion">
		<script>
			$( document ).ready(function() {
				$('input').attr('readonly', true);
				$('textarea').attr('readonly', true);
				$('select').attr('disabled','disabled');
			});
		</script>
	</logic:equal>
	
	<logic:equal value="modificacion" name="ParametrosAlertasForm" property="accion">
		<script>
			$( document ).ready(function() {
				$('#motivo').attr('disabled','disabled');
				$('#gasto').attr('disabled','disabled');
			});
		</script>
	</logic:equal>
	
	<script type="text/javascript" src="./js/parametrosAlertasDetalle.js"></script>
</body>
</html>