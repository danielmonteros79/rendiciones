<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="com.sa.entities.*"%>

<link rel="stylesheet" type="text/css" href="./css/select2Personalized.css">

<% 
	Usuario userSession = (Usuario) request.getSession().getAttribute("usuario");
	if (userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL ||
		userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL_LESS_PARAMS_CIERRE ||
		userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL_LESS_CIERRE ||
		userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL_LESS_PARAMS ||
		userSession.getTipoPerfil() == TipoPerfil.VIEW_APROBACION) { 
%>
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
			<div class="col-lg-3 py-1 py-sm-0 col-sm-6">
				<span class="has-float-label">
					<input type="text" class="form-control an-integer-pos" id="filtroId" placeholder="ID"/>
					<label>ID</label>
				</span>
			</div>
			<div class="col-lg-3 py-1 py-sm-0 col-sm-6">
				<span class="has-float-label">
					<input type="text" class="form-control text-uppercase" id="filtroUsuario" placeholder="Usuario" maxlength="8"/>
					<label>Usuario</label>
				</span>
			</div>
			<div class="col-lg-3 py-1 py-sm-0 col-sm-6 mt-sm-3 mt-lg-0 ">
				<div class=" form-group   has-float-label">
					<select id="filtroMotivo" class="form-control "></select>
					<label>Motivo</label>
				</div>
			</div>
			<div class="col-lg-3 py-1 py-sm-0 col-sm-6 mt-sm-3 mt-lg-0 ">
				<div class=" form-group  has-float-label" >
					<select id="filtroAlerta" class=" form-control basic-single2 " >
						<option value="">Seleccione una opci&oacute;n</option>
						<option value="0">Sin Alerta</option>
						<option value="1">Riesgo</option>
					</select>
					<label for="filtroAlerta">Tipo Alerta</label>
				</div>
			</div>
			
		</div>
		<div class="row">
			<div class="col-sm-12 pt-3 pt-md-3 text-center">
				<a href="#a" class="btn btn-primary px-4 py-2 mx-2 btn-personalized"  onclick="filtrar()">Filtrar</a>
				<a href="#a" class="btn btn-primary px-4 py-2 mx-2" onclick="limpiar()">Limpiar</a>
			</div>
		</div>
	</div>
</div>

<div class="container py-5 div-resultado" id="listadoAprobacionesDivResultado">
	<div class="row">
		<div class="col-sm-12 mb-md-4">
			<i class="bbva-icon icon-coronita_bullet fab fa-rotate-270 text-blue align-middle mx-2"></i>
			<small id="aprobacionOculta" class="font-weight-bold d-inline">APROBACI&Oacute;N</small>
		</div>
	</div>
	<div class="row py-3 pb-md-2">
		<div class="col-sm-12">
			<h2 class="font-weight-500">Rendiciones pendientes de aprobaci&oacute;n: <bean:write name="cantRendiciones" /></h2>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 py-3 table-responsive-lg">
			<a href="#a" class="btn btn-light px-3 mt-1 float-right mb-4" onclick="seleccionarTodo()">
					Seleccionar todo
			</a>
			<div class="dt-container" id="aprobacionesDtContainer" ></div>
		</div>
	</div>
</div>

<div class="bg-light">
	<div class="container py-4 text-center">
		<a href="#a" class="btn btn-primary px-5 py-3 mx-2" onclick="aprobarRendiciones()">
			Aprobar
		</a>
	</div>
</div>

<input type="hidden" id="glg" value="<bean:write name="glg"/>" />

<jsp:include page="../global/modalJournal.jsp" />
<jsp:include page="../global/modalAlerta.jsp" />

<script type="text/javascript" src="js/select2.min.js"></script>
<script type="text/javascript" src="js/aprobacion/listadoAprobaciones.js"></script>
<% } else { %>
	No tiene permisos para ver esta p&aacute;gina
<% } %>