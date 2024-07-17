<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="com.sa.entities.Rendicion"%>
<%@ page import="java.util.*"%>
<script type="text/javascript">
$(document).ready(function() {
	var usuarioAprobador = "<%= request.getAttribute("usuarioAprobador") %>";
	var estadoRend = "<%= request.getAttribute("estadoRend") %>";
	var motivoRend = "<%= request.getAttribute("motivoRend") %>";
	var rendExceptuada = "<%= request.getAttribute("rendExceptuada") %>";
	
	//Verifica si existe aprobador o no en esta etapa de la rendicion
	if(usuarioAprobador != "SI"){
		$('#aprobador').hide();	
	}
	
	//Verifica el motivo para generar el preformato de fechas
	if(motivoRend.includes('EVENTO J.A.E.')){
		$('#fechaDesdeTitle').html("Realizacion del evento:");
		$('#fechaHastaDiv').html("");
	}
// 	else if(motivoRend.includes('GYMPASS')){
// 		$('#fechaDesdeTitle').html("Mes del gasto:");
// 		$('#fechaHastaDiv').html("");
// 	}
	else if(motivoRend.includes('MOVILIDAD EN HUELGA')){
		$('#fechaDesdeTitle').html("Fecha de compra aereo:");
		$('#fechaHastaDiv').html("");
	}
	else if(motivoRend.includes('VIAJE GLOMO/FORMACION PDI')){
		$('#fechaDesdeTitle').html("Inicio del viaje:");
		$('#fechaHastaTitle').html("Fin del viaje:");
	}
	
	//Verifica si la rendicion esta exceptuada para tildar o no el casillero
	if(rendExceptuada.includes("true")){
		$("#exc-check").prop("checked", true);
	}
	else{
		$("#exc-check").prop("checked", false);
	}
	
	//Verifica el estado de la rendicion para saber si debe deshabilitar el casillero
	if(estadoRend != "PENDI"){
		$("#exc-check").prop("disabled", true);
	}
});
</script>
<bean:define id="RendicionForm" name="RendicionForm" scope="session" toScope="request" />




<div class="container py-5">
	<div class="row pb-3 d-none" id="messageContainer">
		<div class="col-sm-12">
			<h5 id="message"></h5>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-12 mb-md-4">
			<i class="bbva-icon icon-coronita_bullet fab fa-rotate-270 text-blue align-middle mx-2"></i>
			<small class="font-weight-bold">RENDICIONES</small>
		</div>
	</div>
	<div class="row pb-3 pt-3 pb-md-4">
		<div id="subtitle" class="col-sm-12">
			<h2 class="font-weight-500 d-no-edit">Carg&aacute; los datos de gastos/consumos asociados al motivo</h2>
			<h2 class="font-weight-500 d-none d-edit">Modific&aacute; la rendici&oacute;n y presion&aacute; Finalizar modificaci&oacute;n</h2>
		</div>
	</div>
	<div class="row pb-3 pt-3 pb-md-4">
		<div class="col-sm-12">
			Detalles del motivo de rendici&oacute;n
		</div>
	</div>
	<div id="editRendicion" class="px-3 pb-3 font-weight-bold">
		<div class="row">
			<div class="col-sm-12 py-3 border  bg-light" >
				ID-Rendici&oacute;n:
				<span id="idRendicion"><bean:write name="Rendicion" property="id" /></span>
			</div>
		</div>
		<div class="row d-none">
			<div class="col-sm-12 py-3 border bg-warning">
				Aviso:
				<span id="aviso"><bean:write name="Rendicion" property="aviso" /></span>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-5 py-3 border">
				Usuario:
				<span id="usuario"><bean:write name="RendicionForm" property="nombreUsuario" /></span>
			</div>
			<div class="col-sm-7">
				<div class="row">
					<div class="col-12 col-sm-6 py-3 border">
						C. Costo Usuario:
						<span id="cCostos"><bean:write name="RendicionForm" property="costos" /></span>
					</div>
					<div class="col-12 col-sm-6 py-3 border">
						Sector:
						<span id="sector"><bean:write name="RendicionForm" property="sector" /></span>
					</div>
				</div>
			</div>
			
			<div class="col-5 py-3 border d-no-edit" id="estadoDiv">
				Estado de rendici&oacute;n:
				<bean:write name="RendicionForm" property="descripcionEstado" />
			</div>
			
			<div class="col-7 py-3 border d-no-edit">
				<span id="aprobador">Pr&oacute;ximo Aprobador:
				<bean:write name="RendicionForm" property="usuarioAprobador" /></span>
			</div>
			
			<div class="col-sm-5 py-3 border-left d-no-edit motivo-div">
				Motivo:
				<bean:write name="RendicionForm" property="motivo" />
			</div>
			<div class="col-sm-5 py-3 border-left d-none d-edit motivo-div">
				<div class="has-float-label form-group">
					<select id="editRendicionMotivo" class="form-control bg-light"  required></select>
					<i class="bbva-icon icon-uniE003 text-primary"></i>
					<label>Motivo</label>
				
					<div class="invalid-feedback mb-3"></div>
				</div>
			</div>
			<div class="col-sm-7">
				<div class="row">
					<div class="col-12 col-sm-6 py-3 border d-no-edit" id="fechaDesdeDiv">
						<span id="fechaDesdeTitle">Desde:</span>
						<span id="fechaDesde"><bean:write name="RendicionForm" property="fechaDesde" /></span>
					</div>
					<div class="col-12 col-sm-6 py-3 border d-no-edit" id="fechaHastaDiv">
						<span id="fechaHastaTitle">Hasta:</span>
						<span id="fechaHasta"><bean:write name="RendicionForm" property="fechaHasta" /></span>
					</div>
					<div class="col-12 col-sm-6 py-3 has-float-label d-none d-edit">
						<div class="input-group">
							<input type="text" class="form-control datepicker bg-light" id="editRendicionFechaDesde" placeholder="Desde" required/>
							<label>Desde</label>
							<div class="input-group-append">
								<button class="btn btn-outline-primary bg-light border-white hover-darkblue datepicker-btn" tabindex="-1" type="button">
									<i class="bbva-icon icon-coronita_calendar fa-lg"></i>
								</button>
							</div>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-12 col-sm-6 py-3 border-right has-float-label d-none d-edit">
						<div class="input-group">
							<input type="text" class="form-control datepicker bg-light" id="editRendicionFechaHasta" placeholder="Desde" required/>
							<label>Hasta</label>
							<div class="input-group-append">
								<button class="btn btn-outline-primary bg-light border-white hover-darkblue datepicker-btn" tabindex="-1" type="button">
									<i class="bbva-icon icon-coronita_calendar fa-lg"></i>
								</button>
							</div>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
				</div>
			</div>

			<div class="col-5 py-3 border d-no-edit">
				Cantidad de d&iacute;as:
				<bean:write name="RendicionForm" property="cantDias" />
			</div>
			<div class="col-7 py-3 border d-no-edit">
				&Uacute;ltima modificaci&oacute;n:
				<bean:write name="RendicionForm" property="fechaUltimaModificacion" format="dd/MM/yyyy" />
			</div>
			<div class="col-12 py-3 border d-no-edit">
				Descripci&oacute;n/Observaciones:
				<span id="descripcion"><bean:write name="RendicionForm" property="descripcion" /></span>
			</div>
			<div id="mostrarMensajeRend" class="col-12 py-3 border  d-none ">
				<span class="" id="descripcionRechazo"></span>
			</div>
			  
			  
			<div class="col-12 py-3 border-right border-bottom border-left d-edit d-none">
				<div class="has-float-label">
					<textarea class="form-control bg-light" id="editRendicionDescripcion" maxlength="120" required
						placeholder="(120 caracteres)" rows="5"></textarea>
					<label>Ingres&aacute; una observaci&oacute;n</label>
					<div class="invalid-feedback mb-3"></div>
				</div>
			</div>

			<div class=" bg-light p-3 mt-4 cursor-pointer" id="checkbox-container">
				<input class="m-2 cursor-pointer" type="checkbox" value=""
					id="exc-check"  > 
					<label class="form-check-label cursor-pointer" for="flexCheckChecked">Excepci&oacute;n solicitada a la GLG. Se debe agregar la documentaci&oacute;n respaldatoria de lo consensuado. </label>
			</div>
		</div>
	</div>
</div>


	<div class="bg-light d-no-edit" id="divNuevoGasto">
				<div class="container py-3 text-center">
					<div class="row d-none d-est-PENDI">
						<div class="col-sm-12 pb-2 pb-md-2">
							<h3 class="font-weight-400">Carg&aacute; los gastos
								seg&uacute;n el medio de pago</h3>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12">
							<a href="#a"
								class="btn btn-primary px-5 py-3 m-2 d-none d-est-PENDI"
								onclick="nuevoGasto(0)"> Efectivo </a> <a href="#a"
								class="btn btn-primary p-3 m-2 d-none d-est-PENDI"
								onclick="nuevoGasto(1)"> Tarjeta corporativa </a>
						</div>
					</div>
				</div>
			</div>


<div class="container py-5 d-no-edit">
	<div class="row pb-2">
		<div class="col-sm-12">
			<h2 class="font-weight-500">Tu listado de gastos/consumos</h2>

		<div class="row d-none" id="tableMessageContainer">
			<div class="col-sm-12">
				<h5 class="pb-1" id="tableMessage"></h5>
			</div>
		</div>
		
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 py-3 table-responsive-lg">
			<h5 class="pb-3 table-message d-none" id="gastosTableMessage"></h5>
			<div class="dt-container" id="gastosDtContainer"></div>
		</div>
	</div>
</div>

	<logic:present name="readonly">
	<div class="container py-2 text-left d-no-edit  mb-3" >
		<div class=" d-flex justify-content-between align-items-center flex-row">
			<div class=" d-flex flex-column ">
				<h2 id="listadoImagenesText" >Listado de im&aacute;genes</h2>
			</div>
		</div>
		<div class="d-flex justify-content-start align-items-start flex-column"  id="containerImagenes">
			<div class="mr-3 d-flex " id="containerImagenesCargadas"></div>
		</div>	
		<button class="btn btn-light" id="abrirTodas">Ver todas</button>	
	</div>
	</logic:present>
	<logic:notPresent name="readonly">
		<div class="container py-2 text-left d-no-edit  mb-3" >
			<div class=" d-flex justify-content-between align-items-center flex-row">
			
				<div class=" d-flex flex-column ">
					<h2 id="listadoImagenesText" >Listado de im&aacute;genes</h2>
					<p id="mensajeImgRend" class=" border border-top-0 border-left-0 border-right-0 border-warning p-1">Agreg&aacute; las im&aacute;genes correspondientes a la rendici&oacute;n.</p>
				</div>
				<div class="text-center bg-warning" id="containerImgBtn">
					<a href="#a" class="btn btn-primary px-4 py-2 m-2 d-no-edit" id="imagenesBtn"  onclick="openImagenes()" >
					Adjuntar Im&aacute;genes
					</a>
				</div>
		
			</div>
			<div class="  d-flex justify-content-start align-items-start flex-column "  id="containerImagenes">
				<div class="mr-3 d-flex " id="containerImagenesCargadas"></div>
			</div>
			<button class="btn btn-light" id="abrirTodas">Ver todas</button>	
		</div>
		<div id="imagenesContainer"></div>
	</logic:notPresent>

<div class="bg-light" id="divAcciones">
	<div class="container py-3 text-center">
		<div class="row">
			<div class="col-sm-12">
				<a href="#a" class="btn btn-primary px-5 py-3 m-2 d-none d-no-edit d-est-ESCAN d-est-PSUP d-est-PFIRM d-est-OBSER"
					onclick="activarRechazarRendicion('rechazar')">
					Rechazar rendici&oacute;n
				</a>
				<a href="#a" class="btn btn-primary px-5 py-3 m-2 d-none d-no-edit d-est-RECHA" onclick="activarRechazarRendicion('reactivar')">
					Reactivar rendici&oacute;n
				</a>
				<!-- <a href="#a" class="btn btn-primary px-5 py-3 m-2 d-none d-no-edit d-est-PENDI d-est-OBSER" onclick="modificarRendicion()">
					Modificar rendici&oacute;n
				</a> -->
				<a href="#a" class="btn btn-primary px-5 py-3 m-2 d-none d-edit" onclick="cancelarModificacion()">
					Cancelar modificaci&oacute;n
				</a>
				<a href="#a" class="btn btn-primary px-5 py-3 m-2 d-none d-edit" onclick="finalizarModificacion()">
					Finalizar modificaci&oacute;n
				</a>
				<a href="#a" class="btn btn-primary px-5 py-3 m-2 d-none d-no-edit d-est-OBSER" onclick="finalizarObservacion()">
					Finalizar observaci&oacute;n
				</a>
			</div>
		</div>
	</div>
</div>



<!-- <div class="container py-5 d-no-edit"> -->
<!-- 	<div class="row pb-3"> -->
<!-- 		<div class="col-sm-12"> -->
<!-- 			<h2 class="font-weight-500">Consumos pendientes</h2> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- 	<div class="row d-none" id="tableConsumosMessageContainer"> -->
<!-- 		<div class="col-sm-12"> -->
<!-- 			<h5 class="pb-3" id="tableConsumosMessage"></h5> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- 	<div class="row"> -->
<!-- 		<div class="col-md-12 py-3 table-responsive-lg"> -->
<!-- 			<div class="dt-container" id="consumosPendientesDtContainer"></div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- </div> -->

<div class="container py-2 text-left d-no-edit  mb-3" >
			<div class=" d-flex justify-content-between align-items-center flex-row">
				<div class=" d-flex flex-column "></div>
				<div class="text-center d-none" id="containerSaveBtn">
					<a href="#a" class="btn btn-primary px-4 py-2 m-2 d-no-edit" id="saveRenBtn"  onclick="validarRend()" >
					Generar
					</a>
				</div>
			</div>	
		</div>

<input type="hidden" id="estadoRend" value="<bean:write name="RendicionForm" property="estadoRend"/>" />
<input type="hidden" id="codMotivo" value="<bean:write name="RendicionForm" property="codMotivo"/>" />
<input type="hidden" id="user" value="<bean:write name="RendicionForm" property="user"/>" />
<input type="hidden" id="urlThuban" value="<bean:write name="RendicionForm" property="linkThuban"/>" />
<input type="hidden" id="idu" value="<bean:write name="Rendicion" property="idu"/>" />
<input type="hidden" id="nombreUsuarioRend" value="<bean:write name="RendicionForm" property="nombreUsuario"/>" />
<input type="hidden" id="gastoFechaMin" value="<bean:write name="RendicionForm" property="gastoFechaMin"/>" />
<input type="hidden" id="gastoFechaMax" value="<bean:write name="RendicionForm" property="gastoFechaMax"/>" />
<input type="hidden" id="costosDestino" value="<bean:write name="RendicionForm" property="costosDestino"/>" />

<input type="hidden" id="motivoRechazo" value="<bean:write name="Rendicion" property="motivoRechazo" /> "/>

<jsp:include page="../global/modalGasto.jsp" />
<jsp:include page="../global/modalDatosAdicionales.jsp" />
<jsp:include page="../global/modalCupones.jsp" />
<jsp:include page="../global/modalImagenes.jsp" />
<jsp:include page="../global/modalGastoFueraDePolitica.jsp" />
<jsp:include page="../global/modalRendicionFueraDePolitica.jsp" />
<jsp:include page="../global/modalAlerta.jsp" />

<script type="text/javascript" src="static/js/rendiciones/rendicionDetalleGastos.js"></script>