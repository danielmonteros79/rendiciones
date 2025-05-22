<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%
Usuario userSession = (Usuario) request.getSession().getAttribute("usuario");
if (userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL
		|| userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL_LESS_CIERRE) {
%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<script>
var codigo = "<%=request.getSession().getAttribute("cod_motivo")%>";
</script>
<body>


	<html:form action="parametrosAlertasFiltro"
		styleId="parametrosAlertasFiltro">
		
			<div class="bg-light" id="divFiltro">
				<logic:present name="message">
					<%String message = (String) request.getAttribute("message");if (message.contains("ERROR")) {%>
					<div id="messageErr" class="message text-danger  pt-2 text-center">
						<%=message.substring(7)%>
					</div>
					<%} else if (message.contains("OK")) {%>
					<div id="messageOk" class="message pt-2  text-center">
						<%=message.substring(4)%>
					</div>
					<%} else {%>
					<div id="messageAviso" class="message  pt-2 text-center">
						AVISO:
						<%=message%>
					</div>
					<%}%>
				</logic:present>

<!-- 				<div class="container py-3"> -->
<!-- 					<div class="row pb-3 d-none" id="messageContainer"> -->
<!-- 						<div class="col-sm-12"> -->
<!-- 							<h5 id="message"></h5> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<div class="row"> -->
<!-- 						<div class="col-sm-12 "> -->
<!-- 							<span class="font-weight-bold">B&uacute;squeda</span> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<div class="row"> -->
<!-- 						<div class="col-sm-12"> -->
<!-- 							<h5 class="pt-3 pb-5 text-danger" id="filtroMsgValidacion" -->
<!-- 								style="display: none;"></h5> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<div class="row"> -->
<!-- 						<div class="col-sm-12 col-lg-6 pt-2  has-float-label scroll-err"> -->
<!-- 						<div class="has-float-label form-group "> -->
<%-- 							<html:select property="codMotivo" styleClass="form-control" --%>
<%-- 								onchange="selectMotivo();" styleId="motivo"> --%>
<%-- 								<html:option value="0203"></html:option> --%>
<%--  								<html:options collection="cmbMotivo" property="id" --%> 
<%--  									labelProperty="descripcion" /> --%> 
<%-- 							</html:select> --%>
<!-- 							<i class="bbva-icon icon-uniE003 text-primary"></i> <label>Motivo</label> -->
<!-- 						</div>  -->
						
<!-- 						<select id="filtroMotivo" class="form-control"></select>
<!-- 						<i class="bbva-icon icon-uniE003 text-primary"></i> <label>Motivo</label> -->
						
<!-- 					</div> -->
<!-- 						<div class="col-sm-12 col-lg-6 pt-2  has-float-label scroll-err"> -->
<!-- 							<div class="has-float-label form-group "> -->
<%-- 								<html:select property="codGasto" styleId="filtroGasto" onchange="selectGasto();"
<%-- 									styleClass="form-control" styleId="gasto"> --%>
<%-- 									<html:option value=""></html:option> --%>
<%-- 									<html:options collection="cmbGasto" property="id" --%>
<%-- 										labelProperty="descripcion" /> --%>
<%-- 									<html:option value="9999 - TODOS LOS GASTOS"></html:option> --%>
<%-- 								</html:select> --%>
<%-- 								<i class="bbva-icon icon-uniE003 text-primary"></i> <label>Gasto</label> --%> 
								
<!-- 								<select id="gasto" class="form-control"></select> -->
<!-- 								<i class="bbva-icon icon-uniE003 text-primary"></i> <label>Gasto</label> -->
								
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<div class="row"> -->
<!-- 						<div class="col-sm-12 pt-3  text-center"> -->

<%-- 							<html:hidden property="codGasto" styleId="codGasto"/> --%>
<%-- 							<html:button property="" value="Limpiar" styleClass="btn btn-primary px-4 py-2 mx-2"  onclick="resetForm();" /> --%>
<%-- 							<html:submit styleClass="btn btn-primary px-4 py-2"  value="Filtrar" onclick="filtrar()"/> --%>
							
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
			</div>
	</html:form>

	<div class="container py-5 div-resultado"
		id="listadoRendicionesDivResultado">
		<div class="row">
			<div class="col-sm-12 mb-md-4">
				<i
					class="bbva-icon icon-coronita_bullet fab fa-rotate-270 text-blue align-middle mx-2"></i>
				<small class="font-weight-bold">ALERTAS</small>
			</div>
			<div class="col-sm-12 text-right">
					<html:form action="parametrosAlertasDetalle" styleId="addAlerta">
						<input type="hidden" name="accion" value="alta" />
						<a href="#" onclick="agregarAlerta()" title="Alta de motivo"
							class="btn btn-info px-5 py-3"> Agregar Alerta </a>
					</html:form>

				</div>
		</div>
		<div class="row py-3">
			<div class="col-sm-12">
				<h2 class="font-weight-500">Listado de Alertas</h2>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12 py-3 table-responsive-lg">
				<div class="dt-container" id="motivoDtContainer"></div>
			</div>
		</div>
	</div>


	
	<script type="text/javascript" src="./static/js/js/DOMPurify-main/DOMPurify-main/dist/purify.js"></script>
	<script type="text/javascript" src="./static/js/parametrosAlertas.js"></script>
</body>
</html>

<% } else { %>
No tiene permisos para ver esta p&aacute;gina
<% } %>