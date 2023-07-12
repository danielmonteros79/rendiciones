<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.Locale"%>
<%@page import="com.sa.entities.Usuario"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Motivo de suspension</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
<link rel='stylesheet' type='text/css'
	href='./css/displaytag.css' />
<link rel="stylesheet" type='text/css'
	href="./css/jquery-ui.structure.css" />
<link rel="stylesheet" type='text/css' href="./css/jquery-ui.theme.css" />
<link rel="stylesheet" type='text/css' href="./css/buttons.css" />


<script type="text/javascript" src="static/js/jquery.js"></script>
<script type="text/javascript" src="static/js/jquery-ui.js"></script>
<script type="text/javascript" src="static/js/jquery.ui.datepicker-es.js"></script>
<script type="text/javascript" src="static/js/datepicker-settings.js"></script>
<script type="text/javascript" src="static/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="./static/js/popUpsOpen.js"></script>
<script type="text/javascript" src="./static/js/onClose.js"></script>
<script type="text/javascript" src="./static/js/validarRechazo.js"></script>
<style>
body {
	background-color: #c2d5f1;
}
</style>
</head>
<body>
<logic:present name="messageModifTCJP">
	<% String message = (String) request.getAttribute("messageModifTCJP"); 
	if(message.contains("ERROR")){
	%>
	<div id="messageErr"
		style="color: red; font-weight: bold; text-align: center; width: 100%; font-size: 14">
	<%= message %></div>
	<%} else { %><div id="messageOk"
		style="color: green; font-weight: bold; text-align: center; width: 100%; font-size: 14">
	<%= message %></div>
	<%} %>
		<html:form action="aprobacionDetalle" styleId="RendicionForm">
			<html:hidden property="glg" styleId="glg"/>
	</html:form>
	<input type="button" value="Salir" onClick="salir()"
		class="buttonCancel" style="float: right; margin-right: 1%;" />
</logic:present>
<logic:notPresent name="messageModifTCJP">
	<html:form action="aprobacionDetalle" styleId="RendicionForm">
		<html:hidden property="idRendicion" styleId="idRendicion" />
		<html:hidden property="estado" styleId="estado"/>
			
	</html:form>
	<html:form action="gestionarCierre" styleId="RechazarRendicionForm"	method="post" onsubmit="redirect(window.opener);">
		<table>
			<tbody>
				<tr>
					<td style="width:130px;"> 
						<label for="motivo" style="font-weight:bold">Motivo de suspensi&oacute;n:</label> 
			 		</td>
			 		<td>
						<html:select property="cmboMotivo" styleId="cmboMotivo" style="width:280px; color:black;">
						<html:options collection="ComboMotivo" property="id" labelProperty="descripcion" />
						</html:select>				
					</td>
				</tr>
				<tr>
					<td>
						<label for="motivo" style="font-weight:bold;" >Descripci&oacute;n:</label>
					</td>
					<td>
						<html:textarea 
							 property="motivoRechazo" styleId="motivoRechazo"style="width:280px;height:80px;" value="">
						</html:textarea>
					</td>
				</tr>	
			</tbody>
		</table>
		<div style="aling-center">			
			<html:link href="#" styleClass="buttonCancel" style="float:center; margin-left:150px;" onclick="suspender()">Suspender
			</html:link>
			<input type="button" value="Salir" onClick=salir(); class="buttonCancel" style="float:center; margin-rigth: 1%;" /> 	
		</div>	
			<p style="color: red; text-align: center; font-size: small; display: none;" id="mensaje">Por favor complete el campo con una descripcion</p>
		</html:form>
</logic:notPresent>

	<script>
		function suspender() {
			
				if (confirm("¿Seguro que desea suspender la rendición?")) {
					document.forms[1].action = document.forms[1].action + location.search;
					document.getElementById("RechazarRendicionForm").submit();
					document.getElementById("RechazarRendicionForm").reset();
// 					window.opener.location.reload();
// 					window.close();
					return false;
				}
			}
		function salir(){
			window.opener.location.href='cierre.do';
		}
</script>
</body>
</html>