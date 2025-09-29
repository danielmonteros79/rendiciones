
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
<title>Consumos No Rendidos</title>
</head>
<body>


<div class="container py-5  div-resultado" id="consumosDivResultado">

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
	
	<div class="row">
		<div class="col-sm-12 mb-md-4">
			<i class="bbva-icon icon-coronita_bullet fab fa-rotate-270 text-blue align-middle mx-2"></i>
			<small class="font-weight-bold">CONSUMOS NO RENDIDOS</small>
		</div>
	</div>
	<div class="row py-3">
		<div class="col-sm-12">
			<h2 class="font-weight-500">Tu listado de consumos no rendidos</h2>
		</div>
		<div class="col-md-12 py-3 table-responsive-lg">
			<div class="dt-container" id="consumosDtContainer"></div>
		</div>
		
	</div>

			<%-- <div  id="consumosDtContainer">
				<div id="paginacion" >
					<div class="py-4 table table-responsive ">
						<h5 class="pb-3 table-message" id="consumosTableMessage"></h5>
						<display:table uid="row" name="consumos" class="w-100" requestURI="consumosNoRendidos.do" id="consumosTable" excludedParams="username password"
							decorator="com.sa.decorator.ConsumosNoRendidosTableDecorator" pagesize="10" export="false">
						    <display:column property="fecha" class="text-center" format="{0,date,dd/MM/yyyy}" title="FECHA" />
						    <display:column property="cupon" title="CUP&Oacute;N" />
						    <display:column property="establecimiento" title="ESTABLECIMIENTO" />
						    <display:column property="montoNum" class="text-right nowrap" format="$ {0,number,#,##0.00}" title="MONTO" />
						    <display:column property="moneda" title="MONEDA" />
						    <display:column property="estado" title="ESTADO" />
							
							<display:setProperty name="basic.msg.empty_list" value="<h5 class='font-weight-400 dt-empty'>Tu lista de consumos no rendidos est&aacute; vac&iacute;a</h5>" />
						</display:table>
					</div>
			</div>
			
			</div>  --%>
		</div>
	



<script type="text/javascript">
		jQuery(document).ready(function() {
			$('#checkResumen').show();
			
			$(".balloon").each(function() {
				$(this).balloon({
					html: true,
					contents: $(this).prop('title'),
					tipSize: 20,
					position: "top",
					css: {
			    	    border: 'solid 4px #0080FF',
			    	    padding: '10px',
			    	    fontSize: '14px',
			    	    fontWeight: 'bold',
			    	    backgroundColor: '#FFFFFF',
			    	    color: '#000000'
		    		}
				});
			});
		});
	</script>


<script type="text/javascript" src="static/js/tarjetaCorporativa/consumosNoRendidos.js"></script>

</body>
</html>