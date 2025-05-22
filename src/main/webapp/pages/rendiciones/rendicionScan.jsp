<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>
<head>
<!-- <script src="http://code.jquery.com/jquery-1.10.2.min.js"></script> -->
<script type="text/javascript" src="//cdn.rawgit.com/MrRio/jsPDF/master/dist/jspdf.min.js"></script>
<script type="text/javascript" src="./static/js/listadoRendiciones.js"></script>
<script type="text/javascript" src="./static/js/rendicionDetalleGastos.js"></script>
<script type="text/javascript" src="./static/js/rendicionDetalle.js"></script>
<link rel="stylesheet" type="text/css" href="./css/rendicionDetalle.css">
<link rel="stylesheet" type="text/css" href="./css/validation.css">
<link rel="stylesheet" type="text/css" href="./css/detalleGastos.css">


<logic:present name="error">
	<br>
	<p id="error"
		style="text-align: center; color: red; font-weight: bold; font-size: 13px; margin-top: -10PX; margin-bottom: -1px;">
		<bean:write name="error"/>
	</p>
	</logic:present>
<!-- 	<br> -->

</head>
<body>
	<div id="divBackground"></div>
	<html:form action="/mostrarDetalleScanSave" styleId="RendicionForm">
		<!-- Form de exportacion a pdf-->
		<html:hidden name="RendicionForm" property="nameFile" styleId="nameFile" />
		<html:hidden name="RendicionForm" property="html" styleId="html" />
		<html:hidden name="RendicionForm" property="fileType" styleId="fileType" />
		<fieldset>
			<div id="editor"></div>
			<table style="height: 60%;" id="Rtable">
				<thead>
					<tr>
						<th colspan="8">Detalle Rendición</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td align="left" colspan="4" id="TDusuario">Usuario: <html:text
								property="user" styleId="TDuser" readonly="true" /> <html:text
								property="nombreUsuario" styleId="TDnombreUsuario"
								readonly="true" />
						</td>
						<td align="left" colspan="2" id="TDidrendicion">ID-Rendición:
							<html:text property="idRendicion" styleId="idRendicion"
								readonly="true" />
						</td>
						<td align="left" colspan="1" id="TDcostos">C.Costos: <html:text
								property="costos" styleId="Costos" readonly="true" />
						</td>
						<td align="left" colspan="1" id="TDsector">Sector: <html:text
								property="sector" styleId="Sector" readonly="true" />
						</td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td align="left" colspan="3" Id="TDrendicion">RENDICION</td>
						<td align=right colspan="5" Id="TDperiodo">PERIODO</td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td align="left" colspan="3" id="TDmotivo" ><label
							for="motivo"> Motivo:</label> <html:text property="motivo"
								size="14" readonly="true" styleId="tdmotivo">
							</html:text></td>
						<td colspan="1"></td>
						<td align="right" colspan="2" id="TDfeDesde"><label
							for="fechaDesde"> Fe.Desde</label> <html:text
								property="fechaDesde" size="8" styleClass="fechaDDMMYY"
								readonly="true" styleId="tdfeDesde" /></td>

						<td align="left" colspan="3" id="TDfeHasta"><label
							for="fechaHasta"> Fe.Hasta</label> <html:text
								property="fechaHasta" size="8" styleClass="fechaDDMMYY"
								readonly="true" styleId="tdfeHasta" /></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td align="left" colspan="4" id="TDdescripcion">Descripcion/Observaciones:</td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td align="left" colspan="8"><label for="descripcion"></label>
							<html:textarea property="descripcion"
								onkeyup="cantCaracteres(120)" styleId="descripcion"
								readonly="true"></html:textarea></td>
					</tr>
					<tr>

						<td colspan="8" style="text-align: right;"><html:button
								styleClass="buttonAdd" property="" styleId="pdf" value="Scan" onclick="scan();" /></td>

					</tr>
				</tbody>
			</table>
		</fieldset>
		</tbody>
	</html:form>
	<script>
	jQuery(document).ready(function() {

		$('#checkRendiciones').show();
	});
	   
	</script>

	<script src="static/js/exportar.js"></script>
	<script type="text/javascript">
	function scan(){ 
	exportDataGridPDF();
	document.forms[0].submit();		
	}
	</script>
</body>