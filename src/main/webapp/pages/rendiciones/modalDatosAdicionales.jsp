<div class="modal fade" id="modalDatosAdicionales" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<a href="#a" class="float-right" data-dismiss="modal">
					<i class="bbva-icon icon-coronita_close"></i>
				</a>
			</div>
			<div class="modal-body px-5 mx-5">
				<div class="row px-5 mx-5">
					<div class="col-sm-12">
						<h1 class="font-weight-300">Datos adicionales del gasto</h1>
					</div>
				</div>
				<div class="row px-5 mx-5" id="modalDatosAdicionalesCampos"></div>
				
				<div class="row px-5 mx-5 my-3 text-right">
					<div class="col-sm-12">
						<a href="#a" class="btn btn-link px-5 py-3 mr-2 font-weight-bold" data-dismiss="modal">
							<i class="bbva-icon icon-coronita_close"></i> Salir
						</a>
						<a href="#a" class="btn btn-info px-5 py-3 ml-2" onclick="modalDatosAdicionalesGuardar()">
							Guardar
						</a>
					</div>
				</div>
				
				<div class="row px-5 mx-5" id="modalDatosAdicionalesTablaDiv">
					<div class="col-md-12 py-3 table-responsive-lg">
						<table class="table" id="modalDatosAdicionalesTabla">
							<thead>
								<tr>
									<th>NRO</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="js/rendiciones/modalDatosAdicionales.js"></script>