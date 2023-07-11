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
	
	<script type="text/javascript" src="static/js/main.js"></script>
	<script type="text/javascript" src="static/js/jquery.js"></script>
	<script type="text/javascript" src="static/js/jquery-ajax-native.js"></script>
	
	<title>Archivos adjuntados</title>
</head>
<body style="padding:20px;">
	<logic:present name="msg">
		<% String message = (String) request.getAttribute("msg");
		
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
	
	<div>
		<html:form action="adjuntarImagenPopUp" styleId="rendicionAvisoForm" method="POST">
			<div id="divBackground"></div>
			<input type="hidden" name="accion" value="caratula">
			<html:hidden property="rendicion.id" styleId="idRendicion"/>
			<div style="text-align:center;">
				<input type="image" style="width:60px;padding:30px;" src='./images/iconos/pdf_2.png' value="GENERAR"/>
				<br>
				<input type="button" onclick="salir()" class="buttonContinue" value="SALIR"/>
			</div>
		</html:form>
	</div>
	
	<script>
		$(function() {
		    $("#rendicionAvisoForm").submit(function(e) {
		    	LoadModalDiv();
		        e.preventDefault();
		        var actionurl = e.currentTarget.action;
		        
		        $.ajax({
	                url: actionurl,
	                type: 'post',
	                data: $("#rendicionAvisoForm").serialize(),
	                dataType: 'native',
	                xhrFields: {
                    	responseType: 'blob'
                  	},
	                success: function(blob) {
	    				window.opener.location.href = "listadoRendiciones.do";
	    				
	                	var link = window.opener.document.createElement('a');
	                    link.href = window.opener.URL.createObjectURL(blob);
	                    link.download = "caratulaRendicion_" + $("#idRendicion").val() + ".pdf";
	                    window.opener.document.body.appendChild(link);
	                    link.click();
	                    salir();
	                }, error: function(XMLHttpRequest, textStatus, errorThrown) {
	                    console.log("Error: " + errorThrown);
	    				HideModalDiv();
	                }
		        });
		    });
		});
	
		function salir() {
			if (window.opener != null && !window.opener.closed) {
				window.opener.location.href = "listadoRendiciones.do";
				window.close();
				HideModalDiv();
			}
		};
		window.onunload = salir;
	</script>
</body>