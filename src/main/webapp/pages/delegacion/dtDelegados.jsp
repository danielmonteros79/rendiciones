<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>

<h5 class="pb-3 table-message" id="delegadosTableMessage"></h5>
<display:table uid="row" name="delegados" requestURI="abmDelegado.do" id="delegadosTable" excludedParams="username password"
	decorator="com.sa.decorator.delegacion.AbmDelegadoTableDecorator" pagesize="10" export="false">
	<display:column property="delegadoNombre" title="USUARIO" />
	<display:column property="delegadoCentroCostos" class="text-right" title="C.COSTOS" />
	<display:column property="delegadoSector" class="text-right" title="SECTOR" />
	<display:column property="delegadoInforme" title="INFORME" />
	<display:column property="delegadoAccionDesc" title="ACCI&Oacute;N" />
	<display:column property="delegadoEstado" title="ESTADO" />
	<display:column property="feDesde" class="text-center" format="{0,date,dd/MM/yyyy}" title="DESDE" />
	<display:column property="feHasta" class="text-center" format="{0,date,dd/MM/yyyy}" title="HASTA" />
	<display:column property="opciones" title="OPCIONES" />
	
	<display:setProperty name="basic.msg.empty_list" value="<h5 class='font-weight-400 dt-empty'>Tu lista de delegados est&aacute; vac&iacute;a</h5>" />
</display:table>

