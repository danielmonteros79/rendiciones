<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html>
<head>
<%-- <bean:define id="usuarioNombre" name="usuarioNombre" scope="request" type="java.lang.String"></bean:define> --%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="./static/js/popUpsOpen.js"></script>
<script type="text/javascript" src="static/js/cierreTarjeta.js"></script>
<style type="text/css">
#paginado {
	color: black !important;
}
</style>
</head>
<body>	  
	<%		
		Usuario userSession = (Usuario) request.getSession().getAttribute(
				"usuario");
		Usuario userWorking = (Usuario) request.getSession().getAttribute(
				"userWorking");
		
		if (userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL
				|| userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL_LESS_PARAMS) {
	%>
	<logic:present name="message">
		<%
			String message = (String) request.getAttribute("message");
			if (message == null || message == "") {
				message = (String) request.getSession().getAttribute("message");
				request.getSession().removeAttribute("message");
			}
			
			if (message.contains("ERROR")) {
		%>
		<div id="messageErr" class="message">
			<%=message.substring(7)%>
		</div>
		<%
			} else if (message.contains("OK")) {
		%>
		<div id="messageOk" class="message">
			<%=message.substring(4)%>
		</div>
		<%
			} else {
		%>
		<div id="messageAviso" class="message">
			AVISO:
			<%=message%>
		</div>
		<%
			}
		%>
	</logic:present>

	<table style="margin-bottom: 10px;">
		<html:form action="CierreTarjeta" styleId="CierreTarjetaForm">
			<tbody class="filtro" style="font-size: small; font-weight: bold;">
				<tr>
					<td style="width: 15%;">Usuario: <html:text property="usuario" styleId="usuario" style="width: 50%;text-transform:uppercase;" maxlength="8" /></td>
					<td id="userWorking" style="text-align: left; width: 50%;" >
					<logic:notEmpty name="usuarioNombre" ><bean:write name="usuarioNombre"/></logic:notEmpty></td>
					<td align="right" style="text-align: right; width: 1%;"><html:submit styleClass="buttonFilter" value="Filtrar" /></td>
					<td style="text-align: rigth; width: 1%;"><a onclick="ShowCierreTarj()" Class="buttonAprobar" style="float: right; color: black;" id="Gestionar"> Generar </a></td>
				</tr>
			</tbody>
		</html:form>
	</table>
	<div id="paginado">
		<table style="margin-bottom: 10px; border: none;">
			<html:form action="CierreTarjeta" styleId="CierreTarjetaForm">
				<tbody class="filtro" style="font-size: small; font-weight: bold;">
					<tr>
						<td style="text-align: left; padding-left: 20px;">----- Total
							Consumos -----</td>
					</tr>
					<tr>
						<td style="text-align: right; position: absolute;">Pesos:</td>
						<td align="right" id="totalPesos" style="text-align: left; position: absolute; margin-left: 50px;">
						</td>
						<td style="text-align: right; position: absolute; margin-left: 150px;">Dolares:
						</td>
						<td align="right" id="totalDolar" style="text-align: left; position: absolute; margin-left: 210px;">
						</td>
					</tr>
				</tbody>
			</html:form>
		</table>
		<div style="height: 8px; width: 100%;">&nbsp;</div>
		<html:hidden property="estado" styleId="estado" value="" />
		<display:table uid="row" name="cierreTarjeta" requestURI="CierreTarjeta.do" id="CierreTarjetaForm" 
			decorator="com.sa.decorator.CierreTarjetaTableDecorator" pagesize="16" style="color:black;margin-top:20px;" export="true">
			<display:column property="idSecResumen" title="ID" style="width:10%" media="hidden" sortable="true" style="width:3%;text-align:center;"/>
			<display:column property="nroTarjeta" title="Nro. Tarjeta" sortable="true" style="width:8%;text-align:center;"/>
			<display:column property="fechaCupon" format="{0,date,dd/MM/yyyy}" title="Fecha" style="width:3%;text-align:center;" media="html"/>
			<display:column property="cuponAdmDev" title="Cupon" style="width:15%;" sortable="true" media="none" />
			<display:column property="establecimiento" title="Establecimiento" style="width:15%;" sortable="true" />
			<display:column property="montoCupon" title="Monto" sortable="true" style="width:5%;text-align:center;" format="{0,number,#,###.00}" />
			<display:column property="moneda" title="Moneda" style="width:5%;text-align:center;" sortable="true" />
			<display:column style="width:1%;text-align:center;" property="cupones" title="Asig." sortable="false" sortName="asignada" media=" html" />
			<display:setProperty name="export.csv.filename" value="ListadoAprobacion.csv" />
			<display:setProperty name="export.excel.filename" value="ListadoAprobacion.xls" />
		</display:table>
	</div>
	<logic:equal value="1" name="displaySuccess">
		<script type="text/javascript">
			$("#Generado").show(0);
		</script>
	</logic:equal>
	<logic:equal value="2" name="displaySuccess">
		<script type="text/javascript">
			$("#Suspendido").show(0);
		</script>
	</logic:equal>
	<%
		} else {
	%>
	No tiene permisos para ver esta p&aacute;gina
	<%
		}
	%>
</body>
</html>
