<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<h5 class="pb-3 table-message" id="consumosNoRendidosTableMessage"></h5>
<div class="py-2  table">
	<display:table uid="row" name="aprobacionesPendientes"
		requestURI="aprobacionesPendientes.do" class="w-100"
		id="consumosPendientesDtTable" excludedParams="username password"
		pagesize="30" export="false"
		decorator="com.sa.decorator.ConsumosNoRendidosTableDecorator">
		<display:column property="usuario" class="usuario" title="USUARIO"
			style="width:10%" />
		<display:column property="establecimiento" class="descripcion"
			title="DESCRIPCION" style="width:15%" />
		<display:column property="fechaCupon" class="fechaCupon"
			format="{0,date,dd/MM/yyyy}" title="FECHA" style="width:10%" />
		<display:column property="moneda" title="MONEDA" class="moneda"
			style="width:10%" />
		<display:column property="estadoResumen" class="estado"
			title="ESTADO" style="width:20%" />
		<display:column property="fechaCupon" class="fechaCierre"
			title="FECHA CIERRE" format="{0,date,dd/MM/yyyy}" style="width:20%" />
		<display:column property="montoCupon" class="estimadoDebito"
			title="ESTIMADO DEBITO" style="width:20%" />
		<display:column property="check"  class="text-center check" title="SEL." style="width:2%;"/>
		<display:setProperty name="basic.msg.empty_list"
			value="<h5 class='font-weight-400 dt-empty'>No ten&eacute;s consumos pendientes por mostrar</h5>" />
	</display:table>
</div>