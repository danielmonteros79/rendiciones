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
<script type="text/javascript">
	jQuery(document).ready(function() {
		$('#checkReemplazar').show();
	});

	
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<script type="text/javascript" src="./js/validarRelacionUsuarioDelegadoAlta.js"></script>
	
	
	<link rel="stylesheet" type="text/css" href="./css/validation.css">
	<style>td {white-space:nowrap;text-align:left;font-weight: bold; font-size: 11px; margin-bottom: 5px; }</style>
</head>
<body>
<input type="hidden" id="fechaHoy"/>

		<logic:present name="msgAviso">
			<input type="hidden" id="msgAviso" value="<bean:write name='msgAviso'/>"/>
		</logic:present>
		<logic:notPresent name="msgAviso">
			<input type="hidden" id="msgAviso" value=""/>
		</logic:notPresent>
	<html:form action="execAbmDelegaciones" styleId="ambDelegacionForm" >
		<logic:present name="msgModOk">
			
			<div id="msgModOk" style="color:green; text-size:small;text-align:center;"><strong> <bean:write name="msgModOk"/> </strong></div>
		</logic:present>
		<logic:present name="msgError">
			<div id="msgError" style="color:red; text-size:small;text-align:center;"><strong> <bean:write name="msgError"/> </strong></div>
		</logic:present>
		<div id="fechaHoyValidate" style="color:red; text-size:small;text-align:center;display:none;"><strong>La fecha desde / hasta no puede ser mayor a la fecha de hoy</strong></div>
	<div id="fechaDesdeValidate" style="color:red; text-size:small;text-align:center;display:none;"><strong>La fecha desde no puede ser mayor a la fecha Hasta</strong></div>
		<table>
			<thead>
		<tr>
		<%	String tipoEjec = (String) request.getParameter("optn");
			if(tipoEjec.trim().equalsIgnoreCase("A")){%>
				
				<th colspan="12">Alta Delegaci&oacute;n</th>
			<%}else { %>
				<th colspan="12">Modificaci&oacute;n Delegaci&oacute;n</th>
				<html:hidden property="feDesdeOld"/>
				<html:hidden property="feHastaOld"/>
			<%} %>
		</tr>
	</thead>
			<tbody>
			<html:hidden property="opcion"/>
				<tr>
					<td>
						Usuario:
					</td>
					<td>
						<html:text property="delegadoUser" styleId="delegadoUser" onchange="checkUser()" style="width:75px;" maxlength="8"/>
					</td>
					<td colspan="6">
						<html:text property="delegadoNombre" styleId="delegadoNombre" style="width:450px; font-weight: bold;" readonly="true" maxlength="75"/>
					</td>
					<td>
						C.Costos:
					</td>
					<td>
						<html:text property="delegadoCentroCostos" styleId="delegadoCentroCosto" style="width:40px; font-weight: bold;" readonly="true" maxlength="4"/>
					</td>
					<td>
						Sector:
					</td>
					<td>
						<html:text property="delegadoSector" styleId="delegadoSector" style="width:40px; font-weight: bold;" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td>
						Informe:
					</td>
					<td>
						<html:select property="informe" styleId="informe" style="width:82px;">
							<html:option value="S">Si</html:option>
							<html:option value="N">No</html:option>
							<html:option value="D">Delegado</html:option>
						</html:select>
					</td>
					<td>
						Acci&oacute;n:
					</td>
					<td>
						<html:select
							property="accion" styleId="accion">
							<html:options  collection="comboAcciones" property="accion"
								labelProperty="accionDesc" />
						</html:select>
					</td>
					<td>
						Estado:
					</td>
					<td>
						<html:text property="estado" styleId="estado" style="width:20px; font-weight: bold;" readonly="true"/>
					</td>
					<td>
						Desde:
					</td>
					<td>
						<html:text property="feDesde" styleId="fechaDesde" size="8" style="width:75px;" styleClass="fechaDDMMYY" />
						<img id="imageCal1" src='./images/Calendar.png' border='0' style="float:left;position:absolute;cursor:pointer;width:21px;">
					</td>
					<td style="text-align:right;">
						Hasta:
					</td>
					<td colspan="2">
						<html:text property="feHasta" styleId="fechaHasta" onchange="checkEstado()" size="8" style="width:75px;" styleClass="fechaDDMMYY" />
						<img id="imageCal2" src='./images/Calendar.png' border='0' style="float:left;position:absolute;cursor:pointer;width:21px;">
					</td>
				</tr>
				<tr>
					<td colspan="3"></td>
					<td colspan="5">
						<div id="errorFechas" class="error"></div>
					</td>
				</tr>
				<tr>
					<td colspan="12" style="text-align:right;padding-right:10px;">
						<html:submit styleClass="buttonSave" styleId="bntSubmit" value="Guardar"/>
						
						<a href="relacionUsuarioDelegado.do">
							<input type="button" class="buttonCancel" value="Volver"/>
						</a>
					</td>
				</tr>
			</tbody>
		</table>
	</html:form>
</body>

</html>

