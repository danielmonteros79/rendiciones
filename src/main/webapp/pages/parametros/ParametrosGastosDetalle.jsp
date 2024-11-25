<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>
<%@ page session="true" contentType="text/html; charset=UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="./css/validation.css">
<link rel="stylesheet" type="text/css" href="./css/Parametros.css">
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
<script>
var codigo = "<%=request.getSession().getAttribute("cod_motivo")%>";
var motivo = "<%=request.getSession().getAttribute("cod_motivo")%>";
var descMotivo = "<%=request.getSession().getAttribute("descripcion_motivo")%>";
console.log(motivo);
console.log(descMotivo);
</script>
<body>
	<html:form action="saveGasto" styleId="parametrosGastosForm">
		<div class="modal-body px-3 mx-3 px-lg-3 mx-lg-5 mt-5">
			<div class="row  px-5 mx-5">
				<div class="col-sm-12">
					<h1 class="font-weight-300">
						<span id="modalDelegadoNuevoModif"></span> Gasto
					</h1>
				</div>
				<div class="col-sm-12 pt-1 pb-5 text-muted">Ingres&aacute; los
					datos del gasto.</div>
			</div>
			<div class="row px-5 mx-5">
				<div class="col-sm-12 col-lg-6 pt-2  has-float-label scroll-err">
					<div class="has-float-label">
						<html:text property="motivo" styleId="motivo"
							styleClass="form-control bg-light text-uppercase" maxlength="4" />
						<label for="motivo">C&oacute;digo del motivo</label>
						<div class="invalid-feedback mb-3"></div>
					</div>
				</div>
				<div class="col-sm-12 col-lg-6 pt-2  has-float-label scroll-err">
					<div class="has-float-label">
						<html:text property="descripcionMotivo" styleId="descripcionMotivo"
							styleClass="form-control bg-light" maxlength="50" />
						<label for="descripcionMotivo">Descripci&oacute;n del motivo</label>
						<div class="invalid-feedback mb-3"></div>
					</div>
				</div>
				<div class="col-sm-12 col-lg-4 pt-2  scroll-err">
					<div class="has-float-label">
						<html:text property="codigo" styleId="codigo"
							styleClass="form-control bg-light text-uppercase" maxlength="4" />
						<label for="codigo">C&oacute;digo del gasto</label>
						<!-- <div class="invalid-feedback mb-3"></div>  -->
					</div>
				</div>
				<div class="col-sm-12 col-lg-4 pt-2  pl-lg-1 scroll-err">
					<div class="has-float-label">
						<html:text property="descripcionGasto" styleId="descripcionGasto"
							styleClass="form-control bg-light" maxlength="50" />
						<label for="descripcionGasto">Descripci&oacute;n del gasto</label>
					</div>
				</div>
				<div
					class="col-sm-12 col-lg-4 pt-2  pl-lg-1 scroll-err">
					<div class="has-float-label form-group">

						<html:select property="estado" styleId="estado"
							styleClass="form-control bg-light">
							<html:option value=""></html:option>
							<html:option value="I">Inactivo</html:option>
							<html:option value="A">Activo</html:option>
						</html:select>
						<i class="bbva-icon icon-uniE003 text-primary"></i> <label
							for="estado">Estado</label>
						<div class="invalid-feedback mb-3"></div>
					</div>
				</div>

				<div class="col-sm-12 col-lg-6 pt-2  scroll-err">
					<div class="has-float-label form-group d-flex align-items-center bg-light">

						<html:select property="bimon" styleClass="form-control bg-light">
							<html:option value=""></html:option>
							<html:option value="S">SI</html:option>
							<html:option value="N">NO</html:option>
						</html:select>
						<div class= "pr-3">
							<i class="bbva-icon icon-uniE003 text-primary"></i> <label
								for="bimon">¿Es bimonetario?</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
				</div>

				<div class="col-sm-12 col-lg-6 pt-2  pl-lg-1 scroll-err">
					<div class="has-float-label">
						<html:text property="idCentroCostos"
							styleClass="form-control bg-light text-uppercase" maxlength="40" />
						<label for="idCentroCostos">C&oacute;digo de datos adicionales</label>
						<div class="invalid-feedback mb-3"></div>
					</div>
				</div>

				<div class="col-sm-12 col-lg-12 pt-2  has-float-label scroll-err">
					<div class="has-float-label">
						<html:textarea styleId="detalleRistra" property="detalleRistra" styleClass="form-control bg-light"/>
						<label for="detalleRistra">Detalle de ristra contable</label>
					</div>
				</div>

				<div class="col-sm-12 text-right mt-5">
					<a href="parametrosGastos.do?cod_motivo=<%= request.getSession().getAttribute("cod_motivo") %>"
   						class="btn btn-link px-5 py-3 mr-2 font-weight-bold"> Volver </a>



					<logic:equal value="modificacion" name="ParametrosGastosForm"
						property="accion">
						<html:submit styleClass="btn btn-info px-5 py-3 ml-2"
							value="Guardar" />
					</logic:equal>
					<logic:equal value="alta" name="ParametrosGastosForm"
						property="accion">
						<html:submit styleClass="btn btn-info px-5 py-3 ml-2"
							value="Guardar" />
					</logic:equal>
					<logic:equal value="baja" name="ParametrosGastosForm"
						property="accion">
						<html:button property="" styleClass="btn btn-info px-5 py-3 ml-2"
							onclick="confirmEliminarGasto()" value="Eliminar" />
					</logic:equal>

				</div>

				<html:hidden property="accion" styleId="accion" />
				<html:hidden property="back" styleId="back" value="true" />
			</div>
		</div>
	</html:form>

	<logic:equal value="alta" name="ParametrosGastosForm" property="accion">
		<script>
			$( document ).ready(function() {
				$('#estado').attr('disabled','disabled');
				$('#motivo').val(motivo);
				$('#motivo').attr('readonly', true);
				$('#descripcionMotivo').val(descMotivo);
				$('#descripcionMotivo').attr('readonly', true);
				$('#codigo').attr('readOnly',true);				
			});
		</script>
	</logic:equal>

	<logic:equal value="baja" name="ParametrosGastosForm" property="accion">
		<script>
			$( document ).ready(function() {
				$('input').attr('readonly', true);
				$('select').attr('disabled','disabled');
				$(".ck-button").attr('disabled','disabled');
				$("[name^='oscar']").attr('disabled','disabled');
				$("[name^='d_centrosCostoI']").hide();
				$("#addCC").hide();
			});
		</script>
	</logic:equal>

	<logic:equal value="modificacion" name="ParametrosGastosForm"
		property="accion">
		<script>
			$( document ).ready(function() {
				$('#codigo').attr('readonly', true);
				$('#descripcionMotivo').attr('readonly', true);
				$('#motivo').attr('disabled','disabled');
			});
		</script>
	</logic:equal>


	<jsp:include page="modalRistra.jsp" />
	<script type="text/javascript" src="./static/js/parametrosGastosDetalle.js"></script>
</body>
</html>