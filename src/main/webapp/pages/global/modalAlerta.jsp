<div class="modal fade" style="top: 10%; " id="modalAlerta" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false">
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
						<h1 class="font-weight-300 text-danger"> Alerta <span id="modalAlertaTitulo" ></span></h1>
						<hr class="bg-danger" style="height: 0.3rem;">
					</div>
					<div class="col-sm-12 pt-1 pb-3 text-muted h6">
						Detalle de alerta
					</div>
				</div>
				<div class="row px-lg-5 mx-5">
					<div class="col-sm-12 pt-2 scroll-err">
						<div class="has-float-label">
							<ol class="h5 text-danger" id="itemsAlerta">
							</ol>
						</div>
					</div>
					
					<div class="col-sm-12 scroll-err">
						<div class="invalid-feedback mb-3" id="modalDelegadoValidacionFechas"></div>
					</div>
				</div>
		
			</div>
			<div class="modal-footer px-3 mx-3 px-lg-3 mx-lg-5">
				<div class="row ">
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

<script type="text/javascript" src="js/global/modalAlerta.js"></script>