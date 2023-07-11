<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>

<h5 class="my-4 table-message" id="ExceptuadosTableMessage"></h5>
	<display:table uid="row" name="exceptuados"
		requestURI="parametrosExceptuadosFiltro.do"
		id="ParametrosExceptuadosTable" excludedParams="false"
		decorator="com.sa.decorator.parametros.ParametrosExceptuadosTableDecorator"
		pagesize="10"  class="w-100 text-left" export="true">
		<display:column media="html csv excel" property="motivoUsuario"
			title="MOTIVO/USUARIO" />
		<display:column media="html csv excel" property="descripcionNombre"
			title="DESCRIPCI&Oacute;N/NOMBRE" />
		<display:column media="html csv excel" property="hasta" title="Hasta"
			format="{0,date,dd/MM/yyyy}" />
		<display:column media="html csv excel" property="desde" title="Desde"
			format="{0,date,dd/MM/yyyy}" />
		<display:column media="html csv excel" property="estado"
			title="ESTADO" />
		<display:column media="html" property="opciones" title="Opciones" />
		<display:setProperty name="export.excel.filename"
			value="ListadoParametrosExceptuados.xls" />
		<display:setProperty name="basic.msg.empty_list" value="<h5 class='font-weight-400 dt-empty'>Tu lista de exceptuados</h5>" />
		<display:setProperty name="export.csv.filename"
			value="ListadoParametrosExceptuados.csv" />
		<display:setProperty name="export.csv.filename"
			value="ListadoParametrosMotivo.csv" />
		<display:setProperty name="export.excel.filename"
			value="ListadoParametrosMotivo.xls" />
		<display:setProperty name="basic.msg.empty_list" value="<h5 class='font-weight-400 dt-empty'>Tu lista de exceptuados est&aacute; vac&iacute;a</h5>" />
	</display:table>





