<style>
  .modal-background {
  	position:relative;
  }
  
  .link-overlay{
	top:318px;
	left:285px;
	position:absolute;
	display:block;
	width:100px;
	height:35px;
	background-color:rgba(0,0,0,0);
  }
   .link-overlay2{
	top:373px;
	left:285px;
	position:absolute;
	display:block;
	width:100px;
	height:35px;
	background-color:rgba(0,0,0,0);
  }
  
  .img-pop{
  width: 100%;
  padding: 0;
  }
  .modal-body{
  	padding: 0 .5rem .5rem .5rem;
  }

  .modal-header{
  padding: .6rem}
}
#container-modal{
	position: relative !important;
	height: 100vh !important;
}
#miModal{
	position:absolute !important;
	top:50% !important;
	left: 50% !important;
	transform: translate(-50%, -50%) !important;
	text-align:center !imporatnt;
	height: 100vh !important;



}
	
</style> 
<div id="container-modal">
	<div class="modal fade" id="miModal" tabindex="-1" role="dialog" aria-labelledby="miModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <a href="#a" class="float-right" class="close" data-dismiss="modal" >
	          <i class="bbva-icon icon-coronita_close"></i>
	        </a>
	      </div>
	      <div class="modal-body">
	      	<div class="modal-background">
	        	<img class="img-pop"src ="images/Inicio_PopUp.png" alt="Pop Inicio" >
	        	<a href="https://drive.google.com/file/d/1jbb0NfnVkOYn8z3IQpsqDVFnw2K1gn18/view" target="_blank" class="link-overlay" ></a>
	       		<a href="https://drive.google.com/file/d/1qWLOC96Pwzk6BGv9qVVurnDmiz8EphNu/view" target="_blank" class="link-overlay2" ></a>
	        </div>
	      </div>
	    </div>
	  </div>
	</div>
</div>

<script>
	let mostrarModalInicio = ${modalInicio}
	$(document).ready(function() { 
		mostrarModalInicio ? $('#miModal').modal('show') : $('#miModal').modal('hide')

	});
	

</script>

