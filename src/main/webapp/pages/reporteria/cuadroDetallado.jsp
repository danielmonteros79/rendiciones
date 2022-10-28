<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.struts.action.ActionForm"%>
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

<link rel="stylesheet" type="text/css" href="./css/Parametros.css">
<link rel="stylesheet" type="text/css" href="./css/validation.css">
<style>
td {
	text-align: left;
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
	<html:form action="FiltroCuadroDetallado" styleId="CuadroDetalladoForm">
		<table style="margin-bottom: 10px;">
			<thead>
				<tr>
					<th colspan="13">Filtro Cuadro Detallado</th>
				</tr>
			</thead>
			<tbody class="filtro" style="font-size: small; font-weight: bold;">
				<tr>
					<td colspan="4">Usuario:
						<html:text styleClass="green" property="nombreUsuario" style="width: 280px;" readonly="true"/>
					</td>
					<td colspan="4">C.Costos:
						<html:text styleClass="green" property="costos" readonly="true" />
					</td>
				</tr>
				<tr>
					<td>Opci&oacute;n:</td>
					<td>
						<html:select property="opcion" styleId="opcion" onchange="selectOpcion()">
							<html:option value="01">Estado actual</html:option>
							<html:option value="02">Estado final</html:option>
							<html:option value="03">Fecha de carga</html:option>
						</html:select>
					</td>
					<td>GLG:</td>
<!-- 					<td colspan="3" id="tdGlg"  -->
<!-- 						style="white-space:normal;padding-right:90px;height:29px;width:750px;vertical-align:top;"> -->
<%-- 						<logic:iterate name="CuadroFiltroForm" property="glg" id="glgI" indexId="i"> --%>
<%-- 							<%String glg ="glgI[" + i + "]"; %> --%>
<%-- 							<%String glgSel ="glgSelI[" + i + "]"; %> --%>
<%-- 							<%String glgId ="glg" + i; %> --%>
<%-- 							<bean:define id="val" name="CuadroFiltroForm" property="<%=glg%>"/> --%>
<!-- 							<div class="ck-button"><label> -->
<%-- 								<html:checkbox styleId="<%=glgId%>" value="<%=val.toString()%>"  --%>
<%-- 									property="<%=glgSel%>" style="width:50px;" /> --%>
<%-- 								<span><bean:write name="CuadroFiltroForm" property="<%=glg%>" /></span> --%>
<!-- 							</label></div> -->
<%-- 						</logic:iterate> --%>
<!-- 						<input type="hidden" name="glg"/> -->
<!-- 					</td> -->
					<td>
						<html:select property="codGlg" styleId="codGlg" style="width:100px;">
							<html:options collection="ComboGlg" property="id" labelProperty="descripcion"/>
						</html:select>
					</td>
					<td>Motivo:</td>
					<td colspan="3">
						<html:select property="codMotivo" styleId="codMotivo" style="width:320px;">
							<html:option value="">TODOS</html:option>
							<html:options collection="ComboMotivo" property="id" labelProperty="descripcion"/>
						</html:select>
					</td>
				</tr>
				<tr>
					<td>Desde:</td>
					<td>
						<html:text property="fechaDesde" styleId="fechaDesde" size="8" 
							style="width:80px; color:black;" styleClass="fechaDDMMYY"/>
						<img id="imageCal1" src='./images/Calendar.png' border='0' 
							style="float:left;position:absolute;cursor:pointer;margin-left:5px;">
					</td>
					<td>Hasta:</td>
					<td style="padding-right:30px;">
						<html:text property="fechaHasta" styleId="fechaHasta" size="8" 
							style="width:80px; color:black;" styleClass="fechaDDMMYY"/>
						<img id="imageCal2" src='./images/Calendar.png' border='0' 
							style="float:left;position:absolute;cursor:pointer;margin-left:5px;">
					</td>
					<td>Monto Desde:</td>
					<td>
						<html:text property="montoDesde" styleId="montoDesde" style="width:110px;" 
							maxlength="16" onkeypress="return keyPressMonto(event, 'montoDesde');"/>
					</td>
					<td>Monto Hasta:</td>
					<td>
						<html:text property="montoHasta" styleId="montoHasta" style="width:110px;" 
							maxlength="16" onkeypress="return keyPressMonto(event, 'montoHasta');"/>
					</td>
				</tr>
				<tr>
					<td></td>
					<td colspan="3">
						<div id="errorFechas" style="color:red;"></div>
					</td>
					<td></td>
					<td colspan="3">
						<div id="errorMonto" style="color:red;"></div>
					</td>
				</tr>
				<tr>
					<td>Usuario:</td>
					<td>
						<html:text property="usuario" styleId="usuario" style="width:110px;text-transform:uppercase;" maxlength="8"/>
					</td>
					<td>Estado</td>
					<td colspan="3"> 
						<html:select property="codEstado" styleId="codEstado">
							<html:option value="">Todos</html:option>
							<html:options collection="ComboEstado" property="id" labelProperty="descripcion" />
						</html:select>
					</td>
				</tr>
				<tr>
					<td colspan="13" style="text-align:right;padding-right:10px;">
					 <html:submit styleClass="buttonFilter" value="Filtrar" />
					 <html:button styleClass="buttonClear" value="Limpiar" property="" onclick="resetForm()" />
					</td>
				</tr>
			</tbody>
		</table>
	</html:form>

	<logic:equal value="t" name="Tabla">
		<div id="paginacion" style="margin-top: 43px;">
			<display:table uid="row" name="CuadroDetallado"
				requestURI="FiltroCuadroDetallado.do" id="RendicionesTable" excludedParams="username password"
				decorator="com.sa.decorator.CuadroDetalladoTableDecorator" pagesize="15"
				style="margin-left:-0.9%;width:99.7%;" export="true">
				<display:column property="id" title="ID" style="width:4%;text-align:center" sortable="true" media="html csv excel" />
				<display:column property="motivo" title="Motivo" style="width:16%" media="html csv excel" />
				<display:column property="descripcion" title="Descripcion" style="width:20%" media="html csv excel" />
				<display:column property="estado" title="Estado" style="width:10%" media="html csv excel" />
				<display:column property="proxUsuario" title="PROX.USUARIO"  style="width:3%;text-align:center;" media="html" />
				<display:column property="fechaUltModif" format="{0,date,dd/MM/yyyy}" title="Fecha.Ult.Modif" style="width:3%;text-align:center;" media="html" />
				<display:column property="importe" title="Importe (*)" style="width:10%;text-align:center;" media="html csv excel"/>
				<display:column property="opciones" title="Opciones" style="width:3%;text-align:center;" media="html" />
				
				<display:setProperty name="export.csv.filename" value="CuadroDetallado.csv"/>
			    <display:setProperty name="export.excel.filename" value="CuadroDetallado.xls"/>
			</display:table>
		</div>
		
		<div style="color:#FF0000;">
			<br/><br/>
			<p>(*) importe rendici&oacute;n estimado</p>
		</div>
	</logic:equal>
	
	<script type="text/javascript" src="./js/cuadroDetallado.js"></script>
</body>