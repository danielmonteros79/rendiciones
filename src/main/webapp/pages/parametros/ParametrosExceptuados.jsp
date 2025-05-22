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
	<html:form action="parametrosExceptuadosFiltro"
		styleId="parametrosExceptuadosFiltro">
		<div class="filtros" id="parametrosExceptuadosFiltro">
			<div class="bg-light" id="filtro">

				<logic:present name="message">
					<%
					String message = (String) request.getAttribute("message");

					if (message.contains("ERROR")) {
					%>
					<%-- <div id="messageErr" class="message  text-danger  pt-5 text-center">
						<%=message.substring(7)%>
					</div> --%>
					<%
					} else if (message.contains("OK")) {
					%>
					<div id="messageOk" class="message pt-5  text-success text-center">
						<%=message.substring(4)%>
					</div>
					<%
					} else {
					%>
					<div id="messageAviso" class="message  text-warning pt-5 text-center">
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
							<span class="font-weight-bold">Filtro Exceptuados</span>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12">
							<h5 class="pt-3 pb-5 text-danger" id="filtroMsgValidacion"
								style="display: none;"></h5>
						</div>
					</div>
		
						<div class="row">
							<div class="col-12">
								<div class="has-float-label">
									<html:text property="exceptuadoFiltro" styleClass="form-control text-uppercase" styleId="exceptuadoFiltro" maxlength="8" style="text-transform:uppercase" />
									<label>Exceptuado</label>
									<div class="invalid-feedback mb-3"></div>
								</div>
							</div>
							<div class="col-12 pt-3 d-flex ">						
								<div class=pr-3>
									Motivo
									<html:radio property="motivoUsuario" value="motivo"
										onchange="habilitar();" />
								</div>
								<div>
									Usuario
									<html:radio property="motivoUsuario" value="usuario"
										onchange="habilitar();" />
								</div>
								<html:hidden styleId="motivoUsuario" property="motivoUsuario"/>
							</div>

						</div>
				
					<div class="row">
						<div class="col-sm-12 pt-3 pt-md-5 text-center">
							<a href="#a" class="btn btn-primary px-5 py-3 mx-2" onclick="filtrar()">Filtrar</a>
							<%-- <html:submit styleClass="buttonFilter" styleClass="btn btn-primary px-5 py-3 mx-2" value="Filtrar"  onclick="filtrar()"/> --%>
							<html:button styleClass="btn btn-primary px-5 py-3 mx-2"
								value="Limpiar" property="" onclick="limpiar()" />
						</div>
					</div>
				</div>
			</div>
			</div>
	</html:form>

	<div class="container py-5 div-resultado"
		id="listadoRendicionesDivResultado">
		<div class="row">
			<div class="col-sm-12 mb-md-4">
				<i
					class="bbva-icon icon-coronita_bullet fab fa-rotate-270 text-blue align-middle mx-2"></i>
				<small class="font-weight-bold">Exceptuados</small>
			</div>
		</div>
		<div class="row py-3" id="exceptuadoDivResultado"> 
			<div class="col-sm-12">
				<h2 class="font-weight-500">Listado de Exceptuados</h2>
				<div class="dt-container" id="exceptuadosDtContainer"></div> 
			</div>
			
		</div>

	</div>


	<%-- <div id="paginacion" class="container pb-5" >
		<div class="py-4 table  table-responsive">
			<display:table uid="row" name="exceptuados"
				requestURI="parametrosExceptuadosFiltro.do"
				id="ParametrosExceptuadosTable" excludedParams="false"
				decorator="com.sa.decorator.parametros.ParametrosExceptuadosTableDecorator"
				pagesize="15" class="w-100" export="true" >
				<display:column media="html csv excel" property="motivoUsuario"
					title="Motivo/Usuario"  sortable="true"/>
				<display:column media="html csv excel" property="descripcionNombre"
					title="Descripción/Nombre"  />
				<display:column media="html csv excel" property="hasta" title="Hasta"
					format="{0,date,dd/MM/yyyy}"  />
				<display:column media="html csv excel" property="desde" title="Desde"
					format="{0,date,dd/MM/yyyy}" />
				<display:column media="html csv excel" property="estado"
					title="Estado" />
				<display:column media="html" property="opciones" title="Opciones" />
				<display:setProperty name="export.excel.filename"
					value="ListadoParametrosExceptuados.xls" />
				<display:setProperty name="export.csv.filename"
					value="ListadoParametrosExceptuados.csv" />
			</display:table>
		</div>
	</div>  --%>

	<html:form action="parametrosExceptuadosDetalle"
		styleId="addExceptuado">
		<div class="bg-light">
			<div class="container py-5 text-center">
				<div class="row">
					<div class="col-sm-12">
						<h2 class="font-weight-400">&iquest;Quer&eacute;s dar de alta
							a un Exceptuado?</h2>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12 pt-3 pt-md-5">
						<html:form action="parametrosMotivoDetalle" styleId="addMotivo">
							<input type="hidden" name="accion" value="alta" />
							<a href="#" onclick="agregarExceptuado()" title="Alta de motivo"
								class="btn btn-info px-5 py-3"> Agregar nuevo exceptuado </a>
						</html:form>

					</div>
				</div>
			</div>
		</div>
	</html:form>



	<script type="text/javascript" src="./static/js/parametrosExceptuados.js"></script>
</body>
</html>


