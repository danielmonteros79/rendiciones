<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="modal fade" id="modalDatosAdicionales" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-xl" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<a href="#a" class="float-right" data-dismiss="modal">
					<i class="bbva-icon icon-coronita_close"></i>
				</a>
			</div>
			<div class="modal-body px-3 mx-3 px-lg-3 mx-lg-5">
				<div class="row px-5 mx-5 pb-3">
					<div class="col-sm-12">
						<h1 class="font-weight-300">Datos adicionales del gasto</h1>
					</div>
				</div>
				<div class="row px-5 mx-5 pb-3 d-none" id="modalDatosAdicionalesMessageContainer">
					<div class="col-sm-12">
						<h5 id="modalDatosAdicionalesMessage"></h5>
					</div>
				</div>
				<div class="row px-5 mx-5 pb-3 d-none" id="modalDatosAdicionalesActionMessageContainer">
					<div class="col-sm-12">
						<h5 id="modalDatosAdicionalesActionMessage"></h5>
					</div>
				</div>
				
				<div class="row px-5 mx-5" id="modalDatosAdicionalesCampos"></div>
				
				<div class="row px-5 mx-5 my-3 text-right d-none d-not-readonly">
					<div class="col-sm-12">
						<a href="#a" class="btn btn-info px-5 py-3 ml-2" onclick="modalDatosAdicionalesGuardar()">
							Guardar
						</a>
						<a href="#a" id="modalDatosAdicionalesBtnSalir" class="btn btn-link px-5 py-3 mr-2 font-weight-bold d-none d-new" data-dismiss="modal">
							<i class="bbva-icon icon-coronita_close"></i> Salir
						</a>
						<a href="#a" id="modalDatosAdicionalesBtnCancelar" class="btn btn-link px-5 py-3 mr-2 font-weight-bold d-none d-edit"
							onclick="modalDatosAdicionalesCancelar()">
							<i class="bbva-icon icon-coronita_close"></i> Cancelar
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
				
				<div class="row px-3 mx-3 px-lg-3 mx-lg-5 my-3 text-right d-none d-readonly">
					<div class="col-sm-12">
						<a href="#a" class="btn btn-link px-5 py-3 mr-2 font-weight-bold" data-dismiss="modal">
							<i class="bbva-icon icon-coronita_close"></i> Salir
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="static/js/global/modalDatosAdicionales.js"></script>