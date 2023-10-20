<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>


<h5 class="pb-3 table-message" id="gastosTableMessage"></h5>
<display:table uid="row" name="gastos" requestURI="rendicionDetalleGastos.do" id="gastosTable" excludedParams="username password"
	decorator="com.sa.decorator.GastosTableDecorator" pagesize="20" export="false">
	<display:column property="descGasto" title="TIPO DE GASTO" />
    <display:column property="observacionGasto" title="DESCRIPCI&Oacute;N DE GASTO" />
    <display:column property="montoNum" class="nowrap" format="$ {0,number,#,##0.00}" title="MONTO DE GASTO" />
    <display:column property="moneda" title="MONEDA" />
    <display:column property="fechagastos" class="text-center" title="FE. GASTO" />
    <display:column property="comprobante" title="TIPO COMPROBANTE" />
    <display:column property="datosAdicionales" class="text-center" title="DATOS ADICIONALES" />
    <display:column property="cupones" class="text-center" title="CUPONES" />
    <logic:equal name="showOpciones" value="true">
    	<display:column property="opciones" title="OPCIONES" />
    </logic:equal>
	
	<display:setProperty name="basic.msg.empty_list" value="<h5 class='font-weight-400 dt-empty'>La lista de gastos/consumos est&aacute; vac&iacute;a</h5>" />
</display:table>

