
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.*"%>

<html>
<head>
<title>Log-in</title>

<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/bootstrap-float-label.css">
<link rel="stylesheet" type="text/css" href="css/bootstrap-datepicker.min.css">
<link rel="stylesheet" type="text/css" href="css/font-awesome.css">
<link rel="stylesheet" type="text/css" href="css/style-icons.css">

<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
<link rel="stylesheet" type='text/css'
	href="./css/jquery-ui.structure.css" />
<link rel="stylesheet" type='text/css' href="./css/jquery-ui.theme.css" />

<script type="text/javascript" src="static/js/jquery.js"></script>
<script type="text/javascript" src="static/js/jquery-ui.js"></script>
<script type="text/javascript">

	

</script>
</head>
<%
	String ivUser = request.getHeader("iv-user");
	request.getSession().setAttribute("ivUser", ivUser);
	 %>
	 
<body>

<div id="userIv">
	 
	 <%request.getSession().getAttribute("ivUser");
	 %>
	 </div>
	<div id="capa_madre">

		<div id="headerTop" class="container py-4">
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
		<div id="headerCentral" class="container">
			<div id="headerLogoProy" align="left" class="container">
				<img alt="" src="./images/logoSUMINISTRO.png" align="left"
					height="80px" height="100px">

			</div>
			<div id="headerLogoBanco" align="right" class="container">
				<img alt="BBVA" src="./images/logoBBVA.png" align="right"> <br>
				<br> <br /> <br /> BBVA Argentina
			</div>
		
		</div>
		<div id="headerFoot"></div>
		<div id="sidebar">
		
			
			<div id="cuerpo_central_container" class="container mt-5">
				<div id="cuerpo_central">
					
					<div id="cuerpo_central_content">
						<div id="cuerpo_central_top">
							<h2>Ingreso al sistema</h2>
						</div>
						<div id="cuerpo_central_content" align="left" class="pt-4" >
							
								<table>
									<col width="20%">
									<col width="50%">
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
									<html:form action="/Login.do" >
										<tr>
											<td align="left">Usuario :</td>
											<%
												if (request.getAttribute("ivUser") == null) {
											%>
											<td  class="text-uppercase form-control"><html:text property="username" style="text-transform:uppercase"></html:text></td>
											<%
												} else {
											%>
											<td><html:text property="username"
													value='<%=request.getAttribute("ivUser").toString().toUpperCase()%>'
													readonly="true"></html:text></td>
											<%
												}
											%>
										</tr>
										<tr>
											<td align="left" >Password :</td>
											<td  class="text-uppercase form-control" align="left"><html:password property="password"></html:password></td>
										</tr>
										<tr>
											<logic:present name="errorLogin">
												<td class="error" colspan="2"><bean:write
														name="errorLogin" /></td>
											</logic:present>
											<td colspan="2" class="error"><html:errors /></td>
										</tr>
										<tr>
											
											<td colspan="2" align="center" ><html:submit value="Ingresar" styleClass="btn btn-primary px-3 py-1 my-3" style="margin-right:17%"></html:submit></td>
										</tr>
									</html:form>
								</table>
							
						</div>
					</div>
				</div>
			</div>
		</div>
		
	</div>
</body>
</html>