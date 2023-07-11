<div class="modal fade" id="modalAprobarRendicion" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<a href="#a" class="float-right" data-dismiss="modal">
					<i class="bbva-icon icon-coronita_close"></i>
				</a>
			</div>
			<div class="modal-body px-3 mx-3 px-lg-3 mx-lg-5">
				<div class="row">
					<div class="col-sm-12 pb-3">
						&iquest;Est&aacute;s seguro que quer&eacute;s aprobar la rendici&oacute;n?
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12 pt-2 scroll-err">
						<div class="has-float-label">
							<textarea class="form-control bg-light" id="modalAprobarRendicionComentario" placeholder="(113 caracteres)" rows="5" maxlength="113"
								required></textarea>
							<label>Comentario</label>
							<div class="invalid-feedback mb-3"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer px-3 mx-3 px-lg-3 mx-lg-5">
				<div class="row">
					<div class="col-sm-12">
						<a href="#a" class="btn btn-link px-5 py-3 mr-2 font-weight-bold" data-dismiss="modal">
							<i class="bbva-icon icon-coronita_close"></i> Salir
						</a>
						<a href="#a" class="btn btn-info px-5 py-3 ml-2" onclick="modalAprobarRendicionAprobar()">
							Aprobar
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="static/js/aprobacion/modalAprobarRendicion.js"></script>