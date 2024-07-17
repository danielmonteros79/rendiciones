<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>

<h5 class="pb-3 table-message" id="motivosTableMessage"></h5>
<display:table uid="row" name="motivos" requestURI="parametrosMotivo.do"
	id="ParametrosMotivoTable" 
	decorator="com.sa.decorator.parametros.ParametrosMotivoTableDecorator"
	pagesize="30"  export="true">
	<display:column media="html csv excel" property="codigo" title="MOTIVO"
		 style=" width:4%" />
	<display:column media="html csv excel" property="descripcion"
		title="DESCRIPCION" />
	<display:column media="html csv excel" property="idGlg" title="GLG"
		style="" />
	<!-- <display:column media="html csv excel" property="idCentroCostos"
		title="CENTRO COSTOS" style="" /> -->
	<display:column media="html csv excel" property="codSup"
		title="REQUIERE SUPERIOR" />
	<display:column media="html csv excel" property="codFirma"
		title="REQUIERE FIRMANTE" />
	<display:column media="html csv excel" property="codAprobacionGlg"
		title="REQUIERE CTRL.GLG" />
	<display:column media="html csv excel" property="estado" title="ESTADO"
		style="" />
	<display:column media="html" property="opciones" title="OPCIONES"
		style="width:4%" />

	<display:setProperty name="export.csv.filename"
		value="ListadoParametrosMotivo.csv" />
	<display:setProperty name="export.excel.filename"
		value="ListadoParametrosMotivo.xls" />

	<display:setProperty name="basic.msg.empty_list"
		value="<h5 class='font-weight-400 dt-empty'>Tu lista de rendiciones est&aacute; vac&iacute;a</h5>" />
</display:table>


