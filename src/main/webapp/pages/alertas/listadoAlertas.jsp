<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="com.sa.entities.Usuario"%>
<%@page import="com.sa.entities.Rendicion"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>


<% 
	Usuario userSession = (Usuario) request.getSession().getAttribute("usuario");
	Usuario userWorking = (Usuario) request.getSession().getAttribute("userWorking");
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
	<div class="row p-3 font-weight-bold">
		<div class="col-sm-5 py-3 border">
			Nombre usuario:
			<%= userSession.getNombre() %>
		</div>
		<div class="col-sm-7">
			<div class="row">
				<div class="col-12 col-sm-6 py-3 border">
					Id-rend:
					<bean:write name="AlertaForm" property="idRendicion" />
				</div>
				<div class="col-12 col-sm-6 py-3 border">
					Sector:
					
					<%= userWorking.getSector() %>
				</div>
			</div>
		</div>
	</div>
	
	
	<div class="row py-3 pb-md-5">
		<div class="col-sm-12">
			<h2 class="font-weight-500">Listado de alertas</h2>
		</div>
	</div>
	<div class="row d-none" id="tableMessageContainer">
		<div class="col-sm-12">
			<h5 class="pb-1" id="tableMessage"></h5>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 py-1 table-responsive-lg">
			<div class="table  table-responsive" id="listadoAlertasDtContainer">
			</div>
		</div>
	</div>
	

	<div class="d-flex justify-content-center">
		<a  class="btn btn-primary px-5 py-3 text-white" onclick="determinarRuta()" id="btnAprobar">
					Aprobaci&oacute;n
		</a>
	</div>

</div>


<script type="text/javascript" src="js/alertas/listadoAlertas.js"></script>

