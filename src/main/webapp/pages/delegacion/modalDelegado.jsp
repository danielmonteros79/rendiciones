<div class="modal fade" id="modalDelegado" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false">
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
						<h1 class="font-weight-300"><span id="modalDelegadoNuevoModif"></span> delegado</h1>
					</div>
					<div class="col-sm-12 pt-1 pb-5 text-muted">
						Ingres&aacute; los datos del empleado que me sustituye (para cargar o aprobar).
					</div>
				</div>
				<div class="row px-lg-5 mx-5">
					<div class="col-sm-12 pt-2 scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light text-uppercase" id="modalDelegadoUsuario" placeholder="Usuario" maxlength="8" required/>
							<label for="modalDelegadoUsuario">Usuario</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-sm-12 col-lg-5 pt-2 pr-lg-1 scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light" id="modalDelegadoCCostos" placeholder="C. Costos" disabled/>
							<label for="modalDelegadoCCostos">C. Costos</label>
						</div>
					</div>
					<div class="col-sm-12 col-lg-5 pt-2 px-lg-1 scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light" id="modalDelegadoSector" placeholder="Sector" disabled/>
							<label for="modalDelegadoSector">Sector</label>
						</div>
					</div>
					<div class="col-sm-12 col-lg-2 pt-2 pl-lg-1 scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light" id="modalDelegadoEstado" placeholder="Estado" disabled/>
							<label for="modalDelegadoEstado">Estado</label>
						</div>
					</div>
					<div class="col-sm-12 pt-2 scroll-err">
						<div class="has-float-label">
							<input type="text" class="form-control bg-light" id="modalDelegadoNombre" placeholder="Nombre" disabled/>
							<label for="modalDelegadoNombre">Nombre</label>
						</div>
					</div>
					<div class="col-sm-12 pt-2 scroll-err">
						<div class="has-float-label form-group">
							<select id="modalDelegadoInforme" class="form-control bg-light" required>
								<option value="S">Si</option>
								<option value="N">No</option>
								<option value="D">Delegado</option>
							</select>
							<i class="bbva-icon icon-uniE003 text-primary"></i>
							<label for="modalDelegadoInforme">Informe</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-sm-12 pt-2 scroll-err">
						<div class="has-float-label form-group">
							<select id="modalDelegadoAccion" class="form-control bg-light" required>
								<option value="I">Ingreso de rendiciones</option>
								<option value="A">Aprobaci&oacute;n de rendiciones</option>
								<option value="T">Todas las anteriores</option>
							</select>
							<i class="bbva-icon icon-uniE003 text-primary"></i>
							<label for="modalDelegadoAccion">Acci&oacute;n</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-sm-12 col-lg-6 pt-2 pr-lg-1 has-float-label scroll-err">
						<div class="input-group">
							<input type="text" class="form-control datepicker bg-light" id="modalDelegadoFechaDesde"
								placeholder="Desde" required/>
							<label for="modalDelegadoFechaDesde">Desde</label>
							<div class="input-group-append">
								<button class="btn btn-outline-primary bg-light border-white hover-darkblue datepicker-btn" tabindex="-1" type="button">
									<i class="bbva-icon icon-coronita_calendar fa-lg"></i>
								</button>
							</div>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-sm-12 col-lg-6 pt-2 pl-lg-1 has-float-label scroll-err">
						<div class="input-group">
							<input type="text" class="form-control datepicker bg-light" id="modalDelegadoFechaHasta"
								placeholder="Hasta" required/>
							<label for="modalDelegadoFechaHasta">Hasta</label>
							<div class="input-group-append">
								<button class="btn btn-outline-primary bg-light border-white hover-darkblue datepicker-btn" tabindex="-1" type="button">
									<i class="bbva-icon icon-coronita_calendar fa-lg"></i>
								</button>
							</div>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
					<div class="col-sm-12 scroll-err">
						<div class="invalid-feedback mb-3" id="modalDelegadoValidacionFechas"></div>
					</div>
				</div>
				<input type="hidden" id="modalDelegadoFechaDesdeOld">
				<input type="hidden" id="modalDelegadoFechaHastaOld">
				<input type="hidden" id="modalDelegadoUserAlta">
				<input type="hidden" id="modalDelegadoFechaAlta">
			</div>
			<div class="modal-footer px-3 mx-3 px-lg-3 mx-lg-5">
				<div class="row px-5 mx-5">
					<div class="col-sm-12">
						<a href="#a" class="btn btn-link px-5 py-3 mr-2 font-weight-bold" data-dismiss="modal">
							<i class="bbva-icon icon-coronita_close"></i> Salir
						</a>
						<a href="#a" class="btn btn-info px-5 py-3 ml-2" onclick="modalDelegadoGuardar()">
							Guardar
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="js/delegacion/modalDelegado.js"></script>