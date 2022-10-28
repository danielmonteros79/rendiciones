<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>
<bean:define id="ParametrosGastosForm" name="ParametrosGastosForm" scope="session" toScope="request"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="./css/validation.css">
	<link rel="stylesheet" type="text/css" href="./css/Parametros.css">
	<style>
		td {
			white-space:nowrap;
			text-align:left;
		}
		label.error {
			position: absolute;
		}
	</style>
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
	<html:form action="saveGasto" styleId="parametrosGastosForm">
		<table>
			<tbody>
				<tr>
					<td class="fieldTable">Gasto</td>
					<td colspan="3">
						<html:text property="codigo" styleId="codigo" style="width:60px;" maxlength="4"/>
						<span style="font-weight:bold;margin:0 15px 0 25px">Descripci&oacute;n</span>
						<html:text property="descripcionGasto" styleId="descripcionGasto" style="width:360px;" maxlength="50"/>
					</td>
					<td class="fieldTable">Estado</td>
					<td>
						<html:select property="estado" styleId="estado" style="width:75px; color:black;">
							<html:option value=""></html:option>
							<html:option value="I">Inactivo</html:option>
							<html:option value="A">Activo</html:option>
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="fieldTable">Motivo</td>
					<td>
						<html:select styleId="motivo" property="motivo" style="max-width:350px; color:black;">
							<html:option value=""></html:option>
							<html:options collection="cmbMotivo" property="id" labelProperty="descripcion"/>
						</html:select>
					</td>
					<td class="fieldTable">C. Costos</td>
					<td>
						<html:text property="idCentroCostos" style="width:59px;" maxlength="4"/>
					</td> 
					<td class="fieldTable">Bimon</td>
					<td>
						<html:select property="bimon" style="width:75px; color:black;">
							<html:option value=""></html:option>
							<html:option value="S">S</html:option>
							<html:option value="N">N</html:option>
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="fieldTable">Comprobante</td>
					<td>
						<html:select property="comprob" style="color:black;">
							<html:option value=""></html:option>
							<html:options collection="cmbComprobante" property="id" labelProperty="descripcion"/>
						</html:select>
					</td>
					<td class="fieldTable">Antigüedad</td>
					<td>
						<html:text property="antiguedad" style="width:59px;" maxlength="4"/>
					</td>
					<td class="fieldTable" align="right">
						Incl/Excl
					</td>
					<td>
						<html:select property="maInclExcl" style="width:75px; color:black;">
							<html:option value=""></html:option>
							<html:option value="I">Incl</html:option>
							<html:option value="E">Excl</html:option>
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="fieldTable">Observaci&oacute;n</td>
					<td>
						<html:select property="observ" style="color:black;">
							<html:option value=""></html:option>
							<html:options collection="cmbObservacion" property="id" labelProperty="descripcion"/>
						</html:select>
					</td>
					<td class="fieldTable">Nivel Ingreso</td>
					<td>
						<html:select property="idNivAutoriz" style="width:65px; color:black;">
							<html:option value=""></html:option>
							<html:option value="1">1</html:option>
							<html:option value="2">2</html:option>
							<html:option value="3">3</html:option>
							<html:option value="4">4</html:option>
						</html:select>
					</td>
					<td class="fieldTable">Plazo Aprob</td>
					<td>
						<html:select property="plazoAprob" style="width:75px; color:black;">
							<html:option value=""></html:option>
							<html:option value="DIA">DIA</html:option>
							<html:option value="SEM">SEM</html:option>
							<html:option value="QUIN">QUIN</html:option>
							<html:option value="MES">MES</html:option>
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="fieldTable">Ristra</td>
					<td colspan="3">
						<html:text property="ristra" style="width:450px;" maxlength="69" readonly="true"/>
						<a href="#" onclick="showRistraPopup();">
							<logic:equal value="baja" name="ParametrosGastosForm" property="accion">
								<img src="images/iconos/ver.png" alt="Ver" title="Ver" border="0">
							</logic:equal>
							<logic:notEqual value="baja" name="ParametrosGastosForm" property="accion">
								<img src="images/iconos/editar.png" alt="Editar" title="Editar" border="0">
							</logic:notEqual>
						</a>
					</td>
					<td colspan="3" style="width:241px;">
						<div class="ck-button first"><label><html:checkbox value="O" property="oscar.o"/><span>O</span></label></div>
						<div class="ck-button"><label><html:checkbox value="S" property="oscar.s"/><span>S</span></label></div>
						<div class="ck-button"><label><html:checkbox value="C" property="oscar.c"/><span>C</span></label></div>
						<div class="ck-button"><label><html:checkbox value="A" property="oscar.a"/><span>A</span></label></div>
						<div class="ck-button last"><label><html:checkbox value="R" property="oscar.r"/><span>R</span></label></div>
						<html:hidden styleId="oscarO" property="oscar.o"/>
						<html:hidden styleId="oscarS" property="oscar.s"/>
						<html:hidden styleId="oscarC" property="oscar.c"/>
						<html:hidden styleId="oscarA" property="oscar.a"/>
						<html:hidden styleId="oscarR" property="oscar.r"/>
					</td>
				</tr>
				<tr>
					<td style="font-weight:bold;vertical-align:top;padding-top:6px;">Centros Costo</td>
					<td colspan="7" id="tdCentrosCosto" style="white-space:normal;padding-right:90px;height:29px;width:750px;vertical-align:top;">
						<logic:iterate name="ParametrosGastosForm" property="centrosCosto" id="centrosCostoI" indexId="i">
							<%String cc="centrosCostoI[" + i + "]"; %>
							<%String ccId="centrosCosto_" + i; %>
							<html:text styleId="<%=ccId%>" property="<%=cc%>" maxlength="4" style="width:50px;"
								onkeypress="return numericOnly(event);"/>
							<a href="#" onclick="borrarCentroCosto(<%=i%>);" name="d_<%=cc%>">
								<img src="./images/iconos/borrar.png" alt="Eliminar" title="Eliminar" style="width:10px;"/>
							</a>
						</logic:iterate>
						<a id="addCC" href="#" onclick="agregarCentroCosto();">
							<img src="./images/iconos/add.png" alt="Agregar" title="Agregar" style="width:10px;margin-top:6px;"/>
						</a>
						<input type="hidden" name="centrosCosto"/>
					</td>
				</tr>
				<tr>
					<td colspan="1"></td>
					<td colspan="7">
						<div id="errorCentrosCosto" style="color:red;"></div>
					</td>
				</tr>
				<tr>
					<td colspan="6" style="text-align:right;padding-right:10px;">
						<logic:equal value="modificacion" name="ParametrosGastosForm" property="accion">
					 		<html:submit styleClass="buttonSave" value="Guardar"/>	
						</logic:equal>
						<logic:equal value="alta" name="ParametrosGastosForm" property="accion">
					 		<html:submit styleClass="buttonSave" value="Guardar"/>	
						</logic:equal>
						<logic:equal value="baja" name="ParametrosGastosForm" property="accion">
							<html:button property="" styleClass="buttonCancel" onclick="confirmEliminarGasto()" value="Eliminar"/>
						</logic:equal>
						<a href="parametrosGastosFiltro.do"><input type="button" class="buttonCancel" value="Volver"/></a>
					</td>
				</tr>
			</tbody>
		</table>
		<html:hidden property="accion" styleId="accion"/>
		<html:hidden property="back" styleId="back" value="true"/>
	</html:form>
	
	<logic:equal value="alta" name="ParametrosGastosForm" property="accion">
		<script>
			$( document ).ready(function() {
				$('#estado').attr('disabled','disabled');
			});
		</script>
	</logic:equal>
	
	<logic:equal value="baja" name="ParametrosGastosForm" property="accion">
		<script>
			$( document ).ready(function() {
				$('input').attr('readonly', true);
				$('select').attr('disabled','disabled');
				$(".ck-button").attr('disabled','disabled');
				$("[name^='oscar']").attr('disabled','disabled');
				$("[name^='d_centrosCostoI']").hide();
				$("#addCC").hide();
			});
		</script>
	</logic:equal>
	
	<logic:equal value="modificacion" name="ParametrosGastosForm" property="accion">
		<script>
			$( document ).ready(function() {
				$('#codigo').attr('readonly', true);
				$('#descripcionGasto').attr('readonly', true);
				$('#motivo').attr('disabled','disabled');
			});
		</script>
	</logic:equal>
	
	<script type="text/javascript" src="./js/parametrosGastosDetalle.js"></script>
</body>
</html>