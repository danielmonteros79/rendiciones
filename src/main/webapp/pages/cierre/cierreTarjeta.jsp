<%@page import="com.sa.entities.*"%>

<% 
	Usuario userSession = (Usuario) request.getSession().getAttribute("usuario");
	if (userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL ||
		userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL_LESS_PARAMS) { 
%>
<div class="bg-light" id="filtro">
	<div class="container py-5">
		<div class="row pb-3 d-none" id="messageContainer">
			<div class="col-sm-12">
				<h5 id="message"></h5>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12 mb-md-4">
				<span class="font-weight-bold">B&uacute;squeda</span>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<h5 class="pt-3 pb-5 text-danger" id="filtroMsgValidacion" style="display:none;"></h5>
			</div>
		</div>
		<div class="row">
			<div class="col-12">
				<div class="has-float-label">
					<input type="text" class="form-control text-uppercase" id="filtroUsuario" placeholder="Usuario" maxlength="8" required/>
					<label>Usuario</label>
					<div class="invalid-feedback mb-3"></div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12 pt-3 pt-md-5 text-center">
				<a href="#a" class="btn btn-primary px-5 py-3 mx-2" onclick="filtrar()">Filtrar</a>
				<a href="#a" class="btn btn-primary px-5 py-3 mx-2" onclick="limpiar()">Limpiar</a>
			</div>
		</div>
	</div>
</div>

<div class="container py-5 d-none div-resultado" id="listadoRendicionesDivResultado">
	<div class="row">
		<div class="col-sm-12 mb-md-4">
			<i class="bbva-icon icon-coronita_bullet fab fa-rotate-270 text-blue align-middle mx-2"></i>
			<small class="font-weight-bold">CONSUMOS</small>
		</div>
	</div>
	<div class="row px-3 font-weight-bold">
		<div class="col-sm-6 py-3 border">
			Total consumos en pesos:
			<span id="totalPesos"></span>
		</div>
		<div class="col-sm-6 py-3 border">
			Total consumos en d&oacute;lares:
			<span id="totalDolares"></span>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 py-3 table-responsive-lg">
			<div class="dt-container" id="cierreTarjetaDtContainer"></div>
		</div>
	</div>
</div>

<div class="bg-light d-none div-resultado">
	<div class="container py-5 text-center">
		<a href="#a" class="btn btn-primary px-5 py-3 mx-2" onclick="generarCierre()">
			Generar
		</a>
	</div>
</div>

<jsp:include page="../cierre/modalGenerarCierreTarjeta.jsp" />

<script type="text/javascript" src="static/js/cierre/cierreTarjeta.js"></script>
<% } else { %>
	No tiene permisos para ver esta p&aacute;gina
<% } %>