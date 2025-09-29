<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<% request.setCharacterEncoding("UTF-8"); %>
<!-- Enlaces a archivos CSS -->
<link rel="stylesheet" type="text/css" href="./css/virtual-select.min.css">
<link rel="stylesheet" type="text/css" href="./css/select2.css">
		
<html:form action="saveRendicion.do" styleId="rendicionDetalle">
	<div class="container py-5">
		<!-- Sección de encabezado y usuario -->
		<div class="row">
			<div class="col-sm-12 mb-md-4">
				<i class="bbva-icon icon-coronita_bullet fab fa-rotate-270 text-blue align-middle mx-2"></i>
				<small class="font-weight-bold">RENDICIONES</small>
			</div>
		</div>
		
		<!-- Subtítulo -->
		<div class="row pb-3 pt-2 pb-md-4">
			<div class="col-sm-12">
				<h2 class="font-weight-500">Ingres&aacute; el motivo de rendici&oacute;n de gastos</h2>
			</div>
		</div>
		
		<!-- Información del usuario -->
		<div class="row p-3 font-weight-bold">
			<div class="col-lg-4 col-sm-12  py-3 border">
				Usuario:
				<bean:write name="RendicionForm" property="nombreUsuario" />
			</div>
			<div class="col-lg-8 col-sm-12 ">
				<div class="row">
					<div class="col-12 col-sm-6 py-3 border">
						C. Costo Usuario:
						<bean:write name="RendicionForm" property="costos" />
					</div>
					<div class="col-12 col-sm-6 py-3 border">
						Sector:
						<bean:write name="RendicionForm" property="sector" />
					</div>
				</div>
			</div>
		</div>
		
		<!-- Título de la sección de rendición -->
		<div class="row pt-3 pb-3 text-black-50">
			<div class="col-12 col-sm-5">
				<h5 class="font-weight-400">Rendici&oacute;n</h5>
			</div>
			<div class="col-12 col-sm-7">
				<h5 class="font-weight-400 d-inline" id="preFormato">Per&iacute;odo</h5>
				<small >( Debe comprender la fecha en la que se hizo el gasto )</small>
			</div>
		</div>
		
		<!-- Selector de motivo -->
		<div class="row">
			<div class="col-12 col-sm-5">
				<div class="has-float-label form-group" style="display:flex; justify-content:center; align-items:center">
					<div style="margin-left: 5px; float:left; width:80%">
						<html:select property="motivo" styleId="rendicionDetalleMotivo"
							styleClass="form-control  basic-single ">
							<html:options collection="ComboMotivo" property="id"
								labelProperty="descripcion" />
						</html:select>
						<label for="motivo">Motivo</label>
						<div class="invalid-feedback mb-3"></div>
					</div>
					<div style=" width:20%; float:right; height:100%">
						<a id="ayudaMotivo" class="bg-light " style="padding:.85rem" target="_blank" onclick="ayudaMotivo()" >
							<img id="ayudaMotivoImg" width="15px" src='./images/iconos/question-mark-2-48.png' 
							alt='Ayuda Motivo' title="AYUDA MOTIVO"
							border='0' align="top"  />
						</a>
					</div>
				</div>
			</div>
			
			<!-- Sección de selección de fechas o mes -->
            <div class="col-12 col-sm-7">
                <div class="row">
                    <!-- Contenedor de fecha -->
                    <div class="col-12" id="fechaContainer">
                        <div class="row">
                            <div class="col-12 col-sm-6 has-float-label">
                                <div class="input-group">
                                    <input type="text" class="form-control datepicker bg-light" id="rendicionDetalleFechaDesde" name="fechaDesde" placeholder="Desde" required/>
                                    <label id="leyendaDesde">Desde</label>
                                    <div class="input-group-append">
                                        <button class="btn btn-outline-primary bg-light border-white hover-darkblue datepicker-btn" tabindex="-1" type="button">
                                            <i class="bbva-icon icon-coronita_calendar fa-lg"></i>
                                        </button>
                                    </div>
                                    <div class="invalid-feedback mb-3"></div>
                                </div>
                            </div>
                            <div class="col-12 col-sm-6 has-float-label">
                                <div class="input-group" id="rendicionDetalleFechaHastaContainer">
                                    <input type="text" class="form-control datepicker bg-light" id="rendicionDetalleFechaHasta" name="fechaHasta" placeholder="Desde" required/>
                                    <label id="leyendaHasta">Hasta</label>
                                    <div class="input-group-append">
                                        <button class="btn btn-outline-primary bg-light border-white hover-darkblue datepicker-btn" tabindex="-1" type="button">
                                            <i class="bbva-icon icon-coronita_calendar fa-lg"></i>
                                        </button>
                                    </div>
                                    <div class="invalid-feedback mb-3"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Contenedor de mes -->
                    <div class="col-12" id="mesContainer" style="display: none;">
                        <div class="has-float-label">
                            <select class="form-control bg-light" id="mesSelect" name="mes">
                                <option value="01">Enero</option>
                                <option value="02">Febrero</option>
                                <option value="03">Marzo</option>
                                <option value="04">Abril</option>
                                <option value="05">Mayo</option>
                                <option value="06">Junio</option>
                                <option value="07">Julio</option>
                                <option value="08">Agosto</option>
                                <option value="09">Septiembre</option>
                                <option value="10">Octubre</option>
                                <option value="11">Noviembre</option>
                                <option value="12">Diciembre</option>
                            </select>
                            <label>Mes del Gasto</label>
                            <div class="invalid-feedback mb-3"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
		
		<!-- Sección de descripción/observaciones -->
		<div class="row pt-4 pb-3 text-black-50">
			<div class="col-12">
				<h5 class="font-weight-400">Descripci&oacute;n/Observaciones</h5>
			</div>
		</div>
		<div class="row">
			<div class="col-12">
				<div class="has-float-label">
					<textarea class="form-control bg-light" name="descripcion" id="rendicionDetalleDescripcion" maxlength="120" required
						placeholder="(120 caracteres)" rows="5"></textarea>
					<label>Ingres&aacute; una observaci&oacute;n</label>
					<div class="invalid-feedback mb-3"></div>
				</div>
			</div>
		</div>
		
		<!-- Botón de continuar -->
		<div class="row">
			<div class="col-sm-12 pt-3 pt-md-5 text-center">
				<a href="#a" class="btn btn-primary px-5 py-3 mx-2" onclick="continuar()">Continuar</a>
			</div>
		</div>
	</div>
	
</html:form>
<script type="text/javascript" src="./static/js/select2.min.js"></script>
<script type="text/javascript" src="./static/js/rendiciones/rendicionDetalle.js"></script>