<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>

<h5 class="pb-3 table-message" id="consumosTableMessage"></h5>
<display:table uid="row" name="resumen" requestURI="resumenesAnteriores.do" id="resumenTable" class="w-100 text-left" excludedParams="username password" pagesize="10" export="false">
    <display:column property="fecha"  format="{0,date,dd/MM/yyyy}" title="FECHA" />
    <display:column property="cupon" title="CUP&Oacute;N" />
    <display:column property="establecimiento" title="ESTABLECIMIENTO" />
    <display:column property="monto"  format="$ {0,number,#,##0.00}" title="MONTO" />
    <display:column property="moneda" title="MONEDA" />
    <display:column property="estado" title="ESTADO" />
	
	<display:setProperty name="basic.msg.empty_list" value="<h5 class='font-weight-400 dt-empty'>Tu resumen est&aacute; vac&iacute;a</h5>" />
</display:table>
