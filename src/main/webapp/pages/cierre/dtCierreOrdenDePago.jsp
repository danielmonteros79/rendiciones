<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>

<h5 class="pb-3 table-message" id="cierreOrdenDePagoTableMessage"></h5>

<display:table uid="row" name="rendiciones" requestURI="cierreOrdenDePago.do" id="ordenesDePagoTable" excludedParams="username password"
	decorator="com.sa.decorator.CierreOrdenDePagoTableDecorator" pagesize="30" export="false">
	<display:column property="id" title="ID" />
	<display:column property="usuarioRendicion" title="USUARIO" />
	<display:column property="nombreUsuarioRendicion" title="NOMBRE USUARIO" />
	<display:column property="motivo" title="MOTIVO" />
	<display:column property="importe" class="nowrap" title="IMPORTE" />
	<display:column property="importeTarjeta" class=" nowrap" title="TARJETA" />
	<display:column property="opciones" class="nowrap" title="OPCIONES" />
	<display:column property="check" class="text-center" title="SEL." />
	<display:setProperty name="basic.msg.empty_list" value="<h5 class='font-weight-400 dt-empty'>Tu lista de ordenes de pago est&aacute; vac&iacute;a</h5>" />
</display:table>