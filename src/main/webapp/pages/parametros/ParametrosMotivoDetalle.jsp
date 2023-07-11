<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>

<bean:define id="ParametrosMotivoForm" name="ParametrosMotivoForm"
	scope="session" toScope="request" />


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

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
<div class="mt-5 mb-1 pt-3 container text-center">
	<logic:present name="message">
		<% String message = (String) request.getAttribute("message");
		
		if(message.contains("ERROR")) { %>
		<div id="messageErr" class="message">
			<%= message.substring(7) %>
		</div>
		<%} else if(message.contains("OK")) { %>
		<div id="messageOk" class="message text-center text-warning h5">
			<%= message.substring(4) %>
		</div>
		<%} else {%>
		<div id="messageAviso" class="message">
			AVISO:
			<%= message %>
		</div>
		<%}%>
	</logic:present>
</div>

	<html:form action="saveMotivo" styleId="parametrosMotivoForm">
		<div class="modal-body px-3 mx-3 px-lg-3 mx-lg-5 mt-5">
			<div class="row px-5 mx-5">
				<div class="col-sm-12">
					<h1 class="font-weight-300">
						<span id="modalDelegadoNuevoModif"></span> Motivo
					</h1>
				</div>
				<div class="col-sm-12 pt-1 pb-5 text-muted">Ingres&aacute; los
					datos del motivo.</div>
			</div>
			<div class="row px-5 mx-5">
				<div class="col-sm-12 pt-2 scroll-err">
					<div class="has-float-label">
						<html:text property="codigo" styleId="codigo" styleClass="form-control bg-light text-uppercase" maxlength="4" onkeypress="return numericOnly(event);"/>
						 <label for="modalDelegadoUsuario">Motivo</label>
						 <div class="errorDivCod"></div>
					</div>
				</div>
				<div class="col-sm-12 col-lg-6 pt-2  has-float-label scroll-err">
					<div class="has-float-label">
						<html:text property="descripcion" styleClass="form-control bg-light text-uppercase"  maxlength="50"/>
						<label for="modalDelegadoCCostos">Descripci&oacute;n</label>
						<div class="errorDiv"></div>
					</div>
				</div>
				<div
					class="col-sm-12 col-lg-6 pt-2 pl-lg-1 has-float-label scroll-err">
					<div class="has-float-label form-group">
						<html:select property="estado" styleId="estado"  styleClass="form-control bg-light">
							<html:option value=""></html:option>
							<html:option value="I">Inactivo</html:option>
							<html:option value="A">Activo</html:option>
						</html:select>
						
						<i class="bbva-icon icon-uniE003 text-primary"></i> <label
							for="estado">Estado</label>
						<div class="invalid-feedback mb-3"></div>
						
						<div class="errorDiv"></div>
					</div>
				</div>

				<div class="col-sm-12 col-lg-4 pt-2  scroll-err">
					<div class="has-float-label form-group">
						<html:select property="codSup" styleId="codSup" styleClass="form-control bg-light">
							<html:option value=""></html:option>
							<html:option value="PSUP">PSUP</html:option>
						</html:select>
						
						<i class="bbva-icon icon-uniE003 text-primary"></i> <label
							for="modalDelegadoInforme">Superior</label>
						<div class="invalid-feedback mb-3"></div>
						
						<div class="errorDiv"></div>
					</div>
				</div>
				<div class="col-sm-12 col-lg-4 pt-2  pl-lg-1 scroll-err">
					<div class="has-float-label form-group">
						<html:select property="codFirma" styleId="codFirma" styleClass="form-control bg-light">
							<html:option value=""></html:option>
							<html:option value="PFIRM">PFIRM</html:option>
						</html:select>
						 <i class="bbva-icon icon-uniE003 text-primary"></i> <label
							for="codFirma">Firma</label>
						 <div class="errorDiv"></div>
					</div>
				</div>
				<div
					class="col-sm-12 col-lg-4 pt-2  pl-lg-1 has-float-label scroll-err">
					<div class="has-float-label form-group ">
						<html:text property="idGlg" styleId="idGlg" styleClass="form-control bg-light text-uppercase " maxlength="2" onkeypress="return numericOnly(event);"/>
						 <label for="modalDelegadoUsuario">Ctrl. GLG</label>
						 <div class="errorDiv"></div>
					</div>
				</div>


				<div class="col-sm-12 col-lg-4 pt-2 scroll-err">
					<div class="has-float-label form-group d-flex align-items-center  bg-light">
						<html:select property="idNivCarga" styleId="idNivCarga" styleClass="form-control bg-light">
							<html:option value=""></html:option>
							<html:option value="01">01</html:option>
							<html:option value="02">02</html:option>
							<html:option value="03">03</html:option>
							<html:option value="04">04</html:option>
							<html:option value="05">05</html:option>
							<html:option value="06">06</html:option>
							<html:option value="07">07</html:option>
							<html:option value="08">08</html:option>
							<html:option value="09">09</html:option>
							<html:option value="10">10</html:option>
						</html:select>
						<div class= "pr-3">
						 <i class="bbva-icon icon-uniE003 text-primary"></i> <label
							for="modalDelegadoAccion">Nvl. Ingreso</label>
						</div>
					</div>
					 <div class="errorDiv"></div>
				</div>

				<div class="col-sm-12 col-lg-4 pt-2 pl-lg-1 scroll-err">
					<div class="has-float-label form-group">
					
						<html:select property="idNivAutoriz" styleId="idNivAutoriz" styleClass="form-control bg-light">
							<html:option value=""></html:option>
							<html:option value="01">01</html:option>
							<html:option value="02">02</html:option>
							<html:option value="03">03</html:option>
							<html:option value="04">04</html:option>
							<html:option value="05">05</html:option>
							<html:option value="06">06</html:option>
							<html:option value="07">07</html:option>
							<html:option value="08">08</html:option>
							<html:option value="09">09</html:option>
							<html:option value="10">10</html:option>
						</html:select>
						<i class="bbva-icon icon-uniE003 text-primary"></i> <label
							for="idNivAutoriz">Nvl. Firma</label>
						 <div class="errorDiv"></div>
					</div>
				</div>


				<div class="col-sm-12 col-lg-4 pt-2 pl-lg-1 scroll-err">
					<div class="has-float-label form-group  d-flex align-items-center  bg-light">
						<html:select property="maInclExcl" styleId="maInclExcl" styleClass="form-control bg-light">
							<html:option value=""></html:option>
							<html:option value="I">Incl</html:option>
							<html:option value="E">Excl</html:option>
						</html:select>
						<div class= "pr-3">
						<i class="bbva-icon icon-uniE003 text-primary"></i> <label
							for="modalDelegadoAccion">Incl/Excl</label>
						</div>
						 <div class="errorDiv"></div>
					</div>
				</div>

				<div class="col-sm-12 col-lg-4 pt-2 scroll-err">
					<div class="has-float-label form-group">
						<html:text property="idCentroCostos" styleId="idCentroCostos" styleClass="form-control bg-light text-uppercase" maxlength="4" onkeypress="return numericOnly(event);"/>
						<label for="idCentroCostos">C.Costos</label>
						 <div class="errorDiv"></div>
					</div>
				</div>


				<div class="col-sm-12 col-lg-4 pt-2 pl-lg-1 has-float-label scroll-err">
					<div class="input-group ">
						<html:text property="fechaDesde" styleId="fechaDesde" size="8"  styleClass="form-control datepicker bg-light"/>
						<div class="input-group-append">
							<button
								class="btn btn-outline-primary bg-light border-white hover-darkblue datepicker-btn"
								tabindex="-1" type="button">
								<i class="bbva-icon icon-coronita_calendar fa-lg"></i>
							</button>
						</div>
						<label for="fechaDesde">Desde</label>
						 <div class="errorDiv"></div>
					</div>
				</div>
				
				<div class="col-sm-12 col-lg-4 pt-2 pl-lg-1 has-float-label scroll-err">
					<div class="input-group ">
						<html:text property="fechaHasta" styleId="fechaHasta" size="8" style="width:96px; color:black;" styleClass="form-control datepicker bg-light"/>
						<div class="input-group-append">
							<button
								class="btn btn-outline-primary bg-light border-white hover-darkblue datepicker-btn"
								tabindex="-1" type="button">
								<i class="bbva-icon icon-coronita_calendar fa-lg"></i>
							</button>
						</div>
						<label for="fechaHasta">Hasta</label>
						 <div class="errorDiv"></div>
						<div id="errorFechas" style="color: red;"></div>
					</div>
				</div>
				
				<div class="col-sm-12 col-lg-6 pt-2 scroll-err ">
				
					<div class="btn btn-light px-4 py-1 ">
					<label ><html:checkbox value="O" styleId="oscarO" property="oscar.o"/><span class="d-block">O</span></label>
					</div>
					<div class="btn btn-light px-4 py-1 ">
						<label><html:checkbox value="S" styleId="oscarS" property="oscar.s" /><span class="d-block">S</span></label>
					</div>
					<div class="btn btn-light px-4 py-1 " >
						<label><html:checkbox value="C" styleId="oscarC" property="oscar.c" /><span class="d-block ">C</span></label>
					</div>
					<div class="btn btn-light px-4 py-1 " >
						<label><html:checkbox value="A" styleId="oscarA" property="oscar.a" /><span class="d-block">A</span></label>
					</div>
					<div  class="btn btn-light px-4 py-1 ">
						<label><html:checkbox value="R" styleId="oscarR" property="oscar.r" /><span class="d-block">R</span></label>
					</div>
				
					<html:hidden styleId="oscarO" property="oscar.o"/>
					<html:hidden styleId="oscarS" property="oscar.s"/>
					<html:hidden styleId="oscarC" property="oscar.c"/>
					<html:hidden styleId="oscarA" property="oscar.a"/>
					<html:hidden styleId="oscarR" property="oscar.r"/>
					
					 <div class="errorDiv"></div>
				</div>
				
				<div class="col-sm-12 col-lg-6 pt-2 scroll-err d-flex align-items-center">
					<label for="meDiasInterv" class="mr-2">Centros de costos</label>
					<logic:iterate name="ParametrosMotivoForm"  property="centrosCosto" id="centrosCostoI" indexId="i">
								<%String cc="centrosCostoI[" + i + "]"; %>
								<%String ccId="centrosCosto_" + i; %>
								<html:text styleId="<%=ccId%>" property="<%=cc%>" maxlength="4" styleClass="text-primary"
									onkeypress="return numericOnly(event);"/>
								<a href="#" onclick="borrarCentroCosto(<%=i%>);" name="d_<%=cc%>">
									<!-- <img src="./images/iconos/borrar.png" alt="Eliminar" title="Eliminar" style="width:10px;"/> -->
									<i class="bbva-icon icon-coronita_trash fa-md"  title="Eliminar"></i>
								</a>
					</logic:iterate>
					<a id="addCC" href="#" onclick="agregarCentroCosto();">
						<!-- <img src="./images/iconos/add.png" alt="Agregar" title="Agregar" style="width:10px;margin-top:6px;"/> -->
						<i class="ml-2 bbva-icon icon-coronita_contract fa-md"  title="Agregar"></i>
					</a>
					<input type="hidden" name="centrosCosto" />
					<div class="invalid-feedback mb-3"></div>
				</div>
				<div class="col-sm-12 col-lg-6 pt-2 scroll-err">
					<div class="has-float-label form-group">
						<html:text property="idOperEspe" styleId="oscaidOperEspe" styleClass="form-control bg-light text-uppercase" maxlength="5"/>
						<label for="idOperEspe">Oper.Especial</label>
						 <div class="errorDiv"></div>
					</div>
				</div>

				<div class="col-sm-12 col-lg-6 pt-2 pl-lg-1 scroll-err">
					<div class="has-float-label form-group">
						<html:text property="meDiasInterv" styleId="meDiasInterv" styleClass="form-control bg-light text-uppercase" maxlength="9" onkeypress="return numericOnly(event);"/>
						<label for="meDiasInterv">Medida Cant.D&iacute;as</label>
						
					</div>
				</div>
				
				

				<div class="col-sm-12 col-lg-12 pt-2  has-float-label scroll-err">
					<div class="has-float-label">
						<html:textarea styleId="txAviso" property="txAviso" styleClass="form-control bg-light"/>
						<label for="txAviso">Aviso</label>
					</div>
				</div>

				<div class="col-sm-12 mt-4 text-right">
					<html:form action="saveMotivo" styleId="parametrosMotivoForm">
						<a href="parametrosMotivoFiltro.do"
							class="btn btn-link px-5 py-3 mr-2 font-weight-bold"> Volver
						</a>
						
						<logic:equal value="alta" name="ParametrosMotivoForm" property="accion">
							<html:submit styleClass="btn btn-info px-5 py-3 ml-2" value="Guardar"/>
						</logic:equal>
						<logic:equal value="modificacion" name="ParametrosMotivoForm" property="accion">
							<html:submit styleClass="btn btn-info px-5 py-3 ml-2" value="Guardar"/>
						</logic:equal>
						<logic:equal value="baja" name="ParametrosMotivoForm" property="accion">
							<html:button property="" styleClass="btn btn-info px-5 py-3 ml-2" onclick="confirmEliminarMotivo()" value="Eliminar"/>
						</logic:equal>
					

					</html:form>

				</div>




			</div>
		</div>
	</html:form>




	<script type="text/javascript" src="./static/js/parametrosMotivoDetalle.js"></script>

</body>

<logic:equal value="alta" name="ParametrosMotivoForm" property="accion">
	<script>
			$( document ).ready(function() {
				$('#estado').attr('disabled','disabled');
			});
		</script>
</logic:equal>

<logic:equal value="modificacion" name="ParametrosMotivoForm"
	property="accion">
	<script>
			$( document ).ready(function() {
				$('#codigo').attr('readonly', true);
			});
		</script>
</logic:equal>

<logic:equal value="baja" name="ParametrosMotivoForm" property="accion">
	<script>
			$( document ).ready(function() {
				$('input').attr('readonly', true);
				$('textarea').attr('readonly', true);
				$('select').attr('disabled','disabled');
				$('#fechaDesde').attr('disabled','disabled');
				$('#fechaHasta').attr('disabled','disabled');
				$(".ck-button").attr('disabled','disabled');
				$("[name^='oscar']").attr('disabled','disabled');
				$("[name^='d_centrosCostoI']").hide();
				$("#addCC").hide();
				$("#imageCal1").hide();
				$("#imageCal2").hide();
			});
		</script>
</logic:equal>
