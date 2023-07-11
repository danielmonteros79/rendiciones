<html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="com.sa.entities.Rendicion"%>
<%@ page import="java.util.*"%>

<bean:define id="RendicionForm" name="RendicionForm" scope="session" toScope="request" />

<head>
	<script type="text/javascript" src="./static/js/rendicionDetalleGastos.js"></script>
	<script type="text/javascript" src="./static/js/cronologiaEstados.js"></script>
	
	<link rel="stylesheet" type="text/css" href="./css/rendicionDetalle.css">
	<link rel="stylesheet" type="text/css" href="./css/validation.css">
	<link rel="stylesheet" type="text/css" href="./css/detalleGastos.css">
	<script>
		$(document).ready(function() {
			document.getElementById("BotonManual").style.display = "";
			document.getElementById("BotonPreguntas").style.display = "";

			$("#ayudaMotivo").attr("href", "ayuda/ayuda_" + $("#codMotivo").val() + ".pdf");
			
			var msg = "<%= request.getSession().getAttribute("msg") %>";
			if (msg != "null") {
				alert(msg);
			}
			<% request.getSession().removeAttribute("msg"); %>
		});
		
		function screenLoadImages() {
			window.location.href = "rendicionAviso.do" +
				"?action=mostrarPantalla" +
				"&codigo=" + $('#idRendicion').val() +
				"&codMotivo=" + $('#codMotivo').val() +
				"&estadoRend=" + $('#estadoRendicion').val();
		}
		
		function generateCaratula() {
			window.location.href = "rendicionAviso.do?generate=anymode&rnd="+$('#idRendicion').val();
		}
		function activarRechazarRendicion() {
			var r = confirm("¿Desea cambiar el estado de esta rendición?");
			if (r == true) {
				window.location.href = "activarRechazarRendicion.do" +
		         	"?codigo=" + $('#idRendicion').val() +
		         	"&estado=" + $('#estadoRendicion').val();;
			} else {
			}
			
		}
	</script>
	<logic:equal value="1" name="trxOk">
		<script>
			$(document).ready(function() {		
		        if (window.opener != null && !window.opener.closed) {
		            window.opener.HideModalDiv();
		            
		            window.opener.location.href="mostrarDetalleGastos.do" +
		            	"?codigo=" + $('#idRendicion').val() +
		            	"&codMotivo=" + $('#codMotivo').val() +
		            	"&estadoRend=" + $('#estadoRendicion').val();
			}});
		    window.onunload = OnClose;
	    </script>
	</logic:equal>
	<logic:equal value="1" name="modif">
		<script>
			jQuery(document).ready(function() {
				$('#checkRendiciones').hide();
				$('#checkAprobacion').show();
			});
		</script>
	</logic:equal>
	<style>
		span.ui-dialog-title {
			text-align: center;
			margin-left: 4%;
		}
	</style>
</head>
<body>
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
				AVISO: <%= message %>
			</div>
		<%}%>
	</logic:present>

	<logic:present name="Rendicion">
		<div id="overlay"></div>
		<input type="hidden" id="estadoRendicion" value='<bean:write name="estadoRend"/>' />
		
		<div id="divBackground"></div>
		<div id="estadoCronolog" style="display: none;"></div>
		<div id="estadoView" style="display: none;"> 
			<bean:write name="estadoRend"/> 
		</div>
		<div style="position: relative; text-align: center;">
			<div id="circulo1"></div>
			<img alt="" src="./images/iconos/right-arrow.png" />
			<div id="circulo2"></div>
			<img alt="" src="./images/iconos/right-arrow.png" />
			<div id="circulo3"></div>
			<img alt="" src="./images/iconos/right-arrow.png" />
			<div id="circulo4"></div>
			<img alt="" src="./images/iconos/right-arrow.png" />
			
			<logic:equal value="RECHA" name="estadoRend">
				<div id="circuloVacio"></div>
			</logic:equal>
			<logic:notEqual value="RECHA" name="estadoRend">
				<div id="circulo5"></div>
			</logic:notEqual>
		</div>

		<html:form action="RendicionLoad" styleId="RendicionForm">
		<html:text property="linkThuban" style="display:none;" styleId="linkThuban"/>
			<fieldset>
			<table style="height: 60%;">
			<thead>
				<tr>
					<th colspan="4">Detalle Rendici&oacute;n</th>
				</tr>
		    </thead>
		    <tbody>
				<tr>
					<td colspan="3">
						ID-Rendición:
						<html:text styleClass="green" property="idRendicion" styleId="idRendicion" readonly="true" />
					</td>
					<td>
						<logic:present name="showCaratula">
							<html:button styleClass="buttonSave" property="" value="Generar Caratula" onclick="generateCaratula()" />
						</logic:present>
						<logic:notEqual value="PENDI" name="estadoRend">
							<a href="#" onclick="thubanOpen()">
								<img id="imgAdjuntadas" src="./images/iconos/photo-48.png" title="IM&Aacute;GENES ADJUNTADAS"
										 alt="Im&aacute;genes Adjuntadas" height="36" width="36"
										 style="position: absolute; margin-top: -14px;margin-left:145px;">
							</a>
						</logic:notEqual>
					</td>
				</tr>
				<logic:equal value="PENDI" name="estadoRend">
					<logic:equal value="si" name="avisoRendicion">
						<tr>
							<td colspan="4">
						  		<h2 style="text-align:center;margin:0;">AVISO</h2>
						  		<div id="avisoTextArea"><%=request.getAttribute("avisoMostrar") %></div>
						  	</td>
						</tr>
					</logic:equal>
				</logic:equal>
				<tr>
					<td>
						<html:hidden property="user" />
						Usuario:
						<html:text styleClass="green" property="nombreUsuario" readonly="true"  style="width:250px;"/>
					</td>
		        	<td colspan="2">
		        		C.Costos:
		        		<html:text styleClass="green" property="costos" styleId="Costos" readonly="true" style="width:100px;" />
		        		<html:hidden property="costosDestino" styleId="costosDestino"/>
		        	</td>
		        	<td>
		        		Sector:
		        		<html:text styleClass="green" property="sector" styleId="Sector" readonly="true" style="width:100px;" />
		        	</td>
				</tr>
				<tr>
		    		<logic:equal value="SI" name="usuarioAprobador">
		  				<td style="font-size:small;font-weight: bold;">
		        			Usuario aprobador:
		  					<html:text styleClass="green" property="usuarioAprobador" readonly="true" />
						</td>
					</logic:equal>
					<td colspan="3" style="font-size:small;font-weight: bold;">
		 				Estado rendici&oacute;n:
		 				<html:text styleClass="green" property="descripcionEstado" readonly="true" style="width:200px;" />
		 			</td>
				</tr>
				<tr>
		        	<td>RENDICION</td>
		        	<td colspan="2" style="text-align:center; padding-right:30px;">PERIODO</td>
		        	<td>
		     			<logic:equal value="si" name="ultimaModif">
		     				<logic:equal value="ORDPG" name="estadoRend">
		     					Acreditaci&oacute;n:
		     				</logic:equal>
		     				<logic:notEqual value="ORDPG" name="estadoRend">
		     					Ult Modif:
		     				</logic:notEqual>
		  					<html:text styleClass="green" property="fechaUltimaModificacion" readonly="true" style="width:100px;" />
		  				</logic:equal>
		  			</td>
		  		</tr>
		  		<tr>
		        	<html:hidden property="codMotivo" styleId="codMotivo" />
		        	<td>
		        		<label for="motivo">Motivo:</label>
		        		<span class="green" style="font-weight:initial;">
							<bean:write name="RendicionForm" property="motivo"/>
						</span>
		        		<a id="ayudaMotivo" target="_blank">
							<img id="ayudaMotivoImg" width="15px" src='./images/iconos/question-mark-2-48.png' 
								alt='Ayuda Motivo' title="AYUDA MOTIVO"
								border='0' style="margin-left: 5px;" align="top" />
						</a>
		        	</td>
		        	<td>
		        		<label for="fechaDesde">Desde:</label>
		        		<html:text property="fechaDesde" size="7" readonly="true" styleClass="fechaDDMMYY green" styleId="fechaDesdeDisable" />
		        	</td>
					<td style="padding-right: 30px;">
						<label for="fechaHasta">Hasta:</label>
						<html:text property="fechaHasta" size="7" readonly="true" styleClass="fechaDDMMYY green" styleId="fechaHastaDisable" />
					</td>
					<td>
						<label for="cantDias">Cantidad D&iacute;as:</label>
						<html:text styleClass="green" property="cantDias" size="8" readonly="true" />
					</td>
				</tr>
				<tr>
		        	<td colspan="4">Descripci&oacute;n/Observaciones:</td>
				</tr>
				<tr>
		        	<td colspan="4">
		        		<html:textarea styleClass="green" property="descripcion" onkeyup="cantCaracteres(120)" styleId="descripcion" readonly="true"/>
		        	</td>
				</tr>
				<logic:notEqual value="No" name="motivoRechAprob">
				
				<logic:equal value="RECHA" name="motivoRechAprob">
					<tr>
						<td align="left" colspan="4">Motivo del rechazo:</td>
					</tr>
				</logic:equal>
					
				<logic:equal value="APROB" name="motivoRechAprob">
					<tr>
						<td align="left" colspan="4">Motivo de Aprobaci&oacute;n:</td>
					</tr>
				</logic:equal>
					<tr>
						<td align="left" colspan="4">
		        			<html:textarea styleClass="green" property="motivoRechazo" styleId="descripcionRechazo" readonly="true"/>
		        		</td>
					</tr>
				</logic:notEqual>
				
				
			</tbody>
			</table>
			</fieldset>

				<logic:equal value="RECHA" name="estadoRend">
						<html:button styleClass="buttonActivarRechazar" property="" value="Reactivar rendici&oacute;n" onclick="activarRechazarRendicion()" />
				</logic:equal>
					
<!-- 				MODIFICAR TODOS A LA VEZ	 -->
				<logic:equal value="ESCAN" name="estadoRend">
						<html:button styleClass="buttonActivarRechazar" property="" value="Rechazar rendici&oacute;n" onclick="activarRechazarRendicion()" />
				</logic:equal>
				<logic:equal value="PSUP " name="estadoRend">
						<html:button styleClass="buttonActivarRechazar" property="" value="Rechazar rendici&oacute;n" onclick="activarRechazarRendicion()" />
				</logic:equal>
				<logic:equal value="PFIRM" name="estadoRend">
						<html:button styleClass="buttonActivarRechazar" property="" value="Rechazar rendici&oacute;n" onclick="activarRechazarRendicion()" />
				</logic:equal>
				<logic:equal value="OBSER" name="estadoRend">
						<html:button styleClass="buttonActivarRechazar" property="" value="Rechazar rendici&oacute;n" onclick="activarRechazarRendicion()" />
				</logic:equal>
		

			<logic:notPresent name="readonly">
				<logic:present name="showAviso">
					<a href="#" onclick="screenLoadImages()" style="margin-left:10px;">
						<img id="adjuntarImg" width="32px" height="32px" 
							alt="Ingreso" title="ADJUNTAR IM&Aacute;GENES" src="./images/iconos/attach-2-48.png" />
					</a>
				</logic:present>
		  	
		  		<logic:equal value="PENDI" name="estadoRend">
				    <a href='#' onClick=showNuevoGastoPopup(); id="BotonAdd" style="margin-right: 12px; margin-top: 15px;">
				    	<img id="addEfectivo" src='./images/iconos/banknotes-48.png' title="GASTO EFECTIVO" border='0' width="28" height="28">
				    </a>
			    	<a href='#' onClick=showNuevoGastoPopupCupon(); id="BotonAdd" style="margin-right: 12px; margin-top: 13px;">
			    		<img id="addCupon" src='./images/iconos/visa-48.png'
			    			title="GASTO TARJ CORPO" border='0' width="34" height="34">
		 	    	</a> 
				</logic:equal>
			</logic:notPresent>
		</html:form>
		
		<html:form action="bajaGasto" styleId="RendicionDetalleForm">
			<logic:present name="messageModif">
				<div id="messageOk" style="color: green; font-weight: bold; text-align: center; width: 100%; font-size: 14">
					<%= (String) request.getSession().getAttribute("messageModif") %>
					<% request.getSession().removeAttribute("messageModif"); %>
				</div>
			</logic:present>
			
			<display:table uid="row" name="Gastos" requestURI="mostrarDetalleGastos.do" id="Gastos" pagesize="8"
		    	decorator="com.sa.decorator.GastosTableDecorator" style="margin-top:15px;">
				<display:column property="descGasto" title="Tipo gasto" style="width:10%" class="descGasto" maxLength="25"/>
			    <display:column property="observacionGasto" title="Descripcion de Gasto" style="width:15%;" class="descGasto" maxLength="50" />
			    <display:column property="nroGasto" title="Rendicion Gastos" media="none" style="width:10%" class="idGasto" />
			    <display:column property="monto" title="Monto Gastos" style="width:10%; text-align:right;" />
			    <display:column property="moneda" title="Moneda" style="width:1%; text-align:center;" />
			    <display:column property="fechagastos" format="{0,date,dd/MM/yyyy}" title="Fe. Gastos" style="width:1%; text-align:center;" />
			    <display:column property="comprobante" title="Tipo Comprobante" style="width:10%;" />
			    <logic:notPresent name="readonly">
			    	<display:column property="opciones" title="Opciones" style="width:1%; text-align:center;" />
			    </logic:notPresent>
			    <display:column property="comentarios" title="Datos Adicionales" style="width:1%; text-align:center;" />
			    <display:column property="cupones" title="Cupones" style="width:2.5%; text-align:center;" />
			</display:table>
		</html:form>
		
		<script>
			function eliminarGasto(idGasto, estadoRend) {
			    if (confirm("¿Estas seguro que quieres eliminar el gasto?")) {
					$("#overlay").show();
			    	$.post("bajaGasto.do", {
			    		codigo: $('#idRendicion').val(),
			    		idGasto: idGasto,
						codMotivo: $('#codMotivo').val(),
						estadoRend: estadoRend
					}).done(function(data) {
						location.reload();
					})
			    }
			    return false;
			}
			
			function thubanOpen (){
				var link =($('#linkThuban').val());
				window.open(link);
			};
		</script>
		
		<logic:equal value="2" name="tipoSubmit">
			<script>
				window.location.href = "mostrarDetalleGastos.do" + 
					"?codigo=" + $('#idRendicion').val() +
					"&estadoRend=" + "PENDI" +
					"&codMotivo=" + $('#codMotivo').val();
			</script>
		</logic:equal>
	</logic:present>
</body>
</html>