<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" type="text/css" href="./css/Parametros.css">
<link rel="stylesheet" type="text/css" href="./css/validation.css">

<style>
td {
	white-space: nowrap;
	text-align: left;
}

label.error {
	position: absolute;
}
</style>
</head>

<%
Usuario userSession = (Usuario) request.getSession().getAttribute("usuario");
/* if (userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL
		|| userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL_LESS_PARAMS) */ {
%>
<body>
	<html:form action="cuadroGeneralFiltro" styleId="cuadroGeneralFiltro">
		<div class="bg-light" id="filtro">

			<div class="py-3 bg-light container">

				<logic:present name="message">
					<%
					String message = (String) request.getAttribute("message");

					if (message.contains("ERROR")) {
					%>
					<div id="messageErr" class="message text-danger  pt-1 text-center">
						<%=message.substring(7)%>
					</div>
					<%
					} else if (message.contains("OK")) {
					%>
					<div id="messageOk" class="message pt-1 text-center">
						<%=message.substring(4)%>
					</div>
					<%
					} else {
					%>
					<div id="messageAviso" class="message  pt-1 text-center">
						AVISO:
						<%=message%>
					</div>
					<%
					}
					%>
				</logic:present>
				<div class="row">
					<div class="mb-md-2">
						<span class="font-weight-bold">B&uacute;squeda</span>
					</div>
				</div>

				<div class="row">
					<div class="col-sm-12   pl-lg-1 col-lg-6">
						<span>Usuario </span>
						<html:text styleClass="form-control" property="nombreUsuario"
							readonly="true" />
					</div>
					<div class="col-sm-12 pl-lg-1 col-lg-6">
						<span>C.Costos: </span>
						<html:text styleClass="form-control" property="costos"
							readonly="true" />
					</div>
					<div
						class="col-sm-6 col-lg-3 pt-2  pl-lg-1 has-float-label scroll-err">
						<div class="has-float-label form-group">
							<html:select property="opcion" styleClass="form-control"
								styleId="opcion" onchange="selectOpcion()">
								<html:option value="01">Estado actual</html:option>
								<html:option value="02">Estado final</html:option>
								<html:option value="03">Fecha de carga</html:option>
							</html:select>

							<i class="bbva-icon icon-uniE003 text-primary"></i> <label
								for="opcion">Opci&oacute;n:</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-sm-6 col-lg-3 pt-2 pl-1 has-float-label scroll-err">
						<div class="has-float-label form-group">
							<html:select property="codGlg" styleId="codGlg"
								styleClass="form-control ">
								<html:options collection="ComboGlg" property="id"
									labelProperty="descripcion" />
							</html:select>
							<i class="bbva-icon icon-uniE003 text-primary"></i> <label
								for="modalDelegadoInforme">GLG</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>

					<div
						class="col-sm-6 col-lg-3 pt-2 pl-lg-1 has-float-label scroll-err">
						<div class="has-float-label form-group">
							<html:select property="codMotivo" styleId="codMotivo"
								styleClass="form-control">
								<html:option value="">TODOS</html:option>
								<html:options collection="ComboMotivo" property="id"
									labelProperty="descripcion" />
							</html:select>
							<i class="bbva-icon icon-uniE003 text-primary"></i> <label
								for="modalDelegadoInforme">Motivo</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>

					<div
						class="col-sm-6 col-lg-3 pt-2  pl-1 has-float-label scroll-err">
						<div class="input-group">
							<html:text property="fechaDesde" styleId="fechaDesde"
								styleClass="form-control datepicker" readonly="true" />

							<label for="fechaDesde">Desde</label>
							<div class="invalid-feedback mb-3"></div>
						</div>

					</div>
					<div
						class="col-sm-6 col-lg-3 pt-2 has-float-label scroll-err pl-lg-1">

						<div class="input-group">

							<html:text property="fechaHasta" styleId="fechaHasta"
								styleClass="form-control datepicker " readonly="true"/>

							<label for="fechaHasta">Hasta</label>

							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>

					<div class="col-sm-6 col-lg-3 pt-2 pl-1 has-float-label scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control" id="montoDesde"
								placeholder="montoDesde"
								onkeypress="return keyPressMonto(event, 'montoDesde');" /> <label
								for="montoDesde">Monto desde</label>
						</div>
						<div id="errorMonto" style="color: red;"></div>
					</div>

					<div
						class="col-sm-6 col-lg-3 pt-2  pl-lg-1 has-float-label scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control" id="montoHasta"
								placeholder="montoHasta"
								onkeypress="return keyPressMonto(event, 'montoHasta');" /> <label
								for="montoHasta">Monto hasta</label>
						</div>
						<div id="errorMonto" style="color: red;"></div>
					</div>

					<div
						class="col-sm-6 col-lg-3 pt-2  pl-1 has-float-label scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control" id="usuario"
								placeholder="Descripción" /> <label for="modalDelegadoCCostos">Usuario</label>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-sm-12 pt-3 pt-md-3 text-center">
						<html:submit value="Filtrar" property="" onclick="filtrar()"
							styleClass="btn btn-primary px-4 py-2 mx-2" />
						<html:button styleClass="btn btn-primary px-4 py-2 mx-2"
							value="Limpiar" property="" onclick="resetForm()" />

					</div>
				</div>
			</div>
			<html:hidden styleId="codEstado" property="codEstado" />

		</div>
	</html:form>


	<div class="container pt-5 div-resultado"
		id="listadoRendicionesDivResultado">
		<div class="row">
			<div class="col-sm-12 mb-md-4">
				<i
					class="bbva-icon icon-coronita_bullet fab fa-rotate-270 text-blue align-middle mx-2"></i>
				<small class="font-weight-bold">Cuadro General</small>
			</div>
		</div>
		<div class="row py-3">
			<div class="col-sm-12">
				<h2 class="font-weight-500">Listado General</h2>
			</div>
		</div>

	</div>


	<logic:equal value="t" name="Tabla">
		<div id="paginacion" style="margin-top: 20px;" class=" container">
			<div class="py-4  table  table-responsive">
				<display:table uid="row" name="Rendicion" class="w-100"
					requestURI="cuadroGeneralFiltro.do" id="RendicionesTable"
					excludedParams="username password"
					decorator="com.sa.decorator.CuadroGeneralTableDecorator"
					pagesize="15" style="width:50%;" export="true">
					<display:column property="estado" title="ESTADO"
						style="white-space:nowrap" media="html csv excel" />
					<display:column property="cantRend" title="CANTIDAD DE RENDICIONES"
						style="white-space:nowrap" media="html csv excel" />
					<display:column property="montoTotal" title="MONTO TOTAL(*)"
						maxLength="55" media="html csv excel" />
					<display:column property="opciones" title="DETALLE"
						style="text-align:center; width:4%" media="html" />

					<display:setProperty name="paging.banner.all_items_found" value="" />
					<display:setProperty name="paging.banner.onepage" value="" />

					<display:setProperty name="export.csv.filename"
						value="CuadroGeneral.csv" />
					<display:setProperty name="export.excel.filename"
						value="CuadroGeneral.xls" />
				</display:table>
			</div>
		</div>

		<div style="color: #FF0000" class="container">
			<p>(*) importe rendici&oacute;n estimado</p>
		</div>
	</logic:equal>

	<script type="text/javascript" src="./static/js/cuadroGeneral.js"></script>
</body>
</html>

<%
} 
%>

<%-- <%
} else {
%>
<div class="my-5 py-5 text-center">
	<h6 class="mt-3">
		<span class="text-danger">ERROR: </span>No tiene permisos para ver
		esta p&aacute;gina
	</h6>
</div>
<% } %> --%>
