<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./css/select2Personalized.css">
<link rel="stylesheet" type="text/css" href="./css/Parametros.css">
<link rel="stylesheet" type="text/css" href="./css/validation.css">
<style>
td {
	text-align: left;
}

label.error {
	position: absolute;
}
</style>
</head>
<body>

	<html:form action="FiltroCuadroDetallado" styleId="CuadroDetalladoForm">
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
				<div id="messageOk" class="message pt-1  text-center">
					<%=message.substring(4)%>
				</div>
				<%
				} else {
				%>
				<div id="messageAviso" class="message pt-1 text-center">
					AVISO:
					<%=message%>
				</div>
				<%
				}
				%>
			</logic:present>

				<div class="row">
					<div class="mb-md-2 col-sm-12">
						<span class="font-weight-bold">B&uacute;squeda</span>
					</div>
				</div>

				<div class="row">
					<div class="col-sm-12  col-lg-6">
						<span>Usuario </span>
						<html:text styleClass="form-control" property="nombreUsuario"
							readonly="true" />
					</div>
					<div class="col-sm-12 pl-lg-1 col-lg-6">
						<span>C.Costos: </span>
						<html:text styleClass="form-control" property="costos"
							readonly="true" />
					</div>
					<div class="col-sm-6 col-lg-4 pt-2  has-float-label scroll-err">
						<div class="has-float-label form-group">
							<html:select property="opcion" styleClass="form-control"
								styleId="opcion" onchange="selectOpcion()">
								<html:option value="01">Estado actual</html:option>
								<html:option value="02">Estado final</html:option>
								<html:option value="03">Fecha de carga</html:option>
							</html:select>
							<label
								for="opcion">Opci&oacute;n:</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-sm-6 col-lg-4 pt-2 pl-1 has-float-label scroll-err">
						<div class="has-float-label form-group">
							<html:select property="codGlg" styleClass="form-control"
								styleId="codGlg">
								<html:options collection="ComboGlg" property="id"
									labelProperty="descripcion" />
							</html:select>
							<label
								for="codGlg">GLG</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>

					<div
						class="col-sm-6 col-lg-4 pt-2 pl-lg-1 has-float-label scroll-err">
						<div class="has-float-label form-group">
							<html:select property="codMotivo" styleId="codMotivo"
								styleClass="form-control">
								<html:option value="">TODOS</html:option>
								<html:options collection="ComboMotivo" property="id"
									labelProperty="descripcion" />
							</html:select>
							<label
								for="modalDelegadoInforme">Motivo</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>

					<div class="col-sm-6 col-lg-4 pt-2  has-float-label scroll-err">
						<div class="input-group">
							<html:text property="fechaDesde" styleId="fechaDesde"
								styleClass="form-control datepicker " readonly="true" />
							<label for="fechaDesde">Desde</label>
							<div class="invalid-feedback mb-3"></div>
						</div>

					</div>
					<div
						class="col-sm-6  col-lg-4 pt-2 pl-lg-1  has-float-label scroll-err ">

						<div class="input-group">
							<html:text property="fechaHasta" styleId="fechaHasta"
								styleClass="form-control datepicker" readonly="true" />
							<label for="fechaHasta">Hasta</label>

							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>

					<div class="col-sm-6 col-lg-4 pt-2 pl-1 has-float-label scroll-err">
						<div class="has-float-label">
							<label
							for="montoDesde">Monto desde</label>
							<html:text property="montoDesde" styleId="montoDesde" styleClass="form-control"
							maxlength="16" onkeypress="return keyPressMonto(event, 'montoDesde');"/>
						</div>
						<div id="errorMonto" style="color: red;"></div>
					</div>

					<div class="col-sm-12 col-lg-4 pt-2 has-float-label scroll-err">
						<div class="has-float-label">
					 		<label for="montoHasta">Monto hasta</label> 
								<html:text property="montoHasta" styleId="montoHasta" styleClass="form-control"  
								maxlength="16" onkeypress="return keyPressMonto(event, 'montoHasta');"/>	
						</div>
						<div id="errorMonto" style="color: red;"></div>
					</div>

					<div
						class="col-sm-6 col-lg-4 pt-2  pl-lg-1 has-float-label scroll-err">
						<div class="has-float-label">
						<!-- <input type="text" class="form-control" id="usuario"
							placeholder="Descripción" /> <label for="usuario">Usuario</label> -->
							<label for="usuario">Usuario</label>
							<html:text property="usuario" styleClass="form-control" styleId="usuario" style="text-transform:uppercase;" maxlength="8"/>
						</div>
					</div>



					<div
						class="col-sm-6 col-lg-4 pt-2 pl-sm-1 has-float-label scroll-err">
						<div class="has-float-label form-group">
							<html:select property="codEstado" styleId="codEstado"
								styleClass="form-control">
								<html:option value="">Todos</html:option>
								<html:options collection="ComboEstado" property="id"
									labelProperty="descripcion" />
							</html:select>
							<i class="bbva-icon icon-uniE003 text-primary"></i> <label
								for="codEstado">Estado</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-sm-12 pt-3 pt-md-3 text-center">
						<html:submit value="Filtrar" onclick="filtrar()"
							styleClass="btn btn-primary px-4 py-2 mx-2" />
						<html:button styleClass="btn btn-primary px-4 py-2 mx-2"
							value="Limpiar" property="" onclick="resetForm()" />
					</div>
				</div>
			</div>
			<html:hidden styleId="codEstado" property="codEstado" />

		</div>

		<div class="container pt-5 div-resultado"
			id="listadoRendicionesDivResultado">
			<div class="row">
				<div class="col-sm-12 mb-md-4">
					<i
						class="bbva-icon icon-coronita_bullet fab fa-rotate-270 text-blue align-middle mx-2"></i>
					<small class="font-weight-bold">Cuadro Detalle</small>
				</div>
			</div>
			<div class="row py-3">
				<div class="col-sm-12">
					<h2 class="font-weight-500">Listado Detallado</h2>
				</div>
			</div>

		</div>

	</html:form>

	<logic:equal value="t" name="Tabla">
		<div id="paginacion" style="margin-top: 20px;" class=" container ">
			<div class="py-4  table container table-responsive">
				<display:table uid="row" name="CuadroDetallado" class="w-100"
					requestURI="FiltroCuadroDetallado.do" id="RendicionesTable"
					excludedParams="username password"
					decorator="com.sa.decorator.CuadroDetalladoTableDecorator"
					pagesize="15" style="width:50%;" export="true">
					<display:column property="id" title="ID"
						style="width:4%;text-align:center" sortable="true"
						media="html csv excel" />
					<display:column property="motivo" title="MOTIVO" style="width:15%"
						media="html csv excel" />
					<display:column property="descripcion" title="DESCRIPCI&Oacute;N"
						style="width:40%" media="html csv excel" />
					<display:column property="estado" title="Estado" style="width:10%"
						media="html csv excel" />
					<display:column property="proxUsuario" title="PROX.USUARIO"
						style="width:3%;text-align:center;" media="html" />
					<display:column property="fechaUltModif"
						format="{0,date,dd/MM/yyyy}" title="FECHA.ULT.MODIF"
						style="width:3%;text-align:center;" media="html" />
					<display:column property="importe" title="IMPORTE(*)"
						style="width:10%;text-align:center;" media="html csv excel" />
					<display:column property="opciones" title="DETALLE"
						style="width:3%;text-align:center;" media="html" />
					<display:setProperty name="export.csv.filename"
						value="CuadroDetallado.csv" />
					<display:setProperty name="export.excel.filename"
						value="CuadroDetallado.xls" />
				</display:table>
			</div>
		</div>

		<div style="color: #FF0000" class="container py-4">
			<p>(*) importe rendici&oacute;n estimado</p>
		</div>
	</logic:equal>

	<script type="text/javascript" src="static/js/select2.min.js"></script>
	<script type="text/javascript" src="./static/js/cuadroDetallado.js"></script>
</body>
</html>



