
<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>
<bean:define id="ParametrosExceptuadosForm" name="ParametrosExceptuadosForm" scope="session" toScope="request" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" type="text/css" href="./css/validation.css">
<style>
td {
	white-space: nowrap;
	text-align: left;
}
</style>
</head>
<body>

	<div class="mt-5 mb-1 pt-3 container text-center">

		<logic:present name="message">
			<% String message = (String) request.getAttribute("message");
			if(message.contains("ERROR")) { %>
				<div id="messageErr"  class="message text-danger text-center">
					<%= message.substring(7) %>
				</div>
			<%} else if(message.contains("OK")) { %>
				<div id="messageOk" class="message">
					<%= message.substring(4) %>
				</div>
			<%} else {%>
				<div id="messageAviso" class="message text-warning text-center">
					AVISO: <%= message %>
				</div>
			<%}%>
		</logic:present>
	
	</div>


	<html:form action="saveExcepcion" styleId="parametrosExceptuadosForm">

		<div class="modal-body px-3 mx-3 px-lg-3 mx-lg-5 py-5">
			<div class="row px-5 mx-5">
				<div class="col-sm-12">
					<h1 class="font-weight-300">
						<span id="modalDelegadoNuevoModif"></span> Exceptuado
					</h1>
				</div>
				<div class="col-sm-12 pt-1 pb-5 text-muted">Ingres&aacute; los
					datos del exceptuado.</div>
			</div>
			<div class="row px-5 mx-5">

				<div class="col-12 pt-3 d-flex ">
					<div class=pr-3>
						Motivo
						<html:radio property="motivoUsuario" value="motivo" />
					</div>
					<div>
						Usuario
						<html:radio property="motivoUsuario" value="usuario" />
					</div>
				</div>

				<div class="col-sm-4 col-lg-4 pt-2  has-float-label scroll-err">
					<div class="has-float-label form-group">
						<html:select property="estado" styleId="estado" styleClass="form-control bg-light"  >
							<html:option value="I">Inactivo</html:option>
							<html:option value="A">Activo</html:option>
						</html:select>
						 <i class="bbva-icon icon-uniE003 text-primary"></i> 
						 <label for="estado">Estado</label>
						<div class="invalid-feedback mb-3"></div>
					</div>
				</div>


				<div class="col-sm-4 col-lg-4 pt-2 pl-1 has-float-label scroll-err">
					<div class="input-group">
						<html:text property="desde" styleId="fechaDesde"  styleClass="form-control datepicker bg-light" />
						 <label for="fechaDesde">Desde</label>
						<div class="input-group-append">
							<button
								class="btn btn-outline-primary bg-light border-white hover-darkblue datepicker-btn"
								tabindex="-1" type="button">
								<i class="bbva-icon icon-coronita_calendar fa-lg"></i>
							</button>
						</div>
						<div class="invalid-feedback mb-3"></div>
					</div>
				</div>
				<div class="col-sm-4 col-lg-4 pt-2 pl-1  has-float-label scroll-err">
					<div class="input-group">
						<html:text property="hasta" styleId="fechaHasta"  styleClass="form-control datepicker bg-light"  />
						 <label for="fechaHasta">Hasta</label>
						<div class="input-group-append">
							<button
								class="btn btn-outline-primary bg-light border-white hover-darkblue datepicker-btn"
								tabindex="-1" type="button">
								<i class="bbva-icon icon-coronita_calendar fa-lg"></i>
							</button>
						</div>
						<div id="errorFechas" style="color:red;"></div>
					</div>
				</div>

					<div class="col-sm-12 col-lg-6 pt-2  has-float-label scroll-err">
						<div class="has-float-label">
						<html:text property="desMotivo" styleId="desMotivo" styleClass="form-control bg-light" onchange="checkCodigo()" />
						<label for="desMotivo">C&oacute;digo</label>
						</div>
					</div>
					<div class="col-sm-12 col-lg-6 pt-2 pl-1 has-float-label scroll-err">
						<div class="has-float-label">
						<html:text property="descripcionCodigo" styleId="descripcionCodigo" styleClass="form-control bg-light font-weight-bold" readonly="true" />
							 <label for="descripcionCodigo">Descripci&oacute;n</label>
						</div>
					</div>

				<div class="col-sm-12 text-right mt-4 ">
					<a href="parametrosExceptuadosFiltro.do"
						class="btn btn-link px-5 py-3 mr-2 font-weight-bold"> Volver </a>

					<logic:equal value="baja" name="ParametrosExceptuadosForm" property="accion">
							<html:button property="" styleClass="btn btn-info px-5 py-3 ml-2" onclick="confirmEliminarExceptuado()" value="Eliminar"/>
					</logic:equal>
					<logic:notEqual value="baja" name="ParametrosExceptuadosForm" property="accion">
							<html:submit property="" styleClass="btn btn-info px-5 py-3 ml-2" value="Guardar" />
					</logic:notEqual>	
			
				</div>
			</div>
		</div>
		
	</html:form>
	
	<logic:equal value="alta" name="ParametrosExceptuadosForm"
		property="accion">
		<script>
			$( document ).ready(function() {
				$('#estado').attr('disabled','disabled');
			});
		</script>
	</logic:equal>
	<logic:equal value="modificacion" name="ParametrosExceptuadosForm"
		property="accion">
		<script>
			$( document ).ready(function() {
				$('#desMotivo').attr('readonly', true);
				$('#motivo').attr('disabled','disabled');
				$('#usuario').attr('disabled','disabled');
			});
		</script>
	</logic:equal>
	<logic:equal value="baja" name="ParametrosExceptuadosForm"
		property="accion">
		<script>
			$( document ).ready(function() {
				$('#desMotivo').attr('readonly', true);
				$('#motivo').attr('disabled','disabled');
				$('#usuario').attr('disabled','disabled');
				$('#estado').attr('disabled','disabled');
				$('#fechaDesde').attr('readonly', true);
				$('#fechaHasta').attr('readonly', true);
				$('#fechaDesde').attr('disabled','disabled');
				$('#fechaHasta').attr('disabled','disabled');
				$("#imageCal1").hide();
				$("#imageCal2").hide();
			});
		</script>
	</logic:equal>
	<script type="text/javascript"
		src="./js/parametrosExceptuadosDetalle.js"></script>
</body>
</html> 



