<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.apache.commons.text.StringEscapeUtils" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.Usuario"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Cierre Tarjeta</title>

<link rel="stylesheet" type="text/css" href="./css/main.css">
<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css">
<link rel='stylesheet' type='text/css' href='./css/displaytag.css' />
<link rel="stylesheet" type='text/css'
	href="./css/jquery-ui.structure.css" />
<link rel="stylesheet" type='text/css' href="./css/jquery-ui.theme.css" />
<link rel="stylesheet" type='text/css' href="./css/buttons.css" />
<link rel="stylesheet" type="text/css" href="./css/displayTagSort.css">

<script type="text/javascript" src="static/js/jquery.js"></script>
<script type="text/javascript" src="static/js/jquery-ui.js"></script>
<script type="text/javascript" src="static/js/jquery.ui.datepicker-es.js"></script>
<script type="text/javascript" src="static/js/datepicker-settings.js"></script>
<script type="text/javascript" src="static/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="static/js/cierreTarjeta.js"></script>

<style type="text/css">
#paginado {
	color: black !important;
}

body {
	background-color: #c2d5f1;
}

.green {
	color: #027362;
	border-style: none;
	background-color: #c2d5f1;
	font-weight: bold;
}
</style>
</head>
<body>
	<%-- 	<logic:present name="message"> --%>
	<%-- 		<% String message = (String) request.getAttribute("message"); --%>

	<%-- 		if(message.contains("ERROR")) { %> --%>
	<!-- 			<div id="messageErr" class="message"> -->
	<%-- 				<%= message.substring(7) %> --%>
	<!-- 			</div> -->
	<%-- 		<%} else if(message.contains("OK")) { %> --%>
	<!-- 			<div id="messageOk" class="message"> -->
	<%-- 				<%= message.substring(4) %> --%>
	<!-- 			</div> -->
	<%-- 		<%} else {%> --%>
	<!-- 			<div id="messageAviso" class="message"> -->
	<%-- 				AVISO: <%= message %> --%>
	<!-- 			</div> -->
	<%-- 		<%}%> --%>
	<%-- 	</logic:present> --%>
	<table style="margin-bottom: 10px;">
		<html:form action="SaveShowCierreTarj" styleId="CierreTarjetaSaveForm">
			<%
		    String resumenes = StringEscapeUtils.escapeHtml4(request.getParameter("idSecResumen"));
		    String totalPes = StringEscapeUtils.escapeHtml4(request.getParameter("totalPes"));
		    String totalDol = StringEscapeUtils.escapeHtml4(request.getParameter("totalDol"));
		    String codUsr = StringEscapeUtils.escapeHtml4(request.getParameter("codUsr"));
			%>
			<html:hidden property="idSecResumen" styleId="idSecResumen" value="<%=resumenes%>" />
			<html:hidden property="totalPes" styleId="totalPes"	value="<%=totalPes%>" />
			<html:hidden property="totalDol" styleId="totalDol"	value="<%=totalDol%>" />
			<html:hidden property="codUsr" styleId="codUsr" value="<%=codUsr%>" />
			<tbody class="filtro" style="font-size: small; font-weight: bold;">
				<tr>
					<td style="width: 50%;">Motivo <html:select property="motivo"
							styleId="motivo" style="width:70%;" onchange="selectMotivo();">
							<html:option value=""></html:option>
							<html:options collection="comboMotivo" property="id"
								labelProperty="descripcion" />
						</html:select> <a id="ayudaMotivo" target="_blank"> <img width="15px"
							src='./images/iconos/question-mark-2-48.png'
							style="display: none" alt='Ayuda' title="Ayuda" border='0'
							style="margin-left: 5px;" align="top" id="imagen" />
					</a>
					</td>
					<td style="width: 50%;">Gasto <html:select property="gasto"
							styleId="gasto" style="width:70%;">
							<html:option value=""></html:option>
							<%-- 							<html:options collection="comboGastos" property="id" labelProperty="descripcion" /> --%>
						</html:select> <!-- 						<a id="ayudaMotivo"  target="_blank"> --> <!-- 							<img width="15px" src='./images/iconos/question-mark-2-48.png' style="display:none" alt='Ayuda' title="Ayuda" -->
						<!-- 								border='0' style="margin-left: 5px;" align="top" id="imagen" /> -->
						<!-- 						</a> -->
					</td>
				</tr>
			</tbody>
		</html:form>
	</table>
	<div style="margin-top: 50px;">
		<input type="button" value="Salir" onClick="salir()"
			class="buttonCancel" /> <input onclick="submit()"
			Class="buttonAprobar" type="button" value="Guardar" />
	</div>

	<script>
		function submit() {
			if($('#motivo').val() == ""){
				alert("debe seleccionar un motivo");
				return;
			}else{if($('#gasto').val() == ""){
				alert("Debe seleccionar un gasto");
				return;}else{
					$.ajax({
						url : "SaveShowCierreTarj.do",
						type : "POST",
						data : "idRes=" + $('#idSecResumen').val() 
							+ "&mot=" + $('#motivo').val() 
							+ "&gast=" + $('#gasto').val()
							+ "&totpes=" + $('#totalPes').val() 
							+ "&totdol=" + $('#totalDol').val() 
							+ "&codusr=" + $('#codUsr').val(),
						dataType : "json",
						success : function(data) {
							console.log(data);
							if (data.message == 'ALTA EFECTUADA') {
								window.opener.location.href = "CierreTarjeta.do";
								window.close();
							} else
								alert(data.message);
						},
						error : function(data) {
							console.log(data);
						}
					});
					}
			}
			// 		document.forms[0].action = document.forms[0].action + "?idRes="
			// 		+ $('#idSecResumen').val() + "&mot=" + $('#motivo').val()
			// 		+ "&gast=" + $('#gasto').val();
			// 		alert($('#idSecResumen').val());
			// 		alert($('#motivo').val());
			// 		alert($('#gasto').val());

			// 		document.getElementById("CierreTarjetaSaveForm").submit();
			// 		window.close();
		}

		function salir() {
			window.close();
		}
	</script>
</body>
</html>