<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page import="java.util.*"%>

<link rel="stylesheet" type="text/css" href="./css/rendicionAviso.css">

<script type="text/javascript">
$(document).ready(function() {
	$('#checkRendiciones').show();
});

$.ajaxSetup({
	cache : false
});

	$(document).ready(function() {
		var msg = "<%= request.getSession().getAttribute("msg") %>";
		if (msg != "null") {
			recuperarForm();
			alert(msg);
			if(msg.includes("Error"))
				window.location.href = "listaRendiciones.do";
		}
		<% request.getSession().removeAttribute("msg"); %>
	});
	
	function clickRadio() {
// 		setTimeout(function(){ $('input[name="accion"]').attr('disabled', 'disabled'); }, 3000);
	//	$('input[name="accion"]').attr('disabled', 'disabled');
		//alert($('input[name="accion"]').val());
	}
	
</script>

<div id="divBackground"></div>

<html:form action="rendicionAviso?action=generar" styleId="rendicionAvisoForm" method="POST" enctype="multipart/form-data">
	<table class="panel" style="display:none">
		<tr>
			<td style="width:1%;">
				<input type="radio" name="accion" value="caratula">
			</td>
			<td style="font-weight: bold;">
				ENV&Iacute;O DE COMPROBANTES AL SECTOR DE ESCANEO
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				Utilizar esta forma de generar las im&aacute;genes de los comprobantes genera 
				una demora en la aprobaci&oacute;n de la rendici&oacute;n.
			</td>
		</tr>
	</table>
		<span style="color:red;font-weight:bold;margin-left: 10%;">ANTES DE INCLUIR LAS IMAGENES DEBE HABER INFORMADO TODOS LOS GASTOS</span>
	<table class="panel">
		<tr>
			<td style="width:1%;">
				<input type="radio" name="accion" value="archivo">
			</td>
			<td style="font-weight: bold;">
				ADJUNTAR IM&Aacute;GENES
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<ul>
					<li>Se deben informar todos los comprobantes que ayuden a la comprensi&oacute;n del gasto.</li>
					<li>No debe superar los 1.2 Mb. en los archivos .TIF</li>
					<li>Solo las sucursales pueden informar im&aacute;genes .TIF (que deben ser generadas por los medios disponibles de la sucursal). El resto deben informar .PDF</li>
					<li>En el caso que la im&aacute;gen no sea clara o existan problemas para visualizarla se solicitar&aacute; que env&iacute;e el comprobante al sector que lo solicite o directamente se rechazar&aacute; la rendici&oacute;n.</li>
					<li>Se debe resguardar los comprobantes hasta que la rendici&oacute;n se encuentre finalizada.</li>
				</ul>
			</td>
		</tr>
		<tr class="imagenesTr">
			<td></td>
			<td>
				<div id="formCargar" style="margin-top:10px;">
					AGREGAR NUEVA IMAGEN<br>
			 		<input type="file" name="archivo" id="archivo" accept=".pdf,.tif" 
						onchange="cargar()"/>
					<div style="font-size:11px;margin-top:5px;">
			 			<font color="red" id="validacionArchivo"></font>
			 		</div>
				</div>
			</td>
		</tr>
		<tr class="imagenesTr">
			<td></td>
			<td>
				<div id="imagenesDiv" style="margin-top:10px; display:none;">
					IM&Aacute;GENES SELECCIONADAS
					<table id="archivosASubirTable" style="width:100%;margin-left:0;">
					</table>
				</div>
				<div>
					<span id="unsavedSpan" style="display:none;">
						<img src="./images/iconos/warning.png"/>
							&nbsp;&nbsp; Luego de cargar todas las im&aacute;genes necesarias, no olvide presionar el bot&oacute;n GENERAR. &nbsp;&nbsp;
						<img src="./images/iconos/warning.png"/>
					</span>
				</div>
			</td>
		</tr>
	</table>
	<div id="submitDiv">
		<span id="errores"></span>
		<logic:empty name="RendicionAvisoForm" property="rendicion.usuarioRendicion">
			<input type="button" class="buttonContinue" onclick="showAdjuntarImagenPopup()" style="float:right;" value="GENERAR"/>
		</logic:empty>
		<logic:notEmpty name="RendicionAvisoForm" property="rendicion.usuarioRendicion">
			<input type="submit" onclick="clickRadio()" class="buttonContinue" style="float:right;" value="GENERAR"/>
		</logic:notEmpty>
		
	</div>
</html:form>

<script type="text/javascript" src="./js/rendicionAviso.js"></script>