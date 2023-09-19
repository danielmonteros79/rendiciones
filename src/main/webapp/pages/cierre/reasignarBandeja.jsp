
<link rel="stylesheet" type="text/css" href="./css/select2.css">

<div class="container py-5" id="contianerResignarBandeja">
			<div class="row pb-1 d-none" id="messageContainer">
				<div class="col-sm-12">
					<h5 id="message"></h5>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12 mb-4">
					<i class="bbva-icon icon-coronita_bullet fab fa-rotate-270 text-blue align-middle mx-2"></i>
					<small class="font-weight-bold">REASIGNACI&Oacute;N DE BANDEJA</small>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12 mb-4 mt-3">
					<h2 class="font-weight-500">Seleccion&aacute; los usuarios para reasignar la bandeja</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<h5 class="pt-3 pb-5 text-danger" id="filtroMsgValidacion" style="display:none;"></h5>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-6 py-1 py-sm-0 col-sm-6">
					<div class="has-float-label" id ="usuarioErrorOrigen">
						<input type="text" class="form-control bg-light " id="legajoDelegadoOrigen" placeholder="ID" style="text-transform:uppercase;" maxlength="8" required/>
						<label >Legajo - usuario origen </label>
						<p class="invalid-feedback"></p>
					</div>
				
				</div>
				<div class="col-lg-6 py-1 py-sm-0 col-sm-6">
					<div class="has-float-label">
						<input readonly type="text" class="form-control text-uppercase bg-muted text-muted" id="nombreDelegadoOrigen" placeholder="Usuario" />
						<label class="text-muted" >Nombre Usuario</label>
					</div>
				</div>
				<div class="col-lg-6 py-1 py-sm-0 col-sm-6 mt-sm-3 mt-lg-3 ">
					<div class="has-float-label">
						<input type="text" class="form-control bg-light" id="legajoDelegadoDestino" placeholder="ID" style="text-transform:uppercase;" maxlength="8" required/>
						<label >Legajo - usuario Destino</label>
					<div class="invalid-feedback "></div>
					</div>
				</div>
				
				<div class="col-lg-6 py-1 py-sm-0 col-sm-6 mt-sm-3 mt-lg-3">
					<div class="has-float-label">
						<input readonly type="text" class="form-control bg-muted text-muted " id="nombreDelegadoDestino" placeholder="ID"/>
						<label class="text-muted">Nombre Usuario</label>
					</div>
				</div>
				
				<div class="col-lg-12  mt-sm-3 mt-lg-3">
					<div class=" form-group  has-float-label  bg-light" >
						<select id="comboTipoAprobacion" class="form-control basic-single" required>
							<option value="">Seleccione una opci&oacute;n</option>
							<option value="TODOS">TODAS (FIRMANTE y SUPERVISOR)</option>
							<option value="PFIRM">FIRMANTE</option>
							<option value="PSUP">SUPERVISOR</option>
							<option value="PGLG">GLG</option>
						</select>
						<label for="comboTipoAprobacion">Bandeja</label>
					</div>
				</div>
				
			</div>
	
</div>

<div class="bg-light" id="divAcciones">
	<div class="container py-4 text-center my-2">
		<div class="row">
			<div class="col-sm-12">
				<a href="#a" class="btn btn-primary px-5 py-3 mx-2" onclick="reasignar()">
					Reasignar
				</a>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript" src="static/js/select2.min.js"></script>
<script type="text/javascript" src="static/js/cierre/reasignarBandeja.js"></script>
