<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<script type="text/javascript">
		jQuery(document).ready(function() {
			$('#checkReemplazar').show();
			var msgAviso = $("#msgAviso").val();
			if (msgAviso != "") {
				alert(msgAviso);
			}
		});
		
		function eliminar(delegacion) {
			if (confirm("¿Realmente desea dar de baja la delegación?")) {
				$("#delete_" + delegacion).submit();
			}
		}
	</script>
	<style type="text/css">
		#paginado {
			color: black !important;
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
	
	<logic:present name="msgAviso">
		<input type="hidden" id="msgAviso" value="<bean:write name='msgAviso'/>" />
	</logic:present>
	<logic:notPresent name="msgAviso">
		<input type="hidden" id="msgAviso" value="" />
	</logic:notPresent>
	
	<table>
		<% Usuario user = (Usuario) request.getSession().getAttribute("usuario"); %>
		<tbody class="filtros">
			<tr>
				<td align="left" class="fieldTable">Usuario: <%=user.getNombre()%></td>
				<td align="left" class="fieldTable">C.Costos: <%=user.getCcostos()%></td>
				<td align="left" class="fieldTable">Sector: <%=user.getSector()%></td>
			</tr>
		</tbody>
	</table>
	
	<html:link action="relacionUsuarioDelegadoAlta.do?optn=A" title="Alta de delegado" style="float:right; margin-right:1.2%;margin-top:2px;">
		<img src="./images/addButtonGoogle.png" alt="Nuevo delegado" height="32px" width="32px">
	</html:link>
	
	<display:table uid="row" name="ParametriaUsuarioDelegado" requestURI="relacionUsuarioDelegado.do" id="usuarioDelegadoTbl"
		decorator="com.sa.decorator.parametros.RelacionUsuarioDelegadoTableDecorator" excludedParams="id" pagesize="20"
		style="color:black;margin-top:20px;" export="true">
		<display:column property="id" title="Id User" media="csv excel" />
		<display:column property="delegadoNombre" title="Usuario" style="width:20%" sortable="true" media="html csv excel" />
		<display:column property="delegadoCentroCostos" title="C.Costos" style="width:5%" media="html csv excel" />
		<display:column property="delegadoSector" title="Sector" style="width:5%" media="html csv excel" />
		<display:column property="delegadoInforme" title="Informe" style="width:2%" media="html csv excel" />
		<display:column property="delegadoAccionDesc" title="Accion" style="width:12%" sortable="false" media="html csv excel" />
		<display:column property="delegadoEstado" title="Estado" style="width:2%" sortable="false" media="html csv excel" />
		<display:column property="feDesde" title="Desde" style="width:7%" format="{0,date,dd-MM-yyyy}" media="html csv excel" />
		<display:column property="feHasta" title="Hasta" style="width:7%" format="{0,date,dd-MM-yyyy}" sortable="false" media="html csv excel" />
		<display:column property="opciones" title="Opciones" style="width:4%;" media="html" />
	
		<display:setProperty name="export.csv.filename" value="RelacionUsuarioDelegado.csv" />
		<display:setProperty name="export.excel.filename" value="RelacionUsuarioDelegado.xls" />
	</display:table>
</body>
</html>