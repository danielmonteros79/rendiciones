<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>
<bean:define id="ParametrosGastosForm" name="ParametrosGastosForm" scope="session" toScope="request"/>




<html:form action="RistraPopUpSave" styleId="ParametrosGastosForm">
<div class="modal fade" id="modalGastos" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-xl" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<a href="#a" class="float-right" data-dismiss="modal">
					<i class="bbva-icon icon-coronita_close"></i>
				</a>
			</div>
			<div class="modal-body px-3 mx-3 px-lg-3 mx-lg-5">
				<div class="row px-5 mx-5">
					<div class="col-sm-12">
						<h1 class="font-weight-300"><span id="modalGastosNuevoModif"></span> Ristra</h1>
					</div>
					<div class="col-sm-12 pt-1 pb-5 text-muted">
						Ingres&aacute; los datos de la ristra.
					</div>
				</div>
				<div class="row px-5 mx-5">
					<div class="col-sm-12 col-lg-6 pt-2 scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light text-uppercase" id="modalGastosProducto" placeholder="Producto"  />
							<label for="modalGastosProducto">Producto</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-sm-12 col-lg-6 pt-2 pl-lg-1 scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light" id="modalGastosCCostos" placeholder="C. Costos"  />
							<label for="modalGastosCCostos">Subproducto</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-sm-12 col-lg-4 pt-2  scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light" id="modalGastosSector" placeholder="Sector" />
							<label for="modalGastosSector">Garant&iacute;a</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-sm-12 col-lg-4 pt-2 pl-lg-1 scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light" id="modalGastosEstado" placeholder="Estado"  />
							<label for="modalGastosEstado">Tipo de plazo</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-lg-4 col-sm-12 pt-2 pl-lg-1 scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light" id="modalGastosNombre" placeholder="Nombre"   />
							<label for="modalGastosNombre">Plazo</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-lg-4 col-sm-12 pt-2 scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light" id="modalGastosNombre" placeholder="Nombre" />
							<label for="modalGastosNombre">Subsector</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-lg-4 col-sm-12 pt-2 pl-lg-1 scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light" id="modalGastosNombre" placeholder="Nombre" />
							<label for="modalGastosNombre">Sector B.E.</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-lg-4 col-sm-12 pt-2 pl-lg-1 scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light" id="modalGastosNombre" placeholder="Nombre" />
							<label for="modalGastosNombre">CNAE</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-lg-4 col-sm-12 pt-2 scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light" id="modalGastosNombre" placeholder="Nombre"  />
							<label for="modalGastosNombre">Empresa tutelada</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-lg-4 col-sm-12 pt-2 pl-lg-1 scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light" id="modalGastosNombre" placeholder="Nombre" />
							<label for="modalGastosNombre">&Aacute;mbito</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-sm-12 col-lg-4 pt-2 pl-lg-1 scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light" id="modalGastosNombre" placeholder="Nombre" />
							<label for="modalGastosNombre">Morosidad</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-sm-12 col-lg-4 pt-2 scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light" id="modalGastosNombre" placeholder="Nombre" />
							<label for="modalGastosNombre">Inversi&oacute;n</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-lg-4 col-sm-12 pt-2 pl-lg-1  scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light" id="modalGastosNombre" placeholder="Nombre" />
							<label for="modalGastosNombre">Operaci&oacute;n</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-lg-4 col-sm-12 pt-2 pl-lg-1  scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light" id="modalGastosNombre" placeholder="Nombre" />
							<label for="modalGastosNombre">C&oacute;digo contable</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-sm-12 col-lg-4 pt-2 scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light" id="modalGastosNombre" placeholder="Nombre"  />
							<label for="modalGastosNombre">Divisa</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-lg-4 col-sm-12 pt-2 pl-lg-1 scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light" id="modalGastosNombre" placeholder="Nombre" />
							<label for="modalGastosNombre">Tipo de Divisa</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-lg-4 col-sm-12 pt-2 pl-lg-1 scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light" id="modalGastosNombre" placeholder="Nombre" />
							<label for="modalGastosNombre">Resto</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-lg-12 col-sm-12 pt-2 scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light" id="modalGastosNombre" placeholder="Nombre" />
							<label for="modalGastosNombre">Varios</label>
						</div>
					</div>
					
				</div>
				<input type="hidden" id="modalGastosFechaDesdeOld">
				<input type="hidden" id="modalGastosFechaHastaOld">
				<input type="hidden" id="modalGastosUserAlta">
				<input type="hidden" id="modalGastosFechaAlta">
			</div>
			<div class="modal-footer px-3 mx-3 px-lg-3 mx-lg-5">
				<div class="row px-5 mx-5">
					<div class="col-sm-12">
						<a href="#a" class="btn btn-link px-5 py-3 mr-2 font-weight-bold" data-dismiss="modal">
							<i class="bbva-icon icon-coronita_close"></i> Salir
						</a>
						<logic:notEqual value="baja" name="ParametrosGastosForm" property="accion">
							<html:submit styleClass="btn btn-info px-5 py-3 ml-2" value="Guardar" onclick="modalGastosGuardar()" styleId="saveButton" />
						</logic:notEqual>	
							
					</div>
				</div>
			</div>
			
		</div>
	</div>
</div>
</html:form>

<logic:equal value="baja" name="ParametrosGastosForm" property="accion">
		<script>
			$( document ).ready(function() {
				$('input').attr('readonly', true);
			});
		</script>
</logic:equal>

<script type="text/javascript" src="js/modalRistra.js"></script>