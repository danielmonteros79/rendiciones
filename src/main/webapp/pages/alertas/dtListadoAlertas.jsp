<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
 <%@ page session="true" contentType="text/html; charset=UTF-8"%>

<h5 class="pb-3 table-message" id="rendicionesTableMessage"></h5>
<div class="py-2  table  table-responsive ">
<display:table uid="row" name="rendiciones" requestURI="listadoAlertas.do" id="rendicionesTable" class="w-100 text-left" excludedParams="username password"
	decorator="com.sa.decorator.alertas.ListadoAlertasTableDecorator" pagesize="10" export="false">
	<display:column property="id" class="text-left" title="ID" />
	<display:column property="motivo" title="MOTIVO" />
	<display:column property="descripcion" title="DESCRIPCI&Oacute;N" />
	<display:column property="fechaDesde"  format="{0,date,dd/MM/yyyy}" title="DESDE" />
	<display:column property="fechaHasta"  format="{0,date,dd/MM/yyyy}" title="HASTA" />
	<display:column property="importeNum"  style="width: 14%;" format="$ {0,number,#,##0.00}" title="IMPORTE" />
	<display:column property="alertas"  style="width: 20%;" title="ALERTA"  class="text-danger"/>
	<display:column property="opciones" class="text-center" title="OPCIONES" />
	
	<display:setProperty name="basic.msg.empty_list" value="<h5 class='font-weight-400 dt-empty'>Tu lista de rendiciones est&aacute; vac&iacute;a</h5>" />
</display:table>
</div>