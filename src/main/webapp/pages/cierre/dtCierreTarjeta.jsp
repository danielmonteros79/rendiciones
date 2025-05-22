<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>

<h5 class="pb-3 table-message" id="cierreTarjetaTableMessage"></h5>
<display:table uid="row" name="cierreTarjeta" requestURI="cierreTarjeta.do" id="cierreTarjetaTable" excludedParams="username password"
	decorator="com.sa.decorator.CierreTarjetaTableDecorator" pagesize="30" export="false">
	<display:column property="nroTarjeta" title="NRO. TARJETA" />
    <display:column property="fechaCupon" class="text-center" format="{0,date,dd/MM/yyyy}" title="FECHA" />
    <display:column property="establecimiento" title="ESTABLECIMIENTO" />
    <display:column property="montoCupon" class="text-right nowrap" format="$ {0,number,#,##0.00}" title="MONTO" />
    <display:column property="moneda" title="MONEDA" />
	<display:column property="check" class="text-center" title="SEL." />
	
	<display:setProperty name="basic.msg.empty_list" value="<h5 class='font-weight-400 dt-empty'>La lista de res&uacute;menes est&aacute; vac&iacute;a</h5>" />
</display:table>