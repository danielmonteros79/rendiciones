
<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Res&uacute;menes anteriores</title>
	<link rel="stylesheet" type="text/css" href="./css/select2Personalized.css">
</head>
<body>
	<div class="bg-light" id="filtro">
		<div class="container py-3">
			<div class="row pb-3 d-none" id="messageContainer">
				<div class="col-sm-12">
					<h5 id="message"></h5>
				</div>
			</div>

			<logic:present name="message">
				<% String message = (String) request.getAttribute("message");
		
		if(message.contains("ERROR")) { %>
				<div id="messageErr" class="message">
					<%= message.substring(7) %>
				</div>
				<%} else if(message.contains("OK")) { %>
				<div id="messageOk" class="message">
					<%= message.substring(4) %>
				</div>
				<%} else {%>
				<div id="messageAviso" class="message">
					AVISO:
					<%= message %>
				</div>
				<%}%>
			</logic:present>

			<div class="row">
				<div class="col-sm-12 mb-md-3">
					<span class="font-weight-bold">B&uacute;squeda</span>
				</div>
			</div>
			<div class="row">
				<div class="col-12 py-1 py-sm-0">
					<div class="has-float-label form-group">
						<select id="filtroFecha" class="form-control" required></select> 
						 <label>Resumen</label>
						<div class="invalid-feedback mb-3"></div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12 pt-3 pt-md-3 text-center">
					<a href="#a" class="btn btn-primary px-4 py-2 mx-2"
						onclick="filtrar()">Filtrar</a>
				</div>
			</div>
		</div>
	</div>

	<div class="container pt-5 pb-2 div-resultado">
		<i class="bbva-icon icon-coronita_bullet fab fa-rotate-270 text-blue align-middle mx-2"></i>
		<small class="font-weight-bold">RESUMENES ANTERIORES</small>
	</div>

	<div class="container pb-5 div-resultado d-none" id="resumenDivResultado">

	<div class="row py-3">
		<div class="col-sm-12">
			<h2 class="font-weight-500">Resumen <span id="fechaResumen"></span></h2>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 py-3 table-responsive-lg">
			<div class="dt-container" id="resumenDtContainer">
				<h5 class="pb-3 table-message" id="consumosTableMessage"></h5>
				<display:table uid="row" name="resumen" requestURI="resumenesAnteriores.do" id="resumenTable" excludedParams="username password" pagesize="10" export="false">
				    <display:column property="fecha" class="text-center" format="{0,date,dd/MM/yyyy}" title="FECHA" />
				    <display:column property="cupon" title="CUP&Oacute;N" />
				    <display:column property="establecimiento" title="ESTABLECIMIENTO" />
				    <display:column property="monto" class="text-right nowrap" format="$ {0,number,#,##0.00}" title="MONTO" />
				    <display:column property="moneda" title="MONEDA" />
				    <display:column property="estado" title="ESTADO" />
					
					<display:setProperty name="basic.msg.empty_list" value="<h5 class='font-weight-400 dt-empty'>Tu resumen est&aacute; vac&iacute;a</h5>" />
				</display:table>
			
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">
		$(function() {
			$('#checkResumen').show();
			
			if ($('#resumen').val())
				$('#paginacion').show();
		});
		
		function fechaChange() {
			$('form').submit();
		}
	</script>

<script type="text/javascript" src="static/js/select2.min.js"></script>
<script type="text/javascript" src="static/js/tarjetaCorporativa/resumenesAnteriores.js"></script>

</body>
</html>