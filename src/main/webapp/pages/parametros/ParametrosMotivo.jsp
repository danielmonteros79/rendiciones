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
	<table>
		<thead><tr><th>Filtro motivos</th></tr></thead>
		<html:form action="parametrosMotivoFiltro" styleId="parametrosMotivoFiltro">
			<tbody class="filtros">
				<tr>
					<td align="left">
						Motivo
						<html:text property="codigo" styleId="codigo" style="width:60px;" maxlength="4" onkeypress="return numericOnly(event);"/>
						
						<html:submit styleClass="buttonFilter" style="margin-left:10px;" value="Filtrar" />
						<html:button property="" value="Limpiar" styleClass="buttonClear" onclick="resetForm();" />
					</td>
				</tr>
			</tbody>
		</html:form>
	</table>
	
	<html:form action="parametrosMotivoDetalle" styleId="addMotivo">
		<input type="hidden" name="accion" value="alta"/>
		<a href="#" onclick="agregarMotivo()" title="Alta de motivo" style="float:right; margin-right:1.2%;margin-top:3px;">
			<img src="./images/addButtonGoogle.png" alt="Nuevo motivo" height="32" width="32"> 
		</a>
	</html:form>
	
	<div id="paginacion" style="margin-top:43px;">
		<display:table uid="row" name="motivos"
			requestURI="/parametrosMotivoFiltro.do" id="ParametrosMotivoTable" excludedParams="false"
			decorator="com.sa.decorator.parametros.ParametrosMotivoTableDecorator" pagesize="15"
			style="margin-left:-0.9%;width:99.7%;" export="true">
			<display:column media="html csv excel" property="codigo" title="Motivo" style="width:4%" sortable="true" style="text-align:right;" />
			<display:column media="html csv excel" property="descripcion" title="Descripción" />
			<display:column media="html csv excel" property="idGlg" title="GLG" style="text-align:right;" />
			<display:column media="html csv excel" property="idCentroCostos" title="C. Costos" style="text-align:right;" />
			<display:column media="html csv excel" property="codSup" title="Superior" />
			<display:column media="html csv excel" property="codFirma" title="Firma" />
			<display:column media="html csv excel" property="codAprobacionGlg" title="Ctrl. GLG" />
			<display:column media="html csv excel" property="estado" title="Estado" style="text-align:center;" />
			<display:column media="html" property="opciones" title="Opciones" style="width:4%" />
			
			<display:setProperty name="export.csv.filename" value="ListadoParametrosMotivo.csv"/>
		    <display:setProperty name="export.excel.filename" value="ListadoParametrosMotivo.xls"/>
		</display:table>
	</div>
	
	<script type="text/javascript" src="./js/parametrosMotivo.js"></script> 
</body>
</html>