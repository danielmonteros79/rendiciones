<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="com.sa.entities.Usuario"%>

<% 
	Usuario userSession = (Usuario) request.getSession().getAttribute("usuario");
%>

<div class="container pt-5">
	<div class="row">
		<div class="col-sm-12 mb-md-4">
			<i class="bbva-icon icon-coronita_bullet fab fa-rotate-270 text-blue align-middle mx-2"></i>
			<small class="font-weight-bold">ALERTAS</small>
		</div>
	</div>
	
</div>

<div class="container pt-3 pb-5 div-resultado" id="listadoDelegadosDivResultado">
	<div class="row py-3 pb-md-5">
		<div class="col-sm-12">
			<h2 class="font-weight-500">Detalle de Alerta</h2>
		</div>
	</div>
	<div class="row d-none" id="tableMessageContainer">
		<div class="col-sm-12">
			<h5 class="pb-1" id="tableMessage"></h5>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 py-1 table-responsive-lg">
			<div class="table  table-responsive" id="detalleAlertaDtContainer">
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="js/alertas/detalleAlerta.js"></script>