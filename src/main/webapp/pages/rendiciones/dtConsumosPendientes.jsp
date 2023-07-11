<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>

<div class="py-4  table  table-responsive ">
<display:table uid="row" name="consumosPendientes"
	requestURI="rendicionDetalleGastos.do" class="w-100"  id="consumosPendientesDtTable" excludedParams="username password" pagesize="5" export="false">
    <display:column property="fecha" class="text-center" format="{0,date,dd/MM/yyyy}" title="FECHA" />
    <display:column property="fechaDebito" class="text-center" format="{0,date,dd/MM/yyyy}" title="FECHA D&Eacute;BITO" />
    <display:column property="cupon" title="CUP&Oacute;N" />
    <display:column property="establecimiento" title="ESTABLECIMIENTO" />
    <display:column property="monto" class="text-right nowrap" format="$ {0,number,#,##0.00}" title="MONTO" />
    <display:column property="moneda" title="MONEDA" />
    <display:column property="estado" title="ESTADO" />
	
	<display:setProperty name="basic.msg.empty_list" 
		value="<h5 class='font-weight-400 dt-empty'>No ten&eacute;s consumos pendientes para cargar en esta rendici&oacute;n</h5>" />
</display:table>
</div>