<div class="modal fade" id="modalJournal" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-xl" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<a href="#a" class="float-right" data-dismiss="modal">
					<i class="bbva-icon icon-coronita_close"></i>
				</a>
			</div>
			<div class="modal-body px-3 mx-3 px-lg-3 mx-lg-5">
				<div class="row div-resultado pb-3" id="modalJournalDivResultado">
					<div class="col-sm-12">
						<h1 class="font-weight-300">Consulta de Journal</h1>
					</div>
				</div>
				
				<div class="row py-3">
					<div class="col-sm-12">
						<div class="col-sm-12 py-3 border bg-light">
							ID-Rendici&oacute;n:
							<span id="modalJournalIdRendicion"></span>
						</div>
					</div>
				</div>
				
				<div class="row" id="modalJournalTablaDiv">
					<div class="col-md-12 py-3 table-responsive-lg">
						<table class="table small" id="modalJournalTabla">
							<thead>
								<tr>
									<th>ORDEN</th>
									<th>ESTADO</th>
									<th>PR&Oacute;XIMO USUARIO</th>
									<th>USUARIO APROBADOR</th>
									<th>FECHA APROBACI&Oacute;N</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
						<h5 class="font-weight-400" id="modalJournalTablaVacia">La lista est&aacute; vac&iacute;a</h5>
					</div>
				</div>
			</div>
			<div class="modal-footer px-3 mx-3 px-lg-3 mx-lg-5">
				<div class="row">
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

<script type="text/javascript" src="js/global/modalJournal.js"></script>