<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="com.sa.entities.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css" href="./css/select2Personalized.css">

<% 
	Usuario userSession = (Usuario) request.getSession().getAttribute("usuario");
	if (userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL ||
		userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL_LESS_PARAMS) { 
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
			<div class="col-12 py-1 col-sm-4">
				<span class="has-float-label">
					<input type="text" class="form-control an-integer-pos" id="filtroId" placeholder="ID"/>
					<label>ID</label>
				</span>
			</div>
			<div class="col-12 py-1 col-sm-4 has-float-label">
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
			<div class="col-12 py-1 col-sm-4 has-float-label">
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
			<div class="col-12 py-1 col-sm-4">
				<span class="has-float-label">
					<input type="text" class="form-control text-uppercase" id="filtroUsuario" placeholder="Usuario" maxlength="8"/>
					<label>Usuario</label>
				</span>
			</div>

			<div class="col-12 py-1 col-sm-8 ">
				<div class="has-float-label form-group">
					<select id="filtroMotivo" class="form-control"></select>
					<label>Motivo</label>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12 pt-3 pt-md-3 text-center">
				<a href="#a" class="btn btn-primary px-4 py-2 mx-2" onclick="filtrar()">Filtrar</a>
				<a href="#a" class="btn btn-primary px-4 py-2 mx-2" onclick="limpiar()">Limpiar</a>
			</div>
		</div>
	</div>
</div>

<div class="container py-5 div-resultado" id="listadoRendicionesDivResultado">
	<div class="row">
		<div class="col-sm-12 mb-md-4">
			<i class="bbva-icon icon-coronita_bullet fab fa-rotate-270 text-blue align-middle mx-2"></i>
			<small class="font-weight-bold">CIERRE &Oacute;RDEN DE PAGO</small>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 py-3 table-responsive-lg">
			<div class="dt-container" id="cierreOrdenDePagoDtContainer"></div>
		</div>
	</div>
</div>

<div class="bg-light">
	<div class="container py-4 text-center">
		<a href="#a" class="btn btn-primary px-5 py-3 mx-2" onclick="generarCierre()">
			Generar
		</a>
		<a href="#a" class="btn btn-primary px-5 py-3 mx-2" onclick="suspenderCierre()">
			Suspender
		</a>
	</div>
</div>

<jsp:include page="../global/modalJournal.jsp" />
<jsp:include page="../cierre/modalSuspenderCierre.jsp" />

<script type="text/javascript" src="js/select2.min.js"></script>
<script type="text/javascript" src="js/cierre/cierreOrdenDePago.js"></script>
<% } else { %>
	No tiene permisos para ver esta p&aacute;gina
<% } %>