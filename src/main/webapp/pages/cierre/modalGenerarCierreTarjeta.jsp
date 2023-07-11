<div class="modal fade" id="modalGenerarCierreTarjeta" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-xl" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<a href="#a" class="float-right" data-dismiss="modal">
					<i class="bbva-icon icon-coronita_close"></i>
				</a>
			</div>
			<div class="modal-body px-3 mx-3 px-lg-3 mx-lg-5">
				<div class="row px-5 mx-5">
					<div class="col-sm-12">
						<h1 class="font-weight-300">Generar cierre</h1>
					</div>
					<div class="col-sm-12 pt-1 pb-5 text-muted">
						Ingres&aacute; los datos a continuaci&oacute;n para generar el cierre.
					</div>
				</div>
				<div class="row px-5 mx-5 pb-3 d-none" id="modalGenerarCierreTarjetaMessageContainer">
					<div class="col-sm-12">
						<h5 id="modalGenerarCierreTarjetaMessage"></h5>
					</div>
				</div>
				<div class="row px-5 mx-5">
					<div class="col-sm-12 pt-2 scroll-err">
						<div class="has-float-label form-group">
							<select id="modalGenerarCierreTarjetaMotivo" class="form-control bg-light" required></select>
							<i class="bbva-icon icon-uniE003 text-primary"></i>
							<label for="modalGenerarCierreTarjetaMotivo">Motivo</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-sm-12 pt-2 scroll-err" id="modalGenerarCierreTarjetaTipoGastoDiv">
						<div class="has-float-label form-group">
							<select id="modalGenerarCierreTarjetaTipoGasto" class="form-control bg-light" required></select>
							<i class="bbva-icon icon-uniE003 text-primary"></i>
							<label for="modalGenerarCierreTarjetaTipoGasto">Tipo de gasto</label>
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
						<a href="#a" class="btn btn-info px-5 py-3 ml-2" onclick="modalGenerarCierreTarjetaGenerar()">
							Generar
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="js/cierre/modalGenerarCierreTarjeta.js"></script>