<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
<link rel="stylesheet" type='text/css' href="./css/jquery-ui.structure.css" />
<link rel="stylesheet" type='text/css' href="./css/jquery-ui.theme.css" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<% if (!(request.getServerName().contains("was80desa1") || request.getServerName().contains("ven-ctx-xapp098"))) { 
	String ivUser = request.getHeader("iv-user");
	request.getSession().setAttribute("ivUser", ivUser); 
%>
	<script type="text/javascript">
		window.onload = function() {
			window.location.href ="Login.do";
		}
	</script>
<% } %>
</head>
<body>	
	<% if (request.getServerName().contains("was80desa1") || request.getServerName().contains("ven-ctx-xapp098")) { %>
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
							<h2>Ingreso al sistema</h2>
						</div>
						<div id="cuerpo_central_content">
							<table>
								<col width="20%">
								<col width="50%">
									<html:form action="/Login.do" >
										<tr>
											<td align="right">Usuario :</td>
											<% if (request.getAttribute("ivUser") == null) { %>
											<td align="left">
												<html:text property="username" style="text-transform:uppercase"/>
											</td>
											<% } else { %>
											<td>
												<html:text property="username"
													value='<%=request.getAttribute("ivUser").toString().toUpperCase()%>'
													readonly="true"/>
											</td>
											<% } %>
										</tr>
										<tr>
											<td align="right">Password :</td>
											<td align="left">
												<html:password property="password"/>
											</td>
										</tr>
										<tr>
											<logic:present name="errorLogin">
												<td class="error" colspan="2">
													<bean:write name="errorLogin" />
												</td>
											</logic:present>
											<td colspan="2" class="error">
												<html:errors />
											</td>
										</tr>
										<tr>
											<td colspan="2" align="center" >
												<html:submit value="Ingresar" styleClass="buttonContinue" style="margin-right:17%"/>
											</td>
										</tr>
									</html:form>
								</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<% } %>
</body>
</html>