<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
	
</logic:present>
<script>
function redirect(){
	window.location.href="listadoRendiciones.do";
};
jQuery(document).ready(function() {

	$('#checkCierre').show();
	$("#opcionesCierre").show();
});
</script>
</body>
</html>