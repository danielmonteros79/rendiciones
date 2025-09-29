<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
 <%@ page session="true" contentType="text/html; charset=UTF-8"%>

<h5 class="pb-3 table-message" id="rendicionesTableMessage"></h5>
<div class="py-2  table  table-responsive ">
<display:table uid="row" name="rendiciones" requestURI="detalleAlertas.do"  class="w-100 text-left" excludedParams="username password"
	decorator="com.sa.decorator.alertas.DetalleAlertaTableDecorator" pagesize="10" export="false">
	<display:column property="idGasto" title="ID GASTO" />
	<display:column property="idRendicion"  title="ID rend" />
	<display:column property="nroGasto" title="NUMERO GASTO" />
	<display:column property="descGasto" title="DESCRIPCI&Oacute;N" />
	<display:column property="alerta" title="DESCRIPCI&Oacute;N" class="text-danger"/>

	
	<display:setProperty name="basic.msg.empty_list" value="<h5 class='font-weight-400 dt-empty'>Tu lista de alertas est&aacute; vac&iacute;a</h5>" />
</display:table>

</div>
