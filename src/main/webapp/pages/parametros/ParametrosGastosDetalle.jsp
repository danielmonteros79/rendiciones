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
				<div class="col-sm-12 col-lg-12 pt-2 scroll-err">
					<div class="has-float-label">
						<html:text property="codigo" styleId="codigo"
							styleClass="form-control bg-light text-uppercase" maxlength="4" />
						<label for="codigo">C&oacute;digo</label>
						<div class="invalid-feedback mb-3"></div>
					</div>
				</div>
				<div class="col-sm-12 col-lg-6 pt-2  has-float-label scroll-err">
					<div class="has-float-label">
						<html:text property="descripcionGasto" styleId="descripcionGasto"
							styleClass="form-control bg-light" maxlength="50" />
						<label for="descripcionGasto">Descripci&oacute;n</label>
					</div>
				</div>
				<div
					class="col-sm-12 col-lg-6 pt-2 pl-lg-1 has-float-label scroll-err">
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


				<div class="col-sm-12 col-lg-4 pt-2  has-float-label scroll-err">
					<div class="has-float-label form-group ">
						<select id="filtroMotivo" class="form-control bg-light"></select>
						<i class="bbva-icon icon-uniE003 text-primary"></i> <label>Motivo</label>

					</div>
				</div>

				<div class="col-sm-12 col-lg-4 pt-2 pl-lg-1  scroll-err">
					<div class="has-float-label form-group">
						<html:text property="idCentroCostos"
							styleClass="form-control bg-light text-uppercase" maxlength="4" />
						<label for="idCentroCostos">C.Costos</label>
						<div class="invalid-feedback mb-3"></div>
					</div>
				</div>




				<div class="col-sm-12 col-lg-4 pt-2  pl-lg-1 scroll-err">
					<div class="has-float-label form-group d-flex align-items-center bg-light">

						<html:select property="bimon" styleClass="form-control bg-light">
							<html:option value=""></html:option>
							<html:option value="S">S</html:option>
							<html:option value="N">N</html:option>
						</html:select>
						<div class= "pr-3">
							<i class="bbva-icon icon-uniE003 text-primary"></i> <label
								for="bimon">Bimon</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
				</div>

				<div class="col-sm-12 col-lg-4 pt-2  has-float-label scroll-err">
					<div class="has-float-label form-group ">

						<html:select property="comprob" styleId="filtroComprobante" styleClass="form-control bg-light">
							<html:option value=""></html:option>
							<html:options collection="cmbComprobante" property="id"
								labelProperty="descripcion" />
						</html:select>
						<i class="bbva-icon icon-uniE003 text-primary"></i> <label
							for="modalGastoTipoComprobante">Tipo de comprobante</label>
						<div class="invalid-feedback mb-3"></div>

					</div>
				</div>

				<div
					class="col-sm-12 col-lg-4 pt-2 pl-lg-1 has-float-label scroll-err">
					<div class="has-float-label">
						<html:text property="antiguedad"
							styleClass="form-control bg-light text-uppercase" maxlength="4" />
						<label for="antiguedad">Antigüedad</label>
						<div class="invalid-feedback mb-3"></div>
					</div>
				</div>

				<div class="col-sm-12 col-lg-4 pt-2 pl-lg-1 scroll-err">
					<div class="has-float-label form-group">
						<html:select property="maInclExcl"
							styleClass="form-control bg-light">
							<html:option value=""></html:option>
							<html:option value="I">Incl</html:option>
							<html:option value="E">Excl</html:option>
						</html:select>

						<i class="bbva-icon icon-uniE003 text-primary"></i> <label
							for="maInclExcl">Incl/Excl</label>
						<div class="invalid-feedback mb-3"></div>
					</div>
				</div>

				<div class="col-sm-12 col-lg-4 pt-2  has-float-label scroll-err">
					<div class="has-float-label">

						<html:select property="observ" styleClass="form-control bg-light">
							<html:option value="1"></html:option>
							<html:options collection="cmbObservacion" property="id"
								labelProperty="descripcion" />
						</html:select>

						<i class="bbva-icon icon-uniE003 text-primary"></i> <label>Observacion</label>
						<div class="invalid-feedback mb-3"></div>
					</div>
				</div>

				<div class="col-sm-12 pt-2 col-lg-4 pl-lg-1 scroll-err">
					<div class="has-float-label form-group">

						<html:select property="idNivAutoriz"
							styleClass="form-control bg-light">
							<html:option value=""></html:option>
							<html:option value="1">1</html:option>
							<html:option value="2">2</html:option>
							<html:option value="3">3</html:option>
							<html:option value="4">4</html:option>
						</html:select>
						<i class="bbva-icon icon-uniE003 text-primary"></i> <label
							for="idNivAutoriz">Plazo Ingreso</label>
						<div class="invalid-feedback mb-3"></div>
					</div>
				</div>


				<div class="col-sm-12 pt-2 col-lg-4 pl-lg-1 scroll-err">
					<div class="has-float-label form-group d-flex align-items-center bg-light">
						<html:select property="plazoAprob"
							styleClass="form-control bg-light">
							<html:option value=""></html:option>
							<html:option value="DIA">DIA</html:option>
							<html:option value="SEM">SEM</html:option>
							<html:option value="QUIN">QUIN</html:option>
							<html:option value="MES">MES</html:option>
						</html:select>
						<div class= "pr-3">
							<i class="bbva-icon icon-uniE003 text-primary"></i> <label
								for="plazoAprob">Plazo Aprob</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
				</div>



				<div class="col-lg-6 col-sm-12 pt-2 ">

					<div class="btn btn-light px-4 py-1 ">
						<label><html:checkbox value="O" styleId="oscarO"
								property="oscar.o" /><span class="d-block">O</span></label>
					</div>
					<div class="btn btn-light px-4 py-1 ">
						<label><html:checkbox value="S" styleId="oscarS"
								property="oscar.s" /><span class="d-block">S</span></label>
					</div>
					<div class="btn btn-light px-4 py-1 ">
						<label><html:checkbox value="C" styleId="oscarC"
								property="oscar.c" /><span class="d-block ">C</span></label>
					</div>
					<div class="btn btn-light px-4 py-1 ">
						<label><html:checkbox value="A" styleId="oscarA"
								property="oscar.a" /><span class="d-block">A</span></label>
					</div>
					<div class="btn btn-light px-4 py-1 ">
						<label><html:checkbox value="R" styleId="oscarR"
								property="oscar.r" /><span class="d-block">R</span></label>
					</div>

					<html:hidden styleId="oscarO" property="oscar.o" />
					<html:hidden styleId="oscarS" property="oscar.s" />
					<html:hidden styleId="oscarC" property="oscar.c" />
					<html:hidden styleId="oscarA" property="oscar.a" />
					<html:hidden styleId="oscarR" property="oscar.r" />
				</div>

				<div class="col-sm-12 col-lg-6 pt-2 scroll-err">
					<input type="hidden" name="centrosCosto" /> <label
						for="meDiasInterv">Centros Costo</label>
					<logic:iterate name="ParametrosGastosForm" property="centrosCosto"
						id="centrosCostoI" indexId="i">
						<%
						String cc = "centrosCostoI[" + i + "]";
						%>
						<%
						String ccId = "centrosCosto_" + i;
						%>
						<html:text styleId="<%=ccId%>" property="<%=cc%>" maxlength="4"
							style="width:50px;" onkeypress="return numericOnly(event);" />
						<a href="#" onclick="borrarCentroCosto(<%=i%>);" name="d_<%=cc%>">
							<i class="bbva-icon icon-coronita_trash fa-md" title="Eliminar"></i>
						</a>
					</logic:iterate>
					<a id="addCC" href="#" onclick="agregarCentroCosto();"> <i
						class="ml-2 bbva-icon icon-coronita_contract fa-md"
						title="Agregar"></i>
					</a>

					<div class="invalid-feedback mb-3"></div>
				</div>


				<div class="col-sm-12 col-lg-12 pt-2  has-float-label scroll-err">
					<div class="has-float-label d-flex  bg-light align-items-center">
						<html:text property="ristra"
							styleClass="form-control bg-light text-uppercase" maxlength="69"
							readonly="true" />
						<a href="#" onclick="nuevaRistra()"> <logic:equal value="baja"
								name="ParametrosGastosForm" property="accion">
								<img src="images/iconos/ver.png" alt="Ver" title="Ver"
									border="0">
							</logic:equal> <logic:notEqual value="baja" name="ParametrosGastosForm"
								property="accion">
								<i class="ml-2 bbva-icon icon-coronita_contract fa-md"
									title="Agregar"></i>
							</logic:notEqual>
						</a>
						<div class="pr-3">
							<label for="Ristra">Ristra</label>
						</div>
						<div class="invalid-feedback mb-3"></div>
					</div>
				</div>




				<div class="col-sm-12 text-right mt-5">
					<a href="parametrosGastosFiltro.do"
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
				$('#descripcionGasto').attr('readonly', true);
				$('#motivo').attr('disabled','disabled');
			});
		</script>
	</logic:equal>


	<jsp:include page="modalRistra.jsp" />
	<script type="text/javascript" src="./static/js/parametrosGastosDetalle.js"></script>
	<!-- <script type="text/javascript" src="js/rendiciones/modalGasto.js"></script> -->
</body>
</html>