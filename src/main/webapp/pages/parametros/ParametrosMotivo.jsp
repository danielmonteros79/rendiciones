<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<link rel="stylesheet" type="text/css"
	href="./css/select2Personalized.css">

<%
Usuario userSession = (Usuario) request.getSession().getAttribute("usuario");
if (userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL
		|| userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL_LESS_CIERRE) {
%>


<html:form action="parametrosMotivo"
	styleId="parametrosMotivoFiltro">
	<div class="bg-light" id="divFiltro">

		<%-- <logic:present name="message">
			<%
			String message = (String) request.getAttribute("message");

			if (message.contains("ERROR")) {
			%>
			<div id="messageErr" class="message text-danger  pt-5 text-center">
				<%=message.substring(7)%>
			</div>
			<%
			} else if (message.contains("OK")) {
			%>
			<div id="messageOk" class="message pt-5  text-center">
				<%=message.substring(4)%>
			</div>
			<%
			} else {
			%>
			<div id="messageAviso" class="message  pt-5 text-center">
				AVISO:
				<%=message%>
			</div>
			<%
			}
			%>
		</logic:present> --%>

		<div class="container py-2">


			<div class="row pb-3 d-none" id="messageContainer">
				<div class="col-sm-12">
					<h5 id="message"></h5>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12 mt-2 mb-2">
					<span class="font-weight-bold">B&uacutesqueda</span>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<h5 class="pt-3 pb-5 text-danger" id="filtroMsgValidacion"
						style="display: none;"></h5>
				</div>
			</div>
			<div class="row">
				<div class="col-12">
					<div class="has-float-label">
						<html:text property="codigo" styleId="codigo"
							styleClass="form-control text-uppercase" maxlength="20"
							onkeypress="return alphanumericOnly(event);" />
						<label>Motivo</label>
						<div class="invalid-feedback"></div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12  pt-3 pb-2 text-center">
					<html:button property="" styleClass="btn btn-primary px-4 py-2 mr-3 "
						style="margin-left:10px;" value="Filtrar" onclick="filtrar();"/>
					<html:button property="" value="Limpiar"
						styleClass="btn btn-primary px-4 py-2 " onclick="resetForm();" />

				</div>
			</div>
		</div>
	</div>
</html:form>



<div class="container py-5 div-resultado"
	id="listadoRendicionesDivResultado">
	<div class="row">
		<div class="col-sm-12 mb-2">
			<i
				class="bbva-icon icon-coronita_bullet fab fa-rotate-270 text-blue align-middle mx-2"></i>
			<small class="font-weight-bold">MOTIVOS</small>
		</div>
		<div class="col-sm-12 text-right">
			<html:form action="parametrosMotivoDetalle" styleId="addMotivo">
				<input type="hidden" name="accion" value="alta" />
				<a href="#" onclick="agregarMotivo()" title="Alta de motivo"
					class="btn btn-info px-5 py-3"> Agregar Motivo </a>
			</html:form>

		</div>

	</div>
	<div class="row py-3">
		<div class="col-sm-12">
			<h2 class="font-weight-500">Listado de motivos</h2>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 py-3 table-responsive-lg" id="paginacion">
			<div class="dt-container" id="motivosDtContainer"></div>

		</div>
	</div>
</div>




<script type="text/javascript" src="js/select2.min.js"></script>
<script type="text/javascript" src="./static/js/parametrosMotivo.js"></script>


<% } else { %>
No tiene permisos para ver esta p&aacute;gina
<% } %>
