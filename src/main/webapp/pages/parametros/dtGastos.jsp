
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>

<h5 class="pb-3 table-message" id="gastosTableMessage"></h5>
		<display:table uid="row" name="gastos"
			requestURI="parametrosGastos.do" id="ParametrosGastosTable"
			decorator="com.sa.decorator.parametros.ParametrosGastosTableDecorator"
			pagesize="30" export="true"  >
			<display:column media="html csv excel" property="gasto" title="Gasto"
				style="text-align:right;" />
			<display:column media="html csv excel" property="descripcionGasto"
				title="Descripci&oacute;n de Gasto" />
			<display:column media="html csv excel" property="motivo"
				title="Motivo" style="text-align:right;"  />
			<display:column media="html csv excel" property="descripcionMotivo"
				title="Descripci&oacute;n de Motivo" />
			
			<display:column media="html csv excel" property="estado"
				title="Estado" style="text-align:center;" />
			<display:column media="html" property="opciones" title="Opciones"
				 />
			<display:setProperty name="export.excel.filename"
				value="ListadoParametrosGastos.xls" />
			<display:setProperty name="export.csv.filename"
				value="ListadoParametrosGastos.csv" />
		</display:table>
