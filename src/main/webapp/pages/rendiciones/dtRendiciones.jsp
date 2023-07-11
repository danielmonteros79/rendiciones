<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ page session="true" contentType="text/html; charset=UTF-8"%>

<h5 class="pb-3 table-message" id="rendicionesTableMessage"></h5>
<display:table  uid="row" name="rendiciones" requestURI="listadoRendiciones.do" class="thead-dark" id="rendicionesTable" excludedParams="username password"
	decorator="com.sa.decorator.RendicionesTableDecorator" pagesize="20" export="false">
	<display:column property="id" class="text-left " title="ID" />
	<display:column property="motivo" title="MOTIVO" style="width:30%;" />
	<display:column property="descripcion" title="DESCRIPCI&Oacute;N" style="width:70%;"/>
	<display:column property="fechaDesde" class="text-left" format="{0,date,dd/MM/yyyy}" title="DESDE" />
	<display:column property="fechaHasta" class="text-left" format="{0,date,dd/MM/yyyy}" title="HASTA" />
	<display:column property="importeNum" class="text-left nowrap" format="$ {0,number,#,##0.00}" title="IMPORTE" />
	<display:column property="statusColor" class="text-center" title="ESTADO" media="html" />
	<display:column property="cupones" class="text-center" title="ALERTA" media="html" />
	<display:column property="opciones" title="OPCIONES" />
	
	<display:setProperty name="basic.msg.empty_list" value="<h5 class='font-weight-400 dt-empty'>Tu lista de rendiciones est&aacute; vac&iacute;a</h5>" />
</display:table>


