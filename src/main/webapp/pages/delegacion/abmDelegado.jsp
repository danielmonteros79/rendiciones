<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="com.sa.entities.Usuario"%>

<% 
	Usuario userSession = (Usuario) request.getSession().getAttribute("usuario");
%>

<div class="container pt-5">
	<div class="row">
		<div class="col-sm-12 mb-md-3">
			<i class="bbva-icon icon-coronita_bullet fab fa-rotate-270 text-blue align-middle mx-2"></i>
			<small class="font-weight-bold">DELEGADOS</small>
		</div>
	</div>
	<div class="row p-2 font-weight-bold">
		<div class="col-sm-5 py-3 border">
			Usuario:
			<%= userSession.getNombre() %>
		</div>
		<div class="col-sm-7">
			<div class="row">
				<div class="col-12 col-sm-6 py-3 border">
					C. Costo Usuario:
					<%= userSession.getCcostos() %>
				</div>
				<div class="col-12 col-sm-6 py-3 border">
					Sector:
					<%= userSession.getSector() %>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="container mb-5 div-resultado" id="listadoDelegadosDivResultado">
	<div class="row py-3 pb-md-2">
		<div class="col-sm-12">
			<h2 class="font-weight-500">Tu listado de delegados</h2>
		</div>
	</div>
	<div class="row d-none" id="tableMessageContainer">
		<div class="col-sm-12">
			<h5 class="pb-1" id="tableMessage"></h5>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 py-2 table-responsive-lg">
			<div class="dt-container" id="delegadosDtContainer"></div>
		</div>
	</div>
</div>

<div class="bg-light">
	<div class="container py-4 text-center">
		<a href="#a" class="btn btn-primary px-5 py-3 mx-2" onclick="nuevoDelegado()">
			Agregar delegado
		</a>
	</div>
</div>

<jsp:include page="modalDelegado.jsp" />

<script type="text/javascript" src="static/js/delegacion/abmDelegado.js"></script>