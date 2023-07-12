
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>

<h5 class="pb-3 table-message" id="aprobacionesTableMessage"></h5>
<display:table uid="row" name="Rendicion" requestURI="listadoAprobaciones.do" id="dt-table" excludedParams="username password"
	decorator="com.sa.decorator.AprobacionesTableDecorator" pagesize="10" export="false" style="font-size:14px;" >
	<display:column property="id" class="text-right" title="ID" style="width:3%;"/>
		<display:column property="nombreUsuarioRendicion" title="USUARIO" style="width:20%;"/>
		<display:column property="descripcionMotivo" title="MOTIVO" style="width:20%;"/>
		<display:column property="descripcionEstado" title="ESTADO" style="width:20%;"/>
		<display:column property="descripcionCorta" title="DESCRIPCI&Oacute;N" style="width:20%;"/>
		<display:column property="fechaDesde" class="text-center" format="{0,date,dd/MM/yyyy}" title="DESDE" style="width:5%;"/>
		<display:column property="fechaHasta" class="text-center" format="{0,date,dd/MM/yyyy}" title="HASTA" style="width:5%;"/>
		<display:column property="importeNum" class="text-right nowrap" format="$ {0,number,#,##0.00}" title="IMPORTE" style="width:5%;"/>
		<display:column property="opciones" class="nowrap" title="OPCIONES" style="width:5%;" />
		<display:column property="check" class="text-center" title="SEL." style="width:5%;"/>
		<display:column property="cupones" class="text-right nowrap"  title="ALERTA" style="width:5%;"/>
	
	<display:setProperty name="basic.msg.empty_list" value="<h5 class='font-weight-400 dt-empty'>Tu lista de aprobaciones est&aacute; vac&iacute;a</h5>" />
</display:table>

