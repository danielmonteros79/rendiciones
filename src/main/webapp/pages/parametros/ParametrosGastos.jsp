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
</head>

<body>


	<div class="bg-light" id="filtro">
	
		<logic:present name="message">
					<%
					String message = (String) request.getAttribute("message");

					if (message.contains("ERROR")) {
					%>
					<div id="messageErr" class="message text-danger  pt-5 text-center">
						<%=message.substring(7)%>
					</div>
					<%
					} else if (message.contains("OK")) {
					%>
					<div id="messageOk" class="message pt-5  text-center">
						<%=message.substring(4)%>
					</div>
					<%
					} else {
					%>
					<div id="messageAviso" class="message  pt-5 text-center">
						AVISO:
						<%=message%>
					</div>
					<%
					}
					%>
				</logic:present>
	
	
		<div class="container py-5">
			<div class="row pb-3 d-none" id="messageContainer">
				<div class="col-sm-12">
					<h5 id="message"></h5>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12 mb-md-4">
					<span class="font-weight-bold">Filtro Gastos</span>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<h5 class="pt-3 pb-5 text-danger" id="filtroMsgValidacion"
						style="display: none;"></h5>
				</div>
			</div>
			
			<html:form action="/parametrosGastosFiltro" styleId="parametrosGastosFiltro">
				<div class="row">
					<div class="col-12">
						<div class="has-float-label">
							<html:text property="gasto" styleId="gasto" styleClass="form-control text-uppercase" maxlength="4" onkeypress="return numericOnly(event);"/>
							<label>Gasto</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12 pt-3 pt-md-5 text-center">
					
						<html:submit style="margin-left:10px" styleClass="btn btn-primary px-5 py-3 mx-2" value="Filtrar" />
						<html:button style="margin-rigth:4.1%" property="" value="Limpiar" styleClass="btn btn-primary px-5 py-3 mx-2" onclick="resetForm();"></html:button>
						
					</div>
				</div>
			</html:form>
		</div>
	</div>



	<div class="container py-5 div-resultado"
		id="listadoRendicionesDivResultado">
		<div class="row">
			<div class="col-sm-12 mb-md-4">
				<i
					class="bbva-icon icon-coronita_bullet fab fa-rotate-270 text-blue align-middle mx-2"></i>
				<small class="font-weight-bold">Gastos</small>
			</div>
		</div>
		<div class="row py-3">
			<div class="col-sm-12">
				<h2 class="font-weight-500">Listado de Gastos</h2>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12 py-3 table-responsive-lg">
				<div class="dt-container" id="gastosDtContainer"></div>
			</div>
		</div>
	</div>



	<div id="paginacion" class="text-center pb-5" style="margin-top: 43px;">
		<display:table uid="row" name="gastos"
			requestURI="parametrosGastosFiltro.do" id="ParametrosGastosTable"
			excludedParams="false"
			decorator="com.sa.decorator.parametros.ParametrosGastosTableDecorator"
			pagesize="15" style="margin-left:-0.9%;width:99.7%;" export="true">
			<display:column media="html csv excel" property="gasto" title="Gasto"
				style="text-align:right;" sortable="true" />
			<display:column media="html csv excel" property="descripcionGasto"
				title="Descripción de Gasto" />
			<display:column media="html csv excel" property="motivo"
				title="Motivo" style="text-align:right;" sortable="true" />
			<display:column media="html csv excel" property="descripcionMotivo"
				title="Descripción de Motivo" />
			<display:column media="html csv excel" property="ristra"
				title="Ristra" style="text-align:left;" />
			<display:column media="html csv excel" property="bimon" title="Bimon"
				style="text-align:center;" />
			<display:column media="html csv excel" property="comprob"
				title="Comprob" />
			<display:column media="html csv excel" property="autoriz"
				title="Autoriz" />
			<display:column media="html csv excel" property="observ"
				title="Observación" />
			<display:column media="html csv excel" property="estado"
				title="Estado" style="text-align:center;" />
			<display:column media="html" property="opciones" title="Opciones"
				style="width:4%" />

			<display:setProperty name="export.excel.filename"
				value="ListadoParametrosGastos.xls" />
			<display:setProperty name="export.csv.filename"
				value="ListadoParametrosGastos.csv" />
		</display:table>
	</div>


	<div class="bg-light">
		<div class="container py-5 text-center">
			<div class="row">
				<div class="col-sm-12">
					<h2 class="font-weight-400">&iquest;Quer&eacute;s dar de alta
						a un gasto?</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12 pt-3 pt-md-5">
					<html:form action="parametrosGastosDetalle" styleId="addGasto">
						<input type="hidden" name="accion" value="alta" />
						<input type="hidden" name="back" value="false" />
						<a href="#" onclick="agregarGasto()"
							class="btn btn-info px-5 py-3" title="Alta de motivo">
							Agregar nuevo gasto </a>
					</html:form>

				</div>
			</div>




		</div>
	</div>

	<script type="text/javascript" src="./static/js/parametrosGastos.js"></script>
</body>
</html>