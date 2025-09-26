<%@page import="org.apache.struts.action.ActionForm"%>
<%@ page import="org.apache.commons.text.StringEscapeUtils" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
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
	
	<link rel="stylesheet" type="text/css" href="./css/validation.css">
	<link rel="stylesheet" type="text/css" href="./css/Parametros.css">
	<style>td {white-space:nowrap;text-align:left;}</style>
</head>
<script>
var motivo = "<%=org.apache.commons.text.StringEscapeUtils.escapeEcmaScript(request.getSession().getAttribute("cod_motivo").toString())%>";
var descMotivo = "<%=org.apache.commons.text.StringEscapeUtils.escapeEcmaScript(request.getSession().getAttribute("desc_motivo").toString())%>";
var gasto = "<%=org.apache.commons.text.StringEscapeUtils.escapeEcmaScript(request.getSession().getAttribute("cod_gasto").toString())%>";
</script>
<body>	
	<!-- -------------------------------------- -->
	<div class="mt-5 mb-1 pt-3 container text-center">

		<logic:present name="message">
			<% String message = (String) request.getAttribute("message");
			if(message.contains("ERROR")) { %>
				<div id="messageErr"  class="message text-danger text-center">
					<%= message.substring(7) %>
				</div>
			<%} else if(message.contains("OK")) { %>
				<div id="messageOk" class="message text-warinign h5">
					<%= message.substring(4) %>
				</div>
			<%} else {%>
				<div id="messageAviso" class="message text-center">
					AVISO: <%= message %>
				</div>
			<%}%>
		</logic:present>
	
	</div>
	
	
	
	
	<html:form action="saveAlerta" styleId="parametrosAlertasForm">
	<div class="py-5">
				<div class="row px-5 mx-5">
					<div class="col-sm-12">
						<h1 class="font-weight-300"><span id="modalDelegadoNuevoModif"></span>Alerta</h1>
					</div>
					<div class="col-sm-12 pt-1 pb-5 text-muted">
						Ingres&aacute; los datos de la alerta.
					</div>
				</div>
				<div class="row px-5 mx-5">
				
					<div class="col-sm-12 pt-2 col-lg-4  scroll-err">
						<div class="has-float-label">
						<html:text property="codAlerta" styleId="codAlerta" styleClass="form-control bg-light text-uppercase" maxlength="4" onkeypress="return numericOnly(event);"/>
							<label for="codAlerta">Alerta</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					
					<div class="col-sm-12 pt-2 col-lg-4  scroll-err">

						<div class="has-float-label form-group scroll-err d-flex align-items-center  bg-light ">
<!-- 							<select id="filtroMotivo" property="codMotivo"  class="form-control bg-light"></select> -->
<!-- 							<i class="bbva-icon icon-uniE003 text-primary"></i> <label>Motivo</label> -->
							
							<html:select property="codMotivo" styleId="codMotivo" styleClass="form-control bg-light">
							</html:select>
							<div class= "pr-3">
							<i class="bbva-icon icon-uniE003 text-primary"></i>
							<label for="codMotivo">Motivo</label>
							</div>
						</div>
						
					</div>
					
					<div class="col-sm-12 pt-2 col-lg-4  scroll-err">
						<div class="has-float-label form-group scroll-err">					
				
<!-- 							<select id="filtroGasto" property="codGasto"  class="form-control bg-light"></select> -->
<!-- 							<i class="bbva-icon icon-uniE003 text-primary"></i> <label>Gasto</label> -->
								
								<html:select property="codGasto" styleId="gasto" styleClass="form-control bg-light">
								</html:select>
								<i class="bbva-icon icon-uniE003 text-primary"></i>
								<label for="codGasto">Gasto</label>

						</div>
					</div>
					
					<div class="col-sm-12 col-lg-3 pt-2  has-float-label">
						<div class="has-float-label form-group scroll-err">
							<html:select property="rend" styleClass="form-control bg-light">
								<html:option value=""></html:option>
								<html:option value="REND">Rendici&oacute;n</html:option>
								<html:option value="PROM">Promedio</html:option>
							</html:select>
							<i class="bbva-icon icon-uniE003 text-primary"></i>
							<label for="rend">Rendici&oacute;n</label>
						</div>
					</div>

					<div class="col-sm-6 pt-2 col-lg-3 pl-lg-1 scroll-err">
						<div class="has-float-label form-group">
						<html:select property="montCant" styleId="montCant" styleClass="form-control bg-light" onchange="impCantChange();">
							<html:option value=""></html:option>
							<html:option value="M">Monto</html:option>
							<html:option value="C">Cantidad</html:option>
						</html:select>
							<i class="bbva-icon icon-uniE003 text-primary"></i>
							<label for="montCant">Monto/Cantidad</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
						
					</div>
					
					<div class="col-sm-6 col-lg-3 pt-2 pl-lg-1 has-float-label scroll-err">
						<div class="has-float-label">
						<html:text property="impCant" styleId="impCant" onkeypress="return numericOnly(event);" styleClass="form-control bg-light" /> 
							<label for="impCant">Valor</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>					
					
					<div class="col-sm-12 pt-2 col-lg-3 pl-lg-1 scroll-err">
						<div class="has-float-label form-group">
						<html:select property="periodo" styleClass="form-control bg-light">
							<html:option value=""></html:option>
							<html:option value="DI">Diario</html:option>
							<html:option value="SE">Semanal</html:option>
							<html:option value="ME">Mensual</html:option>
							<html:option value="BI">Bimestral</html:option>
							<html:option value="TR">Trimestral</html:option>
							<html:option value="CU">Cuatrimestral</html:option>
							<html:option value="SM">Semestral</html:option>
							<html:option value="AN">Anual</html:option>
						</html:select>
							<i class="bbva-icon icon-uniE003 text-primary"></i>
							<label for="periodo">Per&iacute;odo</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>				
					
					<div class="col-sm-12 pt-2 col-lg-4  scroll-err">
						<div class="has-float-label form-group">
						<html:select property="criticidad" styleClass="form-control bg-light">
							<html:option value=""></html:option>
							<html:option value="1">Riesgo Grave</html:option>
							<html:option value="2">Riesgo</html:option>
							<html:option value="3">Inc. Grave</html:option>
							<html:option value="4">Incidencia</html:option>
							<html:option value="5">Anomal&iacute;a</html:option>
						</html:select> 
							<i class="bbva-icon icon-uniE003 text-primary"></i>
							<label for="criticidad">Criticidad</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					
					<div class="col-sm-6 pt-2 col-lg-4  scroll-err">
						<div class="has-float-label form-group">
						<html:select property="nivMax" styleId="nivMax" styleClass="form-control bg-light" >
							<html:option value=""></html:option>
							<html:option value="0">0</html:option>
							<html:option value="1">1</html:option>
							<html:option value="2">2</html:option>
							<html:option value="3">3</html:option>
							<html:option value="4">4</html:option>
							<html:option value="5">5</html:option>
							<html:option value="6">6</html:option>
							<html:option value="7">7</html:option>
							<html:option value="8">8</html:option>
							<html:option value="9">9</html:option>
						</html:select>
						
								<i class="bbva-icon icon-uniE003 text-primary"></i>
								<label for="nivMax">Nivel M&aacute;ximo</label>
								<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					
					<div class="col-sm-6 pt-2 col-lg-4 pl-lg-1 scroll-err">
						<div class="has-float-label form-group">
						<html:select property="nivMin" styleId="nivMin" styleClass="form-control bg-light">
							<html:option value=""></html:option>
							<html:option value="0">0</html:option>
							<html:option value="1">1</html:option>
							<html:option value="2">2</html:option>
							<html:option value="3">3</html:option>
							<html:option value="4">4</html:option>
							<html:option value="5">5</html:option>
							<html:option value="6">6</html:option>
							<html:option value="7">7</html:option>
							<html:option value="8">8</html:option>
							<html:option value="9">9</html:option>
						</html:select> 
								<i class="bbva-icon icon-uniE003 text-primary"></i>
								<label for="nivMin">Nivel M&iacute;nimo</label>
								<div class="invalid-feedback mb-3"></div>
								
								<div id="errorImpCant" style="color:red;"></div>
								<div id="errorNiveles" style="color:red;"></div>
						</div>
					</div>
					
					<div class="col-sm-12 col-lg-12 pt-2  has-float-label scroll-err">
						<div class="has-float-label">
							<textarea id="txAviso" name="txAviso" class="form-control bg-light">
            <c:out value="${txAviso}" />
        </textarea>
							<label for="txAviso">Aviso</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					
					<script>
					    const textarea = document.getElementById('txAviso');
					    const errorDiv = document.getElementById('avisoError');
					    const counter = document.getElementById('contadorAviso');
					    const max = 50;
					
					    textarea.addEventListener('input', () => {
					        let value = textarea.value;
					
					        if (value.length > max) {
					            // Recorta el texto automaticamente
					            textarea.value = value.substring(0, max);
					            errorDiv.style.display = 'block';
					            textarea.classList.add('is-invalid');
					        } else {
					            errorDiv.style.display = 'none';
					            textarea.classList.remove('is-invalid');
					        }
					
					        counter.textContent = `${textarea.value.length} / ${max}`;
					    });
					
					    window.addEventListener('DOMContentLoaded', () => {
					        errorDiv.style.display = 'none';
					        counter.textContent = `${textarea.value.length} / ${max}`;
					    });
					</script>
					
					<div class="col-sm-12 col-lg-3 pt-2  has-float-label">
					<div class="has-float-label form-group">
						
						<html:select property="estado" styleId="estado" styleClass="form-control bg-light">
							<html:option value=""></html:option>
							<html:option value="I">Inactivo</html:option>
							<html:option value="A">Activo</html:option>
						</html:select>
						<i class="bbva-icon icon-uniE003 text-primary"></i> <label
							for="estado">Estado</label>
						<div class="invalid-feedback mb-3"></div>
					</div>
					</div>
					
					<div class="col-sm-12 text-right mt-4 ">
				
						<a href="javascript:history.back()" class="btn btn-link px-5 py-3 mr-2 font-weight-bold" >
							 Volver
						</a>
						
						<logic:notEqual value="baja" name="ParametrosAlertasForm" property="accion">
					 		<html:submit styleClass="btn btn-info px-5 py-3 ml-2" value="Guardar" />	
						</logic:notEqual>
						<logic:equal value="baja" name="ParametrosAlertasForm" property="accion">
							<html:button property="" styleClass="btn btn-info px-5 py-3 ml-2" onclick="confirmarEliminarAlerta()" value="Eliminar"/>
						</logic:equal>
						
					</div>					
				
					</div>
				</div>
				</html:form> 
				
		
		
		
	
	
		

	
	
	
	
	
	<logic:equal value="alta" name="ParametrosAlertasForm" property="accion">
		<script>
			$( document ).ready(function() {
				//NO PUEDO INHABILITAR EL SELECT DE MOTIVO PORQUE AFECTA AL FORMULARIO
				$('#codAlerta').attr('readonly', true);
				$('#estado').attr('disabled','disabled');
				$('#codMotivo').append("<option selected>" + motivo + " - " + descMotivo + "</option>");
				setCombo('combos.do?action=getTiposGasto', "select[name='codGasto']", {codMotivo: motivo});
			});
		</script>
	</logic:equal>
	<logic:equal value="baja" name="ParametrosAlertasForm" property="accion">
		<script>
			$( document ).ready(function() {
				$('input').attr('readonly', true);
				$('textarea').attr('readonly', true);
				$('select').attr('disabled','disabled');
				$('#codMotivo').append("<option selected>" + motivo + " - " + descMotivo + "</option>");
				$('#gasto').append("<option selected>" + gasto + "</option>");
			});
		</script>
	</logic:equal>
	
	<logic:equal value="modificacion" name="ParametrosAlertasForm" property="accion">
		<script>
			$( document ).ready(function() {
				$('#codAlerta').attr('readOnly',true);
				$('#estado').attr('disabled','disabled');
				$('#codMotivo').append("<option selected>" + motivo + " - " + descMotivo + "</option>");
				$('#gasto').append("<option selected>" + gasto + "</option>");
			});
		</script>
	</logic:equal>
	
	<script type="text/javascript" src="./static/js/parametrosAlertasDetalle.js"></script>
</body>
</html>