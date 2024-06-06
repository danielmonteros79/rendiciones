
<!DOCTYPE html>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@page import="com.sa.entities.Usuario"%>

<html>
<head>
	<title><tiles:getAsString name="pageTitle" /></title>
	<link rel="shortcut icon" href="./images/favicon.ico">
	

	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/bootstrap-float-label.css">
	<link rel="stylesheet" type="text/css" href="css/bootstrap-datepicker.min.css">
	<link rel="stylesheet" type="text/css" href="css/font-awesome.css">
	<link rel="stylesheet" type="text/css" href="css/style-icons.css">
	<link rel="stylesheet" type="text/css" href="css/main.css">

	<script type="text/javascript" src="static/js/xlsx.full.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.js"></script>
	<script type="text/javascript" src="static/js/jquery.1.12.js"></script>
	<script type="text/javascript" src="static/js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script>
	<script type="text/javascript" src="static/js/localization/bootstrap-datepicker.es.min.js"></script>
	<script type="text/javascript" src="static/js/fontawesome.min.js"></script>
	<script type="text/javascript" src="static/js/autonumeric.min.js"></script>
	<script type="text/javascript" src="static/js/autonumeric-options.js"></script>
	<script type="text/javascript" src="static/js/jquery.form-validator.js" charset="utf-8"></script>
	<script type="text/javascript" src="static/js/jquery.validate.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="static/js/jquery.inputmask.bundle.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="static/js/main.js"></script>
	
	
	<% 
		Usuario userSession = (Usuario) request.getSession().getAttribute("usuario");
		Usuario userWorking = (Usuario) request.getSession().getAttribute("userWorking");
		
		if (userSession == null) { 
	%>
		<logic:present name="errorTimeOut">
			<script type="text/javascript">
				window.onload = function () {
					/* alert("Finaliz\u00f3 el tiempo de sesi\u00f3n. (TimeOut)"); */
					window.location.href = "login.jsp";
				}
			</script>
		</logic:present>
	<% } %>
</head>

<body>
	<tiles:insert attribute="header" />
	
	<div id="content" style ="min-height:86vh">
		<tiles:insert name="content">
			<tiles:put name="accion">
				<tiles:getAsString name="accion" ignore="true" />
			</tiles:put>
		</tiles:insert>
		
		<button id="scroll-btn" class="btn btn-primary" title="Scroll">
		  <i class="fas fa-arrow-down"></i>
		</button>
				
		
		<div class="modal" id="modalConfirm" tabindex="-1" role="dialog">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<a href="#a" class="float-right" data-dismiss="modal">
							<i class="bbva-icon icon-coronita_close"></i>
						</a>
					</div>
					<div class="modal-body" id="modalConfirmMsg"></div>
					<div class="modal-footer">
						<div class="row">
							<div class="col-sm-12">
								<a href="#a" class="btn btn-link px-5 py-3 font-weight-bold" data-dismiss="modal">
									<i class="bbva-icon icon-coronita_close"></i> Cancelar
								</a>
								<a href="#a" class="btn btn-info px-5 py-3 ml-2" id="modalConfirmConfirmar">
									Confirmar
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="modal" id="modalError" tabindex="-1" role="dialog">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<a href="#a" class="float-right" data-dismiss="modal">
							<i class="bbva-icon icon-coronita_close"></i>
						</a>
					</div>
					<div class="modal-body" id="modalErrorMsg"></div>
					<div class="modal-footer">
						<div class="row">
							<div class="col-sm-12">
								<a href="#a" class="btn btn-link px-5 py-3 font-weight-bold" data-dismiss="modal">
									<i class="bbva-icon icon-coronita_close"></i> Salir
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="modal" id="modalLoading" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false">
		    <div class="modal-dialog modal-sm modal-dialog-centered text-center" role="document">
				<div class="modal-content py-3 px-5">
					<h3>Procesando...</h3>
		        	<span class="bbva-icon icon-coronita_update fa-spin fa-3x w-100 text-primary"></span>
				</div>
		    </div>
		</div>
	</div>
	
	<tiles:insert attribute="footer" />
</body>
</html>