
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<h5 class="pb-3 table-message" id="consumosNoRendidosTableMessage"></h5>

<div class="py-4  table">
<display:table uid="row" name="consumosSinRendir"
	requestURI="consumosSinRendir.do" class="w-100"  id="consumosPendientesDtTable" excludedParams="username password" pagesize="30" export="false"
	decorator="com.sa.decorator.ConsumosNoRendidosTableDecorator">
    <display:column property="usuario" class="usuario" title="USUARIO" style="width:10%" />
    <display:column property="establecimiento" class="descripcion" title="DESCRIPCION" style="width:15%"/>
     <display:column property="fechaCupon" class="fecha"  format="{0,date,dd/MM/yyyy}"  title="FECHA" style="width:10%"/>
    <display:column property="moneda" title="MONEDA" class="moneda" style="width:10%"/>
     <display:column property="montoCupon"  class="monto" format="$ {0,number,#,##0.00}" title="MONTO" style="width:20%" />
 	<display:column title="Motivo"  style="width:10%">
		<select class="form-control selectMotivosCP" style="width:1rem !important;"></select>
 	</display:column>
 	<display:column title="Gasto" style="width:10%">
		<select  class="form-control selectGastosCP"></select>
 	</display:column>
 	<display:column property="check"  class="text-center check" title="SEL." style="width:2%;"/>
	<display:setProperty name="basic.msg.empty_list" 
		value="<h5 class='font-weight-400 dt-empty'>No ten&eacute;s consumos pendientes por mostrar</h5>" />
</display:table>
</div>

<script type="text/javascript" src="js/cierre/dtConsumosNoRendidos.js"></script>

