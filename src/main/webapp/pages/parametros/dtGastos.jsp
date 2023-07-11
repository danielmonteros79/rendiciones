
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>

<div id="paginacion" class="text-center pb-5" style="margin-top:43px;">
		<display:table uid="row" name="gastos"
			requestURI="parametrosGastos.do" id="ParametrosGastosTable" excludedParams="username password"
			decorator="com.sa.decorator.parametros.ParametrosGastosTableDecorator" pagesize="15"
			style="margin-left:-0.9%;width:99.7%;" export="true">
			<display:column media="html csv excel" property="gasto" title="Gasto" style="text-align:right;" sortable="true" />
			<display:column media="html csv excel" property="descripcionGasto" title="Descripción de Gasto" />
			<display:column media="html csv excel" property="motivo" title="Motivo" style="text-align:right;" sortable="true" />
			<display:column media="html csv excel" property="descripcionMotivo" title="Descripción de Motivo" />
			<display:column media="html csv excel" property="ristra" title="Ristra" style="text-align:left;" />
			<display:column media="html csv excel" property="bimon" title="Bimon" style="text-align:center;" />
			<display:column media="html csv excel" property="comprob" title="Comprob" />
			<display:column media="html csv excel" property="autoriz" title="Autoriz" />
			<display:column media="html csv excel" property="observ" title="Observación" />
			<display:column media="html csv excel" property="estado" title="Estado" style="text-align:center;" />
			<display:column media="html" property="opciones" title="Opciones" style="width:4%" />
			<display:setProperty name="export.excel.filename" value="ListadoParametrosGastos.xls"/>
			<display:setProperty name="export.csv.filename" value="ListadoParametrosGastos.csv"/>
		</display:table>
	</div>