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
		<thead><tr><th colspan="3">Filtro gastos</th></tr></thead>
		<html:form action="/parametrosGastosFiltro" styleId="parametrosGastosFiltro">
			<tbody class="filtros">
				<tr>
					<td align="left" style="width: 50%; float: left;">
						Gasto
						<html:text property="gasto" styleId="gasto" style="width:60px" maxlength="4" onkeypress="return numericOnly(event);"/>
						
						<html:submit style="margin-left:10px" styleClass="buttonFilter" value="Filtrar" />
						<html:button style="margin-rigth:4.1%" property="" value="Limpiar" styleClass="buttonClear" onclick="resetForm();"></html:button>
					</td>
				</tr>
			</tbody>
		</html:form>
	</table>
	
	<html:form action="parametrosGastosDetalle" styleId="addGasto">
		<input type="hidden" name="accion" value="alta"/>
		<input type="hidden" name="back" value="false"/>
		<a href="#" onclick="agregarGasto()" style="float:right; margin-right:1.2%;margin-top:3px;">
			<img src="./images/addButtonGoogle.png" alt="Nuevo gasto" height="32" width="32"> 
		</a>
	</html:form>
	
	<div id="paginacion" style="margin-top:43px;">
		<display:table uid="row" name="gastos"
			requestURI="parametrosGastosFiltro.do" id="ParametrosGastosTable" excludedParams="false"
			decorator="com.sa.decorator.parametros.ParametrosGastosTableDecorator" pagesize="15"
			style="margin-left:-0.9%;width:99.7%;" export="true">
			<display:column media="html csv excel" property="gasto" title="Gasto" style="text-align:right;" sortable="true" />
			<display:column media="html csv excel" property="descripcionGasto" title="Descripción de Gasto" />
			<display:column media="html csv excel" property="motivo" title="Motivo" style="text-align:right;" sortable="true" />
			<display:column media="html csv excel" property="descripcionMotivo" title="Descripción de Motivo" />
			<display:column media="html csv excel" property="ristra" title="Ristra" style="text-align:left;" />
			<display:column media="html csv excel" property="bimon" title="Bimon" style="text-align:center;" />
			<display:column media="html csv excel" property="comprob" title="Comprob" />
			<display:column media="html csv excel" property="autoriz" title="Autoriz" />
			<display:column media="html csv excel" property="observ" title="Observación" />
			<display:column media="html csv excel" property="estado" title="Estado" style="text-align:center;" />
			<display:column media="html" property="opciones" title="Opciones" style="width:4%" />
			
			<display:setProperty name="export.excel.filename" value="ListadoParametrosGastos.xls"/>
			<display:setProperty name="export.csv.filename" value="ListadoParametrosGastos.csv"/>
		</display:table>
	</div>
	
	<script type="text/javascript" src="./js/parametrosGastos.js"></script>
</body>
</html>