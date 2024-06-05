<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>

<div id="paginacion" class="text-center pb-5" style="margin-top: 43px;">
	<div class="py-2 table  table-responsive">
		<display:table uid="row" name="alerta"
			requestURI="parametrosAlertas.do" id="ParametrosAlertasTable"
			excludedParams="false"
			decorator="com.sa.decorator.parametros.ParametrosAlertasTableDecorator"
			pagesize="15" style="margin-left:-0.9%;width:99.7%;" export="true">
			<display:column media="html csv excel" property="id"
				title="Alerta"  sortable="true"
				style="text-align:right; width:4%" />
<%-- 			<display:column media="html csv excel" property="codMotivo" --%>
<%-- 				title="Motivo"  sortable="true" --%>
<%-- 				style="text-align:right; width:4%" /> --%>
			<display:column media="html csv excel" property="codMotivo"
				title="Motivo" />
			<display:column media="html csv excel" property="desMotivo"
				title="Descripcion Motivo" />
			<display:column media="html csv excel" property="codGasto"
				title="Gasto" sortable="true" style="text-align:right;" />
			<display:column media="html csv excel" property="desGasto"
				title="Descripcion Gasto" />
			<display:column media="html csv excel" property="montCant"
				title="Mont/Cant" style="text-align:center;" />
			<display:column media="html csv excel" property="rend" title="Rend" />
			<display:column media="html csv excel" property="periodo"
				title="Periodo" />
			<display:column media="html csv excel" property="nivelMin"
				title="Nivel Min" style="text-align:center;" />
			<display:column media="html csv excel" property="nivelMax"
				title="Nivel Max" style="text-align:center;" />
			<display:column media="html csv excel" property="estado"
				title="Estado" style="text-align:center;" />
			<display:column media="html" property="opciones" title="Opciones"
				style="width:4%" />

			<display:setProperty name="export.csv.filename"
				value="ListadoParametrosAlerta.csv" />
			<display:setProperty name="export.excel.filename"
				value="ListadoParametrosAlerta.xls" />
		</display:table>
		</div>
	</div>