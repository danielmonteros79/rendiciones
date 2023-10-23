<link rel="stylesheet" type="text/css" href="./css/select2.css">
<script type="text/javascript" src="static/js/global/modalGasto.js"></script>
<div class="modal fade" id="modalGasto" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-xl" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<a href="#a" class="float-right" data-dismiss="modal">
					<i class="bbva-icon icon-coronita_close"></i>
				</a>
			</div>
			<div class="modal-body px-3 mx-3 px-lg-3 mx-lg-5">
				<div class="row px-lg-5 px-sm-2 mx-5">
					<div class="col-sm-12">
						<h1 class="font-weight-300">Detalle de gasto/consumo <span id="modalGastoConCupon"> con cup&oacute;n</span></h1>
					</div>
					<div class="col-sm-12 pt-1 pb-4 text-muted">
						Ingres&aacute; toda la informaci&oacute;n de detalle sobre el gasto o consumo que se realiz&oacute;<span id="modalGastoMsgCupon"></span>
					</div>
					<div class="col-sm-12 pt-1 pb-4 ">
						<h5 class="text-warning" id="modalGastoDetalleCupon"><span  id="modalGastoDetalleCuponDesc"></span></h5>
						<div class="text-warning h5 font-weight-normal" id="modalGastoCuponesCant"></div>
					</div>
														
				</div>
				<div class="row px-5 mx-5 pb-3 d-none" id="modalGastoMessageContainer">
					<div class="col-sm-12">
						<h5 id="modalGastoMessage"></h5>
					</div>
				</div>
				<div class="row px-lg-5 px-sm-2 mx-5 scroll-err">
					<div class="col-lg-2 px-3 col-sm-12">
						<small class="text-muted">ID-Rendici&oacute;n</small>
						<br>
						<span id="modalGastoIdRendicion"></span>
					</div>
					<div class="col-lg-5 col-sm-12 px-3">
						<small class="text-muted">Usuario</small>
						<br>
						<span id="modalGastoUsuario"></span>
					</div>
					<div class="col-lg-2 col-sm-12 py-2">
						<small class="text-muted">C. Costos</small>
						<br>
						<span id="modalGastoCCostos"></span>
					</div>
					<div class="col-lg-3 col-sm-12 py-2">
						<div class="has-float-label">
							<input type="text" class="form-control bg-white an-integer-pos" id="modalGastoCCostosDestino" placeholder="C. Costos Destino" readonly required />
							<label for="modalGastoCCostosDestino">C. Costos Destino</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
				</div>
				<div class="row px-lg-5 px-sm-2 mx-5">
					<div class="col-sm-12 pt-2 scroll-err">
						<div class="has-float-label form-group">
							<select id="modalGastoTipoGasto" class="form-control bg-light" required></select>
							<i class="bbva-icon icon-uniE003 text-primary"></i>
							<label for="modalGastoTipoGasto">Tipo de Gasto</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-sm-12 pt-2 scroll-err">
						<div class="has-float-label form-group">
							<select id="modalGastoMoneda" class="form-control bg-light" required></select>
							<i class="bbva-icon icon-uniE003 text-primary"></i>
							<label for="modalGastoMoneda">Moneda</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-sm-12 pt-2 has-float-label scroll-err">
						<div class="input-group">
							<input type="text" class="form-control datepicker bg-light" id="modalGastoFechaGasto"
								placeholder="Fecha de realizaci&oacute;n del gasto" required/>
							<label for="modalGastoFechaGasto">Fecha de realizaci&oacute;n del gasto</label>
							<div class="input-group-append">
								<button class="btn btn-outline-primary bg-light border-white hover-darkblue datepicker-btn" tabindex="-1" type="button">
									<i class="bbva-icon icon-coronita_calendar fa-lg"></i>
								</button>
							</div>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-sm-12 pt-2 scroll-err" id="modalGastoTipoComprobanteDiv">
						<div class="has-float-label form-group">
							<select id="modalGastoTipoComprobante" class="form-control bg-light" required></select>
							<i class="bbva-icon icon-uniE003 text-primary"></i>
							<label for="modalGastoTipoComprobante">Tipo de comprobante</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-sm-2 pt-2 scroll-err modalGastoFacturaCuitDiv">
						<div class="has-float-label">
							<select id="modalGastoTipoFactura" class="form-control bg-light" required>
								<option value="A">A</option>
								<option value="B">B</option>
								<option value="C">C</option>
							</select>
							<i class="bbva-icon icon-uniE003 text-primary"></i>
							<label for="modalGastoTipoFactura">Tipo Factura</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-sm-5 pt-2 px-0 scroll-err modalGastoFacturaCuitDiv">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light" id="modalGastoFactura" placeholder="Factura" required data-length="12"/>
							<label for="modalGastoFactura">Factura</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-sm-5 pt-2 scroll-err modalGastoFacturaCuitDiv">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light" id="modalGastoCuit" placeholder="CUIT" required data-length="11" data-cuit/>
							<label for="modalGastoCuit">CUIT</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-sm-12 pt-2 scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light an-float-pos" id="modalGastoMonto" placeholder="Monto" required/>
							<label for="modalGastoMonto">Monto</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-sm-12 pt-2 scroll-err">
						<div class="has-float-label">
							<textarea class="form-control bg-light" id="modalGastoObservaciones" placeholder="(120 caracteres)" rows="5" maxlength="120"></textarea>
							<label for="modalGastoObservaciones">Observaciones</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer px-3 mx-3 px-lg-3 mx-lg-5">
				<div class="row px-5 mx-5">
					<div class="col-sm-12">
						<a href="#a" class="btn btn-link px-5 py-3 mr-2 font-weight-bold" data-dismiss="modal">
							<i class="bbva-icon icon-coronita_close"></i> Salir
						</a>
						<a href="#a" class="btn btn-info px-5 py-3 ml-2" onclick="modalGastoGuardar()">
							Guardar
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="static/js/select2.min.js"></script>
