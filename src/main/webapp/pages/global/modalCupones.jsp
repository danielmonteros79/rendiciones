<div class="modal fade" id="modalCupones" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-xl" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<a href="#a" class="float-right" data-dismiss="modal">
					<i class="bbva-icon icon-coronita_close"></i>
				</a>
			</div>
			<div class="modal-body px-3 mx-3 px-lg-3 mx-lg-5">
				<div class="row div-resultado pb-3" id="modalCuponesDivResultado">
					<div class="col-sm-12">
						<h1 class="font-weight-300" id="modalCuponesTitulo"></h1>
					</div>
				</div>
				<div class="py-3 d-none" id="modalCuponesMessageContainer">
					<h5 id="modalCuponesMessage"></h5>
				</div>
				
				<div class="row" id="modalCuponesTablaDiv">
					<div class="col-md-12 py-3 table-responsive-lg">
						<table class="table small" id="modalCuponesTabla">
							<thead>
								<tr>
									<th id="modalCuponesThTarjeta">TARJETA</th>
									<th>FECHA</th>
									<th>CUP&Oacute;N</th>
									<th>ESTABLECIMIENTO</th>
									<th>MONTO</th>
									<th id="modalCuponesThMontoUtiliz">MONTO UTILIZ.</th>
									<th>MONEDA</th>
									<th id="modalCuponesThSel">SEL.</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
						<h5 class="font-weight-400" id="modalCuponesTablaVacia">Tu lista de cupones est&aacute; vac&iacute;a</h5>
					</div>
				</div>
			</div>
			<div class="modal-footer px-3 mx-3 px-lg-3 mx-lg-5">
				<div class="row">
					<div class="col-sm-12">
						<a href="#a" class="btn btn-link px-5 py-3 mr-2 font-weight-bold" data-dismiss="modal">
							<i class="bbva-icon icon-coronita_close"></i> Salir
						</a>
						<a href="#a" class="btn btn-info px-5 py-3 ml-2 d-none" id="modalCuponesBtnContinuar" onclick="modalCuponesContinuar()">
							Continuar
						</a>
						<a href="#a" class="btn btn-info px-5 py-3 ml-2 d-none" id="modalCuponesBtnAsignar" onclick="modalCuponesAsignar()">
							Asignar
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="js/global/modalCupones.js"></script>