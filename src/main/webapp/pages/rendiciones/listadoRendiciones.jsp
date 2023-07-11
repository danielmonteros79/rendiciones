<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>


<div class="bg-light" id="divFiltro">
	<div class="container py-3">
		<div class="row pb-3 d-none" id="messageContainer">
			<div class="col-sm-12">
				<h5 id="message"></h5>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12 mb-md-2">
				<span class="font-weight-bold">B&uacute;squeda</span>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<h5 class="pt-3 pb-5 text-danger" id="filtroMsgValidacion" style="display:none;"></h5>
			</div>
		</div>
		<div class="row">
			<div class="col-12 py-1 py-sm-0 col-sm-4">
				<span class="has-float-label">
					<input type="text" class="form-control an-integer-pos" id="filtroId" placeholder="ID"/>
					<label>ID</label>
				</span>
			</div>
			<div class="col-12 py-1 py-sm-0 col-sm-4 has-float-label">
				<div class="input-group">
					<input type="text" class="form-control datepicker" id="filtroFechaDesde" placeholder="Desde"/>
					<label>Desde</label>
					<div class="input-group-append">
						<button class="btn btn-outline-primary bg-white border-white hover-darkblue datepicker-btn" tabindex="-1" type="button">
							<i class="bbva-icon icon-coronita_calendar fa-lg"></i>
						</button>
					</div>
				</div>
			</div>
			<div class="col-12 py-1 py-sm-0 col-sm-4 has-float-label">
				<div class="input-group">
					<input type="text" class="form-control datepicker" id="filtroFechaHasta" placeholder="Desde"/>
					<label>Hasta</label>
					<div class="input-group-append">
						<button class="btn btn-outline-primary bg-white border-white hover-darkblue datepicker-btn" tabindex="-1" type="button">
							<i class="bbva-icon icon-coronita_calendar fa-lg"></i>
						</button>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12 pt-3 pt-md-3 text-center">
				<a href="#a" class="btn btn-primary px-4 py-2 mx-2" onclick="filtrar()">Filtrar</a>
				<a href="#a" class="btn btn-primary  px-4 py-2 mx-2" onclick="limpiar()">Limpiar</a>
			</div>
		</div>
	</div>
</div>

	<div class="container py-3 div-resultado" id="listadoRendicionesDivResultado">
	<%-- 	<div class="row" align="right">
			<div class="col-sm-12 pt-3 pt-md-5">
				<html:link action="altaRendicion.do" styleId="altaRend" styleClass="btn btn-info px-5 py-3">
					Alta Rendici&oacute;n
				</html:link>
			</div>
		</div> --%>

		<div class="row">
			<div class="col-sm-12 mb-md-3 ">
				<i class="bbva-icon icon-coronita_bullet fab fa-rotate-270 text-blue align-middle mx-2"></i>
				<small class="font-weight-bold">RENDICIONES</small>
			</div>
			<div class="col-sm-12" align="right">
				<html:link action="altaRendicion.do" styleId="altaRend" styleClass="btn btn-info px-5 py-3">
					Nueva Rendici&oacute;n
				</html:link>
			</div>
		</div>
		<div class="row py-3">
			<div class="col-sm-12">
				<h2 class="font-weight-500">Tu listado de rendiciones de gastos</h2>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12 py-3 table-responsive-lg">
				<div class="dt-container" id="rendicionesDtContainer"></div>
			</div>
		</div>
	</div>

<jsp:include page="../global/modalAlerta.jsp" />

<%-- <div class="bg-light">
	<div class="container py-3 text-center">
		<div class="row">
			<div class="col-sm-12">
				<h3 class="font-weight-400 mt-2">&iquest;Quer&eacute;s hacer una rendici&oacute;n de gastos?</h3>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12 pt-3 py-md-2  ">
				<html:link action="altaRendicion.do" styleId="altaRend" styleClass="btn btn-info px-5 py-3">
					Agregar nueva rendici&oacute;n
				</html:link>
			</div>
		</div>
	</div>
</div> --%>



<script type="text/javascript" src="js/rendiciones/listadoRendiciones.js"></script>
