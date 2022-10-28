<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.*"%>

<html>
<head>
	<title>Error</title>
	<link rel="stylesheet" type="text/css" href="css/main.css">
	<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
	<link rel="stylesheet" type='text/css' href="./css/jquery-ui.structure.css" />
	<link rel="stylesheet" type='text/css' href="./css/jquery-ui.theme.css" />
</head>
<body>
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
			<div id="cuerpo_central_container">
				<div id="cuerpo_central">
					<div id="cuerpo_central_content">
						<div id="cuerpo_central_top">
							<h2>Error</h2>
						</div>
						<div id="cuerpo_central_content">
							<%
								List errores = (List) request.getAttribute("errores");
								if (errores != null && errores.size() > 0) {
									out.println("<div>");
									for (int i = 0; i < errores.size(); i++) {
										String e = (String) errores.get(i);
										out.println("<p class=\"msgError\">" + e);
									}
									out.println("</div>");
								}
							%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>