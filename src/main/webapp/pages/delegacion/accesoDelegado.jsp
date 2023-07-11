<link rel="stylesheet" type="text/css" href="./css/select2.css">

<div class="container py-5">
	<div class="row pb-3 d-none" id="messageContainer">
		<div class="col-sm-12">
			<h5 id="message"></h5>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-12 mb-md-4">
			<i class="bbva-icon icon-coronita_bullet fab fa-rotate-270 text-blue align-middle mx-2"></i>
			<small class="font-weight-bold">DELEGACI&Oacute;N</small>
		</div>
	</div>
	<div class="row pb-3 pt-3 pb-md-4">
		<div class="col-sm-12">
			<h2 class="font-weight-500">Seleccion&aacute; el usuario al que quer&eacute;s reemplazar</h2>
		</div>
	</div>
	<div class="pb-3 font-weight-bold">
		<div class="row">
			<div class="col-sm-12 pt-2 scroll-err">
				<div class="has-float-label form-group">
					<select id="delegado" class="form-control bg-light" required></select>
					<label for="delegado">Usuario</label>
					<div class="invalid-feedback mb-3"></div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="bg-light" id="divAcciones">
	<div class="container py-4 text-center">
		<div class="row">
			<div class="col-sm-12">
				<a href="#a" class="btn btn-primary px-5 py-3 mx-2" onclick="reemplazar()">
					Reemplazar
				</a>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="static/js/select2.min.js"></script>
<script type="text/javascript" src="static/js/delegacion/accesoDelegado.js"></script>