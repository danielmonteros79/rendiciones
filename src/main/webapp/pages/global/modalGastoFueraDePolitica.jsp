<link rel="stylesheet" type="text/css" href="./css/select2.css">
<script type="text/javascript" src="static/js/global/modalGasto.js"></script>
<div class="modal fade" id="modalGastoFueraDePolitica" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false">
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
						<h1 class="font-weight-300"></h1>
					</div>
					<div id="modalGastoTitle" class="col-sm-12 pt-1 pb-4 text-muted" style="text-align: center; font-size: 30px;">
						
					</div>
														
				</div>
				<!-- REVISAR EL ID DEL SIGUIENTE DIV -->
				<div class="row px-5 mx-5 pb-3" id="modalGastoMessageContainer">
					<div class="col-sm-12" style="text-align: center; font-size: 20px;">
						<span id="modalGastoValidarMessage"></span>
					</div>
				</div>
			</div>
			<div class="modal-footer px-3 mx-3 px-lg-3 mx-lg-5">
				<div class="row px-5 mx-5">
					<div class="col-sm-12">
						<a href="#a" class="btn btn-link px-5 py-3 mr-2 font-weight-bold" data-dismiss="modal">
							<i class="bbva-icon icon-coronita_close"></i> Volver
						</a>
						<a id="aceptarGastoBtn" href="#a" class="btn btn-info px-5 py-3 ml-2" onclick="modalGastoCancelar()">
							
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="static/js/select2.min.js"></script>