<html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>
<bean:define id="ParametrosGastosForm" name="ParametrosGastosForm" scope="session" toScope="request"/>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<link rel="stylesheet" type="text/css" href="./css/validation.css">
	<link rel="stylesheet" type="text/css" href="./css/main.css">
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css">
	<link rel='stylesheet' type='text/css' href='./css/displaytag.css' />
	<link rel="stylesheet" type='text/css' href="./css/jquery-ui.structure.css" />
	<link rel="stylesheet" type='text/css' href="./css/jquery-ui.theme.css" />
	<link rel="stylesheet" type="text/css" href="./css/validation.css">
	<link rel="stylesheet" type="text/css" href="./css/buttons.css">
	<link rel="stylesheet" type="text/css" href="./css/ristraPopUp.css">
	
	<script type="text/javascript" src="./static/js/jquery.js"></script>
	<script type="text/javascript" src="./static/js/jquery-ui.js"></script>
	<script type="text/javascript" src="./static/js/jquery.ui.datepicker-es.js"></script>
	<script type="text/javascript" src="./static/js/datepicker-settings.js"></script>
	<script type="text/javascript" src="./static/js/date-calculator.js"></script>
	<script type="text/javascript" src="./static/js/jquery.validate.min.js"></script>
	
	
	
	<title>Ristra</title>
</head>
<body>
	<logic:present name="message">
		<% String message = (String) request.getAttribute("message"); 
		if(message.contains("ERROR")){
		%>
		<div id="messageErr" style="color: red; font-weight: bold; text-align: center; width: 100%; font-size: 14">
			<%= message %>
		</div>
		<%} else { %>
			<script>window.close(); window.opener.location.reload();</script>
		<%} %>
	</logic:present>

	<h2 style="text-align: center;">Ristra</h2>
	<html:form action="RistraPopUpSave" styleId="ParametrosGastosForm">
		<div>
			<div class="row d-flex justify-content-center">
				<div>
					Producto
					<html:text property="ristra.producto" style="width: 70px;" styleId="producto" maxlength="2"/>
				</div>
				<div>
					Subproducto
					<html:text property="ristra.subproducto" style="width: 70px;" styleId="subProducto" maxlength="4" />
				</div>
				<div>
					Garantía
					<html:text property="ristra.garantia" style="width: 70px;" styleId="garantia" maxlength="3" />
				</div>
				<div>
					Tipo de plazo
					<html:text property="ristra.tipoPlazo" style="width: 70px;" styleId="tipoPlazo" maxlength="1" />
				</div>
				<div>
					Plazo
					<html:text property="ristra.plazo" style="width: 70px;" styleId="plazo" maxlength="3" />
				</div>
				
				<div>
					Subsector
					<html:text property="ristra.subsector" style="width: 70px;" styleId="subsector" maxlength="1" />
				</div>
				
				<div>
					Sector B.E.
					<html:text property="ristra.sectorBE" style="width: 70px;" styleId="sectorBE" maxlength="2" />
				</div>
				<div>
					CNAE
					<html:text property="ristra.cnae" style="width: 70px;" styleId="cnae" maxlength="5" />
				</div>
			
				<div>
					Empresa tutelada
					<html:text property="ristra.empresaTutelada" style="width: 70px;" styleId="empresaTutelada" maxlength="4" />
				</div>
				
				<div>
					Ambito
					<html:text property="ristra.ambito" style="width: 70px;" styleId="ambito" maxlength="2" />
				</div>
				<div>
					Morosidad
					<html:text property="ristra.morosidad" style="width: 70px;" styleId="morosidad" maxlength="1" />
				</div>
				<div>
					Inversion
					<html:text property="ristra.inversion" style="width: 70px;" styleId="inversion" maxlength="1" />
				</div>
				
				<div>
					Operacion
					<html:text property="ristra.operacion" style="width: 70px;" styleId="operacion" maxlength="3" />
				</div>

				<div>
					Codigo contable
					<html:text property="ristra.codigoContable" style="width: 70px;" styleId="coddigoContable" maxlength="5" />
				</div>

				<div>
					Divisa
					<html:text property="ristra.divisa" style="width: 70px;" styleId="divisa" maxlength="3" />
				</div>
				<div>
					Tipo de divisa
					<html:text property="ristra.tipoDivisa" style="width: 70px;" styleId="tipoDivisa" maxlength="1" />
				</div>
				
				<div>
					Resto
					<html:text property="ristra.resto" style="width: 225px;" styleId="resto" maxlength="10" />
				</div>
	
				<div>
					Varios
					<html:text property="ristra.varios" style="width: 225px;" styleId="varios" maxlength="18" />
				</div>

			</div>

			
				<div style="text-align:center; padding-top:15px">
					<logic:notEqual value="baja" name="ParametrosGastosForm" property="accion">
						<html:submit styleClass="buttonSave" value="Guardar" styleId="saveButton" style="margin-rigth:40px"/>
					</logic:notEqual>
					<input type="button" value="Salir" onClick="window.close();" class="buttonCancel" />
				</div>	
		</div>
	</html:form>
	
	<logic:equal value="baja" name="ParametrosGastosForm" property="accion">
		<script>
			$( document ).ready(function() {
				$('input').attr('readonly', true);
			});
		</script>
	</logic:equal>
</body>
</html>