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
</head>
<script>
var codigo = "<%=request.getSession().getAttribute("cod_motivo")%>";
</script>
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
	
	
		<div class="container py-2">
			<div class="row pb-3 d-none" id="messageContainer">
				<div class="col-sm-12">
					<h5 id="message"></h5>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12 my-2">
					<span class="font-weight-bold">B&uacute;squeda</span>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<h5 class="pt-3 pb-5 text-danger" id="filtroMsgValidacion"
						style="display: none;"></h5>
				</div>
			</div>
			
			<html:form action="parametrosGastos" styleId="parametrosGastosFiltro">
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
					<div class="col-sm-12 pt-3 pb-2 text-center">
					
						<html:submit styleClass="btn btn-primary px-4 py-2 mr-3 "
						style="margin-left:10px;" value="Filtrar" />
						<html:button style="margin-rigth:4.1%" property="" value="Limpiar" styleClass="btn btn-primary px-4 py-2 mx-2" onclick="resetForm();"></html:button>
						
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
			<div class="col-sm-12 text-right">
					<html:form action="parametrosGastosDetalle" styleId="addGasto">
						<input type="hidden" name="accion" value="alta" />
						<input type="hidden" name="back" value="false" />
						<a href="#" onclick="agregarGasto()"
							class="btn btn-info px-5 py-3" title="Alta de motivo">
							Agregar Gasto </a>
					</html:form>

				</div>
		</div>
		<div class="row py-3">
			<div class="col-sm-12">
				<h2 class="font-weight-500">Listado de Gastos</h2>
			</div>
		</div>		
			<div class="row">
				<div class="col-md-12 py-3 table-responsive-lg" id="paginacion">
				<div class="dt-container" id="parametrosGastosDtContainer"></div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="./static/js/parametrosGastos.js"></script>
</body>
</html>