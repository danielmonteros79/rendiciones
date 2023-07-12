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
		|| userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL_LESS_PARAMS) {
%>


<html:form action="parametrosMotivoFiltro"
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

		<div class="container py-5">


			<div class="row pb-3 d-none" id="messageContainer">
				<div class="col-sm-12">
					<h5 id="message"></h5>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12 mb-md-4">
					<span class="font-weight-bold">Filtro Motivos</span>
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
						<html:text property="codigo" styleId="codigo" styleClass="form-control text-uppercase" maxlength="4" onkeypress="return numericOnly(event);"/>
						<label>Motivo</label>
						<div class="invalid-feedback mb-3"></div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12 pt-3 pt-md-5 text-center">	
						<html:submit styleClass="btn btn-primary px-5 py-3 mx-2" style="margin-left:10px;" value="Filtrar" />
						<html:button property="" value="Limpiar" styleClass="btn btn-primary px-5 py-3 mx-2" onclick="resetForm();" />
		
				</div>
			</div>
		</div>
	</div>
</html:form>



<div class="container py-5 div-resultado"
	id="listadoRendicionesDivResultado">
	<div class="row">
		<div class="col-sm-12 mb-md-4">
			<i
				class="bbva-icon icon-coronita_bullet fab fa-rotate-270 text-blue align-middle mx-2"></i>
			<small class="font-weight-bold">MOTIVOS</small>
		</div>
	</div>
	<div class="row py-3">
		<div class="col-sm-12">
			<h2 class="font-weight-500">Listado de motivos</h2>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 py-3 table-responsive-lg">
			<div class="dt-container" id="motivosDtContainer"></div>
		</div>
	</div>
</div>

<%-- 
<logic:equal value="t" name="Tabla">
	<div id="paginacion" class="text-center pb-5" style="margin-top: 43px;">
		<display:table uid="row" name="motivos"
			requestURI="/parametrosMotivoFiltro.do" id="ParametrosMotivoTable"
			excludedParams="false"
			decorator="com.sa.decorator.parametros.ParametrosMotivoTableDecorator"
			pagesize="15" style="margin-left:-0.9%;width:99.7%;" export="true">
			<display:column media="html csv excel" property="codigo"
				title="Motivo" style="width:4%" sortable="true"
				style="text-align:right;" />
			<display:column media="html csv excel" property="descripcion"
				title="Descripción" />
			<display:column media="html csv excel" property="idGlg" title="GLG"
				style="text-align:right;" />
			<display:column media="html csv excel" property="idCentroCostos"
				title="C. Costos" style="text-align:right;" />
			<display:column media="html csv excel" property="codSup"
				title="Superior" />
			<display:column media="html csv excel" property="codFirma"
				title="Firma" />
			<display:column media="html csv excel" property="codAprobacionGlg"
				title="Ctrl. GLG" />
			<display:column media="html csv excel" property="estado"
				title="Estado" style="text-align:center;" />
			<display:column media="html" property="opciones" title="Opciones"
				style="width:4%" />

			<display:setProperty name="export.csv.filename"
				value="ListadoParametrosMotivo.csv" />
			<display:setProperty name="export.excel.filename"
				value="ListadoParametrosMotivo.xls" />
		</display:table>
	</div>
</logic:equal> --%>


<div class="bg-light">
	<div class="container py-5 text-center">
		<div class="row">
			<div class="col-sm-12">
				<h2 class="font-weight-400">&iquest;Quer&eacute;s dar de alta a
					un motivo?</h2>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12 pt-3 pt-md-5">
				<html:form action="parametrosMotivoDetalle" styleId="addMotivo">
					<input type="hidden" name="accion" value="alta" />
					<a href="#" onclick="agregarMotivo()" title="Alta de motivo"
						class="btn btn-info px-5 py-3"> Agregar nuevo motivo </a>
				</html:form>

			</div>

		</div>
	</div>
</div>



<script type="text/javascript" src="./static/js/parametrosMotivo.js"></script>


<% } else { %>
No tiene permisos para ver esta p&aacute;gina
<% } %>
