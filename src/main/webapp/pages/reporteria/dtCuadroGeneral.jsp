<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>


<h5 class="pb-3 table-message" id="rendicionesTableMessage"></h5>

	<display:table uid="row" name="Rendicion" class="w-100"
		requestURI="cuadroGeneralFiltro.do" id="RendicionesTable"
		excludedParams="username password"
		decorator="com.sa.decorator.CuadroGeneralTableDecorator" pagesize="15"
		style="width:50%;" export="true">
		<display:column property="estado" title="ESTADO"
			style="white-space:nowrap" media="html csv excel" />
		<display:column property="cantRend" title="CANTIDAD DE RENDICIONES"
			style="white-space:nowrap" media="html csv excel" />
		<display:column property="montoTotal" title="MONTO TOTAL(*)"
			maxLength="55" media="html csv excel" />
		<display:column property="opciones" title="DETALLE"
			style="text-align:center; width:4%" media="html" />

		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.onepage" value="" />

		<display:setProperty name="export.csv.filename"
			value="CuadroGeneral.csv" />
		<display:setProperty name="export.excel.filename"
			value="CuadroGeneral.xls" />
	</display:table>


