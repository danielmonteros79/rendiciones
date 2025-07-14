
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.*"%>

<html>
<head>
<title>Log-in</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
<link rel="stylesheet" type='text/css'
	href="./css/jquery-ui.structure.css" />
<link rel="stylesheet" type='text/css' href="./css/jquery-ui.theme.css" />

<script type="text/javascript" src="static/js/jquery.js"></script>
<script type="text/javascript" src="static/js/jquery-ui.js"></script>
<script type="text/javascript">
window.onload = function(){
	if(document.getElementById("userIv").value != null){
		window.location.href ="Login.do";
	}
}
</script>
</head>
<body>
<%
	String ivUser = request.getHeader("iv-user");
	request.getSession().setAttribute("ivUser", ivUser);
	 %>
	<input type="hidden" id="userIv" value="<%= request.getSession().getAttribute("ivUser")
		 %>"/>
	<div id="capa_madre">

		<div id="headerTop">
			<div id="fecha">
				<%
					//se formatea la fecha
					java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("EEEE dd MMMM yyyy", new Locale("ES"));
					String fecha = df.format(new java.util.Date());
					String[] v = fecha.split("\\s");
					String fechaForm = v[0] + " " + v[1] + " de " + v[2] + " de " + v[3];
					out.print(fechaForm);
				%>
			</div>
			

		</div>
		<div id="headerCentral">
			<div id="headerLogoProy" align="left">
				<img alt="" src="./images/logoSUMINISTRO.png" align="left" height="80px">
			</div>
			<div id="headerLogoBanco" align="right">
				<img alt="BBVA" src="./images/logoBBVA.png" align="right"> <br>
				<br> <br /> <br /> BBVA Argentina
			</div>
		</div>
		<div id="headerFoot"></div>
		<div id="sidebar">
		
			
		</div>
		
	</div>
</body>
</html>