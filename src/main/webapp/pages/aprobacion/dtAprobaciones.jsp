<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>

<div class="row py-3 pb-md-2">
		<div class="col-sm-12">
			<h2 id="cantRendPendientes" class="font-weight-500">Rendiciones pendientes de aprobaci&oacute;n: <bean:write name="cantRendiciones" /></h2>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 py-3 table-responsive-lg">
			<a href="#a" class="btn btn-light px-3 mt-1 float-right mb-4" onclick="seleccionarTodo()">
					Seleccionar todo
			</a>
			<div class="dt-container" id="aprobacionesDtContainer" >
			
			<h5 class="pb-3 table-message" id="aprobacionesTableMessage"></h5>
				<display:table uid="row" name="Rendicion" requestURI="listadoAprobaciones.do" id="dt-table" excludedParams="username password"
					decorator="com.sa.decorator.AprobacionesTableDecorator" pagesize="10" export="false" style="font-size:14px;" >
					<display:column property="id" class="text-right" title="ID" style="width:1%;"/>
						<display:column property="nombreUsuarioRendicion" title="USUARIO" style="width:15%;"/>
						<display:column property="descripcionMotivo" title="MOTIVO" style="width:20%;"/>
						<display:column property="descripcion" title="DESCRIPCI&Oacute;N" maxLength="30" style="width:15%;"/>
						<display:column property="fechaDesde" class="text-center" format="{0,date,dd/MM/yyyy}" title="DESDE" style="width:2%;"/>
						<display:column property="fechaHasta" class="text-center" format="{0,date,dd/MM/yyyy}" title="HASTA" style="width:2%;"/>
						<display:column property="importeNum" class="text-right nowrap" format="$ {0,number,#,##0.00}" title="IMPORTE" style="width:2%;"/>
						<display:column property="opciones" class="nowrap" title="OPCIONES" style="width:2%;" />
						<display:column property="check" class="text-center" title="SEL." style="width:2%;"/>
						<display:column property="cupones" class="text-right nowrap"  title="ALERTA" style="width:2%;"/>
					<display:setProperty name="basic.msg.empty_list" value="<h5 class='font-weight-400 dt-empty'>Tu lista de aprobaciones est&aacute; vac&iacute;a</h5>" />
				</display:table>
							
			
			
			</div>
		</div>
	</div>

