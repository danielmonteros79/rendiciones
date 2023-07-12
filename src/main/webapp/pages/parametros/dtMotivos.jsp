<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>

<h5 class="pb-3 table-message" id="motivosTableMessage"></h5>
<display:table uid="row" name="motivos" requestURI="parametrosMotivo.do"
	id="ParametrosMotivoTable" excludedParams="false"
	decorator="com.sa.decorator.parametros.ParametrosMotivoTableDecorator"
	pagesize="15" style="margin-left:-0.9%;width:99.7%;" export="true">
	<display:column media="html csv excel" property="codigo" title="Motivo"
		style="width:4%" sortable="true" style="text-align:right;" />
	<display:column media="html csv excel" property="descripcion"
		title="Descripción" />
	<display:column media="html csv excel" property="idGlg" title="GLG"
		style="text-align:right;" />
	<display:column media="html csv excel" property="idCentroCostos"
		title="C. Costos" style="text-align:right;" />
	<display:column media="html csv excel" property="codSup"
		title="Superior" />
	<display:column media="html csv excel" property="codFirma"
		title="Firma" />
	<display:column media="html csv excel" property="codAprobacionGlg"
		title="Ctrl. GLG" />
	<display:column media="html csv excel" property="estado" title="Estado"
		style="text-align:center;" />
	<display:column media="html" property="opciones" title="Opciones"
		style="width:4%" />

	<display:setProperty name="export.csv.filename"
		value="ListadoParametrosMotivo.csv" />
	<display:setProperty name="export.excel.filename"
		value="ListadoParametrosMotivo.xls" />

	<display:setProperty name="basic.msg.empty_list"
		value="<h5 class='font-weight-400 dt-empty'>Tu lista de rendiciones est&aacute; vac&iacute;a</h5>" />
</display:table>


