<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.Locale"%>
<%@page import="com.sa.entities.Usuario"%>
<%@page import="com.sa.entities.TipoPerfil"%>

<html>
<head>
	<title><tiles:getAsString name="pageTitle" /></title>
	
	<link rel="stylesheet" type="text/css" href="css/main_bkp.css">
	<link rel="stylesheet" type="text/css" href="css/rendicionDetalle.css">
	<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
	<link rel='stylesheet' type='text/css' href='./css/displaytag.css' />
	<link rel="stylesheet" type='text/css' href="./css/jquery-ui.structure.css" />
	<link rel="stylesheet" type='text/css' href="./css/jquery-ui.theme.css" />
	<link rel="stylesheet" type="text/css" href="./css/displayTagSort.css">
	
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery-ui.js"></script>
	<script type="text/javascript" src="js/jquery.ui.datepicker-es.js"></script>
	<script type="text/javascript" src="js/jquery.balloon.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/datepicker-settings.js"></script>
	<script type="text/javascript" src="js/date-calculator.js"></script>
	<script type="text/javascript" src="js/main_bkp.js"></script>
	
	<% 
		Usuario userSession = (Usuario) request.getSession().getAttribute("usuario");
		Usuario userWorking = (Usuario) request.getSession().getAttribute("userWorking");
		
		if (userSession == null) { 
	%>
		<logic:present name="errorTimeOut">
			<script type="text/javascript">
				window.onload = function () {
					alert("Finaliz\u00f3 el tiempo de sesi\u00f3n. (TimeOut)");
					window.location.href = "login.jsp";
				}
			</script>
		</logic:present>
	<% } %>
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
			<logic:notPresent name="errorTimeOut">
				<div id="user">Usuario:</div>
				<div id="userCuenta">
					<%= userSession != null ? userSession.getIdUser() : ""%>
				</div>
			</logic:notPresent>
		</div>
		
		<div id="headerCentral">
			<div id="headerLogoProy" align="left">
				<img alt="" src="./images/logoSUMINISTRO.png" align="left" height="80px">
			</div>
			<div id="headerLogoBanco" align="right">
				<img alt="BBVA" src="./images/logoBBVA.png" align="right">
				<br><br><br><br>
				BBVA Argentina
			</div>
		</div>			
		
		<div id="headerFoot">
			<%  if(userSession != null) { %>
				<div id="user-working">Rendiciones de: < <%= userWorking.getNombre() %> > </div>	
			<% } %>		
			
			<div id="logout">
				<a href="logout.do" title="Cerrar sesi&oacute;n">
					<img alt="Cerrar Sesion" style="cursor: pointer;" src="./images/btnLogout.png" align="right" />
				</a>
			</div>
		</div>
		
		<div id="cuerpo_central_container">
			<div id="sidebar">
				<div id="sidebarMenu">
					<div id="cuerpo_central">
						<div id="cuerpo_central_top">
							<h2>
								<logic:notPresent name="opcionEstado">
									<tiles:getAsString name="tituloCuerpo" />
								</logic:notPresent>
								<logic:present name="opcionEstado">
									<logic:equal value="1" name="opcionEstado">Aprobaci&oacute;n Supervisor</logic:equal>	
									<logic:equal value="2" name="opcionEstado">Aprobaci&oacute;n Firma</logic:equal>						
									<logic:equal value="3" name="opcionEstado">Aprobaci&oacute;n GLG</logic:equal>
									<logic:equal value="4" name="opcionEstado">Aprobaci&oacute;n GLG (Entrada)</logic:equal>						
								</logic:present>
							   	<a href='http://intranetprod.arg.igrupobbva/operativas/datos/gr_rendiciones_frec_ques.pdf'
							  		 id="BotonPreguntas" style="display: none; position: absolute; z-index: 999; top: 5px; right: 50px;"
							  		 target="_blank">
							  		<img id="preguntasFrecuentes" src='./images/iconos/solutions-48.png'
							  			title="PREGUNTAS FRECUENTES" border='0' width="34" height="34">
							  	</a>
							   	<a href='http://intranetprod.arg.igrupobbva/operativas/datos/gr_rendiciones_de_gastos.pdf'
							  		 id="BotonManual" style="display: none; position: absolute; z-index: 999; top: 5px; right: 5px;"
							  		 target="_blank">
							  		<img id="manualGuiasRapidas" src='./images/iconos/address-book-48.png'
							  			title="MANUAL - GUIAS RAPIDAS" border='0' width="34" height="34">
							  	</a>
							</h2>
						</div>
						<div id="cuerpo_central_content">
							<div id="analisisPendientes">
								<tiles:insert name="content">
									<tiles:put name="accion">
										<tiles:getAsString name="accion" ignore="true" />
									</tiles:put>
								</tiles:insert>
							</div>
						</div>
					</div>
					
					<div id="sideTitle">
					<% if (userWorking != null && userWorking.getTipoPerfil() != null) { %>
						<ul>
						<% if (userWorking.getTipoPerfil().getPantalla().contains("Rendiciones")) { %>
							<li>
								<html:link action="listadoRendiciones.do"><span>Rendiciones<img id="checkRendiciones" src="./images/checkLink.png" width="20px" height="20px"/></span></html:link>
							</li>
						<% } %>
						
						<% if (userSession.getTipoPerfil().getPantalla().contains("Delegacion")) { %>
							<li>
								<a href="#" onclick="mostrarOpciones('Delegaciones')" class="menu_links"><span>Delegaci&oacute;n<img id="checkReemplazar" src="./images/checkLink.png" width="20px" height="20px" /></span></a>
								<ul id="opcionesDelegaciones">
									<li><html:link action="replacePage.do"><span>Acceso como Delegado</span></html:link></li>
									<% if(userWorking.getIdUser() == userSession.getIdUser()) { %>
										<li><html:link action="relacionUsuarioDelegado.do"><span>Funciones a terceros</span></html:link></li>
									<% } %>
								</ul>
							</li>
						<% } %>
						
						<% if (userWorking.getTipoPerfil().getPantalla().contains("Aprobacion")) { %>
							<li>
								<a href="#" onclick="mostrarOpciones('Aprob')" class="menu_links"><span>Aprobaci&oacute;n<img id="checkAprobacion" src="./images/checkLink.png" width="20px" height="20px"/></span></a>
								<ul id="opcionesAprob">
									<li><html:link action="aprobacion.do?glg=1"><span>Supervisor</span></html:link></li>
									<li><html:link action="aprobacion.do?glg=2"><span>Firma</span></html:link></li>
									<li><html:link action="aprobacion.do?glg=4"><span>Glg (entrada)</span></html:link></li>
									<li><html:link action="aprobacion.do?glg=3"><span>Glg</span></html:link></li>
								</ul>
							</li>
						<% } %>
						
						<% if (userWorking.getTipoPerfil().getPantalla().contains("Parametros")) { %>
							<li>
								<a href="#" onclick="mostrarOpciones('Parametros')" class="menu_links"><span>Par&aacute;metros<img id="checkParametros" src="./images/checkLink.png" width="20px" height="20px" /></span></a>
								<ul id="opcionesParametros">
									<li><html:link action="parametrosMotivo.do"><span>Motivos</span></html:link></li>
									<li><html:link action="parametrosGastos.do"><span>Gastos</span></html:link></li>
									<li><html:link action="parametrosExceptuados.do"><span>Exceptuados</span></html:link></li>
									<li><html:link action="parametrosAlertas.do"><span>Alertas</span></html:link></li>
								</ul>
							</li>
						<% } %>
						
						<% if (userWorking.getTipoPerfil().getPantalla().contains("Cierre")) { %>
							<li>
								<a href="#" onclick="mostrarOpciones('Cierre')" class="menu_links"><span>Cierre<img id="checkCierre" src="./images/checkLink.png" width="20px" height="20px" /></span></a>
								<ul id="opcionesCierre">
									<li><html:link action="cierre.do"><span>Cierre Orden de Pago</span></html:link></li>
									<li><html:link action="CierreTarjeta.do"><span>Cierre Tarjeta de Credito</span></html:link></li>
<!-- 										<li><html:link action="CierreDiario.do"><span>Cierre Diario</span></html:link></li> -->
<!-- 										<li><html:link action="ReactivacionSuspensos.do"><span>Reactivación suspensos</span></html:link></li> -->
<!-- 										<li><html:link action="CierreMensualFormal.do"><span>Cierre mensual (formal)</span></html:link></li> -->
<!-- 										<li><html:link action="CierreMensualSimulado.do"><span>Cierre mensual (simulado)</span></html:link></li> -->
<!-- 										<li><html:link action="CierreMensualContable.do"><span>Cierre mensual (contable)</span></html:link></li> -->
								</ul>
							</li>
						<% } %>
						
						<% if (userWorking.getTipoPerfil().getPantalla().contains("Resumen")) { %>
							<li>
								<a href="#" onclick="mostrarOpciones('Resumen')" class="menu_links"><span style="display:-moz-inline-box;">Tarjeta<br>Corporativa<img id="checkResumen" src="./images/checkLink.png" width="20px" height="20px"/></span></a>
								<ul id="opcionesResumen">
									<li><html:link action="resumen.do"><span>Consumos no rendidos</span></html:link></li>
									<li><html:link action="resumenesAnteriores.do"><span>Res&uacute;menes anteriores</span></html:link></li>
								</ul>
							</li>
						<% } %>
						
						<% if (userWorking.getTipoPerfil().getPantalla().contains("Aprobacion") || userWorking.getTipoPerfil().getPantalla().contains("Cierre")) { %>
							<li style="margin-bottom: 0;">
								<a href="#" onclick="mostrarOpciones('Reporteria')" class="menu_links"><span>Reporter&iacute;a<img id="checkCuadro" src="./images/checkLink.png" width="20px" height="20px" /></span></a>
								<ul id="opcionesReporteria">
									<li><html:link action="cuadroGeneral.do"><span>Cuadro General</span></html:link></li>
									<li style="margin-bottom: 0;"><html:link action="CuadroDetallado.do"><span>Cuadro Detallado</span></html:link>
								</ul>
							</li>
						<% } %>
						</ul>
					<% } else {%>
						<script type="text/javascript">
							window.onload = function () {
								//alert("Ocurrio un problema con el usuario/perfil del usuario. Por favor pruebe ingresando nuevamente a la aplicacion.");
								window.location.href = "Login.do?redirect=" + window.location.pathname + window.location.search;
							}
						</script>
					<% } %>
					</div>
				</div>
				<div id="sidebarMenuBottom"></div>
			</div>
		</div>
		<div id="footer">
			<tiles:insert attribute="footer" />
		</div>
	</div>
</body>
</html>