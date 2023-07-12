<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="./static/js/popUpsOpen.js"></script>
<script type="text/javascript" src="static/js/cierre.js"></script>
<style type="text/css">
#paginado {
	color: black !important;
}
</style>
<script>
	function showJournal(rend) {
		popUpObj = window.open("ShowJournal.do" + "?codigo="
				+ rend, "ModalPopUp", "toolbar=no,"
				+ "scrollbars=no," + "location=no," + "statusbar=no,"
				+ "menubar=no," + "resizable=0," + "width=800,"
				+ "height=525," + "left = 500," + "right = 500,"
				+ "top=250," + "bottom = 250");
		popUpObj.focus();	
	}
</script>
</head>
<body>
	<% 
		Usuario userSession = (Usuario) request.getSession().getAttribute("usuario");
		if (userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL ||
			userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL_LESS_PARAMS) { 
	%>
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
	
	<table style="margin-bottom: 10px;">
		<thead>
			<tr>
				<th colspan="3">Filtro rendiciones</th>
			</tr>
		</thead>
		<html:form action="filtrarCierre" styleId="CierreFiltroForm">
			<tbody class="filtro" style="font-size: small; font-weight: bold;">
				<tr>
					<td style="width:20%;">
						ID
						<html:text property="idRendicion" style="width: 50%" maxlength="16" onkeypress="return numericOnly(event);"/>
					</td>
					<td style="width:30%;">
						Usuario
						<html:text property="user" style="width: 50%;text-transform:uppercase;" maxlength="8"/>
					</td>
					<td style="width:50%;">
						Motivo
						<html:select property="motivo" styleId="motivo" style="width:70%;" onchange="ayudaMotivo();">
							<html:option value="">Todos</html:option>
							<html:options collection="comboMotivo" property="id" labelProperty="descripcion" />
						</html:select>
						<a id="ayudaMotivo"  target="_blank">
							<img width="15px" src='./images/iconos/question-mark-2-48.png' style="display:none" alt='Ayuda' title="Ayuda"
								border='0' style="margin-left: 5px;" align="top" id="imagen" />
						</a>
					</td>
				</tr>
				<tr>
					<td>
						Desde
						<html:text property="fechaDesde" styleId="fechaDesde" size="10" styleClass="fechaDDMMYY" />
						<a href='#' onClick="showCalendar('fechaDesde')">
							<img id="imageCal6" src='./images/Calendar.png' border='0' style="float: left; position: absolute">
						</a>
					</td>
					<td>
						Hasta
						<html:text property="fechaHasta" styleId="fechaHasta" size="10" />
						<a href='#' onClick="showCalendar('fechaHasta')">
							<img id="imageCal7" src='./images/Calendar.png' border='0' style="float: left; position: absolute">
						</a>
					</td>
					<td align="right" style="text-align: right; width: 5%;">
						<html:submit styleClass="buttonFilter" value="Filtrar" />
					</td>
				</tr>
			</tbody>
		</html:form>
	</table>
	
	<div id="paginado">
		<p style="display: none; color: red; font-size: small; text-align: center;"	id="Aguarde">Generando orden de pago, espere...</p>

		<div style="display: none; color: green; font-size: small; text-align: center;"	id="Generado">
			<p>Orden de pago Generada <img src='./images/iconos/checkGreen.png'	border='0' style="height: 12px;" /></p>
		</div>
		<div style="display: none; color: green; font-size: small; text-align: center;"	id="Suspendido">
			<p>Orden de pago Suspendida</p>
			<img src='./images/iconos/checkGreen.png' border='0' />
		</div>
		<a onclick="gestionarCierre(1)" Class="buttonAprobar" style="float: right; margin-right: 1.5%; color: black; margin-bottom: 10px;" id="Gestionar"> Generar </a>
		<a onclick="gestionarCierre(2)"	Class="buttonCancel" style="float: right; margin-right: 1.5%; color: black; margin-bottom: 10px;" id="Suspender"> Suspender </a>
		<div style="height: 8px; width: 100%;">&nbsp</div>
		<html:form action="gestionarCierre" styleId="CierreForm">
			<html:hidden property="estado" styleId="estado" value="" />
			
			<display:table uid="row" name="rendiciones" requestURI="filtrarCierre.do" id="Rendiciontbl" decorator="com.sa.decorator.CierreTableDecorator"
				pagesize="16" style="color:black;margin-top:20px;" export="true">
				<display:column property="id" title="ID" style="width:10%" sortable="true" />
				<display:column property="usuarioRendicion" title="Usuario" style="width:15%;" sortable="true" media="none" />
				<display:column property="nombreUsuarioRendicion" title="Usuario" style="width:15%;" sortable="true" />
				<display:column property="motivo" title="Descripcion motivo" sortable="true" style="width:35%" />
				<display:column property="importe" title="Efectivo" style="width:5%;" sortable="true" />
				<display:column property="importeTarjeta" title="Tarjeta" style="width:5%;" sortable="true" />
				<display:column property="opciones" title="Detalle" style="width:1%;text-align:center;white-space:nowrap;" />
				<display:column style="width:1%" property="cupones" title="Asig." sortable="false" sortName="asignada" media=" html" />
				
				<display:setProperty name="export.csv.filename" value="ListadoAprobacion.csv"/>
   				<display:setProperty name="export.excel.filename" value="ListadoAprobacion.xls"/>
			</display:table>
		</html:form>
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
<% } else { %>
	No tiene permisos para ver esta p&aacute;gina
<% } %>
</body>
</html>