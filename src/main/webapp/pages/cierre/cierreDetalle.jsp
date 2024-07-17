<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>

<div class="container py-5">
	<div class="row pb-3 d-none" id="messageContainer">
		<div class="col-sm-12">
			<h5 id="message"></h5>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-12 mb-md-4">
			<i class="bbva-icon icon-coronita_bullet fab fa-rotate-270 text-blue align-middle mx-2"></i>
			<small class="font-weight-bold">CIERRE</small>
		</div>
	</div>
	<div class="row pb-3 pt-3 pb-md-4">
		<div class="col-sm-12">
			Detalles del motivo de rendici&oacute;n
		</div>
	</div>
	<div class="px-3 pb-3 font-weight-bold">
		<div class="row">
			<div class="col-sm-12 py-3 border bg-light">
				ID-Rendici&oacute;n:
				<span id="idRendicion"><bean:write name="Rendicion" property="id" /></span>
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
			
			<div class="col-sm-5 py-3 border">
				Motivo:
				<bean:write name="RendicionForm" property="motivo" />
			</div>
			<div class="col-sm-7">
				<div class="row">
					<div class="col-12 col-sm-6 py-3 border">
						Desde:
						<span id="fechaDesde"><bean:write name="RendicionForm" property="fechaDesde" /></span>
					</div>
					<div class="col-12 col-sm-6 py-3 border">
						Hasta:
						<span id="fechaHasta"><bean:write name="RendicionForm" property="fechaHasta" /></span>
					</div>
				</div>
			</div>
			
			<div class="col-12 py-3 border">
				Comentario/Observaciones:
				<bean:write name="RendicionForm" property="descripcion" />
			</div>
		</div>
	</div>
</div>

<div class="container py-2 text-left d-no-edit  mb-3" >
	<div class=" d-flex justify-content-between align-items-center flex-row">
		<div class=" d-flex flex-column ">
			<h2 id="listadoImagenesText" >Listado de im&aacute;genes</h2>
		</div>
	</div>
	<div class="  d-flex justify-content-start align-items-start flex-column "  id="containerImagenes">
		<div class="mr-3 d-flex " id="containerImagenesCargadas"></div>
	</div>

</div>


<div class="container py-5">
	<div class="row pb-3 div-resultado" id="rendicionDetalleGastosDivResultado">
		<div class="col-sm-12">
			<h2 class="font-weight-500">Listado de gastos/consumos</h2>
		</div>
	</div>
	<div class="row d-none" id="tableMessageContainer">
		<div class="col-sm-12">
			<h5 class="pb-3" id="tableMessage"></h5>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 py-3 table-responsive-lg">
			<h5 class="pb-3 table-message d-none" id="gastosTableMessage"></h5>
			<div class="dt-container" id="gastosDtContainer"></div>
		</div>
	</div>
</div>

<input type="hidden" id="estadoRend" value="<bean:write name="Rendicion" property="estado"/>" />
<input type="hidden" id="codMotivo" value="<bean:write name="RendicionForm" property="codMotivo"/>" />
<input type="hidden" id="user" value="<bean:write name="RendicionForm" property="user"/>" />
<input type="hidden" id="usuarioRend" value="<bean:write name="RendicionForm" property="user"/>" />
<input type="hidden" id="urlThuban" value="<bean:write name="RendicionForm" property="linkThuban"/>" />

<jsp:include page="../global/modalGasto.jsp" />
<jsp:include page="../global/modalDatosAdicionales.jsp" />
<jsp:include page="../global/modalCupones.jsp" />
<jsp:include page="../global/modalImagenes.jsp" />


<script type="text/javascript" src="js/cierre/cierreDetalle.js"></script>