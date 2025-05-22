<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="com.sa.entities.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css"
	href="./css/select2Personalized.css">

<%
Usuario userSession = (Usuario) request.getSession().getAttribute("usuario");
if (userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL
		|| userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL_LESS_PARAMS) {
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
				<h5 class="pt-3 pb-5 text-danger" id="filtroMsgValidacion"
					style="display: none;"></h5>
			</div>
		</div>
		<div class="row">
			<div class="col-12 py-1 col-sm-4">
				<div class="has-float-label">
					<input type="text" class="form-control text-uppercase"
						id="filtroUsuario" placeholder="ID" maxlength="8" /> <label>Usuario</label>
					<p class="invalid-feedback"></p>
				</div>
			</div>
			<div class="col-12 py-1 col-sm-4 has-float-label">
				<div class="input-group">
					<input readonly type="text"
						class="form-control text-uppercase bg-muted text-muted"
						id="filtroNombre" placeholder="Nombre" /> <label>Nombre </label>
				</div>
			</div>
			
			<div class="col-12 py-1 col-sm-4 has-float-label">
				<div class="input-group">
					<input type="text" class="form-control datepicker"
						id="filtroCierre" placeholder="Desde" /> <label>Fecha de Cierre</label>
					<div class="input-group-append">
						<button
							class="btn btn-outline-primary bg-white border-white hover-darkblue datepicker-btn"
							tabindex="-1" type="button">
							<i class="bbva-icon icon-coronita_calendar fa-lg"></i>
						</button>
					</div>
				</div>
			</div>

			<div class="col-12 py-1 col-sm-4">
				<span class="has-float-label"> <input type="text"
					class="form-control text-uppercase" id="filtroMontoMin"
					placeholder="Monto Min" /> <label>Monto Min</label>
				</span>
			</div>

			<div class="col-12 py-1 col-sm-4">
				<span class="has-float-label"> <input type="text"
					class="form-control text-uppercase" id="filtroMontoMax"
					placeholder="Monto Max" /> <label>Monto Max</label>
				</span>
			</div>
			<div class="col-sm-4 py-1 scroll-err">
				<div class="has-float-label form-group">
					<select id="filtroMoneda" class="form-control basic-single"
						required></select> <label for="filtroMoneda">Moneda</label>
					<div class="invalid-feedback mb-3"></div>

				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12 pt-3 pt-md-3 text-center">
				<a href="#a" class="btn btn-primary px-4 py-2 mx-2"
					onclick="filtrar()">Filtrar</a> <a href="#a"
					class="btn btn-primary px-4 py-2 mx-2" onclick="limpiar()">Limpiar</a>
			</div>
		</div>
	</div>
</div>

<div class="container py-5 div-resultado"
	id="listadoRendicionesDivResultado">
	<div class="row">
		<div class="col-sm-12 mb-md-4">
			<i
				class="bbva-icon icon-coronita_bullet fab fa-rotate-270 text-blue align-middle mx-2"></i>
			<small class="font-weight-bold">CONSUMOS NO RENDIDOS</small>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 py-3 table-responsive-lg">
			<div class="dt-container" id="consumosNoRendidosDtContainer"></div>
		</div>
	</div>
</div>

<div class="bg-light">
	<div class="container py-4 text-center">
		<a href="#a" class="btn btn-primary px-5 py-3 mx-2"
			onclick="asignarConsumosNoRendidos()"> Asignar / Confirmar </a>
	</div>
</div>

<%-- <jsp:include page="../global/modalJournal.jsp" />
<jsp:include page="../cierre/modalSuspenderCierre.jsp" /> --%>

<script type="text/javascript" src="static/js/select2.min.js"></script>
<script type="text/javascript" src="static/js/cierre/consumosNoRendidos.js"></script>

<%
} else {
%>
No tiene permisos para ver esta p&aacute;gina
<%
}
%>