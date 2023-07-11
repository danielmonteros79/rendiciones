
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
	
	<title>Journal</title>
	
	<link rel="stylesheet" type="text/css" href="./css/main.css">
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css">
	<link rel='stylesheet' type='text/css' href='./css/displaytag.css' />
	<link rel="stylesheet" type='text/css' href="./css/jquery-ui.structure.css" />
	<link rel="stylesheet" type='text/css' href="./css/jquery-ui.theme.css" />
	<link rel="stylesheet" type='text/css' href="./css/buttons.css" />
	<link rel="stylesheet" type="text/css" href="./css/displayTagSort.css">
	
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery-ui.js"></script>
	<script type="text/javascript" src="js/jquery.ui.datepicker-es.js"></script>
	<script type="text/javascript" src="js/datepicker-settings.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
	
	<style type="text/css">
		#paginado {
			color: black!important;
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
	<logic:notPresent name="message">
		<div align="center"><h1>Consulta de Journal</h1></div>
		
		<html:form action="ShowJournal">
			<label for="journal" style="font-weight:bold; margin-left:10px;">ID-Rendicion:</label>
			<html:text styleClass="green" property="idRendicion" styleId="idRendicion" readonly="true" />
		</html:form>
		
		<div id="paginacion" style="margin-top:15px;">
			<display:table uid="row" name="journal" requestURI="ShowJournal.do" id="JournalTable" excludedParams="false"
				decorator="com.sa.decorator.JournalTableDecorator" pagesize="15" style="margin-left:-0.9%;width:99.7%;" export="true">
				<display:column media="html csv excel" property="numeroAprob" title="Numero de Orden" style="text-align:right;" sortable="true" />
				<display:column media="html csv excel" property="estado" title="Estado" />
				<display:column media="html csv excel" property="nombreUsuarioProx" title="Proximo Usuario" />
				<display:column media="html csv excel" property="nombreUsuarioAprob" title="Usuario Aprobador" />
				<display:column media="html csv excel" property="fechaApr" title="Fecha Aprobacion" />
				
				<display:setProperty name="export.excel.filename" value="ListadoJournal.xls"/>
				<display:setProperty name="export.csv.filename" value="ListadoJournal.csv"/>
			</display:table>
		</div>
	</logic:notPresent>
	
	<div style="margin-top:50px;">
		<input type="button" value="Salir" onClick="salir()"class="buttonCancel"/>
	</div>
	
	<script>
		function salir(){
			window.close();
		}
	</script>
</body>
</html>