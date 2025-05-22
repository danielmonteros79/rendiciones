<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>

<head>
<link rel="stylesheet" type="text/css" href="./css/buttons.css">
<link rel="stylesheet" type="text/css" href="./css/validation.css">
<link rel="stylesheet" type="text/css" href="./css/main.css">
<link rel="stylesheet" type="text/css" href="./css/rendicionDetalle.css">
<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css">
<link rel='stylesheet' type='text/css' href='./css/displaytag.css' />
<link rel="stylesheet" type='text/css' href="./css/jquery-ui.structure.css" />
<link rel="stylesheet" type='text/css' href="./css/jquery-ui.theme.css" />
<link rel="stylesheet" type="text/css" href="./css/validation.css">

<script type="text/javascript" src="./static/js/jquery.js"></script>
<script type="text/javascript" src="./static/js/jquery-ui.js"></script>
<script type="text/javascript" src="./static/js/jquery.ui.datepicker-es.js"></script>
<script type="text/javascript" src="./static/js/datepicker-settings.js"></script>
<script type="text/javascript" src="./static/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="./static/js/localization/messages_es_AR.js"></script>
<script type="text/javascript" src="./static/js/date-calculator.js"></script>
<script type="text/javascript" src="./static/js/listadoRendiciones.js"></script>
<script type="text/javascript" src="./static/js/descripcionObligatoria.js"></script>
<script type="text/javascript" src="./static/js/popUpDetalleGastos.js"></script>
<script type="text/javascript" src="./static/js/jspdf.min.js"></script>
<script type="text/javascript" src="./static/js/jspdf.plugin.autotable.js"></script>

<style>
body {
	background-color: #c2d5f1;
}

td {
	vertical-align: top;
	text-align: left;
}
</style>
<title>DATOS ADICIONALES DEL GASTO</title>
</head>
<body>
<logic:present name="messageModifTCJP">
	<div>
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
	</div>
</logic:present>
<logic:present name="messageModifLoad">
	<div>
	<% String message = (String) request.getAttribute("messageModifLoad"); 
	if(message.contains("ERROR")){
	%>
	<div id="messageErr"
		style="color: red; font-weight: bold; text-align: center; width: 100%; font-size: 14">
	<%= message %></div>
	<%} else { %><div id="messageOk"
		style="color: green; font-weight: bold; text-align: center; width: 100%; font-size: 14">
	<%= message %></div>
	<%} %>
	</div>
	<div>
		<input type="button" value="Salir"	onClick="window.close();" class="buttonCancel" style="float: right; margin-right: 1%;" />
	</div>
</logic:present>
<logic:notPresent name="messageModifLoad">
<div>
<h2 style="text-align: center;">Datos Adicionales del Gasto</h2>
</div>
<div style="width: 95%; font-size: small; font-weight: bold;"><html:form
	action="descripcionObligatoriaPopupSend"
	styleId="DescripcionObligatoriaForm">
	<html:hidden property="idRendicion" styleId="idRendicion" />
	<html:hidden property="codDetOblig" styleId="codDetOblig" />
	<html:hidden property="idGasto" styleId="idGasto" />
	<html:hidden property="codGasto" styleId="codGasto"></html:hidden>
	<html:hidden property="tipoEntrada" styleId="tipoEntrada"></html:hidden>

	<table style="width: 98%; border: 0;">
		<% List<DatosPantallaDinamica> fieldsScreen = (List<DatosPantallaDinamica>) request.getAttribute("listCampos");
			   for (DatosPantallaDinamica campo : fieldsScreen) {%>
		<tr>
			<logic:equal value="2" name="tipoEntrada">

				<td style="text-align: right; width: 1%; white-space: nowrap;">
				<label id="camposForm"><%=campo.getTituloCampo()%><%=campo.getCampoObligatorio().equals("S") ? " (*)" : "" %>:</label>
				</td>
				<td>
				<% if (campo.getTipoCampo().contains("TXT250")) { %> <textarea
					name="<%=campo.getTipoCampo()%>" rows="4" cols="50" maxlength="250"
					<%=campo.getCampoObligatorio().equals("S") ? "class=Required" : "" %>></textarea>
				<% } else if (campo.getTipoCampo().contains("TXT")) { %> <input
					type="text" name="<%=campo.getTipoCampo()%>" value=""
					maxlength="50"
					<%=campo.getCampoObligatorio().equals("S") ? "class=Required" : "" %> />
				<% } else if (campo.getTipoCampo().contains("NUM")) { %> <input
					type="text" name="<%=campo.getTipoCampo()%>" value=""
					maxlength="9" onkeypress='return numericOnly(event);'
					<%=campo.getCampoObligatorio().equals("S") ? "class=Required" : "" %> />
				<% } else if (campo.getTipoCampo().contains("FEC")) { %> <input
					type="text" styleClass="fechaDDMMYY" id="<%=campo.getTipoCampo()%>"
					name="<%=campo.getTipoCampo()%>" value=""
					<%=campo.getCampoObligatorio().equals("S") ? "class=Required" : "" %> />
				<a href='#' onClick="showCalendar('<%=campo.getTipoCampo()%>')">
				<img src='./images/Calendar.png' border='0'
					style="margin-bottom: -7px;"> </a> <% } else if( campo.getTipoCampo().contains("COD")) { %>
				<select name="<%=campo.getTipoCampo()%>"
					<%=campo.getCampoObligatorio().equals("S") ? "class=Required" : "" %>>
					<option value=""></option>
					<% for (ComboGenerico opcionCombo : campo.getOpcionesCombo()) { %>
					<option value="<%=opcionCombo.getId()%>"><%=opcionCombo.getDescripcion()%></option>
					<% } %>
				</select> <% } %> <% if (campo.getTipoCampo().contains("TXT250")) { %> <label
					id="<%=campo.getTipoCampo()%>-error" class="error"
					for="<%=campo.getTipoCampo()%>"></label> <% } else { %> <label
					id="<%=campo.getTipoCampo()%>-error" class="error"
					for="<%=campo.getTipoCampo()%>" style="display: inline;"></label> <% } %>
				</td>
			</logic:equal>
		</tr>
		<% } %>
	</table>
	
	<Label>Cantidad: </Label>
	<bean:size id="cant" name="filas"/>
	<bean:write name="cant" />
	
	<logic:equal value="2" name="tipoEntrada">
		<input type="button" value="Salir" onClick="Exit()" class="buttonCancel" style="float: right; margin-right: 1%;" />
		<html:link href="#" styleClass="buttonSave" style="float:right; margin-right:1.5%;" onclick="guardarDescripcion();">Guardar</html:link>
	</logic:equal>
	<logic:equal value="1" name="tipoEntrada">
		<input type="button" value="Salir" onClick="window.close();" class="buttonCancel" style="float: right; margin-right: 1%;" />
	</logic:equal>
	<html:hidden property="codMotivo" styleId="codMotivo"/>
	<html:hidden property="estadoRend" styleId="estadoRend"/>
</html:form>

<table id="tableDescripciones" style="width: 98%;">
	<thead>
		<tr>
			<th>NRO</th>
			<% List<String> headers = (List<String>) request.getAttribute("headers");
					   for (String header : headers) {%>
			<th><%=header%></th>
			<% } %>
		</tr>
	</thead>
	<tbody>
		<% List<List<String>> filas = (List<List<String>>) request.getAttribute("filas"); 
		   int i = 0;
		   for (List<String> fila : filas) { %>
		<tr>
			<td><%=i + 1%></td>
			<% int j = 0;
			   for (String col : fila) { %>
			<td><%=col%></td>
			<% j++; } %>
		</tr>
		<% i++; } %>
	</tbody>
</table>
<h5>
	<a href="#" onclick="exportPDF()" style="color:black;float:right;margin-right:5px;">Exportar PDF</a>
</h5>
</div>

<script>

	function guardarDescripcion() {
		if(Validar()) {
			document.forms[0].action = document.forms[0].action + "?rnd="
					+ $('#idRendicion').val() + "&rndg=" + $('#idGasto').val()
					+ "&codGasto=" + $('#codGasto').val() + "&codObserv="
					+ $('#codDetOblig').val() + "&tipoEntrada="
					+ $('#tipoEntrada').val();
			document.getElementById("DescripcionObligatoriaForm").submit();
		}else {
			alert("Debe ingresar los datos obligatorios");
		}
	}
	
	function Validar() {
			var response = true;
			$(".Required").each(function() {
				if($(this).val() == "") {
					response = false;
				}
			}
		);
			
		return response;
	}
	//rnd=55&rndg=1&codGasto=64&codObserv=00001&tipoEntrada=2
	//rnd=55&rndg=1&codGasto=64&codObserv=1&tipoEntrada=2
</script>
<script type="text/javascript">
	function showCalendar(id) {
		$("#" + id).datepicker();
		$("#" + id).datepicker("show");
	}
</script>
<script>
	function OnClose() {
		if (window.opener != null && !window.opener.closed) {
			window.opener.HideModalDiv();

			//	    	        window.close();

		}
	}
	window.onunload = OnClose;
</script>
<script>
	function functionSalir() {
		window.opener.location.href = "mostrarDetalleGastos.do?codigo="
				+ $('#idRendicion').val() + "&codMotivo="
				+ $('#codMotivo').val() + "&estadoRend="
				+ $('#estadoRend').val();
		window.close();
	};
	function Exit() {
		window.close();window.opener.location.reload();
	};
	
</script>
</logic:notPresent>
<script>
	function numericOnly(e){
		var code = e.charCode || e.keyCode;
        // Allow: backspace, tab, enter
        if (e.keyCode == 46 && e.charCode == 0)
        	return;
        else if ($.inArray(code, [8, 9, 13]) !== -1 || 
             // Allow: home, end, left, right, down, up
            (code >= 35 && code <= 40)) {
                 // let it happen, don't do anything
                 return;
        }
        // Ensure that it is a number and stop the keypress
        if (!(code >= 48 && code <= 57)){
            return false;
        }
	}
</script>
</body>