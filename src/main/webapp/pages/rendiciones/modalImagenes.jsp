<div class="modal fade" id="modalImagenes" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<a href="#a" class="float-right" data-dismiss="modal">
					<i class="bbva-icon icon-coronita_close"></i>
				</a>
			</div>
			<div class="modal-body px-5 mx-5">
				<div class="row px-5 mx-5 pb-3">
					<div class="col-sm-12">
						<h1 class="font-weight-300">Im&aacute;genes</h1>
					</div>
				</div>
				<div class="row px-5 mx-5">
					<div class="col-sm-12">
						<h5 class="py-3 text-danger d-none" id="modalImagenesMsgValidacion"></h5>
					</div>
				</div>
				<div class="row px-5 mx-5">
					<div class="col-sm-12">
						<div class="row px-3">
							<div class="col-sm-1 pt-4 bg-warning-light text-center">
								<i class="bbva-icon fa-lg icon-coronita_alarm text-warning"></i>
							</div>
							<div class="col-sm-11 pt-4 pl-0 bg-warning-light">
								<h4 class="font-weight-400">Adjuntar im&aacute;genes</h4>
							</div>
						</div>
						<div class="row px-3">
							<div class="col-sm-1 pt-2 pr-4 pb-4 bg-warning-light"></div>
							<div class="col-sm-11 pt-2 pr-4 pb-4 pl-0 bg-warning-light">
								- Se deben informar todos los comprobantes que ayuden a la comprensi&oacute;n del gasto.<br>
								- No debe superar los 1.2 Mb. en los archivos .TIF.<br>
								- La im&aacute;genes pueden ser .PDF o .TIF (.TIF solo si fue generado por las herramientas de la sucursal).<br>
								- En el caso que la imagen no sea clara o existan problemas para visualizarla se solicitar&aacute; que env&iacute;e el comprobante 
								  al sector que lo solicite o directamente se rechazar&aacute; la rendici&oacute;n.<br>
								- Se deben resguardar los comprobantes hasta que la rendici&oacute;n se encuentre finalizada.<br>
							</div>
						</div>
					</div>
				</div>
				<div class="row px-5 py-3 mx-5">
					<div class="px-3 w-100">
						<div class="btn btn-light w-100 p-0" onclick="modalImagenesSeleccionarArchivo()">
							<div class="col-sm-12 p-0 has-float-label">
								<div class="input-group">
									<input type="text" class="form-control bg-transparent cursor-pointer" readonly
										value="Seleccion&aacute; el archivo que quer&eacute;s subir"/>
									<label class="cursor-pointer">Agregar nueva imagen</label>
									<div class="input-group-append">
										<button class="btn btn-outline-primary bg-transparent hover-primary" tabindex="-1" type="button">
											<i class="bbva-icon icon-coronita_upload fa-lg"></i>
										</button>
									</div>
								</div>
							</div>
						</div>
						<div class="invalid-feedback mb-3"></div>
						<input type="file" id="modalImagenesArchivo" accept=".pdf,.tif" class="d-none" onchange="modalImagenesAgregarArchivo()"/>
					</div>
				</div>
				<div class="row px-5 mx-5">
					<div class="col-md-12 table-responsive-lg">
						<table class="table small" id="modalImagenesTabla">
							<thead>
								<tr>
									<th class="border-top-0">IM&Aacute;GENES SELECCIONADAS</th>
									<th class="w-1 border-top-0"></th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
						<h5 class="font-weight-400" id="modalImagenesTablaVacia">Tu lista de im&aacute;genes est&aacute; vac&iacute;a</h5>
					</div>
				</div>
			</div>
			<div class="modal-footer px-5 mx-5">
				<div class="row px-5 mx-5">
					<div class="col-sm-12">
						<a href="#a" class="btn btn-link px-5 py-3 mr-2 font-weight-bold" data-dismiss="modal">
							<i class="bbva-icon icon-coronita_close"></i> Salir
						</a>
						<a href="#a" class="btn btn-info px-5 py-3 ml-2" onclick="modalImagenesGenerar()">
							Generar
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="js/rendiciones/modalImagenes.js"></script>